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
    
    protected Segment(){
    	
    }
    /**
     * Initializes the neighbouring segments for this segment.
     */
    public void setNeighbours(Segment nextSegment, Segment previousSegment) {
        this.nextSegment = nextSegment;
        this.previousSegment = previousSegment;
    }
}