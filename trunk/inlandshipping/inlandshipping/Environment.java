package inlandshipping;


import java.util.Vector;

/**
 * This class represents the environment: topography, and implements the methodes
 * by which the environment reacts to decisions from agents.
 */
public class Environment {
    private Vector<Node> nodes;
    
    private Vector<Vessel> vessels;
    
    private Vector<Fairway> fairways;
    
    /**
     * This constructor builds an example topography.
     * @throws InterruptedException 
     * @throws CloneNotSupportedException 
     */
    public Environment() throws InterruptedException, CloneNotSupportedException {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        nodes = new Vector<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        
        // new Fairway(from,to,nbLanes,length,maxSpeed)
        Fairway way1 = new Fairway(node1,node2,2,1000,Speed.SLOW);
        Fairway way2 = new Fairway(node2,node3,2,2000,Speed.SLOW);
        Fairway way3 = new Fairway(node3,node1,2,1000,Speed.FAST);
        fairways = new Vector<Fairway>();
        fairways.add(way1);
        fairways.add(way2);
        fairways.add(way3);
        
        // new Vessel(source,destination,size,maxSpeed)
        Vessel vessel1 = new Vessel(node1,node3,Size.SMALL, Speed.SLOW);
        vessels = new Vector<Vessel>();
        vessels.add(vessel1);
    }
    
    /**
     * Returns a vector with the vessels in this environment
     */
    public Vector<Vessel> getVessels() {
        return vessels;
    }
    
    /**
     * Returns a vector with the nodes in this environment
     */
    public Vector<Node> getNodes() {
    	return nodes;
    }
    
    /**
     * Returns a vector with the fairways in this environment
     */
    public Vector<Fairway> getFairways() {
    	return fairways;
    }
    
    
    public void reactTo() {
        
    }
}
