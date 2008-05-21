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
                //TODO elke keer de agent gaat scanne kan hij het pad veranderen
                // Enkel van in een node kan hij wel zijn pad maar gaan veranderen, 
                // hij kan zijn route niet wijzigen van in een egwoon segment.
                // TODO de voorwaarden om naar een segment/node te kunnen bewegen
                // TODO schip heeft bestemming bereikt
                // TODO explorationants enkel uitsturen opt moment da vessel in ne node komt (of ga komen)
                agent.scanEnvironment();
                ArrayList<Fairway> path = agent.getShortestPath();
                Segment currPosition = v.getCurrentPosition();

                // TODO: currentSpeed moet nog geimplementeerd worden!
                //if(v.getCurrentSpeed() == Speed.SLOW){
                if (v.getTopSpeed() == Speed.SLOW) {
                    for (int k=0; k<Configuration.nbSegmentsPerStepSlow; k++) {
                        v.moveToNextSegment(path);
                    }
                }else if(v.getTopSpeed() == Speed.FAST){
                    for (int k=0; k<Configuration.nbSegmentsPerStepFast; k++) {
                        v.moveToNextSegment(path);
                    }
                }
            }
            // Give all the resource agents the opportunity to act
            Iterator<Lock> it = env.getLocks().iterator();
            while (it.hasNext()) {
                Lock l = it.next();
                ResAgent agent = l.getAgent();
                agent.act();
            }
            
            // Give the environment the opportunity to make changes
            env.act();
            
            // Update GUI
            Thread.sleep(Configuration.sleepTime);
            p.redrawGUI(env, time);
            
            
        }
        
        
        
        
        
    }

}
