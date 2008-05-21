package inlandshipping;

import java.util.ArrayList;

public class IntentionAnt {
	
	
	private ArrayList<Fairway> pathToCheck;
	private Vessel vessel;
	private Node previousNode;
	
	// de frequentie waarop de mieren worden uitgestuurd moet overeenkomen met de tijd 
	// dat een reservatie blijft staan
	// maar wat als er een lock is op de fairway waar hij nu is?? het kortste pad begint vanaf de
	// volgende node, dus de ants passere niet langs die lock!! Dus de reservatie moet minstens
	// blijven staan van het eerste/laatste segment tot aan de lock
	
	// misschien een fairway late bijhouden of hij al dan niet een lock heeft en waar??
	//om sequentieel doorzoeken te vermijden.
	
	public IntentionAnt(Vessel vessel){
		this.vessel= vessel;
		pathToCheck = vessel.getAgent().getShortestPath();
		previousNode = vessel.getSource();
	}

	// geen rekening gehouden met richting!! 
	public void makeReservations(){
		int steps = vessel.getNbSegmentsToGo();
		Fairway fairway;
		for(int i = 0; i < pathToCheck.size(); i++){
			fairway = pathToCheck.get(i);
			if node = prevnod
			for(int j = 0; j < fairway.getSegments().length; j ++){
				steps++;
				if(fairway.getSegments()[j] instanceof Lock){
					((Lock) fairway.getSegments()[j]).getAgent().makeReservation(vessel, steps, direction);
					steps += ((Lock) fairway.getSegments()[j]).getTimeNeeded();
				}
			}
			// for the last step to the node
			steps++;
		}
	}
}
