package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.Bezier_1;
import br.UFSC.GRIMA.cad.visual.PerfilBezierFrame;
import br.UFSC.GRIMA.cad.visual.RanhuraPerfilGenericoFrame;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CriarRanhuraPerfilBezier extends RanhuraPerfilGenericoFrame implements ActionListener 
{
	public Face face = null;
	public int eixo = 1;
	public JanelaPrincipal parent;
	private int numeroPontosControle;
	private Point3d[] arrayPontosDeControle;
	private double largura;
	private RanhuraPerfilBezier novaRanhura = new RanhuraPerfilBezier();
	
	public CriarRanhuraPerfilBezier(JanelaPrincipal parent, Face face) {
		super(parent);
		this.setPreferredSize(new Dimension(760, 530));
		this.face = face;
		this.parent = parent;
		// this.parent.setEnabled(false);
		this.init();
	}
	public void init() 
	{
		this.adjustSize();
		this.adjustPosition();
		label29.setVisible(false);
		label31.setVisible(false);
		label32.setVisible(false);
		spinner14.setVisible(false);
		spinner15.setVisible(false);
		label30.setVisible(false);
		label33.setVisible(false);
		label34.setVisible(false);
		spinner16.setVisible(false);
		spinner17.setVisible(false);
		label35.setVisible(false);
		label36.setVisible(false);
		label37.setVisible(false);
		spinner18.setVisible(false);
		spinner19.setVisible(false);
		label38.setVisible(false);
		label39.setVisible(false);
		label40.setVisible(false);
		spinner20.setVisible(false);
		spinner21.setVisible(false);
		this.label10.setText(this.face.getTipoString());
		this.numeroPontosControle = ((Integer)spinner13.getValue()).intValue();
		URL ranhuraVerticalModelo = getClass().getResource("/images/ranhuraVerticalBezierModelo.gif");
		this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));

		super.cancelButton.addActionListener(this);
		super.okButton.addActionListener(this);
		super.radioButtonX.addActionListener(this);
		super.radioButtonY.addActionListener(this);
		super.checkBox1.addActionListener(this);
		super.button1.addActionListener(this);
		if (this.checkBox1.isSelected())
		{
			if (face.verticeAtivado == 0)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
					if(numeroPontosControle==3)
					{
						spinner1.setModel(new SpinnerNumberModel(0, null, null, 1.0));
						spinner3.setValue(novaRanhura.getLargura()/2);
						spinner5.setValue(novaRanhura.getLargura());
						System.out.println("passou???");
					}
				} else
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
				}
			} else if (face.verticeAtivado == 1)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
				} else
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
				}
			} else if (face.verticeAtivado == 2)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
				} else
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
				}
			} else if (face.verticeAtivado == 3)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
				} else
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
				}
			}
		}
		
		super.spinner13.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent arg0)
			{
				numeroPontosControle = ((Integer)spinner13.getValue()).intValue();
				if(numeroPontosControle==3){
					label17.setVisible(false);
					label18.setVisible(false);
					label19.setVisible(false);
					spinner7.setVisible(false);
					spinner8.setVisible(false);
					label20.setVisible(false);
					label21.setVisible(false);
					label22.setVisible(false);
					spinner9.setVisible(false);
					spinner10.setVisible(false);
					label23.setVisible(false);
					label24.setVisible(false);
					label25.setVisible(false);
					spinner11.setVisible(false);
					spinner12.setVisible(false);
					label29.setVisible(false);
					label31.setVisible(false);
					label32.setVisible(false);
					spinner14.setVisible(false);
					spinner15.setVisible(false);
					label30.setVisible(false);
					label33.setVisible(false);
					label34.setVisible(false);
					spinner16.setVisible(false);
					spinner17.setVisible(false);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==4){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(false);
					label21.setVisible(false);
					label22.setVisible(false);
					spinner9.setVisible(false);
					spinner10.setVisible(false);
					label23.setVisible(false);
					label24.setVisible(false);
					label25.setVisible(false);
					spinner11.setVisible(false);
					spinner12.setVisible(false);
					label29.setVisible(false);
					label31.setVisible(false);
					label32.setVisible(false);
					spinner14.setVisible(false);
					spinner15.setVisible(false);
					label30.setVisible(false);
					label33.setVisible(false);
					label34.setVisible(false);
					spinner16.setVisible(false);
					spinner17.setVisible(false);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==5){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(false);
					label24.setVisible(false);
					label25.setVisible(false);
					spinner11.setVisible(false);
					spinner12.setVisible(false);
					label29.setVisible(false);
					label31.setVisible(false);
					label32.setVisible(false);
					spinner14.setVisible(false);
					spinner15.setVisible(false);
					label30.setVisible(false);
					label33.setVisible(false);
					label34.setVisible(false);
					spinner16.setVisible(false);
					spinner17.setVisible(false);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==6){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(true);
					label24.setVisible(true);
					label25.setVisible(true);
					spinner11.setVisible(true);
					spinner12.setVisible(true);
					label29.setVisible(false);
					label31.setVisible(false);
					label32.setVisible(false);
					spinner14.setVisible(false);
					spinner15.setVisible(false);
					label30.setVisible(false);
					label33.setVisible(false);
					label34.setVisible(false);
					spinner16.setVisible(false);
					spinner17.setVisible(false);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==7){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(true);
					label24.setVisible(true);
					label25.setVisible(true);
					spinner11.setVisible(true);
					spinner12.setVisible(true);
					label29.setVisible(true);
					label31.setVisible(true);
					label32.setVisible(true);
					spinner14.setVisible(true);
					spinner15.setVisible(true);
					label30.setVisible(false);
					label33.setVisible(false);
					label34.setVisible(false);
					spinner16.setVisible(false);
					spinner17.setVisible(false);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==8){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(true);
					label24.setVisible(true);
					label25.setVisible(true);
					spinner11.setVisible(true);
					spinner12.setVisible(true);
					label29.setVisible(true);
					label31.setVisible(true);
					label32.setVisible(true);
					spinner14.setVisible(true);
					spinner15.setVisible(true);
					label30.setVisible(true);
					label33.setVisible(true);
					label34.setVisible(true);
					spinner16.setVisible(true);
					spinner17.setVisible(true);
					label35.setVisible(false);
					label36.setVisible(false);
					label37.setVisible(false);
					spinner18.setVisible(false);
					spinner19.setVisible(false);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==9){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(true);
					label24.setVisible(true);
					label25.setVisible(true);
					spinner11.setVisible(true);
					spinner12.setVisible(true);
					label29.setVisible(true);
					label31.setVisible(true);
					label32.setVisible(true);
					spinner14.setVisible(true);
					spinner15.setVisible(true);
					label30.setVisible(true);
					label33.setVisible(true);
					label34.setVisible(true);
					spinner16.setVisible(true);
					spinner17.setVisible(true);
					label35.setVisible(true);
					label36.setVisible(true);
					label37.setVisible(true);
					spinner18.setVisible(true);
					spinner19.setVisible(true);
					label38.setVisible(false);
					label39.setVisible(false);
					label40.setVisible(false);
					spinner20.setVisible(false);
					spinner21.setVisible(false);
				}
				else if(numeroPontosControle==10){
					label17.setVisible(true);
					label18.setVisible(true);
					label19.setVisible(true);
					spinner7.setVisible(true);
					spinner8.setVisible(true);
					label20.setVisible(true);
					label21.setVisible(true);
					label22.setVisible(true);
					spinner9.setVisible(true);
					spinner10.setVisible(true);
					label23.setVisible(true);
					label24.setVisible(true);
					label25.setVisible(true);
					spinner11.setVisible(true);
					spinner12.setVisible(true);
					label29.setVisible(true);
					label31.setVisible(true);
					label32.setVisible(true);
					spinner14.setVisible(true);
					spinner15.setVisible(true);
					label30.setVisible(true);
					label33.setVisible(true);
					label34.setVisible(true);
					spinner16.setVisible(true);
					spinner17.setVisible(true);
					label35.setVisible(true);
					label36.setVisible(true);
					label37.setVisible(true);
					spinner18.setVisible(true);
					spinner19.setVisible(true);
					label38.setVisible(true);
					label39.setVisible(true);
					label40.setVisible(true);
					spinner20.setVisible(true);
					spinner21.setVisible(true);
				}
					
			}
		});
		super.spinner1.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				//desenhar o perfil
			}
		});
		super.spinner2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				
			}
		});
		super.spinner3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				
			}
		});
		super.spinner4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				
			}
		});
		this.setVisible(true);
	}
	public void adjustSize() 
	{
		this.pack();
	}
	public void adjustPosition()
	{
		Toolkit toolkit = this.getToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();
		int posX = (int) (screenSize.getWidth() - size.getWidth()) / 2;
		int posY = (int) (screenSize.getHeight() - size.getHeight()) / 2;
		this.setLocation(posX, posY);
	}
	public void actionPerformed(ActionEvent event) 
	{
		Object origem = event.getSource();
		if (origem == okButton) {
			okSelecionado();
		} else if (origem == cancelButton) {
			CancelSelecionado();
		} else if (origem == radioButtonX) {
			eixo = 0;
			URL ranhuraHorizontalModelo = getClass().getResource(
					"/images/ranhuraHorizontalBezierModelo.GIF");
			this.labelImage.setIcon(new ImageIcon(ranhuraHorizontalModelo));
		} else if (origem == radioButtonY) {
			eixo = 1;
			URL ranhuraVerticalModelo = getClass().getResource(
					"/images/ranhuraVerticalBezierModelo.GIF");
			this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));
		} else if (origem == checkBox1)
		{
			this.selected();
		} else if (origem == button1)
		{
			this.desenharPerfil();
		}
	}
	private void desenharPerfil() 
	{
//		JFrame frame = new JFrame("Perfil Bezier");
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
////		frame.setLayout(new GridLayout(1, 1));
//		frame.setLayout(new BorderLayout());
//		frame.setSize(300, 200);
//		frame.setBackground(Color.WHITE);
		

		boolean ok = true;
		largura = 0.0;
		double deslocamento = 0.0;
		double posicaoZ = 0.0;
		double posX = 0, posY = 0, posZ = 0;
		double profundidade = 0;
		double profundidadeMaxima = 0;
		Point3d [] pontosDeControleJanela = new Point3d[this.numeroPontosControle];
		/**
		 * ****** validacao dos dados introduzidos no campo "P1 X" ******
		 * 
		 */
		if (ok) 
		{
			Point3d p1 = new Point3d((Double)this.spinner1.getValue(), (Double)this.spinner2.getValue(), face.getLargura()/2);
			Point3d p2 = new Point3d((Double)this.spinner3.getValue(), (Double)this.spinner4.getValue(), face.getLargura()/2);
			Point3d p3 = new Point3d((Double)this.spinner5.getValue(), (Double)this.spinner6.getValue(), face.getLargura()/2);
			Point3d p4 = new Point3d((Double)this.spinner7.getValue(), (Double)this.spinner8.getValue(), face.getLargura()/2);
			Point3d p5 = new Point3d((Double)this.spinner9.getValue(), (Double)this.spinner10.getValue(), face.getLargura()/2);
			Point3d p6 = new Point3d((Double)this.spinner11.getValue(), (Double)this.spinner12.getValue(), face.getLargura()/2);
			Point3d p7 = new Point3d((Double)this.spinner14.getValue(), (Double)this.spinner15.getValue(), face.getLargura()/2);
			Point3d p8 = new Point3d((Double)this.spinner16.getValue(), (Double)this.spinner17.getValue(), face.getLargura()/2);
			Point3d p9 = new Point3d((Double)this.spinner18.getValue(), (Double)this.spinner19.getValue(), face.getLargura()/2);
			Point3d p10 = new Point3d((Double)this.spinner20.getValue(), (Double)this.spinner21.getValue(), face.getLargura()/2);
			
			if(numeroPontosControle==3){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
			}
			else if(numeroPontosControle==4){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
			}
			else if(numeroPontosControle==5){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
			}
			else if(numeroPontosControle==6){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
			}
			else if(numeroPontosControle==7){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
			}
			else if(numeroPontosControle==8){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
			}
			else if(numeroPontosControle==9){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
				pontosDeControleJanela[8] = p9;
			}
			else if(numeroPontosControle==10){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
				pontosDeControleJanela[8] = p9;
				pontosDeControleJanela[9] = p10;
			}
			

		}
		
		/**
		 * ****** validacao dos dados introduzidos no campo "DESLOCAMENTO"
		 * ******
		 * 
		 */
		if (ok) {
			try {
				deslocamento = ((Double) this.spinnerDelocamento.getValue())
						.doubleValue();
				if (deslocamento >= 0 ) {
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
									"Erro no DESLOCAMENTO",
									JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("catch " + deslocamento);
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
								"Erro no DESLOCAMENTO",
								JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/******
		 * validacao dos dados introduzidos no campo "POSICAO Z" ******
		 * 
		 */
		if (ok) {
			try {
				posicaoZ = ((Double) this.spinnerZ.getValue()).doubleValue();
				if (posicaoZ >= 0
						&& posicaoZ < face.getProfundidadeMaxima()) {
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Verifique que a profundidade nao ultrapasse a profundidade máxima do bloco",
									"Erro no POSIÇÃO Z(001)",
									JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Número positivo para o POSIÇÃO Z da Ranhura",
								"Erro no POSIÇÃO Z", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		
		
		if (ok) {
			switch (eixo) {
			case 0:/****** HORIZONTAL **********/
				posX = 0;
				posY = deslocamento;
				posZ = posicaoZ;
				break;

			case 1:/****** VERTICAL **********/
				posX = deslocamento;
				posY = 0;
				posZ = posicaoZ;
				break;
			default:
				break;
			}
//			novaRanhura = new RanhuraPerfilBezier();
			double x = 0, y = 0, z = 0;
			double comprimento = 0;
			largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
			this.arrayPontosDeControle = new Point3d[this.numeroPontosControle];
			switch (this.face.verticeAtivado)
			{
			case 0:
				x = posX;
				y = posY;
				z = posZ;
				novaRanhura.setEixo(this.eixo);

				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) 
				{
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (posY + largura >= 0 && posY + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limtites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (posX + largura >= 0 && posX + largura <= face.getComprimento()) 
					{
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				break;

			case 1:
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = face.getComprimento() - deslocamento - largura;
					y = 0;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = face.getLargura() - deslocamento - largura;
					z = posZ;
				}
				largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						//Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (x + largura > 0 && x + largura < face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limtites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						//Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (y + largura >= 0 && y + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				
				break;

			case 2: // falta fazer alguns ajustes
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = face.getLargura() - deslocamento - largura;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = face.getComprimento() - deslocamento - largura;
					y = 0;
					z = posZ;
				}
//				System.out.println("largura = " + largura);
//				System.out.println("deslocamento = " + deslocamento);
//				System.out.println("y = " + y);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (y + largura >= 0 && y + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
										"Erro na largura(001a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (x + largura >= 0 && x + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limites da face",
										"Erro na largura(002a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				break;
				
			case 3:
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = deslocamento;
					y = 0;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = deslocamento;
					z = posZ;
				}
				largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						//Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (y + largura >= 0 && y + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
										"Erro na largura(001a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						//Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}				
					comprimento = this.face.getLargura();
					if (y + largura >= 0 && y + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				
				break;
//
//			case 3:
//				if (eixo == 0) {
//					novaRanhura.setEixo(Ranhura.VERTICAL);
//					x = face.getComprimento() - deslocamentoDesenho - largura;
//					xN = face.getComprimento() - deslocamento;
//					y = 0;
//					yN = 0;
//					z = posZDesenho;
//					zN = zNorma;
//				} else {
//					novaRanhura.setEixo(Ranhura.HORIZONTAL);
//					x = 0;
//					xN = 0;
//					y = deslocamentoDesenho;
//					yN = deslocamento;
//					z = posZDesenho;
//					zN = zNorma;
//				}
//				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
//					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getLargura()) {
//						ok = true;
//					} else {
//						JOptionPane
//								.showMessageDialog(
//										null,
//										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
//										"Erro na largura",
//										JOptionPane.CANCEL_OPTION);
//						ok = false;
//					}
//				} else {
//					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getComprimento()) {
//						ok = true;
//					} else {
//						JOptionPane
//								.showMessageDialog(
//										null,
//										"Verifique que a largura da ranhura esteja dentro dos limites da face",
//										"Erro na largura(001)",
//										JOptionPane.CANCEL_OPTION);
//						ok = false;
//					}
//				}
//				break;
			default:
				break;
			}
			if (ok) /** PROFUNDIDADE **/
			{
				Point3d[] pontosDaCurva = (new Bezier_1(arrayPontosDeControle, 101)).meshArray;
				novaRanhura.setPontosDaCurva(pontosDaCurva);
				double maior = 0;
				double menor = 0;
				
				double zTmp = novaRanhura.getPontosDaCurva()[0].y;
				
				maior = zTmp;
			
				for (int i = 0; i < novaRanhura.getPontosDaCurva().length; i++)
				{
					System.out.println("Pontos da curva : " + novaRanhura.getPontosDaCurva()[i]);
					zTmp = novaRanhura.getPontosDaCurva()[i].y;
					if (zTmp > maior)
					{
						maior = zTmp;
					}
					
					if(zTmp < menor)
					{
						menor = zTmp;
					}
					
				}
				profundidade = (int)(-maior);
				profundidadeMaxima = -menor;
				
				System.out.println("MAIOR PONTO : " + maior);
				System.out.println("MENOR PONTO : " + menor);
			}
			if (ok) {
				novaRanhura.setLargura(largura);
				novaRanhura.setPosicao(x, y, z);
				novaRanhura.setNome(this.textFieldNome.getText());
				novaRanhura.setProfundidade(profundidade);
				novaRanhura.setProfundidadeMaxima(profundidadeMaxima);
				novaRanhura.setPontosDeControle(this.arrayPontosDeControle); // pontos de controle em coordenadas absolutas
				novaRanhura.setComprimento(comprimento);
				
			}
		}
	

//		DesenhadorBezier desenhadorBezier = new DesenhadorBezier(novaRanhura, face);

		PerfilBezierDialog frame = new PerfilBezierDialog(this);
		frame.desenhadorBezier = new DesenhadorBezier(novaRanhura, face);
		frame.scrollPane1.setViewportView(frame.desenhadorBezier);
		
		frame.setVisible(true);
//		this.desenhadorBezier = new DesenhadorBezier(novaRanhura, face);
//		this.desenhadorBezier.repaint();
//		frame.add(db);
//		frame.setVisible(true);
		
		
	}
	private void selected()
	{
//		System.out.println("Vertice Ativado: " + face.verticeAtivado);
		if (this.checkBox1.isSelected())
		{
			if (face.verticeAtivado == 0)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
					novaRanhura.setComprimento(face.getComprimento());
					novaRanhura.setEmTodaAPeca(this.checkBox1.isSelected());
					spinner1.setValue(0d);
					spinner1.setEnabled(false);
					spinnerDelocamento.setValue(0d);
					spinnerDelocamento.setEnabled(false);
					if(numeroPontosControle==3)
					{
						spinner3.setValue(novaRanhura.getLargura()/2);
						spinner5.setValue(novaRanhura.getLargura());
						spinner5.setEnabled(false);
					} else if (numeroPontosControle == 4)
					{
						spinner3.setValue(novaRanhura.getLargura()/3);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 3);
						spinner7.setValue(novaRanhura.getLargura());
						spinner7.setEnabled(false);
					} else if (numeroPontosControle == 5)
					{
						spinner3.setValue(novaRanhura.getLargura() / 4);
						spinner5.setValue(novaRanhura.getLargura() / 2);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 4);
						spinner9.setValue(novaRanhura.getLargura());
						spinner9.setEnabled(false);
					} else if (numeroPontosControle == 6)
					{
						spinner3.setValue(novaRanhura.getLargura() / 5);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 5);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 5);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 5);
						spinner11.setValue(novaRanhura.getLargura());
						spinner11.setEnabled(false);
					} else if (numeroPontosControle == 7)
					{
						spinner3.setValue(novaRanhura.getLargura() / 6);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 6);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 6);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 6);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 6);
						spinner14.setValue(novaRanhura.getLargura());
						spinner14.setEnabled(false);
					} else if (numeroPontosControle == 8)
					{
						spinner3.setValue(novaRanhura.getLargura() / 7);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 7);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 7);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 7);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 7);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 7);
						spinner16.setValue(novaRanhura.getLargura());
						spinner16.setEnabled(false);
					} else if (numeroPontosControle == 9)
					{
						spinner3.setValue(novaRanhura.getLargura() / 8);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 8);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 8);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 8);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 8);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 8);
						spinner16.setValue(novaRanhura.getLargura() * 7 / 8);
						spinner18.setValue(novaRanhura.getLargura());
						spinner18.setEnabled(false);
					} else if (numeroPontosControle == 10)
					{
						spinner3.setValue(novaRanhura.getLargura() / 9);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 9);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 9);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 9);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 9);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 9);
						spinner16.setValue(novaRanhura.getLargura() * 7 / 9);
						spinner18.setValue(novaRanhura.getLargura() * 8 / 9);
						spinner20.setValue(novaRanhura.getLargura());
						spinner20.setEnabled(false);
					}
				} else // ------------------------------------ RANHURA VERTICAL ---------------------------
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
					novaRanhura.setComprimento(face.getLargura());
					spinner1.setValue(0d);
					spinner1.setEnabled(false);
					spinnerDelocamento.setValue(0d);
					spinnerDelocamento.setEnabled(false);
					if(numeroPontosControle==3)
					{
						spinner3.setValue(novaRanhura.getLargura()/2);
						spinner5.setValue(novaRanhura.getLargura());
						spinner5.setEnabled(false);
					} else if (numeroPontosControle == 4)
					{
						spinner3.setValue(novaRanhura.getLargura()/3);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 3);
						spinner7.setValue(novaRanhura.getLargura());
						spinner7.setEnabled(false);
					} else if (numeroPontosControle == 5)
					{
						spinner3.setValue(novaRanhura.getLargura() / 4);
						spinner5.setValue(novaRanhura.getLargura() / 2);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 4);
						spinner9.setValue(novaRanhura.getLargura());
						spinner9.setEnabled(false);
					} else if (numeroPontosControle == 6)
					{
						spinner3.setValue(novaRanhura.getLargura() / 5);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 5);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 5);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 5);
						spinner11.setValue(novaRanhura.getLargura());
						spinner11.setEnabled(false);
					} else if (numeroPontosControle == 7)
					{
						spinner3.setValue(novaRanhura.getLargura() / 6);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 6);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 6);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 6);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 6);
						spinner14.setValue(novaRanhura.getLargura());
						spinner14.setEnabled(false);
					} else if (numeroPontosControle == 8)
					{
						spinner3.setValue(novaRanhura.getLargura() / 7);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 7);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 7);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 7);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 7);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 7);
						spinner16.setValue(novaRanhura.getLargura());
						spinner16.setEnabled(false);
					} else if (numeroPontosControle == 9)
					{
						spinner3.setValue(novaRanhura.getLargura() / 8);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 8);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 8);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 8);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 8);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 8);
						spinner16.setValue(novaRanhura.getLargura() * 7 / 8);
						spinner18.setValue(novaRanhura.getLargura());
						spinner18.setEnabled(false);
					} else if (numeroPontosControle == 10)
					{
						spinner3.setValue(novaRanhura.getLargura() / 9);
						spinner5.setValue(novaRanhura.getLargura() * 2 / 9);
						spinner7.setValue(novaRanhura.getLargura() * 3 / 9);
						spinner9.setValue(novaRanhura.getLargura() * 4 / 9);
						spinner11.setValue(novaRanhura.getLargura() * 5 / 9);
						spinner14.setValue(novaRanhura.getLargura() * 6 / 9);
						spinner16.setValue(novaRanhura.getLargura() * 7 / 9);
						spinner18.setValue(novaRanhura.getLargura() * 8 / 9);
						spinner20.setValue(novaRanhura.getLargura());
						spinner20.setEnabled(false);
					}
				}
			} else if (face.verticeAtivado == 1)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
					novaRanhura.setComprimento(face.getLargura());
					spinner1.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));
					spinner3.setValue(novaRanhura.getComprimento()/2);
					spinner5.setValue(novaRanhura.getComprimento());
				} else
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
					novaRanhura.setComprimento(face.getComprimento());
				}
			} else if (face.verticeAtivado == 2)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
					novaRanhura.setComprimento(face.getComprimento());
				} else
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
					novaRanhura.setComprimento(face.getLargura());
				}
			} else if (face.verticeAtivado == 3)
			{
				if (radioButtonX.isSelected())
				{
					novaRanhura.setEixo(Ranhura.VERTICAL);
					novaRanhura.setLargura(face.getComprimento());
					novaRanhura.setComprimento(face.getLargura());
				} else
				{
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					novaRanhura.setLargura(face.getLargura());
					novaRanhura.setComprimento(face.getComprimento());
				}
			}
		} else if(checkBox1.isSelected() ==  false) // -------------nao selecionado
		{
			novaRanhura.setEmTodaAPeca(this.checkBox1.isSelected());
			if(numeroPontosControle==3)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
			} else if (numeroPontosControle == 4)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
				spinner7.setEnabled(true);
			} else if (numeroPontosControle == 5)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
				spinner7.setEnabled(true);
				spinner9.setEnabled(true);
			} else if (numeroPontosControle == 6)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
				spinner7.setEnabled(true);
				spinner9.setEnabled(true);
				spinner11.setEnabled(true);
			} else if (numeroPontosControle == 7)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
				spinner7.setEnabled(true);
				spinner9.setEnabled(true);
				spinner11.setEnabled(true);
				spinner14.setEnabled(true);
			} else if (numeroPontosControle == 8)
			{
				spinner1.setEnabled(true);
				spinner5.setEnabled(true);
				spinner7.setEnabled(true);
				spinner9.setEnabled(true);
				spinner11.setEnabled(true);
				spinner14.setEnabled(true);
				spinner16.setEnabled(true);
			} 
		}
	}
	private void CancelSelecionado()
	{
		this.parent.setEnabled(true);
		dispose();
	}
	private void okSelecionado() 
	{
		boolean ok = true;
		largura = 0.0;
		double deslocamento = 0.0;
		double posicaoZ = 0.0;
		double posX = 0, posY = 0, posZ = 0;
		double rugosidade = 0;
		double profundidade = 0;
		double profundidadeMaxima = 0;
		Point3d [] pontosDeControleJanela = new Point3d[this.numeroPontosControle];
		/**
		 * ****** validacao dos dados introduzidos no campo "P1 X" ******
		 * 
		 */
		if (ok) 
		{
			Point3d p1 = new Point3d((Double)this.spinner1.getValue(), (Double)this.spinner2.getValue(), face.getLargura()/2);
			Point3d p2 = new Point3d((Double)this.spinner3.getValue(), (Double)this.spinner4.getValue(), face.getLargura()/2);
			Point3d p3 = new Point3d((Double)this.spinner5.getValue(), (Double)this.spinner6.getValue(), face.getLargura()/2);
			Point3d p4 = new Point3d((Double)this.spinner7.getValue(), (Double)this.spinner8.getValue(), face.getLargura()/2);
			Point3d p5 = new Point3d((Double)this.spinner9.getValue(), (Double)this.spinner10.getValue(), face.getLargura()/2);
			Point3d p6 = new Point3d((Double)this.spinner11.getValue(), (Double)this.spinner12.getValue(), face.getLargura()/2);
			Point3d p7 = new Point3d((Double)this.spinner14.getValue(), (Double)this.spinner15.getValue(), face.getLargura()/2);
			Point3d p8 = new Point3d((Double)this.spinner16.getValue(), (Double)this.spinner17.getValue(), face.getLargura()/2);
			Point3d p9 = new Point3d((Double)this.spinner18.getValue(), (Double)this.spinner19.getValue(), face.getLargura()/2);
			Point3d p10 = new Point3d((Double)this.spinner20.getValue(), (Double)this.spinner21.getValue(), face.getLargura()/2);
			
			if(numeroPontosControle==3){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
			}
			else if(numeroPontosControle==4){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
			}
			else if(numeroPontosControle==5){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
			}
			else if(numeroPontosControle==6){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
			}
			else if(numeroPontosControle==7){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
			}
			else if(numeroPontosControle==8){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
			}
			else if(numeroPontosControle==9){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
				pontosDeControleJanela[8] = p9;
			}
			else if(numeroPontosControle==10){
				pontosDeControleJanela[0] = p1;
				pontosDeControleJanela[1] = p2;
				pontosDeControleJanela[2] = p3;
				pontosDeControleJanela[3] = p4;
				pontosDeControleJanela[4] = p5;
				pontosDeControleJanela[5] = p6;
				pontosDeControleJanela[6] = p7;
				pontosDeControleJanela[7] = p8;
				pontosDeControleJanela[8] = p9;
				pontosDeControleJanela[9] = p10;
			}
			

		}
		
		/**
		 * ****** validacao dos dados introduzidos no campo "DESLOCAMENTO"
		 * ******
		 * 
		 */
		if (ok) {
			try {
				deslocamento = ((Double) this.spinnerDelocamento.getValue())
						.doubleValue();
				if (deslocamento >= 0 ) {
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
									"Erro no DESLOCAMENTO",
									JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("catch " + deslocamento);
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Número positivo para o DESLOCAMENTO da Ranhura",
								"Erro no DESLOCAMENTO",
								JOptionPane.CANCEL_OPTION);
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
		/******
		 * validacao dos dados introduzidos no campo "POSICAO Z" ******
		 * 
		 */
		if (ok) {
			try {
				posicaoZ = ((Double) this.spinnerZ.getValue()).doubleValue();
				if (posicaoZ >= 0
						&& posicaoZ < face.getProfundidadeMaxima()) {
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Verifique que a profundidade nao ultrapasse a profundidade máxima do bloco",
									"Erro no POSIÇÃO Z(001)",
									JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Número positivo para o POSIÇÃO Z da Ranhura",
								"Erro no POSIÇÃO Z", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		
		
		if (ok) {
			switch (eixo) {
			case 0:/****** HORIZONTAL **********/
				posX = 0;
				posY = deslocamento;
				posZ = posicaoZ;
				break;

			case 1:/****** VERTICAL **********/
				posX = deslocamento;
				posY = 0;
				posZ = posicaoZ;
				break;
			default:
				break;
			}
//			novaRanhura = new RanhuraPerfilBezier();
			double x = 0, y = 0, z = 0;
			double comprimento = 0;
			largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
			this.arrayPontosDeControle = new Point3d[this.numeroPontosControle];
			switch (this.face.verticeAtivado)
			{
			case 0:
				x = posX;
				y = posY;
				z = posZ;
				novaRanhura.setEixo(this.eixo);

				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) 
				{
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (posY + largura >= 0 && posY + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limtites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (posX + largura >= 0 && posX + largura <= face.getComprimento()) 
					{
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				break;

			case 1:
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = face.getComprimento() - deslocamento - largura;
					y = 0;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = face.getLargura() - deslocamento - largura;
					z = posZ;
				}
				largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						//Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (x + largura > 0 && x + largura < face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limtites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						//Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (y + largura >= 0 && y + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				
				break;

			case 2: // falta fazer alguns ajustes
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = face.getLargura() - deslocamento - largura;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = face.getComprimento() - deslocamento - largura;
					y = 0;
					z = posZ;
				}
//				System.out.println("largura = " + largura);
//				System.out.println("deslocamento = " + deslocamento);
//				System.out.println("y = " + y);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (y + largura >= 0 && y + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
										"Erro na largura(001a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getLargura();
					if (x + largura >= 0 && x + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limites da face",
										"Erro na largura(002a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				break;
				
			case 3:
				if (eixo == 0) {
					novaRanhura.setEixo(Ranhura.VERTICAL);
					x = deslocamento;
					y = 0;
					z = posZ;
				} else {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					x = 0;
					y = deslocamento;
					z = posZ;
				}
				largura = Math.abs(pontosDeControleJanela[0].x -pontosDeControleJanela[pontosDeControleJanela.length - 1].x);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						//Point3d pontoTmp = new Point3d(this.face.getLargura() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getComprimento()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}
					comprimento = this.face.getComprimento();
					if (y + largura >= 0 && y + largura <= face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
										"Erro na largura(001a)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					for(int i = 0; i < pontosDeControleJanela.length; i++)
					{
						Point3d pontoTmp = new Point3d(deslocamento + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						//Point3d pontoTmp = new Point3d(this.face.getComprimento() - deslocamento - largura + pontosDeControleJanela[i].x, - posZ + pontosDeControleJanela[i].y, face.getLargura()/2);
						this.arrayPontosDeControle[i] = pontoTmp;
					}				
					comprimento = this.face.getLargura();
					if (y + largura >= 0 && y + largura <= face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a ranhura esteja dentro dos limites da face",
										"Erro",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				
				break;
//
//			case 3:
//				if (eixo == 0) {
//					novaRanhura.setEixo(Ranhura.VERTICAL);
//					x = face.getComprimento() - deslocamentoDesenho - largura;
//					xN = face.getComprimento() - deslocamento;
//					y = 0;
//					yN = 0;
//					z = posZDesenho;
//					zN = zNorma;
//				} else {
//					novaRanhura.setEixo(Ranhura.HORIZONTAL);
//					x = 0;
//					xN = 0;
//					y = deslocamentoDesenho;
//					yN = deslocamento;
//					z = posZDesenho;
//					zN = zNorma;
//				}
//				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
//					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getLargura()) {
//						ok = true;
//					} else {
//						JOptionPane
//								.showMessageDialog(
//										null,
//										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
//										"Erro na largura",
//										JOptionPane.CANCEL_OPTION);
//						ok = false;
//					}
//				} else {
//					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getComprimento()) {
//						ok = true;
//					} else {
//						JOptionPane
//								.showMessageDialog(
//										null,
//										"Verifique que a largura da ranhura esteja dentro dos limites da face",
//										"Erro na largura(001)",
//										JOptionPane.CANCEL_OPTION);
//						ok = false;
//					}
//				}
//				break;
			default:
				break;
			}
			if (ok) /** PROFUNDIDADE **/
			{
				Point3d[] pontosDaCurva = (new Bezier_1(arrayPontosDeControle, 101)).meshArray;
				novaRanhura.setPontosDaCurva(pontosDaCurva);
				double maior = 0;
				double menor = 0;
				
				double zTmp = novaRanhura.getPontosDaCurva()[0].y;
				
				maior = zTmp;
			
				for (int i = 0; i < novaRanhura.getPontosDaCurva().length; i++)
				{
					System.out.println("Pontos da curva : " + novaRanhura.getPontosDaCurva()[i]);
					zTmp = novaRanhura.getPontosDaCurva()[i].y;
					if (zTmp > maior)
					{
						maior = zTmp;
					}
					
					if(zTmp < menor)
					{
						menor = zTmp;
					}
					
				}
				profundidade = (int)(-maior);
				profundidadeMaxima = -menor;
				
				System.out.println("MAIOR PONTO : " + maior);
				System.out.println("MENOR PONTO : " + menor);
			}
			if (ok) {
				novaRanhura.setLargura(largura);
				novaRanhura.setPosicao(x, y, z);
				novaRanhura.setNome(this.textFieldNome.getText());
				novaRanhura.setRugosidade(rugosidade*0.001);
				novaRanhura.setTolerancia(rugosidade*0.001);
				novaRanhura.setProfundidade(profundidade);
				novaRanhura.setProfundidadeMaxima(profundidadeMaxima);
				novaRanhura.setPontosDeControle(this.arrayPontosDeControle); // pontos de controle em coordenadas absolutas
				novaRanhura.setComprimento(comprimento);
				
				
				for (int i = 0; i < pontosDeControleJanela.length; i++)
				{
					System.out.println("pontos de controle = " + this.arrayPontosDeControle[i]);
				}
				for (int i = 0; i < pontosDeControleJanela.length; i++)
				{
					System.out.println("spinner = " + pontosDeControleJanela[i].x + "," + pontosDeControleJanela[i].y);
				}
				
				if (rugosidade <= Feature.LIMITE_RUGOSIDADE)
					novaRanhura.setAcabamento(true);

				if (this.face.validarFeature(novaRanhura)) {
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(x, y, this.face.getProfundidadeMaxima() - z);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(0.0);
							axis.add(1.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(x, y, this.face.getProfundidadeMaxima() - z);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(0.0);
							axis.add(1.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
						}
					} else if (this.face.getTipo() == Face.XZ)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(x, z, y);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(-1.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(x, z, y);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(-1.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
						}
						
					} else if (this.face.getTipo() == Face.YX)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(x, y, z);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(0.0);
							axis.add(-1.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(-1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(x, y, z);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(0.0);
							axis.add(-1.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(-1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
						}
						
					} else if (this.face.getTipo() == Face.YZ)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(this.face.getProfundidadeMaxima() - z, x, y);
							axis = new ArrayList<Double>();
							axis.add(1.0);
							axis.add(0.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(0.0);
							refDirection.add(1.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(this.face.getProfundidadeMaxima() - z, x, y);
							axis = new ArrayList<Double>();
							axis.add(1.0);
							axis.add(0.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(0.0);
							refDirection.add(1.0);
							refDirection.add(0.0);
						}
						
					} else if (this.face.getTipo() == Face.ZX)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(x,- z, y);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(1.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(-1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(x, - z, y);
							axis = new ArrayList<Double>();
							axis.add(0.0);
							axis.add(1.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(-1.0);
							refDirection.add(0.0);
							refDirection.add(0.0);
						}
						
					} else if (this.face.getTipo() == Face.ZY)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							coordinates = new Point3d(z, x, y);
							axis = new ArrayList<Double>();
							axis.add(-1.0);
							axis.add(0.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(0.0);
							refDirection.add(-1.0);
							refDirection.add(0.0);
							
						} else if (novaRanhura.getEixo() == Ranhura.VERTICAL)
						{
							coordinates = new Point3d(z, x, y);
							axis = new ArrayList<Double>();
							axis.add(-1.0);
							axis.add(0.0);
							axis.add(0.0);
							
							refDirection = new ArrayList<Double>();
							refDirection.add(0.0);
							refDirection.add(-1.0);
							refDirection.add(0.0);
						}
						
					}
					Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
					position.setName(novaRanhura.getNome() + " placement");
					novaRanhura.setPosition(position);	
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

					this.parent.setEnabled(true);
					this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Ranhura Perfil Genérico: " + novaRanhura.getNome() + " adicionada com sucesso!");
					
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Erro na criação da Ranhura", "Feature inválida",
							JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}
	}
}
