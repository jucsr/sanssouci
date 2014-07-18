package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public abstract class Two5DMillingStrategy extends MachinningStrategy
{
	private double overLap;
	private boolean allowMultiplePasses = true;
	public double getOverLap() 
	{
		return overLap;
	}
	public void setOverLap(double overLap) 
	{
		this.overLap = overLap;
	}
	public boolean isAllowMultiplePasses() 
	{
		return allowMultiplePasses;
	}
	public void setAllowMultiplePasses(boolean allowMultiplePasses) 
	{
		this.allowMultiplePasses = allowMultiplePasses;
	}
}
