package br.UFSC.GRIMA.util.geometricOperations;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
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
		//Estava somando o Z tbm (na logica do Two5D, o z permanece constante)
		Point3d plusPoint = new Point3d(p1.x+p2.x, p1.y+p2.y,p1.z/*+p2.getZ()*/);
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
		Point3d minusPoint = new Point3d(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
		return minusPoint;
	}	
	
	public static Point3d multiply(double m, Point3d p)
	{
		//Na logica do Two5D, o z e constante, logo o m so multiplica x e y
		return new Point3d(m * p.x, m * p.y, p.z);
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
		Point3d Pb = new Point3d(p0.x + b * v.x, p0.y + b * v.y, p0.z + b * v.z);
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
		double escalar = p1.x * p2.x + p1.y * p2.y + p1.z * p2.z;
		return escalar;
	}
	
	public static Point3d vectorial(Point3d p1, Point3d p2)
	{
		Point3d vectorial = new Point3d(p1.y * p2.z - p2.y * p1.z, -(p1.x * p2.z - p2.x * p1.z), p1.x * p2.y - p2.x * p1.y);		
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
	public static Point3d nearestPoint1(Point3d p, LimitedArc arc)
	{
		Point3d pontoMaisProximo = null;
		boolean estaFora = false;
		if(p.distance(arc.getCenter()) >= arc.getRadius())
		{
			estaFora = true;
		}
		LimitedLine lineAux = new LimitedLine(arc.getCenter(), p);
		if (estaFora) // ====== p esta fora do circulo
		{
			if(intersectionPoint(arc, lineAux) != null)
			{
				pontoMaisProximo = intersectionPoint(arc, lineAux).get(0);
			}
			else
			{
				if(arc.getInitialPoint().distance(p) < arc.getFinalPoint().distance(p))
				{
					pontoMaisProximo = arc.getInitialPoint();
				}
				else
				{
					pontoMaisProximo = arc.getFinalPoint();
				}
			}
		} else // ======= p esta dentro do circulo
		{
			Point3d vetorUnitario = unitVector(arc.getCenter(), p);
			Point3d intersection = new Point3d(arc.getCenter().x + multiply(arc.getRadius(), vetorUnitario).x, arc.getCenter().y + multiply(arc.getRadius(), vetorUnitario).y, arc.getCenter().z);
			if(belongsArc(arc, intersection))
			{
				pontoMaisProximo = intersection;
			}
			else
			{
				if(p.distance(arc.getInitialPoint()) < p.distance(arc.getFinalPoint()))
				{
					pontoMaisProximo = arc.getInitialPoint();
				}
				else
				{
					pontoMaisProximo = arc.getFinalPoint();
				}
			}
		}
		return pontoMaisProximo;
	}
//	public static double minimumDistancePointToArc(Point3d p, LimitedArc arc)
//	{	
//		LimitedArc arcTmp = new LimitedArc(arc.getInitialPoint(), arc.getFinalPoint(), arc.getCenter());
//		if(arc.getDeltaAngle() < 0)
//		{
//			Point3d temp = arc.getInitialPoint();
//			Point3d temp1 = arc.getFinalPoint();
//			arcTmp = new LimitedArc(temp1, temp, arc.getCenter());
//		}
//		Point3d v = new Point3d(p.getX()-arc.getCenter().getX(), p.getY()-arc.getCenter().getY(), p.getZ()-arc.getCenter().getZ());
//		Point3d normalPoint = plus(arc.getCenter(),multiply(arc.getRadius()/norm(v),v));
//		
//		if (!contentsPoint(normalPoint, arc))
//		{
//			normalPoint = plus(arc.getCenter(),multiply(-arc.getRadius()/norm(v),v));
//			//System.out.println("New normal point " + normalPoint);
//			return chooseMinimum(distance(p, arc.getInitialPoint()), distance(p,arc.getFinalPoint()), distance(p, normalPoint));			
//		}
//		//System.out.println("Normal point " + normalPoint);
//		return distance(p, normalPoint);
//	}	
	public static double minimumDistancePointToArc(Point3d p, LimitedArc arc)
	{	
		double minimaDistancia = 0;
		Point3d pontoMaisProximo = nearestPoint1(p, arc);
		minimaDistancia = p.distance(pontoMaisProximo);
		return minimaDistancia;
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
	public static double minimumDistanceLineToArc1(LimitedLine line, LimitedArc arc)
	{
		boolean inside = false;
		Point3d p = nearestPoint(arc.getCenter(), line);
		LimitedLine lineAux = new LimitedLine(p, arc.getCenter());
		double minimum = 0;
		if(intersectionPoint(arc, line) == null)
		{
			if(intersectionPoint(arc, lineAux) != null) 
			{
				Point3d intersection = intersectionPoint(arc, lineAux).get(0);
				minimum = p.distance(intersection);
			}
			else
			{
				Point3d unitVec1 = unitVector(arc.getCenter(), line.getInitialPoint());
				Point3d p1 = plus(arc.getCenter(),multiply(arc.getRadius(),unitVec1));
				Point3d unitVec2 = unitVector(arc.getCenter(), line.getFinalPoint());
				Point3d p2 = plus(arc.getCenter(),multiply(arc.getRadius(),unitVec2));
				if(belongsArc(arc,p1) || belongsArc(arc,p2))
				{
					inside = true;
				}
//				double distance1 = arc.getInitialPoint().distance(nearestPoint(arc.getInitialPoint(), line));
//				double distance2 = arc.getFinalPoint().distance(nearestPoint(arc.getFinalPoint(), line));
				ArrayList<Double> minimumDistances = new ArrayList<Double>();
				
				double d1 = minimumDistancePointToArc(line.getInitialPoint(), arc);
				double d2 = minimumDistancePointToArc(line.getFinalPoint(), arc);
				double d3 = minimumDistancePointToLine(arc.getInitialPoint(), line);
				double d4 = minimumDistancePointToLine(arc.getFinalPoint(), line);
				//Considera o caso da linha estar dentro do espaço de abrangencia do arco
				if(inside)
				{
					minimumDistances.add(d1);
					minimumDistances.add(d2);
				}
				minimumDistances.add(d3);
				minimumDistances.add(d4);
				minimum = minimumDistances.get(0);
//				minimum = distance1;
//				if (distance2 < minimum) 
//				{
//					minimum = distance2;
//				}
				for(double tmp: minimumDistances)
				{
					if(tmp < minimum)
					{
						minimum = tmp;
					}
				}
			}
		}
		return minimum;
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
//				minimumDistance = intersection.get(0).distance(intersection.get(1));
				double distanceTmp1 = minimumDistancePointToArc(arc1.getInitialPoint(), arc2);
				double distanceTmp2 = minimumDistancePointToArc(arc1.getFinalPoint(), arc2);
				double distanceTmp3 = minimumDistancePointToArc(arc2.getInitialPoint(), arc1);
				double distanceTmp4 = minimumDistancePointToArc(arc2.getFinalPoint(), arc1);
				double distanceTmp5 = intersection.get(0).distance(intersection.get(1));
				ArrayList<Double> distanceTemp = new ArrayList<Double>();
				distanceTemp.add(distanceTmp1);
				distanceTemp.add(distanceTmp2);
				distanceTemp.add(distanceTmp3);
				distanceTemp.add(distanceTmp4);
				distanceTemp.add(distanceTmp5);
				minimumDistance = distanceTemp.get(0);
				for(Double tmp:distanceTemp)
				{
					if(tmp < minimumDistance)
					{
						minimumDistance = tmp;
					}
				}
			}
			else if(intersection.size() == 1)
			{
//				//arc1 intersetado
//				if(belongsArc(arc1,intersection.get(0)))
//				{
////					if(arc2.getCenter().distance(arg0))
//					double distanceTmp1 = minimumDistancePointToArc(arc2.getInitialPoint(), arc1);
//					double distanceTmp2 = minimumDistancePointToArc(arc2.getFinalPoint(), arc1);
//					if(distanceTmp1 < distanceTmp2)
//					{
//						minimumDistance = distanceTmp1;
//					}
//					else
//					{
//						minimumDistance = distanceTmp2;
//					}
//
//				}
//				//arc2 intersetado
//				else if(belongsArc(arc2,intersection.get(0)))
//				{
//					double distanceTmp1 = minimumDistancePointToArc(arc1.getInitialPoint(), arc2);
//					double distanceTmp2 = minimumDistancePointToArc(arc1.getFinalPoint(), arc2);
//					if(distanceTmp1 < distanceTmp2)
//					{
//						minimumDistance = distanceTmp1;
//					}
//					else
//					{
//						minimumDistance = distanceTmp2;
//					}
//				}
				double distanceTmp1 = minimumDistancePointToArc(arc1.getInitialPoint(), arc2);
				double distanceTmp2 = minimumDistancePointToArc(arc1.getFinalPoint(), arc2);
				double distanceTmp3 = minimumDistancePointToArc(arc2.getInitialPoint(), arc1);
				double distanceTmp4 = minimumDistancePointToArc(arc2.getFinalPoint(), arc1);
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
			else
			{
				Point3d pNear1 = nearestPoint1(arc1.getCenter(), arc2);
				LimitedLine aux1 = new LimitedLine(arc1.getCenter(),pNear1);
				Point3d pNear2 = nearestPoint1(arc2.getCenter(), arc1);
				LimitedLine aux2 = new LimitedLine(arc2.getCenter(),pNear2);
//				System.out.println("lol");
				if(intersectionPoint(arc1, aux1) != null)
				{
					minimumDistance = intersectionPoint(arc1, aux1).get(0).distance(pNear1);
				}
				else if(intersectionPoint(arc2, aux2) != null)
				{
					minimumDistance = intersectionPoint(arc2, aux2).get(0).distance(pNear2);
				}
				else
				{
					double distanceTmp1 = minimumDistancePointToArc(arc1.getInitialPoint(), arc2);
					double distanceTmp2 = minimumDistancePointToArc(arc1.getFinalPoint(), arc2);
					double distanceTmp3 = minimumDistancePointToArc(arc2.getInitialPoint(), arc1);
					double distanceTmp4 = minimumDistancePointToArc(arc2.getFinalPoint(), arc1);
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
		}
		return minimumDistance;
	}
	public static double angle(Point3d v)
	{
		double alpha;
		Point3d axisX = new Point3d(1.0, 0.0, 0.0);
//		alpha = Math.acos(escalar(axisX, v) / norm(axisX) / norm(v));
		alpha = Math.atan2(v.y, v.x);

//		if (v.getY() < 0)
//			alpha = -alpha + 2 * Math.PI;
		//System.out.println("Alpha from angle " + alpha*180/Math.PI);

		return alpha;
	}
	public static double angle(Point3d p1, Point3d p2)
	{
		return Math.atan2(p1.y - p2.y, p1.x - p2.x);
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
		return multiply(1 / norm(minus(p2, p1)), minus(p2, p1));
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

		Point3d center = new Point3d(p2.x+distanceToCenterFromP2*Math.cos(centerAngle), p2.y+distanceToCenterFromP2*Math.sin(centerAngle),p2.z);
		
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
		boolean isBoss = true;
		ArrayList<LimitedElement> etmp = new ArrayList<LimitedElement>();
		etmp = addPocket.getElements();
		//Cuidado: se o acabamento passar nas bordas (cavidade e protuberancia) o boolean isBoss e inside deve ser modificado
		ArrayList<LimitedElement> acabamentoElements = parallelPath1(etmp, radius,inside,!isBoss);
		
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
	
	public static ArrayList<LimitedElement> elementsToDesbaste (GeneralClosedPocket pocket, double allowance, double planoZ)
	{
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), planoZ, allowance);
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
	
	/**
	 * Minima distancia entre dois elementos quaisquer
	 * @param e1
	 * @param e2
	 * @return
	 */
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
//				distance = minimumDistanceLineToArc(line1, arc2);
				distance = minimumDistanceLineToArc1(line1, arc2);
				if(distance <= 51)
				{
//					System.out.println("Linha: from " + line1.getInitialPoint() + " to " + line1.getFinalPoint());
//					System.out.println("Arc: from " + arc2.getInitialPoint() + " centred in " + arc2.getCenter());
//					System.out.println("Distancia: " + distance);
				}
			}
		}
		else if (e1.isLimitedArc())
		{
			LimitedArc arc1 = (LimitedArc)e1;
			if (e2.isLimitedLine())
			{
				LimitedLine line2 = (LimitedLine)e2;
//				distance = minimumDistanceArcToLine(arc1,line2);
				distance = minimumDistanceLineToArc1(line2, arc1);
			}
			if (e2.isLimitedArc())
			{
				LimitedArc arc2 = (LimitedArc)e2;
				distance = minimumDistanceArcToArc(arc1, arc2);
				if(distance <= 51)
				{
//					System.out.println("Arc1: from " + arc1.getInitialPoint() + " centred in " + arc1.getCenter() + "DeltaAngle: " + arc1.getDeltaAngle());
//					System.out.println("Arc2: from " + arc2.getInitialPoint() + " centred in " + arc2.getCenter() + "DeltaAngle: " + arc2.getDeltaAngle());
//					System.out.println("Distancia: " + distance);
				}
			}			
		}		
		return distance;
	}
	/**
	 * Minima distancia entre um elemento e um array de elementos
	 * @param formaOriginal
	 * @param element
	 * @return
	 */
	public static double minimumDistance(ArrayList<LimitedElement> formaOriginal, LimitedElement element)
	{
//		showElements(formaOriginal);
		double minimumDistance = minimumDistance(formaOriginal.get(0),element);
		
		for(int i = 0;i < formaOriginal.size(); i++)
		{
			LimitedElement temp = formaOriginal.get(i);
			double auxiliar = minimumDistance(temp,element);
//			System.out.println(temp.getClass());
//			System.out.println(temp.getInitialPoint());
//			System.out.println(auxiliar);
			if(minimumDistance > auxiliar)
			{
				minimumDistance = auxiliar;
			}
//			System.out.println("Minumum Distance: " + minimumDistance + " \t--> " + temp.getClass());
		}
		return minimumDistance;
	}
	/**
	 * Minima distancia entre dois arrays de LimitedElement
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static double minimumDistance(ArrayList<LimitedElement> array1, ArrayList<LimitedElement> array2)
	{
		double minimumDistance = minimumDistance(array1.get(0), array2.get(0));
		for(LimitedElement e1 : array1)
		{
			for(LimitedElement e2 : array2)
			{
//				ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//				show.add(e1);
//				show.add(e2);
//				showElements(show);
				double minimumDistanceTmp = minimumDistance(e1, e2);
//				System.out.println("minimaDistancia: " + minimumDistanceTmp);
				if(minimumDistanceTmp < minimumDistance)
				{
					minimumDistance = minimumDistanceTmp;
				}
//				System.out.println("menorMinimaDistancia: " + minimumDistance);
			}
		}
		return minimumDistance;
	}
	public static double maxDistance(ArrayList<LimitedElement> array1, ArrayList<LimitedElement> array2)
	{
		double minimumDistance = 0;
		for(LimitedElement e1 : array1)
		{
			for(LimitedElement e2 : array2)
			{
				double minimumDistanceTmp = minimumDistance(e1, e2);
				if(minimumDistanceTmp > minimumDistance)
				{
					minimumDistance = minimumDistanceTmp;
				}
			}
		}
		return minimumDistance;
	}
	/**
	 * Minima distancia entr um ponto e um elemento qualquer
	 * @param point
	 * @param element
	 * @return
	 */
	public static double minimumDistance(Point3d point, LimitedElement element)
	{
		if(element.isLimitedArc())
		{
			return minimumDistancePointToArc(point, (LimitedArc)element);
		}
		else
		{
			return minimumDistancePointToLine(point, (LimitedLine)element);
		}
	}
/**
 * Minima distancia entre um ponto e um array de elementos
 * @param elements
 * @param point
 * @return
 */
	public static double minimumDistance(ArrayList<LimitedElement> elements, Point3d point)
	{
		double minimumDistance = minimumDistance(point, elements.get(0));
		for(LimitedElement temp:elements)
		{
			double minimumDistanceTmp = minimumDistance(point,temp);
			if(minimumDistanceTmp < minimumDistance)
			{
				minimumDistance = minimumDistanceTmp;
			}
//			ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//			show.add(temp);
//			showElements(show);
//			System.out.println("MnimumDistance Array: " + minimumDistanceTmp);
		}
		return minimumDistance;
	}
	/**
	 * 	o metodo calcula a menor maior distancia entre uma lista de elementos (supoe-se que eh uma forma com protuberancias, portanto nao hah intersecoes, ou seja, menor distancia = 0)
	 * @param elements --> lista de elementos para o qual deseja-se conhecer a menor_maior distancia
	 * @return
	 */
	public static double minimumMaximunDistanceBetweenElements(ArrayList<LimitedElement> elements)
	{
//		double minimumDistance = minimumDistance(formaOriginal.get(0),element);
		double minimumDistance = 100;//minimumDistance(elements.get(0), elements.get(1));
		ArrayList<Double> negativos = new ArrayList<Double>();
		ArrayList<Double> positivos = new ArrayList<Double>();
		for(int i = 0;i < elements.size(); i++)
		{
			LimitedElement temp = elements.get(i);
			if (temp.isLimitedArc()) {
				LimitedArc atmp = (LimitedArc) temp;
				if (atmp.getDeltaAngle() < 0) 
				{
					negativos.add(atmp.getInitialPoint().distance(atmp.getFinalPoint()));
				} else
				{
					positivos.add(atmp.getInitialPoint().distance(atmp.getFinalPoint()));
				}
			}
		}
		for(int i = 0;i < elements.size(); i++)
		{
			LimitedElement temp = elements.get(i);
			for(int j = i + 1; j < elements.size(); j ++) // Pode dar problema de ponteiro nulo em tempo de execucao!!!!
			{
				LimitedElement temp1 = elements.get(j);
				double dTmp = minimumDistance(temp, temp1);
//				System.out.println(dTmp);
				if(roundNumber(dTmp, 10) > 0 && minimumDistance > dTmp) // a distancia deve ser maior a zero
				{
					if(!isInList(negativos, dTmp))
						minimumDistance = dTmp;
				}
			}
		}
		return minimumDistance;
	}
	public static boolean isInList(ArrayList<Double> list, double in)
	{
		boolean is = false;
		for(Double tmp : list)
		{
			if(roundNumber(tmp, 10) == roundNumber(in, 10))
			{
				is = true;
				break;
			}
		}
		return is;
	}
	public static ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallelPath(GeneralClosedPocket pocket, double distance,double planoZ)
	{
//		double planoZ = 0;
		double percentagem = 0.5;
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = new ArrayList<ArrayList<ArrayList<LimitedElement>>>();
		
//		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath1(elements, distance);
		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath2(pocket, distance, planoZ);
		int aux = 1;
		double distanceAtualizada = 2*distance*percentagem;
		System.out.println("Distancia: " + distance);
		System.out.println("DistanciaAtual: " + distanceAtualizada);
		System.out.println("Percentagem: " + percentagem);
//		parallelPath != null
		while (parallelPath != null)
		{
			multipleParallel.add(parallelPath);
			parallelPath = parallelPath2(pocket, distance + (aux*distanceAtualizada),planoZ);
			aux++;
		}		
	//		System.out.println("mutilplePath: " + multipleParallel.size());
	//		showElements(multipleParallel.get(0).get(0));
		return multipleParallel;
	}


	//Voltei para Array linear (ta ficando complicado de fazer recursivo, toda saída teria que ser transformada em um GeneralClosedPocket)
	public static ArrayList<LimitedElement> parallelPath1 (ArrayList<LimitedElement> elements, double distance, boolean inside, boolean isBoss)
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
//						System.out.println(lacoTmp);
						Point3d center = arcTmp.getInitialPoint();
						Point3d pI = lacoTmp.get(j-1).getFinalPoint();
						newArc = new LimitedArc(center,pI,Math.PI/2);
						
					}
					else
					{
						newArc = parallelArc(arcTmp, distance,inside,isBoss);
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
	
	/**
	 * Transforma um array de Boss e transoforma em um array de array de LimitedElement
	 * Cada Array de LimitedElement corresponde a um boss
	 * @param bossArray
	 * @param planoZ
	 * @return 
	 */
	public static ArrayList<ArrayList<LimitedElement>> tranformeBossToLimitedElement(ArrayList<Boss> bossArray,double planoZ)
	{
		ArrayList<ArrayList<LimitedElement>> elementosFromBoss = new ArrayList<ArrayList<LimitedElement>>();
		for(Boss bossTmp: bossArray)
		{
			ArrayList<LimitedElement> elementosFromBossTmp = new ArrayList<LimitedElement>();
			if(bossTmp.getClass() == CircularBoss.class)
			{
				CircularBoss tmp = (CircularBoss)bossTmp;
//				System.out.println("Profundidade Boss: " + tmp.Z);
//				System.out.println(tmp.getCenter().x + (tmp.getDiametro1()/2));
				LimitedArc arc = new LimitedArc(new Point3d(tmp.X,tmp.Y,planoZ), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, planoZ), -2 * Math.PI);
//				System.out.println("BossCenter: " + arc.getCenter());
//				System.out.println("Protuberancia Arco: " + arc.getInitialPoint());
				elementosFromBossTmp.add(arc);
			}
			else if (bossTmp.getClass() == RectangularBoss.class)
			{
				RectangularBoss tmp = (RectangularBoss)bossTmp;
				//Tamanho em x
				double l = tmp.getL1();
				//Tamanho em y
				double c = tmp.getL2();
				Point3d position = new Point3d(tmp.X,tmp.Y,planoZ);
				LimitedLine l1 = new LimitedLine(pointPlusEscalar(position, "x", tmp.getRadius()),pointPlusEscalar(position,"x",(l-tmp.getRadius())));
				LimitedArc a1 = new LimitedArc(pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()),l1.getFinalPoint(),Math.PI/2);
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(),pointPlusEscalar(a1.getFinalPoint(), "y", (c-2*tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(pointPlusEscalar(l2.getFinalPoint(), "x", -tmp.getRadius()),l2.getFinalPoint(),Math.PI/2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),pointPlusEscalar(a2.getFinalPoint(), "x", -(l - 2*tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()),l3.getFinalPoint(),Math.PI/2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(),pointPlusEscalar(a3.getFinalPoint(),"y",-(c - 2*tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(pointPlusEscalar(l4.getFinalPoint(), "x", tmp.getRadius()),l4.getFinalPoint(),Math.PI/2);
				elementosFromBossTmp.add(l1);
				elementosFromBossTmp.add(a1);
				elementosFromBossTmp.add(l2);
				elementosFromBossTmp.add(a2);
				elementosFromBossTmp.add(l3);
				elementosFromBossTmp.add(a3);
				elementosFromBossTmp.add(l4);
				elementosFromBossTmp.add(a4);
				
			}else if (bossTmp.getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss tmp = (GeneralProfileBoss)bossTmp;
				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(tmp.getVertexPoints(), planoZ, tmp.getRadius());
				ArrayList<LimitedElement> elementosProtuberanciaGeral = addBossVertex.getElements();
				for(int i = 0;i < elementosProtuberanciaGeral.size();i++)
				{
					elementosFromBossTmp.add(elementosProtuberanciaGeral.get(i));
				}
				
			}
			elementosFromBoss.add(elementosFromBossTmp);
		}
		return elementosFromBoss;
	}
	public static ArrayList<ArrayList<LimitedElement>> parallelPath2 (GeneralClosedPocket pocket, double distance, double planoZ)
	{
//		planoZ = 0;
//		System.out.println("Profundidade: " + pocket.getProfundidade());
		boolean inside = true;
		boolean isBoss = true;
		//CUIDADO COM O Z!!
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), planoZ, pocket.getRadius());

		ArrayList<ArrayList<LimitedElement>> saida = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> parallelTemp1 = new ArrayList<LimitedElement>();      		//Paralela dos elementos da protuberancia
		ArrayList<LimitedElement> parallelTemp2 = new ArrayList<LimitedElement>();              //Paralela dos elementos da cavidade
		ArrayList<LimitedElement> totalParallel = new ArrayList<LimitedElement>();              //Array com todas as paralelas
		ArrayList<LimitedElement> formaOriginal = new ArrayList<LimitedElement>();              //Array da forma original (cavidade+protuberancia)
		ArrayList<LimitedElement> elementsCavidade = addPocketVertex.getElements();             //Cavidade
		ArrayList<Boss> bossArray = pocket.getItsBoss();                                       //Protuberancia
		ArrayList<LimitedElement> elementosProtuberancia = new ArrayList<LimitedElement>();
		for(ArrayList<LimitedElement> arrayTmp : tranformeBossToLimitedElement(bossArray, planoZ))
		{
			for(LimitedElement elementTmp : arrayTmp)
			{
				elementosProtuberancia.add(elementTmp);
			}
		}
//		ArrayList<LimitedElement> elementosProtuberancia = tranformeBossToLimitedElement(bossArray, planoZ);


//		ArrayList<LimitedElement> elementosProtuberancia = new ArrayList<LimitedElement>();
//		for(Boss bossTmp: bossArray)
//		{
//			if(bossTmp.getClass() == CircularBoss.class)
//			{
//				CircularBoss tmp = (CircularBoss)bossTmp;
//				System.out.println("Profundidade Boss: " + tmp.Z);
////				System.out.println(tmp.getCenter().x + (tmp.getDiametro1()/2));
//				LimitedArc arc = new LimitedArc(tmp.getCenter(), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, planoZ), -2 * Math.PI);
//				System.out.println("Protuberancia Arco: " + arc.getInitialPoint());
//				elementosProtuberancia.add(arc);
//			}
//			else if (bossTmp.getClass() == RectangularBoss.class)
//			{
//				RectangularBoss tmp = (RectangularBoss)bossTmp;
//				//Tamanho em x
//				double l = tmp.getL1();
//				//Tamanho em y
//				double c = tmp.getL2();
//				Point3d position = new Point3d(tmp.X,tmp.Y,planoZ);
//				LimitedLine l1 = new LimitedLine(pointPlusEscalar(position, "x", tmp.getRadius()),pointPlusEscalar(position,"x",(l-tmp.getRadius())));
//				LimitedArc a1 = new LimitedArc(pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()),l1.getFinalPoint(),Math.PI/2);
//				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(),pointPlusEscalar(a1.getFinalPoint(), "y", (c-2*tmp.getRadius())));
//				LimitedArc a2 = new LimitedArc(pointPlusEscalar(l2.getFinalPoint(), "x", -tmp.getRadius()),l2.getFinalPoint(),Math.PI/2);
//				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),pointPlusEscalar(a2.getFinalPoint(), "x", -(l - 2*tmp.getRadius())));
//				LimitedArc a3 = new LimitedArc(pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()),l3.getFinalPoint(),Math.PI/2);
//				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(),pointPlusEscalar(a3.getFinalPoint(),"y",-(c - 2*tmp.getRadius())));
//				LimitedArc a4 = new LimitedArc(pointPlusEscalar(l4.getFinalPoint(), "x", tmp.getRadius()),l4.getFinalPoint(),Math.PI/2);
//				elementosProtuberancia.add(l1);
//				elementosProtuberancia.add(a1);
//				elementosProtuberancia.add(l2);
//				elementosProtuberancia.add(a2);
//				elementosProtuberancia.add(l3);
//				elementosProtuberancia.add(a3);
//				elementosProtuberancia.add(l4);
//				elementosProtuberancia.add(a4);
//				
//			}else if (bossTmp.getClass() == GeneralProfileBoss.class)
//			{
//				GeneralProfileBoss tmp = (GeneralProfileBoss)bossTmp;
//				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(tmp.getVertexPoints(), planoZ, tmp.getRadius());
//				ArrayList<LimitedElement> elementosProtuberanciaGeral = addBossVertex.getElements();
//				for(int i = 0;i < elementosProtuberanciaGeral.size();i++)
//				{
//					elementosProtuberancia.add(elementosProtuberanciaGeral.get(i));
//				}
//				
//			}
//		}
		for(LimitedElement tmp:elementosProtuberancia)
		{
			boolean allowAdd = true;
			if(tmp.isLimitedArc())
			{
				if(((LimitedArc)tmp).getRadius() == 0)
				{
					allowAdd = false;
				}
			}
			if(allowAdd)
			{
				formaOriginal.add(tmp);
			}
		}
		for(LimitedElement tmp:elementsCavidade)
		{
			formaOriginal.add(tmp);
		}
		parallelTemp1 = parallelPath1(elementosProtuberancia, distance,!inside,isBoss);
//		showElements(parallelTemp1);
		parallelTemp2 = parallelPath1(elementsCavidade, distance, inside,!isBoss);
		
		for(LimitedElement tmp:parallelTemp1)
		{
			totalParallel.add(tmp);
		}
		for(LimitedElement tmp:parallelTemp2)
		{
			totalParallel.add(tmp);
		}
		
//		showElements(totalParallel);
		
//		saida = parallelPath1(laco, distance, !inside);
		
		saida = validarPath(totalParallel, formaOriginal, distance);
		
//		saida.add(totalParallel);
//		showElements(totalParallel);
		
		return saida;
	}
	public static double calcDeltaAngle(Point3d Pi, Point3d Pf, Point3d center, double arcAngle /*int sense*/)
	{
//		double anglePi = Math.atan2(Pi.y - center.y, Pi.x - center.x);
//		double anglePf = Math.atan2(Pf.y - center.y, Pf.x - center.x);
//		double r = center.distance(Pi);
//		double distance = Pi.distance(Pf);
//		double alpha = 2*(Math.asin(distance/(2*r)));
//		if(arcAngle == 2*Math.PI)
//		{
//			if(anglePi > anglePf)
//			{
//				alpha = 2*Math.PI - alpha;
//			}
//		}
//		if(arcAngle < 0) 
//		{
//			alpha = -alpha;
//		}
//		return alpha;
		
		double saida = 0;
		saida = Math.atan2(Pf.y - center.y, Pf.x - center.x) - Math.atan2(Pi.y - center.y, Pi.x - center.x);
//		System.out.println("Saida: " + saida);
		if(saida < 0)
		{
			saida = 2 * Math.PI + saida;
		}
		if(arcAngle < 0)
//		if(sense == LimitedArc.CW)
		{
			saida = -(2 * Math.PI - saida);
		}
		return saida;
	}
	
	public static ArrayList<LimitedArc> quebraArco(LimitedArc arc, ArrayList<Point3d> intersecoes)
	{
		LimitedArc arcInv = new LimitedArc(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp1 = arc.getFinalPoint();
//			if(Math.abs(arc.getDeltaAngle()) == 2*Math.PI)
//			{
//				temp1 = arc.getInitialPoint();
//			}
			arcInv = new LimitedArc(arc.getCenter(), temp1, -arc.getDeltaAngle());
		}
		double intSize = intersecoes.size();
		Point3d arcI = arcInv.getInitialPoint();
		Point3d arcF = arcInv.getFinalPoint();
		Point3d arcCenter = arcInv.getCenter();
		double oldDeltaAngle = arcInv.getDeltaAngle();
		ArrayList<LimitedArc> arcTemp = new ArrayList<LimitedArc>(); 
		Point3d intTemp;
//		System.out.println("Tamanho: " + intSize);
//		System.out.println("DeltaAngulo: " + Math.abs(roundNumber(arc.getDeltaAngle(),10)));
		if(Math.abs(arcInv.getDeltaAngle()) == 2*Math.PI && intersecoes.size() == 1)
		{
			arcTemp.add(arcInv);
		}
		else if(Math.abs(arcInv.getDeltaAngle()) == 2*Math.PI && intersecoes.size() > 1)
		{
			Point3d pI = intersecoes.get(0);
			ArrayList<Point3d> intersecoesTmp = new ArrayList<Point3d>();
			intersecoesTmp.add(pI);
			for(int i = 1;i < intersecoes.size(); i++)
			{
				Point3d temp = intersecoes.get(i);
				boolean alreadyAdd = false;				//indica se o elemento ja foi add no array ordenado
				double angleTemp1 = Math.abs(calcDeltaAngle(pI,temp , arcCenter, oldDeltaAngle));
				int intTempSize = intersecoesTmp.size();
				for(int j = 0;j < intTempSize;j++)
				{
					double angleTemp2 = Math.abs(calcDeltaAngle(pI, intersecoesTmp.get(j), arcCenter, oldDeltaAngle));
					if(angleTemp1 < angleTemp2)
					{
						intersecoesTmp.add(j, temp);
						alreadyAdd = true;
						break;
					}
				}
				if(alreadyAdd == false)
				{
					intersecoesTmp.add(temp);
				}
			}
//			System.out.println(intersecoesTmp);
			for(int i = 0;i < intersecoesTmp.size();i++)
			{
				Point3d intTmp = intersecoesTmp.get(i);
//				System.out.println(intTmp);
//				System.out.println("DeltaAngle: " + calcDeltaAngle(intersecoesTmp.get(0), intTmp, arcCenter, oldDeltaAngle));
				if(i != intersecoesTmp.size()-1)
				{
					Point3d intTmpNext = intersecoesTmp.get(i+1);
//					System.out.println(intTmpNext);
					LimitedArc segTemp = new LimitedArc(arcCenter, intTmp, calcDeltaAngle(intTmp,intTmpNext,arcCenter,oldDeltaAngle));
					arcTemp.add(segTemp);
				}
				else
				{
					Point3d intTmpNext = intersecoesTmp.get(0);
					LimitedArc segTemp = new LimitedArc(arcCenter, intTmp, calcDeltaAngle(intTmp,intTmpNext,arcCenter,oldDeltaAngle));
					arcTemp.add(segTemp);
				}
			}
		}
		else
		{
			for(int h = 0;h < intSize;h++)
			{
				intTemp = intersecoes.get(h);
				if(belongsArc(arcInv,intTemp))
				{
					if(arcTemp.size() == 0)
					{
//						if(arc.getDeltaAngle() == 2*Math.PI)
//						{
//							Point3d intTempNext = intersecoes.get(h+1);
//							LimitedArc segTemp = new LimitedArc(arcCenter, intTemp, calcDeltaAngle(intTemp,intTempNext,arcCenter,oldDeltaAngle));
//							System.err.println(calcDeltaAngle(intTemp,intTempNext,arcCenter,oldDeltaAngle));
//							arcTemp.add(segTemp);
//							segTemp = new LimitedArc(arcCenter, intTempNext, calcDeltaAngle(intTempNext,intTemp,arcCenter,oldDeltaAngle));
//							
//							System.err.println(calcDeltaAngle(intTempNext,intTemp,arcCenter,oldDeltaAngle));
//							System.err.println(intTempNext);
//
//							arcTemp.add(segTemp);
//						}
//						else
//						{
							LimitedArc segTemp = new LimitedArc(arcCenter, arcI, calcDeltaAngle(arcI,intTemp,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);
							segTemp = new LimitedArc(arcCenter, intTemp,calcDeltaAngle(intTemp,arcF,arcCenter,oldDeltaAngle));
							arcTemp.add(segTemp);
//						}
//						showArcs(arcTemp);
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
					}
				}
			}
		}
		
		if(arc.getDeltaAngle()<0)
		{
			return arrayInverter1(elementInverter1(arcTemp));
		}
//		showArcs(arcTemp);
		return arcTemp;
	}
	/**
	 * 
	 * @param center
	 * @param p1 
	 * @param p2
	 * @return deltaAngle desde p1 ate p2 no sentido antihorario
	 */
	public static double getDeltaAngle(Point3d center, Point3d p1, Point3d p2)
	{
		double saida = 0;
		saida = Math.atan2(p2.y - center.y, p2.x - center.x) - Math.atan2(p1.y - center.y, p1.x - center.x);
		if(saida < 0)
		{
			saida = 2*Math.PI + saida;
		}
		return saida;
	}
	public static ArrayList<Double> ordenaNumeros(ArrayList<Double> input)
	{
		ArrayList<Double> output = new ArrayList<Double>();
		int i = 0;
		while(i < input.size())
		{
			double minimum = getMinimumNumber(input);
			output.add(minimum);
			input.remove(minimum);
			i = 0;
		}
		return output;
	}
	public static double getMinimumNumber(ArrayList<Double> array)
	{
		double saida = array.get(0); // Assume-se que o array tem elementos (nao esta vazio)
		for(int i = 0; i < array.size(); i++)
		{
			double tmp = array.get(i);
			if (tmp < saida)
			{
				saida = tmp;
			}
		}
		return saida;
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
//			showElements(elementsIntermediario);
			ArrayList<LimitedElement> elementsIntermediario2 = validar2Path(elementsIntermediario,formaOriginal,distance);
//			showElements(elementsIntermediario2);
//			elementsValidated.add(elementsIntermediario2);
//			System.out.println("elementsInter2: " + elementsIntermediario2.size());
			ArrayList<ArrayList<LimitedElement>> elementsIntermediario3 = validar3Path(elementsIntermediario2);
//			showElements(elementsIntermediario3.get(0));
			if(elementsIntermediario3 != null)
			{
//				System.out.println("elementsInter3: " + elementsIntermediario3.size());
//				showElements(elementsIntermediario3.get(0));
				for(int j = 0;j < elementsIntermediario3.size();j++)
				{
					elementsValidated.add(elementsIntermediario3.get(j));					
				}
			}
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
//				System.out.println("Intersecoes: " + intersection);
				if (ei.isLimitedLine())
				{
					LimitedLine linei = (LimitedLine)ei;
					ArrayList<LimitedLine> lineTemp = quebraLinha(linei,intersection); 
					for(int k = 0;k < lineTemp.size();k++)
					{
						if(!isTheSamePoint(lineTemp.get(k).getInitialPoint(), lineTemp.get(k).getFinalPoint()))
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
//					System.out.println(arcTemp.size());
					for(int k = 0;k < arcTemp.size();k++)
					{
						if(!isTheSamePoint(arcTemp.get(k).getInitialPoint(), arcTemp.get(k).getFinalPoint()))
						{
							elementsIntermediario.add(arcTemp.get(k));
						}
					}
				}
			}
			if(thereIsIntersection == false)
			{
//				elementsIntermediario.add(ei);
//				if(ei.isLimitedLine() && !isTheSamePoint(ei.getInitialPoint(), ei.getFinalPoint()))
//				{
//					elementsIntermediario.add(ei);
//				}
//				else if(ei.isLimitedArc() && roundNumber(((LimitedArc)ei).getDeltaAngle(),10) != 0)
//				{
//					elementsIntermediario.add(ei);
//				}
				if(!isTheSamePoint(ei.getInitialPoint(), ei.getFinalPoint())) //Nao add elementos com PI = PF (de tamanho 0)
				{
					elementsIntermediario.add(ei);
				}
				else if(ei.isLimitedArc())
				{
					System.out.println(Math.abs(((LimitedArc)ei).getDeltaAngle()));
					if(Math.abs(((LimitedArc)ei).getDeltaAngle()) == 2*Math.PI) //Circulos (angulo 2pi) possuem PI = PF
					{
						elementsIntermediario.add(ei); //add circulos
					}
				}
			}
			
		}
//		showElements(elementsIntermediario);
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
			if(roundNumber(minimumDistance(formaOriginal, ei0),7) >= distance)
			{
				elementsIntermediario2.add(ei0);
			}
		}
//		System.out.println("Elementos intermediarios2: " + elementsIntermediario2.size());
//		showElements(elementsIntermediario2);
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
			int count = 0;
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
					if(count != j)
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
					}
				}
				if(hasNoFinalPoint)
				{
					elementsIntermediario2.remove(ei0);
					if(iter.hasNext())
					{
						ei0new = elementsIntermediario2.get(0);
					}
					
				}
//			System.out.println("Numero de lacos: " + numeroDeLacos);
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
//				System.err.println("NuloArcLine:" + intersectionPoint(arci, linej));	
//				System.err.println("NuloArcLine:" + arci.getCenter() + "\t" + linej.getInitialPoint());	
//				System.err.println("NuloArcLine:" + intersectionPoint(arci, linej));	
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
		if(roundNumber(p1.x, 7) == roundNumber(p2.x,7) 
				&& roundNumber(p1.y, 7) == roundNumber(p2.y, 7) 
				&& roundNumber(p1.z, 7) == roundNumber(p2.z, 7)
				)
		{
			return true;
		}
		else
		{
			return false;
		}
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
//	public static isInside(ArrayList<LimitedElement> arrayElement, LimitedElement element)
//	{
//		
//	}
	public static LimitedLine absoluteParallel(LimitedLine line, double distance, boolean inside)
	{
		double angleLine = angle(minus(line.getFinalPoint(), line.getInitialPoint()));
		double newDistanceAngle = angleLine+Math.PI/2;
		double x = Math.cos(newDistanceAngle);
		double y = Math.sin(newDistanceAngle);
		Point3d unitDistance = new Point3d(x,y,line.getInitialPoint().z);
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
	
	public static LimitedArc parallelArc(LimitedArc arc, double distance, boolean inside, boolean isBoss)
	{
//		boolean isBoss = !inside;
//		Point3d newInitialPoint = null;
//		LimitedArc newArc = null;
		double newRadius;
		if (arc.getDeltaAngle()<0) //Sentido horario
		{
			if(isBoss) //Se for boss
			{
				newRadius = arc.getRadius()+distance; //o novo raio sera o antigo mais a distancia da paralela
			}
			else //Se nao for boss
			{
				if(inside) //Se for para dentro
				{
					newRadius = arc.getRadius()+distance;
				}
				else //Se for para fora
				{
					newRadius = arc.getRadius()-distance;
				}
			}
		}
		else //Sentido anti-horario
		{
			if(isBoss) //Se for boss
			{
				newRadius = arc.getRadius()+distance; //o novo raio sera o antigo mais a distancia da paralela
			}
			else //Se nao for boss
			{
				if(inside) //Se for para dentro
				{
					newRadius = arc.getRadius()-distance;
				}
				else //Se for para fora
				{
					newRadius = arc.getRadius()+distance;
				}
			}
		}
		
//		System.out.println("RADIUS: " + newRadius);
		return (new LimitedArc(arc.getCenter(), plus(arc.getCenter(),multiply(newRadius,unitVector(arc.getCenter(),arc.getInitialPoint()))), arc.getDeltaAngle()));

		//Se houver erro, descomentar a parte a baixo e comentar a parte a cima
//		if (arc.getDeltaAngle()<0)
//		{
//			if(!isBoss)
//			{
//				double newRadius = arc.getRadius()+distance; //Para dentro (da cavidade)
//				newInitialPoint = plus(arc.getCenter(),multiply(newRadius,unitVector(arc.getCenter(),arc.getInitialPoint())));
//				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
////				ArrayList<LimitedArc> arcs = new ArrayList<LimitedArc>();
////				arcs.add(newArc);
//			}
//			else
//			{
////				if(arc.getRadius() > distance)
////				{
//				double newRadius = arc.getRadius()-distance; //Para fora (da protuberancia
//				newInitialPoint = plus(arc.getCenter(),multiply(newRadius,unitVector(arc.getCenter(),arc.getInitialPoint())));
//				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
////				}
//			}
//			
//		}
//		else
//		{
//			if(!isBoss)
//			{
////				if(arc.getRadius() > distance)
////				{
//				double newRadius = arc.getRadius()-distance;
//				newInitialPoint = plus(arc.getCenter(),multiply(newRadius,unitVector(arc.getCenter(),arc.getInitialPoint())));
//				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
////				}
//			}
//			else
//			{
//				double newRadius = arc.getRadius()+distance;
//				newInitialPoint = plus(arc.getCenter(),multiply(newRadius,unitVector(arc.getCenter(),arc.getInitialPoint())));
//				newArc = new LimitedArc(arc.getCenter(), newInitialPoint, arc.getDeltaAngle());
//				ArrayList<LimitedArc> arcs = new ArrayList<LimitedArc>();
//				arcs.add(newArc);
//			}
//		}
////		System.out.println("Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " delta " + arc.getDeltaAngle()*180/Math.PI + " radius " + arc.getRadius());
//		return newArc;
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
				System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y  + ", " + arc.getCenter().z + ")" + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ", " + arc.getInitialPoint().z + ")" + "," + arc.getDeltaAngle() + ");");		
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				//System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
				System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + line.getInitialPoint().x + "," + line.getInitialPoint().y + ", " + line.getInitialPoint().z + ");" + ",new Point3d(" + line.getFinalPoint().x + "," + line.getFinalPoint().y + ", " + line.getFinalPoint().z + ");");

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
			System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + line.getInitialPoint().x + "," + line.getInitialPoint().y  + ", " + line.getInitialPoint().z + ")"  + ",new Point3d(" + line.getFinalPoint().x + "," + line.getFinalPoint().y + ", " + line.getFinalPoint().z + ")" );	
			i++;			
		}

	}
	public static void showArcs(ArrayList<LimitedArc> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			LimitedArc arc = (LimitedArc)e;
			System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y + ", " + arc.getCenter().z + ")"  + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ", " + arc.getInitialPoint().z + ")"  + "," + arc.getDeltaAngle() + ");");							
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
//		System.err.println(line.getInitialPoint().z + "\t" + line.getFinalPoint());
		

		LimitedArc arcTmp = new LimitedArc(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle());
		if(arc.getDeltaAngle() < 0)
		{
			Point3d temp1 = arc.getFinalPoint();
//			System.out.println((temp1 == arc.getInitialPoint()));
			if(Math.abs(arc.getDeltaAngle()) == 2*Math.PI)
			{
				temp1 = arc.getInitialPoint();
			}
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
		
		if (roundNumber(line.getInitialPoint().x,10) == roundNumber(line.getFinalPoint().x,10))
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
		boolean p1BelongsArc = belongsArc(arcTmp,P1);
		boolean p1BelongsLine = belongs(line,P1);
		boolean p2BelongsArc = belongsArc(arcTmp,P2);
		boolean p2BelongsLine = belongs(line,P2);
		if(arc.getDeltaAngle() == 2*Math.PI)
		{
			p1BelongsArc = true;
			p2BelongsArc = true;
		}
		if(p1BelongsArc && p1BelongsLine)
		{
			intersection = new ArrayList<Point3d>();
			intersection.add(P1);
		}
		if(p2BelongsArc && p2BelongsLine)
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
			if(Math.abs(arc1.getDeltaAngle()) == 2*Math.PI)
			{
				temp1 = arc1.getInitialPoint();
			}
			arc1Tmp = new LimitedArc(arc1.getCenter(), temp1, -arc1.getDeltaAngle());
		}
		if(arc2.getDeltaAngle() < 0)
		{
			//Point3d temp = arc2.getInitialPoint();
			Point3d temp1 = arc2.getFinalPoint();
			if(Math.abs(arc2.getDeltaAngle()) == 2*Math.PI)
			{
				temp1 = arc2.getInitialPoint();
			}
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
	public static ArrayList<LimitedElement> elementInverter(ArrayList<LimitedElement> toInvert)
	{
		ArrayList<LimitedElement> inverted = new ArrayList<LimitedElement>();
		for(LimitedElement elementTmp:toInvert)
		{
			if(elementTmp.isLimitedLine())
			{
				inverted.add(new LimitedLine(elementTmp.getFinalPoint(), elementTmp.getInitialPoint()));
			}
			else if(elementTmp.isLimitedArc())
			{
				LimitedArc arcTmp = (LimitedArc)elementTmp;
				inverted.add(new LimitedArc(arcTmp.getCenter(), arcTmp.getFinalPoint(), -arcTmp.getDeltaAngle()));
			}
		}
		return inverted;
	}
	public static ArrayList<LimitedArc> elementInverter1(ArrayList<LimitedArc> toInvert)
	{
		ArrayList<LimitedArc> inverted = new ArrayList<LimitedArc>();
		for(LimitedArc arcTmp:toInvert)
		{
//				LimitedArc arcTmp = (LimitedArc)elementTmp;
				inverted.add(new LimitedArc(arcTmp.getCenter(), arcTmp.getFinalPoint(), -arcTmp.getDeltaAngle()));
		}
		return inverted;
	}
	public static ArrayList<LimitedElement> arrayInverter(ArrayList<LimitedElement> toInvert)
	{
		ArrayList<LimitedElement> inverted = new ArrayList<LimitedElement>();
		for(int i = toInvert.size()-1;i >-1;i--)
		{
			inverted.add(toInvert.get(i));
		}
		return inverted;
	}
	public static ArrayList<LimitedArc> arrayInverter1(ArrayList<LimitedArc> toInvert)
	{
		ArrayList<LimitedArc> inverted = new ArrayList<LimitedArc>();
		for(int i = toInvert.size()-1;i >-1;i--)
		{
			inverted.add(toInvert.get(i));
		}
		return inverted;
	}
	
//	public static boolean insidePocketLineLine(LimitedLine linePocket, LimitedLine line)
//	{
//		
//	}
//	public static boolean insidePocketArcLine(LimitedArc arcPocket, LimitedLine line)
//	{
//		
//	}
//	public static boolean insidePocketArcLine(LimitedArc arc, LimitedLine linePocket)
//	{
//		
//	}
//	public static boolean insidePocketArcArc(LimitedArc arcPocket, LimitedArc arc)
//	{
//		
//	}
//	public static boolean insidePocketElements(LimitedElement e1, LimitedElement e2)
//	{
//		
//	}
	/**
	 * Antes de utilizar, garantir que n�o ha intersecao entre os elementos
	 * @param pocketElements
	 * @param element
	 * @return
	 */
	public static boolean insidePocket(ArrayList<LimitedElement> pocketElements, LimitedElement element)
	{
		boolean inside = true;
		GeneralPath gp = (GeneralPath)Face.getShape(pocketElements); 
//		for(LimitedElement elementTmp:pocketElements)
//		{
//			
//		}
		if(!gp.contains(new Point2D.Double(element.getInitialPoint().x, element.getInitialPoint().y)))
		{
			inside = false;
		}
		return inside;
	}
}
