package inlandshipping;

import java.util.Vector;


/**
 * Class representing a fairway.
 */
public class Fairway {
    
	/******************************************************
	 * 			INSTANTIATION VARIABLES					  *
	 ******************************************************/
	
	/*
	 * Number of lanes of the fairway.
	 */
	private int nbLanes;
	
	/*
	 * The max speed that is currently alowed on this fairway.
	 * TODO NIET NODIG!!!
	 */
    private Speed maxSpeed;
    
    /*
     * The name of the fairway.
     */
    public String name;
    
    /*
     * The neighbouring nodes of this fairway
     */
    private Node node1;
    private Node node2;
    
    /*
     * The segments this fairway is composed of
     */
    private Segment[] segments;
    
    /*
     * The length of this fairway
     */
    private int length;
    
    /*
     * The locks that are located on this fairway
     */
    private Vector<Lock> locks = new Vector<Lock>();
    

  /********************************************************
   * 			CONSTRUCTOR
   ********************************************************/
    
    /**
     * Constructs a fairway from the given startnode to the given endnode. The length
     * is the number of segments this fairway is composed of.
     * The new fairway will have locks positioned on all positions in lockpositions.
     */
    public Fairway(Node node1, Node node2, int nbLanes, int length, Speed maxSpeed, Vector<Integer> lockPositions, String name) {
        setNbLanes(nbLanes);
        //TODO 
        this.maxSpeed = maxSpeed;
        setNode1(node1);
        setNode2(node2);
        node1.attachFairway(this);
        node2.attachFairway(this);
        setLength(length);
        constructSegments(lockPositions);
        setName(name);
    }
    
    /******************************************************
     * 			SETTERS AND GETTERS			  			  *
     ******************************************************/
    
    /**
     * Returns the number of lanes of this fairway.
     */
    public int getNbLanes() {
    	return nbLanes;
    }
    
    /**
     * Sets the given number as the number of lanes of this fairway.
     */
    private void setNbLanes(int number) {
    	nbLanes = number;
    }
    
    /**
     * Returns the first node of the fairway.
     */
    public Node getNode1() {
        return node1;
    }
    
    /**
     * Sets the given node as the first node of the fairway.
     */
    private void setNode1(Node node) {
    	node1 = node;
    }
     
    /**
     * Returns the second node of the fairway.
     */
    public Node getNode2() {
        return node2;
    }    
    
    /**
     * Sets the given node as the second node of the fairway.
     */
    private void setNode2(Node node) {
    	node2 = node;
    }
    
    /**
     * Returns the length of the fairway (the nodes not included).
     * @return
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Sets the given length as the length of this fairway.
     */
    private void setLength(int length) {
    	this.length = length;
    }
    
    /**
     * Returns the name of the fairway.
     */
    public String getName(){
    	return name;
    }
    
    /**
     * Sets the given name as the name of this fairway.
     */
    private void setName(String name){
    	this.name =  name;
    }
    /**
     * Returns the locks on this fairway.
     */
    public Vector<Lock> getLocks() {
        return locks;
    }
    
    /**
     * Returns the segments of the fairway (the nodes not included).
     */
    public Segment[] getSegments(){
        return segments;
    }
    
    /**
     * Returns the neighbour segment of the given node from this fairway.
     * OK
     */
    public Segment getNeighbourSegmentOfNode(Node node){
    	if(node == node1)return getSegments()[0];
    	else return getSegments()[getSegments().length -1];
    }
    
    /**
     * Returns the other node then the given node of this fairway.
     * OK
     */
    public Node getOtherNode(Node node){
    	return(node == node1 ? node2 : node1);
    }
    
    /******************************************************
     * 			CONSTRUCTION OF FAIRWAY
     ******************************************************/
    
    /**
     * Constructs the segments composing this fairway and initializes the neighbours.
     */
    private void constructSegments(Vector<Integer> lockPositions) {
        this.segments = new Segment[length];
        for (int i=0; i<length; i++) {
            if (lockPositions.contains(i)) {
                // A lock should be built
                segments[i] = new SimpleLock(this,Configuration.lockTimeNeeded,new SimpleResAgent());
                // Keep the created lock in a list
                locks.add((Lock) segments[i]);
            }
            else {
                // A regular segment should be built
                segments[i] = new Segment(this);
            }
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
    
   




    
  
}
