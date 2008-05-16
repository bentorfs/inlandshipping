package inlandshipping;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

public class GUI extends JPanel {
	
	private Vector<Ellipse2D.Double> nodes = new Vector<Ellipse2D.Double>();
	private Vector<Line2D.Double> fairways = new Vector<Line2D.Double>();
	private Vector<Ellipse2D.Double> vessels = new Vector<Ellipse2D.Double>();
	
	
	/*
	 * The next variables indicate the spatial positions of all items.
	 * This is static, they have to be changed if a new environment is used.
	 */
	// Coordinates of the nodes
	double[] nodeXCoords = new double[] {100,200,100};
	double[] nodeYCoords = new double[] {100,200,200};
	
	// Fairway connections between nodes. The numbers in the array are indices
	// of the arrays above.
	int[] fwFromIndex = new int[] 	{ 0, 0, 1 };
	int[] fwToIndex = new int[] 	{ 1, 2, 2 };
	
	/**
	 * Construct a new GUI window with data from the given environment
	 * @param env
	 */
	public GUI(Environment env) {
		// Create the nodes
		Vector<Node> envNodes = env.getNodes();
		for (int i=0; i<envNodes.size(); i++) {
			Ellipse2D.Double node = new Ellipse2D.Double(nodeXCoords[i]-5, nodeYCoords[i]-5, 10, 10);
			nodes.add(node);
		}
		
		// Create the fairways
		Vector<Fairway> envFw = env.getFairways();
		for (int i=0; i<envFw.size(); i++) {
			double fromX = nodeXCoords[fwFromIndex[i]];
			double fromY = nodeYCoords[fwFromIndex[i]];
			double toX = nodeXCoords[fwToIndex[i]];
			double toY = nodeYCoords[fwToIndex[i]];
			Line2D.Double fw = new Line2D.Double(fromX,fromY,toX,toY);
			fairways.add(fw);
		}
	}
	
	/**
	 * This method refreshes the vessel positions and updates the drawing.
	 */
	public void drawVessels(Environment env) {
		vessels.clear();
		
		Iterator<Vessel> envVessels = env.getVessels().iterator();
		double shipXPosition, shipYPosition;
		while (envVessels.hasNext()) {
			// This code is dirty and temporary
			Vessel v = envVessels.next();
			Segment vPosition = v.getCurrentPosition();
			Fairway vFairway = vPosition.getFairway();
			int pos = env.getFairways().indexOf(vFairway);
			if (pos < 0) {
				// Vessel is on a node
				Node node = (Node) vPosition;
				pos = env.getNodes().indexOf(node);
				shipXPosition = nodeXCoords[pos];
				shipYPosition = nodeYCoords[pos];
			}
			else {
				// Vessel is on a segment
				int fromIndex = fwFromIndex[pos];
				int toIndex = fwToIndex[pos];
				double fromX = nodeXCoords[fromIndex];
				double fromY = nodeYCoords[fromIndex];
				double toX = nodeXCoords[toIndex];
				double toY = nodeYCoords[toIndex];
				// TODO: schip positioneren afhankelijk van hoe ver het op deze fairway zit.
				// nummertjes gebruiken voor de segments hiervoor?
				shipXPosition = (fromX + toX) / 2;
				shipYPosition = (fromY + toY) / 2;
			}
			Ellipse2D.Double vessel = new Ellipse2D.Double(shipXPosition-2, shipYPosition-2, 5, 5);
			vessels.add(vessel);
		}
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
        
        // Draw vessels
        g2.setColor(new Color(255,0,0));
        Iterator<Ellipse2D.Double> v = vessels.iterator();
        while (v.hasNext()) {
        	g2.fill(v.next());
        }

    }
}
