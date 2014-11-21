package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.Two5DMillingOperation;
import br.UFSC.GRIMA.capp.movimentacoes.GenerateTrochoidalMovement1;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Two5DMillingStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrochoidalMovement1Main 
{
	private static ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
	private static ArrayList<LimitedElement> formaOriginal;
	private static GeneralClosedPocket pocket = new GeneralClosedPocket();
	private static  Workingstep ws;

	public static void Project()
	{
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
		
		//Forma 1
//		points.add(new Point2D.Double(8, 160));
//		points.add(new Point2D.Double(8, 320));
//		points.add(new Point2D.Double(480, 320));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200,160));
		
		//Forma 2
		points.add(new Point2D.Double(500, 320));
		points.add(new Point2D.Double(500, 160));
		points.add(new Point2D.Double(280, 160));
		points.add(new Point2D.Double(280, 40));
		points.add(new Point2D.Double(0, 40));
		points.add(new Point2D.Double(0, 320));
		
	    //Forma 3
//		points.add(new Point2D.Double(8, 160));
//		points.add(new Point2D.Double(8, 320));
//		points.add(new Point2D.Double(480, 320));
//		points.add(new Point2D.Double(700, 500));
//		points.add(new Point2D.Double(700, 160));
//		points.add(new Point2D.Double(480, 160));
//		points.add(new Point2D.Double(480, 40));
//		points.add(new Point2D.Double(200, 40));
//		points.add(new Point2D.Double(200,160));
		
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
		
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		
		//Circular Boss
//		CircularBoss arcBoss = new CircularBoss("", 200, 200, pocket.Z, 30, 15, pocket.getProfundidade());
		CircularBoss arcBoss = new CircularBoss("", 200, 200, pocket.Z, 30, 30, pocket.getProfundidade());
		itsBoss.add(arcBoss);
		
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(120, 180, pocket.Z);
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
//		vertexPoints.add(new Point2D.Double(150, 180));
		vertexPoints.add(new Point2D.Double(50, 180));
		vertexPoints.add(new Point2D.Double(50, 240));
		vertexPoints.add(new Point2D.Double(150, 240));
		genBoss.setVertexPoints(vertexPoints);
//		itsBoss.add(genBoss);
		
		pocket.setItsBoss(itsBoss);
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(points, 0, 25);
		formaOriginal = addPocketVertex.getElements();
		
		// --- Menor distancia entre cavidade e protuberancia ---
		ArrayList<LimitedElement> bossElement = new ArrayList<LimitedElement>();
		for(ArrayList<LimitedElement> arrayTmp : GeometricOperations.tranformeBossToLimitedElement(itsBoss, pocket.Z))
		{
			for(LimitedElement elementTmp : arrayTmp)
			{
				bossElement.add(elementTmp);
			}
		}
		double menorDistancia = GeometricOperations.minimumDistance(formaOriginal, bossElement);
		
		// --- Criando Machining workingstep ----
		
		// ---- criando Operacao ----
		MachiningOperation operation = new BottomAndSideRoughMilling("Desbaste", 50);
		operation.setCoolant(true);
		
		// ---- criando Ferramenta ----
		FaceMill ferramenta= new FaceMill();
		ferramenta.setName("1");
		ferramenta.setDiametroFerramenta(menorDistancia/2); //Diametro da ferramenta (mudei)
		ferramenta.setMaterialClasse(Material.ACO_ALTA_LIGA);
				
		// ---- criando Condicoes de usinagem -----
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setAp(2); //15
		cond.setAe(10);
		cond.setF(.0123);
		cond.setN(1500);
				
		// ---- criando estrategia -----
		TrochoidalAndContourParallelStrategy strategy = new TrochoidalAndContourParallelStrategy();
		strategy.setAllowMultiplePasses(true);
		//Setar o trochoidalRadius nos proprios testes
		strategy.setTrochoidalRadius(ferramenta.getDiametroFerramenta()/2);
//		strategy.setTrochoidalFeedRate(20);
		strategy.setOverLap(2);
		strategy.setRotationDirectionCCW(Boolean.TRUE);
		strategy.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
//		strategy.setRadialDephtPercent(20);
		operation.setMachiningStrategy(strategy);
				
		ws = new Workingstep();
		ws.setId("milling test");
		ws.setOperation(operation);
		ws.setFerramenta(ferramenta);
		ws.setFeature(pocket);
		ws.setCondicoesUsinagem(cond);
		Vector workingsteps = new Vector();
		workingsteps.add(ws);
		pocket.setWorkingsteps(workingsteps);
	}
	

	public void generateTrochoidalPathTest()
	{
//		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalRadius(20); //Raio
//		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalFeedRate(25); //Avanco
		ArrayList<ArrayList<LimitedElement>> parallel = GeometricOperations.parallelPath2(pocket, 90, 0);
		ArrayList<Path> paths = new ArrayList<Path>();
		for(ArrayList<LimitedElement> tmp : parallel)
		{
			GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(tmp, ws);
			for(Path pathTmp:gen.getPaths())
			{
				paths.add(pathTmp);
			}
		}
		ArrayList<LimitedElement> movimentacoes = GenerateTrochoidalMovement1.transformPathsInLimitedElements(paths);
		
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
	
	public static void main (String[] args) //generateMultipleParallelAndTrochoidalMovementTest()
	{
		Project();
		double trochoidalRadius = ((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).getTrochoidalRadius();
		System.out.println("Raio Trochoidal1: " + trochoidalRadius);
//		double trochoidalPercent = 1.5;
		double diametroFerramenta = ws.getFerramenta().getDiametroFerramenta();
		System.out.println("diametro da Ferramenta1: " + diametroFerramenta);
		double overLap =((Two5DMillingStrategy)ws.getOperation().getMachiningStrategy()).getOverLap();
		double distance = trochoidalRadius + diametroFerramenta/2;
		GenerateContournParallel generateContorun = new GenerateContournParallel(pocket, 0, 20 ,overLap);
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = generateContorun.multipleParallelPath() ;
		ArrayList<LimitedElement> pathsVector = new ArrayList<LimitedElement>();
		for(int i = 0; i < multiplePath.size(); i++)
		{
			for(int j = 0; j < multiplePath.get(i).size(); j++)
			{
//				GeometricOperations.showElements(multiplePath.get(i).get(j));
				GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(multiplePath.get(i).get(j), ws); //trochoidalRadius, 15
				ArrayList<LimitedElement> movimentacoes = GenerateTrochoidalMovement1.transformPathsInLimitedElements(gen.getPaths());
				for(int k = 0; k < movimentacoes.size(); k ++)
				{
					pathsVector.add(movimentacoes.get(k));
				}
			}
		}
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		for(LimitedElement tmp : formaOriginal)
		{
//			all.add(tmp);
		}
		for(LimitedElement tmp : pathsVector)
		{
			all.add(tmp);
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	
	public void generatePathsInLimitedLineBaseTest()
	{
		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalRadius(10); //Raio
//		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalFeedRate(5); //Avanco
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		LimitedLine l1 = new LimitedLine(new Point3d(20,10,0), new Point3d(73,10,0));
		all.add(l1);
		ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
		elements.add(l1);
		GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(elements,ws);
		ArrayList<Path> paths = gen.getPaths();
		ArrayList<LimitedElement> pathToElements = GenerateTrochoidalMovement1.transformPathsInLimitedElements(paths);
		
		for(LimitedElement tmp : pathToElements)
		{
			all.add(tmp);
		}
		
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	//Adicao dos transitionPaths:
	//-Funcionado para linha-linha
	@Test
	public void generatePathsTest()
	{
		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalRadius(10); //Raio
//		((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).setTrochoidalFeedRate(5); //Avanco
		
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		ArrayList<LimitedElement> elementos = new ArrayList<LimitedElement>();
		double Z = -10;
		//----------------------------------------------------------------------------------------------
		//Linha - Linha
		LimitedLine l1 = new LimitedLine(new Point3d(50,10,Z),new Point3d(83,10,Z));
		LimitedLine l2 = new LimitedLine(new Point3d(83,10,Z), new Point3d(20,53,Z));
		//----------------------------------------------------------------------------------------------
		//Arco - Arco: Caso 1
		LimitedArc a1 = new LimitedArc(new Point3d(50,50,0),new Point3d(50,0,0),Math.PI/2);
		LimitedArc a2 = new LimitedArc(new Point3d(150,50,0),new Point3d(100,50,0),Math.PI/2);
		//Arco - Arco: Caso 2
		Point3d a1C = new Point3d(50,50,Z);
		Point3d a1I = new Point3d(70,60,Z);
		Point3d a1F = new Point3d(60,70,Z);
		double a1Angle = GeometricOperations.calcDeltaAngle(a1I, a1F, a1C, Math.PI);
//		LimitedArc a1 = new LimitedArc(a1C,a1I,a1Angle);
//		LimitedArc a2 = new LimitedArc(new Point3d(70,50,Z),a1F,a1.getDeltaAngle());
		//-----------------------------------------------------------------------------------------------
		//Linha(l1) - Arco(a3)
		Point3d unitVectorl1 = GeometricOperations.unitVector(l1.getInitialPoint(), l1.getFinalPoint());
		double a3Radius = 15;
//		Point3d a3Center = GeometricOperations.pointPlusEscalar(GeometricOperations.absoluteParallel(l1, a3Radius, true).getFinalPoint(),"x",-10);
//		Point3d a3InitialPoint = l1.getFinalPoint();
//		Point3d a3Center = new Point3d(l1.getFinalPoint().x + GeometricOperations.multiply(a3Radius, unitVectorl1).x, l1.getFinalPoint().y + GeometricOperations.multiply(a3Radius, unitVectorl1).y,l1.getFinalPoint().z);
		
		//Arco(a3) - Linha(l1): Caso 1
		LimitedLine parallelL1 = GeometricOperations.absoluteParallel(l1, a3Radius, true);
		Point3d a3Center = parallelL1.getInitialPoint();
		Point3d a3InitialPoint = new Point3d(parallelL1.getInitialPoint().x  + GeometricOperations.multiply(-a3Radius, unitVectorl1).x,parallelL1.getInitialPoint().y  + GeometricOperations.multiply(-a3Radius, unitVectorl1).y,l1.getInitialPoint().z);
		LimitedArc a3 = new LimitedArc(a3Center,a3InitialPoint,Math.PI/2);
		
		//Arco(a4) - Linha(l3): Caso 2 (ERRO!)
		LimitedArc a4 = new LimitedArc(new Point3d(50,50,0),new Point3d(10,50,0),-Math.PI/2);
		LimitedLine l3 = new LimitedLine(a4.getFinalPoint(),GeometricOperations.pointPlusEscalar(a4.getFinalPoint(), "x", 50));
		
		//-----------------------------------------------------------------------------------------------
		elementos.add(a1);
		elementos.add(a2);
		GenerateTrochoidalMovement1 gen = new GenerateTrochoidalMovement1(elementos, ws);
		ArrayList<Path> paths = gen.getPaths();
		ArrayList<LimitedElement> pathToElements = GenerateTrochoidalMovement1.transformPathsInLimitedElements(paths);
		for(LimitedElement tmp : elementos)
		{
			all.add(tmp);
		}
		for(LimitedElement tmp : pathToElements)
		{
			all.add(tmp);
		}
		
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
}
