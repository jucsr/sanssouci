package br.UFSC.GRIMA.util;

import java.awt.Point;
import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.features.Furo;

//import java.awt.Point;
//import java.util.ArrayList;
//
//import jsdai.lang.A_double;
//import jsdai.lang.SdaiException;
//import br.UFSC.GRIMA.entidades.features.Furo;

public class RefactoringUtil {

	public RefactoringUtil(ArrayList<Point> beforePoints,
			ArrayList<Furo> beforeFuros) {
		
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Furo> getFuros() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//As Features nao possuem mais ferramenta!
	
//	ArrayList<Point> realPoints;
//	ArrayList<Furo> realFuros;
//	
//	
//	public RefactoringUtil(ArrayList<Point> beforePoints,
//			ArrayList<Furo> beforeFuros) {
//		int indexResolver = 0;
//
//		
//		
//		for (int i = 0; i < beforePoints.size(); i++) {
//
//			if ((i + 1) < beforeFuros.size()) {
//
//				if (beforeFuros.get(i).getFerramenta().equals(
//						beforeFuros.get(i + 1).getFerramenta())) {
//
//				} else {
//					beforePoints.add(i+1+indexResolver, new Point(0,0));
//					indexResolver++;
//				}
//
//			}
//
//		}
//		beforePoints.add(0,new Point(0,0));
//		beforePoints.add(new Point(0,0));
//		realPoints = beforePoints;
//		
//		beforeFuros.add(0,null);
//		beforeFuros.add(null);
//		realFuros = beforeFuros;
//	}
//	
//	public RefactoringUtil(ArrayList<A_double> beforeAPoints,
//			ArrayList<Furo> beforeFuros,boolean b) {
//		int indexResolver = 0;
//		ArrayList<Point> beforePoints = null;
//		
//		try {
//			beforePoints = Util.ajustDoubleArray(beforeAPoints);
//		} catch (SdaiException e) {
//			e.printStackTrace();
//		}
//		
//		
//		for (int i = 0; i < beforePoints.size(); i++) {
//
//			if ((i + 1) < beforeFuros.size()) {
//
//				if (beforeFuros.get(i).getFerramenta().equals(
//						beforeFuros.get(i + 1).getFerramenta())) {
//
//				} else {
//					beforePoints.add(i+1+indexResolver, new Point(0,0));
//					indexResolver++;
//				}
//
//			}
//
//		}
//		beforePoints.add(0,new Point(0,0));
//		beforePoints.add(new Point(0,0));
//		realPoints = beforePoints;
//		
//		beforeFuros.add(0,null);
//		beforeFuros.add(null);
//		realFuros = beforeFuros;
//	}
//
//	public ArrayList<Point> getPoints() {
//		return realPoints;
//	}
//
//	public ArrayList<Furo> getFuros() {
//		return realFuros;
//	}
//


}
