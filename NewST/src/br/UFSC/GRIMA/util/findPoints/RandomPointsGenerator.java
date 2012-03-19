package br.UFSC.GRIMA.util.findPoints;
import java.util.ArrayList;

import javax.vecmath.Point3d;


public class RandomPointsGenerator 
{
	private ArrayList<Point3d> randomPoints=new ArrayList<Point3d>();
	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;
	private int N;
	private LimitedCircle c= new LimitedCircle();
	private LimitedLine l= new LimitedLine();
	
	public RandomPointsGenerator()
	{
		
	}
	
	public RandomPointsGenerator(LimitedCircle c, LimitedLine l, int N)
	{
		this.N=N;
		this.c=c;
		this.l=l;
		
		
		if (c.xminc<=l.xminl)
		{
			this.xmin=c.xminc;
		}
		else 
		{
			this.xmin=l.xminl;
		}
		
		if (c.xmaxc>=l.xmaxl)
		{
			this.xmax=c.xmaxc;
		}
		else
		{
			this.xmax=l.xmaxl;
		}
		
		if (c.yminc<=l.yminl)
		{
			this.ymin=c.yminc;
		}
		else 
		{
			this.ymin=l.yminl;
		}
		
		if (c.ymaxc>=l.ymaxl)
		{
			this.ymax=c.ymaxc;
		}
		else
		{
			this.ymax=l.ymaxl;
		}

		
	}
	public void fillRandomPoints()
	{	
		for (int i=0;i<this.N;i++)
		{
			randomPoints.add(new Point3d());
		}
		
		for (Point3d p:randomPoints)
		{
			p.x=this.xmin+Math.random()*(this.xmax-this.xmin);
		}
	
		for (Point3d p:randomPoints)
		{		
			p.y=this.l.getY0()+p.x*this.l.getM();	
			//System.out.println(p.x+","+p.y);
			p.z=0;
		}		
		
	}

	public ArrayList<Point3d> getRandomPoints() 
	{
		return randomPoints;
	}

	public void setRandomPoints(ArrayList<Point3d> randomPoints) 
	{
		this.randomPoints = randomPoints;
	}

	public double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getYmin() {
		return ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public double getYmax() {
		return ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}
}
