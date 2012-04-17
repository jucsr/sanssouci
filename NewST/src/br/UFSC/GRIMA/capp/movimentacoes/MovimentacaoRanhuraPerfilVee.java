package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
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
		
		
		zLimite = profundidade+R*(Math.sin(alfa)-1);//calcula a profundidade em que começa a curva
		
		if(zLimite != zAtual){
			//***************se ainda nao chegou na parte curva
			if(this.ranhuraV.getAngulo() == 0){
				andarX = 0;
				andarY = 0;
			}
			else{										//calcula o tanto que o x vai ter que andar para cada caso
				andarX = apUtilizado*Math.tan(alfa);
				andarY = apUtilizado*Math.tan(alfa);
			}
		}
		//*******************se já chegou na parte curva
		else{
			andarX = Math.tan(Math.asin((R-z)/R))*apUtilizado;
			andarY = Math.tan(Math.asin((R-z)/R))*apUtilizado;
		}
		//iniciar corretamente os parametros
		xAtual = this.ranhuraV.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
		yAtual = this.ranhuraV.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
		
		//se a ranhura for VERTICAL
		if(this.ranhuraV.getEixo() == RanhuraPerfilVee.VERTICAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
			
			//pegar o ws anterior a esse e ver se ele é vazio, se for vazio o pontoInicial é esse mesmo, se nao for o pontoInicial é o ultimo ponto do array
			pontoInicial = new Point3d(xAtual,this.ranhuraV.getPosicaoY(),this.ws.getOperation().getRetractPlane());//ponto inicializador do Array
			
			//calcula os limites em que o x pode chegar
			xLimiteEsquerda = xAtual+andarX;
			xLimiteDireita = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()-allowanceBottom-diametroFerramenta/2-andarX;
			largura = xLimiteDireita - xLimiteEsquerda;//largura da ranhura para comparar com o diametro da ferramenta		
				
			while(!terminouZ){
				terminouXY = false;
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp()<=profundidade && largura>=diametroFerramenta){//teste para ver quanto vai descer em 'Z'
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();										//testa se já chegou no fim ou se a largura é maior
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp()==profundidade || largura==diametroFerramenta){//que o diametro da ferramenta.
						terminouZ = true;
					}
				}
				else{//já chegou no fim ou a largura é menor que o diametro da ferramenta
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp()>profundidade && largura>=diametroFerramenta)
						apUtilizado = profundidade + zAtual;//calculo para ver quanto vai descer, esse valor é sempre menor que AP
					else if(largura<diametroFerramenta){
						apUtilizado=0;
						terminouXY = true;
					}
					terminouZ = true;
				}
				z=profundidade + zAtual;
				if(-zAtual<=zLimite)
					andarX = apUtilizado*Math.tan(alfa);//calcula quanto tem que andar em X depois de descer em Z
				else
					andarX = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;

				if(fundo)
					xAtual = xAtual - andarX;//se a ferramenta tiver vindo da direita pra esquerda
				else
					xAtual = xAtual + andarX;//se a ferramenta tiver vindo da esquerda pra direita

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
				
				zAtual = zAtual - apUtilizado;

					
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
						if(xAtual-this.ws.getCondicoesUsinagem().getAe()>=xLimiteEsquerda){//se a ferramenta tiver vindo da direita pra esquerda
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();			//se a ferramenta ainda nao chegou no limite da esquerda ela anda AE
							if(xAtual-this.ws.getCondicoesUsinagem().getAe()==xLimiteEsquerda){
								fundo = false;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = xAtual - xLimiteEsquerda;//se a ferramenta ia passar o limite andando AE ela deixa de andar AE
							fundo = false;
							terminouXY = true;
						}
						xAtual = xAtual - aeUtilizado;
					}
					else{//se a ferramenta tiver indo da esquerda pra direita
						if(xAtual+this.ws.getCondicoesUsinagem().getAe()<=xLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();//se a ferramenta ainda nao chegou no limete da esquerda ela anda AE
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()==xLimiteDireita){
								fundo = true;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = xLimiteDireita-xAtual;//se a ferramenta ia passar o limite andando AE ela deixa de andar AE
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

				xLimiteDireita = xLimiteDireita - andarX;//Arruma os limites que se pode chegar em X
				xLimiteEsquerda = xLimiteEsquerda + andarX;
				largura = xLimiteDireita - xLimiteEsquerda;
			}
			
		}
		else if(this.ranhuraV.getEixo() == RanhuraPerfilVee.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideRoughMilling.class){
			pontoInicial = new Point3d(this.ranhuraV.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());
			
			//calcula os limites em que o y pode chegar
			yLimiteEsquerda = yAtual+andarY;
			yLimiteDireita = this.ranhuraV.getPosicaoY()+this.ranhuraV.getLargura()-allowanceBottom-diametroFerramenta/2-andarY;
			largura = yLimiteDireita - yLimiteEsquerda;//largura da ranhura para comparar com o diametro da ferramenta		
				
			while(!terminouZ){
				terminouXY = false;
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp()<=profundidade && largura>=diametroFerramenta){//teste para ver quanto vai descer em 'Z'
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();										//testa se já chegou no fim ou se a largura é maior
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp()==profundidade || largura==diametroFerramenta){//que o diametro da ferramenta.
						terminouZ = true;
					}
				}
				//já chegou no fim ou a largura é menor que o diametro da ferramenta
				else{
					if(largura>=diametroFerramenta || -zAtual+this.ws.getCondicoesUsinagem().getAp()<=profundidade)
						apUtilizado = profundidade + zAtual;//calculo para ver quanto vai descer, esse valor é sempre menor que AP
					else{
						apUtilizado = 0;
						terminouXY = true;
					}
					terminouZ = true;
				}
				
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
					andarY = apUtilizado*Math.tan(alfa);//calcula quanto tem que andar em Y depois de descer em Z
				else
					andarY = Math.tan(Math.asin((R-z)/(R)))*apUtilizado;
				
				zAtual = zAtual - apUtilizado;
				if(fundo)
					yAtual = yAtual - andarY;//se a ferramenta tiver vindo da direita pra esquerda
				else
					yAtual = yAtual + andarY;//se a ferramenta tiver vindo da esquerda pra direita

					
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				desbaste.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				desbaste.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
				
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
						if(yAtual-this.ws.getCondicoesUsinagem().getAe()>=yLimiteEsquerda){//se a ferramenta tiver vindo da direita pra esquerda
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();			//se a ferramenta ainda nao chegou no limite da esquerda ela anda AE
							if(yAtual-this.ws.getCondicoesUsinagem().getAe()==yLimiteEsquerda){
								fundo = false;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = yAtual - yLimiteEsquerda;//se a ferramenta ia passar o limite andando AE ela deixa de andar AE
							fundo = false;
							terminouXY = true;
						}
						yAtual = yAtual - aeUtilizado;
					}
					else{//se a ferramenta tiver indo da esquerda pra direita
						if(yAtual+this.ws.getCondicoesUsinagem().getAe()<=yLimiteDireita){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();//se a ferramenta ainda nao chegou no limete da esquerda ela anda AE
							if(yAtual+this.ws.getCondicoesUsinagem().getAe()==yLimiteDireita){
								fundo = true;
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = yLimiteDireita-yAtual;//se a ferramenta ia passar o limite andando AE ela deixa de andar AE
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

				yLimiteDireita = yLimiteDireita - andarY;//Arruma os limites que se pode chegar em Y
				yLimiteEsquerda = yLimiteEsquerda + andarY;
				largura = yLimiteDireita - yLimiteEsquerda;
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
		double zLimite;
		double apUtilizado=0;
		double xEstatico=0;
		double yEstatico=0;
		double ultimoXEstatico=0;
		double ultimoYEstatico=0;
		double xVariavel=0;
		double yVariavel=0;
		double alfa=(this.ranhuraV.getAngulo()/2)*Math.PI/180;
		double profundidade=this.ranhuraV.getProfundidade();
		double ultimoApEstatico=0;
		double apConstanteCurva=0;
		double ultimoAp=0;
		double z;
		double R=this.ranhuraV.getRaio();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double r=diametroFerramenta/2;//this.ferramenta.getEdgeRadius();
		double zAtual=this.ranhuraV.getPosicaoZ();
		
		boolean primeiroApCurvaSubida = true;
		boolean vaiVolta = true;
		boolean terminouZLimite = false;
		boolean terminouFundoDescida = false;
		boolean terminouFundoSubida = false;
		boolean chegouNoTopo = false;
		boolean primeiroZSubidaEstatica = true;
		boolean primeiroPonto = true;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
						
		//calculo a profundidade em que começa a curva
		if(alfa == 0)
			zLimite = profundidade-R+r;
		else if(R==r)
			zLimite = profundidade;
		else
			zLimite = profundidade+R*(Math.sin(alfa)-1)+r-(r*Math.cos(alfa));

		xAtual = this.ranhuraV.getPosicaoX()+r*Math.cos(alfa);
		yAtual = this.ranhuraV.getPosicaoY()+r*Math.cos(alfa);
		
		if(this.ranhuraV.getEixo() == RanhuraPerfilVee.VERTICAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){
			pontoInicial = new Point3d(xAtual,this.ranhuraV.getPosicaoY(),this.ws.getOperation().getRetractPlane());
			
			while(!terminouZLimite){
				if(primeiroPonto){//LOOP INFINITO!!!!
					apUtilizado = r-r*Math.sin(alfa);
					zAtual-= apUtilizado;
					primeiroPonto=false;
				}
				else{
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
			while(!terminouFundoDescida && R!=r){
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp() <= profundidade){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp() == profundidade){
						terminouFundoDescida = true;
						ultimoAp = apUtilizado;
					}
					apConstanteCurva = apUtilizado;
				}
				else{
					apUtilizado = profundidade+zAtual;
					ultimoAp = apUtilizado;
					terminouFundoDescida = true;
				}						
				zAtual = zAtual-apUtilizado; 
				if(this.ferramenta.getDiametroFerramenta() == 2*R*Math.cos(alfa)){
					xAtual = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()/2;
				}
				else{
					z=profundidade+zAtual;						
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2 && R!=r){
						xVariavel = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado);
					}
					else{
						xVariavel = this.ranhuraV.getPosicaoX()+this.ranhuraV.getLargura()/2-xAtual;
					}
					xAtual = xAtual+xVariavel;
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
			
			while(!terminouFundoSubida && R!=r){

				if(primeiroApCurvaSubida){
					apUtilizado = ultimoAp;
					primeiroApCurvaSubida = false;
					if(-zAtual-apUtilizado==zLimite){
						terminouFundoSubida = true;
					}
				}
				else{
					apUtilizado = apConstanteCurva;
					if(-zAtual-apUtilizado==zLimite){
						terminouFundoSubida = true;
					}
					z=profundidade+zAtual;
					if(R!=r)
						xVariavel = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado);
					xAtual = xAtual+xVariavel;
				}
				zAtual = zAtual+apUtilizado;

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
				else if(R==r){
					xEstatico = Math.tan(alfa)*apUtilizado;
					if(-zAtual-this.ws.getCondicoesUsinagem().getAp()>=0){
						zAtual+=this.ws.getCondicoesUsinagem().getAp();
						if(-zAtual-this.ws.getCondicoesUsinagem().getAp()==0)
							chegouNoTopo = true;
					}
					xAtual+=xEstatico;
				}
				else if(-zAtual-this.ws.getCondicoesUsinagem().getAp()>=0){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					xEstatico = Math.tan(alfa)*apUtilizado;
					if(-zAtual-this.ws.getCondicoesUsinagem().getAp()<=0){
						chegouNoTopo = true;
					}
					zAtual = zAtual + apUtilizado;
					xAtual = xAtual+xEstatico;
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
			
			
		}
		else if(this.ranhuraV.getEixo() == RanhuraPerfilVee.HORIZONTAL && ws.getOperation().getClass() == BottomAndSideFinishMilling.class){

			pontoInicial = new Point3d(this.ranhuraV.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());
			
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
					xAtual = this.ranhuraV.getPosicaoX();
					xProximo = this.ranhuraV.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhuraV.getComprimento();
					xProximo = this.ranhuraV.getPosicaoX();
					vaiVolta = true;
				}
				
				yEstatico = Math.tan(alfa)*apUtilizado;
				ultimoYEstatico = yEstatico;
				yAtual = yAtual+yEstatico;		
				
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
			}		

			while(!terminouFundoDescida){
				if(-zAtual+this.ws.getCondicoesUsinagem().getAp() <= profundidade){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(-zAtual+this.ws.getCondicoesUsinagem().getAp() == profundidade){
						terminouFundoDescida = true;
						ultimoAp = apUtilizado;
					}
					apConstanteCurva = apUtilizado;
				}
				else{
					apUtilizado = profundidade+zAtual;
					ultimoAp = apUtilizado;
					terminouFundoDescida = true;
				}						
				zAtual = zAtual-apUtilizado; 
				if(this.ferramenta.getDiametroFerramenta() == 2*R*Math.cos(alfa)){
					yAtual = this.ranhuraV.getPosicaoY()+this.ranhuraV.getLargura()/2;
				}
				else{
					z=profundidade+zAtual;
					if(Math.asin((R-z-r)/(R-r)) != Math.PI/2){
						yVariavel = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado);
					}
					else{
						yVariavel = this.ranhuraV.getPosicaoY()+this.ranhuraV.getLargura()/2-yAtual;
					}
					yAtual = yAtual+yVariavel;
				}
			
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
				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
			}
			
			while(!terminouFundoSubida){

				if(primeiroApCurvaSubida){
					apUtilizado = ultimoAp;
					primeiroApCurvaSubida = false;
					if(-zAtual-apUtilizado==zLimite){
						terminouFundoSubida = true;
					}
				}
				else{
					apUtilizado = apConstanteCurva;
					if(-zAtual-apUtilizado==zLimite){
						terminouFundoSubida = true;
					}
					z=profundidade+zAtual;
					yVariavel = Math.abs(Math.tan(Math.asin((R-z-r)/(R-r)))*apUtilizado);
				}
				yAtual = yAtual+yVariavel;
				zAtual = zAtual+apUtilizado;

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

				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
			
			}
			
			while(!chegouNoTopo){
				if(primeiroZSubidaEstatica){
					zAtual=zAtual+ultimoApEstatico;
					yAtual=yAtual+ultimoYEstatico;
					primeiroZSubidaEstatica = false;
					}
				else if(-zAtual-this.ws.getCondicoesUsinagem().getAp()>=0){//r-r*Math.sin(alfa/2)){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					yEstatico = Math.tan(alfa)*apUtilizado;
					if(-zAtual-this.ws.getCondicoesUsinagem().getAp()<=0){//r-r*Math.sin(alfa/2)){
						chegouNoTopo = true;
					}
					zAtual = zAtual + apUtilizado;
					yAtual = yAtual+yEstatico;
				}
				
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

				pontoFinal = new Point3d(xAtual, yAtual, zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual, zAtual));
				acabamento.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo, yAtual, zAtual);
			}
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
