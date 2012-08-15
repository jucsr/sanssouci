package br.UFSC.GRIMA.operationSolids;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import br.UFSC.GRIMA.bReps.CreateGeneralPocketBrep;

public class OperationGeneralClosedPocked extends CSGSolid
{

		private String name ="";
		private float depth;
		private float radius;
		private ArrayList<Point2D> points;
		
		public OperationGeneralClosedPocked(String name, float depth, float radius, ArrayList<Point2D> points)
		{
			this.name = name;
			this.depth = depth;
			this.radius = radius;
			this.points = points;

			CreateGeneralPocketBrep generalBrep = new CreateGeneralPocketBrep(this.name, this.depth, this.radius, this.points);
			this.vertices = generalBrep.vertexArray;
			this.colors = generalBrep.color3f;
			this.indices = generalBrep.indexArray;
			this.scale(5, 5, 5);
		}
		public CSGSolid copy() 
		{
			OperationGeneralClosedPocked generalBrep = new OperationGeneralClosedPocked(this.name, this.depth, this.radius, this.points);
			generalBrep.updateLocation(getLocation());
			return generalBrep;
		}
}
