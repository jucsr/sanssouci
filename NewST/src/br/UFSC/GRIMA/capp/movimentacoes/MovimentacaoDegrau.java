package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoDegrau {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private Degrau degrau;
	
	public MovimentacaoDegrau(Workingstep ws){
		this.ws = ws;
		this.ferramenta = this.ws.getFerramenta();
		this.degrau = (Degrau) this.ws.getFeature();
	}
	
	private ArrayList<LinearPath> lado(){
		double apUtilizado;
		double aeUtilizado;
		double xAtual = this.degrau.getPosicaoX();
		double xProximo;
		double yAtual = this.degrau.getPosicaoY();
		double yProximo;
		double zAtual = this.degrau.getPosicaoZ();
		boolean terminouZ = false;
		boolean terminouXY = false;
		boolean vaiVolta = true;
		ArrayList<LinearPath> lado = new ArrayList<LinearPath>();
		Point3d pontoInicial;
		Point3d pontoFinal;
		
		if(degrau.getEixo() == Degrau.HORIZONTAL){
			if(this.degrau.getPosicaoY()==0){//i = 0
				pontoInicial = new Point3d(0 ,this.degrau.getPosicaoY()+this.degrau.getLargura()-this.ferramenta.getDiametroFerramenta()/2 ,this.degrau.getPosicaoZ());
				////////////////////////////////////////LATERAL
				while(!terminouZ){
					if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.degrau.getProfundidade()){
						apUtilizado = this.ws.getCondicoesUsinagem().getAp();
						if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.degrau.getProfundidade()){
							terminouZ = true;
						}
					}
					else{
						apUtilizado = this.degrau.getProfundidade() + zAtual;
						terminouZ = true;
					}
					zAtual = zAtual - apUtilizado;
					
					if(vaiVolta){
						xAtual = this.degrau.getPosicaoX();
						xProximo = this.degrau.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.degrau.getComprimento();
						xProximo = this.degrau.getPosicaoX();
						vaiVolta = true;
					}
					
					yAtual = this.degrau.getPosicaoY() + this.degrau.getLargura() - this.ferramenta.getDiametroFerramenta()/2;
					
					pontoFinal = new Point3d(xAtual,yAtual,zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					lado.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
					lado.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo,yAtual,zAtual);
				}
				///////////////////////////////FUNDO
				while(!terminouXY){
					if(yAtual-this.ws.getCondicoesUsinagem().getAe()>=this.degrau.getPosicaoY()){
						aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
						if(yAtual-this.ws.getCondicoesUsinagem().getAe()==this.degrau.getPosicaoY()){
							terminouXY = true;
						}
					}
					else{
						aeUtilizado = this.ws.getCondicoesUsinagem().getAe() - yAtual;
						terminouXY = true;
					}
					yAtual = yAtual - aeUtilizado;
					
					if(vaiVolta){
						xAtual = this.degrau.getPosicaoX();
						xProximo = this.degrau.getComprimento();
						vaiVolta = false;
					}
					else{
						xAtual = this.degrau.getComprimento();
						xProximo = this.degrau.getPosicaoX();
						vaiVolta = true;
					}
					
					pontoFinal = new Point3d(xAtual,yAtual,zAtual);
					LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
					lado.add(verticalTemp);
					LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
					lado.add(horizontalTemp);
					pontoInicial = new Point3d(xProximo,yAtual,zAtual);
				}
			}
			else 
				if(this.degrau.getPosicaoY()!=0){
					/////////////////////////////////////////LATERAL
					pontoInicial = new Point3d(0 ,this.degrau.getPosicaoY()+this.ferramenta.getDiametroFerramenta()/2 ,this.degrau.getPosicaoZ());
					while(!terminouZ){
						if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.degrau.getProfundidade()){
							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
							if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.degrau.getProfundidade()){
								terminouZ = true;
							}
						}	
						else{
							apUtilizado = this.degrau.getProfundidade() + zAtual;
							terminouZ = true;
						}
						zAtual = zAtual - apUtilizado;
					
						if(vaiVolta){
							xAtual = this.degrau.getPosicaoX();
							xProximo = this.degrau.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.degrau.getComprimento();
							xProximo = this.degrau.getPosicaoX();
							vaiVolta = true;
						}

						yAtual = this.degrau.getPosicaoY()+this.ferramenta.getDiametroFerramenta()/2;
					
						pontoFinal = new Point3d(xAtual,yAtual,zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						lado.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
						lado.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo,yAtual,zAtual);
					}
			//////////////////////////////////////////////////FUNDO
					while(!terminouXY){
						if(yAtual+this.ws.getCondicoesUsinagem().getAe()<=this.degrau.getPosicaoY()+this.degrau.getLargura()){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(yAtual+this.ws.getCondicoesUsinagem().getAe()==this.degrau.getPosicaoY()+this.degrau.getLargura()){
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = this.degrau.getPosicaoY()+this.degrau.getLargura() - yAtual;
							terminouXY = true;
						}
						yAtual = yAtual + aeUtilizado;
						
						if(vaiVolta){
							xAtual = this.degrau.getPosicaoX();
							xProximo = this.degrau.getComprimento();
							vaiVolta = false;
						}
						else{
							xAtual = this.degrau.getComprimento();
							xProximo = this.degrau.getPosicaoX();
							vaiVolta = true;
						}
						pontoFinal = new Point3d(xAtual,yAtual,zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						lado.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xProximo,yAtual,zAtual));
						lado.add(horizontalTemp);
						pontoInicial = new Point3d(xProximo,yAtual,zAtual);
					}
				
			}
		}
		else 
			if(degrau.getEixo() == Degrau.VERTICAL){
				if(this.degrau.getPosicaoX()==0){ // i=0
					pontoInicial = new Point3d(this.degrau.getPosicaoX()+this.degrau.getLargura()-this.ferramenta.getDiametroFerramenta()/2 ,0 ,this.degrau.getPosicaoZ());
					//////////////////////////////////////////////LATERAL
					while(!terminouZ){
						if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.degrau.getProfundidade()){
							apUtilizado = this.ws.getCondicoesUsinagem().getAp();
							if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.degrau.getProfundidade()){
								terminouZ = true;
							}
						}		
						else{
							apUtilizado = this.degrau.getProfundidade() + zAtual;
							terminouZ = true;
						}	
						zAtual = zAtual - apUtilizado;
						
						if(vaiVolta){
							yAtual = this.degrau.getPosicaoY();
							yProximo = this.degrau.getComprimento();
							vaiVolta = false;
						}	
						else{
							yAtual = this.degrau.getComprimento();
							yProximo = this.degrau.getPosicaoY();
							vaiVolta = true;
						}	
						
						xAtual = this.degrau.getPosicaoX()+this.degrau.getLargura()-this.ferramenta.getDiametroFerramenta()/2;
						
						pontoFinal = new Point3d(xAtual,yAtual,zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						lado.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
						lado.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual,yProximo,zAtual);
					}	
					//////////////////////////////////////////////////FUNDO
					while(!terminouXY){
						if(xAtual-this.ws.getCondicoesUsinagem().getAe()>=this.degrau.getPosicaoY()){
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
							if(xAtual-this.ws.getCondicoesUsinagem().getAe()==this.degrau.getPosicaoY()){
								terminouXY = true;
							}
						}
						else{
							aeUtilizado = this.ws.getCondicoesUsinagem().getAe() - xAtual;
							terminouXY = true;
						}	
						xAtual = xAtual - aeUtilizado;
						
						if(vaiVolta){
							yAtual = this.degrau.getPosicaoY();
							yProximo = this.degrau.getComprimento();
							vaiVolta = false;
						}	
						else{
							yAtual = this.degrau.getComprimento();
							yProximo = this.degrau.getPosicaoY();
							vaiVolta = true;
						}	
						
						pontoFinal = new Point3d(xAtual,yAtual,zAtual);
						LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
						lado.add(verticalTemp);
						LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
						lado.add(horizontalTemp);
						pontoInicial = new Point3d(xAtual,yProximo,zAtual);
					}	
				}	
				else 
					if(this.degrau.getPosicaoX()!=0){
						pontoInicial = new Point3d(this.degrau.getPosicaoX()+this.ferramenta.getDiametroFerramenta()/2 ,0 ,this.degrau.getPosicaoZ());
						//////////////////////////////////////////////LATERAL
						while(!terminouZ){
							if(this.ws.getCondicoesUsinagem().getAp() - zAtual <= this.degrau.getProfundidade()){
								apUtilizado = this.ws.getCondicoesUsinagem().getAp();
								if(this.ws.getCondicoesUsinagem().getAp() - zAtual==this.degrau.getProfundidade()){
									terminouZ = true;
								}
							}		
							else{
								apUtilizado = this.degrau.getProfundidade() + zAtual;
								terminouZ = true;
							}	
							zAtual = zAtual - apUtilizado;

							if(vaiVolta){
								yAtual = this.degrau.getPosicaoY();
								yProximo = this.degrau.getComprimento();
								vaiVolta = false;
							}	
							else{
								yAtual = this.degrau.getComprimento();
								yProximo = this.degrau.getPosicaoY();
								vaiVolta = true;
							}	
							
							xAtual = this.degrau.getPosicaoX()+this.ferramenta.getDiametroFerramenta()/2;
							
							pontoFinal = new Point3d(xAtual,yAtual,zAtual);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							lado.add(verticalTemp);
							LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
							lado.add(horizontalTemp);
							pontoInicial = new Point3d(xAtual,yProximo,zAtual);
						}	
						//////////////////////////////////////////////////FUNDO
						while(!terminouXY){
							if(xAtual+this.ws.getCondicoesUsinagem().getAe()<=this.degrau.getPosicaoX()+this.degrau.getLargura()){
								aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
								if(xAtual+this.ws.getCondicoesUsinagem().getAe()==this.degrau.getPosicaoX()+this.degrau.getLargura()){
									terminouXY = true;
								}
							}	
							else{
								aeUtilizado = this.degrau.getPosicaoX()+this.degrau.getLargura() - xAtual;
								terminouXY = true;
							}	
							xAtual = xAtual + aeUtilizado;
						
							if(vaiVolta){
								yAtual = this.degrau.getPosicaoY();
								yProximo = this.degrau.getComprimento();
								vaiVolta = false;
							}	
							else{
								yAtual = this.degrau.getComprimento();
								yProximo = this.degrau.getPosicaoY();
								vaiVolta = true;
							}		
						
							pontoFinal = new Point3d(xAtual,yAtual,zAtual);
							LinearPath verticalTemp = new LinearPath(pontoInicial,pontoFinal);
							lado.add(verticalTemp);
							LinearPath horizontalTemp = new LinearPath(verticalTemp.getFinalPoint(), new Point3d(xAtual,yProximo,zAtual));
							lado.add(horizontalTemp);
							pontoInicial = new Point3d(xAtual,yProximo,zAtual);
						}	
					}		
			}		
		return lado;
	}
	
	public ArrayList<LinearPath> getMovimentacaoAcabamentoDegrau(){
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
		ArrayList<LinearPath> lado = this.lado();
	
		for(LinearPath pathTemp: lado){		
			saida.add(pathTemp);
		}
		
		Point3d ultimoPonto = saida.get(saida.size()-1).getFinalPoint();
		LinearPath planoSeguro = new LinearPath(ultimoPonto, new Point3d (ultimoPonto.getX(), ultimoPonto.getY() ,this.ws.getOperation().getRetractPlane()));
		planoSeguro.setTipoDeMovimento(LinearPath.FAST_MOV);
		saida.add(planoSeguro);
		return saida;
	}
}