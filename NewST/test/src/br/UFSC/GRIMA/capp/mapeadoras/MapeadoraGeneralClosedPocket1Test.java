package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

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
		points.add(new Point2D.Double(500, 40));
		points.add(new Point2D.Double(0, 40));
		points.add(new Point2D.Double(0, 320));
		
		//Protuberancia
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		CircularBoss arcBoss = new CircularBoss("", 250, 140, pocket.Z, 30, 30, pocket.getProfundidade());
		itsBoss.add(arcBoss);
		pocket.setItsBoss(itsBoss);
	}
	
	@Test
	public void getMenorMaiorDistanciaTest()
	{
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		MapeadoraGeneralClosedPocket1 mp = new MapeadoraGeneralClosedPocket1(pocket);
		double maiorMenorDistancia = mp.getMaiorMenorDistancia();
		Point3d point = new Point3d(100,100,0);
		LimitedLine l = new LimitedLine(point,point);
		for(LimitedElement elementTmp:addPocketVertex.getElements())
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
