package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.movimentacoes.GenerateTrochoidalMovement1;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraGeneralClosedPocket1Test 
{
	private GeneralClosedPocket pocket = new GeneralClosedPocket();
	Projeto projeto;
	Bloco bloco;
	Face faceXY;

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
		
		//Forma 2
//		points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 0));
//		points.add(new Point2D.Double(0, 0));
//		points.add(new Point2D.Double(0, 320));
		
		//Protuberancia
		pocket.setPoints(points);
		pocket.setRadius(30);
		pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		CircularBoss arcBoss = new CircularBoss("", 200, 150, pocket.Z, 60, 60, pocket.getProfundidade());
		itsBoss.add(arcBoss);
		
		//Rectangular Boss
		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
		rectBoss.setPosicao(200, 200, pocket.Z);
		rectBoss.setRadius(10);
//		itsBoss.add(rectBoss);
		pocket.setItsBoss(itsBoss);
		
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("AÃ§o", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
		
		new MapeadoraDeWorkingsteps(projeto);
//		new ToolManager(projeto);
		
		//adicionando feature na face
		faceXY = (Face)(bloco.faces.elementAt(Face.XY));
		faceXY.features.addElement(pocket);
		
		// ---- feature definition ----
		Point3d coordinates = new Point3d(pocket.X, pocket.Y, pocket.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		pocket.setPosition(position);
//		pocket.setPassante(true);
		pocket.setTolerancia(0.05);
		pocket.setRugosidade(0.05);
		
	}
	
	@Test
	public void getMenorMaiorDistanciaTest()
	{
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		MapeadoraGeneralClosedPocket1 mp = new MapeadoraGeneralClosedPocket1(pocket);
//		double maiorMenorDistancia = mp.getMaiorMenorDistancia(pocket);
//		double menorMenorDistancia = mp.getMenorMenorDistance(pocket);
		double maiorMenorDistancia = mp.getMaiorMenorDistancia(GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z));
		double menorMenorDistancia = mp.getMenorMenorDistance(GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z));
//		Point3d point = new Point3d(100,100,0);
//		LimitedArc a1 = new LimitedArc(new Point3d(230,160,0),new Point3d(230,150,0),Math.PI/2);
//		LimitedArc a2 = new LimitedArc(new Point3d(470,30,0),new Point3d(470,0,0),Math.PI/2);
		for(LimitedElement elementTmp:addPocketVertex.getElements())
		{
			all.add(elementTmp);
		}
		for(ArrayList<LimitedElement> arrayTmp:GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z))
		{
			for(LimitedElement elementTmp:arrayTmp)
			{
				all.add(elementTmp);
			}
		}
//		System.out.println("Distance arc-arc: " + GeometricOperations.minimumDistance(a1, a2));
		System.out.println("Maior Menor Distancia: " + maiorMenorDistancia);
		System.out.println("Menor Menor distancia: " + menorMenorDistancia);
//		System.out.println("Proporção (Menor / Maior): " + menorMenorDistancia/maiorMenorDistancia);
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	
	@Test
	public void getAlreadyDesbastedArea()
	{
		ArrayList<LimitedElement> all = new ArrayList<LimitedElement>();
		MapeadoraGeneralClosedPocket1 mp = new MapeadoraGeneralClosedPocket1(pocket);
		double diametroFerramenta = GeometricOperations.roundNumber(mp.getMaiorMenorDistancia(GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z))/3,2);
		double overLap = 0.25*diametroFerramenta;
		System.out.println("Offset Distance: " + diametroFerramenta);
		System.out.println("Overlap: " + overLap);
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, pocket.Z, 60, 2);
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multiplePath = contourn.multipleParallelPath();
		
//		for(ArrayList<ArrayList<LimitedElement>> matrixTmp:multiplePath)
//		{
			for(ArrayList<LimitedElement> arrayTmp:multiplePath.get(0))
//			for(ArrayList<LimitedElement> arrayTmp:matrixTmp)
			{
				for(LimitedElement elementTmp:arrayTmp)
				{
					all.add(elementTmp);
				}
			}
//		}
		//WorkingStep
			
//		GenerateTrochoidalMovement1 trochidalMovment = new GenerateTrochoidalMovement1(elements, ws)
//		GeometricOperations.showElements(multiplePath.get(0).get(0));
//		System.out.println(multiplePath);
		ArrayList<ArrayList<LimitedElement>> bossElements = MapeadoraGeneralClosedPocket1.getAreaAlreadyDesbasted(pocket,pocket.Z,60,2);
		for(ArrayList<LimitedElement> arrayTmp:bossElements)
		{
			for(LimitedElement elementTmp:arrayTmp)
			{
				all.add(elementTmp);
			}
		}
		DesenhadorDeLimitedElements desenhador = new DesenhadorDeLimitedElements(all);
		desenhador.setVisible(true);
		for(;;);
	}
	
}
