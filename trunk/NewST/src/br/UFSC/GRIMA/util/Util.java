package br.UFSC.GRIMA.util;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jsdai.lang.A_double;
import jsdai.lang.SdaiException;

public class Util {
	static public String getContents(File aFile) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	public static ArrayList<Point> ajustDoubleArray(
			ArrayList<A_double> arrayListPoints) throws SdaiException {
		ArrayList<Point> arrayList = new ArrayList<Point>();

		Iterator iterator = arrayListPoints.iterator();
		A_double arrayDouble;
		while (iterator.hasNext()) {
			arrayDouble = (A_double) iterator.next();
			arrayList.add(new Point((int) arrayDouble.getByIndex(1),
					(int) arrayDouble.getByIndex(2)));
		}
		return arrayList;
	}

	public static ArrayList<Point> tarnsformeDoubleArray(
			ArrayList<A_double> arrayListPoints) throws SdaiException {

		ArrayList<Point> arrayList = new ArrayList<Point>();

		Iterator iterator = arrayListPoints.iterator();
		A_double arrayDouble;
		while (iterator.hasNext()) {
			arrayDouble = (A_double) iterator.next();
			arrayList.add(new Point((int) arrayDouble.getByIndex(1),
					(int) arrayDouble.getByIndex(2)));
		}

		return arrayList;
	}

}
