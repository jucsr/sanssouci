package br.UFSC.GRIMA.entidades.machiningResources;

public class Chuck extends WorkpieceHandlingDevice{

	private int numberOfJaws;
	private double minDiameter;
	private double maxDiameter;
	private double maxAllowedLength;
	
	public Chuck(String itsId) {
		super(itsId);
	}

	public int getNumberOfJaws() {
		return numberOfJaws;
	}

	public void setNumberOfJaws(int numberOfJaws) {
		this.numberOfJaws = numberOfJaws;
	}

	public double getMinDiameter() {
		return minDiameter;
	}

	public void setMinDiameter(double minDiameter) {
		this.minDiameter = minDiameter;
	}

	public double getMaxDiameter() {
		return maxDiameter;
	}

	public void setMaxDiameter(double maxDiameter) {
		this.maxDiameter = maxDiameter;
	}

	public double getMaxAllowedLength() {
		return maxAllowedLength;
	}

	public void setMaxAllowedLength(double maxAllowedLength) {
		this.maxAllowedLength = maxAllowedLength;
	}


}
