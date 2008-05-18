package inlandshipping;

/**
 * This class represents a reservation for use of a specific lock by a vessel, at some time.
 * It expires if not renewed on time.
 */
public class LockReservation {
	/*
	 * Amount of timesteps before this reservation expires.
	 */
	private int TTL;
	
	/*
	 * Vessel that made this reservation.
	 */
	private Vessel vessel;
	
	/*
	 * Arrival time for this reservation
	 */
	private int arrivalTime;
	
	/*
	 * Direction this vessel will be coming from.
	 */
	private Segment from;
	
	/*
	 * Agent that keeps this reservation.
	 */
	private ResAgent agent;
	
	/**
	 * Returns the agent that keeps this reservation.
	 */
	public ResAgent getAgent() {
		return agent;
	}
	
	
	/**
	 * Constructs a new reservation for a lock.
	 * @param vessel
	 * @param arrivalTime
	 * @param from
	 */
	public LockReservation(ResAgent agent, Vessel vessel, int arrivalTime, Segment from) {
		this.agent = agent;
		this.vessel = vessel;
		this.arrivalTime = arrivalTime;
		this.from = from;
		this.TTL = Configuration.reservationTTL;
	}
	
	/**
	 * Decreases the time-to-live of this reservation by one, and destructs if TTL becomes < 0.
	 *
	 */
	void decreaseTTL() {
		TTL--;
		if (TTL < 0) {
			agent.expire(this);
		}
	}


}
