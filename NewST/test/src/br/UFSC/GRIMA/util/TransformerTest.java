package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;

public class TransformerTest 
{
	GeneralClosedPocket pocket = new GeneralClosedPocket();
	GeneralClosedPocketVertexAdd addPocket;
	ArrayList<ArrayList<LimitedElement>> bossVirtual = null;
	ArrayList<ArrayList<LimitedElement>> bossReal = new ArrayList<ArrayList<LimitedElement>>();
	@Before
	public void init()
	{
	    ArrayList<Point2D> points = new ArrayList<Point2D>();
	    //Forma 1
		points.add(new Point2D.Double(500, 320));
		points.add(new Point2D.Double(500, 160));
		points.add(new Point2D.Double(280, 160));
		points.add(new Point2D.Double(280, 40));
		points.add(new Point2D.Double(0, 40));
		points.add(new Point2D.Double(0, 320));
		
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
		
		//Protuberancia
		pocket.setPoints(points);
		pocket.setRadius(5);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		CircularBoss arcBoss1 = new CircularBoss("", 50, 265, pocket.Z, 50, 50, pocket.getProfundidade());
//		itsBoss.add(arcBoss1);
		pocket.setItsBoss(itsBoss);
		addPocket = new GeneralClosedPocketVertexAdd(points, pocket.Z, pocket.getRadius());
		bossVirtual = MapeadoraGeneralClosedPocket1.getAreaAlreadyDesbasted1(pocket, bossVirtual, pocket.Z, 20, 2);
		bossReal = GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z);
	}
	@Test
	public void limitedElementToPoints2DTest()
	{
		Triangulation triangulation = new Triangulation(Transformer.limitedElementToPoints2D(addPocket.getElements()));
//		Triangulation triangulation = new Triangulation(pocket.getPoints());
		double areaCavidade = triangulation.getArea();
		System.out.println("Area Cavidade: " + areaCavidade);
		triangulation.drawTriangles();
		double areaBoss = 0;
		for(ArrayList<LimitedElement> array:bossReal)
		{
			triangulation = new Triangulation(Transformer.limitedElementToPoints2D(array));
			triangulation.drawTriangles();
			areaBoss += triangulation.getArea();
		}
		System.out.println("Area Protuberancia: " + areaBoss);
		double areaDesbaste = (areaCavidade - areaBoss);
		System.out.println("Area a ser desbastada: " + areaDesbaste);
		
//		areaBoss = 0;
//		MapeadoraGeneralClosedPocket1.drawShape(addPocket.getElements(), bossVirtual);
		
//		Triangulation triangulation = null;
		for(ArrayList<LimitedElement> array:bossVirtual)
		{
			triangulation = new Triangulation(Transformer.limitedElementToPoints2D(array));
			triangulation.drawTriangles();
			System.err.println(triangulation.getTriangulesIndex());
			areaBoss += triangulation.getArea();
		}
		System.out.println("Area desbastada: " + areaBoss);
		
//		System.out.println("Area restante: " + (areaDesbaste - areaBoss));
//		MapeadoraGeneralClosedPocket1.drawShape(Transformer.ArrayPoint2DToArrayLimitedElement(Transformer.limitedElementToPoints2D(bossVirtual.get(0)), pocket.Z), null);
		for(;;);
	}
	@Test
	public void limitedElementToPoints2DTest2()
	{
		ArrayList<ArrayList<Point2D>> matrixTmp = new ArrayList<ArrayList<Point2D>>();
		for(ArrayList<LimitedElement> array:bossVirtual)
		{
			ArrayList<Point2D> pointsTmp = Transformer.limitedElementToPoints2D(array);
			matrixTmp.add(pointsTmp);
			MapeadoraGeneralClosedPocket1.drawShape(Transformer.ArrayPoint2DToArrayLimitedElement(pointsTmp, pocket.Z), null);
			System.out.println(pointsTmp);
		}
		for(;;);
	}

}
