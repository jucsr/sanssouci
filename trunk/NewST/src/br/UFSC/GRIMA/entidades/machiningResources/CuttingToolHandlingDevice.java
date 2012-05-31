package br.UFSC.GRIMA.entidades.machiningResources;

import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

/**
 * 
 * @author Jc
 *
 */
public abstract class CuttingToolHandlingDevice 
{
	private String itsId = "";
	private int itsToolCapacity;
	private double maxAllowedToolWeight;
	private double allowedToolDiameter;
	private double maxAllowedToolLength;
	private ArrayList<Ferramenta> toolList;
	private Ferramenta itsCurrentTool;
	
	public String getItsId()
	{
		return itsId;
	}
	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}
	public int getItsToolCapacity() 
	{
		return itsToolCapacity;
	}
	public void setItsToolCapacity(int itsToolCapacity)
	{
		this.itsToolCapacity = itsToolCapacity;
	}
	public double getMaxAllowedToolWeight()
	{
		return maxAllowedToolWeight;
	}
	public void setMaxAllowedToolWeight(double maxAllowedToolWeight) 
	{
		this.maxAllowedToolWeight = maxAllowedToolWeight;
	}
	public double getAllowedToolDiameter()
	{
		return allowedToolDiameter;
	}
	public void setAllowedToolDiameter(double allowedToolDiameter)
	{
		this.allowedToolDiameter = allowedToolDiameter;
	}
	public double getMaxAllowedToolLength() 
	{
		return maxAllowedToolLength;
	}
	public void setMaxAllowedToolLength(double maxAllowedToolLength) 
	{
		this.maxAllowedToolLength = maxAllowedToolLength;
	}
	public ArrayList<Ferramenta> getToolList() 
	{
		return toolList;
	}
	public void setToolList(ArrayList<Ferramenta> toolList) 
	{
		this.toolList = toolList;
	}
	public Ferramenta getItsCurrentTool() 
	{
		return itsCurrentTool;
	}
	public void setItsCurrentTool(Ferramenta itsCurrentTool) 
	{
		this.itsCurrentTool = itsCurrentTool;
	}
}
