package br.UFSC.GRIMA.entidades.machiningResources;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.projeto.Axis;
/**
 * 
 * @author Jc
 *
 */
public class MachineTool extends MachiningResource
{
	private String itsLocation = "";
	private Point3d itsOrigin = new Point3d();
	private Axis [] axis;
	private Spindle itsSpindle;
	private MachineToolCapability itsCapability;
	private ArrayList<WorkpieceHandlingDevice> workpieceHandlingInformation;
	private ArrayList<CuttingToolHandlingDevice> toolHandlingInformation;
	
	public String getItsLocation() 
	{
		return itsLocation;
	}
	public void setItsLocation(String itsLocation) 
	{
		this.itsLocation = itsLocation;
	}
	public Point3d getItsOrigin() 
	{
		return itsOrigin;
	}
	public void setItsOrigin(Point3d itsOrigin) 
	{
		this.itsOrigin = itsOrigin;
	}
	public Axis[] getAxis()
	{
		return axis;
	}
	public void setAxis(Axis[] axis) 
	{
		this.axis = axis;
	}
	public Spindle getItsSpindle() 
	{
		return itsSpindle;
	}
	public void setItsSpindle(Spindle itsSpindle) 
	{
		this.itsSpindle = itsSpindle;
	}
	public MachineToolCapability getItsCapability() 
	{
		return itsCapability;
	}
	public void setItsCapability(MachineToolCapability itsCapability) 
	{
		this.itsCapability = itsCapability;
	}
	public ArrayList<WorkpieceHandlingDevice> getWorkpieceHandlingInformation() 
	{
		return workpieceHandlingInformation;
	}
	public void setWorkpieceHandlingInformation(ArrayList<WorkpieceHandlingDevice> workpieceHandlingInformation) 
	{
		this.workpieceHandlingInformation = workpieceHandlingInformation;
	}
	public ArrayList<CuttingToolHandlingDevice> getToolHandlingInformation() 
	{
		return toolHandlingInformation;
	}
	public void setToolHandlingInformation(ArrayList<CuttingToolHandlingDevice> toolHandlingInformation) 
	{
		this.toolHandlingInformation = toolHandlingInformation;
	}
}
