package inlandshipping;

import java.util.ArrayList;


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
    
    private Segment getPreviousSegment(){
    	return previousSegment;
    }
    
    private void setPreviousSegment(Segment previous){
    	previousSegment = previous;
    }
    
    /*
     * 2 possibilities:
     * 	1) on a normal segment, there is only one way to go because he can't turn around.
     * 	2) he is in a node: I) start : previousSegment == null
     * 						II) previousSegment != null
     */
    public void moveToNextSegment(ArrayList<Fairway> path) {
    	if(!(getCurrentPosition() instanceof Node)){
    		ArrayList<Segment> neighbours = getCurrentPosition().getNeighbours();
    		if(neighbours.get(0) == previousSegment){
    			setPreviousSegment(getCurrentPosition());
    			setCurrentPosition(neighbours.get(1));
    		}else{
    			setPreviousSegment(getCurrentPosition());
    			setCurrentPosition(neighbours.get(0));
    			
    		}
    	}else{
    		
    	}
    	
    }
    
}
