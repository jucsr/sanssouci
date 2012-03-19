package br.UFSC.GRIMA.util.projeto;

import java.io.Serializable;
import java.util.ArrayList;

import javax.vecmath.Point3d;

public class Axis2Placement3D implements Serializable
{
	private Point3d coordinates;
	private ArrayList<Double> axis;
	private ArrayList<Double> refDirection;
	private String name;
	
	public Axis2Placement3D(Point3d coordinates, ArrayList<Double> axis, ArrayList<Double> refDirection)
	{
		this.coordinates = coordinates;
		this.axis = axis;
		this.refDirection = refDirection;
	}

	public Point3d getCoordinates() {
		return coordinates;
	}

	public ArrayList<Double> getAxis() {
		return axis;
	}

	public ArrayList<Double> getRefDirection() {
		return refDirection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCoordinates(Point3d coordinates) {
		this.coordinates = coordinates;
	}

	public void setAxis(ArrayList<Double> axis) {
		this.axis = axis;
	}

	public void setRefDirection(ArrayList<Double> refDirection) {
		this.refDirection = refDirection;
	}
}
