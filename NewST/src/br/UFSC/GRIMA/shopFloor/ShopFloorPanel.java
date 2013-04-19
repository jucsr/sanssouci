package br.UFSC.GRIMA.shopFloor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.UFSC.GRIMA.entidades.machiningResources.DrillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;

/**
 * 
 * @author jc
 *
 */
public class ShopFloorPanel extends JPanel
{	private double zoom = 1;
	private ProjetoSF projetoSF;
	private ShopFloor shopFloor;
	private Rectangle2D floor;// = new Rectangle2D.Double();
	private ArrayList<MachineTool> machines;
	private Point2D origemParaDesenho = new Point2D.Double();
	public boolean grade = true;
	private double escala = 10;
	private Dimension tamanho;
	public ShopFloorPanel(ProjetoSF projetoSF,ShopFloor shopFloor)
	{
		
		this.projetoSF= projetoSF;
		this.shopFloor = shopFloor;
		this.machines = projetoSF.getShopFloor().getMachines();
		this.origemParaDesenho = new Point2D.Double(10, 10);
	}
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.translate(origemParaDesenho.getX(), origemParaDesenho.getY() + this.zoom * this.shopFloor.getWidth() * escala);
		g2d.scale(1, -1);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		this.tamanho = new Dimension( (int)(origemParaDesenho.getX() * 2 + this.zoom * this.shopFloor.getLength() * escala), (int)(origemParaDesenho.getY() * 2 + this.zoom * this.shopFloor.getWidth() * escala));
		this.revalidate();

		this.setPreferredSize(this.tamanho);
		
		this.desenharAreaDoChaoDeFabrica(g2d);
		if(grade)
			this.desenharGrade(g2d);

		this.desenharMillingMachines(g2d);
		this.desenharDrillingMachines(g2d);
		g2d.dispose();
	}
	private void desenharGrade(Graphics2D g2d)
	{
		double comprimento = this.shopFloor.getLength();
		double largura = this.shopFloor.getWidth();
		
		int n = (int)(comprimento / 1);
		int m = (int)(largura / 1);
		g2d.setColor(new Color(205, 205, 0));
		
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		
		for(int i = 0; i < n; i++)
		{
			Line2D lineTmp = new Line2D.Double(i * 1 * zoom * escala, 0, i * 1 * zoom * escala, largura * zoom * escala);
			g2d.draw(lineTmp);
		}
		for(int i = 0; i < m; i++)
		{
			Line2D lineTmp = new Line2D.Double(0, i * 1 * zoom * escala, comprimento * zoom * escala, i * 1 * zoom * escala);
			g2d.draw(lineTmp);
		}
	}
	public void zooming(double zoom)
	{
		this.zoom = zoom/100;
	}
	public void desenharAreaDoChaoDeFabrica(Graphics2D g2d)
	{
		this.floor = new Rectangle2D.Double(0, 0, this.zoom * this.shopFloor.getLength() * escala, this.zoom * this.shopFloor.getWidth() * escala);
		g2d.setColor(new Color(189, 183, 107));
		g2d.fill(floor);
		g2d.setColor(Color.blue);
		g2d.draw(floor);
	}
	public void desenharMillingMachines(Graphics2D g2d)
	{
		g2d.setStroke(new BasicStroke());
		for (int i = 0; i < this.machines.size(); i++)
		{
			MachineTool machineTmp = this.machines.get(i);
			if(machineTmp.getClass() == MillingMachine.class)
			{
				Rectangle2D milling2DTmp = new Rectangle2D.Double(machineTmp.getItsOrigin().x * escala * zoom, machineTmp.getItsOrigin().y * escala * zoom, 3 * zoom * escala, 2 * zoom * escala);
				g2d.setColor(new Color(0, 191, 255));
				g2d.fill(milling2DTmp);
				g2d.setColor(Color.black);
				g2d.draw(milling2DTmp);
			}
		}
	}
	public void desenharDrillingMachines(Graphics2D g2d)
	{
		g2d.setStroke(new BasicStroke());
		for (int i = 0; i < this.machines.size(); i++)
		{
			MachineTool machineTmp = this.machines.get(i);
			if(machineTmp.getClass() == DrillingMachine.class)
			{
				Ellipse2D drilling2DTmp = new Ellipse2D.Double(machineTmp.getItsOrigin().x * escala * zoom, machineTmp.getItsOrigin().y * escala * zoom, 40 * zoom * escala, 30 * zoom * escala);
				g2d.fill(drilling2DTmp);
				g2d.draw(drilling2DTmp);
			}
		}
	}
}
