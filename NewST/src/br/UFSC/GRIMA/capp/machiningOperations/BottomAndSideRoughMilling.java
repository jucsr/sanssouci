package br.UFSC.GRIMA.capp.machiningOperations;

public class BottomAndSideRoughMilling extends MachiningOperation
{
	private double allowanceSide;
	private double allowanceBottom;
	
	public BottomAndSideRoughMilling(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}
	public double getAllowanceSide() {
		return allowanceSide;
	}
	public void setAllowanceSide(double allowanceSide) {
		this.allowanceSide = allowanceSide;
	}
	public double getAllowanceBottom() {
		return allowanceBottom;
	}
	public void setAllowanceBottom(double allowanceBottom) {
		this.allowanceBottom = allowanceBottom;
	}

}
