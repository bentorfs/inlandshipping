package inlandshipping;

import java.util.ArrayList;

public class ExplorationAnt {

	private Node sourceNode;
	private Node destinationNode;
	private ArrayList<Fairway> pathSoFar;
	
	public ExplorationAnt(Node sourceNode, Node destinationNode, TaskAgent agent, ArrayList<Fairway> pathSoFar){
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		//pathSofar initialize
	}
	
	public void scanForPossiblePaths(Node sourceNode, Node destinationNode, TaskAgent agent){
		//sourcenode == destination -> setPossiblePath
		// bepaalde node vanuit sourceNode bereikbaar zit al in het pad == lus --> verwerpen
		// else --> create new ant
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void setPossiblePath(TaskAgent agent, ArrayList path){
		agent.setToPossiblePaths(path);
	}
}
