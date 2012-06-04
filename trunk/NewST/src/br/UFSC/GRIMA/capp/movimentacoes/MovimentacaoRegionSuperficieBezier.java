package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.capp.Workingstep;
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
		
		double z=this.regionBezier.getPosicaoZ();
		double y=this.regionBezier.getPosicaoY();
		double x=this.regionBezier.getPosicaoZ();
		double zMinimo=100;
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
		Point3d pontoInicial;
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
				System.out.println("Funcionou");
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
				if(pontos.get(i).size()==0)
					break;
				distanciaTemp=OperationsVector.distanceVector(pontos.get(i).get(0),pontos.get(i).get(1));
				pontoInicial = new Point3d(pontos.get(i).get(0).getX(),pontos.get(i).get(0).getY(),z);
				temp = new ArrayList<Point3d>();
				temp.add(pontos.get(i).get(0));
				m=pontos.get(i).size();
				for(int k=0;k<m;k++){
					menor=100;
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
				}
				if(i+1!=pontos.size()){
					ligarPontos = new LinearPath(pontoInicial, new Point3d(temp.get(temp.size()-2).getX(),temp.get(temp.size()-2).getY(),this.ws.getOperation().getRetractPlane()));
					ligarPontos.setTipoDeMovimento(LinearPath.FAST_MOV);
					desbaste.add(ligarPontos);
					ligarPontos = new LinearPath(new Point3d(temp.get(temp.size()-2).getX(),temp.get(temp.size()-2).getY(),this.ws.getOperation().getRetractPlane()), new Point3d(temp.get(temp.size()-1).getX(),temp.get(temp.size()-1).getY(),this.ws.getOperation().getRetractPlane()));
					desbaste.add(ligarPontos);
					ligarPontos = new LinearPath(new Point3d(temp.get(temp.size()-1).getX(),temp.get(temp.size()-1).getY(),this.ws.getOperation().getRetractPlane()), new Point3d(temp.get(temp.size()-1).getX(),temp.get(temp.size()-1).getY(),z));
					ligarPontos.setTipoDeMovimento(LinearPath.SLOW_MOV);
					desbaste.add(ligarPontos);
				}
//				pontosOrdenados.add(temp);
			}
		}
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
		return acabamento;
	}
	
	
	
	
	
}
