package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraFuroBaseEsferica;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraFuroBaseEsfericaTest {

	Projeto projeto;
	Bloco bloco;
	FuroBaseEsferica furo;
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
//
//		projeto.getTwistDrills().get(14).setDiametoFerramenta(30);
//		projeto.getTwistDrills().get(14).setMaterial("P");
		
		//feature definition
		furo = new FuroBaseEsferica();
		furo.setRaio(13);
		furo.setProfundidade(12);
		furo.setFloorRadius(furo.getRaio());
		furo.setPosicao(15, 25, 50);
		furo.setNome("Furo com Base espferica");
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
	public void mapearFuroBaseEsferica(){
		
		MapeadoraFuroBaseEsferica mapeadoraFuroBaseEsferica = new MapeadoraFuroBaseEsferica(projeto, faceXY ,furo);
		
		Vector<Workingstep> wssFuro = furo.getWorkingsteps();
		
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
