package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
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
	
    LimitedArc arco0= new LimitedArc(new Point3d(175.0,285.0,0), new Point3d(325.0,135.0,0), new Point3d(175.0,135.0,0));
    LimitedLine l1= new LimitedLine(new Point3d(325.0,134.99999999999997,0), new Point3d(325.0,165.0,0));
    LimitedLine l2= new LimitedLine(new Point3d(325.0,165.0,0), new Point3d(355.0000000000001,165.0,0));
    LimitedLine l3= new LimitedLine(new Point3d(355.0,165.0000000000001,0), new Point3d(355.0,195.00000000000034,0));
    LimitedLine l4= new LimitedLine(new Point3d(354.99999999999966,195.0,0), new Point3d(132.99999999999983,195.0,0));
    LimitedLine l5= new LimitedLine(new Point3d(133.0,194.99999999999983,0), new Point3d(133.0,285.0,0));
    LimitedLine l6= new LimitedLine(new Point3d(133.00000000000003,285.0,0), new Point3d(175.0,285.0,0));
    ArrayList <LimitedElement> elementos = new ArrayList<LimitedElement>();
    
	
	@Before
	public void init()
	{
		lineA = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
	    lineB = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
	    elementos.add(arco0);
	    elementos.add(l1);
	    //elementos.add(l2);
	    //elementos.add(l3);
	    //elementos.add(l4);
	    //elementos.add(l5);
	    //elementos.add(l6);

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
	//Teste de Interseção entre Limited Elements (Dois Limited elements)
	public void intersectionElementsTest() 
	{
		/*
		 * ---------------------------------------------------------------------------------
		 * Linha - Linha
		 */
		//Ponto Final e Inicial iguais 
		LimitedLine line1 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		LimitedLine line2 = new LimitedLine(new Point3d(20,10,0), new Point3d(20,20,0));
		
		//Retas que não se tocam
		LimitedLine line3 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		LimitedLine line4 = new LimitedLine(new Point3d(10,20,0), new Point3d(20,20,0));
		
		//Retas com intersectão (vertical - horizoltal)
		LimitedLine line5 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
		LimitedLine line6 = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
		
		//Linhas com interseção (não verticais)
		LimitedLine line7 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 20, 0));
		LimitedLine line8 = new LimitedLine(new Point3d(20, 10, 0), new Point3d(10, 20, 0));
		/*
		 * ---------------------------------------------------------------------------------
		 * Arco - Linha
		 */
		//Ponto Final e inicial iguais
		LimitedArc arc1 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line9 = new LimitedLine(new Point3d(10,10,0),new Point3d(20,10,0));
		
		//Com interseção
		LimitedArc arc2 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line10 = new LimitedLine(new Point3d(30, 10, 0), new Point3d(20, 20, 0));
		
		//Com interseção (arco + linha vertical)
		LimitedArc arc3 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line11 = new LimitedLine(new Point3d(25, 0, 0), new Point3d(25, 20, 0));	
		/*
		 * ---------------------------------------------------------------------------------
		 * Arco - Arco
		 */
		//Com interseção (arco + arco), tamanho igual
		LimitedArc arc4 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedArc arc5 = new LimitedArc(new Point3d(20, 20, 0), new Point3d(30, 20, 0), new Point3d(30,20,0));
		
		//Com interseção (arco + arco)
		LimitedArc arc6 = new LimitedArc(new Point3d(75, 130, 0), new Point3d(50, 105, 0), new Point3d(50,130 , 0));
		LimitedArc arc7 = new LimitedArc(new Point3d(50, 125, 0), new Point3d(74.45, 105.19, 0), new Point3d(50, 100, 0));
		
		////Com interseção (arco + arco) tamanho diferente
		LimitedArc arc8 = new LimitedArc(new Point3d(10, 10, 0), new Point3d(30, 30, 0), new Point3d(10,30 , 0));
		LimitedArc arc9 = new LimitedArc(new Point3d(5, 70, 0), new Point3d(60, 15, 0), new Point3d(70, 60, 0));
		
		Point3d intersection = GeometricOperations.intersectionElements(line5, line6);
		System.err.println("Intersection Validated: " + intersection);
	}
	
	@Test
	//Teste de Interseção entre Limited Elements (Array de Limited Elements)
	public void intersectionElementsTest1()
	{
	    //LimitedArc arco1= new LimitedArc(new Point3d(50,75,0), new Point3d(25,50,0), new Point3d(50,50,0));
	    //LimitedLine linha1= new LimitedLine(new Point3d(50,75,0), new Point3d(80,75,0));
	    //LimitedLine linha2= new LimitedLine(new Point3d(70,60,0), new Point3d(70,90,0));
		//LimitedLine linha1= new LimitedLine(new Point3d(30,100,0), new Point3d(70,60,0));
	   // LimitedLine linha2= new LimitedLine(new Point3d(20,60,0), new Point3d(60,100,0));
	   // LimitedLine linha3= new LimitedLine(new Point3d(60,100,0), new Point3d(100,100,0));
	   // ArrayList <LimitedElement> elementos = new ArrayList<LimitedElement>();
	    //elementos1.add(arco1);
	    //elementos1.add(linha1);
	    //elementos1.add(linha2);
	    //elementos1.add(linha3);
		ArrayList<Point3d> intersecoes = GeometricOperations.intersectionElements(elementos);
		for(int i=0;i<intersecoes.size();i++)
		{
			System.out.println("intersecoes: " + intersecoes.get(i));
		}
		
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
