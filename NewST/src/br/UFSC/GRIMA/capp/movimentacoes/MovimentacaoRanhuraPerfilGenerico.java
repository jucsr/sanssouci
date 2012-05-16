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
		double y=this.ranhuraBezier.getPosicaoY();
		double z=this.ranhuraBezier.getPosicaoZ();
		double largura[] = new double [101];
		double zMax[] = new double [101];
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ultimoAp, numeroDeAps, ultimoAe, numeroDeAes,holdZ=0;
		double profundidadeMaxima=0;
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		int numeroDePontos=this.ranhuraBezier.getPontosDaCurva().length;
		int a=0,k,i,n,j,b,h,c,m,u=1,numeroDeConcavidades,p=0,r,s=0,numero,q,l;
		int t[] = new int[101];
		boolean fundo=false, vaiVolta=true;
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
		r=a;
		
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

			holdZ = z;
			a=0;k=0;n=0;b=0;c=0;numero=r-1;

			pontosDeInterferencia = new Point3d[101];
			
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
				largura = new double [101];
				for(b=1;b<n;b++){//n é o numero de pontos que achou!!!
					largura[k]=pontosDeInterferencia[b].getX()-pontosDeInterferencia[b-1].getX();
					k++;
				}
				if(p==0){
					pontoInicial = new Point3d(this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom,y,this.ranhuraBezier.getPosicaoZ());
					p++;
				}


				if(k!=0)
					h=k;
				else
					break;
				
				q=0;l=0;
				for(b=0;b<h;b++){
					if(largura[b]-2*allowanceBottom<diametroFerramenta)
						q++;
					b++;
					l++;
				}
				if(q==l){
					break;
				}else{
					b=0;	numeroDeConcavidades=k;	c=0;

					if(!fundo){
		/*****************************PULA PARA A PROXIMA CONCAVIDADE COM SEGURANÇA CASO O NUMERO DE CONCAVIDADES DIMINUA***********************************/
						
						if(j!=numeroDeAps && j!=numeroDeAps-1){
							if(x+ponto[1].getX()-ponto[0].getX()+0.5<pontosDeInterferencia[0].getX()+this.ranhuraBezier.getPosicaoX()){
								z=zMax[s];
								s++;c++;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
								
								x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom;
								
								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTempp);
								pontoInicial = new Point3d(x, y, z);

								if(largura[0]-2*allowanceBottom>=diametroFerramenta){
									z=holdZ;

									pontoFinal = new Point3d(x, y, z);
									LinearPath verticalTemppp = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(verticalTemppp);
									pontoInicial = new Point3d(x, y, z);
								}
							}
							/**************************DEFINE O PONTO DE INICIALIZAÇAO DO MOVIMENTO CASO SEJA POSSIVEL************************************/
							else if(largura[0]-2*allowanceBottom>=diametroFerramenta){
								x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
							}
						}
						else if(largura[0]-2*allowanceBottom>=diametroFerramenta){
							x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTemp);
							pontoInicial = new Point3d(x, y, z);
						}

						/***********************************COMEÇA A MOVIMENTAÇÃO NAS CAVIDADES**********************************************/
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0){
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO CAIBA)*****************************/
								if(largura[a]-2*allowanceBottom>=diametroFerramenta){
									b+=2;
									ultimoAe=(largura[a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
									numeroDeAes=(largura[a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
									for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
										if(vaiVolta){
											y=this.ranhuraBezier.getComprimento();//vai para o final
											vaiVolta=false;
										}
										else{
											y=this.ranhuraBezier.getPosicaoY();//volta pro começo
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
								}
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO NÃO CAIBA)*****************************/
								else{
									if(pontosDeInterferencia[b+3]!=null){//lembrar de zerar toda vez...
										z=zMax[c];
										c++;

										pontoFinal = new Point3d(x, y, z);		
										LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(ver);
										pontoInicial = new Point3d(x, y, z);

										b+=2;
										x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()+diametroFerramenta/2+allowanceBottom;

										pontoFinal = new Point3d(x, y, z);		
										LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(vert);
										pontoInicial = new Point3d(x, y, z);

										if(largura[a+2]-2*allowanceBottom>=diametroFerramenta){
											z=holdZ;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(verti);
											pontoInicial = new Point3d(x, y, z);
										}
										a++;
									}
									else{
										break;
									}
								}
							}	
							/*************************************MOVIMENTAÇÃO PARTE CONVEXA*************************************/
							else{
								z=zMax[c];
								c++;

								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);
								
								if(a+1!=numeroDeConcavidades)
									if(a+2 == numeroDeConcavidades){
										if(largura[numeroDeConcavidades-1]-2*allowanceBottom>=diametroFerramenta)
											x+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
									}
									else
										x+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava


								pontoFinal = new Point3d(x, y, z);		
								LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(vert);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades){
									if(largura[a+1]-2*allowanceBottom>=diametroFerramenta){
										z=holdZ;
										pontoFinal = new Point3d(x, y, z);		
										LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(verti);
										pontoInicial = new Point3d(x, y, z);
									}
								}
							}
						}
						fundo=true;
					}
/********************************************************MOVIMENTACAO DA DIREITA PRA ESQUERDA (VOLTA)*************************************************************/
					else{
						b=t[j]-1;
			/*****************************PULA PARA A PROXIMA CONCAVIDADE COM SEGURANÇA CASO O NUMERO DE CONCAVIDADES DIMINUA***********************************/
						if(j!=numeroDeAps && j!=numeroDeAps-1){
							if(x-ponto[1].getX()+ponto[0].getX()-0.5>pontosDeInterferencia[b].getX()+this.ranhuraBezier.getPosicaoX()){
								z=zMax[m-1];
								m--;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);

								x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()-diametroFerramenta/2-allowanceBottom;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTempp);
								pontoInicial = new Point3d(x, y, z);

								if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){
									z=holdZ;

									pontoFinal = new Point3d(x, y, z);
									LinearPath verticalTemppp = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(verticalTemppp);
									pontoInicial = new Point3d(x, y, z);
								}
							}
					/**************************DEFINE O PONTO DE INICIALIZAÇAO DO MOVIMENTO CASO SEJA POSSIVEL************************************/
							else if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){

								x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()-allowanceBottom-diametroFerramenta/2;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
							}
						}
						else if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){

							x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()-allowanceBottom-diametroFerramenta/2;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTemp);
							pontoInicial = new Point3d(x, y, z);
						}

					/***********************************COMEÇA A MOVIMENTAÇÃO NAS CAVIDADES**********************************************/
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0 && numeroDeConcavidades%2!=0){
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO CAIBA)*****************************/
								if(largura[numeroDeConcavidades-1-a]-2*allowanceBottom>=diametroFerramenta){
									b-=2;
									ultimoAe=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
									numeroDeAes=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
									for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
										if(vaiVolta){
											y=this.ranhuraBezier.getComprimento();//vai para o final
											vaiVolta=false;
										}
										else{
											y=this.ranhuraBezier.getPosicaoY();//volta pro começo
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
									}
								}
							/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO NÃO CAIBA)*****************************/
								else{
									if(b-3>=0){
										if(pontosDeInterferencia[b-3]!=null){//lembrar de zerar toda vez...
											z=zMax[numero];
											numero--;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(ver);
											pontoInicial = new Point3d(x, y, z);

											b-=2;
											x=this.ranhuraBezier.getPosicaoX()+pontosDeInterferencia[b].getX()-diametroFerramenta/2-allowanceBottom;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(vert);
											pontoInicial = new Point3d(x, y, z);

											if(largura[numeroDeConcavidades-a-2]-2*allowanceBottom>=diametroFerramenta){
												z=holdZ;

												pontoFinal = new Point3d(x, y, z);		
												LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
												desbaste.add(verti);
												pontoInicial = new Point3d(x, y, z);
											}
											a++;
										}
									}
									else{
										break;
									}
								}
							}
							/*************************************MOVIMENTAÇÃO PARTE CONVEXA*************************************/
							else{
								z=zMax[numero];
								numero--;

								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades)
									if(a+2 == numeroDeConcavidades){
										if(largura[0]-2*allowanceBottom>=diametroFerramenta)
											x-=largura[numeroDeConcavidades-a-1]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
									}
									else
										x-=largura[numeroDeConcavidades-a-1]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava


								pontoFinal = new Point3d(x, y, z);		
								LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(vert);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades){
									if(largura[numeroDeConcavidades-a-2]-2*allowanceBottom>=diametroFerramenta){
										z=holdZ;
										pontoFinal = new Point3d(x, y, z);		
										LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(verti);
										pontoInicial = new Point3d(x, y, z);
									}
								}
							}
						}
						fundo=false;
					}
				}
			}
			else if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.HORIZONTAL){

				/***************************DEFINE AS LARGURAS DAS "PARABOLAS"*****************************/
				largura = new double [101];
				for(b=1;b<n;b++){//n é o numero de pontos que achou!!!
					largura[k]=pontosDeInterferencia[b].getX()-pontosDeInterferencia[b-1].getX();
					k++;
				}
				if(p==0){
					pontoInicial = new Point3d(x,this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom,this.ranhuraBezier.getPosicaoZ());
					p++;
				}


				if(k!=0)
					h=k;
				else
					break;
				
				q=0;l=0;
				for(b=0;b<h;b++){
					if(largura[b]-2*allowanceBottom<diametroFerramenta)
						q++;
					b++;
					l++;
				}
				if(q==l){
					break;
				}else{
					b=0;	numeroDeConcavidades=k;	c=0;

					if(!fundo){
		/*****************************PULA PARA A PROXIMA CONCAVIDADE COM SEGURANÇA CASO O NUMERO DE CONCAVIDADES DIMINUA***********************************/
						
						if(j!=numeroDeAps && j!=numeroDeAps-1){
							if(y+ponto[1].getX()-ponto[0].getX()+0.5<pontosDeInterferencia[0].getX()+this.ranhuraBezier.getPosicaoY()){
								z=zMax[s];
								s++;c++;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
								
								y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[0].getX()+diametroFerramenta/2+allowanceBottom;
								
								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTempp);
								pontoInicial = new Point3d(x, y, z);

								if(largura[0]-2*allowanceBottom>=diametroFerramenta){
									z=holdZ;

									pontoFinal = new Point3d(x, y, z);
									LinearPath verticalTemppp = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(verticalTemppp);
									pontoInicial = new Point3d(x, y, z);
								}
							}
							/**************************DEFINE O PONTO DE INICIALIZAÇAO DO MOVIMENTO CASO SEJA POSSIVEL************************************/
							else if(largura[0]-2*allowanceBottom>=diametroFerramenta){
								y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
							}
						}
						else if(largura[0]-2*allowanceBottom>=diametroFerramenta){
							y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTemp);
							pontoInicial = new Point3d(x, y, z);
						}

						/***********************************COMEÇA A MOVIMENTAÇÃO NAS CAVIDADES**********************************************/
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0){
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO CAIBA)*****************************/
								if(largura[a]-2*allowanceBottom>=diametroFerramenta){
									b+=2;
									ultimoAe=(largura[a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
									numeroDeAes=(largura[a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
									for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
										if(vaiVolta){
											x=this.ranhuraBezier.getComprimento();//vai para o final
											vaiVolta=false;
										}
										else{
											x=this.ranhuraBezier.getPosicaoX();//volta pro começo
											vaiVolta=true;
										}

										pontoFinal = new Point3d(x, y, z);
										LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(ver);
										pontoInicial = new Point3d(x, y, z);

										if(k!=numeroDeAes)//se nao é a ultima passada ele adiciona ae em X
											y+=ae;
										else
											y+=ultimoAe;//adiciona em x o ultimoAe já que está na ultima passada

										pontoFinal = new Point3d(x, y, z);
										LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(vert);
										pontoInicial = new Point3d(x, y, z);
									}
								}
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO NÃO CAIBA)*****************************/
								else{
									if(pontosDeInterferencia[b+3]!=null){//lembrar de zerar toda vez...
										z=zMax[c];
										c++;

										pontoFinal = new Point3d(x, y, z);		
										LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(ver);
										pontoInicial = new Point3d(x, y, z);

										b+=2;
										y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[b].getX()+diametroFerramenta/2+allowanceBottom;

										pontoFinal = new Point3d(x, y, z);		
										LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(vert);
										pontoInicial = new Point3d(x, y, z);

										if(largura[a+2]-2*allowanceBottom>=diametroFerramenta){
											z=holdZ;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(verti);
											pontoInicial = new Point3d(x, y, z);
										}
										a++;
									}
									else{
										break;
									}
								}
							}	
							/*************************************MOVIMENTAÇÃO PARTE CONVEXA*************************************/
							else{
								z=zMax[c];
								c++;

								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);
								
								if(a+1!=numeroDeConcavidades)
									if(a+2 == numeroDeConcavidades){
										if(largura[numeroDeConcavidades-1]-2*allowanceBottom>=diametroFerramenta)
											y+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
									}
									else
										y+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava


								pontoFinal = new Point3d(x, y, z);		
								LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(vert);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades){
									if(largura[a+1]-2*allowanceBottom>=diametroFerramenta){
										z=holdZ;
										pontoFinal = new Point3d(x, y, z);		
										LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(verti);
										pontoInicial = new Point3d(x, y, z);
									}
								}
							}
						}
						fundo=true;
					}
/********************************************************MOVIMENTACAO DA DIREITA PRA ESQUERDA (VOLTA)*************************************************************/
					else{
						b=t[j]-1;
			/*****************************PULA PARA A PROXIMA CONCAVIDADE COM SEGURANÇA CASO O NUMERO DE CONCAVIDADES DIMINUA***********************************/
						if(j!=numeroDeAps && j!=numeroDeAps-1){
							if(y-ponto[1].getX()+ponto[0].getX()-0.5>pontosDeInterferencia[b].getX()+this.ranhuraBezier.getPosicaoY()){
								z=zMax[m-1];
								m--;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);

								y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[b].getX()-diametroFerramenta/2-allowanceBottom;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTempp);
								pontoInicial = new Point3d(x, y, z);

								if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){
									z=holdZ;

									pontoFinal = new Point3d(x, y, z);
									LinearPath verticalTemppp = new LinearPath(pontoInicial,pontoFinal);
									desbaste.add(verticalTemppp);
									pontoInicial = new Point3d(x, y, z);
								}
							}
					/**************************DEFINE O PONTO DE INICIALIZAÇAO DO MOVIMENTO CASO SEJA POSSIVEL************************************/
							else if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){

								y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[b].getX()-allowanceBottom-diametroFerramenta/2;

								pontoFinal = new Point3d(x, y, z);
								LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(verticalTemp);
								pontoInicial = new Point3d(x, y, z);
							}
						}
						else if(largura[h-1]-2*allowanceBottom>=diametroFerramenta){

							y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[b].getX()-allowanceBottom-diametroFerramenta/2;

							pontoFinal = new Point3d(x, y, z);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTemp);
							pontoInicial = new Point3d(x, y, z);
						}

					/***********************************COMEÇA A MOVIMENTAÇÃO NAS CAVIDADES**********************************************/
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0 && numeroDeConcavidades%2!=0){
								/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO CAIBA)*****************************/
								if(largura[numeroDeConcavidades-1-a]-2*allowanceBottom>=diametroFerramenta){
									b-=2;
									ultimoAe=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta)%ae;//calcula o ultimo ae
									numeroDeAes=(largura[numeroDeConcavidades-1-a]-2*allowanceBottom-diametroFerramenta-ultimoAe)/ae;//calcula quantas vezes posso dar ae
									for(k=0;k<=numeroDeAes;k++){//anda todos os aes possiveis inclusive o ultimoAe....
										if(vaiVolta){
											x=this.ranhuraBezier.getComprimento();//vai para o final
											vaiVolta=false;
										}
										else{
											x=this.ranhuraBezier.getPosicaoX();//volta pro começo
											vaiVolta=true;
										}

										pontoFinal = new Point3d(x, y, z);
										LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(ver);
										pontoInicial = new Point3d(x, y, z);

										if(k!=numeroDeAes)//se nao é a ultima passada ele subtrai ae em X
											y-=ae;
										else
											y-=ultimoAe;//subtrai em x o ultimoAe já que está na ultima passada

										pontoFinal = new Point3d(x, y, z);
										LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(vert);
										pontoInicial = new Point3d(x, y, z);
									}
								}
							/*************************MOVIMENTAÇÃO PARTE CONCAVA(CASO NÃO CAIBA)*****************************/
								else{
									if(b-3>=0){
										if(pontosDeInterferencia[b-3]!=null){//lembrar de zerar toda vez...
											z=zMax[numero];
											numero--;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(ver);
											pontoInicial = new Point3d(x, y, z);

											b-=2;
											y=this.ranhuraBezier.getPosicaoY()+pontosDeInterferencia[b].getX()-diametroFerramenta/2-allowanceBottom;

											pontoFinal = new Point3d(x, y, z);		
											LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
											desbaste.add(vert);
											pontoInicial = new Point3d(x, y, z);

											if(largura[numeroDeConcavidades-a-2]-2*allowanceBottom>=diametroFerramenta){
												z=holdZ;

												pontoFinal = new Point3d(x, y, z);		
												LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
												desbaste.add(verti);
												pontoInicial = new Point3d(x, y, z);
											}
											a++;
										}
									}
									else{
										break;
									}
								}
							}
							/*************************************MOVIMENTAÇÃO PARTE CONVEXA*************************************/
							else{
								z=zMax[numero];
								numero--;

								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades)
									if(a+2 == numeroDeConcavidades){
										if(largura[0]-2*allowanceBottom>=diametroFerramenta)
											y-=largura[numeroDeConcavidades-a-1]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
									}
									else
										y-=largura[numeroDeConcavidades-a-1]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava


								pontoFinal = new Point3d(x, y, z);		
								LinearPath vert = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(vert);
								pontoInicial = new Point3d(x, y, z);

								if(a+1!=numeroDeConcavidades){
									if(largura[numeroDeConcavidades-a-2]-2*allowanceBottom>=diametroFerramenta){
										z=holdZ;
										pontoFinal = new Point3d(x, y, z);		
										LinearPath verti = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(verti);
										pontoInicial = new Point3d(x, y, z);
									}
								}
							}
						}
						fundo=false;
					}
				}
			}
			z=holdZ;
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

			distanciaEntrePontos= ponto[1].getX()-ponto[0].getX();

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
						y=this.ranhuraBezier.getPosicaoY()+ponto[i].getX()+Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
					else if(ponto[i].getZ()>ponto[i-1].getZ())
						y=this.ranhuraBezier.getPosicaoY()+ponto[i].getX()-Math.abs(Math.sin(alfa)*r)+distanciaEntrePontos;
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


