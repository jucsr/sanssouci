package br.UFSC.GRIMA.entidades.machiningResources;
/**
 * 
 * @author Jc
 *
 */
public class Turrent extends CuttingToolHandlingDevice
{
	private boolean autoChange;
	private double indexTime;

	public boolean isAutoChange() 
	{
		return autoChange;
	}

	public void setAutoChange(boolean autoChange)
	{
		this.autoChange = autoChange;
	}

	public double getIndexTime() 
	{
		return indexTime;
	}

	public void setIndexTime(double indexTime) 
	{
		this.indexTime = indexTime;
	}	
}
