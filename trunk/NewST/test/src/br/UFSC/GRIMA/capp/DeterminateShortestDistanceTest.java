package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class DeterminateShortestDistanceTest
{
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
	public void determinateShortestDistance()
	{
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		this.cavidade=new Cavidade();
		this.cavidade.setNome("Lucas");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(10);
		this.cavidade.setRaio(0);
		this.cavidade.setComprimento(50);
		this.cavidade.setLargura(50);
		this.cavidade.createGeometricalElements();
		faceXY.addFeature(this.cavidade);
		
		this.boss=new CircularBoss();
		this.boss.setDiametro1(20);
		this.boss.setDiametro2(20);
		this.boss.setAltura(10);
		this.boss.setPosicao(45, 35, 0);
		this.boss.setNome("lucas");
		this.boss.createGeometricalElements();
		itsBoss.add(this.boss);
		
		this.cavidade.setItsBoss(itsBoss);
		
		
		this.faceXY.addFeature(this.boss);
		
		DeterminateShortestDistance deter = new DeterminateShortestDistance(this.cavidade);
		System.out.println("distance = " + deter.getShortestDistance());
		
//		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
//		Janela3D.setVisible(true);
//		while(true);
		
	}
}
