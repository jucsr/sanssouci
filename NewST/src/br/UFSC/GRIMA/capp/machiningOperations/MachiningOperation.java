package br.UFSC.GRIMA.capp.machiningOperations;

import java.io.Serializable;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.movimentacoes.estrategias.MachinningStrategy;
/**
 * 
 * @author Jc
 *
 */
public abstract class MachiningOperation implements Serializable
{
	private String id = "";
	private double retractPlane; // plano de retracao
	private Point3d startPoint = new Point3d(); // ponto de inicio da operacao
	private boolean coolant = true;
	private String operationType;
	private MachinningStrategy machiningStrategy;
	
	public MachinningStrategy getMachiningStrategy() 
	{
		return machiningStrategy;
	}

	public void setMachiningStrategy(MachinningStrategy machiningStrategy) 
	{
		this.machiningStrategy = machiningStrategy;
	}

	public MachiningOperation(String id, double retractPlane)
	{
		this.id = id;
		this.retractPlane = retractPlane;
		this.setOperationType();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getRetractPlane() {
		return retractPlane;
	}
	public void setRetractPlane(double retractPlane) {
		this.retractPlane = retractPlane;
	}
	public Point3d getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point3d startPoint) {
		this.startPoint = startPoint;
	}
	public boolean isCoolant() {
		return coolant;
	}
	public void setCoolant(boolean coolant) {
		this.coolant = coolant;
	}
	public String getOperationType() 
	{
		if(this.getClass() == Boring.class)
		{
			
		}
		return operationType;
	}
	private void setOperationType()
	{
		if(this.getClass() == Boring.class)
		{
			this.operationType = "Boring";
		} else if(this.getClass() == BottomAndSideFinishMilling.class)
		{
			this.operationType = "Bottom and side finish milling";
		} else if(this.getClass() == BottomAndSideRoughMilling.class)
		{
			this.operationType = "Bottom and side rough milling";
		} else if(this.getClass() == CenterDrilling.class)
		{
			this.operationType = "Center drilling";
		} else if(this.getClass() == Drilling.class)
		{
			this.operationType = "Drilling";
		} else if(this.getClass() == FreeformOperation.class)
		{
			this.operationType = "Free form operation";
		} else if(this.getClass() == PlaneFinishMilling.class)
		{
			this.operationType = "Plane finish milling";
		} else if(this.getClass() == PlaneRoughMilling.class)
		{
			this.operationType = "Plane rough milling";
		} else if(this.getClass() == Reaming.class)
		{
			this.operationType = "Reaming";
		} else
		{
			this.operationType = "unknown operation";
		}
	}
}
