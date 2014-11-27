package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;

public class ObjectTransformator 
{
	public static ArrayList<Path> transformLimitedElementsInPaths(ArrayList<LimitedElement> elements)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		for(LimitedElement elementTmp : elements)
		{
			if(elementTmp.isLimitedLine())
			{
//				LinearPath linearTmp = (LinearPath)pathTmp;
				LinearPath lineTmp = new LinearPath(elementTmp.getInitialPoint(), elementTmp.getFinalPoint());
				saida.add(lineTmp);
			}
			else if(elementTmp.isLimitedArc())
			{
				LimitedArc arcTmp = (LimitedArc)elementTmp;
//				LimitedArc arcTmp = new LimitedArc(circularTmp.getInitialPoint(), circularTmp.getFinalPoint(), circularTmp.getCenter());
				CircularPath circularTmp = new CircularPath(arcTmp.getCenter(), arcTmp.getInitialPoint(), arcTmp.getFinalPoint(), arcTmp.getDeltaAngle());
//				if(GeometricOperations.isTheSamePoint(circularTmp.getInitialPoint(), circularTmp.getFinalPoint()))
//				{
//					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), 2 * Math.PI, CircularPath.CCW);	
//				}
//				else
//				{
//					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), circularTmp.getAngulo());	
//				}
				saida.add(circularTmp);
			}
		}
		return saida;
	}
	public static ArrayList<LinearPath> linerizatePaths(ArrayList<Path> paths)
	{
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		int numeroDePontos = 20;
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
}
