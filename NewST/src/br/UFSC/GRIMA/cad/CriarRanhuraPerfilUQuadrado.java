package br.UFSC.GRIMA.cad;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.RanhuraFrame;
import br.UFSC.GRIMA.cad.visual.RanhuraPerfilUFrame;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;


public class CriarRanhuraPerfilUQuadrado extends RanhuraPerfilUFrame implements ActionListener
{	public Face face = null;
	public int eixo = 0;
	public JanelaPrincipal parent;

	public CriarRanhuraPerfilUQuadrado(JanelaPrincipal parent, Face face)
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
		
		this.label10.setText(this.face.getTipoString());
		super.cancelButton.addActionListener(this);
		super.okButton.addActionListener(this);		
		super.radioButtonX.addActionListener(this);
		super.radioButtonY.addActionListener(this);
		super.radioButton1.addActionListener(this);
		super.radioButton2.addActionListener(this);
		super.spinner1.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				double largura1 = ((Double)spinner3.getValue()).doubleValue();
				double profundidade = ((Double)spinner5.getValue()).doubleValue();
				double angulo = ((Double)spinner1.getValue()).doubleValue();
				double largura2 = largura1 - 2 * profundidade / (Math.tan(Math.PI * angulo/180));
				spinner2.setModel(new SpinnerNumberModel(largura2, null, null, 1.0));
			}
		});
		super.spinner2.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				double largura1 = ((Double)spinner3.getValue()).doubleValue();
				double profundidade = ((Double)spinner5.getValue()).doubleValue();
				double largura2 = ((Double)spinner2.getValue()).doubleValue();
				double angulo = (Math.atan(2 * profundidade / (largura1 - largura2))) * 180 / Math.PI;
				spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
			}
		});
		super.spinner3.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				double largura1 = ((Double)spinner3.getValue()).doubleValue();
				double profundidade = ((Double)spinner5.getValue()).doubleValue();
				if(radioButton1.isSelected())
				{
					double angulo = ((Double)spinner1.getValue()).doubleValue();
					double largura2 = largura1 - 2 * profundidade / (Math.tan(Math.PI * angulo/180));
					spinner2.setModel(new SpinnerNumberModel(largura2, null, null, 1.0));
				}
				else if(radioButton2.isSelected())
				{
					double largura2 = ((Double)spinner2.getValue()).doubleValue();
					double angulo = (Math.atan(2 * profundidade / (largura1 - largura2))) * 180 / Math.PI;
					spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
				}
			}
		
		});
		super.spinner5.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				double largura1 = ((Double)spinner3.getValue()).doubleValue();
				double profundidade = ((Double)spinner5.getValue()).doubleValue();
				if(radioButton1.isSelected())
				{
					double angulo = ((Double)spinner1.getValue()).doubleValue();
					double largura2 = largura1 - 2 * profundidade / (Math.tan(Math.PI * angulo/180));
					spinner2.setModel(new SpinnerNumberModel(largura2, null, null, 1.0));
				}
				else if(radioButton2.isSelected())
				{
					double largura2 = ((Double)spinner2.getValue()).doubleValue();
					double angulo = (Math.atan(2 * profundidade / (largura1 - largura2))) * 180 / Math.PI;
					spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
				}
			}
		
		});
		
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
	}
	
	
	public void actionPerformed(ActionEvent event) 
	{	Object origem = event.getSource();
		if (origem == okButton) 
		{	
			okSelecionado();
		}
		else
			if (origem == cancelButton)
			{	CancelSelecionado();
			}
		else
			if (origem == radioButtonX)
			{	
				eixo = 0;
				URL ranhuraHorizontalModelo = getClass().getResource("/images/RanhuraPerfilUQuadradoHorizontal.png");
				spinnerDelocamento.setBounds(135, 165, 40, spinnerDelocamento.getPreferredSize().height);
				spinner5.setBounds(110, 305, 40, 21);

				this.labelImage.setIcon(new ImageIcon(ranhuraHorizontalModelo));
			}
		else
			if (origem == radioButtonY)
			{	
				eixo = 1;
				URL ranhuraVerticalModelo = getClass().getResource("/images/RanhuraPerfilUQuadradoVertical.png");
				spinnerDelocamento.setBounds(80, 105, 40, spinnerDelocamento.getPreferredSize().height);
				spinner5.setBounds(110, 305, 40, 21);
				this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));
			}	
		else
			if (origem == radioButton1)
			{
				this.spinner2.setEnabled(false);
				this.spinner1.setEnabled(true);
			}
		else
			if (origem == radioButton2)
			{
				this.spinner2.setEnabled(true);
				this.spinner1.setEnabled(false);		
			}
	}
		
	private void CancelSelecionado() 
	{	
		this.parent.setEnabled(true);
		dispose();
	}
	private void okSelecionado() 
	{	boolean ok = true;
		double profundidade = 0.0;
		double largura1 = 0.0, largura2 = 0.0;
		double deslocamento = 0.0;
		double posicaoZ = 0.0;      
		double posX = 0, posY = 0, posZ = 0; 
		double angulo = 0;
		double raio = 0;
		double diametroFresa = new Ferramenta().getDiametroFerramenta();
		double rugosidade = 0;
		double tolerancia = 0;
		
		/** ****** validacao dos dados introduzidos no campo "PROFUNDIDADE" ****** 
		 * 
		 */
		
		if (ok) {
			try {
				profundidade = ((Double) this.spinner5.getValue()).doubleValue();

				if (profundidade > 0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para a PROFUNDIDADE da Ranhura",
							"Erro na profundidade", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a PROFUNDIDADE da Ranhura",
						"Erro na PROFUNDIDADE", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		
		/** ****** validacao dos dados introduzidos no campo "LARGURA" ****** 
		 * 
		 */
		
		if (ok) {
			try {
				largura1 = ((Double)this.spinner3.getValue()).doubleValue();
				if (largura1 > 0 && largura1 >= diametroFresa) {
					
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a largura da ranhura seja maior ou igual  da ferramenta (" + diametroFresa + "mm)",
							"Erro na Largura", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um N�mero positivo para a LARGURA da Ranhura",
						"Erro na LARGURA", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
/** ****** validacao dos dados introduzidos no campo "DESLOCAMENTO" ******
 * 
 */
		if (ok) {
			try {
				deslocamento = ((Double)this.spinnerDelocamento.getValue()).doubleValue();
				if (deslocamento > 0 ) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
							"Erro no DESLOCAMENTO", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
						"Erro no DESLOCAMENTO", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * set da RUGOSIDADE, nao precisa validacao
		 * 
		 */
		if (ok) {
				rugosidade = ((Double) ranhuraRug.getValue()).doubleValue();
		}
		/**
		 * set da TOLERANCIA, nao precisa validacao
		 * 
		 */
		if (ok) {
				tolerancia = ((Double) ranhuraTol.getValue()).doubleValue();
		}
		/****** validacao dos dados introduzidos no campo "POSICAO Z" ****** 
		 * 
		 */
				if (ok) {
					try {
						posicaoZ = ((Double)this.spinnerZ.getValue()).doubleValue();
						if (posicaoZ >= 0 && profundidade + posicaoZ < face.getProfundidadeMaxima()) {
							ok = true;
						} else {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Verifique que a profundidade nao ultrapasse a profundidade m�xima do bloco",
									"Erro no POSIÇÃO Z(001)", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Digite um Número positivo para o POSIÇÃO Z da Ranhura",
								"Erro no POSIÇÃO Z", JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				
				/** validação do campo ANGULO
				 * 
				 */
				if (ok)
				{
					try {
						angulo = ((Double) this.spinner1.getValue()).doubleValue();

						if (angulo > 0 && angulo <= 90) {
							ok = true;
						} else {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Digite um Número entre 0 e 90 para o ANGULO da Ranhura",
									"Erro no angulo", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Digite um Número positivo para o ANGULO da Ranhura",
								"Erro no ANGULO", JOptionPane.CANCEL_OPTION);
						ok = false;
					}
					
					// validar até onde possa se fechar, nem deixar virar maior que 90 graus nem negativo
				}
				/**
				 * validacao do campo RAIO
				 */
				if (ok)
				{
					double x,d,R,radAng;
					try {
						raio = ((Double) this.spinner4.getValue()).doubleValue();
						angulo = ((Double) this.spinner1.getValue()).doubleValue();
						largura2 = ((Double)this.spinner2.getValue()).doubleValue();
						radAng = angulo*Math.PI/180;
						x = Math.cos(radAng)*largura2/2;
						d = x/Math.sin(radAng);
						R = d*(largura2/2+x)/x;
						
						if (raio > 0 && raio <= R) {
							ok = true;
						} else {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura da ranhura seja maior que 0 ou menor que o maior raio possivel (" + R + "mm)",
									"Erro no Raio", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Digite um Número positivo para o RAIO da Ranhura",
								"Erro no RAIO", JOptionPane.CANCEL_OPTION);
						ok = false;
					}
					
					raio = ((Double)this.spinner4.getValue()).doubleValue();
					//validar para que eles não se sobreponham
				}
				/**
				 * validacao do campo LARGURA 2
				 */
				if (ok)
				{
					try {
						largura2 = ((Double)this.spinner2.getValue()).doubleValue();
						if (largura2 > 0 && largura2 <= largura1) {

							ok = true;
						} else {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Verifique que a largura2 da ranhura seja maior que 0 e menor que a largura1 (" + largura1 + "mm)",
									"Erro na Largura2", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Digite um N�mero positivo para a LARGURA2 da Ranhura",
								"Erro na LARGURA2", JOptionPane.CANCEL_OPTION);
						ok = false;
					}
					// nao deve ser maior do que a largura 1
				}
				if (ok)
				{
					switch (eixo)
					{	case 0:		/******HORIZONTAL**********/
							posX = 0;
							posY = deslocamento;
							posZ = posicaoZ;  
							break;
							
						case 1:					
							posX = deslocamento;
							posY = 0;
							posZ = posicaoZ;
							break;
							
						default:
							break;
					}
					RanhuraPerfilQuadradoU novaRanhura = new RanhuraPerfilQuadradoU();
					double X = 0, Y = 0, Z = 0;
					double comprimento = 0;
					switch(this.face.verticeAtivado){
						case 0:
							
							X = posX;
							Y = posY;
							Z = posZ;
							novaRanhura.setEixo(this.eixo);
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posY + largura1 > 0 && posY + largura1 < face.getLargura())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limtites da face", 
											"Erro na largura", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							else
							{
								comprimento = this.face.getLargura();
								if (posX + largura1 > 0 && posX + largura1 < face.getComprimento())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limites da face"
											,"Erro na largura(001)", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							break;
							
						case 1:
							if (eixo == 0)
							{
								novaRanhura.setEixo(Degrau.VERTICAL);
								X = deslocamento;
								Y = 0;
								Z = posZ; 
							}
							else
							{
								novaRanhura.setEixo(Degrau.HORIZONTAL);
								X = 0;
								Y = face.getLargura() - deslocamento - largura1;
								Z = posZ;
							}
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posX + largura1 > 0 && posX + largura1 < face.getLargura())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limtites da face", 
											"Erro na largura", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							else
							{
								comprimento = this.face.getLargura();
								if (posY + largura1 > 0 && posY + largura1 < face.getComprimento())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limites da face"
											,"Erro na largura(001)", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
						break;	
						
						case 2:
							if (eixo == 0)
							{
								novaRanhura.setEixo(Degrau.HORIZONTAL);
								X = 0;
								Y = face.getLargura() - deslocamento - largura1;
								Z = posZ;
							}
							else
							{
								novaRanhura.setEixo(Degrau.VERTICAL);
								X = face.getComprimento() - deslocamento - largura1;
								Y = 0;
								Z = posZ;
							}
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posY + largura1 > 0 && posY + largura1 < face.getLargura())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limtites da face", 
											"Erro na largura(001a)", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							else
							{
								comprimento = this.face.getLargura();
								if (posX + largura1 > 0 && posX + largura1 < face.getComprimento())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limites da face"
											,"Erro na largura(002a)", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							break;
							
						case 3:
							if (eixo == 0)
							{
								novaRanhura.setEixo(Degrau.VERTICAL);
								X = face.getComprimento() - deslocamento - largura1;
								Y = 0;
								Z = posZ;
							}
							else
							{
								novaRanhura.setEixo(Degrau.HORIZONTAL);
								X = 0;
								Y = deslocamento;
								Z = posZ;
							}
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posX + largura1 > 0 && posX + largura1 < face.getLargura())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limtites da face", 
											"Erro na largura", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							else
							{
								comprimento = this.face.getLargura();
								if (posY + largura1 > 0 && posY + largura1 < face.getComprimento())
								{
									ok = true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Verifique que a largura da ranhura esteja dentro dos limites da face"
											,"Erro na largura(001)", JOptionPane.CANCEL_OPTION);
									ok = false;
								}
							}
							break;
						default:
							break;
					}
					if (ok)
					{
						novaRanhura.setLargura(largura1);
						novaRanhura.setLargura2(largura2);
						novaRanhura.setProfundidade(profundidade);
						novaRanhura.setPosicao(X, Y, Z);
						novaRanhura.setNome(this.textFieldNome.getText());
						novaRanhura.setRugosidade(rugosidade*0.001);
						novaRanhura.setTolerancia(tolerancia*0.001);
						novaRanhura.setRaio(raio);
						novaRanhura.setAngulo(90 - angulo);
						novaRanhura.setComprimento(comprimento);
						
						if(rugosidade<=Feature.LIMITE_RUGOSIDADE)
							novaRanhura.setAcabamento(true);
						
						if (this.face.validarFeature(novaRanhura))
						{
							Point3d sweptLocation = new Point3d(0, 0, -novaRanhura.getProfundidade());
							Point3d coordinates = null;
							ArrayList<Double> axis = null, refDirection = null, sweptAxis = null, sweptRefDirection = null;
							
							if (this.face.getTipo() == Face.XY)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(0, novaRanhura.Y + novaRanhura.getLargura()/2, this.face.getProfundidadeMaxima() - novaRanhura.Z);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(0.0);
									axis.add(1.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(novaRanhura.X + novaRanhura.getLargura()/2, 0, this.face.getProfundidadeMaxima() - novaRanhura.Z);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(0.0);
									axis.add(1.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
									
								}
							} else if (this.face.getTipo() == Face.XZ)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(0, novaRanhura.Z, novaRanhura.Y + novaRanhura.getLargura()/2);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(-1.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(novaRanhura.X + novaRanhura.getLargura()/2, novaRanhura.Z , 0);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(-1.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
									
								}	
							} else if (this.face.getTipo() == Face.YX)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(0,this.face.getLargura() - novaRanhura.Y, novaRanhura.Z);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(0.0);
									axis.add(-1.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(novaRanhura.X + novaRanhura.getLargura()/2, face.getLargura(), novaRanhura.Z);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(0.0);
									axis.add(-1.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
								}								
							} else if (this.face.getTipo() == Face.YZ)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(this.face.getProfundidadeMaxima() - novaRanhura.Z, novaRanhura.Y + novaRanhura.getLargura()/2, this.face.getComprimento() );
									axis = new ArrayList<Double>();
									axis.add(1.0);
									axis.add(0.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(0.0);
									refDirection.add(0.0);
									refDirection.add(-1.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(this.face.getProfundidadeMaxima() - novaRanhura.Z, 0, this.face.getComprimento() - novaRanhura.X);
									axis = new ArrayList<Double>();
									axis.add(1.0);
									axis.add(0.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(0.0);
									refDirection.add(0.0);
									refDirection.add(-1.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
								}								
							} else if (this.face.getTipo() == Face.ZX)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(0, this.face.getProfundidadeMaxima() - novaRanhura.Z, this.face.getLargura() - novaRanhura.Y - novaRanhura.getLargura()/2);
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(1.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(novaRanhura.X + novaRanhura.getLargura()/2, this.face.getProfundidadeMaxima() - novaRanhura.Z, this.face.getLargura());
									axis = new ArrayList<Double>();
									axis.add(0.0);
									axis.add(1.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(1.0);
									refDirection.add(0.0);
									refDirection.add(0.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
								}								
							} else if (this.face.getTipo() == Face.ZY)
							{
								if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
								{
									coordinates = new Point3d(novaRanhura.Z, novaRanhura.Y + novaRanhura.getLargura()/2, 0);
									axis = new ArrayList<Double>();
									axis.add(-1.0);
									axis.add(0.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(0.0);
									refDirection.add(0.0);
									refDirection.add(1.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(-1.0);
									sweptRefDirection.add(0.0);
									
								} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
								{
									coordinates = new Point3d(novaRanhura.Z, 0, novaRanhura.X + novaRanhura.getLargura()/2);
									axis = new ArrayList<Double>();
									axis.add(-1.0);
									axis.add(0.0);
									axis.add(0.0);
									
									refDirection = new ArrayList<Double>();
									refDirection.add(0.0);
									refDirection.add(0.0);
									refDirection.add(1.0);
									
									sweptAxis = new ArrayList<Double>();
									sweptAxis.add(0.0);
									sweptAxis.add(0.0);
									sweptAxis.add(1.0);
									
									sweptRefDirection = new ArrayList<Double>();
									sweptRefDirection.add(1.0);
									sweptRefDirection.add(0.0);
									sweptRefDirection.add(0.0);
								}			
							}
							Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
							position.setName(novaRanhura.getNome() + " placement");
							novaRanhura.setPosition(position);	
							
							Axis2Placement3D sweptShapePosition = new Axis2Placement3D(sweptLocation, sweptAxis, sweptRefDirection); 
							novaRanhura.setSweptShapePosition(sweptShapePosition);
							
							this.face.addFeature(novaRanhura);
							try
							{
								Feature mae = novaRanhura.getFeaturePrecedente();
								mae.itsSons.add(novaRanhura);
							} catch(Exception e)
							{
								
							}
							this.parent.desenhador.repaint();
							this.parent.atualizarArvore();
							this.parent.atualizarArvorePrecedencias();

							//face.imprimeDados(novaRanhura);
							this.parent.setEnabled(true);
							this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Ranhura Perfil U Quadrado: " + novaRanhura.getNome() + " adicionada com sucesso!");
						
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Erro na criação da Ranhura", "Feature inválida", JOptionPane.OK_CANCEL_OPTION);
						}
					}
		}
	}
}