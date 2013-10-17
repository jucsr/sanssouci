package br.UFSC.GRIMA.shopFloor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PowerTorquePanel extends JPanel
{
	private CreateSpindle janelaSpindle;
	private double recuoX = 100;
	private double recuoY = 10;
	public double yScale = 5;
	public double yScale1 = 20;
	public double xScale = 0.05;
	public double rotacaoMax;
	public double torqueMax;
	public double powerMax;
	public PowerTorquePanel(CreateSpindle janelaSpindle)
	{
		this.janelaSpindle = janelaSpindle;
		this.setLayout(new FlowLayout());
		this.recuoX = recuoX * xScale;
		this.recuoY = recuoY * yScale;
		this.rotacaoMax = ((Double)this.janelaSpindle.spinner5.getValue()).doubleValue();
		this.torqueMax = ((Double)this.janelaSpindle.spinner7.getValue()).doubleValue();
		this.powerMax = ((Double)this.janelaSpindle.spinner6.getValue()).doubleValue();
	}
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.translate(recuoX, recuoY + ((Double)janelaSpindle.spinner7.getValue()).doubleValue() * yScale);
		g2d.scale(1, -1);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		drawGrid(g2d);
		drawTorqueCurve(g2d);
		drawPowerCurve(g2d);
		drawAxis(g2d);
		g2d.dispose();
	}
	public void drawPowerCurve(Graphics2D g2d)
	{
		g2d.setStroke(new BasicStroke());
		g2d.setColor(new Color(35, 142, 35));
		ArrayList<Double> xValues = new ArrayList<Double>();
		ArrayList<Point2D> coordinates = new ArrayList<Point2D>();
		double k = 50;
		double rotTmp = 0;
		for(int i = 0; rotTmp < rotacaoMax; i++)
		{
			rotTmp = rotTmp + 10;
			xValues.add(rotTmp);
		}
		for(int i = 0; i < xValues.size(); i++)
		{
			double xCoordinateTmp = xValues.get(i);
			double yCoordinateTmp = powerMax + 1 - Math.exp(xCoordinateTmp / rotacaoMax);
			Point2D coordinateTmp = new Point2D.Double(xCoordinateTmp * xScale, yCoordinateTmp * yScale1);
			coordinates.add(coordinateTmp);
			
		}
		for(int i = 0; i < coordinates.size() - 1; i++)
		{
			Line2D lineTmp = new Line2D.Double(coordinates.get(i), coordinates.get(i + 1));
			g2d.draw(lineTmp);
		}
	}
	/**
	 * Desenha a curva de torque
	 * @param g2d
	 */
	public void drawTorqueCurve(Graphics2D g2d)
	{
		g2d.setStroke(new BasicStroke());
		g2d.setColor(new Color(255, 0, 0));
		ArrayList<Double> xValues = new ArrayList<Double>();
		ArrayList<Point2D> coordinates = new ArrayList<Point2D>();
		double k = 50;
		double rotTmp = 0;
		for(int i = 0; rotTmp < rotacaoMax; i++)
		{
			rotTmp = rotTmp + 10;
			xValues.add(rotTmp);
		}
		for(int i = 0; i < xValues.size(); i++)
		{
			double xCoordinateTmp = xValues.get(i);
			double yCoordinateTmp = Math.exp(Math.log(torqueMax) - 1 / torqueMax / k * xCoordinateTmp);
			Point2D coordinateTmp = new Point2D.Double(xCoordinateTmp * xScale, yCoordinateTmp * yScale);
			coordinates.add(coordinateTmp);
			
		}
		for(int i = 0; i < coordinates.size() - 1; i++)
		{
			Line2D lineTmp = new Line2D.Double(coordinates.get(i), coordinates.get(i + 1));
			g2d.draw(lineTmp);
		}
	}
	/**
	 * Desenha a grade
	 * @param g2d -> elemento grafico
	 */
	public void drawGrid(Graphics2D g2d)
	{
		double spaceLength = ((Double)janelaSpindle.spinner5.getValue()).doubleValue() * xScale;
		double spaceHigth = ((Double)janelaSpindle.spinner7.getValue()).doubleValue() * yScale;
		
		g2d.setColor(new Color(168, 168, 168));
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		double yTmp = 0;
		
		for(int i = 0; yTmp < spaceHigth; i++) // Cria as linhas horizontais da grade
		{
			yTmp = i * 10 * yScale;
			Point2D initialPoint = new Point2D.Double(0, yTmp);
//			Point2D finalPoint = new Point2D.Double(recuoX + spaceLength, yTmp);
			Point2D finalPoint = new Point2D.Double(spaceLength, yTmp);
			Line2D line = new Line2D.Double(initialPoint, finalPoint);
			g2d.draw(line);

		}
		double xTmp = 0;
		for(int i = 0; xTmp < spaceLength; i++) // Cria as linhas verticais da grade
		{
			xTmp = i * 1000 * xScale;
//			Line2D line = new Line2D.Double(recuoX + xTmp, 0, recuoX + xTmp, spaceHigth);
			Line2D line = new Line2D.Double(xTmp, 0, xTmp, spaceHigth);
			g2d.draw(line);
		}
	}
	/**
	 * desenha os eixos
	 * @param g2d - > elemento grafico
	 */
	public void drawAxis(Graphics2D g2d)
	{
		g2d.scale(1, -1);
		g2d.setStroke(new BasicStroke());
		g2d.setColor(new Color(255, 0, 0));
		double spaceLength = ((Double)janelaSpindle.spinner5.getValue()).doubleValue() * xScale;
		double spaceHigth = ((Double)janelaSpindle.spinner7.getValue()).doubleValue() * yScale;
		double yTmp = 0;
		
		for(int i = 0; yTmp < spaceHigth; i++)
		{
			yTmp = i * 10 * yScale;
			g2d.drawString("" + yTmp / yScale, 0, (int)(-yTmp));
		}
		g2d.setColor(new Color(79, 79, 47));
		double xTmp = 0;
		for(int i = 0; xTmp < spaceLength; i++)
		{
			xTmp = i * 1000 * xScale;
			g2d.drawString("" + (xTmp / xScale), (int)(xTmp), 0);
		}
		g2d.scale(1, -1);
	}
}
