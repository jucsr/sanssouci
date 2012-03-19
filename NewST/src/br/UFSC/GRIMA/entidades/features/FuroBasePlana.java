package br.UFSC.GRIMA.entidades.features;

import java.util.Vector;

import br.UFSC.GRIMA.capp.Workingstep;

public class FuroBasePlana extends Furo
{

	public FuroBasePlana(String string, double x, double y, double z,
			double diametroFuro, double profundidade) {
		super(string,x,y,z,diametroFuro, profundidade);
	}
	
	public FuroBasePlana(String string, double x, double y, double z,
			double diametroFuro,double profundidade, Vector<Workingstep> workingsteps) {
		super(string,x,y,z,diametroFuro,profundidade,workingsteps);
	}

	public FuroBasePlana() 
	{
	}
}
