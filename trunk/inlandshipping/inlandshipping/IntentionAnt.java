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
	
	public IntentionAnt(Vessel vessel, ArrayList<Fairway> path){
		this.vessel= vessel;
		pathToCheck = path;
		previousNode = vessel.getSource();
	}

	/**
	 * Makes reservations at all the locks where the ship will pass. 
	 * PreviousNode makes you know on which way you will enter the lock.
	 */
	public void makeReservations(int timeNow) {
		int steps = 0;
		Fairway fairway;
		int time;
		ResAgent agent;
			
		// for the part from the current position of the vessel to the node where pathToCheck starts
		if(!(vessel.getCurrentPosition() instanceof Node)){
			fairway = vessel.getCurrentPosition().getFairway();
			int fairwayLength = fairway.getSegments().length;
			int position = fairwayLength - vessel.getNbSegmentsToGo() ;
			Node comingFrom = fairway.getOtherNode(previousNode);
			if (fairway.getNode1() == comingFrom) {
				System.out.println("SCHIP 1: position: " + position);
				for(int k = position; k < fairwayLength; k++){
					steps++;
					if (fairway.getSegments()[k] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						agent = ((Lock) fairway.getSegments()[k]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[k - 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[k - 1], timeNow) - time);
					}
				}
			}
			else{
				position = (fairwayLength - position) - 1;
				System.out.println("SCHIP 2: position: " + position);
				for(int k = position; k >= 0; k--){
					steps++;
					if (fairway.getSegments()[k] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						agent = ((Lock) fairway.getSegments()[k]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[k + 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[k + 1], timeNow) - time);
					}
				}
			}
		}
		steps = vessel.getNbSegmentsToGo();
		
		// for the part where pathToCheck starts
		for (int i = 0; i < pathToCheck.size(); i++) {
			fairway = pathToCheck.get(i);
			if (fairway.getNode1() == previousNode) {
				for (int j = 0; j < fairway.getSegments().length; j++) {
					steps++;
					if (fairway.getSegments()[j] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						agent = ((Lock) fairway.getSegments()[j]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[j - 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[j - 1], timeNow) - time);
					}
				}
			} else {
				for (int j = fairway.getSegments().length - 1; j >= 0; j--) {
					steps++;
					if (fairway.getSegments()[j] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						agent = ((Lock) fairway.getSegments()[j]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[j + 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[j + 1], timeNow) - time);
					}
				}
			}
			// for the last step to the node
			steps++;
			previousNode = fairway.getOtherNode(previousNode);
		}
	}

	// zou moete gerefactored worde naar hoeveel steps een large vessel kan make etc,
	// maar hoe rond ge dan af??
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
