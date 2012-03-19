package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;


public class FlatHoleWithRadiusBottomBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] colorArray;
	private Point3d [] arcPointsBottom;
	private Point3d[][] arraysInArcLateral;
	private int numberOfPointsInCircle = 20, numberOfPointsInFloor = 4;
	private float diameter, depth, radiusFloor;
	private String name;
		
	public FlatHoleWithRadiusBottomBrep(String name, float diameter, float depth, float radiusFloor)
	{
		this.name = name;
		this.depth = depth;
		this.diameter = diameter;
		this.radiusFloor = radiusFloor; 
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}
	private void generateVertexArray() 
	{
		vertexArray = new Point3d[numberOfPointsInCircle + numberOfPointsInCircle * numberOfPointsInFloor + 2];
		Point3d[] firstLevel = determinatePointsInCircunference(0, 2 * Math.PI, diameter/2, numberOfPointsInCircle, 0);
		vertexArray[0] = new Point3d();
		
		//upload into vertexArray
		for (int i = 0; i < firstLevel.length; i++)
		{
			vertexArray[i + 1] = firstLevel[i];
		}
		
		arraysInArcLateral = new Point3d[numberOfPointsInFloor][numberOfPointsInCircle];
		arcPointsBottom = determinatePointsInLateralCircunference(0, - Math.PI/2, radiusFloor, numberOfPointsInFloor, 0);
		Point3d [] arrayTmp;
		float yPlaneTemp, radiusTemp;
		for (int i = 0; i < arraysInArcLateral.length; i++)
		{
			radiusTemp = diameter/2 - radiusFloor + (float)arcPointsBottom[i].getX();
			
			yPlaneTemp = -(depth - (float)arcPointsBottom[i].getY());
			arrayTmp = new Point3d[numberOfPointsInCircle];
			arrayTmp = determinatePointsInCircunference(0, 2 * Math.PI, radiusTemp, numberOfPointsInCircle, yPlaneTemp);
			arraysInArcLateral[i] = arrayTmp;
		}
		
		//uploading in vertexArray
		for (int i = 0; i < arraysInArcLateral.length; i++)
		{
			for (int j = 0; j < arraysInArcLateral[i].length; j++)
			{
				vertexArray[arraysInArcLateral[i].length * (i + 1) + 1 + j] = arraysInArcLateral[i][j];
			}
		}
		vertexArray[vertexArray.length - 1] = new Point3d(0, -depth - radiusFloor, 0);
		
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
	}
	private void generateIndexArray() 
	{
		indexArray = new int[numberOfPointsInCircle * 3 * 2 + numberOfPointsInCircle * 2 * 3 + numberOfPointsInCircle * 2 * (numberOfPointsInFloor - 1) * 3];
		for (int i = 0; i < numberOfPointsInCircle - 1; i++)
		{
			//generate index in top face
			indexArray[i * 3] = 0;
			indexArray[i * 3 + 1] = i + 2;
			indexArray[i * 3 + 2] = i + 1;
			
			//generate index in side face
			indexArray[numberOfPointsInCircle * 3 + i * 3] = i + 1;
			indexArray[numberOfPointsInCircle * 3 + i * 3 + 1] = i + 2;
			indexArray[numberOfPointsInCircle * 3 + i * 3 + 2] = numberOfPointsInCircle + 1 + i;		
			
			indexArray[numberOfPointsInCircle * 3 * 2 + i * 3] = 2 + i;
			indexArray[numberOfPointsInCircle * 3 * 2 + i * 3 + 1] = numberOfPointsInCircle + 2 + i;
			indexArray[numberOfPointsInCircle * 3 * 2 + i * 3 + 2] = numberOfPointsInCircle + 1 + i;
			
			//generate index for create the "arc floor" surface
			for(int j = 0; j < numberOfPointsInFloor - 1; j++)
			{
				indexArray[numberOfPointsInCircle * 3 * 3 + (3 * i) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle + 1 + i + j * numberOfPointsInCircle;
				indexArray[numberOfPointsInCircle * 3 * 3 + (3 * i + 1) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle + 2 + i + j * numberOfPointsInCircle;
				indexArray[numberOfPointsInCircle * 3 * 3 + (3 * i + 2) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle * 2 + 1 + i + j * numberOfPointsInCircle;
	        	   
				indexArray[numberOfPointsInCircle * 3 * 4 + (3 * i) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle * 2 + 2 + i + j * numberOfPointsInCircle;
				indexArray[numberOfPointsInCircle * 3 * 4 + (3 * i + 1) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle * 2 + 1 + i + j * numberOfPointsInCircle;
				indexArray[numberOfPointsInCircle * 3 * 4 + (3 * i + 2) + (numberOfPointsInCircle * 3 * 2 * j)] = numberOfPointsInCircle + 2 + i + j * numberOfPointsInCircle;
			}		
			indexArray[numberOfPointsInCircle * 3 * 3 + i * 3 + (numberOfPointsInFloor - 1) * numberOfPointsInCircle * 2 * 3] = vertexArray.length - 1;
			indexArray[numberOfPointsInCircle * 3 * 3 + i * 3 + 1 + (numberOfPointsInFloor  - 1) * numberOfPointsInCircle * 2 * 3] = numberOfPointsInCircle * (numberOfPointsInFloor - 1) + numberOfPointsInCircle + 1 + i;
			indexArray[numberOfPointsInCircle * 3 * 3 + i * 3 + 2 + (numberOfPointsInFloor - 1) * numberOfPointsInCircle * 2 * 3] = numberOfPointsInCircle * (numberOfPointsInFloor - 1) + numberOfPointsInCircle + 2 + i;
		}
		
		//special triangles in top face
		indexArray[numberOfPointsInCircle * 3 - 3] = 0;
		indexArray[numberOfPointsInCircle * 3 - 2] = 1;
		indexArray[numberOfPointsInCircle * 3 - 1] = numberOfPointsInCircle;

		//special triangles in side face
		indexArray[numberOfPointsInCircle * 2 * 3 - 3] = 1;
		indexArray[numberOfPointsInCircle * 2 * 3 - 2] = numberOfPointsInCircle * 2;
		indexArray[numberOfPointsInCircle * 2 * 3 - 1] = numberOfPointsInCircle;
		
		indexArray[numberOfPointsInCircle * 3 * 3 - 3] = 1;
		indexArray[numberOfPointsInCircle * 3 * 3 - 2] = numberOfPointsInCircle + 1;
		indexArray[numberOfPointsInCircle * 3 * 3 - 1] = numberOfPointsInCircle * 2;

		//special triangles in "arc floor"
		for (int i = 0; i < numberOfPointsInFloor - 1; i++)
		{
			indexArray[numberOfPointsInCircle * 4 * 3 + (i * numberOfPointsInCircle * 3 * 2) - 3] = numberOfPointsInCircle * (i + 1) + 1;
			indexArray[numberOfPointsInCircle * 4 * 3 + (i * numberOfPointsInCircle * 3 * 2) - 2] = numberOfPointsInCircle * 3 + numberOfPointsInCircle * i;
			indexArray[numberOfPointsInCircle * 4 * 3 + (i * numberOfPointsInCircle * 3 * 2) - 1] = numberOfPointsInCircle * 2 + numberOfPointsInCircle * i;
				           
			indexArray[numberOfPointsInCircle * 3 * 5 + (i * numberOfPointsInCircle * 3 * 2) - 3] = numberOfPointsInCircle * 2 + numberOfPointsInCircle * i + 1;
			indexArray[numberOfPointsInCircle * 3 * 5 + (i * numberOfPointsInCircle * 3 * 2) - 2] = numberOfPointsInCircle * 3 + numberOfPointsInCircle * i;
			indexArray[numberOfPointsInCircle * 3 * 5 + (i * numberOfPointsInCircle * 3 * 2) - 1] = numberOfPointsInCircle * (i + 1) + 1;
		}
		
		//special triangle in bottom face
		indexArray[indexArray.length - 3] = vertexArray.length - 1;
		indexArray[indexArray.length - 2] = vertexArray.length - 2;
		indexArray[indexArray.length - 1] = numberOfPointsInCircle + numberOfPointsInCircle * (numberOfPointsInFloor - 1) + 1;
		
		//verification
		/*for (int i = 0; i < indexArray.length; i++)
		{
			System.out.println(i + " indexArray = " + indexArray[i]);
		}*/
	}
	private void generateColorArray() 
	{
		colorArray = new Color3f [vertexArray.length];
		for (int i = 0; i < colorArray.length; i++)
	    {
			colorArray [i] = new Color3f(0.3f, 0.3f, 0.3f);
	    }
	}
		
	private Point3d[] determinatePointsInLateralCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double zPlane)
	{
		Point3d[] output = new Point3d [numberOfPoints];
	    double x, y;
	    double dAngle = 0;
	    dAngle = deltaAngle / (numberOfPoints - 1);
	    for(int i = 0; i < numberOfPoints; i++)
	    {
	        x = radius * Math.cos(initialAngle + i * dAngle);
	        y = radius * Math.sin(initialAngle  + i * dAngle);
	        output[i] = new Point3d((float)x,(float)y, (float)zPlane);
	        //System.out.println(i + " x z:" + output[i].getX() + " " + output[i].getZ());
	    }
	    return output;
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
