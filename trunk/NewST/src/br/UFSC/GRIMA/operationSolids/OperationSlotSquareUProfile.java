package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.CreateSquareUProfileOpenBrep;

public class OperationSlotSquareUProfile extends CSGSolid
{
	private String name;
	private float d1;
	private float width;
	private float depth;
	private float firstRadius = 0;
	private float secondRadius;
	private float firstAngle;
	private float secondAngle;
	
	public OperationSlotSquareUProfile(String name, float d1, float width, float depth,  float firstRadius, float secondRadius, float firstAngle, float secondAngle)
	{
		super();
		this.name = name;
		this.width = width;
		this.d1 = d1;
		this.depth = depth;
		this.firstAngle = firstAngle;
		this.secondAngle = secondAngle;
		this.firstRadius = firstRadius;
		this.secondRadius = secondRadius;
		
		CreateSquareUProfileOpenBrep squareUProfileOpenBrep = new CreateSquareUProfileOpenBrep(this.name, d1, this.width, this.depth, this.firstRadius, this.secondRadius, this.firstAngle, this.secondAngle);
		this.vertices = squareUProfileOpenBrep.vertexArray;
		this.indices = squareUProfileOpenBrep.indexArray;
		this.colors = squareUProfileOpenBrep.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationSlotSquareUProfile operationSlotSquareUProfile = new OperationSlotSquareUProfile(this.name, d1, this.width, this.depth, this.firstRadius, this.secondRadius, this.firstAngle, this.secondAngle);
		operationSlotSquareUProfile.updateLocation(getLocation());
		return operationSlotSquareUProfile;	
	}
	
}
