package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
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
	
	LimitedLine l0= new LimitedLine(new Point3d(300.0,295.0,0),new Point3d(300.0,64.99999999999997,0));
	LimitedLine l1= new LimitedLine(new Point3d(225.0,140.0,0),new Point3d(455.0,140.0,0));
	LimitedLine l2= new LimitedLine(new Point3d(380.0,65.00000000000001,0),new Point3d(380.0,135.0,0));
	LimitedArc arco3= new LimitedArc(new Point3d(380.0,135.0,0),new Point3d(505.0,260.0,0),new Point3d(505.0,135.0,0));
	LimitedLine l4= new LimitedLine(new Point3d(505.0,260.0,0),new Point3d(675.0,260.0,0));
	LimitedLine l5= new LimitedLine(new Point3d(600.0,185.0,0),new Point3d(600.0,295.0,0));
	LimitedLine l6= new LimitedLine(new Point3d(675.0,220.0,0),new Point3d(224.99999999999997,220.0,0));
	
    ArrayList <LimitedElement> elementos = new ArrayList<LimitedElement>();
    
    ArrayList<LimitedElement> formaOriginal;
    
    
	
	@Before
	public void init()
	{
		lineA = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
	    lineB = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
//	    l7 = new LimitedLine(new Point3d(170, 150, 0), new Point3d(200, 250, 0)); //erro!!!
//	    l8 = new LimitedLine(new Point3d(200, 150, 0), new Point3d(250, 300, 0)); //erro!!
//	    l8 = new LimitedLine(new Point3d(200, 150, 0), new Point3d(200, 300, 0)); 
		elementos.add(l0);
		elementos.add(l1);
		elementos.add(l2);
		elementos.add(arco3);
	    elementos.add(l4);
	    elementos.add(l5);
	    elementos.add(l6);
	    
	    ArrayList<Point2D> points = new ArrayList<Point2D>();
	    //Forma 1
		points.add(new Point2D.Double(8, 160));
		points.add(new Point2D.Double(8, 320));
		points.add(new Point2D.Double(480, 320));
		points.add(new Point2D.Double(700, 500));
		points.add(new Point2D.Double(700, 160));
		points.add(new Point2D.Double(480, 160));
		points.add(new Point2D.Double(480, 40));
		points.add(new Point2D.Double(200, 40));
		points.add(new Point2D.Double(200,160));
		//Forma 2
//	    points.add(new Point2D.Double(700, 320));
//		points.add(new Point2D.Double(700, 160));
//		points.add(new Point2D.Double(480, 160));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200, 320));
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(points, 0, 25);
		formaOriginal = addPocketVertex.getElements();

	}
	@Test
	public void testeDistance3D()
	{
		System.out.println("*********************************************************");
		System.out.println("Distance\nfrom  " + p1_3D + "\nto " + p2_3D + " = " + GeometricOperations.distance(p1_3D, p2_3D));		
	}
	
	@Test
	public void criarLacosTest()
	{
		LimitedArc arco0= new LimitedArc(new Point3d(266.65151389911676,219.99999999999997,0),new Point3d(299.89995996796796,140.0,0),new Point3d(175.0,135.0,0));
		LimitedLine l1= new LimitedLine(new Point3d(299.89995996796796,140.0,0),new Point3d(380.10004003203204,140.0,0));
		LimitedArc arco2= new LimitedArc(new Point3d(380.10004003203204,140.0,0),new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(505.0,135.0,0));
		LimitedLine l3= new LimitedLine(new Point3d(564.5852267061321,260.0,0),new Point3d(600.0,260.0,0));
		LimitedLine l4= new LimitedLine(new Point3d(600.0,260.0,0),new Point3d(600.0,288.9757236040737,0));
		LimitedLine l5= new LimitedLine(new Point3d(600.0,288.97572360407366,0),new Point3d(564.5852267061321,259.99999999999994,0));
		LimitedLine l6= new LimitedLine(new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(266.6515138991168,219.99999999999997,0));
		ArrayList<LimitedElement> elementos = new ArrayList<LimitedElement>();
		elementos.add(arco0);
		elementos.add(l1);
		elementos.add(arco2);
		elementos.add(l3);
		elementos.add(l4);
		elementos.add(l5);
		elementos.add(l6);
		System.err.println("TAMANHO = " + GeometricOperations.criarLacosM(elementos).size());
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
	//Teste de Interse��o entre Limited Elements (Dois Limited elements)
	public void intersectionElementsTest() 
	{
		/*
		 * ---------------------------------------------------------------------------------
		 * Linha - Linha
		 */
		//Ponto Final e Inicial iguais 
		LimitedLine line1 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		LimitedLine line2 = new LimitedLine(new Point3d(20,10,0), new Point3d(20,20,0));
		
		//Retas que n�o se tocam
		LimitedLine line3 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		LimitedLine line4 = new LimitedLine(new Point3d(10,20,0), new Point3d(20,20,0));
		
		//Retas com intersect�o (vertical - horizoltal)
		LimitedLine line5 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 10, 0));
		LimitedLine line6 = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
		
		LimitedLine l9 = new LimitedLine(new Point3d(233, 159, 0), new Point3d(233, 309, 0));
	    LimitedLine l4= new LimitedLine(new Point3d(354.99999999999966,195.0,0), new Point3d(132.99999999999983,195.0,0));
		
		
		//Linhas com interse��o (n�o verticais)
		LimitedLine line7 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(20, 20, 0));
		LimitedLine line8 = new LimitedLine(new Point3d(20, 10, 0), new Point3d(10, 20, 0));
		
		//
		LimitedLine l3= new LimitedLine(new Point3d(355.0,165.0000000000001,0), new Point3d(355.0,195.00000000000034,0));
		LimitedLine l6= new LimitedLine(new Point3d(133.00000000000003,285.0,0), new Point3d(175.0,285.0,0));
		/*
		 * ---------------------------------------------------------------------------------
		 * Arco - Linha
		 */
		//Ponto Final e inicial iguais
		LimitedArc arc1 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line9 = new LimitedLine(new Point3d(10,10,0),new Point3d(20,10,0));
		
		//Com interse��o
		LimitedArc arc2 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line10 = new LimitedLine(new Point3d(30, 10, 0), new Point3d(20, 20, 0));
		
		//Com interse��o (arco + linha vertical)
		LimitedArc arc3 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedLine line11 = new LimitedLine(new Point3d(25, 0, 0), new Point3d(25, 20, 0));	
		/*
		 * ---------------------------------------------------------------------------------
		 * Arco - Arco
		 */
		//Com interse��o (arco + arco), tamanho igual
		LimitedArc arc4 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
		LimitedArc arc5 = new LimitedArc(new Point3d(20, 20, 0), new Point3d(30, 20, 0), new Point3d(30,20,0));
		
		//Com interse��o (arco + arco)
		LimitedArc arc6 = new LimitedArc(new Point3d(75, 130, 0), new Point3d(50, 105, 0), new Point3d(50,130 , 0));
		LimitedArc arc7 = new LimitedArc(new Point3d(50, 125, 0), new Point3d(74.45, 105.19, 0), new Point3d(50, 100, 0));
		
		////Com interse��o (arco + arco) tamanho diferente
		LimitedArc arc8 = new LimitedArc(new Point3d(10, 10, 0), new Point3d(30, 30, 0), new Point3d(10,30 , 0));
		LimitedArc arc9 = new LimitedArc(new Point3d(5, 70, 0), new Point3d(60, 15, 0), new Point3d(70, 60, 0));
		
		ArrayList<Point3d> intersection = GeometricOperations.intersectionElements(arc4, arc5);
		System.err.println("Intersection Validated: " + intersection);
	}
	
	@Test
	//Teste de Interse��o entre Limited Elements (Array de Limited Elements)
	public void intersectionElementsTest1()
	{
		ArrayList <LimitedElement> elementos1 = new ArrayList<LimitedElement>();
		/*
		 * ---------------------------------------------------------------------------------
		 * Teste 1
		 */
	    //LimitedArc arco1= new LimitedArc(new Point3d(50,75,0), new Point3d(25,50,0), new Point3d(50,50,0));
	    //LimitedLine linha1= new LimitedLine(new Point3d(80,75,0), new Point3d(50,75,0));
	    //LimitedLine linha2= new LimitedLine(new Point3d(70,60,0), new Point3d(70,90,0));
	    /*
		 * ---------------------------------------------------------------------------------
		 * Teste 2
		 */
		//LimitedLine linha1= new LimitedLine(new Point3d(30,100,0), new Point3d(70,60,0));
	    //LimitedLine linha2= new LimitedLine(new Point3d(20,60,0), new Point3d(60,100,0));
	    //LimitedLine linha3= new LimitedLine(new Point3d(60,100,0), new Point3d(100,100,0));
	    //ArrayList <LimitedElement> elementos1 = new ArrayList<LimitedElement>();
	    //elementos1.add(arco1);
	    //elementos1.add(linha1);
	    //elementos1.add(linha2);
	    //elementos1.add(linha3);
		//ArrayList<Point3d> intersecoes = GeometricOperations.intersectionElements(elementos1);
		
	    /*
		 * ---------------------------------------------------------------------------------
		 * Teste 3
		 */
		//Forma 2
//	    LimitedArc arco0= new LimitedArc(new Point3d(299.89995996796796,140.0,0),new Point3d(300.0,135.0,0),new Point3d(175.0,135.0,0));
//	    LimitedArc arco1= new LimitedArc(new Point3d(175.0,260.0,0),new Point3d(266.65151389911676,219.99999999999997,0),new Point3d(175.0,135.0,0));
//	    LimitedArc arco2= new LimitedArc(new Point3d(266.65151389911676,219.99999999999997,0),new Point3d(299.89995996796796,140.0,0),new Point3d(175.0,135.0,0));
//	    LimitedLine l3= new LimitedLine(new Point3d(300.0,134.99999999999997,0),new Point3d(300.0,64.99999999999997,0));
//	    LimitedLine l4= new LimitedLine(new Point3d(225.0,140.0,0),new Point3d(299.89995996796796,140.0,0));
//	    LimitedLine l5= new LimitedLine(new Point3d(299.89995996796796,140.0,0),new Point3d(380.10004003203204,140.0,0));
//	    LimitedLine l6= new LimitedLine(new Point3d(380.10004003203204,140.0,0),new Point3d(455.0,140.0,0));
//	    LimitedLine l7= new LimitedLine(new Point3d(380.0,65.00000000000001,0),new Point3d(380.0,135.0,0));
//	    LimitedArc arco8= new LimitedArc(new Point3d(380.0,135.0,0),new Point3d(380.10004003203204,140.0,0),new Point3d(505.0,135.0,0));
//	    LimitedArc arco9= new LimitedArc(new Point3d(380.10004003203204,140.0,0),new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(505.0,135.0,0));
//	    LimitedArc arco10= new LimitedArc(new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(505.0,260.0,0),new Point3d(505.0,135.0,0));
//	    LimitedLine l11= new LimitedLine(new Point3d(600.0,260.0,0),new Point3d(675.0,260.0,0));
//	    LimitedLine l12= new LimitedLine(new Point3d(505.0,260.0,0),new Point3d(564.5852267061321,260.0,0));
//	    LimitedLine l13= new LimitedLine(new Point3d(564.5852267061321,260.0,0),new Point3d(600.0,260.0,0));
//	    LimitedLine l14= new LimitedLine(new Point3d(600.0,185.0,0),new Point3d(600.0,260.0,0));
//	    LimitedLine l15= new LimitedLine(new Point3d(600.0,260.0,0),new Point3d(600.0,288.9757236040737,0));
//	    LimitedLine l16= new LimitedLine(new Point3d(600.0,288.9757236040737,0),new Point3d(600.0,447.2439309010185,0));
//	    LimitedLine l17= new LimitedLine(new Point3d(564.5852267061321,259.99999999999994,0),new Point3d(550.230639327847,248.2553375995849,0));
//	    LimitedLine l18= new LimitedLine(new Point3d(722.4928342692947,389.19713346076935,0),new Point3d(600.0,288.97572360407366,0));
//	    LimitedLine l19= new LimitedLine(new Point3d(600.0,288.97572360407366,0),new Point3d(564.5852267061321,259.99999999999994,0));
//	    LimitedArc arco20= new LimitedArc(new Point3d(550.2306393278471,248.255337599585,0),new Point3d(471.0759155456894,220.0,0),new Point3d(471.07591554568916,345.0,0));
//	    LimitedLine l21= new LimitedLine(new Point3d(266.6515138991168,219.99999999999997,0),new Point3d(32.99999999999998,220.0,0));
//	    LimitedLine l22= new LimitedLine(new Point3d(471.0759155456892,219.99999999999994,0),new Point3d(413.34848610088295,219.99999999999994,0));
//	    LimitedLine l23= new LimitedLine(new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(266.6515138991168,219.99999999999997,0));
//	    LimitedLine l24= new LimitedLine(new Point3d(108.0,220.0,0),new Point3d(108.0,184.99999999999997,0));
//	    LimitedLine l25= new LimitedLine(new Point3d(108.0,295.0,0),new Point3d(108.0,260.0,0));
//	    LimitedLine l26= new LimitedLine(new Point3d(108.0,260.0,0),new Point3d(108.0,220.0,0));
//	    LimitedLine l27= new LimitedLine(new Point3d(33.0,260.0,0),new Point3d(108.0,260.0,0));
//	    LimitedLine l28= new LimitedLine(new Point3d(108.0,260.0,0),new Point3d(175.0,260.0,0));
//		elementos1.add(arco0);
//		elementos1.add(arco1);
//		elementos1.add(arco2);
//		elementos1.add(l3);
//	    elementos1.add(l4);
//	    elementos1.add(l5);
//	    elementos1.add(l6);
//	    elementos1.add(l7);
//	    elementos1.add(arco8);
//	    elementos1.add(arco9);
//	    elementos1.add(arco10);
//	    elementos1.add(l11);
//	    elementos1.add(l12);
//	    elementos1.add(l13);
//	    elementos1.add(l14);
//	    elementos1.add(l15);
//	    elementos1.add(l16);
//	    elementos1.add(l17);
//	    elementos1.add(l18);
//	    elementos1.add(l19);
//	    elementos1.add(arco20);
//	    elementos1.add(l21);
//	    elementos1.add(l22);
//	    elementos1.add(l23);
//	    elementos1.add(l24);
//	    elementos1.add(l25);
//	    elementos1.add(l26);
//	    elementos1.add(l27);
//	    elementos1.add(l28);
		//Forma 2
//		LimitedLine l0= new LimitedLine(new Point3d(340.0,294.99999999999994,0),new Point3d(340.0,64.99999999999997,0));
//		LimitedLine l1= new LimitedLine(new Point3d(225.0,180.0,0),new Point3d(455.0,180.0,0));
//		LimitedLine l2= new LimitedLine(new Point3d(340.0,65.00000000000001,0),new Point3d(340.0,135.00000000000003,0));
//		LimitedArc arco3= new LimitedArc(new Point3d(340.0,135.0,0),new Point3d(505.0,300.0,0),new Point3d(505.0,135.0,0));
//		LimitedLine l4= new LimitedLine(new Point3d(505.0,300.0,0),new Point3d(675.0,300.0,0));
//		LimitedLine l5= new LimitedLine(new Point3d(560.0,185.00000000000003,0),new Point3d(560.0,295.0,0));
//		LimitedLine l6= new LimitedLine(new Point3d(675.0,180.0,0),new Point3d(224.99999999999997,180.0,0));
//		elementos1.add(l0);
//		elementos1.add(l1);
//		elementos1.add(l2);
//		elementos1.add(arco3);
//	    elementos1.add(l4);
//	    elementos1.add(l5);
//	    elementos1.add(l6);
	    LimitedArc arco0= new LimitedArc(new Point3d(277.469507659596,214.99999999999997,0),new Point3d(304.6148139681572,145.0,0),new Point3d(175.0,135.0,0));
	    LimitedLine l1= new LimitedLine(new Point3d(304.6148139681572,145.0,0),new Point3d(375.3851860318428,145.0,0));
	    LimitedArc arco2= new LimitedArc(new Point3d(375.3851860318428,145.0,0),new Point3d(402.530492340404,214.99999999999994,0),new Point3d(505.0,135.0,0));
	    LimitedLine l3= new LimitedLine(new Point3d(578.5922658192167,265.0,0),new Point3d(595.0,265.0,0));
	    LimitedLine l4= new LimitedLine(new Point3d(595.0,265.0,0),new Point3d(595.0,278.4245097842773,0));
	    LimitedLine l5= new LimitedLine(new Point3d(595.0,278.42450978427723,0),new Point3d(578.5922658192167,265.0,0));
	    LimitedLine l6= new LimitedLine(new Point3d(402.5304923404038,214.99999999999994,0),new Point3d(277.469507659596,214.99999999999997,0));
		elementos1.add(arco0);
		elementos1.add(l1);
		elementos1.add(arco2);
		elementos1.add(l3);
	    elementos1.add(l4);
	    elementos1.add(l5);
	    elementos1.add(l6);
		  /*
	       * ---------------------------------------------------------------------------------
		   * Teste 4 (Dupla intersecao)
		   */		
//		LimitedArc arco0= new LimitedArc(new Point3d(122.6,135.00,0), new Point3d(77.34,160.95,0), new Point3d(92.6,133.57,0));
//		LimitedArc arco1= new LimitedArc(new Point3d(83.2,169.37,0), new Point3d(128.2,143.39,0), new Point3d(114.47,169.10,0));
//		LimitedArc arco2 = new LimitedArc(new Point3d (20,10,0), new Point3d(30,20,0),new Point3d(20,20,0));
//		LimitedArc arco3 = new LimitedArc(new Point3d(20, 20, 0), new Point3d(30, 20, 0), new Point3d(30,20,0));
		//LimitedArc arco4 = new LimitedArc(new Point3d (92.6,133.57,0), new Point3d(122.6,135.00,0), 90, 1 );
//		LimitedLine l1= new LimitedLine(new Point3d(135.10,135.59,0),new Point3d(64.84,174.95,0));
		//elementos1.add(arco2);
		//elementos1.add(arco4);
		//elementos1.add(l1);
		
	    ArrayList<Point3d> intersecoes = GeometricOperations.intersectionElements(elementos1);
		//ArrayList<Point3d> intersecoes = GeometricOperations.intersectionElements(elementos);
		for(int i=0;i<intersecoes.size();i++)
		{
			System.out.println("intersecoes: " + intersecoes.get(i));
		}
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : elementos1)
		{
			all.add(tmp);
		}
		for(LimitedElement tmp : formaOriginal)
		{
			all.add(tmp);
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
		
	}
	@Test
	public void parallelPath1Test()
	{
		//esta bugando no offset 140
//		bugado no offset 90
		ArrayList<LimitedElement> elements = GeometricOperations.parallelPath1(formaOriginal, 100).get(0);
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : elements)
		{
			all.add(tmp);
		}
		for(LimitedElement tmp : formaOriginal)
		{
			all.add(tmp);
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void validarPathTest()
	{
		ArrayList<LimitedElement> elementosIntermediarios = GeometricOperations.validarPath(elementos,formaOriginal,100).get(0);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(elementosIntermediarios);
		desenhador.setVisible(true);
		for(;;);
	}
	
	@Test
	public void minumumDistanceTest()
	{	
//	    LimitedArc arco2= new LimitedArc(new Point3d(266.65151389911676,219.99999999999997,0),new Point3d(299.89995996796796,140.0,0),new Point3d(175.0,135.0,0));
	    LimitedArc arco9= new LimitedArc(new Point3d(380.10004003203204,140.0,0),new Point3d(413.34848610088295,219.99999999999994,0),new Point3d(505.0,135.0,0));
	    System.out.println("Delta Angle: " + arco9.getDeltaAngle());
	    
		System.out.println("MINIMUM = " + GeometricOperations.minimumDistance(formaOriginal, arco9));
		
		formaOriginal.add(arco9);
		
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(formaOriginal);
		desenhador.setVisible(true);
		for(;;);
	}
	
	@Test
	public void belongsTest()
	{
		//LimitedLine l1 = new LimitedLine(new Point3d(10,10,0), new Point3d(20,10,0));
		//Point3d p1 = new Point3d(25,10,0);
		
		LimitedLine l1 = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
		Point3d p1 = new Point3d(15,13,0);
		System.out.println("belongs: " + GeometricOperations.belongs(l1, p1));
		
		double x = Math.sqrt(-1);
		if(((Double)x).isNaN())
		{
			System.out.println("NaNANANANA");
		}
	}
	
	@Test
	public void LimitedArcTest()
	{	
		//construtor com deltaAngle
		LimitedArc arc = new LimitedArc(new Point3d(505.0,135.0,0),new Point3d(380.10004003203204,140.0,0), -90);
		formaOriginal.add(arc);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(formaOriginal);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void determinarMovimentacaoGenCavTest()
	{
		
		class painelTest extends JPanel
		{
			double zoom = 5;
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
				
				desenharGrade(g2d);
				desenharLinha(lineA, g2d);
				desenharLinha(lineB, g2d);
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
		JFrame frame = new JFrame("Painel");
		painelTest painel = new painelTest();
		frame.setSize(700, 400);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
	}
}
