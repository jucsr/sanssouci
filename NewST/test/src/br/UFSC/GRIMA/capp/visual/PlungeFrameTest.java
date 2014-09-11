package br.UFSC.GRIMA.capp.visual;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CreatePlungeStrategy;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class PlungeFrameTest 
{			//array de "Path"s
	private ArrayList<Path> path = new ArrayList<Path>();
	private double retractPlane = 10;
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
	public void test()
	{
		CreatePlungeStrategy frame = new CreatePlungeStrategy(path, retractPlane);
		ArrayList<Path> mergulho = frame.calcularMergulho();
		for (int i=0;i<mergulho.size();i++)
			if (mergulho.get(i).getClass() == LinearPath.class) 
				System.out.println("");
			else if(mergulho.get(i).getClass() == CircularPath.class)
				System.out.println("");
		for(;;);
	}
}
