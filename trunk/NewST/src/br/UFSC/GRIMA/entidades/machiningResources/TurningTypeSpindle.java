package br.UFSC.GRIMA.entidades.machiningResources;
/**
 * 
 * @author Jc
 *
 */
public class TurningTypeSpindle extends Spindle
{
	private double maxWorkpieceDiameter;

	public double getMaxWorkpieceDiameter() 
	{
		return maxWorkpieceDiameter;
	}

	public void setMaxWorkpieceDiameter(double maxWorkpieceDiameter) 
	{
		this.maxWorkpieceDiameter = maxWorkpieceDiameter;
	}
}
