package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.Shape;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraCavidade;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoGeneralClosedPocket {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private GeneralClosedPocket genClosed;
	private ArrayList<Boss> itsBoss;
	
	public MovimentacaoGeneralClosedPocket(Workingstep ws){
		this.itsBoss = genClosed.getItsBoss();
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.genClosed = (GeneralClosedPocket) this.ws.getFeature();
	}
	
public ArrayList<LinearPath> getDesbasteTest(){
		
		ArrayList<LinearPath> desbasteTest;
		double [][]malhaMenoresDistancias;
		double z=0,	maiorMenorDistancia=0, numeroDeCortes, numeroDeAps;
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		ArrayList<Point3d> pontosPossiveis;
		ArrayList<Double> menorDistancia;
		desbasteTest = new ArrayList<LinearPath>();
		numeroDeAps = this.genClosed.getProfundidade()/ap;
		Point3d pontoInicial;
		Point3d pontoFinal = null;
		ArrayList<Shape> bossArray;
		
		for(int p=0;p<numeroDeAps;p++){
			z+=ap;

			pontosPossiveis = MapeadoraGeneralClosedPocket.getPontosPossiveis(z, this.genClosed);
			malhaMenoresDistancias = MapeadoraGeneralClosedPocket.getMalhaMenoresDistancias(z, this.genClosed);
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
		numeroDeAps = this.genClosed.getProfundidade()/ap;
		Point3d pontoInicial;
		Point3d pontoFinal = null;
		ArrayList<Shape> bossArray;
		
		for(int p=0;p<numeroDeAps;p++){
			z+=ap;

			pontosPossiveis = MapeadoraGeneralClosedPocket.getPontosPossiveis(z, this.genClosed);
			malhaMenoresDistancias = MapeadoraGeneralClosedPocket.getMalhaMenoresDistancias(z, this.genClosed);
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
	
	
	
}
