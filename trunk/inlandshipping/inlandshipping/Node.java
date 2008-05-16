package inlandshipping;

import java.util.Vector;

public class Node {
    private Vector<Fairway> fairways;
    
    /**
     * Constructs a new node with no fairways attached.
     * @param fairways
     */
    public Node() {
        this.fairways = new Vector<Fairway>();
    }
    
    /**
     * Attaches the given fairway to this node.
     */
    public void attachFairway(Fairway fairway) {
        fairways.add(fairway);
    }
    
    public Vector<Fairway> getFairways() {
        return (Vector<Fairway>) fairways.clone();
    }
}
