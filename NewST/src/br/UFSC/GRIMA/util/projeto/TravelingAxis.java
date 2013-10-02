package br.UFSC.GRIMA.util.projeto;
/**
 * 
 * @author Jc
 *
 */
public class TravelingAxis extends Axis
{
	private int travelingDirection;
	private double itsTravelingRange;
	private double itsFeedRateRange;
	private double rapidMovementSpeed;
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	private String typeOfAxisString;
	
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
	public int getTravelingDirection() {
		return travelingDirection;
	}
	public void setTravelingDirection(int travelingDirection) {
		this.travelingDirection = travelingDirection;
	}
	public String getTypeOfAxisString()
	{
		this.typeOfAxisString = "Traveling Axis";
		return this.typeOfAxisString;
	}
}
