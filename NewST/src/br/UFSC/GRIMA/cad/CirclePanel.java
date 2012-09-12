package br.UFSC.GRIMA.cad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.SpinnerNumberModel;

import br.UFSC.GRIMA.util.projeto.Projeto;
/**
 * 
 * @author Jc
 *
 */
public class CirclePanel extends DesenhadorDeFaces implements MouseListener, MouseMotionListener
{
	public Ellipse2D circle = new Ellipse2D.Double();
	public Ellipse2D circle2 = new Ellipse2D.Double();
	public Line2D xLine = new Line2D.Double();
	public Line2D yLine = new Line2D.Double();
	public double separacaoGrade = 20;
	public String x = "";
	public String y = "";
	public boolean grade = true, line = true;
	public Shape shape;
	public int clicked = 0;
	private Point2D pontoClicado = new Point2D.Double();
	public Point2D circleCenter = new Point2D.Double();
	private CreateCircularBoss janelaCircle;
	public CirclePanel(Projeto projeto, CreateCircularBoss janelaCircle)
	{
		super(projeto);	
		this.janelaCircle = janelaCircle;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	
		g2d.setStroke(new BasicStroke());
		if(grade)
			this.drawGrade(g2d);
		
        this.drawCoordinates(g2d);
       
        if(shape != null)
        {
        	g2d.setColor(new Color(255, 69, 0));
        	g2d.draw(shape);
        }
        
		g2d.setColor(new Color(205, 205, 193));
        g2d.fill(circle);
        
        g2d.setColor(Color.black);
        g2d.draw(circle);
        
        g2d.draw(circle2);
        
		g2d.setColor(new Color(255, 64, 64));
        float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
        g2d.draw(xLine);
        g2d.draw(yLine);
        
        g2d.dispose();
//        this.janelaCircle.xSpinner.setBounds((int)(circleCenter.getX() / 2), (int)(circleCenter.getY() - 20), 40, 30);
//		this.janelaCircle.xSpinner.setValue(circleCenter.getX());
//		this.janelaCircle.xSpinner.setVisible(true);
	}
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = "" + (e.getX() - 20) / getZoom();
		y = "" + ((projeto.getBloco().getComprimento() * getZoom() - e.getY() + 20) / getZoom());
		double x = Double.parseDouble(this.x) * getZoom() + 20;
		double y = Double.parseDouble(this.y) * getZoom() + 20;
		if(clicked == 0)
		{
			xLine = new Line2D.Double(new Point2D.Double(20, y), new Point2D.Double(x, y));
			yLine = new Line2D.Double(new Point2D.Double(x, 20), new Point2D.Double(x, y));
			
//			this.janelaCircle.xSpinner.setBounds((int)(x / 2 - 10), (int)((e.getY() -10)), 40, 20);
//			this.janelaCircle.xSpinner.setValue(Double.parseDouble(this.x));
//			this.janelaCircle.xSpinner.setVisible(true);
			
			this.janelaCircle.ySpinner.setBounds((int)(x - 20), (int)((projeto.getBloco().getComprimento() * getZoom() - y  + y / 2+ 20)), 40, 20);
			this.janelaCircle.ySpinner.setValue(Double.parseDouble(this.y));
			this.janelaCircle.ySpinner.setVisible(true);
		}
		if(clicked == 1)
		{
			this.circleCenter = new Point2D.Double(pontoClicado.getX(), pontoClicado.getY());
			Point2D ponto1 = new Point2D.Double(pontoClicado.getX() * getZoom() + 20, pontoClicado.getY() * getZoom() + 20);
			Point2D ponto2 = new Point2D.Double(Double.parseDouble(this.x) * getZoom() + 20, Double.parseDouble(this.y) * getZoom() + 20);
			
//			System.out.println("PONTO 1-------------->" + ponto1);
//			System.out.println("PONTO 2-------------->" + ponto2);
			double radius = ponto1.distance(ponto2);
//			System.out.println("radius = ========> " + radius);
			circle = new Ellipse2D.Double(ponto1.getX() - radius, ponto1.getY() - radius, radius * 2, radius * 2);
			this.janelaCircle.radiusSpinner.setValue(radius / getZoom());
			this.janelaCircle.radius2Spinner.setValue(radius / getZoom());
		} else if (clicked == 2)
		{
			this.janelaCircle.radiusSpinner.setEnabled(true);
			this.janelaCircle.radius2Spinner.setEnabled(true);
//			this.janelaCircle.radius2Spinner.setModel(new SpinnerNumberModel((Double)this.janelaCircle.radius2Spinner.getValue(), (Double)this.janelaCircle.radiusSpinner.getValue(), null, 1.0));
		}
//		this.janelaCircle.xSpinner.setBounds((int)(circleCenter.getX() / 2), (int)(-(projeto.getBloco().getComprimento() * getZoom() - e.getY() + 20) / getZoom()), 40, 20);
		

		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		double x = (e.getX() - 20) / getZoom();
		double y = ((projeto.getBloco().getComprimento() * getZoom() - e.getY() + 20) / getZoom());
		pontoClicado = new Point2D.Double(x, y);
		System.out.println("CLICKED --> " + pontoClicado);
		this.clicked++;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}
	
	private void drawGrade(Graphics2D g)
	{
		g.setColor(new Color(156, 156, 156));
		Dimension dimension = this.getSize();
		
		double nHorizontal = dimension.width / separacaoGrade;
		double nVertical = dimension.height / separacaoGrade;
		
		for (int i = 0; i <= nVertical; i++)
		{
			g.drawLine(0, (int)(20 + i * separacaoGrade * getZoom()), dimension.width, (int)(20 + i * separacaoGrade * getZoom()));
		}
		for(int i = 0; i <= nHorizontal; i++)
		{
			g.drawLine((int)(20 + i * separacaoGrade * getZoom()), 0, (int)(20 + i * separacaoGrade * getZoom()), dimension.height);
		}
	}
	private void drawCoordinates(Graphics2D g)
	{
		g.scale(1, -1);
		g.setColor(new Color(139, 90, 0));
		g.setFont(new Font("Arial", Font.BOLD, 12));
		int x, y;
		try
		{
			x = (int)Double.parseDouble(this.x);
			y = (int)Double.parseDouble(this.y);
			g.drawString("x:\t" + this.x, (int)(x * getZoom() + 20), -(int)(y * getZoom() + 20 + 13));
			g.drawString("y:\t" + this.y, (int)(x * getZoom() + 20), -(int)(y * getZoom() + 20 + 2));
		} catch(Exception e)
		{
			
		}
		g.scale(1, -1);
	}
}
