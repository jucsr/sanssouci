package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public class TrochoidalAndContourParallelStrategy extends ContourParallel
{
	private double trochoidalRadius;
	private int trochoidalSense;
	
	public static final int CCW = 0;
	public static final int CW = 1;
	
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
}
