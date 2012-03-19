package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.CreatePocketRoundBottomBrep;

public class OperationRoundFloorPocket extends CSGSolid
{
	private String name ="";
	private float d1 = 0;
	private float d2 = 0;
	private float depth;
	private float radius0;
	private float radius1;
	private float radius2;
	private float radius3;
	private float radiusBottom;
	
	public OperationRoundFloorPocket(String name, float d1, float d2, float depth, float radius0, float radius1, float radius2, float radius3, float radiusBottom)
	{
		this.d1 = d1;
		this.d2 = d2;
		this.depth = depth;
		this.radius0 = radius0;
		this.radius1 = radius1;
		this.radius2 = radius2;
		this.radius3 = radius3;
		this.radiusBottom = radiusBottom;
		
		CreatePocketRoundBottomBrep roundPocket = new CreatePocketRoundBottomBrep("", d1, d2, depth, radius0, radius1, radius2, radius3, radiusBottom);
		this.vertices = roundPocket.vertexArray;
		this.indices = roundPocket.indexArray;
		this.colors = roundPocket.color3f;
		this.scale(5, 5, 5);
	}
	
	public CSGSolid copy() 
	{
		OperationRoundFloorPocket pocketRound = new OperationRoundFloorPocket(name, d1, d2, depth, radius0, radius1, radius2, radius3, radiusBottom);
		pocketRound.updateLocation(getLocation());
		return pocketRound;
	}
	
}
