package br.UFSC.GRIMA.entidades.features;

import br.UFSC.GRIMA.bReps.BezierSurface;

/**
 * 
 * @author Jc
 *
 */
public class Region extends Feature
{
	private String itsId = "";
	private BezierSurface surface;
	
	public Region(int tipo) 
	{
		super(tipo);
	}

	public String getItsId() 
	{
		return itsId;
	}

	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}

	public BezierSurface getSurface() 
	{
		return surface;
	}

	public void setSurface(BezierSurface surface) 
	{
		this.surface = surface;
	}
}
