package br.UFSC.GRIMA.cad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.FuroConicoFrame;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CriarFuroConico extends FuroConicoFrame implements ActionListener
{
	Face face = null;
	public JanelaPrincipal parent;
	public CriarFuroConico(JanelaPrincipal parent, Face face) {
		super(parent);
		// this.setModal(true);
		this.face = face;
		this.parent = parent;
		// this.parent.setEnabled(false);
//		this.textField1.setText("" + face.getProfundidadeMaxima());
//		this.textField1.setEditable(false);

		this.init();
	}
	public void init() {
		

		this.label3.setText(this.face.getTipoString());
		this.cancelButton.addActionListener(this);
		this.okButton.addActionListener(this);
		this.checkBox1.addActionListener(this);
		this.setVisible(true);
		
		if (this.checkBox1.isSelected() == true)
		{
			this.spinner1.setEnabled(false);
			this.spinner6.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent evt) 
				{
					double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner6.getValue())).doubleValue();
					spinner1.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
				}
			});
		}
		else if (this.checkBox1.isSelected() == false)
		{
			this.spinner1.setEnabled(true);
		}
	}
	public void actionPerformed(ActionEvent event) 
	{
		Object origem = event.getSource();
		if (origem.equals(cancelButton)) 
		{
			cancel();
		} else if (origem == okButton) 
		{
			ok();
		} else if(origem == this.checkBox1)
		{
			selected();
		}
	}
	private void selected()
	{
		if (this.checkBox1.isSelected() == true)
		{
			this.spinner1.setEnabled(false);
			double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner6.getValue())).doubleValue();
			spinner1.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
			this.spinner6.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent evt) 
				{
					double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner6.getValue())).doubleValue();
					spinner1.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
				}
			});
		}
		else if (this.checkBox1.isSelected() == false)
		{
			this.spinner1.setEnabled(true);
		}
	}
	private void ok() {
		double posicaoX = 0;
		double posicaoY = 0;
		double posicaoZ = 0;
		double raioFuro = 0;
		double profundidade = 0;
		double tolerancia = 0;
		double rugosidade = 0;
		double raio1 = 0;
		double diametroMinimo = new Ferramenta().getDiametroFerramenta();
		boolean ok = true;
		/**
		 * *****Validacao da POSICAO X
		 * 
		 */
		if (ok) {
			try {
				posicaoX = ((Double)this.spinner4.getValue()).doubleValue();

				if (posicaoX < face.getComprimento() && posicaoX > 0) {
					ok = true;
				} else {
					JOptionPane.showMessageDialog(null,
							"Digite um Numero positivo para a posicao X",
							"Erro na dimensao X", JOptionPane.CANCEL_OPTION);
					ok = false;
				}
				
//				  if(face.getProfundidadeMaxima() + posicaoZ <= 0) {
//				  JOptionPane.showMessageDialog(null,
//				  "a progundidade ultrapassa a maxima profundidade do bloco",
//				  "Erro na dimens�o X", JOptionPane.CANCEL_OPTION); ok = false;
//				  System.out.println("profundidadeMaX: " +
//				  face.getProfundidadeMaxima()); } else if(posicaoX <=0) { ok
//				  = false; JOptionPane.showMessageDialog(null,
//				  "Digite um N�mero positivo para a posi��o X",
//				  "Erro na dimens�o X", JOptionPane.CANCEL_OPTION); }
				 

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a posicao X",
						"Erro na dimensao X", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * validacao da POSICAO Y
		 * 
		 */
		if (ok) {
			try {
				posicaoY = ((Double)this.spinner5.getValue()).doubleValue();
				if (posicaoY > 0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Numero positivo para a posicao Y",
							"Erro na dimensao Y", JOptionPane.OK_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a posicao Y",
						"Erro na dimensao Y", JOptionPane.OK_OPTION);
			}
		}
		/**
		 * validacao da POSICAO Z
		 * 
		 */
		if (ok) {
			try {
				posicaoZ = ((Double)this.spinner6.getValue()).doubleValue();
				if (posicaoZ >= 0) {
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Numero positivo para a posicao Z",
							"Erro na dimensao Z", JOptionPane.OK_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para o comprimento(Z)",
						"Erro na dimensao Z", JOptionPane.OK_OPTION);
			}
		}
		/**
		 * validacao do RAIO
		 * 
		 */
		if (ok) {
			try {
				raioFuro = ((Double)this.spinner2.getValue()).doubleValue();
				switch (face.verticeAtivado) {
				case 0:
					if (2 * raioFuro < diametroMinimo) {
						JOptionPane.showMessageDialog(null,
								"O raio deve ser maior do que o raio da ferramenta "
										+ "(" + diametroMinimo / 2 + "mm)",
								"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoX + raioFuro >= face.getComprimento()
							|| posicaoX - raioFuro <= 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"verifique que o furo esteja dentro dos limites da face \n"
												+ "                    (verifique os valores no eixo X)",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoY + raioFuro >= face.getLargura()
							|| posicaoY - raioFuro <= 0) {
						ok = false;
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que o furo esteja dentro dos limites da face\n "
												+ "          (verifique os valores relativos ao eixo Y)",
										"Erro no Raio",
										JOptionPane.CANCEL_OPTION);
					}
					break;
				case 1:
					if (2 * raioFuro < diametroMinimo) {
						JOptionPane
								.showMessageDialog(
										null,
										"O raio deve ser maior do que o raio da ferramenta ",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoX + raioFuro >= face.getLargura()
							|| posicaoX - raioFuro <= 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"verifique que o furo esteja dentro dos limites da face \n"
												+ "                    (verifique os valores no eixo X)",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoY + raioFuro >= face.getComprimento()
							|| posicaoY - raioFuro <= 0) {
						ok = false;
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que o furo esteja dentro dos limites da face\n "
												+ "          (verifique os valores relativos ao eixo Y)",
										"Erro no Raio",
										JOptionPane.CANCEL_OPTION);
					}
					break;
				case 2:
					if (2 * raioFuro < diametroMinimo) {
						JOptionPane
								.showMessageDialog(
										null,
										"O raio deve ser maior do que o raio da ferramenta ",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoX + raioFuro >= face.getComprimento()
							|| posicaoX - raioFuro <= 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"verifique que o furo esteja dentro dos limites da face \n"
												+ "                    (verifique os valores no eixo X)",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoY + raioFuro >= face.getLargura()
							|| posicaoY - raioFuro <= 0) {
						ok = false;
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que o furo esteja dentro dos limites da face\n "
												+ "          (verifique os valores relativos ao eixo Y)",
										"Erro no Raio",
										JOptionPane.CANCEL_OPTION);
					}
					break;
				case 3:
					if (2 * raioFuro < diametroMinimo) {
						JOptionPane
								.showMessageDialog(
										null,
										"O raio deve ser maior do que o raio da ferramenta ",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoX + raioFuro >= face.getLargura()
							|| posicaoX - raioFuro <= 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"verifique que o furo esteja dentro dos limites da face \n"
												+ "                    (verifique os valores no eixo X)",
										"Mensagem", JOptionPane.CANCEL_OPTION);
						ok = false;
					} else if (posicaoY + raioFuro >= face.getComprimento()
							|| posicaoY - raioFuro <= 0) {
						ok = false;
						JOptionPane
								.showMessageDialog(
										null,
										"Verifique que o furo esteja dentro dos limites da face\n "
												+ "          (verifique os valores relativos ao eixo Y)",
										"Erro no Raio",
										JOptionPane.CANCEL_OPTION);
					}
					break;
				}

				{
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero valido para o Raio", "Erro no Raio",
						JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * validacao da PROFUNDIDADE
		 * 
		 */
		if (ok) {
			try

			{
				profundidade = ((Double)this.spinner1.getValue()).doubleValue();
				double refPosZ = profundidade + posicaoZ;
				if (profundidade > 0 && refPosZ <= face.getProfundidadeMaxima()) 
				{
					ok = true;
				} else if(profundidade >0 && this.checkBox1.isSelected() == true && refPosZ == this.face.getProfundidadeMaxima())
				{
					ok = true;
				}
				else {
					JOptionPane
							.showMessageDialog(
									null,
									"Verifique que a profundidade nao ultrapasse a profundidade maxima do bloco",
									"Erro na profundidade",
									JOptionPane.CANCEL_OPTION);
					ok = false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a profundidade",
						"Erro na profundidade", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * validacao da TOLERANCIA
		 * 
		 */
		if (ok) {
			try {
				tolerancia = ((Double)this.spinner3.getValue()).doubleValue();
				if (tolerancia >= 0.0) 
				{
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para a tolerância",
							"Erro ", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a Tolerancia",
						"Erro na Tolerancia", JOptionPane.OK_OPTION);
			}
		}
		/**
		 *  validacao do RAIO1
		 */
		if (ok)
		{
			raio1 = ((Double)this.raio1.getValue()).doubleValue();
			if (raio1 >= raioFuro)
			{
				JOptionPane.showMessageDialog(null, "O raio 1 não pode ser maior do que o raio", "Erro no raio 1", JOptionPane.OK_OPTION);
				ok = false;
			}
		}
		
		/**
		 * set da RUGOSIDADE, nao precisa validacao
		 * 
		 */
		if (ok) {
				rugosidade = ((Double) furoRug.getValue()).doubleValue();
		}

		if (ok) {
			double X = 0, Y = 0, Z = 0;
			switch (face.verticeAtivado) {
			case 0:
				X = posicaoX;
				Y = posicaoY;
				Z = posicaoZ;
				break;
			case 1:
				X = posicaoY;
				Y = face.getLargura() - posicaoX;
				Z = posicaoZ;
				break;
			case 2:
				X = face.getComprimento() - posicaoX;
				Y = face.getLargura() - posicaoY;
				Z = posicaoZ;
				break;
			case 3:
				X = face.getComprimento() - posicaoY;
				Y = posicaoX;
				Z = posicaoZ;
				break;
			default:
				break;
			}
			
			FuroConico furoConico = new FuroConico();
			furoConico.setPosicao(X, Y, Z);
			furoConico.setProfundidade(profundidade);
			furoConico.setRaio(raioFuro);
			furoConico.setRaio1(raio1);
			furoConico.setNome(this.textField9.getText());
			furoConico.setTolerancia(tolerancia * 0.001);
			furoConico.setRugosidade(rugosidade * 0.001);
			furoConico.setPassante(this.checkBox1.isSelected());
			
			if(tolerancia<=Feature.LIMITE_RUGOSIDADE && rugosidade<=Feature.LIMITE_TOLERANCIA){
				furoConico.setAcabamento(true);
			}
			
			
			if (this.face.validarFeature(furoConico))
			{		
				Point3d coordinates = null;
				ArrayList<Double> axis = null, refDirection = null;
				if (this.face.getTipo() == Face.XY)
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
				} else if (this.face.getTipo() == Face.XZ)
				{
					coordinates = new Point3d(X, Z, Y);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(-1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.YX)
				{
					coordinates = new Point3d(X, this.face.getLargura() - Y, Z);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(0.0);
					axis.add(-1.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.YZ)
				{
					coordinates = new Point3d(this.face.getProfundidadeMaxima() - Z, Y, this.face.getComprimento() - X);
					axis = new ArrayList<Double>();
					axis.add(1.0);
					axis.add(0.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(0.0);
					refDirection.add(0.0);
					refDirection.add(-1.0);
					
				} else if (this.face.getTipo() == Face.ZX)
				{
					coordinates = new Point3d(X, this.face.getProfundidadeMaxima()- Z, this.face.getLargura() - Y);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.ZY)
				{
					coordinates = new Point3d(Z, Y, X);
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
				position.setName(furoConico.getNome() + " placement");
				furoConico.setPosition(position);	
				this.face.addFeature(furoConico);
				
				
				/** fazer isto em todas as janelas */
				try
				{
					Feature mae = furoConico.getFeaturePrecedente();
					mae.itsSons.add(furoConico);
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
				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Furo Cônico: " + furoConico.getNome() + " adicionada com sucesso!");
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao criar Furo",
						"Feature invalida", JOptionPane.OK_CANCEL_OPTION);
			}
		}

	}

	private void cancel() 
	{
		this.parent.setEnabled(true);
		dispose();
	}
}
