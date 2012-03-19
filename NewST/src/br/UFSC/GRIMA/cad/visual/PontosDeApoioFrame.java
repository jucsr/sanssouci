/*
 * Created by JFormDesigner on Wed Sep 28 16:06:48 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Victor
 */
public class PontosDeApoioFrame extends JDialog {
	public PontosDeApoioFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public PontosDeApoioFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		label1 = new JLabel();
		buttonAutomatic = new JRadioButton();
		buttonManual = new JRadioButton();
		panel5 = new JPanel();
		label2 = new JLabel();
		comboBox1 = new JComboBox();
		scrollPane2 = new JScrollPane();
		pointsTable = new JTable();
		scrollPane1 = new JScrollPane();
		panel3 = new JPanel();
		label3 = new JLabel();
		raioCPField = new JTextField();
		panel4 = new JPanel();
		addClampPointButton = new JButton();
		removeClampPointButton = new JButton();
		buttonCalc = new JButton();
		buttonConfirm = new JButton();
		buttonCancel = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("Definir Pontos de Apoio");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"21dlu, $lcgap, 97dlu, $lcgap, 22dlu, $lcgap, 63dlu, $lcgap, 77dlu, $lcgap, 79dlu",
			"3*(default, $lgap), 116dlu, 3*($lgap, default)"));

		//======== panel1 ========
		{
			panel1.setLayout(new FormLayout(
				"default",
				"2*(default, $lgap), default"));

			//---- label1 ----
			label1.setText("Defini\u00e7\u00e3o de Pontos de Apoio:");
			panel1.add(label1, cc.xy(1, 1));

			//---- buttonAutomatic ----
			buttonAutomatic.setText("Autom\u00e1tica");
			panel1.add(buttonAutomatic, cc.xy(1, 3));

			//---- buttonManual ----
			buttonManual.setText("Manual");
			panel1.add(buttonManual, cc.xy(1, 5));
		}
		contentPane.add(panel1, cc.xy(3, 3));

		//======== panel5 ========
		{
			panel5.setLayout(new FormLayout(
				"default, $lcgap, default",
				"default, $lgap, default"));

			//---- label2 ----
			label2.setText("Quantidade de Pontos de Apoio:");
			panel5.add(label2, cc.xy(1, 1));

			//---- comboBox1 ----
			comboBox1.setModel(new DefaultComboBoxModel(new String[] {
				"3",
				"4",
				"5",
				"6",
				"7"
			}));
			panel5.add(comboBox1, cc.xy(1, 3));
		}
		contentPane.add(panel5, cc.xy(3, 5));

		//======== scrollPane2 ========
		{

			//---- pointsTable ----
			pointsTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Coord. X", "Coord. Y", "Coord. Z"
				}
			));
			scrollPane2.setViewportView(pointsTable);
		}
		contentPane.add(scrollPane2, cc.xywh(3, 7, 5, 1));

		//======== scrollPane1 ========
		{

			//======== panel3 ========
			{
				panel3.setLayout(new GridBagLayout());
				((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			}
			scrollPane1.setViewportView(panel3);
		}
		contentPane.add(scrollPane1, cc.xywh(9, 3, 3, 5));

		//---- label3 ----
		label3.setText("Raio dos Pontos de Apoio:");
		contentPane.add(label3, cc.xy(3, 9));
		contentPane.add(raioCPField, cc.xy(5, 9));

		//======== panel4 ========
		{
			panel4.setLayout(new FormLayout(
				"default, $lcgap, default",
				"default"));

			//---- addClampPointButton ----
			addClampPointButton.setText("Adicionar P. de Apoio");
			panel4.add(addClampPointButton, cc.xy(1, 1));
		}
		contentPane.add(panel4, cc.xy(3, 11));

		//---- removeClampPointButton ----
		removeClampPointButton.setText("Remover P. de Apoio");
		contentPane.add(removeClampPointButton, cc.xywh(5, 11, 3, 1));

		//---- buttonCalc ----
		buttonCalc.setText("Recalcular");
		contentPane.add(buttonCalc, cc.xywh(3, 13, 3, 1));

		//---- buttonConfirm ----
		buttonConfirm.setText("Confirmar Apoios");
		contentPane.add(buttonConfirm, cc.xy(9, 13));

		//---- buttonCancel ----
		buttonCancel.setText("Cancelar");
		contentPane.add(buttonCancel, cc.xy(11, 13));
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(buttonAutomatic);
		buttonGroup1.add(buttonManual);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JLabel label1;
	public JRadioButton buttonAutomatic;
	public JRadioButton buttonManual;
	private JPanel panel5;
	private JLabel label2;
	public JComboBox comboBox1;
	private JScrollPane scrollPane2;
	public JTable pointsTable;
	public JScrollPane scrollPane1;
	public JPanel panel3;
	private JLabel label3;
	public JTextField raioCPField;
	private JPanel panel4;
	public JButton addClampPointButton;
	public JButton removeClampPointButton;
	public JButton buttonCalc;
	public JButton buttonConfirm;
	public JButton buttonCancel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
