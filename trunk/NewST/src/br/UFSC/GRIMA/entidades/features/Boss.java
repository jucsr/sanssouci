package br.UFSC.GRIMA.entidades.features;

import java.util.ArrayList;

import br.UFSC.GRIMA.util.findPoints.LimitedElement;

public abstract class Boss extends Feature
{
	ArrayList<LimitedElement> geometricalElements = new ArrayList<LimitedElement>();
	
	public Boss() 
	{
		super(Feature.BOSS);
	}
	
	public ArrayList<LimitedElement> getGeometricalElements() 
	{
		return geometricalElements;
	}
	public void setGeometricalElements(ArrayList<LimitedElement> geometricalElements) 
	{
		this.geometricalElements = geometricalElements;
	}
}
