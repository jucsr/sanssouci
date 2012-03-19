package br.UFSC.GRIMA.cad;

import java.awt.*;

import javax.swing.*;
import javax.vecmath.Point3d;


import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;

public class DesenhadorBezier extends JPanel
{
	private RanhuraPerfilBezier ranhura;
	private Face face;
	
	public DesenhadorBezier(RanhuraPerfilBezier ranhura, Face face)
	{
		   
	      
		this.ranhura = ranhura;
		this.face = face;
		this.setLayout(new FlowLayout());
		this.setSize(1000, 1000);
		this.setBackground(Color.white);
	}
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.translate(0, 50 + face.getProfundidadeMaxima());
		g2d.scale(1, -1);
		
		this.desenharFace(g2d);
		this.desenharPerfil(g2d);
	}
	private void desenharPerfil(Graphics2D g2d) 
	{
		g2d.setColor(new Color(184, 134, 11));
	
		for(int i = 0; i < ranhura.getPontosDaCurva().length - 1; i++)
		{
			Point3d ponto1 = ranhura.getPontosDaCurva()[i];
			Point3d ponto2 = ranhura.getPontosDaCurva()[i + 1];
			
			g2d.drawLine(10 + (int)ponto1.x, 10 + (int)(face.getProfundidadeMaxima() + ponto1.y), 10 + (int)ponto2.x, 10 + (int)(face.getProfundidadeMaxima() + ponto2.y));
		}
	}
	private void desenharFace(Graphics2D g2d)
	{
		g2d.setColor(new Color(240, 255, 240));
		g2d.fillRect(10, 10, (int)face.getComprimento(),  (int)face.getProfundidadeMaxima());
		g2d.setColor(new Color(0, 125, 250));
		g2d.drawRect(10, 10, (int)face.getComprimento(),  (int)face.getProfundidadeMaxima());
	}
}
