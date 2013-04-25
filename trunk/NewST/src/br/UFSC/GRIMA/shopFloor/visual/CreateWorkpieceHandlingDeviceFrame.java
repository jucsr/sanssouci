/*
 * Created by JFormDesigner on Tue Dec 11 16:05:39 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class CreateWorkpieceHandlingDeviceFrame extends JDialog {
	public CreateWorkpieceHandlingDeviceFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateWorkpieceHandlingDeviceFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label6 = new JLabel();
		comboBox1 = new JComboBox();
		label2 = new JLabel();
		spinner1 = new JSpinner();
		label11 = new JLabel();
		label5 = new JLabel();
		spinner2 = new JSpinner();
		label12 = new JLabel();
		label4 = new JLabel();
		spinner3 = new JSpinner();
		label13 = new JLabel();
		label3 = new JLabel();
		spinner4 = new JSpinner();
		label14 = new JLabel();
		label7 = new JLabel();
		spinner5 = new JSpinner();
		label15 = new JLabel();
		label8 = new JLabel();
		spinner6 = new JSpinner();
		label16 = new JLabel();
		label10 = new JLabel();
		spinner7 = new JSpinner();
		label17 = new JLabel();
		label9 = new JLabel();
		spinner8 = new JSpinner();
		label18 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Add workpiece handling device");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 320, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 53, 0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

					//---- label1 ----
					label1.setText("Name");
					panel1.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- textField1 ----
					textField1.setText("Workpiece handling device");
					panel1.add(textField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label6 ----
					label6.setText("Type");
					panel1.add(label6, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- comboBox1 ----
					comboBox1.setModel(new DefaultComboBoxModel(new String[] {
						"Chuck",
						"Bar feeder",
						"Collet",
						"Pallet",
						"Table"
					}));
					comboBox1.setMaximumRowCount(5);
					panel1.add(comboBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label2 ----
					label2.setText("Max load capacity");
					panel1.add(label2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner1 ----
					spinner1.setModel(new SpinnerNumberModel(200, 0, null, 1));
					panel1.add(spinner1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label11 ----
					label11.setText("kg");
					panel1.add(label11, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label5 ----
					label5.setText("Origin x");
					panel1.add(label5, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner2 ----
					spinner2.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner2, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label12 ----
					label12.setText("mm");
					panel1.add(label12, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label4 ----
					label4.setText("Origin y");
					panel1.add(label4, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner3 ----
					spinner3.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner3, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label13 ----
					label13.setText("mm");
					panel1.add(label13, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label3 ----
					label3.setText("Origin z");
					panel1.add(label3, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner4 ----
					spinner4.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner4, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label14 ----
					label14.setText("mm");
					panel1.add(label14, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label7 ----
					label7.setText("Number of jaws");
					panel1.add(label7, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner5 ----
					spinner5.setModel(new SpinnerNumberModel(0, 0, null, 1));
					panel1.add(spinner5, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label15 ----
					label15.setText("mm");
					panel1.add(label15, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label8 ----
					label8.setText("Min diameter");
					panel1.add(label8, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner6 ----
					spinner6.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
					panel1.add(spinner6, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label16 ----
					label16.setText("mm");
					panel1.add(label16, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label10 ----
					label10.setText("Max diameter");
					panel1.add(label10, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner7 ----
					spinner7.setModel(new SpinnerNumberModel(100.0, 0.0, null, 1.0));
					panel1.add(spinner7, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label17 ----
					label17.setText("mm");
					panel1.add(label17, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label9 ----
					label9.setText("Max allowed length");
					panel1.add(label9, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner8 ----
					spinner8.setModel(new SpinnerNumberModel(600.0, 0.0, null, 1.0));
					panel1.add(spinner8, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label18 ----
					label18.setText("mm");
					panel1.add(label18, new GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JLabel label1;
	protected JTextField textField1;
	private JLabel label6;
	protected JComboBox comboBox1;
	private JLabel label2;
	protected JSpinner spinner1;
	private JLabel label11;
	private JLabel label5;
	protected JSpinner spinner2;
	private JLabel label12;
	private JLabel label4;
	protected JSpinner spinner3;
	private JLabel label13;
	private JLabel label3;
	protected JSpinner spinner4;
	private JLabel label14;
	private JLabel label7;
	protected JSpinner spinner5;
	private JLabel label15;
	private JLabel label8;
	protected JSpinner spinner6;
	private JLabel label16;
	private JLabel label10;
	protected JSpinner spinner7;
	private JLabel label17;
	private JLabel label9;
	protected JSpinner spinner8;
	private JLabel label18;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
