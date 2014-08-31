package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public class PlungeRamp extends PlungeStrategy
{
	private double angle;
	public PlungeRamp(Point3d toolDirection, double angle)
	{
		super(toolDirection);
		this.angle = angle;
	}
	public double getAngle()
	{
		return angle; 
	}
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
}
