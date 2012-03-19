package br.UFSC.GRIMA.entidades;

import static org.junit.Assert.*;

import org.junit.Test;

public class STEPProjectTest {

	@Test
	public void begginingTest() {

		try {
			STEPProject project = new STEPProject();
			project.closeSession();
		} catch (Exception e) {
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void parametersTest() {
		
		try {
			STEPProject project =  ProjectManager.getProject();
			project.setProject("Hole");
			project.setWorkPiece("Bauteil1",110.000,110.000,50);
			project.setMaterial("SAE1020", "Steel", "E210000 N/MM^2");
			project.relationateVars();
			project.createSTEP21File();
			project.closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	

}
