package br.UFSC.GRIMA.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
/**
 * 
 * @author Jc
 *
 */
public class PainelDesenhadorDeElementos extends JPanel
{
	private double zoom = 1;
	public ArrayList<LimitedElement> elements;
	
	public PainelDesenhadorDeElementos(ArrayList<LimitedElement> elements)
	{
		this.elements = elements;
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
		
//		Toolkit toolkit = this.getToolkit();
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(25, this.getSize().height - 25);
		g2d.scale(1, -1);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
		desenharGrade(g2d);
		
		desenharElements(elements, g2d);
//		g2d.dispose();
	}
	private void desenharElements(ArrayList<LimitedElement> elements, Graphics2D g2d)
	{
		
		for(int i = 0; i < elements.size(); i++)
		{
			LimitedElement elementTmp = elements.get(i);
			if(elementTmp.isLimitedLine())
			{
				desenharLinha((LimitedLine)elementTmp, g2d);
			} else if(elementTmp.isLimitedArc())
			{
				desenharArco((LimitedArc)elementTmp, g2d);
			}
		}
	}
	private void desenharArco(LimitedArc arc, Graphics2D g2d)
	{
		g2d.setColor(new Color(12, 66, 200));
		
		double anguloInicial = Math.atan2(arc.getInitialPoint().y - arc.getCenter().y, arc.getInitialPoint().x - arc.getCenter().x);
		double anguloFinal = Math.atan2(arc.getFinalPoint().y - arc.getCenter().y, arc.getFinalPoint().x - arc.getCenter().x);
		
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
		double deltaAngulo = -(anguloFinal - anguloInicial) * 180 / Math.PI;
		anguloInicial = anguloInicial * 180 / Math.PI;

		Arc2D arco = new Arc2D.Double((arc.getCenter().x  - arc.getRadius()) * zoom, (arc.getCenter().y - arc.getRadius()) * zoom, arc.getRadius() * 2 * zoom, arc.getRadius() * 2 * zoom, anguloInicial, deltaAngulo, 0);
		g2d.draw(arco);
	}
	private void desenharLinha(LimitedLine line, Graphics2D g2d) 
	{
		g2d.setColor(new Color(12, 66, 200));
		Line2D linha = new Line2D.Double(line.getInitialPoint().x * zoom, line.getInitialPoint().y * zoom, line.getFinalPoint().x * zoom, line.getFinalPoint().y * zoom);
		g2d.draw(linha);
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
			g2d.setColor(new Color(200, 200, 200));
			Line2D lineTmp = new Line2D.Double(0 * zoom, i * 10 * zoom, 100 * 10 * zoom, i * 10 * zoom);
			g2d.draw(lineTmp);
			/*
			 * 	linhas verticais
			 */
			g2d.setColor(new Color(200, 200, 200));
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
}
