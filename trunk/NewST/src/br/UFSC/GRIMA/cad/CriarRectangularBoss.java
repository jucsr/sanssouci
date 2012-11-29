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

import br.UFSC.GRIMA.cad.visual.CreateRectangularBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

/**
 * 
 * @author Lucas K
 *
 */

public class CriarRectangularBoss extends CreateRectangularBossFrame implements 
		ActionListener {

	public Face face = null;
	public JanelaPrincipal parent;
	private Cavidade cavidade = null;
	private GeneralClosedPocket generalClosedPocket = null;
	
	public CriarRectangularBoss(JanelaPrincipal parent, Face face, Cavidade cavidade) {
		super(parent);
		this.cavidade = cavidade;
		this.face = face;
		this.parent = parent;
		
		this.init();
	}
	
	public CriarRectangularBoss(JanelaPrincipal parent, Face face, GeneralClosedPocket  generalClosedPocket) {
		super(parent);
		this.generalClosedPocket = generalClosedPocket;
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
		super.spinner3.setVisible(false);		//deixa o spinner do eixo Z invisivel
		
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
		
		double raio = 0.0;
		double alturaBoss = 0.0; // em relacao a base da cavidade
		double posicaoX = 0.0;
		double posicaoY = 0.0;
		double posicaoZ = 0.0;
		double largura = 0.0;
		double comprimento = 0.0;
		double refLargura = 0.0;
//		double refComprimento = 0.0;
//		double raioMin = 0.0;
		
		double tolerancia = 0.0;
		double rugosidade = 0.0;
		
		

		/** Validacao da posicao X **/

		if (ok) {
			try {
				posicaoX = ((Double) this.spinner1.getValue()).doubleValue();
				if (posicaoX > 0 ) {
					// System.out.println("deslocamento X: " + posicaoX);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"Digite um numero positivo para a posicao X da cavidade",
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
				posicaoY = ((Double) this.spinner2.getValue()).doubleValue();
				if (posicaoY > 0 ) {
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
				posicaoZ = ((Double) this.spinner3.getValue()).doubleValue();
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

		/** Validacao da largura **/

		if (ok) {
			try {
				
				alturaBoss = ((Double) this.spinner6.getValue()).doubleValue();
				largura = ((Double) this.spinner7.getValue()).doubleValue();
				comprimento = ((Double) this.spinner8.getValue()).doubleValue();

				switch (face.verticeAtivado) {

				case 0:

					refLargura = posicaoY + largura;
					if (largura > 0 && refLargura < cavidade.getLargura())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
								+ "\n               (revise a largura ou a posicao Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 1:

					refLargura = posicaoY + largura;
					if (largura >0 && refLargura < cavidade.getComprimento())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
								+ "\n               (revise a largura ou a posicao Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 2:

					refLargura = posicaoY + largura;
					if (largura > 0 && refLargura < cavidade.getLargura())
					{
						//System.out.println("largura: " + largura);
						ok = true;
						
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posicao Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 3:

					refLargura = posicaoY + largura;
					if (largura >0 && refLargura < face.getComprimento())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posicao Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
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
				alturaBoss = ((Double) this.spinner6.getValue()).doubleValue();
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
		
		/**Validacao do raio **/
		
		if (ok) {
			
			raio = ((Double) this.spinner5.getValue()).doubleValue();
			if (raio >= 0 ) {
				// System.out.println("deslocamento X: " + posicaoX);
				ok = true;
			} else {
				ok = false;
				JOptionPane
						.showMessageDialog(
								null,
								"Digite um numero positivo ou nulo para o raio do boss",
								"Erro no raio",
								JOptionPane.OK_CANCEL_OPTION);
			}
			
		} else if (raio > largura && raio > comprimento){
			ok = false;
			JOptionPane
					.showMessageDialog(
							null,
							"Digite um numero para o raio menor que a largura e o comprimento",
							"Erro no raio",
							JOptionPane.OK_CANCEL_OPTION);
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

		
		if (ok) {
			double X = 0, Y = 0, Z = 0;
			switch (face.verticeAtivado) {
			case 0:
				
				X= posicaoX + cavidade.getPosicaoX();
				Y= posicaoY + cavidade.getPosicaoY();
				Z= cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 1:
				
				X = posicaoY + cavidade.getLargura();
				Y = posicaoX;
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 2:
				
				X = cavidade.getComprimento() - comprimento - posicaoX;
				Y = cavidade.getLargura() - largura - posicaoY;
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;
				break;
			case 3:
				
				X = posicaoY;
				Y = cavidade.getComprimento() - posicaoX - comprimento;
				Z = cavidade.getProfundidade() - alturaBoss + cavidade.Z;

				break;
			default:
				break;
			}
			
			RectangularBoss novoRectangularBoss = new RectangularBoss(comprimento, largura, alturaBoss, raio);
			novoRectangularBoss.setPosicao(X, Y, Z);
			novoRectangularBoss.setAltura(alturaBoss);
			novoRectangularBoss.setNome(this.textField9.getText());
			novoRectangularBoss.setTolerancia(tolerancia*0.001);
			novoRectangularBoss.setRugosidade(rugosidade*0.001);
			
			
			if (this.cavidade.validarBoss(novoRectangularBoss)) 
			{
				{
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY) {
						coordinates = new Point3d(novoRectangularBoss.X, novoRectangularBoss.Y,
								this.face.getProfundidadeMaxima() - novoRectangularBoss.Z);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);
					} else if (this.face.getTipo() == Face.XZ) {
						coordinates = new Point3d(novoRectangularBoss.X, novoRectangularBoss.Z, novoRectangularBoss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(-1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YX) {
						coordinates = new Point3d(novoRectangularBoss.X, this.face.getLargura() - novoRectangularBoss.Y, novoRectangularBoss.Z);
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
								this.face.getProfundidadeMaxima() - novoRectangularBoss.Z, novoRectangularBoss.Y, this.face.getComprimento() - novoRectangularBoss.X);
						axis = new ArrayList<Double>();
						axis.add(1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(-1.0);

					} else if (this.face.getTipo() == Face.ZX) {
						coordinates = new Point3d(novoRectangularBoss.X, this.face.getProfundidadeMaxima() - novoRectangularBoss.Z, this.face.getLargura() - novoRectangularBoss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.ZY) {
						coordinates = new Point3d(novoRectangularBoss.Z, novoRectangularBoss.Y, novoRectangularBoss.X);
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
					position.setName(novoRectangularBoss.getNome() + " placement");
					novoRectangularBoss.setPosition(position);
					// this.face.addFeature(novoCircularBoss);

					this.cavidade.addBoss(novoRectangularBoss);
					/** fazer isto em todas as janelas */
					try
					{
						Feature mae = novoRectangularBoss.getFeaturePrecedente();
						mae.itsSons.add(novoRectangularBoss);
					} catch(Exception e)
					{
						
					}
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					this.parent.atualizarArvorePrecedencias();

					/****************** atualiza a ARVORE ***************/
					// face.imprimeDados(novoFuro);
					// this.setModal(false);
					this.parent.setEnabled(true);

					this.parent.textArea1.setText(this.parent.textArea1
							.getText()
							+ "\n"
							+ "Boss: "
							+ novoRectangularBoss.getNome()
							+ " adicionado com sucesso!");
					
					this.dispose();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Error trying to create the Boss", "invalid Boss", JOptionPane.OK_CANCEL_OPTION);
//				String erro = "";
//				StyleContext sc = StyleContext.getDefaultStyleContext();
//				AttributeSet aSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);

				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n Error trying to create the Boss!");
			}
		}
		
	}
		
	private void cancel() {
		this.parent.setEnabled(true);
		dispose();
	}
}
