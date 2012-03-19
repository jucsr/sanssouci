package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.lang.SdaiException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;

public class ProjectReaderTest {
	Util util = new Util();
	ProjectReader projectReader;

	@Before
	public void startObjects() {
		try {
			projectReader = new ProjectReader(util.openFile21(Util
					.getUserPath()
					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21"));
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getWorkpieceTeste() {
		try {
			EWorkpiece eWorkpiece = projectReader.getWorkpiece();
			String identifier = eWorkpiece.getIts_id(eWorkpiece);
			Assert.assertEquals("workpiece", identifier);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	public void getNomeTeste(){
		
		String name;
		try {
			name = projectReader.getNome();
			Assert.assertEquals("oneHoleOneSlotOneStepOnePoket", name);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void getWorkingStepsTeste(){
		
		AMachining_workingstep aWorkingstep;
		
		try {
			aWorkingstep = projectReader.getWorkingSteps();			
			Assert.assertEquals("(#50,#90,#127,#164)", aWorkingstep.toString());
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void getProjectTest() {
		try {
			EProject eProject = projectReader.getProject();
			Assert.assertNotNull(eProject);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getWorkplanTest() {
		try {
			EWorkplan eWorkplan = projectReader.getWorkplan();
			Assert.assertNotNull(eWorkplan);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	
//	@Test
//	public void getSurfaceTextureParameterTest(){
//		try {
//			ASurface_texture_parameter aSurface_texture_parameter = projectReader.getSurfaceTextureParameter();
//			Assert.assertEquals("#42=SURFACE_TEXTURE_PARAMETER(1.0,'Parameter Name','Method Measuring','Parameter Index',(#43,#44));", aSurface_texture_parameter.getByIndex(1).toString());
//		} catch (SdaiException e) {
//			e.printStackTrace();
//			fail();
//		}
//		
//		
//	}

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
