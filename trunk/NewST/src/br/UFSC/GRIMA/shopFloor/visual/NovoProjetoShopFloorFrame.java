/*
 * Created by JFormDesigner on Tue Dec 04 14:25:08 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class NovoProjetoShopFloorFrame extends JFrame {
	public NovoProjetoShopFloorFrame() {
		initComponents();
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		tabbedPane3 = new JTabbedPane();
		panel2 = new JPanel();
		panel5 = new JPanel();
		label1 = new JLabel();
		textField4 = new JTextField();
		label2 = new JLabel();
		label3 = new JLabel();
		label15 = new JLabel();
		textField5 = new JTextField();
		label14 = new JLabel();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();
		panel6 = new JPanel();
		panel9 = new JPanel();
		label8 = new JLabel();
		panel7 = new JPanel();
		label4 = new JLabel();
		spinnerX = new JSpinner();
		label9 = new JLabel();
		label5 = new JLabel();
		spinnerY = new JSpinner();
		label10 = new JLabel();
		panel10 = new JPanel();
		scrollPane2 = new JScrollPane();
		panel8 = new JPanel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("New Shop Floor Project");
		setResizable(false);
		setIconImage(new ImageIcon(getClass().getResource("/images/document-new16.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {500, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {280, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== tabbedPane3 ========
				{

					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

						//======== panel5 ========
						{
							panel5.setBorder(new EtchedBorder(EtchedBorder.RAISED));
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {15, 0, 205, 10, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {15, 0, 0, 0, 0, 10, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

							//---- label1 ----
							label1.setText("Project Name");
							label1.setFont(new Font("Courier New", Font.BOLD, 12));
							panel5.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField4 ----
							textField4.setText("New Project");
							textField4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
							panel5.add(textField4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label2 ----
							label2.setText("Designer");
							label2.setFont(new Font("Courier New", Font.BOLD, 11));
							panel5.add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label3 ----
							label3.setText("user");
							label3.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 18));
							label3.setForeground(new Color(39, 119, 17));
							panel5.add(label3, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label15 ----
							label15.setText("Organization");
							label15.setFont(new Font("Courier New", Font.BOLD, 11));
							panel5.add(label15, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField5 ----
							textField5.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
							textField5.setText("UFSC");
							panel5.add(textField5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label14 ----
							label14.setText("Description");
							label14.setFont(new Font("Courier New", Font.BOLD, 11));
							label14.setVerticalAlignment(SwingConstants.TOP);
							panel5.add(label14, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== panel1 ========
							{
								panel1.setLayout(new GridBagLayout());
								((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {150, 0};
								((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//======== scrollPane1 ========
								{

									//---- textPane1 ----
									textPane1.setText("Description");
									textPane1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
									scrollPane1.setViewportView(textPane1);
								}
								panel1.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(panel1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel2.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane3.addTab("Project", panel2);


					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel9 ========
						{
							panel9.setLayout(new GridBagLayout());
							((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
							((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

							//---- label8 ----
							label8.setText("Shop Floor Dimension");
							label8.setFont(new Font("Courier New", Font.BOLD, 12));
							label8.setHorizontalAlignment(SwingConstants.CENTER);
							panel9.add(label8, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== panel7 ========
							{
								panel7.setLayout(new GridBagLayout());
								((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 55, 0, 0};
								((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

								//---- label4 ----
								label4.setText("Length");
								label4.setFont(new Font("Courier New", Font.PLAIN, 12));
								label4.setHorizontalAlignment(SwingConstants.RIGHT);
								panel7.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinnerX ----
								spinnerX.setModel(new SpinnerNumberModel(200.0, 0.0, null, 1.0));
								panel7.add(spinnerX, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- label9 ----
								label9.setText("m");
								panel7.add(label9, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label5 ----
								label5.setText("Width");
								label5.setFont(new Font("Courier New", Font.PLAIN, 12));
								label5.setHorizontalAlignment(SwingConstants.RIGHT);
								panel7.add(label5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinnerY ----
								spinnerY.setModel(new SpinnerNumberModel(150.0, 0.0, null, 1.0));
								panel7.add(spinnerY, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- label10 ----
								label10.setText("m");
								panel7.add(label10, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel9.add(panel7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== panel10 ========
							{
								panel10.setLayout(new GridBagLayout());
								((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							}
							panel9.add(panel10, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel6.add(panel9, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== scrollPane2 ========
						{

							//======== panel8 ========
							{
								panel8.setBorder(new EtchedBorder());
								panel8.setLayout(new BorderLayout());
							}
							scrollPane2.setViewportView(panel8);
						}
						panel6.add(scrollPane2, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					tabbedPane3.addTab("Set Shop Floor", panel6);

				}
				contentPanel.add(tabbedPane3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
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
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
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
	private JTabbedPane tabbedPane3;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel label1;
	protected JTextField textField4;
	private JLabel label2;
	protected JLabel label3;
	private JLabel label15;
	protected JTextField textField5;
	private JLabel label14;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	protected JTextPane textPane1;
	protected JPanel panel6;
	private JPanel panel9;
	private JLabel label8;
	private JPanel panel7;
	protected JLabel label4;
	protected JSpinner spinnerX;
	private JLabel label9;
	protected JLabel label5;
	protected JSpinner spinnerY;
	private JLabel label10;
	private JPanel panel10;
	private JScrollPane scrollPane2;
	protected JPanel panel8;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
