package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;


public class FuroBaseEsferica extends Furo
{
	private double floorRadius;

	public FuroBaseEsferica() {
		// TODO Auto-generated constructor stub
	}
	
	public FuroBaseEsferica(String id, double x, double y, double z,
			double diametroFuro, double profundidade, double tolerancia) {
		super(id,x,y,z,diametroFuro,profundidade);
		this.setFloorRadius(diametroFuro/2);
		this.setTolerancia(tolerancia);
	}

	public double getFloorRadius() 
	{
		return floorRadius;
	}

	public void setFloorRadius(double floorRadius) 
	{
		this.floorRadius = floorRadius;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Furo com base esférica -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Raio = " + this.getRaio() + " mm"));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()+ " mm"));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()+ " um"));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()+ " um"));
		root.add(new DefaultMutableTreeNode("Posicao X = " + this.getPosicaoX()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Y = " + this.getPosicaoY()+ " mm"));
		root.add(new DefaultMutableTreeNode("Posicao Z = " + this.getPosicaoZ()+ " mm"));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}
}
