package br.UFSC.GRIMA.cad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.CreateCircularBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;


public class CriarCircularBoss extends CreateCircularBossFrame implements
		ActionListener {
	public Face face = null;
	public JanelaPrincipal parent;
	private Cavidade cavidade = null;
	public CriarCircularBoss(JanelaPrincipal parent, Face face,
			Cavidade cavidade) {
		super(parent);
		this.cavidade = cavidade;
		this.face = face;
		this.parent = parent;
		// this.parent.setEnabled(false);

		this.init();
	}

	public void init() {
		this.adjustSize();
		this.adjustPosition();

		this.label16.setText(this.face.getTipoString());

		super.okButton.addActionListener(this);
		super.cancelButton.addActionListener(this);
		// this.checkBox1.addActionListener(this);
		super.spinner7.setVisible(false);
		
		this.setVisible(true);

	}

	public void adjustSize() {
		this.pack();
	}

	// centraliza a janela no desktop do usuario
	public void adjustPosition() {
		Toolkit toolkit = this.getToolkit();

		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();

		// System.out.println(screenSize.toString());
		// System.out.println(size.toString());

		int posX = (int) (screenSize.getWidth() - size.getWidth()) / 2;
		int posY = (int) (screenSize.getHeight() - size.getHeight()) / 2;

		this.setLocation(posX, posY);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object origem = event.getSource();
		if (origem == okButton) {
			ok();
		} else if (origem == cancelButton) {
			cancel();
		}
	}


	private void ok() {
		boolean ok = true;
		double diametro1 = 0.0;
		double diametro2 = 0.0;
		double raio1 = 0.0;
		double raio2 = 0.0;
		double alturaBoss = 0.0; // em relacao a base da cavidade
		double posicaoX = 0.0;
		double posicaoY = 0.0;
		double posicaoZ = 0.0;
		double refLargura11 = 0.0; // referente a posicaoY + raio1
		double refLargura21 = 0.0; // referente a posicaoY - raio1
		double refLargura12 = 0.0; // posicaoY + raio2
		double refLargura22 = 0.0; // posicaoY - raio2
		double refComprimento11 = 0.0; // posicaoX + raio1
		double refComprimento21 = 0.0; // posicaoX - raio1
		double refComprimento12 = 0.0; // posicaoX + raio2
		double refComprimento22 = 0.0; // posicaoX - raio2
		double tolerancia = 0.0;
		double rugosidade = 0.0;

		/** Validacao da posicao X **/

		if (ok) {
			try {
				posicaoX = ((Double) this.spinner6.getValue()).doubleValue();
				if (posicaoX > 0 /* && x < Maquina.posicaoXCv */) {
					// System.out.println("deslocamento X: " + posicaoX);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um numero positivo para a posicaoo X da cavidade",
									"Erro na posicao X",
									JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um numero positivo para a posicao X da cavidade"
										+ "\n               Nao digite letras nem simbolos",
								"Erro na posicao X",
								JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}

		/** Validacao da posicao Y **/

		if (ok) {
			try {
				posicaoY = ((Double) this.spinner5.getValue()).doubleValue();
				if (posicaoY > 0 /* && x < Maquina.posicaoYCv */) {
					// System.out.println("deslocamento Y: " + posicaoY);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um Numero positivo para o deslocamento em Y",
									"Erro no deslocamento em Y",
									JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Numero positivo para o deslocamento em Y"
										+ "\n               Nao digite letras nem simbolos",
								"Erro no deslocamento em Y",
								JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}

		/** ****** validacao dos dados introduzidos no campo "Posicao Z" ********/

		if (ok) {
			try {
				posicaoZ = ((Double) this.spinner7.getValue()).doubleValue();
				if (posicaoZ >= 0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um numero positivo para a posicao Z",
							"Erro no deslocamento Z",
							JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Numero positivo para a posicao Z"
										+ "\n               Nao digite letras nem simbolos",
								"Erro no deslocamento Z?",
								JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}

		/** Validacao dos diametros **/

		if (ok) {
			try {
				diametro1 = ((Double) this.spinner1.getValue()).doubleValue();
				diametro2 = ((Double) this.spinner2.getValue()).doubleValue();
				alturaBoss = ((Double) this.spinner3.getValue()).doubleValue();
				raio1 = (diametro1) / 2;
				raio2 = (diametro2) / 2;
				refLargura11 = posicaoY + raio1;
				refLargura21 = posicaoY - raio1;
				refLargura12 = posicaoY + raio2;
				refLargura22 = posicaoY - raio2;
				refComprimento11 = posicaoX + raio1;
				refComprimento21 = posicaoX - raio1;
				refComprimento12 = posicaoX + raio2;
				refComprimento22 = posicaoX - raio2;

				switch (face.verticeAtivado) {

				case 0:

					if (raio1 > 0 && raio2 > 0
							&& refLargura11 < this.cavidade.getLargura()
							&& (refLargura21 > 0)
							&& //
							refLargura12 < cavidade.getLargura()
							&& (refLargura22 > 0)) //
					{
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
												+ "\n               (revise a L2 ou a posicao Y)",
										"Erro ao criar Cavidade",
										JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 1:

					if (raio1 > 0 && raio2 > 0
							&& refComprimento11 < cavidade.getComprimento()
							&& (refComprimento21 > 0)
							&& //
							refComprimento12 < cavidade.getComprimento()
							&& (refComprimento22 > 0)) {

						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
												+ "\n               (revise a L2 ou a posicaoo Y)",
										"Erro ao criar Cavidade",
										JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 2:

					if (raio1 > 0 && raio2 > 0
							&& refLargura11 < cavidade.getLargura()
							&& (refLargura21 > 0)
							&& //
							refLargura12 < cavidade.getLargura()
							&& (refLargura22 > 0)) {

						ok = true;

					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
												+ "\n               (revise a L2 ou a posicaoo Y)",
										"Erro ao criar Cavidade",
										JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 3:

					if (raio1 > 0 && raio2 > 0
							&& refComprimento11 < cavidade.getComprimento()
							&& (refComprimento21 > 0)
							&& refComprimento12 < cavidade.getComprimento()
							&& (refComprimento22 > 0)) {
						ok = true;
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
												+ "\n               (revise a L2 ou a posicaoo Y)",
										"Erro ao criar Cavidade",
										JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}

					break;
				default:
					break;
				}
			}

			catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Numero positivo para a largura da cavidade"
										+ "\n               Nao digite letras nem simbolos",
								"Erro na largura", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}

		/** Validacao da altura **/

		if (ok) {
			try

			{
				alturaBoss = ((Double) this.spinner3.getValue()).doubleValue();
				double refPosZ = alturaBoss + posicaoZ;
				if (alturaBoss > 0 && refPosZ < face.getProfundidadeMaxima()) {
					ok = true;
				} else if (alturaBoss > 0
						&& refPosZ == this.face.getProfundidadeMaxima()) {
					ok = true;
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Verifique que a altura do Boss nao ultrapasse a profundidade maxima da cavidade",
									"Erro na altura do Boss",
									JOptionPane.CANCEL_OPTION);
					ok = false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a altura do Boss",
						"Erro na altura do Boss", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}

		/** Validacao da Tolerancia **/

		if (ok) {
			try {
				tolerancia = ((Double) this.spinner4.getValue()).doubleValue();
				if (tolerancia >= 0.0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Numero positivo para a tolerancia",
							"Erro ", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a Tolerancia",
						"Erro na Tolerancia", JOptionPane.OK_OPTION);
			}
		}

		/** Set da Rugosidade, nao precisa de **/

		 if (ok) {
		 rugosidade = ((Double) cavidadeRug.getValue()).doubleValue();
		 }

		// CONTINUAR A PARTIR DA LINHA 409 DO CriarFuroBasePlana
		if (ok) {
			double X = 0, Y = 0, Z = 0;
			switch (face.verticeAtivado) {
			case 0:
				X = posicaoX + cavidade.getPosicaoX();
				Y = posicaoY + cavidade.getPosicaoY();
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 1:
				X = posicaoX;
				Y = (face.getLargura() - cavidade.getPosicaoY() - posicaoY);
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 2:
				X = (face.getComprimento() - cavidade.getPosicaoX() - posicaoX);
				Y = (face.getLargura() - cavidade.getPosicaoY() - posicaoY);
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 3:
				X = (-face.getComprimento() + cavidade.getPosicaoX() + posicaoX);
				Y = posicaoY;
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			default:
				break;
			}
			
			CircularBoss novoCircularBoss = new CircularBoss();
			novoCircularBoss.setPosicao(X, Y, Z);
			novoCircularBoss.setAltura(alturaBoss);
			novoCircularBoss.setDiametro1(diametro1);
			novoCircularBoss.setDiametro2(diametro2);
			novoCircularBoss.setNome(this.textField9.getText());
			novoCircularBoss.setTolerancia(tolerancia*0.001);
			novoCircularBoss.setRugosidade(rugosidade*0.001);
			
			if (this.cavidade.validarBoss(novoCircularBoss)) 
			{
				{
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY) {
						coordinates = new Point3d(novoCircularBoss.X, novoCircularBoss.Y,
								this.face.getProfundidadeMaxima() - novoCircularBoss.Z);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);
					} else if (this.face.getTipo() == Face.XZ) {
						coordinates = new Point3d(novoCircularBoss.X, novoCircularBoss.Z, novoCircularBoss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(-1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YX) {
						coordinates = new Point3d(novoCircularBoss.X, this.face.getLargura() - novoCircularBoss.Y, novoCircularBoss.Z);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(-1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YZ) {
						coordinates = new Point3d(
								this.face.getProfundidadeMaxima() - novoCircularBoss.Z, novoCircularBoss.Y, this.face.getComprimento() - novoCircularBoss.X);
						axis = new ArrayList<Double>();
						axis.add(1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(-1.0);

					} else if (this.face.getTipo() == Face.ZX) {
						coordinates = new Point3d(novoCircularBoss.X, this.face.getProfundidadeMaxima() - novoCircularBoss.Z, this.face.getLargura() - novoCircularBoss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.ZY) {
						coordinates = new Point3d(novoCircularBoss.Z, novoCircularBoss.Y, novoCircularBoss.X);
						axis = new ArrayList<Double>();
						axis.add(-1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(1.0);

					}
					Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
					position.setName(novoCircularBoss.getNome() + " placement");
					novoCircularBoss.setPosition(position);
					// this.face.addFeature(novoCircularBoss);

					this.cavidade.addBoss(novoCircularBoss);
					/** fazer isto em todas as janelas */
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					/****************** atualiza a ARVORE ***************/
					// face.imprimeDados(novoFuro);
					// this.setModal(false);
					this.parent.setEnabled(true);

					this.parent.textArea1.setText(this.parent.textArea1
							.getText()
							+ "\n"
							+ "Boss: "
							+ novoCircularBoss.getNome()
							+ " adicionado com sucesso!");
					this.dispose();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Error trying to create the Boss", "invalid Boss", JOptionPane.OK_CANCEL_OPTION);
				String erro = "";
				StyleContext sc = StyleContext.getDefaultStyleContext();
				AttributeSet aSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);

				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n Error trying to create the Boss!");
			}
		}
		
	}

	private void cancel() {
		this.parent.setEnabled(true);
		dispose();
	}
}
