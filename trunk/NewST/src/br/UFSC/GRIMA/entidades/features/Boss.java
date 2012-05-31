package br.UFSC.GRIMA.entidades.features;

import java.util.ArrayList;

import br.UFSC.GRIMA.util.findPoints.LimitedElement;
/**
 * 
 * @author Jc
 *
 */
public abstract class Boss extends Feature
{
	ArrayList<LimitedElement> geometricalElements = new ArrayList<LimitedElement>();
	private double altura;
	public Boss() 
	{
		super(Feature.BOSS);
	}
	public Boss(double altura)
	{
		super(Feature.BOSS);
		this.altura = altura;
	}
	
	public ArrayList<LimitedElement> getGeometricalElements() 
	{
		return geometricalElements;
	}
	public void setGeometricalElements(ArrayList<LimitedElement> geometricalElements) 
	{
		this.geometricalElements = geometricalElements;
	}

	public double getAltura() 
	{
		return altura;
	}

	public void setAltura(double altura) 
	{
		this.altura = altura;
	}
}
