package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
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
		double xProximo=0;
		double yAtual = this.ranhuraQuadU.getPosicaoY();
		double yProximo=0;
		double zAtual = this.ranhuraQuadU.getPosicaoZ();
		double aeUtilizado=0;
		double apUtilizado=this.ws.getCondicoesUsinagem().getAp();
		double xParaDescerAp=0;
		double yParaDescerAp=0;
		double alfa = this.ranhuraQuadU.getAngulo() * Math.PI / 180;
		double allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double zInicioDaCurva;
		double profundidade = this.ranhuraQuadU.getProfundidade();
		double R = this.ranhuraQuadU.getRaio();
		double diametroFerramenta = this.ferramenta.getDiametroFerramenta();
		double z=0;
		double ptoFinal=0;
		double xTemp=0;
		double xLimiteEsquerda, xLimiteDireita;
		double yLimiteEsquerda, yLimiteDireita;
		double yTemp=0;
		double andarX;
		int i;
		int p=0;
		
		boolean terminouZLimite = false;
		boolean terminouXY = false;
		boolean vaiVolta = true;
		boolean fundo = false;
		
		Point3d pontoInicial;
		Point3d pontoFinal;

		zInicioDaCurva = profundidade+R*(Math.sin(alfa)-1);
		
		
		if(zInicioDaCurva != zAtual){
			if(this.ranhuraQuadU.getAngulo() == 90)
				andarX = 0;
			else
				andarX = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
		}
		else
			andarX = 0;
		
		
		xAtual = this.ranhuraQuadU.getPosicaoX() + allowanceBottom + diametroFerramenta/2 + andarX;
		yAtual = this.ranhuraQuadU.getPosicaoY() + allowanceBottom + diametroFerramenta/2 + andarX;
		
		if (this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class) {
			pontoInicial = new Point3d(xAtual,this.ranhuraQuadU.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			
			xAtual = this.ranhuraQuadU.getPosicaoX() + allowanceBottom + diametroFerramenta/2;
			xLimiteEsquerda = xAtual;
			xLimiteDireita = this.ranhuraQuadU.getPosicaoX() + this.ranhuraQuadU.getLargura() - allowanceBottom - diametroFerramenta/2;
		
			while (!terminouZLimite) {
				if(-zAtual<zInicioDaCurva){
					if(this.ws.getCondicoesUsinagem().getAp()-zAtual<=zInicioDaCurva)
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					else
						apUtilizado = zAtual + zInicioDaCurva;
				}
				else{
					if (this.ws.getCondicoesUsinagem().getAp() - zAtual <= profundidade) {
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if (this.ws.getCondicoesUsinagem().getAp() - zAtual == profundidade)
							terminouZLimite = true;
					} else {
						apUtilizado = zAtual + profundidade;
						terminouZLimite = true;
					}
				}
				zAtual = zAtual - apUtilizado;
					
				
				if(zInicioDaCurva >= -zAtual){
					if(this.ranhuraQuadU.getAngulo() == 90){
						xParaDescerAp = 0;
						xTemp = xAtual;
					}else{
						xParaDescerAp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						xTemp = xAtual;					
					} 
					if(fundo){
						ptoFinal = xTemp - R*Math.cos(alfa);
					}
					else{
						ptoFinal = xTemp + R*Math.cos(alfa);
					}
				}
				else{
					z = profundidade + zAtual;
					if(Math.asin((R-z)/(R)) != Math.PI/2){
						xParaDescerAp = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
					}
					else{
						if(fundo){
							xParaDescerAp = xAtual - ptoFinal;
						}
						else{							
							xParaDescerAp = ptoFinal - xAtual;
						}
					}
				}
								
				xLimiteDireita = xLimiteDireita - xParaDescerAp;
				xLimiteEsquerda = xLimiteEsquerda + xParaDescerAp;
				
				terminouXY = false;
				i=1;
				
				if(p==0){
					p++;
					xAtual = xAtual + xParaDescerAp;
					yAtual = this.ranhuraQuadU.getPosicaoY();
					yProximo = this.ranhuraQuadU.getComprimento();
					vaiVolta = false;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				}

					while (!terminouXY) {
						if(i==0){

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
								if(xAtual+this.ws.getCondicoesUsinagem().getAe()>xLimiteDireita){	
									aeUtilizado = xLimiteDireita - xAtual;
									terminouXY = true;
								}
								else{
									aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
									if(xAtual+this.ws.getCondicoesUsinagem().getAe()==xLimiteDireita){	
										terminouXY = true;
									}
								}
								xAtual = xAtual + aeUtilizado;
							}
							else{
								if(xAtual-this.ws.getCondicoesUsinagem().getAe()<xLimiteEsquerda){
									aeUtilizado = xAtual - xLimiteEsquerda;
									terminouXY = true;
								}
								else{
									aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
									if(xAtual-this.ws.getCondicoesUsinagem().getAe()==xLimiteEsquerda)	
										terminouXY = true;
								}
								xAtual = xAtual-aeUtilizado;
							}
						}
						else{
							if(fundo){
								xAtual = xAtual - xParaDescerAp;
								fundo = false;
							}
							else{
								xAtual = xAtual + xParaDescerAp;
								fundo = true;
							}
							i=0;
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
				pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),yAtual,this.ranhuraQuadU.getPosicaoZ());
				
				yAtual = this.ranhuraQuadU.getPosicaoY() + allowanceBottom + diametroFerramenta/2;
				yLimiteEsquerda = yAtual;
				yLimiteDireita = this.ranhuraQuadU.getPosicaoY() + this.ranhuraQuadU.getLargura() - allowanceBottom - diametroFerramenta/2;
			
				while (!terminouZLimite) {
					if(-zAtual<zInicioDaCurva){
						if(this.ws.getCondicoesUsinagem().getAp()-zAtual<=zInicioDaCurva)
							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						else
							apUtilizado = zAtual + zInicioDaCurva;
					}
					else{
						if (this.ws.getCondicoesUsinagem().getAp() - zAtual <= profundidade) {
							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
							if (this.ws.getCondicoesUsinagem().getAp() - zAtual == profundidade)
								terminouZLimite = true;
						} else {
							apUtilizado = zAtual + profundidade;
							terminouZLimite = true;
						}
					}
					zAtual = zAtual - apUtilizado;
						
					if(zInicioDaCurva >= -zAtual){
						if(this.ranhuraQuadU.getAngulo() == 90){
							yParaDescerAp = 0;
							yTemp = yAtual;
						}else{
							yParaDescerAp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
							yTemp = yAtual;					
						} 
						if(fundo){
							ptoFinal = yTemp - R*Math.cos(alfa);
						}
						else{
							ptoFinal = yTemp + R*Math.cos(alfa);
						}
					}
					else{
						z = profundidade + zAtual;
						if(Math.asin((R-z)/(R)) != Math.PI/2){
							yParaDescerAp = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
						}
						else{
							if(fundo){
								yParaDescerAp = yAtual - ptoFinal;
							}
							else{							
								yParaDescerAp = ptoFinal - yAtual;
							}
						}
					}
					
					yLimiteDireita = yLimiteDireita - yParaDescerAp;
					yLimiteEsquerda = yLimiteEsquerda + yParaDescerAp;
					
					terminouXY = false;
					i=1;
					
					if(p==0){
						p++;
						xAtual = xAtual + xParaDescerAp;
						yAtual = this.ranhuraQuadU.getPosicaoY();
						yProximo = this.ranhuraQuadU.getComprimento();
						vaiVolta = false;
						
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					else{
						while (!terminouXY) {
							if(i==0){

								if (vaiVolta) {
									xAtual = this.ranhuraQuadU.getPosicaoY();
									xProximo = this.ranhuraQuadU.getComprimento();
									vaiVolta = false;
								} else {
									xAtual = this.ranhuraQuadU.getComprimento();
									xProximo = this.ranhuraQuadU.getPosicaoY();
									vaiVolta = true;
								}

								if(fundo){
									if(yAtual+this.ws.getCondicoesUsinagem().getAe()>yLimiteDireita){	
										aeUtilizado = yLimiteDireita - yAtual;
										terminouXY = true;
									}
									else{
										aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
										if(yAtual+this.ws.getCondicoesUsinagem().getAe()==yLimiteDireita){	
											terminouXY = true;
										}
									}
									yAtual = yAtual + aeUtilizado;
								}
								else{
									if(yAtual-this.ws.getCondicoesUsinagem().getAe()<yLimiteEsquerda){
										aeUtilizado = yAtual - yLimiteEsquerda;
										terminouXY = true;
									}
									else{
										aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
										if(yAtual-this.ws.getCondicoesUsinagem().getAe()==yLimiteEsquerda)	
											terminouXY = true;
									}
									yAtual = yAtual-aeUtilizado;
								}
							}
							else{
								if(fundo){
									yAtual = yAtual - yParaDescerAp;
									fundo = false;
								}
								else{
									yAtual = yAtual + yParaDescerAp;
									fundo = true;
								}
								i=0;
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
		}
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento() {
		
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
		double xAtual = this.ranhuraQuadU.getPosicaoX();
		double xProximo = 0;
		double yAtual = this.ranhuraQuadU.getPosicaoY();
		double yProximo = 0;
		double zAtual= this.ranhuraQuadU.getPosicaoZ();
		double zLimite;
		double apUtilizado=0;
		double aeUtilizado;
		double xTemp=0;
		double yTemp=0;
		double andarX;
		double andarY;
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
		double y = R*Math.cos(alfa);
		double tempFinal=0;
		double tempAp=0;
		double tempZFinal1=0;
		int contadorAp=0;
		int contador=0;
		int n=0;
		int i=0;
		int p=0;
		
		boolean terminouTudo = false;
		boolean terminouX = true;
		boolean terminouZ = false;
		boolean terminouZ1 = true;
		boolean terminouZ2 = true;
		boolean terminouZ3 = true;
		boolean vaiVolta = true;
				
		Point3d pontoInicial;
		Point3d pontoFinal;
				
		zLimite = profundidade+R*(Math.sin(alfa)-1);
		yAtual = diametroFerramenta/2 + this.ranhuraQuadU.getPosicaoY()-r*Math.cos((Math.PI/2)-alfa);
		
		if(zLimite == 0){
			//if()
		}
		else{
			
		}
		
		
		if(this.ranhuraQuadU.getEixo() == Ranhura.VERTICAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){

			/****************** DESCIDA LINEAR ATÉ COMEÇAR A CURVATURA ***********************/
			pontoInicial = new Point3d(xAtual,this.ranhuraQuadU.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			xAtual = diametroFerramenta/2 + this.ranhuraQuadU.getPosicaoX()-r*Math.cos((Math.PI/2)-alfa);
			
			while(!terminouTudo){
				
				if(p==0){
					contador++;
					p++;
					zAtual = zAtual - (r-(r*Math.cos(alfa)));
					tempZFinal1 = (r-(r*Math.cos(alfa)));

					yAtual   = 0;
					yProximo = this.ranhuraQuadU.getComprimento();
					vaiVolta = false;
					
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				}
				
				else{
					while(!terminouZ){
						contador++;
						n = contador;
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= zLimite){
							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
							temp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
							i=1;
							if(-zAtual == zLimite){
								tempFinal = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
								i=0;
								terminouZ1 = false;
								terminouZ = true;
							}
						}
						else{
							apUtilizado = zLimite + zAtual;
							tempFinal = apUtilizado;
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
				}
				
				/*********************  ACABAMENTO DA CURVA  DESCIDA   *********************/
				while(!terminouZ1){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == profundidade){
							terminouX = false;
							terminouZ1 = true;
							tempAp = apUtilizado;
						}
					}
					else{
						apUtilizado = profundidade + zAtual;
						terminouX = false;
						terminouZ1 = true;
						tempAp = apUtilizado;
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
					
					if(contadorAp == 0){
						zAtual = zAtual + tempAp;
						contadorAp++;
					}
					else{
						zAtual = zAtual + apUtilizado;
					}

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
//					if(-zAtual-this.ws.getCondicoesUsinagem().getAp() >= 0 || contador == 1 || contador == n){//r*(1-Math.sin((Math.PI/2)-alfa))
//						if(contador == 1)
//							apUtilizado = this.ws.getCondicoesUsinagem().getAp()-tempZFinal1;
//						else if(contador == n){
//							apUtilizado = tempFinal;
//							System.out.println(tempFinal);
//						}
//						else
//							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
//						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == 0 || contador == 1){
//							terminouTudo = true;
//							terminouZ3 = true;
//						}
//					}
					
					if(contador == 2 || contador == n){
						if(contador == 2){
							apUtilizado = -zAtual-tempZFinal1;
							terminouTudo = true;
							terminouZ3 = true;
						}
						else
							apUtilizado = tempFinal;
					}
					else{
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					}
					
					
					contador--;
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

			/****************** DESCIDA LINEAR ATÉ COMEÇAR A CURVATURA ***********************/
			pontoInicial = new Point3d(this.ranhuraQuadU.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());
			
			while(!terminouTudo){

				while(!terminouZ){
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) <= zLimite){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						temp = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
						i=1;
						if(-zAtual == zLimite){
							tempFinal = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
							i=0;
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
					yTemp = yAtual;
										
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
							tempAp = apUtilizado;
						}
					}
					else{
						apUtilizado = profundidade + zAtual;
						terminouX = false;
						terminouZ1 = true;
						tempAp = apUtilizado;
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
						andarY = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
					}
					else{
						ptoFinal = yTemp + R*Math.cos(alfa); 
						andarY = ptoFinal - yAtual;
						temp1 = andarY;
					}
					
					yAtual = yAtual + andarY;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					acabamento.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);	
				}
				/************************ ACABAMENTO DA LARGURA 2 ****************************/

				y = this.ranhuraQuadU.getPosicaoY()+largura/2-yAtual;
				largura2 = yAtual + 2*y;
				
				while(!terminouX){
					if(yAtual+this.ws.getCondicoesUsinagem().getAe() <= largura2){
						aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
						if(xAtual+this.ws.getCondicoesUsinagem().getAe() == largura2){
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
					if(Math.asin((R-z)/(R)) != Math.PI/2){
						andarY = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
					}
					else{
						andarY = temp1;
					}
					
					yAtual = yAtual + andarY;
					
					if(contadorAp == 0){
						zAtual = zAtual + tempAp;
						contadorAp++;
					}
					else{
						zAtual = zAtual + apUtilizado;
					}

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
					if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) >= 0){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-(zAtual-this.ws.getCondicoesUsinagem().getAp()) == 0){
							terminouTudo = true;
							terminouZ3 = true;
						}
					}
					else{
						apUtilizado = -zAtual;
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
