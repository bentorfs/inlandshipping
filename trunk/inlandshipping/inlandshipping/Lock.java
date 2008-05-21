package inlandshipping;

import java.util.Vector;

public abstract class Lock extends Segment {
	/*
	 * List of ships that are currently waiting on the each of the sides of the lock.
	 */
	private Vector<Vessel> waitingSideOne;
	private Vector<Vessel> waitingSideTwo;
	
    public Vector<Vessel> getWaitingSideOne() {
        return waitingSideOne;
    }

    public Vector<Vessel> getWaitingSideTwo() {
        return waitingSideTwo;
    }
    
    /****************************************************************
     * Constructor
     ****************************************************************/

    /**
     * Constructs a new lock with the given duration for changing the level of a chamber,
     * part of the given fairway.
     */
    public Lock(Fairway fairway, int timeNeeded) {
        super(fairway);
        this.timeNeeded = timeNeeded;
    }

    /****************************************************************
     * Neighbour/side information
     ****************************************************************/
    
    public Segment getSideOne() {
        return getNeighbours().get(0);
    }
    
    public Segment getSideTwo() {
        return getNeighbours().get(1);
    }


    /****************************************************************
     * Time needed for filling chambers
     ****************************************************************/

    /*
     * The time needed to change the level of a chamber of the locks
     */
    private final int timeNeeded;
    
	/**
	 * Returns the time needed to change the level of a chamber of this lock.
	 * @return
	 */
    public int getTimeNeeded() {
        return timeNeeded;
    }
    
    /****************************************************************
     * Agent information
     ****************************************************************/
    protected ResAgent agent;

    public ResAgent getAgent() {
        return agent;
    }


}
