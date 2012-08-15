package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.BlockBrep;

public class OperationBlock1 extends CSGSolid
{
	private float lenght, width, high;
	private String name;
	private int nX, nY, nZ;
	public OperationBlock1(String name, float lenght, float width, float high, int nX, int nY, int nZ)
	{
		this.name = name;
		this.lenght = lenght;
		this.width = width;
		this.high = high;
		this.nX = nX;
		this.nY = nY;
		this.nZ = nZ;
		BlockBrep block = new BlockBrep(this.name, this.lenght, this.width, this.high, this.nX, this.nY, this.nZ);
		this.vertices = block.vertexArray;
		this.indices = block.indexArray;
		this.colors = block.color3f;
		this.scale(5, 5, 5);
	}
	@Override
	public CSGSolid copy() 
	{
		OperationBlock1 op = new OperationBlock1(name, lenght, width, high, nX, nY, nZ);
		op.updateLocation(getLocation());
		return op;
	}
}
