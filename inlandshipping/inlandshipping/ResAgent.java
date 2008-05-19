package inlandshipping;

import java.util.Iterator;
import java.util.Vector;

public class ResAgent {
	/*
	 * The reservations kept by this agent.
	 */
	private Vector<LockReservation> reservations = new Vector<LockReservation>();
	
	/**
	 * Removes a reservation from the list of reservations.
	 * @param reservation
	 */
	public void expire(LockReservation reservation) {
		reservations.remove(reservation);
	}
	
	/**
	 * Decreases the TTL of all reservations of this agent by 1.
	 */
	public void increaseTTL() {
		Iterator<LockReservation> i = reservations.iterator();
		while (i.hasNext()) {
			i.next().decreaseTTL();
		}
	}

}
