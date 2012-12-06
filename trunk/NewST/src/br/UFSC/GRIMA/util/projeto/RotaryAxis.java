package br.UFSC.GRIMA.util.projeto;

public class RotaryAxis extends Axis
{
	private double maxRotSpeed;
	private int rotaryType;
	private int rotaryDirection;
	public static final int CCW_TYPE = 0;
	public static final int CW_TYPE = 1;
	public static final int CW_AND_CCW_TYPE = 2;
	public static final int X_DIRECTION = 3;
	public static final int Y_DIRECTION = 4;
	
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
	public int getRotaryType() {
		return rotaryType;
	}
	public void setRotaryType(int rotaryType) {
		this.rotaryType = rotaryType;
	}
}
