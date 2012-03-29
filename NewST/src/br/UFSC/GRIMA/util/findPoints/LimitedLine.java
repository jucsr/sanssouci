package br.UFSC.GRIMA.util.findPoints;

import javax.vecmath.Point3d;

/**
 * 
 * @author moises
 * 
 *         Line with the equation: ax+by+cz+c=0
 */

public class LimitedLine extends LimitedElement {
	private Point3d fp;
	private Point3d sp;

	private double a;
	private double b;
	private double c;
	private double d;

	private double m;
	private double y0;

	public double xminl;
	public double xmaxl;
	public double yminl;
	public double ymaxl;

	public boolean vertical = false;

	public LimitedLine() {

	}

	public LimitedLine(Point3d fp, Point3d sp) {
		double ap;
		double bp;
		double cp;

		this.fp = fp;
		this.sp = sp;

		ap = (sp.y - fp.y) * (sp.z - fp.z);
		bp = (sp.x - fp.x) * (sp.z - fp.z);
		cp = (sp.x - fp.x) * (sp.y - fp.y);

		this.setA(2 * ap);
		this.setB(-bp);
		this.setC(-cp);
		this.setD(2 * ap * fp.x - bp * fp.y - cp * fp.z);

		if (sp.x != fp.x) {
			this.m = (sp.y - fp.y) / (sp.x - fp.x);
			this.y0 = fp.y - this.getM() * fp.x;
		} else {
			this.vertical = true;
		}

		if (this.getFp().x <= this.getSp().x) {
			this.xminl = this.getFp().x;
			this.xmaxl = this.getSp().x;
		} else {
			this.xminl = this.getSp().x;
			this.xmaxl = this.getFp().x;
		}

		if (this.getFp().y <= this.getSp().y) {
			this.yminl = this.getFp().y;
			this.ymaxl = this.getSp().y;
		} else {
			this.yminl = this.getSp().y;
			this.ymaxl = this.getFp().y;
		}

	}

	public Point3d getFp() {
		return fp;
	}

	public void setFp(Point3d fp) {
		this.fp = fp;
	}

	public Point3d getSp() {
		return sp;
	}

	public void setSp(Point3d sp) {
		this.sp = sp;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getXminl() {
		return xminl;
	}

	public void setXminl(double xminl) {
		this.xminl = xminl;
	}

	public double getXmaxl() {
		return xmaxl;
	}

	public void setXmaxl(double xmaxl) {
		this.xmaxl = xmaxl;
	}

	public double getYminl() {
		return yminl;
	}

	public void setYminl(double yminl) {
		this.yminl = yminl;
	}

	public double getYmaxl() {
		return ymaxl;
	}

	public void setYmaxl(double ymaxl) {
		this.ymaxl = ymaxl;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

}
