package br.UFSC.GRIMA.entidades.machiningResources;

/**
 * 
 * @author Jc
 *
 */
public class MillingTypeSpindle extends Spindle
{
	private double maxCuttingToolDiameter;

	public double getMaxCuttingToolDiameter() 
	{
		return maxCuttingToolDiameter;
	}

	public void setMaxCuttingToolDiameter(double maxCuttingToolDiameter) 
	{
		this.maxCuttingToolDiameter = maxCuttingToolDiameter;
	}
}
