package br.UFSC.GRIMA.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
//			System.out.println("lol");
			ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
			ArrayList<Integer> newIndexes = new ArrayList<Integer>();
			ArrayList<Integer> tempTriangleIndex = new ArrayList<Integer>();
			ArrayList<Integer> triangleIndex = new ArrayList<Integer>();
//			System.out.println("################################################");
//			System.out.println(iPolygon+ " Polygon");
//			System.out.println("################################################");
			
			//newPolygon = cutEar(oldPolygon);
			
			tempTriangleIndex = cutEarByIndex(oldPolygon);
//			System.out.println("tempTriangleIndex: "+tempTriangleIndex);
			if(tempTriangleIndex.size() != 0)
			{
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
			else
			{
				break;
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
//		System.out.println("alfaTestEar:"+alfa*180/Math.PI + " ear:" + ear);
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
	public ArrayList<ArrayList<Integer>> splitTriangleIndex()
	{
		ArrayList<ArrayList<Integer>> saida = new ArrayList<ArrayList<Integer>>();
		for(int i = 1;i < triangulesIndex.size()-1;i++)
		{
			ArrayList<Integer> saidaAux = new ArrayList<Integer>();
			saidaAux.add(triangulesIndex.get(i-1));
			saidaAux.add(triangulesIndex.get(i));
			saidaAux.add(triangulesIndex.get(i+1));
			saida.add(saidaAux);
//			System.err.println("Index: " + saidaAux);
		}
		return saida;
	}
	public void drawTriangles()
	{
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(300, 300));
		class Panel extends JPanel
		{
			protected void paintComponent(Graphics g)
			{
				ArrayList<ArrayList<Integer>> triangles = splitTriangleIndex();
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
				
				g2d.translate(0, 130);
				g2d.scale(1, -1);
				g2d.setColor(new Color(0, 0, 0));
				for(int i = 0; i < triangles.size(); i++)
				{
					GeneralPath trianguloTmp = new GeneralPath();
					trianguloTmp.moveTo(polygon.get(triangles.get(i).get(0)).getX(), polygon.get(triangles.get(i).get(0)).getY());
					trianguloTmp.lineTo(polygon.get(triangles.get(i).get(1)).getX(), polygon.get(triangles.get(i).get(1)).getY());
					trianguloTmp.lineTo(polygon.get(triangles.get(i).get(2)).getX(), polygon.get(triangles.get(i).get(2)).getY());
					trianguloTmp.lineTo(polygon.get(triangles.get(i).get(0)).getX(), polygon.get(triangles.get(i).get(0)).getY());
//					for(int j = 0; j < triangles.get(i).size(); j++)
//					{
//						if(j != triangles.get(i).size()-1)
//						{
//							trianguloTmp.lineTo(polygon.get(triangles.get(i).get(j+1)).getX(), polygon.get(triangles.get(i).get(j+1)).getY());
//						}
//						else
//						{
//							trianguloTmp.lineTo(polygon.get(triangles.get(i).get(0)).getX(), polygon.get(triangles.get(i).get(0)).getY());
//						}
//					}
//					trianguloTmp.closePath();
					g2d.draw(trianguloTmp);
				}
				
			}
		}
		frame.getContentPane().add(new Panel());
		frame.setVisible(true);
	}
}
