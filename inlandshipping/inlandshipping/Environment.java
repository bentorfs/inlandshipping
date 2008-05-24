package inlandshipping;


import java.util.Vector;

/**
 * This class represents the environment: topography, and implements the methodes
 * by which the environment reacts to decisions from agents.
 */
public class Environment {
    
    /*
     * Contents of the environment
     */
    private Vector<Node> nodes = new Vector<Node>();
    private Vector<Vessel> vessels = new Vector<Vessel>();
    private Vector<Fairway> fairways = new Vector<Fairway>();
    
    private Vector<Lock> locks = new Vector<Lock>();
    public Vector<Lock> getLocks() {
        return locks;
    }
    
    /**
     * This constructor builds an example topography.
     */
    public Environment() {
        Node bruges = new Node();
        bruges.posX = 100;
        bruges.posY = 100;
        nodes.add(bruges);
        
        Node tournai = new Node();  
        tournai.posX = 100;
        tournai.posY = 300;
        nodes.add(tournai);
        
        Node gent = new Node();
        gent.posX = 200;
        gent.posY = 200;
        nodes.add(gent);
        
        Node mons = new Node();
        mons.posX = 200;
        mons.posY = 400;
        nodes.add(mons);
        
        Node antwerp = new Node();
        antwerp.posX = 300;
        antwerp.posY = 160;
        nodes.add(antwerp);
        
        Node brussels = new Node();
        brussels.posX = 260;
        brussels.posY = 300;
        nodes.add(brussels);
        
        Node charleroi = new Node();
        charleroi.posX = 260;
        charleroi.posY = 460;
        nodes.add(charleroi);
        
        Node namur = new Node();
        namur.posX = 340;
        namur.posY = 500;
        nodes.add(namur);
        
        Node hasselt = new Node();
        hasselt.posX = 500;
        hasselt.posY = 300;
        nodes.add(hasselt);
        
        Node liege = new Node();
        liege.posX = 540;
        liege.posY = 400;
        nodes.add(liege);
        
        Node hub = new Node();
        hub.posX = 230;
        hub.posY = 400;
        nodes.add(hub);
        
        // new Fairway(from,to,nbLanes,length,maxSpeed)
        Vector<Integer> lockPositions;
        lockPositions = new Vector<Integer>();
        lockPositions.add(30);
        lockPositions.add(70);
        Fairway way1 = new Fairway(bruges,tournai,2,80,Speed.SLOW, lockPositions);
        fairways.add(way1);
        locks.addAll(way1.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        Fairway way2 = new Fairway(bruges,gent,2,55,Speed.SLOW, lockPositions);
        fairways.add(way2);
        locks.addAll(way2.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        //lockPositions.add(60);
        Fairway way3 = new Fairway(tournai,gent,2,70,Speed.FAST, lockPositions);
        fairways.add(way3);
        locks.addAll(way3.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        Fairway way4 = new Fairway(tournai,mons,2,50,Speed.FAST, lockPositions);
        fairways.add(way4);
        locks.addAll(way4.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        //lockPositions.add(60);
        //lockPositions.add(80);
        Fairway way5 = new Fairway(gent,mons,2,90,Speed.FAST, lockPositions);
        fairways.add(way5);
        locks.addAll(way5.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        Fairway way6 = new Fairway(gent,antwerp,2,60,Speed.FAST, lockPositions);
        fairways.add(way6);
        locks.addAll(way6.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(25);
        Fairway way7 = new Fairway(antwerp,brussels,2,50,Speed.FAST, lockPositions);
        fairways.add(way7);
        locks.addAll(way7.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        Fairway way8 = new Fairway(brussels,hub,2,50,Speed.FAST, lockPositions);
        fairways.add(way8);
        locks.addAll(way8.getLocks());
        
        lockPositions.clear();
        Fairway way9 = new Fairway(hub,mons,2,20,Speed.FAST, lockPositions);
        fairways.add(way9);
        locks.addAll(way9.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(10);
        Fairway way10 = new Fairway(hub,charleroi,2,20,Speed.FAST, lockPositions);
        fairways.add(way10);
        locks.addAll(way10.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(10);
        //lockPositions.add(20);
        Fairway way11 = new Fairway(charleroi,namur,2,30,Speed.SLOW, lockPositions);
        fairways.add(way11);
        locks.addAll(way11.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        //lockPositions.add(50);
        Fairway way12 = new Fairway(namur,liege,2,60,Speed.SLOW, lockPositions);
        fairways.add(way12);
        locks.addAll(way12.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(10);
        //lockPositions.add(30);
        Fairway way13 = new Fairway(hasselt,liege,2,50,Speed.FAST, lockPositions);
        fairways.add(way13);
        locks.addAll(way13.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(40);
        Fairway way14 = new Fairway(hasselt,brussels,2,70,Speed.FAST, lockPositions);
        fairways.add(way14);
        locks.addAll(way14.getLocks());
        
        lockPositions.clear();
        //lockPositions.add(20);
        //lockPositions.add(40);
        //lockPositions.add(60);
        Fairway way15 = new Fairway(antwerp,hasselt,2,80,Speed.FAST, lockPositions);
        fairways.add(way15);
        locks.addAll(way15.getLocks());

        
        // new Vessel(source,destination,size,maxSpeed)
        Vessel vessel1 = new Vessel(mons,bruges,Size.SMALL, Speed.SLOW);
        vessels.add(vessel1);
        Vessel vessel2 = new Vessel(mons,bruges,Size.SMALL, Speed.SLOW);
        vessels.add(vessel2);
        Vessel vessel3 = new Vessel(mons,bruges,Size.SMALL, Speed.SLOW);
        vessels.add(vessel3);
        Vessel vessel4 = new Vessel(bruges,mons,Size.SMALL, Speed.SLOW);
        vessels.add(vessel4);
        
        for (int i=0; i<Configuration.nbStartVessels; i++) {
            Vessel v = getRandomVessel();
            addToVessels(v);
        }
    }
    
    /**
     * Returns a vessel with random source and destination and properties.
     */
    public Vessel getRandomVessel() {
        int nbNodes = getNodes().size();
        int rand1 = (int) (Math.random() * nbNodes);
        int rand2 = (int) (Math.random() * nbNodes);
        // Source and destination must be different
        while (rand1 == rand2) {
            rand2 = (int) (Math.random() * nbNodes);
        }
        Node start = getNodes().get(rand1);
        Node stop = getNodes().get(rand2);
        Size size = (Math.random() > Configuration.fractionLargeVessels) ? Size.SMALL : Size.LARGE;
        Speed speed = (Math.random() > Configuration.fractionFastVessels) ? Speed.SLOW : Speed.FAST;
        return new Vessel(start,stop,size,speed);
    }
    
    /**
     * Returns a vector with the vessels in this environment
     */
    public Vector<Vessel> getVessels() {
        return vessels;
    }
    
    /**
     * Returns a vector with the nodes in this environment
     */
    public Vector<Node> getNodes() {
    	return nodes;
    }
    
    /**
     * Returns a vector with the fairways in this environment
     */
    public Vector<Fairway> getFairways() {
    	return fairways;
    }
    
    /**
     * Add a vessel to the environment
     */
    public void addToVessels(Vessel v) {
        vessels.add(v);
    }
    
    /**
     * This method makes random changes to the environment,
     * according to the configuration of the simulation
     */
    public void act() {
        // Possibly add a vessel
        if (Math.random() < Configuration.probNewVessel) {
            Vessel v = getRandomVessel();
            addToVessels(v);
            System.out.println("A new vessel has entered the environment");
        }
    }


}
