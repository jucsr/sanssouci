package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;

public class FuroBaseConicaReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	ERound_hole eRound_hole = null;
	
	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\FuroBaseConica.p21"));
			aWorkingstep = projectReader.getWorkingSteps();
			workingStepsReader = new WorkingStepsReader(aWorkingstep);
			
			for(int i = 0; i<workingStepsReader.getSize(); i++){
				if(workingStepsReader.get(i).getIts_feature(null).isKindOf(ERound_hole.class)){
					eRound_hole = (ERound_hole) workingStepsReader.get(i).getIts_feature(null);
				}
				if(eRound_hole!=null) i = workingStepsReader.getSize();
			}
			
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFuroTeste(){
		try {
			FuroBaseConica furo = FuroBaseConicaReader.getFuro(eRound_hole);
			
			double diametroFuro = furo.getDiametro();
			Assert.assertEquals(18.0, diametroFuro, 0);
			
			double x = furo.getPosicaoX();
			Assert.assertEquals(30.0, x, 0);
			
			double y = furo.getPosicaoY();
			Assert.assertEquals(25.0, y, 0);
			
			double z = furo.getPosicaoZ();
			Assert.assertEquals(-50.0, z, 0);
			
			double prof = furo.getProfundidade();
			Assert.assertEquals(10.0, prof, 0);
			
			double tipAngle = furo.getTipAngle();
			Assert.assertEquals(1.2217, tipAngle,0.001);

			double tolerancia = furo.getTolerancia();
			Assert.assertEquals(0.05, tolerancia, 0);
			
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
