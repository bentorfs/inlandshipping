package inlandshipping;

/**
 * This class represents a reservation for use of a specific lock by a vessel, at some time.
 * It expires if not renewed on time.
 */
public class LockReservation implements Comparable<LockReservation> {
    
    /****************************************************************
     * Constructor
     ****************************************************************/
    
    /**
     * Constructs a new reservation for a lock.
     */
    LockReservation(ResAgent agent, Vessel vessel, int arrivalTime, Segment direction) {
        this.agent = agent;
        this.vessel = vessel;
        this.arrivalTime = arrivalTime;
        this.direction = direction;
        this.TTL = Configuration.reservationTTL;
        this.ranking = arrivalTime;
    }
    
	/*
	 * Amount of timesteps before this reservation expires.
	 */
	private int TTL;
	
	 /**
     * Decreases the time-to-live of this reservation by one, and removes itself from the reservationlist if TTL becomes < 0.
     *
     */
    void decreaseTTL() {
        TTL--;
        if (TTL < 0) {
            agent.expire(this);
        }
    }
	
	/*
	 * Vessel that made this reservation.
	 */
	private Vessel vessel;
	
    /**
     * Returns the vessel that made this reservation
     */
    public Vessel getVessel() {
        return vessel;
    }
	
	/*
	 * Arrival time for this reservation
	 */
	private int arrivalTime;
	
    /**
     * Returns the arrival time of this reservation
     */
    public int getArrivalTime() {
        return arrivalTime;
    }
	
	/*
	 * Integer used for ranking the reservations for scheduling algorithms
	 */
	private int ranking;
	
    /**
     * Set the ranking variable to the given value.
     */
    void setRanking(int ranking) {
        this.ranking = ranking;
    }
    
    /**
     * Retrieve the ranking variable.
     */
    public int getRanking() {
        return ranking;
    }
	
	/*
	 * Direction this vessel will be coming from.
	 */
	private Segment direction;
	
    /**
     * Returns the direction from which the ship will come in this reservation.
     */
    public Segment getDirection() {
        return direction;
    }
	
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

    /****************************************************************
     * General
     ****************************************************************/

    /**
     * Compares this LockReservation to the given one, this is done by the ranking variable.
     */
    public int compareTo(LockReservation other) {
        return ((Integer) getRanking()).compareTo(other.getRanking());
    }







}
