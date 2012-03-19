package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.FlatHoleWithRadiusBottomBrep;

public class OperationRoundHoleBottom extends CSGSolid {
	
	private String name ="";
	private float depth;
	private float diameter;
	private float radiusBottom;
	

	
	public OperationRoundHoleBottom(String name, float depth, float diameter, float radiusBottom){
	
		this.depth = depth;
		this.diameter = diameter;
		this.radiusBottom = radiusBottom;
		
		FlatHoleWithRadiusBottomBrep roundHoleBottom = new FlatHoleWithRadiusBottomBrep("", diameter, depth, radiusBottom);
		this.vertices = roundHoleBottom.vertexArray;
		this.indices = roundHoleBottom.indexArray;
		this.colors = roundHoleBottom.colorArray;
		this.scale(5, 5, 5);
	
	}



	@Override
	public CSGSolid copy() {
		OperationRoundHoleBottom roundBottom = new OperationRoundHoleBottom(name, depth, diameter, radiusBottom);
		roundBottom.updateLocation(getLocation());
		return roundBottom;
	}
	
	

}
