package br.UFSC.GRIMA.capp;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraDeWorkingStepTest 
{
	private Projeto projeto;
	private MapeadoraDeWorkingsteps mapeadoraDeWorkingstep;
	
	@Before
	public void createProject(){
		
		ArrayList<FaceMill> fresas = new ArrayList<FaceMill>();
		ArrayList<TwistDrill> brocas = new ArrayList<TwistDrill>();
		
		FaceMill fresa12 = new FaceMill(2.0, 10);
		fresa12.setMaterialClasse(Ferramenta.P);
		fresa12.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa11 = new FaceMill(3.0, 15);
		fresa11.setMaterialClasse(Ferramenta.P);
		fresa11.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa10 = new FaceMill(4.0, 20);
		fresa10.setMaterialClasse(Ferramenta.P);
		fresa10.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa9 = new FaceMill(5.0, 41);
		fresa9.setMaterialClasse(Ferramenta.P);
		fresa9.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa = new FaceMill(6.0, 41);
		fresa.setMaterialClasse(Ferramenta.P);
		fresa.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa1 = new FaceMill(8.0, 45);
		fresa1.setMaterialClasse(Ferramenta.P);
		fresa1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa2 = new FaceMill(10.0, 41);
		fresa2.setMaterialClasse(Ferramenta.P);
		fresa2.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa3 = new FaceMill(12.0, 60);
		fresa3.setMaterialClasse(Ferramenta.P);
		fresa3.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa13 = new FaceMill(13.0, 60);
		fresa13.setMaterialClasse(Ferramenta.P);
		fresa13.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa4 = new FaceMill(14.0, 60);
		fresa4.setMaterialClasse(Ferramenta.P);
		fresa4.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa5 = new FaceMill(16.0, 60);
		fresa5.setMaterialClasse(Ferramenta.P);
		fresa5.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa6 = new FaceMill(16.0, 60);
		fresa6.setMaterialClasse(Ferramenta.P);
		fresa6.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa7 = new FaceMill(18.0, 60);
		fresa7.setMaterialClasse(Ferramenta.P);
		fresa7.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa15 = new FaceMill(19.0, 60);
		fresa15.setMaterialClasse(Ferramenta.P);
		fresa15.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa8 = new FaceMill(20.0, 60);
		fresa8.setMaterialClasse(Ferramenta.P);
		fresa8.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		FaceMill fresa14 = new FaceMill(25.0, 60);
		fresa14.setMaterialClasse(Ferramenta.P);
		fresa14.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);

		fresas.add(fresa12);
		fresas.add(fresa11);
		fresas.add(fresa10);
		fresas.add(fresa9);
		fresas.add(fresa);
		fresas.add(fresa1);
		fresas.add(fresa2);
		fresas.add(fresa3);
		fresas.add(fresa13);
		fresas.add(fresa4);
		fresas.add(fresa5);
		fresas.add(fresa6);
		fresas.add(fresa7);
		fresas.add(fresa8);
		fresas.add(fresa15);
		fresas.add(fresa14);
		
		
		TwistDrill broca8 = new TwistDrill(2.0, 41, 70);
		broca8.setMaterialClasse(Ferramenta.P);
		broca8.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca7 = new TwistDrill(4.0, 41, 70);
		broca7.setMaterialClasse(Ferramenta.P);
		broca7.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca6 = new TwistDrill(6.0, 41, 70);
		broca6.setMaterialClasse(Ferramenta.P);
		broca6.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca5 = new TwistDrill(8.0, 41, 70);
		broca5.setMaterialClasse(Ferramenta.P);
		broca5.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca = new TwistDrill(10.0, 50, 70);
		broca.setMaterialClasse(Ferramenta.P);
		broca.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca9 = new TwistDrill(13.0, 50, 70);
		broca9.setMaterialClasse(Ferramenta.P);
		broca9.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca2 = new TwistDrill(14.0, 50, 70);
		broca2.setMaterialClasse(Ferramenta.P);
		broca2.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca3 = new TwistDrill(16.0, 50, 70);
		broca3.setMaterialClasse(Ferramenta.P);
		broca3.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		TwistDrill broca4 = new TwistDrill(20.0, 50, 70);
		broca4.setMaterialClasse(Ferramenta.P);
		broca4.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);

		brocas.add(broca8);		
		brocas.add(broca7);
		brocas.add(broca6);
		brocas.add(broca5);
		brocas.add(broca);
		brocas.add(broca9);
		brocas.add(broca2);
		brocas.add(broca3);
		brocas.add(broca4);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO);
		PropertyParameter properties = new PropertyParameter();
		properties.setParameterName("Hardness");
		properties.setParameterUnit("HB");
		properties.setParameterValue(250);
		ArrayList<PropertyParameter> prop = new ArrayList<PropertyParameter>();
		prop.add(properties);
		material.setProperties(prop);
		
		Bloco bloco = new Bloco(200.0, 150.0, 40.0);
		bloco.setMaterial(material);
		
		DadosDeProjeto dados = new DadosDeProjeto(0, "joao ninguem", "novo projeto", 0);
		
		this.projeto = new Projeto(null, bloco, dados);
		this.projeto.setFaceMills(fresas);
		this.projeto.setTwistDrills(brocas);
		
		
	}
	
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
//		try{
//			Ferramenta f = MapeadoraDeWorkingstep.escolheFresaDeDesbaste(Feature.RANHURA, fresas, 16.0, 50.0, Feature.LIMITE_DESBASTE);
//			Assert.assertEquals(fresa2.getDiametroFerramenta(), f.getDiametroFerramenta());
//		}
//		catch(Exception e)
//		{
//			JOptionPane.showMessageDialog(null, "fresa escolhida = null", "erro" , JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
//			fail();
//		}
//	}
//	
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
//			Ferramenta b = MapeadoraDeWorkingstep.escolheBrocaDeAcabamento(brocas, 16.0, 40.0);
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
	public void mapearWorkingstepsTest()
	{
		
		
		Furo furo = new Furo();
		furo.setPosicao(10, 10, 0);
		furo.setProfundidade(40);
		furo.setRaio(7.0);
		furo.setAcabamento(true);
		
		Ranhura ranhura = new Ranhura();
		ranhura.setLargura(14);
		ranhura.setProfundidade(10);
		ranhura.setPosicao(0, 10, 0);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setAcabamento(true);
		
		Degrau degrau = new Degrau();
		degrau.setPosicao(0, 50, 0);
		degrau.setProfundidade(7.0);
		degrau.setLargura(12.0);
		degrau.setAcabamento(true);
		
		Cavidade cavidade = new Cavidade();
		cavidade.setComprimento(80);
		cavidade.setLargura(50);
		cavidade.setProfundidade(40);
		cavidade.setPosicao(70, 60, 0);
		cavidade.setRaio(7.5);
		cavidade.setAcabamento(true);
		
		Face face = (Face)projeto.getBloco().faces.elementAt(Face.XY);
//		face.features.add(furo);
//		face.features.add(ranhura);
//		face.features.add(degrau);
		face.features.add(cavidade);
		
		this.mapeadoraDeWorkingstep = new MapeadoraDeWorkingsteps(this.projeto);
		
		Vector wssFaces = (Vector) (this.mapeadoraDeWorkingstep.getWorkingsteps());
		Vector wssFace1 = (Vector) wssFaces.get(0);
		//Assert.assertEquals(4, wssFace1.size());
		
	//	System.out.println("Size = " + wssFace1.size() );
		
		for(int i=0; i<wssFace1.size(); i++){
			
			Workingstep wstemp = (Workingstep) wssFace1.get(i); 

			System.err.println(" ============================================ ");
			System.out.println("Feature =" + wstemp.getFeature().getTipoString());
			System.out.println("ferramenta "+i + ": ");
			
			System.out.println("ws ferr diam = " + wstemp.getFerramenta().getDiametroFerramenta());
			System.out.println("ws ferr prof = " + wstemp.getFerramenta().getProfundidadeMaxima());
			System.out.println("ws ferr N = " + wstemp.getCondicoesUsinagem().getN());
			System.out.println("ws ferr Vc = " + wstemp.getCondicoesUsinagem().getVc());
			System.out.println("ws ferr F = " + wstemp.getCondicoesUsinagem().getF());
			System.out.println("ws ferr Vf = " + wstemp.getCondicoesUsinagem().getVf());
			System.out.println("ws ferr ap = " + wstemp.getCondicoesUsinagem().getAp());
			System.out.println("ws ferr ae = " + wstemp.getCondicoesUsinagem().getAe());
			
			
		}
		
//
//		try {
//			StepNcProject stepNcProject = new StepNcProject(this.mapeadoraDeWorkingstep.getWorkingsteps(), this.projeto);
//			//System.out.println(stepNcProject.createSTEP21FileWithoutHeader());
//		} catch (SdaiException e) {
//			e.printStackTrace();
//			fail();
//		}
		
		//CAPP capp = new CAPP(fresas, brocas);
		//capp.escolherFerramenta();
	}
	
//	@Test
//	public void getCondicoesDeUsinagemTest(){
//		
//		TwistDrill broca = new TwistDrill(10.0, 50, 70);
//		broca.setMaterial("P");
//
//		Material materialBloco = new Material("Aço Baixa Liga", Material.ACO_BAIXA_LIGA);
//		MapeadoraDeWorkingstep mapWs = new MapeadoraDeWorkingstep();
//		
//		CondicoesDeUsinagem cond = mapWs.getCondicoesDeUsinagem(broca, materialBloco);
//		
//		Assert.assertEquals(cond.getF(),0.15, 0);
//		Assert.assertEquals(cond.getVc(),95, 0);
//		
//		
//	}
}
