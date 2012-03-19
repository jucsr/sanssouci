package br.UFSC.GRIMA.util.findPoints;

import java.util.ArrayList;

import javax.vecmath.Point3d;

public class LimitedArc extends LimitedElement
{
	private Point3d center, initialPoint, finalPoint;
	private double deltaAngle;
	private ArrayList<Point3d> pointsInArc;
	private double radius;
	public static final int CW = 0;
	public static final int CCW = 1;
	private int sense = 0, n = 50; // n = number of points in arc
	public LimitedArc(Point3d center, Point3d initialPoint, Point3d finalPoint, int sense, int n)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.finalPoint = finalPoint;
		this.sense = sense;
		this.n = n;
		
	}
	public LimitedArc(Point3d center, Point3d initialPoint, double deltaAngle, int sense, int n)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.deltaAngle = deltaAngle;
		this.sense = sense;
		this.n = n;
	}
	public Point3d getCenter() {
		return center;
	}
	public void setCenter(Point3d center) {
		this.center = center;
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
	public ArrayList<Point3d> getPointsInArc() {
		return pointsInArc;
	}
	public void setPointsInArc(ArrayList<Point3d> pointsInArc) {
		this.pointsInArc = pointsInArc;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public int getSense() {
		return sense;
	}
	public void setSense(int sense) {
		this.sense = sense;
	}
	
}