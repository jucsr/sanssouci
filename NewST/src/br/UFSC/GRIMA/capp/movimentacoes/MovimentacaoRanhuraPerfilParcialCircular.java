package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilParcialCircular {

	private Workingstep ws;
	private RanhuraPerfilCircularParcial ranhuraParcCirc;
	private Ferramenta ferramenta;
	private Face face;
	
	public MovimentacaoRanhuraPerfilParcialCircular(Workingstep ws, Face face){
		this.face = face;
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.ranhuraParcCirc = (RanhuraPerfilCircularParcial) this.ws.getFeature();
	}
	
	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		
		double R=this.ranhuraParcCirc.getLargura()/2;
		double xAtual=this.ranhuraParcCirc.getPosicaoX();
		double yAtual=this.ranhuraParcCirc.getPosicaoY();
		double Dz=this.ranhuraParcCirc.getDz();
		double zAtual=this.ranhuraParcCirc.getPosicaoZ()-Dz;
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double xLimiteDireita=0, xLimiteEsquerda=0, largura,z,temp=0, limiteEsquerda, limiteDireita;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double alfa=120*Math.PI/180;
		double meio;
		double xQueSobrou=0;
		double hold=0;
		double ultimoAp,numeroDeAps, ultimoAe, numeroDeAes;
		double cima,hold2;
		int i,k;
		
		boolean fundo = false;
		boolean vaiVolta = true;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		
		//DESCER EM Z
		largura=this.ranhuraParcCirc.getLargura();
		ultimoAp=(R-Dz)%ap;
		numeroDeAps=1+(R-Dz-ultimoAp)/ap;//1 para andar o ultimo AP!
		
		if(alfa==Math.PI/2)
			cima=0;
		else
			cima=this.face.getLargura()/Math.tan(alfa);
		
		
		if(this.ranhuraParcCirc.getEixo()== RanhuraPerfilCircularParcial.VERTICAL){

			xAtual=this.ranhuraParcCirc.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
			pontoInicial = new Point3d(xAtual,this.ranhuraParcCirc.getPosicaoY(),this.ws.getOperation().getRetractPlane());//ponto inicializador do Array
			
			//Calculo dos limites de X........
						//LIMITES INFERIORES
			limiteEsquerda=this.ranhuraParcCirc.getPosicaoX()+allowanceBottom+diametroFerramenta/2;
			limiteDireita=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()-allowanceBottom-diametroFerramenta/2;
			
						//LIMITES SUPERIORES
			if(alfa==90*Math.PI/180){
				xLimiteDireita=limiteDireita;
				xLimiteEsquerda=limiteEsquerda;
			}
			else if(alfa<90*Math.PI/180){
				if(this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2<this.face.getComprimento())
					xLimiteDireita=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2;
				else{
					xQueSobrou=this.ranhuraParcCirc.getPosicaoX()
							+this.ranhuraParcCirc.getLargura()
							+cima
							-allowanceBottom
							-diametroFerramenta/2
							-this.face.getComprimento();
					xLimiteDireita=this.face.getComprimento();
				}
				if(this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2<this.face.getComprimento())
					xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2;
				else
					xLimiteEsquerda=this.face.getComprimento();
			}
			else if(alfa>90*Math.PI/180){
				if(this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2>0)
					xLimiteDireita=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2;
				else
					xLimiteDireita=0;
				if(this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2>0)
					xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2;
				else{
					xQueSobrou=-this.ranhuraParcCirc.getPosicaoX()
							-cima
							-allowanceBottom
							-diametroFerramenta/2;
					xLimiteEsquerda=0;
				}
			}
			
			for(i=(int)numeroDeAps;i>0;i--){
				if(largura<diametroFerramenta)
					i=0;
				else if(i==1 && ultimoAp==0)
					i=0;
				else{
					if(i!=1)
						zAtual-=ap;
					else
						zAtual-=ultimoAp;

					//Renovação dos limites de X.......
					limiteEsquerda+=temp;
					limiteDireita-=temp;

					largura = limiteDireita-limiteEsquerda;
					ultimoAe=largura%ae;
					numeroDeAes=1+(largura-ultimoAe)/ae;//1 para andar o ultimoAe!!!
									
					if(xLimiteDireita!=xLimiteEsquerda){
						if(alfa<90*Math.PI/180){
							xLimiteEsquerda+=temp;
							if(xQueSobrou<=0)
								xLimiteDireita-=temp;
							else{
								xQueSobrou-=temp;
								if(xQueSobrou<0)
									xLimiteDireita+=xQueSobrou;
							}
						}
						else if(alfa>90*Math.PI/180){
							xLimiteDireita-=temp;
							if(xQueSobrou<=0)
								xLimiteEsquerda+=temp;
							else{
								xQueSobrou-=temp;
								if(xQueSobrou<0)
									xLimiteEsquerda-=xQueSobrou;
							}
						}
						else if(alfa==90*Math.PI/180){
							xLimiteDireita-=temp;
							xLimiteEsquerda+=temp;
						}
					}				
					
					z=R-Dz+zAtual;
					if(Math.asin((R-z)/(R))!=Math.PI/2)
						if(i!=1)
							temp = Math.tan(Math.asin((R-z)/(R)))*ap;
						else
							temp = Math.tan(Math.asin((R-z)/(R)))*ultimoAp;
					else{
						temp=xAtual-largura/2;
					}
						
					if(fundo)
						xAtual-=temp;
					else
						xAtual+=temp;
					
					
					
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					for(k=(int)numeroDeAes;k>0;k--){//COMEÇA A FAZER A MOVIMENTAÇAO DE UM AP
						if(k==1 && ultimoAe==0){//SE O ULTIMOAE FOR ZERO ELE SAI DO LAÇO
							if(fundo)
								fundo=false;//TROCA OS PARAMETROS
							else
								fundo=true;
							k=0;
						}
						else{
							if(alfa==Math.PI/2)
								cima=0;
							else{
								if(vaiVolta)//CALCULA O CIMA CASO O yAtual NAO SEJA ZERO NEM MAX.
									cima=this.face.getLargura()/Math.tan(alfa);
								else
									cima=yAtual/Math.tan(alfa);
							}
							if(vaiVolta){//VAI PRA CIMA
								hold=xAtual;

								if(xAtual+cima>this.face.getComprimento())
									xAtual=this.face.getComprimento();
								else if(xAtual+cima<0)
									xAtual=0;
								else if(xAtual+cima>xLimiteDireita)
									xAtual=xLimiteDireita;
								else if(xAtual+cima<xLimiteEsquerda)
									xAtual=xLimiteEsquerda;
								else
									xAtual+=cima;

								if(alfa==Math.PI/2)
									yAtual=this.ranhuraParcCirc.getComprimento();
								else
									yAtual=Math.abs(Math.tan(alfa)*(xAtual-hold));
							}
							else{//VAI PRA BAIXO!
								yAtual= this.ranhuraParcCirc.getPosicaoY();
								
								if(xAtual-cima>this.face.getComprimento())
									xAtual=this.face.getComprimento();
								else if(xAtual-cima<0)
									xAtual=0;
								else if(xAtual-cima>limiteDireita)
									xAtual=limiteDireita;
								else if(xAtual-cima<limiteEsquerda)
									xAtual=limiteEsquerda;
								else
									xAtual-=cima;
							}

							pontoFinal = new Point3d(xAtual, yAtual, zAtual);
							LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTempp);
							pontoInicial = new Point3d(xAtual, yAtual, zAtual);

							if(k!=1){
								if(vaiVolta){//VAI PRA CIMA
									if(xLimiteDireita==xLimiteEsquerda){
										if(!fundo){//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											yAtual-=Math.tan(alfa)*ae;
										}
										else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											yAtual+=Math.tan(alfa)*ae;
										}			
										vaiVolta=false;
									}
									else if(yAtual<this.face.getLargura()-0.00000005 || (!fundo && xAtual+ae>this.face.getComprimento()) || (fundo && xAtual-ae<0)){//o calculo do yAtual tem um erro com escala 10E-10 .. por isso -0.005
										if(alfa>90*Math.PI/180){//ranhura deitada para ESQUERDA
											if(!fundo){//MOVIMENTO DA ESQUERDA PRA DIREITA
												if(yAtual+Math.abs(Math.tan(alfa)*ae)<this.face.getLargura()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													yAtual+=Math.abs(Math.tan(alfa)*ae);
												}
												else{
													hold2=this.face.getLargura()-yAtual;
													yAtual=this.face.getLargura();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													xAtual+=ae-(Math.abs(hold2/Math.tan(alfa)));
												}
											}
											else{//MOVIMENTO DA DIREITA PRA ESQUERDA
												if(xAtual==0){
													yAtual-= ae/Math.abs(Math.tan(alfa));
												}
												else if(xAtual-ae<0){// uma coisa menor que zero xAtual-ae
													hold2=ae-xAtual;
													xAtual=0;
													yAtual=this.face.getLargura();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													yAtual-=Math.abs(Math.tan(alfa))*hold2;
												}
											}
										}
										else if(alfa<90*Math.PI/180){//ranhura deitada para direita
											if(!fundo){//MOVIMENTO DA ESQUERDA PARA DIREITA ---->
												if(xAtual==this.face.getComprimento())
													yAtual-=Math.tan(alfa)*ae;//O XATUAL ESTÁ BUGANDO O cima....
												else if(xAtual+ae>this.face.getComprimento()){
													hold2=ae-this.face.getComprimento()+xAtual;
													xAtual=this.face.getComprimento();
													yAtual=this.face.getLargura();
													
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													yAtual-=hold2*Math.tan(alfa);
												}
											}
											else{//MOVIMENTO DA DIREITA PARA ESQUERDA <====
												if(yAtual+Math.abs(Math.tan(alfa)*ae)<this.face.getLargura()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													yAtual+=Math.abs(Math.tan(alfa)*ae);
												}
												else{
													hold2=this.face.getLargura()-yAtual;
													yAtual=this.face.getLargura();
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													xAtual+=ae-(hold2/Math.tan(alfa));
												}
											}
										}
									}	
									else{
										if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
												xAtual+=ae;
										else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
												xAtual-=ae;
									}
									vaiVolta=false;
								}
								else{//VAI PRA BAIXO
									if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											xAtual+=ae;
									else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											xAtual-=ae;
									vaiVolta=true;
								}
								
								if(yAtual>this.face.getLargura())
									yAtual=this.face.getLargura();
								
								pontoFinal = new Point3d(xAtual, yAtual, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yAtual, zAtual);
							}
							else{//ULTIMO AE
								if(vaiVolta){//VAI PRA CIMA
									if(xLimiteDireita==xLimiteEsquerda){
										if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											yAtual-=Math.tan(alfa)*ultimoAe;
										else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											yAtual+=Math.tan(alfa)*ultimoAe;	
										vaiVolta=false;
									}
									else if(yAtual<this.face.getLargura()-0.00000005 || (!fundo && xAtual+ultimoAe>this.face.getComprimento()) || (fundo && xAtual-ultimoAe<0)){//o calculo do yAtual tem um erro com escala 10E-10 .. por isso -0.005
										if(alfa>90*Math.PI/180){//ranhura deitada para ESQUERDA
											if(!fundo){//MOVIMENTO DA ESQUERDA PRA DIREITA
												if(yAtual+Math.abs(Math.tan(alfa)*ultimoAe)<this.face.getLargura()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													yAtual+=Math.abs(Math.tan(alfa)*ultimoAe);
												}
												else{
													hold2=this.face.getLargura()-yAtual;
													yAtual=this.face.getLargura();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													xAtual+=ultimoAe-(Math.abs(hold2/Math.tan(alfa)));
												}
											}
											else{//MOVIMENTO DA DIREITA PRA ESQUERDA
												if(xAtual==0){
													yAtual-= ultimoAe/Math.abs(Math.tan(alfa));
												}
												else if(xAtual-ultimoAe<0){// uma coisa menor que zero xAtual-ae
													hold2=ultimoAe-xAtual;
													xAtual=0;
													yAtual=this.face.getLargura();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													yAtual-=Math.abs(Math.tan(alfa))*hold2;
												}
											}
										}
										else if(alfa<90*Math.PI/180){//ranhura deitada para direita
											if(!fundo){//MOVIMENTO DA ESQUERDA PARA DIREITA ---->
												if(xAtual==this.face.getComprimento())
													yAtual-=Math.tan(alfa)*ultimoAe;//O XATUAL ESTÁ BUGANDO O cima....
												else if(xAtual+ultimoAe>this.face.getComprimento()){
													hold2=ultimoAe-this.face.getComprimento()+xAtual;
													xAtual=this.face.getComprimento();
													yAtual=this.face.getLargura();
													
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													yAtual-=hold2*Math.tan(alfa);
												}
											}
											else{//MOVIMENTO DA DIREITA PARA ESQUERDA <====
												if(yAtual+Math.abs(Math.tan(alfa)*ultimoAe)<this.face.getLargura()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													yAtual+=Math.abs(Math.tan(alfa)*ultimoAe);
												}
												else{
													hold2=this.face.getLargura()-yAtual;
													yAtual=this.face.getLargura();
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													xAtual+=ultimoAe-(hold2/Math.tan(alfa));
												}
											}
										}
									}	
									else{
										if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
												xAtual+=ultimoAe;
										else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
												xAtual-=ultimoAe;
									}
									vaiVolta=false;
								}
								else{//VAI PRA BAIXO
									if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											xAtual+=ultimoAe;
									else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											xAtual-=ultimoAe;
									vaiVolta=true;
								}
								
								if(yAtual>this.face.getLargura())
									yAtual=this.face.getLargura();
								
								pontoFinal = new Point3d(xAtual, yAtual, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yAtual, zAtual);
								
								if(fundo)
									fundo=false;
								else
									fundo=true;
							}
						}
					}
				}

			}
		}else if(this.ranhuraParcCirc.getEixo()==RanhuraPerfilCircularParcial.HORIZONTAL){

			yAtual=this.ranhuraParcCirc.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
			pontoInicial = new Point3d(this.ranhuraParcCirc.getPosicaoX(),yAtual,this.ws.getOperation().getRetractPlane());//ponto inicializador do Array
			
			//Calculo dos limites de X........
						//LIMITES INFERIORES
			limiteDireita=this.ranhuraParcCirc.getPosicaoY()+allowanceBottom+diametroFerramenta/2;
			limiteEsquerda=this.ranhuraParcCirc.getPosicaoY()+this.ranhuraParcCirc.getLargura()-allowanceBottom-diametroFerramenta/2;
			
						//LIMITES SUPERIORES
			if(alfa==90*Math.PI/180){
				xLimiteDireita=limiteDireita;
				xLimiteEsquerda=limiteEsquerda;
			}
			else if(alfa<90*Math.PI/180){
				if(this.ranhuraParcCirc.getPosicaoY()+cima+allowanceBottom+diametroFerramenta/2<this.face.getLargura())
					xLimiteDireita=this.ranhuraParcCirc.getPosicaoY()+cima+allowanceBottom+diametroFerramenta/2;
				else
					xLimiteDireita=this.face.getLargura();
				
				if(this.ranhuraParcCirc.getPosicaoY()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2<this.face.getLargura())
					xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoY()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2;
				else{
					xQueSobrou=this.ranhuraParcCirc.getPosicaoY()
							+this.ranhuraParcCirc.getLargura()
							+cima
							-allowanceBottom
							-diametroFerramenta/2
							-this.face.getLargura();
					xLimiteEsquerda=this.face.getLargura();
				}
			}
			else if(alfa>90*Math.PI/180){
				if(this.ranhuraParcCirc.getPosicaoY()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2>0)
					xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoY()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2;
				else
					xLimiteEsquerda=0;
				if(this.ranhuraParcCirc.getPosicaoY()+cima+allowanceBottom+diametroFerramenta/2>0)
					xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoY()+cima+allowanceBottom+diametroFerramenta/2;
				else{
					xQueSobrou=-this.ranhuraParcCirc.getPosicaoY()
							-cima
							-allowanceBottom
							-diametroFerramenta/2;
					xLimiteEsquerda=0;
				}
			}
			
			for(i=(int)numeroDeAps;i>0;i--){
				if(largura<diametroFerramenta)
					i=0;
				else if(i==1 && ultimoAp==0)
					i=0;
				else{
					if(i!=1)
						zAtual-=ap;
					else
						zAtual-=ultimoAp;

					//Renovação dos limites de X.......
					limiteEsquerda+=temp;
					limiteDireita-=temp;

					largura = limiteEsquerda-limiteDireita;
					ultimoAe=largura%ae;
					numeroDeAes=1+((largura-ultimoAe)/ae);//1 para andar o ultimoAe!!!
				
					
					if(xLimiteDireita!=xLimiteEsquerda){
						if(alfa<90*Math.PI/180){
							xLimiteDireita+=temp;
							if(xQueSobrou<=0)
								xLimiteEsquerda-=temp;
							else{
								xQueSobrou-=temp;
								if(xQueSobrou<0)
									xLimiteEsquerda+=xQueSobrou;
							}
						}
						else if(alfa>90*Math.PI/180){
							xLimiteEsquerda-=temp;
							if(xQueSobrou<=0)
								xLimiteDireita+=temp;
							else{
								xQueSobrou-=temp;
								if(xQueSobrou<0)
									xLimiteDireita-=xQueSobrou;
							}
						}
						else if(alfa==90*Math.PI/180){
							xLimiteEsquerda-=temp;
							xLimiteDireita+=temp;
						}
					}				
					
					z=R-Dz+zAtual;
					if(Math.asin((R-z)/(R))!=Math.PI/2){
						if(i!=1)
							temp = Math.tan(Math.asin((R-z)/(R)))*ap;
						else
							temp = Math.tan(Math.asin((R-z)/(R)))*ultimoAp;
					}
					else
						temp=yAtual-largura/2;

					if(fundo)
						yAtual-=temp;
					else
						yAtual+=temp;
					
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					for(k=(int)numeroDeAes;k>0;k--){//COMEÇA A FAZER A MOVIMENTAÇAO DE UM AP
						if(k==1 && ultimoAe==0){//SE O ULTIMOAE FOR ZERO ELE SAI DO LAÇO
							if(fundo)
								fundo=false;//TROCA OS PARAMETROS
							else
								fundo=true;
							k=0;
						}
						else{
							if(alfa==Math.PI/2)
								cima=0;
							else{
								if(vaiVolta)//CALCULA O CIMA CASO O yAtual NAO SEJA ZERO NEM MAX.
									cima=this.face.getComprimento()/Math.tan(alfa);
								else
									cima=xAtual/Math.tan(alfa);
							}
							if(vaiVolta){//VAI PRA CIMA
								hold=yAtual;

								if(yAtual+cima>this.face.getLargura())
									yAtual=this.face.getLargura();
								else if(yAtual+cima<0)
									yAtual=0;
								else if(yAtual+cima>xLimiteDireita)
									yAtual=xLimiteDireita;
								else if(yAtual+cima<xLimiteEsquerda)
									yAtual=xLimiteEsquerda;
								else
									yAtual+=cima;

								if(alfa==Math.PI/2)
									xAtual=this.ranhuraParcCirc.getComprimento();
								else
									xAtual=Math.abs(Math.tan(alfa)*(yAtual-hold));

								if(xAtual>this.face.getComprimento())
									xAtual=this.face.getComprimento();
								else if(xAtual<0)
									xAtual=0;
							}
							else{//VAI PRA BAIXO!
								xAtual= this.ranhuraParcCirc.getPosicaoX();
								
								if(yAtual-cima>this.face.getLargura())
									yAtual=this.face.getLargura();
								else if(yAtual-cima<0)
									yAtual=0;
								else if(yAtual-cima>limiteDireita)
									yAtual=limiteDireita;
								else if(yAtual-cima<limiteEsquerda)
									yAtual=limiteEsquerda;
								else
									yAtual-=cima;
							}

							pontoFinal = new Point3d(xAtual, yAtual, zAtual);
							LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTempp);
							pontoInicial = new Point3d(xAtual, yAtual, zAtual);

							if(k!=1){
								if(vaiVolta){//VAI PRA CIMA
									if(xLimiteDireita==xLimiteEsquerda){
										if(!fundo){//MOVIMENTACAO DE BAIXO PRA CIMA
											xAtual+=Math.tan(alfa)*ae;
										}
										else{//MOVIMENTACAO DE CIMA PRA BAIXO
											xAtual-=Math.tan(alfa)*ae;
										}			
										vaiVolta=false;
									}
									else if(xAtual<this.face.getComprimento()-0.00000005 || (!fundo && yAtual+ae>this.face.getLargura()) || (fundo && yAtual-ae<0)){//o calculo do yAtual tem um erro com escala 10E-10 .. por isso -0.005
										if(alfa>90*Math.PI/180){//ranhura deitada para ESQUERDA
											if(!fundo){//MOVIMENTO DA DIREITA PRA ESQUERDA
												if(xAtual+Math.abs(Math.tan(alfa)*ae)<this.face.getComprimento()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													xAtual+=Math.abs(Math.tan(alfa)*ae);
												}
												else{
													hold2=this.face.getComprimento()-xAtual;
													xAtual=this.face.getComprimento();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													yAtual+=ae-(Math.abs(hold2/Math.tan(alfa)));
												}
											}
											else{//MOVIMENTO DA ESQUERDA PRA DIREITA
												if(yAtual==0){
													xAtual-= ae/Math.abs(Math.tan(alfa));
												}
												else if(yAtual-ae<0){// uma coisa menor que zero xAtual-ae
													hold2=ae-yAtual;
													yAtual=0;
													xAtual=this.face.getComprimento();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													xAtual-=Math.abs(Math.tan(alfa))*hold2;
												}
											}
										}
										else if(alfa<90*Math.PI/180){//ranhura deitada para direita
											if(!fundo){//MOVIMENTO DA DIREITA PRA ESQUERDA
												if(yAtual==this.face.getLargura())
													xAtual-=Math.tan(alfa)*ae;//O XATUAL ESTÁ BUGANDO O cima....
												else if(yAtual+ae>this.face.getLargura()){
													hold2=ae-this.face.getLargura()+yAtual;
													xAtual=this.face.getComprimento();
													yAtual=this.face.getLargura();
													
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													xAtual-=hold2*Math.tan(alfa);
												}
											}
											else{//MOVIMENTO DA ESQUERDA PRA DIREITA
												if(xAtual+Math.abs(Math.tan(alfa)*ae)<this.face.getComprimento()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													xAtual+=Math.abs(Math.tan(alfa)*ae);
												}
												else{
													hold2=this.face.getComprimento()-xAtual;
													xAtual=this.face.getComprimento();
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													yAtual+=ae-(hold2/Math.tan(alfa));
												}
											}
										}
									}	
									else{
										if(!fundo)//MOVIMENTACAO DE BAIXO PRA CIMA
											yAtual+=ae;
									else//MOVIMENTACAO DE CIMA PRA BAIXO
											yAtual-=ae;
									}
									vaiVolta=false;
								}
								else{//VAI PRA BAIXO
									if(!fundo)//MOVIMENTACAO DE BAIXO PRA CIMA
										yAtual+=ae;
									else//MOVIMENTACAO DE CIMA PRA BAIXO
										yAtual-=ae;
								vaiVolta=true;
							}
								
								if(xAtual>this.face.getComprimento())
									xAtual=this.face.getComprimento();
								
								pontoFinal = new Point3d(xAtual, yAtual, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yAtual, zAtual);
							}
							else{//ULTIMO AE
								if(vaiVolta){//VAI PRA CIMA
									if(xLimiteDireita==xLimiteEsquerda){
										if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											xAtual-=Math.tan(alfa)*ultimoAe;
										else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											xAtual+=Math.tan(alfa)*ultimoAe;
										vaiVolta=false;
									}
									else if(xAtual<this.face.getLargura()-0.00000005 || (!fundo && yAtual+ultimoAe>this.face.getComprimento()) || (fundo && yAtual-ultimoAe<0)){//o calculo do yAtual tem um erro com escala 10E-10 .. por isso -0.005
										if(alfa>90*Math.PI/180){//ranhura deitada para ESQUERDA
											if(!fundo){//MOVIMENTO DA ESQUERDA PRA DIREITA
												if(xAtual+Math.abs(Math.tan(alfa)*ultimoAe)<this.face.getComprimento()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													xAtual+=Math.abs(Math.tan(alfa)*ultimoAe);
												}
												else{
													hold2=this.face.getComprimento()-xAtual;
													xAtual=this.face.getComprimento();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													yAtual+=ultimoAe-(Math.abs(hold2/Math.tan(alfa)));
												}
											}
											else{//MOVIMENTO DA DIREITA PRA ESQUERDA
												if(yAtual==0){
													xAtual-= ultimoAe/Math.abs(Math.tan(alfa));
												}
												else if(yAtual-ultimoAe<0){// uma coisa menor que zero xAtual-ae
													hold2=ultimoAe-yAtual;
													yAtual=0;
													xAtual=this.face.getComprimento();

													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													xAtual-=Math.abs(Math.tan(alfa))*hold2;
												}
											}
										}
										else if(alfa<90*Math.PI/180){//ranhura deitada para direita
											if(!fundo){//MOVIMENTO DA ESQUERDA PARA DIREITA ---->
												if(yAtual==this.face.getLargura())
													xAtual-=Math.tan(alfa)*ultimoAe;//O XATUAL ESTÁ BUGANDO O cima....
												else if(yAtual+ultimoAe>this.face.getLargura()){
													hold2=ultimoAe-this.face.getLargura()+yAtual;
													xAtual=this.face.getComprimento();
													yAtual=this.face.getLargura();
													
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);
													
													xAtual-=hold2*Math.tan(alfa);
												}
											}
											else{//MOVIMENTO DA DIREITA PARA ESQUERDA <====
												if(xAtual+Math.abs(Math.tan(alfa)*ultimoAe)<this.face.getComprimento()){//SE O AE NAO FAZ O yAtual CHEGAR NO TOPO
													xAtual+=Math.abs(Math.tan(alfa)*ultimoAe);
												}
												else{
													hold2=this.face.getComprimento()-xAtual;
													xAtual=this.face.getComprimento();
													pontoFinal = new Point3d(xAtual, yAtual, zAtual);
													LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
													desbaste.add(horizontalTemp);
													pontoInicial = new Point3d(xAtual, yAtual, zAtual);

													yAtual+=ultimoAe-(hold2/Math.tan(alfa));
												}
											}
										}
									}	
									else{
										if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
											yAtual+=ultimoAe;
										else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
											yAtual-=ultimoAe;
									}
									vaiVolta=false;
								}
								else{//VAI PRA BAIXO
									if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
										yAtual+=ultimoAe;
									else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
										yAtual-=ultimoAe;
									vaiVolta=true;
								}

								if(xAtual>this.face.getComprimento())
									xAtual=this.face.getComprimento();
								
								pontoFinal = new Point3d(xAtual, yAtual, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yAtual, zAtual);
								
								if(fundo)
									fundo=false;
								else
									fundo=true;
							}
						}
					}
				}

			}
		
			
		}
	
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
		return acabamento;
	}
	
	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhuraPerfilCircularParcial() {
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
		
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhuraPerfilCircularParcial() {
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
