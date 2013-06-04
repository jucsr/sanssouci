package br.UFSC.GRIMA.entidades.machiningResources;

import java.io.Serializable;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.projeto.Axis;
/**
 * 
 * @author Jc
 *
 */
public class MachineTool extends MachiningResource implements Serializable
{
	private String itsLocation = "";
	private Point3d itsOrigin = new Point3d();
	private ArrayList<Axis> axis = new ArrayList<Axis>();
	private ArrayList<Spindle> itsSpindle = new ArrayList<Spindle>();
	private MachineToolCapability itsCapability;
	private ArrayList<WorkpieceHandlingDevice> workpieceHandlingDevice = new ArrayList<WorkpieceHandlingDevice>();
	private ArrayList<CuttingToolHandlingDevice> toolHandlingDevice = new ArrayList<CuttingToolHandlingDevice>();
	private double accuracy = 0.1; 
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
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
	public ArrayList<Spindle> getItsSpindle() 
	{
		return itsSpindle;
	}
	public void setItsSpindle(ArrayList<Spindle> itsSpindle) 
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
