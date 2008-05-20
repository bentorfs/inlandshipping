package inlandshipping;

import java.util.ArrayList;
import java.util.Vector;

public class ExplorationAnt {

    private Node sourceNode;
    private Node destinationNode;

    private ArrayList<Fairway> pathSoFar;
    public ArrayList<Fairway> getPathSoFar() {
        return pathSoFar;
    }

    private TaskAgent agent;
    public TaskAgent getAgent() {
        return agent;
    }

    public ExplorationAnt(Node sourceNode, Node destinationNode, TaskAgent agent, ArrayList<Fairway> pathSoFar){
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.pathSoFar = pathSoFar;
        this.agent = agent;
    }

    public void scanForPossiblePaths() throws CloneNotSupportedException {
        //sourcenode == destination -> addToPossiblePaths
        // bepaalde node vanuit sourceNode bereikbaar zit al in het pad == lus --> verwerpen
        // else --> create new ant

        Vector<Fairway> reachableFairways = sourceNode.getFairways();
        for(int i = 0; i < reachableFairways.size(); i++) {
            if (reachableFairways.get(i).getOtherNode(sourceNode) == destinationNode) { // bestemming bereikt
                addToPath(reachableFairways.get(i));
                getAgent().addToPossiblePaths(getPathSoFar());
            }
            else if (!pathSoFar.contains(reachableFairways.get(i))) {
                //ExplorationAnt ant = (ExplorationAnt) this.clone();
                ExplorationAnt newAnt = new ExplorationAnt(reachableFairways.get(i).getOtherNode(sourceNode),destinationNode,agent,getPathSoFar());
                newAnt.addToPath(reachableFairways.elementAt(i));
                newAnt.scanForPossiblePaths();
            }
            else {
                // Lus, geen ant uitsturen
            }
        }
    }

    public void addToPath(Fairway fairway){
        pathSoFar.add(fairway);
    }




}
