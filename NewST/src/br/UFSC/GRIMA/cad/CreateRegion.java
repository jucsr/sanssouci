package br.UFSC.GRIMA.cad;

import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateRegion 
{
	private Projeto projeto;
	private Face face;
	private Feature feature;
	private RectanglePanel rectanglePanel;
	JanelaPrincipal parent;
	private double zoom = 1;
	
	CreateRegion(JanelaPrincipal parent, Projeto projeto, final Face face)
	{
		this.projeto = projeto;
		this.face = face;
		this.parent = parent;
		
	}
}
