package br.UFSC.GRIMA.entidades.machiningResources;

public class CircularWorkTable extends Table
{
	private double tableDiameter;
	
	public CircularWorkTable(String itsId) 
	{
		super(itsId);
	}

	public double getTableDiameter() 
	{
		return tableDiameter;
	}

	public void setTableDiameter(double tableDiameter) 
	{
		this.tableDiameter = tableDiameter;
	}

}
