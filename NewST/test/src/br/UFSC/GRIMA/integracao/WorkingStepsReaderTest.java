package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;

public class WorkingStepsReaderTest {

	Util util = new Util();
	ProjectReader projectReader;
	AMachining_workingstep aWorkingstep;
	WorkingStepsReader workingStepsReader;
	
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
	public void getTeste(){
		EMachining_workingstep eWorkingstep;
		try {
			eWorkingstep = workingStepsReader.get(2);
			Assert.assertEquals("#127=MACHINING_WORKINGSTEP('WS Step 2',#19,#107,#95,$);", eWorkingstep.toString());
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
			
	}
	
	@Test
	public void getSizeTeste(){
		int size;
		try {
			size = workingStepsReader.getSize();
			Assert.assertEquals(4, size);
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
