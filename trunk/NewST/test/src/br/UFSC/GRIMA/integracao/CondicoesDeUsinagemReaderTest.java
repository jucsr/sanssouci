package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;

public class CondicoesDeUsinagemReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	CondicoesDeUsinagem condicoesDeUsinagem;
	
	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21"));
			aWorkingstep = projectReader.getWorkingSteps();
			System.out.println(aWorkingstep);
			workingStepsReader = new WorkingStepsReader(aWorkingstep);
			
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCondicoesTeste(){
		try {
			condicoesDeUsinagem  = CondicoesDeUsinagemReader.getCondicoes(workingStepsReader.get(1));
			Assert.assertEquals(5.0, condicoesDeUsinagem.getAp(), 0);
			Assert.assertEquals(7.5, condicoesDeUsinagem.getAe(), 0);
			Assert.assertEquals(0.1, condicoesDeUsinagem.getVf(), 0);
			Assert.assertEquals(100.0, condicoesDeUsinagem.getVc(), 0);
			Assert.assertEquals(-10, condicoesDeUsinagem.getN(), 0);
			
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
