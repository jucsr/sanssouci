package br.UFSC.GRIMA.util.findPoints;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
/**
 * 
 * @author Jc
 *
 */
public class LimitedArc extends LimitedElement
{
	private Point3d center;
	private double deltaAngle;
	private ArrayList<Point3d> pointsInArc;
	private double radius;
	public static final int CW = 0;
	public static final int CCW = 1;
	private int sense = 0, n = 50; // n = number of points in arc
	private double initialAngle;
	
	public LimitedArc()
	{
		this.center = new Point3d(0.0, 0.0, 0.0);
		this.initialPoint = new Point3d(1.0, 0.0, 0.0);
		double deltaAngle = Math.PI/2;
		this.sense = 1;
		Point3d vectorInitial = GeometricOperations.minus(this.initialPoint, this.center);
		double initialAngle = GeometricOperations.angle(vectorInitial);

		this.radius = GeometricOperations.distance(this.initialPoint, this.center);
		
		this.finalPoint = new Point3d();
		this.finalPoint.setX(this.center.getX()+this.radius*Math.cos(deltaAngle+initialAngle));
		this.finalPoint.setY(this.center.getY()+this.radius*Math.sin(deltaAngle+initialAngle));
		this.finalPoint.setZ(0);
	}	
	
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
	 * @param deltaAngle --> in radians
	 * @param sense 0 clockwise 1 counter clockwise
	 */
	public LimitedArc(Point3d center, Point3d initialPoint, double deltaAngle, int sense)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.deltaAngle = deltaAngle;
		this.radius = GeometricOperations.distance(this.initialPoint, this.center);
		this.sense = sense;
		
//		Point3d axisX = new Point3d(1.0, 0, 0);
		Point3d vectorInitial = GeometricOperations.minus(this.initialPoint, this.center);
		double initialAngle = GeometricOperations.angle(vectorInitial);
		
//		initialAngle = Math.acos(GeometricOperations.escalar(axisX, vectorInitial)/GeometricOperations.norm(axisX)/GeometricOperations.norm(vectorInitial));
//		if (vectorInitial.getY() < 0)
//			initialAngle = -initialAngle;
		this.sense = sense;

		if (this.radius!=0)
		{
			this.finalPoint = new Point3d();
			this.finalPoint.setX(this.center.getX()+this.radius*Math.cos(deltaAngle+initialAngle));
			this.finalPoint.setY(this.center.getY()+this.radius*Math.sin(deltaAngle+initialAngle));
			this.finalPoint.setZ(this.getCenter().getZ());
		}
		else
		{
			this.finalPoint = this.center;
		}
	}
	/**
	 * 
	 * @param center
	 * @param initialPoint
	 * @param deltaAngle radianos
	 */
	public LimitedArc(Point3d center, Point3d initialPoint, double deltaAngle)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.deltaAngle = deltaAngle;
		this.radius = this.initialPoint.distance(this.center);
		if(deltaAngle < 0)
		{
			this.sense = CW;
		}
		else
		{
			this.sense = CCW;
		}
		
//		Point3d axisX = new Point3d(1.0, 0, 0);
		Point3d vectorInitial = GeometricOperations.minus(this.initialPoint, this.center);
//		this.initialAngle = GeometricOperations.angle(center,initialPoint);
		this.initialAngle = GeometricOperations.angle(vectorInitial);

		
//		initialAngle = Math.acos(GeometricOperations.escalar(axisX, vectorInitial)/GeometricOperations.norm(axisX)/GeometricOperations.norm(vectorInitial));
//		if (vectorInitial.getY() < 0)
//			initialAngle = -initialAngle;

		if (this.radius != 0)
		{
			this.finalPoint = new Point3d();
			this.finalPoint.setX(this.center.x + this.radius * Math.cos(deltaAngle + initialAngle));
			this.finalPoint.setY(this.center.y + this.radius * Math.sin(deltaAngle + initialAngle));
			this.finalPoint.setZ(this.center.z);
		}
		else
		{
			this.finalPoint = this.center;
		}
	}
	
	public LimitedArc(Point3d initialPoint, Point3d finalPoint, Point3d center)
	{
		this.center = center;
		this.initialPoint = initialPoint;
		this.finalPoint = finalPoint;
		Point3d vInitial = GeometricOperations.minus(this.initialPoint,this.center);
		Point3d vFinal = GeometricOperations.minus(this.finalPoint,this.center);
		
		this.deltaAngle = GeometricOperations.angle(vFinal)-GeometricOperations.angle(vInitial);
//		if(deltaAngle < 0)
//		{
//			this.sense = CW;
//		}
//		else
//		{
//			this.sense = CCW;
//		}
		this.radius = GeometricOperations.distance(this.center, this.initialPoint);
		this.determinatePointsInArc();
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

	public double getDeltaAngle() {
		return deltaAngle;
	}

	public void setDeltaAngle(double deltaAngle) {
		this.deltaAngle = deltaAngle;
	}

	public double getInitialAngle() {
		return initialAngle;
	}

	public void setInitialAngle(double initialAngle) {
		this.initialAngle = initialAngle;
	}
}