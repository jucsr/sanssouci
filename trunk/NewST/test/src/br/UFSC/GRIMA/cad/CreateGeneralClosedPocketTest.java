package br.UFSC.GRIMA.cad;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.DeterminateShortestDistance;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateGeneralClosedPocketTest 
{
	Projeto projeto;
	Bloco bloco;
	GeneralClosedPocket cavidade;
	CircularBoss boss;
	Face faceXY;
	@Before
	public void createProject()
	{
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("aco", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 80.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
	}
	@Test
	public void createGeneralClosedPocketTest()
	{
		this.cavidade = new GeneralClosedPocket();
		this.cavidade.setNome("Cavidade Perfil Geral Fechado");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(40);
		this.cavidade.setRadius(0);
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
//		points.add(new Point2D.Double(10, 10));
//		points.add(new Point2D.Double(100, 10));
//		points.add(new Point2D.Double(100, 100));
//		points.add(new Point2D.Double(10, 100));
//		points.add(new Point2D.Double(50, 50));
		points.add(new Point2D.Double(44.0,89.0));
		points.add(new Point2D.Double(51.0,68.0));
		points.add(new Point2D.Double(27.0,22.0));
		points.add(new Point2D.Double(55.0,20.0));
		points.add(new Point2D.Double(67.0,50.0));
		points.add(new Point2D.Double(124.0,65.0));
		points.add(new Point2D.Double(136.0,20.0));
		points.add(new Point2D.Double(164.0,19.0));
		points.add(new Point2D.Double(147.0,66.0));
		points.add(new Point2D.Double(168.0,116.0));
		points.add(new Point2D.Double(134.0,84.0));
		points.add(new Point2D.Double(68.0,84.0));
		points.add(new Point2D.Double(45.0,120.0));
		points.add(new Point2D.Double(13.0,93.0));
		this.cavidade.setPoints(points);

		faceXY.addFeature(this.cavidade);
		
		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		Janela3D.setVisible(true);

//		DeterminateShortestDistance dsd = new DeterminateShortestDistance(this.cavidade);
//		System.out.println("shortest distance = " + dsd.getShortestDistance());
		while(true);
	}
}
