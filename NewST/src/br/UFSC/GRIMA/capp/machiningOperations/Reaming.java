package br.UFSC.GRIMA.capp.machiningOperations;

import javax.vecmath.Point3d;

public class Reaming extends DrillingTypeOperation
{
	private double cuttingDepth; // a quantidade 
	private double previousDiameter;

	public Reaming(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}
	
	public double getCuttingDepth() {
		return cuttingDepth;
	}
	public void setCuttingDepth(double cuttingDepth) {
		this.cuttingDepth = cuttingDepth;
	}
	public double getPreviousDiameter() {
		return previousDiameter;
	}
	public void setPreviousDiameter(double previousDiameter) {
		this.previousDiameter = previousDiameter;
	}
}
