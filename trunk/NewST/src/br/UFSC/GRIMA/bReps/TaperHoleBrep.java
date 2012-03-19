package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class TaperHoleBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 20;
	private float depth, diameter, finalDiameter;
	public String name;
	
	public TaperHoleBrep(String name, float depth, float diameter, float finalDiameter)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.finalDiameter = finalDiameter;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}
	public TaperHoleBrep(String name, float depth, float diameter, float finalDiameter, int numberOfPointsInArc)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.finalDiameter = finalDiameter;
		this.numberOfPointsInArc = numberOfPointsInArc;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}
	public TaperHoleBrep(String name, float depth, float diameter, float finalDiameter, int numberOfPointsInArc, Color3f color3f)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.finalDiameter = finalDiameter;
		this.numberOfPointsInArc = numberOfPointsInArc;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray(color3f);
	}
	private void generateVertexArray() 
	{
		vertexArray = new Point3d[(numberOfPointsInArc + 1) * 2];
		
		vertexArray[0] = new Point3d(0, 0, 0);
		vertexArray[vertexArray.length/2] = new Point3d(0, -depth, 0);
		Point3d[] topCircle = determinatePointsInCircunference(0, 2 * Math.PI, diameter/2, numberOfPointsInArc, 0);
		Point3d[] bottomCircle = determinatePointsInCircunference(0, 2 * Math.PI, finalDiameter/2, numberOfPointsInArc, -depth);
		for (int i = 0; i < topCircle.length; i++)
		{
			//create vertex in top face
			vertexArray[i + 1] = topCircle[i];
			//create vertex in bottom face
			vertexArray[vertexArray.length/2 + 1 + i] = bottomCircle[i];
		}
		
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
	}
	
	
	private void generateIndexArray() 
	{
		indexArray = new int [numberOfPointsInArc * 4 * 3];
		
		for (int i = 0; i < numberOfPointsInArc - 1; i++)
		{		
			//generate index in top face
			indexArray[3 * i] = 0;
			indexArray[3 * i + 2] = i + 1;
			indexArray[3 * i + 1] = i + 2;
			
			//generate index in bottom face
			indexArray[numberOfPointsInArc * 3 + 3 * i] = vertexArray.length/2;
			indexArray[numberOfPointsInArc * 3 + 3 * i + 1] = vertexArray.length/2 + i + 1;
			indexArray[numberOfPointsInArc * 3 + 3 * i + 2] = vertexArray.length/2 + i + 2;
			
			//generate index in side face
			indexArray[numberOfPointsInArc * 3 * 2 + 3 * i] = i + 1;
			indexArray[numberOfPointsInArc * 3 * 2 + 3 * i + 1] = i + 2;
			indexArray[numberOfPointsInArc * 3 * 2 + 3 * i + 2] = vertexArray.length/2 + 1 + i;
		
			indexArray[numberOfPointsInArc * 3 * 3 + 3 * i] = i + 2;
			indexArray[numberOfPointsInArc * 3 * 3 + 3 * i + 1] = vertexArray.length/2 + 2 + i;
			indexArray[numberOfPointsInArc * 3 * 3 + 3 * i + 2] = vertexArray.length/2 + 1 + i;
		}
		
		//generate index for special triangles
		indexArray[numberOfPointsInArc * 3 - 3] = 0;
		indexArray[numberOfPointsInArc * 3 - 2] = 1;
		indexArray[numberOfPointsInArc * 3 - 1] = vertexArray.length/2 - 1;
		
		indexArray[numberOfPointsInArc * 2 * 3 - 3] = vertexArray.length/2;
		indexArray[numberOfPointsInArc * 2 * 3 - 2] = vertexArray.length - 1;
		indexArray[numberOfPointsInArc * 2 * 3 - 1] = vertexArray.length/2 + 1;
		
		indexArray[numberOfPointsInArc * 3 * 3 - 3] = vertexArray.length/2 - 1;
		indexArray[numberOfPointsInArc * 3 * 3 - 2] = 1;
		indexArray[numberOfPointsInArc * 3 * 3 - 1] = vertexArray.length - 1;
		
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
	private void generateColorArray(Color3f color3f)
	{
		this.color3f = new Color3f [this.vertexArray.length];
		for(int i = 0; i < this.color3f.length; i++)
		{
			this.color3f[i] = new Color3f(color3f);
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
