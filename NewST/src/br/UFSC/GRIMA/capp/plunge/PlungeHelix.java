package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public class PlungeHelix extends PlungeStrategy 
{
	private double angle, radius;
	public PlungeHelix() 
	{
	}
	public double getAngle() 
	{
		return angle;
	}
	public void setAngle(double angle) 
	{
		this.angle = angle;
	}
	public double getRadius() 
	{
		return radius;
	}
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}

}
