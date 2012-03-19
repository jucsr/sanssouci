package br.UFSC.GRIMA.util;

import javax.vecmath.Point3d;

public class CircularPath extends Path
{
	private double radius;
	private Point3d center;
	private int sense;
	public static final int CW = 0; // sentido horario
	public static final int CCW = 1; // sentido anti-horario
	public CircularPath(Point3d initialPoint, Point3d finalPoint, double radius, Point3d center) 
	{
		super(initialPoint, finalPoint);
		this.radius = radius;
		this.center = center;
	}
	public double getRadius() 
	{
		return radius;
	}
	public Point3d getCenter() 
	{
		return center;
	}
	public int getSense() {
		return sense;
	}
	public void setSense(int sense) {
		this.sense = sense;
	}
}
