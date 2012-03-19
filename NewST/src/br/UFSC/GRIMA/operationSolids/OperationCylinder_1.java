package br.UFSC.GRIMA.operationSolids;

import javax.vecmath.Color3f;

import br.UFSC.GRIMA.bReps.CreateCylinderBrep;

public class OperationCylinder_1 extends CSGSolid
{
	private String name ="";
	private float depth =0;
	private float radius = 0;
	
	public OperationCylinder_1(String name, float radius, float depth)
	{
		super();
		this.name = name;
		this.depth = depth;
		this.radius = radius;
		
		CreateCylinderBrep cyl = new CreateCylinderBrep(name, radius, depth);
		this.vertices = cyl.vertexArray;
		this.indices = cyl.indexArray;
		this.colors = cyl.color3f;
		this.scale(5, 5, 5);
	}
	public OperationCylinder_1(String name, float radius, float depth, int numberOfPoints)
	{
		super();
		this.name = name;
		this.depth = depth;
		this.radius = radius;
		
		CreateCylinderBrep cyl = new CreateCylinderBrep(name, radius, depth, numberOfPoints);
		this.vertices = cyl.vertexArray;
		this.indices = cyl.indexArray;
		this.colors = cyl.color3f;
		this.scale(5, 5, 5);
	}
	public OperationCylinder_1(String name, float radius, float depth, int numberOfPoints, Color3f color3f)
	{
		super();
		this.name = name;
		this.depth = depth;
		this.radius = radius;
		
		CreateCylinderBrep cyl = new CreateCylinderBrep(name, radius, depth, numberOfPoints, color3f);
		this.vertices = cyl.vertexArray;
		this.indices = cyl.indexArray;
		this.colors = cyl.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationCylinder_1 cylinder = new OperationCylinder_1(name, radius, depth);
		cylinder.updateLocation(getLocation());
		return cylinder;
	}
}
