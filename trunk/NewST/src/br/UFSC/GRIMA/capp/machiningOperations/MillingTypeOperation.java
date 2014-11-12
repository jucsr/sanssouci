package br.UFSC.GRIMA.capp.machiningOperations;

import br.UFSC.GRIMA.capp.plunge.ApproachRetractStrategy;

/**
 * 
 * @author Jc
 *
 */
public abstract class MillingTypeOperation extends MachiningOperation
{ 
	private ApproachRetractStrategy approachStrategy;
	private ApproachRetractStrategy retractStrategy;
	
	public MillingTypeOperation(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}

	public ApproachRetractStrategy getApproachStrategy() 
	{
		return approachStrategy;
	}

	public void setApproachStrategy(ApproachRetractStrategy approachStrategy) 
	{
		this.approachStrategy = approachStrategy;
	}

	public ApproachRetractStrategy getRetractStrategy() 
	{
		return retractStrategy;
	}

	public void setRetractStrategy(ApproachRetractStrategy retractStrategy)
	{
		this.retractStrategy = retractStrategy;
	}
}