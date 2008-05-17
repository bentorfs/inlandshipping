package inlandshipping;

import inlandshipping.Characteristics.*;

/**
 * Class represents a vessel that has to travel from startNode to destinationNode.
 */
public class Vessel {
    private Node startNode;
    private Node destinationNode;
    
    private Size size;
    private Speed topSpeed;
    private Speed currentSpeed = Speed.STILL;
    private Segment currentPosition;
    private Cargo cargo = Cargo.EMPTY;
    
    private TaskAgent agent;
    
    /**
     * Constructs a new vessel with given size, top speed and start and destination node
     */
    public Vessel(Node startNode, Node destinationNode, Size size, Speed topSpeed) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.size = size;
        this.topSpeed = topSpeed;
        this.agent = new TaskAgent();
        // TODO: dees zorgt da het schip in feite vertrekt vanuit het eerste segment van de eerste fairway
        // die aan de startnode verbonden is. Wat ni echt galant is. Misschien moeten we toch modelleren da
        // schepen vertrekken in segmenten ipv nodes.
       // this.currentSegment = startNode.getFairways().get(0).getSegmentFromNode(startNode);
        setCurrentPosition(startNode);
    }
    
    /******************************************************
     * 				CHARACTERISTICS
     ******************************************************/
    
  
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

    private void setCurrentPosition(Segment position) {
    	currentPosition = position;
    }
    public void moveToNextSegment() {
    	setCurrentPosition(getCurrentPosition().getNextNeighbour());
    }
    
    
}
