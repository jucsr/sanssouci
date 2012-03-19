package br.UFSC.GRIMA.capp.machiningOperations;
/**
 * 
 * @author Jc
 *
 */
public class BottomAndSideFinishMilling extends MachiningOperation
{
	private double allowanceSide;
	private double allowanceBottom;
	
	public BottomAndSideFinishMilling(String id, double retractPlane) 
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
