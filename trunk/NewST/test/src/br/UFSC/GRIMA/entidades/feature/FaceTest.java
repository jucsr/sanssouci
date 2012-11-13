package br.UFSC.GRIMA.entidades.feature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import br.UFSC.GRIMA.cad.CreateGeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;


public class FaceTest {

	Cavidade cavidade;
	GeneralProfileBoss gpb;
	
	@Test
	public void desenharCavidade()
	{
		this.cavidade = new Cavidade();
		this.cavidade.setNome("Lucas");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(10);
		this.cavidade.setRaio(40);
		this.cavidade.setComprimento(180);
		this.cavidade.setLargura(130);
		this.cavidade.createGeometricalElements();
		
		Point2D[] p = new Point2D[4];
		
		p[0].setLocation(40.0, 100.0);
		p[1].setLocation(100.0, 100.0);
		p[2].setLocation(80.0, 30.0);
		p[3].setLocation(35.0, 30.0);
		ArrayList<Point2D> vertexPoints = null;
		
		final GeneralPath forma = new GeneralPath();
		ArrayList<Point2D> vertices = CreateGeneralProfileBoss.transformPolygonInRoundPolygon(vertexPoints, 3);
		forma.moveTo(40.0, 100.0);
		for(int i = 1; i < vertices.size(); i++)
		{
			forma.lineTo(vertices.get(i).getX(), vertices.get(i).getY());
		}
		forma.closePath();
		
		vertexPoints.set(0, p[0]);
		vertexPoints.set(0, p[1]);
		vertexPoints.set(0, p[2]);
		vertexPoints.set(0, p[3]);
		this.gpb = new GeneralProfileBoss();
		this.gpb.setNome("GeneralBoss");
		this.gpb.setVertexPoints(vertexPoints);
		this.gpb.setRadius(3);
		this.gpb.setAltura(10.0);
		this.gpb.setPosicao(40.0, 100.0, 0.0);
		this.gpb.setForma(forma);
		this.gpb.setRugosidade(0.005);
		
		
		class painelTest extends JPanel{
			
			public void paintComponent(Graphics g)
			{
				super.paintComponents(g);
				Shape s;
				s = new RoundRectangle2D.Double(cavidade.getPosicaoX(), cavidade.getPosicaoY(), cavidade.getComprimento(), cavidade.getLargura(), cavidade.getRaio() * 2, cavidade.getRaio() * 2);

				Graphics2D g2d = (Graphics2D)g;
				//				g2d.translate(25, 475);
				g2d.translate(25, 325);
				g2d.scale(1, -1);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.black);
		//		Face.getShapePontos(gpb);
				g2d.draw(s);
				g2d.draw(forma);
				
			}
		}
		
		
		JFrame frame = new JFrame("roundRectangle");
		painelTest painel = new painelTest();
		frame.setSize(510, 535);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
	}
}
