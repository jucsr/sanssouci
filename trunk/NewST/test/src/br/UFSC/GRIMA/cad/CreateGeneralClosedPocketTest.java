package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.DeterminateShortestDistance;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.CSGSolid;
import br.UFSC.GRIMA.operationSolids.OperationBezierSurface;
import br.UFSC.GRIMA.operationSolids.OperationGeneralClosedPocked;
import br.UFSC.GRIMA.util.Transformer;
import br.UFSC.GRIMA.util.Triangulation;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateGeneralClosedPocketTest 
{
	Projeto projeto;
	Bloco bloco;
	GeneralClosedPocket cavidade;
	CircularBoss boss;
	Face faceXY;
	ArrayList<ArrayList<LimitedElement>> bossVirtual = null;
	ArrayList<Point2D> pointsTmp = new ArrayList<Point2D>();

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

		
		this.cavidade = new GeneralClosedPocket();
		this.cavidade.setNome("Cavidade Perfil Geral Fechado");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(40);
		this.cavidade.setRadius(5);
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
//		points.add(new Point2D.Double(44.0,89.0));
//		points.add(new Point2D.Double(51.0,68.0));
//		points.add(new Point2D.Double(27.0,22.0));
//		points.add(new Point2D.Double(55.0,20.0));
//		points.add(new Point2D.Double(67.0,50.0));
//		points.add(new Point2D.Double(124.0,65.0));
//		points.add(new Point2D.Double(136.0,20.0));
//		points.add(new Point2D.Double(164.0,19.0));
//		points.add(new Point2D.Double(147.0,66.0));
//		points.add(new Point2D.Double(168.0,116.0));
//		points.add(new Point2D.Double(134.0,84.0));
//		points.add(new Point2D.Double(68.0,84.0));
//		points.add(new Point2D.Double(45.0,120.0));
//		points.add(new Point2D.Double(13.0,93.0));
		
		points.add(new Point2D.Double(10, 10));
		points.add(new Point2D.Double(100, 10));
		points.add(new Point2D.Double(100, 100));
		points.add(new Point2D.Double(10, 100));
		points.add(new Point2D.Double(50, 50));
		
		
		this.cavidade.setPoints(points);
		bossVirtual = MapeadoraGeneralClosedPocket1.getAreaAlreadyDesbasted1(cavidade, bossVirtual, cavidade.Z, 20, 2);
		pointsTmp = Transformer.limitedElementToPoints2D(bossVirtual.get(0));

	}
	@Test
	public void createGeneralClosedPocketTest()
	{

		faceXY.addFeature(this.cavidade);
		
		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		Janela3D.setVisible(true);

//		DeterminateShortestDistance dsd = new DeterminateShortestDistance(this.cavidade);
//		System.out.println("shortest distance = " + dsd.getShortestDistance());
		while(true);
	}@Test
	public void generateBezierSurfaceSolidTest()
	{
		JFrame frame = new JFrame("BEZIER SURFACE");
		frame.setBounds(100, 100, 500, 500);
		JPanel painel = new JPanel();
		painel.repaint();
		painel.setLayout(new BorderLayout());
		frame.getContentPane().add(painel);
		J3D j3d = new J3D(painel);
		CSGSolid.appearance = true;
		OperationGeneralClosedPocked operation = new OperationGeneralClosedPocked("BEZIER_SURFACE", (float)cavidade.getProfundidade() ,0, pointsTmp);
		
		j3d.addSolid(operation);
		
		frame.setVisible(true);
		while(true);
	}
}
