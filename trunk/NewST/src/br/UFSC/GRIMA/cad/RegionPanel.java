package br.UFSC.GRIMA.cad;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import br.UFSC.GRIMA.util.projeto.Projeto;

// Desenha o retângulo (region) sobre o desenhador de faces na janela do DefineRegionDimensionFrame
public class RegionPanel extends DesenhadorDeFaces
{
	public Rectangle2D rectangle;
	
	public RegionPanel(Projeto projeto) 
	{
		super(projeto);
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(rectangle);
	}
}