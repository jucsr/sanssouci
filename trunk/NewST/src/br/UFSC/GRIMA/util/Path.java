package br.UFSC.GRIMA.util;

import java.io.Serializable;

import javax.vecmath.Point3d;

public abstract class Path implements Serializable
{
	private Point3d initialPoint;
	private Point3d finalPoint;
	public Path(Point3d initialPoint, Point3d finalPoint)
	{
		this.initialPoint = initialPoint;
		this.finalPoint = finalPoint;
	}
	public Point3d getInitialPoint() {
		return this.initialPoint;
	}
	public Point3d getFinalPoint() {
		return finalPoint;
	}
}
