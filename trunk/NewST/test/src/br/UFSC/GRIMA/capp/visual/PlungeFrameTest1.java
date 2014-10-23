package br.UFSC.GRIMA.capp.visual;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CreatePlungeStrategy1;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class PlungeFrameTest1
{
	private ArrayList<Path> path = new ArrayList<Path>();
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
		
	}
	
	@Test
	public void createPlungeStrategy1Test(){
		CreatePlungeStrategy1 janelaTeste = new CreatePlungeStrategy1(path);
		while (1>0){
			}
	}
}
