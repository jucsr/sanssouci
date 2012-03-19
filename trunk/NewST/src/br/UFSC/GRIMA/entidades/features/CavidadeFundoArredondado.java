package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;

import jsdai.SCombined_schema.EClosed_pocket;

public class CavidadeFundoArredondado extends Feature
{
	private double verticeRaio, fundoRaio, largura, comprimento, profundidade;
	private transient EClosed_pocket eClosed_pocket;
	public CavidadeFundoArredondado(double x, double y, double z, double verticeRaio, double fundoRaio, double largura, double comprimento, double profundidade)
	{
		super(Feature.CAVIDADE_FUNDO_ARREDONDADO);
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.verticeRaio = verticeRaio;
		this.fundoRaio = fundoRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
	}
	
	public CavidadeFundoArredondado(String id, double x, double y, double z, double verticeRaio, double fundoRaio, double largura, double comprimento, double profundidade)
	{
		super(Feature.CAVIDADE_FUNDO_ARREDONDADO);
		setNome(id);
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.verticeRaio = verticeRaio;
		this.fundoRaio = fundoRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
	}

	
	public DefaultMutableTreeNode getNodo() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Banheira -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Comprimento = "
				+ this.getComprimento()));
		root.add(new DefaultMutableTreeNode("Largura = " + this.largura));
		root.add(new DefaultMutableTreeNode("Profundidade = "
				+ this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("Raio nos vertices = " + this.verticeRaio));
		root.add(new DefaultMutableTreeNode("Raio no fundo = " + this.fundoRaio));
		root.add(new DefaultMutableTreeNode("Posição X = "
				+ this.X));
		root.add(new DefaultMutableTreeNode("Posição Y = "
				+ this.Y));
		root.add(new DefaultMutableTreeNode("posição Z = "
				+ this.Z));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		
		this.getNodoWorkingSteps(root);

		return root;
	}
	
	public double getVerticeRaio() {
		return verticeRaio;
	}
	public double getFundoRaio() {
		return fundoRaio;
	}
	public double getLargura() {
		return largura;
	}
	public double getComprimento() {
		return comprimento;
	}
	public double getProfundidade() {
		return profundidade;
	}

	public EClosed_pocket geteClosed_pocket() {
		return eClosed_pocket;
	}

	public void seteClosed_pocket(EClosed_pocket eClosed_pocket) {
		this.eClosed_pocket = eClosed_pocket;
	}
}
