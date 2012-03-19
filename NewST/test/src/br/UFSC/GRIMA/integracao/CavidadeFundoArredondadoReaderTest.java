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
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;

public class CavidadeFundoArredondadoReaderTest {

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
					+ "test\\res\\onePocket"));
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
			
			CavidadeFundoArredondado cavidade = CavidadeFundoArredondadoReader.getCavidade(eClosed_Pocket);
			
			double x = cavidade.getPosicaoX();
			Assert.assertEquals(70.0, x, 0);
			
			double y = cavidade.getPosicaoY();
			Assert.assertEquals(60.0, y, 0);
			
			double z = cavidade.getPosicaoZ();
			Assert.assertEquals(0.0, z, 0);
			
			double verticeRaio = cavidade.getVerticeRaio();
			Assert.assertEquals(10.0, verticeRaio, 0);
			
			double fundoRaio = cavidade.getFundoRaio();
			Assert.assertEquals(10.0, fundoRaio, 0);
			
			double largura = cavidade.getLargura();
			Assert.assertEquals(50.0, largura, 0);
			
			double comprimento = cavidade.getComprimento();
			Assert.assertEquals(80.0, comprimento, 0);
			
			double profundidade = cavidade.getProfundidade();
			Assert.assertEquals(30.0, profundidade, 0);
			
			
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
