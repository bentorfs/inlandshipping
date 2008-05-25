package inlandshipping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ExplorationAntFast {

	private ArrayList<Fairway> pathToCheck;
	private Vessel vessel;
	
	public ExplorationAntFast(Vessel vessel, ArrayList<Fairway> path){
		this.vessel= vessel;
		pathToCheck = path;
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
	public int checkTimeNeeded(int timeNow) {
		/*Iterator<Fairway> it = pathToCheck.iterator();
		System.out.println("begin " + timeNow);
		while (it.hasNext()) {
			Fairway fw = it.next();
			System.out.println("pathtocheck " + fw.name);
		}*/
		
		// Start positions for the intentionAnt
		Segment previousPosition = vessel.getPreviousSegment();
		Segment currentPosition = vessel.getCurrentPosition();
		
		// time is the time at which reservations are made
		int time = timeNow;
		
		// finished becomes true when the ant has reached the destination node of the vessel
		boolean finished = false;
		while (!finished) {
			// If the currentposition is a lock, do a whatif query
			if (currentPosition instanceof Lock && vessel.getCurrentPosition() != currentPosition) {
				Lock currentLock = ((Lock) currentPosition);
				ResAgent agent = currentLock.getAgent();
			
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
					// Remove the fairway that has just been crossed from the path,
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
					
					/*System.out.println("in node loop:");
					Iterator<Fairway> it2 = pathToCheck.iterator();
					while (it2.hasNext()) {
						Fairway fw = it2.next();
						System.out.println("pathtocheck " + fw.name);
					}*/
					
					
					Iterator<Fairway> i1 = possibleFairways.iterator();
					while (i1.hasNext()) {
						Fairway possible = i1.next();
						//System.out.println(possible.name);
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
				currentPosition = getNextPosition(tempPosition, previousPosition);
				previousPosition = tempPosition;
				//System.out.println("next " + timeNow);
			}
		}
		return time;
	}
}
