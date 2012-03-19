//package br.UFSC.GRIMA.integracao;
//
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//
//import javax.vecmath.Point3d;
//
//import jsdai.lang.ASdaiModel;
//import jsdai.lang.SdaiException;
//import junit.framework.Assert;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import br.UFSC.GRIMA.STEPmanager.Util;
//import br.UFSC.GRIMA.acceptance.STEP_NCReader;
//import br.UFSC.GRIMA.entidades.features.Feature;
//import br.UFSC.GRIMA.entidades.features.Furo;
//import br.UFSC.GRIMA.entidades.features.Ranhura;
//import br.UFSC.GRIMA.entidades.ferramentas.Broca;
//import br.UFSC.GRIMA.entidades.ferramentas.Fresa;
//
//public class STEP_NCReaderSlotTest {
//
//	public Util util = new Util();
//	public ASdaiModel model;
//	public STEP_NCReader reader;
//	public ArrayList<Feature> arrayList;
//
//	@Before
//	public void setUp() {
//		try {
//			model = util.openFile21(Util.getUserPath()
//					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21" );
//			reader = new STEP_NCReader(model);
//		} catch (SdaiException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void getFeaturesTest() {
//
//		// NAO ESTAMOS TESTANDO O NOME
//		String holeNameAnswer = "Hole 5.0mm";
//		//
//		Point3d holePositionAnswer = new Point3d(50.0, 50.0, 20.0);
//		double holeDiameterAnswer = 5.0;
//		double holeToolDiameterAnswer = 5.0;
//		double holeToolDepthAnswer = 15;
//		double holeToolTipAngleAnswer = 45;
//
//		int slotAxisAnswer = Ranhura.VERTICAL;
//		Point3d slotPositionAnswer = new Point3d(50.0, 0.0, 40.0);
//		double slotWidthAnswer = 20.0;
//		double slotDepthAnswer = 20.0;
//		// NAO ESTAMOS TESTANDO ESTES DOIS PARAMETROS AINDA
//		// INICIAREMOS OS TESTES QUANDO ESTIVER TRATANDO A FERRAMENTA
//		// DE FRESAMENTO.
//		String slotNameAnswer = "Slot1";
//		double slotToolDepthAnswer = 20.0;
//		double slotToolWidthAnswer = 20.0;
//		//		
//		double slotToolAp = 3.0;
//
//		try {
//			arrayList = reader.getFeatures();
//		} catch (SdaiException e) {
//			e.printStackTrace();
//			fail();
//		}
//		Furo furo = (Furo) arrayList.get(0);
//
//		
//		System.out.println(furo);
//		// FURO
//		Assert.assertEquals(holePositionAnswer, new Point3d(furo.getPosicaoX(),
//				furo.getPosicaoY(), furo.getPosicaoZ()));
//		Assert.assertEquals(holeDiameterAnswer, furo.getDiametro());
//		Assert.assertEquals(holeToolDiameterAnswer, furo.getFerramenta()
//				.getDiametroFerramenta());
//		Assert.assertEquals(holeToolDepthAnswer, furo.getFerramenta()
//				.getProfundidadeMaxima());
//		Assert.assertEquals(holeToolTipAngleAnswer, ((Broca) furo
//				.getFerramenta()).getTipAngle());
//
//		Ranhura ranhura = (Ranhura) arrayList.get(0);
//		// SLOT
//		
//		System.out.println(ranhura.getPosicaoZ());
//		Assert.assertEquals(slotAxisAnswer, ranhura.getEixo());
//		Assert.assertEquals(slotPositionAnswer, new Point3d(ranhura
//				.getPosicaoX(), ranhura.getPosicaoY(), ranhura.getPosicaoZ()));
//		Assert.assertEquals(slotWidthAnswer, ranhura.getLargura());
//		Assert.assertEquals(slotDepthAnswer, ranhura.getProfundidade());
//		Assert.assertEquals(slotToolAp, ((Fresa) ranhura.getFerramenta()).getAp());
//
//	}
//
//	@After
//	public void closeProject() {
//		try {
//			util.closeProject();
//		} catch (SdaiException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
