package inlandshipping;

import java.util.Iterator;

import javax.swing.JFrame;

public class Main {

    /**
     * This entry method runs the simulation.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // Build components
        Environment env = new Environment();
        
        // Start GUI
        JFrame f = new JFrame("MAS Project: Inland Shipping");
        GUI p = new GUI(env);
        f.setContentPane(p);
        f.setSize(650,650);
        f.setVisible(true);
        
        
        // Scheduling tests
        /*
        Vessel v = env.getVessels().elementAt(0);
        Vessel v2 = env.getVessels().elementAt(1);
        Lock l = env.getLocks().elementAt(0);
        ResAgent a = l.getAgent();
        
        a.makeReservation(v, 10, l.getSideOne());
        
        a.makeReservation(v2,11,l.getSideTwo());
        a.updateScheduling(1);
        //int test = a.whatIf(v2, 11, l.getSideTwo(), 0);
        System.out.println("test");
        
        //a.makeReservation(v2, 11,l.getSideOne());
        //a.updateScheduling(1);
        */
        
        // Start main loop
        
        for (int time=0; time<Configuration.simulationTime; time++) {
            // Print a notification every 100 timesteps
            if (time % 100 == 0) {
                System.out.println("Reached timestep: " + time);
            }
            // Give all task agents the opportunity to act
            Iterator<Vessel> i = env.getVessels().iterator();
            while (i.hasNext()) {
                Vessel v = i.next();
                TaskAgent agent = v.getAgent();
                agent.act(time);
            }
            //check
            
            // Give all the resource agents the opportunity to act
            Iterator<Lock> it = env.getLocks().iterator();
            while (it.hasNext()) {
                Lock l = it.next();
                ResAgent agent = l.getAgent();
                agent.act(time);
            }
            
            // Give the environment the opportunity to act
            env.act(time);
            
            // Update GUI
            Thread.sleep(Configuration.sleepTime);
            p.redrawGUI(time);
        }
    }
}
