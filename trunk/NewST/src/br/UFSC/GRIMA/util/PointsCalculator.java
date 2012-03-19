package br.UFSC.GRIMA.util;

import javax.vecmath.Point3d;



import br.UFSC.GRIMA.entidades.Rectangle3D;

import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;

public class PointsCalculator {
	int PONTO1 = 1;
	int PONTO2 = 2;
	int PONTO3 = 3;
	int PONTO4 = 4;
	int PONTO5 = 5;
	int PONTO6 = 6;
	int PONTO7 = 7;
	int notNull;
	int toDo = 1;
	int invertNO = 1;
	int invertYES = 0;
	double widthTriangleBase;
	double heightTriangleBase;
	
	double widthTriangleApoioVertical;
	double heightTriangleApoioVertical;
	
	double widthTriangleApoioHorizontal;
	double heightTriangleApoioHorizontal;
	
	double divisorBase;
	double divisorVertical;
	double divisorHorizontal;
	Rectangle3D rectangle3D;
	
	public PointsCalculator(Rectangle3D rectangle3D, double divBase, double divVertical, double divHorizontal){
		this.divisorBase = divBase;
		this.divisorVertical = divVertical;
		this.divisorHorizontal = divHorizontal;
		
		if (rectangle3D == null){
			notNull = 0;
		}
		
		else {
		this.rectangle3D = rectangle3D; notNull = 1;
		widthTriangleBase = (rectangle3D.getX() / divisorBase);
		heightTriangleBase = (rectangle3D.getY() / divisorBase);
		
		widthTriangleApoioVertical = (rectangle3D.getX() / divisorVertical);
		heightTriangleApoioVertical = (rectangle3D.getY() / divisorVertical);
		
		widthTriangleApoioHorizontal = (rectangle3D.getX() / divisorHorizontal);
		heightTriangleApoioHorizontal = (rectangle3D.getY() / divisorHorizontal);
		}
		
	}
	
	public Point3d calcularApoioBaseTriangular(int ponto, int invertN) {

		
	if (invertN == invertNO){
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2, rectangle3D.getY()
					/ 2 + heightTriangleBase / 2,0);

			return ponto1;
		}

		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(rectangle3D.getX() / 2
					- widthTriangleBase / 2, rectangle3D.getY() / 2 - heightTriangleBase / 2,0);

			return ponto2;
		}

		if (ponto == PONTO3 && notNull == 1) {

			Point3d ponto3 = new Point3d(rectangle3D.getX() / 2
					+ widthTriangleBase / 2, rectangle3D.getY() / 2 - heightTriangleBase / 2,0);
			return ponto3;
		}
	}
	
	if (invertN == invertYES){
		if (ponto == PONTO1 && notNull == 1) {
			
			
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2, rectangle3D.getY()
					/ 2 - heightTriangleBase / 2,0);
			
							
			return ponto1;
		}

		if (ponto == PONTO2 && notNull == 1) {
			
			
			Point3d ponto2 = new Point3d(rectangle3D.getX() / 2
					- widthTriangleBase / 2, rectangle3D.getY() / 2 + heightTriangleBase / 2,0);
				
			return ponto2;
		}

		if (ponto == PONTO3 && notNull == 1) {

			
			
			Point3d ponto3 = new Point3d(rectangle3D.getX() / 2
					+ widthTriangleBase / 2, rectangle3D.getY() / 2 + heightTriangleBase / 2,0);
						
			return ponto3;
		}
	}
	
	
		return null;
	}

	public Point3d calcularApoioBase(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2
					- widthTriangleBase / 2, rectangle3D.getY() / 2 + heightTriangleBase / 2,0);

			return ponto1;
		}

		if (ponto == PONTO2 && notNull == 1) {

			Point3d ponto2 = new Point3d(rectangle3D.getX() / 2
					+ widthTriangleBase / 2, rectangle3D.getY() / 2 + heightTriangleBase / 2,0);
			return ponto2;
		}
		
		if (ponto == PONTO3 && notNull == 1) {
			Point3d ponto3 = new Point3d(rectangle3D.getX() / 2
					- widthTriangleBase / 2, rectangle3D.getY() / 2 - heightTriangleBase / 2,0);

			return ponto3;
		}

		if (ponto == PONTO4 && notNull == 1) {

			Point3d ponto4 = new Point3d(rectangle3D.getX() / 2
					+ widthTriangleBase / 2, rectangle3D.getY() / 2 - heightTriangleBase / 2,0);
			return ponto4;
		}
		
		if (ponto == PONTO5 && notNull == 1) {

			Point3d ponto5 = new Point3d(rectangle3D.getX() / 2
					, rectangle3D.getY() / 2,0);
			return ponto5;
		}
		

		if (ponto == PONTO6 && notNull == 1) {

			Point3d ponto6 = new Point3d(rectangle3D.getX() / 2
					, rectangle3D.getY() / 2 + heightTriangleBase / 2,0);
			return ponto6;
		}
		

		if (ponto == PONTO7 && notNull == 1) {

			Point3d ponto7 = new Point3d(rectangle3D.getX() / 2
					, rectangle3D.getY() / 2 - heightTriangleBase / 2,0);
			return ponto7;
		}
		

		
		return null;
	}

	public Point3d calcularApoioVerticalFrontal(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2
					, 0, rectangle3D.getZ() / 2 + heightTriangleApoioVertical / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(rectangle3D.getX() / 2 - widthTriangleApoioVertical / 2
					, 0, (rectangle3D.getZ() / 2) - (heightTriangleApoioVertical / 2));

			return ponto2;
		}
		
		if (ponto == PONTO3 && notNull == 1) {
			Point3d ponto3 = new Point3d(rectangle3D.getX() / 2 + widthTriangleApoioVertical / 2
					, 0, (rectangle3D.getZ() / 2) - (heightTriangleApoioVertical / 2));

			return ponto3;
		}
		
		if (ponto == PONTO4 && notNull == 1) {
			Point3d ponto4 = new Point3d(rectangle3D.getX() / 2 - widthTriangleApoioVertical / 2
					, 0, (rectangle3D.getZ() / 2) + (heightTriangleApoioVertical / 2));

			return ponto4;
		}
		
		if (ponto == PONTO5 && notNull == 1) {
			Point3d ponto5 = new Point3d(rectangle3D.getX() / 2 + widthTriangleApoioVertical / 2
					, 0, (rectangle3D.getZ() / 2) + (heightTriangleApoioVertical / 2));

			return ponto5;
		}
		
			
		if (ponto == PONTO6 && notNull == 1) {
			Point3d ponto6 = new Point3d(rectangle3D.getX() / 2
					, 0, rectangle3D.getZ() / 2);

			return ponto6;
		}
		
		
		return null;
	}

	public Point3d calcularApoioVerticalPosterior(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2
					, rectangle3D.getY(), rectangle3D.getZ() / 2 + heightTriangleApoioVertical / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(rectangle3D.getX() / 2 - widthTriangleApoioVertical / 2
					, rectangle3D.getY(), (rectangle3D.getZ() / 2) - (heightTriangleApoioVertical / 2));

			return ponto2;
		}
		
		if (ponto == PONTO3 && notNull == 1) {
			Point3d ponto3 = new Point3d(rectangle3D.getX() / 2 + widthTriangleApoioVertical / 2
					, rectangle3D.getY(), rectangle3D.getZ() / 2 - heightTriangleApoioVertical / 2);

			return ponto3;
		}
		
		if (ponto == PONTO4 && notNull == 1) {
			Point3d ponto4 = new Point3d(rectangle3D.getX() / 2 - widthTriangleApoioVertical / 2
					, rectangle3D.getY(), rectangle3D.getZ() / 2 + heightTriangleApoioVertical / 2);

			return ponto4;
		}
		
		if (ponto == PONTO5 && notNull == 1) {
			Point3d ponto5 = new Point3d(rectangle3D.getX() / 2 + widthTriangleApoioVertical / 2
					, rectangle3D.getY(), rectangle3D.getZ() / 2 + heightTriangleApoioVertical / 2);

			return ponto5;
		}
		
			
		if (ponto == PONTO6 && notNull == 1) {
			Point3d ponto6 = new Point3d(rectangle3D.getX() / 2
					, rectangle3D.getY(), rectangle3D.getZ() / 2);

			return ponto6;
		}
		
		
		return null;
	}

	public Point3d calcular2ApoiosVerticalFrontal(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2 - (widthTriangleApoioVertical / 2)
					, 0, rectangle3D.getZ() / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2 - (widthTriangleApoioVertical / 2)
					, 0, rectangle3D.getZ() / 2 );

			return ponto1;
		}
		
				
		return null;
	}

	public Point3d calcular2ApoiosVerticalPosterior(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2 - (widthTriangleApoioVertical / 2)
					, rectangle3D.getY() , rectangle3D.getZ() / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX() / 2 - (widthTriangleApoioVertical / 2)
					, rectangle3D.getY() , rectangle3D.getZ() / 2 );

			return ponto1;
		}
		
		return null;
	}

	public Point3d calcularApoioHorizontalEsquerdo(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(0
					, rectangle3D.getY() / 2, rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(0
					, rectangle3D.getY() / 2 + widthTriangleApoioHorizontal / 2 , rectangle3D.getZ() / 2 - heightTriangleApoioHorizontal / 2 );

			return ponto2;
		}
		
		if (ponto == PONTO3 && notNull == 1) {
			Point3d ponto3 = new Point3d(0
					, (rectangle3D.getY() / 2) - widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 - heightTriangleApoioHorizontal / 2 );

			return ponto3;
		}
		
		if (ponto == PONTO4 && notNull == 1) {
			Point3d ponto4 = new Point3d(0
					, (rectangle3D.getY() / 2) + widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto4;
		}
		
		if (ponto == PONTO5 && notNull == 1) {
			Point3d ponto5 = new Point3d(0
					, (rectangle3D.getY() / 2) - widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto5;
		}
		
				
		if (ponto == PONTO6 && notNull == 1) {
			Point3d ponto6 = new Point3d(0
					, rectangle3D.getY() / 2 , rectangle3D.getZ() / 2);

			return ponto6;
		}
		

		return null;
	}

	public Point3d calcularApoioHorizontalDireito(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX()
					, rectangle3D.getY() / 2, rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(rectangle3D.getX()
					, rectangle3D.getY() / 2 + widthTriangleApoioHorizontal / 2 , rectangle3D.getZ() / 2 - heightTriangleApoioHorizontal / 2 );

			return ponto2;
		}
		
		if (ponto == PONTO3 && notNull == 1) {
			Point3d ponto3 = new Point3d(rectangle3D.getX()
					, (rectangle3D.getY() / 2) - widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 - heightTriangleApoioHorizontal / 2 );

			return ponto3;
		}
		
		if (ponto == PONTO4 && notNull == 1) {
			Point3d ponto4 = new Point3d(rectangle3D.getX()
					, (rectangle3D.getY() / 2) + widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto4;
		}
		
		if (ponto == PONTO5 && notNull == 1) {
			Point3d ponto5 = new Point3d(rectangle3D.getX()
					, (rectangle3D.getY() / 2) - widthTriangleApoioHorizontal/ 2 , rectangle3D.getZ() / 2 + heightTriangleApoioHorizontal / 2 );

			return ponto5;
		}
		
				
		if (ponto == PONTO6 && notNull == 1) {
			Point3d ponto6 = new Point3d(rectangle3D.getX()
					, rectangle3D.getY() / 2 , rectangle3D.getZ() / 2);

			return ponto6;
		}
		return null;
	}

	public Point3d calcular2ApoiosHorizontalEsquerdo(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(0
					, rectangle3D.getY() / 2 + widthTriangleApoioHorizontal / 2, rectangle3D.getZ() / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(0
					, rectangle3D.getY() / 2 - widthTriangleApoioHorizontal / 2 , rectangle3D.getZ() / 2 );

			return ponto2;
		}
		
		return null;
	}

	public Point3d calcular2ApoiosHorizontalDireito(int ponto) {
		
		if (ponto == PONTO1 && notNull == 1) {
			Point3d ponto1 = new Point3d(rectangle3D.getX()
					, rectangle3D.getY() / 2 + widthTriangleApoioHorizontal / 2, rectangle3D.getZ() / 2);

			return ponto1;
		}
		
		if (ponto == PONTO2 && notNull == 1) {
			Point3d ponto2 = new Point3d(rectangle3D.getX()
					, rectangle3D.getY() / 2 - widthTriangleApoioHorizontal / 2 , rectangle3D.getZ() / 2 );

			return ponto2;
		}
		
		return null;
	}
	


	
}
