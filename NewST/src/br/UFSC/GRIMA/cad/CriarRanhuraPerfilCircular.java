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

import br.UFSC.GRIMA.cad.visual.RanhuraPerfilCircularFrame;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CriarRanhuraPerfilCircular extends RanhuraPerfilCircularFrame
		implements ActionListener {
	public Face face = null;
	public int eixo = 0;
	public JanelaPrincipal parent;

	public CriarRanhuraPerfilCircular(JanelaPrincipal parent, Face face) {
		super(parent);

		this.face = face;
		this.parent = parent;
		// this.parent.setEnabled(false);
		this.init();
	}

	public void init() {
		this.adjustSize();
		this.adjustPosition();

		this.label10.setText(this.face.getTipoString());
		super.cancelButton.addActionListener(this);
		super.okButton.addActionListener(this);
		super.radioButtonX.addActionListener(this);
		super.radioButtonY.addActionListener(this);

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

		int posX = (int) (screenSize.getWidth() - size.getWidth()) / 2;
		int posY = (int) (screenSize.getHeight() - size.getHeight()) / 2;

		this.setLocation(posX, posY);
	}

	public void actionPerformed(ActionEvent event) {
		Object origem = event.getSource();
		if (origem == okButton) {
			okSelecionado();
		} else if (origem == cancelButton) {
			CancelSelecionado();
		} else if (origem == radioButtonX) {
			eixo = 0;
			URL ranhuraHorizontalModelo = getClass().getResource("/images/RanhuraPerfilCircularHorizontal.png");
			this.spinner4.setBounds(135, 150, 40, this.spinner4.getPreferredSize().height);
			this.labelImage.setIcon(new ImageIcon(ranhuraHorizontalModelo));
		} else if (origem == radioButtonY) {
			eixo = 1;
			URL ranhuraVerticalModelo = getClass().getResource("/images/RanhuraPerfilCircularVertical.png");
			this.spinner4.setBounds(100, 100, 40, this.spinner4.getPreferredSize().height);
			this.labelImage.setIcon(new ImageIcon(ranhuraVerticalModelo));
		}
	}

	private void CancelSelecionado() {
		this.parent.setEnabled(true);
		dispose();
	}

	private void okSelecionado() {
		boolean ok = true;
		double dz = 0.0;
		double raio = 0.0;
		double deslocamentoNorma = 0.0, deslocamentoDesenho = 0;
		double posicaoZ = 0.0;
		double posXDesenho = 0, posYDesenho = 0, posZDesenho = 0, xNorma = 0, yNorma = 0, zNorma = 0;
		double diametroFresa = new Ferramenta().getDiametroFerramenta();
		double rugosidade = 0;

		/**
		 * ****** validacao dos dados introduzidos no campo "DZ" ******
		 * 
		 */

		if (ok) {
			try {
				dz = ((Double) this.spinner1.getValue()).doubleValue();
				if (dz >= 0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um Número positivo para DZ da Ranhura",
									"Erro",
									JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um Número positivo para DZ da Ranhura",
								"Erro",
								JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}

		/**
		 * ****** validacao dos dados introduzidos no campo "RAIO" ******
		 * 
		 */

		if (ok) {
			try {
				raio = ((Double) this.spinner2.getValue()).doubleValue();
				if (raio > 0 && raio >= diametroFresa) {

					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Verifique que a raio da ranhura seja maior que zero", "Erro no raio",
							JOptionPane.CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a RAIO da Ranhura",
						"Erro na RAIO", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * ****** validacao dos dados introduzidos no campo "DESLOCAMENTO"
		 * ******
		 * 
		 */
		if (ok) {
			try {
				deslocamentoNorma = ((Double) this.spinner4.getValue())
						.doubleValue();
				if (deslocamentoNorma > 0) {
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
				posicaoZ = ((Double) this.spinner3.getValue()).doubleValue();
				if (posicaoZ >= 0
						&& dz + posicaoZ < face.getProfundidadeMaxima()) {
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
				posXDesenho = 0;
				xNorma = 0;
				posYDesenho = deslocamentoNorma - raio * Math.cos(Math.asin(dz/raio));
				yNorma = deslocamentoNorma;
				posZDesenho = posicaoZ;
				zNorma = -posicaoZ + dz;
				break;

			case 1:/****** VERTICAL **********/
				posXDesenho = deslocamentoNorma - raio * Math.cos(Math.asin(dz/raio));
				xNorma = deslocamentoNorma;
				posYDesenho = 0;
				yNorma = 0;
				posZDesenho = posicaoZ;
				zNorma = -posicaoZ + dz;
				break;
			default:
				break;
			}
			RanhuraPerfilCircularParcial novaRanhura = new RanhuraPerfilCircularParcial();
			double Xdesenho = 0, Ydesenho = 0, Zdesenho = 0;
			double xN = 0, yN = 0, zN = 0;
			double largura = 2 * raio * Math.cos(Math.asin(dz/raio));
			double comprimento = 0;
			
			deslocamentoDesenho = deslocamentoNorma - raio * Math.cos(Math.asin(dz/raio));
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
					novaRanhura.setEixo(Degrau.VERTICAL);
					Xdesenho = face.getComprimento() - deslocamentoDesenho - largura;
					xN = face.getComprimento() - deslocamentoNorma;
					Ydesenho = 0;
					yN = 0;
					Zdesenho = posZDesenho;
					zN = zNorma;
				} else {
					novaRanhura.setEixo(Degrau.HORIZONTAL);
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
				novaRanhura.setRaio(raio);
				novaRanhura.setLargura(largura);
				novaRanhura.setDz(dz);
				novaRanhura.setPosicao(Xdesenho, Ydesenho, Zdesenho);
				novaRanhura.setNome(this.textFieldNome.getText());
				novaRanhura.setRugosidade(rugosidade*0.001);
				novaRanhura.setTolerancia(rugosidade*0.001);
				novaRanhura.setPosicaoNorma(xN, yN, zN);
				novaRanhura.setProfundidade(raio - dz);
				novaRanhura.setDeslocamentoNorma(deslocamentoNorma);
				novaRanhura.setComprimento(comprimento);
				novaRanhura.setAngulo(2 * 180 / Math. PI * Math.acos(novaRanhura.getDz() / novaRanhura.getRaio())); // deve estar em graus normais (no gerador de arquivo .21 se deve transformar em radianos)
				
				if (rugosidade <= Feature.LIMITE_RUGOSIDADE)
					novaRanhura.setAcabamento(true);

				if (this.face.validarFeature(novaRanhura)) 
				{
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

					// face.imprimeDados(novaRanhura);
					this.parent.setEnabled(true);
					this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Ranhura Perfil Circular Parcial: " + novaRanhura.getNome() + " adicionada com sucesso!");
					
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