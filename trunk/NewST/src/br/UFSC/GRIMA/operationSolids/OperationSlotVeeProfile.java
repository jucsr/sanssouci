package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.CreateVeeProfileBrep;

/**
 * 
 * @author Jc
 *
 */

public class OperationSlotVeeProfile extends CSGSolid
{
	private String name;
	private float d1;
	private float profileAngle;
	private float radius = 0;
	private float tiltAngle;
	private float depht;
	
	public OperationSlotVeeProfile(String name, float d1, float depth, float radius, float initialAngle, float profileAngle)
	{
		super();
		this.name = name;
		this.profileAngle = profileAngle;
		this.d1 = d1;
		this.tiltAngle = initialAngle;
		this.radius = radius;
		this.depht = depth;
		
		CreateVeeProfileBrep veeProfile = new CreateVeeProfileBrep(this.name, this.d1,  this.depht, this.radius, this.tiltAngle, this.profileAngle);
		this.vertices = veeProfile.vertexArray;
		this.indices = veeProfile.indexArray;
		this.colors = veeProfile.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationSlotVeeProfile operationSlotVeeProfile = new OperationSlotVeeProfile(this.name, this.d1,  this.depht, this.radius, this.tiltAngle, this.profileAngle);
		operationSlotVeeProfile.updateLocation(getLocation());
		return operationSlotVeeProfile;	
	}
	
}
