package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

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
		
		LimitedArc arco0 = new LimitedArc(new Point3d(400, 200.0, 0), new Point3d(350, 200, 0), Math.PI / 2);
	    
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
		points.add(new Point2D.Double(8, 160));
		points.add(new Point2D.Double(8, 320));
		points.add(new Point2D.Double(480, 320));
		points.add(new Point2D.Double(480, 40));
		points.add(new Point2D.Double(200, 40));
		points.add(new Point2D.Double(200,160));
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(points, 0, 25);
		formaOriginal = addPocketVertex.getElements();
	}
	
	@Test
	public void generateTrochoidalPathTest()
	{
		GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(elements, 20, 20);
		ArrayList<LimitedElement> movimentacoes = gen.generatePaths(gen.getPaths());
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : elements)
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
}
