package br.UFSC.GRIMA.cad;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;



public class DesenhadorDeApoios {
	
	public double zoom = 1.0;
	
	public DesenhadorDeApoios()
	{
		
	}
	
	public void DesenharApoios(Face face, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d, ArrayList<Point3d> arrayA, int raio)
	{
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		float dash1[] = {2.0f, 1.0f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		
		
		if(arrayA.size()!= 0)
		{
			for(int i = 0; i<arrayA.size();i++)
			{
				Point3d pAux = arrayA.get(i);
				int posX = (int)Math.round((pAux.getX() - raio) * zoom + origem.x);
				int posY = (int)Math.round((pAux.getY() - raio) * zoom + origem.y);
				int raioAux = (int)Math.round(raio * 2 * zoom);
				g2d.setColor(new Color(176, 226, 255));
				g2d.fillOval(posX, posY, raioAux, raioAux);
				g2d.setColor(new Color(0, 0, 139 ));
				g2d.drawOval(posX, posY, raioAux, raioAux);
			}
			
			
		}
		
		
	}

	public void DesenharApoios(Face face, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d, ArrayList<Point3d> arrayA, int largura, int comprimento)
	{
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		float dash1[] = {2.0f, 1.0f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		
		
		if(arrayA.size()!= 0)
		{
			for(int i = 0; i<arrayA.size();i++)
			{
				Point3d pAux = arrayA.get(i);
				int posX = (int)Math.round((pAux.getX() - comprimento/2) * zoom + origem.x);
				int posY = (int)Math.round((pAux.getY() - largura/2) * zoom + origem.y);
				g2d.setColor(new Color(176, 226, 255));
				g2d.fillRect(posX, posY, comprimento, largura);
				g2d.setColor(new Color(0, 0, 139 ));
				g2d.drawRect(posX, posY, comprimento, largura);
			}
			
			
		}
		
		
	}
}
