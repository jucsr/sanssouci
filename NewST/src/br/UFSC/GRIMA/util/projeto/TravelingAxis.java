package br.UFSC.GRIMA.util.projeto;
/**
 * 
 * @author Jc
 *
 */
public class TravelingAxis extends Axis
{
	private double itsTravelingRange;
	private double itsFeedRateRange;
	private double rapidMovementSpeed;
	
	public double getItsTravelingRange() 
	{
		return itsTravelingRange;
	}
	public void setItsTravelingRange(double itsTravelingRange) 
	{
		this.itsTravelingRange = itsTravelingRange;
	}
	public double getRapidMovementSpeed() 
	{
		return rapidMovementSpeed;
	}
	public void setRapidMovementSpeed(double rapidMovementSpeed) 
	{
		this.rapidMovementSpeed = rapidMovementSpeed;
	}
	public double getItsFeedRateRange() 
	{
		return itsFeedRateRange;
	}
	public void setItsFeedRateRange(double itsFeedRateRange) 
	{
		this.itsFeedRateRange = itsFeedRateRange;
	}
}
