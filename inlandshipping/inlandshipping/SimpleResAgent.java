package inlandshipping;

import java.util.HashMap;
import java.util.Iterator;
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
        // Keep a backup of the current timetable
        HashMap<Integer,SchedulingElem> timeTableBackup = (HashMap<Integer,SchedulingElem>) timeTable.clone();
        makeReservation(vessel, arrivalTime, direction);
        updateScheduling(timeNow);
        int result = findPassThroughTime(vessel, arrivalTime);
        // Put the original timetable back in place
        timeTable = timeTableBackup;
        return result;
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
        // Sort the reservations according to arrival time
        java.util.Collections.sort(getReservations());
        Iterator<LockReservation> i = getReservations().iterator();
        while (i.hasNext()) {
            LockReservation r = i.next();
            schedule(r);
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
    private void schedule(LockReservation r) {
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
        // See which direction the vessel is transferring in
        SchedulingEvent event = (r.getDirection() == getLock().getSideOne() ? SchedulingEvent.TRANSIT_TO_2 : SchedulingEvent.TRANSIT_TO_1);
        // Add the intransit blocks
        for (int i=0; i<getLock().getTimeNeeded(); i++) {
            elem = new SchedulingElem(r.getVessel(), event);
            timeTable.put(start+i,elem);
        }
        // Add the endtransit block
        elem = new SchedulingElem(r.getVessel(), SchedulingEvent.ENDTRANSIT);
        timeTable.put(start + getLock().getTimeNeeded(),elem);
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
            while (timeTable.get(time) == null) {
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
        // See if we have to add empty transfer slots.
        if ((timeTable.get(time - 2).getEvent() == SchedulingEvent.TRANSIT_TO_1 && direction == getLock()
                .getSideOne())
                || (timeTable.get(time - 2).getEvent() == SchedulingEvent.TRANSIT_TO_2 && direction == getLock()
                        .getSideTwo())) {
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
            return time + getLock().getTimeNeeded() - 1;
        }
    }
    
    /**
     * This method acts upon the vessels waiting on both sides of the lock
     * performing the calculated schedule.
     */
    protected void performActions(int time) {
        // See what has to happen at this moment, according to the schedule.
        SchedulingElem task = timeTable.get(time);
        SimpleLock lock = (SimpleLock) getLock();
        // if task is null or transit, nothing needs to be done.
        // if it is starttransit or endtransit, ships need to be moved.
        if (task != null && task.getEvent() == SchedulingEvent.ENDTRANSIT) {
            Vessel v = lock.getVesselInChamber();
            if (v.getPreviousSegment() == lock.getSideOne()) {
                v.setCurrentPosition(lock.getSideTwo());
            }
            else if (v.getPreviousSegment() == lock.getSideTwo()) {
                v.setCurrentPosition(lock.getSideOne());
            }
            lock.setVesselInChamber(null);
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
            if (e.getVessel() == vessel)
                return i;
            i++;
        }
        
    }

}
