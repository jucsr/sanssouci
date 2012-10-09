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
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
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
		this.ws = ws;
		this.cavidade = (Cavidade) this.ws.getFeature();
		this.itsBoss = cavidade.getItsBoss();
		this.ferramenta = this.ws.getFerramenta();
	}

	public ArrayList<LinearPath> getDesbasteTest(){
		
		int numeroDePontosDaMalha = 150;
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		double malha[][][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2];
		ArrayList<Point3d> pontosPeriferia;
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Point2d> coordenadas;
		ArrayList<Point3d> pontosMenores;
		ArrayList<ArrayList<Point3d>> pontos;
		double maiorMenorDistancia = 0;
		ArrayList<Double> menorDistancia;
		ArrayList<Point3d> maximos;
		LinearPath ligarPontos;
		Point3d pontoInicial = new Point3d(0,0,0), pontoFinal = null;
		
		
		double	allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom(),
				allowanceSide = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceSide(), 
				largura=this.cavidade.getLargura()-2*allowanceSide,
				comprimento=this.cavidade.getComprimento()-2*allowanceSide,
				raio=this.cavidade.getRaio(),
				raioAtual,
				c,
				z=-2,
				diametroPrimeiroWs = this.cavidade.getWorkingsteps().get(0).getFerramenta().getDiametroFerramenta(),
				diametroFerramenta = this.ferramenta.getDiametroFerramenta(),
				ae = this.ws.getCondicoesUsinagem().getAe();
		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(this.cavidade.getPosicaoX()+allowanceSide, this.cavidade.getPosicaoY()+allowanceSide, comprimento, largura, 2*raio, 2*raio);

		double malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1];
		
		ArrayList<Shape> bossArray;
		//		ArrayList<Ellipse2D> listaElipses;
		ArrayList<Point3d> pontos2;
		Point2D borda[];


		//CRIAR MALHA DE PONTOS PARA COMPARAï¿½ï¿½O
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = this.cavidade.getPosicaoX()+comprimento*(i+1)/numeroDePontosDaMalha;//x
				malha[i][k][1] = this.cavidade.getPosicaoY()+largura*(k+1)/numeroDePontosDaMalha;//y
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
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.cavidade.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2))+2*allowanceSide;
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
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				GeneralPath path = new GeneralPath();
				GeneralPath generalPath = new GeneralPath();
				path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
				for(int r=0;r<vertex.size();r++){
					path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
				}
				path.closePath();
				int cont = vertex.size();
				ArrayList<Point2D> vertexTmp = new ArrayList<Point2D>();
				for(int r=0;r<cont;r++){
					if(path.contains(vertex.get(r).getX()+allowanceSide,vertex.get(r).getY())){
						vertexTmp.add(new Point2D.Double(vertex.get(r).getX()-allowanceSide, vertex.get(r).getY()));
						generalPath.lineTo(vertex.get(r).getX()-allowanceSide,vertex.get(r).getY());
					}
					else if(path.contains(vertex.get(r).getX()-allowanceSide,vertex.get(r).getY())){
						vertexTmp.add(new Point2D.Double(vertex.get(r).getX()+allowanceSide,vertex.get(r).getY()));
						generalPath.lineTo(vertex.get(r).getX()+allowanceSide,vertex.get(r).getY());						
					}
					else if(path.contains(vertex.get(r).getX(),vertex.get(r).getY()+allowanceSide)){
						vertexTmp.add(new Point2D.Double(vertex.get(r).getX(),vertex.get(r).getY()-allowanceSide));
						generalPath.lineTo(vertex.get(r).getX(),vertex.get(r).getY()-allowanceSide);						
					}
					else{
						vertexTmp.add(new Point2D.Double(vertex.get(r).getX(),vertex.get(r).getY()+allowanceSide));
						generalPath.lineTo(vertex.get(r).getX(),vertex.get(r).getY()+allowanceSide);						
					}
				}
				vertex = vertexTmp;
				bossArray.add(generalPath);
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

		borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(this.cavidade.getPosicaoX()-allowanceSide,this.cavidade.getPosicaoY()+allowanceSide,z), this.cavidade.getComprimento()-2*allowanceSide, this.cavidade.getLargura()-2*allowanceSide, this.cavidade.getRaio());
		for(int k=0;k<borda.length;k++){
			pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
		}

		int b=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS Nï¿½O USINADOS
		pontosPossiveis = new ArrayList<Point3d>();
		coordenadas = new ArrayList<Point2d>();
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

		/*TESTE*/
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
					numeroDeDiametrosAdicionados++;
					raioMedia+=malhaMenoresDistancias[i][k];
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
			numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametroPrimeiroWs))+10;
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
		if(diametroFerramenta!=diametroPrimeiroWs)
			pontos = new ArrayList<ArrayList<Point3d>>();
		pontosMenores = new ArrayList<Point3d>();		
		pontosPossiveis = new ArrayList<Point3d>();
		coordenadas = new ArrayList<Point2d>();
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
					}
					b=0;
				}
			}
		}
		
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
		
		numeroDeCortes = (int) (maiorMenorDistancia/(ae/1.3))+10;
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
			if(diametroFerramenta!=diametroPrimeiroWs)
				pontos.add(pontosMenores);
		}
		
		for(int i=0;i<pontos.size();i++){
			System.out.println("Pontos menores:  "+pontos.get(i).size());
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
	
	public ArrayList<LinearPath> getContorno(double z){
		ArrayList<LinearPath> contorno = new ArrayList<LinearPath>();
	
		
		double 	diametroFerramenta = this.ferramenta.getDiametroFerramenta(),
				raio = this.cavidade.getRaio(),
				allowanceSide = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceSide(),
				comprimento = this.cavidade.getComprimento(),
				largura = this.cavidade.getLargura(),
				xI =this.cavidade.getPosicaoX()+diametroFerramenta/2+allowanceSide,
				xF =this.cavidade.getPosicaoX()+comprimento-diametroFerramenta/2-allowanceSide,
				yI =this.cavidade.getPosicaoY()+diametroFerramenta/2+allowanceSide,
				yF =this.cavidade.getPosicaoY()+largura-diametroFerramenta/2-allowanceSide;
			
		Point3d pontoInicial , pontoFinal;
		LinearPath ligarPontos;
		
//		pontoFinal = new Point3d(xI,yI,this.ws.getOperation().getRetractPlane());
//		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
//		contorno.add(ligarPontos);
		pontoInicial =  new Point3d(xI+raio,yI,this.ws.getOperation().getRetractPlane());
		
		pontoFinal = new Point3d(xI+raio,yI,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
		
		pontoFinal = new Point3d(xI,yI+raio,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
		
		pontoFinal = new Point3d(xI,yF-raio,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
		
		pontoFinal = new Point3d(xI+raio,yF,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
		
		pontoFinal = new Point3d(xF-raio,yF,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
		
		pontoFinal = new Point3d(xF,yF-raio,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;

		pontoFinal = new Point3d(xF,yI+raio,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;

		pontoFinal = new Point3d(xF-raio,yI,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;

		pontoFinal = new Point3d(xI+raio,yI,z);
		ligarPontos = new LinearPath(pontoInicial, pontoFinal);
		contorno.add(ligarPontos);
		pontoInicial = pontoFinal;
	
		
		return contorno;
	}
	
	public ArrayList<LinearPath> getDesbasteTest2(){

		ArrayList<LinearPath> desbasteTest = new ArrayList<LinearPath>();
		double [][]malhaMenoresDistancias;
		double z=0,	maiorMenorDistancia=0, numeroDeCortes, numeroDeAps, c=0;
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double diametroPrimeiroWs = this.cavidade.getWorkingsteps().get(0).getFerramenta().getDiametroFerramenta();
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Double> menorDistancia;
		numeroDeAps = this.cavidade.getProfundidade()/ap;
		Point3d pontoInicial;
		Point3d pontoFinal = null;
		LinearPath ligarPontos;
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
			c=0;
			for(int i=0;i<numeroDeCortes;i++){
				for(int k=0;k<menorDistancia.size();k++){
					if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
						c++;
						if(diametroFerramenta!=diametroPrimeiroWs){
							bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-diametroPrimeiroWs, pontosPossiveis.get(k).getY()-diametroPrimeiroWs, diametroPrimeiroWs, diametroPrimeiroWs));
						}
						else{
							pontoInicial = new Point3d(pontosPossiveis.get(k).getX(),pontosPossiveis.get(k).getY(),-pontosPossiveis.get(k).getZ());
							if(c>1){
								ligarPontos = new LinearPath(pontoInicial, pontoFinal);
								desbasteTest.add(ligarPontos);
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
				ArrayList<Point3d> pontosPeriferia = getPontosPeriferia(z, cavidade);
				ArrayList<Point2d> coordenadas = getCoordenadas(z, cavidade, bossArray);
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


				numeroDeCortes = (int) (maiorMenorDistancia/ae);
				if(numeroDeCortes==0)
					numeroDeCortes=1;
				c=0;
				
				for(int i=0;i<numeroDeCortes;i++){
					for(int k=0;k<pontosPossiveis.size();k++){
						if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
							c++;
							pontoInicial = new Point3d(pontosPossiveis.get(k).getX(),pontosPossiveis.get(k).getY(),-pontosPossiveis.get(k).getZ());
							if(c>1){
								ligarPontos = new LinearPath(pontoInicial, pontoFinal);
								desbasteTest.add(ligarPontos);
							}
							pontoFinal = pontoInicial;
						}
					}



					//				for(int i=0;i<malhaMenoresDistancias.length;i++){
					//					for(int k=0;k<malhaMenoresDistancias[i].length;k++){
					//						menorDistancia.add(malhaMenoresDistancias[i][k]);
					//						if(maiorMenorDistancia<malhaMenoresDistancias[i][k]){
					//							maiorMenorDistancia=malhaMenoresDistancias[i][k];
					//						}
					//					}
					//				}

					//				numeroDeCortes = (int) (maiorMenorDistancia/ae);
					//				c=0;
					//				for(int i=0;i<numeroDeCortes;i++){
					//					for(int k=0;k<menorDistancia.size();k++){
					//						if(menorDistancia.get(k)<=(i+1)*ae && menorDistancia.get(k)>=(i+1)*ae-0.65){
					//							c++;
					//							pontoInicial = new Point3d(pontosPossiveis.get(k));
					//							if(c>1){
					//								ligarPontos = new LinearPath(pontoInicial, pontoFinal);
					//								desbasteTest.add(ligarPontos);
					//							}
					//							pontoFinal = pontoInicial;
					//						}					
					//					}
				}
			}
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

		ArrayList<Point3d> pontosPeriferia;
		ArrayList<Boss> itsBoss;
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
