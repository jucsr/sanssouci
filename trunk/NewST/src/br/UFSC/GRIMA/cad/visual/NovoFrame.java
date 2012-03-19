package br.UFSC.GRIMA.cad.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author julio cesar
 */
public class NovoFrame extends JFrame {
	private static final long serialVersionUID = 4270981382861234482L;

	public NovoFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - julio cesar
		dialogPane = new JPanel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		tabbedPane2 = new JTabbedPane();
		panel9 = new JPanel();
		panel10 = new JPanel();
		label21 = new JLabel();
		textField4 = new JTextField();
		panel8 = new JPanel();
		panel2 = new JPanel();
		label13 = new JLabel();
		panel4 = new JPanel();
		label14 = new JLabel();
		textField1 = new JTextField();
		label17 = new JLabel();
		label15 = new JLabel();
		textField2 = new JTextField();
		label18 = new JLabel();
		label16 = new JLabel();
		textField3 = new JTextField();
		label19 = new JLabel();
		panel6 = new JPanel();
		label20 = new JLabel();
		panel7 = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		comboBox3 = new JComboBox();
		label2 = new JLabel();
		panel3 = new JPanel();
		label3 = new JLabel();
		label6 = new JLabel();
		label9 = new JLabel();
		label4 = new JLabel();
		label7 = new JLabel();
		label10 = new JLabel();
		label5 = new JLabel();
		label8 = new JLabel();
		label11 = new JLabel();
		panel5 = new JPanel();
		labelMaquina = new JLabel();
		labelMaterial = new JLabel();

		// ======== this ========
		setTitle("Novo Projeto");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			/*
			 * // JFormDesigner evaluation mark dialogPane.setBorder(new
			 * javax.swing.border.CompoundBorder( new
			 * javax.swing.border.TitledBorder(new
			 * javax.swing.border.EmptyBorder(0, 0, 0, 0),
			 * "JFormDesigner Evaluation",
			 * javax.swing.border.TitledBorder.CENTER,
			 * javax.swing.border.TitledBorder.BOTTOM, new
			 * java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
			 * java.awt.Color.red), dialogPane.getBorder()));
			 * dialogPane.addPropertyChangeListener(new
			 * java.beans.PropertyChangeListener(){public void
			 * propertyChange(java.beans.PropertyChangeEvent
			 * e){if("border".equals(e.getPropertyName()))throw new
			 * RuntimeException();}});
			 */
			dialogPane.setLayout(new BorderLayout());

			// ======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] {
						0, 85, 80 };
				((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {
						1.0, 0.0, 0.0 };

				// ---- okButton ----
				okButton.setText("OK");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				okButton.setForeground(new Color(0, 0, 153));
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
				
				//---propertiesButton------

				// ---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				cancelButton.setForeground(new Color(0, 0, 153));
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			// ======== tabbedPane2 ========
			{
				tabbedPane2.setFont(new Font("Tahoma", Font.BOLD, 11));
				tabbedPane2.setForeground(new Color(0, 0, 153));

				// ======== panel9 ========
				{
					panel9.setLayout(new BorderLayout());

					// ======== panel10 ========
					{
						panel10.setBorder(new EtchedBorder());
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout) panel10.getLayout()).columnWidths = new int[] {
								8, 0, 105, 0, 0 };
						((GridBagLayout) panel10.getLayout()).rowHeights = new int[] {
								8, 0, 0, 0 };
						((GridBagLayout) panel10.getLayout()).columnWeights = new double[] {
								1.0, 0.0, 0.0, 1.0, 1.0E-4 };
						((GridBagLayout) panel10.getLayout()).rowWeights = new double[] {
								1.0, 0.0, 1.0, 1.0E-4 };

						// ---- label21 ----
						label21.setText("Nome do Projeto:");
						label21.setFont(new Font("Tahoma", Font.BOLD, 11));
						label21.setForeground(new Color(0, 0, 153));
						panel10.add(label21, new GridBagConstraints(1, 1, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ---- textField4 ----
						textField4.setText("Untitled");
						panel10.add(textField4, new GridBagConstraints(2, 1, 1,
								1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
					}
					panel9.add(panel10, BorderLayout.CENTER);
				}
				tabbedPane2.addTab("Projeto", panel9);

				// ======== panel8 ========
				{
					panel8.setLayout(new BorderLayout());

					// ======== panel2 ========
					{
						panel2.setFont(new Font("Tahoma", Font.BOLD, 14));
						panel2.setBorder(new TitledBorder(null, "Bloco",
								TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 153)));
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout) panel2.getLayout()).columnWidths = new int[] {
								8, 0, 8, 0, 3, 0 };
						((GridBagLayout) panel2.getLayout()).rowHeights = new int[] {
								8, 0, 3, 0 };
						((GridBagLayout) panel2.getLayout()).columnWeights = new double[] {
								0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4 };
						((GridBagLayout) panel2.getLayout()).rowWeights = new double[] {
								0.0, 0.0, 0.0, 1.0E-4 };

						// ---- label13 ----
						label13.setText("Dimens\u00f5es do bloco:");
						label13.setFont(new Font("Tahoma", Font.BOLD, 11));
						label13.setForeground(new Color(0, 0, 153));

						panel2.add(label13, new GridBagConstraints(1, 0, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ======== panel4 ========
						{
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout) panel4.getLayout()).columnWidths = new int[] {
									0, 45, 0, 0, 0 };
							((GridBagLayout) panel4.getLayout()).rowHeights = new int[] {
									0, 0, 0, 0, 0, 0 };
							((GridBagLayout) panel4.getLayout()).columnWeights = new double[] {
									0.0, 0.0, 0.0, 0.0, 1.0E-4 };
							((GridBagLayout) panel4.getLayout()).rowWeights = new double[] {
									1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4 };

							// ---- label14 ----
							label14.setText("Comprimento (X):");
							label14
									.setHorizontalAlignment(SwingConstants.RIGHT);
							label14.setFont(new Font("Tahoma", Font.BOLD, 11));
							label14.setForeground(new Color(0, 0, 153));
							panel4.add(label14, new GridBagConstraints(0, 1, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- textField1 ----
							textField1.setText("200");
							panel4.add(textField1, new GridBagConstraints(1, 1,
									1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label17 ----
							label17.setText("mm");
							label17.setFont(new Font("Tahoma", Font.BOLD, 11));
							label17.setForeground(new Color(0, 0, 153));
							panel4.add(label17, new GridBagConstraints(2, 1, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label15 ----
							label15.setText("Largura (Y):");
							label15
									.setHorizontalAlignment(SwingConstants.RIGHT);
							label15.setFont(new Font("Tahoma", Font.BOLD, 11));
							label15.setForeground(new Color(0, 0, 153));
							panel4.add(label15, new GridBagConstraints(0, 2, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- textField2 ----
							textField2.setText("150");
							panel4.add(textField2, new GridBagConstraints(1, 2,
									1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label18 ----
							label18.setText("mm");
							label18.setFont(new Font("Tahoma", Font.BOLD, 11));
							label18.setForeground(new Color(0, 0, 153));
							panel4.add(label18, new GridBagConstraints(2, 2, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label16 ----
							label16.setText("Altura (Z):");
							label16
									.setHorizontalAlignment(SwingConstants.RIGHT);
							label16.setFont(new Font("Tahoma", Font.BOLD, 11));
							label16.setForeground(new Color(0, 0, 153));
							panel4.add(label16, new GridBagConstraints(0, 3, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- textField3 ----
							textField3.setText("13");
							panel4.add(textField3, new GridBagConstraints(1, 3,
									1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label19 ----
							label19.setText("mm");
							label19.setFont(new Font("Tahoma", Font.BOLD, 11));
							label19.setForeground(new Color(0, 0, 153));
							panel4.add(label19, new GridBagConstraints(2, 3, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));
						}
						panel2.add(panel4, new GridBagConstraints(1, 1, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ========ComboBox Material==========
						
						comboBox3.setFont(new Font("Tahoma", Font.BOLD, 11));
						comboBox3.setForeground(new Color(0, 0, 153));
						labelMaterial.setText("Material: ");
						labelMaterial.setFont(new Font("Tahoma", Font.BOLD, 11));
						labelMaterial.setForeground(new Color(0, 0, 153));
						labelMaterial.setHorizontalAlignment(SwingConstants.RIGHT);
						
						
						panel4.add(labelMaterial, new GridBagConstraints(0, 4,
								1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						panel4.add(comboBox3, new GridBagConstraints(1, 4, 
								1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ======== panel6 ========
						{
							panel6.setBorder(new EtchedBorder());
							panel6.setLayout(new GridBagLayout());
							((GridBagLayout) panel6.getLayout()).columnWidths = new int[] {
									0, 0 };
							((GridBagLayout) panel6.getLayout()).rowHeights = new int[] {
									0, 0 };
							((GridBagLayout) panel6.getLayout()).columnWeights = new double[] {
									0.0, 1.0E-4 };
							((GridBagLayout) panel6.getLayout()).rowWeights = new double[] {
									0.0, 1.0E-4 };

							// ---- label20 ----
							URL facesdeReferencia = getClass().getResource(
									"/images/Faces de referencia.JPG");

							label20.setIcon(new ImageIcon(facesdeReferencia));
							panel6.add(label20, new GridBagConstraints(0, 0, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 0), 0, 0));
						}
						panel2.add(panel6, new GridBagConstraints(3, 1, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
					}
					panel8.add(panel2, BorderLayout.CENTER);
				}
				tabbedPane2.addTab("Pe\u00e7a Bruta", panel8);

				// ======== panel7 ========
				{
					panel7.setLayout(new BorderLayout());

					// ======== panel1 ========
					{
						panel1.setBorder(new TitledBorder(null, "Maquina",
								TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(51, 0, 153)));
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout) panel1.getLayout()).columnWidths = new int[] {
								8, 0, 8, 0, 3, 0 };
						((GridBagLayout) panel1.getLayout()).rowHeights = new int[] {
								0, 0, 0, 0, 3, 0 };
						((GridBagLayout) panel1.getLayout()).columnWeights = new double[] {
								0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4 };
						((GridBagLayout) panel1.getLayout()).rowWeights = new double[] {
								0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

						// ---- label1 ----
						label1.setText("Selecione uma maquina:");
						label1.setFont(new Font("Tahoma", Font.BOLD, 13));
						label1.setForeground(new Color(0, 0, 153));
						label1.setHorizontalAlignment(SwingConstants.RIGHT);
						panel1.add(label1, new GridBagConstraints(0, 1, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						panel1.add(comboBox1, new GridBagConstraints(1, 1, 1,
								1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ---- Combo box de ferramentas -----

						

						// ---- label2 ----
						label2.setText("Dimens\u00f5es m\u00e1ximas:");
						label2.setFont(new Font("Tahoma", Font.BOLD, 13));
						label2.setForeground(new Color(0, 0, 153));

						// ======== panel3 ========
						{
							panel3.setLayout(new GridBagLayout());
							((GridBagLayout) panel3.getLayout()).columnWidths = new int[] {
									0, 25, 0, 0 };
							((GridBagLayout) panel3.getLayout()).rowHeights = new int[] {
									0, 0, 0, 0, 0, 0 };
							((GridBagLayout) panel3.getLayout()).columnWeights = new double[] {
									0.0, 0.0, 0.0, 1.0E-4 };
							((GridBagLayout) panel3.getLayout()).rowWeights = new double[] {
									1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4 };

							panel3.add(label2, new GridBagConstraints(0, 1, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));
							// ---- label3 ----
							label3.setText("Comprimento (X):");
							label3.setHorizontalAlignment(SwingConstants.RIGHT);
							label3.setFont(new Font("Tahoma", Font.BOLD, 11));
							label3.setForeground(new Color(0, 0, 153));
							
							
							
							panel3.add(label3, new GridBagConstraints(0, 2, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label6 ----
							label6.setText("200");
							label6.setFont(new Font("Century Gothic",
									Font.BOLD, 14));
							label6.setForeground(new Color(0, 153, 0));
							label6
									.setHorizontalAlignment(SwingConstants.CENTER);
							panel3.add(label6, new GridBagConstraints(1, 2, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label9 ----
							label9.setText("mm");
							label9.setFont(new Font("Tahoma", Font.BOLD, 11));
							label9.setForeground(new Color(0, 0, 153));
							panel3.add(label9, new GridBagConstraints(2, 2, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 0), 0, 0));

							// ---- label4 ----
							label4.setText("Largura (Y):");
							label4.setHorizontalAlignment(SwingConstants.RIGHT);
							label4.setFont(new Font("Tahoma", Font.BOLD, 11));
							label4.setForeground(new Color(0, 0, 153));
							panel3.add(label4, new GridBagConstraints(0, 3, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label7 ----
							label7.setText("150");
							label7.setFont(new Font("Century Gothic",
									Font.BOLD, 14));
							label7.setForeground(new Color(0, 153, 0));
							label7
									.setHorizontalAlignment(SwingConstants.CENTER);
							panel3.add(label7, new GridBagConstraints(1, 3, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label10 ----
							label10.setText("mm");
							label10.setFont(new Font("Tahoma", Font.BOLD, 11));
							label10.setForeground(new Color(0, 0, 153));
							panel3.add(label10, new GridBagConstraints(2, 3, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 0), 0, 0));

							// ---- label5 ----
							label5.setText("Profundidade (Z):");
							label5.setHorizontalAlignment(SwingConstants.RIGHT);
							label5.setFont(new Font("Tahoma", Font.BOLD, 11));
							label5.setForeground(new Color(0, 0, 153));
							panel3.add(label5, new GridBagConstraints(0, 4, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label8 ----
							label8.setText("10");
							label8.setFont(new Font("Century Gothic",
									Font.BOLD, 14));
							label8.setForeground(new Color(0, 153, 0));
							label8
									.setHorizontalAlignment(SwingConstants.CENTER);
							panel3.add(label8, new GridBagConstraints(1, 4, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 5), 0, 0));

							// ---- label11 ----
							label11.setText("mm");
							label11.setFont(new Font("Tahoma", Font.BOLD, 11));
							label11.setForeground(new Color(0, 0, 153));
							panel3.add(label11, new GridBagConstraints(2, 4, 1,
									1, 0.0, 0.0, GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 0), 0, 0));
						}
						panel1.add(panel3, new GridBagConstraints(1, 3, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

						// ======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout) panel5.getLayout()).columnWidths = new int[] {
									0, 0 };
							((GridBagLayout) panel5.getLayout()).rowHeights = new int[] {
									0, 0 };
							((GridBagLayout) panel5.getLayout()).columnWeights = new double[] {
									0.0, 1.0E-4 };
							((GridBagLayout) panel5.getLayout()).rowWeights = new double[] {
									0.0, 1.0E-4 };

							// ---- label12 ----
							URL pr_ban_mdx20_url = getClass().getResource(
									"/images/pr_ban_mdx20.jpg");
							labelMaquina
									.setIcon(new ImageIcon(pr_ban_mdx20_url));
							labelMaquina.setBorder(new EtchedBorder());
							panel5.add(labelMaquina, new GridBagConstraints(0,
									0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 0), 0, 0));
						}
						panel1.add(panel5, new GridBagConstraints(3, 3, 1, 1,
								0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						
						
					}
					panel7.add(panel1, BorderLayout.CENTER);
				}
				tabbedPane2.addTab("Maquinas", panel7);

			}
			dialogPane.add(tabbedPane2, BorderLayout.NORTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - julio cesar
	protected JPanel dialogPane;
	protected JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JTabbedPane tabbedPane2;
	protected JPanel panel9;
	protected JPanel panel10;
	protected JLabel label21;
	protected JTextField textField4;
	protected JPanel panel8;
	protected JPanel panel2;
	protected JLabel label13;
	protected JPanel panel4;
	protected JLabel label14;
	protected JTextField textField1;
	protected JLabel label17;
	protected JLabel label15;
	protected JTextField textField2;
	protected JLabel label18;
	protected JLabel label16;
	protected JTextField textField3;
	protected JLabel label19;
	protected JPanel panel6;
	protected JLabel label20;
	protected JPanel panel7;
	protected JPanel panel1;
	protected JLabel label1;
	protected JComboBox comboBox1;
	protected JComboBox comboBox3;
	protected JLabel label2;
	protected JPanel panel3;
	protected JLabel label3;
	protected JLabel label6;
	protected JLabel label9;
	protected JLabel label4;
	protected JLabel label7;
	protected JLabel label10;
	protected JLabel label5;
	protected JLabel label8;
	protected JLabel label11;
	protected JPanel panel5;
	protected JLabel labelMaquina;
	protected JLabel labelMaterial;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
