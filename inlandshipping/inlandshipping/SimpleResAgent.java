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
    public int whatIf(Vessel vessel, int arrivalTime, Segment direction) {
        // TODO
        return 0;
    }
    
    /**
     * Calculates a new scheduling from the current reservations
     */
    public void updateScheduling() {
        timeTable.clear();
        // TODO: vessels currently in transfer should be stored somewhere and added here.
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
        // See when the ship can start transferring, and add empty transferslots if needed.
        int start = prepareStartTime(r.getArrivalTime(), r.getDirection());
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
    
    private int prepareStartTime(int arrivalTime, Segment direction) {
        // TODO
        if (timeTable.get(arrivalTime) != null) {
            // Some other vessel is in transit
            // Find the first available slot
        }
        else {
            // No other vessel is in transit
            // Find the last event, and see in what position it left the lock
            
        }
        return 0;
    }

}
