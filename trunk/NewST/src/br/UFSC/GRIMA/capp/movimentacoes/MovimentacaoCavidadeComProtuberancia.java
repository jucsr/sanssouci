package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
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
		this.itsBoss = cavidade.getItsBoss();
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.cavidade = (Cavidade) this.ws.getFeature();
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
	
	
	
}
