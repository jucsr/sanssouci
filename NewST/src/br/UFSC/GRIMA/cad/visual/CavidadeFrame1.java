/*
 * Created by JFormDesigner on Wed Nov 10 10:25:34 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Victor
 */
public class CavidadeFrame1 extends JDialog {
	public CavidadeFrame1(Frame owner) {
		super(owner);
		initComponents();
	}

	public CavidadeFrame1(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane2 = new JScrollPane();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel9 = new JPanel();
		label1 = new JLabel();
		panel10 = new JPanel();
		panel11 = new JPanel();
		label17 = new JLabel();
		label3 = new JLabel();
		panel12 = new JPanel();
		label19 = new JLabel();
		panel8 = new JPanel();
		label16 = new JLabel();
		textField9 = new JTextField();
		panel5 = new JPanel();
		label5 = new JLabel();
		textFieldPosX = new JTextField();
		label6 = new JLabel();
		textFieldPosY = new JTextField();
		label7 = new JLabel();
		posZtextField = new JFormattedTextField();
		label4 = new JLabel();
		raioTextField = new JTextField();
		label8 = new JLabel();
		banheiraTol = new JTextField();
		labelDepth = new JLabel();
		profundidadeTextField = new JTextField();
		label13 = new JLabel();
		comprimentoTextField = new JTextField();
		label14 = new JLabel();
		larguraTextField = new JTextField();
		label11 = new JLabel();
		banheiraRug = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Criar Cavidade");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane2 ========
		{

			//======== dialogPane ========
			{
				dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
				dialogPane.setLayout(new BorderLayout());

				//======== contentPanel ========
				{
					contentPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
					contentPanel.setLayout(new GridBagLayout());
					((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
					((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//======== panel9 ========
					{
						panel9.setBorder(new TitledBorder("Coloque os dados como no modelo"));
						panel9.setLayout(new GridBagLayout());
						((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//---- label1 ----
						label1.setIcon(new ImageIcon(getClass().getResource("/images/cavidadeModelo.GIF")));
						panel9.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					contentPanel.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel10 ========
					{
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel11 ========
						{
							panel11.setLayout(new GridBagLayout());
							((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

							//---- label17 ----
							label17.setText("Banheira em");
							panel11.add(label17, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label3 ----
							label3.setText("XY");
							label3.setForeground(Color.red);
							label3.setFont(new Font("Tahoma", Font.BOLD, 16));
							panel11.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel12 ========
						{
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label19 ----
							label19.setIcon(new ImageIcon(getClass().getResource("/images/Faces de referencia.JPG")));
							panel12.add(label19, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel12, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel8 ========
						{
							panel8.setBorder(new EtchedBorder(EtchedBorder.RAISED));
							panel8.setLayout(new GridBagLayout());
							((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {7, 0, 0, 0};
							((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label16 ----
							label16.setText("nome:");
							panel8.add(label16, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField9 ----
							textField9.setText("Cavidade com fundo arredondado");
							panel8.add(textField9, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel8, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setBorder(new EtchedBorder());
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 65, 0, 55, 0, 50, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//---- label5 ----
							label5.setText("X (mm)");
							label5.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textFieldPosX ----
							textFieldPosX.setText("10");
							panel5.add(textFieldPosX, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label6 ----
							label6.setText(" Y (mm)");
							label6.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label6, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textFieldPosY ----
							textFieldPosY.setText("10");
							panel5.add(textFieldPosY, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label7 ----
							label7.setText(" -Z (mm)");
							label7.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label7, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- posZtextField ----
							posZtextField.setText("0");
							panel5.add(posZtextField, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label4 ----
							label4.setText("raio  (mm)");
							label4.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- raioTextField ----
							raioTextField.setText("7.5");
							panel5.add(raioTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label8 ----
							label8.setText("+/- (um)");
							label8.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label8, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- banheiraTol ----
							banheiraTol.setText("50");
							panel5.add(banheiraTol, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- labelDepth ----
							labelDepth.setText("prof (mm)");
							labelDepth.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(labelDepth, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- profundidadeTextField ----
							profundidadeTextField.setText("20");
							panel5.add(profundidadeTextField, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label13 ----
							label13.setText("L1 (mm)");
							label13.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label13, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- comprimentoTextField ----
							comprimentoTextField.setText("50");
							panel5.add(comprimentoTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label14 ----
							label14.setText("L2 (mm)");
							label14.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label14, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- larguraTextField ----
							larguraTextField.setText("40");
							panel5.add(larguraTextField, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label11 ----
							label11.setText("rugosidade(um)");
							label11.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label11, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- banheiraRug ----
							banheiraRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel5.add(banheiraRug, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel5, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					contentPanel.add(panel10, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
			scrollPane2.setViewportView(dialogPane);
		}
		contentPane.add(scrollPane2, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane2;
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel9;
	private JLabel label1;
	private JPanel panel10;
	private JPanel panel11;
	private JLabel label17;
	public JLabel label3;
	private JPanel panel12;
	private JLabel label19;
	private JPanel panel8;
	private JLabel label16;
	protected JTextField textField9;
	private JPanel panel5;
	private JLabel label5;
	protected JTextField textFieldPosX;
	private JLabel label6;
	protected JTextField textFieldPosY;
	private JLabel label7;
	protected JFormattedTextField posZtextField;
	private JLabel label4;
	protected JTextField raioTextField;
	private JLabel label8;
	protected JTextField banheiraTol;
	private JLabel labelDepth;
	protected JTextField profundidadeTextField;
	private JLabel label13;
	protected JTextField comprimentoTextField;
	private JLabel label14;
	protected JTextField larguraTextField;
	private JLabel label11;
	protected JSpinner banheiraRug;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
