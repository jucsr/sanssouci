package br.UFSC.GRIMA.entidades.machiningResources;

public class ToolMagazine extends CuttingToolHandlingDevice
{
	private double toolChangeTime;

	public double getToolChangeTime()
	{
		return toolChangeTime;
	}

	public void setToolChangeTime(double toolChangeTime) 
	{
		this.toolChangeTime = toolChangeTime;
	}
}
