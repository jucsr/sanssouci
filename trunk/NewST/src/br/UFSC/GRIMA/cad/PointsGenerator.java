
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
	private int setupNumber;
	private double diameter;
	int tolerance=0;

	//Variáveis de listagem:
	public ArrayList<ArrayList<ArrayList<Point3d>>> setupsArray;
	public ArrayList<ArrayList<Point3d>> facePointsArray;
	public ArrayList<Corners> forbiddenSpots = null;		//ArrayList das partes proibidas
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

	
	
	public PointsGenerator(Projeto projeto, double diameter){
		this.projeto = projeto;
		this.diameter = diameter + tolerance;
		this.pointgen();
	}
	private void pointgen() {
		
		setupsArray = new ArrayList<ArrayList<ArrayList<Point3d>>>();
		facePointsArray = new ArrayList<ArrayList<Point3d>>();
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
							corners.c1 = furo.X-(furo.getDiametro()/2);
							corners.c2 = furo.X+(furo.getDiametro()/2);
							corners.c3 = furo.Y-(furo.getDiametro()/2);
							corners.c4 = furo.Y+(furo.getDiametro()/2);
							forbiddenSpots.add(corners);
							System.out.println(corners.c1);
							System.out.println(corners.c2);
							System.out.println(corners.c3);
							System.out.println(corners.c4);
						}
					}
				}
				facePointsArray.add(gerarPontos(forbiddenSpots));
				setupsArray.add(i, facePointsArray);
			}
			else setupsArray.add(i, null); 
		}
		
//		//Análise da peça:
//		for (int i=0; i<projeto.getBloco().faces.size(); i++) 
//			if (faceTmp. = 0) setupsArray.add(i, null);
	}
	
	private ArrayList<Point3d> gerarPontos(ArrayList<Corners> forbiddenSpots)
	{
		ArrayList<Point3d> supportsArray = new ArrayList<Point3d>();
		boolean spot1Flag = true;
		boolean spot2Flag = true;
		boolean spot3Flag = true;
		boolean spot4Flag = true;
		//Verificando os cantos:
		for (int i=0; i<forbiddenSpots.size(); i++){
			//para o canto X0Y0 (support1):
			if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
				spot1Flag = false;
			}
			
			//para o canto X0Y1 (support2):
			if (forbiddenSpots.get(i).c1 < diameter &&
					projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter){
				//Função para caso não seja permitido://
				spot2Flag = false;
			}
			
			//para o canto X1Y0:
			if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter &&
					forbiddenSpots.get(i).c3 < diameter){
				//
				spot3Flag = false;
			}
			
			//para o canto X1Y1:
			if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter &&
					projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter){
				//
				spot4Flag = false;
			}
		}
		//Criando os pontos:
		if (spot1Flag) {
			support1 = new Point3d (diameter/2, diameter/2, 0);
			supportsArray.add(support1);
		}
		if (spot2Flag) {
			support2 = new Point3d (0, projeto.getBloco().getLargura()-diameter/2, 0);
			supportsArray.add(support2);
		}
		if (spot3Flag) {
			support3 = new Point3d (projeto.getBloco().getComprimento()-diameter/2, 0, 0);
			supportsArray.add(support3);
		}
		if (spot4Flag) {
			support4 = new Point3d (projeto.getBloco().getComprimento()-diameter/2, projeto.getBloco().getLargura()-diameter/2, 0);
			supportsArray.add(support4);
		}
		
		
		return supportsArray;
	}
}
