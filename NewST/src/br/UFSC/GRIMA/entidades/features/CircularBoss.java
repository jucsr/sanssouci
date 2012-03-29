package br.UFSC.GRIMA.entidades.features;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;

public class CircularBoss extends Boss
{
	private double diametro1;
	private double diametro2;
	private double altura;
	private Point3d centre;
	
	public CircularBoss()
	{
//		this.createGeometricalElements();
	}
	
	public CircularBoss(String nome, double x, double y, double z, double diametro1, double diametro2, double altura)
	{
		this.diametro1 = diametro1;
		this.diametro2 = diametro2;
		this.altura = altura;
		this.setPosicao(x, y, z);
		this.setCentre(new Point3d(x, y, z));
		this.createGeometricalElements();
	}
	public double getDiametro1() 
	{
		return diametro1;
	}

	public void setDiametro1(double diametro1) {
		this.diametro1 = diametro1;
	}

	public double getDiametro2() {
		return diametro2;
	}

	public void setDiametro2(double diametro2) {
		this.diametro2 = diametro2;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public void createGeometricalElements() 
	{
		this.geometricalElements.add(new LimitedCircle(new Point3d(this.X, this.Y, this.Z), this.diametro1 / 2));
	}

	public Point3d getCentre() {
		return centre;
	}

	public void setCentre(Point3d centre) {
		this.centre = centre;
	}
}
