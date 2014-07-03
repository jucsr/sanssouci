package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.cad.Generate3Dview;
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
	public void determinateShortestDistanceTest()
	{
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		this.cavidade=new Cavidade();
		this.cavidade.setNome("cavidade");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(10);
		this.cavidade.setRaio(20);
		this.cavidade.setComprimento(50);
		this.cavidade.setLargura(50);
		this.cavidade.createGeometricalElements();
		faceXY.addFeature(this.cavidade);
		
		this.boss=new CircularBoss();
		this.boss.setDiametro1(10);
		this.boss.setDiametro2(10);
		this.boss.setAltura(10);
		this.boss.setPosicao(25, 25, 0);
		this.boss.setNome("boss");
		this.boss.createGeometricalElements();
		this.boss.setCenter(new Point3d(boss.X, boss.Y, boss.Z));
		itsBoss.add(this.boss);
		
		this.faceXY.addFeature(this.boss);
		
		
		CircularBoss boss1=new CircularBoss();
		boss1.setDiametro1(20);
		boss1.setDiametro2(20);
		boss1.setAltura(10);
		boss1.setPosicao(40, 35, 0);
		boss1.setNome("boss1");
		boss1.createGeometricalElements();
		boss1.setCenter(new Point3d(boss1.X, boss1.Y, boss1.Z));

		itsBoss.add(boss1);
		
		this.faceXY.addFeature(boss1);
		
		CircularBoss boss2=new CircularBoss();
		boss2.setDiametro1(5);
		boss2.setDiametro2(5);
		boss2.setAltura(10);
		boss2.setPosicao(20, 50, 0);
		boss2.setNome("boss2");
		boss2.createGeometricalElements();
		boss2.setCenter(new Point3d(boss2.X, boss2.Y, boss2.Z));

		itsBoss.add(boss2);
		
		this.faceXY.addFeature(boss2);
		
		CircularBoss boss3=new CircularBoss();
		boss3.setDiametro1(10);
		boss3.setDiametro2(10);
		boss3.setAltura(10);
		boss3.setPosicao(20, 35, 0);
		boss3.setNome("boss3");
		boss3.createGeometricalElements();
		boss3.setCenter(new Point3d(boss3.X, boss3.Y, boss3.Z));

		itsBoss.add(boss3);
		
		this.cavidade.setItsBoss(itsBoss);
		this.faceXY.addFeature(boss3);
		
		
		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		Janela3D.setVisible(true);
		
		DeterminateShortestDistance deter = new DeterminateShortestDistance(this.cavidade);
		System.err.println("****>>> shortest distance = " + deter.getShortestDistance());
		
		while(true);
		
		
	}
}
