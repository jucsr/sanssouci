package br.UFSC.GRIMA.operationSolids;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.BezierSurfaceBrep;

/**
 * 
 * @author Jc
 *
 */
public class OperationBezierSurface extends CSGSolid
{
	private Point3d[][] controlVertex;
	private String name = "";
	private int splitU = 11, splitV = 11;

	public OperationBezierSurface(String name, Point3d[][] controlVertex, int splitU, int splitV)
	{
		super();
		this.name = name;
		this.controlVertex = controlVertex;
		this.splitU = splitU;
		this.splitV = splitV;
		
		BezierSurfaceBrep bezierSurface = new BezierSurfaceBrep(this.name,this.controlVertex, this.splitU, this.splitV);
		this.vertices = bezierSurface.vertexArray;
		this.indices = bezierSurface.indexArray;
		this.colors = bezierSurface.color3f;
		this.scale(5, 5, 5);
	}
	@Override
	public CSGSolid copy()
	{
		OperationBezierSurface operation = new OperationBezierSurface(this.name, this.controlVertex, this.splitU, this.splitV);
		operation.defineAppearance(true);
		operation.updateLocation(getLocation());
		return operation;
	}

}
