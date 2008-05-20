package inlandshipping;

import java.util.ArrayList;

public class TaskAgent {
	
	private Vessel vessel;
	ArrayList<ArrayList<Fairway>> possiblePaths;
	
	public TaskAgent(Vessel vessel) {
		this.vessel= vessel;
		possiblePaths = new ArrayList<ArrayList<Fairway>>();
	}

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
		
		//System.out.println("Number of paths found: " + possiblePaths.size());
	}
	
	public void addToPossiblePaths(ArrayList<Fairway> path){
		possiblePaths.add(path);
	}
	
	public ArrayList<Fairway> getShortestPath(){
		if(possiblePaths.size() == 0) return null;
		ArrayList<Fairway> shortest = possiblePaths.get(0);
		for(int i = 1; i < possiblePaths.size(); i ++){
			if(getLengthOfPath(possiblePaths.get(i)) < getLengthOfPath(shortest)){
				shortest = possiblePaths.get(i);
			}
		}
		return shortest;
	}
	
	public int getLengthOfPath(ArrayList<Fairway> path){
		int distance = 0;
		for(int i = 0; i < path.size(); i ++){
			distance += path.get(i).getLength();
		}
		return distance;
	}

    public Vessel getVessel() {
        return vessel;
    }
}
