package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;

public class FuroConico extends Furo
{
	private double raio1;
	private boolean passante;
	public FuroConico() {
	}

	public FuroConico(String id, double x, double y, double z,
			double diametroFuro, double profundidade, double tolerancia,
			double raio1) {
		
		super(id,x,y,z,diametroFuro,profundidade);
		this.setTolerancia(tolerancia);
		this.setRaio1(raio1);
	}
	
	public double getRaio1() 
	{
		return raio1;
	}

	public void setRaio1(double raio1) 
	{
		this.raio1 = raio1;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Furo cônico -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Raio = " + this.getRaio() + " mm"));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()+ " mm"));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()+ " um"));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()+ " um"));
		root.add(new DefaultMutableTreeNode("Posicao X = " + this.getPosicaoX()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Y = " + this.getPosicaoY()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Z = " + this.getPosicaoZ()+ " mm"));
		root.add(new DefaultMutableTreeNode("r1 = " + this.getRaio1() + " mm"));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}

	public boolean isPassante() {
		return passante;
	}

	public void setPassante(boolean passante) {
		this.passante = passante;
	}
}
