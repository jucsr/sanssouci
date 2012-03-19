package br.UFSC.GRIMA.util;

public class Ponto {

	private double x;
	private double y;
	private double z;
	
	public Ponto(double x, double y, double z) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	public double getZ()
	{
		return this.z;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public String toString(){
		return "X=" + x + "   Y=" + y + "   Z=" + z;
	}
	
	public String getDados(){
		return ("[" + x + ", " + y + ", " + z + "]\n");
	}
}
