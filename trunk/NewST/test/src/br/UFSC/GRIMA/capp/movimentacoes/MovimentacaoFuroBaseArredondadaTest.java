package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseArredondadaTest
{
	Bloco bloco;
	Face face;
	double retractPlane = 21;
	FuroBaseArredondada feature;
	@Before
	public void init()
	{
		bloco = new Bloco(200, 150, 80);
		face = (Face) bloco.faces.get(Face.XY);
		feature = new FuroBaseArredondada("FURO", 70, 55, 0, 60, 40, 10, 0.05);
	}
	@Test
	public void destasteTest()
	{
	
		// ---- MILLING
		BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		//BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		milling.setCoolant(true);
		milling.setStartPoint(new Point3d(0, 0, 0));
		milling.setAllowanceSide(0.5);
		milling.setAllowanceBottom(0.5);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		FaceMill faceMill = new FaceMill(10,50);
		
		Workingstep ws = new Workingstep(feature, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(milling);
		ws.setFerramenta(faceMill);
		
		MovimentacaoFuroBaseArredondada m = new MovimentacaoFuroBaseArredondada(ws);
		ArrayList<Path> path = m.desbaste();
		
		for(Path patTmp:path)
		{
//			System.out.println(patTmp);
		}
	}
	@Test
	public void movimentacaoDrillingTest()
	{
		Drilling operation = new Drilling("Furacao", retractPlane);
		operation.setCuttingDepth(50);
		
		Workingstep ws = new Workingstep(feature, face);
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 0, 0);
		ws.setOperation(operation);
		ws.setCondicoesUsinagem(cu);
		
		MovimentacaoFuroBaseArredondada m = new MovimentacaoFuroBaseArredondada(ws);
		m.movimentacaoFuracao();
		ArrayList<Path> path = m.movimentacaoFuracao();
		for(Path patTmp:path)
		{
			System.out.println(patTmp.getFinalPoint());
		}
	}
	@Test
	public void interpolarCircularPathTest()
	{
		Point3d initialPoint = new Point3d(10, 0, 0);
		Point3d finalPoint = new Point3d(0, 10, 0);
		Point3d center = new Point3d(0, 0, 0);
		
		double radius = 10;
		CircularPath path = new CircularPath(initialPoint, finalPoint, radius, center);
		path.setSense(CircularPath.CCW);
		System.out.println("angulo = " + path.angulo * 180 / Math.PI);
		System.out.println("angulo IN = " + path.getinicialAngle() * 180 / Math.PI);
		System.out.println("angulo FI= " + path.getFinalAngle() * 180 / Math.PI);
		System.out.println("sentido = " + path.getSense());
		System.out.println(MovimentacaoFuroBaseArredondada.interpolarCircularPath(path, 10));
		System.out.println(MovimentacaoFuroBaseArredondada.interpolarCircularPath(path, 10).size());
	}
}
