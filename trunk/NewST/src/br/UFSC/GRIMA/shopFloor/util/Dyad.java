package br.UFSC.GRIMA.shopFloor.util;

public class Dyad 
{
	private int index;
	private double value;
	
	public Dyad()
	{
		this.setIndex(0);
		this.setValue(0);
	}
	
	public Dyad(int ind, double val)
	{
		this.setIndex(ind);
		this.setValue(val);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	
	
}
