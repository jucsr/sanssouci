package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilVee {

	private Workingstep ws;
	private Ferramenta ferramenta;
	private RanhuraPerfilVee ranhuraV;
	
	public MovimentacaoRanhuraPerfilVee(Workingstep ws){
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.ranhuraV = (RanhuraPerfilVee) this.ws.getFeature();
	}
	
	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		
		double xAtual=this.ranhuraV.getPosicaoX();
		double xProximo;
		double yAtual=this.ranhuraV.getPosicaoY();
		double yProximo;
		double zAtual=this.ranhuraV.getPosicaoZ();
		double zLimite;
		double profundidade=this.ranhuraV.getProfundidade();
		double alfa=(this.ranhuraV.getAngulo()/2)*Math.PI/180;
		double R=this.ranhuraV.getRaio();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double andarX;
		double andarY;
		double apUtilizado=0;
		double aeUtilizado=0;
		double xLimiteEsquerda, xLimiteDireita;
		double yLimiteEsquerda, yLimiteDireita;
		double largura;
		double z=profundidade;
		
		boolean terminouZ = false;
		boolean terminouXY;
		boolean vaiVolta = true;
		boolean fundo = false;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		zLimite = profundidade+R*(Math.sin(alfa)-1);
		
		if(zLimite != zAtual){
			if(this.ranhuraV.getAngulo() == 90)
				andarX = 0;
			else
				andarX = apUtilizado*Math.tan(alfa);
		}
		else
			andarX = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;

		xAtual = this.ranhuraV.getPosicaoX()+diametroFerramenta/2+andarX+allowanceBottom;
		yAtual = this.ranhuraV.getPosicaoY()+diametroFerramenta/2+andarX+allowanceBottom;
		
		if(this.ranhuraV.getEixo() == RanhuraPerfilVee.VERTICAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
			
			pontoInicial = new Point3d(xAtual,this.ranhuraV.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			xAtual = this.ranhuraV.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
			xLimiteEsquerda = xAtual+andarX;
			xLimiteDireita = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()-allowanceBottom-diametroFerramenta/2-andarX;
			largura = xLimiteDireita - xLimiteEsquerda;		
				
			while(!terminouZ){
				terminouXY = false;
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp()<=profundidade && largura>=diametroFerramenta){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp()==profundidade || largura==diametroFerramenta){
						terminouZ = true;
					}
				}
				else{
					if(largura>=diametroFerramenta)
						apUtilizado = profundidade + zAtual;
					else{
						apUtilizado = 0;
						terminouXY = true;
					}
					terminouZ = true;
				}
				
				if(vaiVolta){
					yAtual = this.ranhuraV.getPosicaoY();
					yProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhuraV.getComprimento();
					yProximo = this.ranhuraV.getPosicaoY();
					vaiVolta = true;
				}
				
				z=profundidade + zAtual;
				if(-zAtual<=zLimite)
					andarX = apUtilizado*Math.tan(alfa);
				else
					andarX = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
				
				xLimiteDireita = xLimiteDireita - andarX;
				xLimiteEsquerda = xLimiteEsquerda + andarX;
				largura = xLimiteDireita - xLimiteEsquerda;
				
				if(largura<diametroFerramenta){
					terminouZ = true;
					terminouXY = true;
				}
				else{
					zAtual = zAtual - apUtilizado;
					if(fundo)
						xAtual = xAtual - andarX;
					else
						xAtual = xAtual + andarX;
				}
					
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				desbaste.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				desbaste.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				
				while(!terminouXY){
					if(vaiVolta){
						yAtual = this.ranhuraV.getPosicaoY();
						yProximo = this.ranhuraV.getComprimento();
						vaiVolta = false;
					}
					else{
						yAtual = this.ranhuraV.getComprimento();
						yProximo = this.ranhuraV.getPosicaoY();
						vaiVolta = true;
					}
					
					if(fundo){
						if(xAtual-this.ws.getCondicoesUsinagem().getAe()>=xLimiteEsquerda){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual-this.ws.getCondicoesUsinagem().getAe()==xLimiteEsquerda){
								fundo = false;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = xAtual - xLimiteEsquerda;
							fundo = false;
							terminouXY = true;
						}
						xAtual = xAtual - aeUtilizado;
					}
					else{
						if(xAtual+this.ws.getCondicoesUsinagem().getAe()<=xLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()==xLimiteDireita){
								fundo = true;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = xLimiteDireita-xAtual;
							fundo = true;
							terminouXY = true;
						}
						xAtual = xAtual + aeUtilizado;
					}
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
				}
			}
			
		}
		else if(this.ranhuraV.getEixo() == RanhuraPerfilVee.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
			pontoInicial = new Point3d(this.ranhuraV.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());
			yAtual = this.ranhuraV.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
			yLimiteEsquerda = yAtual;
			yLimiteDireita = this.ranhuraV.getPosicaoY()+this.ranhuraV.getLargura()-allowanceBottom-diametroFerramenta/2;
			largura = yLimiteDireita - yLimiteEsquerda;		
			
			while(!terminouZ){
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp()<=profundidade && largura>=diametroFerramenta){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp()==profundidade){
						terminouZ = true;
					}
				}
				else{
					if(largura>=diametroFerramenta)
						apUtilizado = profundidade + zAtual;
					else
						apUtilizado = 0;
					terminouZ = true;
				}
				zAtual = zAtual - apUtilizado;
				
				if(vaiVolta){
					xAtual = this.ranhuraV.getPosicaoX();
					xProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhuraV.getComprimento();
					xProximo = this.ranhuraV.getPosicaoX();
					vaiVolta = true;
				}
				
				z=profundidade + zAtual;
				if(-zAtual<=zLimite)
					andarY = apUtilizado/Math.abs(Math.tan((Math.PI/2)-alfa));
				else
					andarY = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
				
				if(fundo)
					yAtual = yAtual - andarY;
				else
					yAtual = yAtual + andarY;
					
				yLimiteDireita = yLimiteDireita - andarY;
				yLimiteEsquerda = yLimiteEsquerda + andarY;
				largura = yLimiteDireita - yLimiteEsquerda;
				
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				desbaste.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				desbaste.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
				
				
				terminouXY = false;
				while(!terminouXY){
					if(vaiVolta){
						xAtual = this.ranhuraV.getPosicaoX();
						xProximo = this.ranhuraV.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.ranhuraV.getComprimento();
						xProximo = this.ranhuraV.getPosicaoX();
						vaiVolta = true;
					}
					
					if(fundo){
						if(yAtual-this.ws.getCondicoesUsinagem().getAe()>=yLimiteEsquerda){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(yAtual-this.ws.getCondicoesUsinagem().getAe()==yLimiteEsquerda){
								fundo = false;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = yAtual - yLimiteEsquerda;
							fundo = false;
							terminouXY = true;
						}
						yAtual = yAtual - aeUtilizado;
					}
					else{
						if(yAtual+this.ws.getCondicoesUsinagem().getAe()<=yLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(yAtual+this.ws.getCondicoesUsinagem().getAe()==yLimiteDireita){
								fundo = true;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = yLimiteDireita-yAtual;
							fundo = true;
							terminouXY = true;
						}
						yAtual = yAtual + aeUtilizado;
					}
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xProximo, yAtual, zAtual);
				}
			}
		}
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
		double xAtual=this.ranhuraV.getPosicaoX();
		double xProximo;
		double yAtual=this.ranhuraV.getPosicaoY();
		double yProximo;
		double zAtual=this.ranhuraV.getPosicaoZ();
		double zLimite;
		double apUtilizado;
		double xEstatico=0;
		double ultimoXEstatico=0;
		double xVariavel=0;
		double alfa=(this.ranhuraV.getAngulo()/2)*Math.PI/180;
		double profundidade=this.ranhuraV.getProfundidade();
		double ultimoApEstatico=0;
		double apConstanteCurva=0;
		double apVariavelCurva=0;
		double z;
		double R=this.ranhuraV.getRaio();
		double r=this.ferramenta.getEdgeRadius();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		
		boolean primeiroApCurvaSubida = true;
		boolean vaiVolta = true;
		boolean terminouZLimite = false;
		boolean terminouFundoDescida = false;
		boolean terminouFundoSubida = false;
		boolean chegouNoTopo = false;
		boolean primeiroZSubidaEstatica = true;
		boolean terminouFundo = false;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
				
				
		if(alfa == 0)
			zLimite = profundidade-R+r;
		else
			zLimite = profundidade+R*(Math.sin(alfa)-1)+r-(r*Math.cos(alfa));

		xAtual = diametroFerramenta/2 + this.ranhuraV.getPosicaoX()+r-r*Math.cos(Math.PI/2-alfa);
		yAtual = diametroFerramenta/2 + this.ranhuraV.getPosicaoY()+r-r*Math.cos(Math.PI/2-alfa);
		

		if(this.ranhuraV.getEixo() == RanhuraPerfilVee.VERTICAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){
			pontoInicial = new Point3d(xAtual,this.ranhuraV.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			
			while(!terminouZLimite){
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp() <= zLimite){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					ultimoApEstatico = apUtilizado;
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp() == zLimite){
						terminouZLimite = true;
					}
				}
				else{
					apUtilizado = zLimite+zAtual;
					ultimoApEstatico = apUtilizado;
					terminouZLimite = true;
				}
				zAtual = zAtual-apUtilizado;
				if(vaiVolta){
					yAtual = this.ranhuraV.getPosicaoY();
					yProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhuraV.getComprimento();
					yProximo = this.ranhuraV.getPosicaoY();
					vaiVolta = true;
				}
				
				xEstatico = Math.tan(alfa)*apUtilizado;
				ultimoXEstatico = xEstatico;
				xAtual = xAtual+xEstatico;		
				
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);
			}		
			while(!terminouFundo){
				if(!terminouFundoDescida){
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp() <= profundidade){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(-zAtual+this.ws.getCondicoesUsinagem().getAp() == profundidade){
							terminouFundoDescida = true;
							apVariavelCurva = apUtilizado;
						}
						apConstanteCurva = apUtilizado;
					}
					else{
						apUtilizado = profundidade+zAtual;
						apVariavelCurva = apUtilizado;
						terminouFundoDescida = true;
					}						
					zAtual = zAtual-apUtilizado; 
					if(this.ferramenta.getDiametroFerramenta() == 2*R*Math.cos(alfa)){
						xAtual = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()/2;
					}
					else{
						z=profundidade+zAtual;
						if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
							xVariavel = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
						}
						else{
							xVariavel = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()/2-xAtual;
						}
						xAtual = xAtual+xVariavel;
					}
				}
				else if(!terminouFundoSubida){
					if(primeiroApCurvaSubida){
						apUtilizado = apVariavelCurva;
						primeiroApCurvaSubida = false;
						if(-zAtual-apUtilizado==zLimite){
							terminouFundoSubida = true;
							terminouFundo = true;
						}
						xAtual=xAtual+xVariavel;
					}
					else{
						apUtilizado = apConstanteCurva;
						if(-zAtual-apUtilizado==zLimite){
							terminouFundoSubida = true;
							terminouFundo = true;
						}

						z=profundidade+zAtual;
						xVariavel = Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado;
						xAtual = xAtual+xVariavel;
					}
					zAtual = zAtual+apUtilizado;
				}
				
				if(vaiVolta){
					yAtual = this.ranhuraV.getPosicaoY();
					yProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhuraV.getComprimento();
					yProximo = this.ranhuraV.getPosicaoY();
					vaiVolta = true;
				}

				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);
			}
			
			while(!chegouNoTopo){
				if(primeiroZSubidaEstatica){
					zAtual=zAtual+ultimoApEstatico;
					xAtual=xAtual+ultimoXEstatico;
					primeiroZSubidaEstatica = false;
					}
				if(-zAtual-this.ws.getCondicoesUsinagem().getAp()>=0){//r-r*Math.sin(alfa/2)){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual-this.ws.getCondicoesUsinagem().getAp()<=0){//r-r*Math.sin(alfa/2)){
						chegouNoTopo = true;
					}
					zAtual = zAtual + apUtilizado;
				}
//				else{
//					apUtilizado = zAtual+r-r*Math.sin(alfa/2);
//					chegouNoTopo = true;
//				}
//				
				if(vaiVolta){
					yAtual = this.ranhuraV.getPosicaoY();
					yProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhuraV.getComprimento();
					yProximo = this.ranhuraV.getPosicaoY();
					vaiVolta = true;
				}
				xAtual = xAtual+xEstatico;
				

				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual, yProximo, zAtual);
			}
			System.out.println("PRONTO!");
			
			
		}
		else if(this.ranhuraV.getEixo() == RanhuraPerfilVee.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){

		}

		return acabamento;
	}
	
	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhuraPerfilVee() {
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
		
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhuraPerfilVee() {
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
