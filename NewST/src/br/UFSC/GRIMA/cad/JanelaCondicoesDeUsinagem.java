package br.UFSC.GRIMA.cad;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.cad.visual.JanelaCondicoesDeUsinagem_view;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem.Material;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class JanelaCondicoesDeUsinagem extends JanelaCondicoesDeUsinagem_view {
	private JanelaPrincipal parent;

	private String[] materiais = { "", "Cibatool", "Aluminio" };
	private String[] ferramentas = { "Broca 3mm" };
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private Ferramenta ferramenta;

	public JanelaCondicoesDeUsinagem(JanelaPrincipal owner) {
		super(owner);

		this.parent = owner;
		this.setModal(true);
		// TODO Auto-generated constructor stub
		this.init();
	}

	public void init() {
		this.adjustSize();
		this.adjustPosition();

		for (int i = 0; i < this.materiais.length; i++) {
			this.comboBox.addItem(this.materiais[i]);
		}

		for (int i = 0; i < this.ferramentas.length; i++) {
			this.ferramentaComboBox.addItem(this.ferramentas[i]);
		}

		JanelaCondicoesDeUsinagem_comboBox_itemAdapter comboBoxListener = new JanelaCondicoesDeUsinagem_comboBox_itemAdapter(
				this);
		this.comboBox.addItemListener(comboBoxListener);
		this.ferramentaComboBox.addItemListener(comboBoxListener);

		JanelaCondicoesDeUsinagem_actionAdapter actionListener = new JanelaCondicoesDeUsinagem_actionAdapter(
				this);
		this.okButton.addActionListener(actionListener);
		this.cancelButton.addActionListener(actionListener);
		this.manualCheckBox.addChangeListener(actionListener);

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
		System.out.println(this.getLocation().toString());
	}

	public void materialSelecionado(int index) {
		switch (index) {
		case 0:// nada
			this.habilitarCampos(true);
			break;
		case 1:// cibatool
			this.habilitarCampos(false);
			this.condicoesDeUsinagem = new CondicoesDeUsinagem(
					Material.CIBATOOL, this.getFerramenta());
			this.atualizarCampos();
			break;
		case 2:// aluminio
			this.habilitarCampos(false);
			this.condicoesDeUsinagem = new CondicoesDeUsinagem(
					Material.ALUMINIO, this.getFerramenta());
			this.atualizarCampos();
			break;
		}
	}

	public void atualizarCampos() {
		this.vcTextField.setText(this.condicoesDeUsinagem.getVc() + "");
		this.vfTextField.setText(this.condicoesDeUsinagem.getVf() + "");
		this.apTextField.setText(this.condicoesDeUsinagem.getAp() + "");
		this.aeTextField.setText(this.condicoesDeUsinagem.getAe() + "");
		this.afTextField.setText(this.condicoesDeUsinagem.getAf() + "");
		this.fTextField.setText(this.condicoesDeUsinagem.getF() + "");
		this.nTextField.setText(this.condicoesDeUsinagem.getN() + "");
		if (this.condicoesDeUsinagem.isAcabamento())
			this.acabamentoCheckBox.setSelected(true);
		else
			this.acabamentoCheckBox.setSelected(false);
	}

	public void habilitarCampos(boolean mode) {
		this.vcTextField.setEnabled(mode);
		this.vfTextField.setEnabled(mode);
		this.apTextField.setEnabled(mode);
		this.aeTextField.setEnabled(mode);
		this.afTextField.setEnabled(mode);
		this.fTextField.setEnabled(mode);
		this.nTextField.setEnabled(mode);
		this.acabamentoCheckBox.setEnabled(mode);
	}

	public void ferramentaSelecionada(int index) {
		this.ferramenta = new Ferramenta();
	}

	public Ferramenta getFerramenta() {
		this.ferramentaSelecionada(0);

		return this.ferramenta;
	}

	public void okAction() {
		if (this.comboBox.getSelectedIndex() == 0
				&& !this.manualCheckBox.isSelected()) {
			// JOptionPane para solicitar que escolha um material
			JOptionPane
					.showMessageDialog(
							null,
							"Selecione um material ou insira manualmente as condi��es de usinagem.",
							"Erro", JOptionPane.ERROR_MESSAGE);
		} else if (this.manualCheckBox.isSelected()) {
			// verificar os campos
			boolean ok = true;
			double vc = 0;
			double vf = 0;
			double ap = 0;
			double ae = 0;
			double af = 0;
			double f = 0;
			int n = 0;
			boolean acabamento = false;

			try {
				vc = Double.parseDouble(this.vcTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de Vc.");
			}

			try {
				vf = Double.parseDouble(this.vfTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de Vf.");
			}

			try {
				ap = Double.parseDouble(this.apTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de Ap.");
			}

			try {
				ae = Double.parseDouble(this.aeTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de Ae.");
			}

			try {
				af = Double.parseDouble(this.afTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de Af.");
			}

			try {
				f = Double.parseDouble(this.fTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de F.");
			}

			try {
				n = Integer.parseInt(this.nTextField.getText());
			} catch (Exception e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valor inv�lido de N.");
			}

			acabamento = this.acabamentoCheckBox.isSelected();

			if (ok) {
				this.condicoesDeUsinagem = new CondicoesDeUsinagem(vc, vf, ap,
						ae, af, f, n, acabamento, this.getFerramenta());
				this.parent.condicoesAtuais = this.condicoesDeUsinagem;
				this.dispose();
			}
		} else {
			this.parent.condicoesAtuais = this.condicoesDeUsinagem;
			this.dispose();
		}

	}

	public void cancelAction() {
		this.dispose();
	}

}

class JanelaCondicoesDeUsinagem_actionAdapter implements ActionListener,
		ChangeListener {
	JanelaCondicoesDeUsinagem adaptee;

	public JanelaCondicoesDeUsinagem_actionAdapter(
			JanelaCondicoesDeUsinagem parent) {
		this.adaptee = parent;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source.equals(this.adaptee.okButton)) {
			this.adaptee.okAction();
		} else {
			this.adaptee.cancelAction();
		}
	}

	public void stateChanged(ChangeEvent changeEvent) {
		AbstractButton abstractButton = (AbstractButton) changeEvent
				.getSource();
		ButtonModel buttonModel = abstractButton.getModel();
		boolean selected = buttonModel.isSelected();
		this.adaptee.habilitarCampos(selected);

	}
}

class JanelaCondicoesDeUsinagem_comboBox_itemAdapter implements ItemListener {
	JanelaCondicoesDeUsinagem adaptee;

	public JanelaCondicoesDeUsinagem_comboBox_itemAdapter(
			JanelaCondicoesDeUsinagem parent) {
		this.adaptee = parent;
	}

	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();

		if (source.equals(this.adaptee.comboBox)) {
			int index = this.adaptee.comboBox.getSelectedIndex();
			this.adaptee.materialSelecionado(index);
		} else {
			int index = this.adaptee.ferramentaComboBox.getSelectedIndex();
			this.adaptee.ferramentaSelecionada(index);
		}
	}
}