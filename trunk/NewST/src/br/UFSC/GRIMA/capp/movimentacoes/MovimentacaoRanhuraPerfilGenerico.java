package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

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
		double y=this.ranhuraBezier.getPosicaoY();
		double z=this.ranhuraBezier.getPosicaoZ();
		double largura[]=null,zMax[]=null;
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ultimoAp, numeroDeAps, ultimoAe, numeroDeAes,holdZ;
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		int numeroDePontos=this.ranhuraBezier.getPontosDaCurva().length;
		int a,k,i,n,j,b,c,numeroDeConcavidades,p=0;
		boolean concavo=false, fundo=false, vaiVolta=true;
		Point3d pontosDeInterferencia[]=null;
		Point3d ponto[]=this.ranhuraBezier.getPontosDaCurva();
		Point3d pontoInicial=null;
		Point3d pontoFinal;

		System.out.println(this.ranhuraBezier.getProfundidadeMaxima());
//		IMPORTANTE
//		
//		
//		getX : pega o X
//		getY : pega o Z
//		getZ : pega o Y
		
		
		ultimoAp= this.ranhuraBezier.getProfundidadeMaxima()%ap;
		numeroDeAps=(this.ranhuraBezier.getProfundidadeMaxima()-ultimoAp)/ap;
		
		
		//if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){
		
		for(j=(int)numeroDeAps;j>0;j--){
			z-=ap;
			a=0;k=0;n=0;b=0;c=0;
																					
			for(i=0;i<numeroDePontos;i++){												
				if(n%2==0){//se n é par													
					if(ponto[i].getY()>z){//PRIMEIRO PONTO DA LARGURA					
						pontosDeInterferencia[n]=ponto[i];								
						n++;c++;
						if(ponto[i-1].getZ()>ponto[i].getZ() && c==1){
							concavo=true;
						}
					}
				}
				else{//se n é impar...
					if(zMax[a]<ponto[i].getY() && !concavo){
						zMax[a]=ponto[i].getY();
					}
					if(ponto[i].getY()<z){//SEGUNDO PONTO DA LARGURA
						pontosDeInterferencia[n]=ponto[i-1];
						n++;a++;
					}
					if(i==numeroDePontos-1){//ultima passada
						pontosDeInterferencia[n]=ponto[i];
					}
				}
			}
			//if(this.ranhuraBezier.getEixo()==RanhuraPerfilBezier.VERTICAL){
			for(b=1;b<=n;b++){
				largura[k]=pontosDeInterferencia[b].getX()-pontosDeInterferencia[b-1].getX();
				k++;
			}
			if(p==0){
				pontoInicial = new Point3d(pontosDeInterferencia[0].getX(),y,this.ranhuraBezier.getPosicaoZ());
				p++;
			}
			
			numeroDeConcavidades=largura.length;
			c=0;
			//for(b=0;b<numeroDeConcavidades;b++){
				if(!fundo){
					if(concavo){//a primeira largura é de forma concava
						x=pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;
						//y=this.ranhuraBezier.getPosicaoY();
						
						pontoFinal = new Point3d(x, y, z);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						pontoInicial = new Point3d(x, y, z);
						
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0){//parte CONCAVA
								if(diametroFerramenta>=largura[a]-2*allowanceBottom){
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
							}	
							else{//parte CONVEXA
								c++;
								holdZ=z;//guarda o valor de z
								z=(z-zMax[c]-(z-zMax[c]%ap)+1)*ap;//calcula o quando que z vai valer para passar pelo "pico" da parte convexa
								
								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);
								
								if(a+1==numeroDeConcavidades){//se a convexa é a ultima parte
									x=this.ranhuraBezier.getPosicaoX()+this.ranhuraBezier.getLargura()-allowanceBottom-diametroFerramenta/2;
									concavo=false;
								}
								else
									x+=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava
								
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
						}
					}
					else{//a primeira parte é convexa
						
					}
					fundo=true;
				}
				else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA
					if(concavo){
						for(a=0;a<numeroDeConcavidades;a++){
							if(a%2==0){
								if(diametroFerramenta>=largura[a]-2*allowanceBottom){
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
							}	
							else{
								c++;
								holdZ=z;//guarda o valor de z
								z=(z-zMax[c]-(z-zMax[c]%ap)+1)*ap;//calcula o quando que z vai valer para passar pelo "pico" da parte convexa

								pontoFinal = new Point3d(x, y, z);		
								LinearPath ver = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(ver);
								pontoInicial = new Point3d(x, y, z);

								if(a+1==numeroDeConcavidades)//se a convexa é a ultima parte
									x=pontosDeInterferencia[0].getX()+allowanceBottom+diametroFerramenta/2;
								else
									x-=largura[a]+2*allowanceBottom+diametroFerramenta;//ponto inicializador da parte concava

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
						}
					}
					else{
						
					}
					fundo=false;
				}
				
				//largura[b];
			//}
			
			
		//}
		
		}
		
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
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


