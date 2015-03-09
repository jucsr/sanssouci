package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class TriangulationTest 
{
	ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
	private GeneralClosedPocket pocket = new GeneralClosedPocket();
	GeneralClosedPocketVertexAdd addPocketVertex;
	ArrayList<Point2D> points = new ArrayList<Point2D>();
	@Before
	public void init()
	{

//		elements.add(new LimitedLine(new Point3d(10,10,0),new Point3d(50,10,0)));
//		elements.add(new LimitedLine(new Point3d(50,10,0),new Point3d(50,50,0)));
//		elements.add(new LimitedLine(new Point3d(50,50,0),new Point3d(10,50,0)));
//		elements.add(new LimitedLine(new Point3d(10,50,0),new Point3d(10,10,0)));
		elements.add(new LimitedLine(new Point3d(10,10,0),new Point3d(20,10,0)));
		elements.add(new LimitedLine(new Point3d(20,10,0),new Point3d(20,20,0)));
		elements.add(new LimitedLine(new Point3d(20,20,0),new Point3d(10,20,0)));
		elements.add(new LimitedLine(new Point3d(10,20,0),new Point3d(10,10,0)));
		
		//Forma de Cachorrinho --> raio 5
//		points.add(new Point2D.Double(44.0,89.0));
//		points.add(new Point2D.Double(51.0,68.0));
//		points.add(new Point2D.Double(27.0,22.0));
//		points.add(new Point2D.Double(55.0,20.0));
//		points.add(new Point2D.Double(67.0,50.0));
//		points.add(new Point2D.Double(124.0,65.0));
//		points.add(new Point2D.Double(136.0,20.0));
//		points.add(new Point2D.Double(164.0,19.0));
//		points.add(new Point2D.Double(147.0,66.0));
//		points.add(new Point2D.Double(168.0,116.0));
//		points.add(new Point2D.Double(134.0,84.0));
//		points.add(new Point2D.Double(68.0,84.0));
//		points.add(new Point2D.Double(45.0,120.0));
//		points.add(new Point2D.Double(13.0,93.0));
		
		//Forma 2
//		points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 160));
//		points.add(new Point2D.Double(280, 160));
//		points.add(new Point2D.Double(280, 40));
//		points.add(new Point2D.Double(0, 40));
//		points.add(new Point2D.Double(0, 320));
		
		//Forma 3
		points.add(new Point2D.Double(10, 10));
		points.add(new Point2D.Double(10, 0));
		points.add(new Point2D.Double(0, 0));
		points.add(new Point2D.Double(0, 10));
		
		pocket.setPoints(points);
		pocket.setRadius(5);
		//pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
	}
	
//	@Test
//	public void getTriangules()
//	{
//		Triangulation triangulation = new Triangulation(addPocketVertex.getElements());
//		//System.out.println(triangulation.getTriangules());
//		System.out.println("Area: " + triangulation.getArea(triangulation.getTriangules()));
////		MapeadoraGeneralClosedPocket1.drawShape(elements, null);
////		for(;;);
//	}
	@Test
	public void getTriangules()
	{
		Triangulation triangulation = new Triangulation(points);
		System.out.println(triangulation.getTriangulesIndex());
		System.out.println(triangulation.getArea(triangulation.getTriangulesIndex()));
	}
}
