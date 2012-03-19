package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;

public class FuroBaseConica extends Furo
{
	private double tipAngle;
	private double tipRadius; // nao esta sendo utilizado
	
	public FuroBaseConica() {
	}
	
	public FuroBaseConica(String id, double x, double y, double z,
			double diametroFuro, double profundidade, double tipAngle,
			double tolerancia) {
		super(id,x,y,z,diametroFuro,profundidade);
		this.setTipAngle(tipAngle);
		this.setTolerancia(tolerancia);
	}
	public void setTipAngle(double tipAngle)
	{
		this.tipAngle = tipAngle;
	}
	public void setTipRadius(double tipRadius)
	{
		this.tipRadius = tipRadius;
	}
	public double getTipAngle()
	{
		return this.tipAngle;
	}
	public double getTipRadius()
	{
		return this.tipRadius;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Conic Bottom Round Hole -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Name: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Diamenter = " + this.getRaio() * 2+ " mm"));
		root.add(new DefaultMutableTreeNode("Depth = " + this.getProfundidade()+ " mm"));
		root.add(new DefaultMutableTreeNode("Tolerance = " + this.getTolerancia()+ " um"));
		root.add(new DefaultMutableTreeNode("Roughness (Ra) = " + this.getRugosidade()+ " um"));
		root.add(new DefaultMutableTreeNode("Position X = " + this.getPosicaoX()+ " mm"));
		root.add(new DefaultMutableTreeNode("Position Y = " + this.getPosicaoY()+ " mm"));
		root.add(new DefaultMutableTreeNode("Position Z = " + this.getPosicaoZ()+ " mm"));
		root.add(new DefaultMutableTreeNode("Angle = " + this.getTipAngle() + "°"));
		
		this.getNodoWorkingSteps(root);

		return root;
	}
}
