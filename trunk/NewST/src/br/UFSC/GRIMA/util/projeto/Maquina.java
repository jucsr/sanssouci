package br.UFSC.GRIMA.util.projeto;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

public class Maquina implements Serializable{
	public  double comprimento;
	public  double largura;
	public  double profundidade;
	
	public Maquina(double comprimento, double largura, double profundidade)
	{	
		this.comprimento = comprimento;
		this.largura = largura;
		this.profundidade = profundidade;		
	}
	
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Maquina");
		root.add(new DefaultMutableTreeNode("Comprimento: " + this.comprimento));
		root.add(new DefaultMutableTreeNode("Largura: " + this.largura));
		root.add(new DefaultMutableTreeNode("Profundidade: " + this.profundidade));
		
		return root;
	}
	
	public void setComprimento(double comprimento)
	{	
		this.comprimento = comprimento;
	}
	public double getComprimento()
	{	
		return this.comprimento;
	}
	public void setLargura(double largura)
	{	
		this.largura = largura;
	}
	public double getLargura()
	{	
		return this.largura;
	}
	public void setProfundidade(double profundidade)
	{	
		this.profundidade = profundidade;
	}
	public double getProfundidade()
	{	
		return this.profundidade;
	}
}

