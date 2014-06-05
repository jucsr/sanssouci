package br.UFSC.GRIMA.capp.machiningOperations;
/**
 * 
 * @author Jc
 *
 */
public class PlaneRoughMilling extends Two5DMillingOperation
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
