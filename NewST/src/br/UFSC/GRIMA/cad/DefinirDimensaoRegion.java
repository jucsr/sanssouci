package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import br.UFSC.GRIMA.cad.visual.DefineRegionDimensionFrame;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class DefinirDimensaoRegion extends DefineRegionDimensionFrame implements ActionListener, ItemListener{

	private Projeto projeto;
	public DesenhadorDeFaces desenhador;
	
	private double x, y;
	private double largura, altura;
	
	public DefinirDimensaoRegion (Frame owner, Projeto projeto)
	{
		super(owner);
		this.projeto = projeto;
		this.desenhador = new DesenhadorDeFaces(this.projeto);
		this.posicaoX.getValue();
		this.posicaoY.getValue();
//		this.largura.getValue();
//		this.altura.getValue();
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
