package inlandshipping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

public class GUI extends JPanel {
	
    private static final long serialVersionUID = 1L;
    
    private Vector<Ellipse2D.Double> nodes = new Vector<Ellipse2D.Double>();
	private Vector<Line2D.Double> fairways = new Vector<Line2D.Double>();
	private Vector<Ellipse2D.Double> vessels = new Vector<Ellipse2D.Double>();
	private Vector<Rectangle2D.Double> locks = new Vector<Rectangle2D.Double>();
	
	private Environment env;
	
	private int nbLargeVessels;
	private int nbSmallVessels;
	
	private int timeStep;
	
	/**
	 * Construct a new GUI window with data from the given environment
	 * @param env
	 */
	public GUI(Environment env) {
	    this.env = env;
		// Create the nodes
		Vector<Node> envNodes = env.getNodes();
		for (int i=0; i<envNodes.size(); i++) {
			Ellipse2D.Double node = new Ellipse2D.Double(envNodes.get(i).posX - 5, envNodes.get(i).posY-5, 10, 10);
			nodes.add(node);
		}
		
		// Create the fairways
		Vector<Fairway> envFw = env.getFairways();
		for (int i=0; i<envFw.size(); i++) {
			double fromX = envFw.get(i).getNode1().posX;
			double fromY = envFw.get(i).getNode1().posY;
			double toX = envFw.get(i).getNode2().posX;
			double toY = envFw.get(i).getNode2().posY;
			Line2D.Double fw = new Line2D.Double(fromX,fromY,toX,toY);
			fairways.add(fw);
		}
		
		// Create the locks
		for (int i=0; i<envFw.size(); i++) {
		    Vector<Lock> realLocks = envFw.get(i).getLocks();
		    Iterator<Lock> it = realLocks.iterator();
		    while (it.hasNext()) {
		        Lock l = it.next();
		        int positionOnFairway = findPositionOf(envFw.get(i).getSegments(),l);
		        double factor = (double) positionOnFairway / envFw.get(i).getLength();
                Node node1 = envFw.get(i).getNode1();
                Node node2 = envFw.get(i).getNode2();
                double lockXPosition = (node2.posX - node1.posX) * factor + node1.posX;
                double lockYPosition = (node2.posY - node1.posY) * factor + node1.posY;
                Rectangle2D.Double lockDrawing = new Rectangle2D.Double(lockXPosition-2,lockYPosition-2,5,5);
                locks.add(lockDrawing);
		    }
		}
	}
	
	/**
	 * This method refreshes the GUI, with new vessel positions.
	 */
	public void redrawGUI(int time) {
	    // Refresh the vessel positions
		vessels.clear();
        nbLargeVessels = 0;
        nbSmallVessels = 0;
		double shipXPosition, shipYPosition;
		Iterator<Vessel> envVessels = env.getVessels().iterator();
        while (envVessels.hasNext()) {
            Vessel v = envVessels.next();
            Segment position = v.getCurrentPosition();
            if (position instanceof Node) {
                // Vessel is on a node
                Node node = (Node) position;
                shipXPosition = node.posX;
                shipYPosition = node.posY;
            }
            else {
                // Vessel is on a segment
                Fairway currentFw = position.getFairway();
                Segment[] fairwaySegments = currentFw.getSegments();
                int positionOnFairway = findPositionOf(fairwaySegments, position);
                double factor = (double) positionOnFairway / currentFw.getLength();
                Node node1 = currentFw.getNode1();
                Node node2 = currentFw.getNode2();
                shipXPosition = (node2.posX - node1.posX) * factor + node1.posX;
                shipYPosition = (node2.posY - node1.posY) * factor + node1.posY;
            }
            int size;
            if (v.getSize() == Size.LARGE) {
                size = 7;
                nbLargeVessels++;
            }
            else {
                size = 4;
                nbSmallVessels++;
            }
            Ellipse2D.Double vessel = new Ellipse2D.Double(shipXPosition - size/2, shipYPosition - size/2, size, size);
            vessels.add(vessel);
        }
        
        // Refresh timestep
        timeStep = time;
        
        repaint();
	}
	
	private int findPositionOf(Segment[] list, Segment element) {
	    for (int i=0; i<list.length; i++) {
	        if (list[i] == element) {
	            return i;
	        }
	    }
	    // This should not happen.
	    System.out.println("the requested element was not in the array!");
	    return -1;
	}
	
	/**
	 * This method draws everything.
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw fairways
        Iterator<Line2D.Double> l = fairways.iterator();
        while (l.hasNext()) {
        	g2.draw(l.next());
        }
        
        // Draw nodes
        Iterator<Ellipse2D.Double> n = nodes.iterator();
        while (n.hasNext()) {
        	g2.fill(n.next());
        }
        
        // Draw locks
        g2.setFont(new Font("sans-serif",10,10));
        g2.setColor(new Color(0,0,255));
        Iterator<Rectangle2D.Double> lo = locks.iterator();
        while (lo.hasNext()) {
            Rectangle2D.Double lockD = lo.next();
            g2.fill(lockD);
            int x = Math.round((float) lockD.x);
            int y = Math.round((float) lockD.y);
            int position = locks.indexOf(lockD);
            Lock lock = env.getLocks().elementAt(position);
            int waiting1 = lock.getWaitingSideOne().size();
            int waiting2 = lock.getWaitingSideTwo().size();
            g2.drawString(waiting1 + "", x+10, y+6);
            g2.drawString(waiting2 + "", x-10, y+6);
        }
        
        
        
        // Draw vessels
        g2.setColor(new Color(255,0,0));
        Iterator<Ellipse2D.Double> v = vessels.iterator();
        while (v.hasNext()) {
        	g2.fill(v.next());
        }
        
        // Draw the time
        g2.setColor(new Color(0,0,0));
        g2.setFont(new Font("sans-serif",10,20));
        g2.drawString("Time: " + timeStep, 10, 30);
        
        // Draw other statistics
        g2.setFont(new Font("sans-serif",10,10));
        g2.drawString("Total vessels: " + vessels.size(), 520, 570);
        g2.drawString("Small vessels: " + nbSmallVessels, 520, 590);
        g2.drawString("Large vessels: " + nbLargeVessels, 520, 610);
       
    }
}
