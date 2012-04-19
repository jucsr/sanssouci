package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilRoundedU {

	private Workingstep ws;
	private Ranhura ranhuraRoundedU;
	private Ferramenta ferramenta;
	
	public MovimentacaoRanhuraPerfilRoundedU(Workingstep ws){
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.ranhuraRoundedU = (RanhuraPerfilRoundedU) this.ws.getFeature();
	}
	
	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		
		double zLimite;
		double profundidade=this.ranhuraRoundedU.getProfundidade();
		double R=this.ranhuraRoundedU.getLargura()/2;
		double xAtual;
		double xProximo;
		double yAtual;
		double yProximo;
		double zAtual=this.ranhuraRoundedU.getPosicaoZ();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double ultimoApLinear,ultimoApCurva;
		double numeroDeApsLinear, numeroDeApsCurva;
		double LimiteDireita, LimiteEsquerda, largura=0,z,temp;
		double aeUtilizado;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double meio;
		int i;
		
		
		boolean fundo = false;
		boolean vaiVolta = true;
		boolean terminouXY;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		zLimite = profundidade-R;
		ultimoApLinear= zLimite%ap;
		numeroDeApsLinear = (zLimite-ultimoApLinear)/ap;
		ultimoApCurva= R%ap;
		numeroDeApsCurva = (R-ultimoApCurva)/ap;
			
		xAtual = this.ranhuraRoundedU.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
		yAtual = this.ranhuraRoundedU.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
		
		if(this.ranhuraRoundedU.getEixo() == RanhuraPerfilRoundedU.VERTICAL && this.ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
			pontoInicial = new Point3d(xAtual,this.ranhuraRoundedU.getPosicaoY(),this.ws.getOperation().getRetractPlane());//ponto inicializador do Array

			meio=this.ranhuraRoundedU.getPosicaoX()+this.ranhuraRoundedU.getLargura()/2;
			LimiteDireita=this.ranhuraRoundedU.getPosicaoX()+this.ranhuraRoundedU.getLargura()-allowanceBottom-diametroFerramenta/2;
			LimiteEsquerda=this.ranhuraRoundedU.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
			
			if(zLimite!=zAtual){
				for(i=(int)numeroDeApsLinear;i>0;i--){
					zAtual -= ap;//desce a ferramenta
					/******************MOVIMENTACAO EM Y***********************/
					if(vaiVolta){
						yAtual = this.ranhuraRoundedU.getPosicaoY();
						yProximo = this.ranhuraRoundedU.getComprimento();
						vaiVolta = false;
					}
					else{
						yAtual = this.ranhuraRoundedU.getComprimento();
						yProximo = this.ranhuraRoundedU.getPosicaoY();
						vaiVolta = true;
					}
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(xAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(xAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
								fundo = true;
							}
							xAtual += aeUtilizado;
						}
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						desbaste.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
				}
				/****************MOVIMENTACAO DA PARTE QUE FALTA************************/
				if(ultimoApLinear>0){
					zAtual -= ultimoApLinear;
					/******************MOVIMENTACAO EM Y***********************/
					if(vaiVolta){
						yAtual = this.ranhuraRoundedU.getPosicaoY();
						yProximo = this.ranhuraRoundedU.getComprimento();
						vaiVolta = false;
					}
					else{
						yAtual = this.ranhuraRoundedU.getComprimento();
						yProximo = this.ranhuraRoundedU.getPosicaoY();
						vaiVolta = true;
					}/**********************************************************/

					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(xAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(xAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
								fundo = true;
							}
							xAtual += aeUtilizado;
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
			/*********************MOVIMENTACAO DA PARTE CURVA*****************/
			for(i=(int)numeroDeApsCurva;i>0;i--){
				zAtual -= ap;//desce a ferramenta
				z=profundidade + zAtual;
				if(Math.asin((R-z)/(R)) != Math.PI/2)
					temp = Math.tan(Math.asin((R-z)/(R)))*ap;
				else
					if(fundo)
						temp = xAtual-meio;
					else
						temp = meio-xAtual;
				
				LimiteDireita -= temp;
				LimiteEsquerda += temp;
				largura = LimiteDireita-LimiteEsquerda;
				
				if(largura>=diametroFerramenta){// verifica se a ferramenta cabe
					if(fundo)//adiciona o tanto que andou na curva
						xAtual -= temp;
					else
						xAtual += temp;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);
					
					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(xAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(xAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
								fundo = true;
							}
							xAtual += aeUtilizado;
						}
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
				}
				else
					i=0;
			}
			/**************************MOVIMENTACAO DA PARTE QUE FALTA*******************/
			if(ultimoApCurva>0){

				zAtual -= ultimoApCurva;
				z=profundidade + zAtual;
				
				if(fundo)
					temp = xAtual-meio;
				else
					temp = meio-xAtual;
				
				if(largura>=diametroFerramenta){
					if(fundo)
						xAtual -= temp;
					else
						xAtual += temp;

					LimiteDireita -= temp;
					LimiteEsquerda += temp;
					largura = LimiteDireita-LimiteEsquerda;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(xAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(xAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
								fundo = true;
							}
							xAtual += aeUtilizado;
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
			
		}
		else if(this.ranhuraRoundedU.getEixo() == RanhuraPerfilRoundedU.HORIZONTAL && this.ws.getOperation().getClass() == BottomAndSideRoughMilling.class){

			pontoInicial = new Point3d(this.ranhuraRoundedU.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());//ponto inicializador do Array

			meio=this.ranhuraRoundedU.getPosicaoY()+this.ranhuraRoundedU.getLargura()/2;
			LimiteDireita=this.ranhuraRoundedU.getPosicaoY()+this.ranhuraRoundedU.getLargura()-allowanceBottom-diametroFerramenta/2;
			LimiteEsquerda=this.ranhuraRoundedU.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
			
			if(zLimite!=zAtual){
				for(i=(int)numeroDeApsLinear;i>0;i--){
					zAtual -= ap;//desce a ferramenta
					/******************MOVIMENTACAO EM Y***********************/
					if(vaiVolta){
						xAtual = this.ranhuraRoundedU.getPosicaoX();
						xProximo = this.ranhuraRoundedU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.ranhuraRoundedU.getComprimento();
						xProximo = this.ranhuraRoundedU.getPosicaoX();
						vaiVolta = true;
					}
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(yAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(yAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = yAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							yAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(yAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(yAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-yAtual;
								terminouXY = true;
								fundo = true;
							}
							yAtual += aeUtilizado;
						}
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						desbaste.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
				}
				/****************MOVIMENTACAO DA PARTE QUE FALTA************************/
				if(ultimoApLinear>0){
					zAtual -= ultimoApLinear;
					/******************MOVIMENTACAO EM Y***********************/
					if(vaiVolta){
						xAtual = this.ranhuraRoundedU.getPosicaoX();
						xProximo = this.ranhuraRoundedU.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.ranhuraRoundedU.getComprimento();
						xProximo = this.ranhuraRoundedU.getPosicaoX();
						vaiVolta = true;
					}/**********************************************************/

					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(yAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(yAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = yAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							yAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(yAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(yAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-yAtual;
								terminouXY = true;
								fundo = true;
							}
							yAtual += aeUtilizado;
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
			/*********************MOVIMENTACAO DA PARTE CURVA*****************/
			for(i=(int)numeroDeApsCurva;i>0;i--){
				zAtual -= ap;//desce a ferramenta
				z=profundidade + zAtual;
				if(Math.asin((R-z)/(R)) != Math.PI/2)
					temp = Math.tan(Math.asin((R-z)/(R)))*ap;
				else
					if(fundo)
						temp = yAtual-meio;
					else
						temp = meio-yAtual;
				
				LimiteDireita -= temp;
				LimiteEsquerda += temp;
				largura = LimiteDireita-LimiteEsquerda;
				
				if(largura>=diametroFerramenta){// verifica se a ferramenta cabe
					if(fundo)//adiciona o tanto que andou na curva
						yAtual -= temp;
					else
						yAtual += temp;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);
					
					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(yAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(yAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = yAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							yAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(yAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(yAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-yAtual;
								terminouXY = true;
								fundo = true;
							}
							yAtual += aeUtilizado;
						}
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
				}
				else
					i=0;
			}
			/**************************MOVIMENTACAO DA PARTE QUE FALTA*******************/
			if(ultimoApCurva>0){

				zAtual -= ultimoApCurva;
				z=profundidade + zAtual;
				
				if(fundo)
					temp = yAtual-meio;
				else
					temp = meio-yAtual;
				
				if(largura>=diametroFerramenta){
					if(fundo)
						yAtual -= temp;
					else
						yAtual += temp;

					LimiteDireita -= temp;
					LimiteEsquerda += temp;
					largura = LimiteDireita-LimiteEsquerda;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					terminouXY = false;
					while(!terminouXY){
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/****************MOVIMENTACAO EM X*************************/
						if(fundo){//movimentacao da direita pra esquerda
							if(yAtual-ae>=LimiteEsquerda){
								aeUtilizado = ae;
								if(yAtual-ae==LimiteEsquerda){
									terminouXY = true;
									fundo = false;
								}
							}
							else{
								aeUtilizado = yAtual-LimiteEsquerda;
								terminouXY = true;
								fundo = false;
							}
							yAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(yAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(yAtual+ae==LimiteDireita){
									terminouXY = true;
									fundo = true;
								}
							}
							else{
								aeUtilizado = LimiteDireita-yAtual;
								terminouXY = true;
								fundo = true;
							}
							yAtual += aeUtilizado;
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
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
		double zLimite;
		double profundidade=this.ranhuraRoundedU.getProfundidade();
		double R=this.ranhuraRoundedU.getLargura()/2;
		double xAtual;
		double xProximo=0;
		double yAtual;
		double yProximo=0;
		double zAtual=this.ranhuraRoundedU.getPosicaoZ();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double ultimoApLinear,ultimoApCurva;
		double numeroDeApsLinear, numeroDeApsCurva;
		double z,temp=0;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double meio;
		double r=this.ferramenta.getDiametroFerramenta()/2;
		int i;
		
		boolean vaiVolta = true;
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		zLimite = profundidade-R;
		ultimoApLinear= zLimite%ap;
		numeroDeApsLinear = (zLimite-ultimoApLinear)/ap;
		ultimoApCurva= (R-r)%ap;
		numeroDeApsCurva = ((R-r)-ultimoApCurva)/ap;	
		
		xAtual = this.ranhuraRoundedU.getPosicaoX()+diametroFerramenta/2;
		yAtual = this.ranhuraRoundedU.getPosicaoY()+diametroFerramenta/2;
		
		if(this.ranhuraRoundedU.getEixo() == RanhuraPerfilRoundedU.VERTICAL && this.ws.getOperation().getClass() == BottomAndSideFinishMilling.class){
			pontoInicial = new Point3d(xAtual,this.ranhuraRoundedU.getPosicaoY(),this.ws.getOperation().getRetractPlane());//ponto inicializador do Array

			meio=this.ranhuraRoundedU.getPosicaoX()+this.ranhuraRoundedU.getLargura()/2;

			if(R==r && this.ferramenta.getClass()==BallEndMill.class){
				xAtual = meio;
				zAtual = -zLimite-r-this.ranhuraRoundedU.getPosicaoZ();
				/******************MOVIMENTACAO EM Y***********************/
				if(vaiVolta){
					yAtual = this.ranhuraRoundedU.getPosicaoY();
					yProximo = this.ranhuraRoundedU.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhuraRoundedU.getComprimento();
					yProximo = this.ranhuraRoundedU.getPosicaoY();
					vaiVolta = true;
				}
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTempp);
				LinearPath horizontalTemp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);
			}
			else{
				/****************************************************MOVIMENTACAO DA PARTE LINEAR***********************************************************/
				if(zLimite!=zAtual && this.ferramenta.getClass() == EndMill.class){
					for(i=(int)numeroDeApsLinear;i>0;i--){
						zAtual -= ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/*********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					/****************MOVIMENTACAO DA PARTE QUE FALTA************************/
					if(ultimoApLinear>0){
						zAtual -= ultimoApLinear;
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/

						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
					}				
					/********VAI PARA O PLANO SEGURO*/

					zAtual = this.ranhuraRoundedU.getPosicaoZ();
					xAtual = this.ranhuraRoundedU.getPosicaoX()+this.ranhuraRoundedU.getLargura()-diametroFerramenta/2;//muda o x de lado!
					
					Point3d ultimoPonto = acabamento.get(acabamento.size()-1).getFinalPoint();
					LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (xAtual, yProximo ,this.ws.getOperation().getRetractPlane()));
					planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
					acabamento.add(planoSeguro);
					/*******************************/
					pontoInicial = new Point3d(xAtual, yProximo, this.ws.getOperation().getRetractPlane());
					
					/*****************MOVIMENTACAO DA SUBIDA LINEAR*******************/
					for(i=(int)numeroDeApsLinear;i>0;i--){
						zAtual -= ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/*********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					if(ultimoApLinear>0){
						zAtual -= ultimoApLinear;
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/

						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
				}
/****************************************************MOVIMENTACAO DA PARTE CURVA************************************************************/
				if(this.ferramenta.getClass()==BallEndMill.class){

					zAtual = -zLimite-r-this.ranhuraRoundedU.getPosicaoZ();
					
					pontoFinal = new Point3d(xAtual, this.ranhuraRoundedU.getPosicaoY(), zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					pontoInicial = new Point3d(xAtual, this.ranhuraRoundedU.getPosicaoY(), zAtual);
						
					for(i=(int)numeroDeApsCurva;i>0;i--){
						/******CALCULO DE QUANTO VAI ANDAR O X NA PARTE CURVA********/
						zAtual -= ap;//desce a ferramenta
						z=profundidade + zAtual;
						if(Math.asin((R-z-r)/(R-r))!=Math.PI/2)
							temp = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*ap);
						else
							temp = meio-xAtual;
						xAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					/**************************MOVIMENTACAO DA PARTE QUE FALTA*******************/
					if(ultimoApCurva>0){
						zAtual -= ultimoApCurva;

						temp = meio-xAtual;
						xAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					/***********************MOVIMENTACAO DA SUBIDA CURVA********************/
					if(ultimoApCurva>0){
						zAtual += ultimoApCurva;
						xAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
					/**************************************************************/
					for(i=(int)numeroDeApsCurva;i>0;i--){
						/******CALCULO DE QUANTO VAI ANDAR O X NA PARTE CURVA********/
						z=profundidade + zAtual;
						if(Math.asin((R-z-r)/(R-r))!=Math.PI/2)
							temp = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*ap);
						
						xAtual +=temp;
						zAtual += ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							yAtual = this.ranhuraRoundedU.getPosicaoY();
							yProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhuraRoundedU.getComprimento();
							yProximo = this.ranhuraRoundedU.getPosicaoY();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					}
				}
			}
			/**********************************************************************************************************/
		}
		else if(this.ranhuraRoundedU.getEixo() == RanhuraPerfilRoundedU.HORIZONTAL && this.ws.getOperation().getClass() == BottomAndSideFinishMilling.class){
			pontoInicial = new Point3d(this.ranhuraRoundedU.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());//ponto inicializador do Array

			meio=this.ranhuraRoundedU.getPosicaoY()+this.ranhuraRoundedU.getLargura()/2;

			if(R==r && this.ferramenta.getClass()==BallEndMill.class){
				yAtual = meio;
				zAtual = -zLimite-r-this.ranhuraRoundedU.getPosicaoZ();
				/******************MOVIMENTACAO EM Y***********************/
				if(vaiVolta){
					xAtual = this.ranhuraRoundedU.getPosicaoX();
					xProximo = this.ranhuraRoundedU.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhuraRoundedU.getComprimento();
					xProximo = this.ranhuraRoundedU.getPosicaoX();
					vaiVolta = true;
				}
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTempp);
				LinearPath horizontalTemp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
			}
			else{
				/****************************************************MOVIMENTACAO DA PARTE LINEAR***********************************************************/
				if(zLimite!=zAtual && this.ferramenta.getClass() == EndMill.class){
					for(i=(int)numeroDeApsLinear;i>0;i--){
						zAtual -= ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/*********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
					/****************MOVIMENTACAO DA PARTE QUE FALTA************************/
					if(ultimoApLinear>0){
						zAtual -= ultimoApLinear;
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/

						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
					}				
					/********VAI PARA O PLANO SEGURO*/

					zAtual = this.ranhuraRoundedU.getPosicaoZ();
					yAtual = this.ranhuraRoundedU.getPosicaoY()+this.ranhuraRoundedU.getLargura()-diametroFerramenta/2;//muda o x de lado!
					
					Point3d ultimoPonto = acabamento.get(acabamento.size()-1).getFinalPoint();
					LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (xProximo, yAtual,this.ws.getOperation().getRetractPlane()));
					planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
					acabamento.add(planoSeguro);
					/*******************************/
					pontoInicial = new Point3d(xProximo, yAtual, this.ws.getOperation().getRetractPlane());
					
					/*****************MOVIMENTACAO DA SUBIDA LINEAR*******************/
					for(i=(int)numeroDeApsLinear;i>0;i--){
						zAtual -= ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/*********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
					if(ultimoApLinear>0){
						zAtual -= ultimoApLinear;
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/

						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual	, zAtual);
					}
				}
/****************************************************MOVIMENTACAO DA PARTE CURVA************************************************************/
				if(this.ferramenta.getClass()==BallEndMill.class){

					zAtual = -zLimite-r-this.ranhuraRoundedU.getPosicaoZ();
					
					pontoFinal = new Point3d(this.ranhuraRoundedU.getPosicaoX(), yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					acabamento.add(verticalTemp);
					pontoInicial = new Point3d(this.ranhuraRoundedU.getPosicaoX(),yAtual, zAtual);
						
					for(i=(int)numeroDeApsCurva;i>0;i--){
						/******CALCULO DE QUANTO VAI ANDAR O X NA PARTE CURVA********/
						zAtual -= ap;//desce a ferramenta
						z=profundidade + zAtual;
						if(Math.asin((R-z-r)/(R-r))!=Math.PI/2)
							temp = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*ap);
						else
							temp = meio-yAtual;
						yAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
					/**************************MOVIMENTACAO DA PARTE QUE FALTA*******************/
					if(ultimoApCurva>0){
						zAtual -= ultimoApCurva;

						temp = meio-xAtual;
						yAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
					/***********************MOVIMENTACAO DA SUBIDA CURVA********************/
					if(ultimoApCurva>0){
						zAtual += ultimoApCurva;
						yAtual += temp;

						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
					/**************************************************************/
					for(i=(int)numeroDeApsCurva;i>0;i--){
						/******CALCULO DE QUANTO VAI ANDAR O X NA PARTE CURVA********/
						z=profundidade + zAtual;
						if(Math.asin((R-z-r)/(R-r))!=Math.PI/2)
							temp = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*ap);
						
						yAtual +=temp;
						zAtual += ap;//desce a ferramenta
						/******************MOVIMENTACAO EM Y***********************/
						if(vaiVolta){
							xAtual = this.ranhuraRoundedU.getPosicaoX();
							xProximo = this.ranhuraRoundedU.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.ranhuraRoundedU.getComprimento();
							xProximo = this.ranhuraRoundedU.getPosicaoX();
							vaiVolta = true;
						}
						/**********************************************************/
						pontoFinal = new Point3d(xAtual, yAtual, zAtual);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						acabamento.add(verticalTempp);
						LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
						acabamento.add(horizontalTempp);
						pontoInicial = new Point3d(xProximo, yAtual, zAtual);
					}
				}
			}
			/**********************************************************************************************************/
		}
		
		return acabamento;
	}
	
	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhuraPerfilRoundedU() {
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
		
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhuraPerfilRoundedU() {
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
