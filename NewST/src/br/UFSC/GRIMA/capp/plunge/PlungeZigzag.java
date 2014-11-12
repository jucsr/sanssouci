package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public class PlungeZigzag extends PlungeStrategy
{
	private double angle, width;
//						<tipo>	<nome atributo>
	public PlungeZigzag(double angle, double width)
	{
		this.angle = angle;
		this.width = width;
	}
	public double getAngle() 
	{
		return angle;
	}
	public void setAngle(double angle) 
	{
		this.angle = angle;
	}
	public double getWidth()
	{
		return width;
	}
	public void setWidth(double width)
	{
		this.width = width;
	}
}
