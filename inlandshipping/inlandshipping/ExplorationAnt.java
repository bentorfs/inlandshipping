package inlandshipping;

import java.util.ArrayList;
import java.util.Vector;

public class ExplorationAnt {

	private Node sourceNode;
	private Node destinationNode;
	private ArrayList<Fairway> pathSoFar;
	
	public ExplorationAnt(Node sourceNode, Node destinationNode, TaskAgent agent, ArrayList<Fairway> pathSoFar){
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.pathSoFar = pathSoFar;
		//pathSofar initialize
	}
	
	public void scanForPossiblePaths(Node sourceNode, Node destinationNode, TaskAgent agent) throws CloneNotSupportedException{
		//sourcenode == destination -> setPossiblePath
		// bepaalde node vanuit sourceNode bereikbaar zit al in het pad == lus --> verwerpen
		// else --> create new ant
		
		Vector<Fairway> reachableFairways = sourceNode.getFairways();
		for(int i = 0; i < reachableFairways.size(); i ++){
			if(reachableFairways.get(i).getOtherNode(sourceNode) == destinationNode){ //bestemming bereikt
				ExplorationAnt ant = (ExplorationAnt) this.clone();
				ant.addToPath(reachableFairways.elementAt(i));
				setPossiblePath(agent, pathSoFar);
			}
			else if(!pathSoFar.contains(reachableFairways.get(i))){ // anders lus
				ExplorationAnt ant = (ExplorationAnt) this.clone();
				ant.addToPath(reachableFairways.elementAt(i));
				ant.scanForPossiblePaths(reachableFairways.get(i).getOtherNode(sourceNode), destinationNode, agent);
			}
		}
				
		
	}
	
	public void addToPath(Fairway fairway){
		pathSoFar.add(fairway);
	}
	
	public void setPossiblePath(TaskAgent agent, ArrayList path){
		agent.addToPossiblePaths(path);
	}
}
