package br.UFSC.GRIMA.cad;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.DegrauFrame;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;


public class CriarDegrau extends DegrauFrame implements ActionListener, ItemListener
{	
	public Face face = null;
	public int direcao = 0;
	public JanelaPrincipal parent;
	private int local = 0;
	
	public CriarDegrau(JanelaPrincipal parent, Face face)
	{	
		super(parent);
		
		this.face = face;
		this.parent = parent;
		//this.parent.setEnabled(false);
		
		this.init();
	}
	
	public void init(){
		this.adjustSize();
		this.adjustPosition();
		
		this.label7.setText(this.face.getTipoString()); /******troca as setings para indicar em que face se esta trabalhando*****/
		super.okButton.addActionListener(this);
		super.cancelButton.addActionListener(this);
		super.radioButton1.addActionListener(this);
		super.radioButton2.addActionListener(this);
//		super.comboBox1.addItem("HORIZONTAL");
//		super.comboBox1.addItem("VERTICAL");
		super.comboBox1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		super.comboBox1.addItemListener(this);/**************IMPORTANTEEEEEEEEEEEEEEEEE***************/
	
		
		this.setVisible(true);
	}
	
	public void adjustSize(){
		this.pack();
	}

	//centraliza a janela no desktop do usuario
	public void adjustPosition(){
		Toolkit toolkit = this.getToolkit();
		
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();

		int posX = (int)(screenSize.getWidth() - size.getWidth())/2;
		int posY = (int)(screenSize.getHeight() - size.getHeight())/2;

		this.setLocation(posX, posY);
//		System.out.println(this.getLocation().toString());
	}
	
	public void itemStateChanged(ItemEvent evento) 
	{
		URL degrauHorizontalModelo = null;
		int index = this.comboBox1.getSelectedIndex();
		switch (index)
		{
			case 0:
				
				if(this.radioButton1.isSelected())
				{
					degrauHorizontalModelo = getClass().getResource("/images/DegrauHorizontalTopo.png");
					spinner1.setBounds(145, 30, 40, spinner1.getPreferredSize().height);
					spinner2.setBounds(145, 295, 40, spinner2.getPreferredSize().height);
				} else if (this.radioButton2.isSelected())
				{
					degrauHorizontalModelo = getClass().getResource("/images/DegrauHorizontalBase.png");
					spinner1.setBounds(145, 165, 40, spinner1.getPreferredSize().height);
					spinner2.setBounds(145, 295, 40, spinner2.getPreferredSize().height);
				}
				this.radioButton1.setText("No Topo");
				this.radioButton2.setText("Na Base");
				this.direcao = 0;
				break;
			case 1:
				
				if(this.radioButton1.isSelected())
				{
					degrauHorizontalModelo = getClass().getResource("/images/DegrauVerticalEsquerda.png");
					spinner1.setBounds(55, 80, 40, spinner1.getPreferredSize().height);
					spinner2.setBounds(125, 295, 40, spinner2.getPreferredSize().height);


				} else if (this.radioButton2.isSelected())
				{
					degrauHorizontalModelo = getClass().getResource("/images/DegrauVerticalDireita.png");
					spinner1.setBounds(270, 80, 40, spinner1.getPreferredSize().height);
					spinner2.setBounds(200, 295, 40, spinner2.getPreferredSize().height);
				}				
				this.radioButton1.setText("Na Esquerda");
				this.radioButton2.setText("Na Direita");
				this.direcao = 1;
				break;
			default:
				break;
		}
		this.labelImage.setIcon(new ImageIcon(degrauHorizontalModelo));
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object origem = event.getSource();
		if (origem == cancelButton)
		{	cancel();
		}
		else if (origem == okButton)
		{	ok();
		}
		else if (origem == radioButton1)
		{	local = 0;
		URL degrauHorizontalModelo = null;	
		if (this.comboBox1.getSelectedIndex() == 0)
			{
				degrauHorizontalModelo = getClass().getResource("/images/DegrauHorizontalTopo.png");
				spinner1.setBounds(145, 30, 40, spinner1.getPreferredSize().height);
				spinner2.setBounds(145, 295, 40, spinner2.getPreferredSize().height);
			} else if (this.comboBox1.getSelectedIndex() == 1)
			{
				degrauHorizontalModelo = getClass().getResource("/images/DegrauVerticalEsquerda.png");
				spinner1.setBounds(55, 80, 40, spinner1.getPreferredSize().height);
				spinner2.setBounds(125, 295, 40, spinner2.getPreferredSize().height);
			}
			this.labelImage.setIcon(new ImageIcon(degrauHorizontalModelo));

		}
		else if (origem == radioButton2)
		{	local = 1;
		URL degrauHorizontalModelo = null;	
		if (this.comboBox1.getSelectedIndex() == 0)
			{
				degrauHorizontalModelo = getClass().getResource("/images/DegrauHorizontalBase.png");
				spinner1.setBounds(145, 165, 40, spinner1.getPreferredSize().height);
				spinner2.setBounds(145, 295, 40, spinner2.getPreferredSize().height);
			} else if (this.comboBox1.getSelectedIndex() == 1)
			{
				degrauHorizontalModelo = getClass().getResource("/images/DegrauVerticalDireita.png");
				spinner1.setBounds(270, 80, 40, spinner1.getPreferredSize().height);
				spinner2.setBounds(200, 295, 40, spinner2.getPreferredSize().height);
			}
			this.labelImage.setIcon(new ImageIcon(degrauHorizontalModelo));

		}
	}

	private void ok() 
	{	double profundidade = 0;
		double largura = 0;
		double posicaoZ = 0;
		boolean ok = true;
		double posX = 0.0, posY = 0.0, posZ = 0.0;
		double diametroFresa = 0;
		double rugosidade = 0;
		
		if (ok)
		{	try
			{	profundidade = ((Double)this.spinner2.getValue()).doubleValue();
				/*if (profundidade > 0)
				{	//System.out.println("posi��o X: " + profundidade);
					ok = true;
				}
				else
				{	ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um N�mero positivo para a profundidade",
							"Erro no valor da profundidade", JOptionPane.CANCEL_OPTION);
				}*/
			}
			catch (Exception e)
			{	JOptionPane.showMessageDialog(null,
					"Digite um N�mero positivo para a profundidade",
					"Erro no valor da profundidade", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		if (ok)
		{	try
			{	largura = ((Double)this.spinner1.getValue()).doubleValue();
									

			if (largura > 0)
			{	//System.out.println("Largura: " + largura);
				ok = true;
			}
			else
			{	ok = false;
			JOptionPane.showMessageDialog(null,
					"O valor da largura deve ser maior do que zero",
					"Erro no valor da largura", JOptionPane.CANCEL_OPTION);
			}



				
			}
			catch (Exception e)
			{	JOptionPane.showMessageDialog(null,
					"Digite um N�mero positivo para a largura",
					"Erro no valor da largura", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		if (ok)
		{	try
			{	posicaoZ = ((Double)this.spinner3.getValue()).doubleValue();
				if (posicaoZ >= 0 && face.getProfundidadeMaxima() > posicaoZ + profundidade)
				{	
					ok = true;
				}
				else
				{	ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a profundidade n�o ultrapasse a profundidade m�xima da face",
							"erro na posi��o Z", JOptionPane.CANCEL_OPTION);
				}
			}
			catch (Exception e)
			{	JOptionPane.showMessageDialog(null,
					"Digite um N�mero positivo para a largura????",
					"Erro no valor da largura", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * set da RUGOSIDADE, nao precisa validacao
		 * 
		 */
		if (ok) {
				rugosidade = ((Double) degrauRug.getValue()).doubleValue();
		}
		if (ok)
		{
			/**
			 * ******************
			 */
			//double tmp;
			/**
			 * o codigo a seguir acha o ponto de referencia do degrau respecto ao zero da face.
			 * 
			 */
			switch (direcao)		/******gera degraus em alguma aresta******/
			{	
				case 0:				/****************HORIZONTAL****************/
					if (local == 0)	/*******SUPERIOR***********/
					{	
						posX = 0;
						posY = face.getLargura() - largura;
						posZ = posicaoZ;
					}
					else			/*******INFERIOR***********/
					{
						posX = 0;
						posY = 0;
						posZ = posicaoZ;
					}
					break;
				case 1:				/******************VERTICAL*******************/
					if (local == 0) /********ESQUERDA***********/
					{	
						posX = 0;
						posY = 0;
						posZ = posicaoZ;
					}
					else			/**********DIREITA***********/
					{
						posX = face.getComprimento() - largura;
						posY = 0;
						posZ = posicaoZ;
					}
					break;
				default:
					break;
			}
			Degrau novoDegrau =  new Degrau();
			double X = 0, Y = 0, Z = 0;
			double comprimento = 0;
			switch(face.verticeAtivado)
			{
				case 0:
					X = posX;  			/**********Nao faz nada *****************/
					Y = posY;
					Z = posZ;
					novoDegrau.setEixo(this.direcao);
					if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
					{
						comprimento = this.face.getComprimento();
						if(largura > 0 && largura < face.getLargura())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (001)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					else
					{
						comprimento = this.face.getLargura();
						if(largura > 0 && largura < face.getComprimento())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (002)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					break;		
				case 1:
					if(this.direcao == 0)		/*******************HORIZONTAL*******************/
					{
						novoDegrau.setEixo(Degrau.VERTICAL);
						if(this.local == 0)		/***********superior*************/
						{
							X = face.getComprimento() - largura;
							Y = 0;
							Z = posZ;
						}
						else					/***********inferior************/
						{
							Y = 0;
							X = 0;
							Z = posZ;
						}
					}
					else{						/*****************VERTICAL*********************/
						novoDegrau.setEixo(Degrau.HORIZONTAL);
						
						if(this.local == 0){
							X = 0;
							Y = face.getLargura() - largura;
							Z = posZ;
						}
						else{
							X = 0;
							Y = 0;
							Z = posZ;
						}
					}	
					if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
					{
						comprimento = this.face.getComprimento();
						if (largura > 0 && largura < face.getLargura())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (003)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					else
					{
						comprimento = this.face.getLargura();
						if (largura > 0 && largura < face.getComprimento())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (004)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					break;
				case 2:
					if (this.direcao == 0)
					{
						novoDegrau.setEixo(Degrau.HORIZONTAL);
						
						if (this.local == 0)
						{
							X = 0;
							Y = 0;
							Z = posZ;
						}
						else
						{
							X = 0;
							Y = face.getLargura() - largura;
							Z = posZ;
						}
					}
					else
					{
						novoDegrau.setEixo(Degrau.VERTICAL);
						
						if (this.local == 0)
						{
							X = face.getComprimento() - largura;
							Y = 0;
							Z = posZ;
						}
						else
						{
							X = 0;
							Y = 0;
							Z = posZ;
						}
					}
					if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
					{
						comprimento = this.face.getComprimento();
						if(largura > 0 && largura < face.getLargura())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (001a)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					else
					{
						comprimento = this.face.getLargura();
						if(largura > 0 && largura < face.getComprimento())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (002a)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					break;
				case 3:
					if (this.direcao == 0)
					{
						novoDegrau.setEixo(Degrau.VERTICAL);
						if (local == 0)
						{
							X = 0;
							Y = 0;
							Z = posZ;
						}
						else
						{
							X = face.getComprimento() - largura;
							Y = 0;
							Z = posZ;
						}
					}
					else
					{
						novoDegrau.setEixo(Degrau.HORIZONTAL);
						if (this.local == 0)
						{
							X = 0;
							Y = 0;
							Z = posZ;
						}
						else
						{
							X = 0;
							Y = face.getLargura() - largura;
							Z = posZ;
						}
					}
					if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
					{
						comprimento = this.face.getComprimento();
						if (largura > 0 && largura < face.getLargura())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (003a)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					else
					{
						comprimento = this.face.getLargura();
						if (largura > 0 && largura < face.getComprimento())
						{
							ok = true;
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura esteja dentro dos limites da face",
									"Erro no valor da largura (004a)", JOptionPane.CANCEL_OPTION);
							ok = false;
						}
					}
					break;
				default:
					break;
			}
			/************coloca os parametros do degrau**************/
			if(ok){
			novoDegrau.setLargura(largura);	
			novoDegrau.setProfundidade(profundidade);
			novoDegrau.setPosicao(X, Y, Z);
			novoDegrau.setNome(this.textField2.getText());
			novoDegrau.setRugosidade(rugosidade*0.001);
			novoDegrau.setTolerancia(rugosidade*0.001);
			novoDegrau.setComprimento(comprimento);
			
			if(rugosidade<=Feature.LIMITE_RUGOSIDADE)
				novoDegrau.setAcabamento(true);
			
				if (this.face.validarFeature(novoDegrau))
				{
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(X, novoDegrau.getLargura() + Y, this.face.getProfundidadeMaxima() - Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(-1.0);
								refDirection.add(0.0);
							} else // no topo
							{
								coordinates = new Point3d(X + this.face.getComprimento(), Y, this.face.getProfundidadeMaxima() - Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(1.0);
								refDirection.add(0.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(X + novoDegrau.getLargura(), Y + this.face.getLargura(), this.face.getProfundidadeMaxima() - Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(-1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							} else // na direita
							{
								coordinates = new Point3d(X, Y, this.face.getProfundidadeMaxima() - Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							}
						}
					} else if (this.face.getTipo() == Face.XZ)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(Y, X, novoDegrau.getLargura() + Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(-1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(-1.0);
							} else // no topo
							{
								coordinates = new Point3d(this.face.getLargura() + Y, Z, this.face.getComprimento() - novoDegrau.getLargura() + X);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(-1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(1.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(Z + novoDegrau.getLargura(), X, Y);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(-1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(-1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							} else // na direita
							{
								coordinates = new Point3d(this.face.getComprimento() - novoDegrau.getLargura() - Z, X, Y);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(-1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							}
						}
					} else if (this.face.getTipo() == Face.YX)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(this.face.getLargura() - novoDegrau.getLargura() + X, Y, Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(-1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(1.0);
								refDirection.add(0.0);
							} else // no topo
							{
								coordinates = new Point3d(this.face.getProfundidadeMaxima() + X, novoDegrau.getLargura() + Y, Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(-1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(-1.0);
								refDirection.add(0.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(novoDegrau.getLargura() + X,Y, Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(-1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(-1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							} else // na direita
							{
								coordinates = new Point3d(this.face.getComprimento() - novoDegrau.getLargura() + X, this.face.getLargura() + Y, Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(0.0);
								axis.add(-1.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							}
						}
						
					} else if (this.face.getTipo() == Face.YZ)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(-Z + this.face.getProfundidadeMaxima(), novoDegrau.getLargura() + X, this.face.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(-1.0);
								refDirection.add(0.0);
							} else // no topo
							{
								coordinates = new Point3d(-Z + this.face.getProfundidadeMaxima(), this.face.getLargura() + X,Y - novoDegrau.getLargura());
								axis = new ArrayList<Double>();
								axis.add(1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(1.0);
								refDirection.add(0.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(-Z + this.face.getProfundidadeMaxima(), this.face.getComprimento() + X, this.face.getLargura() - novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(1.0);
							} else // na direita
							{
								coordinates = new Point3d(-Z + this.face.getProfundidadeMaxima(), X, novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(-1.0);
							}
						}
						
					} else if (this.face.getTipo() == Face.ZX)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(X, this.face.getProfundidadeMaxima() - Z, this.face.getLargura() - novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(1.0);
							} else // no topo
							{
								coordinates = new Point3d(this.face.getComprimento() + X, this.face.getProfundidadeMaxima() - Z, novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(-1.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(novoDegrau.getLargura() + X, this.face.getProfundidadeMaxima() + Y, - Z);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(-1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							} else // na direita
							{
								coordinates = new Point3d(this.face.getComprimento() - novoDegrau.getLargura() + X, this.face.getProfundidadeMaxima() - Z, this.face.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(0.0);
								axis.add(1.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(1.0);
								refDirection.add(0.0);
								refDirection.add(0.0);
							}
						}
					} else if (this.face.getTipo() == Face.ZY)
					{
						if (novoDegrau.getEixo() == Degrau.HORIZONTAL)
						{
							if (novoDegrau.getPosicaoY() == 0) // na base
							{
								coordinates = new Point3d(Z, novoDegrau.getLargura() + X, Y);
								axis = new ArrayList<Double>();
								axis.add(-1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(-1.0);
								refDirection.add(0.0);
							} else // no topo
							{
								coordinates = new Point3d(Z, this.face.getComprimento() - novoDegrau.getLargura() + X, this.face.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(-1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(1.0);
								refDirection.add(0.0);
							}
						} else if (novoDegrau.getEixo() == Degrau.VERTICAL)
						{
							if(novoDegrau.getPosicaoX() == 0) // na esquerda
							{
								coordinates = new Point3d(Z, X, this.face.getLargura() - novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(-1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(1.0);
							} else // na direita
							{
								coordinates = new Point3d(Z, this.face.getComprimento() + X, novoDegrau.getLargura() + Y);
								axis = new ArrayList<Double>();
								axis.add(-1.0);
								axis.add(0.0);
								axis.add(0.0);
								
								refDirection = new ArrayList<Double>();
								refDirection.add(0.0);
								refDirection.add(0.0);
								refDirection.add(-1.0);
							}
						}						
					}
					Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
					position.setName(novoDegrau.getNome() + " placement");
					novoDegrau.setPosition(position);	
					this.face.addFeature(novoDegrau);
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					//face.imprimeDados(novoDegrau);
					this.parent.setEnabled(true);
				
					this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Degrau: " + novoDegrau.getNome() + " adicionado com sucesso!");
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "N�o � poss�vel criar o Degrau", "Erro ao criar Feature", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}
	}
	private void cancel()
	{
		this.parent.setEnabled(true);
		dispose();
	}
}