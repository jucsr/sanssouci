package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;


public class ConicalHoleBottomBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 20;
	private float depth, diameter, tipRadius, tipAngle;
	private String name;
	
	public ConicalHoleBottomBrep(String name, float depth, float diameter, float tipRadius, float tipAngle)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.tipRadius = tipRadius;
		this.tipAngle = tipAngle;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}

	private void generateVertexArray() 
	{
		Point3d[] topCircle = determinatePointsInCircunference(0, 2 * Math.PI, diameter/2, numberOfPointsInArc, 0);
		Point3d[] bottomCircle = null;
		if (tipRadius >= 1E-16 && tipAngle >= 1E-16)
		{
			vertexArray = new Point3d[(numberOfPointsInArc + 1) * 3 - 1];
			vertexArray[0] = new Point3d(0, 0, 0);
			vertexArray[vertexArray.length/2] = new Point3d(0, -depth, 0);
			Point3d [] middleCircle = determinatePointsInCircunference(0, 2 * Math.PI, diameter/2, numberOfPointsInArc, -depth);
			bottomCircle = determinatePointsInCircunference(0, 2 * Math.PI, tipRadius, numberOfPointsInArc, -depth - (diameter/2 - tipRadius) * Math.tan(tipAngle * Math.PI/180));
			for (int i = 0; i < topCircle.length; i++)
			{
				//create vertex in top face
				vertexArray[i + 1] = topCircle[i];
				//create vertex in middle face
				vertexArray[numberOfPointsInArc + 1 + i] = middleCircle[i];
				//create vertex in bottom face
				vertexArray[numberOfPointsInArc * 2 + 1 + i] = bottomCircle[i];
			}
			vertexArray[vertexArray.length - 1] = new Point3d(0 , bottomCircle[0].y, 0);
		}
		else
		{
			vertexArray = new Point3d[(numberOfPointsInArc + 1) * 2];
			vertexArray[0] = new Point3d(0, 0, 0);
			double totalDepth = depth + diameter * Math.tan(tipAngle * Math.PI/180)/2;
			bottomCircle = determinatePointsInCircunference(0, 2 * Math.PI, diameter/2, numberOfPointsInArc, - depth);
			for (int i = 0; i < topCircle.length; i++)
			{
				//create vertex in top face
				vertexArray[i + 1] = topCircle[i];
				//create vertex in bottom face
				vertexArray[vertexArray.length/2 + i] = bottomCircle[i];
			}
			vertexArray[vertexArray.length - 1] = new Point3d(0, -totalDepth, 0);
		}
		
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
	}

	private void generateIndexArray() 
	{
		if (tipRadius >= 1E-16 && tipAngle >= 1E-16)
		{
			indexArray = new int[numberOfPointsInArc * 6 * 3];
			
			for (int i = 0; i < numberOfPointsInArc - 1; i++)
			{
				//generate index for top face
				indexArray[i * 3] = 0;
				indexArray[i * 3 + 1] = i + 2;
				indexArray[i * 3 + 2] = i + 1;
				
				//generate index for side face
				indexArray[numberOfPointsInArc * 3 + i * 3] = i + 1;
				indexArray[numberOfPointsInArc * 3 + i * 3 + 1] = i + 2;
				indexArray[numberOfPointsInArc * 3 + i * 3 + 2] = numberOfPointsInArc + 1 + i;
				
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3] = i + 2;
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3 + 1] = numberOfPointsInArc + i + 2;
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3 + 2] = numberOfPointsInArc + i + 1;

				indexArray[numberOfPointsInArc * 3 * 3 + i * 3] = numberOfPointsInArc + 1 + i;
				indexArray[numberOfPointsInArc * 3 * 3 + i * 3 + 1] = numberOfPointsInArc + 2 + i;
				indexArray[numberOfPointsInArc * 3 * 3 + i * 3 + 2] = numberOfPointsInArc * 2 + 1 + i;
				
				indexArray[numberOfPointsInArc * 4 * 3 + i * 3] = numberOfPointsInArc * 2 + 2 + i;
				indexArray[numberOfPointsInArc * 4 * 3 + i * 3 + 1] = numberOfPointsInArc * 2 + 1 + i;
				indexArray[numberOfPointsInArc * 4 * 3 + i * 3 + 2] = numberOfPointsInArc + 2 + i;

				//generate index for bottom face
				indexArray[numberOfPointsInArc * 5 * 3 + i * 3] = vertexArray.length - 1;
				indexArray[numberOfPointsInArc * 5 * 3 + i * 3 + 1] = numberOfPointsInArc * 2 + 1 + i;
				indexArray[numberOfPointsInArc * 5 * 3 + i * 3 + 2] = numberOfPointsInArc * 2 + 2 + i;
			}
			//special triangles
			indexArray[numberOfPointsInArc * 3 - 3] = 0;
			indexArray[numberOfPointsInArc * 3 - 2] = 1;
			indexArray[numberOfPointsInArc * 3 - 1] = numberOfPointsInArc;
			
			indexArray[numberOfPointsInArc * 2 * 3 - 3] = 1;
			indexArray[numberOfPointsInArc * 2 * 3 - 2] = numberOfPointsInArc * 2;
			indexArray[numberOfPointsInArc * 2 * 3 - 1] = numberOfPointsInArc * 1;
			
			indexArray[numberOfPointsInArc * 3 * 3 - 3] = 1;
			indexArray[numberOfPointsInArc * 3 * 3 - 2] = numberOfPointsInArc + 1;
			indexArray[numberOfPointsInArc * 3 * 3 - 1] = numberOfPointsInArc * 2;

			indexArray[numberOfPointsInArc * 4 * 3 - 3] = numberOfPointsInArc * 2;
			indexArray[numberOfPointsInArc * 4 * 3 - 2] = numberOfPointsInArc + 1;
			indexArray[numberOfPointsInArc * 4 * 3 - 1] = numberOfPointsInArc * 2 + 1;

			indexArray[numberOfPointsInArc * 5 * 3 - 3] = numberOfPointsInArc * 2 + 1;
			indexArray[numberOfPointsInArc * 5 * 3 - 2] = numberOfPointsInArc * 3;
			indexArray[numberOfPointsInArc * 5 * 3 - 1] = numberOfPointsInArc * 2;
			
			indexArray[indexArray.length - 3] = vertexArray.length - 1;
			indexArray[indexArray.length - 2] = numberOfPointsInArc * 3;
			indexArray[indexArray.length - 1] = numberOfPointsInArc * 2 + 1;
		}
		else
		{
			indexArray = new int[numberOfPointsInArc * 5 * 3];
			
			for (int i = 0; i < numberOfPointsInArc - 1; i++)
			{
				//generate index for top face
				indexArray[i * 3] = 0;
				indexArray[i * 3 + 1] = i + 2;
				indexArray[i * 3 + 2] = i + 1;
				
				//generate index for side face
				indexArray[numberOfPointsInArc * 3 + i * 3] = i + 1;
				indexArray[numberOfPointsInArc * 3 + i * 3 + 1] = i + 2;
				indexArray[numberOfPointsInArc * 3 + i * 3 + 2] = numberOfPointsInArc + 1 + i;
				
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3] = i + 2;
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3 + 1] = numberOfPointsInArc + i + 2;
				indexArray[numberOfPointsInArc * 2 * 3 + i * 3 + 2] = numberOfPointsInArc + i + 1;
				
				//generate index for bottom face
				indexArray[numberOfPointsInArc * 3 * 3 + i * 3] = vertexArray.length - 1;
				indexArray[numberOfPointsInArc * 3 * 3 + i * 3 + 1] = numberOfPointsInArc + 1 + i;
				indexArray[numberOfPointsInArc * 3 * 3 + i * 3 + 2] = numberOfPointsInArc + 2 + i;
			}
			
			//special triangles
			indexArray[numberOfPointsInArc * 3 - 3] = 0;
			indexArray[numberOfPointsInArc * 3 - 2] = 1;
			indexArray[numberOfPointsInArc * 3 - 1] = numberOfPointsInArc;
			
			indexArray[numberOfPointsInArc * 2 * 3 - 3] = 1;
			indexArray[numberOfPointsInArc * 2 * 3 - 2] = numberOfPointsInArc * 2;
			indexArray[numberOfPointsInArc * 2 * 3 - 1] = numberOfPointsInArc * 1;
			
			indexArray[numberOfPointsInArc * 3 * 3 - 3] = 1;
			indexArray[numberOfPointsInArc * 3 * 3 - 2] = numberOfPointsInArc + 1;
			indexArray[numberOfPointsInArc * 3 * 3 - 1] = numberOfPointsInArc * 2;
			
			indexArray[indexArray.length - 3] = vertexArray.length - 1;
			indexArray[indexArray.length - 2] = numberOfPointsInArc * 2;
			indexArray[indexArray.length - 1] = numberOfPointsInArc + 1;
		}
		
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
	
	public Point3d [] determinatePointsInCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double yPlane)
	{
		Point3d[] output = new Point3d [numberOfPoints];
		double x, z;
		double dAngle = 0;
		dAngle = deltaAngle / numberOfPoints;
		for(int i = 0; i < numberOfPoints; i++)
		{
			x = radius * Math.cos(initialAngle + i * dAngle);
			z = radius * Math.sin(initialAngle  + i * dAngle);
			output[i] = new Point3d((float)x,(float)yPlane, (float)z);
		}
		return output;
	}
}
