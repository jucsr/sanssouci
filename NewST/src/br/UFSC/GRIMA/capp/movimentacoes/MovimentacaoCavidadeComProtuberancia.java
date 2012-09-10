package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraCavidade;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;

public class MovimentacaoCavidadeComProtuberancia {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private Cavidade cavidade;
	private ArrayList<Boss> itsBoss;

	public MovimentacaoCavidadeComProtuberancia(Workingstep ws){
		this.cavidade = (Cavidade) this.ws.getFeature();
		this.itsBoss = cavidade.getItsBoss();
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
	}

	public ArrayList<LinearPath> getDesbasteTest(){

		ArrayList<LinearPath> desbasteTest;
		double [][]malhaMenoresDistancias;
		double z=0,	maiorMenorDistancia=0, numeroDeCortes, numeroDeAps;
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double diametroPrimeiroWs = this.cavidade.getWorkingsteps().get(0).getFerramenta().getDiametroFerramenta();
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Double> menorDistancia;
		desbasteTest = new ArrayList<LinearPath>();
		numeroDeAps = this.cavidade.getProfundidade()/ap;
		Point3d pontoInicial;
		Point3d pontoFinal = null;
		ArrayList<Shape> bossArray = getBossArray(z, cavidade);;

		for(int p=0;p<numeroDeAps;p++){
			z+=ap;

			pontosPossiveis = getPontosPossiveis(z, this.cavidade, getBossArray(z, cavidade));
			malhaMenoresDistancias = getMalhaMenoresDistancias(z, this.cavidade);
			menorDistancia = new ArrayList<Double>();
			for(int i=0;i<malhaMenoresDistancias.length;i++){
				for(int k=0;k<malhaMenoresDistancias[i].length;k++){
					menorDistancia.add(malhaMenoresDistancias[i][k]);
					if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
						maiorMenorDistancia=malhaMenoresDistancias[i][k];
					}
				}
			}

			numeroDeCortes = (int) (maiorMenorDistancia/ae);

			for(int i=0;i<numeroDeCortes;i++){
				for(int k=0;k<menorDistancia.size();k++){
					if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
						if(diametroFerramenta!=diametroPrimeiroWs){
							bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-diametroPrimeiroWs, pontosPossiveis.get(k).getY()-diametroPrimeiroWs, diametroPrimeiroWs*2, 2*diametroPrimeiroWs));
						}
						else{
							pontoInicial = new Point3d(pontosPossiveis.get(k));
							if(i!=0 && p!=0){
								desbasteTest.add(new LinearPath(pontoInicial, pontoFinal));
							}
							pontoFinal = pontoInicial;
						}
					}					
				}
			}

			if(diametroFerramenta!=diametroPrimeiroWs){
				pontosPossiveis = getPontosPossiveis(z, this.cavidade, bossArray);
				malhaMenoresDistancias = getMalhaMenoresDistancias(z, this.cavidade);
				menorDistancia = new ArrayList<Double>();
				for(int i=0;i<malhaMenoresDistancias.length;i++){
					for(int k=0;k<malhaMenoresDistancias[i].length;k++){
						menorDistancia.add(malhaMenoresDistancias[i][k]);
						if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
							maiorMenorDistancia=malhaMenoresDistancias[i][k];
						}
					}
				}

				numeroDeCortes = (int) (maiorMenorDistancia/ae);

				for(int i=0;i<numeroDeCortes;i++){
					for(int k=0;k<menorDistancia.size();k++){
						if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
							pontoInicial = new Point3d(pontosPossiveis.get(k));
							if(i!=0 && p!=0){
								desbasteTest.add(new LinearPath(pontoInicial, pontoFinal));
							}
							pontoFinal = pontoInicial;
						}					
					}
				}
			}

			/*
			 * COMO FAZER PARA ADICIONAR OS BOSSES ?
			 * CHAMAR OS PONTOS POSSIVEIS DO ANTERIOR 
			 * USAR ESSE DESBASTEtEST DE CIMA PARA ADICIONAR OS BOSS
			 * CRIAR NOVOS PONTOSPOSSIVEIS/COORDENADAS/MENOREDISTANCIA
			 * USAR ESSE DESBASTEtEST DE CIMA
			 */
		}

		return desbasteTest;
	}

	public ArrayList<LinearPath> acabamentoTest(){
		ArrayList<LinearPath> acabamentoTest = new ArrayList<LinearPath>();

		double [][]malhaMenoresDistancias;
		double z=0,	maiorMenorDistancia=0, numeroDeAps;
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Double> menorDistancia;
		numeroDeAps = this.cavidade.getProfundidade()/ap;
		Point3d pontoInicial;
		Point3d pontoFinal = null;
		ArrayList<Shape> bossArray;

		for(int p=0;p<numeroDeAps;p++){
			z+=ap;

			pontosPossiveis = getPontosPossiveis(z, this.cavidade, getBossArray(z, cavidade));
			malhaMenoresDistancias = getMalhaMenoresDistancias(z, this.cavidade);
			menorDistancia = new ArrayList<Double>();
			for(int i=0;i<malhaMenoresDistancias.length;i++){
				for(int k=0;k<malhaMenoresDistancias[i].length;k++){
					menorDistancia.add(malhaMenoresDistancias[i][k]);
					if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
						maiorMenorDistancia=malhaMenoresDistancias[i][k];
					}
				}
			}

			for(int i=0;i<1;i++){
				for(int k=0;k<menorDistancia.size();k++){
					if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
						pontoInicial = new Point3d(pontosPossiveis.get(k));
						if(i!=0 && p!=0){
							acabamentoTest.add(new LinearPath(pontoInicial, pontoFinal));
						}
						pontoFinal = pontoInicial;
					}					
				}
			}	
		}

		return acabamentoTest;
	}

	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();

		double largura=this.cavidade.getLargura(),
				comprimento=this.cavidade.getComprimento(),
				raio=this.cavidade.getRaio(),
				raioAtual,
				z=-10,
				diametroFerramenta = 10;//this.ferramenta.getDiametroFerramenta();
		double malha[][][] = new double[99][99][2];
		ArrayList<Point3d> pontosPossiveis;
		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(this.cavidade.getPosicaoX(), this.cavidade.getPosicaoY(), comprimento, largura, raio, raio);

		ArrayList<Shape> bossArray;
		//		ArrayList<Ellipse2D> listaElipses;
		ArrayList<Point3d> pontosPeriferia;
		ArrayList<Double> menorDistancia;
		ArrayList<ArrayList<Point3d>> pontos;
		ArrayList<Point3d> pontos2;
		Point2D borda[];


		//CRIAR MALHA DE PONTOS PARA COMPARAÇÃO
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = comprimento*(i+1)/100;//x
				malha[i][k][1] = largura*(k+1)/100;//y
			}
		}


		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		pontosPeriferia = new ArrayList<Point3d>();
		Boss bossTmp;
		//		listaElipses = new ArrayList<Ellipse2D>();		
		for(int i=0;i<this.itsBoss.size();i++){
			bossTmp=this.itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=(boss.getDiametro2()-boss.getDiametro1()*(-z-this.cavidade.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				bossArray.add(new Ellipse2D.Double(bossTmp.getPosicaoX()-raioAtual, bossTmp.getPosicaoY()-raioAtual, raioAtual*2, raioAtual*2));
				borda = Cavidade.determinarPontosEmCircunferencia(boss.getCentre(), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2/raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX(), bossTmp.getPosicaoY(), boss.getL1(), boss.getL1(), boss.getRadius(), boss.getRadius()));
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), boss.getL1(), boss.getL2(), boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			//			else if(this.itsBoss.get(i).getClass()==GeneralBoss.class){
			//				
			//			}

		}

		borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(this.cavidade.getPosicaoX(),this.cavidade.getPosicaoY(),z), this.cavidade.getComprimento(), this.cavidade.getLargura(), this.cavidade.getRaio());
		for(int k=0;k<borda.length;k++){
			pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
		}

		int b=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS NÃO USINADOS
		pontosPossiveis = new ArrayList<Point3d>();
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size())
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
				}
			}
		}

		if(pontosPossiveis.size()<1){
			//	break;
		}
		double distanciaTmp;
		double maiorMenorDistancia=0;
		int numeroDeCortes;

		menorDistancia = new ArrayList<Double>();
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			menorDistancia.add(distanciaTmp);
		}

		for(int i=0;i<menorDistancia.size();i++){
			if(maiorMenorDistancia<menorDistancia.get(i)){
				maiorMenorDistancia=menorDistancia.get(i);
			}
		}

		numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametroFerramenta));

		pontos = new ArrayList<ArrayList<Point3d>>();
		for(int i=0;i<numeroDeCortes;i++){
			pontos2 = new ArrayList<Point3d>();
			for(int k=0;k<menorDistancia.size();k++){
				if(menorDistancia.get(k)<=(i+1)*(0.75*diametroFerramenta) && menorDistancia.get(k)>=(i+1)*(0.75*diametroFerramenta)-0.5){
					pontos2.add(pontosPossiveis.get(k));
				}
			}
			pontos.add(pontos2);
		}

		//ELIMINAR OS PONTOS QUE NÃO PODEM SER USINADOS DA MALHA

		//CRIAR O ARRAY DAS MENORES DISTANCIAS

		//ADICIONAR NA SAIDA.


		return desbaste;
	}

	private static double[][][] getMalha(Cavidade cavidadeTmp){
		double malha[][][] = new double [99][99][2];
		double largura=cavidadeTmp.getLargura(), comprimento=cavidadeTmp.getComprimento();

		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = cavidadeTmp.getPosicaoX()+comprimento*(i+1)/100;//x
				malha[i][k][1] = cavidadeTmp.getPosicaoY()+largura*(k+1)/100;//y
			}
		}

		return malha;
	}


	private static ArrayList<Point3d> getPontosPossiveis(double z,Cavidade cavidadeTmp, ArrayList<Shape> bossArray){

		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(cavidadeTmp.getPosicaoX(), cavidadeTmp.getPosicaoY(), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), 2*cavidadeTmp.getRaio(), 2*cavidadeTmp.getRaio());
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
		double[][][] malha = getMalha(cavidadeTmp);
		int b=0;
		//		ArrayList<Shape> bossArray = getBossArray(z, cavidadeTmp); 

		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						//						System.out.println(k);
					}
					b=0;
				}
			}
		}

		return pontosPossiveis;
	}

	private static ArrayList<Point2d> getCoordenadas(double z, Cavidade cavidadeTmp, ArrayList<Shape> bossArray){


		ArrayList<Point2d> coordenadas = new ArrayList<Point2d>();
		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(cavidadeTmp.getPosicaoX(), cavidadeTmp.getPosicaoY(), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), 2*cavidadeTmp.getRaio(), 2*cavidadeTmp.getRaio());
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
		double[][][] malha = getMalha(cavidadeTmp);
		int b=0;
//		ArrayList<Shape> bossArray = getBossArray(z, cavidadeTmp); 

		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						coordenadas.add(new Point2d(i,k));
						//						System.out.println(k);
					}
					b=0;
				}
			}
		}

		return coordenadas;
	}

	private static ArrayList<Shape> getBossArray(double z, Cavidade cavidadeTmp){

		ArrayList<Boss> itsBoss;
		itsBoss = cavidadeTmp.getItsBoss();
		double raioAtual;
		ArrayList<Shape> bossArray;

		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		Boss bossTmp;


		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-cavidadeTmp.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX(), bossTmp.getPosicaoY(), boss.getL1(), boss.getL2(), boss.getRadius()*2, boss.getRadius()*2));
			}
			else if(itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				GeneralPath path = new GeneralPath();
				path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
				for(int r=0;r<vertex.size();r++){
					path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
				}
				path.closePath();
				bossArray.add(path);
			}
		}

		return bossArray;
	}

	private static ArrayList<Point3d> getPontosPeriferia(double z,Cavidade cavidadeTmp){

		ArrayList<Point3d> pontosPeriferia;ArrayList<Boss> itsBoss;
		itsBoss = cavidadeTmp.getItsBoss();
		double raioAtual;
		Point2D borda[];

		pontosPeriferia = new ArrayList<Point3d>();
		Boss bossTmp;
		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-cavidadeTmp.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				borda = Cavidade.determinarPontosEmCircunferencia(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2*raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), boss.getL1(), boss.getL2(), boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				double distancia, maiorX, maiorY;
				int q;
				for(int j=0;j<vertex.size();j++){
					if(j==vertex.size()-1)
						q=0;
					else
						q=j+1;

					if(vertex.get(j).getX()>vertex.get(q).getX())
						maiorX = vertex.get(j).getX();
					else
						maiorX = vertex.get(q).getX();

					if(vertex.get(j).getY()>vertex.get(q).getY())
						maiorY = vertex.get(j).getY();
					else
						maiorY = vertex.get(q).getY();


					if(vertex.get(j).getX()==vertex.get(q).getX()){
						distancia = vertex.get(j).getY();
						for(int h=0;h<1000;h++){
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
							if(maiorY == vertex.get(j).getY())
								distancia-=1;
							else
								distancia+=1;
							if(distancia==vertex.get(q).getY()){
								h=1000;
								pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
							}
						}
					}
					else if(vertex.get(j).getY()==vertex.get(q).getY()){
						distancia = vertex.get(j).getX();
						for(int h=0;h<1000;h++){
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
							if(maiorX == vertex.get(j).getX())
								distancia-=1;
							else
								distancia+=1;
							if(distancia==vertex.get(q).getX()){
								h=1000;
								pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
							}
						}
					}
					else{
						double a,b;
						a= (vertex.get(q).getY()-vertex.get(j).getY())/(vertex.get(q).getX()-vertex.get(j).getX());
						b= vertex.get(j).getY()-a*vertex.get(j).getX();

						if(Math.abs(vertex.get(j).getX()-vertex.get(q).getX())>Math.abs(vertex.get(j).getY()-vertex.get(q).getY())){
							distancia = vertex.get(j).getX();
							for(int h=0;h<1000;h++){
								pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
								if(maiorX == vertex.get(j).getX()){
									distancia-=1;
									if(distancia<=vertex.get(q).getX()){
										h=1000;
										pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
									}
								}
								else{
									distancia+=1;
									if(distancia>=vertex.get(q).getX()){
										h=1000;
										pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
									}
								}
							}	
						}
						else{
							distancia = vertex.get(j).getY();
							for(int h=0;h<1000;h++){
								pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
								if(maiorY == vertex.get(j).getY()){
									distancia-=1;
									if(distancia<=vertex.get(q).getY()){
										h=1000;
										pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
									}
								}
								else{
									distancia+=1;
									if(distancia>=vertex.get(q).getY()){
										h=1000;
										pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
									}
								}
							}	
						}
					}
				}		
			}
		}

		borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(cavidadeTmp.getPosicaoX(),cavidadeTmp.getPosicaoY(),z), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), cavidadeTmp.getRaio());
		for(int k=0;k<borda.length;k++){
			pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
		}

		return pontosPeriferia;
	}

	private static double[][] getMalhaMenoresDistancias(double z, Cavidade cavidadeTmp){

		ArrayList<Point3d> pontosPeriferia = getPontosPeriferia(z, cavidadeTmp);
		double malhaMenoresDistancias[][] = new double[99][99];
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, cavidadeTmp, getBossArray(z, cavidadeTmp));
		ArrayList<Point2d> coordenadas = getCoordenadas(z, cavidadeTmp, getBossArray(z, cavidadeTmp));
		ArrayList<Double> menorDistancia;		
		double distanciaTmp;

		menorDistancia = new ArrayList<Double>();
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);		
		}
		return malhaMenoresDistancias;
	}

}
