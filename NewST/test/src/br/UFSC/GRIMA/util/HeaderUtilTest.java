package br.UFSC.GRIMA.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.ProjectManager;
import br.UFSC.GRIMA.entidades.STEPWorkingStepsProject;

public class HeaderUtilTest {
	STEPWorkingStepsProject project;
	String file21;
	
	@Before
	public void cleanHeader() {
		try {
			ProjectManager.clearProject();
			project = ProjectManager.getProject();

			project.setProject("Hole");

			project.setMaterial("St50", "Stahl", "E210000 N/MM^2");

			project.setWorkPiece("Bauteil1", 110.000, 110.000, 40.000);

			project.addHoleWorkingStep("Hole0", 0.01, 10, 50.000, 50.000,
					50.000,""+15,""+45);

			project.addHoleWorkingStep("Hole1", 0.02, 12, 80.000, 80.000,
					50.000,""+15,""+45);

			project.addHoleWorkingStep("Hole2", 0.03, 13, 90.000, 90.000,
					50.000,""+15,""+45);

			project.relationateVars();
			file21 = project.createSTEP21File();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void changeHeader(){
		try{
			String answer = HeaderUtil.changeHeader(file21, "Generated at GRIMA");
			System.out.println("HERE: "+answer);
			Assert.assertNotNull(answer);
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void destroyObjects(){
		try {
			ProjectManager.clearProject();
			project.closeSession();
		} catch (SdaiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
