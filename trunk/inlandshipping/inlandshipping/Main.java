package inlandshipping;

import java.util.Iterator;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Build components
        Environment env = new Environment();
        
        // Start main loop
        int maxTimeSteps = 50000;
        for (int time=0; time<maxTimeSteps; time++) {
            Iterator<Vessel> i = env.getVessels().iterator();
            while (i.hasNext()) {
                Vessel v = i.next();
                TaskAgent agent = v.getAgent();
                //decision = agent.makeDecision();
                //decisions.add(decision);
            }
            //Environment.reactTo(Vector<Decision> decisions);
        }
        
        
    }

}
