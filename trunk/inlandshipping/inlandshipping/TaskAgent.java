package inlandshipping;

import java.util.ArrayList;

public class TaskAgent {
	
	/******************************************************
	 * 			Instantiation Variables
	 ******************************************************/
	private Vessel vessel;
	ArrayList<ArrayList<Fairway>> possiblePaths;
	
	
	/******************************************************
	 * 			Constructor
	 ******************************************************/
	
	public TaskAgent(Vessel vessel) {
		setVessel(vessel);
		initializePossiblePaths(new ArrayList<ArrayList<Fairway>>());
	}
	
	/******************************************************
	 * 			Instantiation Methods
	 ******************************************************/
	
	public Vessel getVessel() {
	    return vessel;
	}
	
	private void setVessel(Vessel vessel){
		this.vessel = vessel;
	}
	
	public ArrayList<ArrayList<Fairway>> getPossiblePaths(){
		return possiblePaths;
	}
	
	private void initializePossiblePaths(ArrayList<ArrayList<Fairway>> paths){
		possiblePaths = paths;
	}
	

	/******************************************************
	 * 			Path Finding Methods
	 ******************************************************/
	
	/**
	 * This method sends exploration ants to scan the environment for the possible
	 * paths from the vessels source to its destination.
	 */
	public void scanEnvironment() {
	    possiblePaths.clear();
	    Segment currentPosition = getVessel().getCurrentPosition();
	    Node antStartNode;
	    if (currentPosition instanceof Node) {
	        antStartNode = (Node) currentPosition;
	    }
	    else {
	        antStartNode = currentPosition.getFairway().getOtherNode(getVessel().getSource());
	    }
		ExplorationAnt ant = new ExplorationAnt(antStartNode, getVessel().getDestination(), this, new ArrayList<Fairway>());
		ant.scanForPossiblePaths();
	}
	
	/**
	 * Adds a path an exploration ant has found to the possible paths.
	 */
	public void addToPossiblePaths(ArrayList<Fairway> path){
		possiblePaths.add(path);
	}
	
	/**
	 * Returns the path with the shortest distance.
	 */
	public ArrayList<Fairway> getShortestPath() {
		if(possiblePaths.size() == 0) return null;
		ArrayList<Fairway> shortest = possiblePaths.get(0);
		for(int i = 1; i < possiblePaths.size(); i ++){
			if(getLengthOfPath(possiblePaths.get(i)) < getLengthOfPath(shortest)){
				shortest = possiblePaths.get(i);
			}
		}
		return shortest;
	}
	
	/**
	 * Returns the length of the given path.
	 */
	public int getLengthOfPath(ArrayList<Fairway> path) {
		int distance = 0;
		for(int i = 0; i < path.size(); i ++){
			distance += path.get(i).getLength();
		}
		return distance;
	}

	/**
	 * Returns the path with the shortest time to cross it.
	 */
	public ArrayList<Fairway> getBestPath(){
		if(possiblePaths.size() == 0) return null;
		ArrayList<Fairway> best = possiblePaths.get(0);
		for(int i = 1; i < possiblePaths.size(); i ++){
			if(getTimeToCrossPath(possiblePaths.get(i)) < getTimeToCrossPath(best)){
				best = possiblePaths.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the time to cross the given path.
	 */
	public int getTimeToCrossPath(ArrayList<Fairway> path){
		int time = 0;
		ResAgent agent;
		Fairway fairway;
		
		for (int i = 0; i < path.size(); i++) {
			fairway = path.get(i);
			
			
		}
		return time;
	}
	
   /*******************************************************
    * 			Acting Methods
    *******************************************************/
    
    /**
     * This method should be called at every timestep in the simulation
     * for decision making and acting.
     */
    public void act(int timeNow) {
        // TODO elke keer de agent gaat scanne kan hij het pad veranderen
        // Enkel van in een node kan hij wel zijn pad maar gaan veranderen,
        // hij kan zijn route niet wijzigen van in een egwoon segment.
        // TODO de voorwaarden om naar een segment/node te kunnen bewegen
        // TODO explorationants enkel uitsturen opt moment da vessel in ne node komt (of ga komen)
        scanEnvironment();
        
        // Kijk naar het kortste pad. TODO: een "plan" invoeren, dat een paar iteraties
        // bewaard blijft in plaats van élke iteratie alle paden te zoeken en het kortste
        // te nemen.
        ArrayList<Fairway> path = getShortestPath();
        
        // Send an intention ant to the current chosen path.
        // TODO: this should not happen at every time point.
        IntentionAnt intAnt = new IntentionAnt(getVessel());
        intAnt.makeReservations(timeNow);

        // Verplaats het schip in de richting van pad van het huidige plan.
        // TODO: currentSpeed moet nog geimplementeerd worden!
        // dus er staat getTopSpeed ipv getCurrentSpeed
        if (getVessel().getTopSpeed() == Speed.SLOW) {
            for (int k = 0; k < Configuration.nbSegmentsPerStepSlow; k++) {
                getVessel().moveToNextSegment(path);
            }
        } else if (getVessel().getTopSpeed() == Speed.FAST) {
            for (int k = 0; k < Configuration.nbSegmentsPerStepFast; k++) {
                getVessel().moveToNextSegment(path);
            }
        }
    }
}
