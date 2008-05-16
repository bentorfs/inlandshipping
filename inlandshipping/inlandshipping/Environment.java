package inlandshipping;

import java.util.Vector;

/**
 * This class represents the environment: topography, and implements the methodes
 * by which the environment reacts to decisions from agents.
 */
public class Environment {
    private Vector<Node> nodes;
    
    private Vector<Vessel> vessels;
    
    /**
     * This constructor builds an example topography.
     */
    public Environment() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        
        // new Fairway(from,to,nbLanes,length,maxSpeed)
        Fairway way1 = new Fairway(node1,node2,2,1000,5);
        Fairway way2 = new Fairway(node2,node3,2,2000,5);
        Fairway way3 = new Fairway(node3,node1,2,1000,5);
        
        nodes = new Vector<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        
        // new Vessel(source,destination,size,maxSpeed)
        Vessel vessel1 = new Vessel(node1,node3,1,3);
        vessels = new Vector<Vessel>();
        vessels.add(vessel1);
    }
    
    /**
     * Returns a vector with the vessels in this environment
     */
    public Vector<Vessel> getVessels() {
        return (Vector<Vessel>) vessels;
    }
    
    
    public void reactTo() {
        
    }
}