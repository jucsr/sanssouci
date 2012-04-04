package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;

public class RanhuraPerfilQuadradoU extends Ranhura
{
	private double angulo;
	private double largura2;
	private double raio;
	
	
	public RanhuraPerfilQuadradoU() {
	}
	public RanhuraPerfilQuadradoU(String id, double x, double y, double z, double locX, double locY, double locZ,
			double larguraRanhura, double profundidadeRanhura, int eixoAtual,
			double raio, double angulo, double largura2) {
		super(id,x,y,z,locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual);
		setRaio(raio);
		setAngulo(angulo);
		setLargura2(largura2);
	}
	
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	public double getLargura2() {
		return largura2;
	}
	public void setLargura2(double largura2) {
		this.largura2 = largura2;
	}
	public double getRaio() {
		return raio;
	}
	public void setRaio(double raio) {
		this.raio = raio;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ranhura -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Largura 1 = " + this.getLargura()));
		root.add(new DefaultMutableTreeNode("Largura 2 = " + this.getLargura2()));
		root.add(new DefaultMutableTreeNode("ângulo = " + this.getAngulo()));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("posição Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Deslocamento : " + this.getDeslocamento()));
		root.add(new DefaultMutableTreeNode("Eixo : " + this.getStringEixo()));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}
}
