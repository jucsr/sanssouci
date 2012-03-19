package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.SphericalHoleBottomBrep;

public class OperationSphericalHoleBottom extends CSGSolid
{
	String name;
	float depth;
	float diameter;
	public OperationSphericalHoleBottom(String name, float depth, float diameter)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		
		SphericalHoleBottomBrep sphericalHoleBottomBrep = new SphericalHoleBottomBrep(this.name, this.diameter, this.depth);
		this.vertices = sphericalHoleBottomBrep.vertexArray;
		this.colors = sphericalHoleBottomBrep.colorArray;
		this.indices = sphericalHoleBottomBrep.indexArray;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() {
		OperationSphericalHoleBottom operationSphericalHoleBottom = new OperationSphericalHoleBottom(this.name, this.depth, this.diameter);
		operationSphericalHoleBottom.updateLocation(getLocation());
		return operationSphericalHoleBottom;
	}

}
