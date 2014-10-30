package br.UFSC.GRIMA.capp.visual;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CreatePlungeStrategy1;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class PlungeFrameTest1
{
	private ArrayList<Path> path = new ArrayList<Path>();
	private ArrayList<Path> path1 = new ArrayList<Path>();
	private ArrayList<Path>	saida= new ArrayList<Path>();
	private double retractPlane = 100;
	
	@Before
	public void init()
	{
		LinearPath p0 = new LinearPath(new Point3d(10,10,-10), new Point3d(110,10,-10));
		LinearPath p1 = new LinearPath(new Point3d(110,10,-10), new Point3d(110,60,-10));
		LinearPath p2 = new LinearPath(new Point3d(110,60,-10), new Point3d(10,60,-10));
		LinearPath p3 = new LinearPath(new Point3d(10,60,-10), new Point3d(10,10,-10));
		path.add(p0);
		path.add(p1);
		path.add(p2);
		path.add(p3);
		
		LinearPath q0 = new LinearPath(new Point3d(10,0,-10), new Point3d(90,0,-10));
		CircularPath q1 = new CircularPath(new Point3d(90,10,-10), new Point3d(90,0,-10), new Point3d(100,10,-10), Math.PI/2);//centro, inicial, final, angulo
		LinearPath q2 = new LinearPath(new Point3d(100,10,-10), new Point3d(100,60,-10));
		CircularPath q3 = new CircularPath(new Point3d(90,60,-10), new Point3d(100,60,-10), new Point3d(90,70,-10), Math.PI/2);//centro, inicial, final, angulo
		LinearPath q4 = new LinearPath(new Point3d(90,70,-10), new Point3d(10,70,-10));
		CircularPath q5 = new CircularPath(new Point3d(10,60,-10), new Point3d(10,70,-10), new Point3d(0,60,-10), Math.PI/2);//centro, inicial, final, angulo
		LinearPath q6= new LinearPath(new Point3d(0,60,-10), new Point3d(0,10,-10));
		CircularPath q7 = new CircularPath(new Point3d(10,10,-10), new Point3d(0,10,-10), new Point3d(10,0,-10), Math.PI/2);//centro, inicial, final, angulo
		
		path1.add(q0);
		path1.add(q1);
		path1.add(q2);
		path1.add(q3);
		path1.add(q4);
		path1.add(q5);
		path1.add(q6);
		path1.add(q7);
		
	}
	
	@Test
	public void createPlungeStrategy1Test(){
		CreatePlungeStrategy1 janelaTeste = new CreatePlungeStrategy1(path1);
		while (1>0){
			}
	}
}
