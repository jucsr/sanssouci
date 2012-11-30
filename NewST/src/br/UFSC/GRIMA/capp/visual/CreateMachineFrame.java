/*
 * Created by JFormDesigner on Tue Nov 13 17:03:28 BRST 2012
 */

package br.UFSC.GRIMA.capp.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class CreateMachineFrame extends JDialog {
	public CreateMachineFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateMachineFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		radioButton1 = new JRadioButton();
		radioButton2 = new JRadioButton();
		radioButton3 = new JRadioButton();
		radioButton4 = new JRadioButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Create new Machine");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setBorder(new EtchedBorder());
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

					//---- radioButton1 ----
					radioButton1.setText("Drilling Machine");
					panel1.add(radioButton1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- radioButton2 ----
					radioButton2.setText("Milling Machine");
					radioButton2.setSelected(true);
					panel1.add(radioButton2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- radioButton3 ----
					radioButton3.setText("Turning Machine");
					radioButton3.setEnabled(false);
					panel1.add(radioButton3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- radioButton4 ----
					radioButton4.setText("Machining Center");
					radioButton4.setEnabled(false);
					panel1.add(radioButton4, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
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

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioButton1);
		buttonGroup1.add(radioButton2);
		buttonGroup1.add(radioButton3);
		buttonGroup1.add(radioButton4);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	public JRadioButton radioButton1;
	public JRadioButton radioButton2;
	public JRadioButton radioButton3;
	public JRadioButton radioButton4;
	private JPanel buttonBar;
	public JButton okButton;
	public JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
