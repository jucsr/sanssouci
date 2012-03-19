package br.UFSC.GRIMA.integracao;

import static org.junit.Assert.fail;

import java.util.Vector;

import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;

public class FuroBasePlanaReaderTest {
	
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
					+ "test\\res\\oneHoleOneSlotOneStepOnePoket.p21"));
//					+ "test\\res\\STEP-NC File.p21"));
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
			
			FuroBasePlana furo = FuroBasePlanaReader.getFuro(eRound_hole);
			
			double diametroFuro = furo.getDiametro();
			Assert.assertEquals(12.4, diametroFuro, 0);
			
			double x = furo.getPosicaoX();
			Assert.assertEquals(20.0, x, 0);
			
			double y = furo.getPosicaoY();
			Assert.assertEquals(10.0, y, 0);
			
			double z = furo.getPosicaoZ();
			Assert.assertEquals(0.0, z, 0);
			
			double prof = furo.getProfundidade();
			Assert.assertEquals(10.0, prof, 0);
			
			Assert.assertFalse(furo.isPassante());
			
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
			
	}
	
	@Test
	public void Teste(){

		Vector<String> vec = new Vector<String>();
		
		vec.add("b");
		vec.add("c");
		vec.add("d");
		vec.add("e");
		
		for(int i = 0; i<vec.size(); i++){
			System.out.println(vec.get(i));
			System.out.println(vec.elementAt(i));
		}
		
		System.out.println(vec.get(0).getClass());
		System.out.println(vec.elementAt(0).getClass());
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

