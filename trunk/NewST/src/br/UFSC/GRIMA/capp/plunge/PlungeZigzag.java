package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public class PlungeZigzag extends PlungeStrategy
{
	private double angle, width;
//						<tipo>	<nome atributo>
	public PlungeZigzag(Point3d toolDirection)
	{
		super(toolDirection);
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
