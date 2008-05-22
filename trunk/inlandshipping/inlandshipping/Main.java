package inlandshipping;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws CloneNotSupportedException 
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
                agent.act();
            }
            // Give all the resource agents the opportunity to act
            Iterator<Lock> it = env.getLocks().iterator();
            while (it.hasNext()) {
                Lock l = it.next();
                ResAgent agent = l.getAgent();
                agent.act(time);
            }
            
            // Give the environment the opportunity to make changes
            env.act();
            
            // Update GUI
            Thread.sleep(Configuration.sleepTime);
            p.redrawGUI(env, time);
            
            
        }
        
        
        
        
        
    }

}
