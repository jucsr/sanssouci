package br.UFSC.GRIMA.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.vecmath.Point3d;

public class Transformer {
	ArrayList<Point3d> arrayList = null;

	public static ArrayList<Point3d> transformVectorToArray(Vector<Ponto> vector) {

		Iterator<Ponto> iterator = vector.iterator();
		int size = vector.size() - 1;
		ArrayList<Point3d> arrayList = new ArrayList<Point3d>(size+1);
		for (int i = 0; i <= size; i++) {
			Ponto p = (Ponto) vector.get(i);
			arrayList.add(new Point3d(p.getX(), p.getY(), p.getZ()));
		}

		return arrayList;
	}
}
