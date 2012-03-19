package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * 
 * @author Jc
 *
 */
public class CreatePartialCircularProfileBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 9;
	private float d1, radius, initialAngle, sweepAngle;
	private String name;
	
	public CreatePartialCircularProfileBrep(String name, float d1, float radius, float initialAngle, float sweepAngle)
	{
		this.name = name;
		this.d1 = d1;
		this.radius = radius;
		this.initialAngle = initialAngle;
		this.sweepAngle = sweepAngle;
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
		indexArray = new int[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 2 * 3];
		
		for (int i = 0; i < numberOfPointsInArc - 1; i++)
		{
			//generate index for front face
			indexArray[i * 3] = 0;
			indexArray[i * 3 + 1] = i + 2;
			indexArray[i * 3 + 2] = i + 1;
			
			//generate index for rear face
			indexArray[(numberOfPointsInArc - 1) * 3 + 3 * i] = vertexArray.length/2;
			indexArray[(numberOfPointsInArc - 1) * 3 + 3 * i + 1] = vertexArray.length/2 + i + 1;
			indexArray[(numberOfPointsInArc - 1) * 3 + 3 * i + 2] = vertexArray.length/2 + i + 2;
		}
		
		//generate index for side face
		for (int i = 0; i < vertexArray.length/2 - 1; i++)
		{
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + 3 * i] = i;
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + 3 * i + 1] = i + 1;
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + 3 * i + 2] = vertexArray.length/2 + i;
			
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 + 3 * i] = vertexArray.length/2 + i;
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 + 3 * i + 2] = vertexArray.length/2 + i + 1;
			indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 + 3 * i + 1] = i + 1;
		}
		
		//generate index for special triangles in side face
		indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 - 3] = vertexArray.length/2 - 1;
		indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 - 2] = 0;
		indexArray[(numberOfPointsInArc - 1) * 3 * 2 + (numberOfPointsInArc + 1) * 3 - 1] = vertexArray.length - 1;;

		indexArray[indexArray.length - 3] = 0;
		indexArray[indexArray.length - 1] = vertexArray.length - 1;
		indexArray[indexArray.length - 2]= vertexArray.length/2; 
		
		//verification
		/*for (int i = 0; i < indexArray.length; i++)
		{
			System.out.println(i + " indexArray = " + indexArray[i]);
		}*/
	}

	private void generateVertexArray() 
	{
		vertexArray = new Point3d[(numberOfPointsInArc + 1) * 2];
		Point3d [] arc = this.determinatePointsInArcCircunference(-(initialAngle * Math.PI/180), -sweepAngle * Math.PI/180, radius, numberOfPointsInArc, 0);
		vertexArray[0] = new Point3d(0, 0, 0);
		//generate vertex in front face
		for (int i = 0; i < arc.length; i++)
		{
			vertexArray[i + 1] = arc[i];
		}
		
		//generate vertex in rear face
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
