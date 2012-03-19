/*
 * Created by JFormDesigner on Tue Nov 08 21:57:03 BRST 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import br.UFSC.GRIMA.cad.DesenhadorBezier;

/**
 * @author Victor
 */
public class PerfilBezierFrame extends JDialog {
	public PerfilBezierFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public PerfilBezierFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		panel1 = new JPanel();
		buttonBar = new JPanel();
		okButton = new JButton();
		panel3 = new JPanel();
		panel2 = new JPanel();
		toolBar1 = new JToolBar();
		button1 = new JButton();
		spinner1 = new JSpinner();
		button2 = new JButton();

		//======== this ========
		setTitle("Perfil Bezier preview");
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== scrollPane1 ========
				{

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {405, 0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 205, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
					}
					scrollPane1.setViewportView(panel1);
				}
				contentPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			//======== panel3 ========
			{
				panel3.setLayout(new GridBagLayout());
				((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

				//======== panel2 ========
				{
					panel2.setLayout(new FlowLayout());

					//======== toolBar1 ========
					{

						//---- button1 ----
						button1.setText("+zoom");
						toolBar1.add(button1);

						//---- spinner1 ----
						spinner1.setModel(new SpinnerNumberModel(100.0, 0.0, 500.0, 5.0));
						toolBar1.add(spinner1);

						//---- button2 ----
						button2.setText("-zoom");
						toolBar1.add(button2);
					}
					panel2.add(toolBar1);
				}
				panel3.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			}
			dialogPane.add(panel3, BorderLayout.NORTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	public JScrollPane scrollPane1;
	private JPanel panel1;
	private JPanel buttonBar;
	protected JButton okButton;
	private JPanel panel3;
	private JPanel panel2;
	private JToolBar toolBar1;
	protected JButton button1;
	protected JSpinner spinner1;
	protected JButton button2;
	public DesenhadorBezier desenhadorBezier;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
