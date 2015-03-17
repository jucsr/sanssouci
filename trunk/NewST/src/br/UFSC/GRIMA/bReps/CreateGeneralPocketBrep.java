package br.UFSC.GRIMA.bReps;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;

public class CreateGeneralPocketBrep
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private String name = "";
	private float depth;
	private float radius;
	private ArrayList<Point2D> polygonVertex; // poligono em sentido CCW
	
	public CreateGeneralPocketBrep(String name, float depth, float radius, ArrayList<Point2D> polygonVertex)
	{
		this.name = name;
		this.depth = depth;
		this.radius = radius;
		this.polygonVertex = polygonVertex;		
		this.generateVertexArray();
		this.generateColorArray();
		this.generateIndexArray();
	}
//	public Point3d[] getVertexArray()
//	{
//		return vertexArray;
//	}
	private void generateVertexArray()
	{		
		ArrayList<Point2D> vertices = CreateGeneralPocket.transformPolygonInRoundPolygon(polygonVertex, radius);
		
		vertexArray = new Point3d[vertices.size() * 2];

		int a = 0;
		//--- vertex top face
		for(int i = 0; i < vertices.size(); i++)
		{
			vertexArray[i] = new Point3d(vertices.get(i).getX(), vertices.get(i).getY(), 0);
			System.out.println("VertexArray[i]: "+vertexArray[i]);
		}
		a = vertices.size();
		//System.out.println("Size VertexArray: " + a);
		// vertex in bottom face
		for(int i = 0; i < vertices.size(); i++)
		{
			vertexArray[i + a] = new Point3d(vertices.get(i).getX(), vertices.get(i).getY(), -depth);
			//System.out.println("i+a:"+(i+a));
		}		
		//System.out.println("Size VertexArray: " + 2*a);
		
	}
	private void generateIndexArray()
	{
		ArrayList<Point2D> vertexTmp = new ArrayList<Point2D>();
		ArrayList<Integer> indexListTemp1 = new ArrayList<Integer>();
		
		for (int i=0;i<vertexArray.length/2;i++)
		{
			vertexTmp.add(new Point2D.Double(vertexArray[i].x, vertexArray[i].y));
		}
		
		indexListTemp1=triangulation(vertexTmp);
		
		indexArray = new int [vertexArray.length * 3000];
		int a = 0;
		int aux = 1;
		for(int i=0;i<indexListTemp1.size();i++)
		{
			indexArray[i]=indexListTemp1.get(i);
			if(aux == 3)
			{
				System.out.println("IndexList1: "+indexListTemp1.get(i-2)+", " + indexListTemp1.get(i-1)+", " + indexListTemp1.get(i));
				aux = 0;
			}
			aux++;
			//indexArray[i+indexListTemp1.size()]=indexListTemp1.get(indexListTemp1.size()-1-i);
		}
		
		for(int i=0;i<indexListTemp1.size();i++)
		{
			indexArray[i+indexListTemp1.size()]=indexListTemp1.get(indexListTemp1.size()-1-i) + vertexTmp.size();
			//System.out.println("IndexList2: "+indexListTemp1.get(indexListTemp1.size()-1-i));
		}		
		
		System.out.println("Pass index faces");
		a=indexListTemp1.size()*2;
		
		
		for(int i = 0; i < vertexArray.length / 2; i++)
		{
			if(i<vertexArray.length / 2 - 1)
			{
				indexArray[a] = vertexArray.length / 2 + i;
				a=a+1;
				indexArray[a] = i + 1;
				a=a+1;
				indexArray[a] = i;								
				a=a+1;

				indexArray[a] = vertexArray.length / 2 + i + 1;
				a=a+1;
				indexArray[a] = i+1;
				a=a+1;
				indexArray[a] = vertexArray.length / 2 + i;
				a=a+1;										
				//System.out.println("triangleLateral:" + i + " " + (i+1) + " " + (vertexArray.length / 2 + i));
			}
			else
			{
				indexArray[a] = vertexArray.length / 2 + i;
				a=a+1;
				indexArray[a] = 0;
				a=a+1;
				indexArray[a] = i;								
				a=a+1;

				indexArray[a] = vertexArray.length / 2 + 0;
				a=a+1;
				indexArray[a] = 0;
				a=a+1;
				indexArray[a] = vertexArray.length / 2 + i;
				a=a+1;													}
		}
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
			ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
			ArrayList<Integer> newIndexes = new ArrayList<Integer>();
			ArrayList<Integer> tempTriangleIndex = new ArrayList<Integer>();
			ArrayList<Integer> triangleIndex = new ArrayList<Integer>();
//			System.out.println("################################################");
//			System.out.println(iPolygon+ " Polygon");
//			System.out.println("################################################");
			
			//newPolygon = cutEar(oldPolygon);
			
			tempTriangleIndex = cutEarByIndex(oldPolygon);
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

		
	private void generateColorArray() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
	    {
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
	    }	
	}
}
