package br.UFSC.GRIMA.util;

import java.awt.Point;
import java.awt.geom.GeneralPath;
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
//	ArrayList<LimitedElement> elements;
//	public Triangulation(ArrayList<LimitedElement> elements)
//	{
//		this.elements = Transformer.transformPathsInLimitedElements(Transformer.linerizatePaths1(Transformer.transformLimitedElementsInPaths(elements)));
//	}
	ArrayList<Point2D> polygon = new ArrayList<Point2D>();
	ArrayList<Integer> triangulesIndex = new ArrayList<Integer>();
	public Triangulation(ArrayList<Point2D> polygon)
	{
		//polygon = new ArrayList<Point2D>();
		//triangulesIndex = new ArrayList<Integer>();
		this.polygon = polygon;
		this.triangulesIndex = triangulation(polygon);
	}
	public ArrayList<Integer> getTriangulesIndex()
	{
//		return triangulation(polygon);
		return triangulesIndex;
	}
	public double getArea(/*ArrayList<Integer> triangulesIndex*/)
	{
		int flag = 0;
		ArrayList<Integer> triangulesIndexTmp = new ArrayList<Integer>();
		double areaTotal = 0;
		for(int i = 0;i < triangulesIndex.size();i++)
		{
			triangulesIndexTmp.add(triangulesIndex.get(i));
			flag++;
			if(flag==3)
			{
				double a = polygon.get(triangulesIndexTmp.get(0)).distance(polygon.get(triangulesIndexTmp.get(1)));
				double b = polygon.get(triangulesIndexTmp.get(0)).distance(polygon.get(triangulesIndexTmp.get(2)));
				double c = polygon.get(triangulesIndexTmp.get(1)).distance(polygon.get(triangulesIndexTmp.get(2)));
				double s = (a+b+c)/2;
				double areaTrianguleTmp = Math.sqrt(s*(s-a)*(s-b)*(s-c));
				areaTotal = areaTotal+areaTrianguleTmp;
				triangulesIndexTmp = new ArrayList<Integer>();
				flag = 0;
			}
		}
		return areaTotal;
	}
//	public ArrayList<ArrayList<Point2D>> getTriangules()
//	{
//		ArrayList<LimitedElement> elementsTmp = new ArrayList<LimitedElement>(); //Copia do array original (onde iremos add as linhas dos triangulos)
//		for(LimitedElement e:elements)
//		{
//			elementsTmp.add(e);
//		}
//		ArrayList<ArrayList<Point3d>> triangules = new ArrayList<ArrayList<Point3d>>();
////		for(LimitedElement e:elements)
//		for(int i=0;i<elements.size();i++)
//		{
//			LimitedElement eAtualI = elements.get(i);
//			Point3d ePointInitI = eAtualI.getInitialPoint(); //Pivot
//			Point3d ePointFinalI = eAtualI.getFinalPoint();
//			System.out.println("ePointInitI: " + ePointInitI);
//			System.out.println("ePointFinalI: " + ePointFinalI);
//			System.out.println("\n");
//			for(int j = 0;j < elements.size();j++)
//			{
//				LimitedElement eAtualJ = elements.get(j);
//				Point3d ePointInitJ = eAtualJ.getInitialPoint();
//				Point3d ePointFinalJ = eAtualJ.getFinalPoint();
//				if((!GeometricOperations.isTheSamePoint(ePointInitI, ePointInitJ)) && 
//						(!GeometricOperations.isTheSamePoint(ePointFinalI, ePointInitJ)) && 
//							(!GeometricOperations.isTheSamePoint(ePointInitI, ePointFinalJ)))
//				{
//					System.out.println("ePointInitJ: " + ePointInitJ);
//					System.out.println("ePointFinalJ: " + ePointFinalJ);
//					LimitedLine lineTrianguleTmp = new LimitedLine(ePointInitI,ePointInitJ);
//					LimitedLine lineTrianguleValidation1 = new LimitedLine(ePointInitI,ePointFinalJ);
//					LimitedLine lineTrianguleValidation2 = new LimitedLine(ePointInitJ,ePointFinalI);
//					boolean lineIntersected = false;
//					boolean lineValidation1Intersected = false;
//					boolean lineValidation2Intersected = false;
//					//line validation
//					for(LimitedElement eTmp:elementsTmp)
//					{
//						if((GeometricOperations.intersectionElements(lineTrianguleTmp, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleTmp)))
////						if((GeometricOperations.intersectionElements(lineTrianguleTmp, eTmp) != null))
//						{
//							lineIntersected = true;
//						}
//						if((GeometricOperations.intersectionElements(lineTrianguleValidation1, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleValidation1)))
////						if((GeometricOperations.intersectionElements(lineTrianguleValidation1, eTmp) != null))
//						{
//							lineValidation1Intersected = true;
//						}
//						if((GeometricOperations.intersectionElements(lineTrianguleValidation2, eTmp) != null) && (GeometricOperations.insideShape(elements, lineTrianguleValidation2)))
////						if((GeometricOperations.intersectionElements(lineTrianguleValidation2, eTmp) != null))
//						{
//							lineValidation2Intersected = true;
//						}
//					}
//					if(!lineIntersected)
//					{
//						System.out.println("lineIntersected");
//						elementsTmp.add(lineTrianguleTmp);
//						
//						if(!lineValidation1Intersected)
//						{
//							System.out.println("lineValidation1Intersected");
//							ArrayList<Point3d> trianguleTmp = new ArrayList<Point3d>();
//							trianguleTmp.add(ePointInitI);
//							trianguleTmp.add(ePointInitJ);
//							trianguleTmp.add(ePointFinalJ);
//							System.out.println(trianguleTmp);
//							boolean isTheSameTriangule = false;
//							for(ArrayList<Point3d> arrayTmp:triangules)
//							{
//								if(isTheSameTriangule(arrayTmp, trianguleTmp))
//								{
//									isTheSameTriangule = true;
//								}
//							}
//							if(!isTheSameTriangule)
//							{
//								System.out.println("isNotTheSameTriangule");
//								triangules.add(trianguleTmp);
//							}
//						}
//						if(!lineValidation2Intersected)
//						{
//							System.out.println("lineValidation2Intersected");
//							ArrayList<Point3d> trianguleTmp = new ArrayList<Point3d>();
//							trianguleTmp.add(ePointInitI);
//							trianguleTmp.add(ePointInitJ);
//							trianguleTmp.add(ePointFinalI);
//							System.out.println(trianguleTmp);
//							boolean isTheSameTriangule = false;
//							for(ArrayList<Point3d> arrayTmp:triangules)
//							{
//								if(isTheSameTriangule(arrayTmp, trianguleTmp))
//								{
//									isTheSameTriangule = true;
//								}
//							}
//							if(!isTheSameTriangule)
//							{
//								System.out.println("isNotTheSameTriangule");
//								triangules.add(trianguleTmp);
//							}
//						}
//					}
//				}
//			}
//			System.out.println("\n");
//		}
//		return Transformer.matrixPoint2DtoPoint3d(triangules);
//	}
//	public boolean isTheSameTriangule(ArrayList<Point3d> triangule1, ArrayList<Point3d> triangule2)
//	{
////		ArrayList<Boolean> isTheSamePoint = new ArrayList<Boolean>();
//		int aux = 0;
//		for(Point3d pointTmp1:triangule1)
//		{
//			for(Point3d pointTmp2:triangule2)
//			{
//				if(GeometricOperations.isTheSamePoint(pointTmp1, pointTmp2))
//				{
////					isTheSamePoint.add(true);
//					aux++;
//				}
//			}
//		}
////		boolean isTheSameTriangule = true;
////		for(Boolean booleanTmp:isTheSamePoint)
////		{
////			if(!booleanTmp)
////			{
////				isTheSameTriangule = false;
////			}
////		}
////		return isTheSameTriangule;
//		if(aux == 3)
//		{
//			return true;
//		}
//		return false;
//	}
//	public double getArea(ArrayList<ArrayList<Point2D>> triangules)
//	{
//		double resposta = 0;
//		for(ArrayList<Point2D> trianguleTmp:triangules)
//		{
//			resposta += getAreaTriangule(trianguleTmp);
//		}
//		return resposta;
//	}
//	public double getAreaTriangule(ArrayList<Point2D> triangule)
//	{
//		//double resposta = 0;
//		double a = triangule.get(0).distance(triangule.get(1));
//		double b = triangule.get(1).distance(triangule.get(2));
//		double c = triangule.get(2).distance(triangule.get(0));
//		double s = (a+b+c)/2;
//		double area = Math.sqrt(s*(s-a)*(s-b)*(s-c));
//		return area;
//	}
	
	public ArrayList<Integer> triangulation(ArrayList<Point2D> polygon)
	{		
		ArrayList<ArrayList<Point2D>> triangles = new ArrayList<ArrayList<Point2D>>();
		ArrayList<Point2D> oldPolygon = new ArrayList<Point2D>();
		ArrayList<Integer> oldIndexes = new ArrayList<Integer>();

		ArrayList<Integer> allIndexFace = new ArrayList<Integer>();
		int iPolygon=1;
		
		for (int i=0;i<polygon.size();i++)
		{
			//System.out.println("Adding Point:"+polygon.get(i));
			oldPolygon.add(polygon.get(i));
			oldIndexes.add(i);
		}
		
		while (oldPolygon.size()>=4)
		{
			ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
			ArrayList<Integer> newIndexes = new ArrayList<Integer>();
			ArrayList<Integer> tempTriangleIndex = new ArrayList<Integer>();
			ArrayList<Integer> triangleIndex = new ArrayList<Integer>();
//			System.out.println("################################################");
//			System.out.println(iPolygon+ " Polygon");
//			System.out.println("################################################");
			
			//newPolygon = cutEar(oldPolygon);
			
			tempTriangleIndex = cutEarByIndex(oldPolygon);
			System.err.println("Index: " + tempTriangleIndex.size());
			//System.out.println("tempTriangleIndex: "+tempTriangleIndex);
			triangleIndex.add(oldIndexes.get(tempTriangleIndex.get(0)));
			triangleIndex.add(oldIndexes.get(tempTriangleIndex.get(1)));
			triangleIndex.add(oldIndexes.get(tempTriangleIndex.get(2)));
			
			//System.out.println(allIndexFace.size() + " IndexTriangle: " + triangleIndex);
			
			allIndexFace.add(triangleIndex.get(0));
			allIndexFace.add(triangleIndex.get(1));
			allIndexFace.add(triangleIndex.get(2));
			
			for(int i=0;i<oldPolygon.size();i++)
			{
				if(i!=tempTriangleIndex.get(1))
				{
					newIndexes.add(oldIndexes.get(i));
				}
			}
			
			oldIndexes=new ArrayList<Integer>();

			for (int i=0;i<newIndexes.size();i++)
			{
				//System.out.println("Adding Point:"+newPolygon.get(i));
				oldIndexes.add(newIndexes.get(i));
			}			
						
			oldPolygon = new ArrayList<Point2D>();
			
			iPolygon++;

			for (int i=0;i<oldIndexes.size();i++)
			{
				//System.out.println("Adding Point:"+oldIndexes.get(i));
				oldPolygon.add(polygon.get(oldIndexes.get(i)));
			}
		}
		
		allIndexFace.add(oldIndexes.get(0));
		allIndexFace.add(oldIndexes.get(1));
		allIndexFace.add(oldIndexes.get(2));
		
		//triangles.add(oldPolygon);
		//System.out.println(" --------------------------------------------------");
		//System.out.println(" - Triangle " + triangles.size() + " " + oldPolygon);
		return allIndexFace;
//		return oldPolygon;
	}

	public ArrayList<Integer> cutEarByIndex(ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
		ArrayList<Integer> cutIndex = new ArrayList<Integer>();
		GeneralPath forma=new GeneralPath();
		int current=0, after=0, before=0;
		newPolygon = new ArrayList<Point2D>();
		forma.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
		
		for (int i=1;i<polygon.size();i++)
		{
			forma.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
		}
		forma.closePath();
		
		if (polygon.size()==3) //se for um triangulo
		{
			for (int i =0;i< polygon.size();i++)
			{
				newPolygon.add(polygon.get(i));
			}
		}
		else
		{
			for (int i = 0; i < polygon.size(); i++)
			{
				boolean ear=false;
				ArrayList<Point2D> pTemp = new ArrayList<Point2D>();
				
				pTemp = pointIteration(i,polygon);
				ear = isEar(i, polygon);
				//System.out.println(i + " alfa " + ear +" "+ 1/Math.PI*180*solveAngle(pTemp.get(0), pTemp.get(1), pTemp.get(2), forma) + " " + pTemp.get(0));				
				
				//System.out.println(" ear " + ear);
				if (ear)
				{
					current=i;
					if(current==0)
					{
						before=polygon.size()-1;
					}
					else
					{
						before=current-1;
					}
					
					if(current==polygon.size()-1)
					{
						after=0;
					}
					else
					{
						after=current+1;
					}
	//				triangles.add(pTemp);
//					System.out.println("Triangle " + triangles.size() + " " + pTemp);
					
					cutIndex.add(before);
					cutIndex.add(current);
					cutIndex.add(after);
					/*
					newPolygon = new ArrayList<Point2D>();
					for (int j=0;j< polygon.size();j++)
					{
						if (j!=current)
						{
							newPolygon.add(polygon.get(j));
						}
					}		
					*/
					break;
				}
			}
		}
		return cutIndex;
	}
	
	
	public ArrayList<Point2D> cutEar(ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
		GeneralPath forma=new GeneralPath();
		int current=0;
		newPolygon = new ArrayList<Point2D>();
		forma.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
		
		for (int i=1;i<polygon.size();i++)
		{
			forma.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
		}
		forma.closePath();
		
		if (polygon.size()==3)
		{
			for (int i =0;i< polygon.size();i++)
			{
				newPolygon.add(polygon.get(i));
			}
		}
		else
		{
			for (int i = 0; i < polygon.size(); i++)
			{
				boolean ear=false;
				ArrayList<Point2D> pTemp = new ArrayList<Point2D>();
				
				pTemp = pointIteration(i,polygon);
				ear = isEar(i, polygon);
				//System.out.println(i + " alfa " + ear +" "+ 1/Math.PI*180*solveAngle(pTemp.get(0), pTemp.get(1), pTemp.get(2), forma) + " " + pTemp.get(0));				
				
				//System.out.println(" ear " + ear);
				if (ear)
				{
					current=i;
	//				triangles.add(pTemp);
//					System.out.println("Triangle " + triangles.size() + " " + pTemp);
					newPolygon = new ArrayList<Point2D>();
					for (int j=0;j< polygon.size();j++)
					{
						if (j!=current)
						{
							newPolygon.add(polygon.get(j));
						}
					}		
					
					break;
				}
			}
		}
		return newPolygon;
	}

	public ArrayList<Point2D> pointIteration(int index, ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> pointAroundI = new ArrayList<Point2D>();
		Point2D p0 = null, pAfter = null, pBefore = null;
			
		p0 = polygon.get(index);
		try {
			pBefore = polygon.get(index - 1);
		} catch (Exception e) {
			pBefore = polygon.get(polygon.size() - 1);
		}
		try {
			pAfter = polygon.get(index + 1);
		} catch (Exception e) {
			pAfter = polygon.get(0);
		}
		
		pointAroundI.add(p0);	
		pointAroundI.add(pAfter);
		pointAroundI.add(pBefore);
		
		return pointAroundI;
	}
	
	public boolean isEar(int indexPoint, ArrayList<Point2D> polygon)
	{
		boolean ear = true;
		GeneralPath tempTriangle = new GeneralPath();
		GeneralPath forma = new GeneralPath();
		
		ArrayList<Point2D> threePoints = new ArrayList<Point2D>();
		
		threePoints = pointIteration(indexPoint, polygon);
		
		//System.out.println("testEar:"+threePoints.get(0)+" "+threePoints.get(1)+" "+threePoints.get(2));
		tempTriangle.moveTo(threePoints.get(0).getX(),threePoints.get(0).getY());
		tempTriangle.lineTo(threePoints.get(1).getX(),threePoints.get(1).getY());
		tempTriangle.lineTo(threePoints.get(2).getX(),threePoints.get(2).getY());
		tempTriangle.closePath();
		
		forma.moveTo(polygon.get(0).getX(),polygon.get(0).getY());
		for (int i=1;i<polygon.size();i++)
		{
			forma.lineTo(polygon.get(i).getX(),polygon.get(i).getY());
		}
		forma.closePath();
		
		double alfa = solveAngle(threePoints.get(0), threePoints.get(1), threePoints.get(2), forma, polygon);
		if (alfa < Math.PI)
		{			
			for (int i = 0; i < polygon.size(); i ++)
			{
				if (threePoints.get(0).distance(polygon.get(i))!=0&&threePoints.get(1).distance(polygon.get(i))!=0&&threePoints.get(2).distance(polygon.get(i))!=0)
				{
					if (tempTriangle.contains(polygon.get(i)))
					{
						ear=false;
						//System.out.println("-Found Point within triangle:"+polygon.get(i));
						break;
					}
				}
			}			
		}
		else
		{
			ear=false;
		}
		//System.out.println("alfaTestEar:"+alfa*180/Math.PI + " ear:" + ear);
		return ear;
	}
	public static double solveAngle(Point2D p0, Point2D p1, Point2D p2, GeneralPath forma, ArrayList<Point2D> pointList)
	{
		double distance0, distance1, distance2;
		double alfa;

		distance0 = p1.distance(p2);
		distance1 = p0.distance(p1);
		distance2 = p2.distance(p0);

		int nPoints = 20;
		double hx = (p2.getX() - p1.getX()) / nPoints;
		double hy = (p2.getY() - p1.getY()) / nPoints;
		boolean allOut;
		
		double v1v2=(p1.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p2.getY() - p0.getY());
		alfa = Math.acos(v1v2/(distance1 * distance2));

		int nPointsIn = 0;
		for (int iPoint = 1; iPoint < nPoints - 1; iPoint++) 
		{
			Point2D testPoint = new Point2D.Double((p1.getX() + hx * iPoint),
					(int) (p1.getY() + hy * iPoint));

			if (forma.contains(testPoint)|| pointList.size()==3) 
			{
				nPointsIn++;
			}
		}

		if (nPointsIn == 0)
		{
			alfa = 2 * Math.PI - alfa;
		}
		//System.out.println("alfaAf="+alfa*180/Math.PI);
		return alfa;
	}
}
