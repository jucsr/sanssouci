package br.UFSC.GRIMA.operationSolids;

import br.UFSC.GRIMA.bReps.CreatePocketBrep;

public class OperationPocket_1 extends CSGSolid {

	String name = "";
	float d1 = 0;
	float d2 = 0;
	float depth = 0;
	float radius = 0;

	public OperationPocket_1(String name, float d1, float d2, float depth,
			float radius) {
		super();
		this.name = name;
		this.d1 = d1;
		this.d2 = d2;
		this.depth = depth;
		this.radius = radius;

		CreatePocketBrep pbr = new CreatePocketBrep("", d1, d2, depth, radius);
		super.vertices = pbr.vertexArray;
		super.indices = pbr.indexArray;
		super.colors = pbr.color3f;
		scale(5, 5, 5);
		// loadCoordinateFile(cilinderArg,new Color3f(0.3f,0.3f,0.3f));
		// scale(radius*5, d1*5, radius*5);
	}

	@Override
	public CSGSolid copy() {
		OperationPocket_1 op = new OperationPocket_1(name, d1, d2, depth,
				radius);
		op.updateLocation(getLocation());
		return op;
	}

}
