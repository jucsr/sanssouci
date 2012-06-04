package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.GCodeGenerator;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MovimentacaoRegionSuperficieBezierTest {

		private Projeto projeto;
		DadosDeProjeto dados;
		Bloco bloco;
		Face face;
		double retractPlane = 21;
		private Region feature;
				
		double[][][] control_points = {
				{ { -3, -3, 0 }, { -1, -3, 0 }, { 1, -3, 0 }, { 3, -3, 0 } },
				{ { -3, -1, 0 }, { -1, -1, 0 }, { 1, -1, 0 }, { 3, -1, 0 } },
				{ { -3, 1, 0 }, { -1, 1, 0 }, { 1, 1, 0 }, { 3, 1, 0 } },
				{ { -3, 3, 0 }, { -1, 3, 0 }, { 1, 3, 0 }, { 3, 3, 0 } } 
				};
		public Point3d [][] controlVertex = new Point3d[4][4];
		
//		Point3d p00 = new Point3d(0, 0, 30);
//		Point3d p01 = new Point3d(30, 0, 0);
//		Point3d p02 = new Point3d(60, 0, 0);
//		Point3d p03 = new Point3d(90, 0, 30);
//		Point3d p10 = new Point3d(0, 30, 0);
//		Point3d p11 = new Point3d(30, 30, 0);
//		Point3d p12 = new Point3d(60, 30, 0);
//		Point3d p13 = new Point3d(90, 30, 0);
//		Point3d p20 = new Point3d(0, 60, 0);
//		Point3d p21 = new Point3d(30, 60, 0);
//		Point3d p22 = new Point3d(60, 60, 0);
//		Point3d p23 = new Point3d(90, 60, 0);
//		Point3d p30 = new Point3d(0, 90, 30);
//		Point3d p31 = new Point3d(30, 90, 0);
//		Point3d p32 = new Point3d(60, 90, 0);
//		Point3d p33 = new Point3d(90, 90, 30);
		
		Point3d p00 = new Point3d(0, 0, 40);
		Point3d p01 = new Point3d(30, 0, 0);
		Point3d p02 = new Point3d(60, 0, 0);
		Point3d p03 = new Point3d(90, 0, 0);
		Point3d p10 = new Point3d(0, 30, 0);
		Point3d p11 = new Point3d(30, 30, 20);
		Point3d p12 = new Point3d(60, 30, 40);
		Point3d p13 = new Point3d(90, 30, 00);
		Point3d p20 = new Point3d(0, 60, 0);
		Point3d p21 = new Point3d(30, 60, 40);
		Point3d p22 = new Point3d(60, 60, 20);
		Point3d p23 = new Point3d(90, 60, 0);
		Point3d p30 = new Point3d(0, 90, 0);
		Point3d p31 = new Point3d(30, 90, 0);
		Point3d p32 = new Point3d(60, 90, 0);
		Point3d p33 = new Point3d(90, 90, 40);
		
		@Before
		public void init()
		{
			bloco = new Bloco(200, 150, 80);
			dados = new DadosDeProjeto(0, "a","",0);
			projeto = new Projeto(null,bloco,dados);
			face = (Face) bloco.faces.get(Face.XY);
			feature = new Region(Feature.REGION);
			feature.setControlVertex(controlVertex);
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
			
			MovimentacaoRegionSuperficieBezier m = new MovimentacaoRegionSuperficieBezier(ws);
			ArrayList<LinearPath> path = m.desbaste();
			
			for(Path patTmp:path)
			{
//				System.out.println(patTmp);
			}
			Vector wsts = new Vector();
			wsts.add(ws);
			Vector wsFace = new Vector();
			wsFace.add(wsts);
			GCodeGenerator codigoG = new GCodeGenerator(wsFace, projeto);
			System.out.println(codigoG.GenerateGCodeString());
			
			
			
		}
//		@Test
//		public void movimentacaoDrillingTest()
//		{
//			Drilling operation = new Drilling("Furacao", retractPlane);
//			operation.setCuttingDepth(50);
//			
//			Workingstep ws = new Workingstep(feature, face);
//			CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 0, 0);
//			ws.setOperation(operation);
//			ws.setCondicoesUsinagem(cu);
//			
//			MovimentacaoFuroBaseArredondada m = new MovimentacaoFuroBaseArredondada(ws);
//			m.movimentacaoFuracao();
//			ArrayList<Path> path = m.movimentacaoFuracao();
//			for(Path patTmp:path)
//			{
//				System.out.println(patTmp.getFinalPoint());
//			}
//		}
//		@Test
//		public void movBullNoseEndMillTest()
//		{
//			BottomAndSideRoughMilling operation = new BottomAndSideRoughMilling("OP 1", retractPlane);
//			CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
//			BullnoseEndMill b = new BullnoseEndMill(10,50);
//			b.setEdgeRadius(3);
//			Workingstep ws = new Workingstep(feature, face);
//			ws.setCondicoesUsinagem(cu);
//			ws.setFerramenta(b);
//			ws.setOperation(operation);
//			
//			MovimentacaoFuroBaseArredondada m = new MovimentacaoFuroBaseArredondada(ws);
//			
//			ArrayList<Path> path = m.operacaoComBullnoseEndMill();
//			for(Path patTmp:path)
//			{
//				System.out.println(patTmp.getFinalPoint());
//			}
//		}
//		@Test
//		public void interpolarCircularPathTest()
//		{
//			Point3d initialPoint = new Point3d(10, 0, 0);
//			Point3d finalPoint = new Point3d(0, 10, 0);
//			Point3d center = new Point3d(0, 0, 0);
//			
//			double radius = 10;
//			CircularPath path = new CircularPath(initialPoint, finalPoint, radius, center);
//			path.setSense(CircularPath.CCW);
//			System.out.println("angulo = " + path.angulo * 180 / Math.PI);
//			System.out.println("angulo IN = " + path.getinicialAngle() * 180 / Math.PI);
//			System.out.println("angulo FI= " + path.getFinalAngle() * 180 / Math.PI);
//			System.out.println("sentido = " + path.getSense());
//			System.out.println(MovimentacaoFuroBaseArredondada.interpolarCircularPath(path, 10));
//			System.out.println(MovimentacaoFuroBaseArredondada.interpolarCircularPath(path, 10).size());
//		}
//		@Test
//		public void getTrechosTest()
//		{
//			Point3d[] pontosDeControle = new Point3d[10];
//			pontosDeControle[0] = new Point3d(0, 0, 0);
//			pontosDeControle[1] = new Point3d(20, 0, -40);
//			pontosDeControle[2] = new Point3d(40, 0, -20);
//			pontosDeControle[3] = new Point3d(60, 0, 40);
//			pontosDeControle[4] = new Point3d(80, 0, -20);
//			pontosDeControle[5] = new Point3d(100, 0, -40);
//			pontosDeControle[6] = new Point3d(120, 0, 0);
//			
////			#63=CARTESIAN_POINT($,(0.0,0.0,0.0));
////			#64=CARTESIAN_POINT($,(20.0,0.0,-50.0));
////			#65=CARTESIAN_POINT($,(40.0,0.0,55.0));
////			#66=CARTESIAN_POINT($,(60.0,0.0,-55.0));
////			#67=CARTESIAN_POINT($,(80.0,0.0,15.0));
////			#68=CARTESIAN_POINT($,(100.0,0.0,-60.0));
////			#69=CARTESIAN_POINT($,(120.0,0.0,0.0));
////			#70=CARTESIAN_POINT($,(140.0,0.0,39.0));
////			#71=CARTESIAN_POINT($,(160.0,0.0,-50.0));
////			#72=CARTESIAN_POINT($,(180.0,0.0,0.0));
//			pontosDeControle[0] = new Point3d(0, 0, 0);
//			pontosDeControle[1] = new Point3d(20, 0, -50);
//			pontosDeControle[2] = new Point3d(40, 0, 55);
//			pontosDeControle[3] = new Point3d(60, 0, -55);
//			pontosDeControle[4] = new Point3d(80, 0, 15);
//			pontosDeControle[5] = new Point3d(100, 0, -60);
//			pontosDeControle[6] = new Point3d(120, 0, 0);
//			pontosDeControle[7] = new Point3d(140, 0, 39);
//			pontosDeControle[8] = new Point3d(160, 0, -50);
//			pontosDeControle[9] = new Point3d(180, 0, 0);
//			
//			Point3d[] entrada = new Bezier_1(pontosDeControle, 101).meshArray;
//			
////			for(Point3d ponto: entrada)
////			{
////				System.out.println("ponto = " + ponto);
////			}
//			ArrayList<ArrayList<Point3d>> trechos = MovimentacaoFuroBaseArredondada.getTrechos(entrada, -10);
//			System.out.println("size = " + trechos.size());
//			System.out.println("size = " + trechos);
//			for(int i = 0; i < trechos.size(); i++)
//			{
////				System.out.println(trechos.get(i));
////				System.out.println(trechos.get(i).size());
//			}
//		}
	

	
}
