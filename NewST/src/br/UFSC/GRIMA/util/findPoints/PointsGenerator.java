package br.UFSC.GRIMA.util.findPoints;

import java.util.ArrayList;

import javax.vecmath.Point3d;

public class PointsGenerator {
	private ArrayList<Point3d> points = new ArrayList<Point3d>();
	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;

	private double h;
	private int N;
	private LimitedCircle c = new LimitedCircle();
	private LimitedLine limitedLine = new LimitedLine();

	public PointsGenerator() {

	}

	public PointsGenerator(LimitedCircle c, LimitedLine l, int N) {
		this.N = N;
		this.c = c;
		this.limitedLine = l;

		this.xmin = l.xminl;
		this.xmax = l.xmaxl;
		this.ymin = l.yminl;
		this.ymax = l.ymaxl;
	}

	public PointsGenerator(LimitedLine l, int N) {
		this.N = N;
		this.limitedLine = l;

		this.xmin = l.xminl;
		this.xmax = l.xmaxl;
		this.ymin = l.yminl;
		this.ymax = l.ymaxl;
	}

	public void fillPoints() {
		if (limitedLine.vertical) {
			this.h = (limitedLine.getYmaxl() - limitedLine.getYminl()) / this.N;
		} else {
			this.h = (limitedLine.getXmaxl() - limitedLine.getXminl()) / this.N;
		}

		if (!limitedLine.vertical) {
			for (int i = 0; i < this.N; i++) {
				points.add(new Point3d());
				points.get(points.size() - 1).x = this.xmin + i * this.h;
				points.get(points.size() - 1).y = this.limitedLine.getY0() + points.get(points.size() - 1).x * this.limitedLine.getM();
				points.get(points.size() - 1).z = 0;
			}
		} else {
			for (int i = 0; i < this.N; i++) {
				points.add(new Point3d());
				points.get(points.size() - 1).x = this.xmin;
				points.get(points.size() - 1).y = limitedLine.getYminl() + i * this.h;
				points.get(points.size() - 1).z = 0;
			}

		}
	}
	public void generatePointsInLine()
	{
		this.points = new ArrayList<Point3d>();
		double xTmp = 0, yTmp = 0, zTmp = 0;
		if(!this.limitedLine.vertical)
		{
			this.h = (limitedLine.getSp().x - limitedLine.getFp().x) / (this.N - 1);
			System.out.println("point FP = " + limitedLine.getFp());
			System.out.println("point SP = " + limitedLine.getSp());
			
			System.out.println("h = " + h);
			
			
			for(int i = 0; i< N; i++)
			{
				xTmp = this.limitedLine.getFp().x + i * this.h;
				yTmp = this.limitedLine.getFp().y + this.limitedLine.getM() * (xTmp - this.limitedLine.getFp().x);
				zTmp = this.limitedLine.getFp().z;
				this.points.add(new Point3d(xTmp, yTmp, zTmp));
			}
		} else
		{
			
		}
		System.out.println("point in line = " + points);
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
		return limitedLine;
	}

	public void setL(LimitedLine l) {
		this.limitedLine = l;
	}
}
