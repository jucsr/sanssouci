package br.UFSC.GRIMA.cam;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrocoidalGCodeTest 
{
	private Workingstep ws;
	private GeneralClosedPocket pocket = new GeneralClosedPocket();
	private ArrayList<LimitedElement> formaOriginal = new ArrayList<LimitedElement>();

	@Before
	public void init()
	{
		//Forma 1
	    ArrayList<Point2D> points = new ArrayList<Point2D>();
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
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		//Circular Boss
		CircularBoss arcBoss = new CircularBoss("", 350, 200, pocket.Z, 30, 15, pocket.getProfundidade());
		itsBoss.add(arcBoss);
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(400, 200, pocket.Z);
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
		
		// --- Criando Machining workingstep ----
		
		// ---- criando Operacao ----
		MachiningOperation operation = new BottomAndSideRoughMilling("Desbaste", 50);
		operation.setCoolant(true);
		
		// ---- criando Ferramenta ----
		FaceMill ferramenta= new FaceMill();
		ferramenta.setName("1");
		ferramenta.setDiametroFerramenta(250);

		ferramenta.setMaterialClasse(Material.ACO_ALTA_LIGA);
			
		// ---- criando Condicoes de usinagem -----S
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setAp(2);
		cond.setAe(60);
		cond.setF(.0123);
		cond.setN(1500);
			
	    ws = new Workingstep();
		ws.setOperation(operation);
		ws.setFerramenta(ferramenta);
		ws.setFeature(pocket);
		ws.setCondicoesUsinagem(cond);
		Vector workingsteps = new Vector();
		workingsteps.add(ws);
		pocket.setWorkingsteps(workingsteps);
		
		//========
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
				LimitedLine l1 = new LimitedLine(GeometricOperations.pointPlusEscalar(position, "x", tmp.getRadius()),GeometricOperations.pointPlusEscalar(position,"x",(l-tmp.getRadius())));
				LimitedArc a1 = new LimitedArc(GeometricOperations.pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()),l1.getFinalPoint(),Math.PI/2);
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(),GeometricOperations.pointPlusEscalar(a1.getFinalPoint(), "y", (c-2*tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(GeometricOperations.pointPlusEscalar(l2.getFinalPoint(), "x", -tmp.getRadius()),l2.getFinalPoint(),Math.PI/2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),GeometricOperations.pointPlusEscalar(a2.getFinalPoint(), "x", -(l - 2*tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(GeometricOperations.pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()),l3.getFinalPoint(),Math.PI/2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(),GeometricOperations.pointPlusEscalar(a3.getFinalPoint(),"y",-(c - 2*tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(GeometricOperations.pointPlusEscalar(l4.getFinalPoint(), "x", tmp.getRadius()),l4.getFinalPoint(),Math.PI/2);
				formaOriginal.add(l1);
				formaOriginal.add(a1);
				formaOriginal.add(l2);
				formaOriginal.add(a2);
				formaOriginal.add(l3);
				formaOriginal.add(a3);
				formaOriginal.add(l4);
				formaOriginal.add(a4);
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
	public void getCodeTest()
	{
		GenerateTrocoidalGCode gCode = new GenerateTrocoidalGCode(ws, 10);
		System.out.println(gCode.getGCode());
		LimitedLine l1 = new LimitedLine(new Point3d(30,310,0),new Point3d(30,295,0));
		LimitedLine l2 = new LimitedLine(new Point3d(30,295,0), new Point3d(40,280,50));
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
//		all.add(l1);
//		all.add(l2);
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = gCode.getMultipleLimitedElements();
		for(int i = 0;i < multiplePath.size();i++)
		{
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
	public void getDesbasteContournParallel()
	{
		MovimentacaoGeneralClosedPocket mov = new MovimentacaoGeneralClosedPocket(ws);
		ArrayList<Path> paths = mov.getDesbasteContourParallel();
		ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
		ArrayList<ArrayList<ArrayList<LimitedElement>>> elements1 = GeometricOperations.multipleParallelPath(pocket, 60, 0);
		System.out.println("Tamanho: " + elements1.get(0).size());
		for(Path pathTmp:paths)
		{
			if(pathTmp.isCircular())
			{
				CircularPath arcPath = (CircularPath)pathTmp;
				LimitedArc arcTmp = new LimitedArc(arcPath.getCenter(),arcPath.getInitialPoint(),arcPath.getAngulo());
				elements.add(arcTmp);
			}
			else if(pathTmp.isLine())	
			{
				LinearPath linePath = (LinearPath)pathTmp;
				LimitedLine lineTmp = new LimitedLine(linePath.getInitialPoint(),linePath.getFinalPoint());
				elements.add(lineTmp);
			}
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(elements);
		desenhador.setVisible(true);
		for(;;);
	}
}
