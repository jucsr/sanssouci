package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * 
 * @author Jc
 *
 */
public class RoundedUProfileBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 9;
	private float depth, width, d1;
	private String name;
	
	public RoundedUProfileBrep(String name, float depth, float width, float d1)
	{
		this.name = name;
		this.depth = depth;
		this.width = width;
		this.d1 = d1;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}

	private void generateVertexArray() 
	{
		vertexArray = new Point3d[(numberOfPointsInArc + 3) * 2];
		vertexArray[0] = new Point3d(0, width/2, 0);
		Point3d[] arc = determinatePointsInArcCircunference(0, -Math.PI, width/2, numberOfPointsInArc, 0);
		
		//generate vertex in front face
		for (int i = 0; i < arc.length; i++)
		{
			vertexArray[i + 1] = new Point3d(arc[i].x, arc[i].y + width/2, arc[i].z);
		}
		vertexArray[numberOfPointsInArc + 1] = new Point3d(-width/2, depth, 0); 
		vertexArray[numberOfPointsInArc + 2] = new Point3d(width/2, depth, 0);
		
		//generate vertex in rear face
		for(int i = 0; i < vertexArray.length/2; i++)
		{
			vertexArray[vertexArray.length/2 + i] = new Point3d(vertexArray[i].x, vertexArray[i].y, -d1);
		}
		
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
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

	private void generateColorArray() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
	    {
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
	    }	
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
