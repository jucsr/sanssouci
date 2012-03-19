package br.UFSC.GRIMA.util.findPoints;
import java.util.ArrayList;

import javax.vecmath.Point3d;



public class PointsGenerator 
{
	private ArrayList<Point3d> points=new ArrayList<Point3d>();
	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;
	
	private double h;
	private int N;
	private LimitedCircle c= new LimitedCircle();
	private LimitedLine l= new LimitedLine();
	
	public PointsGenerator()
	{
		
	}
	
	public PointsGenerator(LimitedCircle c, LimitedLine l, int N)
	{
		this.N=N;
		this.c=c;
		this.l=l;
				
		this.xmin=l.xminl;
		this.xmax=l.xmaxl;
		this.ymin=l.yminl;
		this.ymax=l.ymaxl;
	}

	public PointsGenerator(LimitedLine l, int N)
	{
		this.N=N;
		this.l=l;
				
		this.xmin=l.xminl;
		this.xmax=l.xmaxl;
		this.ymin=l.yminl;
		this.ymax=l.ymaxl;
	}

	
	public void fillPoints()
	{	
		if (l.vertical)
		{
			this.h=(l.getYmaxl()-l.getYminl())/this.N;
		}
		else
		{
			this.h=(l.getXmaxl()-l.getXminl())/this.N;
		}
		
		if (!l.vertical)
		{
			for (int i=0;i<this.N;i++)
			{
				points.add(new Point3d());			
				points.get(points.size()-1).x=this.xmin+i*this.h;
				points.get(points.size()-1).y=this.l.getY0()+points.get(points.size()-1).x*this.l.getM();
				points.get(points.size()-1).z=0;
			}
		}
		else
		{
			for (int i=0;i<this.N;i++)
			{
				points.add(new Point3d());			
				points.get(points.size()-1).x=this.xmin;
				points.get(points.size()-1).y=l.getYminl()+i*this.h;
				points.get(points.size()-1).z=0;
			}
			
		}
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

	public ArrayList<Point3d> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point3d> points) {
		this.points = points;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public LimitedCircle getC() {
		return c;
	}

	public void setC(LimitedCircle c) {
		this.c = c;
	}

	public LimitedLine getL() {
		return l;
	}

	public void setL(LimitedLine l) {
		this.l = l;
	}
}

