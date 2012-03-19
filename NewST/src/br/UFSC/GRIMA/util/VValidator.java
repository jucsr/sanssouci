package br.UFSC.GRIMA.util;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;

import com.sun.xml.internal.fastinfoset.sax.Features;



import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.exceptions.ProjetoInvalidoException;

public class VValidator {


	
	Rectangle3D rectangle3D;
	ArrayList<Feature> arrayList;
	ArrayList<ArrayList<Point3d>> list;
	ArrayList<Point3d> listaApoiosBase;
	ArrayList<Point3d> listaApoiosHorizontais;
	ArrayList<Point3d> listaApoiosVerticais;
	Point3d apoioBase1;
	Point3d apoioBase2;
	Point3d apoioBase3;
	Point3d apoioBase4;
	Point3d apoioBase5;
	Point3d apoioBase6;
	Point3d apoioBase7;
	
	Point3d apoioVerticalFrontal1;
	Point3d apoioVerticalFrontal2;
	Point3d apoioVerticalFrontal3;
	Point3d apoioVerticalFrontal4;
	Point3d apoioVerticalFrontal5;
	
	Point3d apoioVerticalPosterior1;
	Point3d apoioVerticalPosterior2;
	Point3d apoioVerticalPosterior3;
	Point3d apoioVerticalPosterior4;
	Point3d apoioVerticalPosterior5;
	
	Point3d apoioHorizontalEsquerdo1;
	Point3d apoioHorizontalEsquerdo2;
	Point3d apoioHorizontalEsquerdo3;
	Point3d apoioHorizontalEsquerdo4;
	Point3d apoioHorizontalEsquerdo5;
	
	Point3d apoioHorizontalDireito1;
	Point3d apoioHorizontalDireito2;
	Point3d apoioHorizontalDireito3;
	Point3d apoioHorizontalDireito4;
	Point3d apoioHorizontalDireito5;
	
	
	int PONTO1 = 1;
	int PONTO2 = 2;
	int PONTO3 = 3;
	int PONTO4 = 4;
	int PONTO5 = 5;
	int PONTO6 = 6;
	int PONTO7 = 7;
	int toDo = 1;
	int triangle = 0;
	int tried3Base = 0;
	int tried4Base = 0;
	int tried5Base = 0;
	int tried6Base = 0;
	int tried7Base = 0;
	int resizetoDo = 1;
	int resizeLimit = 0;
	int invertN = 1;
	int nBase;
	int nVertical;
	int nHorizontal;
	double raioApoio;
	PointsCalculator pCalculator;
	Verificador pVerificador;
	double divisorPadrao = Math.sqrt(2);
	double divisorBase = divisorPadrao;
	double divisorVertical = divisorPadrao;
	double divisorHorizontal = divisorPadrao;
	

	
	public VValidator(Rectangle3D rectangle3D, ArrayList<Feature> arrayList, double raioApoio) {
		this.arrayList = arrayList;
		this.raioApoio = raioApoio;
		this.rectangle3D = rectangle3D;
		pCalculator = new PointsCalculator(rectangle3D, divisorBase, divisorVertical, divisorHorizontal);
		pVerificador = new Verificador(rectangle3D, divisorBase, divisorVertical, divisorHorizontal);
	}

	public ArrayList<ArrayList<Point3d>> getPoints( int apoiosBase, int apoiosVerticais, 
			int apoiosHorizontais) throws ProjetoInvalidoException {
			
		
		nBase = apoiosBase; // Quantidade de Apoios da Base.
		nVertical = apoiosVerticais; // Quantidade de Apoios Laterais em Posi�ao Vertical.
		nHorizontal = apoiosHorizontais; // Quantidade de Apoios Laterais em Posi�ao Horizontal.
		ArrayList<ArrayList<Point3d>> list = new ArrayList<ArrayList<Point3d>>();
		
		ArrayList<Point3d> listaApoiosBase = new ArrayList<Point3d>();
		ArrayList<Point3d> listaApoiosVerticais = new ArrayList<Point3d>();
		ArrayList<Point3d> listaApoiosHorizontais = new ArrayList<Point3d>();
		
		
		if(rectangle3D==null){ return null; } // Se o retangulo for nulo, retorna nulo ao tentar obter Pontos de Apoio.
		
		// Verifica�ao do valor do raio.
		if(raioApoio < 0
				|| (raioApoio > (rectangle3D.getX() / 3))
				|| (raioApoio > (rectangle3D.getY() / 3))
		){
			throw new ProjetoInvalidoException("Raio do Apoio nao valido");
		}
		
		// Verifica�ao dos valores das dimensoes do  bloco.
		if(rectangle3D.getX() < 0 
				|| rectangle3D.getY() < 0 
				|| rectangle3D.getZ() < 0 )
		{throw new ProjetoInvalidoException("Erro nas dimensoes do bloco");}
		
				
		// Verfica o numero de Apoios da Base e de acordo com isso adiciona os pontos de apoio solicitados
		// Tambem verifica se os valores dos features estao corretos e se estes colidem com os pontos de apoio.
		// Se existe colisao, tenta-se corregir os pontos de apoio.
		
		else if (apoiosBase == 3) {
			
			triangle = 1;			
			apoioBase1 = pCalculator.calcularApoioBaseTriangular(PONTO1, invertN);
			apoioBase2 = pCalculator.calcularApoioBaseTriangular(PONTO2, invertN);
			apoioBase3 = pCalculator.calcularApoioBaseTriangular(PONTO3, invertN);
			
			listaApoiosBase.add(apoioBase1);
			listaApoiosBase.add(apoioBase2);
			listaApoiosBase.add(apoioBase3);
			
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				
				if ((feature.getClass() == FuroBasePlana.class) || (feature.getClass() == Cavidade.class) 
						|| (feature.getClass() == FuroConico.class)){
					
					int tipo = feature.getTipo();
					
					if (tipo == Feature.FURO) {
						Furo furo = (Furo) feature;
						if(furo.isPassante()){
						
							if (pVerificador.verificarValoresFuro(furo)){throw new ProjetoInvalidoException("Erro no furo: " + furo.getNome());}
						//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
							else if ((pVerificador.validateFuroComArrayBase(furo, raioApoio, listaApoiosBase)) == false){
							listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
							}
						}
						
					}
					
					if(feature.getClass() == FuroConico.class){
						FuroConico furoConico = (FuroConico) feature;
						
						if (furoConico.isPassante()){
							
							double xFuroAux = (furoConico.getPosicaoX() + (furoConico.getRaio() - furoConico.getRaio1()));
							double yFuroAux = (furoConico.getPosicaoY() + (furoConico.getRaio() - furoConico.getRaio1()));
							double diametroFuroAux = (furoConico.getRaio1()*2);
							
							Furo furoAux = new Furo("furoAux", xFuroAux,yFuroAux,0,diametroFuroAux,furoConico.getProfundidade());
							
								if (pVerificador.verificarValoresFuro(furoAux)){throw new ProjetoInvalidoException("Erro no furo: " + furoAux.getNome());}
								//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
								else if ((pVerificador.validateFuroComArrayBase(furoAux, raioApoio, listaApoiosBase)) == false){
								listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
								}
						}
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;
						
						if(cavidade.isPassante()){
						
							if(pVerificador.verificarValoresCavidade(cavidade)){throw new ProjetoInvalidoException("Erro na cavidade: " + cavidade.getIndice());}
							//else if(pVerificador.validateCavidadeComFeatures(cavidade, posic, arrayList)== false ){throw new ProjetoInvalidoException("Colisao com cavidade" + cavidade.getIndice());}
														
							else if(pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio, listaApoiosBase) == false ){
								listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);}
							
						}
					}
					
				}
				
				else { listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);				
					}
				
				posic++;
				
			}
						
			//return list;
		}
		
		else if (apoiosBase == 4){
		
			triangle = 0;	
			apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
			apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
			apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
			apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
			listaApoiosBase.add(apoioBase1);
			listaApoiosBase.add(apoioBase2);
			listaApoiosBase.add(apoioBase3);
			listaApoiosBase.add(apoioBase4);
			
			Iterator<Feature> iterator = arrayList.iterator();
			int posic = 0;
			
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				
				
				if ((feature.getClass() == FuroBasePlana.class) || (feature.getClass() == Cavidade.class) 
						|| (feature.getClass() == FuroConico.class)){
						
					int tipo = feature.getTipo();
					
					if (tipo == Feature.FURO) {
						Furo furo = (Furo) feature;
						if(furo.isPassante()){
						
							if (pVerificador.verificarValoresFuro(furo)){throw new ProjetoInvalidoException("Erro no furo: " + furo.getNome());}
						//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
							else if ((pVerificador.validateFuroComArrayBase(furo, raioApoio, listaApoiosBase)) == false){
							listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
							}
						}
						
					}
					
					if(feature.getClass() == FuroConico.class){
						FuroConico furoConico = (FuroConico) feature;
						
						if (furoConico.isPassante()){
							
							double xFuroAux = (furoConico.getPosicaoX() + (furoConico.getRaio() - furoConico.getRaio1()));
							double yFuroAux = (furoConico.getPosicaoY() + (furoConico.getRaio() - furoConico.getRaio1()));
							double diametroFuroAux = (furoConico.getRaio1()*2);
							
							Furo furoAux = new Furo("furoAux", xFuroAux,yFuroAux,0,diametroFuroAux,furoConico.getProfundidade());
							
								if (pVerificador.verificarValoresFuro(furoAux)){throw new ProjetoInvalidoException("Erro no furo: " + furoAux.getNome());}
								//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
								else if ((pVerificador.validateFuroComArrayBase(furoAux, raioApoio, listaApoiosBase)) == false){
								listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
								}
						}
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;
						
						if(cavidade.isPassante()){
						
							if(pVerificador.verificarValoresCavidade(cavidade)){throw new ProjetoInvalidoException("Erro na cavidade: " + cavidade.getIndice());}
							//else if(pVerificador.validateCavidadeComFeatures(cavidade, posic, arrayList)== false ){throw new ProjetoInvalidoException("Colisao com cavidade" + cavidade.getIndice());}
														
							else if(pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio, listaApoiosBase) == false ){
								listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);}
							
						}
					}
						
							
				}
				
				else { listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);				
					}
				

				posic++;
			}
			
			
			//return list;
			
		}
		
		else if (apoiosBase == 5){
		
			triangle = 0;
			apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
			apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
			apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
			apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
			apoioBase5 = pCalculator.calcularApoioBase(PONTO5);
			listaApoiosBase.add(apoioBase1);
			listaApoiosBase.add(apoioBase2);
			listaApoiosBase.add(apoioBase3);
			listaApoiosBase.add(apoioBase4);
			listaApoiosBase.add(apoioBase5);
			
			Iterator<Feature> iterator = arrayList.iterator();
			int posic = 0;
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				
				if ((feature.getClass() == FuroBasePlana.class) || (feature.getClass() == Cavidade.class) 
						|| (feature.getClass() == FuroConico.class)){
	
					int tipo = feature.getTipo();
					
					if (tipo == Feature.FURO) {
						Furo furo = (Furo) feature;
						if(furo.isPassante()){
						
							if (pVerificador.verificarValoresFuro(furo)){throw new ProjetoInvalidoException("Erro no furo: " + furo.getNome());}
						//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
							else if ((pVerificador.validateFuroComArrayBase(furo, raioApoio, listaApoiosBase)) == false){
							listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
							}
						}
						
					}
					
					if(feature.getClass() == FuroConico.class){
						FuroConico furoConico = (FuroConico) feature;
						
						if (furoConico.isPassante()){
							
							double xFuroAux = (furoConico.getPosicaoX() + (furoConico.getRaio() - furoConico.getRaio1()));
							double yFuroAux = (furoConico.getPosicaoY() + (furoConico.getRaio() - furoConico.getRaio1()));
							double diametroFuroAux = (furoConico.getRaio1()*2);
							
							Furo furoAux = new Furo("furoAux", xFuroAux,yFuroAux,0,diametroFuroAux,furoConico.getProfundidade());
							
								if (pVerificador.verificarValoresFuro(furoAux)){throw new ProjetoInvalidoException("Erro no furo: " + furoAux.getNome());}
								//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
								else if ((pVerificador.validateFuroComArrayBase(furoAux, raioApoio, listaApoiosBase)) == false){
								listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
								}
						}
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;

						if(cavidade.isPassante()){

							if(pVerificador.verificarValoresCavidade(cavidade)){throw new ProjetoInvalidoException("Erro na cavidade: " + cavidade.getIndice());}
							//else if(pVerificador.validateCavidadeComFeatures(cavidade, posic, arrayList)== false ){throw new ProjetoInvalidoException("Colisao com cavidade" + cavidade.getIndice());}

							else if(pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio, listaApoiosBase) == false ){
								listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);}

						}
					}
								
				}
				
				else { listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);				
					}
						
				posic++;
			}
					
			
			
			//return list;
			
		}
		
		else if (apoiosBase == 6){
		
			triangle = 0;
			apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
			apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
			apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
			apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
			apoioBase6 = pCalculator.calcularApoioBase(PONTO6);
			apoioBase7 = pCalculator.calcularApoioBase(PONTO7);
			listaApoiosBase.add(apoioBase1);
			listaApoiosBase.add(apoioBase2);
			listaApoiosBase.add(apoioBase3);
			listaApoiosBase.add(apoioBase4);
			listaApoiosBase.add(apoioBase6);
			listaApoiosBase.add(apoioBase7);
			
			Iterator<Feature> iterator = arrayList.iterator();
			int posic = 0;
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				
				if ((feature.getClass() == FuroBasePlana.class) || (feature.getClass() == Cavidade.class) 
						|| (feature.getClass() == FuroConico.class)){
						
					int tipo = feature.getTipo();
					
					if (tipo == Feature.FURO) {
						Furo furo = (Furo) feature;
						if(furo.isPassante()){
						
							if (pVerificador.verificarValoresFuro(furo)){throw new ProjetoInvalidoException("Erro no furo: " + furo.getNome());}
						//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
							else if ((pVerificador.validateFuroComArrayBase(furo, raioApoio, listaApoiosBase)) == false){
							listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
							}
						}
						
					}
					
					if(feature.getClass() == FuroConico.class){
						FuroConico furoConico = (FuroConico) feature;
						
						if (furoConico.isPassante()){
							
							double xFuroAux = (furoConico.getPosicaoX() + (furoConico.getRaio() - furoConico.getRaio1()));
							double yFuroAux = (furoConico.getPosicaoY() + (furoConico.getRaio() - furoConico.getRaio1()));
							double diametroFuroAux = (furoConico.getRaio1()*2);
							
							Furo furoAux = new Furo("furoAux", xFuroAux,yFuroAux,0,diametroFuroAux,furoConico.getProfundidade());
							
								if (pVerificador.verificarValoresFuro(furoAux)){throw new ProjetoInvalidoException("Erro no furo: " + furoAux.getNome());}
								//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
								else if ((pVerificador.validateFuroComArrayBase(furoAux, raioApoio, listaApoiosBase)) == false){
								listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
								}
						}
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;
						
						if(cavidade.isPassante()){
						
							if(pVerificador.verificarValoresCavidade(cavidade)){throw new ProjetoInvalidoException("Erro na cavidade: " + cavidade.getIndice());}
							//else if(pVerificador.validateCavidadeComFeatures(cavidade, posic, arrayList)== false ){throw new ProjetoInvalidoException("Colisao com cavidade" + cavidade.getIndice());}
														
							else if(pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio, listaApoiosBase) == false ){
								listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);}
							
						}
					}
									
				}
				
				else { listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);				
					}
				
				
				posic++;
			}
			
			
			//return list;
			
		}
		
		else if (apoiosBase == 7){
		
			triangle = 0;
			apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
			apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
			apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
			apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
			apoioBase5 = pCalculator.calcularApoioBase(PONTO5);
			apoioBase6 = pCalculator.calcularApoioBase(PONTO6);
			apoioBase7 = pCalculator.calcularApoioBase(PONTO7);
			listaApoiosBase.add(apoioBase1);
			listaApoiosBase.add(apoioBase2);
			listaApoiosBase.add(apoioBase3);
			listaApoiosBase.add(apoioBase4);
			listaApoiosBase.add(apoioBase5);
			listaApoiosBase.add(apoioBase6);
			listaApoiosBase.add(apoioBase7);
			
			Iterator<Feature> iterator = arrayList.iterator();
			int posic = 0;
			
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				
				if ((feature.getClass() == FuroBasePlana.class) || (feature.getClass() == Cavidade.class) 
						|| (feature.getClass() == FuroConico.class)){
						
					int tipo = feature.getTipo();
					
					if (tipo == Feature.FURO) {
						Furo furo = (Furo) feature;
						if(furo.isPassante()){
						
							if (pVerificador.verificarValoresFuro(furo)){throw new ProjetoInvalidoException("Erro no furo: " + furo.getNome());}
						//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
							else if ((pVerificador.validateFuroComArrayBase(furo, raioApoio, listaApoiosBase)) == false){
							listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
							}
						}
						
					}
					
					if(feature.getClass() == FuroConico.class){
						FuroConico furoConico = (FuroConico) feature;
						
						if (furoConico.isPassante()){
							
							double xFuroAux = (furoConico.getPosicaoX() + (furoConico.getRaio() - furoConico.getRaio1()));
							double yFuroAux = (furoConico.getPosicaoY() + (furoConico.getRaio() - furoConico.getRaio1()));
							double diametroFuroAux = (furoConico.getRaio1()*2);
							
							Furo furoAux = new Furo("furoAux", xFuroAux,yFuroAux,0,diametroFuroAux,furoConico.getProfundidade());
							
								if (pVerificador.verificarValoresFuro(furoAux)){throw new ProjetoInvalidoException("Erro no furo: " + furoAux.getNome());}
								//else if ((pVerificador.validateFurocomFeatures(furo, posic, arrayList)) == false){throw new ProjetoInvalidoException("Colisao do furo: " + furo.getNome());}
								else if ((pVerificador.validateFuroComArrayBase(furoAux, raioApoio, listaApoiosBase)) == false){
								listaApoiosBase = criarBaseCorreta(nBase, listaApoiosBase);
							
								}
						}
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;
						
						if(cavidade.isPassante()){
						
							if(pVerificador.verificarValoresCavidade(cavidade)){throw new ProjetoInvalidoException("Erro na cavidade: " + cavidade.getIndice());}
							//else if(pVerificador.validateCavidadeComFeatures(cavidade, posic, arrayList)== false ){throw new ProjetoInvalidoException("Colisao com cavidade" + cavidade.getIndice());}
														
							else if(pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio, listaApoiosBase) == false ){
								listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);}
							
						}
					}
									
				}
				
				else { listaApoiosBase = criarBaseCorreta(nBase,listaApoiosBase);				
					}
				
				
				posic++;
			}
						
			//return list;
			
		}
		
		
		// Verifica o numero de pontos de apoio verticais e adiciona os pontos de acordo com a quantidade solicitada.
		// Verifica se existe colisao com os features, se isto ocorrer, nao e possivel fabricar a pe�a.
		if (apoiosVerticais == 1){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioVerticalFrontal1 = pCalculator.calcularApoioVerticalFrontal(PONTO6);
			apoioVerticalPosterior1 = pCalculator.calcularApoioVerticalPosterior(PONTO6);
			

			listAux.add(apoioVerticalFrontal1);
			listAux.add(apoioVerticalPosterior1);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio vertical:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio vertical:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			listaApoiosVerticais.add(apoioVerticalFrontal1);
			listaApoiosVerticais.add(apoioVerticalPosterior1);
		}
		
		else if (apoiosVerticais == 2){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioVerticalFrontal1 = pCalculator.calcular2ApoiosVerticalFrontal(PONTO1);
			apoioVerticalPosterior1 = pCalculator.calcular2ApoiosVerticalPosterior(PONTO1);
			
			apoioVerticalFrontal2 = pCalculator.calcular2ApoiosVerticalFrontal(PONTO2);
			apoioVerticalPosterior2 = pCalculator.calcular2ApoiosVerticalPosterior(PONTO2);
			
		
			listAux.add(apoioVerticalFrontal1);
			listAux.add(apoioVerticalPosterior1);
			
			listAux.add(apoioVerticalFrontal2);
			listAux.add(apoioVerticalPosterior2);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio vertical:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio vertical:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			
			listaApoiosVerticais.add(apoioVerticalFrontal1);
			listaApoiosVerticais.add(apoioVerticalPosterior1);
			
			listaApoiosVerticais.add(apoioVerticalFrontal2);
			listaApoiosVerticais.add(apoioVerticalPosterior2);
		
		}
		
		else if (apoiosVerticais == 3){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioVerticalFrontal1 = pCalculator.calcularApoioVerticalFrontal(PONTO1);
			apoioVerticalPosterior1 = pCalculator.calcularApoioVerticalPosterior(PONTO1);
			
			apoioVerticalFrontal2 = pCalculator.calcularApoioVerticalFrontal(PONTO2);
			apoioVerticalPosterior2 = pCalculator.calcularApoioVerticalPosterior(PONTO2);
			
			apoioVerticalFrontal3 = pCalculator.calcularApoioVerticalFrontal(PONTO3);
			apoioVerticalPosterior3 = pCalculator.calcularApoioVerticalPosterior(PONTO3);
			
			listAux.add(apoioVerticalFrontal1);
			listAux.add(apoioVerticalPosterior1);
			
			listAux.add(apoioVerticalFrontal2);
			listAux.add(apoioVerticalPosterior2);
			
			listAux.add(apoioVerticalFrontal3);
			listAux.add(apoioVerticalPosterior3);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio vertical:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio vertical:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
			}
			
			listaApoiosVerticais.add(apoioVerticalFrontal1);
			listaApoiosVerticais.add(apoioVerticalPosterior1);
			
			listaApoiosVerticais.add(apoioVerticalFrontal2);
			listaApoiosVerticais.add(apoioVerticalPosterior2);
			
			listaApoiosVerticais.add(apoioVerticalFrontal3);
			listaApoiosVerticais.add(apoioVerticalPosterior3);

			
		}
		
			else if (apoiosVerticais == 4){
				ArrayList<Point3d> listAux = new ArrayList<Point3d>();
				
				apoioVerticalFrontal1 = pCalculator.calcularApoioVerticalFrontal(PONTO2);
				apoioVerticalPosterior1 = pCalculator.calcularApoioVerticalPosterior(PONTO2);
				
				apoioVerticalFrontal2 = pCalculator.calcularApoioVerticalFrontal(PONTO3);
				apoioVerticalPosterior2 = pCalculator.calcularApoioVerticalPosterior(PONTO3);
				
				apoioVerticalFrontal3 = pCalculator.calcularApoioVerticalFrontal(PONTO4);
				apoioVerticalPosterior3 = pCalculator.calcularApoioVerticalPosterior(PONTO4);
				
				apoioVerticalFrontal4 = pCalculator.calcularApoioVerticalFrontal(PONTO5);
				apoioVerticalPosterior4 = pCalculator.calcularApoioVerticalPosterior(PONTO5);
				
				listAux.add(apoioVerticalFrontal1);
				listAux.add(apoioVerticalPosterior1);
				
				listAux.add(apoioVerticalFrontal2);
				listAux.add(apoioVerticalPosterior2);
				
				listAux.add(apoioVerticalFrontal3);
				listAux.add(apoioVerticalPosterior3);
				
				listAux.add(apoioVerticalFrontal4);
				listAux.add(apoioVerticalPosterior4);
				
				Iterator<Feature> iterator = arrayList.iterator();
				
				int posic = 0;
						
				while (iterator.hasNext()) {
					Feature feature = iterator.next();
									
					int tipo = feature.getTipo();
					if (tipo == Feature.FURO) {
						    Furo furo = (Furo) feature;
											
							if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
								throw new ProjetoInvalidoException("Furo colide com apoio vertical:");
								//listAux = criarBaseCorreta(nBase,list);
							}
						
					}
					
					if (tipo == Feature.CAVIDADE){
						Cavidade cavidade = (Cavidade) feature;
						    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
							throw new ProjetoInvalidoException("Cavidade colide com apoio vertical:");
							//listAux = criarBaseCorreta(nBase,list);}
						
					}
					
					posic++;
					
				}
				
				}
				
				listaApoiosVerticais.add(apoioVerticalFrontal1);
				listaApoiosVerticais.add(apoioVerticalPosterior1);
				
				listaApoiosVerticais.add(apoioVerticalFrontal2);
				listaApoiosVerticais.add(apoioVerticalPosterior2);
				
				listaApoiosVerticais.add(apoioVerticalFrontal3);
				listaApoiosVerticais.add(apoioVerticalPosterior3);
				
				listaApoiosVerticais.add(apoioVerticalFrontal4);
				listaApoiosVerticais.add(apoioVerticalPosterior4);


	
	
		}
		
		else if (apoiosVerticais == 5){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioVerticalFrontal1 = pCalculator.calcularApoioVerticalFrontal(PONTO2);
			apoioVerticalPosterior1 = pCalculator.calcularApoioVerticalPosterior(PONTO2);
			
			apoioVerticalFrontal2 = pCalculator.calcularApoioVerticalFrontal(PONTO3);
			apoioVerticalPosterior2 = pCalculator.calcularApoioVerticalPosterior(PONTO3);
			
			apoioVerticalFrontal3 = pCalculator.calcularApoioVerticalFrontal(PONTO4);
			apoioVerticalPosterior3 = pCalculator.calcularApoioVerticalPosterior(PONTO4);
			
			apoioVerticalFrontal4 = pCalculator.calcularApoioVerticalFrontal(PONTO5);
			apoioVerticalPosterior4 = pCalculator.calcularApoioVerticalPosterior(PONTO5);
			
			apoioVerticalFrontal5 = pCalculator.calcularApoioVerticalFrontal(PONTO6);
			apoioVerticalPosterior5 = pCalculator.calcularApoioVerticalPosterior(PONTO6);
			
			
			listAux.add(apoioVerticalFrontal1);
			listAux.add(apoioVerticalPosterior1);
			
			listAux.add(apoioVerticalFrontal2);
			listAux.add(apoioVerticalPosterior2);
			
			listAux.add(apoioVerticalFrontal3);
			listAux.add(apoioVerticalPosterior3);
			
			listAux.add(apoioVerticalFrontal4);
			listAux.add(apoioVerticalPosterior4);
			
			listAux.add(apoioVerticalFrontal5);
			listAux.add(apoioVerticalPosterior5);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio vertical:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio vertical:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
			}
			
			listaApoiosVerticais.add(apoioVerticalFrontal1);
			listaApoiosVerticais.add(apoioVerticalPosterior1);
			
			listaApoiosVerticais.add(apoioVerticalFrontal2);
			listaApoiosVerticais.add(apoioVerticalPosterior2);
			
			listaApoiosVerticais.add(apoioVerticalFrontal3);
			listaApoiosVerticais.add(apoioVerticalPosterior3);
			
			listaApoiosVerticais.add(apoioVerticalFrontal4);
			listaApoiosVerticais.add(apoioVerticalPosterior4);
			
			listaApoiosVerticais.add(apoioVerticalFrontal5);
			listaApoiosVerticais.add(apoioVerticalPosterior5);
			

		}
		
		
		// Verifica o numero de pontos de apoio horizontais e adiciona os pontos de acordo com a quantidade solicitada.
		// Verifica se existe colisao com os features, se isto ocorrer, nao e possivel fabricar a pe�a.
		
		if (apoiosHorizontais == 1){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioHorizontalEsquerdo1 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO6);
			apoioHorizontalDireito1 = pCalculator.calcularApoioHorizontalDireito(PONTO6);
			
			listAux.add(apoioHorizontalEsquerdo1);
			listAux.add(apoioHorizontalDireito1);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio horizontal:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio horizontal:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo1);
			listaApoiosHorizontais.add(apoioHorizontalDireito1);
			
			
		}
		
		else if (apoiosHorizontais == 2){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioHorizontalEsquerdo1 = pCalculator.calcular2ApoiosHorizontalEsquerdo(PONTO1);
			apoioHorizontalEsquerdo2 = pCalculator.calcular2ApoiosHorizontalEsquerdo(PONTO2);
			
			apoioHorizontalDireito1 = pCalculator.calcular2ApoiosHorizontalDireito(PONTO1);
			apoioHorizontalDireito2 = pCalculator.calcular2ApoiosHorizontalDireito(PONTO2);
			
						
			listAux.add(apoioHorizontalEsquerdo1);
			listAux.add(apoioHorizontalEsquerdo2);
			
			listAux.add(apoioHorizontalDireito1);
			listAux.add(apoioHorizontalDireito2);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio horizontal:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio horizontal:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo1);
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo2);
			
			listaApoiosHorizontais.add(apoioHorizontalDireito1);
			listaApoiosHorizontais.add(apoioHorizontalDireito2);
			
		
			
			
			
		}
		
		else if (apoiosHorizontais == 3){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioHorizontalEsquerdo1 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO1);
			apoioHorizontalDireito1 = pCalculator.calcularApoioHorizontalDireito(PONTO1);
			
			apoioHorizontalEsquerdo2 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO2);
			apoioHorizontalDireito2 = pCalculator.calcularApoioHorizontalDireito(PONTO2);
			
			apoioHorizontalEsquerdo3 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO3);
			apoioHorizontalDireito3 = pCalculator.calcularApoioHorizontalDireito(PONTO3);
			
	
			
			listAux.add(apoioHorizontalEsquerdo1);
			listAux.add(apoioHorizontalDireito1);
			
			listAux.add(apoioHorizontalEsquerdo2);
			listAux.add(apoioHorizontalDireito2);
			
			listAux.add(apoioHorizontalEsquerdo3);
			listAux.add(apoioHorizontalDireito3);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio horizontal:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio horizontal:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo1);
			listaApoiosHorizontais.add(apoioHorizontalDireito1);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo2);
			listaApoiosHorizontais.add(apoioHorizontalDireito2);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo3);
			listaApoiosHorizontais.add(apoioHorizontalDireito3);
			

			
		}
		
		else if (apoiosHorizontais == 4){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioHorizontalEsquerdo1 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO2);
			apoioHorizontalDireito1 = pCalculator.calcularApoioHorizontalDireito(PONTO2);
			
			apoioHorizontalEsquerdo2 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO3);
			apoioHorizontalDireito2 = pCalculator.calcularApoioHorizontalDireito(PONTO3);
			
			apoioHorizontalEsquerdo3 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO4);
			apoioHorizontalDireito3 = pCalculator.calcularApoioHorizontalDireito(PONTO4);
			
			apoioHorizontalEsquerdo4 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO5);
			apoioHorizontalDireito4 = pCalculator.calcularApoioHorizontalDireito(PONTO5);
			

			
			listAux.add(apoioHorizontalEsquerdo1);
			listAux.add(apoioHorizontalDireito1);
			
			listAux.add(apoioHorizontalEsquerdo2);
			listAux.add(apoioHorizontalDireito2);
			
			listAux.add(apoioHorizontalEsquerdo3);
			listAux.add(apoioHorizontalDireito3);
			
			listAux.add(apoioHorizontalEsquerdo4);
			listAux.add(apoioHorizontalDireito4);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio horizontal:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio horizontal:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo1);
			listaApoiosHorizontais.add(apoioHorizontalDireito1);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo2);
			listaApoiosHorizontais.add(apoioHorizontalDireito2);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo3);
			listaApoiosHorizontais.add(apoioHorizontalDireito3);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo4);
			listaApoiosHorizontais.add(apoioHorizontalDireito4);
			

			
			
		}
		
		else if (apoiosHorizontais == 5){
			ArrayList<Point3d> listAux = new ArrayList<Point3d>();
			
			apoioHorizontalEsquerdo1 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO2);
			apoioHorizontalDireito1 = pCalculator.calcularApoioHorizontalDireito(PONTO2);
			
			apoioHorizontalEsquerdo2 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO3);
			apoioHorizontalDireito2 = pCalculator.calcularApoioHorizontalDireito(PONTO3);
			
			apoioHorizontalEsquerdo3 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO4);
			apoioHorizontalDireito3 = pCalculator.calcularApoioHorizontalDireito(PONTO4);
			
			apoioHorizontalEsquerdo4 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO5);
			apoioHorizontalDireito4 = pCalculator.calcularApoioHorizontalDireito(PONTO5);
			
			apoioHorizontalEsquerdo5 = pCalculator.calcularApoioHorizontalEsquerdo(PONTO6);
			apoioHorizontalDireito5 = pCalculator.calcularApoioHorizontalDireito(PONTO6);

			
			listAux.add(apoioHorizontalEsquerdo1);
			listAux.add(apoioHorizontalDireito1);
			
			listAux.add(apoioHorizontalEsquerdo2);
			listAux.add(apoioHorizontalDireito2);
			
			listAux.add(apoioHorizontalEsquerdo3);
			listAux.add(apoioHorizontalDireito3);
			
			listAux.add(apoioHorizontalEsquerdo4);
			listAux.add(apoioHorizontalDireito4);
			
			listAux.add(apoioHorizontalEsquerdo5);
			listAux.add(apoioHorizontalDireito5);
			
			Iterator<Feature> iterator = arrayList.iterator();
			
			int posic = 0;
					
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
								
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					    Furo furo = (Furo) feature;
										
						if ((pVerificador.validateFuroComArrayLateral(furo, raioApoio, listAux)) == false){
							throw new ProjetoInvalidoException("Furo colide com apoio horizontal:");
							//listAux = criarBaseCorreta(nBase,list);
						}
					
				}
				
				if (tipo == Feature.CAVIDADE){
					Cavidade cavidade = (Cavidade) feature;
					    if(pVerificador.validateCavidadeComArrayLateral(cavidade, raioApoio, listAux) == false ){
						throw new ProjetoInvalidoException("Cavidade colide com apoio horizontal:");
						//listAux = criarBaseCorreta(nBase,list);}
					
				}
				
				posic++;
				
			}
			
								
			}
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo1);
			listaApoiosHorizontais.add(apoioHorizontalDireito1);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo2);
			listaApoiosHorizontais.add(apoioHorizontalDireito2);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo3);
			listaApoiosHorizontais.add(apoioHorizontalDireito3);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo4);
			listaApoiosHorizontais.add(apoioHorizontalDireito4);
			
			listaApoiosHorizontais.add(apoioHorizontalEsquerdo5);
			listaApoiosHorizontais.add(apoioHorizontalDireito5);
	
			
		}
		
		list.add(listaApoiosBase);
		list.add(listaApoiosVerticais);
		list.add(listaApoiosHorizontais);
		
		return list;
		
		//return null;
		
		
		
	}
	
	// Valida os pontos de Apoio da Base.
	
	private boolean validatePoints(int i, ArrayList<Point3d> listaPontos){
		boolean aux = true;
		
			Iterator<Feature> iterator = arrayList.iterator();
			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				int tipo = feature.getTipo();
				if (tipo == Feature.FURO) {
					Furo furo = (Furo) feature;
					if (pVerificador.validateFuroComArrayBase(furo, raioApoio,listaPontos) == false){aux = false;}
				}
				
				if (tipo == Feature.CAVIDADE) {
					Cavidade cavidade = (Cavidade) feature;
					if (pVerificador.validateCavidadeComArrayBase(cavidade, raioApoio,listaPontos) == false){aux = false;}
				}
			}
		
		
		return aux;
	}
	
	// Corrige os pontos de apoio para evitar colisoes com os features.
	// Tenta fazer um "resize" dos pontos de apoio e invertir eles, se isto nao da certo e
	// ja nao e possivel aumentar a distancia entre os pontos, tenta com outra quantidade de pontos.
	
	private ArrayList<Point3d> criarBaseCorreta(int i, ArrayList<Point3d> list ) throws ProjetoInvalidoException{
		nBase = i;
		
		ArrayList<Point3d> listaPontos = list;
		
		while (validatePoints(nBase, listaPontos) == false){
			
			if (nBase == 3){
				listaPontos = new ArrayList<Point3d>();
				apoioBase1 = pCalculator.calcularApoioBaseTriangular(PONTO1, invertN);
				apoioBase2 = pCalculator.calcularApoioBaseTriangular(PONTO2, invertN);
				apoioBase3 = pCalculator.calcularApoioBaseTriangular(PONTO3, invertN);
				listaPontos.add(apoioBase1);
				listaPontos.add(apoioBase2);
				listaPontos.add(apoioBase3);
				triangle = 1;
			}
			
			if (nBase == 4){
				listaPontos = new ArrayList<Point3d>();
				apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
				apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
				apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
				apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
				listaPontos.add(apoioBase1);
				listaPontos.add(apoioBase2);
				listaPontos.add(apoioBase3);
				listaPontos.add(apoioBase4);
				triangle = 0;
			}
			
			if (nBase == 5){
				listaPontos = new ArrayList<Point3d>();
				apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
				apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
				apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
				apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
				apoioBase5 = pCalculator.calcularApoioBase(PONTO5);
				listaPontos.add(apoioBase1);
				listaPontos.add(apoioBase2);
				listaPontos.add(apoioBase3);
				listaPontos.add(apoioBase4);
				listaPontos.add(apoioBase5);
				triangle = 0;
			}
			
			if (nBase == 6){
				listaPontos = new ArrayList<Point3d>();
				apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
				apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
				apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
				apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
				apoioBase6 = pCalculator.calcularApoioBase(PONTO6);
				apoioBase7 = pCalculator.calcularApoioBase(PONTO7);
				listaPontos.add(apoioBase1);
				listaPontos.add(apoioBase2);
				listaPontos.add(apoioBase3);
				listaPontos.add(apoioBase4);
				listaPontos.add(apoioBase6);
				listaPontos.add(apoioBase7);
				triangle = 0;
			}
			
			if (nBase == 7){
				listaPontos = new ArrayList<Point3d>();
				apoioBase1 = pCalculator.calcularApoioBase(PONTO1);
				apoioBase2 = pCalculator.calcularApoioBase(PONTO2);
				apoioBase3 = pCalculator.calcularApoioBase(PONTO3);
				apoioBase4 = pCalculator.calcularApoioBase(PONTO4);
				apoioBase5 = pCalculator.calcularApoioBase(PONTO5);
				apoioBase6 = pCalculator.calcularApoioBase(PONTO6);
				apoioBase7 = pCalculator.calcularApoioBase(PONTO7);
				listaPontos.add(apoioBase1);
				listaPontos.add(apoioBase2);
				listaPontos.add(apoioBase3);
				listaPontos.add(apoioBase4);
				listaPontos.add(apoioBase5);
				listaPontos.add(apoioBase6);
				listaPontos.add(apoioBase7);
				triangle = 0;
			}
			
			if (validatePoints(nBase, listaPontos) == false){correctApoiosBase();}
		}
		
		return listaPontos;
	}

	

	private void correctApoiosBase() throws ProjetoInvalidoException {
		//Se o int toDo for igual a 1, o proximo paso � inverter
		
		
		if(triangle == 0){
			toDo = 0;
		}
		
		if (toDo == 1){
			invertN = 0;
			toDo = 0;
			
		}
		
		//Se o int toDo for igual a o, o proximo paso � fazer um resize
		else {
			invertN = 1;
			resizePointsBase();
			toDo = 1;
			
		}
	}
  
	// Faz um "resize" dos pontos de apoio, aumentando ou diminuindo a distancia entre eles,
	// isto e feito modificando o divisor, que e utilizado para calcular as medidas dos pontos de apoio
	// de acordo com a pe�a fornecida.
	
	private void resizePointsBase() throws ProjetoInvalidoException {
		
		
		
		if (resizetoDo == 1 ){
			if (divisorBase <= 2){
				divisorBase = (divisorBase + 0.1);}
			
			else {resizetoDo = 2;}
			pCalculator = new PointsCalculator(rectangle3D, divisorBase, divisorVertical, divisorHorizontal);
			
		}
		
			
		else {
			if (divisorBase >= 1.25){
				divisorBase = (divisorBase - 0.1);}
			
			else {
				tryAnotherBase();
				}
			pCalculator = new PointsCalculator(rectangle3D, divisorBase, divisorVertical, divisorHorizontal);									
				
		}
	}

	public void tryAnotherBase() throws ProjetoInvalidoException{
		
	     if (tried4Base == 0){
			tried4Base = 1;
			divisorBase = divisorPadrao;
			resizetoDo = 1;
			nBase = 4;
		}
		
		 else if (tried3Base == 0){
			tried3Base = 1;
			divisorBase = divisorPadrao;
			resizetoDo = 1;
			nBase = 3;
			
		}
		
		
		else if (tried5Base ==0){
			tried5Base = 1;
			divisorBase = divisorPadrao;
			resizetoDo = 1;
			nBase = 5;
		}
		
		else if (tried6Base ==0){
			tried6Base = 1;
			divisorBase = divisorPadrao;
			resizetoDo = 1;
			nBase = 6;
		}
		
		else if (tried7Base ==0){
			tried7Base = 1;
			divisorBase = divisorPadrao;
			resizetoDo = 1;
			nBase = 7;
		}
		
		else{ 
			throw new ProjetoInvalidoException("Impossivel criar Peça");
			}
	}
	

		
}
