package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;

public class FuroBaseArredondada extends Furo
{
	private double r1;
	
	public FuroBaseArredondada(){
		
	}

	public FuroBaseArredondada(String id, double x, double y, double z,
			double diametroFuro, double profundidade, double r1, double tolerancia) {
		super(id, x,y,z,diametroFuro,profundidade);
		this.setR1(r1);
		this.setTolerancia(tolerancia);
	}

	public double getR1() 
	{
		return r1;
	}

	public void setR1(double r1) 
	{
		this.r1 = r1;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Furo com base arredondada -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Raio = " + this.getRaio() + " mm"));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()+ " mm"));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()+ " um"));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()+ " um"));
		root.add(new DefaultMutableTreeNode("Posicao X = " + this.getPosicaoX()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Y = " + this.getPosicaoY()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Z = " + this.getPosicaoZ()+ " mm"));
		root.add(new DefaultMutableTreeNode("r1 = " + this.getR1() + " mm"));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}
}
