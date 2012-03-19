package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraRanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhuraPerfilQuadradoUTest {

	Projeto projeto;
	Bloco bloco;
	RanhuraPerfilQuadradoU slot;
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
		faceXY.features.addElement(slot);
		
		// ---- feature definition ----
		slot = new RanhuraPerfilQuadradoU();
		slot.setPosicao(0, 60, 0);
		slot.setNome("Ranhura perfil U Quadrado");
//		slot.setLargura(21);
		slot.setLargura2(12);
		slot.setComprimento(120);
		slot.setRaio(4.0);
		slot.setAngulo(30);
		slot.setProfundidade(4);
		slot.setTolerancia(0.0053245);
		slot.setRugosidade(0.0053245);
		slot.setEixo(Ranhura.HORIZONTAL);
		Point3d coordinates = new Point3d(0, slot.Y + slot.getProfundidade() * Math.tan(slot.getAngulo() * Math.PI  / 180) + slot.getLargura2() / 2, faceXY.getProfundidadeMaxima() - slot.Z); // por causa da profundidade
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("Slot placement");
		slot.setPosition(position);
		
	}
	
	@Test
	public void getCondicoesDeUsinagemTest(){
		
		MapeadoraRanhuraPerfilQuadradoU mapeadoraRanhura = new MapeadoraRanhuraPerfilQuadradoU(projeto, faceXY ,slot);
		
		CondicoesDeUsinagem cond = null;
		
		for(int i = 0; i< slot.getWorkingsteps().size(); i++){
			
			cond = slot.getWorkingsteps().get(i).getCondicoesUsinagem();
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
	public void mapearMapeadoraRanhuraPerfilQuadradoUTest(){
		
		MapeadoraRanhuraPerfilQuadradoU mapeadoraRanhura = new MapeadoraRanhuraPerfilQuadradoU(projeto, faceXY ,slot);
		
		Vector<Workingstep> wssRanhura = slot.getWorkingsteps();
		
		Assert.assertNotNull(wssRanhura);
		
		System.out.println("Size = " + wssRanhura.size());
		System.out.println();

		for(int i = 0; i<wssRanhura.size();i++){
			System.out.println(wssRanhura.get(i).getOperation());
			System.out.println(wssRanhura.get(i).getOperation().getStartPoint());
			System.out.println(wssRanhura.get(i).getFerramenta().getClass());
			System.out.println("Diametro = " + wssRanhura.get(i).getFerramenta().getDiametroFerramenta());
			System.out.println("Prof Max = " + wssRanhura.get(i).getFerramenta().getProfundidadeMaxima());
			System.out.println();
		}
		
	}
	
	
}
