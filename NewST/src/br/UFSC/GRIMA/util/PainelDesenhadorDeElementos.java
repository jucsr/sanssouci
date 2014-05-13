package br.UFSC.GRIMA.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
/**
 * 
 * @author Jc
 *
 */
public class PainelDesenhadorDeElementos extends JPanel implements MouseMotionListener
{
	private double zoom = 1;
	public ArrayList<LimitedElement> elements;
	public ArrayList<Point3d> intersections;
	public boolean desenharCoordenadas = false;
	public boolean desenharIntersecoes = false;
	public String x = "";
	public String y = "";
	private Dimension tamanho;
	private double xMax;
	private double yMax;
	private int nDecimais = 0;
	
	public PainelDesenhadorDeElementos(ArrayList<LimitedElement> elements)
	{
		this.elements = elements;
		this.addMouseMotionListener(this);
		this.intersections = GeometricOperations.intersectionElements(elements);
		for(int i = 0; i < elements.size(); i++)
		{
			LimitedElement elementTmp = elements.get(i);
			if(elementTmp.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)elementTmp;
				/**
				 * ==horizontal
				 */
				if(line.xmaxl > xMax)
					xMax = line.xmaxl;
				/**
				 * ==vertical
				 */
				if(line.ymaxl > yMax)
					yMax = line.ymaxl;
			} else if(elementTmp.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)elementTmp;
				/**
				 * ==horizontal
				 */
				if(arc.getInitialPoint().x > xMax)
					xMax = arc.getInitialPoint().x;
				if(arc.getFinalPoint().x > xMax)
					xMax = arc.getFinalPoint().x;
				if(arc.getCenter().x > xMax)
					xMax = arc.getCenter().x;
				/**
				 *  == vertical
				 */
				if(arc.getInitialPoint().y > yMax)
					yMax = arc.getInitialPoint().y;
				if(arc.getFinalPoint().y > yMax)
					yMax = arc.getFinalPoint().y;
				if(arc.getCenter().y > yMax)
					yMax = arc.getCenter().y;
			}
		}
	}
	public void setnDecimais(int nDecimais)
	{
		this.nDecimais = nDecimais;
	}
	public void setZoom(double zoom)
	{
		this.zoom = zoom;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		Graphics2D g2d = (Graphics2D) g.create();
		this.tamanho = new Dimension((int)(xMax * zoom + 100), (int)(yMax * zoom + 50));
		this.setPreferredSize(tamanho);
		this.revalidate();
		this.setBackground(Color.black);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(25, this.getSize().height - 25);
		g2d.scale(1, -1);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
		desenharGrade(g2d);
		desenharCoordinatesNoPonteiro(g2d);
		desenharElements(elements, g2d);
		if(desenharIntersecoes)
		{
			this.desenharIntersecoes(this.intersections, g2d);
		}
		
//		g2d.dispose();
	}
	private void desenharIntersecoes(ArrayList<Point3d> intersecoes, Graphics2D g2d)
	{
		for(Point3d pontoTmp: intersecoes)
		{
			this.desenharCoordenadas(pontoTmp, g2d, new Color(255, 200, 0));
		}
	}
	private void desenharElements(ArrayList<LimitedElement> elements, Graphics2D g2d)
	{
		
		for(int i = 0; i < elements.size(); i++)
		{
			LimitedElement elementTmp = elements.get(i);
			if(elementTmp.isLimitedLine())
			{
//				desenharLinha((LimitedLine)elementTmp, g2d, new Color(127, 255, 0));
//				desenharLinha((LimitedLine) elementTmp, g2d, new Color(0, 100, 255));
				desenharLinha((LimitedLine) elementTmp, g2d, new Color((int)(Math.random()*254), (int)(Math.random()*254), (int)(Math.random()*254)));
			} else if(elementTmp.isLimitedArc())
			{
//				desenharArco((LimitedArc)elementTmp, g2d, new Color(0, 100, 255));
				desenharArco((LimitedArc)elementTmp, g2d, new Color((int)(Math.random()*254), (int)(Math.random()*254), (int)(Math.random()*254)));
			}
		}
	}
	private void desenharCoordenadas(Point3d ponto, Graphics2D g2d, Color color)
	{
		g2d.setColor(color);
		g2d.fill(new Ellipse2D.Double(ponto.x * zoom - 5 / 2, ponto.y * zoom - 5 / 2, 5, 5));
		g2d.scale(1, -1);
//		g2d.setFont(new Font("Shruti", Font.BOLD, 12));
		g2d.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 12));
		g2d.drawString("(" + GeometricOperations.roundNumber(ponto.x, nDecimais) + ", " + GeometricOperations.roundNumber(ponto.y, nDecimais) + ")", (int)(ponto.x * zoom + 2), (int)(-ponto.y * zoom - 2));
		g2d.scale(1, -1);
	}
	private void desenharArco(LimitedArc arc, Graphics2D g2d, Color color)
	{
//		g2d.setColor(new Color(12, 66, 200));
		g2d.setColor(color);
		double anguloInicial = Math.atan2(arc.getInitialPoint().y - arc.getCenter().y, arc.getInitialPoint().x - arc.getCenter().x);
		double anguloFinal = Math.atan2(arc.getFinalPoint().y - arc.getCenter().y, arc.getFinalPoint().x - arc.getCenter().x);
		double deltaAngulo = 0;
		if(arc.getDeltaAngle() > 0)
		{
			if(anguloInicial < 0)
			{
				anguloInicial = 2 * Math.PI + anguloInicial;
			}
			if(anguloFinal <= 0)
			{
				anguloFinal = 2 * Math.PI + anguloFinal;
			}
			if(anguloInicial > anguloFinal)
			{
				anguloFinal = 2 * Math.PI + anguloFinal;
			}
			deltaAngulo = - (anguloFinal - anguloInicial) * 180 / Math.PI;
			anguloInicial = - anguloInicial * 180 / Math.PI;
		}
		else
		{
			deltaAngulo = arc.getDeltaAngle()* 180 / Math.PI;
			System.out.println("DeltaAngle: " + deltaAngulo);
		}
		
//		System.out.println("================");
//		System.out.println("A_INI = " + anguloInicial);
//		System.out.println("A_FIN = " + anguloFinal * 180 / Math.PI);
//		System.out.println("DELTA = " + deltaAngulo);
//		System.out.println("================");
		
		Arc2D arco = new Arc2D.Double((arc.getCenter().x  - arc.getRadius()) * zoom, (arc.getCenter().y - arc.getRadius()) * zoom, arc.getRadius() * 2 * zoom, arc.getRadius() * 2 * zoom, anguloInicial, deltaAngulo, 0);
//		Arc2D arco = new Arc2D.Double((100) * zoom, (100) * zoom, 20 * 2 * zoom, 20 * 2 * zoom, 0, 90, 0);

		g2d.draw(arco);
		if(desenharCoordenadas)
		{
			float dash1[] = {2.0f, 4.0f};  
		    BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);  
		    g2d.setStroke(dashed);  
		  
		  
			Line2D line1 = new Line2D.Double(arc.getCenter().x * zoom, arc.getCenter().y * zoom, arc.getInitialPoint().x * zoom, arc.getInitialPoint().y * zoom);
			Line2D line2 = new Line2D.Double(arc.getCenter().x * zoom, arc.getCenter().y * zoom, arc.getFinalPoint().x * zoom, arc.getFinalPoint().y * zoom);
			g2d.draw(line1);
			g2d.draw(line2);
			g2d.setStroke(new BasicStroke());
			
			desenharCoordenadas(arc.getCenter(), g2d, new Color(0, 255, 0));
			desenharCoordenadas(arc.getInitialPoint(), g2d, new Color(0, 255, 0));
			desenharCoordenadas(arc.getFinalPoint(), g2d, new Color(0, 255, 0));
		}
	}
	private void desenharLinha(LimitedLine line, Graphics2D g2d, Color color) 
	{
		g2d.setColor(color);
		Line2D linha = new Line2D.Double(line.getInitialPoint().x * zoom, line.getInitialPoint().y * zoom, line.getFinalPoint().x * zoom, line.getFinalPoint().y * zoom);
		g2d.draw(linha);
		if(desenharCoordenadas)
		{
			desenharCoordenadas(line.getInitialPoint(), g2d, new Color(0, 255, 0));
			desenharCoordenadas(line.getFinalPoint(), g2d, new Color(0, 255, 0));
		}
	}
	private void desenharGrade(Graphics2D g2d)
	{
		for(int i = 0; i < 100; i++)
		{
			/**
			 * 	desenha as linhas
			 */
			/*
			 * 	linhas horizontais
			 */
//			g2d.setColor(new Color(200, 200, 200));
			g2d.setColor(new Color(33, 33, 33));
			Line2D lineTmp = new Line2D.Double(0 * zoom, i * 10 * zoom, 100 * 10 * zoom, i * 10 * zoom);
			g2d.draw(lineTmp);
			/*
			 * 	linhas verticais
			 */
//			g2d.setColor(new Color(200, 200, 200));
			lineTmp = new Line2D.Double(i * 10 * zoom, 0 * zoom, i * 10 * zoom, 100 * 10 * zoom);
			g2d.draw(lineTmp);
			
			/**
			 * 	desenha os numeros
			 */
			/*
			 * 	eixo Y
			 */
			g2d.setColor(new Color(200, 12, 12));
			g2d.scale(1,  -1);
			g2d.drawString(""+ (i * 10), (int)(-10), (int)(-i * 10 * zoom));
			g2d.scale(1, -1);
			/*
			 * 	eixo X
			 */
			g2d.rotate(-Math.PI / 2);
			g2d.scale(-1, 1);
			g2d.drawString(""+ (i * 10), (int)(-10), (int)(i * 10 * zoom));
			g2d.scale(-1, 1);
			g2d.rotate(Math.PI / 2);
			
		}
	}
	private void desenharCoordinatesNoPonteiro(Graphics2D g2d)
	{
		g2d.scale(1, -1);
		g2d.setColor(new Color(230,232,250));
		g2d.setFont(new Font("Lucida Calligraphy", Font.BOLD, 12));
		int x, y;
		try
		{
			x = (int)Double.parseDouble(this.x);
			y = (int)Double.parseDouble(this.y);
			g2d.drawString("x:\t" + this.x, (int)(x * zoom), -(int)(y * zoom));
			g2d.drawString("y:\t" + this.y, (int)(x * zoom), -(int)(y * zoom + 13));
		} catch(Exception e)
		{
			
		}
		g2d.scale(1, -1);
	}
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		x = "" + GeometricOperations.truncarDecimais((e.getX() - 25) / zoom, nDecimais);
		y = "" + GeometricOperations.truncarDecimais(((this.getSize().height - e.getY() - 25) / zoom), nDecimais);
		this.repaint();
	}
}
