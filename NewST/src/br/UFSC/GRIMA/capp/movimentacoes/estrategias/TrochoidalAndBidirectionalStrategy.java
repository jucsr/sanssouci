package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public class TrochoidalAndBidirectionalStrategy extends Bidirectional
{
	private double trochoidalRadius;

	public double getTrochoidalRadius() 
	{
		return trochoidalRadius;
	}

	public void setTrochoidalRadius(double trochoidalRadius) 
	{
		this.trochoidalRadius = trochoidalRadius;
	}
}