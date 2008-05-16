package inlandshipping;

/**
 * Class represents a vessel that has to travel from startNode to destinationNode.
 */
public class Vessel {
    private Node startNode;
    private Node destinationNode;
    
    private int size;
    private int topSpeed;
    
    private int currentSpeed = 0;
    private Segment currentSegment;
 
    
    private TaskAgent agent;
    
    /**
     * Constructs a new vessel with given size, top speed and start and destination node
     */
    public Vessel(Node startNode, Node destinationNode, int size, int topSpeed) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.size = size;
        this.topSpeed = topSpeed;
        this.agent = new TaskAgent();
        
        // TODO: dees zorgt da het schip in feite vertrekt vanuit het eerste segment van de eerste fairway
        // die aan de startnode verbonden is. Wat ni echt galant is. Misschien moeten we toch modelleren da
        // schepen vertrekken in segmenten ipv nodes.
       // this.currentSegment = startNode.getFairways().get(0).getSegmentFromNode(startNode);
        this.currentSegment = startNode;
    }
    
    /**
     * Returns the Task Agent for this vessel
     */
    public TaskAgent getAgent() {
        return agent;
    }
}
