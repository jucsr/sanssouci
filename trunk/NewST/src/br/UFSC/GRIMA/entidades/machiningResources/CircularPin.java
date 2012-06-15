package br.UFSC.GRIMA.entidades.machiningResources;

public class CircularPin extends ClampingPin
{
	private double diameter;
	
	public CircularPin(String itsId)
	{
		super(itsId);
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
}
