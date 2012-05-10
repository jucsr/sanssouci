
package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import javax.vecmath.Point3d;

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

	//ArrayList das partes proibidas:
	private ArrayList<Corners> forbiddenSpots = null;
	Corners corners = new Corners();
	//Variáveis de pontos de apoio:
	Point3d support1 = null;
	Point3d support2 = null;
	Point3d support3 = null;
	Point3d support4 = null;
	Point3d support5 = null;
	Point3d support6 = null;
	Point3d support7 = null;
	Point3d support8 = null;
	//
	public PointsGenerator(Projeto projeto, double diameter){
		this.projeto = projeto;
		this.diameter = diameter+tolerance;
		this.pointgen();
	}
	private void pointgen() {
		forbiddenSpots = new ArrayList<Corners>();
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
						if(cavidade.isPassante()){
							//Gerar os cantos da cavidade:
							corners.c1 = cavidade.X;
							corners.c2 = cavidade.X+cavidade.getComprimento();
							corners.c3 = cavidade.Y;
							corners.c4 = cavidade.Y+cavidade.getLargura();
							forbiddenSpots.add(corners);
							//para verificação:
							System.out.println(forbiddenSpots.get(j).c1);
							System.out.println(forbiddenSpots.get(j).c2);
							System.out.println(forbiddenSpots.get(j).c3);
							System.out.println(forbiddenSpots.get(j).c4);

						}
					}
					if (featureTmp.getClass() == FuroBasePlana.class){				//caso FURO
						FuroBasePlana furo = (FuroBasePlana)featureTmp;
						if(furo.isPassante()){
							corners.c1 = furo.getPosicaoX();
							corners.c2 = furo.X+furo.getDiametro();
							corners.c3 = furo.Y;
							corners.c4 = furo.Y+furo.getDiametro();
							forbiddenSpots.add(corners);
							System.out.println(corners.c1);
							System.out.println(corners.c2);
							System.out.println(corners.c3);
							System.out.println(corners.c4);
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
