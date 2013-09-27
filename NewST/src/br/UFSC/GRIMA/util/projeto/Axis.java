package br.UFSC.GRIMA.util.projeto;

import java.io.Serializable;

import javax.vecmath.Point3d;

public abstract class Axis implements Serializable
{
	private String name = "";
	private Point3d origin = new Point3d();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point3d getOrigin() {
		return origin;
	}
	public void setOrigin(Point3d origin) {
		this.origin = origin;
	}
}
