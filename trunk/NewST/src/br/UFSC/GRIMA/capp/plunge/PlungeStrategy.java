package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public abstract class PlungeStrategy
{
	private Point3d toolDirection; // Vetor diretor do mergulho

	public PlungeStrategy(Point3d toolDirection)
	{
		this.toolDirection = toolDirection;
	}

	public Point3d getToolDirection()
	{
		return toolDirection;
	}

	public void setToolDirection(Point3d toolDirection)
	{
		this.toolDirection = toolDirection;
	}
	
}
