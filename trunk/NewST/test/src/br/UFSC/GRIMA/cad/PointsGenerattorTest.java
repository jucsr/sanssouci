package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class PointsGenerattorTest {
	Projeto projeto;
	Bloco bloco;
	Cavidade cavidade;
	CircularBoss boss;
	Face faceXY;
	@Before
	public void createProject()
	{
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
	}
	@Test
	public void generalTest()
	{
		this.cavidade=new Cavidade();
		this.cavidade.setNome("Lucas");
		this.cavidade.setPosicao(9, 9, 0);
		this.cavidade.setProfundidade(40);
		this.cavidade.setPassante(true);
		this.cavidade.setRaio(20);
		this.cavidade.setComprimento(185);
		this.cavidade.setLargura(135);
		this.cavidade.createGeometricalElements();

		faceXY.addFeature(this.cavidade);
		
		
		PointsGenerator generator = new PointsGenerator(this.projeto, 10);
//		ArrayList<Point3d> a = generator.gerarPontos();
		for (int i=0; i<generator.setupsArray.get(Face.XY).get(Face.XY).size(); i++){
			
			System.out.println(generator.setupsArray.get(Face.XY).get(Face.XY).get(i));
		}
	}
}
