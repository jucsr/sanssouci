package br.UFSC.GRIMA.entidades.machiningResources;

public abstract class ClampingPin extends WorkpieceHandlingDevice
{
	private double height;
	public ClampingPin(String itsId)
	{
		super(itsId);
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
}
