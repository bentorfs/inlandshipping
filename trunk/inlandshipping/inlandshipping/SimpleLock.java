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
    }
}
