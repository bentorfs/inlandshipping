package inlandshipping;


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
    private Segment previousSegment;
    private Cargo cargo = Cargo.EMPTY;
    
    private boolean isWorking = true;
    
    private TaskAgent agent;
    
    /**
     * Constructs a new vessel with given size, top speed and start and destination node
     * @throws InterruptedException 
     * @throws CloneNotSupportedException 
     */
    public Vessel(Node startNode, Node destinationNode, Size size, Speed topSpeed) throws InterruptedException, CloneNotSupportedException {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.size = size;
        this.topSpeed = topSpeed;
        this.agent = new TaskAgent(this);
        // TODO: dees zorgt da het schip in feite vertrekt vanuit het eerste segment van de eerste fairway
        // die aan de startnode verbonden is. Wat ni echt galant is. Misschien moeten we toch modelleren da
        // schepen vertrekken in segmenten ipv nodes.
       // this.currentSegment = startNode.getFairways().get(0).getSegmentFromNode(startNode);
        setCurrentPosition(startNode);
        this.previousSegment = null;
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
    
    public Node nextNode(Node node){
    	return getCurrentPosition().getFairway().getOtherNode(node);
    }
    
    
}
