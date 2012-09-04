package br.UFSC.GRIMA.cad;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.cad.visual.ImageFrame;
import br.UFSC.GRIMA.cad.visual.NovoProjectFrame;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.CSGSolid;
import br.UFSC.GRIMA.operationSolids.OperationBlock;
import br.UFSC.GRIMA.operationSolids.OperationBlock1;
import br.UFSC.GRIMA.util.ImageSource;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CriarNovoProjeto extends NovoProjectFrame implements ActionListener,
		ItemListener {
	private static final long serialVersionUID = 5407049021976865441L;
	private AcessaBD bancoDeDados;
	public double comprimento = 200;
	public double largura = 150;
	public double profundidade = 13;
	public Bloco bloco;
	private int userID;
	private String userName;
	private Material material; 
	CSGSolid csgBloco;
	J3D j3d;
	private PropertyParameter propertyParameter;
	double x = 0.0;
	double y = 0.0;
	double z = 0.0;
	double toleranciaGlobal = -1;
	// private Fundo parent = null;

	public CriarNovoProjeto(int userID, String userName)// Fundo parent)
	{
		this.userID = userID;
		this.userName = userName;
		
		this.material = new Material("SAE 1020", Material.ACO_SEM_LIGA);
		this.propertyParameter = new PropertyParameter();
		this.propertyParameter.setParameterName("Hardness");
		this.propertyParameter.setParameterUnit("HB");
		this.propertyParameter.setParameterValue(250);
		ArrayList<PropertyParameter> prop = new ArrayList<PropertyParameter>();
		prop.add(this.propertyParameter);
		this.material.setProperties(prop);	
		this.label3.setText(this.userName);
		this.init();
	}

	public void init() {
		// inicializa o banco de dados
		// this.bancoDeDados = new AcessaBD(userID);

		// inicializa o ammbiente visual
		this.initComponents();
		this.adjustJFrame();
		//this.bloco = new Bloco(200, 100, 30);
		this.j3d = new J3D(this.panel8);
//		this.csgBloco = new OperationBlock("", (Double)this.spinner2.getValue(), (Double)this.spinner3.getValue(), (Double)this.spinner1.getValue());
		this.csgBloco = new OperationBlock1("", ((Double)this.spinner2.getValue()).floatValue(), ((Double)this.spinner3.getValue()).floatValue(), ((Double)this.spinner1.getValue()).floatValue(), 3, 2, 3);

		this.j3d.addSolid(csgBloco);
	}

	public void initComponents() {
		// this.parent = parent;
		
		this.cancelButton.addActionListener(this);
		this.okButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.button1.addActionListener(this);
		this.button1.setEnabled(false);
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
		ArrayList<PropertyParameter> prop = new ArrayList<PropertyParameter>();
		switch (index) {
		case 0:
			this.material = new Material("SAE 1020", Material.ACO_SEM_LIGA);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(125);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 1:
			this.material = new Material("Steel-high Carbon", Material.ACO_ALTO_CARBONO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(210);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 2:
			this.material = new Material("Steel-low allowed", Material.ACO_BAIXA_LIGA);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(275);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 3:
			this.material = new Material("Steel-high allowed", Material.ACO_ALTA_LIGA);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(270);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);			
			break;
		case 4:
			this.material = new Material("Cast Steel", Material.ACO_FUNDIDO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(190);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 5:
			this.material = new Material("Austenitic Stainless Steel", Material.ACO_INOX_AUST);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(190);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 6:
			this.material = new Material("Cast Austenitic Stainless Steel", Material.ACO_INOX_AUST);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(200);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 7:
			this.material = new Material("Super Heat-resistant Alloy - Ni-based", Material.SUPER_LIGA_NI);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(300);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 8:
			this.material = new Material("Ti Alloy", Material.LIGA_TITANIO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(900);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 9:
			this.material = new Material("Malleable Cast Iron", Material.FERRO_FUNDIDO_MALEAVEL);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(180);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 10:
			this.material = new Material("Gray Cast Iron", Material.FERRO_FUNDIDO_CINZENTO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(210);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 11:
			this.material = new Material("Nodular Cast Iron", Material.FERRO_FUNDIDO_NODULAR);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(205);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 12:
			this.material = new Material("Extra Hard Steel", Material.ACO_EXTRA_DURO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HRc");
			this.propertyParameter.setParameterValue(54);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 13:
			this.material = new Material("Al Alloy", Material.LIGA_ALUMINIO);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(68);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		case 14:
			this.material = new Material("Cu Alloy", Material.LIGA_COBRE);
			this.propertyParameter = new PropertyParameter();
			this.propertyParameter.setParameterName("Hardness");
			this.propertyParameter.setParameterUnit("HB");
			this.propertyParameter.setParameterValue(100);
			prop.add(this.propertyParameter);
			this.material.setProperties(prop);
			break;
		default:
			break;
		}
//		this.textField1.setText(Double.toString(comprimento));
//		this.textField2.setText(Double.toString(largura));
//		this.textField3.setText(Double.toString(profundidade));
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
		} else if (origem == button1)
		{
			this.visualizar();
		}
	}

	private void visualizar() 
	{
		this.bloco = new Bloco(((Double)this.spinner1.getValue()).doubleValue(), ((Double)this.spinner2.getValue()).doubleValue(), ((Double)this.spinner3.getValue()).doubleValue(), this.material);
		
//		this.j3d.removeSolid();
		
		this.j3d = new J3D(this.panel8);
//		CSGSolid block = new OperationBlock("block",(float)x, (float)y, (float)z);
//		csgBloco = new OperationBlock("block", this.bloco.getLargura(), this.bloco.getProfundidade(), this.bloco.getComprimento());
		csgBloco = new OperationBlock1("block", (float)this.bloco.getLargura(), (float)this.bloco.getProfundidade(), (float)this.bloco.getComprimento(), 3, 3, 3);
		
		this.j3d.addSolid(csgBloco);
		
		System.out.println("x = " + this.bloco.getComprimento());
		System.out.println("y = " + this.bloco.getLargura());
		System.out.println("z = " + this.bloco.getProfundidade());

	}

	private void propertiesAction(int selectedIndex) 
	{
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
//		double x = 0.0;
//		double y = 0.0;
//		double z = 0.0;
		boolean ok = true;
		
		String projectName = null, owner, description, organization;
		
		AcessaBD aBD = null;

		// System.out.println("Dimens�es do bloco: ");
		if (ok) {
			try {
				this.x = ((Double)this.spinner1.getValue()).doubleValue();
				if (this.x > 0) {
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
				this.y = ((Double)this.spinner2.getValue()).doubleValue();
				if (this.y > 0) {
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
				this.z = ((Double)this.spinner3.getValue()).doubleValue();
				if (this.z > 0) {
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
		if (ok)
		{
			try
			{
				this.toleranciaGlobal = ((Double)this.spinner4.getValue()).doubleValue();
			if (this.toleranciaGlobal > 0 && this.toleranciaGlobal < 51)
			{
				ok = true;
			} else
			{
				JOptionPane.showMessageDialog(null, "Coloque um valor entre 0 e 50 um", "Erro na tolerância", JOptionPane.CANCEL_OPTION);
				ok = false;
			}
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Coloque um valor entre 0 e 50 um", "Erro na tolerância", JOptionPane.CLOSED_OPTION);
				ok = false;
			}
		}
		if (ok) {
			/*
			 *  Banco de Dados
			 */
			aBD = new AcessaBD("150.162.105.1", "webTools", "webcad", "julio123");

			// pega o nome do projeto que o usuario digitou
			projectName = this.textField4.getText();
						
			if(aBD.existeProjeto(this.userID, projectName)){

				int i = JOptionPane
				.showConfirmDialog(
						null,
						"Já existe um projeto com esse nome! \n"
						+ "Deseja substituí-lo ?",
						"Projeto Duplicado",
						JOptionPane.OK_CANCEL_OPTION);

				if (i == JOptionPane.OK_OPTION) {

					ok = true;

				}else{

					ok = false;

				}
			}
		}
		if (ok) {

			owner = this.label3.getText();
			description = this.textPane1.getText();
			organization = this.textField5.getText();
			
//			// armazena os dados principais do projeto em uma classe
			DadosDeProjeto dadosDeProjeto = new DadosDeProjeto(this.userID,
					owner, projectName,this.material, description, organization);
//			dadosDeProjeto.setSelectedMaterial(comboBox1.getSelectedIndex());
//			// Cria a classe que armazena todos os dados do projeto - pe�a,
//			// maquina, ordens de fabricacao
			this.bloco = new Bloco(this.x, this.y, this.z, this.toleranciaGlobal, this.material);

			Projeto projeto = new Projeto(this.bloco, dadosDeProjeto);
			// inicia a janela do CAD
			JanelaPrincipal Fun = new JanelaPrincipal(projeto, aBD);
			Fun.setVisible(true);
			Fun.setResizable(true);

			dispose();
		}
	}

}