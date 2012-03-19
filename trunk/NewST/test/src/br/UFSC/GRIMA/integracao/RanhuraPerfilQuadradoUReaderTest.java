package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.ESlot;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;

public class RanhuraPerfilQuadradoUReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	ESlot eSlot = null;
	
	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\RanhuraPerfilUQuadrado.p21" ));
			aWorkingstep = projectReader.getWorkingSteps();
			workingStepsReader = new WorkingStepsReader(aWorkingstep);
			
			for(int i = 0; i<workingStepsReader.getSize(); i++){
				if(workingStepsReader.get(i).getIts_feature(null).isKindOf(ESlot.class)){
					eSlot = (ESlot) workingStepsReader.get(i).getIts_feature(null);
				}
				if(eSlot!=null) i = workingStepsReader.getSize();
			}
			
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	 
	@Test
	public void getRanhuraTeste(){
		try {
			
			RanhuraPerfilQuadradoU ranhura = RanhuraPerfilQuadradoUReader.getRanhura(eSlot);
			
			String id = ranhura.getNome();
			Assert.assertEquals("Ranhura perfil U Quadrado",id);

			int eixo = ranhura.getEixo();
			Assert.assertEquals(Ranhura.HORIZONTAL, eixo);
			
			double larguraRanhura = ranhura.getLargura();
			Assert.assertEquals(23.547, larguraRanhura, 0.001);
			
			double profundidadeRanhura = ranhura.getProfundidade();
			Assert.assertEquals(10.0, profundidadeRanhura, 0);
			
			double raio = ranhura.getRaio();
			Assert.assertEquals(4.0, raio , 0);
			
			double angulo = ranhura.getAngulo();
			Assert.assertEquals(0.523, angulo, 0.001);
			
			double largura2 = ranhura.getLargura2();
			Assert.assertEquals(12.0, largura2, 0);
			
			double tolerancia = ranhura.getTolerancia();
			Assert.assertEquals(0.053245, tolerancia, 0.0001);
			
			double comprimento = ranhura.getComprimento();
			Assert.assertEquals(120, comprimento, 0);
			
			double X, Y, Z;
			X = ranhura.getPosicaoX();
			Y = ranhura.getPosicaoY();
			Z = ranhura.getPosicaoZ();
			Assert.assertEquals(0.0, X, 0);
			Assert.assertEquals(60.0, Y, 0);
			Assert.assertEquals(0.0, Z, 0);
			
			double locX = ranhura.getLocX();
			Assert.assertEquals(0.0, locX, 0);
			
			double locY = ranhura.getLocY();
			Assert.assertEquals(71.773, locY, 0.001);
			
			double locZ = ranhura.getLocZ();
			Assert.assertEquals(50.0, locZ, 0);
			
			
			
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
