package inlandshipping;

import java.util.ArrayList;

public class ExplorationAntFast {
	
	/******************************************************
	 * 			Instance variables
	 ******************************************************/
	private Node sourceNode;
    private Node destinationNode;
    private ArrayList<Fairway> pathSoFar;
    private TaskAgent agent;
   

    /******************************************************
     * 			Constructor
     ******************************************************/
    public ExplorationAntFast(Node sourceNode, Node destinationNode, TaskAgent agent, ArrayList<Fairway> pathSoFar){
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.pathSoFar = pathSoFar;
        this.agent = agent;
    }

    /******************************************************
     * 			Instantiation
     ******************************************************/
    
    /*
     * Returns the source of the exploration ant.
     */
    public Node getSource(){
    	return sourceNode;
    }
    
    /*
     * Sets the source of the exploration ant.
     */
    private void setSource(Node source){
    	sourceNode = source;
    }
    
    /*
     * Returns the destination of the exploration ant.
     */
    public Node getDestination(){
    	return destinationNode;
    }
    
    /*
     * Sets the destination of the exploration ant.
     */
    private void getDestination(Node destination){
    	destinationNode = destination;
    }
    
    public ArrayList<Fairway> getPathSoFar() {
        return pathSoFar;
    }
    
    private void setPathSofar(ArrayList<Fairway> path){
    	pathSoFar = path;
    }
    
    public TaskAgent getAgent() {
        return agent;
    }
    
    private void setAgent(TaskAgent agent){
    	this.agent = agent;
    }
    
   
    

    public void scanForPossiblePaths(){
    	
    }
}
