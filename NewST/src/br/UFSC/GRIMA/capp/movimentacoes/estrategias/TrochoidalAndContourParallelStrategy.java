package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public class TrochoidalAndContourParallelStrategy extends ContourParallel
{
	//Trocar a variavel trocoidal sense por um boolean isCCW
	private double trochoidalRadius;
	private int trochoidalSense;
	private double radialDephtPercent;
	public static final int CCW = 0;
	public static final int CW = 1;
	private double trochoidalFeedRate;
	
	public double getRadialDephtPercent()
	{
		return radialDephtPercent;
	}
	public void setRadialDephtPercent(double radialDephtPercent)
	{
		this.radialDephtPercent = radialDephtPercent;
	}
	
	public double getTrochoidalRadius() 
	{
		return trochoidalRadius;
	}
	public void setTrochoidalRadius(double trochoidalRadius)
	{
		this.trochoidalRadius = trochoidalRadius;
	}
	public int getTrochoidalSense() 
	{
		return trochoidalSense;
	}
	public void setTrochoidalSense(int trochoidalSense) 
	{
		this.trochoidalSense = trochoidalSense;
	}
	public double getTrochoidalFeedRate() 
	{
		return trochoidalFeedRate;
	}
	public void setTrochoidalFeedRate(double trochoidalFeedRate)
	{
		this.trochoidalFeedRate = trochoidalFeedRate;
	}
}
