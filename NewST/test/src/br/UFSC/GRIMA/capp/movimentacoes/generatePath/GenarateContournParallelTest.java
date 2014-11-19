package br.UFSC.GRIMA.capp.movimentacoes.generatePath;

import java.awt.Point;
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

public class GenarateContournParallelTest
{
    ArrayList <LimitedElement> elementos = new ArrayList<LimitedElement>();
	GeneralClosedPocket pocket = new GeneralClosedPocket();
    ArrayList<LimitedElement> formaOriginal = new ArrayList<LimitedElement>();
    
	@Before
	public void init()
	{
	    
	    ArrayList<Point2D> points = new ArrayList<Point2D>();
	    //Forma 1
//		points.add(new Point2D.Double(8, 160));
////		points.add(new Point2D.Double(8, 500)); // erro
//		points.add(new Point2D.Double(8, 500));
////		points.add(new Point2D.Double(480, 500));
//		points.add(new Point2D.Double(480, 320));
////		points.add(new Point2D.Double(480, 500));
//		points.add(new Point2D.Double(700, 500));
//		points.add(new Point2D.Double(700, 160));
//		points.add(new Point2D.Double(480, 160));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200,160));
		//Forma 2
//	    points.add(new Point2D.Double(700, 320));
//		points.add(new Point2D.Double(700, 160));
//		points.add(new Point2D.Double(480, 160));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200, 320));
	    
//	    points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 160));
//		points.add(new Point2D.Double(280, 160));
//		points.add(new Point2D.Double(280, 40));
//		points.add(new Point2D.Double(0, 40));
//		points.add(new Point2D.Double(0, 320));
		//Forma 4
//		points.add(new Point2D.Double(10, 10));
//		points.add(new Point2D.Double(200, 10));
//		points.add(new Point2D.Double(200, 100));
//		points.add(new Point2D.Double(350, 100));
//		points.add(new Point2D.Double(350, 10));
//		points.add(new Point2D.Double(500, 10));
//		points.add(new Point2D.Double(500, 100));
//		points.add(new Point2D.Double(400, 100));
//		points.add(new Point2D.Double(400, 200));
//		points.add(new Point2D.Double(180,200));
//		points.add(new Point2D.Double(180, 100));
//		points.add(new Point2D.Double(10, 100));
		
		//Forma de Cachorrinho
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
		
		//Forma 5
//		points.add(new Point2D.Double(29.0,93.0));
//		points.add(new Point2D.Double(30.0,34.0));
//		points.add(new Point2D.Double(123.0,34.0));
//		points.add(new Point2D.Double(123.0,60.0));
//		points.add(new Point2D.Double(164.0,60.0));
//		points.add(new Point2D.Double(164.0,96.0));
		
		pocket.setPoints(points);
		pocket.setRadius(5);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		//Circular Boss
//		CircularBoss arcBoss = new CircularBoss("", 350, 200, pocket.Z, 30, 15, pocket.getProfundidade());
//		CircularBoss arcBoss = new CircularBoss("", 290, 150, pocket.Z, 30, 30, pocket.getProfundidade());
//		CircularBoss arcBoss = new CircularBoss("", 320, 230, pocket.Z, 30, 30, pocket.getProfundidade());
//		CircularBoss arcBoss = new CircularBoss("", 200, 150, pocket.Z, 60, 60, pocket.getProfundidade());
		CircularBoss arcBoss1 = new CircularBoss("", 200, 150, pocket.Z, 60, 60, pocket.getProfundidade());
		CircularBoss arcBoss2 = new CircularBoss("", 100, 200, pocket.Z, 40, 40, pocket.getProfundidade());
		CircularBoss arcBoss3 = new CircularBoss("", 39, 103, pocket.Z, 4, 4, pocket.getProfundidade());
//		LimitedArc arco0= new LimitedArc(new Point3d(39.0,103.0, -1.6),new Point3d(43.0,103.0, -1.6),-6.283185307179586);
//		itsBoss.add(arcBoss1);
		itsBoss.add(arcBoss3);

		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(150, 180, pocket.Z);
		rectBoss.setRadius(10);
//		itsBoss.add(rectBoss);
		//General Boss
		GeneralProfileBoss genBoss = new GeneralProfileBoss();
		genBoss.setRadius(10);
		ArrayList<Point2D> vertexPoints = new ArrayList<Point2D>();
		vertexPoints.add(new Point2D.Double(150, 300));
		vertexPoints.add(new Point2D.Double(300, 300));
		vertexPoints.add(new Point2D.Double(300, 250));
		vertexPoints.add(new Point2D.Double(200, 250));
		vertexPoints.add(new Point2D.Double(200, 180));
//		vertexPoints.add(new Point2D.Double(150, 230)); // ------- //
		vertexPoints.add(new Point2D.Double(50, 180));
		vertexPoints.add(new Point2D.Double(50, 240));
		vertexPoints.add(new Point2D.Double(150, 240));
		genBoss.setVertexPoints(vertexPoints);
//		itsBoss.add(genBoss);
		
		pocket.setItsBoss(itsBoss);
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		formaOriginal = addPocketVertex.getElements();

		for(int i = 0; i < pocket.getItsBoss().size(); i++)
		{
			if(pocket.getItsBoss().get(i).getClass() == RectangularBoss.class)
			{
				RectangularBoss tmp = (RectangularBoss)pocket.getItsBoss().get(i);
				//Tamanho em x
				double l = tmp.getL1();
				//Tamanho em y
				double c = tmp.getL2();
				Point3d position = new Point3d(tmp.X,tmp.Y,tmp.Z);
				LimitedLine l1 = new LimitedLine(GeometricOperations.pointPlusEscalar(position, "x", (l - tmp.getRadius())), GeometricOperations.pointPlusEscalar(position, "x", tmp.getRadius()));
				LimitedArc a1 = new LimitedArc(GeometricOperations.pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()), l1.getFinalPoint(), -Math.PI / 2);
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(), GeometricOperations.pointPlusEscalar(a1.getFinalPoint(), "y", (c - 2 * tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(GeometricOperations.pointPlusEscalar(l2.getFinalPoint(), "x", tmp.getRadius()), l2.getFinalPoint(), -Math.PI / 2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),GeometricOperations.pointPlusEscalar(a2.getFinalPoint(), "x", (l - 2 * tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(GeometricOperations.pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()), l3.getFinalPoint(), -Math.PI / 2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(), GeometricOperations.pointPlusEscalar(a3.getFinalPoint(), "y", -(c - 2 * tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(GeometricOperations.pointPlusEscalar(l4.getFinalPoint(), "x", -tmp.getRadius()), l4.getFinalPoint(), -Math.PI / 2);
//				formaOriginal.add(l1);
//				formaOriginal.add(a1);
//				formaOriginal.add(l2);
//				formaOriginal.add(a2);
//				formaOriginal.add(l3);
//				formaOriginal.add(a3);
//				formaOriginal.add(l4);
//				formaOriginal.add(a4);
			} else if(pocket.getItsBoss().get(i).getClass() == CircularBoss.class)
			{
				CircularBoss tmp = (CircularBoss)pocket.getItsBoss().get(i);
				LimitedArc arc = new LimitedArc(tmp.getCenter(), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, tmp.Z), 2 * Math.PI);
				formaOriginal.add(arc);
			} else if(pocket.getItsBoss().get(i).getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss gen = (GeneralProfileBoss)pocket.getItsBoss().get(i);
				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(gen.getVertexPoints(), pocket.Z, genBoss.getRadius());
				for(int j = 0; j < addBossVertex.getElements().size(); j++)
				{
					formaOriginal.add(addBossVertex.getElements().get(j));
				}
			}
		}
	}

	@Test
	public void mutipleParallelPathTest()
	{
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, 0, 10,2);
//		GenerateContournParallel contourn1 = new GenerateContournParallel(pocket, 0, 60,2);
//		GenerateContournParallel contourn2 = new GenerateContournParallel(pocket, 0, 60,2);


		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = contourn.multipleParallelPath();
//		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = GenerateContournParallel.multipleParallelPath(pocket, 0, 25,2);
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(int i = 0;i < multiplePath.size();i++)
		{
//			System.out.println(multiplePath.get(i).size());
			for(int j = 0;j < multiplePath.get(i).size(); j++)
			{
				for(int k = 0;k < multiplePath.get(i).get(j).size(); k++)
				{
					all.add(multiplePath.get(i).get(j).get(k));
				}
			}
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
	public void parallelPathTest()
	{
		double distance = 30;
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, 0, distance,2);
		ArrayList<LimitedElement> elementForIntersection = new ArrayList<LimitedElement>();
		for(ArrayList<LimitedElement> arrayTmp : contourn.parallelPath2(distance))
		{
			for(LimitedElement elementTmp:arrayTmp)
			{
				all.add(elementTmp);
				if(elementTmp.isLimitedArc())
				{
					if(((LimitedArc)elementTmp).getDeltaAngle() < 0)
					{
						if(((LimitedArc)elementTmp).getDeltaAngle() == -2*Math.PI)
						{
//							elementForIntersection.add(elementTmp);
//							all.add(elementTmp);
						}
						else
						{
//							elementForIntersection.add(elementTmp);
//							all.add(elementTmp);
						}
					}
				}
			}
		}
//		LimitedElement l = new LimitedLine(new Point3d(250,70,0),new Point3d(250,130,0));
//		elementForIntersection.add(new LimitedLine(new Point3d(250,70,0),new Point3d(250,130,0)));
//		all.add(new LimitedLine(new Point3d(250,70,0),new Point3d(250,130,0)));
//		System.out.println(all.size());
		GeometricOperations.showElements(elementForIntersection);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void validarPath1Test()
	{
		double distance = 28;
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, 0, distance,2);
		ArrayList<LimitedElement> all = GenerateContournParallel.validar1Path(contourn.parallelPath2(distance).get(0));//new ArrayList<LimitedElement>();
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void validarPath2Test()
	{
		double distance = 33;
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, 0, distance,2);
		ArrayList<LimitedElement> all = GenerateContournParallel.validar2Path(GenerateContournParallel.validar1Path(contourn.parallelPath2(distance).get(0)),formaOriginal,distance);//new ArrayList<LimitedElement>();
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	@Test
	public void absolutParallelTest()
	{
		//O erro esta no conceito de inside (quando o sentido do elemento é CW, o inside é considerado para baixo (e nem sempre é o caso)
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>(); //Array do desenhador
		Point3d p1I = new Point3d(50,50,0);
		Point3d p1F = new Point3d(100,50,0);
		LimitedLine l1 = new LimitedLine(p1I, p1F);
		LimitedLine lineParallel = GenerateContournParallel.absoluteParallel(l1, 10, true,true);
		all.add(l1);
		all.add(lineParallel);
		GeometricOperations.showElements(all);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
}