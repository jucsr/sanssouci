package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.EStep;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.features.Degrau;

public class DegrauReaderTest {
	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	EStep eStep = null;
	
	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21" ));
			aWorkingstep = projectReader.getWorkingSteps();
			workingStepsReader = new WorkingStepsReader(aWorkingstep);
			
			for(int i = 0; i<workingStepsReader.getSize(); i++){
				if(workingStepsReader.get(i).getIts_feature(null).isKindOf(EStep.class)){
					eStep = (EStep) workingStepsReader.get(i).getIts_feature(null);
				}
				if(eStep!=null) i = workingStepsReader.getSize();
			}
			
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	 
	@Test
	public void getDegrauTeste(){
		try {
			Degrau degrau = DegrauReader.getDegrau(eStep);
			
			double larguraDegrau = degrau.getLargura();
			Assert.assertEquals(13.0, larguraDegrau, 0);
			
			double profundidadeDegrau = degrau.getProfundidade();
			Assert.assertEquals(12.0, profundidadeDegrau, 0);
			
			int eixo = degrau.getEixo();
			Assert.assertEquals(Degrau.HORIZONTAL, eixo);
			
			double x = degrau.getPosicaoX();
			Assert.assertEquals(87.0, x, 0);
			
			double y = degrau.getPosicaoY();
			Assert.assertEquals(00.0, y, 0);
			
			double z = degrau.getPosicaoZ();
			Assert.assertEquals(0.0, z, 0);
			
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
