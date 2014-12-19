package br.UFSC.GRIMA.util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class Triangulation 
{
	ArrayList<LimitedElement> elements;
	public Triangulation(ArrayList<LimitedElement> elements)
	{
		this.elements = Transformer.transformPathsInLimitedElements(Transformer.linerizatePaths1(Transformer.transformLimitedElementsInPaths(elements)));
	}
	
	public ArrayList<ArrayList<Point2D>> getTriangules()
	{
		ArrayList<LimitedElement> elementsTmp = new ArrayList<LimitedElement>(); //Copia do array original (onde iremos add as linhas dos triangulos)
		for(LimitedElement e:elements)
		{
			elementsTmp.add(e);
		}
		ArrayList<ArrayList<Point3d>> triangules = new ArrayList<ArrayList<Point3d>>();
//		for(LimitedElement e:elements)
		for(int i=0;i<elements.size();i++)
		{
			LimitedElement eAtualI = elements.get(i);
			Point3d ePointInitI = eAtualI.getInitialPoint(); //Pivot
			Point3d ePointFinalI = eAtualI.getFinalPoint();
			System.out.println("ePointInitI: " + ePointInitI);
			System.out.println("ePointFinalI: " + ePointFinalI);
			System.out.println("\n");
			for(int j = 0;j < elements.size();j++)
			{
				LimitedElement eAtualJ = elements.get(j);
				Point3d ePointInitJ = eAtualJ.getInitialPoint();
				Point3d ePointFinalJ = eAtualJ.getFinalPoint();
				if((!GeometricOperations.isTheSamePoint(ePointInitI, ePointInitJ)) && 
						(!GeometricOperations.isTheSamePoint(ePointFinalI, ePointInitJ)) && 
							(!GeometricOperations.isTheSamePoint(ePointInitI, ePointFinalJ)))
				{
					System.out.println("ePointInitJ: " + ePointInitJ);
					System.out.println("ePointFinalJ: " + ePointFinalJ);
					LimitedLine lineTrianguleTmp = new LimitedLine(ePointInitI,ePointInitJ);
					LimitedLine lineTrianguleValidation1 = new LimitedLine(ePointInitI,ePointFinalJ);
					LimitedLine lineTrianguleValidation2 = new LimitedLine(ePointInitJ,ePointFinalI);
					boolean lineIntersected = false;
					boolean lineValidation1Intersected = false;
					boolean lineValidation2Intersected = false;
					//line validation
					for(LimitedElement eTmp:elementsTmp)
					{
						if((GeometricOperations.intersectionElements(lineTrianguleTmp, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleTmp)))
//						if((GeometricOperations.intersectionElements(lineTrianguleTmp, eTmp) != null))
						{
							lineIntersected = true;
						}
						if((GeometricOperations.intersectionElements(lineTrianguleValidation1, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleValidation1)))
//						if((GeometricOperations.intersectionElements(lineTrianguleValidation1, eTmp) != null))
						{
							lineValidation1Intersected = true;
						}
						if((GeometricOperations.intersectionElements(lineTrianguleValidation2, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleValidation2)))
//						if((GeometricOperations.intersectionElements(lineTrianguleValidation2, eTmp) != null))
						{
							lineValidation2Intersected = true;
						}
					}
					if(!lineIntersected)
					{
						System.out.println("lineIntersected");
						elementsTmp.add(lineTrianguleTmp);
						
						if(!lineValidation1Intersected)
						{
							System.out.println("lineValidation1Intersected");
							ArrayList<Point3d> trianguleTmp = new ArrayList<Point3d>();
							trianguleTmp.add(ePointInitI);
							trianguleTmp.add(ePointInitJ);
							trianguleTmp.add(ePointFinalJ);
							System.out.println(trianguleTmp);
							boolean isTheSameTriangule = false;
							for(ArrayList<Point3d> arrayTmp:triangules)
							{
								if(isTheSameTriangule(arrayTmp, trianguleTmp))
								{
									isTheSameTriangule = true;
								}
							}
							if(!isTheSameTriangule)
							{
								System.out.println("isNotTheSameTriangule");
								triangules.add(trianguleTmp);
							}
						}
						if(!lineValidation2Intersected)
						{
							System.out.println("lineValidation2Intersected");
							ArrayList<Point3d> trianguleTmp = new ArrayList<Point3d>();
							trianguleTmp.add(ePointInitI);
							trianguleTmp.add(ePointInitJ);
							trianguleTmp.add(ePointFinalI);
							System.out.println(trianguleTmp);
							boolean isTheSameTriangule = false;
							for(ArrayList<Point3d> arrayTmp:triangules)
							{
								if(isTheSameTriangule(arrayTmp, trianguleTmp))
								{
									isTheSameTriangule = true;
								}
							}
							if(!isTheSameTriangule)
							{
								System.out.println("isNotTheSameTriangule");
								triangules.add(trianguleTmp);
							}
						}
					}
				}
			}
			System.out.println("\n");
		}
		return Transformer.matrixPoint2DtoPoint3d(triangules);
	}
	public boolean isTheSameTriangule(ArrayList<Point3d> triangule1, ArrayList<Point3d> triangule2)
	{
//		ArrayList<Boolean> isTheSamePoint = new ArrayList<Boolean>();
		int aux = 0;
		for(Point3d pointTmp1:triangule1)
		{
			for(Point3d pointTmp2:triangule2)
			{
				if(GeometricOperations.isTheSamePoint(pointTmp1, pointTmp2))
				{
//					isTheSamePoint.add(true);
					aux++;
				}
			}
		}
//		boolean isTheSameTriangule = true;
//		for(Boolean booleanTmp:isTheSamePoint)
//		{
//			if(!booleanTmp)
//			{
//				isTheSameTriangule = false;
//			}
//		}
//		return isTheSameTriangule;
		if(aux == 3)
		{
			return true;
		}
		return false;
	}
	public double getArea(ArrayList<ArrayList<Point2D>> triangules)
	{
		double resposta = 0;
		for(ArrayList<Point2D> trianguleTmp:triangules)
		{
			resposta += getAreaTriangule(trianguleTmp);
		}
		return resposta;
	}
	public double getAreaTriangule(ArrayList<Point2D> triangule)
	{
		//double resposta = 0;
		double a = triangule.get(0).distance(triangule.get(1));
		double b = triangule.get(1).distance(triangule.get(2));
		double c = triangule.get(2).distance(triangule.get(0));
		double s = (a+b+c)/2;
		double area = Math.sqrt(s*(s-a)*(s-b)*(s-c));
		return area;
	}
}
