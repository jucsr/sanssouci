package br.UFSC.GRIMA.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToolReaderTest {

	ToolReader toolReader;
	
	@Before
	public void startReader(){
		
		toolReader = new ToolReader();
		toolReader.setFerramentaTable("TwistDrill");
		
	}
	

	@Test
	public void getSizeTest() {
		
		Assert.assertEquals(108, toolReader.getSize());
		
	}

	@Test
	public void getPropertieTest() {
		
		String nome = toolReader.getPropertie("Nome", 17);
		
		Assert.assertEquals("R840-1900-x0-AyA" , nome );
	}
	
	@Test
	public void getFerramentaByDiameter() {
		
		int id = toolReader.getFerramentaByDiameter(8.0);
		
		Assert.assertEquals(6, id);
	}
	
}
