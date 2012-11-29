package br.UFSC.GRIMA.cad;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.RanhuraFrame;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;


public class CriarRanhura extends RanhuraFrame implements ActionListener
{	public Face face = null;
	public int eixo = 0;
	public JanelaPrincipal parent;

	public CriarRanhura(JanelaPrincipal parent, Face face)
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
				URL ranhuraHorizontalModelo = getClass().getResource("/images/RanhuraSimples.png");
				this.spinner1.setBounds(165, 95, 40, spinner1.getPreferredSize().height);
				this.spinner2.setBounds(130, 305, 40, spinner2.getPreferredSize().height);
				this.spinner3.setBounds(130, 165, 40, spinner3.getPreferredSize().height);
				this.labelImage.setIcon(new ImageIcon(ranhuraHorizontalModelo));
			}
		else
			if (origem == radioButtonY)
			{	
				eixo = 1;
				URL ranhuraVerticalModelo = getClass().getResource("/images/RanhuraSimplesVertical.png");
				this.spinner1.setBounds(185, 115, 40, spinner1.getPreferredSize().height);
				this.spinner2.setBounds(110, 305, 40, spinner2.getPreferredSize().height);
				this.spinner3.setBounds(90, 100, 40, spinner3.getPreferredSize().height);
				this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));
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
		double largura = 0.0;
		double deslocamento = 0.0;
		double posicaoZ = 0.0;      
		double posX = 0, posY = 0, posZ = 0; 
		double diametroFresa = new Ferramenta().getDiametroFerramenta();
		double rugosidade = 0;
		
		/** ****** validacao dos dados introduzidos no campo "PROFUNDIDADE" ****** 
		 * 
		 */
		
		if (ok) {
			try {
				profundidade = ((Double)this.spinner2.getValue()).doubleValue();
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
				largura = ((Double)this.spinner1.getValue()).doubleValue();
				if (largura > 0 && largura >= diametroFresa) {
					
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a largura da ranhura seja maior ou igual � da ferramenta (" + diametroFresa + "mm)",
							"Erro na Largura", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a LARGURA da Ranhura",
						"Erro na LARGURA", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
/** ****** validacao dos dados introduzidos no campo "DESLOCAMENTO" ******
 * 
 */
		if (ok) {
			try {
				deslocamento = ((Double)this.spinner3.getValue()).doubleValue();
				if (deslocamento > 0 ) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um N�mero positivo para o DESLOCAMENTO da Ranhura",
							"Erro no DESLOCAMENTO", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um N�mero positivo para o DESLOCAMENTO da Ranhura",
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
		/****** validacao dos dados introduzidos no campo "POSICAO Z" ****** 
		 * 
		 */
				if (ok) {
					try {
						posicaoZ = ((Double)this.spinner4.getValue()).doubleValue();
						if (posicaoZ >= 0 && profundidade + posicaoZ < face.getProfundidadeMaxima()) {
							ok = true;
						} else {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Verifique que a profundidade nao ultrapasse a profundidade m�xima do bloco",
									"Erro no POOSI��O Z(001)", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Digite um N�mero positivo para o POSI��O Z da Ranhura",
								"Erro no POOSI��O Z", JOptionPane.CANCEL_OPTION);
						ok = false;
					}
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
					Ranhura novaRanhura = new Ranhura();
					double X = 0, Y = 0, Z = 0, comprimento = 0;
					switch(this.face.verticeAtivado){
						case 0:
							
							X = posX;
							Y = posY;
							Z = posZ;
							novaRanhura.setEixo(this.eixo);
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posY + largura > 0 && posY + largura < face.getLargura())
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
								if (posX + largura > 0 && posX + largura < face.getComprimento())
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
								novaRanhura.setEixo(Ranhura.VERTICAL);
								X = deslocamento;
								Y = 0;
								Z = posZ; 
							}
							else
							{
								novaRanhura.setEixo(Ranhura.HORIZONTAL);
								X = 0;
								Y = face.getLargura() - deslocamento - largura;
								Z = posZ;
							}
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posX + largura > 0 && posX + largura < face.getLargura())
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
								if (posY + largura > 0 && posY + largura < face.getComprimento())
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
								Y = face.getLargura() - deslocamento - largura;
								Z = posZ;
							}
							else
							{
								novaRanhura.setEixo(Degrau.VERTICAL);
								X = face.getComprimento() - deslocamento - largura;
								Y = 0;
								Z = posZ;
							}
							if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
							{
								comprimento = this.face.getComprimento();
								if (posY + largura > 0 && posY + largura < face.getLargura())
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
								if (posX + largura > 0 && posX + largura < face.getComprimento())
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
								X = face.getComprimento() - deslocamento - largura;
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
								if (posX + largura > 0 && posX + largura < face.getLargura())
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
								if (posY + largura > 0 && posY + largura < face.getComprimento())
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
						novaRanhura.setLargura(largura);
						novaRanhura.setProfundidade(profundidade);
						novaRanhura.setPosicao(X, Y, Z);
						novaRanhura.setNome(this.textFieldNome.getText());
						novaRanhura.setRugosidade(rugosidade*0.001);
						novaRanhura.setTolerancia(rugosidade*0.001);
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
							Axis2Placement3D sweptShapePosition = new Axis2Placement3D(sweptLocation, sweptAxis, sweptRefDirection); 
								
							position.setName(novaRanhura.getNome() + " placement");
							novaRanhura.setPosition(position);	
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
							this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Ranhura retangular: " + novaRanhura.getNome() + " adicionada com sucesso!");
							
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