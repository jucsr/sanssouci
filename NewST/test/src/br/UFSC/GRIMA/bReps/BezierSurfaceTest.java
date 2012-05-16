package br.UFSC.GRIMA.bReps;

import javax.vecmath.Point3d;

import org.junit.Test;

public class BezierSurfaceTest 
{
	double[][][] control_points = {
			{ { -3, -3, 0 }, { -1, -3, 0 }, { 1, -3, 0 }, { 3, -3, 0 } },
			{ { -3, -1, 0 }, { -1, -1, 0 }, { 1, -1, 0 }, { 3, -1, 0 } },
			{ { -3, 1, 0 }, { -1, 1, 0 }, { 1, 1, 0 }, { 3, 1, 0 } },
			{ { -3, 3, 0 }, { -1, 3, 0 }, { 1, 3, 0 }, { 3, 3, 0 } } 
			};
	Point3d [][] controlVertex = new Point3d[4][4];
	
	Point3d p00 = new Point3d(-3, -3, 0);
	Point3d p01 = new Point3d(-1, -3, 0);
	Point3d p02 = new Point3d(1, -3, 0);
	Point3d p03 = new Point3d(3, -3, 0);
	Point3d p10 = new Point3d(-3, -1, 0);
	Point3d p11 = new Point3d(-1, -1, 0);
	Point3d p12 = new Point3d(1, -1, 0);
	Point3d p13 = new Point3d(3, -1, 0);
	Point3d p20 = new Point3d(-3, 1, 0);
	Point3d p21 = new Point3d(-1, 1, 0);
	Point3d p22 = new Point3d(1, 1, 0);
	Point3d p23 = new Point3d(3, 1, 0);
	Point3d p30 = new Point3d(-3, 3, 0);
	Point3d p31 = new Point3d(-1, 3, 0);
	Point3d p32 = new Point3d(1, 3, 0);
	Point3d p33 = new Point3d(3, 3, 9);
	
	@Test
	public void bezierTest()
	{
		controlVertex[0][0] = p00;
		controlVertex[0][1] = p01;
		controlVertex[0][2] = p02;
		controlVertex[0][3] = p03;
		controlVertex[1][0] = p10;
		controlVertex[1][1] = p11;
		controlVertex[1][2] = p12;
		controlVertex[1][3] = p13;
		controlVertex[2][0] = p20;
		controlVertex[2][1] = p21;
		controlVertex[2][2] = p22;
		controlVertex[2][3] = p23;
		controlVertex[3][0] = p30;
		controlVertex[3][1] = p31;
		controlVertex[3][2] = p32;
		controlVertex[3][3] = p33;
		
		BezierSurface b = new BezierSurface(controlVertex, 4, 4);
//		System.out.println(b.getMeshArray()[0][0]);
		for(int i = 0; i < b.getMeshArray().length; i++)
		{
			for(int j = 0; j < b.getMeshArray()[i].length; j++)
			{
				System.out.println("[" + i + "]" + "[" + j + "]" + b.getMeshArray()[i][j]);
			}
		}
//		for(int j = 0; j < b.getMeshArray()[0].length; j++)
//		{
//			System.out.println(b.getMeshArray()[0][j]);
//			
//		}
	}
}
