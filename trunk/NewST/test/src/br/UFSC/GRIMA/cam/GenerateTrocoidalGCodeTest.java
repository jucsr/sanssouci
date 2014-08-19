package br.UFSC.GRIMA.cam;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;

public class GenerateTrocoidalGCodeTest 
{
	Workingstep ws;
	GeneralClosedPocket pocket = new GeneralClosedPocket();

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
//		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
//		//Circular Boss
//		CircularBoss arcBoss = new CircularBoss("", 350, 200, pocket.Z, 30, 15, pocket.getProfundidade());
//		itsBoss.add(arcBoss);
//		//Rectangular Boss
//		RectangularBoss rectBoss = new RectangularBoss(40, 40, pocket.getProfundidade(), 0);
//		rectBoss.setPosicao(400, 200, pocket.Z);
//		rectBoss.setRadius(10);
//		itsBoss.add(rectBoss);
//		//General Boss
//		GeneralProfileBoss genBoss = new GeneralProfileBoss();
//		genBoss.setRadius(10);
//		ArrayList<Point2D> vertexPoints = new ArrayList<Point2D>();
//		vertexPoints.add(new Point2D.Double(150, 300));
//		vertexPoints.add(new Point2D.Double(300, 300));
//		vertexPoints.add(new Point2D.Double(300, 250));
//		vertexPoints.add(new Point2D.Double(200, 250));
//		vertexPoints.add(new Point2D.Double(200, 180));
////		vertexPoints.add(new Point2D.Double(150, 230)); // ------- //
//		vertexPoints.add(new Point2D.Double(50, 180));
//		vertexPoints.add(new Point2D.Double(50, 240));
//		vertexPoints.add(new Point2D.Double(150, 240));
//		genBoss.setVertexPoints(vertexPoints);
//		itsBoss.add(genBoss);
		
//		pocket.setItsBoss(itsBoss);
		
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
		cond.setAe(10);
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
	}
	
	@Test
	public void getCodeTest()
	{
		GenerateTrocoidalGCode gCode = new GenerateTrocoidalGCode(ws, 10);
		System.out.println(gCode.getGCode());
	}
	
}
