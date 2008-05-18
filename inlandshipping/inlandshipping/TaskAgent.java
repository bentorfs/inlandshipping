package inlandshipping;

import java.util.ArrayList;

public class TaskAgent {
	
	private Vessel vessel;
	ArrayList<ArrayList<Fairway>> possiblePaths;
	
	public TaskAgent(Vessel vessel) throws InterruptedException{
		this.vessel= vessel;
		possiblePaths = new ArrayList<ArrayList<Fairway>>();
		scanEnvironment();
	}

	public void scanEnvironment() throws InterruptedException{
		while(vessel.isWorking()){
			ExplorationAnt ant = new ExplorationAnt(vessel.getSource(), vessel.getDestination(), this);
			
			Thread.sleep(1000);
		}
	}
	
	public void setToPossiblePaths(ArrayList<Fairway> path){
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
		return null;
	}
	
	public int getLengthOfPath(ArrayList<Fairway> path){
		int distance = 0;
		for(int i = 0; i < path.size(); i ++){
			distance += path.get(i).getLength();
		}
		return distance;
	}
}
