package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
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
		ArrayList<LimitedElement> acabamentoElements = parallelPath1(addPocket.getElements(), radius).get(0);
		
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
		}
		
		return roundNumber(minimumDistance,10);
	}
	
//	public 
//
	public static ArrayList<ArrayList<LimitedElement>> parallelPath1 (ArrayList<LimitedElement> elements, double distance)
	{
		ArrayList<ArrayList<LimitedElement>> saida = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> lacoTmp = new ArrayList<LimitedElement>();
		for (int i = 0; i < elements.size(); i++)
		{
			if(elements.get(i).isLimitedLine())
			{
				LimitedLine lineTmp = (LimitedLine)elements.get(i);
				LimitedLine newLine = absoluteParallel(lineTmp, distance);
				lacoTmp.add(newLine);
				//System.err.println("linha " + i);
				
			} else if(elements.get(i).isLimitedArc())
			{
				LimitedArc arcTmp = (LimitedArc)elements.get(i);
				LimitedArc newArc = parallelArc(arcTmp, distance);
				lacoTmp.add(newArc);
				System.out.println("Center: " + newArc.getCenter());
				//System.err.println("arco " + i);
			}
		}
		
		saida = validarPath(lacoTmp, elements, distance);
		
//		saida.add(lacoTmp);
		
		return saida;
	}
//	public static ArrayList<ArrayList<LimitedElement>> parallelPath (ArrayList<LimitedElement> elements, double distance)
//	{
//		ArrayList<LimitedElement> parallel = new ArrayList<LimitedElement>();
//		
//		for (int i = 0; i < elements.size(); i++)
//		{
//			
//			LimitedElement eCurrent;
//			LimitedElement eBefore;
//			LimitedElement eAfter;
//			
//			if(i==0)
//			{
//				eCurrent = elements.get(i);
//				eBefore = elements.get(elements.size()-1);
//				eAfter = elements.get(i+1);
//			}
//			else if(i==elements.size()-1)
//			{
//				eCurrent = elements.get(i);
//				eBefore = elements.get(i-1);
//				eAfter = elements.get(0);				
//			}
//			else
//			{
//				eCurrent = elements.get(i);
//				eBefore = elements.get(i-1);
//				eAfter = elements.get(i+1);				
//			}
//			
//			if(eCurrent.isLimitedLine())
//			{
//				LimitedLine lineCurrent = (LimitedLine)eCurrent;
//				System.out.println("*******************************");
//				System.out.println("Current element is a line");
//				
//				if(eBefore.isLimitedLine()&&eAfter.isLimitedLine())
//				{
//					System.out.println("Line before and Line after");
//					
//					LimitedLine lineBefore = (LimitedLine)eBefore;
//					LimitedLine lineAfter = (LimitedLine)eAfter;
//
//					Point3d newInitialPoint = new Point3d();
//					
//					LimitedLine parallelBefore = absoluteParallel(lineBefore, distance);
//					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//					LimitedLine parallelAfter = absoluteParallel(lineAfter, distance);
//					
//					newInitialPoint = intersect(parallelBefore, parallelCurrent);
//					Point3d newFinalPoint = new Point3d();
//					
//					newFinalPoint = intersect(parallelAfter, parallelCurrent);
//					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint);
//					
//					Point3d unitCurrent = unitVector(lineCurrent.getInitialPoint(), lineCurrent.getFinalPoint());
//					Point3d unitNew = unitVector(newLine.getInitialPoint(), newLine.getFinalPoint());
//					
//					double test = escalar(unitNew, unitCurrent);
//					if (norm(newLine)>0)
//					{
//						if (test>0)
//							parallel.add(newLine);						
//					}
//				}
//								
//				else if(eBefore.isLimitedLine()&&eAfter.isLimitedArc())
//				{
//					System.out.println("Line before and Arc after");
//					LimitedLine lineBefore = (LimitedLine)eBefore;
//					LimitedArc arcAfter = (LimitedArc)eAfter;
//					
//					Point3d newInitialPoint = new Point3d();	
//					Point3d newFinalPoint = new Point3d();	
//					
//					boolean validLine = false;
//					
//					LimitedLine parallelBefore = absoluteParallel(lineBefore, distance);
//					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//					
//					newInitialPoint = intersect(parallelBefore, parallelCurrent);
//										
//					if (arcAfter.getDeltaAngle() > 0)
//					{
//						if(distance >= arcAfter.getRadius())
//						{
//							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcAfter),arcAfter.getCenter());
//							newFinalPoint = intersect(lineDiagonal, parallelCurrent);		
//						}
//						else
//						{
//							newFinalPoint = parallelCurrent.getFinalPoint();
//						}
//					}
//					else
//					{
//						newFinalPoint = parallelCurrent.getFinalPoint();
//					}
//					
//					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint); 
//					if (norm(newLine)>0)
//						parallel.add(newLine);
//				}
//				
//				else if(eBefore.isLimitedArc()&&eAfter.isLimitedLine())
//				{
//					System.out.println("Arc before and line after");
//					LimitedArc arcBefore = (LimitedArc)eBefore;
//					LimitedLine lineAfter = (LimitedLine)eAfter;
//					
//					Point3d newInitialPoint = new Point3d();	
//					Point3d newFinalPoint = new Point3d();
//					
//					LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//					LimitedLine parallelAfter = absoluteParallel(lineAfter, distance);
//					
//					if (arcBefore.getDeltaAngle() >= 0)
//					{
//						if(distance < arcBefore.getRadius())
//						{						
//							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcBefore),arcBefore.getCenter());							
//							newInitialPoint = intersect(lineDiagonal, parallelCurrent);		
//						}
//						else
//						{
//							newInitialPoint = parallelCurrent.getInitialPoint();
//						}
//					}
//					else
//					{
//						newInitialPoint = parallelCurrent.getInitialPoint();
//						System.out.println("Inside else, angle < 0 " + parallelCurrent.getInitialPoint());
//					}
//					
//					newFinalPoint = intersect(parallelCurrent, parallelAfter);
//					//System.out.println("Line Current from " + lineCurrent.getInitialPoint() + " to " + lineCurrent.getFinalPoint());
//					System.out.println("New Line from " + newInitialPoint + " to " + newFinalPoint);
//					
//					LimitedLine newLine = new LimitedLine(newInitialPoint, newFinalPoint);
//
//					if (norm(newLine) > 0)
//						parallel.add(newLine);
//				}			
//				
//				else if(eBefore.isLimitedArc()&&eAfter.isLimitedArc())
//				{
//					LimitedArc arcBefore = (LimitedArc)eBefore;
//					LimitedArc arcAfter = (LimitedArc)eAfter;
//					
//					Point3d newInitialPoint = new Point3d();	
//					Point3d newFinalPoint = new Point3d();
//					
//					boolean validity=false;
//					
//					Point3d intersection = new Point3d();
//					
//					System.out.println("Arco - Linea - Arco");
//
//					if(arcBefore.getDeltaAngle() > 0)						
//					{
//						System.out.println("Arc before");
//						if(distance < arcBefore.getRadius())						
//						{			
//							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//							newInitialPoint = parallelCurrent.getInitialPoint();
//						}
//						else
//						{
//							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcBefore),arcBefore.getCenter());
//							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//							System.out.println("Distance > Radius");
//							newInitialPoint = intersect(lineDiagonal, parallelCurrent);		
//						}
//					}
//					else
//					{
//						newInitialPoint = absoluteParallel(lineCurrent,distance).getInitialPoint();
//					}
//					
//					if(arcAfter.getDeltaAngle() > 0)
//					{
//						System.out.println("Arc after");
//						if(distance < arcAfter.getRadius())						
//						{	
//							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//							newFinalPoint = parallelCurrent.getFinalPoint();
//						}
//						else
//						{
//							LimitedLine lineDiagonal = new LimitedLine(middlePoint(arcAfter),arcAfter.getCenter());
//							LimitedLine parallelCurrent = absoluteParallel(lineCurrent, distance);
//							System.out.println("Distance > Radius");
//							newFinalPoint = intersect(lineDiagonal, parallelCurrent);
//							System.out.println("Radius arc " + arcAfter.getRadius());
//							System.out.println("Newfinalpoint for intersection " + newFinalPoint);
//						}
//					}
//					else
//					{
//						System.out.println("Arc with angle > 180º");
//						newFinalPoint = absoluteParallel(lineCurrent,distance).getFinalPoint();
//					}
//					System.out.println("New final point " + newFinalPoint);
//					LimitedLine newParallelLine = new LimitedLine(newInitialPoint, newFinalPoint);
//					System.out.println("Line from " + newParallelLine.getInitialPoint() + " to " + newParallelLine.getFinalPoint());
//					if(norm(newParallelLine)>0)
//						parallel.add(newParallelLine);
//				}								
//			}
//			
//			if(eCurrent.isLimitedArc())				
//			{
//				LimitedArc arcCurrent = (LimitedArc)eCurrent;
//				if(eBefore.isLimitedLine() && eAfter.isLimitedLine())
//				{
//					LimitedLine lineBefore = (LimitedLine)eBefore;
//					LimitedLine lineAfter = (LimitedLine)eAfter;
//
//					Point3d newInitialPoint = new Point3d();
//					if(arcCurrent.getDeltaAngle() < 0)
//					{
//						System.out.println("************************************************");
//						Point3d vectorInitial = multiply(arcCurrent.getRadius() + distance, unitVector(arcCurrent.getCenter(),arcCurrent.getInitialPoint()));
//						newInitialPoint = plus(arcCurrent.getCenter(),vectorInitial);
//						LimitedArc arc = new LimitedArc(arcCurrent.getCenter(), newInitialPoint, arcCurrent.getDeltaAngle(),LimitedArc.CCW);
//						System.out.println(arcCurrent.getInitialPoint() + " to " + arcCurrent.getFinalPoint());
//						if (arc.getRadius()>0)
//							parallel.add(arc);
//					}					
//					else
//					{
//						if (distance < arcCurrent.getRadius())
//						{
//							Point3d vectorInitial = multiply(arcCurrent.getRadius() - distance, unitVector(arcCurrent.getCenter(),arcCurrent.getInitialPoint()));
//							newInitialPoint = plus(arcCurrent.getCenter(),vectorInitial);
//							LimitedArc arc = new LimitedArc(arcCurrent.getCenter(), newInitialPoint, arcCurrent.getDeltaAngle(),LimitedArc.CCW);
//							if (arc.getRadius()>0)
//								parallel.add(arc);							
//						}
//					}
//				}
//			}
//		}
//		System.out.println("************************************************");
//		System.out.println("Lista de elementos antes de retornar el parallel");
//		for(LimitedElement e:parallel)
//		{
//			if(e.isLimitedArc())
//			{
//				LimitedArc arc = (LimitedArc)e;
//				System.out.println("Arc From " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " Radius " + arc.getRadius());
//			}
//			else if(e.isLimitedLine())
//			{
//				LimitedLine line = (LimitedLine)e;
//				System.out.println("Line From " + line.getInitialPoint() + " to " + line.getFinalPoint() + " Norm " + norm(line));
//			}
//		}
//		System.out.println("************************************************");
//		return validarPath(parallel,elements,distance);	
////		ArrayList<ArrayList<LimitedElement>> s = new ArrayList<ArrayList<LimitedElement>>();
////				s.add(parallel);
////		return s;	
//	}
	
	public static ArrayList<ArrayList<LimitedElement>> validarPath(ArrayList<LimitedElement> elements, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		//vetor de saida
		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
		//Array de elementos intermediarios
		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
		ArrayList<LimitedElement> elementsIntermediario2 = new ArrayList<LimitedElement>();
		//elementsValidated.add(new ArrayList<LimitedElement>());
		//showElements(elements);
		
		/*
		 * 	Valida��o 1: Quebra dos Elementos na intersecao
		 */
		for (int i=0; i < elements.size(); i++)
		{
			ArrayList<Point3d> intersection = null;
			boolean thereIsIntersection = false;
			LimitedElement ei = elements.get(i);
			int numeroDeIntersecao = 0;
			for (int j = 0; j < elements.size(); j++)
			{
				LimitedElement ej = elements.get(j);
				intersection = null;
				if(!ei.equals(ej))
				{
					intersection = intersectionElements(ei, ej);
					if (intersection != null)
					{
						int intSize = intersection.size();
						System.out.println("intersection: " + intersection);
						for(int k = 0;k < intSize;k++)
						{
							if(!(alreadyUsed(intersection.get(k),intersecoes)))
							{
								intersecoes.add(intersection.get(k));
							}
						}
						thereIsIntersection = true;
						if(numeroDeIntersecao == 0)
						{
							//adiciona os pontos de intersecao ao vetor de intersecoes
							if (ei.isLimitedLine())
							{
								LimitedLine linei = (LimitedLine)ei;
								if(intSize == 1)
								{
									LimitedLine lineBeforeIntersection = new LimitedLine(linei.getInitialPoint(), intersection.get(0));
									LimitedLine lineAfterIntersection = new LimitedLine(intersection.get(0), linei.getFinalPoint());
									elementsIntermediario.add(lineBeforeIntersection);
									elementsIntermediario.add(lineAfterIntersection);
								}
								else if(intSize == 2)
								{
									ArrayList<Point3d> intersectionTemp = intersection;
									if(distance(linei.getInitialPoint(), intersection.get(0)) > distance(linei.getInitialPoint(), intersection.get(1)))
									{
										intersectionTemp.set(0, intersection.get(1));
										intersectionTemp.set(1, intersection.get(0));
									}
									LimitedLine lineBeforeIntersection = new LimitedLine(linei.getInitialPoint(), intersectionTemp.get(0));
									LimitedLine lineBetweenIntersection = new LimitedLine(intersectionTemp.get(0),intersectionTemp.get(1));
									LimitedLine lineAfterIntersection = new LimitedLine(intersectionTemp.get(0), linei.getFinalPoint());
									elementsIntermediario.add(lineBeforeIntersection);
									elementsIntermediario.add(lineBetweenIntersection);
									elementsIntermediario.add(lineAfterIntersection);
								}
							}
							else if(ei.isLimitedArc())
							{
								LimitedArc arci = (LimitedArc)ei;
								if(intSize == 1)
								{
									LimitedArc arcBeforeIntersection = new LimitedArc(arci.getInitialPoint(), intersection.get(0), arci.getCenter());
									LimitedArc arcAfterIntersection = new LimitedArc(intersection.get(0), arci.getFinalPoint(), arci.getCenter());
									elementsIntermediario.add(arcBeforeIntersection);
									elementsIntermediario.add(arcAfterIntersection);
								}
								else if(intSize == 2)
								{
									ArrayList<Point3d> intersectionTemp = intersection;
									if(distance(arci.getInitialPoint(), intersection.get(0)) > distance(arci.getInitialPoint(), intersection.get(1)))
									{
										intersectionTemp.set(0, intersection.get(1));
										intersectionTemp.set(1, intersection.get(0));
									}
									LimitedArc arcBeforeIntersection = new LimitedArc(arci.getInitialPoint(), intersection.get(0), arci.getCenter());
									LimitedArc arcBetweenIntersection = new LimitedArc(intersectionTemp.get(0),intersectionTemp.get(1),arci.getCenter());
									LimitedArc arcAfterIntersection = new LimitedArc(intersection.get(0), arci.getFinalPoint(), arci.getCenter());
									elementsIntermediario.add(arcBeforeIntersection);
									elementsIntermediario.add(arcBetweenIntersection);
									elementsIntermediario.add(arcAfterIntersection);
								}
							}
							numeroDeIntersecao++;
						}
						else
						{
							int indice2 = elementsIntermediario.size() - 2;
							int indice3 = elementsIntermediario.size() - 1;
							if((elementsIntermediario.get(indice3).isLimitedLine()))
							{
								LimitedLine aux2 = (LimitedLine)elementsIntermediario.get(indice2);
								ArrayList<Point3d> intAux2 = intersectionElements(aux2, ej);
								LimitedLine aux3 = (LimitedLine)elementsIntermediario.get(indice3);
								ArrayList<Point3d> intAux3 = intersectionElements(aux3, ej);
								if(intSize == 2)
								{
									int indice1 = elementsIntermediario.size() - 3;
									LimitedLine aux1 = (LimitedLine)elementsIntermediario.get(indice1);
									ArrayList<Point3d> intAux1 = intersectionElements(aux1, ej);
									if(intAux1 != null)
									{
										if(intSize == 1)
										{
											LimitedLine lineBeforeIntersection = new LimitedLine(aux1.getInitialPoint(), intAux1.get(0));
											LimitedLine lineAfterIntersection = new LimitedLine(intAux1.get(0), aux1.getFinalPoint());
											elementsIntermediario.add(lineBeforeIntersection);
											elementsIntermediario.add(lineAfterIntersection);
											elementsIntermediario.remove(indice1);
										}
										else if(intSize == 2)
										{
											ArrayList<Point3d> intersectionTemp = intAux1;
											if(distance(aux2.getInitialPoint(), intAux1.get(0)) > distance(aux2.getInitialPoint(), intAux1.get(1)))
											{
												intersectionTemp.set(0, intAux1.get(1));
												intersectionTemp.set(1, intAux1.get(0));
											}
											LimitedLine lineBeforeIntersection = new LimitedLine(aux2.getInitialPoint(), intersectionTemp.get(0));
											LimitedLine lineBetweenIntersection = new LimitedLine(intersectionTemp.get(0), intersectionTemp.get(1));
											LimitedLine lineAfterIntersection = new LimitedLine(intersectionTemp.get(1), aux2.getFinalPoint());
											elementsIntermediario.add(lineBeforeIntersection);
											elementsIntermediario.add(lineBetweenIntersection);
											elementsIntermediario.add(lineAfterIntersection);
											elementsIntermediario.remove(indice1);
										}
									}
								}
								if(intAux2 != null)
								{
									//intersection = intersectionElements(aux2,ej);
									if(intSize == 1)
									{
										LimitedLine lineBeforeIntersection = new LimitedLine(aux2.getInitialPoint(), intAux2.get(0));
										LimitedLine lineAfterIntersection = new LimitedLine(intAux2.get(0), aux2.getFinalPoint());
										elementsIntermediario.add(lineBeforeIntersection);
										elementsIntermediario.add(lineAfterIntersection);
										elementsIntermediario.remove(indice2);
									}
									else if(intSize == 2)
									{
										ArrayList<Point3d> intersectionTemp = intAux2;
										if(distance(aux2.getInitialPoint(), intAux2.get(0)) > distance(aux2.getInitialPoint(), intAux2.get(1)))
										{
											intersectionTemp.set(0, intAux2.get(1));
											intersectionTemp.set(1, intAux2.get(0));
										}
										LimitedLine lineBeforeIntersection = new LimitedLine(aux2.getInitialPoint(), intersectionTemp.get(0));
										LimitedLine arcBetweenIntersection = new LimitedLine(intersectionTemp.get(0), intersectionTemp.get(1));
										LimitedLine lineAfterIntersection = new LimitedLine(intersectionTemp.get(1), aux2.getFinalPoint());
										elementsIntermediario.add(lineBeforeIntersection);
										elementsIntermediario.add(arcBetweenIntersection);
										elementsIntermediario.add(lineAfterIntersection);
										elementsIntermediario.remove(indice2);
									}
								}
								if (intAux3 != null)
								{
									if(intSize == 1)
									{
										//intersection = intersectionElements(aux3,ej);
										LimitedLine lineBeforeIntersection = new LimitedLine(aux3.getInitialPoint(), intAux3.get(0));
										LimitedLine lineAfterIntersection = new LimitedLine(intAux3.get(0), aux3.getFinalPoint());
										elementsIntermediario.add(lineBeforeIntersection);
										elementsIntermediario.add(lineAfterIntersection);
										elementsIntermediario.remove(indice3);
									}
									else if(intSize == 2)
									{
										ArrayList<Point3d> intersectionTemp = intAux3;
										if(distance(aux3.getInitialPoint(), intAux3.get(0)) > distance(aux3.getInitialPoint(), intAux3.get(1)))
										{
											intersectionTemp.set(0, intAux3.get(1));
											intersectionTemp.set(1, intAux3.get(0));
										}
										LimitedLine lineBeforeIntersection = new LimitedLine(aux3.getInitialPoint(), intersectionTemp.get(0));
										LimitedLine arcBetweenIntersection = new LimitedLine(intersectionTemp.get(0), intersectionTemp.get(1));
										LimitedLine lineAfterIntersection = new LimitedLine(intersectionTemp.get(1), aux3.getFinalPoint());
										elementsIntermediario.add(lineBeforeIntersection);
										elementsIntermediario.add(arcBetweenIntersection);
										elementsIntermediario.add(lineAfterIntersection);
										elementsIntermediario.remove(indice3);
									}
								}
							}
							else if ((elementsIntermediario.get(indice3).isLimitedArc()))
							{
								LimitedArc aux2 = (LimitedArc) elementsIntermediario.get(indice2);
								ArrayList<Point3d> intAux2 = intersectionElements(aux2, ej);
								LimitedArc aux3 = (LimitedArc) elementsIntermediario.get(indice3);
								ArrayList<Point3d> intAux3 = intersectionElements(aux3, ej);
								Point3d centerEi = ((LimitedArc)ei).getCenter();
								if(intSize == 2)
								{
									int indice1 = elementsIntermediario.size() - 3;
									LimitedArc aux1 = (LimitedArc)elementsIntermediario.get(indice1);
									ArrayList<Point3d> intAux1 = intersectionElements(aux1, ej);
									if(intAux1 != null)
									{
										if(intSize == 1)
										{
											LimitedArc arcBeforeIntersection = new LimitedArc(aux1.getInitialPoint(), intAux1.get(0),centerEi);
											LimitedArc arcAfterIntersection = new LimitedArc(intAux1.get(0), aux1.getFinalPoint(),centerEi);
											elementsIntermediario.add(arcBeforeIntersection);
											elementsIntermediario.add(arcAfterIntersection);
											elementsIntermediario.remove(indice1);
										}
										else if(intSize == 2)
										{
											ArrayList<Point3d> intersectionTemp = intAux1;
											if(distance(aux2.getInitialPoint(), intAux1.get(0)) > distance(aux2.getInitialPoint(), intAux1.get(1)))
											{
												intersectionTemp.set(0, intAux1.get(1));
												intersectionTemp.set(1, intAux1.get(0));
											}
											LimitedArc arcBeforeIntersection = new LimitedArc(aux1.getInitialPoint(), intersectionTemp.get(0),centerEi);
											LimitedArc arcBetweenIntersection = new LimitedArc(intersectionTemp.get(0), intersectionTemp.get(1),centerEi);
											LimitedArc arcAfterIntersection = new LimitedArc(intersectionTemp.get(1), aux1.getFinalPoint(),centerEi);
											elementsIntermediario.add(arcBeforeIntersection);
											elementsIntermediario.add(arcBetweenIntersection);
											elementsIntermediario.add(arcAfterIntersection);
											elementsIntermediario.remove(indice1);
									}
								}
							}
								if (intAux2 != null)
								{
									//intersection = intersectionElements(aux2,ej);
									if(intSize == 1)
									{
										LimitedArc arcBeforeIntersection = new LimitedArc(aux2.getInitialPoint(), intAux2.get(0), centerEi);
										LimitedArc arcAfterIntersection = new LimitedArc(intAux2.get(0), aux2.getFinalPoint(), centerEi);
										elementsIntermediario.add(arcBeforeIntersection);
										elementsIntermediario.add(arcAfterIntersection);
										elementsIntermediario.remove(indice2);
									}
									else if(intSize == 2)
									{
										ArrayList<Point3d> intersectionTemp = intAux2;
										if(distance(aux2.getInitialPoint(), intAux2.get(0)) > distance(aux2.getInitialPoint(), intAux2.get(1)))
										{
											intersectionTemp.set(0, intAux2.get(1));
											intersectionTemp.set(1, intAux2.get(0));
										}
										LimitedArc arcBeforeIntersection = new LimitedArc(aux2.getInitialPoint(), intAux2.get(0), centerEi);
										LimitedArc arcBetweenIntersection = new LimitedArc(intAux2.get(0), intAux2.get(1), centerEi);
										LimitedArc arcAfterIntersection = new LimitedArc(intAux2.get(1), aux2.getFinalPoint(), centerEi);
										elementsIntermediario.add(arcBeforeIntersection);
										elementsIntermediario.add(arcBetweenIntersection);
										elementsIntermediario.add(arcAfterIntersection);
										elementsIntermediario.remove(indice2);
									}
								}
								if(intAux3 != null)
								{
									//intersection = intersectionElements(aux3,ej);
									if(intSize == 1)
									{
										LimitedArc arcBeforeIntersection = new LimitedArc(aux3.getInitialPoint(), intAux3.get(0), centerEi);
										LimitedArc arcAfterIntersection = new LimitedArc(intAux3.get(0), aux3.getFinalPoint(), centerEi);
										System.out.println("ArcCenter1: " + arcBeforeIntersection.getCenter());
										System.out.println("ArcCenter3: " + arcAfterIntersection.getCenter());
										elementsIntermediario.add(arcBeforeIntersection);
										elementsIntermediario.add(arcAfterIntersection);
										elementsIntermediario.remove(indice3);
									}
									else if(intSize == 2)
									{
										ArrayList<Point3d> intersectionTemp = intAux3;
										if(distance(aux3.getInitialPoint(), intAux3.get(0)) > distance(aux3.getInitialPoint(), intAux3.get(1)))
										{
											intersectionTemp.set(0, intAux3.get(1));
											intersectionTemp.set(1, intAux3.get(0));
										}
										LimitedArc arcBeforeIntersection = new LimitedArc(aux3.getInitialPoint(), intAux3.get(0), centerEi);
										LimitedArc arcBetweenIntersection = new LimitedArc(intAux3.get(0), intAux3.get(1), centerEi);
										LimitedArc arcAfterIntersection = new LimitedArc(intAux3.get(1), aux3.getFinalPoint(), centerEi);
										elementsIntermediario.add(arcBeforeIntersection);
										elementsIntermediario.add(arcBetweenIntersection);
										elementsIntermediario.add(arcAfterIntersection);
										elementsIntermediario.remove(indice3);
								}
							}
						}
					}
				}
			}
		}
		if(thereIsIntersection == false)
		{
			elementsIntermediario.add(ei);
		}
	}
		//showElements(elementsIntermediario);
		//elementsValidated.add(elementsIntermediario);
		
		//showElements(elementsIntermediario);
		
		System.out.println("Intersecoes: " + intersecoes.size());
		
//		Point3d intersection = intersecoes.get(0);
//		Point3d initialPoint = intersection;
		
		/*
		 * 	Valida��o 2: Elementos com a minima distancia (em relacao a forma original) menor que a distancia de offset, sao descartados 
		 */
		System.out.println("Elementos intermediarios: " + elementsIntermediario.size());
		for(int i = 0; i< elementsIntermediario.size();i++)
		{
			LimitedElement ei0 = elementsIntermediario.get(i);
			//System.out.println("Menor distancia elemento " + i + ": " +  minimumDistance(formaOriginal, ei0));
			if(minimumDistance(formaOriginal, ei0) >= distance)
			{
				elementsIntermediario2.add(ei0);
			}
		}
		
		//showElements(elementsIntermediario2);
		/*
		 * 	Valida��o 3: Separacao dos elementos em lacos 
		 */
		int numeroDeLacos = 0;
		Point3d initialPoint = null;
//		for(int i = 0; i < elementsIntermediario2.size(); i++)
//		{
//			System.out.println("Numero de lacos: " + numeroDeLacos);
//			LimitedElement ei0 = elementsIntermediario2.get(0);
//			elementsValidated.get(numeroDeLacos).add(ei0);
//			elementsIntermediario2.remove(ei0);
//			for(int j = 1; j < elementsIntermediario2.size(); j++)
//			{
//				LimitedElement ej = elementsIntermediario2.get(j);
//				if(ei0.isLimitedArc())
//				{
//					LimitedArc arci = (LimitedArc)ei0;
//					Point3d arciI = arci.getInitialPoint();
//					Point3d arciF = arci.getFinalPoint();
//					if(i == 0)
//					{
//						initialPoint = arciI;
//					}
//					if(ej.isLimitedArc())
//					{
//						LimitedArc arcj = (LimitedArc)ej;
//						Point3d arcjI = arcj.getInitialPoint();
//						Point3d arcjF = arcj.getFinalPoint();
//						if(!(isTheSamePoint(arcjF,arcjI)))
//						{
//							if(isTheSamePoint(arciF,arcjI))
//							{
//								elementsValidated.get(numeroDeLacos).add(ej);
//								elementsIntermediario2.remove(ej);
//							}
//						}
//						
//						else
//						{
//							System.out.println("lol");
//							numeroDeLacos++;
//						}
//					}
//					else if(ej.isLimitedLine())
//					{
//						LimitedLine linej = (LimitedLine)ej;
//						Point3d linejI = linej.getInitialPoint();
//						Point3d linejF = linej.getFinalPoint();
//						if(!(isTheSamePoint(linejF,initialPoint)))
//						{
//							if(isTheSamePoint(arciF,linejI))
//							{
//								elementsValidated.get(numeroDeLacos).add(ej);
//								elementsIntermediario2.remove(ej);
//							}
//						}
//						else
//						{
//							System.out.println("lol");
//							numeroDeLacos++;
//						}
//					}
//				}
//				else if(ei0.isLimitedLine())
//				{
//					LimitedLine linei = (LimitedLine)ei0;
//					Point3d lineiI = linei.getInitialPoint();
//					Point3d lineiF = linei.getFinalPoint();
//					if(i == 0)
//					{
//						initialPoint = lineiI;
//					}
//					if(ej.isLimitedArc())
//					{
//						LimitedArc arcj = (LimitedArc)ej;
//						Point3d arcjI = arcj.getInitialPoint();
//						Point3d arcjF = arcj.getFinalPoint();
//						if(!(isTheSamePoint(arcjF,initialPoint)))
//						{
//							if(isTheSamePoint(lineiF,arcjI))
//							{
//								elementsValidated.get(numeroDeLacos).add(ej);
//								elementsIntermediario2.remove(ej);
//							}
//						}
//						else
//						{
//							numeroDeLacos++;
//						}
//					}
//					else if(ej.isLimitedLine())
//					{
//						LimitedLine linej= (LimitedLine)ej;
//						Point3d linejI = linej.getInitialPoint();
//						Point3d linejF = linej.getFinalPoint();
//						if(!(isTheSamePoint(linejF,initialPoint)))
//						{
//							if(isTheSamePoint(lineiF,linejI))
//							{
//								elementsValidated.get(numeroDeLacos).add(ej);
//								elementsIntermediario2.remove(ej);
//							}
//						}
//						else
//						{
//							numeroDeLacos++;
//						}
//					}
//				}
//			}
//		}
		elementsValidated.add(elementsIntermediario2);
		System.out.println("Size:" + elements.size());
		System.out.println("Size Validated:" + elementsValidated.get(0).size());
		return elementsValidated;
	}
	
//	public static ArrayList<LimitedElement> validar2Path(ArrayList<LimitedElement> elements, ArrayList<LimitedElement> formaOriginal, double distance)
//	{
//		
//	}
	
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
					System.out.println("Here is an intersection point " + intersection);
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
				
				//Alternativa para n�o identificar pontos de interse��o nas extremidades dos Limited Elements
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
				//o metodo intersectionPoint n�o est� definido para (line, arc) mas sim pra (arc, line)	
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
		if(roundNumber(p1.x, 10) == roundNumber(p2.x,10) && roundNumber(p1.y, 10) == roundNumber(p2.y, 10) 
				&& roundNumber(p1.z, 10) == roundNumber(p2.z, 10)){
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
		//System.out.println("Distance Vector " + distanceVector);
		//System.out.println("New Initial Point " + newInitialPoint);
		//System.out.println("New Final Point " + newFinalPoint);
		LimitedLine lineParallel = new LimitedLine(newInitialPoint, newFinalPoint);
		
		//System.out.println("Parallel from " + lineParallel.getInitialPoint() + " to " + lineParallel.getFinalPoint());
		
		return lineParallel;
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
	
//	public static boolean belongsArc(LimitedArc arc, Point3d p)
//	{
//		
//	}
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
//				LimitedArc tempArc = new LimitedArc(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle()/2,1);
//				Point3d unitToNewPoint = unitVector(tempArc.getFinalPoint(), arc.getCenter());
//				Point3d newCenter = plus(tempArc.getFinalPoint(),multiply(distance,unitToNewPoint));
//				newArc = new LimitedArc(newCenter, newCenter, arc.getDeltaAngle(),1);
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
		
		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath1(elements, distance);
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
				parallelPath = parallelPath1(path,distance);
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
				//System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI  + " radius " + arc.getRadius()); 
				System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ",0)" + ",new Point3d(" + arc.getFinalPoint().x + "," + arc.getFinalPoint().y + ",0)" + "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y + ",0));");					

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
			
			//Garante que n�o calularemos uma tangente = infinity
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
		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp = arc.getInitialPoint();
			Point3d temp1 = arc.getFinalPoint();
			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
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
					intersection = new ArrayList<Point3d> ();
					intersection.add(new Point3d(x1,y1,0));
				}
			}
		} 
		if((anguloInicial <= anguloNaIntersecao2 && anguloFinal >= anguloNaIntersecao2))
		{
			moduloR2 = Math.sqrt(Math.pow((x2 - line.getInitialPoint().x),2) + Math.pow((y2 - line.getInitialPoint().y), 2));
			diretor2 = new Point3d(((x2 - line.getInitialPoint().x)/moduloR1),((y2 - line.getInitialPoint().y)/moduloR1),0);
			//Validacao do possivel ponto de intersecao
			if (!((diretor1.getX() == (-1)*diretor2.getX()) && (diretor1.getY() == (-1)*diretor2.getY())))
			{
				if(moduloR1 >= moduloR2)
				{
					if(intersection == null)
					{
						intersection = new ArrayList<Point3d> ();
					}
					intersection.add(new Point3d(x2,y2,0));
				}
			}
		}
		if(!(intersection == null))
		{
			if(intersection.size() == 1)
			{
				if (!belongs(line, intersection.get(0))) 
				{
					intersection = null;
				} 
			}
			else if(intersection.size() == 2)
			{
				if (!belongs(line, intersection.get(1))) 
				{
					intersection = null;
				} 
			}
		}
		return intersection;
	}
	/**
	 *  Este metodo verifica se a circunferencia criada com arc1 está dentro da circunferencia criada com arc2
	 * @param arc1 --> arco Base
	 * @param arc2 --> arco a verificar se está contido
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
		LimitedArc arc1Tmp = new LimitedArc(arc1.getInitialPoint(), arc1.getFinalPoint(), arc1.getCenter());
		LimitedArc arc2Tmp = new LimitedArc(arc2.getInitialPoint(), arc2.getFinalPoint(), arc2.getCenter());

		if(arc1.getDeltaAngle() < 0)
		{
			Point3d temp = arc1.getInitialPoint();
			Point3d temp1 = arc1.getFinalPoint();
			arc1Tmp = new LimitedArc(temp1, temp, arc1.getCenter());
		}
		if(arc2.getDeltaAngle() < 0)
		{
			Point3d temp = arc2.getInitialPoint();
			Point3d temp1 = arc2.getFinalPoint();
			arc2Tmp = new LimitedArc(temp1, temp, arc2.getCenter());

		}
		ArrayList<Point3d> intersection = null;
		
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
			return intersection;
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
				if(roundNumber(ypossiveis1[i], 10) == roundNumber(ypossiveis2[j], 10))
				{
					if(i == 0 || i == 1)
					{
						ypossiveis.add(ypossiveis1[i]);
						
					} 
					else if(i == 2 || i == 3)
					{
						ypossiveis.add(ypossiveis1[i]);
					}
					//ypossiveis1[i] = -1;
					break;
				}
			}
		}
		
		System.out.println("Array Ypossiveis: " + ypossiveis);
		System.out.println("Possiveis size: " + ypossiveis.size());
		y1 = ypossiveis.get(0);
		y2 = ypossiveis.get(1);
		Point3d possivel1 = new Point3d(x1, y1, 0);
		Point3d possivel2 = new Point3d(x2, y2, 0);
		
//		System.err.println("poss 1 =" + possivel1);
//		System.err.println("poss 2 =" + possivel2);
		
		double anguloInicial1 = Math.atan2(arc1Tmp.getInitialPoint().y - arc1Tmp.getCenter().y, arc1Tmp.getInitialPoint().x - arc1Tmp.getCenter().x);
		double anguloFinal1 = Math.atan2(arc1Tmp.getFinalPoint().y - arc1Tmp.getCenter().y, arc1Tmp.getFinalPoint().x - arc1Tmp.getCenter().x);
		
		double anguloInicial2 = Math.atan2(arc2Tmp.getInitialPoint().y - arc2Tmp.getCenter().y, arc2Tmp.getInitialPoint().x - arc2Tmp.getCenter().x);
		double anguloFinal2 = Math.atan2(arc2Tmp.getFinalPoint().y - arc2Tmp.getCenter().y, arc2Tmp.getFinalPoint().x - arc2Tmp.getCenter().x);
		
		double anguloNaIntersecaoCirculo1Possivel1 = Math.atan2(possivel1.y - arc1Tmp.getCenter().y, possivel1.x - arc1Tmp.getCenter().x);
		double anguloNaIntersecaoCirculo1Possivel2 = Math.atan2(possivel2.y - arc1Tmp.getCenter().y, possivel2.x - arc1Tmp.getCenter().x);
		
		double anguloNaIntersecaoCirculo2Possivel1 = Math.atan2(possivel1.y - arc2Tmp.getCenter().y, possivel1.x - arc2Tmp.getCenter().x);
		double anguloNaIntersecaoCirculo2Possivel2 = Math.atan2(possivel2.y - arc2Tmp.getCenter().y, possivel2.x - arc2Tmp.getCenter().x);
				
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
		
//		System.out.println("AI1 = " + (anguloInicial1 * 180 / Math.PI));
//		System.out.println("AF1 = " + (anguloFinal1 * 180 / Math.PI));
//		System.out.println("AI2 = " + (anguloInicial2 * 180 / Math.PI));
//		System.out.println("AF2 = " + (anguloFinal2 * 180 / Math.PI));
//		System.out.println("anguloNaIntersecaoCirculo1Possivel1 = " + (anguloNaIntersecaoCirculo1Possivel1 * 180 / Math.PI));
//		System.out.println("anguloNaIntersecaoCirculo1Possivel2 = " + (anguloNaIntersecaoCirculo1Possivel2 * 180 / Math.PI));
//		System.out.println("anguloNaIntersecaoCirculo2Possivel1 = " + (anguloNaIntersecaoCirculo2Possivel1 * 180 / Math.PI));
//		System.out.println("anguloNaIntersecaoCirculo2Possivel2 = " + (anguloNaIntersecaoCirculo2Possivel2 * 180 / Math.PI));
		//System.out.println("")
		

		if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel1) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel1))
				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel1) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel1)))
		{
			intersection = new ArrayList<Point3d> ();
			intersection.add(possivel1);
		}
		if(((anguloInicial1 <= anguloNaIntersecaoCirculo1Possivel2) && (anguloFinal1 >= anguloNaIntersecaoCirculo1Possivel2))
				&&((anguloInicial2 <= anguloNaIntersecaoCirculo2Possivel2) && (anguloFinal2 >= anguloNaIntersecaoCirculo2Possivel2)))
		{
			if(intersection == null)
			{
				intersection = new ArrayList<Point3d> ();
			}
			intersection.add(possivel2);
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
