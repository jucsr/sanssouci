package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * 
 * @author Jc
 *
 */
public class CreateVeeProfileBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 5;
	private float d1, depth, radius, tiltAngle, profileAngle;
	private String name;
	
	public CreateVeeProfileBrep(String name, float d1, float depth, float radius, float tiltAngle, float profileAngle)
	{
		this.name = name;
		this.d1 = d1;
		this.depth = depth;
		this.radius = radius;
		this.tiltAngle = tiltAngle;
		this.profileAngle = profileAngle;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}

	private void generateColorArray() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
	    {
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
	    }	
	}

	private void generateIndexArray() 
	{
		indexArray = new int[(numberOfPointsInArc + 2) * 3 * 2 + (numberOfPointsInArc + 2) * 3 * 2];
		
		for(int i = 0; i < vertexArray.length/2 - 2; i++)
		{
			//generate index for front face
			indexArray[i * 3] = 0;
			indexArray[i * 3 + 2] = i + 1;
			indexArray[i * 3 + 1] = i + 2;
			
			//generate index for rear face
			indexArray[(numberOfPointsInArc + 2) * 3 + i * 3] = vertexArray.length/2;
			indexArray[(numberOfPointsInArc + 2) * 3 + i * 3 + 1] = vertexArray.length/2 + 1 + i;
			indexArray[(numberOfPointsInArc + 2) * 3 + i * 3 + 2] = vertexArray.length/2 + 2 + i;
			
			//generate index for side faces
			indexArray[(numberOfPointsInArc + 2) * 3 * 2 + i * 3] = i + 1;
			indexArray[(numberOfPointsInArc + 2) * 3 * 2 + i * 3 + 1] = i + 2;
			indexArray[(numberOfPointsInArc + 2) * 3 * 2 + i * 3 + 2] = vertexArray.length/2 + i + 1;

			indexArray[(numberOfPointsInArc + 2) * 3 * 3 + i * 3] = vertexArray.length/2 + i + 2;
			indexArray[(numberOfPointsInArc + 2) * 3 * 3 + i * 3 + 1] = vertexArray.length/2 + i + 1;
			indexArray[(numberOfPointsInArc + 2) * 3 * 3 + i * 3 + 2] = i + 2;
		}
		
		//special triangles in front and rear faces
		indexArray[(numberOfPointsInArc + 2) * 3 - 3] = 0;
		indexArray[(numberOfPointsInArc + 2) * 3 - 2] = 1;
		indexArray[(numberOfPointsInArc + 2) * 3 - 1] = vertexArray.length/2 - 1;
		
		indexArray[(numberOfPointsInArc + 2) * 3 * 2 - 3] = vertexArray.length/2;
		indexArray[(numberOfPointsInArc + 2) * 3 * 2 - 2] = vertexArray.length - 1;
		indexArray[(numberOfPointsInArc + 2) * 3 * 2 - 1] = vertexArray.length/2 + 1;
		
		//special triangles in side faces
		indexArray[(numberOfPointsInArc + 2) * 3 * 3 - 3] = vertexArray.length/2 - 1;
		indexArray[(numberOfPointsInArc + 2) * 3 * 3 - 2] = 1;
		indexArray[(numberOfPointsInArc + 2) * 3 * 3 - 1] = vertexArray.length - 1;
		
		indexArray[indexArray.length - 3] = 1;
		indexArray[indexArray.length - 2] = vertexArray.length/2 + 1;
		indexArray[indexArray.length - 1] = vertexArray.length - 1;
		
		//verification
		/*for (int i = 0; i < indexArray.length; i++)
		{
			System.out.println(i + " indexArray = " + indexArray[i]);
		}*/
	}

	private void generateVertexArray() 
	{
		vertexArray = new Point3d[(numberOfPointsInArc + 3) * 2];
		//generate vertex in front face
		Point3d[] arc = new Point3d[numberOfPointsInArc];
		double deltaAngle = -(180 - profileAngle) * Math.PI/180;
		//System.out.println(" deltaAngle = " + deltaAngle * 180 / Math.PI);
		double initialAngle = -(-90 + tiltAngle + profileAngle) * Math.PI/180;
		//System.out.println(" initialAngle = " + initialAngle * 180 / Math.PI);
		arc = determinatePointsInArcCircunference(initialAngle, deltaAngle, radius, numberOfPointsInArc, 0);
		
		//vertex in front face
		if (depth >= radius)
		{
			vertexArray[0] = new Point3d(0, radius, 0);
		}
		else
		{
			vertexArray[0] = new Point3d(0, depth, 0);
		}
		for (int i = 0; i < arc.length; i++)
		{
			vertexArray[i + 1] = new Point3d(arc[i].x, arc[i].y + radius, 0);
		}
		double xt = radius/(Math.cos((90 - tiltAngle) * Math.PI/180)) + (depth - radius)/Math.tan(tiltAngle * Math.PI/180);
		vertexArray[numberOfPointsInArc + 1] = new Point3d(-xt, depth, 0);
		double xi = radius/Math.cos(Math.abs(initialAngle)) + (depth - radius) * Math.tan(Math.abs(initialAngle));
		vertexArray[numberOfPointsInArc + 2] = new Point3d(xi, depth, 0);
		
		//vertex in rear face
		for (int i = 0; i < vertexArray.length/2; i++)
		{
			vertexArray[vertexArray.length/2 + i] = new Point3d(vertexArray[i].x, vertexArray[i].y, -d1);
		}
		
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
	}
	private Point3d[] determinatePointsInArcCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double zPlane)
	{
      Point3d[] output = new Point3d [numberOfPoints];
      double x, y;
      double dAngle = 0;
      dAngle = deltaAngle / (numberOfPoints-1);
      for(int i = 0; i < numberOfPoints; i++)
      {
          x = radius * Math.cos(initialAngle + i * dAngle);
          y = radius * Math.sin(initialAngle  + i * dAngle);
          output[i] = new Point3d((float)x,(float)y, (float)zPlane);
          //System.out.println(i + " x z:" + output[i].getX() + " " + output[i].getZ());
      }
      return output;
	}	
}
