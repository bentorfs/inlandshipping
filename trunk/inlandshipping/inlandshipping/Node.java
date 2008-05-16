package inlandshipping;

import java.util.Vector;

public class Node extends Segment{
    private Vector<Fairway> fairways;
    
    /**
     * Constructs a new node, with the given fairway attached.
     */
    public Node(Fairway fairway) {
    	super(fairway);
        this.fairways = new Vector<Fairway>();
        attachFairway(fairway);
    }
    
    /**
     * Constructs a new node, with no fairway attached.
     *
     */
    public Node(){
    	this.fairways = new Vector<Fairway>();
    }
    
    /**
     * Attaches the given fairway to this node.
     */
    public void attachFairway(Fairway fairway) {
        fairways.add(fairway);
    }
    
    /**
     * Returns all fairways connected to this node.
     * @return
     */
    public Vector<Fairway> getFairways() {
        return (Vector<Fairway>) fairways.clone();
    }
}
