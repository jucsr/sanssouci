package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class GenerateTrochoidalMovementTest
{
	private ArrayList<LimitedElement> elements;
	
	
	@Before
	
	public void Project(){
		
		Point3d point1 = new Point3d(0, 0, 0); 
		Point3d point2 = new Point3d(100, 0, 0);
		LimitedLine linha1 = new LimitedLine(point1, point2);
		elements = new ArrayList<LimitedElement>();
		
		elements.add(linha1);
		
	}
	
	@Test
	
	public void generateTrochoidalMovementTest(){
		GenerateTrochoidalMovement path = new GenerateTrochoidalMovement(elements, 10, 2.5);
	}
}
