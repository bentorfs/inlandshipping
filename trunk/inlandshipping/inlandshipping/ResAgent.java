package inlandshipping;

import java.util.Iterator;
import java.util.Vector;

public abstract class ResAgent {
	
    /****************************************************************
     * Constructor
     ****************************************************************/
	public ResAgent() {
	    
	}
	
    /****************************************************************
     * Lock responsibility
     ****************************************************************/
	   
	/*
     * The lock this agent is responsible for.
     */
    private Lock lock;
    
    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
	
    /****************************************************************
     * Manage reservations
     ****************************************************************/
	
	/*
     * The reservations kept by this agent.
     */
    private Vector<LockReservation> reservations = new Vector<LockReservation>();
	
	/**
	 * Add a reservation to the list of reservations of this agent.
	 */
	private void addReservation(LockReservation reservation) {
	    reservations.add(reservation);
	}
	
	/**
	 * Returns the reservation of a given vessel at this lock.
	 * Returns null if the given vessel has no reservation.
	 */
	public LockReservation getReservationOf(Vessel v) {
		Iterator<LockReservation> i = getReservations().iterator();
	    while (i.hasNext()) {
	        LockReservation res = i.next();
	        if (res.getVessel() == v) {
	            return res;
	        }
	    }
	    return null;
	}
	
	
	/**
	 * Remove all reservations by the given vessel
	 */
	public void removeReservationsBy(Vessel v) {
		Vector<LockReservation> toRemove = new Vector<LockReservation>();
	    Iterator<LockReservation> i = getReservations().iterator();
	    while (i.hasNext()) {
	        LockReservation res = i.next();
	        if (res.getVessel() == v) {
	        	toRemove.add(res);
	        }
	    }
	    i = toRemove.iterator();
	    while (i.hasNext()) {
	    	LockReservation res = i.next();
	    	removeReservation(res);
	    }
	}
	
	/**
	 * Removes the given reservation from the list.
	 */
	private void removeReservation(LockReservation res) {
	    reservations.remove(res);
	}
	
	/**
	 * Returns the reservations made at this agent.
	 */
	protected Vector<LockReservation> getReservations() {
	    //return (Vector<LockReservation>) reservations.clone();
		return reservations;
	}
	
	/**
	 * Prints a list of all reservations to the terminal
	 */
	public void printReservations() {
		System.out.println("-- Reservations for " + this + "---");
		Iterator<LockReservation> i = getReservations().iterator();
		while (i.hasNext()) {
			LockReservation lr = i.next();
			System.out.println(lr.toString());
		}
		System.out.println("-----------------");
		
	}
	
	/**
     * Prints the current timetable to the terminal
     */
    public abstract void printTimeTable();
	
	/**
	 * Removes a reservation from the list of reservations.
	 * @param reservation
	 */
	void expire(LockReservation reservation) {
		reservations.remove(reservation);
	}
	
	/**
	 * Decreases the TTL of all reservations stored by 1,
	 * and removes those whose TTL drops below zero.
	 */
	private void decreaseTTLs() {
		Iterator<LockReservation> i = reservations.iterator();
		Vector<LockReservation> toDelete = new Vector<LockReservation>();
		while (i.hasNext()) {
		    LockReservation r = i.next();
			if (r.decreaseTTL()) {
			    toDelete.add(r);
			}
		}
		i = toDelete.iterator();
		while (i.hasNext()) {
		    expire(i.next());
		}
	}
	
    /****************************************************************
     * Methods used by vessels
     ****************************************************************/
	
	/**
	 * Method returns when, according to current scheduling, a vessel could have traversed
	 * the lock, if it arrived at the specified time from the specified direction.
	 */
	public abstract int whatIf(Vessel vessel, int arrivalTime, Segment direction, int timeNow);
	
	/**
	 * Create a new reservation for a vessel arriving at the given time from the given direction.
	 * If the vessel made another reservation at this agent, it is removed.
	 */
	public void makeReservation(Vessel vessel, int arrivalTime, Segment direction) {
	    //System.out.println("a reservation has been made for time " + arrivalTime + " at " + getLock());
	    LockReservation reservation = new LockReservation(this,vessel,arrivalTime,direction);
	    LockReservation existingRes = getReservationOf(vessel);
	    if (existingRes == null) {
	    	addReservation(reservation);
	    } else if (!existingRes.equals(reservation)) {
	    	removeReservationsBy(vessel);
	    	addReservation(reservation);
	    }
	    else {
	    	// Vessel already made this reservation
	    }
	}
	
	/**
	 * Calculates a new scheduling from the current reservations
	 */
	public abstract void updateScheduling(int time);
	
    /****************************************************************
     * Make decisions
     ****************************************************************/
	
	/**
	 * This method is to be called at each timepoint during the simulation.
	 * 
	 * @param time The current timepoint of the simulation.
	 */
	public void act(int time) {
	    // Decrease the TTL of all reservations
	    decreaseTTLs();
	    // Create a new scheduling based on the remaining reservations
	    updateScheduling(time);
	    
	    //printReservations();
    	//printTimeTable();
	    
	    // Perform the actions of this timepoint
	    performActions(time);
	}
	
	protected abstract void performActions(int time);


}
