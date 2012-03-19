package br.UFSC.GRIMA.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.exceptions.ProjetoInvalidoException;

public class VValidatorTest {
	VValidator validator;
	Rectangle3D rectangle3D;
	ArrayList<Feature> arrayList;
	
	double raioApoio = 1;

	@Before
	public void setUp() {

		rectangle3D = new Rectangle3D(50, 50, 50);

	}

	@Test
	public void validarSimples() {
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 25, 7.5, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));

				
		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		try {
			list = validator.getPoints(3,0,0);
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			System.out.println(list.size());
			System.out.println("Pontos de Apoio da base");
			System.out.println("Ponto 0:(" +listaApoiosBase.get(0).getX() + ", " + listaApoiosBase.get(0).getY() + listaApoiosBase.get(0).getZ() + ")");
			System.out.println("Ponto 1:(" +listaApoiosBase.get(1).getX() + ", " + listaApoiosBase.get(1).getY() + listaApoiosBase.get(1).getZ() +")");
			System.out.println("Ponto 2:(" +listaApoiosBase.get(2).getX() + ", " + listaApoiosBase.get(2).getY() + listaApoiosBase.get(2).getZ() +")");
			
			Assert.assertEquals(25.0, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getY(), 0);
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();
		}

	}

	@Test
	public void validarSimplesLateral() {
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 25, 7.5, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));

				
		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		try {
			list = validator.getPoints(3,3,0);
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(25.0, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosVerticais.get(0).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(0).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(0).getZ(), 0);
			
			Assert.assertEquals(25, listaApoiosVerticais.get(1).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(1).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(1).getZ(), 0);
			
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(2).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(2).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(2).getZ(), 0);
			
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(3).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(3).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(3).getZ(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(4).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(4).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(4).getZ(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(5).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(5).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(5).getZ(), 0);
			

		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();
		}

	}

	@Test
	public void validarSimplesHorizontal() {
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 25, 7.5, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));

				
		validator = new VValidator(rectangle3D, arrayList,  raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(3,0,3);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(25.0, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(0).getX(), 0);
			Assert.assertEquals(25, listaApoiosHorizontais.get(0).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(0).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(1).getX(), 0);
			Assert.assertEquals(25, listaApoiosHorizontais.get(1).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(1).getZ(), 0);
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(2).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(2).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(2).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(3).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(3).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(3).getZ(), 0);
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(4).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(4).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(4).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(5).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(5).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(5).getZ(), 0);
			

		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();
		}

	}
	
	@Test
	public void validarSimplesHorizontalVertical() {
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 25, 7.5, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));

				
		validator = new VValidator(rectangle3D, arrayList,raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(3,3,3);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(25.0, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosVerticais.get(0).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(0).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(0).getZ(), 0);
			
			Assert.assertEquals(25, listaApoiosVerticais.get(1).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(1).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(1).getZ(), 0);
			
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(2).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(2).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(2).getZ(), 0);
			
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(3).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(3).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(3).getZ(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(4).getX(), 0);
			Assert.assertEquals(0, listaApoiosVerticais.get(4).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(4).getZ(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosVerticais.get(5).getX(), 0);
			Assert.assertEquals(50, listaApoiosVerticais.get(5).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosVerticais.get(5).getZ(), 0);
			
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(0).getX(), 0);
			Assert.assertEquals(25, listaApoiosHorizontais.get(0).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(0).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(1).getX(), 0);
			Assert.assertEquals(25, listaApoiosHorizontais.get(1).getY(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(1).getZ(), 0);
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(2).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(2).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(2).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(3).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosHorizontais.get(3).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(3).getZ(), 0);
			
			Assert.assertEquals(0, listaApoiosHorizontais.get(4).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(4).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(4).getZ(), 0);
			
			Assert.assertEquals(50, listaApoiosHorizontais.get(5).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(5).getY(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosHorizontais.get(5).getZ(), 0);
			

		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();
		}

	}

	
	@Test
	public void validarMedio() {
		arrayList = new ArrayList<Feature>();

		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 9.5, 41, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));
		arrayList.add(new Furo("Furo4", 43, 15, 0, 2));
		arrayList.add(new Furo("Furo5", 43, 43, 0, 2));
		arrayList.add(new Furo("Furo6", 41, 10, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;

		try {
			list = validator.getPoints(3,0,0);
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			
			Assert.assertEquals(25.0, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(38.7800755757214, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(11.219924424278604, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(11.219924424278604, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(38.7800755757214, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(11.219924424278604, listaApoiosBase.get(2).getY(), 0);
			
		} catch (ProjetoInvalidoException e) {

			System.out.println(e.getMessage());
			fail();
		}

	}
	
	
	@Test
	public void validarFuroNegativo() {
		arrayList = new ArrayList<Feature>();

		arrayList.add(new Furo("Furo1", -25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 9.5, 41, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));
		arrayList.add(new Furo("Furo4", 43, 7.5, 0, 2));
		arrayList.add(new Furo("Furo5", 43, 43, 0, 2));
		arrayList.add(new Furo("Furo6", 41, 10, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;

		try {
			list = validator.getPoints(3,0,0);
			fail();
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
		} catch (ProjetoInvalidoException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void validarBlocoNegativo() {
		
		Rectangle3D rectNegativo = new Rectangle3D(-50, 50, 50);
		
		arrayList = new ArrayList<Feature>();

		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 9.5, 41, 0, 2));
		arrayList.add(new Furo("Furo3", 8, 8, 0, 2));
		arrayList.add(new Furo("Furo4", 43, 7.5, 0, 2));
		arrayList.add(new Furo("Furo5", 43, 43, 0, 2));
		arrayList.add(new Furo("Furo6", 41, 10, 0, 2));

		validator = new VValidator(rectNegativo, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;

		try {
			list = validator.getPoints(3,0,0);
			fail();
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
		} catch (ProjetoInvalidoException e) {
			System.out.println(e.getMessage());
		}


	}

	/*@Test
	public void validarColisaoFuros() {
		
		Rectangle3D rectNegativo = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();

		arrayList.add(new Furo("Furo1", 8, 8, 0, 2));
		arrayList.add(new Furo("Furo2", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo3", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo4", 43, 7.5, 0, 2));
		arrayList.add(new Furo("Furo5", 43, 43, 0, 2));
		arrayList.add(new Furo("Furo6", 41, 10, 0, 2));

		validator = new VValidator(rectNegativo, arrayList, divisor, raioApoio);
		ArrayList<Point3d> list;

		try {
			list = validator.getPoints(3);
			fail();
		} catch (ProjetoInvalidoException e) {
			System.out.println(e.getMessage());
		}


	}*/
	
	@Test
	public void validarBloco4Apoios() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 1, 2, 0, 2));
		arrayList.add(new Furo("Furo2", 3, 4, 0, 2));
		arrayList.add(new Furo("Furo3", 15, 15, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(4,0,0);
			
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(3).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(3).getY(), 0);
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}


	}
	
	@Test
	public void validarBloco5Apoios() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 1, 2, 0, 2));
		arrayList.add(new Furo("Furo2", 3, 4, 0, 2));
		arrayList.add(new Furo("Furo3", 15, 15, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(5,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(3).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(3).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosBase.get(4).getX(), 0);
			Assert.assertEquals(25, listaApoiosBase.get(4).getY(), 0);
			
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}


	}
	
	@Test
	public void validarBloco5ApoiosComError() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 25, 42, 0, 2));
		arrayList.add(new Furo("Furo2", 10, 7.5, 0, 2));
		arrayList.add(new Furo("Furo3", 2, 8, 0, 2));
		arrayList.add(new Furo("Furo4", 25, 25, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(5,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			
			
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();

		}


	}
	@Test
	public void validarBloco6Apoios() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 1, 2, 0, 2));
		arrayList.add(new Furo("Furo2", 3, 4, 0, 2));
		arrayList.add(new Furo("Furo3", 15, 15, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(6,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(3).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(3).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosBase.get(4).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(4).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosBase.get(5).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(5).getY(), 0);
			
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}


	}
	
	@Test
	public void validarBloco7Apoios() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 1, 2, 0, 2));
		arrayList.add(new Furo("Furo2", 3, 4, 0, 2));
		arrayList.add(new Furo("Furo3", 15, 15, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(7,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(3).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(3).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosBase.get(4).getX(), 0);
			Assert.assertEquals(25, listaApoiosBase.get(4).getY(), 0);
						
			Assert.assertEquals(25, listaApoiosBase.get(5).getX(), 0);
			Assert.assertEquals(42.67766952966369, listaApoiosBase.get(5).getY(), 0);
			
			Assert.assertEquals(25, listaApoiosBase.get(6).getX(), 0);
			Assert.assertEquals(7.322330470336315, listaApoiosBase.get(6).getY(), 0);
			
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}


	}
	
	@Test
	public void validarBloco4ApoiosSimples() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Furo("Furo1", 7.3, 42.7, 0, 3));
		arrayList.add(new Furo("Furo2", 3, 4, 0, 2));
		arrayList.add(new Furo("Furo3", 15, 15, 0, 2));

		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(4,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(2).getY(), 0);
			
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(3).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(3).getY(), 0);
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}
		
	

	}
	
	@Test
	public void validarCavidade() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Cavidade(7,7,50,0,2,2,50));
		arrayList.add(new Cavidade(25,7,50,0,2,2,50));
		
		
		
		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(3,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(25, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(2).getY(), 0);
			
		
		} catch (ProjetoInvalidoException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			fail();

		}
		
	

	}
	
	@Test
	public void validarCavidadecomRaio() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Cavidade(7.5,7.5,50,10,3,3, 50));
		arrayList.add(new Cavidade(25,7,50,0,2,2, 50));
		
		
		
		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(3,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			Assert.assertEquals(25, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(41.510220632827824, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(8.489779367172176, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(8.489779367172176, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(41.510220632827824, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(8.489779367172176, listaApoiosBase.get(2).getY(), 0);
			
		
		} catch (ProjetoInvalidoException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			fail();

		}
		
	

	}
	
	@Test
	public void validarCavidadecomRaioMedio() {
		
		Rectangle3D rectangle3D = new Rectangle3D(50, 50, 50);
		
		arrayList = new ArrayList<Feature>();
		arrayList.add(new Cavidade(6,6,50,3,10,10,50));
		arrayList.add(new Cavidade(42,42,50,3,5,5,50));
		
		
		
		validator = new VValidator(rectangle3D, arrayList, raioApoio);
		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(4,0,0);
			listaApoiosBase = list.get(0);
			listaApoiosVerticais = list.get(1);
			listaApoiosHorizontais = list.get(2);
			
			System.out.println(list.size());
			
			Assert.assertEquals(25, listaApoiosBase.get(0).getX(), 0);
			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(0).getY(), 0);

			Assert.assertEquals(9.512582112588076, listaApoiosBase.get(1).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(1).getY(), 0);

			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getX(), 0);
			Assert.assertEquals(40.48741788741192, listaApoiosBase.get(2).getY(), 0);
			
						
		
		} catch (ProjetoInvalidoException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			fail();

		}
		
	

	}
	@Test
	public void validarNulo() {

		validator = new VValidator(null, arrayList, raioApoio);

		ArrayList<ArrayList<Point3d>> list;
		ArrayList<Point3d> listaApoiosBase;
		ArrayList<Point3d> listaApoiosVerticais;
		ArrayList<Point3d> listaApoiosHorizontais;
		
		try {
			list = validator.getPoints(3,0,0);
					
			Assert.assertNull(list);
		} catch (ProjetoInvalidoException e) {
			
			System.out.println(e.getMessage());
			fail();

		}

	}

}
