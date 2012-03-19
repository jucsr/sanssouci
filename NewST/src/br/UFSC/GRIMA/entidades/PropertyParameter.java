package br.UFSC.GRIMA.entidades;

import java.io.Serializable;

public class PropertyParameter implements Serializable
{
	private String name, unit;
	private double parameterValue;
	
	public PropertyParameter() {

	}
	
	public PropertyParameter(String name, String unit, double parameterValue) {
	
		this.name = name;
		this.unit = unit;
		this.parameterValue = parameterValue;
	}
	
	public void setParameterName(String name)
	{
		this.name = name;
	}
	public void setParameterUnit(String unit)
	{
		this.unit = unit;
	}
	public void setParameterValue(double parameterValue)
	{
		this.parameterValue = parameterValue;
	}
	public String getParameterName()
	{
		return this.name;
	}
	public String getParameterUnit()
	{
		return this.unit;
	}
	public double getParameterValue()
	{
		return this.parameterValue;
	}
}
