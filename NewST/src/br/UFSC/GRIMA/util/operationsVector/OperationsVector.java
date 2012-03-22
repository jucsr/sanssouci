package br.UFSC.GRIMA.util.operationsVector;

import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.findPoints.PointsGenerator;
/**
 * 
 * @author Jc
 *
 */
public class OperationsVector 
{
	public static Vector3d crossVector(Vector3d v1, Vector3d v2)
	{
		Vector3d v3 = new Vector3d();
		v3.x = v1.y * v2.z - v2.y * v1.z;
		v3.y = -(v1.x * v2.z - v2.x * v1.z);
		v3.z = v1.x * v2.y - v2.x * v1.y;
		
		return v3;
	}
	
	public static Vector3d crossVector(Point3d p1, Point3d p2)
	{
		Vector3d v3 = new Vector3d();
		v3.x = p1.y * p2.z - p2.y * p1.z;
		v3.y = -(p1.x * p2.z - p2.x * p1.z);
		v3.z = p1.x * p2.y - p2.x * p1.y;
		
		return v3;
	}
	
	public static double moduleVector(Vector3d v)
	{
		double m = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
		return m;
	}
	
	public static double moduleVector(Point3d p)
	{
		double m = Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
		return m;
	}
	
	public static double distanceVector(Vector3d v1, Vector3d v2)
	{
		return Math.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y)
				* (v1.y - v2.y) + (v1.z - v2.z) * (v1.z - v2.z));
	}
	
	public static double distanceVector(Point3d p1, Point3d p2)
	{
		double distance = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y) + (p1.z - p2.z) * (p1.z - p2.z));
		
//		System.err.println("distanceVector = " + distance);
		return distance;
	}
	
	public static Vector3d negativeVector(Vector3d v0)
	{
		Vector3d v1 = new Vector3d();
		v1.setX(-v0.getX());
		v1.setY(-v0.getY());
		v1.setZ(-v0.getZ());
		return v1;
	}
	
	public static Vector3d negativeVector(Point3d p0)
	{
		Vector3d v1 = new Vector3d();
		v1.setX(-p0.getX());
		v1.setY(-p0.getY());
		v1.setZ(-p0.getZ());
		return v1;
	}
	
	public static Vector3d plusVector(Vector3d v1, Vector3d v2)
	{
		Vector3d r = new Vector3d();
		r.setX(v1.x + v2.x);
		r.setY(v1.y + v2.y);
		r.setZ(v1.z + v2.z);
		
		return r;
	}
	
	public static Vector3d plusVector(Point3d p1, Point3d p2)
	{
		Vector3d r = new Vector3d();
		r.setX(p1.x + p2.x);
		r.setY(p1.y + p2.y);
		r.setZ(p1.z + p2.z);

		return r;
	}
	
	public static Point3d plusVector(Point3d v1, Vector3d v2)
	{
		Point3d r = new Point3d();
		r.setX(v1.x + v2.x);
		r.setY(v1.y + v2.y);
		r.setZ(v1.z + v2.z);
		
		return r;
	}
	
	public static Point3d nearestPoint(Point3d p0, LimitedLine line)
	{
		Point3d n = new Point3d();
		Point3d p1 = line.getFp();
		Point3d p2 = line.getSp();
		
		double u = ((p0.x - p1.x) * (p2.x - p1.x) + (p0.y - p1.y) * (p2.y - p1.y)) / 
				(distanceVector(p1, p2) * distanceVector(p1, p2));
		n.x = p1.x + u * (p2.x - p1.x);
		n.y = p1.y + u * (p2.y - p1.y);
		n.z = 0;
		return n;
	}
	
	public static Point3d nearestPoint(Point3d point, LimitedCircle circle)
	{
		
		Point3d n = new Point3d();
		n.x = circle.getCenter().x
				+ circle.getRadius()
				* (point.x - circle.getCenter().x)
				/ (calculateDistanceBetweenCircleAndPoint(point, circle) + circle
						.getRadius());
		n.y = circle.getCenter().y
				+ circle.getRadius()
				* (point.y - circle.getCenter().y)
				/ (calculateDistanceBetweenCircleAndPoint(point, circle) + circle
						.getRadius());
		n.z = 0;
		return n;
	}
	
	/**
	 * This method calculate the distance between a line and a point in space 3d
	 * @param line --> the line 
	 * @param point --> point
	 * @return --> double distance
	 */
	public double calculateDistanceBetweenLineAndPoint(LimitedLine line, Point3d point)
	{
		double d = 0;
		Point3d v0 = point;
		Point3d v1 = line.getFp();
		Point3d v2 = line.getSp();

		d = moduleVector(crossVector(plusVector(v0, negativeVector(v1)),
				plusVector(v0, negativeVector(v2))))
				/ moduleVector(plusVector(v2, negativeVector(v1)));

		return d;
	}
	/**
	 * This method calculate the distance between a circle and a point in plane XY
	 * @param p --> point  
	 * @param c --> center
	 * @return --> distance
	 */
	public static double calculateDistanceBetweenCircleAndPoint(Point3d p, LimitedCircle c)
	{
		double d = Math.sqrt((p.x - c.getCenter().x) * (p.x - c.getCenter().x)
				+ (p.y - c.getCenter().y) * (p.y - c.getCenter().y))
				- c.getRadius();
		return d;
	}
	/**
	 * This method calculate the distance between a circle and a limited line in plane XY
	 * @param limitedLine --> limited line  
	 * @param limitedCircle --> circle
	 * @return --> distance
	 */
	
	public static double calculateShortestDistanceBetweenCircleAndLine(LimitedLine limitedLine, LimitedCircle limitedCircle, int N)
	{		
		PointsGenerator rpg=new PointsGenerator(limitedLine,N);
		Point3d pL = new Point3d();
		Point3d pC = new Point3d();
		rpg.fillPoints();
//		rpg.generatePointsInLine();
//		System.out.println("points in line = " + rpg.getPoints());
		double distancia=0;
		int n=1;
		
		
		for (Point3d rPoint:rpg.getPoints())
		{
			pL=nearestPoint(rPoint, limitedLine);
			
			if(pL.x >= limitedLine.xminl && pL.x <= limitedLine.xmaxl && pL.y >= limitedLine.yminl && pL.y <= limitedLine.ymaxl)
			{
				
			}
			else
			{
				continue;
			}

			pC=nearestPoint(rPoint,limitedCircle);
			double distanciaTemp = distanceVector(new Vector3d(pL),new Vector3d(pC));
			if (n==1)
			{
				distancia=distanciaTemp;
			}
			
			if (distancia > distanciaTemp)
			{
				distancia=distanciaTemp;
			}			
			n++;
		}
//		System.out.println(n + "\td = " + distancia);
		return distancia;
	}
	/**
	 * This method calculate the distance between two limited line in plane XY
	 * @param l1 --> limited line 1  
	 * @param l2 --> limited line 2
	 * @return --> distance
	 */
	
	public static double calculateShortestDistanceBetweenTwoLimitedCrossedLine(LimitedLine l1, LimitedLine l2, int N)
	{
		PointsGenerator rpg=new PointsGenerator(l1, N);
		Point3d pL1 = new Point3d();
		Point3d pL2 = new Point3d();
		rpg.fillPoints();
		
		double distancia=0;
		double distanciaTemp=0;
		int n=1;
		
		for (Point3d rPoint:rpg.getPoints())
		{
			pL2=nearestPoint(rPoint, l2);
			
			//System.out.println("rP x " + rPoint.x + " y " + rPoint.y + "\tnP x " + pL2.x + " y " + pL2.y);
			
			if(pL2.x >= l2.xminl && pL2.x <= l2.xmaxl && pL2.y >= l2.yminl && pL2.y <= l2.ymaxl)
			{
				distanciaTemp = distanceVector(new Vector3d(rPoint),new Vector3d(pL2));
				
				if (n==1)
				{
					distancia=distanciaTemp;
				}
				
				if (distancia > distanciaTemp)
				{
					distancia=distanciaTemp;
				}							
			}
			n++;			
		}
		//System.out.println(n + "\td = " + distancia);
		
		return distancia;
	}
	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static double chooseShortestDistanceBetweenTwoLimitedLines(LimitedLine l1, LimitedLine l2, int N)
	{
		double shortDistance=0;
		double d1=calculateShortestDistanceBetweenTwoLimitedCrossedLine(l1,l2,N);
		double dTemp=0;
		if(d1==0)
		{
			//System.out.println("d1=0");
			double d2=calculateShortestDistanceBetweenTwoLimitedCrossedLine(l2,l1,N);
			if (d2==0)
			{
				//System.out.println("d2=0");
			}
			else
			{
				//System.out.println("d2="+d2);
				dTemp=d2;
			}
		}
		else
		{
			//System.out.println("d1="+d1);
			dTemp=d1;
		}
		double dl1=distanceVector(l1.getFp(),l2.getFp());
		double dl2=distanceVector(l1.getFp(),l2.getSp());
		double dl3=distanceVector(l1.getSp(),l2.getFp());
		double dl4=distanceVector(l1.getSp(),l2.getSp());
		
		/*
		System.out.println("dl1="+dl1);
		System.out.println("dl2="+dl2);
		System.out.println("dl3="+dl3);
		System.out.println("dl4="+dl4);
		*/
		if (dTemp!=0)
		{
			shortDistance=Math.min(Math.min(dTemp,Math.min(dl1, dl2)),Math.min(dl3,dl4));
		}
		else
		{
			shortDistance=Math.min(Math.min(dl1,dl2), Math.min(dl3,dl4));
		}

		System.out.println("Sd = "+shortDistance);
		return shortDistance;
	}
	/**
	 * 
	 * @param circle
	 * @param arc
	 * @return
	 */
	public static double calculateShortestDistanceBeetweenArcAndCircle(LimitedCircle circle, LimitedArc arc)
	{
		double shortestDistance = distanceVector(circle.getCenter(), arc.getPointsInArc().get(0)) - circle.getRadius();
		double distanceTmp = distanceVector(circle.getCenter(), arc.getPointsInArc().get(0)) - circle.getRadius();
		for(int i = 0; i < arc.getPointsInArc().size(); i++)
		{
			distanceTmp = distanceVector(circle.getCenter(), arc.getPointsInArc().get(i)) - circle.getRadius();
			if(distanceTmp < shortestDistance)
			{
				shortestDistance = distanceTmp;
			}
		}
		return shortestDistance;
	}
	
}