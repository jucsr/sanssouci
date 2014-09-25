package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;

public class MapeadoraGeneralClosedPocket1Test 
{
	private GeneralClosedPocket pocket = new GeneralClosedPocket();

	@Before
	public void init()
	{
	    ArrayList<Point2D> points = new ArrayList<Point2D>();

		points.add(new Point2D.Double(500, 320));
		points.add(new Point2D.Double(500, 160));
		points.add(new Point2D.Double(280, 160));
		points.add(new Point2D.Double(280, 40));
		points.add(new Point2D.Double(0, 40));
		points.add(new Point2D.Double(0, 320));
		
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		
	}
	
	@Test
	public void getMenorMaiorDistanciaTest()
	{
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		MapeadoraGeneralClosedPocket1 mp = new MapeadoraGeneralClosedPocket1(pocket);
		double maiorMenorDistancia = mp.getMaiorMenorDistancia();
		
		System.out.println("Maior Menor Distancia: " + maiorMenorDistancia);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(addPocketVertex.getElements());
		desenhador.setVisible(true);
		for(;;);
	}
}
