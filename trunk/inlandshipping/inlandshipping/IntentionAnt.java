package inlandshipping;

import java.util.ArrayList;

public class IntentionAnt {
	
	private ArrayList<Fairway> pathToCheck;
	private Vessel vessel;
	
	public IntentionAnt(Vessel vessel){
		this.vessel= vessel;
		pathToCheck = vessel.getAgent().getShortestPath();
	}

	
}
