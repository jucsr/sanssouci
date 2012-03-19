package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.ConicalHoleBottomBrep;

public class OperationConicalHoleBottom extends CSGSolid
{
	private String name ="";
	private float depth;
	private float diameter;
	private float tipAngle;
	
	public OperationConicalHoleBottom(String name, float depth, float diameter, float tipAngle)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.tipAngle = tipAngle;
		
		ConicalHoleBottomBrep conicalHoleBottomBrep = new ConicalHoleBottomBrep(this.name, this.depth, this.diameter, 0, this.tipAngle);
		this.vertices = conicalHoleBottomBrep.vertexArray;
		this.colors = conicalHoleBottomBrep.color3f;
		this.indices = conicalHoleBottomBrep.indexArray;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationConicalHoleBottom operationConicalHoleBottom = new OperationConicalHoleBottom(this.name, this.depth, this.diameter, this.tipAngle);
		operationConicalHoleBottom.updateLocation(getLocation());
		return operationConicalHoleBottom;
	}
}
