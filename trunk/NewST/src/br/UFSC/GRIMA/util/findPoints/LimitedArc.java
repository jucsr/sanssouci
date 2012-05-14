package br.UFSC.GRIMA.util.findPoints;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
/**
 * 
 * @author Jc
 *
 */
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
		this.radius = OperationsVector.distanceVector(this.initialPoint, this.center);
	}
	/**
	 * 
	 * @param center
	 * @param initialPoint
	 * @param deltaAngle --> em graus sexagessimais
	 * @param sense
	 * @param n
	 */
	public LimitedArc(Point3d center, Point3d initialPoint, double deltaAngle, int sense, int n)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.deltaAngle = deltaAngle * Math.PI/180;
		this.sense = sense;
		this.n = n;
		this.radius = OperationsVector.distanceVector(this.initialPoint, this.center);
//		System.out.println("radius = " +radius);
//		System.out.println("inicialPoint = " +initialPoint);
		this.determinatePointsInArc();
	}
	/**
	 * 
	 * @return pontos no arco
	 */
	public ArrayList<Point3d> determinatePointsInArc()
	{
		double initialAngle = Math.atan2((initialPoint.y - center.y), (initialPoint.x - center.x));
//		System.out.println("INICIAL ANGLE = " + initialAngle * 180 / Math.PI );
		this.pointsInArc = new ArrayList<Point3d>();
		double x, y, z, dAngulo = 0;

		// no sentido ANTIHORARIO
		dAngulo = deltaAngle / (n - 1);
		for(int i = 0; i < n; i++)
		{
			x = center.x + radius * Math.cos(initialAngle + i * dAngulo);
			y = center.y + radius * Math.sin(initialAngle + i * dAngulo);
			z = center.z;
			pointsInArc.add(i, new Point3d(x, y, z));
		}
//		System.out.println("SAIDA = " + pointsInArc);
		return pointsInArc;
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