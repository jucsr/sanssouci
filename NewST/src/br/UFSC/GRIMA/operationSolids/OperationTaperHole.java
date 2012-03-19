package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.TaperHoleBrep;

public class OperationTaperHole extends CSGSolid
{
	private String name ="";
	private float depth;
	private float diameter;
	private float finalDiameter;
	public OperationTaperHole(String name, float depth, float diameter, float finalDiameter)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.finalDiameter = finalDiameter;
		
		TaperHoleBrep conicalHoleBottomBrep = new TaperHoleBrep(this.name, this.depth, this.diameter, this.finalDiameter);
		this.vertices = conicalHoleBottomBrep.vertexArray;
		this.colors = conicalHoleBottomBrep.color3f;
		this.indices = conicalHoleBottomBrep.indexArray;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationTaperHole operationTaperHole = new OperationTaperHole(this.name, this.depth, this.diameter, this.finalDiameter);
		operationTaperHole.updateLocation(getLocation());
		return operationTaperHole;
	}
}
