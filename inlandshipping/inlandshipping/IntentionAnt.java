package inlandshipping;

import java.util.ArrayList;

public class IntentionAnt {
	
	
	private ArrayList<Fairway> pathToCheck;
	private Vessel vessel;
	
	// de frequentie waarop de mieren worden uitgestuurd moet overeenkomen met de tijd 
	// dat een reservatie blijft staan
	
	// misschien een fairway late bijhouden of hij al dan niet een lock heeft en waar??
	//om sequentieel doorzoeken te vermijden.
	
	public IntentionAnt(Vessel vessel){
		this.vessel= vessel;
		pathToCheck = vessel.getAgent().getShortestPath();
	}

	public void makeReservations(){
		int steps = vessel.getNbSegmentsToGo();
		Fairway fairway;
		for(int i = 0; i < pathToCheck.size(); i++){
			fairway = pathToCheck.get(i);
			for(int j = 0; j < fairway.getSegments().length; j ++){
				steps++;
				if(fairway.getSegments()[j] instanceof Lock){
					// maak reservatie
					// tel de wachttijd van de lock erbij
				}
			}
			// for the last step to the node
			steps++;
		}
	}
}
