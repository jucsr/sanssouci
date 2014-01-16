package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class GeometricOperations 
{
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
		Point3d p1 = line.getSp();
		Point3d p0 = line.getFp();
		
		Point3d v = new Point3d(p1.getX() - p0.getX(), p1.getY()-p0.getY(), p1.getZ()-p0.getZ());
		Point3d w = new Point3d(p.getX() - p0.getX(), p.getY()-p0.getY(), p.getZ()-p0.getZ());
		
		double c1 = escalar(v,w);
		double c2 = escalar(v,v);
				
				
		if ( c1 <= 0 )  // before P0
		{
			System.out.println("Nearest point " + p0);
			return p0;
		}
		if ( c2  <= c1 ) // after P1
		{
			System.out.println("Nearest point " + p1);
			return p1;
		}

       double b = c1 / c2;
       Point3d Pb = new Point3d(p0.getX() + b*v.getX(), p0.getY() + b*v.getY(), p0.getZ() + b*v.getZ());
       System.out.println("Nearest point " + Pb);      
       return Pb;
	}
	
	public static double minimumDistance(Point3d p,  LimitedLine line)
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
	
	public static double minimumDistance(LimitedLine S1, LimitedLine S2)
	{
	    Point3d   u = new Point3d(S1.getSp().getX() - S1.getFp().getX(), S1.getSp().getY() - S1.getFp().getY(), S1.getSp().getZ() - S1.getFp().getZ());
	    Point3d   v = new Point3d(S2.getSp().getX() - S2.getFp().getX(), S2.getSp().getY() - S2.getFp().getY(), S2.getSp().getZ() - S2.getFp().getZ());
	    Point3d   w = new Point3d(S1.getFp().getX() - S2.getFp().getX(), S1.getFp().getY() - S2.getFp().getY(), S1.getFp().getZ() - S2.getFp().getZ());
	    
	    double    a = escalar(u,u);         // always >= 0
	    double    b = escalar(u,v);
	    double    c = escalar(v,v);         // always >= 0
	    double    d = escalar(u,w);
	    double    e = escalar(v,w);
	    double    D = a*c - b*b;        // always >= 0
	    double    sc, sN, sD = D;       // sc = sN / sD, default sD = D >= 0
	    double    tc, tN, tD = D;       // tc = tN / tD, default tD = D >= 0

	    // compute the line parameters of the two closest points
	    if (D < SMALL_NUM) 
	    { // the lines are almost parallel
	        sN = 0.0;         // force using point P0 on segment S1
	        sD = 1.0;         // to prevent possible division by 0.0 later
	        tN = e;
	        tD = c;
	    }
	    else 
	    {                 // get the closest points on the infinite lines
	        sN = (b*e - c*d);
	        tN = (a*e - b*d);
	        if (sN < 0.0) {        // sc < 0 => the s=0 edge is visible
	            sN = 0.0;
	            tN = e;
	            tD = c;
	        }
	        else if (sN > sD) {  // sc > 1  => the s=1 edge is visible
	            sN = sD;
	            tN = e + b;
	            tD = c;
	        }
	    }

	    if (tN < 0.0) 
	    {            // tc < 0 => the t=0 edge is visible
	        tN = 0.0;
	        // recompute sc for this edge
	        if (-d < 0.0)
	            sN = 0.0;
	        else if (-d > a)
	            sN = sD;
	        else 
	        {
	            sN = -d;
	            sD = a;
	        }
	    }
	    else if (tN > tD) 
	    {      // tc > 1  => the t=1 edge is visible
	        tN = tD;
	        // recompute sc for this edge
	        if ((-d + b) < 0.0)
	            sN = 0;
	        else if ((-d + b) > a)
	            sN = sD;
	        else {
	            sN = (-d +  b);
	            sD = a;
	        }
	    }
	    // finally do the division to get sc and tc
	    sc = (Math.abs(sN) < SMALL_NUM ? 0.0 : sN / sD);
	    tc = (Math.abs(tN) < SMALL_NUM ? 0.0 : tN / tD);

	    // get the difference of the two closest points
	    Point3d   dP = new Point3d(w.getX() + (sc * u.getX()) - (tc * v.getX()), w.getY() + (sc * u.getY()) - (tc * v.getY()), w.getZ() + (sc * u.getZ()) - (tc * v.getZ()));  // =  S1(sc) - S2(tc)

	    return norm(dP);   // return the closestdistance
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
	
	public static double minimumDistance(Point3d p, LimitedArc arc)
	{		
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
	
	public static double minimumDistance(LimitedLine line, LimitedArc arc)
	{
		double distance=0.0;
		
		//System.out.println("Center of arc " + arc.getCenter());
		Point3d nearestFromLine = nearestPoint(arc.getCenter(), line);
		//System.out.println("Nearest calculated*****" + nearestFromLine);
		
		if(chooseMinimum(distance(arc.getCenter(), line.getFp()), distance(arc.getCenter(), line.getSp())) <= arc.getRadius())
		{
			//System.out.println("Line within the arc ");
			//System.out.println("Arc Initial to line" + minimumDistance(arc.getInitialPoint(), line));
			distance = chooseMinimum(minimumDistance(arc.getInitialPoint(), line), minimumDistance(arc.getFinalPoint(), line), minimumDistance(line.getFp(), arc), minimumDistance(line.getSp(), arc));
			return distance;
		}
		
		Point3d v = new Point3d(nearestFromLine.getX()-arc.getCenter().getX(), nearestFromLine.getY()-arc.getCenter().getY(), nearestFromLine.getZ()-arc.getCenter().getZ());
		Point3d normalPoint = plus(arc.getCenter(),multiply(arc.getRadius()/norm(v),v));
		if (!contentsPoint(normalPoint, arc))
		{
			//System.out.println("normalPoint is not within the arc");
			return chooseMinimum(minimumDistance(arc.getInitialPoint(),line), minimumDistance(arc.getFinalPoint(), line), minimumDistance(line.getFp(),arc), minimumDistance(line.getSp(), arc));
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

		distances.add(minimumDistance(arc1.getInitialPoint(), arc2));
		pointsArc1.add(arc1.getInitialPoint());
		pointsArc2.add(nearestPoint(arc1.getInitialPoint(), arc2));
//		System.out.println("--------------");
//		System.out.println("From " + arc1.getInitialPoint() + " to arc  from " + arc2.getInitialPoint() + " to " + arc2.getFinalPoint());
//		System.out.println(minimumDistance(arc1.getInitialPoint(), arc2));
		
		distances.add(minimumDistance(arc1.getFinalPoint(), arc2));
		pointsArc1.add(arc1.getFinalPoint());
		pointsArc2.add(nearestPoint(arc1.getFinalPoint(), arc2));
//		System.out.println("--------------");
//		System.out.println("From " + arc1.getFinalPoint() + " to arc  from " + arc2.getInitialPoint() + " to " + arc2.getFinalPoint());
//		System.out.println(minimumDistance(arc1.getFinalPoint(), arc2));
		
		distances.add(minimumDistance(arc2.getInitialPoint(), arc1));
		pointsArc1.add(nearestPoint(arc2.getInitialPoint(),arc1));
		pointsArc2.add(arc2.getInitialPoint());
//		System.out.println("--------------");
//		System.out.println("From " + arc2.getInitialPoint() + " to arc  from " + arc1.getInitialPoint() + " to " + arc1.getFinalPoint());
//		System.out.println(minimumDistance(arc2.getInitialPoint(), arc1));
		
		distances.add(minimumDistance(arc2.getFinalPoint(), arc1));
		pointsArc1.add(nearestPoint(arc2.getFinalPoint(),arc1));
		pointsArc2.add(arc2.getFinalPoint());
//		System.out.println("--------------");
//		System.out.println("From " + arc2.getFinalPoint() + " to arc  from " + arc1.getInitialPoint() + " to " + arc1.getFinalPoint());
//		System.out.println(minimumDistance(arc2.getFinalPoint(), arc1));

		int indexMinimum = chooseMinimumIndex(distances);
		System.out.println("Nearest point in Arc1 is " + pointsArc1.get(indexMinimum));
		System.out.println("Nearest point in Arc2 is " + pointsArc2.get(indexMinimum));
		return pointsArc2.get(chooseMinimumIndex(distances));
		
	}
	
	public static double minimumDistance(LimitedArc arc1, LimitedArc arc2)
	{
		return minimumDistance(nearestPoint(arc1,arc2),arc1);
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
	
	public static Point3d unitVector(Point3d p1, Point3d p2)
	{
		//System.out.println("Unit vector from " + p1 + " to " + p2 + " " + multiply(1/norm(minus(p2,p1)),minus(p2,p1)));
		return multiply(1/norm(minus(p2,p1)),minus(p2,p1));
	}
	
	public static LimitedArc roundVertex(Point3d p1, Point3d p2, Point3d p3, double radius)
	{
		double initialAngle = angle(minus(p3,p2));
		double finalAngle = angle(minus(p1,p2));
		
		if (initialAngle > finalAngle)
			initialAngle = initialAngle - 2*Math.PI;
		
		double teta = finalAngle-initialAngle;

		System.out.println("InitialAngle " + initialAngle*180/Math.PI);
		System.out.println("FinalAngle " + finalAngle*180/Math.PI);
		System.out.println("Teta " + teta*180/Math.PI);
		
		double alfa = Math.PI-teta;
		double centerAngle = initialAngle + teta/2;
		if (teta >= Math.PI)
		{
			alfa = teta-Math.PI;
			double alfaPrima = 2*Math.PI-teta;
			centerAngle = initialAngle - alfaPrima/2;
		}		
		System.out.println("Alpha " + alfa*180/Math.PI);
		
		double distanceToCenterFromP2 = Math.sqrt(1+Math.tan(alfa/2)*Math.tan(alfa/2))*radius;
		System.out.println("Angle to center point " + centerAngle*180/Math.PI);

		Point3d center = new Point3d(p2.getX()+distanceToCenterFromP2*Math.cos(centerAngle), p2.getY()+distanceToCenterFromP2*Math.sin(centerAngle),p2.getZ());
		
		Point3d initialPoint = plus(p2,multiply(Math.tan(alfa/2)*radius, unitVector(p2,p1)));
		
		LimitedArc arc = new LimitedArc();
		if (teta >= Math.PI)
			arc = new LimitedArc(center, initialPoint, -alfa, 1);
		else
			arc = new LimitedArc(center, initialPoint, alfa, 1);
		
		return arc;
	}
	
	public static ArrayList<LimitedElement> acabamentoPath (GeneralClosedPocketAdd addPocket)	
	{		
		ArrayList<LimitedElement> elements = addPocket.getElements();
		ArrayList<LimitedArc> arcElements = new ArrayList<LimitedArc>();
		ArrayList<LimitedElement> acabamentoElements = new ArrayList<LimitedElement>();
		
		for (LimitedElement e:elements)
		{
			if (e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				
				if (arc.getDeltaAngle()<0)
				{
					Point3d newInitialPoint = plus(arc.getCenter(),multiply(2*arc.getRadius(),unitVector(arc.getCenter(),arc.getInitialPoint())));
					System.out.println("Modifying");
					System.out.println("Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " delta " + arc.getDeltaAngle()*180/Math.PI + " radius " + arc.getRadius());
					System.out.println("To");
					arc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle(),1);					
				}
				arcElements.add(arc);
				System.out.println("Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " delta " + arc.getDeltaAngle()*180/Math.PI + " radius " + arc.getRadius());
			}
		}
		
		for (int i = 0; i < arcElements.size(); i++)
		{
			LimitedArc arc1 = new LimitedArc();
			LimitedArc arc2 = new LimitedArc();
			
			if(i!=arcElements.size()-1)
			{
				arc1 = arcElements.get(i);
				arc2 = arcElements.get(i+1);
			}
			else
			{
				arc1 = arcElements.get(i);
				arc2 = arcElements.get(0);				
			}
			
			if(arc1.getDeltaAngle()<0)
			{
				LimitedLine line = new LimitedLine();
				if(arc2.getDeltaAngle()<0)
				{
					line = new LimitedLine(arc1.getFinalPoint(), arc2.getInitialPoint());
				}				
				else
				{
					line = new LimitedLine(arc1.getFinalPoint(), arc2.getCenter());
				}
				acabamentoElements.add(arc1);
				acabamentoElements.add(line);
				
			}
			else
			{
				LimitedLine line = new LimitedLine();
				if(arc2.getDeltaAngle()<0)
				{
					line = new LimitedLine(arc1.getCenter(), arc2.getInitialPoint());
				}				
				else
				{
					line = new LimitedLine(arc1.getCenter(), arc2.getCenter());
				}
				acabamentoElements.add(line);
			}
		}
		
		return acabamentoElements;
	}
}
