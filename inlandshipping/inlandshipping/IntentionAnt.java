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
	
	// <Ben> Kunde uw ants ni laten vertrekken vanaf waar het vessel nu is? anders is die TTL te lang...
	
	// misschien een fairway late bijhouden of hij al dan niet een lock heeft en waar??
	//om sequentieel doorzoeken te vermijden.
	
	public IntentionAnt(Vessel vessel){
		this.vessel= vessel;
		pathToCheck = vessel.getAgent().getShortestPath();
		previousNode = vessel.getSource();
	}

	/**
	 * Makes reservations at all the locks where the ship will pass. 
	 * PreviousNode makes you know on which way you will enter the lock.
	 */
	public void makeReservations(int timeNow) {
		int steps = vessel.getNbSegmentsToGo();
		Fairway fairway;
		int time;
		for(int i = 0; i < pathToCheck.size(); i++){
			fairway = pathToCheck.get(i);
			if (fairway.getNode1() == previousNode){
				for(int j = 0; j < fairway.getSegments().length; j ++){
					steps++;
					if(fairway.getSegments()[j] instanceof Lock){
						time = calculateTimeForReservation(steps, timeNow);
						((Lock) fairway.getSegments()[j]).getAgent().makeReservation(vessel, time, fairway.getSegments()[j - 1]);
						steps += (((Lock) fairway.getSegments()[j]).getAgent().whatIf(vessel, time, fairway.getSegments()[j - 1], timeNow) - time);
					}
				}
			}
			else{ 
				for(int j = fairway.getSegments().length - 1; j >= 0; j--){
					steps++;
					if(fairway.getSegments()[j] instanceof Lock){
						time = calculateTimeForReservation(steps, timeNow);
						((Lock) fairway.getSegments()[j]).getAgent().makeReservation(vessel, time, fairway.getSegments()[j + 1]);
						steps += (((Lock) fairway.getSegments()[j]).getAgent().whatIf(vessel, time, fairway.getSegments()[j + 1], timeNow) - time);
					}
				}
			}
			// for the last step to the node
			steps++;
			previousNode = fairway.getOtherNode(previousNode);
		}
	}

	public int calculateTimeForReservation(int steps, int timeNow){
		if(vessel.getTopSpeed() == Speed.SLOW){
			return steps + timeNow;
		} 
		else 
			if(vessel.getTopSpeed() == Speed.FAST && (steps % 2) == 0){
				return timeNow + steps/2;
			} 
			else return timeNow + (steps+1)/2;
	}
}
