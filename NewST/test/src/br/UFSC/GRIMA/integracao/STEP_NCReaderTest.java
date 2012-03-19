package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
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
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;

public class STEP_NCReaderTest {

	public Util util = new Util();
	public ASdaiModel model;
	public STEP_NCReader reader;

	@Before
	public void setUp() {
		try {
			model = util.openFile21(Util.getUserPath()
					+ "test\\res\\testBoxy.p21" );
			reader = new STEP_NCReader(model);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}

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
	
	@After
	public void closeProject() {
		try {
			util.closeProject();
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
}
