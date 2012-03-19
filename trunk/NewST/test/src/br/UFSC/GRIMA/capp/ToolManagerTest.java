package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;

public class ToolManagerTest {

	@Test
	public void getFerramentasTest() {

		ToolManager toolManager = new ToolManager();
		
		System.out.println(" criou");
		
		int a = toolManager.getTwistDrills().size();
		int b = toolManager.getCenterDrills().size();
		int c = toolManager.getFaceMills().size();
		int d = toolManager.getEndMills().size();
		int e = toolManager.getBallEndMills().size();
		int f = toolManager.getBullnoseEndMills().size();
		int g = toolManager.getBoringTools().size();
		int h = toolManager.getReamers().size();
		
		Assert.assertEquals(108, a);
		Assert.assertEquals(54, b);
		Assert.assertEquals(55, c);
		Assert.assertEquals(50, d);
		Assert.assertEquals(56, e);
		Assert.assertEquals(55, f);
		Assert.assertEquals(21, g);
		Assert.assertEquals(126, h);
		
		
		ArrayList<TwistDrill> t = toolManager.createTwistDrills();
		
		for(int i=0;i<7;i++){
			System.out.println(t.get(i).getName());
		}
		
		
		System.out.println("fim");
	}

	
}
