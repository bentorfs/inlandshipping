package inlandshipping;

/**
 * Class represents a simple lock consisting of just one large chamber.
 */
public class SimpleLock extends Lock {
    /**
     * Construct a new simple lock with the given duration for changing the level.
     */
    public SimpleLock(Fairway fairway, int timeNeeded, SimpleResAgent agent) {
        super(fairway, timeNeeded);
        this.agent = agent;
        SimpleResAgent a = (SimpleResAgent) agent;
        a.setLock(this);
    }
    
    /*
     * The vessel currently in the chamber of this lock.
     */
    private Vessel vesselInChamber;

    /*
     * Returns the vessel in the chamber of this lock.
     */
    public Vessel getVesselInChamber() {
        return vesselInChamber;
    }

    /*
     * Sets the vessel in the chamber of this lock to the given vessel.
     */
    public void setVesselInChamber(Vessel vesselInChamber) {
        this.vesselInChamber = vesselInChamber;
    }
}
