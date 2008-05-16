package inlandshipping;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
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
        /*
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
        */
        
        
    }

}
