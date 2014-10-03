package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class MapeadoraGeneralClosedPocket1Test 
{
	private GeneralClosedPocket pocket = new GeneralClosedPocket();

	@Before
	public void init()
	{
	    ArrayList<Point2D> points = new ArrayList<Point2D>();
	    //Forma 1
//		points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 160));
//		points.add(new Point2D.Double(280, 160));
//		points.add(new Point2D.Double(280, 40));
//		points.add(new Point2D.Double(0, 40));
//		points.add(new Point2D.Double(0, 320));
		
		//Forma 2
		points.add(new Point2D.Double(500, 320));
		points.add(new Point2D.Double(500, 0));
		points.add(new Point2D.Double(0, 0));
		points.add(new Point2D.Double(0, 320));
		
		//Protuberancia
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		CircularBoss arcBoss = new CircularBoss("", 50, 50, pocket.Z, 60, 60, pocket.getProfundidade());
//		itsBoss.add(arcBoss);
		
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(200, 200, pocket.Z);
		rectBoss.setRadius(10);
		itsBoss.add(rectBoss);
		pocket.setItsBoss(itsBoss);
	}
	
	@Test
	public void getMenorMaiorDistanciaTest()
	{
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		MapeadoraGeneralClosedPocket1 mp = new MapeadoraGeneralClosedPocket1(pocket);
		double maiorMenorDistancia = mp.getMaiorMenorDistancia(pocket);
		Point3d point = new Point3d(100,100,0);
		LimitedLine l = new LimitedLine(point,point);
		for(LimitedElement elementTmp:addPocketVertex.getElements())
		{
			all.add(elementTmp);
		}
		for(LimitedElement elementTmp:GeometricOperations.tranformeBossToLimitedElement(pocket.getItsBoss(), pocket.Z))
		{
			all.add(elementTmp);
		}
//		all.add(l);
		System.out.println("Maior Menor Distancia: " + maiorMenorDistancia);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
}
