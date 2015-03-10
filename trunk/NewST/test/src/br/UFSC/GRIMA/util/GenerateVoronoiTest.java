package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class GenerateVoronoiTest 
{
	GeneralClosedPocket pocket = new GeneralClosedPocket();
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
//		points.add(new Point2D.Double(100, 64));
//		points.add(new Point2D.Double(100, 0));
//		points.add(new Point2D.Double(0, 0));
//		points.add(new Point2D.Double(0, 64));
		
		//Forma de Cachorrinho --> raio 5
		points.add(new Point2D.Double(44.0,89.0));
		points.add(new Point2D.Double(51.0,68.0));
		points.add(new Point2D.Double(27.0,22.0));
		points.add(new Point2D.Double(55.0,20.0));
		points.add(new Point2D.Double(67.0,50.0));
		points.add(new Point2D.Double(124.0,65.0));
		points.add(new Point2D.Double(136.0,20.0));
		points.add(new Point2D.Double(164.0,19.0));
		points.add(new Point2D.Double(147.0,66.0));
		points.add(new Point2D.Double(168.0,116.0));
		points.add(new Point2D.Double(134.0,84.0));
		points.add(new Point2D.Double(68.0,84.0));
		points.add(new Point2D.Double(45.0,120.0));
		points.add(new Point2D.Double(13.0,93.0));
		
		//Protuberancia
		pocket.setPoints(points);
		pocket.setRadius(5);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		CircularBoss arcBoss1 = new CircularBoss("", 200, 150, pocket.Z, 60, 60, pocket.getProfundidade());
		CircularBoss arcBoss2 = new CircularBoss("", 100, 200, pocket.Z, 40, 40, pocket.getProfundidade());
		CircularBoss arcBoss3 = new CircularBoss("", 39, 103, pocket.Z, 4, 4, pocket.getProfundidade());
		CircularBoss arcBoss4 = new CircularBoss("", 39, 40, pocket.Z, 4, 4, pocket.getProfundidade());


//		itsBoss.add(arcBoss1);
//		itsBoss.add(arcBoss2);
//		itsBoss.add(arcBoss4);

		
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(200, 200, pocket.Z);
		rectBoss.setRadius(10);
//		itsBoss.add(rectBoss);
		pocket.setItsBoss(itsBoss);
	}
	
	@Test
	public void getVoronoiTest()
	{
		GenerateVoronoiArray voronoi = new GenerateVoronoiArray(pocket);
//		ConsolePrinter.showPointsToMatLabTest(voronoi.getVornoiPoints());
		MapeadoraGeneralClosedPocket1.drawShapeAndPoints(voronoi.getPocketElements(), voronoi.getBossElements(), voronoi.getVornoiPoints());
//		MapeadoraGeneralClosedPocket1.drawShapeAndPoints(voronoi.getPocketElements(), voronoi.getBossElements(), MapeadoraGeneralClosedPocket.getPontosPeriferiaGeneral(pocket.getPoints(), pocket.Z, pocket.getRadius()));
		for(;;);
	}

}
