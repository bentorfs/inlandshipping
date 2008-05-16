package inlandshipping;

/**
 * Class represents a segment; a small part of a fairway.
 */
public class Segment {
    private Segment nextSegment;
    private Segment previousSegment;
    
    protected Fairway fairway;
    
    /**
     * Constructs a new segment, as part of the given fairway.
     */
    public Segment(Fairway fairway) {
        this.fairway = fairway;
    }
    
    /**
     * Constructs a new segment, not part of any fairway.
     *
     */
    protected Segment(){
    	
    }
    
    /**
     * Initializes the neighbouring segments for this segment.
     */
    public void setNeighbours(Segment previousSegment, Segment nextSegment) {
        this.nextSegment = nextSegment;
        this.previousSegment = previousSegment;
    }
    
    /**
     * Returns the segment before this one of the fairway.
     */
    public Segment getPreviousNeighbour(){
    	return previousSegment;
    }
    
    /**
     * Returns the segment after this one of the fairway.
     */
    public Segment getNextNeighbour(){
    	return nextSegment;
    }
    
    /**
     * Returns the fairway this segment belongs to.
     */
    public Fairway getFairway() {
    	return fairway;
    }
}
