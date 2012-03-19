package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraFuroBasePlana;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraFuroBasePlanaTest {

	Projeto projeto;
	Bloco bloco;
	FuroBasePlana furo;
	Face faceXY;
	
	@Before
	public void createProject(){
		

		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
		
		new ToolManager(projeto);
		
		// ---- feature definition ----
		furo = new FuroBasePlana();
		furo.setNome("FURO 1");
		furo.setPosicao(20, 60, 0);
		furo.setProfundidade(6);
		furo.setRaio(8);
		furo.setPassante(false);
		furo.setTolerancia(0.01);
		furo.setRugosidade(0.01);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		
		faceXY = (Face)(bloco.faces.elementAt(Face.XY));
		faceXY.features.addElement(furo);
	}
	
	@Test
	public void getCondicoesDeUsinagemTest(){
		
		MapeadoraFuroBasePlana mapeadoraFuroBasePlana = new MapeadoraFuroBasePlana(projeto);
		
		TwistDrill twistDrill = projeto.getTwistDrills().get(0);
		
		//Teste de Diametros Nao Convencionais
//		twistDrill.setDiametoFerramenta(2.99);
//		twistDrill.setDiametoFerramenta(20.01);
//		twistDrill.setDiametoFerramenta(-3.00);
//		twistDrill.setDiametoFerramenta(0.00);
		
		CondicoesDeUsinagem cond = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(projeto, twistDrill, bloco.getMaterial());
		
		//vc, f, vf, n, ap, ae
		
		System.out.println("vc = " + cond.getVc());
		System.out.println("f = " + cond.getF());
		System.out.println("vf = " + cond.getVf());
		System.out.println("n = " + cond.getN());
		System.out.println("ap = " + cond.getAp());
		System.out.println("ae = " + cond.getAe());
		
	}
	
	@Test
	public void mapearFuroBasePlana(){
		
		MapeadoraFuroBasePlana mapeadoraFuroBasePlana = new MapeadoraFuroBasePlana(projeto, faceXY ,furo);
		
		Vector<Workingstep> wssFuro = furo.getWorkingsteps();
		
		Assert.assertNotNull(wssFuro);
		
		Assert.assertNotNull(wssFuro);
		
		System.out.println("Size = " + wssFuro.size());
		System.out.println();

		for(int i = 0; i<wssFuro.size();i++){
			System.out.println(wssFuro.get(i).getOperation());
			System.out.println(wssFuro.get(i).getOperation().getStartPoint());
			System.out.println(wssFuro.get(i).getFerramenta().getClass());
			System.out.println(wssFuro.get(i).getFerramenta().getDiametroFerramenta());
			System.out.println();
		}
		
	}
	
}
