package inlandshipping;

public class ParallelLock extends Lock {
    
    /**
     * Construct a new parallel lock with the given duration for changing the level.
     */
    public ParallelLock(int timeNeeded) {
        super(timeNeeded);
    }

}
