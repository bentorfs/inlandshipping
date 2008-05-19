package inlandshipping;

import java.util.Vector;

public abstract class Lock {
	/*
	 * List of ships that are currently waiting on the 'next' and 'previous' side of the lock
	 */
	private Vector<Vessel> waitingNext;
	private Vector<Vessel> waitingPrevious;
	
	/*
	 * The time needed to change the level of a chamber of the lock.s
	 */
	private final int timeNeeded;
	
	/**
	 * Constructs a new lock with the given duration for changing the level of a chamber.
	 */
	public Lock(int timeNeeded) {
	    this.timeNeeded = timeNeeded;
	}
	
}
