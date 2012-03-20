package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import javax.vecmath.Point3d;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilQuadradoU {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private RanhuraPerfilQuadradoU ranhuraQuadU;

	public MovimentacaoRanhuraPerfilQuadradoU(Workingstep ws) {
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.ranhuraQuadU = (RanhuraPerfilQuadradoU) this.ws.getFeature();
	}

	private ArrayList<LinearPath> desbaste() {

		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();

		double xAtual = this.ranhuraQuadU.getPosicaoX();
		double xProximo;
		double yAtual = this.ranhuraQuadU.getPosicaoY();
		double yProximo;
		double zAtual = this.ranhuraQuadU.getPosicaoZ();
		double zLimite;
		double aeUtilizado;
		double apUtilizado;
		double xParaDescerAp;
		double yParaDescerAp;
		double h;
		double alfa = this.ranhuraQuadU.getAngulo() * Math.PI / 180;
		double allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		boolean terminouZLimite = false;
		boolean terminouXY = false;
		boolean vaiVolta = true;
		boolean fundo = false;

		Point3d pontoInicial;
		Point3d pontoFinal;

		h = (this.ranhuraQuadU.getLargura()-this.ferramenta.getDiametroFerramenta() - 2 * allowanceBottom) / 2;
		zLimite = h / Math.tan(alfa);
		if (zLimite > this.ranhuraQuadU.getProfundidade())
			zLimite = this.ranhuraQuadU.getProfundidade();
		
		if (this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL) {
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),0,this.ranhuraQuadU.getPosicaoZ());
			while (!terminouZLimite) {
				if (this.ws.getCondicoesUsinagem().getAp() - zAtual <= zLimite) {
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if (this.ws.getCondicoesUsinagem().getAp() - zAtual == zLimite)
						terminouZLimite = true;
				} else {
					apUtilizado = zAtual + zLimite;
					terminouZLimite = true;
				}
				zAtual = zAtual - apUtilizado;
				xParaDescerAp = Math.tan(alfa)*(-zAtual)+allowanceBottom;
				
				if(fundo){
					xAtual = this.ws.getCondicoesUsinagem().getAe()+this.ranhuraQuadU.getLargura()+this.ranhuraQuadU.getPosicaoX()-xParaDescerAp-this.ferramenta.getDiametroFerramenta()/2;
					fundo = false;
				}
				else{
					xAtual = this.ranhuraQuadU.getPosicaoX()+xParaDescerAp-this.ws.getCondicoesUsinagem().getAe()+this.ferramenta.getDiametroFerramenta()/2;
					fundo = true;
				}
				terminouXY = false;
				
				while (!terminouXY) {
						if (vaiVolta) {
							yAtual = this.ranhuraQuadU.getPosicaoY();
							yProximo = this.ranhuraQuadU.getComprimento();
							vaiVolta = false;
						} else {
							yAtual = this.ranhuraQuadU.getComprimento();
							yProximo = this.ranhuraQuadU.getPosicaoY();
							vaiVolta = true;
						}
						
						if(fundo){
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()>this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-this.ferramenta.getDiametroFerramenta()/2){
								aeUtilizado = this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-this.ferramenta.getDiametroFerramenta()/2-xAtual;
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual+this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-this.ferramenta.getDiametroFerramenta()/2){
									terminouXY = true;
								}
							}
							xAtual = xAtual + aeUtilizado;
							}
						else{
							if(xAtual-this.ws.getCondicoesUsinagem().getAe()<this.ranhuraQuadU.getPosicaoX()+xParaDescerAp+this.ferramenta.getDiametroFerramenta()/2){
								aeUtilizado = xAtual-xParaDescerAp-this.ferramenta.getDiametroFerramenta()/2-this.ranhuraQuadU.getPosicaoX();
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual-this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoX()+xParaDescerAp+this.ferramenta.getDiametroFerramenta()/2)
									terminouXY = true;
							}
							xAtual = xAtual-aeUtilizado;
						}
						
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);						
					}
				}
			}
		else{
			if(this.ranhuraQuadU.getEixo()==Ranhura.HORIZONTAL){
				pontoInicial = new Point3d(0,this.ranhuraQuadU.getPosicaoY(),this.ranhuraQuadU.getPosicaoZ());
				while (!terminouZLimite) {
					if (this.ws.getCondicoesUsinagem().getAp() - zAtual <= zLimite) {
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if (this.ws.getCondicoesUsinagem().getAp() - zAtual == zLimite)
							terminouZLimite = true;
					} else {
						apUtilizado = zAtual + zLimite;
						terminouZLimite = true;
					}
					zAtual = zAtual - apUtilizado;
					
					yParaDescerAp = Math.tan(alfa)*(-zAtual)+allowanceBottom;
					
					if(fundo){
						yAtual = this.ranhuraQuadU.getPosicaoY()+this.ranhuraQuadU.getLargura()+this.ws.getCondicoesUsinagem().getAe()-yParaDescerAp-this.ferramenta.getDiametroFerramenta()/2;
						fundo = false;
					}
					else{
						yAtual = this.ranhuraQuadU.getPosicaoY()+yParaDescerAp-this.ws.getCondicoesUsinagem().getAe()+this.ferramenta.getDiametroFerramenta()/2;
						fundo = true;
					}
					terminouXY = false;
					
					while (!terminouXY) {
						if (vaiVolta) {
							xAtual = this.ranhuraQuadU.getPosicaoX();
							xProximo = this.ranhuraQuadU.getComprimento();
							vaiVolta = false;
						} else {
							xAtual = this.ranhuraQuadU.getComprimento();
							xProximo = this.ranhuraQuadU.getPosicaoX();
							vaiVolta = true;
						}
						
						if(fundo){
							if(yAtual+this.ws.getCondicoesUsinagem().getAe()>this.ranhuraQuadU.getPosicaoY()+this.ranhuraQuadU.getLargura()-yParaDescerAp-this.ferramenta.getDiametroFerramenta()/2){
								aeUtilizado = this.ranhuraQuadU.getPosicaoY()+this.ranhuraQuadU.getLargura()-yParaDescerAp-this.ferramenta.getDiametroFerramenta()/2-yAtual;
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(yAtual+this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoY()+this.ranhuraQuadU.getLargura()-yParaDescerAp-this.ferramenta.getDiametroFerramenta()/2){
									terminouXY = true;
								}
							}
							yAtual = yAtual + aeUtilizado;
							}
						else{
							if(yAtual-this.ws.getCondicoesUsinagem().getAe()<this.ranhuraQuadU.getPosicaoY()+yParaDescerAp+this.ferramenta.getDiametroFerramenta()/2){
								aeUtilizado = yAtual-yParaDescerAp-this.ferramenta.getDiametroFerramenta()/2-this.ranhuraQuadU.getPosicaoY();
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(yAtual-this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoY()+yParaDescerAp+this.ferramenta.getDiametroFerramenta()/2)
									terminouXY = true;
							}
							yAtual = yAtual-aeUtilizado;
						}
						
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);						
					}
				}
			}
		}
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento() {
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		double xAtual=0;
		double xProximo;
		double yAtual;
		double yProximo;
		double zAtual;
		double zLimite;
		double apUtilizado;
		double aeUtilizado;
		double temp;
		double x;
		double somaDosAps = 0;
		double alfa = this.ranhuraQuadU.getAngulo() * Math.PI / 180;
		double largura  = this.ranhuraQuadU.getLargura();
		double largura2 = this.ranhuraQuadU.getLargura2();
		double R = this.ranhuraQuadU.getRaio();
		double r = this.ferramenta.getDiametroFerramenta()/2;
		double profundidade = this.ranhuraQuadU.getProfundidade();
		
		boolean terminouX = true;
		boolean terminouZ = false;
		boolean terminouZ1 = true;
		boolean terminouZ2 = true;
		boolean terminouZ3 = true;
		boolean vaiVolta = true;
				
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		zLimite = profundidade-R*(1-Math.sin(alfa))+R*Math.sin(alfa);
		zAtual = -r*(1-Math.sin(alfa));
		

		if(this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL){

			/****************** DESCIDA LINEAR ATÉ COMEÇAR A CURVATURA ***********************/
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),this.ranhuraQuadU.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			while(!terminouZ){
				if(-zAtual <= zLimite){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual == zLimite)
						terminouZ1 = false;
				}
				else{
					apUtilizado = zLimite + zAtual;
					terminouZ1 = false;
				}
				zAtual = zAtual - apUtilizado;

				if(vaiVolta){
					yAtual   = 0;
					yProximo = this.ranhuraQuadU.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual   = this.ranhuraQuadU.getComprimento();
					yProximo = 0;
					vaiVolta = true;
				}

				temp = (apUtilizado - zAtual)/Math.sin(alfa);
				xAtual = Math.sqrt(Math.exp(temp) + Math.exp(apUtilizado-zAtual));

				/*********************  ACABAMENTO DA CURVA  DESCIDA   *********************/
				while(!terminouZ1){
					if(-zAtual <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-zAtual == profundidade)
							terminouX = false;
					}
					else{
						apUtilizado = profundidade + zAtual;
						terminouX = false;
					}
					zAtual = zAtual - apUtilizado;

					if(vaiVolta){
						yAtual   = 0;
						yProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						yAtual   = this.ranhuraQuadU.getComprimento();
						yProximo = 0;
						vaiVolta = true;
					}
					somaDosAps = somaDosAps + apUtilizado;

					xAtual = R*Math.cos(Math.asin((R*Math.sin(alfa)+somaDosAps)/R));


					/************************ ACABAMENTO DA LARGURA 2 ****************************/

					x = this.ranhuraQuadU.getPosicaoX()+largura/2-xAtual;
					largura2 = xAtual + 2*x;

					while(!terminouX){
						if(xAtual <= largura2){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual == largura2)
								terminouZ2 = false;
						}
						else{
							aeUtilizado = largura2 - xAtual;
							terminouZ2 = false;
						}
						xAtual = xAtual + aeUtilizado;

						if(vaiVolta){
							yAtual   = 0;
							yProximo = this.ranhuraQuadU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual   = this.ranhuraQuadU.getComprimento();
							yProximo = 0;
							vaiVolta = true;
						}



						/********************** ACABAMENTO DA CURVA SUBIDA **********************/
						while(!terminouZ2){
							if(-zAtual >= zLimite){
								apUtilizado = this.ws.getCondicoesUsinagem().getAp();
								if(-zAtual >= zLimite)
									terminouZ3 = false;
							}
							else{
								apUtilizado = zLimite + zAtual;
								terminouZ3 = false;
							}
							zAtual = zAtual + apUtilizado;

							if(vaiVolta){
								yAtual   = 0;
								yProximo = this.ranhuraQuadU.getComprimento();
								vaiVolta = false;
							}
							else{
								yAtual   = this.ranhuraQuadU.getComprimento();
								yProximo = 0;
								vaiVolta = true;
							}

							somaDosAps = somaDosAps - apUtilizado;
							xAtual = R*Math.cos(Math.asin((R*Math.sin(alfa)+somaDosAps)/R));


							/********************** SUBIDA LINEAR **********************/
							while(!terminouZ3){
								if(-zAtual >= r*(1-Math.sin(alfa))){
									apUtilizado = this.ws.getCondicoesUsinagem().getAp();
									if(-zAtual == r*(1-Math.sin(alfa))){
										terminouZ3 = true;
										terminouZ = true;
									}
								}
								else{
									apUtilizado = -zAtual -r*(1-Math.sin(alfa));
									terminouZ3 = true;
									terminouZ = true;
								}
								zAtual = zAtual + apUtilizado;

								if(vaiVolta){
									yAtual   = 0;
									yProximo = this.ranhuraQuadU.getComprimento();
									vaiVolta = false;
								}
								else{
									yAtual   = this.ranhuraQuadU.getComprimento();
									yProximo = 0;
									vaiVolta = true;
								}
								temp = (apUtilizado - zAtual)/Math.sin(alfa);

								xAtual = Math.sqrt(Math.exp(temp) + Math.exp(apUtilizado-zAtual));
							}
						}
					}
				}
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
			}
		}
/*		else{
			
		}
*/
		return acabamento;
	}
	
	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhuraPerfilQuadradoU() {
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		ArrayList<LinearPath> desbaste = this.desbaste();
		
		for(LinearPath pathTemp : desbaste)
			saida.add(pathTemp);

		Point3d ultimoPonto = saida.get(saida.size()-1).getFinalPoint();
		LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (ultimoPonto.getX(), ultimoPonto.getY() ,this.ws.getOperation().getRetractPlane()));
		planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
		saida.add(planoSeguro);
		return saida;
	}
	
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhuraPerfilQuadradoU() {
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		ArrayList<LinearPath> acabamento = this.acabamento();
		
		for(LinearPath pathTemp : acabamento)
			saida.add(pathTemp);

		Point3d ultimoPonto = saida.get(saida.size()-1).getFinalPoint();
		LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (ultimoPonto.getX(), ultimoPonto.getY() ,this.ws.getOperation().getRetractPlane()));
		planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
		saida.add(planoSeguro);
		return saida;
	}
	
}
