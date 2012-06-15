package br.UFSC.GRIMA.entidades.machiningResources;

public class Vise extends WorkpieceHandlingDevice
{
	private double height;
	private double width;
	public Vise(String itsId, double height, double width) 
	{
		super(itsId);
		this.height = height;
		this.width = width;
	}
	public Vise(String itsId, double height) 
	{
		super(itsId);
		this.height = height;

	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}

}
