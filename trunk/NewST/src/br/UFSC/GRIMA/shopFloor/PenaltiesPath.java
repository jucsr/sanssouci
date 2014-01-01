package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

public class PenaltiesPath 
{
	private ArrayList<Double> penaltiesCost = new ArrayList<Double>();
	private ArrayList<Double> penaltiesTime = new ArrayList<Double>();
	
	public ArrayList<Double> getPenaltiesCost() 
	{
		return penaltiesCost;
	}
	
	public ArrayList<Double> getPenaltiesTime() 
	{
		return penaltiesTime;
	}

	public void setPenaltiesCost(ArrayList<Double> penaltiesCost) {
		this.penaltiesCost = penaltiesCost;
	}

	public void setPenaltiesTime(ArrayList<Double> penaltiesTime) {
		this.penaltiesTime = penaltiesTime;
	}

	
}
