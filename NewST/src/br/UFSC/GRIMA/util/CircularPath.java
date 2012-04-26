package br.UFSC.GRIMA.util;

import javax.vecmath.Point3d;
/**
 * 
 * @author Jc
 *
 */
public class CircularPath extends Path
{
	private double radius;
	private Point3d center;
	private int sense;
	public static final int CW = 0; // sentido horario
	public static final int CCW = 1; // sentido anti-horario
	public double angulo;
	double initialAngle;
	double finalAngle;
	public CircularPath(Point3d initialPoint, Point3d finalPoint, double radius, Point3d center) 
	{
		super(initialPoint, finalPoint);
		this.radius = radius;
		this.center = center;
		this.calculateAngle(initialPoint, finalPoint);
	}
	private void calculateAngle(Point3d initialPoint, Point3d finaPoint)
	{
		initialAngle = Math.atan2(initialPoint.y - center.y, initialPoint.x - center.x);
		finalAngle = Math.atan2(finaPoint.y - center.y, finaPoint.x - center.x);
		this.angulo = finalAngle - initialAngle;
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
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	public double getinicialAngle() {
		return initialAngle;
	}
	public double getFinalAngle() {
		return finalAngle;
	}
}
