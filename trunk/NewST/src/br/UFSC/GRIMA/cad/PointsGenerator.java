
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
	private double diameter;
	int tolerance=2;
	//Variáveis de listagem:
	public ArrayList<ArrayList<ArrayList<Point3d>>> setupsArray;
	public ArrayList<ArrayList<Point3d>> facesArray;
	public ArrayList<Corners> forbiddenSpots = null;		//ArrayList das partes proibidas
	Corners corners = null;
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
		this.setupsArray = new ArrayList<ArrayList<ArrayList<Point3d>>>();
		this.facesArray = new ArrayList<ArrayList<Point3d>>();
		this.forbiddenSpots = new ArrayList<Corners>();
		//this.corners = new Corners();
		this.pointgen();
	}
	private void pointgen() {
		
//		setupsArray = new ArrayList<ArrayList<ArrayList<Point3d>>>();
//		facesArray = new ArrayList<ArrayList<Point3d>>();
//		forbiddenSpots = new ArrayList<Corners>();
//		corners = new Corners();
//		setupsNumber = 0;
		//****Procura por FEATURES nas faces*****//
		for (int i=0; i<projeto.getBloco().faces.size(); i++)						//Procura por FEATURES em cada face
		{
			Face faceTmp = (Face)projeto.getBloco().faces.elementAt(i);	
			
			if (faceTmp.features.size() > 0)										//Se a face possui FEATURES
			{
				System.out.println(faceTmp.getTipo());
				for (int j=0; j<faceTmp.features.size(); j++){						//Procura por FEATURES PASSANTES
					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
					if (featureTmp.getClass() == Cavidade.class){					//caso CAVIDADE
						Cavidade cavidade = (Cavidade)featureTmp;
						if(cavidade.isPassante()){
							this.corners = new Corners();
							//Gerar os cantos da cavidade:
							corners.c1 = cavidade.X;
							corners.c2 = cavidade.X+cavidade.getComprimento();
							corners.c3 = cavidade.Y;
							corners.c4 = cavidade.Y+cavidade.getLargura();
							forbiddenSpots.add(corners);
							//para verificação:
//							System.out.println(forbiddenSpots.get(j).c1);
//							System.out.println(forbiddenSpots.get(j).c2);
//							System.out.println(forbiddenSpots.get(j).c3);
//							System.out.println(forbiddenSpots.get(j).c4);

						}
					}
					if (featureTmp.getClass() == FuroBasePlana.class){				//caso FURO
						FuroBasePlana furo = (FuroBasePlana)featureTmp;
						if(furo.isPassante()){
							this.corners = new Corners();
							corners.c1 = furo.X-(furo.getDiametro()/2);
							corners.c2 = furo.X+(furo.getDiametro()/2);
							corners.c3 = furo.Y-(furo.getDiametro()/2);
							corners.c4 = furo.Y+(furo.getDiametro()/2);
							forbiddenSpots.add(corners);
//							System.out.println(corners.c1);
//							System.out.println(corners.c2);
//							System.out.println(corners.c3);
//							System.out.println(corners.c4);
						}
					}
				}
				System.out.println("Lista dos pontos proibidos:");
				for (int k=0; k<forbiddenSpots.size(); k++){
					System.out.printf("%.1f\t%.1f\t%.1f\t%.1f\n", forbiddenSpots.get(k).c1, forbiddenSpots.get(k).c2, forbiddenSpots.get(k).c3, forbiddenSpots.get(k).c4);
				}
				gerarPontosBase(forbiddenSpots);
				
				setupsArray.add(i, facesArray);
			}
			else setupsArray.add(i, null); 
		}
		
//		//Análise da peça:
//		for (int i=0; i<projeto.getBloco().faces.size(); i++) 
//			if (faceTmp. = 0) setupsArray.add(i, null);
	}
	
	private void gerarPontosBase(ArrayList<Corners> forbiddenSpots)
	{
		ArrayList<Point3d> supportsArray = new ArrayList<Point3d>();
		boolean spot1Flag = true;
		boolean spot2Flag = true;
		boolean spot3Flag = true;
		boolean spot4Flag = true;

		System.out.println("Gerando os suportes:");
		//Verificando os cantos:
		for (int i=0; i<forbiddenSpots.size(); i++){
			//para o canto X0Y0 (support1):
			if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
				spot1Flag = false;
				System.out.printf("Proibido no canto 1 por:\t %d\n", i);
				System.out.println(forbiddenSpots.size());
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
/**Criando os pontos:**/
//Canto UM 1:
		if (spot1Flag) {
			support1 = new Point3d (diameter/2, diameter/2, 0);
			supportsArray.add(support1);
		}
		//Caso não seja possível colocar um suporte no canto da peça, tenta distribuir pra os dois pontos laterais mais próximos
		if (!spot1Flag){
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
					//Varre e  faz bubblesort:
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c3 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && (forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2) < diameter){
							System.out.println("Novamente o ponto não pode ser colocado no canto 1");
							i=j;
							j=0;
						}
					}
					support1 = new Point3d (forbiddenSpots.get(i).c2 + diameter/2, diameter/2, 0);
					supportsArray.add(support1);
					System.out.println("Suporte 1 criado");
				}
			}
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c1 < diameter && forbiddenSpots.get(i).c4 < forbiddenSpots.get(j).c3 && forbiddenSpots.get(j).c3 - forbiddenSpots.get(i).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 5");
						}	
					}
					support5 = new Point3d (diameter/2, diameter/2 + forbiddenSpots.get(i).c4, 0);
					supportsArray.add(support5);
					System.out.println("Suporte 5 criado");
				}
			}
			
		}
//Canto DOIS 2:
		if (spot2Flag) {
			support2 = new Point3d (diameter/2, projeto.getBloco().getLargura()-diameter/2, 0);
			supportsArray.add(support2);
		}
		if (!spot2Flag){
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter && forbiddenSpots.get(i).c1 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (projeto.getBloco().getLargura() - forbiddenSpots.get(j).c4 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && (forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2) < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 2");
						}
					}
					support2 = new Point3d (forbiddenSpots.get(i).c2 + diameter/2, projeto.getBloco().getLargura() - diameter/2, 0);
					supportsArray.add(support2);
					System.out.println("Suporte 2 criado");
				}
			}
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter && forbiddenSpots.get(i).c1 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c1 < diameter && forbiddenSpots.get(j).c4 < forbiddenSpots.get(i).c3 && forbiddenSpots.get(i).c3 - forbiddenSpots.get(j).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 6");
						}	
					}
					support6 = new Point3d (diameter/2,forbiddenSpots.get(i).c3 - diameter/2, 0);
					supportsArray.add(support6);
					System.out.println("Suporte 6 criado");
				}
			}
			
		}
//Canto TRÊS 3:		
		if (spot3Flag) {
			support3 = new Point3d (projeto.getBloco().getComprimento()-diameter/2, diameter/2, 0);
			supportsArray.add(support3);
		}
		if (!spot3Flag){
//Reloca o Canto 3:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter && forbiddenSpots.get(i).c3 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c3 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 3");
						}
					}
					support3 = new Point3d (forbiddenSpots.get(i).c1 - diameter/2, diameter/2, 0);
					supportsArray.add(support3);
					System.out.println("Suporte 3 criado");
				}
			}
//Reloca o canto 7:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter && forbiddenSpots.get(i).c3 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (projeto.getBloco().getComprimento() - forbiddenSpots.get(j).c2 < diameter && forbiddenSpots.get(i).c4 < forbiddenSpots.get(j).c3 && forbiddenSpots.get(j).c3 - forbiddenSpots.get(i).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 7");
						}	
					}
					support7 = new Point3d (projeto.getBloco().getComprimento() - diameter/2, forbiddenSpots.get(i).c4 + diameter/2, 0);
					supportsArray.add(support7);
					System.out.println("Suporte 7 criado");
				}
			}
			
		}
//Canto QUATRO 4:
		if (spot4Flag) {
			support4 = new Point3d (projeto.getBloco().getComprimento()-diameter/2, projeto.getBloco().getLargura()-diameter/2, 0);
			supportsArray.add(support4);
		}
		if (!spot4Flag){
//Reloca o Canto 4:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter && projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (projeto.getBloco().getLargura() - forbiddenSpots.get(j).c4 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 4");
						}
					}
					support4 = new Point3d (forbiddenSpots.get(i).c1 - diameter/2, projeto.getBloco().getLargura() - diameter/2, 0);
					supportsArray.add(support4);
					System.out.println("Suporte 4 criado");
				}
			}
//Reloca o canto 8:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (projeto.getBloco().getComprimento() - forbiddenSpots.get(i).c2 < diameter && projeto.getBloco().getLargura() - forbiddenSpots.get(i).c4 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (projeto.getBloco().getComprimento() - forbiddenSpots.get(j).c2 < diameter && forbiddenSpots.get(i).c4 < forbiddenSpots.get(j).c3 && forbiddenSpots.get(i).c3 - forbiddenSpots.get(j).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espaço proibido Suporte 8");
						}	
					}
					support8 = new Point3d (projeto.getBloco().getComprimento() - diameter/2, forbiddenSpots.get(i).c3 - diameter/2, 0);
					supportsArray.add(support8);
					System.out.println("Suporte 8 criado");
				}
			}
			
		}
		facesArray.add(supportsArray);
		
		return;
	}
}
