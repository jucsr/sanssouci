package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.EClosed_pocket;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.features.Cavidade;

public class CavidadeReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	EClosed_pocket eClosed_Pocket = null;
	
	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\Cavidade.p21"));
			aWorkingstep = projectReader.getWorkingSteps();
			workingStepsReader = new WorkingStepsReader(aWorkingstep);
			
			for(int i = 0; i<workingStepsReader.getSize(); i++){
				if(workingStepsReader.get(i).getIts_feature(null).isKindOf(EClosed_pocket.class)){
					eClosed_Pocket = (EClosed_pocket) workingStepsReader.get(i).getIts_feature(null);
				}
				if(eClosed_Pocket!=null) i = workingStepsReader.getSize();
			}
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCavidadeTeste(){
		try {
			
			Cavidade cavidade = CavidadeReader.getCavidade(eClosed_Pocket);
			
			String nome = cavidade.getNome();
			Assert.assertEquals("Cavidade 1", nome);
			
			double raioCavidade = cavidade.getRaio();
			Assert.assertEquals(10.0, raioCavidade, 0);
			
			double larguraCavidade = cavidade.getLargura();
			Assert.assertEquals(50.0, larguraCavidade, 0);
			
			double comprimentoCavidade = cavidade.getComprimento();
			Assert.assertEquals(80.0, comprimentoCavidade, 0);
			
			double profundidadeCavidade = cavidade.getProfundidade();
			Assert.assertEquals(30.0, profundidadeCavidade, 0);
			
			double tolerancia = cavidade.getTolerancia();
			Assert.assertEquals(0.06, tolerancia, 0);
			
			double x = cavidade.getPosicaoX();
			Assert.assertEquals(70.0, x, 0);
			
			double y = cavidade.getPosicaoY();
			Assert.assertEquals(50.0, y, 0);
			
			double z = cavidade.getPosicaoZ();
			Assert.assertEquals(0.0, z, 0);
			
			double locX = cavidade.getLocX();
			Assert.assertEquals(110.0, locX, 0);
			
			double locY = cavidade.getLocY();
			Assert.assertEquals(75.0, locY, 0);
			
			double locZ = cavidade.getLocZ();
			Assert.assertEquals(50.0, locZ, 0);
			
			
//			Vector trajetorias = cavidade.getTrajetoriasDeMovimentacao();
//			int size = trajetorias.size();
//			
//			System.out.println("Trajet�ria 1 = "+trajetorias.get(0));
//			System.out.println("Trajet�ria 2 = "+trajetorias.get(1));
//			
//			Assert.assertEquals(2,size);
//			
//			Fresa fresa = (Fresa) cavidade.getFerramenta();
//			
//			double diametroFresa = fresa.getDiametroFerramenta();
//			Assert.assertEquals(20.0, diametroFresa, 0);
//			
//			double profundidadeFresa = fresa.getProfundidadeMaxima();
//			Assert.assertEquals(120.0, profundidadeFresa, 0);
//			
//			double tipAngle = fresa.getAp();
//			Assert.assertEquals(2.0, tipAngle, 0);
			
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
