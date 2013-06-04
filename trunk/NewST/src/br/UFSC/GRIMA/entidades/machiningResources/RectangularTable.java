package br.UFSC.GRIMA.entidades.machiningResources;

public class RectangularTable extends Table
{
	private double tableWidth;
	private double tableLength;
	
	public RectangularTable(String itsId) 
	{
		super(itsId);
	}

	public double getTableWidth() 
	{
		return tableWidth;
	}

	public void setTableWidth(double tableWidth) 
	{
		this.tableWidth = tableWidth;
	}

	public double getTableLength() 
	{
		return tableLength;
	}

	public void setTableLength(double tableLength) 
	{
		this.tableLength = tableLength;
	}
}
