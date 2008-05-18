package inlandshipping;

import java.util.ArrayList;

public class ExplorationAnt {

	private Node sourceNode;
	private Node destinationNode;
	
	public ExplorationAnt(Node sourceNode, Node destinationNode, TaskAgent agent){
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
	}
	
	public void setPossiblePath(TaskAgent agent, ArrayList path){
		agent.addToPossiblePaths(path);
	}
}
