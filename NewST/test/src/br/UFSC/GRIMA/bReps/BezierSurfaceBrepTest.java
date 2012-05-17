package br.UFSC.GRIMA.bReps;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.OperationBezierSurface;

public class BezierSurfaceBrepTest 
{
	private	 BezierSurfaceBrep brep;
	private Point3d [][] controlVertex = new Point3d[4][4];
	
	@Before
	public void init()
	{
		
		Point3d p00 = new Point3d(-30, -30, -30);
		Point3d p01 = new Point3d(-10, -30, -50);
		Point3d p02 = new Point3d(10, -30, -50);
		Point3d p03 = new Point3d(30, -30, -50);
		Point3d p10 = new Point3d(-30, -10, -50);
		Point3d p11 = new Point3d(-10, -10, -10);
		Point3d p12 = new Point3d(10, -10, -10);
		Point3d p13 = new Point3d(30, -10, -50);
		Point3d p20 = new Point3d(-30, 10, -50);
		Point3d p21 = new Point3d(-10, 10, -10);
		Point3d p22 = new Point3d(10, 10, -10);
		Point3d p23 = new Point3d(30, 10, -0);
		Point3d p30 = new Point3d(-30, 30, -50);
		Point3d p31 = new Point3d(-10, 30, -20);
		Point3d p32 = new Point3d(10, 30, -50);
		Point3d p33 = new Point3d(30, 30, -20);
		
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

	}
	@Test
	public void generateVertexTest()
	{
		brep = new BezierSurfaceBrep("BEZIER_SURFACE", controlVertex, 3, 2);
		//		System.out.println(brep.vertexArray);
		for(int i = 0; i < brep.vertexArray.length; i++)
		{
			System.out.println(i + " " + brep.vertexArray[i]);
		}
	}
	@Test
	public void generateIndexTest()
	{
		brep = new BezierSurfaceBrep("BEZIER_SURFACE", controlVertex, 4, 3);

		for(int i = 0; i < brep.indexArray.length; i++)
		{
			System.out.println(i + "\t" + brep.indexArray[i]);
		}
	}
	@Test
	public void generateBezierSurfaceSolidTest()
	{
		JFrame frame = new JFrame("BEZIER SURFACE");
		frame.setBounds(100, 100, 800, 500);
		JPanel painel = new JPanel();
		painel.repaint();
		painel.setLayout(new BorderLayout());
		frame.getContentPane().add(painel);
		J3D j3d = new J3D(painel);
		OperationBezierSurface operation = new OperationBezierSurface("BEZIER_SURFACE", controlVertex, 11, 11);
		
		j3d.addSolid(operation);
		
		frame.setVisible(true);
		while(true);
	}
}
