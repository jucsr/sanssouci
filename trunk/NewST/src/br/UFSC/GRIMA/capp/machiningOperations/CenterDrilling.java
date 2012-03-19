package br.UFSC.GRIMA.capp.machiningOperations;
/**
 * 
 * @author Jc
 *
 */
public class CenterDrilling extends MachiningOperation
{
	private double cuttingDepth; // a quantidade a ser furada
	private double previousDiameter;
	public CenterDrilling(String id, double retractPlane) 
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
