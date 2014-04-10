package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrochoidalMovementTest
{
	private ArrayList<LimitedElement> elements;
	private GenerateTrochoidalMovement path;
	
	
	@Before
	
	public void Project(){
		
		Point3d point1 = new Point3d(0, 0, 0); 
		Point3d point2 = new Point3d(0, 300, 0);
		LimitedLine linha1 = new LimitedLine(point1, point2);
		elements = new ArrayList<LimitedElement>();
		
		elements.add(linha1);
		path = new GenerateTrochoidalMovement(elements, 30, 10);
	}
	
	@Test
	
	public void generateTrochoidalMovementTest()
	{
		
	}
	
	@Test
	
	public void determinarMovimentacao(){
		


		class painelTest extends JPanel
		{
			
			painelTest()
			{

			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponents(g);

				Graphics2D g2d = (Graphics2D)g;
				g2d.translate(25, 325);
				g2d.scale(1, -1);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
				for(int i = 0; i < elements.size(); i++)
				{
					if(elements.get(i).isLimitedLine())
					{
						LimitedLine temp = (LimitedLine) elements.get(i);
						Line2D line = new Line2D.Double(temp.getInitialPoint().x, temp.getInitialPoint().y, temp.getFinalPoint().x, temp.getFinalPoint().y);
						g2d.draw(line);
					
						for(int j = 0; j < path.movimentacao.size(); j++){
							
							CircularPath arcTemp = (CircularPath) path.movimentacao.get(j);
							Arc2D.Double arc2D = new Arc2D.Double(arcTemp.getCenter().x, arcTemp.getCenter().y, 50, 50, 0, 360, 0);
							g2d.draw(arc2D);
						 // System.out.println("Centro x, y "+arcTemp.getCenter().x + arcTemp.getCenter().y);
							
							
						}
						
					} else if (elements.get(i).isLimitedArc())
					{
						
					}
				}

			}
		}
		JFrame frame = new JFrame("Path");
		painelTest painel = new painelTest();
		frame.setSize(700, 400);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
	
	}
}
