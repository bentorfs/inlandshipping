package inlandshipping;

import java.util.ArrayList;

public class TaskAgent {
	
	/******************************************************
	 * 			Instantiation Variables
	 ******************************************************/
	private Vessel vessel;
	ArrayList<ArrayList<Fairway>> possiblePaths;
	ArrayList<Fairway> plan;
	
	
	/******************************************************
	 * 			Constructor
	 ******************************************************/
	
	public TaskAgent(Vessel vessel) {
		setVessel(vessel);
		initializePossiblePaths(new ArrayList<ArrayList<Fairway>>());
		plan = null;
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
	    if (antStartNode != getVessel().getDestination()) {
	    	ExplorationAnt ant = new ExplorationAnt(antStartNode, getVessel().getDestination(), this, new ArrayList<Fairway>());
	    	ant.scanForPossiblePaths();
	    }
	    else {
	    	// No exploration ants needed any more, the vessel is on its last fairway
	    }
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
		if(possiblePaths.size() == 0) {
			return new ArrayList<Fairway>();
		}
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
	 * Invariant: this method can only be called when the vessel is in a node,
	 * 			  the node is the current source of the vessel.
	 */
	public ArrayList<Fairway> getBestPath(int timeNow){
		if(possiblePaths.size() == 0) {
			// Destination has been reached
			return new ArrayList<Fairway>();
		}
		//ArrayList<Fairway> best = possiblePaths.get(0);
		//for(int i = 1; i < possiblePaths.size(); i ++){
		//	if(getTimeToCrossPath(possiblePaths.get(i), timeNow) < getTimeToCrossPath(best, timeNow)){
		//		best = possiblePaths.get(i);
		//	}
		//}
		
		ArrayList<Fairway> pathToCheck = (ArrayList<Fairway>) possiblePaths.get(0).clone();
		ExplorationAntFast ant = new ExplorationAntFast(getVessel(),pathToCheck);
		int bestTimeTillNow = ant.checkTimeNeeded(timeNow);
		ArrayList<Fairway> bestPathTillNow = possiblePaths.get(0);
		for(int i = 1; i < possiblePaths.size(); i ++){
			pathToCheck = (ArrayList<Fairway>) possiblePaths.get(i).clone();
			ant = new ExplorationAntFast(getVessel(),pathToCheck);
			int timeNeeded = ant.checkTimeNeeded(timeNow);
			if(timeNeeded < bestTimeTillNow){
				bestTimeTillNow = timeNeeded;
				bestPathTillNow = possiblePaths.get(i);
			}
		}
		return bestPathTillNow;
	}
	
	/**
	 * Returns the time to cross the given path.
	 * Invariant: this method can only be called when the vessel is in a node,
	 * 			  the node is the current source of the vessel.
	 */
	// Om de tijd van die locks te weten moetk timeNow meegeven, isser een 
	// andere methode waar da niet bij moet??? want nu gebeurt diene
	// updateScheduling automatisch...
	public int getTimeToCrossPath(ArrayList<Fairway> path, int timeNow){
		int time = 0;
		ResAgent agent;
		Fairway fairway;
		Node previousNode = getVessel().getSource();
		
		for (int i = 0; i < path.size(); i++) {
			fairway = path.get(i);
			if (fairway.getNode1() == previousNode) {
				for (int j = 0; j < fairway.getSegments().length; j++) {
					time++;
					if (fairway.getSegments()[j] instanceof Lock) {
						agent = ((Lock) fairway.getSegments()[j]).getAgent();
						// probleemke... :(
						time += (agent.whatIf(vessel, time, fairway.getSegments()[j - 1], timeNow) - timeNow);
					}
				}
			}else {
				for (int j = fairway.getSegments().length - 1; j >= 0; j--) {
					time++;
					if (fairway.getSegments()[j] instanceof Lock) {
						agent = ((Lock) fairway.getSegments()[j]).getAgent();
						// probleemke... :(
						time += (agent.whatIf(vessel, time, fairway.getSegments()[j + 1], timeNow) - timeNow);
					}
				}
			}
			
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
    	// The threshold (in timeunits) at which the agent will change his plan.
    	int threshold = 50;
    	
    	// TODO explorationants enkel uitsturen opt moment da vessel in ne node komt (of ga komen)
        if(vessel.getCurrentPosition() instanceof Node){
    		scanEnvironment();
        }
        // Kijk naar het kortste pad. 
        // TODO: een "plan" invoeren, dat een paar iteraties
        // bewaard blijft in plaats van élke iteratie alle paden te zoeken en het kortste
        // te nemen.
                
        //ArrayList<Fairway> path = getShortestPath();
        if(plan == null){
        	plan = getBestPath(timeNow);
        }else {
        	ArrayList<Fairway> newPath = getBestPath(timeNow);
        	if(getTimeToCrossPath(newPath, timeNow) < (getTimeToCrossPath(plan, timeNow)+ threshold) ){
        		if(Math.random() < 0.6){ // experiment!!	
        			plan = newPath;
        		}
        	}
        }
        
        // Send an intention ant to the current chosen path.
        // TODO: this should not happen at every time point.
        //if (getVessel().getCurrentPosition() instanceof Node) {
        	ArrayList<Fairway> path2 = (ArrayList<Fairway>) plan.clone();
        	IntentionAnt intAnt = new IntentionAnt(getVessel(), path2);
        	intAnt.makeReservations(timeNow);
        //}

        // Verplaats het schip in de richting van pad van het huidige plan.
        // TODO: currentSpeed moet nog geimplementeerd worden!
        // dus er staat getTopSpeed ipv getCurrentSpeed
        if (getVessel().getTopSpeed() == Speed.SLOW) {
            for (int k = 0; k < Configuration.nbSegmentsPerStepSlow; k++) {
                getVessel().moveToNextSegment(plan);
            }
        } else if (getVessel().getTopSpeed() == Speed.FAST) {
            for (int k = 0; k < Configuration.nbSegmentsPerStepFast; k++) {
                getVessel().moveToNextSegment(plan);
            }
        }
    }
}
