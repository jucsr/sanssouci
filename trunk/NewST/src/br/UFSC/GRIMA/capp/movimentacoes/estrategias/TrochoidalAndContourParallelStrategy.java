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
//	private int trochoidalSense;
	private boolean trochoidalSense;
	private double radialDephtPercent;
	public static final boolean CCW = false;
	public static final boolean CW = true;
//	private double trochoidalFeedRate;
	
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
	public boolean getTrochoidalSense() 
	{
		return trochoidalSense;
	}
	public void setTrochoidalSense(boolean trochoidalSense) 
	{
		this.trochoidalSense = trochoidalSense;
	}
//	public double getTrochoidalFeedRate() 
//	{
//		return trochoidalFeedRate;
//	}
//	public void setTrochoidalFeedRate(double trochoidalFeedRate)
//	{
//		this.trochoidalFeedRate = trochoidalFeedRate;
//	}
}
