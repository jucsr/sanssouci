/*
 * Created by JFormDesigner on Thu Dec 06 15:00:50 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class CreateTravelingAxisFrame extends JDialog {
	public CreateTravelingAxisFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateTravelingAxisFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		label12 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		spinner4 = new JSpinner();
		label3 = new JLabel();
		spinner5 = new JSpinner();
		label4 = new JLabel();
		spinner6 = new JSpinner();
		label9 = new JLabel();
		spinner1 = new JSpinner();
		label5 = new JLabel();
		comboBox1 = new JComboBox();
		label10 = new JLabel();
		spinner3 = new JSpinner();
		label11 = new JLabel();
		spinner2 = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Traveling Axis");
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

					//---- label12 ----
					label12.setText("Name");
					panel1.add(label12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- textField1 ----
					textField1.setText("TravelingAxis name");
					panel1.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label2 ----
					label2.setText("Origin x");
					panel1.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner4 ----
					spinner4.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label3 ----
					label3.setText("Origin y");
					panel1.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner5 ----
					spinner5.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label4 ----
					label4.setText("Origin z");
					panel1.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner6 ----
					spinner6.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label9 ----
					label9.setText("Max traveling");
					panel1.add(label9, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner1 ----
					spinner1.setModel(new SpinnerNumberModel(150.0, 0.0, null, 1.0));
					panel1.add(spinner1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label5 ----
					label5.setText("Direction");
					panel1.add(label5, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- comboBox1 ----
					comboBox1.setModel(new DefaultComboBoxModel(new String[] {
						"X",
						"Y",
						"Z"
					}));
					comboBox1.setMaximumRowCount(3);
					panel1.add(comboBox1, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label10 ----
					label10.setText("Feeding range");
					panel1.add(label10, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- spinner3 ----
					spinner3.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner3, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label11 ----
					label11.setText("Rapid Mov Speed (mm/min)");
					panel1.add(label11, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- spinner2 ----
					spinner2.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
					panel1.add(spinner2, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {108, 96};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
	private JLabel label12;
	protected JTextField textField1;
	private JLabel label2;
	protected JSpinner spinner4;
	private JLabel label3;
	protected JSpinner spinner5;
	private JLabel label4;
	protected JSpinner spinner6;
	private JLabel label9;
	protected JSpinner spinner1;
	private JLabel label5;
	protected JComboBox comboBox1;
	private JLabel label10;
	protected JSpinner spinner3;
	private JLabel label11;
	protected JSpinner spinner2;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
