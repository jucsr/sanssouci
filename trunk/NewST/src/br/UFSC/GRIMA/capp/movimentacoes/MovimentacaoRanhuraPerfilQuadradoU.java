package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Ponto;

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
		double aeUtilizado=0;
		double apUtilizado=0;
		double xParaDescerAp = 0;
		double yParaDescerAp;
		double h;
		double alfa = this.ranhuraQuadU.getAngulo() * Math.PI / 180;
		double allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double zInicioDaCurva;
		double profundidade = this.ranhuraQuadU.getProfundidade();
		double R = this.ranhuraQuadU.getRaio();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double xInicial;
		double xLimiteEsquerda;
		double xLimiteDireita;
		double largura = this.ranhuraQuadU.getLargura();
		double r = this.ferramenta.getEdgeRadius();
		double temp=0;
		double tempFinal=0;
		double z=0;
		int i=0;
		double ptoFinal;
		double xTemp=0;
		
		boolean direita = true; 
		boolean movimentoLateral = false; 
		boolean terminouZLimite = false;
		boolean terminouXY = false;
		boolean vaiVolta = true;
		boolean fundo = false;
		boolean couber = true;
		boolean inicioDaCurva = false;
		boolean parteCurva = true;
		
		Point3d pontoInicial;
		Point3d pontoFinal;

		
		zInicioDaCurva = profundidade+R*(Math.sin(alfa)-1);
		
		
		
		h = (this.ranhuraQuadU.getLargura()-diametroFerramenta - 2 * allowanceBottom) / 2;
		
		if(this.ranhuraQuadU.getAngulo() == 90){
			zLimite = profundidade - R;					
		}else{
			zLimite = h / Math.tan(alfa);
		}
		
		if (zLimite > profundidade)
			zLimite = profundidade;
		
		xInicial = this.ranhuraQuadU.getPosicaoX() + allowanceBottom + diametroFerramenta/2 + (r*(1-Math.sin((Math.PI/2)-alfa)));
		xLimiteEsquerda = xInicial;
		xLimiteDireita = largura + this.ranhuraQuadU.getPosicaoX()- allowanceBottom - diametroFerramenta/2;
		xAtual = xInicial;
		
		
		if (this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class) {
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),0,this.ranhuraQuadU.getPosicaoZ());
			/*
			while(!inicioDaCurva){
				if(largura-2*allowanceBottom >= diametroFerramenta){
					if(this.ws.getCondicoesUsinagem().getAp()-zAtual <= zInicioDaCurva){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						temp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						largura = largura - 2*temp;
						if(this.ws.getCondicoesUsinagem().getAp()-zAtual == zInicioDaCurva){
							inicioDaCurva = true;
							parteCurva = false;
						}
					}
					else{
						apUtilizado = zInicioDaCurva + zAtual;
						tempFinal = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						largura = largura - 2*tempFinal;
						inicioDaCurva = true;
						parteCurva = false;
					}
					zAtual = zAtual - apUtilizado;
					terminouXY = false;
				}
				else{
					inicioDaCurva = true;
					parteCurva = false;
					terminouXY = true;
				}
				
				while(!terminouXY){
					if(vaiVolta){
						yAtual = 0;
						yProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}else{
						yAtual = this.ranhuraQuadU.getComprimento();
						yProximo = 0;
						vaiVolta = true;
					}
					xLimiteEsquerda = xLimiteEsquerda + temp;
					xLimiteDireita = xLimiteDireita - temp;
					
					if(direita){
						if(xAtual+this.ws.getCondicoesUsinagem().getAe() <= xLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual+this.ws.getCondicoesUsinagem().getAe() == xLimiteDireita){
								terminouXY = true;
								direita = false;
							}
						}
						else{
								aeUtilizado = xLimiteDireita - xAtual;
								terminouXY = true;
								direita = false;
						}						
					}
					else{
						if(xAtual-this.ws.getCondicoesUsinagem().getAe() >= xLimiteEsquerda){
							aeUtilizado = -this.ws.getCondicoesUsinagem().getAe();
							if(xAtual-this.ws.getCondicoesUsinagem().getAe() == xLimiteEsquerda){
								terminouXY = true;
								direita = true;
							}
						}
						else{
								aeUtilizado = xLimiteEsquerda - xAtual;
								terminouXY = true;
								direita = true;
							}
						}
					
					xAtual = xAtual + aeUtilizado;
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				}
			}
			
			while(!parteCurva){
				System.out.println("entrou");
				if(largura-2*allowanceBottom >= diametroFerramenta){
					if(this.ws.getCondicoesUsinagem().getAp()-zAtual <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(this.ws.getCondicoesUsinagem().getAp()-zAtual == profundidade)
							parteCurva = true;
					}
					else{
						apUtilizado = profundidade + zAtual;
						parteCurva = true;
					}
					zAtual = zAtual - apUtilizado;
					terminouXY = false;
				}
				else{
					parteCurva = true;
					terminouXY = true;
				}
				
				while(!terminouXY){
					if(vaiVolta){
						yAtual = 0;
						yProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}else{
						yAtual = this.ranhuraQuadU.getComprimento();
						yProximo = 0;
						vaiVolta = true;
					}
					
					z = profundidade + zAtual;
					
					if(Math.tan(Math.asin((R-z-r)/(R-r))) != 0){ 
						temp = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					largura = largura - 2*temp;
					
					if(i == 0){
						xLimiteEsquerda = xLimiteEsquerda + tempFinal;
						xLimiteDireita = xLimiteDireita - tempFinal;
						i++;
					}
					else{
						xLimiteEsquerda = xLimiteEsquerda + temp;
						xLimiteDireita = xLimiteDireita - temp;					
					}
					
					if(direita){
						if(xAtual+this.ws.getCondicoesUsinagem().getAe() <= xLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual+this.ws.getCondicoesUsinagem().getAe() == xLimiteDireita){
								terminouXY = true;
								direita = false;
							}
						}
						else{
								aeUtilizado = xLimiteDireita - xAtual;
								terminouXY = true;
								direita = false;
						}						
					}
					else{
						if(xAtual-this.ws.getCondicoesUsinagem().getAe() >= xLimiteEsquerda){
							aeUtilizado = -this.ws.getCondicoesUsinagem().getAe();
							if(xAtual-this.ws.getCondicoesUsinagem().getAe() == xLimiteEsquerda){
								terminouXY = true;
								direita = true;
							}
						}
						else{
								aeUtilizado = xLimiteEsquerda - xAtual;
								terminouXY = true;
								direita = true;
						}
					}
					xAtual = xAtual + aeUtilizado;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				}
			}*/
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
				
				if(zInicioDaCurva >= -zAtual){
					if(this.ranhuraQuadU.getAngulo() == 90){
						xParaDescerAp = 0;
					}else{
						xParaDescerAp = Math.tan(alfa)*(-zAtual)+allowanceBottom;
					}
					xTemp = xAtual;
				}
				else{
					z = profundidade + zAtual;
					if(Math.asin((R-z)/(R)) != Math.PI/2){
						xParaDescerAp = xParaDescerAp + Math.tan(Math.asin((R-z)/(R)))*apUtilizado+allowanceBottom;
					}
					else{
						ptoFinal = xTemp + R*Math.cos(alfa); 
						xParaDescerAp = ptoFinal - xAtual + allowanceBottom;
					}
//					
//					z = profundidade + zAtual;
//					xParaDescerAp = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
//				
//					if(this.ranhuraQuadU.getAngulo() == 90){
//						xParaDescerAp = 0;
//					}else{
//						xParaDescerAp = Math.tan(alfa)*(-zAtual)+allowanceBottom;
//					}
				}
				
				if(fundo){
					xAtual = this.ws.getCondicoesUsinagem().getAe()+this.ranhuraQuadU.getLargura()+this.ranhuraQuadU.getPosicaoX()-xParaDescerAp-diametroFerramenta/2;
					fundo = false;
				}
				else{
					xAtual = this.ranhuraQuadU.getPosicaoX()+xParaDescerAp-this.ws.getCondicoesUsinagem().getAe()+diametroFerramenta/2;
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
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()>this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-diametroFerramenta/2){
								aeUtilizado = this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-diametroFerramenta/2-xAtual;
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual+this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoX()+this.ranhuraQuadU.getLargura()-xParaDescerAp-diametroFerramenta/2){
									terminouXY = true;
								}
							}
							xAtual = xAtual + aeUtilizado;
							}
						else{
							if(xAtual-this.ws.getCondicoesUsinagem().getAe()<this.ranhuraQuadU.getPosicaoX()+xParaDescerAp+diametroFerramenta/2){
								aeUtilizado = xAtual-xParaDescerAp-diametroFerramenta/2-this.ranhuraQuadU.getPosicaoX();
								terminouXY = true;
							}
							else{
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual-this.ws.getCondicoesUsinagem().getAe()==this.ranhuraQuadU.getPosicaoX()+xParaDescerAp+diametroFerramenta/2)
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
			if(this.ranhuraQuadU.getEixo()==Ranhura.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
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

					if(this.ranhuraQuadU.getAngulo() == 90){
						yParaDescerAp = 0;
					}else{
						yParaDescerAp = Math.tan(alfa)*(-zAtual)+allowanceBottom;
						}
					
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
		
		double xAtual = 0;
		double xProximo = 0;
		double yAtual = 0;
		double yProximo = 0;
		double zAtual;
		double zLimite;
		double apUtilizado;
		double aeUtilizado;
		double xTemp=0;
		double andarX;
		double ptoFinal;
		double temp=0;
		double temp1=0;
		double z;
		double alfa = this.ranhuraQuadU.getAngulo() * Math.PI / 180;
		double largura  = this.ranhuraQuadU.getLargura();
		double largura2 = this.ranhuraQuadU.getLargura2();
		double R = this.ranhuraQuadU.getRaio();
		double r = this.ferramenta.getEdgeRadius();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double profundidade = this.ranhuraQuadU.getProfundidade();
		double x = R*Math.cos(alfa);
		double tempFinal=0;
		int i=0;
		
		boolean terminouTudo = false;
		boolean terminouX = true;
		boolean terminouZ = false;
		boolean terminouZ1 = true;
		boolean terminouZ2 = true;
		boolean terminouZ3 = true;
		boolean vaiVolta = true;
				
		Point3d pontoInicial;
		Point3d pontoFinal;
				
		zAtual = -r*(1-Math.cos(alfa));
		zLimite = profundidade+R*(Math.sin(alfa)-1);
		xAtual = diametroFerramenta/2 + this.ranhuraQuadU.getPosicaoX() + (r*(1-Math.sin((Math.PI/2)-alfa)));
		yAtual = diametroFerramenta/2 + this.ranhuraQuadU.getPosicaoY() + (r*(1-Math.sin((Math.PI/2)-alfa)));
		
		
		if(this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){

			/****************** DESCIDA LINEAR AT� COME�AR A CURVATURA ***********************/
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),this.ranhuraQuadU.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			
			while(!terminouTudo){

				while(!terminouZ){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= zLimite){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						temp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						i=1;
						if(-zAtual == zLimite){
							terminouZ1 = false;
							terminouZ = true;
						}
					}
					else{
						apUtilizado = zLimite + zAtual;
						tempFinal = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						terminouZ = true;
						terminouZ1 = false;
						i=0;
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

					if(i==0){
						xAtual = xAtual + tempFinal;
					}
					else{
						xAtual = xAtual + temp;
					}
					xTemp = xAtual;
										
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
				}
				
				/*********************  ACABAMENTO DA CURVA  DESCIDA   *********************/
				while(!terminouZ1){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == profundidade){
							terminouX = false;
							terminouZ1 = true;
						}
					}
					else{
						apUtilizado = profundidade + zAtual;
						terminouX = false;
						terminouZ1 = true;
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
					
					z = profundidade + zAtual;
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
						andarX = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					else{
						ptoFinal = xTemp + R*Math.cos(alfa); 
						andarX = ptoFinal - xAtual;
						temp1 = andarX;
					}
					
					xAtual = xAtual + andarX;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
				}
				/************************ ACABAMENTO DA LARGURA 2 ****************************/

				x = this.ranhuraQuadU.getPosicaoX()+largura/2-xAtual;
				largura2 = xAtual + 2*x;

				while(!terminouX){
					if(xAtual+this.ws.getCondicoesUsinagem().getAe() <= largura2){
						aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
						if(xAtual+this.ws.getCondicoesUsinagem().getAe() == largura2){
							terminouZ2 = false;
							terminouX = true;
						}
					}
					else{
						aeUtilizado = largura2 - xAtual;
						terminouZ2 = false;
						terminouX = true;
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
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
				}
				
				/********************** ACABAMENTO DA CURVA SUBIDA **********************/
				while(!terminouZ2){
					if(-(zAtual+this.ws.getCondicoesUsinagem().getAp()) >= zLimite){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual+this.ws.getCondicoesUsinagem().getAp()) == zLimite){
							terminouZ3 = false;
							terminouZ2 = true;
						}
					}
					else{
						apUtilizado = -(zLimite + zAtual);
						terminouZ3 = false;
						terminouZ2 = true;
					}
					
					z = profundidade + zAtual;
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
						andarX = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					else{
						andarX = temp1;
					}
					
					xAtual = xAtual + andarX;
					
					
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

					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
				}				
				/********************** SUBIDA LINEAR **********************/
				while(!terminouZ3){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) >= r*(1-Math.sin((Math.PI/2)-alfa))){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == r*(1-Math.sin((Math.PI/2)-alfa))){
							terminouTudo = true;
							terminouZ3 = true;
						}
					}
					else{
						apUtilizado = -zAtual -r*(1-Math.sin((Math.PI/2)-alfa));
						terminouTudo = true;
						terminouZ3 = true;
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
					
					if(i == 0){
						xAtual = xAtual + tempFinal;
						i++;
					}
					else{
						xAtual = xAtual + temp;
					}
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);	
				}
			}
		}
		else if(this.ranhuraQuadU.getEixo() == Ranhura.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){

			/****************** DESCIDA LINEAR AT� COME�AR A CURVATURA ***********************/
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),this.ranhuraQuadU.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			
			while(!terminouTudo){

				while(!terminouZ){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= zLimite){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						temp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						i=1;
						if(-zAtual == zLimite){
							terminouZ1 = false;
							terminouZ = true;
						}
					}
					else{
						apUtilizado = zLimite + zAtual;
						tempFinal = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						terminouZ = true;
						terminouZ1 = false;
						i=0;
					}
					zAtual = zAtual - apUtilizado;

					if(vaiVolta){
						xAtual   = 0;
						xProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual   = this.ranhuraQuadU.getComprimento();
						xProximo = 0;
						vaiVolta = true;
					}

					if(i==0){
						yAtual = yAtual + tempFinal;
					}
					else{
						yAtual = yAtual + temp;
					}
					xTemp = yAtual;
										
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}
				
				/*********************  ACABAMENTO DA CURVA  DESCIDA   *********************/
				while(!terminouZ1){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == profundidade){
							terminouX = false;
							terminouZ1 = true;
						}
					}
					else{
						apUtilizado = profundidade + zAtual;
						terminouX = false;
						terminouZ1 = true;
					}
					zAtual = zAtual - apUtilizado;

					if(vaiVolta){
						xAtual   = 0;
						xProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual   = this.ranhuraQuadU.getComprimento();
						xProximo = 0;
						vaiVolta = true;
					}
					
					z = profundidade + zAtual;
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
						andarX = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					else{
						ptoFinal = xTemp + R*Math.cos(alfa); 
						andarX = ptoFinal - yAtual;
						temp1 = andarX;
					}
					
					yAtual = yAtual + andarX;
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}
				/************************ ACABAMENTO DA LARGURA 2 ****************************/

				x = this.ranhuraQuadU.getPosicaoY()+largura/2-yAtual;
				largura2 = yAtual + 2*x;

				while(!terminouX){
					if(yAtual+this.ws.getCondicoesUsinagem().getAe() <= largura2){
						aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
						if(yAtual+this.ws.getCondicoesUsinagem().getAe() == largura2){
							terminouZ2 = false;
							terminouX = true;
						}
					}
					else{
						aeUtilizado = largura2 - yAtual;
						terminouZ2 = false;
						terminouX = true;
					}
					yAtual = yAtual + aeUtilizado;

					if(vaiVolta){
						xAtual   = 0;
						xProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual   = this.ranhuraQuadU.getComprimento();
						xProximo = 0;
						vaiVolta = true;
					}
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}
				
				/********************** ACABAMENTO DA CURVA SUBIDA **********************/
				while(!terminouZ2){
					if(-(zAtual+this.ws.getCondicoesUsinagem().getAp()) >= zLimite){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual+this.ws.getCondicoesUsinagem().getAp()) == zLimite){
							terminouZ3 = false;
							terminouZ2 = true;
						}
					}
					else{
						apUtilizado = -(zLimite + zAtual);
						terminouZ3 = false;
						terminouZ2 = true;
					}
					
					z = profundidade + zAtual;
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
						andarX = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					else{
						andarX = temp1;
					}
					
					yAtual = yAtual + andarX;
					
					
					zAtual = zAtual + apUtilizado;

					if(vaiVolta){
						xAtual   = 0;
						xProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual   = this.ranhuraQuadU.getComprimento();
						xProximo = 0;
						vaiVolta = true;
					}

					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}				
				/********************** SUBIDA LINEAR **********************/
				while(!terminouZ3){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) >= r*(1-Math.sin((Math.PI/2)-alfa))){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == r*(1-Math.sin((Math.PI/2)-alfa))){
							terminouTudo = true;
							terminouZ3 = true;
						}
					}
					else{
						apUtilizado = -zAtual -r*(1-Math.sin((Math.PI/2)-alfa));
						terminouTudo = true;
						terminouZ3 = true;
					}
					zAtual = zAtual + apUtilizado;

					if(vaiVolta){
						xAtual   = 0;
						xProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual   = this.ranhuraQuadU.getComprimento();
						xProximo = 0;
						vaiVolta = true;
					}
					
					if(i == 0){
						yAtual = yAtual + tempFinal;
						i++;
					}
					else{
						yAtual = yAtual + temp;
					}
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}
			}
		}
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
