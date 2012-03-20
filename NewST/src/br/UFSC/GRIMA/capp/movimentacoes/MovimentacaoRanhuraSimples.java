package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import javax.vecmath.Point3d;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraSimples {
	private Workingstep ws;
	private Ranhura ranhura;
	private Ferramenta ferramenta;
	
	public MovimentacaoRanhuraSimples(Workingstep ws){
		this.ws = ws;
		this.ranhura = (Ranhura) this.ws.getFeature();
		this.ferramenta = this.ws.getFerramenta();
	}
	
	private ArrayList<LinearPath> lado1(){
		
		double xAtual = this.ranhura.getPosicaoX();
		double xProximo;
		double yAtual = this.ranhura.getPosicaoY();
		double yProximo;
		double zAtual = this.ranhura.getPosicaoZ();
		double apUtilizado;
		boolean terminouZ = false;
		boolean vaiVolta = true;
	
		ArrayList<LinearPath> lado1 = new ArrayList<LinearPath>();
		Point3d pontoInicial;
		Point3d pontoFinal;
		if (ranhura.getEixo() == Ranhura.HORIZONTAL){
			pontoInicial = new Point3d(0,this.ranhura.getPosicaoY() + this.ferramenta.getDiametroFerramenta()/2,this.ranhura.getPosicaoZ());
			while(!terminouZ){
				if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.ranhura.getProfundidade()){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.ranhura.getProfundidade()){
						terminouZ = true;
					}
				}
				else{
					apUtilizado = this.ranhura.getProfundidade() + zAtual;
					terminouZ = true;
				}
				zAtual = zAtual - apUtilizado;
				
				if(vaiVolta){
					xAtual = this.ranhura.getPosicaoX();
					xProximo = this.ranhura.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhura.getComprimento();
					xProximo = this.ranhura.getPosicaoX();
					vaiVolta = true;
				}
				yAtual = this.ranhura.getPosicaoY()+this.ferramenta.getDiametroFerramenta()/2;
				
				
				pontoFinal = new Point3d(xAtual,yAtual,zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				lado1.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
				lado1.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo,yAtual,zAtual);
			}
			
			Point3d ultimoPonto = lado1.get(lado1.size()-1).getFinalPoint();
			LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (this.ranhura.getPosicaoX(), this.ranhura.getPosicaoY()+this.ranhura.getLargura()-this.ferramenta.getDiametroFerramenta()/2 ,this.ws.getOperation().getRetractPlane()));
			planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
			lado1.add(planoSeguro);
			
		}
		else{ 
			if(ranhura.getEixo() == Ranhura.VERTICAL){	
				
				pontoInicial = new Point3d(this.ranhura.getPosicaoX() + this.ferramenta.getDiametroFerramenta()/2,0,this.ranhura.getPosicaoZ());
				while(!terminouZ){
					if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.ranhura.getProfundidade()){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.ranhura.getProfundidade()){
							terminouZ = true;
						}
					}
					else{
						apUtilizado = this.ranhura.getProfundidade() + zAtual;
						terminouZ = true;
					}
					zAtual = zAtual - apUtilizado;
				
					xAtual = this.ranhura.getPosicaoX() + this.ferramenta.getDiametroFerramenta()/2;
				
					if(vaiVolta){
						yAtual = this.ranhura.getPosicaoY();
						yProximo = this.ranhura.getLargura();
						vaiVolta = false;
					}
					else{
						yAtual = this.ranhura.getLargura();
						yProximo = this.ranhura.getPosicaoY();
						vaiVolta = true;
					}
					
					pontoFinal = new Point3d(xAtual,yAtual,zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					lado1.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
					lado1.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual,yProximo,zAtual);
				}
				
				Point3d ultimoPonto = lado1.get(lado1.size()-1).getFinalPoint();
				LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (this.ranhura.getPosicaoX()+this.ranhura.getComprimento()-this.ferramenta.getDiametroFerramenta()/2, this.ranhura.getPosicaoY() ,this.ws.getOperation().getRetractPlane()));
				planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
				lado1.add(planoSeguro);
			}
		}
		return lado1;
	}
	
	private ArrayList<LinearPath> lado2(){
		
		double xAtual = this.ranhura.getPosicaoX();
		double xProximo;
		double yAtual = this.ranhura.getPosicaoY();
		double yProximo;
		double zAtual = this.ranhura.getPosicaoZ();
		double apUtilizado;
		boolean terminouZ = false;
		boolean vaiVolta = true;
		
		ArrayList<LinearPath> lado2 = new ArrayList<LinearPath>();
		Point3d pontoInicial;
		Point3d pontoFinal;
		if (ranhura.getEixo() == Ranhura.HORIZONTAL){
			pontoInicial = new Point3d(0,this.ranhura.getPosicaoY() + this.ranhura.getLargura() - this.ferramenta.getDiametroFerramenta()/2,this.ranhura.getPosicaoZ());
			while(!terminouZ){
				if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.ranhura.getProfundidade()){
					apUtilizado = this.ws.getCondicoesUsinagem().getAp();
					if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.ranhura.getProfundidade()){
						terminouZ = true;
					}
				}
				else{
					apUtilizado = this.ranhura.getProfundidade() + zAtual;
					terminouZ = true;
				}
				zAtual = zAtual - apUtilizado;
				
				if(vaiVolta){
					xAtual = this.ranhura.getPosicaoX();
					xProximo = this.ranhura.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhura.getComprimento();
					xProximo = this.ranhura.getPosicaoX();
					vaiVolta = true;
				}
				
				yAtual = this.ranhura.getLargura() + this.ranhura.getPosicaoY() - this.ferramenta.getDiametroFerramenta()/2;
				
				pontoFinal = new Point3d(xAtual,yAtual,zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				lado2.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
				lado2.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo,yAtual,zAtual);
			}
		}
		else{ 
			if(ranhura.getEixo() == Ranhura.VERTICAL){
				pontoInicial = new Point3d(this.ranhura.getPosicaoX() + this.ferramenta.getDiametroFerramenta()/2,0,this.ranhura.getPosicaoZ());
				while(!terminouZ){
					if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.ranhura.getProfundidade()){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.ranhura.getProfundidade()){
							terminouZ = true;
						}
					}
					else{
						apUtilizado = this.ranhura.getProfundidade() + zAtual;
						terminouZ = true;
					}
					zAtual = zAtual - apUtilizado;
					
					xAtual = this.ranhura.getPosicaoX() + this.ranhura.getLargura() - this.ferramenta.getDiametroFerramenta()/2;
				
					if(vaiVolta){
						yAtual = this.ranhura.getPosicaoY();
						yProximo = this.ranhura.getLargura();
						vaiVolta = false;
					}
					else{
						yAtual = this.ranhura.getLargura();
						yProximo = this.ranhura.getPosicaoY();
						vaiVolta = true;
					}
					
					pontoFinal = new Point3d(xAtual,yAtual,zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					lado2.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
					lado2.add(horizontalTemp);
					pontoInicial = new Point3d(xAtual,yProximo,zAtual);
				}
			}
		}
		return lado2;
	}
	
	private ArrayList<LinearPath> fundo(){
		
		ArrayList<LinearPath> lado2 = this.lado2();		
		Point3d ultimoPonto = lado2.get(lado2.size()-1).getFinalPoint();
		double xAtual = ultimoPonto.getX(); 
		double xProximo;
		double yAtual = ultimoPonto.getY();
		double yProximo;
		double zAtual = ultimoPonto.getZ();
		boolean vaiVolta = true;
		boolean terminouY = false;
		boolean terminouX = false;

		ArrayList<LinearPath> fundo = new ArrayList<LinearPath>();
		Point3d pontoInicial;
		Point3d pontoFinal;
		if (ranhura.getEixo() == Ranhura.HORIZONTAL){
			pontoInicial = new Point3d(0,this.ranhura.getPosicaoY() + this.ranhura.getLargura() - this.ws.getCondicoesUsinagem().getAe(),this.ranhura.getPosicaoZ());
			while(!terminouY){
				if(yAtual-this.ws.getCondicoesUsinagem().getAe() >= this.ws.getCondicoesUsinagem().getAe()+this.ranhura.getPosicaoY()){
					yAtual = yAtual - this.ws.getCondicoesUsinagem().getAe();
					if(yAtual-this.ws.getCondicoesUsinagem().getAe() == this.ws.getCondicoesUsinagem().getAe()+this.ranhura.getPosicaoY()){
						terminouY = true;
					}
				}
				else{
					yAtual = this.ranhura.getPosicaoY()+this.ws.getCondicoesUsinagem().getAe(); 
					terminouY = true;
				}
				
				if(vaiVolta){
					xAtual = this.ranhura.getPosicaoX();
					xProximo = this.ranhura.getComprimento();
					vaiVolta = false;
				}
				else{
					xAtual = this.ranhura.getComprimento();
					xProximo = this.ranhura.getPosicaoX();
					vaiVolta = true;
				}
				
				pontoFinal = new Point3d(xAtual,yAtual,zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				fundo.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
				fundo.add(horizontalTemp);
				pontoInicial = new Point3d(xProximo,yAtual,zAtual);
			}
		}
		this.ws.getOperation().getRetractPlane();
		if(ranhura.getEixo() == Ranhura.VERTICAL){
			pontoInicial = new Point3d(this.ranhura.getPosicaoX() + this.ws.getCondicoesUsinagem().getAe(),0,this.ranhura.getPosicaoZ());
			
			while(!terminouX){
				if(xAtual-this.ws.getCondicoesUsinagem().getAe() >= this.ranhura.getPosicaoX() + this.ws.getCondicoesUsinagem().getAe()){
					xAtual = xAtual - this.ws.getCondicoesUsinagem().getAe();
					if(xAtual-this.ws.getCondicoesUsinagem().getAe() == this.ranhura.getPosicaoX() + this.ws.getCondicoesUsinagem().getAe()){
						terminouX = true;
					}
				}
				else{
					xAtual = this.ranhura.getPosicaoX()+this.ws.getCondicoesUsinagem().getAe();
					terminouX = true;
				}
				if(vaiVolta){
					yAtual = this.ranhura.getPosicaoY();
					yProximo = this.ranhura.getLargura();
					vaiVolta = false;
				}
				else{
					yAtual = this.ranhura.getLargura();
					yProximo = this.ranhura.getPosicaoY();
					vaiVolta = true;
				}
				
				pontoFinal = new Point3d(xAtual,yAtual,zAtual);
				LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
				fundo.add(verticalTemp);
				LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
				fundo.add(horizontalTemp);
				pontoInicial = new Point3d(xAtual,yProximo,zAtual);
			}
		}					
		return fundo;
	}
	
	public ArrayList<LinearPath> getMovimentacaoAcabamentoRanhura(){
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		ArrayList<LinearPath> lado1 = this.lado1();
		ArrayList<LinearPath> lado2 = this.lado2();
		ArrayList<LinearPath> fundo = this.fundo();
		
		for(LinearPath pathTemp: lado1){		
			saida.add(pathTemp);
		}
		for(LinearPath pathTemp: lado2){
			saida.add(pathTemp);
		}
		for(LinearPath pathTemp: fundo){
			saida.add(pathTemp);
		}
		Point3d ultimoPonto = saida.get(saida.size()-1).getFinalPoint();
		LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (ultimoPonto.getX(), ultimoPonto.getY() ,this.ws.getOperation().getRetractPlane()));
		planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
		saida.add(planoSeguro);
		return saida;
	}
	
	private ArrayList<LinearPath> desbaste(){
		ArrayList<LinearPath> desbaste = new ArrayList<LinearPath>();
		
		boolean vaiVolta = true;
		boolean terminouXY = false;
		boolean terminouZ = false;
		boolean fundo = false;
		double apUtilizado;
		double aeUtilizado;
		double xAtual = this.ranhura.getPosicaoX();
		double xProximo;
		double yAtual = this.ranhura.getPosicaoY();
		double yProximo;
		double zAtual = this.ranhura.getPosicaoZ();
		double allowanceBottom = ((BottomAndSideRoughMilling) this.ws.getOperation()).getAllowanceBottom();
		
		Point3d pontoFinal;
		Point3d pontoInicial;
		
		if(this.ranhura.getEixo()==Ranhura.HORIZONTAL){
			pontoInicial = new Point3d(0,this.ranhura.getPosicaoY(),this.ranhura.getPosicaoZ());
			while(!terminouZ){
				if(this.ws.getCondicoesUsinagem().getAp()-zAtual<=this.ranhura.getProfundidade()){
					apUtilizado =  this.ws.getCondicoesUsinagem().getAp();
					if(this.ws.getCondicoesUsinagem().getAp()-zAtual==this.ranhura.getProfundidade())
						terminouZ = true;
				}
				else{
					apUtilizado = zAtual + this.ranhura.getProfundidade();
					terminouZ = true;
				}
				zAtual = zAtual - apUtilizado;
				
				if(fundo){
					yAtual = this.ranhura.getPosicaoY()+this.ranhura.getLargura()+this.ws.getCondicoesUsinagem().getAe()-allowanceBottom-this.ferramenta.getDiametroFerramenta()/2;
					fundo = false;
				}
				else{
					yAtual = this.ferramenta.getDiametroFerramenta()/2+this.ranhura.getPosicaoY()+allowanceBottom-this.ws.getCondicoesUsinagem().getAe();
					fundo = true;
				}
				
				terminouXY = false;
				
				while(!terminouXY){
					if(vaiVolta){
						xAtual = this.ranhura.getPosicaoX();
						xProximo = this.ranhura.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.ranhura.getComprimento();
						xProximo = this.ranhura.getPosicaoX();
						vaiVolta = true;
					}
					if(fundo){
						if(yAtual+this.ws.getCondicoesUsinagem().getAe()<=this.ranhura.getPosicaoY()+this.ranhura.getLargura()-allowanceBottom-this.ferramenta.getDiametroFerramenta()/2){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(yAtual+this.ws.getCondicoesUsinagem().getAe()==this.ranhura.getPosicaoY()+this.ranhura.getLargura()-this.ferramenta.getDiametroFerramenta()/2-allowanceBottom)
								terminouXY = true;
						}
						else{
							aeUtilizado = this.ranhura.getPosicaoY()+this.ranhura.getLargura()-this.ferramenta.getDiametroFerramenta()/2-allowanceBottom-yAtual;
							terminouXY = true;
						}
						yAtual = yAtual + aeUtilizado;
					}
					else{
						if(yAtual - this.ws.getCondicoesUsinagem().getAe()>=this.ranhura.getPosicaoY()+this.ferramenta.getDiametroFerramenta()/2+allowanceBottom){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(yAtual - this.ws.getCondicoesUsinagem().getAe()==this.ranhura.getPosicaoY()+this.ferramenta.getDiametroFerramenta()/2+allowanceBottom)
								terminouXY = true;
						}
						else{
							aeUtilizado = yAtual - this.ranhura.getPosicaoY() -this.ferramenta.getDiametroFerramenta()/2-allowanceBottom;
							terminouXY = true;
						}
						yAtual = yAtual - aeUtilizado;
					}
					
					pontoFinal = new Point3d(xAtual,yAtual,zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
					desbaste.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo,yAtual,zAtual);
				}	
			}
		}
		else{
			if(this.ranhura.getEixo()==Ranhura.VERTICAL){
				pontoInicial = new Point3d(this.ranhura.getPosicaoX(),0,this.ranhura.getPosicaoZ());
				while(!terminouZ){
					if(this.ws.getCondicoesUsinagem().getAp()-zAtual<=this.ranhura.getProfundidade()){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(this.ws.getCondicoesUsinagem().getAp()-zAtual==this.ranhura.getProfundidade())
							terminouZ = true;
					}
					else{
						apUtilizado = this.ranhura.getProfundidade() + zAtual;
						terminouZ = true;
					}
					zAtual = zAtual - apUtilizado;
					
					if(fundo){
						xAtual = this.ws.getCondicoesUsinagem().getAe()+this.ranhura.getLargura()+this.ranhura.getPosicaoX()-allowanceBottom-this.ferramenta.getDiametroFerramenta()/2;
						fundo = false;
					}
					else{
						xAtual = this.ranhura.getPosicaoX()+this.ferramenta.getDiametroFerramenta()/2+allowanceBottom-this.ws.getCondicoesUsinagem().getAe();
						fundo = true;
					}
					terminouXY = false;
					
					while(!terminouXY){
						if(vaiVolta){
							yAtual = this.ranhura.getPosicaoY();
							yProximo = this.ranhura.getComprimento();
							vaiVolta = false;
						}
						else{
							yAtual = this.ranhura.getComprimento();
							yProximo = this.ranhura.getPosicaoY();
							vaiVolta = true;
						}
						
						if(fundo){
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()<=this.ranhura.getPosicaoX()+this.ranhura.getLargura()-allowanceBottom-this.ferramenta.getDiametroFerramenta()/2){
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual+this.ws.getCondicoesUsinagem().getAe()==this.ranhura.getPosicaoX()+this.ranhura.getLargura()-this.ferramenta.getDiametroFerramenta()/2-allowanceBottom)
									terminouXY = true;
							}
							else{
								aeUtilizado = this.ranhura.getPosicaoX()+this.ranhura.getLargura()-this.ferramenta.getDiametroFerramenta()/2-allowanceBottom-xAtual;
								terminouXY = true;
							}
							xAtual = xAtual + aeUtilizado;
						}
						else{
							if(xAtual - this.ws.getCondicoesUsinagem().getAe()>=this.ranhura.getPosicaoX()+this.ferramenta.getDiametroFerramenta()/2+allowanceBottom){
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual - this.ws.getCondicoesUsinagem().getAe()==this.ranhura.getPosicaoX()+this.ferramenta.getDiametroFerramenta()/2+allowanceBottom)
									terminouXY = true;
							}
							else{
								aeUtilizado = xAtual - this.ranhura.getPosicaoX() -this.ferramenta.getDiametroFerramenta()/2-allowanceBottom;
								terminouXY = true;
							}
							xAtual = xAtual - aeUtilizado;
						}
						
						pontoFinal = new Point3d(xAtual,yAtual,zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						desbaste.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
						desbaste.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual,yProximo,zAtual);
					}	
				}
			}
		}
		return desbaste;
	}
	
	public ArrayList<LinearPath> getMovimentacaoDesbasteRanhura(){
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		ArrayList<LinearPath> desbaste = this.desbaste();
		
		for(LinearPath pathTemp: desbaste){		
			saida.add(pathTemp);
		}
		
		Point3d ultimoPonto = saida.get(saida.size()-1).getFinalPoint();
		LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (ultimoPonto.getX(), ultimoPonto.getY() ,this.ws.getOperation().getRetractPlane()));
		planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
		saida.add(planoSeguro);
		return saida;
	}
	
}
