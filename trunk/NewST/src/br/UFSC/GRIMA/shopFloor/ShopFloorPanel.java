package br.UFSC.GRIMA.shopFloor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.JPanel;

/**
 * 
 * @author jc
 *
 */
public class ShopFloorPanel extends JPanel
{	private double zoom;
	private ProjetoSF projetoSF;
	private ShopFloor shopFloor;
	private Rectangle2D floor;
	public ShopFloorPanel(ProjetoSF projetoSF,ShopFloor shopFloor)
	{
		
		this.projetoSF= projetoSF;
		this.shopFloor = shopFloor;
		this.floor = new Rectangle2D.Double(10,10,this.shopFloor.getWidth(),this.shopFloor.getLength());

	}
	public void paintComponent(Graphics g)
	{	super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.draw(floor);
		g2d.dispose();
	}
	public void zooming(double zoom){
		this.zoom = zoom/100;
		this.floor = new Rectangle2D.Double(10,10,this.zoom*this.shopFloor.getWidth(),this.zoom*this.shopFloor.getLength());
		
	}
	
}
