package inlandshipping;

import java.util.ArrayList;

public class TaskAgent {
	
	private Vessel vessel;
	public TaskAgent(Vessel vessel) throws InterruptedException{
		this.vessel= vessel;
		scanEnvironment();
	}

	public void scanEnvironment() throws InterruptedException{
		while(vessel.isWorking()){
			ExplorationAnt ant = new ExplorationAnt(vessel.getSource(), vessel.getDestination(), this);
			ArrayList shortestPath = ant.getShortestPath();
			Thread.sleep(1000);
		}
	}
}
