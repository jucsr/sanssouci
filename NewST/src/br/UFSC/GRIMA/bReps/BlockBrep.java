package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class BlockBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	
	private float lenght, width, high;
	private String name;
	private int nX, nY, nZ;
	/**
	 * 
	 * @param name
	 * @param lenght
	 * @param width
	 * @param high
	 * @param nX --> numero de pontos (2 ou maior)
	 * @param nY
	 * @param nZ
	 */
	public BlockBrep(String name, float lenght, float width, float high, int nX, int nY, int nZ)
	{
		this.name = name;
		this.lenght = lenght;
		this.width = width;
		this.high = high;
		this.nX = nX;
		this.nY = nY;
		this.nZ = nZ;
		this.generateVertex();
		for(int i = 0; i < vertexArray.length; i++)
		{
			System.err.println("i = " + i + "\t" + vertexArray[i]);
		}
		
		this.generateIndex();
		
		for(int i = 0; i < indexArray.length; i++)
		{
//			System.out.println(i + " index = " + "\t" + indexArray[i]);			
		}
		this.generateColorArray();
	}
	
	
	private void generateVertex()
	{
		float deltaX = lenght / (nX - 1);
		float deltaY = width / (nY - 1);
		float deltaZ = high / (nZ - 1);
		float x, y, z;
		// generating vertex in top face (XY)
//		vertexArray = new Point3d[nX * nY * 2 + (nX * (nZ - 2)) * 2 + ((nY - 2) * (nZ - 2)) * 2];
		vertexArray = new Point3d[nX * nY * 2];

		int a = 0;
		for(int i = 0; i < nX; i++)
		{
			for(int j = 0; j < nY; j++)
			{
				x = deltaX * i;
				y = deltaY * j;
				z = 0;
				vertexArray[a] = new Point3d(x, y, z);
				a++;
			}
		}

		//generating vertex in bottom face (YX)
		for(int i = 0; i < nX * nY; i++)
		{
			vertexArray[a] = new Point3d(vertexArray[i].x, vertexArray[i].y, -high);
			a++;
		}
//		System.out.println("I = " + a);

//		//generating vertex in front face (XZ)
//		for(int i = 0; i < nX; i++)
//		{
//			for(int j = 0; j < nZ; j++)
//			{
//				x = deltaX * i;
//				z = deltaZ * j;
//				y = 0;
//				vertexArray[a] = new Point3d(x, y, z);
//				a++;
//			}
//		}
//		System.out.println("I = " + a);
//
//		//generating vertex in rear face (ZX)
//		for(int i = 0; i < nX * nY; i++)
//		{
//			vertexArray[a] = new Point3d(vertexArray[nX * nY * 2 + i].x, vertexArray[nX * nY * 2 + i].y, width);
//			a++;
//		}
//		System.out.println("I = " + a);
//
//		//generating vertex in left face (ZY)
//		for(int i = 0; i < nY; i++)
//		{
//			for(int j = 0; j < nZ; j++)
//			{
//				x = 0;
//				y = deltaY * i;
//				z = deltaZ * j;
//				vertexArray[a] = new Point3d(x, y, z);
//				a++;
//			}
//		}
//		System.out.println("I = " + a);
//
//		
//		//generating vertex in right face (YZ)
//		for(int i = 0; i < nY * nZ; i++)
//		{
//			vertexArray[a] = new Point3d(vertexArray[(nX * nY * + nY * nZ) * 2 + i].x, vertexArray[(nX * nY * + nY * nZ) * 2 + i].y, lenght);
//		}
//		System.out.println("I = " + a);

	}
	private void generateIndex()
	{
		indexArray = new int[(nX * nY * 2) * 3 + (nX + nY) * 2 * 3];
		int a = 0;
		//TOP
		for(int i = 0; i < nX - 1; i++)
		{
			for(int j = 0; j < nY - 1; j++)
			{
				indexArray[a] = (i + 1) + nY * j;
				a++;
				indexArray[a] = i + nY * j;
				a++;
				indexArray[a] = nY * (j + 1) + i;
				a++;
			}
		}
		
//		for (int i = 0; i < nX - 1; i++) 
//		{
//			indexArray[a] = (i + 1) + nY * i;
//			a++;
//			indexArray[a] = i + nY * i;
//			a++;
//			indexArray[a] = nY * (i + 1) + i;
//			a++;
//		}
//		for(int i = 0; i < nX - 1; i++)
//		{
//			indexArray[a] = (i + 1) + nY * i;
//			a++;
//			indexArray[a] = nY * (i + 1) + i;
//			a++;
//			indexArray[a] = nY * (i + 1) + i + 1;
//			a++;
//		}
		//BOTTOM
		
		//FRONT
		
		//REAR
		
		//LEFT
		
		//RIGHT
		
	}

	private void generateColorArray() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
	    {
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
	    }	
	}
	
}