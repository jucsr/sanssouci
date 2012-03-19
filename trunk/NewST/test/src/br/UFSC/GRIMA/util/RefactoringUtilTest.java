package br.UFSC.GRIMA.util;

import java.awt.Point;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class RefactoringUtilTest {
	Furo furo;
	Ferramenta ferramenta;
	RefactoringUtil util;
	ArrayList<Furo> expectedFuros;
	ArrayList<Point> expectedPoints;

	@Before
	public void startObjects() {
		expectedPoints = new ArrayList<Point>();

		expectedPoints.add(new Point(0, 0));
		expectedPoints.add(new Point(20, 30));
		expectedPoints.add(new Point(40, 70));
		expectedPoints.add(new Point(0, 0));
		expectedPoints.add(new Point(90, 90));
		expectedPoints.add(new Point(120, 150));
		expectedPoints.add(new Point(0, 0));
		expectedPoints.add(new Point(10, 10));
		expectedPoints.add(new Point(0, 0));

		expectedFuros = new ArrayList<Furo>();

		furo = null;
		expectedFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(15, 100);
		furo.setPosicao(20, 30, 0);
		expectedFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(15, 100);
		furo.setPosicao(40, 70, 0);
		expectedFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(20, 100);
		furo.setPosicao(90, 90, 0);
		expectedFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(20, 100);
		furo.setPosicao(120, 150, 0);
		expectedFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(5, 100);
		furo.setPosicao(10, 10, 0);
		expectedFuros.add(furo);
		furo = null;
		expectedFuros.add(furo);

		ArrayList<Point> beforePoints = new ArrayList<Point>();
		beforePoints.add(new Point(20, 30));
		beforePoints.add(new Point(40, 70));
		beforePoints.add(new Point(90, 90));
		beforePoints.add(new Point(120, 150));
		beforePoints.add(new Point(10, 10));

		ArrayList<Furo> beforeFuros = new ArrayList<Furo>();

		furo = new Furo();
		ferramenta = new Ferramenta(15, 100);
		furo.setPosicao(20, 30, 0);
		beforeFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(15, 100);
		furo.setPosicao(40, 70, 0);
		beforeFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(20, 100);
		furo.setPosicao(90, 90, 0);
		beforeFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(20, 100);
		furo.setPosicao(120, 150, 0);
		beforeFuros.add(furo);
		furo = new Furo();
		ferramenta = new Ferramenta(5, 100);
		furo.setPosicao(10, 10, 0);
		beforeFuros.add(furo);

		util = new RefactoringUtil(beforePoints, beforeFuros);

		ArrayList<Furo> actualFuros = util.getFuros();

	}

//	@Test
//	public void getPointsTest() {
//		ArrayList<Point> actualPoints = util.getPoints();
//
//		for (int i = 0; i < actualPoints.size(); i++) {
//			Assert.assertEquals("When i = "+i,expectedPoints.get(i), actualPoints.get(i));
//
//		}
//	}
//
//	@Test
//	public void getFurosTest() {
//		ArrayList<Furo> actualFuros = util.getFuros();
//
//		for (int i = 0; i < actualFuros.size(); i++) {
//
//			if (expectedFuros.get(i) != null) {
//				Assert.assertEquals(expectedFuros.get(i).getFerramenta().getDiametroFerramenta(), actualFuros.get(i).getFerramenta().getDiametroFerramenta());
//			}else{
//				Assert.assertEquals(expectedFuros.get(i), actualFuros.get(i));
//			}
//		}
//	}
}
