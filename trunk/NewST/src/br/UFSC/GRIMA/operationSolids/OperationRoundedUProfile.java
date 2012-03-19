package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.RoundedUProfileBrep;

public class OperationRoundedUProfile extends CSGSolid
{
	private String name;
	private float d1;
	private float depth;
	private float width = 0;
	
	public OperationRoundedUProfile(String name, float depth, float width, float d1)
	{
		super();
		this.name = name;
		this.depth = depth;
		this.d1 = d1;
		this.width = width;
		
		RoundedUProfileBrep partialCircularProfile = new RoundedUProfileBrep(this.name, this.depth,  this.width, this.d1);
		this.vertices = partialCircularProfile.vertexArray;
		this.indices = partialCircularProfile.indexArray;
		this.colors = partialCircularProfile.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationRoundedUProfile operationRoundedUProfile = new OperationRoundedUProfile(this.name, this.depth,  this.width, this.d1);
		operationRoundedUProfile.updateLocation(getLocation());
		return operationRoundedUProfile;	
	}
	
}
