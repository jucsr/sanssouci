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

public class RanhuraReaderTest {
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
					+ "test\\res\\Ranhura.p21" ));
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
			
			Ranhura ranhura = RanhuraReader.getRanhura(eSlot);
			
			String id = ranhura.getNome();
			Assert.assertEquals("Ranhura normal", id);
			
			int eixo = ranhura.getEixo();
			Assert.assertEquals(Ranhura.VERTICAL, eixo);
			
			double larguraRanhura = ranhura.getLargura();
			Assert.assertEquals(25.0, larguraRanhura, 0);
			
			double profundidadeRanhura = ranhura.getProfundidade();
			Assert.assertEquals(10.0, profundidadeRanhura, 0);
			
			double tolerancia = ranhura.getTolerancia();
			Assert.assertEquals(0.055, tolerancia, 0);
			
			double comprimento = ranhura.getComprimento();
			Assert.assertEquals(100 , comprimento, 0);
			
			double x = ranhura.getPosicaoX();
			Assert.assertEquals(30.0, x, 0);
			
			double y = ranhura.getPosicaoY();
			Assert.assertEquals(00.0, y, 0);
			
			double z = ranhura.getPosicaoZ();
			Assert.assertEquals(00.0, z, 0);
			
			double locX = ranhura.getLocX();
			Assert.assertEquals(42.5, locX, 0);
			
			double locY = ranhura.getLocY();
			Assert.assertEquals(00.0, locY, 0);
			
			double locZ = ranhura.getLocZ();
			Assert.assertEquals(50.0, locZ, 0);
			
//			Vector trajetorias = ranhura.getTrajetoriasDeMovimentacao();
//			int size = trajetorias.size();
//			
//			System.out.println("Trajetoria 1 = "+trajetorias.get(0));
//			System.out.println("Trajetoria 2 = "+trajetorias.get(1));
//			System.out.println("Trajetoria 3 = "+trajetorias.get(2));
//			System.out.println("Trajetoria 4 = "+trajetorias.get(3));
//			
//			Assert.assertEquals(4,size);
//			
//			Fresa fresa = (Fresa) ranhura.getFerramenta();
//			
//			double diametroFresa = fresa.getDiametroFerramenta();
//			Assert.assertEquals(10.0, diametroFresa, 0);
//			
//			double profundidadeFresa = fresa.getProfundidadeMaxima();
//			Assert.assertEquals(70.0, profundidadeFresa, 0);
//			
//			double tipAngle = fresa.getAp();
//			Assert.assertEquals(5.0, tipAngle, 0);
			
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
