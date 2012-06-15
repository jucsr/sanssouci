package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.security.spec.EllipticCurve;
import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

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
				raio=this.cavidade.getRaio();
		double malha[][][] = new double[99][99][2];
		RoundRectangle2D retangulo = new RoundRectangle2D.Double(this.cavidade.getPosicaoX(), this.cavidade.getPosicaoY(), comprimento, largura, raio, raio);
	
		ArrayList<RoundRectangle2D> listaRetangulos = new ArrayList<RoundRectangle2D>();
		ArrayList<Ellipse2D> listaElipses = new ArrayList<Ellipse2D>();
				
		//CRIAR MALHA DE PONTOS PARA COMPARAÇÃO
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = comprimento*(i+1)/100;//x
				malha[i][k][1] = largura*(k+1)/100;//y
			}
		}
		
		//REPRODUZIR BOSS
		for(int i=0;i<this.itsBoss.size();i++){
			//FAZER ALGUMA COISA PRA CRIAR OS 2Ds
			//COLOCAR OS 2Ds CRIADOS EM SEUS RESPECTIVOS ARRAYS (listaRetangulos, OU listaElipses)
		}
		
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS NÃO USINADOS
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retangulo.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<listaRetangulos.size();g++){
						if(listaRetangulos.get(g).contains(malha[i][k][0], malha[i][k][1])){
							//ADICIONAR AOS PONTOS NÃO USINADOS
						}
					}
					for(int f=0;f<listaElipses.size();f++){
						if(listaElipses.get(f).contains(malha[i][k][0], malha[i][k][1])){
							//ADICIONAR AOS PONTOS NÃO USINADOS
						}
					}
				}
				else{
					//ADICIONAR AOS PONTOS NÃO USINADOS
				}
			}
		}
		
		//ELIMINAR OS PONTOS QUE NÃO PODEM SER USINADOS DA MALHA
		
		//CRIAR O ARRAY DAS MENORES DISTANCIAS
		
		//ADICIONAR NA SAIDA.
		
		
		return desbaste;
	}
	
	
	
}
