package br.UFSC.GRIMA.util.entidadesAdd;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GeneralClosedPocketVertexAdd 
{
	private ArrayList<Point3d> vertex = new ArrayList<Point3d>();
	private GeneralPath forma = new GeneralPath();
	private ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
	private double radius;
	
	public GeneralClosedPocketVertexAdd(ArrayList<Point3d> vertex3d, double radius)
	{
		double zCoordinate = vertex3d.get(0).getZ();
		this.radius = radius;
		ArrayList<Point2D> vertex2D = new ArrayList<Point2D>();
		
		for (Point3d p:vertex3d)
		{
			vertex2D.add(new Point2D.Double(p.getX(), p.getY()));
		}
		
		for (Point2D p:CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex2D))
		{
			this.vertex.add(new Point3d(p.getX(), p.getY(), zCoordinate));
			System.out.println(p.getX() + " " + p.getY() + " " + zCoordinate);			
		}
		
		this.makeForma();
		this.makeElements();
		this.showElements();
	}
	
	public GeneralClosedPocketVertexAdd(ArrayList<LimitedElement> elementsIn)
	{		
		this.makeVertex(elementsIn);
		this.makeForma();
		this.makeElements();
		this.showElements();
	}
	
	public GeneralClosedPocketVertexAdd(ArrayList<Point2D> vertex2D, double zCoordinate, double radius)
	{
		this.radius = radius;
		for (Point2D p:CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex2D))
		{
			this.vertex.add(new Point3d(p.getX(), p.getY(), zCoordinate));
			System.out.println(p.getX() + " " + p.getY() + " " + zCoordinate);			
		}
		this.makeForma();
		this.makeElements();
		this.showElements();
	}	

	private void makeVertex(ArrayList<LimitedElement> elements)
	{
		ArrayList<LimitedArc> arcElements = new ArrayList<LimitedArc>();
		boolean existRadius=false;
		for (LimitedElement e: elements)
		{
			if (e.isLimitedArc())
			{
				arcElements.add((LimitedArc)e);
				if (!existRadius)
				{
					if (((LimitedArc)e).getRadius()!=0)
						radius = ((LimitedArc)e).getRadius();
				}
			}
		}
		
		System.out.println("Vertex generated ");
		for (LimitedArc arc:arcElements)
		{			
			Point3d vertArc = GeometricOperations.arcToVertec(arc);
			this.vertex.add(vertArc);
			System.out.println(vertArc);
		}
	}
	private void makeForma()
	{
		int i=0;
		for (Point3d v:this.vertex)
		{
			if (i==0)
			{
				this.forma.moveTo(this.vertex.get(0).getX(), this.vertex.get(0).getY());
			}
			else
			{
				this.forma.lineTo(v.getX(), v.getY());
			}
			
			if(i==this.vertex.size()-1)
				this.forma.closePath();
			i++;
		}						
	}

	private void makeElements()
	{
		ArrayList<LimitedElement> tempElements = new ArrayList<LimitedElement>(); 
		
		for (int i=0; i < this.vertex.size(); i++)
		{
			Point3d p1 = new Point3d();
			Point3d p2 = new Point3d();
			Point3d p3 = new Point3d();
			
			LimitedArc arcNew = new LimitedArc();
			
//			System.out.println("----------------------------------------------");
			if (i==0)
			{
				p1 = this.vertex.get(this.vertex.size()-1);
				p2 = this.vertex.get(i);
				p3 = this.vertex.get(i+1);
			}
			else if (i < this.vertex.size()-1)
			{
				p1 = this.vertex.get(i-1);
				p2 = this.vertex.get(i);
				p3 = this.vertex.get(i+1);
			}
			else
			{
				p1 = this.vertex.get(i-1);
				p2 = this.vertex.get(i);
				p3 = this.vertex.get(0);
			}
			arcNew = GeometricOperations.roundVertex(p1, p2, p3, this.radius);
			
//			System.out.println("First point " + p1);
//			System.out.println("Second point " + p2);
//			System.out.println("Third point " + p3);
//			
//			System.out.println("Center Point of arc " + arcNew.getCenter());
//			System.out.println("Initial Point of arc " + arcNew.getInitialPoint());
//			System.out.println("Final Point of arc " + arcNew.getFinalPoint());

			if (arcNew.getRadius()==0)
			{
				if (tempElements.size()!=0)
				{
					if (tempElements.getClass().equals(LimitedLine.class))
					{
						Point3d lastPoint = ((LimitedLine)tempElements.get(tempElements.size()-1)).getFinalPoint();
						tempElements.add(new LimitedLine(lastPoint, p2));
					}
				}
				tempElements.add(new LimitedLine(p2, p3));
			}
			else
			{
				if (tempElements.size()!=0)
				{
//					System.out.println("Size  elements " + tempElements.size());
					if (tempElements.get(tempElements.size()-1).getClass().equals(LimitedLine.class))
					{
						Point3d lastPoint = ((LimitedLine)tempElements.get(tempElements.size()-1)).getFinalPoint();
						tempElements.add(new LimitedLine(lastPoint, arcNew.getInitialPoint()));
					}
					else if (tempElements.get(tempElements.size()-1).getClass().equals(LimitedArc.class))
					{
						Point3d lastPoint = ((LimitedArc)tempElements.get(tempElements.size()-1)).getFinalPoint();
						tempElements.add(new LimitedLine(lastPoint, arcNew.getInitialPoint()));
					}
				}
				
				tempElements.add(arcNew);
			}
		}
		
		Point3d lastPoint = new Point3d();
		Point3d firstPoint = new Point3d();
		if (tempElements.get(tempElements.size()-1).getClass().equals(LimitedLine.class))
		{
			lastPoint = ((LimitedLine)tempElements.get(tempElements.size()-1)).getFinalPoint();
		}
		else if (tempElements.get(tempElements.size()-1).getClass().equals(LimitedArc.class))
		{
			lastPoint = ((LimitedArc)tempElements.get(tempElements.size()-1)).getFinalPoint();
		}
		
		if (tempElements.get(0).getClass().equals(LimitedLine.class))
		{
			firstPoint = ((LimitedLine)tempElements.get(0)).getInitialPoint();
		}
		else if (tempElements.get(0).getClass().equals(LimitedArc.class))
		{
			firstPoint = ((LimitedArc)tempElements.get(0)).getInitialPoint();
		}
		
		tempElements.add(new LimitedLine (lastPoint, firstPoint));
		
		this.elements = tempElements;
	}
	
	public void showElements()
	{
		int i = 0;
		System.out.println("Elements after rounding with radius " + this.radius);
		for (LimitedElement e:this.elements)
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
	
	public ArrayList<Point3d> getVertex() {
		return vertex;
	}

	public void setVertex(ArrayList<Point3d> vertex) {
		this.vertex = vertex;
	}

	public GeneralPath getForma() {
		return forma;
	}

	public void setForma(GeneralPath forma) {
		this.forma = forma;
	}

	public ArrayList<LimitedElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<LimitedElement> elements) {
		this.elements = elements;
	}
}
