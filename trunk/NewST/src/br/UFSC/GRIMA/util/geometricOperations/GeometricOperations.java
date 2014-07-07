package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class GeometricOperations 
{
	public static ArrayList<Point3d> intersecoes = new ArrayList<Point3d>();
	private static double SMALL_NUM  =  0.00000001;

	private static double chooseMinimum(ArrayList<Double> distances)
	{
		double minimum=10E49;
		int i = 0;
		for (double d:distances)
		{
			if (i==0)
				minimum = d;
			else if (minimum > d)
				minimum = d;
			i++;
		}
		return minimum;
	}
	
	
	private static double chooseMinimum(double ...distances)
	{
		double minimum=10E49;
		int i = 0;
		for (double d:distances)
		{
			if (i==0)
				minimum = d;
			else if (minimum > d)
				minimum = d;
			i++;
		}
		return minimum;
	}

	private static int chooseMinimumIndex(ArrayList<Double> distances)
	{
		double minimum=10E49;
		int minimumIndex=0;
		int i = 0;
		for (double d:distances)
		{
			if (i==0)
			{
				minimum = d;
				minimumIndex = i;
			}
			else if (minimum > d)
			{
				minimum = d;
				minimumIndex = i;
			}
			i++;
		}
		return minimumIndex;
	}
	
	
	public static double distance2D(Point2D p1, Point2D p2)
	{
		double distance = Math.sqrt(Math.pow(p1.getX()-p2.getX(),2.0)+Math.pow(p1.getY()-p2.getY(), 2.0));
		return distance;
	}
	
	public static double distance(Point3d v1, Point3d v2)
	{
		double distance = Math.sqrt(Math.pow(v1.getX()-v2.getX(), 2.0) + Math.pow(v1.getY()-v2.getY(), 2.0)+Math.pow(v1.getZ()-v2.getZ(), 2.0));
		return distance;
	}
	
	public static double norm(Point3d p)
	{
		return Math.sqrt(p.getX()*p.getX()+p.getY()*p.getY()+p.getZ()*p.getZ());
	}
	
	public static Point3d plus(Point3d p1, Point3d p2)
	{
		Point3d plusPoint = new Point3d(p1.getX()+p2.getX(), p1.getY()+p2.getY(),p1.getZ()+p2.getZ());
		return plusPoint;
	}
	
	public static Point3d pointPlusEscalar(Point3d p, String svar, double dvar)
	{
		Point3d plusPoint = p;
		if(svar == "x")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY(),p.getZ());
		}
		else if(svar == "y")
		{
			plusPoint = new Point3d(p.getX(), p.getY()+dvar,p.getZ());
		}
		else if(svar == "z")
		{
			plusPoint = new Point3d(p.getX(), p.getY(),p.getZ()+dvar);
		}
		else if(svar == "+x+y")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY()+dvar,p.getZ());
		}
		else if(svar == "+x-y")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY()-dvar,p.getZ());
		}
		else if(svar == "-x+y")
		{
			plusPoint = new Point3d(p.getX()-dvar, p.getY()+dvar,p.getZ());
		}
		else if(svar == "-x-y")
		{
			plusPoint = new Point3d(p.getX()-dvar, p.getY()-dvar,p.getZ());
		}
		else if(svar == "+x+z")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY(),p.getZ()+dvar);
		}
		else if(svar == "+x-z")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY(),p.getZ()-dvar);
		}
		else if(svar == "-x+z")
		{
			plusPoint = new Point3d(p.getX()-dvar, p.getY(),p.getZ()+dvar);
		}
		else if(svar == "-x-z")
		{
			plusPoint = new Point3d(p.getX()-dvar, p.getY(),p.getZ()-dvar);
		}
		else if(svar == "+y+z")
		{
			plusPoint = new Point3d(p.getX(), p.getY()+dvar,p.getZ()+dvar);
		}
		else if(svar == "+y-z")
		{
			plusPoint = new Point3d(p.getX(), p.getY()+dvar,p.getZ()-dvar);
		}
		else if(svar == "-y+z")
		{
			plusPoint = new Point3d(p.getX(), p.getY()-dvar,p.getZ()+dvar);
		}
		else if(svar == "-y-z")
		{
			plusPoint = new Point3d(p.getX(), p.getY()-dvar,p.getZ()-dvar);
		}
		else if(svar == "+x+y+z")
		{
			plusPoint = new Point3d(p.getX()+dvar, p.getY()+dvar,p.getZ()+dvar);
		}
		return plusPoint;
	}
	

	public static Point3d minus(Point3d p1, Point3d p2)
	{
		Point3d minusPoint = new Point3d(p1.getX()-p2.getX(), p1.getY()-p2.getY(),p1.getZ()-p2.getZ());
		return minusPoint;
	}	
	
	public static Point3d multiply(double m, Point3d p)
	{
		return new Point3d(m*p.getX(),m*p.getY(),m*p.getZ());
	}
	
	public static Point3d crossVector(Vector3d v1, Vector3d v2)
	{
		Point3d v3 = new Point3d();
		v3.x = v1.y * v2.z - v2.y * v1.z;
		v3.y = -(v1.x * v2.z - v2.x * v1.z);
		v3.z = v1.x * v2.y - v2.x * v1.y;
		
		return v3;
	}
	
	public static double moduleVector(Point3d p)
	{
		double m = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY() + p.getZ() * p.getZ());
		return m;
	}
		
	public static Point3d nearestPoint(Point3d p,  LimitedLine line)
	{
		Point3d p1 = line.getFinalPoint();
		Point3d p0 = line.getInitialPoint();
		
		Point3d v = new Point3d(p1.x - p0.x, p1.y-p0.y, p1.z-p0.z);
		Point3d w = new Point3d(p.x - p0.x, p.y-p0.y, p.z-p0.z);
		
		double c1 = escalar(v,w);
		double c2 = escalar(v,v);
				
				
		if ( c1 <= 0 )  // before P0
		{
//			System.out.println("Nearest point " + p0);
			return p0;
		}
		if ( c2  <= c1 ) // after P1
		{
//			System.out.println("Nearest point " + p1);
			return p1;
		}

       double b = c1 / c2;
       Point3d Pb = new Point3d(p0.x + b*v.x, p0.y + b*v.y, p0.z + b*v.z);
//       System.out.println("Nearest point " + Pb);      
       return Pb;
	}
	
	public static double minimumDistancePointToLine(Point3d p,  LimitedLine line)
	{
		Point3d near = nearestPoint(p,line);
		return distance(p, near);
	}
	
	public static double escalar(Point3d p1, Point3d p2)
	{
		double escalar = p1.getX()*p2.getX()+p1.getY()*p2.getY()+p1.getZ()*p2.getZ();
		return escalar;
	}
	
	public static Point3d vectorial(Point3d p1, Point3d p2)
	{
		Point3d vectorial= new Point3d(p1.getY()*p2.getZ()-p2.getY()*p1.getZ(),-(p1.getX()*p2.getZ()-p2.getX()*p1.getZ()), p1.getX()*p2.getY()-p2.getX()*p1.getY());		
		return vectorial;
	}
	
//	public static double minimumDistanceLineToLine(LimitedLine S1, LimitedLine S2)
//	{
//	    Point3d   u = new Point3d(S1.getFinalPoint().getX() - S1.getInitialPoint().getX(), S1.getFinalPoint().getY() - S1.getInitialPoint().getY(), S1.getFinalPoint().getZ() - S1.getInitialPoint().getZ());
//	    Point3d   v = new Point3d(S2.getFinalPoint().getX() - S2.getInitialPoint().getX(), S2.getFinalPoint().getY() - S2.getInitialPoint().getY(), S2.getFinalPoint().getZ() - S2.getInitialPoint().getZ());
//	    Point3d   w = new Point3d(S1.getInitialPoint().getX() - S2.getInitialPoint().getX(), S1.getInitialPoint().getY() - S2.getInitialPoint().getY(), S1.getInitialPoint().getZ() - S2.getInitialPoint().getZ());
////	    Point3d   w = new Point3d(S1.getFinalPoint().getX() - S2.getFinalPoint().getX(), S1.getFinalPoint().getY() - S2.getFinalPoint().getY(), S1.getFinalPoint().getZ() - S2.getFinalPoint().getZ());
//
//	    double    a = escalar(u,u);         // always >= 0
//	    double    b = escalar(u,v);
//	    double    c = escalar(v,v);         // always >= 0
//	    double    d = escalar(u,w);
//	    double    e = escalar(v,w);
//	    double    D = a*c - b*b;        // always >= 0
//	    double    sc, sN, sD = D;       // sc = sN / sD, default sD = D >= 0
//	    double    tc, tN, tD = D;       // tc = tN / tD, default tD = D >= 0
//
//	    // compute the line parameters of the two closest points
//	    if (D < SMALL_NUM) 
//	    { // the lines are almost parallel
//	        sN = 0.0;         // force using point P0 on segment S1
//	        sD = 1.0;         // to prevent possible division by 0.0 later
//	        tN = e;
//	        tD = c;
//	    }
//	    else 
//	    {                 // get the closest points on the infinite lines
//	        sN = (b*e - c*d);
//	        tN = (a*e - b*d);
//	        if (sN < 0.0) {        // sc < 0 => the s=0 edge is visible
//	            sN = 0.0;
//	            tN = e;
//	            tD = c;
//	        }
//	        else if (sN > sD) {  // sc > 1  => the s=1 edge is visible
//	            sN = sD;
//	            tN = e + b;
//	            tD = c;
//	        }
//	    }
//
//	    if (tN < 0.0) 
//	    {            // tc < 0 => the t=0 edge is visible
//	        tN = 0.0;
//	        // recompute sc for this edge
//	        if (-d < 0.0)
//	            sN = 0.0;
//	        else if (-d > a)
//	            sN = sD;
//	        else 
//	        {
//	            sN = -d;
//	            sD = a;
//	        }
//	    }
//	    else if (tN > tD) 
//	    {      // tc > 1  => the t=1 edge is visible
//	        tN = tD;
//	        // recompute sc for this edge
//	        if ((-d + b) < 0.0)
//	            sN = 0;
//	        else if ((-d + b) > a)
//	            sN = sD;
//	        else {
//	            sN = (-d +  b);
//	            sD = a;
//	        }
//	    }
//	    // finally do the division to get sc and tc
//	    sc = (Math.abs(sN) < SMALL_NUM ? 0.0 : sN / sD);
//	    tc = (Math.abs(tN) < SMALL_NUM ? 0.0 : tN / tD);
//
//	    // get the difference of the two closest points
//	    Point3d   dP = new Point3d(w.getX() + (sc * u.getX()) - (tc * v.getX()), w.getY() + (sc * u.getY()) - (tc * v.getY()), w.getZ() + (sc * u.getZ()) - (tc * v.getZ()));  // =  S1(sc) - S2(tc)
//
//	    return norm(dP);   // return the closestdistance
//	}
	
	public static double minimumDistanceLineToLine(LimitedLine S1, LimitedLine S2)
	{
		double minimumDistance = 0;
		if(intersectionPoint(S1, S2) == null)
		{
			Point3d p1I = S1.getInitialPoint();
			Point3d p1F = S1.getFinalPoint();
			Point3d p2I = S2.getInitialPoint();
			Point3d p2F = S2.getFinalPoint();
			double tmp1 = p1I.distance(nearestPoint(p1I, S2));
			double tmp2 = p1F.distance(nearestPoint(p1F, S2));
			double tmp3 = p2I.distance(nearestPoint(p2I, S1));
			double tmp4 = p2F.distance(nearestPoint(p2F, S1));
			ArrayList<Double> distances = new ArrayList<Double>();
			distances.add(tmp1);
			distances.add(tmp2);
			distances.add(tmp3);
			distances.add(tmp4);
			minimumDistance = distances.get(0);
			for(Double tmp:distances)
			{
				if(tmp < minimumDistance)
				{
					minimumDistance = tmp;
				}
			}
		}
		return minimumDistance;
	}
	
	public static Point3d nearestPoint(Point3d p, LimitedArc arc)
	{
		Point3d v = new Point3d(p.getX()-arc.getCenter().getX(), p.getY()-arc.getCenter().getY(), p.getZ()-arc.getCenter().getZ());
		Point3d normalPoint = plus(arc.getCenter(),multiply(arc.getRadius()/norm(v),v));
		
		if (!contentsPoint(normalPoint, arc))
		{
			normalPoint = plus(arc.getCenter(),multiply(-arc.getRadius()/norm(v),v));
			//System.out.println("New normal point " + normalPoint);
			ArrayList<Double> distances = new ArrayList<Double>();
			ArrayList<Point3d> points = new ArrayList<Point3d>();
			
			distances.add(distance(p, arc.getInitialPoint()));
			points.add(arc.getInitialPoint());
			
			distances.add(distance(p,arc.getFinalPoint()));
			points.add(arc.getFinalPoint());
			
			distances.add(distance(p, normalPoint));
			points.add(normalPoint);
			
			int indexMinimum = chooseMinimumIndex(distances);
			
			return points.get(indexMinimum);
		}
		//System.out.println("Normal point " + normalPoint);
		return normalPoint;

	}
	
	public static double minimumDistancePointToArc(Point3d p, LimitedArc arc)
	{	
		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp = arc.getInitialPoint();
			Point3d temp1 = arc.getFinalPoint();
			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
		}
		Point3d v = new Point3d(p.getX()-arc.getCenter().getX(), p.getY()-arc.getCenter().getY(), p.getZ()-arc.getCenter().getZ());
		Point3d normalPoint = plus(arc.getCenter(),multiply(arc.getRadius()/norm(v),v));
		
		if (!contentsPoint(normalPoint, arc))
		{
			normalPoint = plus(arc.getCenter(),multiply(-arc.getRadius()/norm(v),v));
			//System.out.println("New normal point " + normalPoint);
			return chooseMinimum(distance(p, arc.getInitialPoint()), distance(p,arc.getFinalPoint()), distance(p, normalPoint));			
		}
		//System.out.println("Normal point " + normalPoint);
		return distance(p, normalPoint);
	}	

	public static double minimumDistanceArcToLine(LimitedArc arc, LimitedLine line)
	{
		return minimumDistanceLineToArc(line, arc);
	}
	
//	public static double minimumDistanceLineToArc(LimitedLine line, LimitedArc arc)
//	{
//		double distance=0.0;
//		
//		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
//		if(arc.getDeltaAngle() < 0)
//		{
//			Point3d temp = arc.getInitialPoint();
//			Point3d temp1 = arc.getFinalPoint();
//			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
//		}
//		
//		//System.out.println("Center of arc " + arc.getCenter());
//		Point3d nearestFromLine = nearestPoint(arcTmp.getCenter(), line);
//		//System.out.println("Nearest calculated*****" + nearestFromLine);
//		
//		if(chooseMinimum(distance(arcTmp.getCenter(), line.getInitialPoint()), distance(arcTmp.getCenter(), line.getFinalPoint())) <= arcTmp.getRadius())
//		{
//			//System.out.println("Line within the arc ");
//			//System.out.println("Arc Initial to line" + minimumDistance(arc.getInitialPoint(), line));
//			distance = chooseMinimum(minimumDistancePointToLine(arcTmp.getInitialPoint(), line), minimumDistancePointToLine(arcTmp.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(), arcTmp), minimumDistancePointToArc(line.getFinalPoint(), arcTmp));
//			return distance;
//		}
//		
//		Point3d v = new Point3d(nearestFromLine.getX()-arcTmp.getCenter().getX(), nearestFromLine.getY()-arcTmp.getCenter().getY(), nearestFromLine.getZ()-arcTmp.getCenter().getZ());
//		Point3d normalPoint = plus(arcTmp.getCenter(),multiply(arcTmp.getRadius()/norm(v),v));
//		if (!contentsPoint(normalPoint, arcTmp))
//		{
//			//System.out.println("normalPoint is not within the arc");
//			return chooseMinimum(minimumDistancePointToLine(arcTmp.getInitialPoint(),line), minimumDistancePointToLine(arcTmp.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(),arcTmp), minimumDistancePointToArc(line.getFinalPoint(), arcTmp));
//		}
//
//		distance = distance(normalPoint,nearestFromLine);
//		return distance;
//	}
	
	public static double minimumDistanceLineToArc(LimitedLine line, LimitedArc arc)
	{
		double distance=0.0;
		
		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp = arc.getInitialPoint();
			Point3d temp1 = arc.getFinalPoint();
			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
		}
		
		Point3d nearestFromLine = nearestPoint(arcTmp.getCenter(), line);
		
		if(chooseMinimum(distance(arcTmp.getCenter(), line.getInitialPoint()), distance(arcTmp.getCenter(), line.getFinalPoint())) <= arcTmp.getRadius())
		{
			//System.out.println("Line within the arc ");
			//System.out.println("Arc Initial to line" + minimumDistance(arc.getInitialPoint(), line));
			distance = chooseMinimum(minimumDistancePointToLine(arcTmp.getInitialPoint(), line), minimumDistancePointToLine(arcTmp.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(), arcTmp), minimumDistancePointToArc(line.getFinalPoint(), arcTmp));
			return distance;
		}
		
		Point3d v = new Point3d(nearestFromLine.getX()-arcTmp.getCenter().getX(), nearestFromLine.getY()-arcTmp.getCenter().getY(), nearestFromLine.getZ()-arcTmp.getCenter().getZ());
		Point3d normalPoint = plus(arcTmp.getCenter(),multiply(arcTmp.getRadius()/norm(v),v));
		if (!contentsPoint(normalPoint, arcTmp))
		{
			//System.out.println("normalPoint is not within the arc");
			return chooseMinimum(minimumDistancePointToLine(arcTmp.getInitialPoint(),line), minimumDistancePointToLine(arcTmp.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(),arcTmp), minimumDistancePointToArc(line.getFinalPoint(), arcTmp));
		}

		distance = distance(normalPoint,nearestFromLine);
		return distance;
	}

	public static Point3d nearestPoint(LimitedArc arc1, LimitedArc arc2)
	{
		ArrayList<Double> distances = new ArrayList<Double>();
		ArrayList<Point3d> pointsArc2 = new ArrayList<Point3d>();
		ArrayList<Point3d> pointsArc1 = new ArrayList<Point3d>();
		
		Point3d point1Near2 = new Point3d();
		Point3d point2Near1 = new Point3d();
		
		point1Near2 = nearestPoint(arc1.getCenter(), arc2);
		point2Near1 = nearestPoint(arc2.getCenter(), arc1);
		
		Point3d v1 = new Point3d();
		Point3d v2 = new Point3d();
		
		v1 = multiply(1.0/distance(point1Near2, arc1.getCenter()),minus(point1Near2, arc1.getCenter()));
		v2 = multiply(1.0/distance(point2Near1, arc2.getCenter()),minus(point2Near1, arc2.getCenter()));
		
		Point3d n1 = new Point3d();
		Point3d n2 = new Point3d();
		
		n1 = plus(arc1.getCenter(), multiply(arc1.getRadius(),v1));
		n2 = plus(arc2.getCenter(), multiply(arc2.getRadius(),v2));
		
//		System.out.println("-----------------------------");
//		System.out.println("Nearest Point from center 1 " + arc1.getCenter() + " to arc 2"  + point1Near2);		
//		System.out.println("Nearest Point from center 2 " + arc2.getCenter() + " to arc 1"  + point2Near1);
//		System.out.println("Within arc " +  arc1.getInitialPoint() +  " to " + arc1.getFinalPoint()) ;
//		System.out.println("Nearest Point " + point1Near2);
		if (contentsPoint(n1, arc1))
		{
			pointsArc1.add(n1);
			pointsArc2.add(point1Near2);
			distances.add(distance(n1, point1Near2));
		}
//		System.out.println("Within arc " +  arc2.getInitialPoint() +  " to " + arc2.getFinalPoint()) ;
//		System.out.println("Nearest Point " + point2Near1);
		if (contentsPoint(n2, arc2))
		{
			pointsArc1.add(point1Near2);
			pointsArc2.add(n2);		
			distances.add(distance(n2, point2Near1));
		}

		distances.add(minimumDistancePointToArc(arc1.getInitialPoint(), arc2));
		pointsArc1.add(arc1.getInitialPoint());
		pointsArc2.add(nearestPoint(arc1.getInitialPoint(), arc2));
//		System.out.println("--------------");
//		System.out.println("From " + arc1.getInitialPoint() + " to arc  from " + arc2.getInitialPoint() + " to " + arc2.getFinalPoint());
//		System.out.println(minimumDistance(arc1.getInitialPoint(), arc2));
		
		distances.add(minimumDistancePointToArc(arc1.getFinalPoint(), arc2));
		pointsArc1.add(arc1.getFinalPoint());
		pointsArc2.add(nearestPoint(arc1.getFinalPoint(), arc2));
//		System.out.println("--------------");
//		System.out.println("From " + arc1.getFinalPoint() + " to arc  from " + arc2.getInitialPoint() + " to " + arc2.getFinalPoint());
//		System.out.println(minimumDistance(arc1.getFinalPoint(), arc2));
		
		distances.add(minimumDistancePointToArc(arc2.getInitialPoint(), arc1));
		pointsArc1.add(nearestPoint(arc2.getInitialPoint(),arc1));
		pointsArc2.add(arc2.getInitialPoint());
//		System.out.println("--------------");
//		System.out.println("From " + arc2.getInitialPoint() + " to arc  from " + arc1.getInitialPoint() + " to " + arc1.getFinalPoint());
//		System.out.println(minimumDistance(arc2.getInitialPoint(), arc1));
		
		distances.add(minimumDistancePointToArc(arc2.getFinalPoint(), arc1));
		pointsArc1.add(nearestPoint(arc2.getFinalPoint(),arc1));
		pointsArc2.add(arc2.getFinalPoint());
//		System.out.println("--------------");
//		System.out.println("From " + arc2.getFinalPoint() + " to arc  from " + arc1.getInitialPoint() + " to " + arc1.getFinalPoint());
//		System.out.println(minimumDistance(arc2.getFinalPoint(), arc1));

		int indexMinimum = chooseMinimumIndex(distances);
		//System.out.println("Nearest point in Arc1 is " + pointsArc1.get(indexMinimum));
		//System.out.println("Nearest point in Arc2 is " + pointsArc2.get(indexMinimum));
		return pointsArc2.get(chooseMinimumIndex(distances));
		
	}
	
//	public static double minimumDistanceArcToArc(LimitedArc arc1, LimitedArc arc2)
//	{
//		LimitedArc arc1Tmp = new LimitedArc(arc1.getInitialPoint(), arc1.getFinalPoint(), arc1.getCenter());
//		LimitedArc arc2Tmp = new LimitedArc(arc2.getInitialPoint(), arc2.getFinalPoint(), arc2.getCenter());
//
//		if(arc1.getDeltaAngle() < 0)
//		{
//			Point3d temp = arc1.getInitialPoint();
//			Point3d temp1 = arc1.getFinalPoint();
//			arc1Tmp = new LimitedArc(temp1, temp, arc1.getCenter());
//		}
//		if(arc2.getDeltaAngle() < 0)
//		{
//			Point3d temp = arc2.getInitialPoint();
//			Point3d temp1 = arc2.getFinalPoint();
//			arc2Tmp = new LimitedArc(temp1, temp, arc2.getCenter());
//
//		}
//		return minimumDistancePointToArc(nearestPoint(arc1Tmp,arc2Tmp),arc1Tmp);
//	}
	
	public static double minimumDistanceArcToArc(LimitedArc arc1, LimitedArc arc2)
	{
		double minimumDistance = 0;
		if(intersectionPoint(arc1, arc2) == null)
		{
			LimitedLine centerToCenter = new LimitedLine(arc1.getCenter(), arc2.getCenter());
			
			ArrayList<Point3d> intersection = new ArrayList<Point3d>();
			if(intersectionPoint(arc1, centerToCenter) != null)
			{
				intersection.add(intersectionPoint(arc1, centerToCenter).get(0));
			}
			if(intersectionPoint(arc2, centerToCenter) != null)
			{
				intersection.add(intersectionPoint(arc2, centerToCenter).get(0));
			}
			if(intersection.size() == 2)
			{
				minimumDistance = intersection.get(0).distance(intersection.get(1));
			}
			else if(intersection.size() == 1)
			{
				if(belongsArc(arc1,intersection.get(0)))
				{
					double distanceTmp1 = arc1.getCenter().distance(arc2.getInitialPoint()) - arc1.getRadius();
					double distanceTmp2 = arc1.getCenter().distance(arc2.getFinalPoint()) - arc1.getRadius();
					if(distanceTmp1 < distanceTmp2)
					{
						minimumDistance = distanceTmp1;
					}
					else
					{
						minimumDistance = distanceTmp2;
					}
				}
				else
				{
					if(belongsArc(arc2,intersection.get(0)))
					{
						double distanceTmp1 = arc2.getCenter().distance(arc1.getInitialPoint()) - arc2.getRadius();
						double distanceTmp2 = arc2.getCenter().distance(arc1.getFinalPoint()) - arc2.getRadius();
						if(distanceTmp1 < distanceTmp2)
						{
							minimumDistance = distanceTmp1;
						}
						else
						{
							minimumDistance = distanceTmp2;
						}
					}
				}
			}
			else
			{
				double distanceTmp1 = arc1.getInitialPoint().distance(arc2.getInitialPoint());
				double distanceTmp2 = arc1.getInitialPoint().distance(arc2.getFinalPoint());
				double distanceTmp3 = arc2.getInitialPoint().distance(arc1.getInitialPoint());
				double distanceTmp4 = arc2.getInitialPoint().distance(arc1.getFinalPoint());
				ArrayList<Double> distanceTemp = new ArrayList<Double>();
				distanceTemp.add(distanceTmp1);
				distanceTemp.add(distanceTmp2);
				distanceTemp.add(distanceTmp3);
				distanceTemp.add(distanceTmp4);
				minimumDistance = distanceTemp.get(0);
				for(Double tmp:distanceTemp)
				{
					if(tmp < minimumDistance)
					{
						minimumDistance = tmp;
					}
				}
			}
		}
		return minimumDistance;
	}
	
	public static double angle(Point3d v)
	{
		double alpha=0.0;
		Point3d axisX = new Point3d(1.0, 0.0, 0.0);
		alpha = Math.acos(escalar(axisX, v)/norm(axisX)/norm(v));
				
		if(v.getY() < 0)
			alpha = -alpha + 2*Math.PI;
		//System.out.println("Alpha from angle " + alpha*180/Math.PI);

		return alpha;
	}
		
	public static boolean contentsPoint(Point3d p, LimitedArc arc)
	{
		boolean contents = false;
		Point3d v = minus(p,arc.getCenter());
		double beta = angle(v);
		double alpha = angle(minus(arc.getInitialPoint(), arc.getCenter()));
		double omega = angle(minus(arc.getFinalPoint(), arc.getCenter()));
//		System.out.println("Point p " + p);
		if (alpha > omega)
		{
			alpha = alpha - 2*Math.PI;
		}
//		System.out.println("angle between p and axisX " + beta*180/Math.PI);
//		System.out.println("angle between axisX and initialPoint " + alpha*180/Math.PI);
//		System.out.println("angle between axisX and finalPoint " + omega*180/Math.PI);
		if (alpha <= beta && beta <= omega)
		{
			contents = true;
//			System.out.println("The point p " + p + " is within " + arc.getInitialPoint() + " and " + arc.getFinalPoint());
		}
		else
		{
//			System.out.println("The point p " + p + " is not within " + arc.getInitialPoint() + " and " + arc.getFinalPoint());			
		}
		return contents;
	}
	
	/**
	 * 
	 * @param p1 initialPoint
	 * @param p2 finalPoint
	 * @return
	 */
	public static Point3d unitVector(Point3d p1, Point3d p2)
	{
		//System.out.println("Unit vector from " + p1 + " to " + p2 + " " + multiply(1/norm(minus(p2,p1)),minus(p2,p1)));
		return multiply(1/norm(minus(p2,p1)),minus(p2,p1));
	}

	public static LimitedArc roundVertexBetweenAdjacentLines(LimitedLine line1, LimitedLine line2, double radius)	
	{
		Point3d p1 = line1.getInitialPoint();
		Point3d p2 = line1.getFinalPoint();
		Point3d p3 = line2.getFinalPoint();
		
		return roundVertex(p1, p2, p3, radius);
	}
	
	public static LimitedArc roundVertex(Point3d p1, Point3d p2, Point3d p3, double radius)
	{
		double initialAngle = angle(minus(p3,p2));
		double finalAngle = angle(minus(p1,p2));
		
		if (initialAngle > finalAngle)
			initialAngle = initialAngle - 2*Math.PI;
		
		double teta = finalAngle-initialAngle;

//		System.out.println("InitialAngle " + initialAngle*180/Math.PI);
//		System.out.println("FinalAngle " + finalAngle*180/Math.PI);
//		System.out.println("Teta " + teta*180/Math.PI);
		
		double alfa = Math.PI-teta;
		double centerAngle = initialAngle + teta/2;
		if (teta >= Math.PI)
		{
			alfa = teta-Math.PI;
			double alfaPrima = 2*Math.PI-teta;
			centerAngle = initialAngle - alfaPrima/2;
		}		
//		System.out.println("Alpha " + alfa*180/Math.PI);
		
		double distanceToCenterFromP2 = Math.sqrt(1+Math.tan(alfa/2)*Math.tan(alfa/2))*radius;
//		System.out.println("Angle to center point " + centerAngle*180/Math.PI);

		Point3d center = new Point3d(p2.getX()+distanceToCenterFromP2*Math.cos(centerAngle), p2.getY()+distanceToCenterFromP2*Math.sin(centerAngle),p2.getZ());
		
		Point3d initialPoint = plus(p2,multiply(Math.tan(alfa/2)*radius, unitVector(p2,p1)));
		
		LimitedArc arc = new LimitedArc();
		if (teta >= Math.PI)
			arc = new LimitedArc(center, initialPoint, -alfa);
		else
			arc = new LimitedArc(center, initialPoint, alfa);
		
		return arc;
	}
	/**
	 * 
	 * @param addPocket
	 * @return -- o array de elementos do path de acabamento
	 */
	public static ArrayList<LimitedElement> acabamentoPath (GeneralClosedPocketVertexAdd addPocket, double radius)	
	{
		boolean inside = true;
		ArrayList<LimitedElement> etmp = new ArrayList<LimitedElement>();
		etmp = addPocket.getElements();
		ArrayList<LimitedElement> acabamentoElements = parallelPath1(etmp, radius,inside);
		
		return acabamentoElements;
	}
	
	public static GeneralPath linearPathToGeneralPath(ArrayList<LinearPath> linhas)
	{
		GeneralPath shape = new GeneralPath();
		for (int i = 0; i < linhas.size(); i++)
		{
			if(i==0)
			{
				shape.moveTo(linhas.get(i).getInitialPoint().getX(), linhas.get(i).getInitialPoint().getY());
			}
			else
			{
				shape.lineTo(linhas.get(i).getInitialPoint().getX(), linhas.get(i).getInitialPoint().getY());
			}
		}
		shape.closePath();
		return shape;
	}
	
	public static ArrayList<LinearPath> acabamentoLinearPath(GeneralClosedPocketVertexAdd addPocket, double radius)
	{
		ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>(); 
		saida = acabamentoPath(addPocket, radius);
		ArrayList<LinearPath> linearSaida = new ArrayList<LinearPath>();		
		for(LimitedElement s:saida)
		{
			if (s.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)s;
				LinearPath linearPath = new LinearPath(line.getInitialPoint(), line.getFinalPoint(),LinearPath.SLOW_MOV);
				linearSaida.add(linearPath);
			}
			if (s.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)s;
			
				CircularPath circ = new CircularPath(arc.getCenter(), arc.getInitialPoint(), arc.getFinalPoint(), arc.getDeltaAngle(),CircularPath.CCW);
				
				for(LinearPath line:GeometricOperations.arcToLinear(circ, 100))
				{
					linearSaida.add(line);
				}
			}
		}
		return linearSaida;
	}
	
	public static ArrayList<LinearPath> elementsLinearPath(ArrayList<LimitedElement> saida)
	{
		ArrayList<LinearPath> linearSaida = new ArrayList<LinearPath>();		
		for(LimitedElement s:saida)
		{
			if (s.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)s;
				LinearPath linearPath = new LinearPath(line.getInitialPoint(), line.getFinalPoint(),LinearPath.SLOW_MOV);
				linearSaida.add(linearPath);
			}
			if (s.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)s;
			
				CircularPath circ = new CircularPath(arc.getCenter(), arc.getInitialPoint(), arc.getFinalPoint(), arc.getDeltaAngle(),CircularPath.CCW);
				
				for(LinearPath line:GeometricOperations.arcToLinear(circ, 100))
				{
					linearSaida.add(line);
				}
			}
		}
		return linearSaida;
	}
	
	/**
	 * 
	 * @param arcPath 
	 * @param n : number of lines to make the arc
	 * @return : ArrayList of LinearPath
	 */
	public static ArrayList<LinearPath> arcToLinear(CircularPath arcPath, int n)
	{
		ArrayList<LinearPath> linearPath = new ArrayList<LinearPath>();

//		System.out.println("Circular path " + arcPath);
//		System.out.println("center " + arcPath.getCenter());
//		System.out.println("initialPoint " + arcPath.getInitialPoint());
//		System.out.println("finalPoint " + arcPath.getFinalPoint());
//		System.out.println("angulo " + arcPath.getAngulo());

		
		LimitedArc arc = new LimitedArc(arcPath.getCenter(), arcPath.getInitialPoint(), arcPath.getAngulo(), LimitedArc.CCW);
		
		double varAngle = arc.getDeltaAngle()/n;
		double initialAngle = angle(unitVector(arc.getCenter(),arc.getInitialPoint()));
		for(int i = 0; i < n; i++)
		{
			double x1 = arc.getCenter().getX()+arc.getRadius()*Math.cos(initialAngle+i*varAngle);
			double y1 = arc.getCenter().getY()+arc.getRadius()*Math.sin(initialAngle+i*varAngle);
			
			double x2 = arc.getCenter().getX()+arc.getRadius()*Math.cos(initialAngle+(i+1)*varAngle);
			double y2 = arc.getCenter().getY()+arc.getRadius()*Math.sin(initialAngle+(i+1)*varAngle);
			
			
			Point3d p1 = new Point3d(x1, y1, arc.getCenter().getZ());
			Point3d p2 = new Point3d(x2, y2, arc.getCenter().getZ());
			LinearPath linear = new LinearPath(p1, p2);
			
			linearPath.add(linear);
		}
		return linearPath;
	}
	
	public static ArrayList<Point3d> linearPathToPoints(ArrayList<LinearPath> linearPath)
	{
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		for (LinearPath l:linearPath)
		{
			points.add(l.getInitialPoint());
			points.add(l.getFinalPoint());
		}
		return points;
	}
	
	public static ArrayList<LimitedElement> elementsToDesbaste (GeneralClosedPocket pocket, double allowance)
	{
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), pocket.getPosicaoZ(), allowance);
		return acabamentoPath(addPocket, allowance);
	}
	
	public static double minimumDistance(ArrayList<LimitedElement> elementsToAssess)
	{
		ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
		elements = elementsToAssess;
		
		double distance;		
		System.out.println("Elements " + elements.size());
		if (elements.size()<=3)
		{
			distance = 0;
			return distance;
		}
		else
		{
			distance = minimumDistance(elements.get(0), elements.get(2));
		}
		
		for (int i = 0; i < elements.size(); i++)
		{
			LimitedElement e;;
			LimitedElement eBefore;
			LimitedElement eAfter;
			double distanceT=distance;
			if(i==0)
			{
				e = elements.get(i);
				eBefore = elements.get(elements.size()-1);
				eAfter = elements.get(i+1);
				distance = minimumDistance(eBefore, eAfter);
			}
			else if(i==elements.size()-1)
			{
				e = elements.get(i);
				eBefore = elements.get(i-1);
				eAfter = elements.get(0);
				distanceT = minimumDistance(eBefore, eAfter);
			}
			else
			{
				e = elements.get(i);
				eBefore = elements.get(i-1);
				eAfter = elements.get(i+1);
				distanceT = minimumDistance(eBefore, eAfter);
			}
			
			if (distanceT<distance)
			{
				distance = distanceT;
				System.out.println("Distance changed " + distanceT + " i = " +i);
			}
		}	
//		System.out.println("Minimum distance " + distance);
		return distance;
	}
	
	public static double minimumDistance(LimitedElement e1, LimitedElement e2)
	{
		double distance=0.0;
		if (e1.isLimitedLine())
		{
			LimitedLine line1 = (LimitedLine)e1;
			if (e2.isLimitedLine())
			{
				LimitedLine line2 = (LimitedLine)e2;
				distance = minimumDistanceLineToLine(line1, line2);
			}
			if (e2.isLimitedArc())
			{
				LimitedArc arc2 = (LimitedArc)e2;
				distance = minimumDistanceLineToArc(line1, arc2);
			}
		}
		else if (e1.isLimitedArc())
		{
			LimitedArc arc1 = (LimitedArc)e1;
			if (e2.isLimitedLine())
			{
				LimitedLine line2 = (LimitedLine)e2;
				distance = minimumDistanceArcToLine(arc1,line2);
			}
			if (e2.isLimitedArc())
			{
				LimitedArc arc2 = (LimitedArc)e2;
				distance = minimumDistanceArcToArc(arc1, arc2);
			}			
		}		
		return roundNumber(distance, 10);
	}
	
	public static double minimumDistance(ArrayList<LimitedElement> formaOriginal, LimitedElement element)
	{
		double minimumDistance = minimumDistance(formaOriginal.get(0),element);
		
		for(int i = 0;i < formaOriginal.size(); i++)
		{
			LimitedElement temp = formaOriginal.get(i);
			double auxiliar = minimumDistance(temp,element);
			if(minimumDistance > auxiliar)
			{
				minimumDistance = auxiliar;
			}
//			System.out.println("Minumum Distance: " + minimumDistance + " \t--> " + temp.getClass());
		}
		return roundNumber(minimumDistance,10);
	}
	
public static ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallelPath(GeneralClosedPocket pocket, double distance)
		{
			ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = new ArrayList<ArrayList<ArrayList<LimitedElement>>>();
			
//			ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath1(elements, distance);
			ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath2(pocket, distance);
			int aux = 1;
//			parallelPath != null
			while (parallelPath != null)
			{
				multipleParallel.add(parallelPath);
				parallelPath = parallelPath2(pocket, aux*distance);
				aux++;
			}		
	//		System.out.println("mutilplePath: " + multipleParallel.size());
	//		showElements(multipleParallel.get(0).get(0));
			return multipleParallel;
		}


	//Voltei para Array linear (ta ficando complicado de fazer recursivo, toda saída teria que ser transformada em um GeneralClosedPocket)
	public static ArrayList<LimitedElement> parallelPath1 (ArrayList<LimitedElement> elements, double distance, boolean inside)
	{
//		boolean inside = true;
//		ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>();
//		ArrayList<LimitedElement> laco = new ArrayList<LimitedElement>();
//		for (int i = 0; i < elements.size(); i++)
//		{
//			ArrayList<LimitedElement> a0 = elements.get(i);
			ArrayList<LimitedElement> lacoTmp = new ArrayList<LimitedElement>();
//			System.out.println("Elementos do laco " + i + " da Forma Atual: " + a0.size());
			for(int j = 0;j < elements.size();j++)
			{
				if(elements.get(j).isLimitedLine())
				{
					LimitedLine lineTmp = (LimitedLine)elements.get(j);
					LimitedLine newLine = absoluteParallel(lineTmp, distance,inside);
					if(newLine != null)
					{
						lacoTmp.add(newLine);
					}
					//System.err.println("linha " + i);
				} 
				else if(elements.get(j).isLimitedArc())
				{
					LimitedArc arcTmp = (LimitedArc)elements.get(j);
					LimitedArc newArc;
					if (arcTmp.getRadius() == 0)
					{
						System.out.println(lacoTmp);
						Point3d center = arcTmp.getInitialPoint();
						Point3d pI = lacoTmp.get(j-1).getFinalPoint();
						newArc = new LimitedArc(center,pI,Math.PI/2);
						
					}
					else
					{
						newArc = parallelArc(arcTmp, distance,inside);
					}
					if(newArc != null)
					{
						lacoTmp.add(newArc);
					}
//				System.out.println("Center: " + newArc.getCenter());
					//System.err.println("arco " + i);
				}
			}
//			laco.add(lacoTmp);
//		}
		
//		saida = validarPath(laco, elements, distance);
		
//		saida.add(lacoTmp);
		
		return lacoTmp;
	}
	public static ArrayList<ArrayList<LimitedElement>> parallelPath2 (GeneralClosedPocket pocket, double distance)
	{
		boolean inside = true;
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), pocket.Z, pocket.getRadius());

		ArrayList<ArrayList<LimitedElement>> saida = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> parallelTemp1 = new ArrayList<LimitedElement>();      		//Paralela dos elementos da protuberancia
		ArrayList<LimitedElement> parallelTemp2 = new ArrayList<LimitedElement>();              //Paralela dos elementos da cavidade
		ArrayList<LimitedElement> totalParallel = new ArrayList<LimitedElement>();              //Array com todas as paralelas
		ArrayList<LimitedElement> formaOriginal = new ArrayList<LimitedElement>();              //Array da forma original (cavidade+protuberancia)
		ArrayList<LimitedElement> elementsCavidade = addPocketVertex.getElements();             //Cavidade
		ArrayList<Boss> bossArray = pocket.getItsBoss();                                        //Protuberancia
		ArrayList<LimitedElement> elementosProtuberancia = new ArrayList<LimitedElement>();
		for(Boss bossTmp: bossArray)
		{
			if(bossTmp.getClass() == CircularBoss.class)
			{
				CircularBoss tmp = (CircularBoss)bossTmp;
				System.out.println(tmp.getCenter().x + (tmp.getDiametro1()/2));
				LimitedArc arc = new LimitedArc(tmp.getCenter(), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, tmp.Z), 2 * Math.PI);
				System.out.println("Protuberancia Arco: " + arc.getInitialPoint());
				elementosProtuberancia.add(arc);
			}
			else if (bossTmp.getClass() == RectangularBoss.class)
			{
				RectangularBoss tmp = (RectangularBoss)bossTmp;
				//Tamanho em x
				double l = tmp.getL1();
				//Tamanho em y
				double c = tmp.getL2();
				Point3d position = new Point3d(tmp.X,tmp.Y,tmp.Z);
//				Point3d arcCenter = pointPlusEscalar(position, "+x+y", tmp.getRadius());
//				Point3d arcI = pointPlusEscalar(position, "y", tmp.getRadius());
//				for(int i = 0;i < 4;i++)
//				{
//					LimitedArc a = new LimitedArc(arcCenter,arcI,Math.PI/2);
//					LimitedLine lTmp = new LimitedLine(a.getFinalPoint(),l - 2*(tmp.getRadius()),0);
//					if(i == )
//					arcCenter = 
//				}
				LimitedLine l1 = new LimitedLine(pointPlusEscalar(position, "x", tmp.getRadius()),pointPlusEscalar(position,"x",(l-2*tmp.getRadius())));
				LimitedArc a1 = new LimitedArc(pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()),l1.getFinalPoint(),Math.PI/2);
				System.out.println(a1.getFinalPoint());
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(),pointPlusEscalar(a1.getFinalPoint(), "y", (c-2*tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(pointPlusEscalar(l2.getFinalPoint(), "x", -tmp.getRadius()),l2.getFinalPoint(),Math.PI/2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),pointPlusEscalar(a2.getFinalPoint(), "x", -(l - 2*tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()),l3.getFinalPoint(),Math.PI/2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(),pointPlusEscalar(a3.getFinalPoint(),"y",-(c - 2*tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(pointPlusEscalar(l4.getFinalPoint(), "x", tmp.getRadius()),l4.getFinalPoint(),Math.PI/2);
				
//				ArrayList<Point2D> vertex = new ArrayList<Point2D>();
//				Point2D p1 = new Point2D.Double(position.x,position.y);
//				Point2D p2 = new Point2D.Double(p1.getX() + l,p1.getY());
//				Point2D p3 = new Point2D.Double(p2.getX(),p2.getY() + c);
//				Point2D p4 = new Point2D.Double(p3.getX() - l,p3.getY());
//				vertex.add(p1);
//				vertex.add(p2);
//				vertex.add(p3);
//				vertex.add(p4);
//				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(vertex, tmp.Z, tmp.getRadius());
//				ArrayList<LimitedElement> elementosProtuberanciaRect = addBossVertex.getElements();
//				showElements(elementosProtuberanciaRect);
//				if(tmp.getRadius() == 0)
//				{
//					LimitedElement elementTemp1 = elementosProtuberanciaRect.get(0);					
//					elementosProtuberanciaRect.add(elementTemp1);
//					elementosProtuberanciaRect.remove(0);
//				}
//				showElements(elementosProtuberanciaRect);
//				for(int i = 0;i < elementosProtuberanciaRect.size();i++)
//				{
//					elementosProtuberancia.add(elementosProtuberanciaRect.get(i));
//				}
				elementosProtuberancia.add(l1);
				elementosProtuberancia.add(a1);
				elementosProtuberancia.add(l2);
				elementosProtuberancia.add(a2);
				elementosProtuberancia.add(l3);
				elementosProtuberancia.add(a3);
				elementosProtuberancia.add(l4);
				elementosProtuberancia.add(a4);
				
				
			}else if (bossTmp.getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss tmp = (GeneralProfileBoss)bossTmp;
				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(tmp.getVertexPoints(), tmp.Z, tmp.getRadius());
				ArrayList<LimitedElement> elementosProtuberanciaGeral = addBossVertex.getElements();
				for(int i = 0;i < elementosProtuberanciaGeral.size();i++)
				{
					elementosProtuberancia.add(elementosProtuberanciaGeral.get(i));
				}
				
			}
		}
//		if(elementosProtuberancia.size() != 0)
//		{
//			laco.add(elementosProtuberancia);
//		}
//		
//		if(elementsCavidade.size() != 0)
//		{
//			laco.add(elementsCavidade);
//		}
		for(LimitedElement tmp:elementosProtuberancia)
		{
			formaOriginal.add(tmp);
		}
		for(LimitedElement tmp:elementsCavidade)
		{
			formaOriginal.add(tmp);
		}
		parallelTemp1 = parallelPath1(elementosProtuberancia, distance,!inside);
		showElements(parallelTemp1);
		parallelTemp2 = parallelPath1(elementsCavidade, distance, inside);
		
		for(LimitedElement tmp:parallelTemp1)
		{
			totalParallel.add(tmp);
		}
		for(LimitedElement tmp:parallelTemp2)
		{
			totalParallel.add(tmp);
		}
		
//		saida = parallelPath1(laco, distance, !inside);
		
		saida = validarPath(totalParallel, formaOriginal, distance);
		
//		saida.add(lacoTmp);
		
		return saida;
	}
	public static double calcDeltaAngle(Point3d Pi, Point3d Pf, Point3d center, double arcAngle)
	{
		double anglePi = Math.atan2(Pi.y - center.y, Pi.x - center.x);
		double anglePf = Math.atan2(Pf.y - center.y, Pf.x - center.x);
		double r = center.distance(Pi);
		double distance = Pi.distance(Pf);
		double alpha = 2*(Math.asin(distance/(2*r)));
		if(arcAngle == 2*Math.PI)
		{
			if(anglePi > anglePf)
			{
				alpha = 2*Math.PI - alpha;
			}
		}
		if(arcAngle < 0) 
		{
			alpha = -alpha;
		}
		return alpha;
	}
	
	public static ArrayList<LimitedArc> quebraArco(LimitedArc arc, ArrayList<Point3d> intersecoes)
	{
		double intSize = intersecoes.size();
		Point3d arcI = arc.getInitialPoint();
		Point3d arcF = arc.getFinalPoint();
		Point3d arcCenter = arc.getCenter();
		double oldDeltaAngle = arc.getDeltaAngle();
		ArrayList<LimitedArc> arcTemp = new ArrayList<LimitedArc>(); 
		Point3d intTemp;
		if(arc.getDeltaAngle() == 2*Math.PI && intersecoes.size() == 1)
		{
			arcTemp.add(arc);
		}
		else
		{
			for(int h = 0;h < intSize;h++)
			{
				intTemp = intersecoes.get(h);
				if(belongsArc(arc,intTemp))
				{
					if(arcTemp.size() == 0)
					{
						if(arc.getDeltaAngle() == 2*Math.PI)
						{
							Point3d intTempNext = intersecoes.get(h+1);
							LimitedArc segTemp = new LimitedArc(arcCenter, intTemp, calcDeltaAngle(intTemp,intTempNext,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);
							segTemp = new LimitedArc(arcCenter, intTempNext, calcDeltaAngle(intTempNext,intTemp,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);

						}
						else
						{
							LimitedArc segTemp = new LimitedArc(arcCenter, arcI, calcDeltaAngle(arcI,intTemp,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);
							segTemp = new LimitedArc(arcCenter, intTemp,calcDeltaAngle(intTemp,arcF,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);
						}
						showArcs(arcTemp);
					}
					else
					{
						int arcTempSize = arcTemp.size();
						for(int s = 0;s < arcTempSize;s++)
						{
							Point3d arcTempI = arcTemp.get(s).getInitialPoint();
							Point3d arcTempF = arcTemp.get(s).getFinalPoint();
							LimitedArc aTmp = arcTemp.get(s);
							//Problema: Esta fazendo o belongs com o mesmo aTmp, varias vezes

							if((roundNumber(calcDeltaAngle(arcTempI,intTemp,arcCenter,oldDeltaAngle),10) != 0) && (roundNumber(calcDeltaAngle(intTemp,arcTempF,arcCenter,oldDeltaAngle),10) != 0))							
							{
								
								if(belongsArc(aTmp,intTemp))
								{
									LimitedArc segTemp = new LimitedArc(arcCenter, arcTempI, calcDeltaAngle(arcTempI,intTemp,arcCenter,oldDeltaAngle));
									arcTemp.add(segTemp);
									segTemp = new LimitedArc(arcCenter, intTemp, calcDeltaAngle(intTemp,arcTempF,arcCenter,oldDeltaAngle));
									arcTemp.add(segTemp);
									arcTemp.remove(aTmp);
									break;
								}
							}
						}
						showArcs(arcTemp);
					}
				}
			}
		}
		return arcTemp;
	}
	public static ArrayList<LimitedLine> quebraLinha(LimitedLine line, ArrayList<Point3d> intersecoes)
	{
		double intSize = intersecoes.size(); 
		Point3d lineI = line.getInitialPoint();
		Point3d lineF = line.getFinalPoint();
		ArrayList<LimitedLine> lineTemp = new ArrayList<LimitedLine>(); 
		Point3d intTemp;
		for(int h = 0;h < intSize;h++)
		{
			intTemp = intersecoes.get(h);
			if(lineTemp.size() == 0)
			{
				LimitedLine segTemp = new LimitedLine(lineI, intTemp);
				lineTemp.add(segTemp);
				segTemp = new LimitedLine(intTemp, lineF);
				lineTemp.add(segTemp);
			}
			else
			{
				int lineTempSize = lineTemp.size();
				for(int s = 0;s < lineTempSize;s++)
				{
					Point3d lineTempI = lineTemp.get(s).getInitialPoint();
					Point3d lineTempF = lineTemp.get(s).getFinalPoint();
					LimitedLine lTmp = lineTemp.get(s);
					if(belongs(lTmp,intTemp))
					{
						LimitedLine segTemp = new LimitedLine(lineTempI, intTemp);
						lineTemp.add(segTemp);
						segTemp = new LimitedLine(intTemp, lineTempF);
						lineTemp.add(segTemp);
						lineTemp.remove(lTmp);
						break;
					}
				}
			}
		}
		return lineTemp;
	}
	
	//O validar é um array de arrays, pois cria lacos
	public static ArrayList<ArrayList<LimitedElement>> validarPath(ArrayList<LimitedElement> elements, ArrayList<LimitedElement> formaOriginal, double distance)
	{
//		showElements(elements);
		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
//		System.out.println("elements: " + elements.size());
//		for(int i = 0;i < elements.size();i++)
//		{
			ArrayList<LimitedElement> elementsIntermediario = validar1Path(elements);
			ArrayList<LimitedElement> elementsIntermediario2 = validar2Path(elementsIntermediario,formaOriginal,distance);
			System.out.println("elementsInter2: " + elementsIntermediario2.size());
			ArrayList<ArrayList<LimitedElement>> elementsIntermediario3 = validar3Path(elementsIntermediario2);
			if(elementsIntermediario3 != null)
			{
				System.out.println("elementsInter3: " + elementsIntermediario3.size());
//				showElements(elementsIntermediario2);
				for(int j = 0;j < elementsIntermediario3.size();j++)
				{
					elementsValidated.add(elementsIntermediario3.get(j));					
				}
			}
//		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
//		elementsValidated.add(elementsIntermediario2);
//		}
		if (elementsValidated.size() != 0)
		{
			return elementsValidated;
		}
		else
		{
			return null;
		}
	}
//	public static ArrayList<LimitedElement> validar1Path(ArrayList<LimitedElement> elements)
//	{
//		/*
//		 * 	Valida��o 1: Quebra dos Elementos na intersecao
//		 */
//		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
//		for (int i=0; i < elements.size(); i++)
//		{
//			ArrayList<Point3d> intersection = null;
//			boolean thereIsIntersection = false;
//			LimitedElement ei = elements.get(i);
////			System.out.println("PIei: " + ei.getInitialPoint());
//			int numeroDeIntersecao = 0;
//			for (int j = 0; j < elements.size(); j++)
//			{
//				LimitedElement ej = elements.get(j);
////				System.out.println("PIej: " + ej.getInitialPoint());
//				intersection = null;
//				if(!ei.equals(ej))
//				{
//					intersection = intersectionElements(ei, ej);
//					//essa condicao so funciona se o metodo que calcula as intersecoes retornar um array nulo quando nao ha intersecao (ao inves de um array vazio)
//					if (intersection != null)
//					{
//						int intSize = intersection.size();
//						boolean intSize2 = false;
//						boolean auxIntSize2 = false;
//						if(intSize == 2)
//						{
//							intSize2 = true;
//						}
//	//					System.out.println("intersection: " + intersection);
//						for(int k = 0;k < intSize;k++)
//						{
//							if(!(alreadyUsed(intersection.get(k),intersecoes)))
//							{
//								intersecoes.add(intersection.get(k));
//							}
//						}
//						thereIsIntersection = true;
//						if(numeroDeIntersecao == 0)
//						{
////							System.out.println("intersecoes: " + intersection);
////							System.out.println("PIei: " + ei.getInitialPoint());
////							System.out.println("PIej: " + ej.getInitialPoint());
//							//adiciona os pontos de intersecao ao vetor de intersecoes
//							if (ei.isLimitedLine())
//							{
//								
//								LimitedLine linei = (LimitedLine)ei;
//								ArrayList<LimitedLine> lineTemp = quebraLinha(linei,intersection); 
//								for(int k = 0;k < lineTemp.size();k++)
//								{
//									elementsIntermediario.add(lineTemp.get(k));
//								}
//							}
//							else if(ei.isLimitedArc())
//							{
//								LimitedArc arci = (LimitedArc)ei;
//								ArrayList<LimitedArc> arcTemp = quebraArco(arci,intersection); 
//								for(int k = 0;k < arcTemp.size();k++)
//								{
//									elementsIntermediario.add(arcTemp.get(k));
//								}
//							}
//							numeroDeIntersecao++;
//							auxIntSize2 = intSize2;
//						}
//						else
//						{
////							System.out.println("PIei: " + ei.getInitialPoint());
//							int indice2 = elementsIntermediario.size() - 2;
//							int indice3 = elementsIntermediario.size() - 1;
//							if((elementsIntermediario.get(indice3).isLimitedLine()))
//							{
//								LimitedLine aux2 = (LimitedLine)elementsIntermediario.get(indice2);
//								LimitedLine aux3 = (LimitedLine)elementsIntermediario.get(indice3);
//								ArrayList<LimitedLine> auxTemp = new ArrayList<LimitedLine>();
//								ArrayList<LimitedLine> lineTemp = null;
//								auxTemp.add(aux2);
//								auxTemp.add(aux3);
//								if(auxIntSize2)
//								{
//									int indice1 = elementsIntermediario.size() - 3;
//									LimitedLine aux1 = (LimitedLine)elementsIntermediario.get(indice1);
//									auxTemp.add(aux1);
//								}
//								
//								for(int k = 0;k < auxTemp.size();k++)
//								{
//									LimitedLine aTmp = auxTemp.get(k);
//									boolean belongs1 = belongs(aTmp,intersection.get(0));
//									if(auxIntSize2)
//									{
//										boolean belongs2 = belongs(aTmp,intersection.get(1));
//										if(belongs1 && belongs2)
//										{
//											lineTemp = quebraLinha(aTmp,intersection);
//										}
//										else if(belongs1)
//										{
//											ArrayList<Point3d> intTemp = new ArrayList<Point3d>();
//											intTemp.add(intersection.get(0));
//											lineTemp = quebraLinha(aTmp,intTemp);
//										}
//										else if(belongs2)
//										{
//											ArrayList<Point3d> intTemp = new ArrayList<Point3d>();
//											intTemp.add(intersection.get(1));
//											lineTemp = quebraLinha(aTmp,intTemp);
//										}
//									}
//									else
//									{
//										if(belongs1)
//										{
//											lineTemp = quebraLinha(aTmp,intersection);
//										}
//									}
//								}
//								if(lineTemp != null)
//								{
//									for(int k = 0;k < lineTemp.size();k++)
//									{
//										elementsIntermediario.add(lineTemp.get(k));
//									}
//								}
//							}
//							else if ((elementsIntermediario.get(indice3).isLimitedArc()))
//							{
//								LimitedArc aux2 = (LimitedArc) elementsIntermediario.get(indice2);
//								LimitedArc aux3 = (LimitedArc) elementsIntermediario.get(indice3);
//								ArrayList<LimitedArc> auxTemp = new ArrayList<LimitedArc>();
//								ArrayList<LimitedArc> ArcTemp = null;
//								auxTemp.add(aux2);
//								auxTemp.add(aux3);
//								if(auxIntSize2)
//								{
//									int indice1 = elementsIntermediario.size() - 3;
//									LimitedArc aux1 = (LimitedArc)elementsIntermediario.get(indice1);
//									auxTemp.add(aux1);
//								}
//								
//								for(int k = 0;k < auxTemp.size();k++)
//								{
//									LimitedArc aTmp = auxTemp.get(k);
//									boolean belongs1 = belongsArc(aTmp,intersection.get(0));
//									if(auxIntSize2)
//									{
//										boolean belongs2 = belongsArc(aTmp,intersection.get(1));
//										if(belongs1 && belongs2)
//										{
//											ArcTemp = quebraArco(aTmp,intersection);
//										}
//										else if(belongs1)
//										{
//											ArrayList<Point3d> intTemp = new ArrayList<Point3d>();
//											intTemp.add(intersection.get(0));
//											ArcTemp = quebraArco(aTmp,intTemp);
//										}
//										else if(belongs2)
//										{
//											ArrayList<Point3d> intTemp = new ArrayList<Point3d>();
//											intTemp.add(intersection.get(1));
//											ArcTemp = quebraArco(aTmp,intTemp);
//										}
//									}
//									else
//									{
//										if(belongs1)
//										{
//											ArcTemp = quebraArco(aTmp,intersection);
//										}
//									}
//								}
//								if(ArcTemp != null)
//								{
//									for(int k = 0;k < ArcTemp.size();k++)
//									{
//										elementsIntermediario.add(ArcTemp.get(k));
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			if(thereIsIntersection == false)
//			{
//				elementsIntermediario.add(ei);
//			}
//		}
//		return elementsIntermediario;
//	}
	
	public static ArrayList<LimitedElement> validar1Path(ArrayList<LimitedElement> elements)
	{
		/*
		 * 	Valida��o 1: Quebra dos Elementos na intersecao
		 */
		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
		for (int i=0; i < elements.size(); i++)
		{
			ArrayList<Point3d> intersection = null;
			boolean thereIsIntersection = false;
			boolean firstIntersection = true;
			LimitedElement ei = elements.get(i);
			for (int j = 0; j < elements.size(); j++)
			{
				ArrayList<Point3d> intersectionTemp = null;
				LimitedElement ej = elements.get(j);
//				intersection = null;
				if(!ei.equals(ej))
				{
					intersectionTemp = intersectionElements(ei, ej);
					
					//essa condicao so funciona se o metodo que calcula as intersecoes retornar um array nulo quando nao ha intersecao (ao inves de um array vazio)
					if (intersectionTemp != null)
					{
						thereIsIntersection = true;
						if(firstIntersection)
						{
							intersection = new ArrayList<Point3d>();
							firstIntersection = false;
						}
//						System.out.println(intersection);
						for(int k = 0;k < intersectionTemp.size();k++)
						{
							intersection.add(intersectionTemp.get(k));
						}
					}
				}
			}
			if(intersection != null)
			{
				if (ei.isLimitedLine())
				{
					LimitedLine linei = (LimitedLine)ei;
					ArrayList<LimitedLine> lineTemp = quebraLinha(linei,intersection); 
					for(int k = 0;k < lineTemp.size();k++)
					{
						if(lineTemp.get(k).getInitialPoint() != lineTemp.get(k).getFinalPoint())
						{
							elementsIntermediario.add(lineTemp.get(k));
						}
					}
				}
				else if(ei.isLimitedArc())
				{
					LimitedArc arci = (LimitedArc)ei;
//					System.out.println(arci.getInitialPoint());
//					System.out.println(arci.getCenter());
//					System.out.println(arci.getDeltaAngle());
//					System.out.println(intersection);
					ArrayList<LimitedArc> arcTemp = quebraArco(arci,intersection); 
					System.out.println(arcTemp.size());
					for(int k = 0;k < arcTemp.size();k++)
					{
						if(arcTemp.get(k).getInitialPoint() != arcTemp.get(k).getFinalPoint())
						{
							elementsIntermediario.add(arcTemp.get(k));
						}
					}
				}
			}
			if(thereIsIntersection == false)
			{
				elementsIntermediario.add(ei);
			}
		}
		return elementsIntermediario;
	}
	public static ArrayList<LimitedElement> validar2Path(ArrayList<LimitedElement> elementsIntermediario, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		/*
		 * 	Valida��o 2: Elementos com a minima distancia (em relacao a forma original) menor que a distancia de offset, sao descartados 
		 */
		ArrayList<LimitedElement> elementsIntermediario2 = new ArrayList<LimitedElement>();
//		System.out.println("Elementos intermediarios: " + elementsIntermediario.size());
		for(int i = 0; i< elementsIntermediario.size();i++)
		{
			LimitedElement ei0 = elementsIntermediario.get(i);
			//System.out.println("Menor distancia elemento " + i + ": " +  minimumDistance(formaOriginal, ei0));
			if(minimumDistance(formaOriginal, ei0) >= distance)
			{
				elementsIntermediario2.add(ei0);
			}
		}
//		System.out.println("Elementos intermediarios2: " + elementsIntermediario2.size());
	
		return elementsIntermediario2;
	}
	public static ArrayList<ArrayList<LimitedElement>> validar3Path(ArrayList<LimitedElement> elementsIntermediario2)
	{
		/*
		 * 	Validaï¿½ï¿½o 3: Separacao dos elementos em lacos 
		 */
		if(elementsIntermediario2.size() == 0)
		{
			return null;
		}
		else
		{
			ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
			elementsValidated.add(new ArrayList<LimitedElement>());
			int numeroDeLacos = 0;
			Point3d initialPoint = null;
			boolean alreadyPassed = false;
			LimitedElement ei0 = elementsIntermediario2.get(0);
			LimitedElement ei0new = elementsIntermediario2.get(0);
//			System.out.println("Size:" + elementsIntermediario2.size());
			Iterator iter = elementsIntermediario2.iterator();
			while(iter.hasNext())
			{
				boolean hasNoFinalPoint = true;
//			System.out.println("Size:" + elementsIntermediario2.size());
				ei0 = ei0new;
				Point3d ei0I = ei0.getInitialPoint();
//				System.out.println("ei0I: " + ei0I);
				Point3d ei0F = ei0.getFinalPoint();
				elementsValidated.get(numeroDeLacos).add(ei0);
				for(int j = 0; j < elementsIntermediario2.size(); j++)
				{
//				System.out.println("Aux2: " + aux2);
					LimitedElement ej = elementsIntermediario2.get(j);
					if(!(alreadyPassed))
					{
						initialPoint = ei0I;
						alreadyPassed = true;
//					System.out.println("InitialPoint: " + initialPoint);
					}
					Point3d ejI = ej.getInitialPoint();
//				System.out.println("ejI: " + ejI);
					if(isTheSamePoint(ei0F,ejI))
					{
						ei0new = elementsIntermediario2.get(j);
//						System.out.println("ei0I: " + ei0I);
						elementsIntermediario2.remove(ei0);
						hasNoFinalPoint = false;
						break;
					}
					else if(isTheSamePoint(ei0F,initialPoint))
					{
						alreadyPassed = false;
						elementsIntermediario2.remove(ei0);
						if(iter.hasNext())
						{
							ei0new = elementsIntermediario2.get(0);
							numeroDeLacos++;
							elementsValidated.add(new ArrayList<LimitedElement>());
						}
						break;
					}
					if(hasNoFinalPoint)
					{
						elementsIntermediario2.remove(ei0);
						if(iter.hasNext())
						{
							ei0new = elementsIntermediario2.get(0);
						}

					}
				}
//			System.out.println("Numero de lacos: " + numeroDeLacos);
			}
			System.out.println("laco1: " + elementsValidated.get(0).size());
			if(elementsValidated.size() == 2)
			{
				System.out.println("laco2: " + elementsValidated.get(1).size());
			}
			if(elementsValidated.size() == 3)
			{
				System.out.println("laco2: " + elementsValidated.get(1).size());
				System.out.println("laco3: " + elementsValidated.get(2).size());
			}
			
			return elementsValidated;
		}
	}
	
	public static boolean alreadyUsed(Point3d point, ArrayList<Point3d> array)
	{
		boolean answer = false;
		if(array.size() > 0)
		{
			for (int i = 0; i < array.size();i++)
			{
				if(isTheSamePoint(point, array.get(i)))
				{
					answer = true;
				}
			}
		}
		return answer;
	}
	public static ArrayList<Point3d> intersectionElements(ArrayList<LimitedElement> elements)
	{
		ArrayList<Point3d> intersecoes = new ArrayList<Point3d>();
		for (int i=0; i < elements.size(); i++)
		{
			LimitedElement ei = elements.get(i);
			for (int j = i + 1; j < (elements.size()); j++)
			{
				LimitedElement ej = elements.get(j);
				ArrayList<Point3d> intersection = intersectionElements(ei, ej);
				if(intersection!=null)
				{
					//adiciona os pontos de intersecao ao vetor de intersecoes
					for(int k = 0; k < intersection.size();k++)
					{
						intersecoes.add(intersection.get(k));
						
					}
//					System.out.println("Here is an intersection point " + intersection);
				}
			}
		}
		return intersecoes;
	}
	
	public static ArrayList<Point3d> intersectionElements(LimitedElement ei, LimitedElement ej)
	{
		//Se o elemento i for um arco
		
		if (ei.isLimitedArc())
		{
			LimitedArc arci = (LimitedArc)ei;
			//Se o elemento j for um arco (arco, arco)
			if(ej.isLimitedArc())
			{
				LimitedArc arcj = (LimitedArc)ej;
				//intersection = intersectionPoint(arci,arcj);
				
				//Alternativa para nï¿½o identificar pontos de interseï¿½ï¿½o nas extremidades dos Limited Elements
				if(isTheSamePoint(arci.getInitialPoint(),arcj.getInitialPoint()) || 
				   isTheSamePoint(arci.getFinalPoint(),arcj.getFinalPoint()) || 
				   isTheSamePoint(arci.getInitialPoint(),arcj.getFinalPoint())|| 
				   isTheSamePoint(arci.getFinalPoint(),arcj.getInitialPoint()))
				{
					return null;
				}
				
				return intersectionPoint(arci, arcj);
			}
			//Se o elemento j for uma linha (arco,linha)
			else if ( ej.isLimitedLine())
			{
				LimitedLine linej = (LimitedLine)ej;
				if(isTheSamePoint(arci.getInitialPoint(),linej.getInitialPoint()) || 
				   isTheSamePoint(arci.getFinalPoint(),linej.getFinalPoint()) || 
				   isTheSamePoint(arci.getInitialPoint(),linej.getFinalPoint())|| 
				   isTheSamePoint(arci.getFinalPoint(),linej.getInitialPoint()))
					{
						return null;
					}
				//System.out.println("NuloArcLine:" + intersectionPoint(arci, linej));	
				return intersectionPoint(arci, linej);
			}
			
		}
		//Se o elemento i for uma linha
		else if(ei.isLimitedLine())
		{
			LimitedLine linei = (LimitedLine)ei;
			//Se o elemento j for um arco (linha, arco)
			if(ej.isLimitedArc())
			{
				LimitedArc arcj = (LimitedArc)ej;
				if(isTheSamePoint(linei.getInitialPoint(),arcj.getInitialPoint()) || 
				   isTheSamePoint(linei.getFinalPoint(),arcj.getFinalPoint()) || 
				   isTheSamePoint(linei.getInitialPoint(),arcj.getFinalPoint())|| 
				   isTheSamePoint(linei.getFinalPoint(),arcj.getInitialPoint()))
					{
						return null;
					}
				//o metodo intersectionPoint nï¿½o estï¿½ definido para (line, arc) mas sim pra (arc, line)	
				//System.out.println("NuloLineArc:" + intersectionPoint(arcj, linei));
				return intersectionPoint(arcj, linei);
			}
			//Se o elemento j for uma linha (linha, linha)
			else if ( ej.isLimitedLine())
			{
				LimitedLine linej = (LimitedLine)ej;
				ArrayList<Point3d> intersection = new ArrayList<Point3d>();
				if(intersectionPoint(linei, linej) == null)
				{
					return null;
				}
				else
				{
					intersection.add(intersectionPoint(linei, linej));
				}
				
				if(isTheSamePoint(linei.getInitialPoint(),linej.getInitialPoint()) || 
				   isTheSamePoint(linei.getFinalPoint(),linej.getFinalPoint()) || 
				   isTheSamePoint(linei.getInitialPoint(),linej.getFinalPoint())|| 
				   isTheSamePoint(linei.getFinalPoint(),linej.getInitialPoint()))
				{
					return null;
				}
				//System.out.println("NuloLineLine:" + intersectionPoint(linei, linej));
				return intersection;
			}
			
		}
		else
		{
			return null;
		}
		return null;
	}
	
	//Verifica a igualdade de pontos entre os Limited Elements
	public static boolean isTheSamePoint(Point3d p1, Point3d p2)
	{
		if(roundNumber(p1.x, 10) == roundNumber(p2.x,10) 
				&& roundNumber(p1.y, 10) == roundNumber(p2.y, 10) 
				&& roundNumber(p1.z, 10) == roundNumber(p2.z, 10)
				)
		{
			return true;
		}
		else
			return false;
	}
	public static Point3d middlePoint(LimitedArc arc)
	{
		double initialAngle = angle(minus(arc.getInitialPoint(), arc.getCenter()));
		double x = arc.getRadius()*Math.cos(arc.getDeltaAngle()/2 + initialAngle);
		double y = arc.getRadius()*Math.sin(arc.getDeltaAngle()/2 + initialAngle);
		return new Point3d(arc.getCenter().getX()+x, arc.getCenter().getY() + y, arc.getCenter().getZ());
	}

	public static double angle(LimitedLine lineBefore, LimitedLine lineCurrent)
	{
		Point3d beginPoint = minus(lineCurrent.getFinalPoint(), lineCurrent.getInitialPoint());
		Point3d endPoint = minus(lineBefore.getInitialPoint(), lineBefore.getFinalPoint());
		
		double initialAngle = angle(beginPoint);
		double finalAngle = angle(endPoint);
		
		double angleCurrentBefore = finalAngle-initialAngle;
		return angleCurrentBefore;
	}
	
	public static LimitedLine absoluteParallel(LimitedLine line, double distance, boolean inside)
	{
		
		double angleLine = angle(minus(line.getFinalPoint(), line.getInitialPoint()));
		double newDistanceAngle = angleLine+Math.PI/2;
		double x = Math.cos(newDistanceAngle);
		double y = Math.sin(newDistanceAngle);
		Point3d unitDistance = new Point3d(x,y,line.getInitialPoint().getZ());
		Point3d distanceVector;
		if(inside)
		{
			distanceVector = multiply(distance, unitDistance);		
		}
		else
		{
			distanceVector = multiply(-distance, unitDistance);
		}

		Point3d newInitialPoint = plus(line.getInitialPoint(),distanceVector);
		Point3d newFinalPoint = plus(line.getFinalPoint(),distanceVector);
//		System.out.println("Distance Vector " + distanceVector);
//		System.out.println("New Initial Point " + newInitialPoint);
//		System.out.println("New Final Point " + newFinalPoint);
		if((roundNumber(newInitialPoint.x,10) == roundNumber(newFinalPoint.x,10)) && (roundNumber(newInitialPoint.y,10) == roundNumber(newFinalPoint.y,10)))
		{
			return null;
		}
		else
		{
			LimitedLine lineParallel = new LimitedLine(newInitialPoint, newFinalPoint);
			return lineParallel;
		}
		//System.out.println("Parallel from " + lineParallel.getInitialPoint() + " to " + lineParallel.getFinalPoint());
		
	}
	
	public static boolean belongs(LimitedLine line, Point3d p)
	{
		Point3d p1 = unitVector(line.getInitialPoint(),p);
		Point3d p2 = unitVector(line.getInitialPoint(),line.getFinalPoint());
		
		double normaLinha = norm(minus(line.getFinalPoint(),line.getInitialPoint()));
		double normaNoPonto = norm(minus(p,line.getInitialPoint()));
		
		if(isTheSamePoint(p1, p2))
		{
			if(normaLinha >= normaNoPonto)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
 
//		System.out.println("Norma p1: " + norm(p1));
//		System.out.println("Norma p2: " + norm(p2));
//		if (norm(minus(p1, p2))==0 && norm(p1) <= norm(p2))
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
	}
	
	public static boolean belongsArc(LimitedArc arc, Point3d p)
	{

//		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
		LimitedArc arcTmp = new LimitedArc(arc.getCenter(),arc.getInitialPoint(), arc.getDeltaAngle());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp = arc.getInitialPoint();
			Point3d temp1 = arc.getFinalPoint();
			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
		}
		
		double anguloInicial = Math.atan2(arcTmp.getInitialPoint().y - arcTmp.getCenter().y, arcTmp.getInitialPoint().x - arcTmp.getCenter().x);
		double anguloFinal = Math.atan2(arcTmp.getFinalPoint().y - arcTmp.getCenter().y, arcTmp.getFinalPoint().x - arcTmp.getCenter().x);
		double anguloP = Math.atan2(p.y - arcTmp.getCenter().y, p.x - arcTmp.getCenter().x);
		
		if(anguloInicial < 0)
		{
			anguloInicial = 2 * Math.PI + anguloInicial;
		}
		if(anguloFinal <= 0)
		{
			anguloFinal = 2 * Math.PI + anguloFinal;
		}
		if(roundNumber(anguloP,10) == 0 && roundNumber(anguloInicial,10) == 0)
		{
			return true;
		}
		if(anguloP <= 0)
		{
			anguloP = 2 * Math.PI + anguloP;
		}
		
		//Normalizacao dos angulos inicial e final
		if(anguloInicial > anguloFinal)
		{
			anguloFinal = 2 * Math.PI + anguloFinal;
		}
		
//		System.out.println("AnguloInicial: " + anguloInicial);
//		System.out.println("AnguloFinal: " + anguloFinal);
//		System.out.println("AnguloP: " + anguloP);
		double distance = arc.getCenter().distance(p);
//		System.out.println("Distance: " + distance);
		if(roundNumber(distance,10) == roundNumber(arc.getRadius(),10))
		{
			if((anguloFinal >= anguloP && anguloInicial <= anguloP) || (anguloFinal >= (anguloP + 2*Math.PI) && anguloInicial <= (anguloP + 2*Math.PI)))
			{
				return true;
			}
		}
		return false;			
	}
	public static double norm(LimitedLine line)
	{		
		return distance(line.getInitialPoint(),line.getFinalPoint()); 
	}
	
	public static Point3d intersect(LimitedLine line1, LimitedLine line2)
	{				
		double a1 = (line1.getFinalPoint().getY()-line1.getInitialPoint().getY())/(line1.getFinalPoint().getX()-line1.getInitialPoint().getX());
		double b1 = line1.getFinalPoint().getY()-a1*line1.getFinalPoint().getX();
				
		double a2 = (line2.getFinalPoint().getY()-line2.getInitialPoint().getY())/(line2.getFinalPoint().getX()-line2.getInitialPoint().getX());
		double b2 = line2.getFinalPoint().getY()-a2*line2.getFinalPoint().getX();
		
		double x = (b2-b1)/(a1-a2);
		double y = a1*x+b1;			
		
		if(line1.getInitialPoint().getX()==line1.getFinalPoint().getX())
		{
			if(line2.getInitialPoint().getX()!=line2.getFinalPoint().getX())
			{
				double xSpecial = line1.getInitialPoint().getX();
				double ySpecial = a2*xSpecial + b2;
				return new Point3d(xSpecial,ySpecial,line1.getInitialPoint().getZ());
			}
		}
		if(line2.getInitialPoint().getX()==line2.getFinalPoint().getX())
		{
			if(line1.getInitialPoint().getX()!=line1.getFinalPoint().getX())
			{
				double xSpecial = line2.getInitialPoint().getX();
				double ySpecial = a1*xSpecial + b1;
				return new Point3d(xSpecial,ySpecial,line1.getInitialPoint().getZ());
			}
		}		
		return new Point3d(x,y,line1.getInitialPoint().getZ());
	}
	
//	public static ArrayList<LimitedElement> parallelPath (ArrayList<LimitedElement> elements, double distance)	
//	{		
//		ArrayList<LimitedElement> parallelElements = new ArrayList<LimitedElement>();
//		
//		for (int i = 0; i < elements.size(); i++)
//		{			
//			LimitedElement e;;
//			LimitedElement eBefore;
//			LimitedElement eAfter;
//			
//			if(i==0)
//			{
//				e = elements.get(i);
//				eBefore = elements.get(elements.size()-1);
//				eAfter = elements.get(i+1);
//			}
//			else if(i==elements.size()-1)
//			{
//				e = elements.get(i);
//				eBefore = elements.get(i-1);
//				eAfter = elements.get(0);				
//			}
//			else
//			{
//				e = elements.get(i);
//				eBefore = elements.get(i-1);
//				eAfter = elements.get(i+1);				
//			}
//			
//			if (e.isLimitedArc() && eBefore.isLimitedLine() && eAfter.isLimitedLine())
//			{
//				LimitedArc arc = (LimitedArc)e;
//				parallelElements.add(parallelArc(((LimitedArc)e), distance));
//			}
//
//			if (e.isLimitedLine() && eBefore.isLimitedLine() && eAfter.isLimitedLine())
//			{
//				LimitedLine lineCurrent = (LimitedLine)e;
//				LimitedLine lineBefore = (LimitedLine)eBefore;
//				LimitedLine lineAfter = (LimitedLine)eAfter;
//												
//				Point3d initialPoint = intersect(lineBefore, lineCurrent, distance); 
//				Point3d finalPoint = intersect(lineCurrent, lineAfter, distance); 
//
//				LimitedLine lineParallel = new LimitedLine(initialPoint, finalPoint);
//				parallelElements.add(lineParallel);
//			}
//			
//			
//			if (e.isLimitedLine() && eBefore.isLimitedLine() && eAfter.isLimitedArc())
//			{
//				LimitedLine lineCurrent = (LimitedLine)e;
//				LimitedLine lineBefore = (LimitedLine)eBefore;
//				LimitedArc arcAfter = (LimitedArc)eAfter;
//				
//				LimitedArc parallelArcAfter = parallelArc(arcAfter, distance);
//				Point3d initialPoint = intersect(lineBefore, lineCurrent, distance);
//				Point3d finalPoint = new Point3d();
//				if (parallelArcAfter.getRadius() != 0)
//					finalPoint = parallelArcAfter.getInitialPoint();
//				else
//					finalPoint = parallelArcAfter.getCenter();
//				
//				LimitedLine lineParallel = new LimitedLine(initialPoint, finalPoint);
//				parallelElements.add(lineParallel);
//			}
//
//			if (e.isLimitedLine() && eBefore.isLimitedArc() && eAfter.isLimitedLine())
//			{
//				LimitedLine lineCurrent = (LimitedLine)e;
//				LimitedArc arcBefore = (LimitedArc)eBefore;
//				LimitedLine lineAfter = (LimitedLine)eAfter;
//				
//				LimitedArc parallelArcBefore = parallelArc(arcBefore, distance);
//				
//				Point3d initialPoint = new Point3d();
//				if (parallelArcBefore.getRadius() != 0)
//					initialPoint = parallelArcBefore.getFinalPoint();
//				else
//					initialPoint = parallelArcBefore.getCenter();
//
//				Point3d finalPoint = intersect(lineCurrent, lineAfter, distance);
//				
//				LimitedLine lineParallel = new LimitedLine(initialPoint, finalPoint);
//				parallelElements.add(lineParallel);
//			}	
//			
//			if (e.isLimitedLine() && eBefore.isLimitedArc() && eAfter.isLimitedArc())
//			{
//				LimitedArc arcBefore = (LimitedArc)eBefore;
//				LimitedArc arcAfter = (LimitedArc)eAfter;
//				
//				LimitedArc parallelArcBefore = parallelArc(arcBefore, distance);
//				LimitedArc parallelArcAfter = parallelArc(arcAfter, distance);
//				
//				Point3d initialPoint = new Point3d();
//				if (parallelArcBefore.getRadius() != 0)
//					initialPoint = parallelArcBefore.getFinalPoint();
//				else
//					initialPoint = parallelArcBefore.getCenter();
//				
//				Point3d finalPoint = new Point3d();
//				if (parallelArcAfter.getRadius() != 0)
//					finalPoint = parallelArcAfter.getInitialPoint();
//				else
//					finalPoint = parallelArcAfter.getCenter();
//				
//				LimitedLine lineParallel = new LimitedLine(initialPoint, finalPoint);
//				parallelElements.add(lineParallel);
//			}	
//		}
//		
//		ArrayList<LimitedElement> tempElements = new ArrayList<LimitedElement>();
//		
//		int i=0;
//		for(LimitedElement e:parallelElements)
//		{
//			if(e.isLimitedArc())
//			{
//				LimitedArc arc = (LimitedArc)e;
//				LimitedLine line1;
//				LimitedLine line2;
//
//				boolean haveLinesInterception = false;
//				if (i==0)
//				{
//					if(parallelElements.get(parallelElements.size()-1).isLimitedLine())
//					{
//						line1 = (LimitedLine)parallelElements.get(parallelElements.size()-1);						
//					}
//					line2 = (LimitedLine)parallelElements.get(i+1);
//				}
//				else if(i==parallelElements.size()-1)
//				{
//					line1 = (LimitedLine)parallelElements.get(i-1);
//					if(parallelElements.get(0).isLimitedLine())
//					{
//						line2 = (LimitedLine)parallelElements.get(0);
//					}					
//				}
//				else
//				{
//					if (parallelElements.get(i-1).isLimitedLine() && parallelElements.get(i+1).isLimitedLine())
//					{
//						line1 = (LimitedLine)parallelElements.get(i-1);
//						line2 = (LimitedLine)parallelElements.get(i+1);
//						haveLinesInterception = intersects(line1, line2);
//					}
//				}
//				if (arc.getRadius()!=0)
//				{
//					if(!haveLinesInterception)
//					{
//						tempElements.add(e);
//					}					
//				}
//				else				
//				{
//					System.out.println("Arc eliminated From " + arc.getInitialPoint() + " to " + arc.getFinalPoint());
//				}
//			}
//			else if(e.isLimitedLine())
//			{
//				LimitedLine line = (LimitedLine)e;
////				System.out.println("Line lenght " + line.getLenght());
////				System.out.println("from " + line.getInitialPoint() + " to " + line.getFinalPoint());
//				if (line.getLenght()!=0)
//				{						
//					tempElements.add(e);
//				}
//				else
//				{
//					System.out.println("Line eliminated From " + line.getInitialPoint() + " To " + line.getFinalPoint());
//				}
//			}
//			i++;
//		}
//		parallelElements = tempElements;
//		return parallelElements;
//	}

	
	public static Point3d intersect(LimitedLine line1, LimitedLine line2, double distance)
	{
						
		Point3d unitVectorCurrent = unitVector(line2.getInitialPoint(), line2.getFinalPoint());
		Point3d unitVectorBefore = unitVector(line1.getFinalPoint(), line1.getInitialPoint());
		
		double initialAngle = angle(unitVectorCurrent);
		double finalAngle = angle(unitVectorBefore);				
		double angle = finalAngle-initialAngle;				
		double teta = initialAngle + angle/2;
		
		double lenght = distance/Math.sin(angle/2);
		
		Point3d intersect = new Point3d(line1.getFinalPoint().getX()+lenght*Math.cos(teta), line1.getFinalPoint().getY()+lenght*Math.sin(teta), line1.getInitialPoint().getZ());
		
		return intersect;
	}
	
	public static LimitedArc parallelArc(LimitedArc arc, double distance, boolean inside)
	{
		Point3d newInitialPoint;
		LimitedArc newArc = null;
		if (arc.getDeltaAngle()<0 || !(inside))
		{
			newInitialPoint = plus(arc.getCenter(),multiply((arc.getRadius()+distance),unitVector(arc.getCenter(),arc.getInitialPoint())));
			newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
		}
		else
		{
			if(arc.getRadius() > distance)
			{
				newInitialPoint = plus(arc.getCenter(),multiply((arc.getRadius()-distance),unitVector(arc.getCenter(),arc.getInitialPoint())));
				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
			}
		}
//		System.out.println("Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " delta " + arc.getDeltaAngle()*180/Math.PI + " radius " + arc.getRadius());
		return newArc;
	}

	public static Point3d arcToVertec(LimitedArc arc)	
	{
		double radius = arc.getRadius();
		double angle = arc.getDeltaAngle();
		Point3d centerToInitial = minus(arc.getInitialPoint(), arc.getCenter());
		Point3d initialToFinalHalf = multiply(0.5,minus(arc.getFinalPoint(), arc.getInitialPoint()));
		
		Point3d unitarialFromCenterToVertex = unitVector(arc.getCenter(), initialToFinalHalf);
		double distanceCenterToVertex = radius/Math.cos(Math.abs(angle/2));
		
		Point3d vertex3d = plus(arc.getCenter(), multiply(distanceCenterToVertex,unitarialFromCenterToVertex));
		return vertex3d;
	}
	public static void showElements(ArrayList<LimitedElement> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				//System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI  + " radius " + arc.getRadius()); 
				System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y + ",0)" + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ",0)" + "," + arc.getDeltaAngle() + ");");		
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				//System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
				System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + line.getInitialPoint().x + "," + line.getInitialPoint().y + ",0)" + ",new Point3d(" + line.getFinalPoint().x + "," + line.getFinalPoint().y + ",0));");

			}			
			i++;			
		}

	}
	public static void showLines(ArrayList<LimitedLine> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			LimitedLine line = (LimitedLine)e;
			System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + line.getInitialPoint().x + "," + line.getInitialPoint().y + ",0)" + ",new Point3d(" + line.getFinalPoint().x + "," + line.getFinalPoint().y + ",0));");	
			i++;			
		}

	}
	public static void showArcs(ArrayList<LimitedArc> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			LimitedArc arc = (LimitedArc)e;
			System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y + ",0)" + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ",0)" + "," + arc.getDeltaAngle() + ");");							
			i++;			
		}

	}
	
	
	public static ArrayList<Point2D> scalePoints(ArrayList<Point2D> points, double multiple)
	{
		ArrayList<Point2D> out = new ArrayList<Point2D> ();
		for (Point2D p:points)
		{
			out.add(new Point2D.Double(p.getX()*multiple,p.getY()*multiple));
		}
		return out;
	}
	
	public static boolean intersects(LimitedLine line1, LimitedLine line2)
	{
		Point3d intersect = intersectionPoint(line1, line2);
		if (intersect==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	public static Point3d intersectionPoint(LimitedLine line1, LimitedLine line2)
	{
		double x0=0;
		double y0=0;
		double a1 =0;
		double b1 =0;
		double a2 = 0;
		double b2 = 0;
		
		if(line1.getInitialPoint().getZ()==line2.getInitialPoint().getZ())
		{
			//Equation y = a + b*x
			Point3d pi1 = line1.getInitialPoint();
			Point3d pf1 = line1.getFinalPoint();
			
			Point3d pi2 = line2.getInitialPoint();
			Point3d pf2 = line2.getFinalPoint();
			
			//Garante que nï¿½o calularemos uma tangente = infinity
			//Xinicial1 = Xfinal1 e Xinicial2 != Xfinal2
			if((roundNumber(pi1.x,10) == roundNumber(pf1.x,10)) && (roundNumber(pi2.x,10) != roundNumber(pf2.x,10)))
			{
				double xini = pi2.x;
				double xfin = pf2.x;
				if(xini > xfin)
				{
					xini = pf2.x;
					xfin = pi2.x;
				}
				//Verifica se Xi1 nao estiver entre Xi2 e Xf2 (garantindo que nao haja intersecao)
				if((pi1.x < xini) || (pi1.x > xfin))
				{
					return null;
				}	
				//Caso haja intersecao, ela e calculada
				b2 = (pf2.y-pi2.y)/(pf2.x-pi2.x);
				a2 = pi2.y - b2*pi2.x;
				x0 = pi1.x;
				y0 = a2 + b2 * x0;
			}
			//Xinicial1 != Xfinal1 e Xinicial2 == Xfinal2
			else if((roundNumber(pi1.x,10) != roundNumber(pf1.x,10)) && (roundNumber(pi2.x,10) == roundNumber(pf2.x,10)))
			{
				double xini = pi1.x;
				double xfin = pf1.x;
				if(xini > xfin)
				{
					xini = pf1.x;
					xfin = pi1.x;
				}
				//Verifica se Xi2 nao esta entre Xi1 e Xf1 (garantindo que nao haja intersecao)
				if((pi2.x < xini)&& (pi2.x > xfin))
				{
					return null;
				}
				//Caso haja intersecao, ela e calculada
				b1 = (pf1.y-pi1.y)/(pf1.x-pi1.x);
				a1 = pi1.y - b1*pi1.x;
				x0 = pi2.x;
				y0 = a1 + b1 * x0;
			}
			
			else if((roundNumber(pi1.x,10) == roundNumber(pf1.x,10)) && (roundNumber(pi2.x,10) == roundNumber(pf2.x,10)))
			{
				return null;
			}
			//Xinicial1 != Xfinal1 e Xinicial2 != Xfinal2
			else if((roundNumber(pi1.x,10) != roundNumber(pf1.x,10)) && (roundNumber(pi2.x,10) != roundNumber(pf2.x,10)))
			{
				b1 = (pf1.y-pi1.y)/(pf1.x-pi1.x);
				a1 = pi1.y - b1*pi1.x;
				b2 = (pf2.y-pi2.y)/(pf2.x-pi2.x);
				a2 = pi2.y - b2*pi2.x;
				if (b1==b2)
				{
					return null;
				}
				else
				{
					x0 = -(a1-a2)/(b1-b2);
					y0 = a1 + b1*x0;
				}
				
			}
		}
		Point3d intersect = new Point3d(x0, y0, line1.getInitialPoint().getZ());
		
		if(belongs(line1, intersect) && belongs(line2, intersect))
		{
			return intersect;
		}
		else
		{
			return null;
		}
//		Point3d vetorUnitario1 = unitVector(line1.getInitialPoint(), line1.getFinalPoint());
//		Point3d vetorUnitario2 = unitVector(line2.getInitialPoint(), line2.getFinalPoint());
//		double distance1 = line1.getInitialPoint().distance(intersect);
//		double distance2 = line2.getInitialPoint().distance(intersect);
//		
//		double possivelIntersecaoX1 = line1.getInitialPoint().x + multiply(distance1, vetorUnitario1).x;
//		double possivelIntersecaoY1 = line1.getInitialPoint().y + multiply(distance1, vetorUnitario1).y;
//		double possivelIntersecaoX2 = line2.getInitialPoint().x + multiply(distance2, vetorUnitario2).x;
//		double possivelIntersecaoY2 = line2.getInitialPoint().y + multiply(distance2, vetorUnitario2).y;
//		
//		double intersectXF = truncarDecimais(intersect.x, 10);
//		double intersectYF = truncarDecimais(intersect.y, 10);
//		double possivelIntersecaoXF1 = truncarDecimais(possivelIntersecaoX1, 10);
//		double possivelIntersecaoYF1 = truncarDecimais(possivelIntersecaoY1, 10);
//		double possivelIntersecaoXF2 = truncarDecimais(possivelIntersecaoX2, 10);
//		double possivelIntersecaoYF2 = truncarDecimais(possivelIntersecaoY2, 10);
//		
//		System.out.println("intersection x = " + intersectXF);
//		System.out.println("intersection y = " + intersectYF);
//		System.out.println("poss intersection x = " + possivelIntersecaoXF1);
//		System.out.println("poss intersection y = " + possivelIntersecaoYF1);
//		if(intersectXF == possivelIntersecaoXF1 && intersectYF ==  possivelIntersecaoYF1 && intersectXF == possivelIntersecaoXF2 && intersectYF == possivelIntersecaoYF2)
//		{
//			return intersect;
//		}
//		else
//		{
//			return null;
//		}
//		if(belongs(line1, intersect) && belongs(line2, intersect))
//		{
//			return intersect;
//		}
//		else
//		{
//			return null;
//		}
	}
	public static double truncarDecimais(double numero, int casasDecimais)
	{
		double saida = 0;
		String intersectX = ""+ numero;
//		System.out.println("NUMERO = " + intersectX);
//		System.out.println("CONTAINS = " + intersectX.contains("."));
		int carateresDepoisDoPonto = intersectX.length() - intersectX.indexOf(".") - 1;
//		System.out.println("CARAT = " + carateresDepoisDoPonto);
		if(carateresDepoisDoPonto > casasDecimais)
		{
			int indiceDoPontoDecimal = intersectX.indexOf(".");
			String decimais = intersectX.substring(indiceDoPontoDecimal, indiceDoPontoDecimal + casasDecimais + 1);
			String intersectXFormatado = (int)(numero) + decimais;
			saida = (Double.parseDouble(intersectXFormatado));
		} else
		{
			saida = numero;
		}
		return saida;
	}

	public static ArrayList<Point3d> intersectionPoint(LimitedArc arc, LimitedLine line)
	{
		LimitedArc arcTmp = new LimitedArc(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle());
		if(arc.getDeltaAngle() < 0)
		{
//			Point3d temp = arc.getInitialPoint();
			Point3d temp1 = arc.getFinalPoint();
			arcTmp = new LimitedArc(arc.getCenter(), temp1, -arc.getDeltaAngle());
		}
		ArrayList<Point3d> intersection = null;
		/**
		 *  line --> y = m * x + b
		 *  circle --> (cx - p)^2 + (cy - q)^2 = r^2
		 */
		double x1,x2,y1,y2;
		double radical = 0;
		double r = Math.sqrt(Math.pow(arcTmp.getInitialPoint().getX() - arcTmp.getCenter().getX(), 2) + Math.pow(arcTmp.getInitialPoint().getY() - arcTmp.getCenter().getY(), 2));
		double xin = arcTmp.getCenter().x - r;
		double xif = arcTmp.getCenter().x + r;
		
		if (line.getInitialPoint().x == line.getFinalPoint().x) 
		{
			if (!((line.getInitialPoint().x >= xin) && (line.getInitialPoint().x <= xif))) 
			{
				return null;
			}
			else 
			{
				x1 = line.getInitialPoint().x;
				x2 = line.getInitialPoint().x;
				y1 = arcTmp.getCenter().y + Math.sqrt(Math.pow(r, 2) - Math.pow(line.getInitialPoint().x - arcTmp.getCenter().x, 2));
				y2 = arcTmp.getCenter().y - Math.sqrt(Math.pow(r, 2) - Math.pow(line.getInitialPoint().x - arcTmp.getCenter().x, 2));
			}
		}
		else
		{
			double m = (line.getFinalPoint().getY() - line.getInitialPoint().getY()) / (line.getFinalPoint().getX() - line.getInitialPoint().getX());
			double b = line.getInitialPoint().getY() - m * line.getInitialPoint().getX();
			/**
			 * caso nao haja ponto de intersecao
			 */
			radical = Math.pow(-2 * arcTmp.getCenter().x + 2 * m * b - 2 * m * arcTmp.getCenter().y, 2) - 4 * (1 + Math.pow(m, 2)) * (Math.pow(arcTmp.getCenter().x, 2) + Math.pow(b, 2) - 2 * b * arcTmp.getCenter().y + Math.pow(arcTmp.getCenter().y, 2) - Math.pow(r, 2));
			//Garante que nao de NaN na raiz
			if(radical<0){
				return null;
			}
			x1 = (2 * arcTmp.getCenter().x - 2 * m * b + 2 * m *  arcTmp.getCenter().y + Math.sqrt(radical)) / (2 + 2 * Math.pow(m, 2));
			x2 = (2 * arcTmp.getCenter().x - 2 * m * b + 2 * m *  arcTmp.getCenter().y - Math.sqrt(radical)) / (2 + 2 * Math.pow(m, 2));
			y1 = m * x1 + b;
			y2 = m * x2 + b;
		}
		//double x;
		//double y;
		
		/**
		 *  caso a linha for vertical -- Se houver intersecao, o x da intersecao sera no x da linha
		 */
		
		double anguloInicial = Math.atan2(arcTmp.getInitialPoint().y - arcTmp.getCenter().y, arcTmp.getInitialPoint().x - arcTmp.getCenter().x);
		double anguloFinal = Math.atan2(arcTmp.getFinalPoint().y - arcTmp.getCenter().y, arcTmp.getFinalPoint().x - arcTmp.getCenter().x);
		double anguloNaIntersecao1 = Math.atan2(y1 - arcTmp.getCenter().y, x1 - arcTmp.getCenter().x);
		double anguloNaIntersecao2 = Math.atan2(y2 - arcTmp.getCenter().y, x2 - arcTmp.getCenter().x);
		
		
		
		if(anguloInicial < 0)
		{
			anguloInicial = 2 * Math.PI + anguloInicial;
		}
		if(anguloFinal <= 0)
		{
			anguloFinal = 2 * Math.PI + anguloFinal;
		}
		if(anguloNaIntersecao1 < 0)
		{
			anguloNaIntersecao1 = 2 * Math.PI + anguloNaIntersecao1;
		}
		if(anguloNaIntersecao2 < 0)
		{
			anguloNaIntersecao2 = 2* Math.PI + anguloNaIntersecao2;
		}
		
		//Normalizacao dos angulos inicial e final
		if(anguloInicial > anguloFinal)
		{
			anguloFinal = 2 * Math.PI + anguloFinal;
		}
		
//		System.err.println("x1 = " + x1 + "\ty1 = " + y1);
//		System.err.println("x2 = " + x2 + "\ty2 = " + y2);
//		System.err.println("ARC center = " + arc.getCenter());
//		System.err.println("anguloIntersecao1 (DEG) = " + (anguloNaIntersecao1 * 180 / Math.PI));
//		System.err.println("anguloIntersecao1 (RAD) = " + (anguloNaIntersecao1));
//		System.err.println("anguloIntersecao2 (DEG) = " + (anguloNaIntersecao2 * 180 / Math.PI));
//		System.err.println("anguloIntersecao2 (RAD) = " + (anguloNaIntersecao2));
//		System.err.println("anguloInicial (DEG) = " + (anguloInicial * 180 / Math.PI));
//		System.err.println("anguloInicial (RAD) = " + anguloInicial);
//		System.err.println("anguloFinal (DEG) = " + (anguloFinal * 180 / Math.PI));
//		System.err.println("anguloFinal (RAD) = " + anguloFinal);
//		System.err.println("Linha: " + line.getInitialPoint() + line.getFinalPoint());
//		System.err.println("Arco: " + arc.getInitialPoint() + arc.getFinalPoint() + arc.getCenter());
//		System.err.println("Radical: " + radical);
//		
//		//Modulo da linha de entrada
//		double moduloR1 = Math.sqrt(Math.pow((line.getFinalPoint().x - line.getInitialPoint().x),2) + Math.pow((line.getFinalPoint().y - line.getInitialPoint().y), 2));
//		//Vetor diretor da linha de entrada
//		Point3d diretor1 = new Point3d(((line.getFinalPoint().x - line.getInitialPoint().x)/moduloR1),((line.getFinalPoint().y - line.getInitialPoint().y)/moduloR1),0);
//		//Modulo da linha com ponto inicial da linha de entrada e ponto final = possivel ponto de intersecao
//		double moduloR2;
//		//Vetor da linha com ponto inicial da linha de entrada e ponto final = possivel ponto de intersecao
//		Point3d diretor2;
//		
//		if((anguloInicial <= anguloNaIntersecao1 && anguloFinal >= anguloNaIntersecao1))
//		{
//			moduloR2 = Math.sqrt(Math.pow((x1 - line.getInitialPoint().x),2) + Math.pow((y1 - line.getInitialPoint().y), 2));
//			diretor2 = new Point3d(((x1 - line.getInitialPoint().x)/moduloR1),((y1 - line.getInitialPoint().y)/moduloR1),0);
//			//Validacao do possivel ponto de intersecao
//			if (!((diretor1.getX() == (-1)*diretor2.getX()) && (diretor1.getY() == (-1)*diretor2.getY())))
//			{
//				if(moduloR1 >= moduloR2)
//				{
//					intersection = new ArrayList<Point3d> ();
//					intersection.add(new Point3d(x1,y1,0));
//				}
//			}
//		} 
//		if((anguloInicial <= anguloNaIntersecao2 && anguloFinal >= anguloNaIntersecao2))
//		{
//			moduloR2 = Math.sqrt(Math.pow((x2 - line.getInitialPoint().x),2) + Math.pow((y2 - line.getInitialPoint().y), 2));
//			diretor2 = new Point3d(((x2 - line.getInitialPoint().x)/moduloR1),((y2 - line.getInitialPoint().y)/moduloR1),0);
//			//Validacao do possivel ponto de intersecao
//			if (!((diretor1.getX() == (-1)*diretor2.getX()) && (diretor1.getY() == (-1)*diretor2.getY())))
//			{
//				if(moduloR1 >= moduloR2)
//				{
//					if(intersection == null)
//					{
//						intersection = new ArrayList<Point3d> ();
//					}
//					intersection.add(new Point3d(x2,y2,0));
//				}
//			}
//		}
		Point3d P1 = new Point3d(x1,y1,line.getInitialPoint().z);
		Point3d P2 = new Point3d(x2,y2,line.getInitialPoint().z);
		if(belongsArc(arcTmp,P1) && belongs(line,P1))
		{
			intersection = new ArrayList<Point3d>();
			intersection.add(P1);
		}
		if(belongsArc(arcTmp,P2) && belongs(line,P2))
		{
			if(intersection == null)
			{
				intersection = new ArrayList<Point3d> ();
			}
			intersection.add(P2);
		}
//		if(!(intersection == null))
//		{
//			if(intersection.size() == 1)
//			{
//				if (!belongs(line, intersection.get(0))) 
//				{
//					intersection = null;
//				} 
//			}
//			else if(intersection.size() == 2)
//			{
//				if (!belongs(line, intersection.get(1))) 
//				{
//					intersection = null;
//				} 
//			}
//		}
		return intersection;
	}
	/**
	 *  Este metodo verifica se a circunferencia criada com arc1 estÃ¡ dentro da circunferencia criada com arc2
	 * @param arc1 --> arco Base
	 * @param arc2 --> arco a verificar se estÃ¡ contido
	 * @return
	 */
	public static boolean circleIsContentInOtherCircle(LimitedArc arc1, LimitedArc arc2)
	{
		boolean estaContido = false;
		double distanciaEntreOsCentros = arc1.getCenter().distance(arc2.getCenter());
		if(roundNumber(arc1.getRadius(), 10) <= roundNumber(distanciaEntreOsCentros + arc2.getRadius(), 10))
		{
			estaContido = true;
		}
		return estaContido;
	}
	public static ArrayList<Point3d> intersectionPoint(LimitedArc arc1, LimitedArc arc2)
	{
//		LimitedArc arc1Tmp = new LimitedArc(arc1.getInitialPoint(), arc1.getFinalPoint(), arc1.getCenter());
//		LimitedArc arc2Tmp = new LimitedArc(arc2.getInitialPoint(), arc2.getFinalPoint(), arc2.getCenter());
		LimitedArc arc1Tmp = new LimitedArc(arc1.getCenter(), arc1.getInitialPoint(), arc1.getDeltaAngle());
		LimitedArc arc2Tmp = new LimitedArc(arc2.getCenter(), arc2.getInitialPoint(), arc2.getDeltaAngle());

		if(arc1.getDeltaAngle() < 0)
		{
			//Point3d temp = arc1.getInitialPoint();
			Point3d temp1 = arc1.getFinalPoint();
			arc1Tmp = new LimitedArc(arc1.getCenter(), temp1, -arc1.getDeltaAngle());
		}
		if(arc2.getDeltaAngle() < 0)
		{
			//Point3d temp = arc2.getInitialPoint();
			Point3d temp1 = arc2.getFinalPoint();
			arc2Tmp = new LimitedArc(arc2.getCenter(), temp1, -arc2.getDeltaAngle());

		}
		ArrayList<Point3d> intersection = new ArrayList<Point3d>();
		
		double x1, x2, y1 = 0, y2 = 0, r1 = 0, r2 = 0, cx1, cx2, cy1, cy2;
		
		cx1 = arc1Tmp.getCenter().x;
		cx2 = arc2Tmp.getCenter().x;
		cy1 = arc1Tmp.getCenter().y;
		cy2 = arc2Tmp.getCenter().y;
		r1 = arc1Tmp.getRadius();
		r2 = arc2Tmp.getRadius();
		
//		if(isTheSamePoint(arc1.getCenter(),arc2.getCenter()))
//		{
//			return intersection;
//		}
//		if(circleIsContentInOtherCircle(arc1Tmp, arc2Tmp) || circleIsContentInOtherCircle(arc2Tmp, arc1Tmp))
//		{
//			return intersection;
//		}
		x1 = (cx1*Math.pow(cy1, 2) - Math.pow(cx1, 2)*cx2 - cx1*Math.pow(cx2, 2) + cx1*Math.pow(cy2, 2) + cx2*Math.pow(cy1, 2) + cx2*Math.pow(cy2, 2) - cx1*Math.pow(r1, 2) + cx2*Math.pow(r1, 2) + cx1*Math.pow(r2, 2) - cx2*Math.pow(r2, 2) + Math.pow(cx1, 3) + Math.pow(cx2, 3) - cy1*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) + cy2*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1,2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1,2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) -Math.pow(r1,4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - 2*cx1*cy1*cy2 - 2*cx2*cy1*cy2)/(2*(Math.pow(cx1, 2) - 2*cx1*cx2 + Math.pow(cx2, 2) + Math.pow(cy1, 2) - 2*cy1*cy2 + Math.pow(cy2, 2)));
		x2 = (cx1*Math.pow(cy1, 2) - Math.pow(cx1, 2)*cx2 - cx1*Math.pow(cx2, 2) + cx1*Math.pow(cy2, 2) + cx2*Math.pow(cy1, 2) + cx2*Math.pow(cy2, 2) - cx1*Math.pow(r1, 2) + cx2*Math.pow(r1, 2) + cx1*Math.pow(r2, 2) - cx2*Math.pow(r2, 2) + Math.pow(cx1, 3) + Math.pow(cx2, 3) + cy1*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - cy2*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - 2*cx1*cy1*cy2 - 2*cx2*cy1*cy2)/(2*(Math.pow(cx1, 2) - 2*cx1*cx2 + Math.pow(cx2, 2) + Math.pow(cy1, 2) - 2*cy1*cy2 + Math.pow(cy2, 2)));
		
		if(((Double)x1).isNaN() || ((Double)x2).isNaN())
		{
			return null;
		}
		/**
		 * todos os possiveis pontos de intersecao nos dois circulos com x1 e x2 calculados
		 */
		double y11 = cy1 + Math.sqrt(Math.pow(r1, 2) - Math.pow(x1 - cx1, 2));
		double y12 = cy1 - Math.sqrt(Math.pow(r1, 2) - Math.pow(x1 - cx1, 2));
		double y13 = cy1 + Math.sqrt(Math.pow(r1, 2) - Math.pow(x2 - cx1, 2));
		double y14 = cy1 - Math.sqrt(Math.pow(r1, 2) - Math.pow(x2 - cx1, 2));
		double y21 = cy2 + Math.sqrt(Math.pow(r2, 2) - Math.pow(x1 - cx2, 2));
		double y22 = cy2 - Math.sqrt(Math.pow(r2, 2) - Math.pow(x1 - cx2, 2));
		double y23 = cy2 + Math.sqrt(Math.pow(r2, 2) - Math.pow(x2 - cx2, 2));
		double y24 = cy2 - Math.sqrt(Math.pow(r2, 2) - Math.pow(x2 - cx2, 2));
		ArrayList<Point3d> pPossiveis = new ArrayList<Point3d>();
		pPossiveis.add(new Point3d(x1,y11,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x1,y12,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x2,y13,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x2,y14,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x1,y21,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x1,y22,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x2,y23,arc1.getCenter().z));
		pPossiveis.add(new Point3d(x2,y24,arc1.getCenter().z));
		
//		for(int i = 0;i < pPossiveis.size(); i++)
//		{
//			System.out.println("P: " + pPossiveis.get(i));
//		}
		for(int i = 0;i < pPossiveis.size(); i++)
		{
			Point3d pTmp = pPossiveis.get(i);
//			System.out.println("P: " + pTmp);
			if(belongsArc(arc1Tmp,pTmp) && belongsArc(arc2Tmp,pTmp))
			{
				if(!(alreadyUsed(pTmp, intersection)))
				{
					intersection.add(pTmp);
				}
			}
		}
//		System.out.println("pPossiveis: " + pPossiveis);
//		for(int i = 0;i < pPossiveis.size(); i++)
//		{
//			Point3d pTmp = pPossiveis.get(i);
//			if(!(alreadyUsed(pTmp, intersection)))
//			{
//				intersection.add(pTmp);
//			}
//		}
//		ArrayList<Double> ypossiveis = new ArrayList<Double>();
//		double [] ypossiveis1 = {y11, y12, y13, y14};
//		double [] ypossiveis2 = {y21, y22, y23, y24};
//		for(int i = 0; i < ypossiveis1.length; i++)
//		{
//			System.out.println("Ypossiveis1: " + ypossiveis1[i]);
//			System.out.println("Ypossiveis2: " + ypossiveis2[i]);
//			for(int j = 0; j < ypossiveis2.length; j++)
//			{
//				if(roundNumber(x1, 10) == roundNumber(x2, 10))
//				{
//					ypossiveis.add(ypossiveis1[0]);
//					ypossiveis.add(ypossiveis1[1]);
//					break;
//				}
//				if(roundNumber(ypossiveis1[i], 10) == roundNumber(ypossiveis2[j], 10))
//				{
//					if(i == 0 || i == 1)
//					{
//						ypossiveis.add(ypossiveis1[i]);
//						
//					} 
//					else if(i == 2 || i == 3)
//					{
//						ypossiveis.add(ypossiveis1[i]);
//					}
//					//ypossiveis1[i] = -1;
//					break;
//				}
//			}
//		}
		
//		System.out.println("Array Ypossiveis: " + ypossiveis);
//		System.out.println("Possiveis size: " + ypossiveis.size());
//		y1 = pPossiveis.get(0);
//		y2 = pPossiveis.get(1);
//		Point3d possivel1 = new Point3d(x1, y1, 0);
//		Point3d possivel2 = new Point3d(x2, y2, 0);
//		
////		System.err.println("poss 1 =" + possivel1);
////		System.err.println("poss 2 =" + possivel2);
//		
//		double anguloInicial1 = Math.atan2(arc1Tmp.getInitialPoint().y - arc1Tmp.getCenter().y, arc1Tmp.getInitialPoint().x - arc1Tmp.getCenter().x);
//		double anguloFinal1 = Math.atan2(arc1Tmp.getFinalPoint().y - arc1Tmp.getCenter().y, arc1Tmp.getFinalPoint().x - arc1Tmp.getCenter().x);
//		
//		double anguloInicial2 = Math.atan2(arc2Tmp.getInitialPoint().y - arc2Tmp.getCenter().y, arc2Tmp.getInitialPoint().x - arc2Tmp.getCenter().x);
//		double anguloFinal2 = Math.atan2(arc2Tmp.getFinalPoint().y - arc2Tmp.getCenter().y, arc2Tmp.getFinalPoint().x - arc2Tmp.getCenter().x);
//		
//		double anguloNaIntersecaoCirculo1Possivel1 = Math.atan2(possivel1.y - arc1Tmp.getCenter().y, possivel1.x - arc1Tmp.getCenter().x);
//		double anguloNaIntersecaoCirculo1Possivel2 = Math.atan2(possivel2.y - arc1Tmp.getCenter().y, possivel2.x - arc1Tmp.getCenter().x);
//		
//		double anguloNaIntersecaoCirculo2Possivel1 = Math.atan2(possivel1.y - arc2Tmp.getCenter().y, possivel1.x - arc2Tmp.getCenter().x);
//		double anguloNaIntersecaoCirculo2Possivel2 = Math.atan2(possivel2.y - arc2Tmp.getCenter().y, possivel2.x - arc2Tmp.getCenter().x);
//				
//		/**
//		 * correcao dos angulos negativos
//		 */
//		if(anguloInicial1 < 0)
//		{
//			anguloInicial1 = 2 * Math.PI + anguloInicial1;
//		}
//		if(anguloFinal1 <= 0)
//		{
//			anguloFinal1 = 2 * Math.PI + anguloFinal1;
//		}
//		if(anguloInicial2 < 0)
//		{
//			anguloInicial2 = 2 * Math.PI + anguloInicial2;
//		}
//		if(anguloFinal2 < 0)
//		{
//			anguloFinal2 = 2 * Math.PI + anguloFinal2;
//		}
//		if(anguloNaIntersecaoCirculo1Possivel1 < 0)
//		{
//			anguloNaIntersecaoCirculo1Possivel1 = 2 * Math.PI + anguloNaIntersecaoCirculo1Possivel1;
//		}
//		if(anguloNaIntersecaoCirculo1Possivel2 < 0)
//		{
//			anguloNaIntersecaoCirculo1Possivel2 = 2 * Math.PI + anguloNaIntersecaoCirculo1Possivel2;
//		}
//		if(anguloNaIntersecaoCirculo2Possivel1 < 0)
//		{
//			anguloNaIntersecaoCirculo2Possivel1 = 2 * Math.PI + anguloNaIntersecaoCirculo2Possivel1;
//		}
//		if(anguloNaIntersecaoCirculo2Possivel2 < 0)
//		{
//			anguloNaIntersecaoCirculo2Possivel2 = 2 * Math.PI + anguloNaIntersecaoCirculo2Possivel2;
//		}
//		
//		if(anguloInicial1 > anguloFinal1)
//		{
//			anguloFinal1 = 2 * Math.PI + anguloFinal1;
//		}
//		if(anguloInicial2 > anguloFinal2)
//		{
//			anguloFinal2 = 2 * Math.PI + anguloFinal2;
//		}
//		
////		System.out.println("AI1 = " + (anguloInicial1 * 180 / Math.PI));
////		System.out.println("AF1 = " + (anguloFinal1 * 180 / Math.PI));
////		System.out.println("AI2 = " + (anguloInicial2 * 180 / Math.PI));
////		System.out.println("AF2 = " + (anguloFinal2 * 180 / Math.PI));
////		System.out.println("anguloNaIntersecaoCirculo1Possivel1 = " + (anguloNaIntersecaoCirculo1Possivel1 * 180 / Math.PI));
////		System.out.println("anguloNaIntersecaoCirculo1Possivel2 = " + (anguloNaIntersecaoCirculo1Possivel2 * 180 / Math.PI));
////		System.out.println("anguloNaIntersecaoCirculo2Possivel1 = " + (anguloNaIntersecaoCirculo2Possivel1 * 180 / Math.PI));
////		System.out.println("anguloNaIntersecaoCirculo2Possivel2 = " + (anguloNaIntersecaoCirculo2Possivel2 * 180 / Math.PI));
//		//System.out.println("")
//		
//
//		if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel1) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel1))
//				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel1) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel1)))
//		{
//			intersection = new ArrayList<Point3d> ();
//			intersection.add(possivel1);
//		}
//		if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel2) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel2))
//				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel2) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel2)))
//		{
//			if(intersection == null)
//			{
//				intersection = new ArrayList<Point3d> ();
//			}
//			intersection.add(possivel2);
//		}
		if(intersection.size() == 0)
		{
			return null;
		}
		return intersection;
	}
	public static double roundNumber(double number, int decimals)
	{
		double saida = 0;
		saida = number * Math.pow(10, decimals);
		saida = Math.round(saida);
		saida = saida / Math.pow(10, decimals);
//		saida  = truncarDecimais(number, decimals);
		
		return saida;
	}
}
