package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;

import java.util.Vector;

import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.acceptance.STEP_NCReader;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class STEP_NCReaderTest {

	public Util util = new Util();
	public ASdaiModel model;
	public STEP_NCReader reader;
	private Workingstep ws1;
	private Workingstep ws2;
	private GeneralClosedPocket pocket = new GeneralClosedPocket();

	@Before
	public void setUp() {
		try {
			model = util.openFile21(Util.getUserPath()
					+ "test\\res\\testePrecedencias.p21" );
			reader = new STEP_NCReader(model);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
//	public void init()
//	{
//	    ArrayList<Point2D> points = new ArrayList<Point2D>();
//	
//		points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 160));
//		points.add(new Point2D.Double(280, 160));
//		points.add(new Point2D.Double(280, 40));
//		points.add(new Point2D.Double(0, 40));
//		points.add(new Point2D.Double(0, 320));
//		pocket.setNome(" cavidade geral");
//		pocket.setPoints(points);
//		pocket.setRadius(30);
//		pocket.setPosicao(50, 50, 0);
//		pocket.setProfundidade(20);
//		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
//		CircularBoss arcBoss = new CircularBoss("", 150, 200, pocket.Z, 50, 50, pocket.getProfundidade());
//		pocket.setItsBoss(itsBoss);
//		// ---- criando Operacao ----
//		MachiningOperation operation = new BottomAndSideRoughMilling("Desbaste", 50);
//		operation.setCoolant(true);
//		// ---- criando Ferramenta ----
//		FaceMill ferramenta= new FaceMill();
//		ferramenta.setName("1");
//		ferramenta.setDiametroFerramenta(20);
//		ferramenta.setMaterialClasse(Material.ACO_ALTA_LIGA);
//		// ---- criando Condicoes de usinagem -----
//		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
//		cond.setAp(5); //15
//		cond.setAe(10);
//		cond.setF(.0123);
//		cond.setN(1500);
//		// ---- criando estrategia -----
//		TrochoidalAndContourParallelStrategy strategy = new TrochoidalAndContourParallelStrategy();
//		strategy.setAllowMultiplePasses(true);
//		strategy.setTrochoidalRadius(ferramenta.getDiametroFerramenta()/2);
////		strategy.setTrochoidalFeedRate(25);
//		strategy.setRotationDirectionCCW(Boolean.TRUE);
//		strategy.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
////		strategy.setRadialDephtPercent(20);
//		strategy.setOverLap(2);
//		operation.setMachiningStrategy(strategy);
//		// ---- workingstep 1 -----
//		ws1 = new Workingstep();
//		ws1.setId("milling test");
//		ws1.setOperation(operation);
//		ws1.setFerramenta(ferramenta);
//		ws1.setFeature(pocket);
//		ws1.setCondicoesUsinagem(cond);
//		Vector workingsteps = new Vector();
//		workingsteps.add(ws1);
//		pocket.setWorkingsteps(workingsteps);
//		// ---- criando estrategia -----
//		ContourParallel strategy2 = new ContourParallel();
//		strategy2.setAllowMultiplePasses(false);
//		strategy2.setOverLap(2);
//		strategy2.setRotationDirectionCCW(true);
//		// ---- workingstep 2 -----
//		ws1 = new Workingstep();
//		ws1.setId("milling test2");
//		ws1.setOperation(operation);
//		ws1.setFerramenta(ferramenta);
//		ws1.setFeature(pocket);
//		ws1.setCondicoesUsinagem(cond);
//		workingsteps.add(ws1);
//		pocket.setWorkingsteps(workingsteps);
//	}
	@Test
	public void getPiecePropsTest() {
		Rectangle3D rectangle = reader.getPieceProps();
		Assert.assertEquals(100.0, rectangle.getX());
		Assert.assertEquals(120.0, rectangle.getY());
		Assert.assertEquals(40.0, rectangle.getZ());
	}

//	@Test
//	public void readFeaturesTest() {
//		try {
//			ArrayList<Feature> list = reader.getFeatures();
//			
//			Iterator<Feature> iterator = list.iterator();
//
//			while (iterator.hasNext()) {
//
//				Feature feature = iterator.next();
//
//				if (feature.getTipo() == Feature.FURO) {
//					Furo furo = (Furo) feature;
//					TwistDrill broca = (TwistDrill) furo.getFerramenta();
//
//					Assert.assertEquals(12.4,furo.getDiametro(),0);
//					Assert.assertEquals(20.0,furo.getPosicaoX(),0);
//					Assert.assertEquals(10.0,furo.getPosicaoY(),0);
//					Assert.assertEquals(00.0,furo.getPosicaoZ(),0);
//					Assert.assertEquals(12.4,broca.getDiametroFerramenta(),0);
//					Assert.assertEquals(79.0,broca.getProfundidadeMaxima(),0);
//					Assert.assertEquals(0.349,broca.getToolTipHalfAngle(),0.001);
//
//				}
//
//				else if (feature.getTipo() == Feature.CAVIDADE) {
//					Cavidade cavidade = (Cavidade) feature;
//					FaceMill fresa = (FaceMill) cavidade.getFerramenta();
//
//					Assert.assertEquals(30.0,cavidade.getPosicaoX(),0);
//					Assert.assertEquals(40.0,cavidade.getPosicaoY(),0);
//					Assert.assertEquals(00.0,cavidade.getPosicaoZ(),0);
//					Assert.assertEquals(15.0,cavidade.getProfundidade(),0);
//					Assert.assertEquals(40.0,cavidade.getLargura(),0);
//					Assert.assertEquals(30.0,cavidade.getComprimento(),0);
//
//					Assert.assertEquals(9.0,fresa.getDiametroFerramenta(),0);
//					Assert.assertEquals(120.0,fresa.getProfundidadeMaxima(),0);
//					Assert.assertEquals(2.3,fresa.getAp(),0);
//				}
//
//				else if (feature.getTipo() == Feature.RANHURA) {
//					Ranhura ranhura = (Ranhura) feature;
//					FaceMill fresa = (FaceMill) ranhura.getFerramenta();
//
//					Assert.assertEquals(Ranhura.HORIZONTAL,ranhura.getEixo());
//					Assert.assertEquals(0.0,ranhura.getPosicaoX(),0);
//					Assert.assertEquals(80.0,ranhura.getPosicaoY(),0);
//					Assert.assertEquals(00.0,ranhura.getPosicaoZ(),0);
//					Assert.assertEquals(7.0,ranhura.getProfundidade(),0);
//					Assert.assertEquals(16.3,ranhura.getLargura(),0);
//
//					Assert.assertEquals(10.0,fresa.getDiametroFerramenta(),0);
//					Assert.assertEquals(70.0,fresa.getProfundidadeMaxima(),0);
//					Assert.assertEquals(5.0,fresa.getAp(),0);
//
//				}else if (feature.getTipo() == Feature.DEGRAU) {
//					
//					System.out.println("sem leitor pra degrau");
//				
//				}else{
//					fail();
//				}
//
//			}
//
//		} catch (SdaiException e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

	
	@Test
	public void getSettedWorkingStepsTest(){
		
		Vector<Workingstep> wksList;
		
		try {
			
			wksList = reader.getAllWorkingSteps().get(0);
			
			for(int i = 0; i < wksList.size(); i++){
				
				System.out.println("Feature = " + wksList.get(i).getFeature());
				System.out.println("WorkingStep = " + wksList.get(i));
				
				System.out.println("ap = " + wksList.get(i).getCondicoesUsinagem().getAp());
				System.out.println("ae = " + wksList.get(i).getCondicoesUsinagem().getAe());
				System.out.println("vf = " + wksList.get(i).getCondicoesUsinagem().getVf());
				System.out.println("vc = " + wksList.get(i).getCondicoesUsinagem().getVc());
				System.out.println("n = " + wksList.get(i).getCondicoesUsinagem().getN());
				
			}
			
			Assert.assertEquals(2.3, wksList.get(3).getCondicoesUsinagem().getAp(), 0);
			Assert.assertEquals(9.0, wksList.get(3).getCondicoesUsinagem().getAe(), 0);
			Assert.assertEquals(0.04, wksList.get(3).getCondicoesUsinagem().getVf(), 0);
			Assert.assertEquals(100.0, wksList.get(3).getCondicoesUsinagem().getVc(), 0);
			Assert.assertEquals(-12.0, wksList.get(3).getCondicoesUsinagem().getN(), 0);
			
			Assert.assertEquals(1.8, wksList.get(2).getCondicoesUsinagem().getAp(), 0);
			Assert.assertEquals(9.0, wksList.get(2).getCondicoesUsinagem().getAe(), 0);
			Assert.assertEquals(0.1, wksList.get(2).getCondicoesUsinagem().getVf(), 0);
			Assert.assertEquals(100.0, wksList.get(2).getCondicoesUsinagem().getVc(), 0);
			Assert.assertEquals(-10.0, wksList.get(2).getCondicoesUsinagem().getN(), 0);
			
			Assert.assertEquals(5.0, wksList.get(1).getCondicoesUsinagem().getAp(), 0);
			Assert.assertEquals(7.5, wksList.get(1).getCondicoesUsinagem().getAe(), 0);
			Assert.assertEquals(0.1, wksList.get(1).getCondicoesUsinagem().getVf(), 0);
			Assert.assertEquals(100.0, wksList.get(1).getCondicoesUsinagem().getVc(), 0);
			Assert.assertEquals(-10.0, wksList.get(1).getCondicoesUsinagem().getN(), 0);
			
			Assert.assertEquals(2.0, wksList.get(0).getCondicoesUsinagem().getAp(), 0);
			Assert.assertEquals(0.0, wksList.get(0).getCondicoesUsinagem().getAe(), 0);
			Assert.assertEquals(0.045, wksList.get(0).getCondicoesUsinagem().getVf(), 0);
			Assert.assertEquals(100.0, wksList.get(0).getCondicoesUsinagem().getVc(), 0);
			Assert.assertEquals(-15.0, wksList.get(0).getCondicoesUsinagem().getN(), 0);
			
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
		
		
	}
	@Test
	public void getAllWorkingstepsTest()
	{
		try {
			Vector ws = this.reader.getAllWorkingSteps();
			for(int i = 0; i < ws.size(); i++)
			{
				System.out.println("vector WS = " + ws.elementAt(i));
			}
		} catch (SdaiException e) {
			e.printStackTrace();
		} 
	}
	@Test
	public void setWorkingstepsPrecedentesTest()
	{
		Projeto projeto = reader.getProjeto();
		System.out.println(((Face)projeto.getBloco().faces.firstElement()).features.size());
		for(int i = 0;i < projeto.getWorkingsteps().size();i++)
		{
			System.out.println("Workingstep: " + projeto.getWorkingsteps().get(i));
			System.out.println("Workingstep precedente: " + (((Workingstep)projeto.getWorkingsteps().get(0).get(i)).getWorkingstepPrecedente()));
		}
	}
	
	@After
	public void closeProject() {
		try {
			util.closeProject();
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
}
