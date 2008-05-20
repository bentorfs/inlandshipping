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
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        // Build components
        Environment env = new Environment();
        
        
        // Start GUI
        JFrame f = new JFrame("Inland Shipping");
        GUI p = new GUI(env);
        f.setContentPane(p);
        f.setSize(800,800);
        f.setVisible(true);
        
        //Segment newpos = env.getFairways().get(0).segments[100];
        //env.getVessels().get(0).setCurrentPosition(newpos);
        
        p.drawVessels(env);
        
        /*for (int i=5; i<500; i++) {
        	p.test(i, 5);
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        
        
        
        // Start main loop
        
        int maxTimeSteps = Configuration.simulationTime;
        for (int time=0; time<maxTimeSteps; time++) {
            System.out.println("Timestep: " + time);
            Iterator<Vessel> i = env.getVessels().iterator();
            while (i.hasNext()) {
                Vessel v = i.next();
                TaskAgent agent = v.getAgent();
                //TODO elke keer de agent gaat scanne kan hij het pad veranderen
                // Enkel van in een node kan hij wel zijn pad maar gaan veranderen, 
                // hij kan zijn route niet wijzigen van in een egwoon segment.
                // TODO de voorwaarden om naar een segment/node te kunnen bewegen
                // TODO schip heeft bestemming bereikt
                agent.scanEnvironment();
                ArrayList<Fairway> path = agent.getShortestPath();
                Segment currPosition = v.getCurrentPosition();
                if(v.getCurrentSpeed() == Speed.SLOW){
                	v.moveToNextSegment(path);
                }else if(v.getCurrentSpeed() == Speed.FAST){
                	v.moveToNextSegment(path);
                	v.moveToNextSegment(path);
                }
                //decision = agent.makeDecision();
                //decisions.add(decision);
                
                // Update GUI
                //Thread.sleep(100);
                //p.drawVessels(env);
            }
            //Environment.reactTo(Vector<Decision> decisions);
        }
        
        
        
        
        
    }

}
