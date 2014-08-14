package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrochoidalMovement1Test 
{
	private ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
	private ArrayList<LimitedElement> formaOriginal;
	
	@Before
	public void Project(){
		
		Point3d point1 = new Point3d(50, 50, 0); 
		Point3d point2 = new Point3d(70, 50, 0);
		LimitedLine linha1 = new LimitedLine(point1, point2);
		LimitedLine linha2 = new LimitedLine(linha1.getFinalPoint(), new Point3d(300, 300, 0));
		
//	    LimitedArc arco0= new LimitedArc(new Point3d(325.0,135.0,0), new Point3d(175.0,285.0,0), new Point3d(175.0,135.0,0));
//	    LimitedArc arco0= new LimitedArc(new Point3d(400, 200.0,0), new Point3d(350.0,250,0), new Point3d(350,200,0));
//	    LimitedArc arco0= new LimitedArc(new Point3d(350.0,250,0), new Point3d(400, 200.0,0), new Point3d(350,200,0));
	 
//		LimitedArc arco0= new LimitedArc(new Point3d(400, 200.0,0), new Point3d(350,200,0), Math.PI / 2);
		LimitedLine l1= new LimitedLine(new Point3d(325.0,134.99999999999997,0), new Point3d(325.0,165.0,0));
	    LimitedLine l2= new LimitedLine(new Point3d(325.0,165.0,0), new Point3d(355.0000000000001,165.0,0));
	    LimitedLine l3= new LimitedLine(new Point3d(355.0,165.0000000000001,0), new Point3d(355.0,195.00000000000034,0));
	    LimitedLine l4= new LimitedLine(new Point3d(354.99999999999966,195.0,0), new Point3d(132.99999999999983,195.0,0));
	    LimitedLine l5= new LimitedLine(new Point3d(133.0,194.99999999999983,0), new Point3d(133.0,285.0,0));
	    LimitedLine l6= new LimitedLine(new Point3d(133.00000000000003,285.0,0), new Point3d(175.0,285.0,0));
	    LimitedLine l7= new LimitedLine(new Point3d(133.00000000000003,285.0,0), new Point3d(175.0,285.0,0));
	    LimitedLine l8= new LimitedLine(new Point3d(133.00000000000003,285.0,0), new Point3d(175.0,285.0,0));
		
//		LimitedArc arco0 = new LimitedArc(new Point3d(400, 200.0, 0), new Point3d(350, 200, 0), Math.PI /2);
		LimitedArc arco0 = new LimitedArc(new Point3d(400, 200.0, 0), new Point3d(400, 150, 0), Math.PI /2);
	    
//		elements.add(l1);
//		elements.add(l2);
//		elements.add(l3);
//		elements.add(l4);
//		elements.add(l5);
//		elements.add(l6);
//		elements.add(l7);
//		elements.add(l8);
		elements.add(arco0);
		
		ArrayList<Point2D> points = new ArrayList<Point2D>();
//		points.add(new Point2D.Double(8, 160));
//		points.add(new Point2D.Double(8, 320));
//		points.add(new Point2D.Double(480, 320));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200,160));
		
		points.add(new Point2D.Double(700, 320));
		points.add(new Point2D.Double(700, 160));
		points.add(new Point2D.Double(480, 160));
		points.add(new Point2D.Double(480, 40));
		points.add(new Point2D.Double(200, 40));
		points.add(new Point2D.Double(200, 320));
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(points, 0, 25);
		formaOriginal = addPocketVertex.getElements();
	}
	
	@Test
	public void generateTrochoidalPathTest()
	{
		GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(formaOriginal, 20, 25);
		ArrayList<LimitedElement> movimentacoes = gen.generatePaths(gen.getPaths());
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : formaOriginal)
		{
			all.add(tmp);
		}
		for(LimitedElement tmp : movimentacoes)
		{
			all.add(tmp);
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void generateMultipleParallelAndTrochoidalMovementTest()
	{
		double trochoidalRadius = 5;
		double trochoidalPercent = 1.5;
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
		
		GeneralClosedPocket pocket = new GeneralClosedPocket();
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		//Circular Boss
		CircularBoss arcBoss = new CircularBoss("", 350, 200, pocket.Z, 30, 15, pocket.getProfundidade());
		itsBoss.add(arcBoss);
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(400, 200, pocket.Z);
		rectBoss.setRadius(10);
		itsBoss.add(rectBoss);
		//General Boss
		GeneralProfileBoss genBoss = new GeneralProfileBoss();
		genBoss.setRadius(10);
		ArrayList<Point2D> vertexPoints = new ArrayList<Point2D>();
		vertexPoints.add(new Point2D.Double(150, 300));
		vertexPoints.add(new Point2D.Double(300, 300));
		vertexPoints.add(new Point2D.Double(300, 250));
		vertexPoints.add(new Point2D.Double(200, 250));
		vertexPoints.add(new Point2D.Double(200, 180));
//		vertexPoints.add(new Point2D.Double(150, 180));
		vertexPoints.add(new Point2D.Double(50, 180));
		vertexPoints.add(new Point2D.Double(50, 240));
		vertexPoints.add(new Point2D.Double(150, 240));
		genBoss.setVertexPoints(vertexPoints);
		itsBoss.add(genBoss);
		
		pocket.setItsBoss(itsBoss);
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		formaOriginal = addPocketVertex.getElements();
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = GeometricOperations.multipleParallelPath(pocket, trochoidalRadius, trochoidalPercent) ;
		ArrayList<LimitedElement> pathsVector = new ArrayList<LimitedElement>();
		for(int i = 0; i < multiplePath.size(); i++)
		{
			for(int j = 0; j < multiplePath.get(i).size(); j++)
			{
				GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(multiplePath.get(i).get(j), trochoidalRadius, 10);
				ArrayList<LimitedElement> movimentacoes = gen.generatePaths(gen.getPaths());
				for(int k = 0; k < movimentacoes.size(); k ++)
				{
					pathsVector.add(movimentacoes.get(k));
				}
//				for(int k = 0; k < multiplePath.get(i).get(j).size(); k++)
//				{
//					if(multiplePath.get(i).get(j).get(k) != null)
//					{
//						if(multiplePath.get(i).get(j).get(k).isLimitedLine())
//						System.err.println("line = "+ ((LimitedLine)multiplePath.get(i).get(j).get(k)).getInitialPoint() + ((LimitedLine)multiplePath.get(i).get(j).get(k)).getFinalPoint());
//					}
//				}
			}
		}
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : formaOriginal)
		{
			all.add(tmp);
		}
		for(LimitedElement tmp : pathsVector)
		{
			all.add(tmp);
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
}
