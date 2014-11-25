package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;


public class ConsolePrinter {
	public static void showPoints(ArrayList<Point2D> points)
	{
		for(Point2D pointTmp:points)
		{
			System.out.println("points.add(new Point2D.Double(" + pointTmp.getX() + "," + pointTmp.getY() + "));");
		}
	}
	public static void showPointsToMatLabTest(ArrayList<Point3d> points)
	{
		String saida = "";
		saida = "X = [";
		int aux = 0;
		for(Point3d pointTmp:points)
		{
//			System.out.println(aux);
			saida += pointTmp.x;
			if(aux < points.size())
			{
				saida+=";";
			}
			saida+="\n";
			aux++;
		}
		aux = 0;
		saida += "];\n";
		saida += "Y = [";
		for(Point3d pointTmp:points)
		{
//			System.out.println(aux);
			saida += pointTmp.y;
			if(aux < points.size())
			{
				saida+=";";
			}
			saida+="\n";
			aux++;
		}
		saida += "];\n";
		
		aux = 0;
//		saida += "];\n";
		
		saida += "Z = [";
		for(Point3d pointTmp:points)
		{
//			System.out.println(aux);
			saida += pointTmp.z;
			if(aux < points.size())
			{
				saida+=";";
			}
			saida+="\n";
			aux++;
		}
		saida += "];\n";
		
		saida+= "tri = delaunay(X, Y); \ntrisurf(tri, X, Y, Z); \ncolormap hsv \ncolorbar";
		
		System.out.println(saida);
	}
	public static void showElements(LimitedElement element, int i)
	{
		if(element.isLimitedArc())
		{
			LimitedArc arc = (LimitedArc)element;
			System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y  + ", " + arc.getCenter().z + ")" + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ", " + arc.getInitialPoint().z + ")" + "," + arc.getDeltaAngle() + ");");		
		}
		else if (element.isLimitedLine())
		{
			System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + element.getInitialPoint().x + "," + element.getInitialPoint().y + ", " + element.getInitialPoint().z + ");" + ",new Point3d(" + element.getFinalPoint().x + "," + element.getFinalPoint().y + ", " + element.getFinalPoint().z + ");");
		}			
	}
	public static void showElements(ArrayList<LimitedElement> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{	
			showElements(e, i);
			i++;			
		}
	}
	public static void showLines(ArrayList<LimitedLine> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			LimitedLine line = (LimitedLine)e;
			System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + line.getInitialPoint().x + "," + line.getInitialPoint().y  + ", " + line.getInitialPoint().z + ")"  + ",new Point3d(" + line.getFinalPoint().x + "," + line.getFinalPoint().y + ", " + line.getFinalPoint().z + ")" );	
			i++;			
		}

	}
	public static void showArcs(ArrayList<LimitedArc> elements)
	{
		int i = 0;
		for (LimitedElement e:elements)
		{
			LimitedArc arc = (LimitedArc)e;
			System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + arc.getCenter().x + "," + arc.getCenter().y + ", " + arc.getCenter().z + ")"  + ",new Point3d(" + arc.getInitialPoint().x + "," + arc.getInitialPoint().y + ", " + arc.getInitialPoint().z + ")"  + "," + arc.getDeltaAngle() + ");");							
			i++;			
		}

	}

}
