package inlandshipping;

import java.util.ArrayList;

/**
 * Class represents a segment; a small part of a fairway.
 */
public class Segment {
    private Segment nextSegment;
    private Segment previousSegment;
    
    private ArrayList<Segment> neighbours;
    
    protected Fairway fairway;
    
    
    /******************************************************
     * 			CONSTRUCTORS
     ******************************************************/
    
    /**
     * Constructs a new segment, as part of the given fairway.
     */
    public Segment(Fairway fairway) {
        this.fairway = fairway;
        neighbours = new ArrayList<Segment>();
    }
    
    /**
     * Constructs a new segment, not part of any fairway. This is for Nodes. 
     * Nodes belong to different fairways. When fairways get attached to a node, the node will 
     * know all the fairways it belongs to.
     */
    protected Segment(){
    	neighbours = new ArrayList<Segment>();
    }
    
    /******************************************************
     * 			NEIGHBOURS OF SEGMENTS
     ******************************************************/
    
    public void addNeighbour(Segment segment){
    	neighbours.add(segment);
    }
    
    public ArrayList<Segment> getNeighbours(){
    	return neighbours;
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
