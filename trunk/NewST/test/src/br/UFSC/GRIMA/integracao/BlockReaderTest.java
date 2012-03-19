package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.lang.SdaiException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;

public class BlockReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	BlocoReader blockReader;
	EWorkpiece eWorkpiece;

	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21"));
			eWorkpiece = projectReader.getWorkpiece();			
			blockReader = new BlocoReader(eWorkpiece);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getNomeTeste() {
		try {
			String blockName = blockReader.getNome();
			Assert.assertEquals("piece",blockName);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getPositionTeste() {
		try {
			String blockPosition = blockReader.getPosition().toString();
			Assert.assertEquals("#7=AXIS2_PLACEMENT_3D('workpiece placement',#8,#9,#10);", blockPosition);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getComprimentoTeste() {
		try {
			double comprimento = blockReader.getComprimento();
			Assert.assertEquals(100.0, comprimento, 0);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getLarguraTeste() {
		try {
			double largura = blockReader.getLargura();
			Assert.assertEquals(120.0, largura, 0);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getProfundidadeTeste() {
		try {
			double profundidade = blockReader.getProfundidade();
			Assert.assertEquals(40.0, profundidade, 0);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void getMaterialTeste() {
		try {
			String material = blockReader.getMaterial();
			Assert.assertEquals("SAE 1020", material);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void getToleranciaGlobalTeste() {
		try {
			double tolerancia = blockReader.getToleranciaGlobal();
			Assert.assertEquals(0.05, tolerancia, 0.0);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void closeProject() {
		try {
			util.closeProject();
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
