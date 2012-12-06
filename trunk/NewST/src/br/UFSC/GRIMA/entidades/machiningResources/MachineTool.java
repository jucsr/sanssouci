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
	private ArrayList<Axis> axis = new ArrayList<Axis>();
	private Spindle itsSpindle;
	private MachineToolCapability itsCapability;
	private ArrayList<WorkpieceHandlingDevice> workpieceHandlingDevice;
	private ArrayList<CuttingToolHandlingDevice> toolHandlingDevice;
	
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
	public ArrayList<Axis> getAxis()
	{
		return axis;
	}
	public void setAxis(ArrayList<Axis> axis) 
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
	public ArrayList<WorkpieceHandlingDevice> getWorkpieceHandlingDevice() 
	{
		return workpieceHandlingDevice;
	}
	public void setWorkpieceHandlingDevice(ArrayList<WorkpieceHandlingDevice> workpieceHandlingDevice) 
	{
		this.workpieceHandlingDevice = workpieceHandlingDevice;
	}
	public ArrayList<CuttingToolHandlingDevice> getToolHandlingDevice() 
	{
		return toolHandlingDevice;
	}
	public void setToolHandlingDevice(ArrayList<CuttingToolHandlingDevice> toolHandlingDevice) 
	{
		this.toolHandlingDevice = toolHandlingDevice;
	}
}
