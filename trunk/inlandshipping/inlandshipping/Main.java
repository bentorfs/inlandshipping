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
        GUI p = new GUI();
        f.setContentPane(p);
        f.setSize(800,800);
        f.setVisible(true);
        
        
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
