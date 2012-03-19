package br.UFSC.GRIMA.operationSolids;

public class OperationPocket extends CSGSolid {
	/*
	 * String name =""; float depth =0; float radius = 0; float d1; float d2;
	 * public CSGSolid pocket;
	 */

	public OperationPocket() {
		super();
		/*
		 * this.name = name; this.depth = depth; this.radius = r; this.d1=d1;
		 * this.d2=d2;
		 */

	}

	@Override
	public CSGSolid copy() {
		OperationPocket pocket = new OperationPocket();
		pocket.updateLocation(getLocation());
		return pocket;
	}

}
