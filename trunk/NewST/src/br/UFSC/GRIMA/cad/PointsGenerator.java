
package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.util.projeto.Projeto;

final class Corners{
	double c1;
	double c2;
	double c3;
	double c4;
}

final class FeaturesTypes{									//Tipos de features existentes
	boolean haPassante, haCavidade, haFuro, haDegrau, haRanhura;
}

final class PossibleHandlingDevices{						//Definir os tipos de suportes poss�veis nesta classe
	boolean vise, anglePlates, clamps, parallels;
}

public class PointsGenerator {
	private Projeto projeto;
	private double diameter;
	int tolerance=2;
	//Vari�veis de listagem:
	public ArrayList<ArrayList<ArrayList<Point3d>>> setupsArray;
	public ArrayList<ArrayList<Point3d>> facesArray;
	public ArrayList<Corners> forbiddenSpots = null;		//ArrayList das partes proibidas
	Corners corners = null;
	//Vari�veis de pontos de apoio:
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
		setupsArray = new ArrayList<ArrayList<ArrayList<Point3d>>>();
//		this.facesArray = new ArrayList<ArrayList<Point3d>>();
//		this.forbiddenSpots = new ArrayList<Corners>();
		//this.corners = new Corners();
		this.pointgen();
	}
	private void supportTypeChecker(){													//Verifica quais s�o as features nas faces. Cria booleanos para dizer quais suportes s�o necess�rios
		//Vari�veis usadas:
		FeaturesTypes[] featTypeArray = new FeaturesTypes[6];
		PossibleHandlingDevices[] posHandDev = new PossibleHandlingDevices[6];
		
		//Procura pela features no bloco:
		for (int i=0; i<projeto.getBloco().faces.size(); i++){
			Face faceTmp = (Face)projeto.getBloco().faces.elementAt(i);	
			if (faceTmp.features.size() > 0){
				for (int j=0; j<faceTmp.features.size(); j++){
					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
					if (featureTmp.getClass() == Cavidade.class){						//caso CAVIDADE
						featTypeArray[i].haCavidade = true;
						Cavidade cavidade = (Cavidade)featureTmp;
						if(cavidade.isPassante()){
							featTypeArray[i].haPassante = true;
						}
					}
					if (featureTmp.getClass() == Furo.class){
						featTypeArray[i].haFuro = true;
						if (featureTmp.getClass() == FuroBasePlana.class){				//caso FURO
							FuroBasePlana furo = (FuroBasePlana)featureTmp;
							if(furo.isPassante()){
								featTypeArray[i].haPassante = true;
							}
						}
					}
					if (featureTmp.getClass() == Degrau.class){
						featTypeArray[i].haDegrau = true;
					}
					if (featureTmp.getClass() == Ranhura.class){
						featTypeArray[i].haRanhura = true;
					}
				}
			}	
		}
		
		//Analisa os poss�veis suportes utilizados
		for (int i=0; i<6; i++){
			if (featTypeArray[i].haPassante) posHandDev[i].parallels = true;
			if (featTypeArray[i].haFuro || featTypeArray[i].haCavidade) posHandDev[i].vise = true;
			if (featTypeArray[i].haDegrau || featTypeArray[i].haRanhura) posHandDev[i].vise = false;
		}
	}
	private void pointgen() {
		//****Procura por FEATURES nas faces*****//
		for (int i=0; i<projeto.getBloco().faces.size(); i++)						//Procura por FEATURES em cada face
		{
			Face faceTmp = (Face)projeto.getBloco().faces.elementAt(i);	
			
			if (faceTmp.features.size() > 0)										//Se a face possui FEATURES
			{
				facesArray = new ArrayList<ArrayList<Point3d>>();
				forbiddenSpots = new ArrayList<Corners>();
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
							System.out.println("furo passante");
						}
					}
				}
				if (forbiddenSpots.isEmpty()){
					facesArray.add(null);
				}
				else{
					facesArray.add(gerarPontosBase(forbiddenSpots, faceTmp));
				}
				for(int k=1; k<6; k++){
					facesArray.add(gerarPontosLaterais(k));
				}
				setupsArray.add(facesArray);
			}
			
		}
		System.out.println("Imprindo Vetor do SETUPS:");
		for (int i=0; i<setupsArray.size(); i++){
			System.out.printf("Setup %d:\t", i);
			System.out.println(setupsArray.get(i));
		}
		projeto.setSetupsArray(setupsArray);
		System.out.println("PONTOS GERADOS COM SUCESSO");
	}
	
	private ArrayList<Point3d> gerarPontosBase(ArrayList<Corners> forbiddenSpots, Face faceTemp)
	{
		ArrayList<Point3d> supportsArray = new ArrayList<Point3d>();
		boolean spot1Flag = true;
		boolean spot2Flag = true;
		boolean spot3Flag = true;
		boolean spot4Flag = true;
		System.out.println(forbiddenSpots.size());
		for (int i=0; i<forbiddenSpots.size(); i++){
			System.out.println("pontos proibidos"+i);
			System.out.println(this.forbiddenSpots.get(i).c1);
			System.out.println(this.forbiddenSpots.get(i).c2);
			System.out.println(this.forbiddenSpots.get(i).c3);
			System.out.println(this.forbiddenSpots.get(i).c4);
		}
		
/**Verificando os cantos:**/

		for (int i=0; i<forbiddenSpots.size(); i++){
			//para o canto X0Y0 (support1):
			if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
				spot1Flag = false;
				System.out.printf("Proibido no canto 1 por:\t %d\n", i);
				System.out.println(forbiddenSpots.size());
			}
			
			//para o canto X0Y1 (support2):
			if (forbiddenSpots.get(i).c1 < diameter &&
					faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter){
				//Fun��o para caso n�o seja permitido://
				spot2Flag = false;
			}
			
			//para o canto X1Y0:
			if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter &&
					forbiddenSpots.get(i).c3 < diameter){
				//
				spot3Flag = false;
			}
			
			//para o canto X1Y1:
			if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter &&
					faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter){
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
		//Caso n�o seja poss�vel colocar um suporte no canto da pe�a, tenta distribuir pra os dois pontos laterais mais pr�ximos
		if (!spot1Flag){
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (forbiddenSpots.get(i).c1 < diameter && forbiddenSpots.get(i).c3 < diameter){
					//Varre e  faz bubblesort:
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c3 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && (forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2) < diameter){
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
			support2 = new Point3d (diameter/2, faceTemp.getLargura()-diameter/2, 0);
			supportsArray.add(support2);
		}
		if (!spot2Flag){
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter && forbiddenSpots.get(i).c1 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (faceTemp.getLargura() - forbiddenSpots.get(j).c4 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && (forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2) < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 2");
						}
					}
					support2 = new Point3d (forbiddenSpots.get(i).c2 + diameter/2, faceTemp.getLargura() - diameter/2, 0);
					supportsArray.add(support2);
					System.out.println("Suporte 2 criado");
				}
			}
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter && forbiddenSpots.get(i).c1 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c1 < diameter && forbiddenSpots.get(j).c4 < forbiddenSpots.get(i).c3 && forbiddenSpots.get(i).c3 - forbiddenSpots.get(j).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 6");
						}	
					}
					support6 = new Point3d (diameter/2,forbiddenSpots.get(i).c3 - diameter/2, 0);
					supportsArray.add(support6);
					System.out.println("Suporte 6 criado");
				}
			}
			
		}
//Canto TR�S 3:		
		if (spot3Flag) {
			support3 = new Point3d (faceTemp.getComprimento()-diameter/2, diameter/2, 0);
			supportsArray.add(support3);
		}
		if (!spot3Flag){
//Reloca o Canto 3:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter && forbiddenSpots.get(i).c3 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (forbiddenSpots.get(j).c3 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2 < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 3");
						}
					}
					support3 = new Point3d (forbiddenSpots.get(i).c1 - diameter/2, diameter/2, 0);
					supportsArray.add(support3);
					System.out.println("Suporte 3 criado");
				}
			}
//Reloca o canto 7:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter && forbiddenSpots.get(i).c3 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (faceTemp.getComprimento() - forbiddenSpots.get(j).c2 < diameter && forbiddenSpots.get(i).c4 < forbiddenSpots.get(j).c3 && forbiddenSpots.get(j).c3 - forbiddenSpots.get(i).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 7");
						}	
					}
					support7 = new Point3d (faceTemp.getComprimento() - diameter/2, forbiddenSpots.get(i).c4 + diameter/2, 0);
					supportsArray.add(support7);
					System.out.println("Suporte 7 criado");
				}
			}
			
		}
//Canto QUATRO 4:
		if (spot4Flag) {
			support4 = new Point3d (faceTemp.getComprimento()-diameter/2, faceTemp.getLargura()-diameter/2, 0);
			supportsArray.add(support4);
		}
		if (!spot4Flag){
//Reloca o Canto 4:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter && faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (faceTemp.getLargura() - forbiddenSpots.get(j).c4 < diameter && forbiddenSpots.get(j).c1 > forbiddenSpots.get(i).c2 && forbiddenSpots.get(j).c1 - forbiddenSpots.get(i).c2 < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 4");
						}
					}
					support4 = new Point3d (forbiddenSpots.get(i).c1 - diameter/2, faceTemp.getLargura() - diameter/2, 0);
					supportsArray.add(support4);
					System.out.println("Suporte 4 criado");
				}
			}
//Reloca o canto 8:
			for (int i=0; i<forbiddenSpots.size(); i++){
				if (faceTemp.getComprimento() - forbiddenSpots.get(i).c2 < diameter && faceTemp.getLargura() - forbiddenSpots.get(i).c4 < diameter){
					for (int j=0; j<forbiddenSpots.size(); j++){
						if (faceTemp.getComprimento() - forbiddenSpots.get(j).c2 < diameter && forbiddenSpots.get(i).c4 < forbiddenSpots.get(j).c3 && forbiddenSpots.get(i).c3 - forbiddenSpots.get(j).c4 < diameter){
							i=j;
							j=0;
							System.out.println("espa�o proibido Suporte 8");
						}	
					}
					support8 = new Point3d (faceTemp.getComprimento() - diameter/2, forbiddenSpots.get(i).c3 - diameter/2, 0);
					supportsArray.add(support8);
					System.out.println("Suporte 8 criado");
				}
			}
			
		}
		
		return supportsArray;
	}

	private ArrayList<Point3d> gerarPontosLaterais(int faceType){
	//Vari�veis:
		Point3d support1 = null;
		Point3d support2 = null;
		ArrayList<Point3d> supportsArray = new ArrayList<Point3d>();
		Face faceTmp = (Face) projeto.getBloco().faces.elementAt(faceType);
		if (faceTmp.features.size() == 0){
			support1 = new Point3d(diameter, diameter, 0);
			if (faceTmp.getComprimento()>faceTmp.getLargura()){
				support2 = new Point3d(faceTmp.getComprimento()-diameter, diameter, 0);
			}
			else{
				support2 = new Point3d(diameter, faceTmp.getLargura()-diameter, 0);
			}
		}
		supportsArray.add(support1);
		supportsArray.add(support2);
		return supportsArray;
	}
}