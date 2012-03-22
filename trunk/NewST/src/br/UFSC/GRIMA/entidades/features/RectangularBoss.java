package br.UFSC.GRIMA.entidades.features;

import java.util.ArrayList;

import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

/**
 * 
 * @author Jc
 *
 */
public class RectangularBoss extends Boss
{
	private double l1, l2, h, radius;
	private ArrayList<LimitedElement> geometricalElements;
	
	public RectangularBoss(double l1, double l2, double h, double radius)
	{
		this.l1 = l1;
		this.l2 = l2;
		this.h = h;
		this.radius = radius;
	}
	
	public double getL1() 
	{
		return l1;
	}

	public void setL1(double l1) 
	{
		this.l1 = l1;
	}

	public double getL2() 
	{
		return l2;
	}

	public void setL2(double l2) 
	{
		this.l2 = l2;
	}

	public double getH()
	{
		return h;
	}

	public void setH(double h) 
	{
		this.h = h;
	}

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	public void createGeometricalElements()
	{
		this.geometricalElements = new ArrayList<LimitedElement>();
		if(this.radius == 0)
		{
//			this.geometricalElements.add(new LimitedLine(new Point3d(), sp))
		}
	}

	public ArrayList<LimitedElement> getGeometricalElements() {
		return geometricalElements;
	}

	public void setGeometricalElements(ArrayList<LimitedElement> geometricalElements) {
		this.geometricalElements = geometricalElements;
	}
}
