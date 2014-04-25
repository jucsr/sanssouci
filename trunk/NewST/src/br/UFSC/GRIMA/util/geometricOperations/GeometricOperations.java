package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
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
		
		Point3d v = new Point3d(p1.getX() - p0.getX(), p1.getY()-p0.getY(), p1.getZ()-p0.getZ());
		Point3d w = new Point3d(p.getX() - p0.getX(), p.getY()-p0.getY(), p.getZ()-p0.getZ());
		
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
       Point3d Pb = new Point3d(p0.getX() + b*v.getX(), p0.getY() + b*v.getY(), p0.getZ() + b*v.getZ());
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
	
	public static double minimumDistanceLineToLine(LimitedLine S1, LimitedLine S2)
	{
	    Point3d   u = new Point3d(S1.getFinalPoint().getX() - S1.getInitialPoint().getX(), S1.getFinalPoint().getY() - S1.getInitialPoint().getY(), S1.getFinalPoint().getZ() - S1.getInitialPoint().getZ());
	    Point3d   v = new Point3d(S2.getFinalPoint().getX() - S2.getInitialPoint().getX(), S2.getFinalPoint().getY() - S2.getInitialPoint().getY(), S2.getFinalPoint().getZ() - S2.getInitialPoint().getZ());
	    Point3d   w = new Point3d(S1.getInitialPoint().getX() - S2.getInitialPoint().getX(), S1.getInitialPoint().getY() - S2.getInitialPoint().getY(), S1.getInitialPoint().getZ() - S2.getInitialPoint().getZ());
	    
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
	
	public static double minimumDistancePointToArc(Point3d p, LimitedArc arc)
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

	public static double minimumDistanceArcToLine(LimitedArc arc, LimitedLine line)
	{
		return minimumDistanceLineToArc(line, arc);
	}
	
	public static double minimumDistanceLineToArc(LimitedLine line, LimitedArc arc)
	{
		double distance=0.0;
		
		//System.out.println("Center of arc " + arc.getCenter());
		Point3d nearestFromLine = nearestPoint(arc.getCenter(), line);
		//System.out.println("Nearest calculated*****" + nearestFromLine);
		
		if(chooseMinimum(distance(arc.getCenter(), line.getInitialPoint()), distance(arc.getCenter(), line.getFinalPoint())) <= arc.getRadius())
		{
			//System.out.println("Line within the arc ");
			//System.out.println("Arc Initial to line" + minimumDistance(arc.getInitialPoint(), line));
			distance = chooseMinimum(minimumDistancePointToLine(arc.getInitialPoint(), line), minimumDistancePointToLine(arc.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(), arc), minimumDistancePointToArc(line.getFinalPoint(), arc));
			return distance;
		}
		
		Point3d v = new Point3d(nearestFromLine.getX()-arc.getCenter().getX(), nearestFromLine.getY()-arc.getCenter().getY(), nearestFromLine.getZ()-arc.getCenter().getZ());
		Point3d normalPoint = plus(arc.getCenter(),multiply(arc.getRadius()/norm(v),v));
		if (!contentsPoint(normalPoint, arc))
		{
			//System.out.println("normalPoint is not within the arc");
			return chooseMinimum(minimumDistancePointToLine(arc.getInitialPoint(),line), minimumDistancePointToLine(arc.getFinalPoint(), line), minimumDistancePointToArc(line.getInitialPoint(),arc), minimumDistancePointToArc(line.getFinalPoint(), arc));
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
	
	public static double minimumDistanceArcToArc(LimitedArc arc1, LimitedArc arc2)
	{
		return minimumDistancePointToArc(nearestPoint(arc1,arc2),arc1);
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
			arc = new LimitedArc(center, initialPoint, -alfa, 1);
		else
			arc = new LimitedArc(center, initialPoint, alfa, 1);
		
		return arc;
	}
	/**
	 * 
	 * @param addPocket
	 * @return -- o array de elementos do path de acabamento
	 */
	public static ArrayList<LimitedElement> acabamentoPath (GeneralClosedPocketVertexAdd addPocket, double radius)	
	{		
		ArrayList<LimitedElement> acabamentoElements = parallelPath(addPocket.getElements(), radius).get(0);
		
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
		System.out.println("Minimum distance " + distance);
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
		return distance;
	}
	
	public static ArrayList<ArrayList<LimitedElement>> parallelPath (ArrayList<LimitedElement> elements, double distance)
	{
		ArrayList<LimitedElement> parallel = new ArrayList<LimitedElement>();
		
		for (int i = 0; i < elements.size(); i++)
		{
			
			LimitedElement eCurrent;
			LimitedElement eBefore;
			LimitedElement eAfter;
			
			if(i==0)
			{
				eCurrent = elements.get(i);
				eBefore = elements.get(elements.size()-1);
				eAfter = elements.get(i+1);
			}
			else if(i==elements.size()-1)
			{
				eCurrent = elements.get(i);
				eBefore = elements.get(i-1);
				eAfter = elements.get(0);				
			}
			else
			{
				eCurrent = elements.get(i);
				eBefore = elements.get(i-1);
				eAfter = elements.get(i+1);				
			}
			
			if(eCurrent.isLimitedLine())
			{
				LimitedLine lineCurrent = (LimitedLine)eCurrent;
				System.out.println("*******************************");
				System.out.println("Current element is a line");
				
				if(eBefore.isLimitedLine()&&eAfter.isLimitedLine())
				{
					System.out.println("Line before and Line after");
					
					LimitedLine lineBefore = (LimitedLine)eBefore;
					LimitedLine lineAfter = (LimitedLine)eAfter;

					Point3d newInitialPoint = new Point3d();
					
					LimitedLine parallelBefore = absoluteParallel(lineBefore, distance);
					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
					LimitedLine parallelAfter = absoluteParallel(lineAfter, distance);
					
					newInitialPoint = intersect(parallelBefore, parallelCurrent);
					Point3d newFinalPoint = new Point3d();
					
					newFinalPoint = intersect(parallelAfter, parallelCurrent);
					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint);
					
					Point3d unitCurrent = unitVector(lineCurrent.getInitialPoint(), lineCurrent.getFinalPoint());
					Point3d unitNew = unitVector(newLine.getInitialPoint(), newLine.getFinalPoint());
					
					double test = escalar(unitNew, unitCurrent);
					if (norm(newLine)>0)
					{
						if (test>0)
							parallel.add(newLine);						
					}
				}
								
				else if(eBefore.isLimitedLine()&&eAfter.isLimitedArc())
				{
					System.out.println("Line before and Arc after");
					LimitedLine lineBefore = (LimitedLine)eBefore;
					LimitedArc arcAfter = (LimitedArc)eAfter;
					
					Point3d newInitialPoint = new Point3d();	
					Point3d newFinalPoint = new Point3d();	
					
					boolean validLine = false;
					
					LimitedLine parallelBefore = absoluteParallel(lineBefore, distance);
					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
					
					newInitialPoint = intersect(parallelBefore, parallelCurrent);
										
					if (arcAfter.getDeltaAngle() > 0)
					{
						if(distance >= arcAfter.getRadius())
						{
							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcAfter),arcAfter.getCenter());
							newFinalPoint = intersect(lineDiagonal, parallelCurrent);		
						}
						else
						{
							newFinalPoint = parallelCurrent.getFinalPoint();
						}
					}
					else
					{
						newFinalPoint = parallelCurrent.getFinalPoint();
					}
					
					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint); 
					if (norm(newLine)>0)
						parallel.add(newLine);
				}
				
				else if(eBefore.isLimitedArc()&&eAfter.isLimitedLine())
				{
					System.out.println("Arc before and line after");
					LimitedArc arcBefore = (LimitedArc)eBefore;
					LimitedLine lineAfter = (LimitedLine)eAfter;
					
					Point3d newInitialPoint = new Point3d();	
					Point3d newFinalPoint = new Point3d();
					
					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
					LimitedLine parallelAfter = absoluteParallel(lineAfter, distance);
					
					if (arcBefore.getDeltaAngle() >= 0)
					{
						if(distance < arcBefore.getRadius())
						{						
							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcBefore),arcBefore.getCenter());							
							newInitialPoint = intersect(lineDiagonal, parallelCurrent);		
						}
						else
						{
							newInitialPoint = parallelCurrent.getInitialPoint();
						}
					}
					else
					{
						newInitialPoint = parallelCurrent.getInitialPoint();
						System.out.println("Inside else, angle < 0 " + parallelCurrent.getInitialPoint());
					}
					
					newFinalPoint = intersect(parallelCurrent, parallelAfter);
					//System.out.println("Line Current from " + lineCurrent.getInitialPoint() + " to " + lineCurrent.getFinalPoint());
					System.out.println("New Line from " + newInitialPoint + " to " + newFinalPoint);
					
					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint);

					if (norm(newLine) > 0)
						parallel.add(newLine);
				}			
				
				else if(eBefore.isLimitedArc()&&eAfter.isLimitedArc())
				{
					LimitedArc arcBefore = (LimitedArc)eBefore;
					LimitedArc arcAfter = (LimitedArc)eAfter;
					
					Point3d newInitialPoint = new Point3d();	
					Point3d newFinalPoint = new Point3d();
					
					boolean validity=false;
					
					Point3d intersection = new Point3d();
					
					System.out.println("Arco - Linea - Arco");

					if(arcBefore.getDeltaAngle() > 0)						
					{
						System.out.println("Arc before");
						if(distance < arcBefore.getRadius())						
						{			
							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
							newInitialPoint = parallelCurrent.getInitialPoint();
						}
						else
						{
							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcBefore),arcBefore.getCenter());
							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
							System.out.println("Distance > Radius");
							newInitialPoint = intersect(lineDiagonal, parallelCurrent);		
						}
					}
					else
					{
						newInitialPoint = absoluteParallel(lineCurrent,distance).getInitialPoint();
					}
					
					if(arcAfter.getDeltaAngle() > 0)
					{
						System.out.println("Arc after");
						if(distance < arcAfter.getRadius())						
						{	
							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
							newFinalPoint = parallelCurrent.getFinalPoint();
						}
						else
						{
							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcAfter),arcAfter.getCenter());
							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
							System.out.println("Distance > Radius");
							newFinalPoint = intersect(lineDiagonal, parallelCurrent);
							System.out.println("Radius arc " + arcAfter.getRadius());
							System.out.println("Newfinalpoint for intersection " + newFinalPoint);
						}
					}
					else
					{
						System.out.println("Arc with angle > 180Âº");
						newFinalPoint = absoluteParallel(lineCurrent,distance).getFinalPoint();
					}
					System.out.println("New final point " + newFinalPoint);
					LimitedLine newParallelLine = new LimitedLine(newInitialPoint, newFinalPoint);
					System.out.println("Line from " + newParallelLine.getInitialPoint() + " to " + newParallelLine.getFinalPoint());
					if(norm(newParallelLine)>0)
						parallel.add(newParallelLine);
				}								
			}
			
			if(eCurrent.isLimitedArc())				
			{
				LimitedArc arcCurrent = (LimitedArc)eCurrent;
				if(eBefore.isLimitedLine() && eAfter.isLimitedLine())
				{
					LimitedLine lineBefore = (LimitedLine)eBefore;
					LimitedLine lineAfter = (LimitedLine)eAfter;

					Point3d newInitialPoint = new Point3d();
					if(arcCurrent.getDeltaAngle() < 0)
					{
						System.out.println("************************************************");
						Point3d vectorInitial = multiply(arcCurrent.getRadius() + distance, unitVector(arcCurrent.getCenter(),arcCurrent.getInitialPoint()));
						newInitialPoint = plus(arcCurrent.getCenter(),vectorInitial);
						LimitedArc arc = new LimitedArc(arcCurrent.getCenter(), newInitialPoint, arcCurrent.getDeltaAngle(),LimitedArc.CCW);
						System.out.println(arcCurrent.getInitialPoint() + " to " + arcCurrent.getFinalPoint());
						if (arc.getRadius()>0)
							parallel.add(arc);
					}					
					else
					{
						if (distance < arcCurrent.getRadius())
						{
							Point3d vectorInitial = multiply(arcCurrent.getRadius() - distance, unitVector(arcCurrent.getCenter(),arcCurrent.getInitialPoint()));
							newInitialPoint = plus(arcCurrent.getCenter(),vectorInitial);
							LimitedArc arc = new LimitedArc(arcCurrent.getCenter(), newInitialPoint, arcCurrent.getDeltaAngle(),LimitedArc.CCW);
							if (arc.getRadius()>0)
								parallel.add(arc);							
						}
					}
				}
			}
		}
		System.out.println("************************************************");
		System.out.println("Lista de elementos antes de retornar el parallel");
		for(LimitedElement e:parallel)
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				System.out.println("Arc From " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " Radius " + arc.getRadius());
			}
			else if(e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				System.out.println("Line From " + line.getInitialPoint() + " to " + line.getFinalPoint() + " Norm " + norm(line));
			}
		}
		System.out.println("************************************************");
		return validarPath(parallel);	
		//ArrayList<ArrayList<LimitedElement>> s = new ArrayList<ArrayList<LimitedElement>>();
				//s.add(parallel);
		//return s;	
	}
	
	public static ArrayList<ArrayList<LimitedElement>> validarPath(ArrayList<LimitedElement> elements)
	{
		//vetor de saída
		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
		//ArrayList<ArrayList<LimitedElement>> elements2 = new ArrayList<ArrayList<LimitedElement>>();
		//Array de elementos intermediarios
		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
		ArrayList<LimitedElement> elementsIntermediario2 = new ArrayList<LimitedElement>();
		//Array de intenrsecoes
		//ArrayList<Point3d> intersecoes = new ArrayList<Point3d>();
		
		elementsValidated.add(new ArrayList<LimitedElement>());
		for (int i=0; i < elements.size(); i++)
		{
			LimitedElement ei = elements.get(i);
			for (int j=i+1; j < (elements.size()-1); j++)
			{
				LimitedElement ej = elements.get(j);
				Point3d intersection = intersectionElements(ei,ej);
				if(intersection!=null)
				{
					//adiciona os pontos de interseção ao vetor de interseções
					intersecoes.add(intersection);
					System.out.println("Here is an intersection point " + intersection);
					if (ei.isLimitedLine())
					{
						LimitedLine linei = (LimitedLine)ei;
						LimitedLine lineBeforeIntersection = new LimitedLine(linei.getInitialPoint(), intersection);
						LimitedLine lineAfterIntersection = new LimitedLine(intersection, linei.getFinalPoint());
						//elementsIntermediario.add(lineBeforeIntersection);
						//elementsIntermediario.add(lineAfterIntersection);
						elementsValidated.get(0).add(lineBeforeIntersection);
						elementsValidated.get(0).add(lineAfterIntersection);
						//var1 = var1++;
					}
					else if(ei.isLimitedArc())
					{
						LimitedArc arci = (LimitedArc)ei;
						LimitedArc arcBeforeIntersection = new LimitedArc(arci.getInitialPoint(), intersection, arci.getCenter());
						LimitedArc arcAfterIntersection = new LimitedArc(intersection, arci.getFinalPoint(), arci.getCenter());
						//elementsIntermediario.add(arcBeforeIntersection);
						//elementsIntermediario.add(arcAfterIntersection);
						elementsValidated.get(0).add(arcBeforeIntersection);
						elementsValidated.get(0).add(arcAfterIntersection);
					}
				}
				else 
				{
					elementsValidated.get(0).add(ei);
				}
			}
		}
		System.out.println("Interseções: " + intersecoes.size());
		
//		Point3d intersection = intersecoes.get(0);
//		Point3d initialPoint;
//		
////		if(intersecao == elementsIntermediario.get(0).isLimitedLine())
////			pontoInicial = ((LimitedLine)elementsIntermediario.get(0)).getInitialPoint();
////		else if(elementsIntermediario.get(0).isLimitedArc())
////			pontoInicial = ((LimitedArc)elementsIntermediario.get(0)).getInitialPoint();
//		
//		for(int i = 0; i< elementsIntermediario.size();i++)
//		{
//			if(intersection == ((LimitedLine)elementsIntermediario.get(i)).getInitialPoint())
//				initialPoint = ((LimitedLine)elementsIntermediario.get(i)).getInitialPoint();
//					for(int j = i; j<elementsIntermediario.size()-1;j++)
//					{
//				
//					}
//			LimitedElement ei1 = elementsIntermediario.get(i);
//			}
//		}
//		
		System.out.println("Size:" + elements.size());
		System.out.println("Size Validated:" + elementsValidated.get(0).size());
		return elementsValidated;
	}
	
	public static ArrayList<Point3d> intersectionElements(ArrayList<LimitedElement> elements)
	{
		ArrayList<Point3d> intersecoes = new ArrayList<Point3d>();
		for (int i=0; i < elements.size(); i++)
		{
			LimitedElement ei = elements.get(i);
			for (int j=i+1; j < (elements.size()); j++)
			{
				LimitedElement ej = elements.get(j);
				Point3d intersection = intersectionElements(ei,ej);
				if(intersection!=null)
				{
					//adiciona os pontos de interseção ao vetor de interseções
					intersecoes.add(intersection);
					System.out.println("Here is an intersection point " + intersection);
				}
			}
		}
		return intersecoes;
	}
	
	public static Point3d intersectionElements(LimitedElement ei, LimitedElement ej)
	{
		Point3d intersection;
		//Se o elemento i for um arco
		if (ei.isLimitedArc())
		{
			LimitedArc arci = (LimitedArc)ei;
			//Se o elemento j for um arco (arco, arco)
			if(ej.isLimitedArc())
			{
				LimitedArc arcj = (LimitedArc)ej;
				intersection = intersectionPoint(arci,arcj);
				
				//Alternativa para não identificar pontos de interseção nas extremidades dos Limited Elements
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
				//o metodo intersectionPoint não está definido para (line, arc) mas sim pra (arc, line)	
				return intersectionPoint(arcj, linei);
			}
			//Se o elemento j for uma linha (linha, linha)
			else if ( ej.isLimitedLine())
			{
				LimitedLine linej = (LimitedLine)ej;
				if(isTheSamePoint(linei.getInitialPoint(),linej.getInitialPoint()) || 
				   isTheSamePoint(linei.getFinalPoint(),linej.getFinalPoint()) || 
				   isTheSamePoint(linei.getInitialPoint(),linej.getFinalPoint())|| 
				   isTheSamePoint(linei.getFinalPoint(),linej.getInitialPoint()))
				{
					return null;
				}
				return intersectionPoint(linei, linej);
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
		if(truncarDecimais(p1.x, 10) == truncarDecimais(p2.x,10) && truncarDecimais(p1.y, 10) == truncarDecimais(p2.y, 10) 
				&& truncarDecimais(p1.z, 10) == truncarDecimais(p2.z, 10)){
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
	
	public static LimitedLine absoluteParallel(LimitedLine line, double distance)
	{
		
		double angleLine = angle(minus(line.getFinalPoint(), line.getInitialPoint()));
		double newDistanceAngle = angleLine+Math.PI/2;
		double x = Math.cos(newDistanceAngle);
		double y = Math.sin(newDistanceAngle);
		Point3d unitDistance = new Point3d(x,y,line.getInitialPoint().getZ());
		Point3d distanceVector = multiply(distance, unitDistance);		

		Point3d newInitialPoint = plus(line.getInitialPoint(),distanceVector);
		Point3d newFinalPoint = plus(line.getFinalPoint(),distanceVector);
		System.out.println("Distance Vector " + distanceVector);
		System.out.println("New Initial Point " + newInitialPoint);
		System.out.println("New Final Point " + newFinalPoint);
		LimitedLine lineParallel = new LimitedLine(newInitialPoint, newFinalPoint);
		
		System.out.println("Parallel from " + lineParallel.getInitialPoint() + " to " + lineParallel.getFinalPoint());
		
		return lineParallel;
	}
	
	public static boolean belongs(LimitedLine line, Point3d p)
	{
		Point3d p1 = unitVector(line.getInitialPoint(),p);
		Point3d p2 = unitVector(line.getInitialPoint(),line.getFinalPoint());
		
		if (norm(minus(p1, p2))==0 && norm(p1) <= norm(p2))
		{
			return true;
		}
		else
		{
			return false;
		}
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
	
	public static LimitedArc parallelArc(LimitedArc arc, double distance)
	{
		LimitedArc newArc = new LimitedArc();		
		if (arc.getDeltaAngle()<0)
		{
			Point3d newInitialPoint = plus(arc.getCenter(),multiply((arc.getRadius()+distance),unitVector(arc.getCenter(),arc.getInitialPoint())));
			newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle(),1);					
		}
		else
		{
			if(arc.getRadius()>=distance)
			{
				Point3d newInitialPoint = plus(arc.getCenter(),multiply((arc.getRadius()-distance),unitVector(arc.getCenter(),arc.getInitialPoint())));
				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle(),1);
			}
			else
			{
				LimitedArc tempArc = new LimitedArc(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle()/2,1);
				Point3d unitToNewPoint = unitVector(tempArc.getFinalPoint(), arc.getCenter());
				Point3d newCenter = plus(tempArc.getFinalPoint(),multiply(distance,unitToNewPoint));
				newArc = new LimitedArc(newCenter, newCenter, arc.getDeltaAngle(),1);
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
	
	public static ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallelPath(ArrayList<LimitedElement> elements, double distance)
	{
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = new ArrayList<ArrayList<ArrayList<LimitedElement>>>();
		
		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath(elements, distance);
		int i = 0;
		while (i < 7)
		{
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println(i+1 + " th Parallel Path");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			multipleParallel.add(parallelPath);
			for (ArrayList<LimitedElement> path:parallelPath)
			{
				parallelPath = parallelPath(path,distance);
				showElements(path);
			}
			//showElements(parallelPath);
			i++;
		}		

		
		return multipleParallel;
	}

	public static void showElements(ArrayList<LimitedElement> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI  + " radius " + arc.getRadius()); 
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
			}			
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
			
			double Yinter = 0;
			Point3d intersection = null;
			//Garante que não calularemos uma tangente = infinity
			//Xinicial1 = Xfinal1 e Xinicial2 != Xfinal2
			if((pi1.getX() == pf1.getX()) && (pi2.getX() != pf2.getX()))
			{
				System.out.println("Xinicial1 = Xfinal1 e Xinicial2 != Xfinal2");
				//Caso o Xinicial < Xfinal
				if(pi2.getX() < pi2.getX())
				{
					System.out.println("Xinicial < Xfinal");
					//Verifica se Xi1 não está entre Xi2 e Xf2 (garantindo que não haja interseção)
					if(!((pi1.getX() >= pi2.getX())&& (pi1.getX() <= pf2.getX())))
					{
						return null;
					}	
				}
				//Caso o Xinicial > Xfinal
				if(pi2.getX() > pi2.getX())
				{
					System.out.println("Xinicial > Xfinal");
					//Verifica se Xi1 não está entre Xi2 e Xf2 (garantindo que não haja interseção)
					if(!((pi1.getX() >= pf2.getX())&& (pi1.getX() <= pi2.getX())))
					{
						return null;
					}	
				}
				//Caso haja interseção, ela é calculada
				b2 = (pf2.getY()-pi2.getY())/(pf2.getX()-pi2.getX());
				a2 = pi2.getY() - b2*pi2.getX();
				Yinter = a2 + b2*pi1.getX();
				intersection = new Point3d(pi1.getX(), Yinter,0);
				return intersection;
			}
			//Xinicial1 != Xfinal1 e Xinicial2 == Xfinal2
			else if((pi1.getX() != pf1.getX()) && (pi2.getX() == pf2.getX()))
			{
				//Caso o Xinicial < Xfinal
				System.out.println("Xinicial1 != Xfinal1 e Xinicial2 == Xfinal2");
				if(pi1.getX() < pf1.getX())
				{
					System.out.println("Xinicial < Xfinal");
					//Verifica se Xi2 não está entre Xi1 e Xf1 (garantindo que não haja interseção)
					if(!((pi2.getX() >= pi1.getX())&& (pi2.getX() <= pf1.getX())))
					{
						return null;
					}
				}
				//Caso o Xinicial > Xfinal
				else if(pi1.getX() > pf1.getX())
				{
					System.out.println("Xinicial > Xfinal");
					//Verifica se Xi2 não está entre Xi1 e Xf1 (garantindo que não haja interseção)
					if(!((pi2.getX() >= pf1.getX())&& (pi2.getX() <= pi1.getX())))
					{
						return null;
					}
				}
				//Caso haja interseção, ela é calculada
				b1 = (pf1.getY()-pi1.getY())/(pf1.getX()-pi1.getX());
				a1 = pi1.getY() - b1*pi1.getX();
				Yinter = a1 + b1*pi2.getX();
				intersection = new Point3d(pi2.getX(), Yinter,0);
				return intersection;
			}
			
			else if((pi1.getX() == pf1.getX()) && (pi2.getX() == pf2.getX()))
			{
				System.out.println("Xinicial1 == Xfinal1 e Xinicial2 == Xfinal2");
				return null;
			}
					
			//Xinicial1 != Xfinal1 e Xinicial2 != Xfinal2
			else if((pi1.getX() != pf1.getX()) && (pi2.getX() != pf2.getX()))
			{
				System.out.println("Xinicial1 != Xfinal1 e Xinicial2 != Xfinal2");
				b1 = (pf1.getY()-pi1.getY())/(pf1.getX()-pi1.getX());
				a1 = pi1.getY() - b1*pi1.getX();
				b2 = (pf2.getY()-pi2.getY())/(pf2.getX()-pi2.getX());
				a2 = pi2.getY() - b2*pi2.getX();
				
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
		
		Point3d vetorUnitario1 = unitVector(line1.getInitialPoint(), line1.getFinalPoint());
		Point3d vetorUnitario2 = unitVector(line2.getInitialPoint(), line2.getFinalPoint());
		double distance1 = line1.getInitialPoint().distance(intersect);
		double distance2 = line2.getInitialPoint().distance(intersect);
		
		double possivelIntersecaoX1 = line1.getInitialPoint().x + multiply(distance1, vetorUnitario1).x;
		double possivelIntersecaoY1 = line1.getInitialPoint().y + multiply(distance1, vetorUnitario1).y;
		double possivelIntersecaoX2 = line2.getInitialPoint().x + multiply(distance2, vetorUnitario2).x;
		double possivelIntersecaoY2 = line2.getInitialPoint().y + multiply(distance2, vetorUnitario2).y;
		
		double intersectXF = truncarDecimais(intersect.x, 10);
		double intersectYF = truncarDecimais(intersect.y, 10);
		double possivelIntersecaoXF1 = truncarDecimais(possivelIntersecaoX1, 10);
		double possivelIntersecaoYF1 = truncarDecimais(possivelIntersecaoY1, 10);
		double possivelIntersecaoXF2 = truncarDecimais(possivelIntersecaoX2, 10);
		double possivelIntersecaoYF2 = truncarDecimais(possivelIntersecaoY2, 10);
		
		System.out.println("intersection x = " + intersectXF);
		System.out.println("intersection y = " + intersectYF);
		System.out.println("poss intersection x = " + possivelIntersecaoXF1);
		System.out.println("poss intersection y = " + possivelIntersecaoYF1);
		if(intersectXF == possivelIntersecaoXF1 && intersectYF ==  possivelIntersecaoYF1 && intersectXF == possivelIntersecaoXF2 && intersectYF == possivelIntersecaoYF2)
		{
			return intersect;
		}
		else
		{
			return null;
		}
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

	public static Point3d intersectionPoint(LimitedArc arc, LimitedLine line)
	{
		Point3d intersection = null;
		/**
		 *  line --> y = m * x + b
		 *  circle --> (cx - p)^2 + (cy - q)^2 = r^2
		 */
		double x1,x2,y1,y2;
		double radical = 0;
		double r = Math.sqrt(Math.pow(arc.getInitialPoint().getX() - arc.getCenter().getX(), 2) + Math.pow(arc.getInitialPoint().getY() - arc.getCenter().getY(), 2));
		System.out.println("Raio: " + r);
		double xin = arc.getCenter().x - r;
		double xif = arc.getCenter().x + r;
		System.out.println("XcircIni: " + xin);
		System.out.println("XcircFin: " + xif);
		
		if (line.getInitialPoint().x == line.getFinalPoint().x) 
		{
			if (!((line.getInitialPoint().x >= xin) && (line.getInitialPoint().x <= xif))) 
			{
				System.out.println("S/Inter");
				return null;
			}
			else 
			{
				x1 = line.getInitialPoint().x;
				x2 = line.getInitialPoint().x;
				y1 = arc.getCenter().y + Math.sqrt(Math.pow(r, 2) - Math.pow(line.getInitialPoint().x - arc.getCenter().x, 2));
				y2 = arc.getCenter().y - Math.sqrt(Math.pow(r, 2) - Math.pow(line.getInitialPoint().x - arc.getCenter().x, 2));
			}
		}
		else
		{
			double m = (line.getFinalPoint().getY() - line.getInitialPoint().getY()) / (line.getFinalPoint().getX() - line.getInitialPoint().getX());
			double b = line.getInitialPoint().getY() - m * line.getInitialPoint().getX();
			/**
			 * caso nao haja ponto de intersecao
			 */
			radical = Math.pow(-2 * arc.getCenter().x + 2 * m * b - 2 * m * arc.getCenter().y, 2) - 4 * (1 + Math.pow(m, 2)) * (Math.pow(arc.getCenter().x, 2) + Math.pow(b, 2) - 2 * b * arc.getCenter().y + Math.pow(arc.getCenter().y, 2) - Math.pow(r, 2));
			//Garante que nao de NaN na raiz
			if(radical<0){
				return null;
			}
			x1 = (2 * arc.getCenter().x - 2 * m * b + 2 * m *  arc.getCenter().y + Math.sqrt(radical)) / (2 + 2 * Math.pow(m, 2));
			x2 = (2 * arc.getCenter().x - 2 * m * b + 2 * m *  arc.getCenter().y - Math.sqrt(radical)) / (2 + 2 * Math.pow(m, 2));
			y1 = m * x1 + b;
			y2 = m * x2 + b;
		}
		double x;
		double y;
		
		/**
		 *  caso a linha for vertical -- Se houver intersecao, o x da intersecao sera no x da linha
		 */
		
		double anguloInicial = Math.atan2(arc.getInitialPoint().y - arc.getCenter().y, arc.getInitialPoint().x - arc.getCenter().x);
		double anguloFinal = Math.atan2(arc.getFinalPoint().y - arc.getCenter().y, arc.getFinalPoint().x - arc.getCenter().x);
		double anguloNaIntersecao1 = Math.atan2(y1 - arc.getCenter().y, x1 - arc.getCenter().x);
		double anguloNaIntersecao2 = Math.atan2(y2 - arc.getCenter().y, x2 - arc.getCenter().x);
		
		
		
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
		
		//Normalização dos angulos inicial e final
		if(anguloInicial > anguloFinal)
		{
			anguloFinal = 2 * Math.PI + anguloFinal;
		}
		
		System.err.println("x1 = " + x1 + "\ty1 = " + y1);
		System.err.println("x2 = " + x2 + "\ty2 = " + y2);
		System.err.println("ARC center = " + arc.getCenter());
		System.err.println("anguloIntersecao1 (DEG) = " + (anguloNaIntersecao1 * 180 / Math.PI));
		System.err.println("anguloIntersecao1 (RAD) = " + (anguloNaIntersecao1));
		System.err.println("anguloIntersecao2 (DEG) = " + (anguloNaIntersecao2 * 180 / Math.PI));
		System.err.println("anguloIntersecao2 (RAD) = " + (anguloNaIntersecao2));
		System.err.println("anguloInicial (DEG) = " + (anguloInicial * 180 / Math.PI));
		System.err.println("anguloInicial (RAD) = " + anguloInicial);
		System.err.println("anguloFinal (DEG) = " + (anguloFinal * 180 / Math.PI));
		System.err.println("anguloFinal (RAD) = " + anguloFinal);
		System.err.println("Linha: " + line.getInitialPoint() + line.getFinalPoint());
		System.err.println("Arco: " + arc.getInitialPoint() + arc.getFinalPoint() + arc.getCenter());
		System.err.println("Radical: " + radical);
		
		//Modulo da linha de entrada
		double moduloR1 = Math.sqrt(Math.pow((line.getFinalPoint().x - line.getInitialPoint().x),2) + Math.pow((line.getFinalPoint().y - line.getInitialPoint().y), 2));
		//Vetor diretor da linha de entrada
		Point3d diretor1 = new Point3d(((line.getFinalPoint().x - line.getInitialPoint().x)/moduloR1),((line.getFinalPoint().y - line.getInitialPoint().y)/moduloR1),0);
		//Modulo da linha com ponto inicial da linha de entrada e ponto final = possivel ponto de intersecao
		double moduloR2;
		//Vetor da linha com ponto inicial da linha de entrada e ponto final = possivel ponto de intersecao
		Point3d diretor2;
		if((anguloInicial <= anguloNaIntersecao1 && anguloFinal >= anguloNaIntersecao1))
		{
			moduloR2 = Math.sqrt(Math.pow((x1 - line.getInitialPoint().x),2) + Math.pow((y1 - line.getInitialPoint().y), 2));
			diretor2 = new Point3d(((x1 - line.getInitialPoint().x)/moduloR1),((y1 - line.getInitialPoint().y)/moduloR1),0);
			//Validacao do possivel ponto de intersecao
			if (!((diretor1.getX() == (-1)*diretor2.getX()) && (diretor1.getY() == (-1)*diretor2.getY())))
			{
				if(moduloR1 >= moduloR2)
				{
					intersection = new Point3d(x1,y1,0);
				}
			}
		} 
		else if((anguloInicial <= anguloNaIntersecao2 && anguloFinal >= anguloNaIntersecao2))
		{
			moduloR2 = Math.sqrt(Math.pow((x2 - line.getInitialPoint().x),2) + Math.pow((y2 - line.getInitialPoint().y), 2));
			diretor2 = new Point3d(((x2 - line.getInitialPoint().x)/moduloR1),((y2 - line.getInitialPoint().y)/moduloR1),0);
			//Validacao do possivel ponto de intersecao
			if (!((diretor1.getX() == (-1)*diretor2.getX()) && (diretor1.getY() == (-1)*diretor2.getY())))
			{
				if(moduloR1 >= moduloR2)
				{
					intersection = new Point3d(x2,y2,0);
				}
			}
		}
		
		//intersection = new Point3d(x, y, 0);
		System.err.println("INTERSECTION POINT = " + intersection);

		return intersection;
	}
	public static Point3d intersectionPoint(LimitedArc arc1, LimitedArc arc2)
	{
		Point3d intersection = null;
		
		double x1, x2, y1 = 0, y2 = 0, r1 = 0, r2 = 0, cx1, cx2, cy1, cy2, x = 0, y = 0;
		
		cx1 = arc1.getCenter().x;
		cx2 = arc2.getCenter().x;
		cy1 = arc1.getCenter().y;
		cy2 = arc2.getCenter().y;
		r1 = Math.sqrt(Math.pow(arc1.getInitialPoint().x - arc1.getCenter().x, 2) + Math.pow(arc1.getInitialPoint().y - arc1.getCenter().y, 2));
		r2 = Math.sqrt(Math.pow(arc2.getInitialPoint().x - arc2.getCenter().x, 2) + Math.pow(arc2.getInitialPoint().y - arc2.getCenter().y, 2));
		
		System.out.println("R1 = " + r1);
		System.out.println("R2 = " + r2);
		
//		if(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1,2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1,2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) -Math.pow(r1,4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4) < 0 || 
//				- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4) < 0)
//		{
//			return null;
//		}
		
		x1 = (cx1*Math.pow(cy1, 2) - Math.pow(cx1, 2)*cx2 - cx1*Math.pow(cx2, 2) + cx1*Math.pow(cy2, 2) + cx2*Math.pow(cy1, 2) + cx2*Math.pow(cy2, 2) - cx1*Math.pow(r1, 2) + cx2*Math.pow(r1, 2) + cx1*Math.pow(r2, 2) - cx2*Math.pow(r2, 2) + Math.pow(cx1, 3) + Math.pow(cx2, 3) - cy1*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) + cy2*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1,2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1,2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) -Math.pow(r1,4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - 2*cx1*cy1*cy2 - 2*cx2*cy1*cy2)/(2*(Math.pow(cx1, 2) - 2*cx1*cx2 + Math.pow(cx2, 2) + Math.pow(cy1, 2) - 2*cy1*cy2 + Math.pow(cy2, 2)));
		x2 = (cx1*Math.pow(cy1, 2) - Math.pow(cx1, 2)*cx2 - cx1*Math.pow(cx2, 2) + cx1*Math.pow(cy2, 2) + cx2*Math.pow(cy1, 2) + cx2*Math.pow(cy2, 2) - cx1*Math.pow(r1, 2) + cx2*Math.pow(r1, 2) + cx1*Math.pow(r2, 2) - cx2*Math.pow(r2, 2) + Math.pow(cx1, 3) + Math.pow(cx2, 3) + cy1*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - cy2*Math.sqrt(- Math.pow(cx1, 4) + 4*Math.pow(cx1, 3)*cx2 - 6*Math.pow(cx1, 2)*Math.pow(cx2, 2) - 2*Math.pow(cx1, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx1, 2)*cy1*cy2 - 2*Math.pow(cx1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx1, 2)*Math.pow(r1, 2) + 2*Math.pow(cx1, 2)*Math.pow(r2, 2) + 4*cx1*Math.pow(cx2, 3) + 4*cx1*cx2*Math.pow(cy1, 2) - 8*cx1*cx2*cy1*cy2 + 4*cx1*cx2*Math.pow(cy2, 2) - 4*cx1*cx2*Math.pow(r1, 2) - 4*cx1*cx2*Math.pow(r2, 2) - Math.pow(cx2, 4) - 2*Math.pow(cx2, 2)*Math.pow(cy1, 2) + 4*Math.pow(cx2, 2)*cy1*cy2 - 2*Math.pow(cx2, 2)*Math.pow(cy2, 2) + 2*Math.pow(cx2, 2)*Math.pow(r1, 2) + 2*Math.pow(cx2, 2)*Math.pow(r2, 2) - Math.pow(cy1, 4) + 4*Math.pow(cy1, 3)*cy2 - 6*Math.pow(cy1, 2)*Math.pow(cy2, 2) + 2*Math.pow(cy1, 2)*Math.pow(r1, 2) + 2*Math.pow(cy1, 2)*Math.pow(r2, 2) + 4*cy1*Math.pow(cy2, 3) - 4*cy1*cy2*Math.pow(r1, 2) - 4*cy1*cy2*Math.pow(r2, 2) - Math.pow(cy2, 4) + 2*Math.pow(cy2, 2)*Math.pow(r1, 2) + 2*Math.pow(cy2, 2)*Math.pow(r2, 2) - Math.pow(r1, 4) + 2*Math.pow(r1, 2)*Math.pow(r2, 2) - Math.pow(r2, 4)) - 2*cx1*cy1*cy2 - 2*cx2*cy1*cy2)/(2*(Math.pow(cx1, 2) - 2*cx1*cx2 + Math.pow(cx2, 2) + Math.pow(cy1, 2) - 2*cy1*cy2 + Math.pow(cy2, 2)));
		
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
		
		ArrayList<Double> ypossiveis = new ArrayList<Double>();
		double [] ypossiveis1 = {y11, y12, y13, y14};
		double [] ypossiveis2 = {y21, y22, y23, y24};
		for(int i = 0; i < ypossiveis1.length; i++)
		{
			System.out.println("Ypossiveis1: " + ypossiveis1[i]);
			System.out.println("Ypossiveis2: " + ypossiveis2[i]);
			for(int j = 0; j < ypossiveis2.length; j++)
			{
				if(truncarDecimais(x1, 10) == truncarDecimais(x2, 10))
				{
					ypossiveis.add(ypossiveis1[0]);
					ypossiveis.add(ypossiveis1[1]);
					break;
				}
				if(truncarDecimais(ypossiveis1[i], 10) == truncarDecimais(ypossiveis2[j], 10))
				{
					if(i == 0 || i == 1)
					{
						ypossiveis.add(ypossiveis1[i]);
						
					} else if(i == 2 || i == 3)
					{
						ypossiveis.add(ypossiveis1[i]);
					}
					//ypossiveis1[i] = -1;
					break;
				}
			}
		}
		
		System.out.println("Array Ypossiveis: " + ypossiveis);
		y1 = ypossiveis.get(0);
		y2 = ypossiveis.get(1);
		Point3d possivel1 = new Point3d(x1, y1, 0);
		Point3d possivel2 = new Point3d(x2, y2, 0);
		
		System.err.println("poss 1 =" + possivel1);
		System.err.println("poss 2 =" + possivel2);
		
		double anguloInicial1 = Math.atan2(arc1.getInitialPoint().y - arc1.getCenter().y, arc1.getInitialPoint().x - arc1.getCenter().x);
		double anguloFinal1 = Math.atan2(arc1.getFinalPoint().y - arc1.getCenter().y, arc1.getFinalPoint().x - arc1.getCenter().x);
		
		double anguloInicial2 = Math.atan2(arc2.getInitialPoint().y - arc2.getCenter().y, arc2.getInitialPoint().x - arc2.getCenter().x);
		double anguloFinal2 = Math.atan2(arc2.getFinalPoint().y - arc2.getCenter().y, arc2.getFinalPoint().x - arc2.getCenter().x);
		
		double anguloNaIntersecaoCirculo1Possivel1 = Math.atan2(possivel1.y - arc1.getCenter().y, possivel1.x - arc1.getCenter().x);
		double anguloNaIntersecaoCirculo1Possivel2 = Math.atan2(possivel2.y - arc1.getCenter().y, possivel2.x - arc1.getCenter().x);
		
		double anguloNaIntersecaoCirculo2Possivel1 = Math.atan2(possivel1.y - arc2.getCenter().y, possivel1.x - arc2.getCenter().x);
		double anguloNaIntersecaoCirculo2Possivel2 = Math.atan2(possivel2.y - arc2.getCenter().y, possivel2.x - arc2.getCenter().x);
				
		/**
		 * correcao dos angulos negativos
		 */
		if(anguloInicial1 < 0)
		{
			anguloInicial1 = 2 * Math.PI + anguloInicial1;
		}
		if(anguloFinal1 <= 0)
		{
			anguloFinal1 = 2 * Math.PI + anguloFinal1;
		}
		if(anguloInicial2 < 0)
		{
			anguloInicial2 = 2 * Math.PI + anguloInicial2;
		}
		if(anguloFinal2 < 0)
		{
			anguloFinal2 = 2 * Math.PI + anguloFinal2;
		}
		if(anguloNaIntersecaoCirculo1Possivel1 < 0)
		{
			anguloNaIntersecaoCirculo1Possivel1 = 2 * Math.PI + anguloNaIntersecaoCirculo1Possivel1;
		}
		if(anguloNaIntersecaoCirculo1Possivel2 < 0)
		{
			anguloNaIntersecaoCirculo1Possivel2 = 2 * Math.PI + anguloNaIntersecaoCirculo1Possivel2;
		}
		if(anguloNaIntersecaoCirculo2Possivel1 < 0)
		{
			anguloNaIntersecaoCirculo2Possivel1 = 2 * Math.PI + anguloNaIntersecaoCirculo2Possivel1;
		}
		if(anguloNaIntersecaoCirculo2Possivel2 < 0)
		{
			anguloNaIntersecaoCirculo2Possivel2 = 2 * Math.PI + anguloNaIntersecaoCirculo2Possivel2;
		}
		
		if(anguloInicial1 > anguloFinal1)
		{
			anguloFinal1 = 2 * Math.PI + anguloFinal1;
		}
		if(anguloInicial2 > anguloFinal2)
		{
			anguloFinal2 = 2 * Math.PI + anguloFinal2;
		}
		
		System.out.println("AI1 = " + (anguloInicial1 * 180 / Math.PI));
		System.out.println("AF1 = " + (anguloFinal1 * 180 / Math.PI));
		System.out.println("AI2 = " + (anguloInicial2 * 180 / Math.PI));
		System.out.println("AF2 = " + (anguloFinal2 * 180 / Math.PI));
		System.out.println("anguloNaIntersecaoCirculo1Possivel1 = " + (anguloNaIntersecaoCirculo1Possivel1 * 180 / Math.PI));
		System.out.println("anguloNaIntersecaoCirculo1Possivel2 = " + (anguloNaIntersecaoCirculo1Possivel2 * 180 / Math.PI));
		System.out.println("anguloNaIntersecaoCirculo2Possivel1 = " + (anguloNaIntersecaoCirculo2Possivel1 * 180 / Math.PI));
		System.out.println("anguloNaIntersecaoCirculo2Possivel2 = " + (anguloNaIntersecaoCirculo2Possivel2 * 180 / Math.PI));
		//System.out.println("")
		
//		if(((anguloNaIntersecaoCirculo1Possivel1 <= anguloFinal1 && anguloNaIntersecaoCirculo1Possivel1 >= anguloInicial1) || (anguloNaIntersecaoCirculo1Possivel1 + 2 * Math.PI <= anguloFinal1 && anguloNaIntersecaoCirculo1Possivel1 + 2 * Math.PI >= anguloInicial1)) &&
//				((anguloNaIntersecaoCirculo2Possivel1 >= anguloFinal2 && anguloNaIntersecaoCirculo2Possivel1 <= anguloInicial2) ||(anguloNaIntersecaoCirculo2Possivel1 + 2 * Math.PI >= anguloFinal2 && anguloNaIntersecaoCirculo2Possivel1 + 2 * Math.PI <= anguloInicial2)))
//		{
//			intersection = possivel1;
//			System.out.println("X1");
//		} else if(((anguloNaIntersecaoCirculo1Possivel2 <= anguloFinal1 && anguloNaIntersecaoCirculo1Possivel2 >= anguloInicial1) || (anguloNaIntersecaoCirculo1Possivel2 + 2 * Math.PI <= anguloFinal1 && anguloNaIntersecaoCirculo1Possivel2 + 2 * Math.PI >= anguloInicial1)) &&
//				(anguloNaIntersecaoCirculo2Possivel2 >= anguloFinal2 && anguloNaIntersecaoCirculo2Possivel2 <=anguloInicial2) || (anguloNaIntersecaoCirculo2Possivel2 + 2 * Math.PI >= anguloFinal2 && anguloNaIntersecaoCirculo2Possivel2 + 2 * Math.PI <=anguloInicial2))
//		{
//			intersection = possivel2;
//			System.out.println("X2");
//		}
//		
		if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel1) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel1))
				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel1) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel1)))
		{
			intersection = possivel1;
			System.out.println("X1");
		}
		else if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel2) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel2))
				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel2) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel2)))
		{
			intersection = possivel2;
			System.out.println("X2");
		}
		
		return intersection;
	}
}
