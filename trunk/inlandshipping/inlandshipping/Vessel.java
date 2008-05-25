package inlandshipping;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Class represents a vessel that has to travel from startNode to destinationNode.
 */
public class Vessel {
    private Node startNode;
    private Node destinationNode;
    
    private Speed currentSpeed = Speed.STILL;
    private Segment currentPosition;
    private Segment previousSegment;
    
    /**
     * Cargo properties of the vessel.
     */
    private Cargo cargo = Cargo.FULL;
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
    
    /*
     * The top speed of the vessel.
     */
    private Speed topSpeed;
    public Speed getTopSpeed() {
        return topSpeed;
    }

    /*
     * The size of the vessel.
     */
    private Size size;
    public Size getSize() {
        return size;
    }
    
    /*
     * The number of segments passed so far on the fairway the vessel is floating on currently.
     */
    private int nbSegmentsPassed;
    
    private boolean isWorking = true;
    
    private TaskAgent agent;
    
    /**
     * Constructs a new vessel with given size, top speed and start and destination node
     * @throws InterruptedException 
     * @throws CloneNotSupportedException 
     */
    public Vessel(Node startNode, Node destinationNode, Size size, Speed topSpeed) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.size = size;
        this.topSpeed = topSpeed;
        this.agent = new TaskAgent(this);
        setCurrentPosition(startNode);
        this.previousSegment = null;
        nbSegmentsPassed = 0;
    }
    
    /******************************************************
     * 				CHARACTERISTICS
     ******************************************************/
    
    public Speed getCurrentSpeed(){
    	return currentSpeed;
    }
    public boolean isWorking(){
    	return isWorking;
    }
    
    public Node getDestination(){
    	return destinationNode;
    }
    
    public Node getSource(){
    	return startNode;
    }
    
    private void setSource(Node node){
    	startNode = node;
    }
    /**
     * Returns the Task Agent for this vessel
     */
    public TaskAgent getAgent() {
        return agent;
    }
    
    /******************************************************
     * 				MOVEMENT
     ******************************************************/
    public Segment getCurrentPosition() {
    	return currentPosition;
    }

    public void setCurrentPosition(Segment position) {
    	currentPosition = position;
    }
    
    public Segment getPreviousSegment(){
    	return previousSegment;
    }
    
    public void setPreviousSegment(Segment previous){
    	previousSegment = previous;
    }
    
    /*
     * 3 possibilities:
     * 	1) on a normal segment, there is only one way to go because he can't turn around.
     * 	2) he is in a node: I) start : previousSegment == null
     * 						II) previousSegment != null
     *  3) he is in a lock and doesn't need to do anything.
     *  
     * When a vessel reaches a node, the source has to change.
     * 
     * Invariant: At the beginning a ship can only start in a Node!
     */

    public void moveToNextSegment(ArrayList<Fairway> path) {
        nbSegmentsPassed++;
        if (getCurrentPosition() instanceof Lock) {
            // He is in a lock
            // Notify the lock
            Lock lock = (Lock) getCurrentPosition();
            lock.arrival(this);
        } else if (getCurrentPosition() instanceof Node) {
            // He is in a node
            if (getPreviousSegment() == null) {
                setPreviousSegment(getCurrentPosition());
                setCurrentPosition(path.get(0).getNeighbourSegmentOfNode(
                        (Node) getCurrentPosition()));
            } else {
                setPreviousSegment(getCurrentPosition());
                Node thisNode = (Node) getCurrentPosition();
                Vector<Fairway> possibleFairways = thisNode.getFairways();
                // er kunnen geen 2 fairways zowel in path als in de fairways
                // van
                // de node zitten, anders gaat het schip in een lus
                // --> ALTIJD ZO??? kvind geen tegenvoorbeeld
                for (int i = 0; i < path.size(); i++) {
                    for (int j = 0; j < possibleFairways.size(); j++) {
                        if (path.get(i) == possibleFairways.get(j)) {
                            setCurrentPosition(path.get(i)
                                    .getNeighbourSegmentOfNode(thisNode));
                        }
                    }
                }
            }
            if (getCurrentPosition() instanceof Node) {
                setSource((Node) getCurrentPosition());
                nbSegmentsPassed = 0;
                if (getCurrentPosition() == getDestination()) {
                    System.out.println("Vessel has reached target");
                    setCargo(Cargo.EMPTY);
                }
            }
        } else {
            // He is in a regular segment
            ArrayList<Segment> neighbours = getCurrentPosition()
                    .getNeighbours();
            if (neighbours.get(0) == previousSegment) {
                setPreviousSegment(getCurrentPosition());
                setCurrentPosition(neighbours.get(1));
            } else {
                setPreviousSegment(getCurrentPosition());
                setCurrentPosition(neighbours.get(0));
            }
            if (getCurrentPosition() instanceof Node) {
                setSource((Node) getCurrentPosition());
                nbSegmentsPassed = 0;
                if (getCurrentPosition() == getDestination()) {
                    System.out.println("Vessel has reached target");
                    setCargo(Cargo.EMPTY);
                }
            }
        }
    }
    
    /*
     * This method returns the percentage of road the vessel has passed 
     * so far on his current fairway.
     * In a node this will always be 0%. -> probleem 100%???
     */
    
    public double GetPercentageOfFairwayPassed() {
    	return nbSegmentsPassed / getCurrentPosition().getFairway().getLength()+1;
    }

    /*
     * Returns the number of segments the vessel has to pass on the current fairway to reach
     * the next node.
     */
    public int getNbSegmentsToGo() {
    	if(getCurrentPosition() instanceof Node) return 0;
    	else return getCurrentPosition().getFairway().getLength() - nbSegmentsPassed + 1;
    }


}
