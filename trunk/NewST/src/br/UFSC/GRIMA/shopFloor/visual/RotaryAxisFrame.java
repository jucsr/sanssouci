/*
 * Created by JFormDesigner on Wed Dec 05 14:45:22 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class RotaryAxisFrame extends JDialog {
	public RotaryAxisFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public RotaryAxisFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void radioButton1ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void radioButton3ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void okButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label4 = new JLabel();
		textField1 = new JTextField();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		label2 = new JLabel();
		spinner1 = new JSpinner();
		label3 = new JLabel();
		comboBox2 = new JComboBox();
		label5 = new JLabel();
		spinner2 = new JSpinner();
		label6 = new JLabel();
		spinner3 = new JSpinner();
		label7 = new JLabel();
		spinner4 = new JSpinner();
		buttonBar = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();

		//======== this ========
		setTitle("Rotary Axis");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {27, 98, 48, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- label4 ----
				label4.setText("Name");
				contentPanel.add(label4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField1 ----
				textField1.setText("Axis name");
				contentPanel.add(textField1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label1 ----
				label1.setText("Rotary Axis");
				contentPanel.add(label1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- comboBox1 ----
				comboBox1.setModel(new DefaultComboBoxModel(new String[] {
					"x",
					"y"
				}));
				contentPanel.add(comboBox1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label2 ----
				label2.setText("Max Rotation Speed (rpm)");
				contentPanel.add(label2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- spinner1 ----
				spinner1.setModel(new SpinnerNumberModel(50.0, 0.0, null, 1.0));
				contentPanel.add(spinner1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label3 ----
				label3.setText("Rotary Direction");
				contentPanel.add(label3, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- comboBox2 ----
				comboBox2.setModel(new DefaultComboBoxModel(new String[] {
					"CCW",
					"CW",
					"CW & CCW"
				}));
				contentPanel.add(comboBox2, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label5 ----
				label5.setText("Origin x");
				contentPanel.add(label5, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- spinner2 ----
				spinner2.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				contentPanel.add(spinner2, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label6 ----
				label6.setText("Origin y");
				contentPanel.add(label6, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- spinner3 ----
				spinner3.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				contentPanel.add(spinner3, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label7 ----
				label7.setText("Origin z");
				contentPanel.add(label7, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- spinner4 ----
				spinner4.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				contentPanel.add(spinner4, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
	private JLabel label4;
	protected JTextField textField1;
	private JLabel label1;
	protected JComboBox comboBox1;
	private JLabel label2;
	protected JSpinner spinner1;
	private JLabel label3;
	protected JComboBox comboBox2;
	private JLabel label5;
	protected JSpinner spinner2;
	private JLabel label6;
	protected JSpinner spinner3;
	private JLabel label7;
	protected JSpinner spinner4;
	private JPanel buttonBar;
	protected JButton cancelButton;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
