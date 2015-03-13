package br.drawLines;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GeneralClosedPocket 
{
	private double radius = 0;
	private ArrayList<Point2D> pointsToTriangulation;
	private GeneralPath poligono;
	private ArrayList<Double> anguloList;
	
	public GeneralClosedPocket()
	{
		
	}
	public GeneralClosedPocket(double radius, ArrayList<Point2D> pointsToTriangulation, GeneralPath poligono, ArrayList<Double> anguloList)
	{
		this.pointsToTriangulation = pointsToTriangulation;
		this.poligono = poligono;
		this.radius = radius;
		this.anguloList = anguloList;
	}
	public GeneralPath getPath()
	{
		return poligono;
	}
	public void setPath(GeneralPath path) 
	{
		this.poligono = path;
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	public ArrayList<Point2D> getPoints()
	{
		return pointsToTriangulation;
	}
	public void setPoints(ArrayList<Point2D> points) 
	{
		this.pointsToTriangulation = points;
	}
	public GeneralPath getPoligono()
	{
		return poligono;
	}
	public void setPoligono(GeneralPath poligono) 
	{
		this.poligono = poligono;
	}
	public ArrayList<Double> getAnguloList() 
	{
		return anguloList;
	}
	public void setAnguloList(ArrayList<Double> anguloList)
	{
		this.anguloList = anguloList;
	}
}
