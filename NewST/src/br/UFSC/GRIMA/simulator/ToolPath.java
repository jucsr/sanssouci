package br.UFSC.GRIMA.simulator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class ToolPath {

	ArrayList<Point> arrayListPoints;
	Point actualPoint;
	Iterator iterator;

	public ToolPath(ArrayList<Point> arrayListPoints) {
		this.arrayListPoints = arrayListPoints;
		iterator = arrayListPoints.iterator();
		nextPoint();
	}

	public void addPoint(Point point, int index){
		arrayListPoints.add(index,point);
	}
	
	public int getNextY() {
		return (int) actualPoint.getY();
	}

	public void nextPoint() {
		if (iterator.hasNext()) {
			actualPoint = (Point) iterator.next();
		}
	}

	public boolean isLast() {
		return !iterator.hasNext();
	}

	public int getNextX() {
		return (int) actualPoint.getX();
	}

}
