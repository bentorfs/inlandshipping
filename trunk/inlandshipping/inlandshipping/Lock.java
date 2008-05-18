package inlandshipping;

import java.util.Vector;

public class Lock {
	/*
	 * List of ships that are currently waiting on the 'next' and 'previous' side of the lock
	 */
	private Vector<Vessel> waitingNext;
	private Vector<Vessel> waitingPrevious;
	
}
