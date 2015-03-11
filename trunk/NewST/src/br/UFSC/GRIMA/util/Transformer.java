package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class Transformer {
	ArrayList<Point3d> arrayList = null;

	public static ArrayList<Point3d> transformVectorToArray(Vector<Ponto> vector) 
	{

		Iterator<Ponto> iterator = vector.iterator();
		int size = vector.size() - 1;
		ArrayList<Point3d> arrayList = new ArrayList<Point3d>(size+1);
		for (int i = 0; i <= size; i++) {
			Ponto p = (Ponto) vector.get(i);
			arrayList.add(new Point3d(p.getX(), p.getY(), p.getZ()));
		}

		return arrayList;
	}
	
	public static ArrayList<Path> transformLimitedElementsInPaths(ArrayList<LimitedElement> elements)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		for(LimitedElement elementTmp : elements)
		{
			if(elementTmp.isLimitedLine())
			{
				LinearPath lineTmp = new LinearPath(elementTmp.getInitialPoint(), elementTmp.getFinalPoint());
				saida.add(lineTmp);
			}
			else if(elementTmp.isLimitedArc())
			{
				LimitedArc arcTmp = (LimitedArc)elementTmp;
				CircularPath circularTmp = new CircularPath(arcTmp.getCenter(), arcTmp.getInitialPoint(), arcTmp.getFinalPoint(), arcTmp.getDeltaAngle());
				saida.add(circularTmp);
			}
		}
		return saida;
	}
	/**
	 *  transforma paths em limitedElements
	 * @param paths --> paths
	 * @return
	 */
	public static ArrayList<LimitedElement> transformPathsInLimitedElements(ArrayList<Path> paths)
	{
		ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>();
		for(Path pathTmp : paths)
		{
			if(pathTmp.isLine())
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				LimitedLine lineTmp = new LimitedLine(linearTmp.getInitialPoint(), linearTmp.getFinalPoint());
				saida.add(lineTmp);
			}
			else if(pathTmp.isCircular())
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
				LimitedArc arcTmp;

				if(GeometricOperations.isTheSamePoint(circularTmp.getInitialPoint(), circularTmp.getFinalPoint()))
				{
					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), 2 * Math.PI, CircularPath.CCW);	
				}
				else
				{
					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), circularTmp.getAngulo());	
				}
				saida.add(arcTmp);
			}
		}
		return saida;
	}
	public static ArrayList<LinearPath> linerizatePaths(ArrayList<Path> paths)
	{
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		int numeroDePontos = 2;
		for(Path pathTmp:paths)
		{
			if(pathTmp.getClass() == LinearPath.class)
			{
				saida.add((LinearPath)pathTmp);
			}
			else if(pathTmp.getClass() == CircularPath.class)
			{
				CircularPath circularPathTmp = (CircularPath)pathTmp;
				Point2D[] interpolationPoints = Cavidade.determinarPontosEmCircunferencia(circularPathTmp.getCenter(), circularPathTmp.getInitialPoint(), circularPathTmp.getAngulo(), numeroDePontos);
				for(int i = 0;i < interpolationPoints.length-2;i++)
				{
					saida.add(new LinearPath(new Point3d(interpolationPoints[i].getX(),interpolationPoints[i].getY(),circularPathTmp.getCenter().z), new Point3d(interpolationPoints[i+1].getX(),interpolationPoints[i+1].getY(),circularPathTmp.getCenter().z)));
				}
			}
		}
		return saida;
	}
	public static ArrayList<Path> linerizatePaths1(ArrayList<Path> paths)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		for(LinearPath linePath:linerizatePaths(paths))
		{
			saida.add(linePath);
		}
		return saida;
	}
	public static ArrayList<Point2D> arrayPoint2DtoPoint3d(ArrayList<Point3d> arrayPoint3d)
	{
		ArrayList<Point2D> arrayPoint2D = new ArrayList<Point2D>();
		for(Point3d point3d:arrayPoint3d)
		{
			arrayPoint2D.add(new Point2D.Double(point3d.x,point3d.y));
		}
		return arrayPoint2D;
	}
	public static ArrayList<ArrayList<Point2D>> matrixPoint2DtoPoint3d(ArrayList<ArrayList<Point3d>> matrixPoint3d)
	{
		ArrayList<ArrayList<Point2D>> matrixPoint2D = new ArrayList<ArrayList<Point2D>>();
		for(ArrayList<Point3d> arrayPoint3d:matrixPoint3d)
		{
			matrixPoint2D.add(arrayPoint2DtoPoint3d(arrayPoint3d));
		}
		return matrixPoint2D;
	}
	public static ArrayList<LimitedElement> arrayDimensioning(ArrayList<ArrayList<LimitedElement>> matrix)
	{
		ArrayList<LimitedElement> arrayDimensioned = new ArrayList<LimitedElement>();
		for(ArrayList<LimitedElement> array:matrix)
		{
			for(LimitedElement e:array)
			{
				arrayDimensioned.add(e);
			}
		}
		return arrayDimensioned;
	}
	public static ArrayList<Point2D> limitedElementToPoints2D(ArrayList<LimitedElement> arrayLimitedElements)
	{
		ArrayList<Point2D> arrayPoint2D = new ArrayList<Point2D>();
		for(LimitedElement e : arrayLimitedElements)
		{
			if(e.isLimitedLine())
			{
				Point2D point = new Point2D.Double(e.getInitialPoint().x,e.getInitialPoint().y);
				arrayPoint2D.add(point);
				System.out.println("point: " + point.getX() + ", " + point.getY());
			}
			else if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				for(Point2D point:Cavidade.determinarPontosEmCircunferencia(arc.getCenter(), arc.getInitialPoint(), arc.getDeltaAngle(), 10))
				{
					arrayPoint2D.add(point);
					System.out.println("point: " + point.getX() + ", " + point.getY());
				}
			}
		}
		return arrayPoint2D;
	}
	
//	public static ArrayList<LimitedElement> ShapeToElements(GeneralPath gp)
//	{
//		
//	}
}
