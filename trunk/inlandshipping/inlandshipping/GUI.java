package inlandshipping;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.JPanel;

public class GUI extends JPanel {
    private Ellipse2D.Double circle = new Ellipse2D.Double(10, 10, 350, 350);
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.fill(circle);
    }
}
