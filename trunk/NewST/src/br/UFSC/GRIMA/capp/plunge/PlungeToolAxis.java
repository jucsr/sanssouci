package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;

public class PlungeToolAxis extends PlungeStrategy
{

	public PlungeToolAxis(Point3d toolDirection) //toolDirection eh entrada 
	{
		super(toolDirection); //constroi o pai com a entrada
	}
}
