package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class GeometricOperationsTest 
{
	LimitedLine lineA;
	LimitedLine lineB;
	Point3d p1_3D = new Point3d(0.0, 1.0, 0.0);
	Point3d p2_3D = new Point3d(0.0, 0.0, 1.0);
	Point3d p3_3D = new Point3d(110.0, 100.0, 0.0);
	
	Point3d fp1_3D = new Point3d(2.0, 0.0, 0.0);
	Point3d sp1_3D = new Point3d(0.0, 2.0, 0.0);
	
	Point3d fp2_3D = new Point3d(2.0, 2.0, 0.0);
	Point3d sp2_3D = new Point3d(2.0, 9.0, 0.0);
	
//	Point3d fp3_3D = new Point3d(80.0, 140.0, 0.0);
//	Point3d sp3_3D = new Point3d(120.0, 140.0, 0.0);
	
//	Point3d fp3_3D = new Point3d(105.860, 35.860, 0.0);
//	Point3d sp3_3D = new Point3d(134.14, 64.140, 0.0);

	Point3d fp3_3D = new Point3d(134.61, 85.61, 0.0);
	Point3d sp3_3D = new Point3d(107.05, 102.32, 0.0);

	
	Point3d initialPoint1_3D = new Point3d(1.0, 0.0, 0.0);
	Point3d initialPoint2_3D = new Point3d(0.0, 1.0, 0.0);
	Point3d initialPoint3_3D = new Point3d(4.0, 13.0, 0.0);
	
	Point3d initialPoint4_3D = new Point3d(125.0, 100.0, 0.0);
	Point3d initialPoint5_3D = new Point3d(105.10, 101.76, 0.0);
	
	Point3d center1 = new Point3d(0.0, 0.0, 0.0);
	Point3d center2 = new Point3d(2.0, 15.0, 0.0);
	Point3d center3 = new Point3d(110.0, 100.0, 0.0);	
	Point3d center4 = new Point3d(113.760, 96.770, 0.0);
	
	LimitedLine line1 = new LimitedLine(fp1_3D, sp1_3D);
	LimitedLine line2 = new LimitedLine(fp2_3D, sp2_3D);
	
	LimitedLine line3 = new LimitedLine(fp3_3D, sp3_3D);
	
	LimitedArc arc1 = new LimitedArc(center1, initialPoint1_3D, Math.PI/2, 1);
	LimitedArc arc2 = new LimitedArc(center1, initialPoint2_3D, Math.PI/2, 1);
	LimitedArc arc3 = new LimitedArc(center2, initialPoint3_3D, Math.PI, 1);
	
	LimitedArc arc4 = new LimitedArc(center3, initialPoint4_3D, 3*Math.PI/2, 1);
	LimitedArc arc5 = new LimitedArc(center4, initialPoint5_3D, 240*Math.PI/180.0, 1);
	
	Point3d testPoint = new Point3d(-0.5, -0.5, 0);
	Point3d testPoint2 = new Point3d(1.0, 13.0, 0);
	
	@Before
	public void init()
	{
		lineA = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
	    lineB = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
	}
	@Test
	public void testeDistance3D()
	{
		System.out.println("*********************************************************");
		System.out.println("Distance\nfrom  " + p1_3D + "\nto " + p2_3D + " = " + GeometricOperations.distance(p1_3D, p2_3D));		
	}
	
	@Test
	public void testeMinimumDistance()
	{
		System.out.println("*********************************************************");
		System.out.println("Nearest Point to Line limited by " + " " + line3.getInitialPoint() + " " + line3.getFinalPoint());
		System.out.println("from point " + p3_3D);
		System.out.println(GeometricOperations.minimumDistancePointToLine(p3_3D, line3));
		System.out.println("*********************************************************");
		System.out.println("Minimum distance between Limited lines");
		System.out.println("Line1 from " + line1.getInitialPoint() + " to " + line1.getFinalPoint());
		System.out.println("Line2 from " + line2.getInitialPoint() + " to " + line2.getFinalPoint());
		System.out.println(GeometricOperations.minimumDistanceLineToLine(line1,line2));
		System.out.println("*********************************************************");
		System.out.println("Circle segment limited ");
		System.out.println("From " + arc1.getInitialPoint());
		System.out.println("To " + arc1.getFinalPoint());
		System.out.println("with Radius " + arc1.getRadius());
		System.out.println("and center " + arc1.getCenter());
		System.out.println("To point " + fp1_3D);
		System.out.println("Distance " + GeometricOperations.minimumDistancePointToArc(fp1_3D, arc1));
		System.out.println("*********************************************************");
		System.out.println("Circle segment limited ");
		System.out.println("From " + arc2.getInitialPoint());
		System.out.println("To " + arc2.getFinalPoint());
		System.out.println("with Radius " + arc2.getRadius());
		System.out.println("and center " + arc2.getCenter());
		System.out.println("To point " + testPoint);
		System.out.println("Distance " + GeometricOperations.minimumDistancePointToArc(testPoint, arc2));
		System.out.println("*********************************************************");
		System.out.println("Circle segment limited ");
		System.out.println("From " + arc3.getInitialPoint());
		System.out.println("To " + arc3.getFinalPoint());
		System.out.println("with Radius " + arc3.getRadius());
		System.out.println("and center " + arc3.getCenter());
		System.out.println("To point " + testPoint2);
		System.out.println("Distance " + GeometricOperations.minimumDistancePointToArc(testPoint2, arc3));
		System.out.println("*********************************************************");
		System.out.println("Circle segment limited ");
		System.out.println("From " + arc4.getInitialPoint());
		System.out.println("To " + arc4.getFinalPoint());
		System.out.println("with Radius " + arc4.getRadius());
		System.out.println("and center " + arc4.getCenter());
		System.out.println("To Line ");
		System.out.println("From " + line3.getInitialPoint());
		System.out.println("To " + line3.getFinalPoint());
		System.out.println("Distance " + GeometricOperations.minimumDistanceLineToArc(line3, arc4));
		System.out.println("*********************************************************");
		System.out.println("Circle segment limited ");
		System.out.println("From " + arc4.getInitialPoint());
		System.out.println("To " + arc4.getFinalPoint());
		System.out.println("with Radius " + arc4.getRadius());
		System.out.println("and center " + arc4.getCenter());
		System.out.println("to another Circle segment limited ");
		System.out.println("From " + arc5.getInitialPoint());
		System.out.println("To " + arc5.getFinalPoint());
		System.out.println("with Radius " + arc5.getRadius());
		System.out.println("and center " + arc5.getCenter());
		System.out.println("Distance " + GeometricOperations.minimumDistanceArcToArc(arc4, arc5));
	}
	
	@Test
	public void intersectionElementsTest() 
	{
		//Ponto Final e Inicial iguais 
		LimitedLine line1 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		LimitedLine line2 = new LimitedLine(new Point3d(20,10,0), new Point3d(20,20,0));
		
		//Retas que não se tocam
		LimitedLine line3 = new LimitedLine(new Point3d(30,30,0), new Point3d(40,30,0));
		LimitedLine line4 = new LimitedLine(new Point3d(30,40,0), new Point3d(40,40,0));
		
		//Retas com intersectão (vertical - horizoltal)
		LimitedLine line5 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
		LimitedLine line6 = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
		
		//Linhas não paraleleas com intersecao
		LimitedLine line7 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 20, 0));
		LimitedLine line8 = new LimitedLine(new Point3d(15, 10, 0), new Point3d(20, 0, 0));
		
		Point3d intersection = GeometricOperations.intersectionElements(line5, line6);
		System.err.println("Intersection Validated: " + intersection);
	}
	@Test
	public void determinarMovimentacaoGenCavTest()
	{

		class painelTest extends JPanel
		{
			painelTest()
			{
			GeneralPath formaFeature = new GeneralPath();
			
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
		
				desenharLinha(lineA, g2d);
				desenharLinha(lineB, g2d);
			}

			private void desenharLinha(LimitedLine line, Graphics2D g2d) 
			{
				Line2D linha = new Line2D.Double(line.getInitialPoint().x, line.getInitialPoint().y, line.getFinalPoint().x, line.getFinalPoint().y);
				g2d.draw(linha);
			}
			
		}
		JFrame frame = new JFrame("Painel");
		painelTest painel = new painelTest();
		frame.setSize(700, 400);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
	}
}
