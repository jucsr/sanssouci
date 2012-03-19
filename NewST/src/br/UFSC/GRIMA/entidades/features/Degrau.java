package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import jsdai.SCombined_schema.EStep;

public class Degrau extends Feature implements Serializable{
	private double largura = 0.0;
	private double profundidade = 0.0;

	private int eixo = Degrau.HORIZONTAL;
	
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	double i;
	private double comprimento;
	private transient EStep eStep;
	
	public Degrau(){
		super(Feature.DEGRAU);
	}
	
	public Degrau(String nome, double x, double y, double z, double largura, double profundidade){
		super(Feature.DEGRAU);
		this.setNome(nome);
		this.largura = largura;
		this.profundidade = profundidade;
		setPosicao(x, y, z);
	}

	public Degrau(String nome, int eixo, double x, double y, double z, double largura, double profundidade){
		super(Feature.DEGRAU);
		this.setNome(nome);
		this.eixo = eixo;
		this.largura = largura;
		this.profundidade = profundidade;
		setPosicao(x, y, z);
	}
	
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Degrau -" + this.getIndice());
		root.add(new DefaultMutableTreeNode
				("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode
				("Largura = " + this.getLargura()));
		root.add(new DefaultMutableTreeNode
				("Profundidade = " +this.getProfundidade()));
		root.add(new DefaultMutableTreeNode
				("prosição Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode
				("Eixo : " + this.getTipoStringEixo()));
		root.add(new DefaultMutableTreeNode("Lado : " + this.getTipoLado(i)));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}
	
	public void setEixo(int i){
		if(i == Degrau.HORIZONTAL){
			this.eixo = Degrau.HORIZONTAL;
		}
		else if(i == Degrau.VERTICAL){
			this.eixo = Degrau.VERTICAL;
		}
		else{
			this.eixo = Degrau.HORIZONTAL;
		}
	}
	
	public int getEixo(){
		return this.eixo;
	}
	/**
	 * fazer os metodos get e set
	 * @param largura
	 */
	public void setLargura(double largura){
		this.largura = largura;
	}
	
	public double getLargura(){
		return this.largura;
	}
	
	public void setProfundidade(double profundidade)
	{	this.profundidade = profundidade;
	}
	
	public double getProfundidade()
	{	return this.profundidade;
	}

	public String getTipoStringEixo()
	{
		
		if (this.eixo == Degrau.HORIZONTAL)
		{
			return "HORIZONTAL";
		}
		else
		{
			return "VERTICAL";
		}
	}
	
	public String getTipoLado(double i)
	{
		
		if (this.eixo == HORIZONTAL)
		{
			i = this.Y;
			if (this.Y == 0)
			{
				return "NA BASE";
			}
			else
			{
				return "NO TOPO";
			}
		}
		else
		{
			i = this.X;
			if (this.X == 0)
			{
				return "NA ESQUERDA";
			}
			else
			{
				return "NA DIREITA";
			}
		}
	}
	public void imprimirDados()
	{
		System.out.print("***DEGRAU " + this.getIndice() + "*******");
		System.out.print("\t Profundidade = " + this.getProfundidade());
		System.out.print("\t Largura = " + this.getLargura());
		System.out.print("\t Posição X = " + this.getPosicaoX());
		System.out.print("\t Posição Y = " + this.getPosicaoY());
		System.out.print("\t Posição Z = " + this.getPosicaoZ());
		
		System.out.print("\n Eixo = " + this.getTipoStringEixo());
		System.out.print("\n DEGRAU Adicionado com sucesso\n");
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public EStep getEStep() {
		return eStep;
	}

	public void setEStep(EStep eStep) {
		this.eStep = eStep;
	}
	public String getStringEixo()
	{
		if (this.eixo == HORIZONTAL)
		{
			return "HORIZONTAL";
		}
		else
			return "VERTICAL";
	}
}
