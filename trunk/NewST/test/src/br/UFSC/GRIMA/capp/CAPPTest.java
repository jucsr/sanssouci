package br.UFSC.GRIMA.capp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import junit.framework.Assert;

import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;

public class CAPPTest 
{
//	@Test
//	public void esolheFresa()
//	{
//		ArrayList<FaceMill> fresas = new ArrayList<FaceMill>();
//		FaceMill fresa = new FaceMill(10.0, 50);
//		FaceMill fresa2 = new FaceMill(15.0, 60);
//		FaceMill fresa3 = new FaceMill(12.0, 60);
//		FaceMill fresa4 = new FaceMill(20.0, 60);
//		
//		fresas.add(fresa);
//		fresas.add(fresa2);
//		fresas.add(fresa3);
//		fresas.add(fresa4);
//		
//		
//		try{
//			Ferramenta f = MapeadoraDeWorkingstep.escolheFresaDeDesbaste(Feature.RANHURA, fresas, 17.0, 40.0, Feature.LIMITE_DESBASTE);
//			Assert.assertEquals(fresa2.getDiametroFerramenta(), f.getDiametroFerramenta());
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			fail();
//		}
//	}
//	@Test
//	public void escolheBrocaTest()
//	{
//		ArrayList<TwistDrill> brocas = new ArrayList<TwistDrill>();
//		
//		TwistDrill broca = new TwistDrill(10.0, 50, 70);
//		TwistDrill broca2 = new TwistDrill(14.0, 50, 70);
//		TwistDrill broca3 = new TwistDrill(16.0, 50, 70);
//		TwistDrill broca4 = new TwistDrill(20.0, 50, 70);
//		
//		brocas.add(broca);
//		brocas.add(broca2);
//		brocas.add(broca3);
//		brocas.add(broca4);
//		
//		try
//		{
//			TwistDrill b = MapeadoraDeWorkingstep.escolheBrocaDeAcabamento(brocas, 16.0, 40);
//			Assert.assertEquals(broca3.getDiametroFerramenta(), b.getDiametroFerramenta());
//		}
//		catch(Exception e)
//		{
//			JOptionPane.showMessageDialog(null, "broca escolhida = null", "erro" , JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
//			fail();
//		}
//	}
	@Test
	public void escolheFerramentaTest()
	{
		ArrayList<FaceMill> fresas = new ArrayList<FaceMill>();
		ArrayList<TwistDrill> brocas = new ArrayList<TwistDrill>();
		
		FaceMill fresa = new FaceMill(10.0, 50);
		FaceMill fresa2 = new FaceMill(15.0, 60);
		FaceMill fresa3 = new FaceMill(12.0, 60);
		FaceMill fresa4 = new FaceMill(20.0, 60);
		
		fresas.add(fresa);
		fresas.add(fresa2);
		fresas.add(fresa3);
		fresas.add(fresa4);
		
		TwistDrill broca = new TwistDrill(10.0, 50, 70);
		TwistDrill broca2 = new TwistDrill(14.0, 50, 70);
		TwistDrill broca3 = new TwistDrill(16.0, 50, 70);
		TwistDrill broca4 = new TwistDrill(20.0, 50, 70);
		
		brocas.add(broca);
		brocas.add(broca2);
		brocas.add(broca3);
		brocas.add(broca4);
		
		//CAPP capp = new CAPP(fresas, brocas);
		//capp.escolherFerramenta();
	}
}
