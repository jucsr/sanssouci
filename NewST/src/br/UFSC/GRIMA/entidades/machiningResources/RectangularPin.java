package br.UFSC.GRIMA.entidades.machiningResources;

public class RectangularPin extends ClampingPin
{
	private double length;
	private double width;
	
	public RectangularPin(String itsId)
	{
		super(itsId);
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
