package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
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
		double LimiteDireita, LimiteEsquerda, largura,z,temp;
		double aeUtilizado;
		double ap=this.ws.getCondicoesUsinagem().getAp();
		double ae=this.ws.getCondicoesUsinagem().getAe();
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
			
			LimiteDireita=this.ranhuraRoundedU.getPosicaoX()+this.ranhuraRoundedU.getLargura()-allowanceBottom-diametroFerramenta/2;
			LimiteEsquerda=this.ranhuraRoundedU.getPosicaoX()+diametroFerramenta/2+allowanceBottom;
			
			for(i=(int)numeroDeApsLinear;i>=0;i--){
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
							if(xAtual-ae==LimiteEsquerda)
								terminouXY = true;
						}
						else{
							aeUtilizado = xAtual-LimiteEsquerda;
							terminouXY = true;
						}
						xAtual -= aeUtilizado;
					}
					else{//movimentacao da esquerda pra direita
						if(xAtual+ae<=LimiteDireita){
							aeUtilizado = ae;
							if(xAtual+ae==LimiteDireita)
								terminouXY = true;
						}
						else{
							aeUtilizado = LimiteDireita-xAtual;
							terminouXY = true;
						}
						xAtual += aeUtilizado;
					}
					pontoFinal = new Point3d(xAtual, yAtual, zAtual);
					LinearPath verticalTempp = new LinearPath(pontoInicial,pontoFinal);
					desbaste.add(verticalTempp);
					LinearPath horizontalTempp = new LinearPath(verticalTempp.getFinalPoint(), new Point3d(xAtual,yProximo, zAtual));
					desbaste.add(horizontalTempp);
					pontoInicial = new Point3d(xAtual, yProximo, zAtual);
					System.out.println("aaaaaa");
				}
			}
			/****************MOVIMENTACAO DA PARTE QUE FALTA************************/
			if(ultimoApLinear>0){
				zAtual -= ultimoApLinear;
				
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
							if(xAtual-ae==LimiteEsquerda)
								terminouXY = true;
						}
						else{
							aeUtilizado = xAtual-LimiteEsquerda;
							terminouXY = true;
						}
						xAtual -= aeUtilizado;
					}
					else{//movimentacao da esquerda pra direita
						if(xAtual+ae<=LimiteDireita){
							aeUtilizado = ae;
							if(xAtual+ae==LimiteDireita)
								terminouXY = true;
						}
						else{
							aeUtilizado = LimiteDireita-xAtual;
							terminouXY = true;
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
			/*********************MOVIMENTACAO DA PARTE CURVA*****************/
			for(i=(int)numeroDeApsCurva;i>=0;i--){
				z=profundidade + zAtual+0.0000000000001;
				temp = Math.tan(Math.asin((R-z)/(R)))*ap;
				LimiteDireita -= temp;
				LimiteEsquerda += temp;
				largura = LimiteDireita-LimiteEsquerda;
				
				if(largura>=diametroFerramenta){// verifica se a ferramenta cabe
					zAtual -= ap;//desce a ferramenta
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
								if(xAtual-ae==LimiteEsquerda)
									terminouXY = true;
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita)
									terminouXY = true;
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
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
				
				z=profundidade + zAtual+0.0000000000001;
				temp = Math.tan(Math.asin((R-z)/(R)))*ap;
				LimiteDireita -= temp;
				LimiteEsquerda += temp;
				largura = LimiteDireita-LimiteEsquerda;
				
				if(largura>=diametroFerramenta){
					zAtual -= ultimoApCurva;
					if(fundo)
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
								if(xAtual-ae==LimiteEsquerda)
									terminouXY = true;
							}
							else{
								aeUtilizado = xAtual-LimiteEsquerda;
								terminouXY = true;
							}
							xAtual -= aeUtilizado;
						}
						else{//movimentacao da esquerda pra direita
							if(xAtual+ae<=LimiteDireita){
								aeUtilizado = ae;
								if(xAtual+ae==LimiteDireita)
									terminouXY = true;
							}
							else{
								aeUtilizado = LimiteDireita-xAtual;
								terminouXY = true;
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
		else if(this.ranhuraRoundedU.getEixo() == RanhuraPerfilRoundedU.HORIZONTAL && this.ws.getOperation().getClass() == BottomAndSideFinishMilling.class){
			
		}
		
		
		
		
		return desbaste;
	}
	
	private ArrayList<LinearPath> acabamento(){
		ArrayList<LinearPath> acabamento = new ArrayList<LinearPath>();
		
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
