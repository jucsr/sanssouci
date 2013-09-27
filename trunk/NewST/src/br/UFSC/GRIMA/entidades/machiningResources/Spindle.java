package br.UFSC.GRIMA.entidades.machiningResources;

import java.io.Serializable;

import br.UFSC.GRIMA.util.projeto.Axis;

/**
 * 
 * @author Jc
 *
 */
public abstract class Spindle implements Serializable
{
	private String itsId = "";
	private double spindleMaxPower; 
	private double itsSpeedRange;
	private double maxTorque;
	private boolean isCoolant;
	
	public String getItsId() 
	{
		return itsId;
	}
	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}
	public double getSpindleMaxPower() 
	{
		return spindleMaxPower;
	}
	public void setSpindleMaxPower(double spindleMaxPower) 
	{
		this.spindleMaxPower = spindleMaxPower;
	}
	public double getItsSpeedRange() {
		return itsSpeedRange;
	}
	public void setItsSpeedRange(double itsSpeedRange) {
		this.itsSpeedRange = itsSpeedRange;
	}
	public double getMaxTorque() {
		return maxTorque;
	}
	public void setMaxTorque(double maxTorque) {
		this.maxTorque = maxTorque;
	}
	public boolean getIsCoolant() {
		return isCoolant;
	}
	public void setIsCoolant(boolean isCoolant) {
		this.isCoolant = isCoolant;
	}
}
