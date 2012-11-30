package br.UFSC.GRIMA.capp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.JPanel;

/**
 * 
 * @author jc
 *
 */
public class ShopFloorPanel extends JPanel
{
	public ShopFloorPanel()
	{
		
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.draw(new Ellipse2D.Double(0, 0, 100, 100));
	}
}
