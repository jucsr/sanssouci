package br.UFSC.GRIMA.util.projeto;

public class RotaryAxis 
{
	private double maxRotSpeed;
	private int rotaryDirection;
	public static final int CCW = 0;
	public static final int CW = 1;
	
	public double getMaxRotSpeed() {
		return maxRotSpeed;
	}
	public void setMaxRotSpeed(double maxRotSpeed) {
		this.maxRotSpeed = maxRotSpeed;
	}
	public int getRotaryDirection() {
		return rotaryDirection;
	}
	public void setRotaryDirection(int rotaryDirection) {
		this.rotaryDirection = rotaryDirection;
	}
}
