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

import br.UFSC.GRIMA.cad.visual.RanhuraPerfilVeeFrame;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CriarRanhuraPerfilVee extends RanhuraPerfilVeeFrame implements ActionListener {
	public Face face = null;
	public int eixo = 0;
	public JanelaPrincipal parent;

	public CriarRanhuraPerfilVee(JanelaPrincipal parent, Face face) {
		super(parent);

		this.face = face;
		this.parent = parent;
		// this.parent.setEnabled(false);
		this.init();
	}
	public void init() 
	{
		this.adjustSize();
		this.adjustPosition();
		this.label10.setText(this.face.getTipoString());
		super.cancelButton.addActionListener(this);
		super.okButton.addActionListener(this);
		super.radioButtonX.addActionListener(this);
		super.radioButtonY.addActionListener(this);
		super.radioButton1.addActionListener(this);
		super.radioButton2.addActionListener(this);
		double profundidade = ((Double)spinner3.getValue()).doubleValue();
		double raio = ((Double)spinner4.getValue()).doubleValue();
		double angulo = ((Double)spinner1.getValue()).doubleValue();
		double largura = 2 * profundidade / Math.tan((90 - angulo / 2) * Math.PI / 180) - 2 * raio * (1 - 1 / Math.cos((90 - angulo / 2) *  Math.PI / 180) / Math.tan((90 - angulo / 2) * Math.PI / 180));
		spinner2.setModel(new SpinnerNumberModel(largura, null, null, 1.0));
		
		super.spinner1.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				double profundidade = ((Double)spinner3.getValue()).doubleValue();
				double raio = ((Double)spinner4.getValue()).doubleValue();
				double angulo = ((Double)spinner1.getValue()).doubleValue();
				
				double largura = 2 * profundidade / Math.tan((90 - angulo / 2) * Math.PI / 180) - 2 * raio * (1 - 1 / Math.cos((90 - angulo / 2) *  Math.PI / 180) / Math.tan((90 - angulo / 2) * Math.PI / 180));
				spinner2.setModel(new SpinnerNumberModel(largura, null, null, 1.0));
			}
		});
		super.spinner2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				double largura = ((Double)spinner3.getValue()).doubleValue();
				double raio = ((Double)spinner4.getValue()).doubleValue();
				double profundidade = ((Double)spinner1.getValue()).doubleValue();
				// calcular o angulo em funcao dos outros parâmetros e settar no spinner angulo
				//double angulo = 180*2*Math.atan(largura/(2*profundidade))/Math.PI;
				double angulo = (Math.asin((4*raio*raio - largura*largura - 4*raio*largura - 4*profundidade*profundidade)/Math.sqrt(16*profundidade*profundidade + (largura*largura+4*raio*largura+4*raio)*(largura*largura+4*raio*largura+4*raio))) - Math.atan((largura*largura+4*raio*largura+4*raio)/(4*profundidade)))*180/Math.PI;
				spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
			}
		});
		super.spinner3.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				
				double profundidade = ((Double)spinner3.getValue()).doubleValue();
				double raio = ((Double)spinner4.getValue()).doubleValue();
				if(radioButton1.isSelected()){
					double angulo = ((Double)spinner1.getValue()).doubleValue();

					double largura = 2 * profundidade / Math.tan((90 - angulo / 2) * Math.PI / 180) - 2 * raio * (1 - 1 / Math.cos((90 - angulo / 2) *  Math.PI / 180) / Math.tan((90 - angulo / 2) * Math.PI / 180));
					spinner2.setModel(new SpinnerNumberModel(largura, null, null, 1.0));
				}
				else if(radioButton2.isSelected()){
					double largura = ((Double)spinner3.getValue()).doubleValue();
					double angulo = 180*2*Math.atan(largura/(2*profundidade))/Math.PI;
					spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
				}
			}
		});
		super.spinner4.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				
				double profundidade = ((Double)spinner3.getValue()).doubleValue();
				double raio = ((Double)spinner4.getValue()).doubleValue();
				if(radioButton1.isSelected()){
					double angulo = ((Double)spinner1.getValue()).doubleValue();

					double largura = 2 * profundidade / Math.tan((90 - angulo / 2) * Math.PI / 180) - 2 * raio * (1 - 1 / Math.cos((90 - angulo / 2) *  Math.PI / 180) / Math.tan((90 - angulo / 2) * Math.PI / 180));
					spinner2.setModel(new SpinnerNumberModel(largura, null, null, 1.0));
				}
				else if(radioButton2.isSelected()){
					double largura = ((Double)spinner3.getValue()).doubleValue();
					double angulo = 180*2*Math.atan(largura/(2*profundidade))/Math.PI;
					spinner1.setModel(new SpinnerNumberModel(angulo, null, null, 1.0));
				}
			}
		});
		this.setVisible(true);
	}
	public void adjustSize() 
	{
		this.pack();
	}
	// centraliza a janela no desktop do usuario
	public void adjustPosition() {
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
			URL ranhuraHorizontalModelo = getClass().getResource("/images/RanhuraPerfilVHorizontal.png");
			this.spinnerDelocamento.setBounds(135, 155, 40, this.spinnerDelocamento.getPreferredSize().height);

			this.labelImage.setIcon(new ImageIcon(ranhuraHorizontalModelo));
		} else if (origem == radioButtonY) {
			eixo = 1;
			URL ranhuraVerticalModelo = getClass().getResource("/images/RanhuraPerfilVVertical.png");
			spinnerDelocamento.setBounds(100, 105, 40, spinnerDelocamento.getPreferredSize().height);

			this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));
		} else if (origem == radioButton1)
		{
			this.spinner1.setEnabled(true);
			this.spinner2.setEnabled(false);
		} else if (origem == radioButton2)
		{
			this.spinner1.setEnabled(false);
			this.spinner2.setEnabled(true);
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
		double largura = 0.0;
		double deslocamentoNorma = 0.0, deslocamentoDesenho = 0;
		double posicaoZ = 0.0;
		double posXDesenho = 0, posYDesenho = 0, posZDesenho = 0, xNorma = 0, yNorma = 0, zNorma = 0;
		double diametroFresa = new Ferramenta().getDiametroFerramenta();
		double rugosidade = 0;
		double profundidade = 0;
		double angulo = 0;
		double raio = 0;
		/**
		 * ****** validacao dos dados introduzidos no campo "LARGURA" ******
		 * 
		 */
		if (ok) {
			try {
				largura = ((Double) this.spinner2.getValue()).doubleValue();
				if (largura > 0 && largura >= diametroFresa) {

					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a LARGURA da ranhura seja maior que zero", "Erro na largura",
							JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a LARGURA  da Ranhura",
						"Erro", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * validacao do spinner RAIO
		 */
		if (ok)
		{
			try {
				raio = ((Double) this.spinner4.getValue()).doubleValue();
				angulo = ((Double) this.spinner1.getValue()).doubleValue();
				largura = ((Double) this.spinner2.getValue()).doubleValue();
				double R = largura/(2*Math.cos(angulo/2*Math.PI/180));
				if (raio > 0 && raio < R) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número maior que 0 e menor que o maior raio possivel (" + R + "mm)",
							"Erro no raio", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para o RAIO da Ranhura",
						"Erro no RAIO", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
			// validar!!!!
		}
		/**
		 * validacao do spinner ANGULO
		 */
		if (ok)
		{
			try {
				angulo = ((Double) this.spinner1.getValue()).doubleValue();

				if (angulo > 0 && angulo < 180) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número entre 0 e 180� para o ANGULO da Ranhura",
							"Erro no angulo", JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para o ANGULO da Ranhura",
						"Erro no ANGULO", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
			// validar!!!!
		}
		
		/**
		 * ****** validacao dos dados introduzidos no campo "DESLOCAMENTO"
		 * ******
		 * 
		 */
		if (ok) {
			try {
				deslocamentoNorma = ((Double) this.spinnerDelocamento.getValue())
						.doubleValue();
				if (deslocamentoNorma > 0 && deslocamentoNorma - largura / 2 > 0) {
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
		
		/**
		 * ****** validacao dos dados introduzidos no campo "PROFUNDIDADE" ******
		 * 
		 */
		if (ok) {
			try {
				profundidade = ((Double) this.spinner3.getValue()).doubleValue();
				if (profundidade > 0 && profundidade + posicaoZ < face.getProfundidadeMaxima()) {

					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a profundidade da ranhura seja maior que zero", "Erro na profundidade",
							JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a PROFUNDIDADE  da Ranhura",
						"Erro na RAIO", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		if (ok) {
			switch (eixo) {
			case 0:/****** HORIZONTAL **********/
				posXDesenho = 0;
				xNorma = 0;
				posYDesenho = deslocamentoNorma - largura/2;
				yNorma = deslocamentoNorma;
				posZDesenho = posicaoZ;
				zNorma = -posicaoZ;
				break;

			case 1:/****** VERTICAL **********/
				posXDesenho = deslocamentoNorma - largura/2;
				xNorma = deslocamentoNorma;
				posYDesenho = 0;
				yNorma = 0;
				posZDesenho = posicaoZ;
				zNorma = -posicaoZ;
				break;
			default:
				break;
			}
			RanhuraPerfilVee novaRanhura = new RanhuraPerfilVee();
			double Xdesenho = 0, Ydesenho = 0, Zdesenho = 0;
			double xN = 0, yN = 0, zN = 0;
			double comprimento = 0;
			deslocamentoDesenho = deslocamentoNorma - largura/2;
			switch (this.face.verticeAtivado) {
			case 0:

				Xdesenho = posXDesenho;
				xN = xNorma;
				Ydesenho = posYDesenho;
				yN = yNorma;
				Zdesenho = posZDesenho;
				zN = zNorma;
				novaRanhura.setEixo(this.eixo);
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					comprimento = this.face.getComprimento();
					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getLargura()) {
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
					comprimento = this.face.getLargura();
					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getComprimento()) {
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
					novaRanhura.setEixo(Degrau.VERTICAL);
					Xdesenho = deslocamentoDesenho;
					xN = deslocamentoNorma;
					Ydesenho = 0;
					yN = yNorma;
					Zdesenho = posZDesenho;
					zN = zNorma;
				} else {
					novaRanhura.setEixo(Degrau.HORIZONTAL);
					Xdesenho = 0;
					xN = xNorma;
					Ydesenho = face.getLargura() - deslocamentoDesenho - largura;
					yN = deslocamentoNorma;
					Zdesenho = posZDesenho;
					zN = zNorma;
				}
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					comprimento = this.face.getComprimento();
					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getLargura()) {
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
					comprimento = this.face.getLargura();
					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getComprimento()) {
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

			case 2:
				if (eixo == 0) {
					novaRanhura.setEixo(Degrau.HORIZONTAL);
					Xdesenho = 0;
					xN = 0;
					Ydesenho = face.getLargura() - deslocamentoDesenho - largura;
					yN = face.getLargura() - deslocamentoNorma;
					Zdesenho = posZDesenho;
					zN = zNorma;
				} else {
					novaRanhura.setEixo(Degrau.VERTICAL);
					Xdesenho = face.getComprimento() - deslocamentoDesenho - largura;
					xN = face.getComprimento() - deslocamentoNorma;
					Ydesenho = 0;
					yN = 0;
					Zdesenho = posZDesenho;
					zN = zNorma;
				}
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					comprimento = this.face.getComprimento();
					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getLargura()) {
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
					comprimento = this.face.getLargura();
					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getComprimento()) {
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
					Xdesenho = face.getComprimento() - deslocamentoDesenho - largura;
					xN = face.getComprimento() - deslocamentoNorma;
					Ydesenho = 0;
					yN = 0;
					Zdesenho = posZDesenho;
					zN = zNorma;
				} else {
					novaRanhura.setEixo(Ranhura.HORIZONTAL);
					Xdesenho = 0;
					xN = 0;
					Ydesenho = deslocamentoDesenho;
					yN = deslocamentoNorma;
					Zdesenho = posZDesenho;
					zN = zNorma;
				}
				if (novaRanhura.getEixo() == Ranhura.HORIZONTAL) {
					comprimento = this.face.getComprimento();
					if (posXDesenho + largura > 0 && posXDesenho + largura < face.getLargura()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limtites da face",
										"Erro na largura",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				} else {
					comprimento = this.face.getLargura();
					if (posYDesenho + largura > 0 && posYDesenho + largura < face.getComprimento()) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que a largura da ranhura esteja dentro dos limites da face",
										"Erro na largura(001)",
										JOptionPane.CANCEL_OPTION);
						ok = false;
					}
				}
				break;
			default:
				break;
			}
			if (ok) {
				novaRanhura.setLargura(largura);
				novaRanhura.setPosicao(Xdesenho, Ydesenho, Zdesenho);
				novaRanhura.setNome(this.textFieldNome.getText());
				novaRanhura.setRugosidade(rugosidade*0.001);
				novaRanhura.setTolerancia(rugosidade*0.001);
				novaRanhura.setPosicaoNorma(xN, yN, zN);
				novaRanhura.setProfundidade(profundidade);
				novaRanhura.setDeslocamentoNorma(deslocamentoNorma);
				novaRanhura.setAngulo(angulo);
				novaRanhura.setRaio(raio);
				novaRanhura.setComprimento(comprimento);
				
				if (rugosidade <= Feature.LIMITE_RUGOSIDADE)
					novaRanhura.setAcabamento(true);

				if (this.face.validarFeature(novaRanhura)) {
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY)
					{
						if (novaRanhura.getEixo() == Ranhura.HORIZONTAL)
						{
							/*coordinates = new Point3d(novaRanhura.X, novaRanhura.getLocY(), this.face.getProfundidadeMaxima() - novaRanhura.Z);*/
							coordinates = new Point3d(xN, yN, this.face.getProfundidadeMaxima() - zN);
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
							/*coordinates = new Point3d(novaRanhura.getLocX(), novaRanhura.Y, this.face.getProfundidadeMaxima() - novaRanhura.Z);*/
							coordinates = new Point3d(xN, yN, this.face.getProfundidadeMaxima() - zN);
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
							coordinates = new Point3d(xN, zN, yN + novaRanhura.getLargura()/2);
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
							coordinates = new Point3d(xN, zN + novaRanhura.getLargura()/2, yN);
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
							coordinates = new Point3d(xN, yN + novaRanhura.getLargura()/2, zN);
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
							coordinates = new Point3d(xN + novaRanhura.getLargura()/2, yN, zN);
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
							coordinates = new Point3d(this.face.getProfundidadeMaxima() - zN, xN, yN + novaRanhura.getLargura()/2);
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
							coordinates = new Point3d(this.face.getProfundidadeMaxima() - zN, xN + novaRanhura.getLargura()/2, yN);
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
							coordinates = new Point3d(xN, this.face.getProfundidadeMaxima() - zN, yN + novaRanhura.getLargura()/2);
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
							coordinates = new Point3d(xN + novaRanhura.getLargura()/2, this.face.getProfundidadeMaxima() - zN, yN);
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
							coordinates = new Point3d(zN, xN, yN + novaRanhura.getLargura()/2);
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
							coordinates = new Point3d(zN, xN + novaRanhura.getLargura()/2, yN);
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
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					// face.imprimeDados(novaRanhura);
					this.parent.setEnabled(true);
					this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Ranhura Perfil Vee: " + novaRanhura.getNome() + " adicionada com sucesso!");

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
