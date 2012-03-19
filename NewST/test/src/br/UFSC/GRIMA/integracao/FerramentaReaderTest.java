package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;

import java.util.Vector;

import jsdai.lang.SdaiException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.acceptance.STEP_NCReader;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class FerramentaReaderTest {

	Util util = new Util();
	STEP_NCReader reader;

	@Before
	public void startObjects() {
		try {

			reader = new STEP_NCReader(util.openFile21(Util.getUserPath()
					+ "test\\res\\testBoxy.p21"));

		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAllFerramentasTeste() {

		Vector<Vector<Workingstep>> allWorkingstes;

		try {

			allWorkingstes = reader.getAllWorkingSteps();

			for (int i = 0; i < allWorkingstes.size(); i++) {

				Vector<Workingstep> wssTmp = allWorkingstes.get(i);
				
				for(int j = 0; j < wssTmp.size(); j++){
					
					Workingstep wsTmp = wssTmp.get(j);
					
					Ferramenta ferrTmp = wsTmp.getFerramenta();
					
					System.out.println("Classe: "+ferrTmp.getClass());
					System.out.println("Nome : " +ferrTmp.getName());
				}

			}

		} catch (SdaiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
