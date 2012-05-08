
package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.util.projeto.Projeto;

final class Corners{
	double c1;
	double c2;
	double c3;
	double c4;
}

public class PointsGenerator {
	private Projeto projeto;
	private ArrayList <Face>setups;
	private int setupNumber;
	private double diameter;
	int tolerance=1;

	//Array das partes proibidas:
	private ArrayList<Point3d> xForbidden = new ArrayList<Point3d> ();
	
	private ArrayList<Point3d> yForbidden = new ArrayList<Point3d> ();
	///
	
	private ArrayList<Corners> matrix = null;
	
	//Variáveis de posição:
	Point3d corner1 = null;
	Point3d corner2 = null;
	Point3d corner3 = null;
	Point3d corner4 = null;
	//
	public PointsGenerator(Projeto projeto, double diameter){
		this.projeto = projeto;
		this.diameter = diameter+tolerance;
		this.pointgen();
	}
	private void pointgen() {
		//Gera os cantos:
		matrix = new ArrayList<Corners>();
		
		corner1 = new Point3d (0, 0, 0);
		corner2 = new Point3d (0, this.projeto.getBloco().getY()-(diameter), 0);
		corner3 = new Point3d (this.projeto.getBloco().getX()- (diameter), 0, 0);
		corner4 = new Point3d (this.projeto.getBloco().getX()- (diameter), this.projeto.getBloco().getY()-(diameter), 0);

		System.out.println (corner1);
		System.out.println (corner2);
		System.out.println (corner3);
		System.out.println (corner4);
		
		//****Procura por FEATURES nas faces*****//
		for (int i=0; i<projeto.getBloco().faces.size(); i++)						//Procura por FEATURES em cada face
		{
			Face faceTmp = (Face)projeto.getBloco().faces.elementAt(i);	
			if (faceTmp.features.size() > 0)										//Se a face possui FEATURES
			{
				for (int j=0; j<faceTmp.features.size(); j++){						//Procura por FEATURES PASSANTES
					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
					if (featureTmp.getClass() == Cavidade.class){					//caso CAVIDADE
						Cavidade cavidade = (Cavidade)featureTmp;
						if(cavidade.isPassante()){									//caso a peça tenha os 4 cantos livres
							
							xForbidden.add(j, new Point3d (cavidade.X, cavidade.X+cavidade.getComprimento(), cavidade.Y));
							System.out.println("Faixa proibida em X:");
							System.out.println(xForbidden.get(j));
							
							yForbidden.add(j, new Point3d (cavidade.Y, cavidade.Y+cavidade.getLargura(), cavidade.X));
							System.out.println("Faixa proibida em Y:");
							System.out.println(yForbidden.get(j));
							
							/*
							//Gerar os cantos da cavidade:
							Point3d cornerPocket1 = new Point3d (cavidade.X, cavidade.Y, 0);
							Point3d cornerPocket2 = new Point3d (cavidade.X, cavidade.Y+cavidade.getLargura(), 0);
							Point3d cornerPocket3 = new Point3d (cavidade.X+cavidade.getComprimento(), cavidade.Y, 0);
							Point3d cornerPocket4 = new Point3d (cavidade.X+cavidade.getComprimento(), cavidade.Y+cavidade.getLargura(), 0);
							System.out.println(cornerPocket1);
							System.out.println(cornerPocket2);
							System.out.println(cornerPocket3);
							System.out.println(cornerPocket4);
							
							//testando os cantos:
							if (corner1.x < cornerPocket1.x || corner1.y < cornerPocket1.y){
								System.out.println("permitido no canto X0Y0");
							}
							if (corner2.x < xForbiddenInitial || corner2.y > yForbiddenFinal){
								System.out.println("permitido no canto X0Y1");
							}
							if (corner3.x > xForbiddenFinal || corner3.y < yForbiddenFinal){
								System.out.println("permitido no canto X1Y0");
							}
							if (corner4.x > xForbiddenFinal || corner4.y > yForbiddenFinal){
								System.out.println("permitido no canto X1Y1");
							}
							//fim teste dos cantos
							*/
						}
					}
					if (featureTmp.getClass() == FuroBasePlana.class){				//caso FURO
						FuroBasePlana furo = (FuroBasePlana)featureTmp;
						if(furo.isPassante()){
							//code
						}
					}
				}
				
//				faceTmp.setPontosDeApoio(pontosDeApoio);
				setupNumber++;
				
			}
		}
	}
	public ArrayList<Point3d> gerarPontos()
	{
		return null;
	}
}
