package inlandshipping;

import java.util.Vector;

public abstract class Lock extends Segment {
	/*
	 * List of ships that are currently waiting on the each of the sides of the lock.
	 */
	private Vector<Vessel> waitingSideOne = new Vector<Vessel>();
	private Vector<Vessel> waitingSideTwo = new Vector<Vessel>();
	
    public Vector<Vessel> getWaitingSideOne() {
        return waitingSideOne;
    }

    public Vector<Vessel> getWaitingSideTwo() {
        return waitingSideTwo;
    }
    
    /**
     * Returns whether the given vehicle is currently in transit in this lock
     * (instead of just waiting)
     */
    protected abstract boolean inTransit(Vessel v);
    
    /**
     * This method signifies the arrival of a vessel in the lock,
     * it should be added to the waiting list if it isn't already in it.
     */
    public void arrival(Vessel v) {
        if (v.getPreviousSegment() == getSideOne() 
                && !getWaitingSideOne().contains(v)
                && !inTransit(v)) {
            getWaitingSideOne().add(v);
        }
        else if (v.getPreviousSegment() == getSideTwo() 
                && !getWaitingSideTwo().contains(v)
                && !inTransit(v)) {
            getWaitingSideTwo().add(v);
        }
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
