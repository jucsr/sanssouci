package br.UFSC.GRIMA.entidades.machiningResources;

import java.io.Serializable;

import javax.vecmath.Point3d;


public abstract class WorkpieceHandlingDevice implements Serializable
{
	private String itsId = "";
	private double maxLoadCapacity;
	private Point3d itsOrigin = new Point3d();
	private String type;
	
	public WorkpieceHandlingDevice(String itsId)
	{
		this.itsId = itsId;
	}
	public String getItsId() {
		return itsId;
	}
	public void setItsId(String itsId) {
		this.itsId = itsId;
	}
	public double getMaxLoadCapacity() {
		return maxLoadCapacity;
	}
	public void setMaxLoadCapacity(double maxLoadCapacity) {
		this.maxLoadCapacity = maxLoadCapacity;
	}
	public Point3d getItsOrigin() {
		return itsOrigin;
	}
	public void setItsOrigin(Point3d itsOrigin) {
		this.itsOrigin = itsOrigin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
