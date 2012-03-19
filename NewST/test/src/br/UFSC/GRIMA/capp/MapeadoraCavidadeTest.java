package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraCavidade;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraCavidadeTest {

	Projeto projeto;
	Bloco bloco;
	Cavidade cavidade;
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
		faceXY.features.addElement(cavidade);
		
		// ---- feature definition ----
		cavidade = new Cavidade();
		cavidade.setComprimento(80);
		cavidade.setLargura(50);
		cavidade.setProfundidade(10);
		cavidade.setPosicao(10, 50, 0);
		cavidade.setRaio(15);
		cavidade.setNome("Cavidade 1");
		Point3d coordinates = new Point3d(70 + 40, 50 + 25, 50 - 0);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		cavidade.setPosition(position);
		cavidade.setPassante(true);
		cavidade.setTolerancia(0.05);
		cavidade.setRugosidade(0.05);
		
	}
	
	@Test
	public void mapearCavidadeTest(){
		
		MapeadoraCavidade mapeadoraCavidade = new MapeadoraCavidade(projeto, faceXY ,cavidade);
		
		Vector<Workingstep> wssCavidade = cavidade.getWorkingsteps();
		
		CondicoesDeUsinagem cond = null;
		
		for(int i = 0; i< wssCavidade.size(); i++){
			
			cond = wssCavidade.get(i).getCondicoesUsinagem();
			//vc, f, vf, n, ap, ae

			System.out.println("vc = " + cond.getVc());
			System.out.println("f = " + cond.getF());
			System.out.println("vf = " + cond.getVf());
			System.out.println("n = " + cond.getN());
			System.out.println("ap = " + cond.getAp());
			System.out.println("ae = " + cond.getAe());
			System.out.println();
		}
		
		System.out.println("Size = " + wssCavidade.size());
		System.out.println();

		for(int i = 0; i<wssCavidade.size();i++){
			System.out.println(wssCavidade.get(i).getOperation());
			System.out.println(wssCavidade.get(i).getOperation().getStartPoint());
			System.out.println(wssCavidade.get(i).getFerramenta().getClass());
			System.out.println(wssCavidade.get(i).getFerramenta().getDiametroFerramenta());
			System.out.println();
		}
		
	}
}
