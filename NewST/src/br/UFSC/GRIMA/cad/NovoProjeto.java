package br.UFSC.GRIMA.cad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import jsdai.lang.SdaiException;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.cad.visual.ImageFrame;
import br.UFSC.GRIMA.cad.visual.NovoFrame;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.ProjectManager;
import br.UFSC.GRIMA.entidades.STEPWorkingStepsProject;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.util.ImageSource;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Maquina;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class NovoProjeto extends NovoFrame implements ActionListener,
		ItemListener {
	private static final long serialVersionUID = 5407049021976865441L;
	private AcessaBD bancoDeDados;
	public Maquina maquina/* = new Maquina() */;
	public double comprimento = 200;
	public double largura = 150;
	public double profundidade = 13;
	public Bloco bloco;
	private int userID;
	private String userName;

	// private Fundo parent = null;

	public NovoProjeto(int userID, String userName)// Fundo parent)
	{
		this.userID = userID;
		this.userName = userName;
		this.init();

	}

	public void init() {
		// inicializa o banco de dados
		// this.bancoDeDados = new AcessaBD(userID);

		// inicializa o ammbiente visual
		this.initComponents();
		this.adjustJFrame();
	}

	public void initComponents() {
		// this.parent = parent;
		this.maquina = new Maquina(comprimento, largura, profundidade);
		// this.bloco = new Bloco(X, Y, Z);
		
		comboBox3.addItem("SAE1015");
		comboBox3.addItem("SAE1020");
		comboBox3.addItem("SAE1030");
		

		this.comboBox1.addItem("MÁQUINA ROLAND MDX - 20");
		this.comboBox1.addItem("MÁQUINA ROLAND MDX - 40");
		this.comboBox1.addItem("MÁQUINA ROLAND MDX - 500");
		this.comboBox1
				.setToolTipText("Selecione uma das maquinas da seguinte lista");
		this.comboBox1.setForeground(new Color(0, 0, 153));

		this.cancelButton.addActionListener(this);
		this.okButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
	}

	public void adjustJFrame() {
		// this.setMaximasDimensoes();
		Toolkit toolkit = this.getToolkit();

		this.setResizable(false);
		this.pack();

		Dimension windowDimension = toolkit.getScreenSize();
		Dimension thisDimension = this.getPreferredSize();
		this
				.setLocation((int) (windowDimension.getWidth() - thisDimension
						.getWidth()) / 2,
						(int) (windowDimension.getHeight() - thisDimension
								.getHeight()) / 2);

		this.setVisible(true);
	}

	/*
	 * public void setMaximasDimensoes() {
	 * textField1.setText(Double.toString(comprimento));
	 * textField2.setText(Double.toString(largura));
	 * textField3.setText(Double.toString(profundidade)); }
	 */

	public void itemStateChanged(ItemEvent evt) {

		int index = this.comboBox1.getSelectedIndex();
		switch (index) {
		case 0:
			URL rolandMDX_20 = getClass().getResource(
					"/images/pr_ban_mdx20.jpg");
			comprimento = 200;
			largura = 150;
			profundidade = 20;
			this.labelMaquina.setIcon(new ImageIcon(rolandMDX_20));
			// this.parent.getArvore();
			break;
		case 1:
			URL rolandMDX_40 = getClass().getResource(
					"/images/pr_ban_mdx-40.jpg");
			comprimento = 305;
			largura = 305;
			profundidade = 50;
			this.labelMaquina.setIcon(new ImageIcon(rolandMDX_40));

			break;
		case 2:
			URL rolandMDX_500 = getClass().getResource("/images/mdx-500.JPG");
			comprimento = 500;
			largura = 330;
			profundidade = 80;
			this.labelMaquina.setIcon(new ImageIcon(rolandMDX_500));

			break;
		default:
			break;
		}
		this.maquina = new Maquina(comprimento, largura, profundidade);
		this.textField1.setText(Double.toString(comprimento));
		this.textField2.setText(Double.toString(largura));
		this.textField3.setText(Double.toString(profundidade));
		this.label6.setText(Double.toString(comprimento));
		this.label7.setText(Double.toString(largura));
		this.label8.setText(Double.toString(profundidade));
	}

	public void actionPerformed(ActionEvent evento) {
		
//		try {
//			ProjectManager.setToolType(this.comboBox2.getSelectedIndex());
//			ProjectManager.setMaterialType(this.comboBox3.getSelectedIndex());
//			
//			
//		} catch (SdaiException e) {
//			e.printStackTrace();
//		}
		
		Object origem = evento.getSource();
		if (origem == cancelButton) {
			this.cancelAction();
		} else if (origem == okButton) {
			this.okAction();
		}
	}

	private void propertiesAction(int selectedIndex) {
		Icon icon = (new ImageSource()).getToolIcon(selectedIndex);
		ImageFrame imgFrame = new ImageFrame();
		imgFrame.setResizable(false);
		imgFrame.setIcon(icon);
		imgFrame.setVisible(true);
		
	}

	public void cancelAction() {
		dispose();
	}

	/**
	 * Valida os dados inseridos nos campos e, caso esteja tudo OK, abre a
	 * janela principal do aplicativo.
	 * 
	 */
	public void okAction() {
		double x = 0.0;
		double y = 0.0;
		double z = 0.0;
		boolean ok = true;

		// System.out.println("Dimens�es do bloco: ");
		if (ok) {
			try {
				x = Double.parseDouble(textField1.getText());
				if (x > 0 && x <= maquina.getComprimento()) {
					// System.out.println("X: " + x);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"O valor asignado ao COMPRIMENTO (X), deve ser positivo\n"
											+ "              e menor do que o comprimento m�ximo\n"
											+ "                       da m�quina selecionada",
									"Erro na dimens�o X", JOptionPane.OK_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um N�mero v�lido para o comprimento(X)",
						"Erro na dimens�o X", JOptionPane.OK_OPTION);
				ok = false;
			}
		}
		if (ok) {
			try {
				y = Double.parseDouble(textField2.getText());
				if (y > 0 && y <= maquina.getLargura()) {
					// System.out.println("Y: " + y);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"O valor asignado � LARGURA (Y) da pe�a bruta \n"
											+ "   deve ser positivo e menor do que o largura \n"
											+ "            m�xima da m�quina selecionada",
									"Erro na dimens�o Y", JOptionPane.OK_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um n�mero positivo para a largura(Y)",
						"Erro na dimens�o Y", JOptionPane.OK_OPTION);
				ok = false;
			}
		}
		if (ok) {
			try {
				z = Double.parseDouble(textField3.getText());
				if (z > 0 && z <= maquina.getProfundidade()) {
					// System.out.println("Z: " + z);
					ok = true;
				} else {
					ok = false;
					JOptionPane
							.showMessageDialog(
									null,
									"    O valor asignado � ALTURA (Z) da pe�a bruta\n"
											+ "deve ser positivo e menor do que a profundidade\n"
											+ "          m�xima da m�quina selecionada",
									"Erro na dimens�o Z", JOptionPane.OK_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um numero positivo para a profundidade(Z)",
						"Erro na profundidade(Z)", JOptionPane.OK_OPTION);
				ok = false;
			}
		}

		if (ok) {
			// pega o nome do projeto que o usuario digitou
			String projectName = this.textField4.getText();
//			try {
//				STEPWorkingStepsProject stepHoleProject  = ProjectManager.getHoleProject();
////				Material material = stepHoleProject.getMaterialModel();
//				
//				ProjectManager.getHoleProject().setProject(projectName);
//				ProjectManager.getHoleProject().setMaterial(material.getName(), material.getType(),
//						material.getPropertie());
//				ProjectManager.getHoleProject().setWorkPiece("", x, y, z);
//			} catch (SdaiException e) {
//				e.printStackTrace();
//			}

			// armazena os dados principais do projeto em uma classe
			DadosDeProjeto dadosDeProjeto = new DadosDeProjeto(this.userID,
					this.userName, projectName,this.comboBox3.getSelectedIndex());
			dadosDeProjeto.setSelectedMaterial(comboBox3.getSelectedIndex());
			// Cria a classe que armazena todos os dados do projeto - pe�a,
			// maquina, ordens de fabricacao
			Projeto projeto = new Projeto(this.maquina, new Bloco(x, y, z),
					dadosDeProjeto);

			
			AcessaBD aBD = new AcessaBD("150.162.105.1", "webTools", "webcad", "julio123");
					
			// inicia a janela do CAD
			JanelaPrincipal Fun = new JanelaPrincipal(projeto,
					aBD);
			Fun.setVisible(true);
			Fun.setResizable(true);

			// fecha a janela do NOVOPROJETO
			dispose();
		}
	}

}