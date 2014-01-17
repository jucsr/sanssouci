package br.UFSC.GRIMA.util;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
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
	private Point3d initialPoint=new Point3d();
	private Point3d finalPoint=new Point3d();
	
	public CircularPath(Point3d center, Point3d initialPoint, Point3d finalPoint, double angulo, int sense)
	{
		super(initialPoint, finalPoint);
		this.angulo = angulo;
		this.sense = sense;
		this.center = center;
		this.initialPoint = initialPoint;
		this.finalPoint = finalPoint;
	}
	
	public CircularPath(Point3d initialPoint, Point3d finalPoint,  Point3d center) 
	{
		super(initialPoint, finalPoint);
		this.setSense(CCW);
		this.radius = GeometricOperations.distance(initialPoint, center);
		this.center = center;
		this.calculateAngle(initialPoint, finalPoint);
	}
	public CircularPath(Point3d initialPoint, Point3d finalPoint, double radius) 
	{
		super(initialPoint, finalPoint);
		this.radius = radius;
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

	public double getInitialAngle() {
		return initialAngle;
	}

	public void setInitialAngle(double initialAngle) {
		this.initialAngle = initialAngle;
	}

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

	public static int getCw() {
		return CW;
	}

	public static int getCcw() {
		return CCW;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setCenter(Point3d center) {
		this.center = center;
	}

	public void setFinalAngle(double finalAngle) {
		this.finalAngle = finalAngle;
	}
}
