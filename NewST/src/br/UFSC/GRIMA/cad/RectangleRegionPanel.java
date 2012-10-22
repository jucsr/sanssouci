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
import java.awt.geom.RoundRectangle2D;

import br.UFSC.GRIMA.util.projeto.Projeto;

public class RectangleRegionPanel extends DesenhadorDeFaces implements MouseListener, MouseMotionListener
{
	public boolean grade = true;
	public String x = "";
	public String y = "";
	public Shape shape;
	public double separacaoGrade = 20;
	public RoundRectangle2D rectangle = new RoundRectangle2D.Double();
	public Line2D xLine = new Line2D.Double();
	public Line2D yLine = new Line2D.Double();
	public Line2D lengthLine = new Line2D.Double();
	public Line2D widthLine = new Line2D.Double();
	public int clicked = 0;
	private Point2D pontoClicado = new Point2D.Double();
	private Point2D esquina = new Point2D.Double();
	private CreateRegion janelaRectangle;

	public RectangleRegionPanel(Projeto projeto, CreateRegion janelaRectangle)
	{
		super(projeto);
		this.janelaRectangle = janelaRectangle;
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
		
       
        if(shape != null)
        {
        	g2d.setColor(new Color(255, 69, 0));
        	g2d.draw(shape);
        }
		
		g2d.setColor(new Color(205, 205, 193));
        g2d.fill(rectangle);
        
        g2d.setColor(Color.black);
        g2d.draw(rectangle);
        
        g2d.setColor(new Color(255, 255, 0));
        float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
        g2d.draw(xLine);
        g2d.draw(yLine);
        
        g2d.draw(lengthLine);
        g2d.draw(widthLine);
        
        this.drawCoordinates(g2d);
        g2d.dispose();
	}
	@Override
	public void mouseDragged(MouseEvent arg0)
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
			
			this.janelaRectangle.xSpinner.setBounds((int)(x / 2 - 10), (int)((e.getY() -10)), 40, 20);
			this.janelaRectangle.xSpinner.setValue(Double.parseDouble(this.x));
			this.janelaRectangle.xSpinner.setVisible(true);
			
			this.janelaRectangle.ySpinner.setBounds((int)(x - 20), (int)((projeto.getBloco().getComprimento() * getZoom() - y  + y / 2+ 20)), 40, 20);
			this.janelaRectangle.ySpinner.setValue(Double.parseDouble(this.y));
			this.janelaRectangle.ySpinner.setVisible(true);
		}
		if(clicked == 1)
		{
			this.esquina = new Point2D.Double(pontoClicado.getX(), pontoClicado.getY());
			double length = x - esquina.getX() * getZoom() - 20;
			double width = y - esquina.getY() * getZoom() - 20;
			Point2D ponto1 = new Point2D.Double(esquina.getX() * getZoom() + 20, esquina.getY() * getZoom() + 20);
			Point2D ponto2 = new Point2D.Double(Double.parseDouble(this.x) * getZoom() + 20, Double.parseDouble(this.y) * getZoom() + 20);
			
//			System.out.println("PONTO 1-------------->" + ponto1);
//			System.out.println("PONTO 2-------------->" + ponto2);
			double radius = ponto1.distance(ponto2);
//			System.out.println("radius = ========> " + radius);
			rectangle = new RoundRectangle2D.Double(ponto1.getX(), ponto1.getY(), length, width, 0, 0);
			
			this.janelaRectangle.lengthSpinner.setValue(length / getZoom());
			this.janelaRectangle.widthSpinner.setValue(width / getZoom());
			
			lengthLine = new Line2D.Double(ponto1.getX(), ponto1.getY() + width / 4, ponto1.getX() + length, ponto1.getY() + width / 4);
			widthLine = new Line2D.Double(ponto1.getX() + length / 4, ponto1.getY(), ponto1.getX() + length / 4, ponto1.getY() + width);
			
			this.janelaRectangle.lengthSpinner.setBounds((int)(x - length / 2 - 20), (int)((projeto.getBloco().getComprimento() * getZoom() - y  + 3 * width / 4 + 20 + 10)), 40, 20);
			this.janelaRectangle.widthSpinner.setBounds((int)(x - 3 * length / 4 - 20), (int)(projeto.getBloco().getComprimento() * getZoom() - y + width / 2 + 20 + 10), 40, 20);
			
			this.janelaRectangle.lengthSpinner.setVisible(true);
			this.janelaRectangle.widthSpinner.setVisible(true);
			
		} else if (clicked == 2)
		{
			this.janelaRectangle.radiusSpinner.setEnabled(true);
			this.janelaRectangle.lengthSpinner.setEnabled(true);
			this.janelaRectangle.widthSpinner.setEnabled(true);
		}
		

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
