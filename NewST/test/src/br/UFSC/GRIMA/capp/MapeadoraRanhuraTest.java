package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraRanhura;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhuraTest {

	Projeto projeto;
	Bloco bloco;
	Ranhura ranhura;
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
		
		//adicionando feature na face
		faceXY = (Face)(bloco.faces.elementAt(Face.XY));
		faceXY.features.addElement(ranhura);
		
		// ---- feature definition ----
		ranhura = new Ranhura();
		ranhura.setNome("Ranhura normal");
		ranhura.setLargura(3);
		ranhura.setProfundidade(5);
		ranhura.setPosicao(30, 0, 0);
		ranhura.setEixo(Ranhura.VERTICAL);
		ranhura.setComprimento(100);
		ranhura.setTolerancia(0.0055);
		ranhura.setTolerancia(0.0055);
		Point3d coordinates = new Point3d(ranhura.X + ranhura.getLargura() / 2, ranhura.Y, faceXY.getProfundidadeMaxima() - ranhura.Z); // adicionei a altura do bloco
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("ranhura Placement");
		ranhura.setPosition(position);
		
	}
	
	@Test
	public void getCondicoesDeUsinagemTest(){
		
		MapeadoraRanhura mapeadoraRanhura = new MapeadoraRanhura(projeto, faceXY ,ranhura);
		
		CondicoesDeUsinagem cond = null;
		
		for(int i = 0; i< ranhura.getWorkingsteps().size(); i++){
			
			cond = ranhura.getWorkingsteps().get(i).getCondicoesUsinagem();
			//vc, f, vf, n, ap, ae

			System.out.println("vc = " + cond.getVc());
			System.out.println("f = " + cond.getF());
			System.out.println("vf = " + cond.getVf());
			System.out.println("n = " + cond.getN());
			System.out.println("ap = " + cond.getAp());
			System.out.println("ae = " + cond.getAe());
			System.out.println();
		}
		
	}
	
	@Test
	public void mapearRanhuraTest(){
		
		MapeadoraRanhura mapeadoraRanhura = new MapeadoraRanhura(projeto, faceXY ,ranhura);
		
		Vector<Workingstep> wssRanhura = ranhura.getWorkingsteps();
		
		Assert.assertNotNull(wssRanhura);
		
		System.out.println("Size = " + wssRanhura.size());
		System.out.println();

		for(int i = 0; i<wssRanhura.size();i++){
			System.out.println(wssRanhura.get(i).getOperation());
			System.out.println(wssRanhura.get(i).getOperation().getStartPoint());
			System.out.println(wssRanhura.get(i).getFerramenta().getClass());
			System.out.println(wssRanhura.get(i).getFerramenta().getDiametroFerramenta());
			System.out.println();
		}
		
	}
}
