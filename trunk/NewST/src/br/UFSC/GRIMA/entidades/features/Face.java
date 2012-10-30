package br.UFSC.GRIMA.entidades.features;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D.Float;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.cad.JanelaPrincipal;


public class Face implements Serializable{
	
	public final static int XY = 0;
	public final static int YZ = 1;
	public final static int XZ = 2;
	public final static int YX = 3;
	public final static int ZY = 4;
	public final static int ZX = 5;
	
	public int verticeAtivado = 0;
	
	private int tipo = 0;
	private double comprimento = 0.0;
	private double largura = 0.0;
	private double profundidadeMaxima = 0;
	public Vector features = new Vector();
	
	private ArrayList<Point3d> pontosDeApoio = new ArrayList<Point3d>();

	public int[] indices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	public Face(int tipo, double comprimento, double largura)
	{
		this.setTipo(tipo);
		this.setComprimento(comprimento);
		this.setLargura(largura);
	}
	
	public Face(int tipo)
	{
		this.setTipo(tipo);
	}
	public double getLarguraDesenhada(int vertice)
	{
		switch (vertice)
		{
			case 0:
				return this.getLargura();
			case 1:
				return this.getComprimento();
			case 2:
				return this.getLargura();
			case 3:
				return this.getComprimento();
			default:
				return -1.0;
		}
	}
	public double getComprimentoDesenhado(int vertice)
	{
		switch (vertice)
		{
			case 0:
				return this.getComprimento();
			case 1:
				return this.getLargura();
			case 2:
				return this.getComprimento();
			case 3:
				return this.getLargura();
			default:
				return -1.0;
		}
	}
	public void setProfundidadeMaxima(double profundidadeMaxima)
	{
		this.profundidadeMaxima = profundidadeMaxima;
	}
	public double getProfundidadeMaxima()
	{
		return this.profundidadeMaxima;
	}
	/**
	 * 
	 * @param vertice
	 * @param tipo inverter os eixos ou n�o
	 * @param br.UFSC.GRIMA.feature
	 * @return
	 */
	public Feature alterarOrientacaoParalela222 (int vertice, int tipo, Feature feature)
	{	
		//double posX, posY, posZ;
		boolean modo;
		if (tipo == 1)
		{	modo = true;
		}
		else
		{	modo = false;
		}
		//System.out.println("%%%%%%%%" + modo + " " + vertice);
		
		switch(vertice)
		{	/**		
			*	###############  VERTICE 0 ##############
			*/
			case 0: 
				switch (feature.getTipo())
				{
					case Feature.FURO:
						Furo furoNovo = (Furo)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{	
						}
						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauNovo = (Degrau)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
						}
						return degrauNovo;
					case Feature.RANHURA:
						Ranhura ranhuraNova = (Ranhura)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeNova = (Cavidade)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
						}
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfa = (CavidadeFundoArredondado)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
						}
						return cfa;
					default:
						break;
				}
				break;
				/**
				*		#########	VERTICE 1	###############
				*/
			case 1:
				switch (feature.getTipo())
				{
					case Feature.FURO:
						Furo furoNovo = (Furo)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{	
							furoNovo.setPosicao(furoNovo.getPosicaoY(), 
									furoNovo.getPosicaoX(), 
									furoNovo.getPosicaoZ());
						}
						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauNovo = (Degrau)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							if (degrauNovo.getEixo() == Degrau.HORIZONTAL)
							{
								degrauNovo.setPosicao(degrauNovo.getPosicaoY(),
										degrauNovo.getPosicaoX(), 
										degrauNovo.getPosicaoZ());
								degrauNovo.setProfundidade(degrauNovo.getProfundidade());
								degrauNovo.setLargura(degrauNovo.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
								
							}
							else
							{
								degrauNovo.setPosicao(degrauNovo.getPosicaoY(),
										degrauNovo.getPosicaoX(), 
										degrauNovo.getPosicaoZ());
								degrauNovo.setProfundidade(degrauNovo.getProfundidade());
								degrauNovo.setLargura(degrauNovo.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
							}
						}
						return degrauNovo;
					case Feature.RANHURA:
						Ranhura ranhuraNova = (Ranhura)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							ranhuraNova.setPosicao(ranhuraNova.getPosicaoY(), ranhuraNova.getPosicaoX(), ranhuraNova.getPosicaoZ());
							ranhuraNova.setProfundidade(ranhuraNova.getProfundidade());
							ranhuraNova.setLargura(ranhuraNova.getLargura());
							if (ranhuraNova.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhuraNova.setEixo(Ranhura.VERTICAL);
							}
							else
							{
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							}
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeNova = (Cavidade)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							double temp = cavidadeNova.getLargura();			
							cavidadeNova.setLargura(cavidadeNova.getComprimento());
							cavidadeNova.setComprimento(temp);
							cavidadeNova.setPosicao(cavidadeNova.getPosicaoY(), 
									cavidadeNova.getPosicaoX(), 
									cavidadeNova.getPosicaoZ());
						}
						return cavidadeNova;
					default:
						break;
				}
				break;
				/**
				*		#########	VERTICE 2	###############
				*/
			case 2:
				switch (feature.getTipo())
				{
					case Feature.FURO:
						Furo furoNovo = (Furo)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							furoNovo.setPosicao(furoNovo.getPosicaoX(), furoNovo.getPosicaoY(), furoNovo.getPosicaoZ());
						}
						return furoNovo;

					case Feature.DEGRAU:
						Degrau degrauNovo = (Degrau)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							if (degrauNovo.getEixo() == Degrau.HORIZONTAL)
							{
								degrauNovo.setPosicao(degrauNovo.getPosicaoX(),
										degrauNovo.getPosicaoY(), 
										degrauNovo.getPosicaoZ());
								degrauNovo.setProfundidade(degrauNovo.getProfundidade());
								degrauNovo.setLargura(degrauNovo.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
							}
							else
							{
								degrauNovo.setPosicao(degrauNovo.getPosicaoX(),
										degrauNovo.getPosicaoY(), 
										degrauNovo.getPosicaoZ());
								degrauNovo.setProfundidade(degrauNovo.getProfundidade());
								degrauNovo.setLargura(degrauNovo.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
							}
						}
						return degrauNovo;
					case Feature.RANHURA:
						if (!modo)
						{
							Ranhura ranhuraNova =(Ranhura)this.alterarOrientacao(vertice, feature);
							if(ranhuraNova.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhuraNova.setPosicao(ranhuraNova.getPosicaoX(), ranhuraNova.getPosicaoY(), ranhuraNova.getPosicaoZ());
								ranhuraNova.setProfundidade(ranhuraNova.getProfundidade());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							}
							else
							{
								ranhuraNova.setPosicao(ranhuraNova.getPosicaoX(), ranhuraNova.getPosicaoY(), ranhuraNova.getPosicaoZ());
								ranhuraNova.setProfundidade(ranhuraNova.getProfundidade());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
							}
							return ranhuraNova;
						}
					case Feature.CAVIDADE:
						Cavidade cavidadeNova = (Cavidade)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							cavidadeNova.setPosicao(cavidadeNova.getPosicaoX(),
									cavidadeNova.getPosicaoY(),
									cavidadeNova.getPosicaoZ());
							cavidadeNova.setComprimento(cavidadeNova.getComprimento());
							cavidadeNova.setLargura(cavidadeNova.getLargura());
							cavidadeNova.setProfundidade(cavidadeNova.getProfundidade());
							
						}
							return cavidadeNova;
					default:
						break;
				}
				break;
				/**
				*		#########	VERTICE 3	###############
				*/
			case 3:
				switch (feature.getTipo())
				{
					case Feature.FURO:
						Furo furoNovo = (Furo)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							furoNovo.setPosicao(furoNovo.getPosicaoY(), furoNovo.getPosicaoX(), furoNovo.getPosicaoZ());
						}
						return furoNovo;
						
					case Feature.DEGRAU:
						Degrau degrauNovo = (Degrau)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							degrauNovo.setPosicao(degrauNovo.getPosicaoY(),
									degrauNovo.getPosicaoX(), 
									degrauNovo.getPosicaoZ());
							degrauNovo.setLargura(degrauNovo.getLargura());
							degrauNovo.setProfundidade(degrauNovo.getProfundidade());
							if (degrauNovo.getEixo() == Degrau.HORIZONTAL)							
								degrauNovo.setEixo(Degrau.VERTICAL);
							else
							{
								degrauNovo.setEixo(Degrau.HORIZONTAL); /***********NAO ESTA FUNCIONANDO DIREITO&&&&&&&&&&&&&&&&&&&&&&&&&&&            ********************/
							}
						}
						return degrauNovo;
					case Feature.RANHURA:
						Ranhura ranhuraNova = (Ranhura)this.alterarOrientacao(vertice, feature);
						if (!modo)	
						{
							ranhuraNova.setPosicao(ranhuraNova.getPosicaoY(), ranhuraNova.getPosicaoX(), ranhuraNova.getPosicaoZ());
							ranhuraNova.setProfundidade(ranhuraNova.getProfundidade());
							ranhuraNova.setLargura(ranhuraNova.getLargura());
							if (ranhuraNova.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhuraNova.setEixo(Ranhura.VERTICAL);
							}
							else
							{
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							}
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeNova = (Cavidade)this.alterarOrientacao(vertice, feature);
						if (!modo)
						{
							cavidadeNova.setPosicao(cavidadeNova.getPosicaoY(), cavidadeNova.getPosicaoX(), cavidadeNova.getPosicaoZ());
							double temp = 	cavidadeNova.getLargura();			
							cavidadeNova.setLargura(cavidadeNova.getComprimento());
							cavidadeNova.setComprimento(temp);
						}
						return cavidadeNova;
						default:
							break;
				}
				break;
				default:
					break;
		}
		return null;
	}
	
	public Feature alterarOrientacao(int verticeNovo, Feature feature)
	{
		switch (verticeNovo)
		{
			case 0:			/**
							*    VERTICE 0
							*/
				
//				System.out.println("posx case 0" );
				return feature;
				
			case 1:			/**
							*	VERTICE 1
							*/
				double posX = 0,  posY = 0, posZ = 0;
				
				switch(feature.getTipo())
				{
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
						
						posX = this.largura - furoOriginal.getPosicaoY();
						posY = furoOriginal.getPosicaoX();
						posZ = furoOriginal.getPosicaoZ();
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());
						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
						{
							posX = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
							posY = 0;
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.VERTICAL);/***********************/
							return degrauNovo;
						}
						else
						{
							posX = 0;
							posY = degrauOriginal.getPosicaoX();
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.HORIZONTAL);/*********************NU ENTIENDO XQ =(*********/
							return degrauNovo;
						}
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
						{
							posX = this.largura - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoY();
							posY = 0;
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.VERTICAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						else
						{
							posX = 0;
							posY = ranhuraOriginal.getPosicaoX();
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						
						posX = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
						posY = cavidadeOriginal.getPosicaoX();
						posZ = cavidadeOriginal.getPosicaoZ();
						cavidadeNova.setPosicao(posX, posY, posZ);
						cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
						cavidadeNova.setRaio(cavidadeOriginal.getRaio());
						cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
						cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
						cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						/**
						 *  PASSAR OS BOSS DA CAVIDADE ORIGINAL PARA A CAVIDADE ALTERADA!!!
						 */
						ArrayList<Boss> itsBoss = new ArrayList<Boss>();      // cria um array de parametros do boss
						
						for(int i = 0; i< cavidadeOriginal.getItsBoss().size(); i++)
						{
							Boss bossTmp = cavidadeOriginal.getItsBoss().get(i);   

							if (bossTmp.getClass() == CircularBoss.class)
							{
								CircularBoss cb = (CircularBoss)bossTmp;
								CircularBoss tmp = new CircularBoss();
								
								posX = this.largura - cb.getPosicaoY();
								posY = cb.getPosicaoX();
								posZ = cb.getPosicaoZ();
								tmp.setPosicao(posX, posY, posZ);
								tmp.setAltura(cb.getAltura());
								tmp.setDiametro1(cb.getDiametro1());
								tmp.setDiametro2(cb.getDiametro2());
						//		tmp.setComprimento(cavidadeOriginal.getLargura());
						//		tmp.setLargura(cavidadeOriginal.getComprimento());
						//		tmp.setPassante(cavidadeOriginal.isPassante());
								
								itsBoss.add(tmp);
							
								System.out.println("posX" + posX);
								
							} else if(bossTmp.getClass() == RectangularBoss.class)
							{
								/**
								 *  IMPLEMENTAR PARA RECTANGULAR BOSS!!!
								 */
							}
							cavidadeNova.setItsBoss(itsBoss);  // seta todos os boss tmp que foi criado
						}
						
						
						
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado novaC;
						posX = this.largura - cOriginal.Y - cOriginal.getLargura();
						posY = cOriginal.X;
						posZ = cOriginal.Z;
						novaC = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
						novaC.setRugosidade(cOriginal.getRugosidade());
						novaC.setTolerancia(cOriginal.getTolerancia());
						return novaC;
					default:
						break;
				}
				break;
			case 2:/**
					*			VERTICE 2
					*/
				
				switch(feature.getTipo())
				{
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
					
						posX = this.comprimento - furoOriginal.getPosicaoX();
						posY = this.largura - furoOriginal.getPosicaoY();
						posZ = furoOriginal.getPosicaoZ();
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());
						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						
						if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
						{
							posX = 0;
							posY = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.HORIZONTAL);
							return degrauNovo;
						}
						else
						{
							posX = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
							posY = 0;
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setEixo(Degrau.VERTICAL);
							return degrauNovo;
						}
						
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
						{
							posX = 0;
							posY = this.largura - ranhuraOriginal.getPosicaoY() - ranhuraOriginal.getLargura();
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						else
						{
							posX = this.comprimento - ranhuraOriginal.getPosicaoX() - ranhuraOriginal.getLargura();
							posY = 0;
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.VERTICAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						
						posX = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
						posY = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
						posZ = cavidadeOriginal.getPosicaoZ();
						cavidadeNova.setPosicao(posX, posY, posZ);
						cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
						cavidadeNova.setRaio(cavidadeOriginal.getRaio());
						cavidadeNova.setComprimento(cavidadeOriginal.getComprimento());
						cavidadeNova.setLargura(cavidadeOriginal.getLargura());
						cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						System.out.println("CAV X = " + posX);
						/**
						 *  PASSAR OS BOSS DA CAVIDADE ORIGINAL PARA A CAVIDADE ALTERADA!!!
						 */
						ArrayList<Boss> itsBoss = new ArrayList<Boss>();      // cria um array de parametros do boss
						
						for(int i = 0; i< cavidadeOriginal.getItsBoss().size(); i++)
						{
							Boss bossTmp = cavidadeOriginal.getItsBoss().get(i);   

							if (bossTmp.getClass() == CircularBoss.class)
							{
								CircularBoss cb = (CircularBoss)bossTmp;
								CircularBoss tmp = new CircularBoss();
								
								posX = this.comprimento - cb.getPosicaoX();
								posY = this.largura - cb.getPosicaoY();
								posZ = cb.getPosicaoZ();
								tmp.setPosicao(posX, posY, posZ);
								tmp.setAltura(cb.getAltura());
								tmp.setDiametro1(cb.getDiametro1());
								tmp.setDiametro2(cb.getDiametro2());
								
								itsBoss.add(tmp);
								System.out.println("posX case 2" + posX);
								
							} else if(bossTmp.getClass() == RectangularBoss.class)
							{
								/**
								 *  IMPLEMENTAR PARA RECTANGULAR BOSS!!!
								 */
							}
							cavidadeNova.setItsBoss(itsBoss);  // seta todos os boss tmp que foi criado
						}
						
						
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado novaC;
						posX = this.comprimento - cOriginal.X - cOriginal.getComprimento();
						posY = this.largura - cOriginal.Y - cOriginal.getLargura();
						posZ = cOriginal.Z;
						novaC = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getLargura(), cOriginal.getComprimento(), cOriginal.getProfundidade());
						novaC.setRugosidade(cOriginal.getRugosidade());
						novaC.setTolerancia(cOriginal.getTolerancia());
						return novaC;
					default:
						break;
				}
				break;
			case 3:/**
					*		VERTICE 3
					*/
				
				switch(feature.getTipo())
				{
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
						posX = furoOriginal.getPosicaoY();
						posY = this.comprimento - furoOriginal.getPosicaoX();
						posZ = furoOriginal.getPosicaoZ();
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());
						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
						{
							posX = degrauOriginal.getPosicaoY();
							posY = 0;
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.VERTICAL);
						}
						else
						{
							posX = 0;
							posY = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.HORIZONTAL);
						}
						return degrauNovo;
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
						{
							posX = ranhuraOriginal.getPosicaoY();
							posY = 0;
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.VERTICAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						else
						{
							posX = 0;
							posY = this.comprimento - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoX();
							posZ = ranhuraOriginal.getPosicaoZ();
							
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setEixo(Ranhura.HORIZONTAL);
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						//posX = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
						posX = cavidadeOriginal.getPosicaoY();
						//posY = cavidadeOriginal.getPosicaoX();
						posY = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
						posZ = cavidadeOriginal.getPosicaoZ();
						cavidadeNova.setPosicao(posX, posY, posZ);
						cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
						cavidadeNova.setRaio(cavidadeOriginal.getRaio());
						cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
						cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
						cavidadeNova.setPassante(cavidadeOriginal.isPassante());

						/**
						 *  PASSAR OS BOSS DA CAVIDADE ORIGINAL PARA A CAVIDADE ALTERADA!!!
						 */
						ArrayList<Boss> itsBoss = new ArrayList<Boss>();      // cria um array de parametros do boss
						
						for(int i = 0; i< cavidadeOriginal.getItsBoss().size(); i++)
						{
							Boss bossTmp = cavidadeOriginal.getItsBoss().get(i);   

							if (bossTmp.getClass() == CircularBoss.class)
							{
								CircularBoss cb = (CircularBoss)bossTmp;
								CircularBoss tmp = new CircularBoss();
								
								posX = cb.getPosicaoY();
								posY = this.getComprimento() - cb.getPosicaoX() ;
								posZ = cb.getPosicaoZ();
								tmp.setPosicao(posX, posY, posZ);
								tmp.setAltura(cb.getAltura());
								tmp.setDiametro1(cb.getDiametro1());
								tmp.setDiametro2(cb.getDiametro2());
								
								itsBoss.add(tmp);
								System.out.println("posX case 3" );
								
							} else if(bossTmp.getClass() == RectangularBoss.class)
							{
								/**
								 *  IMPLEMENTAR PARA RECTANGULAR BOSS!!!
								 */
							}
							cavidadeNova.setItsBoss(itsBoss);  // seta todos os boss tmp que foi criado
						}
						
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado novaC;
						posX = cOriginal.Y;
						posY = this.comprimento - cOriginal.X - cOriginal.getComprimento();
						posZ = cOriginal.Z;
						novaC = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
						novaC.setRugosidade(cOriginal.getRugosidade());
						novaC.setTolerancia(cOriginal.getTolerancia());
						return novaC;
					default:
						break;
				}
				break;
			default:
				break;
		}
		return null;
	}
	
	public Feature alterarOrientacaoParalela(int verticeNovo, int tipo, Feature feature)
	{
		boolean modo;
		if (tipo == 1)
		{	modo = true;
		}
		else
		{	modo = false;
		}
		double posX = 0,  posY = 0, posZ = 0;
		switch (verticeNovo)
		{
			case 0:			/**
							*    VERTICE 0
							*/
				
			switch (feature.getTipo())
			{
				case Feature.FURO:
					Furo furoOriginal = (Furo)feature;
					//Furo furoNovo = (Furo)this.alterarOrientacao(verticeNovo, br.UFSC.GRIMA.feature);
					Furo furoNovo = new Furo();
					
					if (modo)
					{
					}
					else
					{	
						posX = furoOriginal.getPosicaoY();
						posY = furoOriginal.getPosicaoX();
						posZ = furoOriginal.getPosicaoZ();
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setPassante(furoOriginal.isPassante());
					}
					return furoNovo;
				case Feature.DEGRAU:
					Degrau degrauOriginal = (Degrau)feature;
					Degrau degrauNovo = new Degrau();
					//Degrau degrauNovo = (Degrau)this.alterarOrientacao(verticeNovo, br.UFSC.GRIMA.feature);
					if (modo)
					{
						
					}
					else
					{
						if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
						{
							posX = degrauOriginal.getPosicaoY();
							posY = degrauOriginal.getPosicaoX();
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.VERTICAL);/***********************/
							return degrauNovo;
						}
						else
						{
							posX = 0;
							posY = degrauOriginal.getPosicaoX();
							posZ = degrauOriginal.getPosicaoZ();
							degrauNovo.setPosicao(posX, posY, posZ);
							degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
							degrauNovo.setLargura(degrauOriginal.getLargura());
							degrauNovo.setEixo(Degrau.HORIZONTAL);/*********************NU ENTIENDO XQ =(*********/
							return degrauNovo;
						}
						/*posX = degrauOriginal.getPosicaoX();
						posY = degrauOriginal.getPosicaoY();
						posZ = degrauOriginal.getPosicaoZ();
						degrauNovo.setPosicao(posX, posY, posZ);
						degrauNovo.setLargura(degrauOriginal.getLargura());
						degrauNovo.setProfundidade(degrauOriginal.getProfundidade());*/
					}
					//return degrauNovo;
				case Feature.RANHURA:
					//Ranhura ranhuraNova = (Ranhura)this.alterarOrientacao(verticeNovo, br.UFSC.GRIMA.feature);
					Ranhura ranhuraOriginal = (Ranhura)feature;
					Ranhura ranhuraNova = new Ranhura();
					if (modo)
					{
					}
					else
					{
						if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
						{
							posX = ranhuraOriginal.getPosicaoY();
							posY = ranhuraOriginal.getPosicaoX();
							posZ = ranhuraOriginal.getPosicaoZ();
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							ranhuraNova.setEixo(Ranhura.VERTICAL);
						}
						else
						{
							posX = 0;
							//posY = this.getLarguraDesenhada(verticeAtivado) - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoX();
							posY = ranhuraOriginal.getPosicaoX();
							//System.out.println("ranhuraOriginal_posY: " + ranhuraOriginal.getPosicaoY());
							//System.out.println("ranhuraOriginal_largura: " + ranhuraOriginal.getLargura());
							posZ = ranhuraOriginal.getPosicaoZ();
							ranhuraNova.setPosicao(posX, posY, posZ);
							ranhuraNova.setLargura(ranhuraOriginal.getLargura());
							ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							ranhuraNova.setEixo(Ranhura.HORIZONTAL);
						}
					}
					return ranhuraNova;
				case Feature.CAVIDADE:
					Cavidade cavidadeOriginal = (Cavidade)feature;
					Cavidade cavidadeNova = new Cavidade();
					//Cavidade cavidadeNova = (Cavidade)this.alterarOrientacao(verticeNovo, br.UFSC.GRIMA.feature);
					if (modo)
					{
						
					}
					else
					{
						posX = cavidadeOriginal.getPosicaoY();
						posY = cavidadeOriginal.getPosicaoX();
						posZ = cavidadeOriginal.getPosicaoZ();
						cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
						cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
						cavidadeNova.setPosicao(posX, posY, posZ);
						cavidadeNova.setRaio(cavidadeOriginal.getRaio());
						cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
						cavidadeNova.setPassante(cavidadeOriginal.isPassante());
					}
					return cavidadeNova;
				case Feature.CAVIDADE_FUNDO_ARREDONDADO:
					CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
					CavidadeFundoArredondado cNova = null;
					if (modo)
					{
						
					}
					else
					{
						posX = cOriginal.Y;
						posY = cOriginal.X;
						posZ = cOriginal.getPosicaoZ();
						
						cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
						cNova.setRugosidade(cOriginal.getRugosidade());
						cNova.setTolerancia(cOriginal.getTolerancia());
					}
					return cNova;
				default:
					break;
				}
				
				return feature;
				
			case 1:			/**
							*	VERTICE 1
							*/
				switch(feature.getTipo())
				{
					
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
						if (modo)
						{
							posX = this.getComprimentoDesenhado(verticeNovo) - furoOriginal.getPosicaoY();
							posY = furoOriginal.getPosicaoX();
							posZ = furoOriginal.getPosicaoZ();
						}
						else//paralela
						{
							posX = furoOriginal.getPosicaoX();
							posY = this.getComprimentoDesenhado(verticeNovo)- furoOriginal.getPosicaoY();
							posZ = furoOriginal.getPosicaoZ();
						}
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());

						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						if (modo)
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posX = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
								posY = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);/***********************/
								return degrauNovo;
							}
							else
							{
								posX = 0;
								posY = degrauOriginal.getPosicaoX();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);/*********************NU ENTIENDO XQ =(*********/
								return degrauNovo;
							}
						}
						else
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posY = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
								posX = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);/***********************/
								return degrauNovo;
							}
							else
							{
								posY = 0;
								posX = degrauOriginal.getPosicaoX();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);/*********************NU ENTIENDO XQ =(*********/
								return degrauNovo;
							}
						}
						
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (modo)
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								posX = this.largura - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoY();
								posY = 0;
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posX = 0;
								posY = ranhuraOriginal.getPosicaoX();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						else
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								//posX = this.largura - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoY();
								//posY = 0;
								posX = 0;
								posY = this.largura - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoY();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posY = 0;
								posX = ranhuraOriginal.getPosicaoX();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						
						if (modo)
						{
							posX = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
							posY = cavidadeOriginal.getPosicaoX();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
							cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						else
						{
							posX = cavidadeOriginal.getPosicaoX();
							posY = this.getComprimentoDesenhado(verticeNovo) - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getComprimento());
							cavidadeNova.setLargura(cavidadeOriginal.getLargura());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado cNova;
						if (modo)
						{
							posX = this.largura - cOriginal.Y - cOriginal.getLargura();
							posY = cOriginal.X;
							posZ = cOriginal.getPosicaoZ();
							
							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
						}
						else
						{
							posX = cOriginal.X;
							posY = this.getComprimentoDesenhado(verticeNovo) - cOriginal.Y - cOriginal.getLargura();
							posZ = cOriginal.getPosicaoZ();
							
							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getLargura(), cOriginal.getComprimento(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
						}
						return cNova;
					default:
						break;
				}
				break;
			case 2:/**
					*			VERTICE 2
					*/
				
				switch(feature.getTipo())
				{
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
						if (modo)
						{
							posX = this.getComprimentoDesenhado(verticeNovo) - furoOriginal.getPosicaoX();
							posY = this.largura - furoOriginal.getPosicaoY();
							posZ = furoOriginal.getPosicaoZ();
						}
						else
						{
							posY = this.getComprimentoDesenhado(verticeNovo) - furoOriginal.getPosicaoX();
							posX = this.getLarguraDesenhada(verticeNovo) - furoOriginal.getPosicaoY();
							posZ = furoOriginal.getPosicaoZ();
						}
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());

						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						
						if (modo)
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posX = 0;
								posY = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
								return degrauNovo;
							}
							else
							{
								posX = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
								posY = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
								return degrauNovo;
							}
						}
						else
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posY = 0;
								posX = this.largura - degrauOriginal.getPosicaoY() - degrauOriginal.getLargura();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
								return degrauNovo;
							}
							else
							{
								posY = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
								posX = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
								return degrauNovo;
							}
						}
						
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (modo)
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								posX = 0;
								posY = this.largura - ranhuraOriginal.getPosicaoY() - ranhuraOriginal.getLargura();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posX = this.comprimento - ranhuraOriginal.getPosicaoX() - ranhuraOriginal.getLargura();
								posY = 0;
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						else
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								posY = 0;
								posX = this.largura - ranhuraOriginal.getPosicaoY() - ranhuraOriginal.getLargura();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posY = this.comprimento - ranhuraOriginal.getPosicaoX() - ranhuraOriginal.getLargura();
								posX = 0;
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						
						if (modo)
						{
							posX = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
							posY = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getComprimento());
							cavidadeNova.setLargura(cavidadeOriginal.getLargura());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						else
						{
							posX = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
							posY = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
							cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado cNova;
						if (modo)
						{
							posX = this.comprimento - cOriginal.X - cOriginal.getComprimento();
							posY = this.largura - cOriginal.Y - cOriginal.getLargura();
							posZ = cOriginal.getPosicaoZ();
							
							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getLargura(), cOriginal.getComprimento(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
						}
						else
						{
							posX = this.largura - cOriginal.getPosicaoY() - cOriginal.getLargura();
							posY = this.comprimento - cOriginal.getPosicaoX() - cOriginal.getComprimento();
							posZ = cOriginal.getPosicaoZ();

							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
						}
						return cNova;
					default:
						break;
				}
				break;
			case 3:/**
					*		VERTICE 3
					*/
				
				switch(feature.getTipo())
				{
					case Feature.FURO:
						Furo furoOriginal = (Furo)feature;
						Furo furoNovo = new Furo();
						posZ = furoOriginal.getPosicaoZ();
						if (modo)
						{
							posX = furoOriginal.getPosicaoY();
							posY = this.getLarguraDesenhada(verticeNovo) - furoOriginal.getPosicaoX();
						}
						else
						{
							posX = this.getLarguraDesenhada(verticeNovo) - furoOriginal.getPosicaoX();
							posY = furoOriginal.getPosicaoY();
						}
						furoNovo.setPosicao(posX, posY, posZ);
						furoNovo.setProfundidade(furoOriginal.getProfundidade());
						furoNovo.setRaio(furoOriginal.getRaio());
						furoNovo.setPassante(furoOriginal.isPassante());

						return furoNovo;
					case Feature.DEGRAU:
						Degrau degrauOriginal = (Degrau)feature;
						Degrau degrauNovo = new Degrau();
						if (modo)
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posX = degrauOriginal.getPosicaoY();
								posY = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
							}
							else
							{
								posX = 0;
								posY = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
							}
						}
						else
						{
							if (degrauOriginal.getEixo() == Degrau.HORIZONTAL)
							{
								posY = degrauOriginal.getPosicaoY();
								posX = 0;
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.HORIZONTAL);
							}
							else
							{
								posY = 0;
								posX = this.comprimento - degrauOriginal.getLargura() - degrauOriginal.getPosicaoX();
								posZ = degrauOriginal.getPosicaoZ();
								degrauNovo.setPosicao(posX, posY, posZ);
								degrauNovo.setProfundidade(degrauOriginal.getProfundidade());
								degrauNovo.setLargura(degrauOriginal.getLargura());
								degrauNovo.setEixo(Degrau.VERTICAL);
							}
						}
						return degrauNovo;
					case Feature.RANHURA:
						Ranhura ranhuraOriginal = (Ranhura)feature;
						Ranhura ranhuraNova = new Ranhura();
						if (modo)
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								posX = ranhuraOriginal.getPosicaoY();
								posY = 0;
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posX = 0;
								posY = this.comprimento - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoX();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						else
						{
							if (ranhuraOriginal.getEixo() == Ranhura.HORIZONTAL)
							{
								posY = ranhuraOriginal.getPosicaoY();
								posX = 0;
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.HORIZONTAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
							else
							{
								posY = 0;
								posX = this.comprimento - ranhuraOriginal.getLargura() - ranhuraOriginal.getPosicaoX();
								posZ = ranhuraOriginal.getPosicaoZ();
								
								ranhuraNova.setPosicao(posX, posY, posZ);
								ranhuraNova.setLargura(ranhuraOriginal.getLargura());
								ranhuraNova.setEixo(Ranhura.VERTICAL);
								ranhuraNova.setProfundidade(ranhuraOriginal.getProfundidade());
							}
						}
						return ranhuraNova;
					case Feature.CAVIDADE:
						Cavidade cavidadeOriginal = (Cavidade)feature;
						Cavidade cavidadeNova = new Cavidade();
						//posX = this.largura - cavidadeOriginal.getPosicaoY() - cavidadeOriginal.getLargura();
						if (modo)
						{
							posX = cavidadeOriginal.getPosicaoY();
							//posY = cavidadeOriginal.getPosicaoX();
							posY = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getLargura());
							cavidadeNova.setLargura(cavidadeOriginal.getComprimento());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						else
						{
							posY = cavidadeOriginal.getPosicaoY();
							//posY = cavidadeOriginal.getPosicaoX();
							posX = this.comprimento - cavidadeOriginal.getPosicaoX() - cavidadeOriginal.getComprimento();
							posZ = cavidadeOriginal.getPosicaoZ();
							cavidadeNova.setPosicao(posX, posY, posZ);
							cavidadeNova.setProfundidade(cavidadeOriginal.getProfundidade());
							cavidadeNova.setRaio(cavidadeOriginal.getRaio());
							cavidadeNova.setComprimento(cavidadeOriginal.getComprimento());
							cavidadeNova.setLargura(cavidadeOriginal.getLargura());
							cavidadeNova.setPassante(cavidadeOriginal.isPassante());
						}
						return cavidadeNova;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cOriginal = (CavidadeFundoArredondado)feature;
						CavidadeFundoArredondado cNova;
						if (modo)
						{
							posX = cOriginal.getPosicaoY();
							posY = this.comprimento - cOriginal.getPosicaoX() - cOriginal.getComprimento();
							posZ = cOriginal.getPosicaoZ();
							
							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getComprimento(), cOriginal.getLargura(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
						}
						else
						{
							posY = cOriginal.getPosicaoY();
							posX = this.comprimento - cOriginal.getPosicaoX() - cOriginal.getComprimento();
							posZ = cOriginal.getPosicaoZ();
							cNova = new CavidadeFundoArredondado(posX, posY, posZ, cOriginal.getVerticeRaio(), cOriginal.getFundoRaio(), cOriginal.getLargura(), cOriginal.getComprimento(), cOriginal.getProfundidade());
							cNova.setRugosidade(cOriginal.getRugosidade());
							cNova.setTolerancia(cOriginal.getTolerancia());
							
						}
						return cNova;
					default:
						break;
				}
				break;
			default:
				break;
		}
		return null;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Face " + this.getTipoString());
		//root.add(new DefaultMutableTreeNode("Face" + this.comprimento));/*************??????????********/
		root.add(new DefaultMutableTreeNode("Length = " + this.getComprimento()));
		root.add(new DefaultMutableTreeNode("Width = " + this.getLargura()));
		

		//DefaultMutableTreeNode features = new DefaultMutableTreeNode("Features");
		for (int i = 0; i < this.features.size(); i++)
		{
			DefaultMutableTreeNode tmp = null;
			Feature ftmp = (Feature)this.features.elementAt(i);   /******************??????????*************/
			
			switch (ftmp.getTipo())
			{
				case Feature.FURO:
					tmp = ((Furo)ftmp).getNodo();
					break;
					
				case Feature.DEGRAU:
					tmp = ((Degrau)ftmp).getNodo();
					break;
					
				case Feature.RANHURA:
					tmp = ((Ranhura)ftmp).getNodo();
					break;
					
				case Feature.CAVIDADE:
					tmp = ((Cavidade)ftmp).getNodo();
					break;
				case Feature.CAVIDADE_FUNDO_ARREDONDADO:
					tmp = ((CavidadeFundoArredondado)ftmp).getNodo();
					break;
				case Feature.BOSS:  //============= ISTO TAH ESTRANHO!!! ===========
					if(ftmp.getClass() == CircularBoss.class)
					{
						tmp = ((CircularBoss)ftmp).getNode();
					}
					else if(ftmp.getClass() == RectangularBoss.class)
					{
						tmp = ((RectangularBoss)ftmp).getNode();
					} else if(ftmp.getClass() == GeneralProfileBoss.class)
					{
						tmp = ((GeneralProfileBoss)ftmp).getNodo();
					}
					break;
				case Feature.CAVIDADE_PERFIL_GERAL:
					tmp = ((GeneralClosedPocket)ftmp).getNodo();
					break;
				case Feature.REGION:
					tmp = ((Region)ftmp).getNodo();
				default:
					break;
			}
			root.add(tmp);
		}
		//root.add(features);
		return root;
	}
	public boolean setTipo(int tipo){
		if(tipo >= 0 && tipo <= 5){
			this.tipo = tipo;
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "Tipo errado: " + tipo, "Erro", 0);
			return false;
		}
	}
	public int getTipo(){
		return this.tipo;
	}
	
	public boolean setVertice(int verticeAtivado)
	{
		if(verticeAtivado >= 0 && verticeAtivado <= 3){
	
			this.verticeAtivado = verticeAtivado;
			return true;
		}
	else
		return false;
	}
	public void incrementaVertice()
	{
		this.verticeAtivado++;
		if(this.verticeAtivado == 4)
		{
			this.verticeAtivado = 0;
		}
	}	
	public int getVerticeAtivada()
	{
		return this.verticeAtivado;
	}
	public void setLargura(double largura){
		this.largura = largura;
	}
	public double getLargura(){
		return this.largura;
	}
	public void setComprimento(double comprimento){
		this.comprimento = comprimento;
	}
	public double getComprimento(){
		return this.comprimento;
	}
	public void addFeature(Feature feature){
		indices[feature.getTipo()]++;
		feature.setIndice(indices[feature.getTipo()]);
		this.features.add(feature);
		JanelaPrincipal.setDoneCAPP(false);
	}
	public boolean verificaInterseccaoFeatures(Feature feature)
	{
		boolean intersectou = false;
		int intersecta = 0;
		int Nintersecta = 0;
		
			for(int i = 0; i < features.size(); i++)
			{
				Feature featureTmp = (Feature)this.features.elementAt(i);
				Shape shapeTmp = this.getShape(featureTmp);
				Point2D [] borda = this.getShapePontos(feature);
				
				for(int j = 0; j < borda.length; j++)
				{
					if(shapeTmp.contains(borda[i]))
						intersecta = 1;
					else
						Nintersecta = 1;
				}
				if(intersecta == 1 && Nintersecta == 1)
				{
					intersectou = true;
					break;
				}
			}
			System.out.printf("intersecta: %d \nNintersecta: %d\n", intersecta, Nintersecta);
			
		return intersectou;
	}
	public boolean validarFeature(Feature feature)
	{
		boolean valido = false;
		
		/** Implementar o tratamento de intersec��es **/
		
//		if(verificaInterseccaoFeatures(feature))
//		{
//			valido = false;
//			JOptionPane.showMessageDialog(null, "H� colis�o de features");
//		}
//		else
		{
			Feature mae = getMae(feature);
			
			if(mae != null)
			{
				if(mae.getClass() == FuroBasePlana.class)
				{
					FuroBasePlana furoBP = (FuroBasePlana)mae;
					if(feature.getPosicaoZ() == furoBP.getPosicaoZ() + furoBP.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == FuroBaseArredondada.class)
				{
					FuroBaseArredondada furoBA = (FuroBaseArredondada)mae;
					if(feature.getPosicaoZ() == furoBA.getPosicaoZ() + furoBA.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == FuroConico.class)
				{
					FuroConico furoC = (FuroConico)mae;
					if(feature.getPosicaoZ() == furoC.getPosicaoZ() + furoC.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}

				}else if(mae.getClass() == Ranhura.class)
				{
					Ranhura ranhura = (Ranhura)mae;
					if(feature.getPosicaoZ() == ranhura.getPosicaoZ() + ranhura.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == RanhuraPerfilQuadradoU.class)
				{
					RanhuraPerfilQuadradoU rqu = (RanhuraPerfilQuadradoU)mae;
					if(feature.getPosicaoZ() == rqu.getPosicaoZ() + rqu.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == Degrau.class)
				{
					Degrau degrau = (Degrau)mae;
					if(feature.getPosicaoZ() == degrau.getPosicaoZ() + degrau.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == Cavidade.class)
				{
					Cavidade cavidade = (Cavidade)mae;
					if(feature.getPosicaoZ() == cavidade.getPosicaoZ() + cavidade.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == CavidadeFundoArredondado.class)
				{
					CavidadeFundoArredondado cavFundoArr = (CavidadeFundoArredondado)mae;
					if(feature.getPosicaoZ() == cavFundoArr.getPosicaoZ() + cavFundoArr.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == GeneralClosedPocket.class)
				{
					GeneralClosedPocket generalPocket = (GeneralClosedPocket)mae;
					if(feature.getPosicaoZ() == generalPocket.getPosicaoZ() + generalPocket.getProfundidade())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				
				}else if(mae.getClass() == CircularBoss.class)
				{
					CircularBoss cb = (CircularBoss)mae;
					if(feature.getPosicaoZ() == cb.getPosicaoZ())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == RectangularBoss.class)
				{
					RectangularBoss rb = (RectangularBoss)mae;
					if(feature.getPosicaoZ() == rb.getPosicaoZ())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}else if(mae.getClass() == GeneralProfileBoss.class)
				{
					GeneralProfileBoss gb = (GeneralProfileBoss)mae;
					if(feature.getPosicaoZ() == gb.getPosicaoZ())
					{
						valido = true;
						feature.setFeaturePrecedente(mae);
					}
					else
					{
						valido = false;
						
						JOptionPane.showMessageDialog(null, "Erro ao criar a feature, verifique a posicao Z (deve coincidir com a profundidade da feature )" + mae.getNome() + " (" + getProfundidade(mae) + " mm)");
					}
				}
				//** M�es inv�lidas por enquanto inv�lidas, mas arrumar para que elas possam valer tbm *//
				else if(mae.getClass() == FuroBaseEsferica.class || 
						mae.getClass() == FuroBaseConica.class ||
						mae.getClass() == RanhuraPerfilBezier.class ||
						mae.getClass() == RanhuraPerfilCircularParcial.class || 
						mae.getClass() == RanhuraPerfilRoundedU.class ||
						mae.getClass() == RanhuraPerfilVee.class)
				{
					valido = false;
					JOptionPane.showMessageDialog(null, "A nova feature n�o pode ser constru�da dentro desta Feature M�e");
				}
			}
			else
			{
				if(feature.Z == 0)
				{
					valido = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Position Z should be 0");
					valido = false;
				}
			}
		}
			return valido;
	}
	public Feature getMae(Feature feature)
	{
		Feature mae = null;
		ArrayList<Feature> maes = new ArrayList<Feature>();
		boolean isContained = false;
	
		if(features.size() == 0)
		{
			return mae;
		}
		else
		{
			for(int i = 0; i < this.features.size(); i++)
			{
				Feature featureTmp = (Feature)this.features.elementAt(i);
				Shape shapeTmp = this.getShape(featureTmp);
				Shape shapeNew = this.getShape(feature);
				Point2D [] borda = this.getShapePontos(feature);
				for(int j = 0; j < borda.length; j++)
				{
					if(!shapeTmp.contains(borda[j]) && !shapeTmp.equals(shapeNew))
					{
						isContained = false;
						break;
					}else if(shapeTmp.equals(shapeNew))
					{
						isContained = true;
						break;
					}else if (shapeTmp.contains(borda[j]))
					{
						isContained = true;
					}
				}
				if(isContained)
				{
					maes.add(featureTmp);
				}
			}
			
			if(maes.size() > 0)
				mae = maes.get(maes.size() - 1);
				System.out.println("MAE: "+ mae);
		}
			
//		System.out.println(maes);
		return mae;
	}
	public Shape getShape(Feature feature)
	{
		Shape shape = null;
		if(feature.getClass() == FuroBasePlana.class ||feature.getClass() == FuroBaseConica.class || feature.getClass() == FuroBaseEsferica.class)
		{
			Furo furo = (Furo)feature;
			shape = new Ellipse2D.Double(furo.X - furo.getRaio(), furo.Y - furo.getRaio(), furo.getRaio() * 2, furo.getRaio() * 2);
		
		} else if(feature.getClass() == FuroBaseArredondada.class)
		{
			FuroBaseArredondada furoBA = (FuroBaseArredondada)feature;
			shape = new Ellipse2D.Double(furoBA.X - furoBA.getRaio(), furoBA.Y - furoBA.getRaio(), furoBA.getR1() * 2, furoBA.getR1() * 2);
		
		} else if(feature.getClass() == FuroConico.class)
		{
			FuroConico furoC = (FuroConico)feature;
			shape = new Ellipse2D.Double(furoC.X - furoC.getRaio(), furoC.Y - furoC.getRaio(), furoC.getRaio1() * 2, furoC.getRaio1() * 2);
		
		} else if(feature.getClass() == Cavidade.class)
		{
			Cavidade cavidade = (Cavidade)feature;
			shape = new RoundRectangle2D.Double(cavidade.getPosicaoX(), cavidade.getPosicaoY(), cavidade.getComprimento(), cavidade.getLargura(), cavidade.getRaio() * 2, cavidade.getRaio() * 2);
		
		} else if(feature.getClass() == GeneralClosedPocket.class)
		{
			GeneralClosedPocket gcp = (GeneralClosedPocket)feature;
		//	shape = new RoundRectangle2D.Double(gcp.getPosicaoX(), cavidade.getPosicaoY(), cavidade.getComprimento(), cavidade.getLargura(), cavidade.getRaio() * 2, cavidade.getRaio() * 2);
			shape = gcp.getForma();
					
		} else if(feature.getClass() == Ranhura.class)
		{
			Ranhura ranhura = (Ranhura)feature;
			shape = new Rectangle2D.Double(ranhura.X, ranhura.Y, ranhura.getComprimento(), ranhura.getLargura());
		
		} else if(feature.getClass() == Degrau.class)
		{
			Degrau degrau = (Degrau)feature;
			shape = new Rectangle2D.Double(degrau.getPosicaoX(), degrau.getPosicaoY(), degrau.getComprimento(), degrau.getLargura());
			System.out.printf("shapeTmp: X: %f\nY: %f\nCompr: %f\nLarg: %f\n", degrau.getPosicaoX(),degrau.getPosicaoY(), degrau.getComprimento(), degrau.getLargura());
		
		} else if(feature.getClass() == CircularBoss.class)
		{
			CircularBoss cb = (CircularBoss)feature;
			shape = new Ellipse2D.Double(cb.X - cb.getDiametro1()/2, cb.Y - cb.getDiametro1()/2, cb.getDiametro1(), cb.getDiametro1());
		
		} else if(feature.getClass() == RectangularBoss.class)
		{
			RectangularBoss rb = (RectangularBoss)feature;
			shape = new RoundRectangle2D.Double(rb.X, rb.Y, rb.getL2(), rb.getL1(), rb.getRadius()*2, rb.getRadius()*2);
		
		} else if(feature.getClass() == GeneralProfileBoss.class)
		{
			GeneralProfileBoss gpb = (GeneralProfileBoss)feature;
			shape = gpb.getForma();
//			ArrayList<Point2D> vertices = CreateGeneralPocket.transformPolygonInRoundPolygon(gpb.getVertexPoints(), gpb.getRadius());
//			((Path2D) shape).moveTo(gpb.getPosicaoX(),gpb.getPosicaoY());
//			for(int i = 1; i < vertices.size(); i++)
//			{
//				((Path2D)shape).lineTo(gpb.getPosicaoX(), gpb.getPosicaoY());
//			}
//			((Path2D)shape).closePath();
		}
		return shape;
	}
	public Point2D[] getShapePontos(Feature feature)
	{
		Point2D saida[] = null;
		if(feature.getClass() == FuroBasePlana.class)
		{
			FuroBasePlana furo = (FuroBasePlana)feature;
			saida = Cavidade.determinarPontosEmCircunferencia(new Point3d(furo.X, furo.Y, furo.Z), 0, 2 * Math.PI, furo.getRaio(), (int)(2 * Math.PI  * furo.getRaio()/4));
		}else if(feature.getClass() == FuroConico.class)
		{
			FuroConico fc = (FuroConico)feature;
			saida = Cavidade.determinarPontosEmCircunferenciaV2(new Point3d(fc.X, fc.Y, fc.Z), 0, 2 * Math.PI, fc.getRaio());
		}else if(feature.getClass() == FuroBaseArredondada.class)
		{
			FuroBaseArredondada fba = (FuroBaseArredondada)feature;
			saida = Cavidade.determinarPontosEmCircunferenciaV2(new Point3d(fba.X, fba.Y, fba.Z), 0, 2 * Math.PI, fba.getRaio());
		}else if(feature.getClass() == Cavidade.class)
		{
			Cavidade cavidade = (Cavidade)feature;
			saida = Cavidade.determinarPontosEmRoundRectangular(new Point3d(cavidade.X, cavidade.Y, cavidade.Z), cavidade.getComprimento(), cavidade.getLargura(), cavidade.getRaio());
		}else if(feature.getClass() == Degrau.class)
		{
			Degrau degrau = (Degrau)feature;
			saida = Cavidade.determinarPontosEmRetangulo(new Point3d(degrau.X, degrau.Y, 0), degrau.getLargura(), degrau.getComprimento());
		}else if(feature.getClass() == CircularBoss.class)
		{
			CircularBoss cb = (CircularBoss)feature;
			saida = Cavidade.determinarPontosEmCircunferenciaV2(cb.getCentre(), 0, (Math.PI)*2, cb.getDiametro1()/2);
		}else if(feature.getClass() == RectangularBoss.class)
		{
			RectangularBoss rb = (RectangularBoss)feature;
			saida = Cavidade.determinarPontosEmRoundRectangular(new Point3d(rb.X,rb.Y,rb.Z), rb.getL2(), rb.getL1(), rb.getRadius());
		}else if(feature.getClass() == GeneralProfileBoss.class)
		{
			GeneralProfileBoss gpb = (GeneralProfileBoss)feature;
			ArrayList<Point2D> vertex = gpb.getVertexPoints();
			ArrayList<Point2D> retas = new ArrayList<Point2D>();
			for(int i=0; i < vertex.size()-1; i++)
			{
				saida = Cavidade.determinarPontosEmReta(new Point3d( vertex.get(i).getX(), vertex.get(i).getY(), 0.0), 
														new Point3d(vertex.get(i+1).getX(), vertex.get(i+1).getY(), 0.0 ));
				for(int j=0; j < saida.length; j++)
				{
					retas.add(saida[j]);
				}
				
			}
			saida = (Point2D[]) retas.toArray();
		}
		System.out.println("feature class --> " + feature);
		return saida;
	}
	public double getProfundidade(Feature f)
	{
		System.out.println("PROFUNDIDADE");
		switch (f.tipo)
		{
			case Feature.FURO:
				Furo furo = (Furo)f;
				return furo.getProfundidade();
			case Feature.DEGRAU:
				Degrau degrau = (Degrau)f;
				return degrau.getProfundidade();
			case Feature.RANHURA:
				Ranhura ranhura = (Ranhura)f;
				return ranhura.getProfundidade();
			case Feature.CAVIDADE:
				Cavidade cavidade = (Cavidade)f;
				return cavidade.getProfundidade();
			case Feature.CAVIDADE_FUNDO_ARREDONDADO:
				CavidadeFundoArredondado cav = (CavidadeFundoArredondado)f;
				return cav.getProfundidade();
			case Feature.CAVIDADE_PERFIL_GERAL:
				GeneralClosedPocket gen = (GeneralClosedPocket)f;
				return gen.getProfundidade();
			default:
				return -1;
		}
	}
	
	public Rectangle2D criarRetanguloShape(Feature feature)
	{
		double x = 0, y = 0, comprimento = 0, largura = 0;
		switch(feature.tipo)
		{
			case Feature.FURO:
				Furo furo = (Furo)feature;
				x = furo.getPosicaoX() - furo.getRaio();
				y = furo.getPosicaoY() - furo.getRaio();
				comprimento = largura = furo.getRaio() * 2;
				
				Rectangle2D furoRectangle = new Rectangle2D.Double(x, y, comprimento, largura);
				return furoRectangle;
			case Feature.DEGRAU:
				Degrau degrau = (Degrau)feature;
				x = degrau.getPosicaoX();
				y = degrau.getPosicaoY();
				if (degrau.getEixo() == Degrau.HORIZONTAL)
				{
					comprimento = this.getComprimento();
						largura = degrau.getLargura();
				}
				else
				{
					comprimento = degrau.getLargura();
						largura = this.getComprimento();
				}
				Rectangle2D degrauRectangle = new Rectangle2D.Double(x, y, comprimento, largura);
				return degrauRectangle;
			case Feature.RANHURA:
				Ranhura ranhura = (Ranhura)feature;
				x = ranhura.getPosicaoX();
				y = ranhura.getPosicaoY();
				if(ranhura.getEixo() == Ranhura.HORIZONTAL)
				{
					comprimento = this.getComprimento();
						largura = ranhura.getLargura();
				}
				else
				{
					comprimento = ranhura.getLargura();
						largura = this.getComprimento();
				}
				Rectangle2D ranhuraRectangle = new Rectangle2D.Double(x, y, comprimento, largura);
				return ranhuraRectangle;
			case Feature.CAVIDADE:
				Cavidade cavidade = (Cavidade)feature;
				x = cavidade.getPosicaoX();
				y = cavidade.getPosicaoY();
				comprimento = cavidade.getComprimento();
				largura = cavidade.getLargura();
				
				Rectangle2D cavidadeRectangle = new Rectangle2D.Double(x, y, comprimento, largura);
				return cavidadeRectangle;
			case Feature.CAVIDADE_FUNDO_ARREDONDADO:
				CavidadeFundoArredondado cavidadeF = (CavidadeFundoArredondado)feature;
				x = cavidadeF.getPosicaoX();
				y = cavidadeF.getPosicaoY();
				comprimento = cavidadeF.getComprimento();
				largura = cavidadeF.getLargura();
				
				Rectangle2D cavidadeFRectangle = new Rectangle2D.Double(x, y, comprimento, largura);
				return cavidadeFRectangle;
			case Feature.BOSS:
				Boss boss = (Boss)feature;
				if(boss.getClass() == CircularBoss.class)
				{
					CircularBoss c = (CircularBoss)boss;
					x = c.X - c.getDiametro2() / 2;
					y = c.Y - c.getDiametro2() / 2;
					comprimento = c.getDiametro2();
					largura = c.getDiametro2();
					Rectangle2D rectangle = new Rectangle2D.Double(x, y, comprimento, largura);
					return rectangle;
				}else if(boss.getClass() == RectangularBoss.class)
				{
					RectangularBoss r = (RectangularBoss)boss;
					x = r.X - r.getL1() / 2;
					y = r.Y - r.getL2() / 2;
					comprimento = r.getL1();
					largura = r.getL2();
					Rectangle2D rectangle = new Rectangle2D.Double(x, y, comprimento, largura);
					return rectangle;
				}
			case Feature.CAVIDADE_PERFIL_GERAL:
				GeneralClosedPocket gen = (GeneralClosedPocket)feature;
				Rectangle2D rectangle = gen.getForma().getBounds2D();
				return rectangle;
			default:
				break;
		}
		
		return new Rectangle2D.Double(0,0,1,1);
	}
	
	public void imprimeFeature(Feature fTmp){
		switch(fTmp.getTipo()){		
			case Feature.FURO:
				Furo furoTmp = (Furo)fTmp;
				furoTmp.imprimirDados();
			break;
			case Feature.DEGRAU:
				Degrau degrauTmp = (Degrau)fTmp;
				degrauTmp.imprimirDados();
				break;
			case Feature.RANHURA:
				Ranhura ranhuraTmp = (Ranhura)fTmp;
				ranhuraTmp.imprimirDados();
				break;
			case Feature.CAVIDADE:
				Cavidade cavidadeTmp = (Cavidade)fTmp;
				cavidadeTmp.imprimirDados();
				break;
				default:
				break;
		} 
	}
	public String getTipoString(){
		switch(this.tipo){
		case Face.XY:
			return "XY";
		case Face.YZ:
			return "YZ";
		case Face.XZ:
			return "XZ";
		case Face.YX:
			return "YX";
		case Face.ZY:
			return "ZY";
		case Face.ZX:
			return "ZX";
			default:
				return "";//todos os tipos
		}
	}
	
	public void imprimeDados(Feature f)
	{
		System.out.println("########### " + f.getTipoString());
		System.out.println("Mae-->" + f.featureMae);
		System.out.println("Anteriores-->" + f.featuresAnteriores);
		System.out.println("########### " + f.getTipoString());
	}

	public static String getStringFace(int face) {

		String strFace = null;

		switch (face) {
		case Face.XY:
			strFace = "XY";
			break;
		case Face.YZ:
			strFace = "YZ";
			break;
		case Face.XZ:
			strFace = "XZ";
			break;
		case Face.YX:
			strFace = "YX";
			break;
		case Face.ZY:
			strFace = "ZY";
			break;
		case Face.ZX:
			strFace = "ZX";
			break;
		default:
			break;
		}

		return strFace;
	}

	public static int getIndiceFace(String str) {
		
		int indice = -1;
		
		if (str.equals("XY")) {
			indice = 0;
		} else if (str.equals("YZ")) {
			indice = 1;
		} else if (str.equals("XZ")) {
			indice = 2;
		} else if (str.equals("YX")) {
			indice = 3;
		} else if (str.equals("ZY")) {
			indice = 4;
		} else if (str.equals("ZX")) {
			indice = 5;
		}

		return indice;
	}
	
	public ArrayList<Point3d> getPontosDeApoio() {
		return pontosDeApoio;
	}

	public void setPontosDeApoio(ArrayList<Point3d> pontosDeApoio) {
		this.pontosDeApoio = pontosDeApoio;
	}

}
