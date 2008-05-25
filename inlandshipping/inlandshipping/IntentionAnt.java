package inlandshipping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

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
	 * Returns the next position this ant has to move to
	 */
	public Segment getNextPosition(Segment currentPosition, Segment previousPosition) {
		ArrayList<Segment> neighbours = currentPosition.getNeighbours();
		if (neighbours.get(0) == previousPosition) {
			return neighbours.get(1);
		}
		else {
			return neighbours.get(0);
		}
	}
	
	/**
	 * Makes reservations at all the locks where the ship will pass. 
	 * PreviousNode makes you know on which way you will enter the lock.
	 */
	public void makeReservations(int timeNow) {
		// Start positions for the intentionAnt
		Segment previousPosition = vessel.getPreviousSegment();
		Segment currentPosition = vessel.getCurrentPosition();
		// time is the time at which reservations are made
		int time = timeNow;
		
		// finished becomes true when the ant has reached the destination node of the vessel
		boolean finished = false;
		while (!finished) {
			// If the currentposition is a lock, make a reservation (except if the vessel is already in the lock)
			if (currentPosition instanceof Lock && vessel.getCurrentPosition() != currentPosition) {
				Lock currentLock = ((Lock) currentPosition);
				ResAgent agent = currentLock.getAgent();
				// Make the reservation
				agent.makeReservation(vessel, time, previousPosition);
				// Increase time to the time the ant is out of the lock
				time = agent.whatIf(vessel, time, previousPosition, timeNow);
				// Move ant to next segment
				Segment tempPosition = currentPosition;
				currentPosition = getNextPosition(currentPosition, previousPosition);
				previousPosition = tempPosition;
			}
			else if (currentPosition instanceof Node) {
				// The ant is in a node, and needs to choose a new direction
				
				// if previousPosition == null, the vessel has only just appeared
				// and there is no previous position
				if (previousPosition != null) {
					// Remove the fairway that has just been crossed from the path
					pathToCheck.remove(previousPosition.getFairway());
				}
				
				Node currentNode = (Node) currentPosition;
				
				// See if the ant has reached its final destination.
				if (currentNode == vessel.getDestination()) {
					finished = true;
				}
				// Otherwise, make a choice of the fairways in this node.
				else {
					Vector<Fairway> possibleFairways = currentNode.getFairways();
					Iterator<Fairway> i1 = possibleFairways.iterator();
					while (i1.hasNext()) {
						Fairway possible = i1.next();
						if (pathToCheck.contains(possible)) {
							time++;
							currentPosition = possible.getNeighbourSegmentOfNode(currentNode);
							previousPosition = currentNode;
						}
					}
				}
			}
			else { // instanceof Segment
				// The ant is on a regular segment, and needs to advance to the next one
				// time is increased by 1 to reflect the time needed to cross this segment
				// TODO: Speed of the vessel in rekening nemen!
				time++;
				Segment tempPosition = currentPosition;
				currentPosition = getNextPosition(currentPosition, previousPosition);
				previousPosition = tempPosition;
			}
		}
	}
	
	/**
	 * Makes reservations at all the locks where the ship will pass. 
	 * PreviousNode makes you know on which way you will enter the lock.
	 */
	/*
	public void makeReservations(int timeNow) {
		int steps = 0;
		Fairway fairway;
		int time;
		ResAgent agent;
		// for the part from the current position of the vessel to the node where pathToCheck starts
		if(!(vessel.getCurrentPosition() instanceof Node)){
			fairway = vessel.getCurrentPosition().getFairway();
			int fairwayLength = fairway.getSegments().length;
			int position = fairwayLength - (vessel.getNbSegmentsToGo()-1) ;
			Node comingFrom = previousNode;
			if (fairway.getNode1() == comingFrom) {
				System.out.println("SCHIP 1: position: " + position);
				for(int k = position; k <= fairwayLength; k++){
					if (fairway.getSegments()[k-1] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						System.out.println("TIME_RESERVATION_1: "+ time);
						agent = ((Lock) fairway.getSegments()[k-1]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[k - 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[k - 1], timeNow) - time);
					}
					steps++;
				}
			}
			else{
				position = (fairwayLength - position)+1;
				System.out.println("SCHIP 2: position: " + position);
				for(int k = position; k > 0; k--){
					if (fairway.getSegments()[k-1] instanceof Lock) {
						time = calculateTimeForReservation(steps, timeNow);
						System.out.println("TIME2_RESERVATION_2: "+ time);
						agent = ((Lock) fairway.getSegments()[k-1]).getAgent();
						agent.makeReservation(vessel, time, fairway.getSegments()[k + 1]);
						steps += (agent.whatIf(vessel, time, fairway.getSegments()[k + 1], timeNow) - time);
					}
					steps++;
				}
			}
		}
		
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
	*/

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
