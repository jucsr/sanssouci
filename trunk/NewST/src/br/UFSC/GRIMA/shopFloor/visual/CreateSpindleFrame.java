/*
 * Created by JFormDesigner on Tue Dec 11 13:27:21 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class CreateSpindleFrame extends JDialog {
	public CreateSpindleFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateSpindleFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel16 = new JPanel();
		label13 = new JLabel();
		textField1 = new JTextField();
		label14 = new JLabel();
		comboBox1 = new JComboBox();
		label1 = new JLabel();
		spinner1 = new JSpinner();
		label6 = new JLabel();
		spinner5 = new JSpinner();
		label9 = new JLabel();
		spinner6 = new JSpinner();
		label10 = new JLabel();
		spinner7 = new JSpinner();
		label11 = new JLabel();
		checkBox1 = new JCheckBox();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Create Spindle");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

				//======== panel16 ========
				{
					panel16.setLayout(new GridBagLayout());
					((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
					((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

					//---- label13 ----
					label13.setText("Name");
					panel16.add(label13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- textField1 ----
					textField1.setText("Spindle");
					panel16.add(textField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label14 ----
					label14.setText("Spindle type");
					panel16.add(label14, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- comboBox1 ----
					comboBox1.setModel(new DefaultComboBoxModel(new String[] {
						"Milling",
						"Turning"
					}));
					panel16.add(comboBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label1 ----
					label1.setText("Max cutting tool diameter (mm)");
					panel16.add(label1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner1 ----
					spinner1.setModel(new SpinnerNumberModel(20.0, 1.0, null, 1.0));
					panel16.add(spinner1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label6 ----
					label6.setText("Rotation speed range(rpm)");
					panel16.add(label6, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner5 ----
					spinner5.setModel(new SpinnerNumberModel(6000.0, 0.0, null, 1.0));
					panel16.add(spinner5, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label9 ----
					label9.setText("Max power (kW)");
					panel16.add(label9, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner6 ----
					spinner6.setModel(new SpinnerNumberModel(5.0, 0.0, null, 1.0));
					panel16.add(spinner6, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label10 ----
					label10.setText("Max torque (N-m)");
					panel16.add(label10, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner7 ----
					spinner7.setModel(new SpinnerNumberModel(7.0, 0.0, null, 1.0));
					panel16.add(spinner7, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label11 ----
					label11.setText("Coolant information");
					panel16.add(label11, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					panel16.add(checkBox1, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				}
				contentPanel.add(panel16, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
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
	private JPanel panel16;
	private JLabel label13;
	protected JTextField textField1;
	private JLabel label14;
	protected JComboBox comboBox1;
	protected JLabel label1;
	protected JSpinner spinner1;
	private JLabel label6;
	protected JSpinner spinner5;
	private JLabel label9;
	protected JSpinner spinner6;
	private JLabel label10;
	protected JSpinner spinner7;
	private JLabel label11;
	protected JCheckBox checkBox1;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
