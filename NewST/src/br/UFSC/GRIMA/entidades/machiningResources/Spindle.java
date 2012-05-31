package br.UFSC.GRIMA.entidades.machiningResources;

import br.UFSC.GRIMA.util.projeto.Axis;

/**
 * 
 * @author Jc
 *
 */
public abstract class Spindle 
{
	private String itsId = "";
	private Axis itsOrientation;
	private double spindleMaxPower;
	private double spindleNoseToTable;
	
	public String getItsId() 
	{
		return itsId;
	}
	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}
	public Axis getItsOrientation() 
	{
		return itsOrientation;
	}
	public void setItsOrientation(Axis itsOrientation) 
	{
		this.itsOrientation = itsOrientation;
	}
	public double getSpindleMaxPower() 
	{
		return spindleMaxPower;
	}
	public void setSpindleMaxPower(double spindleMaxPower) 
	{
		this.spindleMaxPower = spindleMaxPower;
	}
	public double getSpindleNoseToTable() 
	{
		return spindleNoseToTable;
	}
	public void setSpindleNoseToTable(double spindleNoseToTable) 
	{
		this.spindleNoseToTable = spindleNoseToTable;
	}
}
