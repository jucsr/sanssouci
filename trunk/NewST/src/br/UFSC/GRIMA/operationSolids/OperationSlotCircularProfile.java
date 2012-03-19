package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.CreatePartialCircularProfileBrep;

/**
 * 
 * @author Jc
 *
 */
public class OperationSlotCircularProfile extends CSGSolid
{
	private String name;
	private float d1;
	private float sweepAngle;
	private float radius = 0;
	private float initialAngle;
	
	public OperationSlotCircularProfile(String name, float d1, float radius, float initialAngle, float sweepAngle)
	{
		super();
		this.name = name;
		this.sweepAngle = sweepAngle;
		this.d1 = d1;
		this.initialAngle = initialAngle;
		this.radius = radius;
		
		CreatePartialCircularProfileBrep partialCircularProfile = new CreatePartialCircularProfileBrep(this.name, this.d1,  this.radius, this.initialAngle, this.sweepAngle);
		this.vertices = partialCircularProfile.vertexArray;
		this.indices = partialCircularProfile.indexArray;
		this.colors = partialCircularProfile.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationSlotCircularProfile operationSlotCircularProfile = new OperationSlotCircularProfile(this.name, this.d1,  this.radius, this.initialAngle, this.sweepAngle);
		operationSlotCircularProfile.updateLocation(getLocation());
		return operationSlotCircularProfile;	
	}
	
}
