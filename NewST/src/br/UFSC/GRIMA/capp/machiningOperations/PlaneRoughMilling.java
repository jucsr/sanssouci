package br.UFSC.GRIMA.capp.machiningOperations;

public class PlaneRoughMilling extends MachiningOperation
{
	private double axialCuttingDepth;
	private double allowanceBottom;
	
	public PlaneRoughMilling(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}

	public double getAxialCuttingDepth() {
		return axialCuttingDepth;
	}

	public void setAxialCuttingDepth(double axialCuttingDepth) {
		this.axialCuttingDepth = axialCuttingDepth;
	}

	public double getAllowanceBottom() {
		return allowanceBottom;
	}

	public void setAllowanceBottom(double allowanceBottom) {
		this.allowanceBottom = allowanceBottom;
	}
}
