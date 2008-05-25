package inlandshipping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class SimpleResAgent extends ResAgent {

    /****************************************************************
     * Constructor
     ****************************************************************/
    public SimpleResAgent() {

    }
    
    /****************************************************************
     * Methods used by vessels
     ****************************************************************/
    
    /**
     * Method returns when, according to current scheduling, a vessel could have traversed
     * the lock, if it arrived at the specified time from the specified direction.
     */
    public int whatIf(Vessel vessel, int arrivalTime, Segment direction, int timeNow) {
    	
    	// See if this vessel has this reservation already
    	// If so, just return the information of that reservation
    	LockReservation res = getReservationOf(vessel);
    	if (res != null && res.getArrivalTime() == arrivalTime
    			&& res.getDirection() == direction) {
    		updateScheduling(timeNow);
    		//System.out.println("whatif returns " + findPassThroughTime(vessel,arrivalTime));
    		
    		return findPassThroughTime(vessel,arrivalTime);
    	}
    	// Otherwise, make a tentative reservation and see what the result would be.
    	else {
    		// Keep a backup of the current timetable
    		HashMap<Integer,SchedulingElem> timeTableBackup = (HashMap<Integer,SchedulingElem>) timeTable.clone();
        	makeReservation(vessel, arrivalTime, direction);
        	updateScheduling(timeNow);
        	int result = findPassThroughTime(vessel, arrivalTime);
        	removeReservationsBy(vessel);
        	// Put the original timetable back in place
        	timeTable = timeTableBackup;
        	
        	return result;
    	}
    }

    /**
     * Calculates a new (FIFO) scheduling from the current reservations
     */
    public void updateScheduling(int time) {
        // If there is a transfer going on right now, store all related SchedulingElem's in inTransfer
        HashMap<Integer,SchedulingElem> inTransfer = new HashMap<Integer,SchedulingElem>();
        int at = time;
        SchedulingElem doingAt = timeTable.get(at);
        SimpleLock lock = (SimpleLock) getLock();
        // The first check is to ignore reservations of ships that did not show up
        // The second check is to ignore empty events
        // The third check is to continue copying until a starttransit event is found
        while (lock.getVesselInChamber() != null 
                && doingAt != null && doingAt.getEvent() != SchedulingEvent.STARTTRANSIT) {
            inTransfer.put(at,doingAt);
            at++;
            doingAt = timeTable.get(at);
        }
        // Clear the timetable
        timeTable.clear();
        // Add all stored events to the empty timetable
        timeTable.putAll(inTransfer);
        
        //System.out.println("size of timetable at start of scheduling: " + timeTable.size());
        
        // Sort the reservations according to arrival time
        java.util.Collections.sort(getReservations());
        
        Iterator<LockReservation> i = getReservations().iterator();
        while (i.hasNext()) {
            LockReservation r = i.next();
            schedule(r,time);
        }
    }
    
    /****************************************************************
     * Manage the schedule
     ****************************************************************/
    
    /*
     * HashMap containing the schedule in a mapping from integer timepoints to scheduling elements.
     */
    private HashMap<Integer,SchedulingElem> timeTable = new HashMap<Integer,SchedulingElem>();
    
    /**
     * Adds the given reservation to the schedule.
     */
    private void schedule(LockReservation r,int timeNow) {
        // See when the ship can start transferring.
        int start = prepareStartTime(r.getArrivalTime());
        // Add empty transferslots if needed, and get the resulting new start time.
        start = addEmptySlots(start, r.getDirection());
        if (start < r.getArrivalTime()) {
            // The lock is ready before the vessel arrives, 
            // of course, the transaction can only start when it arrives.
            start = r.getArrivalTime();
        }
        // Add the starttransit block
        SchedulingElem elem = new SchedulingElem(r.getVessel(), SchedulingEvent.STARTTRANSIT);
        timeTable.put(start,elem);
        //System.out.println("putting starttransit at " + start + " for vessel " + r.getVessel());
        // See which direction the vessel is transferring in
        SchedulingEvent event = (r.getDirection() == getLock().getSideOne() ? SchedulingEvent.TRANSIT_TO_2 : SchedulingEvent.TRANSIT_TO_1);
        // Add the intransit blocks
        for (int i=1; i<=getLock().getTimeNeeded(); i++) {
            elem = new SchedulingElem(r.getVessel(), event);
            timeTable.put(start+i,elem);
            //System.out.println("putting intransit at " + (start+i) + " for vessel " + r.getVessel());
        }
        // Add the endtransit block
        elem = new SchedulingElem(r.getVessel(), SchedulingEvent.ENDTRANSIT);
        //System.out.println("putting endtransit at " + (start + getLock().getTimeNeeded() + 1) + " for vessel " + r.getVessel());
        timeTable.put(start + getLock().getTimeNeeded() + 1,elem);
    }
    
    /**
     * Returns the first empty slot for a ship arriving at arrivalTime
     */
    private int prepareStartTime(int arrivalTime) {
        int time;
        if (timeTable.get(arrivalTime) != null) {
            // Some other vessel is in transit
            // Find the first available slot
            time = arrivalTime + 1;
            while (timeTable.get(time) != null) {
                time++;
            }
        }
        else {
            // No other vessel is in transit
            // Find the last event
            time = arrivalTime - 1;
            // The second check is in case there was no prior event
            while (timeTable.get(time) == null && time >= (arrivalTime - getLock().getTimeNeeded())) {
                time--;
            }
            // Time is now the last occupied position, we need the first unoccupied one.
            time++;
        }
        return time;
    }
    
    /**
     * Returns when a vessel, arriving at given time from given direction,
     * could enter the lock. Possibly after inserting empty transfer slots.
     */
    private int addEmptySlots(int time, Segment direction) {
        // time is now the timepoint at which there is no vessel in the lock.
        // If nothing is happening prior to the given time, the given time is okay.
        // This is in the case no vessel has ever used the lock.
        if (timeTable.get(time - 2) == null) {
            return time;
        }
        // See if we have to add empty transfer slots.
        if (( timeTable.get(time - 2).getEvent() == SchedulingEvent.TRANSIT_TO_1 
                && direction == getLock().getSideOne())
                || (timeTable.get(time - 2).getEvent() == SchedulingEvent.TRANSIT_TO_2 
                && direction == getLock().getSideTwo())) {
            // It's okay, don't add empty transfer slots
            return time;
        } else {
            // It's not okay, add the empty transfers lots
            SchedulingEvent event = (timeTable.get(time - 2).getEvent() == SchedulingEvent.TRANSIT_TO_1) ? SchedulingEvent.TRANSIT_TO_2
                    : SchedulingEvent.TRANSIT_TO_1;
            for (int i = 0; i < getLock().getTimeNeeded(); i++) {
                SchedulingElem elem = new SchedulingElem(null, event);
                timeTable.put(time + i, elem);
            }
            return time + getLock().getTimeNeeded();
        }
    }
    
    /**
     * This method acts upon the vessels waiting on both sides of the lock
     * performing the calculated schedule.
     */
    protected void performActions(int time) {
    	//System.out.println("performing actions at time " + time);
    	
        // See what has to happen at this moment, according to the schedule.
        SchedulingElem task = timeTable.get(time);
        SimpleLock lock = (SimpleLock) getLock();
        // if task is null or transit, nothing needs to be done.
        // if it is starttransit or endtransit, ships need to be moved.
        if (task != null && task.getEvent() == SchedulingEvent.ENDTRANSIT) {
            Vessel v = lock.getVesselInChamber();
            if (v.getPreviousSegment() == lock.getSideOne()) {
                v.setCurrentPosition(lock.getSideTwo());
                v.setPreviousSegment(getLock());
            }
            else if (v.getPreviousSegment() == lock.getSideTwo()) {
                v.setCurrentPosition(lock.getSideOne());
                v.setPreviousSegment(getLock());
            }
            lock.setVesselInChamber(null);
            //removeReservationsBy(v);
        }
        else if (task != null && task.getEvent() == SchedulingEvent.STARTTRANSIT) {
            Vessel currentVessel = task.getVessel();
            if (lock.getWaitingSideOne().contains(currentVessel)) {
                // The scheduled vessel is waiting at side one
                lock.getWaitingSideOne().remove(currentVessel);
                lock.setVesselInChamber(currentVessel);
            }
            else if (lock.getWaitingSideTwo().contains(currentVessel)) {
                // The scheduled vessel is waiting at side two
                lock.getWaitingSideTwo().remove(currentVessel);
                lock.setVesselInChamber(currentVessel);
            }
            else {
                // The scheduled vessel is not present
                // Remove his reservation
                removeReservationsBy(currentVessel);
                //System.out.println(getReservationOf(currentVessel));
                updateScheduling(time);
                System.out.println("a vessel did not show up on time");
            }
        }
    }
    
    /**
     * Searches the timetable to find when the given vessel
     * will have passed through the lock.
     */
    private int findPassThroughTime(Vessel vessel, int arrivalTime) {
        int i = arrivalTime;
        while (true) {
            SchedulingElem e = timeTable.get(i);
            
            // DEBUG
            if (e == null) {
            	System.out.println("nothing was scheduled on a moment a ship arrived");
            }
            
            if (e != null && e.getVessel() == vessel && e.getEvent() == SchedulingEvent.ENDTRANSIT)
                return i;
            i++;
        }
        
    }
    
    /**
     * Prints the current timetable to the terminal
     */
    public void printTimeTable() {
    	Set<Integer> keyset = timeTable.keySet();
    	Vector<Integer> keys = new Vector<Integer>(keyset);
    	Collections.sort(keys);
    	Iterator<Integer> i = keys.iterator();
    	System.out.println("----Timetable for " + this.toString() + "---");
    	while (i.hasNext()) {
    		int current = i.next();
    		SchedulingElem e = timeTable.get(current);
    		System.out.println("At time " + current + ": \t" + e.toString());
    	}
    	System.out.println("-------------------");
    }

}
