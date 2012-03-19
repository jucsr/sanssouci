package br.UFSC.GRIMA.util;

import javax.vecmath.Point3d;

public class LinearPath extends Path
{
	public static final int FAST_MOV = 1;
	public static final int SLOW_MOV = 0;
	private int tipoDeMovimento = 0;
	
	public int getTipoDeMovimento() {
		return tipoDeMovimento;
	}

	public void setTipoDeMovimento(int tipoDeMovimento) {
		this.tipoDeMovimento = tipoDeMovimento;
	}

	public LinearPath(Point3d initialPoint, Point3d finalPoint) 
	{
		super(initialPoint, finalPoint);
	}
	
	
}
