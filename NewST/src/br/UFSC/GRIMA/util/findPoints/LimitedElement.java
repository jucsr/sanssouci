package br.UFSC.GRIMA.util.findPoints;

import java.io.Serializable;

import javax.vecmath.Point3d;

public abstract class LimitedElement implements Serializable
{
	public Point3d initialPoint, finalPoint;

	public Point3d getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(Point3d initialPoint) {
		this.initialPoint = initialPoint;
	}

	public Point3d getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(Point3d finalPoint) {
		this.finalPoint = finalPoint;
	}

	public boolean isLimitedArc()
	{
		if (this.getClass().equals(LimitedArc.class))
			return true;
		else
			return false;
	}
	
	public boolean isLimitedLine()
	{
		if (this.getClass().equals(LimitedLine.class))
			return true;
		else
			return false;
	}
}
