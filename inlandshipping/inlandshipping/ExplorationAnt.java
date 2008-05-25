package inlandshipping;

import java.util.ArrayList;
import java.util.Vector;

public class ExplorationAnt {

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
    public ExplorationAnt(Node sourceNode, Node destinationNode, TaskAgent agent, ArrayList<Fairway> pathSoFar){
        setSource(sourceNode);
        setDestination(destinationNode);
        setPathSofar(pathSoFar);
        setAgent(agent);
    }

    /******************************************************
     * 			Instantiation
     ******************************************************/
    
    /**
     * Returns the source of the exploration ant.
     */
    public Node getSource(){
    	return sourceNode;
    }
    
    /**
     * Sets the source of the exploration ant.
     */
    private void setSource(Node source){
    	sourceNode = source;
    }
    
    /**
     * Returns the destination of the exploration ant.
     */
    public Node getDestination(){
    	return destinationNode;
    }
    
    /**
     * Sets the destination of the exploration ant.
     */
    private void setDestination(Node destination){
    	destinationNode = destination;
    }
    
    /**
     * Returns the path collected by the exploration ant so far.
     */
    public ArrayList<Fairway> getPathSoFar() {
        return pathSoFar;
    }
    
    /**
     * Sets the path the exploration ant has passed so far.
     */
    private void setPathSofar(ArrayList<Fairway> path){
    	pathSoFar = path;
    }
    
    /**
     * Returns the task agent of the exploration ant.
     */
    public TaskAgent getAgent() {
        return agent;
    }
    
    /**
     * Sets the task agent of the exploration ant.
     */
    private void setAgent(TaskAgent agent){
    	this.agent = agent;
    }
    
   
    /******************************************************
     * 			Path Finding Methods
     ******************************************************/

    /**
     * Scans for all the possible path from the source to the destination.
     */
    public void scanForPossiblePaths(){
    	Vector<Fairway> reachableFairways = getSource().getFairways();
    	for(int i = 0; i < reachableFairways.size(); i++){
    		if (reachableFairways.get(i).getOtherNode(getSource()) == getDestination()) {
    			// bestemming bereikt
    	        addToPathSoFar(reachableFairways.get(i));
    	        getAgent().addToPossiblePaths((ArrayList<Fairway>) getPathSoFar().clone());
    	        pathSoFar.remove(reachableFairways.get(i));
    		}
    	    else if (!getPathSoFar().contains(reachableFairways.get(i))) {
    	    	ArrayList<Fairway> newPathSoFar = (ArrayList<Fairway>) getPathSoFar().clone();
    	        ExplorationAnt newAnt = new ExplorationAnt(reachableFairways.elementAt(i).getOtherNode(getSource()),getDestination(),getAgent(),newPathSoFar);
    	        newAnt.addToPathSoFar(reachableFairways.elementAt(i));
    	        newAnt.scanForPossiblePaths();
    	    }
    	    else {
    	    	// Lus, geen ant uitsturen
    	    }
    	}
    }
    
    /**
     * Adds the fairway to path passed so far.
     */
    public void addToPathSoFar(Fairway fairway){
    	getPathSoFar().add(fairway);
    }
}
