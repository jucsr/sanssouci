package br.UFSC.GRIMA.util.operationsVector;

import javax.vecmath.Point3d;

import org.junit.Test;
	
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class OperationVectorTest 
{
	@Test
	public void calculateShortestDistanceBetweenCircleAndLineTest()
	{
		LimitedLine line = new LimitedLine(new Point3d(100, 10, 0), new Point3d(1000, 30, 0));

		
		LimitedCircle circle = new LimitedCircle();
		circle.setCenter(new Point3d(30, 80, 0));
		circle.setRadius(10);
		
		double distance = OperationsVector.calculateShortestDistanceBetweenCircleAndLine(line, circle, 5);
		System.out.println("calculateShortest... " + distance);
//		System.out.println("M = " + line.getM());
	}
	@Test
	public void pointsInArcTest()
	{
		Point3d center = new Point3d(50, 50, 0);
		Point3d initialPoint = new Point3d(50, 30, 0);
		double deltaAngle = 360;
		LimitedArc la = new LimitedArc(center, initialPoint, deltaAngle, LimitedArc.CCW, 5);
		System.out.println("points in arc = " + la.getPointsInArc());
	}
	@Test
	public void calculateShortestDistanceBeetweenArcAndLineTest()
	{
		Point3d center = new Point3d(50, 50, 0);
		Point3d initialPoint = new Point3d(50, 30, 0);
		double deltaAngle = 180;
		LimitedArc arc = new LimitedArc(center, initialPoint, deltaAngle, LimitedArc.CCW, 33);
		LimitedLine line = new LimitedLine(new Point3d(0, 0, 0), new Point3d(10, 10, 0));
		
		System.out.println("shortest distance = " + OperationsVector.calculateShortestDistanceBeetweenArcAndLine(line, arc));
	}
	@Test
	public void calculateCoordinatesOfPointInshortestDistanceBeetweenLineAndPoint()
	{
		Point3d p1 = new Point3d(30, 20, 0);
		Point3d p2 = new Point3d(10, 10, 0);
		Point3d p0 = new Point3d(0, 0, 0);
//		OperationsVector.distanceToSegment(p1, p2, p0);
		System.out.println("distance = " + OperationsVector.distanceToSegment(p1, p2, p0));
	}
}
