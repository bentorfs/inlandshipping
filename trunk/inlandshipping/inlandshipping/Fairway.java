package inlandshipping;


/**
 * Class representing a fairway.
 */
public class Fairway {
    private int nbLanes;
    private int nbLanesInUse = 0;
    private int length;
    private Speed maxSpeed;
    
    private Node startNode;
    private Node endNode;
    
    private Segment[] segments;
   
    /**
     * Constructs a fairway from the given startnode to the given endnode. The length
     * is the number of segments this fairway is composed of.
     */
    public Fairway(Node startNode, Node endNode, int nbLanes, int length, Speed maxSpeed) {
        this.nbLanes = nbLanes;
        this.maxSpeed = maxSpeed;
        this.startNode = startNode;
        this.endNode = endNode;
        startNode.attachFairway(this);
        endNode.attachFairway(this);
        this.length = length;
        constructSegments();
    }
    
    /**
     * Constructs the segments composing this fairway and initializes the neighbours.
     */
    private void constructSegments() {
        this.segments = new Segment[length];
        for (int i=0; i<length; i++) {
            segments[i] = new Segment(this);
        }
        segments[0].setNeighbours(startNode,segments[1]);
        segments[length-1].setNeighbours(segments[length-2], endNode);
        for (int i=1; i<length-1; i++) {
            segments[i].setNeighbours(segments[i-1],segments[i+1]);
        }
    }
    
    /**
     * This method returns the first segment a vessel has to traverse when it enters
     * this fairway via the given node.
     */
//    public Segment getSegmentFromNode(Node node) {
//        if (node == startNode) {
//            return segments[0];
//        }
//        else {
//            return segments[length];
//        }
//    }

}
