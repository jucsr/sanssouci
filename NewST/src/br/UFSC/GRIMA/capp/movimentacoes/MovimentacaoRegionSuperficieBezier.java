package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;

public class MovimentacaoRegionSuperficieBezier {

	private Workingstep ws;
	private Region regionBezier;
	private Ferramenta ferramenta;

	public MovimentacaoRegionSuperficieBezier(Workingstep ws){
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.regionBezier = (Region) this.ws.getFeature();
	}
	
	public ArrayList<LinearPath> desbaste(){
		
		double z=this.ws.getOperation().getStartPoint().z;
		double y=this.regionBezier.getPosicaoY();
		double x=this.regionBezier.getPosicaoZ();
		double zMinimo=100;
		double zMaximo=-10000;
		double distanciaTmp;
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double maiorMenorDistancia=0;
		double menor=100;
		double distanciaTemp;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		int numeroDeAps;
		int numeroDeCortes;
		int t=0,m;
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		ArrayList<Point3d> pontosProibidos;
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<ArrayList<Point3d>> pontos;
		ArrayList<Point3d> pontos2 = null;
		ArrayList<ArrayList<Point3d>> pontosOrdenados = new ArrayList<ArrayList<Point3d>>();
		ArrayList<Point3d> temp;
		ArrayList<Double> menorDistancia;
		Point3d malha[][] = new BezierSurface(regionBezier.getControlVertex(), 200, 200).getMeshArray();
		Point3d pontoInicial = new Point3d(0,0,0);
		Point3d pontoFinal;
		LinearPath ligarPontos;

		for(int i=0;i<malha.length;i++){//PERCORRE A MALHA TODA PARA ACHAR O MENOR Z
			for(int j=0;j<malha[i].length;j++){
				if(zMinimo>malha[i][j].getZ()){
					zMinimo=malha[i][j].getZ();
				}		
			}
		}

		numeroDeAps= (int) (-zMinimo/ap);
		
		for(int h=0;h<numeroDeAps;h++){

			z-=ap;

			pontosProibidos = new ArrayList<Point3d>();
			pontosPossiveis = new ArrayList<Point3d>();
			for(int j=0;j<malha.length;j++){//PERCORRE A SUPERFICIE INTEIRA
				for(int i=0;i<malha[j].length;i++){
					if(malha[i][j].getZ()<=z){
						if(malha[i][j].getZ()>=z-0.5){//PEGA OS PONTOS QUE NAO PODE DESBASTAR (PONTOS DA PERIFERIA APENAS, OU SEJA
							pontosProibidos.add(new Point3d(malha[i][j].x,malha[i][j].y,z));//   PONTOS DA "CASCA" DOS POLIGONOS)
						}
					}
					else if((i==0 || i==malha[j].length-1 || j==0 || j==malha.length-1) && malha[i][j].getZ()<z){
						pontosPossiveis.add(new Point3d(malha[i][j].x,malha[i][j].y,z));
					}
					if(malha[i][j].getZ()<z){
						pontosPossiveis.add(new Point3d(malha[i][j].getX(),malha[i][j].getY(),z));//PEGA OS PONTOS QUE PODE DESBASTAR
					}
				}
			}




			if(pontosPossiveis.size()<1){
				break;
			}

			menorDistancia = new ArrayList<Double>();
			for(int i=0;i<pontosPossiveis.size();i++){
				distanciaTmp=100;
				for(int k=0;k<pontosProibidos.size();k++){
					if(OperationsVector.distanceVector(pontosProibidos.get(k), pontosPossiveis.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.distanceVector(pontosProibidos.get(k), pontosPossiveis.get(i));
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


			for(int i=0;i<pontos.size();i++){
				if(pontos.get(i).size()==0 || pontos.get(i).size()==1){
					continue;
				}

				if(i==0 && h==0){
					pontoInicial = new Point3d(pontos.get(i).get(0).getX(),pontos.get(i).get(0).getY(),z);					
				}
				if(i==0 && h!=0){
					ligarPontos = new LinearPath(pontoInicial, new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()));
					desbaste.add(ligarPontos);
					ligarPontos = new LinearPath(new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()), new Point3d(pontos.get(i).get(0).getX(),pontos.get(i).get(0).getY(),this.ws.getOperation().getRetractPlane()));
					desbaste.add(ligarPontos);
				}

				distanciaTemp=OperationsVector.distanceVector(pontos.get(i).get(0),pontos.get(i).get(1));
				temp = new ArrayList<Point3d>();
				temp.add(pontos.get(i).get(0));
				m=pontos.get(i).size();
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
						ligarPontos = new LinearPath(pontoInicial, new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()));
						ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
						desbaste.add(ligarPontos);
						ligarPontos = new LinearPath(new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()), new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),this.ws.getOperation().getRetractPlane()));
						desbaste.add(ligarPontos);
						ligarPontos = new LinearPath(new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),this.ws.getOperation().getRetractPlane()), new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z));
						ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
						desbaste.add(ligarPontos);
						pontoInicial = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z);
					}else{
						pontoFinal = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z);
						ligarPontos = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(ligarPontos);
						pontoInicial = new Point3d(pontos.get(i).get(t).getX(),pontos.get(i).get(t).getY(),z);
					}
					temp.add(pontos.get(i).get(t));
					pontos.get(i).remove(t);
					if(pontos.get(i).size()==0 || pontos.get(i).size()==1){
						continue;
					}
				}
				if(i+1!=pontos.size() &&
						pontos.get(i+1).size()!=0){
					ligarPontos = new LinearPath(pontoInicial, new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()));
					ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
					desbaste.add(ligarPontos);
					ligarPontos = new LinearPath(new Point3d(pontoInicial.getX(),pontoInicial.getY(),this.ws.getOperation().getRetractPlane()), new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),this.ws.getOperation().getRetractPlane()));
					desbaste.add(ligarPontos);
					ligarPontos = new LinearPath(new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),this.ws.getOperation().getRetractPlane()), new Point3d(pontos.get(i+1).get(0).getX(),pontos.get(i+1).get(0).getY(),z));
					ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
					desbaste.add(ligarPontos);
				}
				//				pontosOrdenados.add(temp);
			}
		}
		return desbaste;
	}
	
	public ArrayList<LinearPath> desbaste1(){
		ArrayList<LinearPath> 	desbaste1 = new ArrayList<LinearPath>();
		LinearPath ligaPontos;

		Point3d malha[][] = new BezierSurface(regionBezier.getControlVertex(), 200, 200).getMeshArray(),
				initialPoint = new Point3d(0,0,0),
				finalPoint;
		double 	ap=this.ws.getCondicoesUsinagem().getAp(),
				numeroDeAes,
				numeroDeAps,
				tmp,
				ae=this.ws.getCondicoesUsinagem().getAe(),
				raioFerramenta=this.ferramenta.getDiametroFerramenta()/2,
				zMaximo=malha[0][0].getZ(),
				x=this.regionBezier.getPosicaoX(),
				y=this.regionBezier.getPosicaoY(),
				z=this.regionBezier.getPosicaoZ(),
				xInicio=malha[0][0].getX(),
				xFim=malha[malha.length-1][malha[malha.length-1].length-1].getX(),
				yInicio=malha[0][0].getY(),
				yFim=malha[malha.length-1][malha[malha.length-1].length-1].getY();
		
		boolean vaiVolta=true,
				fundo=false;
		
		for(int i=0;i<malha.length;i++){//PERCORRE A MALHA TODA PARA ACHAR O MENOR Z
			for(int j=0;j<malha[i].length;j++){
				if(zMaximo<malha[i][j].getZ()){
					zMaximo=malha[i][j].getZ();
				}
			}
		}
		
		tmp = (xFim-xInicio)%ae;
		numeroDeAes = ((xFim-xInicio)-tmp)/ae;
		tmp = -zMaximo%ap;
		numeroDeAps = (-zMaximo-tmp)/ap;
		
		
		for(int i=0;i<numeroDeAps;i++){
			z-=ap;
			
			if(i==0){
				y=yInicio;
				initialPoint = new Point3d(x,y,this.ws.getOperation().getRetractPlane());
			}
			
			
			finalPoint = new Point3d(x,y,z);
			ligaPontos = new LinearPath(initialPoint, finalPoint);
			desbaste1.add(ligaPontos);
			initialPoint = new Point3d(x,y,z);
			
			if(vaiVolta){
				y=yFim;
				vaiVolta=false;
			}
			else{
				y=yInicio;
				vaiVolta=true;
			}
			
			finalPoint = new Point3d(x,y,z);
			ligaPontos = new LinearPath(initialPoint, finalPoint);
			desbaste1.add(ligaPontos);			
			initialPoint = new Point3d(x,y,z);
			
			for(int k=0;k<numeroDeAes;k++){
				if(!fundo)
					x+=ae;
				else
					x-=ae;

				finalPoint = new Point3d(x,y,z);
				ligaPontos = new LinearPath(initialPoint, finalPoint);
				desbaste1.add(ligaPontos);
				initialPoint = new Point3d(x,y,z);
				
				if(vaiVolta){
					y=yFim;
					vaiVolta=false;
				}
				else{
					y=yInicio;
					vaiVolta=true;
				}
				
				finalPoint = new Point3d(x,y,z);
				ligaPontos = new LinearPath(initialPoint, finalPoint);
				desbaste1.add(ligaPontos);			
				initialPoint = new Point3d(x,y,z);	
			}
			if(fundo)
				fundo=false;
			else
				fundo=true;
			
		}
		return desbaste1;
	}
	public ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();

		Point3d malha[][] = new BezierSurface(regionBezier.getControlVertex(), 50, 50).getMeshArray();
		Point3d pontoInicial = null;
		Point3d pontoFinal;
		LinearPath ligaPontos;	
		double x=this.regionBezier.getPosicaoX();
		double y=this.regionBezier.getPosicaoY();
		double z=this.regionBezier.getPosicaoZ();
		double r=this.ferramenta.getDiametroFerramenta()/2;
		double alfa, alfaX=0;
		double h;
		double distanciaEntrePontos=0, distanciaEntrePontosX=0;
		//int numeroDePontos=this.ranhuraBezier.getPontosDaCurva().length;
		int i;


		for(int k=0;k<malha.length;k++){

			for(i=0;i<malha[k].length;i++){

				
				
				if(i==malha[k].length-1)
					distanciaEntrePontos= malha[k][i].getY()-malha[k][i-1].getY();
				else
					distanciaEntrePontos= malha[k][i+1].getY()-malha[k][i].getY();
					
				
				if(k!=0)
					distanciaEntrePontosX= malha[k][i].getX()-malha[k-1][i].getX();
				else
					distanciaEntrePontosX= malha[k+1][i].getX()-malha[k][i].getX();
								
				if(k%2==0){
					
					if(i==0)
						alfa=(Math.atan2((malha[k][i+1].getZ()-malha[k][i].getZ()),distanciaEntrePontos));
					else
						alfa=(Math.atan2((malha[k][i].getZ()-malha[k][i-1].getZ()),distanciaEntrePontos));
					
					
					if(k==0)
						alfaX=(Math.atan2((malha[k+1][i].getZ()-malha[k][i].getZ()),distanciaEntrePontosX));
					else
						alfaX=(Math.atan2((malha[k][i].getZ()-malha[k-1][i].getZ()),distanciaEntrePontosX));
					
//					System.out.println(alfaX*180/Math.PI);
					
					if(i==0 && k==0){
						y=this.regionBezier.getPosicaoY()-(Math.sin(alfa)*r);
						x=this.regionBezier.getPosicaoX()-(Math.sin(alfaX)*r);
						pontoInicial= new Point3d(x,y,this.ws.getOperation().getRetractPlane());
					}
					else if(i==0){
						y=this.regionBezier.getPosicaoY()-(Math.sin(alfa)*r);
						x=this.regionBezier.getPosicaoX()+malha[k][0].getX()-(Math.sin(alfaX)*r);
						pontoInicial= new Point3d(x,y,z);						
					}

					//ADICIONA Z
					h=r-Math.cos(alfa)*r;
					z=malha[k][i].getZ()-h;
					if(-z<0.001)
						z=0;
					if(y<0.001)
						y=0;

					pontoFinal = new Point3d(x, y, z);
					ligaPontos = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(ligaPontos);
					pontoInicial = new Point3d(x, y, z);

//					x = malha[k][0].getX();
//					
//					pontoFinal = new Point3d(x, y, z);
//					ligaPontos = new LinearPath(pontoInicial,pontoFinal);
//					acabamento.add(ligaPontos);
//					pontoInicial = new Point3d(x, y, z);

					if(i!=0){
//						if(malha[k][i].getZ()<malha[k][i-1].getZ())
						y=this.regionBezier.getPosicaoY()+malha[k][i].getY()-(Math.sin(alfa)*r);//+distanciaEntrePontos;
						x=this.regionBezier.getPosicaoX()+malha[k][i].getX()-(Math.sin(alfaX)*r);//+distanciaEntrePontosX;
//						else if(malha[k][i].getZ()>malha[k][i-1].getZ())
//							y=this.regionBezier.getPosicaoY()+malha[k][i].getY()-(Math.sin(alfa)*r)+distanciaEntrePontos;
					}
				}
				//VOLTA 
				else{
					
					
					if(i==0)
						alfa=(Math.atan2((malha[k][malha[k].length-i-2].getZ()-malha[k][malha[k].length-i-1].getZ()),distanciaEntrePontos));
					else
						alfa=(Math.atan2((malha[k][malha[k].length-i-1].getZ()-malha[k][malha[k].length-i].getZ()),distanciaEntrePontos));
					

//					if(i==0)
//						alfaX=(Math.atan2((malha[k][malha[k].length-i-2].getZ()-malha[k][malha[k].length-i-1].getZ()),distanciaEntrePontosX));
//					else
//						alfaX=(Math.atan2((malha[k][malha[k].length-i-1].getZ()-malha[k][malha[k].length-i].getZ()),distanciaEntrePontosX));
					if(k==0)
						alfaX=(Math.atan2((malha[k+1][i].getZ()-malha[k][i].getZ()),distanciaEntrePontosX));
					else
						alfaX=(Math.atan2((malha[k][i].getZ()-malha[k-1][i].getZ()),distanciaEntrePontosX));
					
//					System.out.println(alfaX*180/Math.PI);
						
					if(i==0){
						y=this.regionBezier.getPosicaoY()+malha[k][malha[k].length-1].getY()+(Math.sin(alfa)*r);
						x=this.regionBezier.getPosicaoX()+malha[k][malha[k].length-1].getX()-(Math.sin(alfaX)*r);
						pontoInicial= new Point3d(x,y,z);
					}
					
					//ADICIONA Z
					h=r-Math.cos(alfa)*r;
					z=malha[k][malha[k].length-1-i].getZ()-h;
					if(-z<0.001)
						z=0;
					if(y<0.001)
							y=0;

					pontoFinal = new Point3d(x, y, z);
					ligaPontos = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(ligaPontos);
					pontoInicial = new Point3d(x, y, z);

//					x = malha[k][malha[k].length-1].getX();
//					
//					pontoFinal = new Point3d(x, y, z);
//					ligaPontos = new LinearPath(pontoInicial,pontoFinal);
//					acabamento.add(ligaPontos);
//					pontoInicial = new Point3d(x, y, z);

					if(malha[k].length-i!=0){
//						if(malha[k][malha[k].length-1-i].getZ()<malha[k][malha[k].length-2-i].getZ())
						y=this.regionBezier.getPosicaoY()+malha[k][malha[k].length-1-i].getY()+(Math.sin(alfa)*r);//-distanciaEntrePontos;
						x=this.regionBezier.getPosicaoX()+malha[k][malha[k].length-1-i].getX()-(Math.sin(alfaX)*r);//-distanciaEntrePontosX;
//						else if(malha[k][malha[k].length-1-i].getZ()>malha[k][malha[k].length-2-i].getZ())
//							y=this.regionBezier.getPosicaoY()+malha[k][malha[k].length-1-i].getY()+Math.abs(Math.sin(alfa)*r)-distanciaEntrePontos;
					}					
				}

			}	
		}

		return acabamento;
	}
	
	
	
	
	
}
