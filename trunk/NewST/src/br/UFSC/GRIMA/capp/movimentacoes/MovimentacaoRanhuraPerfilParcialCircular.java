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
		
		double zLimite;
		double profundidade=this.ranhuraParcCirc.getProfundidade();
		double R=this.ranhuraParcCirc.getLargura()/2;
		double lateral, lateralProximo, frenteProximo, frente;
		double xAtual=this.ranhuraParcCirc.getPosicaoX();
		double xProximo;
		double yAtual=this.ranhuraParcCirc.getPosicaoY();
		double yVar=this.face.getLargura();
		double zAtual=this.ranhuraParcCirc.getPosicaoZ();
		double diametroFerramenta=this.ferramenta.getDiametroFerramenta();
		double allowanceBottom= ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		double ultimoApLinear,ultimoApCurva;
		double numeroDeApsLinear, numeroDeApsCurva;
		double xLimiteDireita, xLimiteEsquerda, largura,z,temp2=this.face.getLargura(),temp=0, limiteEsquerda, limiteDireita;
		double aeUtilizado;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ae=this.ws.getCondicoesUsinagem().getAe();
		double meio;
		double alfa=60*Math.PI/180;
		double Dz=this.ranhuraParcCirc.getDz();
		double xQueSobrou=0;
		double hold=0;
		double ultimoAp,numeroDeAps, ultimoAe, numeroDeAes;
		double cima;
		int i,k,cont;
		
		
		boolean fundo = false;
		boolean vaiVolta = true;
		boolean terminouXY=false;
		
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		meio=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()/2;	
		
		//DESCER EM Z
		largura=this.ranhuraParcCirc.getLargura();
		ultimoAp=(R-Dz)%ap;
		numeroDeAps=1+(R-Dz-ultimoAp)/ap;//1 para andar o ultimo AP!
		
		if(alfa==Math.PI/2)
			cima=0;
		else if(alfa<Math.PI/2)
			cima=this.face.getLargura()/Math.tan(alfa);
		else
			cima=-this.face.getLargura()/Math.tan(alfa);
		
		
		//yAtual=this.ranhuraParcCirc.getPosicaoY()+diametroFerramenta/2+allowanceBottom;
		
		if(this.ranhuraParcCirc.getEixo()== RanhuraPerfilCircularParcial.VERTICAL){

			xAtual=this.ranhuraParcCirc.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
			pontoInicial = new Point3d(xAtual,this.ranhuraParcCirc.getPosicaoY(),this.ws.getOperation().getRetractPlane());//ponto inicializador do Array
			
			//Calculo dos limites de X........
						//LIMITES INFERIORES
			limiteEsquerda=this.ranhuraParcCirc.getPosicaoX()+allowanceBottom+diametroFerramenta/2;
			limiteDireita=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()-allowanceBottom-diametroFerramenta/2;
						//LIMITES SUPERIORES
			if(this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2<=this.face.getComprimento())
				xLimiteDireita=this.ranhuraParcCirc.getPosicaoX()+this.ranhuraParcCirc.getLargura()+cima-allowanceBottom-diametroFerramenta/2;
			else{
				xQueSobrou=this.ranhuraParcCirc.getPosicaoX()
						+this.ranhuraParcCirc.getLargura()
						+cima
						-this.face.getComprimento();
				xLimiteDireita=this.face.getComprimento();
			}
			if(this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2>=this.face.getComprimento())
				xLimiteEsquerda=xLimiteDireita;
			else if(this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2<=0)//passa pela esquerda
				xLimiteEsquerda=0;
			else
				xLimiteEsquerda=this.ranhuraParcCirc.getPosicaoX()+cima+allowanceBottom+diametroFerramenta/2;
			
			
			
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

					cont=0;
					//Renovação dos limites de X.......
					limiteEsquerda+=temp;
					limiteDireita-=temp;

					largura = limiteDireita-limiteEsquerda;
					ultimoAe=largura%ae;
					numeroDeAes=1+(largura-ultimoAe)/ae;//1 para andar o ultimoAe!!!

					if(xLimiteDireita!=xLimiteEsquerda){//se os limites forem iguais eles nao vao mudar.
						xLimiteEsquerda+=temp;
						if(xQueSobrou<=0){
							xLimiteDireita-=temp;
						}
						else{
							xQueSobrou-=temp;
							if(xQueSobrou<0)
								xLimiteDireita+=xQueSobrou;
						}
					}
					z=R-Dz+zAtual;
					if(Math.asin((R-z)/(R))!=Math.PI/2)
						temp = Math.tan(Math.asin((R-z)/(R)))*ap;
					else
						if(fundo)
							temp = xAtual-meio;
						else
							temp = meio-xAtual;

					if(fundo)
						xAtual-=temp;
					else
						xAtual+=temp;

					//Calculo da movimentacao em X quando desce Ap..........
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					pontoInicial = new Point3d(xAtual, yAtual, zAtual);

					for(k=(int)numeroDeAes;k>0;k--){
						if(k==1 && ultimoAe==0){
							if(fundo)
								fundo=false;
							else
								fundo=true;
							k=0;
						}
						else{
							if(vaiVolta){//VAI PRA CIMA
								hold=xAtual;
								if(!fundo){//MOVIMENTACAO DA ESQUERDA PRA DIREITA
									if(xAtual+cima<xLimiteEsquerda)
										xAtual=xLimiteEsquerda;
									else{
										if(xAtual+cima<=xLimiteDireita)
											xAtual+=cima;
										else
											xAtual=xLimiteDireita;
									}
								}else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA
									if(xAtual+cima>xLimiteDireita)
										xAtual=xLimiteDireita;
									else{
										if(xAtual+cima>=xLimiteEsquerda)
											xAtual+=cima;	
										else
											xAtual=xLimiteEsquerda;
									}
								}
								if(alfa==Math.PI/2)
									yAtual=this.ranhuraParcCirc.getComprimento();
								else
									yAtual=Math.abs(Math.tan(alfa)*(xAtual-hold));
							}
							else{//VAI PRA BAIXO!
								yAtual= this.ranhuraParcCirc.getPosicaoY();
								if(!fundo){//MOVIMENTACAO DA ESQUERDA PRA DIREITA
									if(xAtual-cima<=limiteDireita)
										xAtual-=cima;
									else
										xAtual=limiteDireita;
								}else{//MOVIMENTACAO DA DIREITA PRA ESQUERDA
									if(xAtual-cima>=limiteEsquerda)
										xAtual-=cima;
									else
										xAtual=limiteEsquerda;
								}
							}

							pontoFinal = new Point3d(xAtual, yAtual, zAtual);
							LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
							desbaste.add(verticalTempp);
							pontoInicial = new Point3d(xAtual, yAtual, zAtual);

							if(k!=1){
								if(vaiVolta){
									if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
										if(xAtual+ae>=xLimiteDireita){
											hold = xAtual+ae;
											xAtual=xLimiteDireita;
										}
										else
											xAtual+=ae;
									else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
										if(xAtual-ae<=xLimiteEsquerda){
											hold = xAtual-ae;
											xAtual=xLimiteEsquerda;
										}
										else
											xAtual-=ae;
									//Math.abs(hold-xAtual) Partezinha...
									//tem que andar alguma coisa em y...  y=Math.tan(alfa)*Math.abs(hold-xAtual);
									if(hold-xAtual!=0 && cont==0){
										yVar = yAtual;
										cont++;
										
										pontoFinal = new Point3d(xAtual, yVar, zAtual);
										LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(horizontalTemp);
										pontoInicial = new Point3d(xAtual, yVar, zAtual);
										
										temp2+= (Math.tan(alfa)*(ae-Math.abs(hold-xAtual)));
									}										
									if(xQueSobrou>0 && vaiVolta && (xAtual==xLimiteDireita || xAtual==xLimiteDireita)){
										temp2-=Math.tan(alfa)*ae;
										yVar=temp2;
									}
									else if(vaiVolta)
										yVar=yAtual;
									vaiVolta=false;
								}
								else{
									if(!fundo)//MOVIMENTACAO DA ESQUERDA PRA DIREITA
										if(xAtual+ae>=limiteDireita)
											xAtual=limiteDireita;
										else
											xAtual+=ae;
									else//MOVIMENTACAO DA DIREITA PRA ESQUERDA
										if(xAtual-ae<=limiteEsquerda)
											xAtual=limiteEsquerda;
										else
											xAtual-=ae;
									yVar=yAtual;
									vaiVolta=true;
								}
								pontoFinal = new Point3d(xAtual, yVar, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yVar, zAtual);
							}
							else{
								if(vaiVolta){
									if(fundo){//MOVIMENTACAO DA DIREITA PRA ESQUERDA
										if(xAtual-ultimoAe<=xLimiteEsquerda)
											xAtual=xLimiteEsquerda;
										else
											xAtual-=ultimoAe;
										fundo=false;
									}
									else{//MOVIMENTACAO DA ESQUERDA PRA DIREITA
										if(xAtual+ultimoAe>=xLimiteDireita)
											xAtual=xLimiteDireita;
										else
											xAtual+=ultimoAe;
										fundo=true;
									}
									if(hold-xAtual!=0 && cont==0){
										yVar = yAtual;
										cont++;
										
										pontoFinal = new Point3d(xAtual, yVar, zAtual);
										LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
										desbaste.add(horizontalTemp);
										pontoInicial = new Point3d(xAtual, yVar, zAtual);
										
										temp2+= (Math.tan(alfa)*(ae-Math.abs(hold-xAtual)));
									}	
									if(xQueSobrou>0 && vaiVolta && (xAtual==xLimiteDireita || xAtual==xLimiteDireita)){
										temp2-=Math.tan(alfa)*ae;
										yVar=temp2;
									}
									else if(vaiVolta)
										yVar=yAtual;
									vaiVolta=false;
								}
								else{
									if(fundo){//MOVIMENTACAO DA DIREITA PRA ESQUERDA
										if(xAtual-ultimoAe<=limiteEsquerda)
											xAtual=limiteEsquerda;
										else
											xAtual-=ultimoAe;
										fundo=false;
									}
									else{//MOVIMENTACAO DA ESQUERDA PRA DIREITA
										if(xAtual+ultimoAe>=limiteDireita)
											xAtual=limiteDireita;
										else
											xAtual+=ultimoAe;
										fundo=true;
									}
									yVar=yAtual;
									vaiVolta=true;
								}
								pontoFinal = new Point3d(xAtual, yVar, zAtual);
								LinearPath horizontalTemp = new LinearPath(pontoInicial,pontoFinal);
								desbaste.add(horizontalTemp);
								pontoInicial = new Point3d(xAtual, yVar, zAtual);
							}
						}
					}
				}

			}
		}else if(this.ranhuraParcCirc.getEixo()==RanhuraPerfilCircularParcial.HORIZONTAL){
			
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
