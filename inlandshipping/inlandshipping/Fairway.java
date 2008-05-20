package inlandshipping;


/**
 * Class representing a fairway.
 */
public class Fairway {
    private int nbLanes;
    private int nbLanesInUse = 0;
    private int length;
    private Speed maxSpeed;
    
    private Node node1;
    private Node node2;
    
    private Segment[] segments;
   
    /**
     * Constructs a fairway from the given startnode to the given endnode. The length
     * is the number of segments this fairway is composed of.
     */
    public Fairway(Node node1, Node node2, int nbLanes, int length, Speed maxSpeed) {
        this.nbLanes = nbLanes;
        this.maxSpeed = maxSpeed;
        this.node1 = node1;
        this.node2 = node2;
        node1.attachFairway(this);
        node2.attachFairway(this);
        this.length = length;
        constructSegments();
    }
    
    public int getLength(){
    	return length;
    }
    
    /**
     * Constructs the segments composing this fairway and initializes the neighbours.
     */
    private void constructSegments() {
        this.segments = new Segment[length];
        for (int i=0; i<length; i++) {
            segments[i] = new Segment(this);
        }
        node1.addNeighbour(segments[0]);
        node2.addNeighbour(segments[length-1]);
        segments[0].addNeighbour(node1);
        segments[0].addNeighbour(segments[1]);
        segments[length-1].addNeighbour(segments[length-2]);
        segments[length-1].addNeighbour(node2);
        for (int i=1; i<length-1; i++) {
        	segments[i].addNeighbour(segments[i-1]);
        	segments[i].addNeighbour(segments[i+1]);
        }
    }
    
    public Segment[] getSegments(){
    	return segments;
    }
    
    public Segment getNeighbourSegmentOfNode(Node node){
    	if(node == node1)return getSegments()[0];
    	else return getSegments()[getSegments().length -1];
    }
    
    public Node getOtherNode(Node node){
    	return(node == node1 ? node2 : node1);
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }
    
  
}
