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
	 * Returns the reservations made at this agent.
	 */
	protected Vector<LockReservation> getReservations() {
	    return (Vector<LockReservation>) reservations.clone();
	}
	
	/**
	 * Removes a reservation from the list of reservations.
	 * @param reservation
	 */
	void expire(LockReservation reservation) {
		reservations.remove(reservation);
	}
	
	/**
	 * Decreases the TTL of all reservations of this agent by 1.
	 */
	private void increaseTTL() {
		Iterator<LockReservation> i = reservations.iterator();
		while (i.hasNext()) {
			i.next().decreaseTTL();
		}
	}
	
    /****************************************************************
     * Methods used by vessels
     ****************************************************************/
	
	/**
	 * Method returns when, according to current scheduling, a vessel could have traversed
	 * the lock, if it arrived at the specified time from the specified direction.
	 */
	public abstract int whatIf(Vessel vessel, int arrivalTime, Segment direction);
	
	/**
	 * Create a new reservation for a vessel arriving at the given time from the given direction.
	 */
	public void makeReservation(Vessel vessel, int arrivalTime, Segment direction) {
	    LockReservation reservation = new LockReservation(this,vessel,arrivalTime,direction);
	    addReservation(reservation);
	}
	
	/**
	 * Calculates a new scheduling from the current reservations
	 */
	public abstract void updateScheduling();
	
    /****************************************************************
     * Make decisions
     ****************************************************************/
	
	/**
	 * This method is to be called at each timepoint during the simulation.
	 */
	public void act() {
	    increaseTTL();
	    updateScheduling();
	    // TODO: performActions() ofzo... schepen effectief verplaatsen. O
	    // Als het schip da momenteel aan de beurt is er niet is, reservatie schrappen
	    // en updateScheduling opnieuw doen.
	}


}
