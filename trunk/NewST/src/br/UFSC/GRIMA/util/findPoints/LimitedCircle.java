package br.UFSC.GRIMA.util.findPoints;
import javax.vecmath.Point3d;


public class LimitedCircle extends LimitedElement
{
	private Point3d center = new Point3d();
	private double radius;
	double xminc;
	double xmaxc;
	double yminc;
	double ymaxc;
	
	public LimitedCircle()
	{
		
	}
	
	public LimitedCircle(Point3d center, double radius)
	{
		this.center=center;
		this.radius=radius;
		xminc = this.getCenter().x-this.getRadius();
		xmaxc = this.getCenter().x+this.getRadius();
		yminc = this.getCenter().y-this.getRadius();
		ymaxc = this.getCenter().y+this.getRadius();		
	}
	
	public Point3d getCenter() 
	{
		return center;
	}
	public void setCenter(Point3d center) 
	{
		this.center = center;
	}
	public double getRadius() 
	{
		return radius;
	}
	public void setRadius(double ratio) 
	{
		this.radius = ratio;
	} 
	

}
