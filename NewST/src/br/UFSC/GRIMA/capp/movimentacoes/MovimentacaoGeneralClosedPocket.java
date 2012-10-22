package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraCavidade;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;

public class MovimentacaoGeneralClosedPocket {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private GeneralClosedPocket genClosed;
	private ArrayList<Boss> itsBoss;

	public MovimentacaoGeneralClosedPocket(Workingstep ws){
		this.ws = ws;
		this.genClosed = (GeneralClosedPocket) this.ws.getFeature();
		this.itsBoss = genClosed.getItsBoss();
		this.ferramenta = this.ws.getFerramenta();
	}


	public ArrayList<LinearPath> getDesbaste(){


		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		ArrayList<Point3d> pontosPeriferia;
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Point2d> coordenadas;
		ArrayList<Point2D> vertex = genClosed.getPoints();
		ArrayList<Point3d> pontosMenores;
		ArrayList<ArrayList<Point3d>> pontos;
		ArrayList<Double> menorDistancia;
		ArrayList<Point3d> maximos;		
		ArrayList<Shape> bossArray;
		ArrayList<Point3d> pontos2;
		ArrayList<Point2D> vertexPoint;
		ArrayList<Point3d> periferia;
		Point2D borda[];	
		LinearPath ligarPontos;
		Point3d pontoInicial = new Point3d(0,0,0), pontoFinal = null;
		GeneralPath general = new GeneralPath();
		Boss bossTmp;

		int numeroDePontosDaMalha = 150;
		double	allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom(),
				allowanceSide = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceSide(), 
				largura,
				comprimento,
				raio=this.genClosed.getRadius(),
				raioAtual,
				c,
				z=-ws.getCondicoesUsinagem().getAp(),
				diametroPrimeiroWs = this.genClosed.getWorkingsteps().get(0).getFerramenta().getDiametroFerramenta(),
				diametroFerramenta = this.ferramenta.getDiametroFerramenta(),
				ae = this.ws.getCondicoesUsinagem().getAe(),
				malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1],
				malha[][][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2],
				maiorMenorDistancia = 0,
				xMenor=1000,
				xMaior=0,
				yMenor=1000,
				yMaior=0;


		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), raio);
		general.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		for(int r=0;r<vertex.size();r++){
			general.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		general.closePath();

		//CRIAR MALHA DE PONTOS PARA COMPARA��O

		vertexPoint = genClosed.getPoints();
		for(int i=0;i<vertexPoint.size();i++){
			if(xMenor>vertexPoint.get(i).getX())
				xMenor=vertexPoint.get(i).getX();
			if(xMaior<vertexPoint.get(i).getX())
				xMaior=vertexPoint.get(i).getX();
			if(yMenor>vertexPoint.get(i).getY())
				yMenor=vertexPoint.get(i).getY();
			if(yMaior<vertexPoint.get(i).getY())
				yMaior=vertexPoint.get(i).getY();			
		}
		largura = yMaior-yMenor;
		comprimento = xMaior-xMenor;

		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = xMenor+comprimento*(i+1)/numeroDePontosDaMalha;//x
				malha[i][k][1] = yMenor+largura*(k+1)/numeroDePontosDaMalha;//y
			}
		}

		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		pontosPeriferia = new ArrayList<Point3d>();
		//		listaElipses = new ArrayList<Ellipse2D>();	
		for(int i=0;i<this.itsBoss.size();i++){
			bossTmp=this.itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2))+2*allowanceSide;
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
				borda = Cavidade.determinarPontosEmCircunferencia(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2*raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX()+allowanceSide, bossTmp.getPosicaoY()+allowanceSide, boss.getL1()-2*allowanceSide, boss.getL2()-2*allowanceSide, boss.getRadius()*2, boss.getRadius()*2));
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX()+allowanceSide,boss.getPosicaoY()+allowanceSide,z), boss.getL1()-2*allowanceSide, boss.getL2()-2*allowanceSide, boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(this.itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertexx = boss.getVertexPoints();
				vertexx = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertexx), boss.getRadius());		
				periferia = getPontosPeriferiaGeneral(vertexx, z, boss.getRadius());
				for(int q=0;q<periferia.size();q++){
					pontosPeriferia.add(periferia.get(q));
				}
				GeneralPath path = new GeneralPath();
				path.moveTo(vertexx.get(0).getX(), vertexx.get(0).getY());
				for(int r=0;r<vertexx.size();r++){
					path.lineTo(vertexx.get(r).getX(), vertexx.get(r).getY());
				}
				path.closePath();
				bossArray.add(path);
			}
		}
		
		periferia = getPontosPeriferiaGeneral(vertex, z, raio);
		for(int i=0;i<periferia.size();i++){
			pontosPeriferia.add(periferia.get(i));
		}

		int b=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS N�O USINADOS
		pontosPossiveis = new ArrayList<Point3d>();
		coordenadas = new ArrayList<Point2d>();
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						coordenadas.add(new Point2d(i,k));
					}
					b=0;
				}
			}
		}

		double distanciaTmp;
		int numeroDeCortes;

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

		maximos = new ArrayList<Point3d>();

		double diametroTmp=1000, raioMedia=0, raioMenor=10000;
		int contador=0, numeroDeDiametrosAdicionados=0;

		for(int i=1;i<malhaMenoresDistancias.length-1;i++){
			for(int k=1;k<malhaMenoresDistancias.length-1;k++){
				contador = 0;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]<=2)
					contador=0;

				if(contador>=6){
					if(raioMenor>malhaMenoresDistancias[i][k])
						raioMenor = malhaMenoresDistancias[i][k];
					maximos.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
					if(diametroTmp>malhaMenoresDistancias[i][k] && malhaMenoresDistancias[i][k]!=0){
						diametroTmp=malhaMenoresDistancias[i][k];
					}
				}
			}
		}

		raioMedia = diametroPrimeiroWs/2;

		for(int i=0;i<menorDistancia.size();i++){
			if(maiorMenorDistancia<menorDistancia.get(i)){
				maiorMenorDistancia=menorDistancia.get(i);
			}
		}

		//		numeroDeCortes = (int) (maiorMenorDistancia/ae)+10;
		//		if(diametroFerramenta!=diametroPrimeiroWs)
		numeroDeCortes = (int) (maiorMenorDistancia/(0.5*diametroPrimeiroWs));
		double variacao = (comprimento/numeroDePontosDaMalha+largura/numeroDePontosDaMalha)/2;


		pontos = new ArrayList<ArrayList<Point3d>>();
		for(int i=0;i<numeroDeCortes;i++){
			pontos2 = new ArrayList<Point3d>();
			for(int k=0;k<menorDistancia.size();k++){
				if(i==0){
					if(menorDistancia.get(k)<=diametroPrimeiroWs/2+variacao && menorDistancia.get(k)>=diametroPrimeiroWs/2){
						pontos2.add(pontosPossiveis.get(k));
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
				}
				else{
					if(menorDistancia.get(k)<=(i+1)*diametroPrimeiroWs/2+variacao/2 && menorDistancia.get(k)>=(i+1)*diametroPrimeiroWs/2-variacao/2){
						pontos2.add(pontosPossiveis.get(k));
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
				}
			}
			pontos.add(pontos2);
		}

		for(int i=0;i<pontos.size();i++){
			System.out.println("Pontos :  "+pontos.get(i).size());
		}

		if(diametroFerramenta!=diametroPrimeiroWs){
			ArrayList<Point3d> copiaPontos = null;
			pontos = new ArrayList<ArrayList<Point3d>>();
			copiaPontos = pontosPossiveis;
			pontosMenores = new ArrayList<Point3d>();		
			pontosPossiveis = new ArrayList<Point3d>();
			coordenadas = new ArrayList<Point2d>();
			for(int i=0;i<malha.length;i++){
				for(int k=0;k<malha[i].length;k++){
					if(general.contains(malha[i][k][0], malha[i][k][1])){
						for(int g=0;g<bossArray.size();g++){
							if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
								b++;
							}
						}
						if(b==bossArray.size()){
							pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
							coordenadas.add(new Point2d(i,k));
						}
						b=0;
					}
				}
			}
			
			ArrayList<Double> copiaMenorDistancia = menorDistancia;
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

			numeroDeCortes = (int) (maiorMenorDistancia/(ae/1.3));
			for(int i=0;i<numeroDeCortes;i++){
				for(int k=0;k<pontosPossiveis.size();k++){
					if(i==0){
						if(menorDistancia.get(k)<=diametroFerramenta/2+variacao && menorDistancia.get(k)>=diametroFerramenta/2){
							pontosMenores.add(pontosPossiveis.get(k));
						}
					}
					else{
						if(menorDistancia.get(k)<=(i+1)*0.5*diametroFerramenta+variacao/2 && menorDistancia.get(k)>=(i+1)*0.5*diametroFerramenta-variacao/2){
							pontosMenores.add(pontosPossiveis.get(k));
						}
					}
				}
				pontos.add(pontosMenores);
			}



			for(int k=0;k<copiaPontos.size();k++){
				if(copiaMenorDistancia.get(k)<=diametroFerramenta/2+variacao && copiaMenorDistancia.get(k)>=diametroFerramenta/2){
					pontosMenores.add(copiaPontos.get(k));
				}
			}
			pontos.add(pontosMenores);

			for(int i=0;i<pontos.size();i++){
				System.out.println("Pontos menores:  "+pontos.get(i).size());
			}

		}
		pontoInicial = new Point3d(pontos.get(0).get(0).getX(),pontos.get(0).get(0).getY(), this.ws.getOperation().getRetractPlane());

		
		
		double tmp, distancia;
		int t=0;

		//		for(int i=0;i<pontos.size();i++){
		//			pontoFinal = pontos.get(i).get(0);
		//			ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//			desbaste.add(ligarPontos);
		//			for(int j=0;j<pontos.get(i).size();j++){
		//				tmp = 100;
		//				for(int k=0;k<pontos.get(i).size();k++){
		//					distancia = OperationsVector.distanceVector(pontoFinal, pontos.get(i).get(k));
		//					if(distancia<tmp){
		//						tmp = distancia;
		//						t=k;
		//					}
		//				}
		//				if(tmp>5 && j!=(pontos.get(i).size()-1)){
		//					pontoInicial = pontoFinal;
		//					pontoFinal = new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane());
		//					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//					ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
		//					desbaste.add(ligarPontos);
		//					pontoInicial = pontoFinal;
		//					pontoFinal = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),this.ws.getOperation().getRetractPlane());
		//					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//					desbaste.add(ligarPontos);
		//					pontoInicial = pontoFinal;
		//					pontoFinal = new Point3d(pontos.get(i).get(t));
		//					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//					ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
		//					desbaste.add(ligarPontos);
		//				}
		//				else{
		//					pontoInicial = pontoFinal;
		//					pontoFinal = pontos.get(i).get(t);
		//					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//					ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
		//					desbaste.add(ligarPontos);
		//				}
		//			}
		//			
		//			pontoInicial = pontoFinal;
		//			pontoFinal = new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane());
		//			ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//			ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
		//			desbaste.add(ligarPontos);
		//			
		//			if(i!=pontos.size()-1){
		//				pontoInicial = pontoFinal;
		//				pontoFinal = new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),this.ws.getOperation().getRetractPlane());
		//				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		//				ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
		//				desbaste.add(ligarPontos);
		//			}
		//		}


		ArrayList<Point3d> temp;
		double m, menor, distanciaTemp;
		int h=0;

		for(int i=0;i<pontos.size();i++){
			if(pontos.get(i).size()==0 || pontos.get(i).size()==1){
				continue;
			}

			if(i==0 && h==0){
				pontoInicial = new Point3d(pontos.get(i).get(0).getX(),pontos.get(i).get(0).getY(),z);
				h=1;
			}
			if(i==0 && h!=0){
				pontoFinal = new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane());
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;

				pontoFinal = new Point3d(pontos.get(i).get(0).getX(),pontos.get(i).get(0).getY(),this.ws.getOperation().getRetractPlane());
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;

				pontoFinal = new Point3d(pontos.get(i).get(0));
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;
			}

			//			distanciaTemp=OperationsVector.distanceVector(pontos.get(i).get(0),pontos.get(i).get(1));
			temp = new ArrayList<Point3d>();
			temp.add(pontos.get(i).get(0));
			m = pontos.get(i).size();
			for(int k=0;k<m;k++){
				menor=100;
				t=0;
				for(int j=0;j<pontos.get(i).size();j++){
					if(temp.get(k)!=pontos.get(i).get(j)){
						distanciaTemp=OperationsVector.distanceVector(temp.get(k), pontos.get(i).get(j));

						if(distanciaTemp<menor){
							menor=distanciaTemp;							
							t=j;
						}
					}
				}
				if(menor>5){
					pontoFinal = new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane());
					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
					ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
					desbaste.add(ligarPontos);
					pontoInicial = pontoFinal;

					pontoFinal = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),this.ws.getOperation().getRetractPlane());
					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
					desbaste.add(ligarPontos);
					pontoInicial = pontoFinal;

					pontoFinal = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z);
					ligarPontos = new LinearPath(pontoInicial, pontoFinal);
					ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
					desbaste.add(ligarPontos);
					pontoInicial = pontoFinal;
				}else{
					pontoFinal = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z);
					ligarPontos = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(ligarPontos);
					pontoInicial = pontoFinal;
				}
				temp.add(pontos.get(i).get(t));
				pontos.get(i).remove(t);
				if(pontos.get(i).size()==0 || pontos.get(i).size()==1){
					continue;
				}
			}
			if(i+1!=pontos.size() &&
					pontos.get(i+1).size()!=0){
				pontoFinal = new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane());
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;

				pontoFinal = new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),this.ws.getOperation().getRetractPlane());
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;

				pontoFinal = new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),z);
				ligarPontos = new LinearPath(pontoInicial, pontoFinal);
				ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
				desbaste.add(ligarPontos);
				pontoInicial = pontoFinal;
			}
		}

		pontoFinal = new Point3d(pontoInicial.getX(), pontoInicial.getY(), this.ws.getOperation().getRetractPlane());
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
		desbaste.add(ligarPontos);
		pontoInicial = pontoFinal;

		return desbaste;

	}

//	public ArrayList<LinearPath> getDesbasteTest(){
//
//		ArrayList<LinearPath> desbasteTest;
//		double [][]malhaMenoresDistancias;
//		double z=0,	maiorMenorDistancia=0, numeroDeCortes, numeroDeAps;
//		double ae=this.ws.getCondicoesUsinagem().getAe();
//		double ap=this.ws.getCondicoesUsinagem().getAp();
//		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
//		ArrayList<Point3d> pontosPossiveis;
//		ArrayList<Double> menorDistancia;
//		desbasteTest = new ArrayList<LinearPath>();
//		numeroDeAps = this.genClosed.getProfundidade()/ap;
//		Point3d pontoInicial;
//		Point3d pontoFinal = null;
//		ArrayList<Shape> bossArray;
//
//		for(int p=0;p<numeroDeAps;p++){
//			z+=ap;
//
//			pontosPossiveis = MapeadoraGeneralClosedPocket.getPontosPossiveis(z, this.genClosed);
//			malhaMenoresDistancias = MapeadoraGeneralClosedPocket.getMalhaMenoresDistancias(z, this.genClosed);
//			menorDistancia = new ArrayList<Double>();
//			for(int i=0;i<malhaMenoresDistancias.length;i++){
//				for(int k=0;k<malhaMenoresDistancias[i].length;k++){
//					menorDistancia.add(malhaMenoresDistancias[i][k]);
//					if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
//						maiorMenorDistancia=malhaMenoresDistancias[i][k];
//					}
//				}
//			}
//
//			numeroDeCortes = (int) (maiorMenorDistancia/ae);
//
//			for(int i=0;i<numeroDeCortes;i++){
//				for(int k=0;k<menorDistancia.size();k++){
//					if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
//						pontoInicial = new Point3d(pontosPossiveis.get(k));
//						if(i!=0 && p!=0){
//							desbasteTest.add(new LinearPath(pontoInicial, pontoFinal));
//						}
//						pontoFinal = pontoInicial;
//					}					
//				}
//			}		
//
//			/*
//			 * COMO FAZER PARA ADICIONAR OS BOSSES ?
//			 * CHAMAR OS PONTOS POSSIVEIS DO ANTERIOR 
//			 * USAR ESSE DESBASTEtEST DE CIMA PARA ADICIONAR OS BOSS
//			 * CRIAR NOVOS PONTOSPOSSIVEIS/COORDENADAS/MENOREDISTANCIA
//			 * USAR ESSE DESBASTEtEST DE CIMA
//			 */
//		}
//
//		return desbasteTest;
//	}

//	public ArrayList<LinearPath> acabamentoTest(){
//		ArrayList<LinearPath> acabamentoTest = new ArrayList<LinearPath>();
//
//		double [][]malhaMenoresDistancias;
//		double z=0,	maiorMenorDistancia=0, numeroDeAps;
//		double ae=this.ws.getCondicoesUsinagem().getAe();
//		double ap=this.ws.getCondicoesUsinagem().getAp();
//		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
//		ArrayList<Point3d> pontosPossiveis;
//		ArrayList<Double> menorDistancia;
//		numeroDeAps = this.genClosed.getProfundidade()/ap;
//		Point3d pontoInicial;
//		Point3d pontoFinal = null;
//		ArrayList<Shape> bossArray;
//
//		for(int p=0;p<numeroDeAps;p++){
//			z+=ap;
//
//			pontosPossiveis = MapeadoraGeneralClosedPocket.getPontosPossiveis(z, this.genClosed);
//			malhaMenoresDistancias = MapeadoraGeneralClosedPocket.getMalhaMenoresDistancias(z, this.genClosed);
//			menorDistancia = new ArrayList<Double>();
//			for(int i=0;i<malhaMenoresDistancias.length;i++){
//				for(int k=0;k<malhaMenoresDistancias[i].length;k++){
//					menorDistancia.add(malhaMenoresDistancias[i][k]);
//					if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
//						maiorMenorDistancia=malhaMenoresDistancias[i][k];
//					}
//				}
//			}
//
//			for(int i=0;i<1;i++){
//				for(int k=0;k<menorDistancia.size();k++){
//					if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
//						pontoInicial = new Point3d(pontosPossiveis.get(k));
//						if(i!=0 && p!=0){
//							acabamentoTest.add(new LinearPath(pontoInicial, pontoFinal));
//						}
//						pontoFinal = pontoInicial;
//					}					
//				}
//			}	
//		}
//
//		return acabamentoTest;
//	}

	private static ArrayList<Point3d> getPontosPeriferiaGeneral(ArrayList<Point2D> vertex, double z, double raio){

		GeneralPath path = new GeneralPath();
		path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		ArrayList<Shape> bossArray = new ArrayList<Shape>();
		ArrayList<Point3d> pontosPeriferia = new ArrayList<Point3d>();

		for(int r=0;r<vertex.size();r++){
			path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		path.closePath();
		bossArray.add(path);
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
					if(maiorY == vertex.get(j).getY()){
						distancia-=1;
						if(distancia<=vertex.get(q).getY()){
							h=1000;
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
						}
					}
					else{
						distancia+=1;
						if(distancia>=vertex.get(q).getY()){
							h=1000;
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
						}
					}
				}
			}
			else if(vertex.get(j).getY()==vertex.get(q).getY()){
				distancia = vertex.get(j).getX();
				for(int h=0;h<1000;h++){
					pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
					if(maiorX == vertex.get(j).getX()){
						distancia-=1;
						if(distancia<=vertex.get(q).getX()){
							h=1000;
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
						}
					}
					else{
						distancia+=1;
						if(distancia>=vertex.get(q).getX()){
							h=1000;
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
						}
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
		return pontosPeriferia;
	}

}