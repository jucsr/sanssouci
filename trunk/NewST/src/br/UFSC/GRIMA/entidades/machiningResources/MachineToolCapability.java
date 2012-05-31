package br.UFSC.GRIMA.entidades.machiningResources;

import java.util.ArrayList;

/**
 * 
 * @author Jc
 *
 */
public class MachineToolCapability
{
	private String itsId = "";
	private double tableLoadingCapacity;
	private MachinableSize machinableWorkpiece;
	private double maxFeeding;
	
	public String getItsId() 
	{
		return itsId;
	}
	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}
	public double getTableLoadingCapacity()
	{
		return tableLoadingCapacity;
	}
	public void setTableLoadingCapacity(double tableLoadingCapacity)
	{
		this.tableLoadingCapacity = tableLoadingCapacity;
	}
	public MachinableSize getMachinableWorkpiece()
	{
		return machinableWorkpiece;
	}
	public void setMachinableWorkpiece(MachinableSize machinableWorkpiece) 
	{
		this.machinableWorkpiece = machinableWorkpiece;
	}
	public double getMaxFeeding()
	{
		return maxFeeding;
	}
	public void setMaxFeeding(double maxFeeding)
	{
		this.maxFeeding = maxFeeding;
	}
}
