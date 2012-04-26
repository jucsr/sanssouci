package br.UFSC.GRIMA.capp.machiningOperations;

import java.io.Serializable;

import javax.vecmath.Point3d;
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
	public MachiningOperation(String id, double retractPlane)
	{
		this.id = id;
		this.retractPlane = retractPlane;
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
}
