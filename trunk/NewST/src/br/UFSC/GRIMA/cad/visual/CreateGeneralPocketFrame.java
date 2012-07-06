/*
 * Created by JFormDesigner on Thu Jul 05 14:44:03 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Victor
 */
public class CreateGeneralPocketFrame extends JDialog {
	public CreateGeneralPocketFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateGeneralPocketFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		toolBar1 = new JToolBar();
		button2 = new JButton();
		button1 = new JButton();
		toolBar2 = new JToolBar();
		spinner1 = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Create General Closed Profile Pocket");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new BorderLayout());

				//======== panel1 ========
				{
					panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

					//======== toolBar1 ========
					{

						//---- button2 ----
						button2.setText("Help");
						toolBar1.add(button2);

						//---- button1 ----
						button1.setText("close curve");
						toolBar1.add(button1);
					}
					panel1.add(toolBar1);

					//======== toolBar2 ========
					{
						toolBar2.add(spinner1);
					}
					panel1.add(toolBar2);
				}
				contentPanel.add(panel1, BorderLayout.NORTH);
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
	protected JPanel contentPanel;
	private JPanel panel1;
	private JToolBar toolBar1;
	protected JButton button2;
	protected JButton button1;
	private JToolBar toolBar2;
	protected JSpinner spinner1;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
