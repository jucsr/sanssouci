package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.Bezier_1;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilGenerico {

	private Workingstep ws;
	private RanhuraPerfilBezier ranhuraBezier;
	private Ferramenta ferramenta;

	public MovimentacaoRanhuraPerfilGenerico(Workingstep ws){
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.ranhuraBezier = (RanhuraPerfilBezier) this.ws.getFeature();
	}

	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();


		double x=this.ranhuraBezier.getPosicaoX();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double y=this.ranhuraBezier.getPosicaoY()-diametroFerramenta;
		double z=this.ranhuraBezier.getPosicaoZ();
		double largura[] = new double [101];
		double zMax[] = new double [101];
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ultimoAp, numeroDeAps, ultimoAe, numeroDeAes,holdZ=0;
		double profundidadeMaxima=0;
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		int numeroDePontos=this.ranhuraBezier.getPontosDaCurva().length;
		int a=0,k,i,n,j,b,h,c,m,u=1,numeroDeConcavidades,p=0,r,s,numero;
		int t[] = new int[101];
		boolean fundo=false, vaiVolta=true,inicio;
		Point3d[] pontosDeInterferencia = new Point3d[101];
		Point3d[] ponto= this.ranhuraBezier.getPontosDaCurva();
		Point3d pontoInicial=null;
		Point3d pontoFinal;

		for(i=0;i<numeroDePontos;i++){//pega a profundidade maxima
			if(ponto[i].getZ()<profundidadeMaxima)
				profundidadeMaxima=ponto[i].getZ();

			if(i!=0){
				if(ponto[i-1].getZ()<ponto[i].getZ()){
					u=0;
					zMax[a]=ponto[i].getZ();
				}
				if(ponto[i-1].getZ()>ponto[i].getZ() && u==0){
					a++;
					u++;
				}
			}
		}
		m=a;

		ultimoAp= -profundidadeMaxima%ap;
		numeroDeAps=(-profundidadeMaxima-ultimoAp)/ap;

		/**************************COMEÇA A DESCER APS********************************/
		for(j=(int)numeroDeAps;j>=0;j--){
			if(j==0 && ultimoAp!=0)
				z-=ultimoAp;
			else if(j!=0)
				z-=ap;
			else
				break;

			inicio=true;
			a=0;k=0;n=0;b=0;c=0;h=m;m=0;r=0;s=0;numero=0;

			/***********************ACHA OS PONTOS DE INTERFERENCIA NA ALTURA******************************/
			for(i=0;i<numeroDePontos;i++){
				if(n%2==0){//se n é par													
					if(ponto[i].getZ()<z){//PRIMEIRO PONTO DA LARGURA
						pontosDeInterferencia[n]=ponto[i];
						n++;
					}
				}
				else if(n%2!=0){//se n é impar...
					if(ponto[i].getZ()>z){//SEGUNDO PONTO DA LARGURA
						pontosDeInterferencia[n]=ponto[i-1];
						n++;
					}
					else if(i==numeroDePontos-1){//ultima passada
						pontosDeInterferencia[n]=ponto[i];
						n++;
					}
				}
			}
			//onde n é o numero de pontos que interferem
			t[j]=n;

			/**********************************************************************************************/

			if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){

				/***************************DEFINE AS LARGURAS DAS "PARABOLAS"*****************************/
				for(b=1;b<n;b++){//n é o numero de pontos que achou!!!
					largura[k]=pontosDeInterferencia[b].getX()-pontosDeInterferencia[b-1].getX();
					k++;
				}
				if(p==0){
					pontoInicial = new Point3d(this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom,y,this.ranhuraBezier.getPosicaoZ());
					p++;
				}
				/*********************************************************************************************/
				b=0;
				numeroDeConcavidades=k;
				/*
				if(!fundo){
					b=0;
					if(x<zMax[s].getX() && pontosDeInterferencia[0].getX()>zMax[s].getX() && zMax[s].getX()!=0){
						if(p==0){
							holdZ=z;
							p++
						}
						z=zMax[s].getZ();
						//add
						x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom;
						//add
						if(largura[s+1]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
							z=holdZ;
							p=0;
							//add
						}
						s++;
					}
					else{
						if(largura[0]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
							x=pontosDeInterferencia[b].getX()+this.ranhuraBezier.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
							//add
						}
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0){
								if(largura[a]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
									b+=2;

									if(vaiVolta){
										y=this.ranhuraBezier.getComprimento()+diametroFerramenta;//vai para o final
										vaiVolta=false;
									}
									else{
										y=this.ranhuraBezier.getPosicaoY()-diametroFerramenta;//volta pro começo
										vaiVolta=true;
									}
										//add
										for(g=0;g<=numeroDeAes;g++){
											if(g==numeroDeAes)
												x+=ultimoAe;
											else
												x+=ae;\
									//add
									}
								}
								else{
									if(pontosDeInterferencia[b+3]!=null){//lembrar de zerar toda vez...
										if(p==0){
											holdZ=z;
											p++;
										}
										z=zMax[numero];
										numero++;
										//add
										b+=2;
										x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()+diametroFerramenta/2+allowanceBottom;
										//add
										if(largura[a+2]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
											p=0;
											z=holdZ;
											//add
										}
										a++;
									}
									else{
										b+=2;
										break;
									}
								}
							}
							else if(a%2!=0){
								if(p==0){
									holdZ=z;
									p++;
								}
								z=zMax[numero];
								numero++;
								//add
								x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()+diametroFerramenta/2+allowanceBottom;
								//add
								if(largura[a+1]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
									p=0;
									z=holdZ;
									//add
								}
							}
						}
					}
				}
				else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA

				}

				 */


				c=0;
				if(j!=numeroDeAps && j!=numeroDeAps-1){
					System.out.println(t[j+2]+"   "+t[j+1]);
					if(t[j+2]<t[j+1]){
						if(r==0){
							holdZ=z;
							r++;
						}
						z=zMax[s];

						pontoFinal = new Point3d(x, y, z);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						pontoInicial = new Point3d(x, y, z);

						x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom;

						pontoFinal = new Point3d(x, y, z);
						LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTempp);
						pontoInicial = new Point3d(x, y, z);

						//if(largura[s+1]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
							z=holdZ;
							r=0;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verticalTemppp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTemppp);
							pontoInicial = new Point3d(x, y, z);
						//}
						s++;
					}
				}
				if(!fundo){
				//	if(largura[0]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
						x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;

						pontoFinal = new Point3d(x, y, z);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						pontoInicial = new Point3d(x, y, z);
//					}
//					else{
//						
//					}
//					else if(numeroDeConcavidades==1 || numeroDeConcavidades==2){
//						break;
//					}
//					else{
//						z=zMax[numero];
//						//add
//						if(largura[3]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
//							x=
//						}
//					}

					for(a=0;a<numeroDeConcavidades;a++){
						if(a%2==0){//parte CONCAVA
							//if(largura[a]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
								b+=2;
								ultimoAe=(largura[a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
								numeroDeAes=(largura[a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
								for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
									if(vaiVolta){
										y=this.ranhuraBezier.getComprimento()+diametroFerramenta;//vai para o final
										vaiVolta=false;
									}
									else{
										y=this.ranhuraBezier.getPosicaoY()-diametroFerramenta;//volta pro começo
										vaiVolta=true;
									}

									pontoFinal = new Point3d(x, y, z);
									LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(ver);
									pontoInicial = new Point3d(x, y, z);

									if(k!=numeroDeAes)//se nao é a ultima passada ele adiciona ae em X
										x+=ae;
									else
										x+=ultimoAe;//adiciona em x o ultimoAe já que está na ultima passada

									pontoFinal = new Point3d(x, y, z);
									LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(vert);
									pontoInicial = new Point3d(x, y, z);
								}
						//	}
//							else{
//								if(pontosDeInterferencia[b+3]!=null){//lembrar de zerar toda vez...
//									if(p==0){
//										holdZ=z;
//										p++;
//									}
//									z=zMax[numero];
//									numero++;
//
//									pontoFinal = new Point3d(x, y, z);		
//									LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
//									desbaste.add(ver);
//									pontoInicial = new Point3d(x, y, z);
//									
//									b+=2;
//									x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()+diametroFerramenta/2+allowanceBottom;
//
//									pontoFinal = new Point3d(x, y, z);		
//									LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
//									desbaste.add(vert);
//									pontoInicial = new Point3d(x, y, z);
//									
//									if(largura[a+2]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
//										p=0;
//										z=holdZ;
//
//										pontoFinal = new Point3d(x, y, z);		
//										LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
//										desbaste.add(verti);
//										pontoInicial = new Point3d(x, y, z);
//									}
//									a++;
//								}
//								else{
//									b+=2;
//									break;
//								}
//							}
						}	
						else{//parte CONVEXA
						//	if(p==0){
								holdZ=z;
							//	p++;
							//}
							z=zMax[numero];
							numero++;

							pontoFinal = new Point3d(x, y, z);		
							LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(ver);
							pontoInicial = new Point3d(x, y, z);

							if(a+1==numeroDeConcavidades){//se a convexa é a ultima parte
								x=this.ranhuraBezier.getPosicaoX()+this.ranhuraBezier.getLargura()-allowanceBottom-diametroFerramenta/2;
							}
							else
								x+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava

							pontoFinal = new Point3d(x, y, z);		
							LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(vert);
							pontoInicial = new Point3d(x, y, z);
							
						//	if(largura[a+1]-diametroFerramenta-2*allowanceBottom>=diametroFerramenta){
							//	p=0;
								z=holdZ;
								pontoFinal = new Point3d(x, y, z);		
								LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verti);
								pontoInicial = new Point3d(x, y, z);
						//	}
							
//							holdZ=z;//guarda o valor de z
//							z=zMax[c];
//							c++;
//
//							pontoFinal = new Point3d(x, y, z);		
//							LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
//							desbaste.add(ver);
//							pontoInicial = new Point3d(x, y, z);
//
//							if(a+1==numeroDeConcavidades){//se a convexa é a ultima parte
//								x=this.ranhuraBezier.getPosicaoX()+this.ranhuraBezier.getLargura()-allowanceBottom-diametroFerramenta/2;
//							}
//							else
//								x+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
//
//							pontoFinal = new Point3d(x, y, z);
//							LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
//							desbaste.add(vert);
//							pontoInicial = new Point3d(x, y, z);
//
//							z=holdZ;
//
//							pontoFinal = new Point3d(x, y, z);
//							LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
//							desbaste.add(verti);
//							pontoInicial = new Point3d(x, y, z);
						}
						//}
					}
					fundo=true;
				}
				else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA
					//x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[t-1].getX()-allowanceBottom-diametroFerramenta/2;

					pontoFinal = new Point3d(x,y,z);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					pontoInicial = new Point3d(x, y, z);

					for(a=0;a<numeroDeConcavidades;a++){
						//if(diametroFerramenta<=largura[a]-2*allowanceBottom){
						if(inicio){
							ultimoAe=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
							numeroDeAes=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
							for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
								if(vaiVolta){
									y=this.ranhuraBezier.getComprimento()+diametroFerramenta;//vai para o final
									vaiVolta=false;
								}
								else{
									y=this.ranhuraBezier.getPosicaoY()-diametroFerramenta;//volta pro começo
									vaiVolta=true;
								}

								pontoFinal = new Point3d(x, y, z);
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);

								if(k!=numeroDeAes)//se nao é a ultima passada ele subtrai ae em X
									x-=ae;
								else
									x-=ultimoAe;//subtrai em x o ultimoAe já que está na ultima passada

								pontoFinal = new Point3d(x, y, z);
								LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(vert);
								pontoInicial = new Point3d(x, y, z);
								inicio=false;
							}

						}	
						else{
							inicio=true;
							holdZ=z;//guarda o valor de z
							z=zMax[c];
							c++;

							pontoFinal = new Point3d(x, y, z);		
							LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(ver);
							pontoInicial = new Point3d(x, y, z);

							if(a+1==numeroDeConcavidades)//se a convexa é a ultima parte
								x=pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;
							else
								x-=largura[numeroDeConcavidades-a-1]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava

							pontoFinal = new Point3d(x, y, z);
							LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(vert);
							pontoInicial = new Point3d(x, y, z);

							z=holdZ;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verti);
							pontoInicial = new Point3d(x, y, z);
						}
						//}
					}
					fundo=false;
				}
			}
		}

		return desbaste;
	}

	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();

		double x=this.ranhuraBezier.getPosicaoX();
		double y=this.ranhuraBezier.getPosicaoY();
		double z=this.ranhuraBezier.getPosicaoZ();
		double r=this.ferramenta.getDiametroFerramenta()/2;
		double alfa;
		double h;
		double distanciaEntrePontos=0;
		int numeroDePontos=this.ranhuraBezier.getPontosDaCurva().length;
		int i;
		boolean vaiVolta=true;
		Point3d[] ponto= this.ranhuraBezier.getPontosDaCurva();
		Point3d pontoInicial=null;
		Point3d pontoFinal;


		if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){
			distanciaEntrePontos= ponto[1].getX()-ponto[0].getX();
		}
		else if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.HORIZONTAL){
			distanciaEntrePontos= ponto[1].getY()-ponto[0].getY();
		}



		for(i=0;i<numeroDePontos;i++){
			if(i==0)
				alfa=(Math.atan((ponto[i+1].getZ()-ponto[i].getZ())/distanciaEntrePontos));
			else
				alfa=(Math.atan((ponto[i].getZ()-ponto[i-1].getZ())/distanciaEntrePontos));

			if(i==0){
				if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){
					x=this.ranhuraBezier.getPosicaoX()+Math.abs(Math.sin(alfa)*r);
					pontoInicial= new Point3d(x,this.ranhuraBezier.getPosicaoY(),this.ws.getOperation().getRetractPlane());
				}
				else if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.HORIZONTAL){
					y=this.ranhuraBezier.getPosicaoY()+Math.abs(Math.sin(alfa)*r);
					pontoInicial= new Point3d(this.ranhuraBezier.getPosicaoX(),y,this.ws.getOperation().getRetractPlane());
				}
			}


			//ADICIONA Z
			h=r-Math.cos(alfa)*r;
			z=ponto[i].getZ()-h;

			pontoFinal = new Point3d(x, y, z);
			LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
			acabamento.add(verti);
			pontoInicial = new Point3d(x, y, z);


			if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){
				//ADICIONA Y
				if(vaiVolta){
					y=this.ranhuraBezier.getComprimento();
					vaiVolta=false;
				}
				else{
					y=this.ranhuraBezier.getPosicaoY();
					vaiVolta=true;
				}
				pontoFinal = new Point3d(x, y, z);
				LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(vert);
				pontoInicial = new Point3d(x, y, z);

				if(i!=0){
					if(ponto[i].getZ()<ponto[i-1].getZ())
						x=this.ranhuraBezier.getPosicaoX()+ponto[i].getX()+Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
					else if(ponto[i].getZ()>ponto[i-1].getZ())
						x=this.ranhuraBezier.getPosicaoX()+ponto[i].getX()-Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
				}
			}
			else if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.HORIZONTAL){
				if(vaiVolta){
					x=this.ranhuraBezier.getComprimento();
					vaiVolta=false;
				}
				else{
					x=this.ranhuraBezier.getPosicaoX();
					vaiVolta=true;
				}
				pontoFinal = new Point3d(x, y, z);
				LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
				acabamento.add(vert);
				pontoInicial = new Point3d(x, y, z);
				if(i!=0){
					if(ponto[i].getZ()<ponto[i-1].getZ())
						y=this.ranhuraBezier.getPosicaoY()+ponto[i].getY()+Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
					else if(ponto[i].getZ()>ponto[i-1].getZ())
						y=this.ranhuraBezier.getPosicaoY()+ponto[i].getY()-Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
				}
			}
		}


		return acabamento;
	}

	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhuraPerfilGenerico() {
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
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhuraPerfilGenerico() {
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


