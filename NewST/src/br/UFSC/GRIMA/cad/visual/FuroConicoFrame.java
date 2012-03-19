/*
 * Created by JFormDesigner on Wed Nov 17 17:35:28 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Victor
 */
public class FuroConicoFrame extends JDialog {
	public FuroConicoFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public FuroConicoFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane2 = new JScrollPane();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel9 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner1 = new JSpinner();
		spinner4 = new JSpinner();
		spinner2 = new JSpinner();
		raio1 = new JSpinner();
		spinner5 = new JSpinner();
		spinner6 = new JSpinner();
		label1 = new JLabel();
		panel10 = new JPanel();
		panel11 = new JPanel();
		label17 = new JLabel();
		label3 = new JLabel();
		panel12 = new JPanel();
		label19 = new JLabel();
		panel8 = new JPanel();
		nameLabel = new JLabel();
		textField9 = new JTextField();
		panel5 = new JPanel();
		labelDepth = new JLabel();
		spinner3 = new JSpinner();
		label8 = new JLabel();
		label11 = new JLabel();
		furoRug = new JSpinner();
		label9 = new JLabel();
		label2 = new JLabel();
		checkBox1 = new JCheckBox();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Criar c\u00f4nico");
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
						panel9.setBorder(new TitledBorder("Dimens\u00f5es em mil\u00edmetros"));
						panel9.setLayout(new GridBagLayout());
						((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 373, 0, 0};
						((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 397, 0, 0};
						((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//======== layeredPane1 ========
						{

							//---- spinner1 ----
							spinner1.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
							layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
							spinner1.setBounds(260, 305, 40, spinner1.getPreferredSize().height);

							//---- spinner4 ----
							spinner4.setModel(new SpinnerNumberModel(30.0, 0.0, null, 1.0));
							layeredPane1.add(spinner4, JLayeredPane.DEFAULT_LAYER);
							spinner4.setBounds(115, 165, 40, spinner4.getPreferredSize().height);

							//---- spinner2 ----
							spinner2.setModel(new SpinnerNumberModel(7.5, 0.0, null, 1.0));
							layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
							spinner2.setBounds(110, 70, 40, spinner2.getPreferredSize().height);

							//---- raio1 ----
							raio1.setModel(new SpinnerNumberModel(3.0, 0.0, 50.0, 1.0));
							layeredPane1.add(raio1, JLayeredPane.DEFAULT_LAYER);
							raio1.setBounds(265, 70, 40, 21);

							//---- spinner5 ----
							spinner5.setModel(new SpinnerNumberModel(30.0, 0.0, null, 1.0));
							layeredPane1.add(spinner5, JLayeredPane.DEFAULT_LAYER);
							spinner5.setBounds(255, 160, 40, spinner5.getPreferredSize().height);

							//---- spinner6 ----
							spinner6.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane1.add(spinner6, JLayeredPane.DEFAULT_LAYER);
							spinner6.setBounds(5, 275, 40, spinner6.getPreferredSize().height);

							//---- label1 ----
							label1.setIcon(new ImageIcon(getClass().getResource("/images/FuroConico.png")));
							layeredPane1.add(label1, JLayeredPane.DEFAULT_LAYER);
							label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));
						}
						panel9.add(layeredPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
							label17.setText("Furo em");
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
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label19 ----
							label19.setIcon(new ImageIcon(getClass().getResource("/images/Faces de referencia.png")));
							panel12.add(label19, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
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

							//---- nameLabel ----
							nameLabel.setText("nome:");
							panel8.add(nameLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField9 ----
							textField9.setText("Furo conico");
							textField9.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
							panel8.add(textField9, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel8, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setBorder(new CompoundBorder(
								new TitledBorder("Par\u00e2metros adicionais"),
								new EmptyBorder(5, 5, 5, 5)));
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 45, 0, 40, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//---- labelDepth ----
							labelDepth.setText("toler\u00e2ncias dos di\u00e1metros");
							labelDepth.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(labelDepth, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- spinner3 ----
							spinner3.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel5.add(spinner3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label8 ----
							label8.setText("+/- (um)");
							label8.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label8, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label11 ----
							label11.setText("textura das superf\u00edcies");
							panel5.add(label11, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- furoRug ----
							furoRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel5.add(furoRug, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label9 ----
							label9.setText("(um)");
							label9.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label9, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label2 ----
							label2.setText("furo passante");
							label2.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
							panel5.add(checkBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
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
	private JLayeredPane layeredPane1;
	protected JSpinner spinner1;
	protected JSpinner spinner4;
	protected JSpinner spinner2;
	protected JSpinner raio1;
	protected JSpinner spinner5;
	protected JSpinner spinner6;
	private JLabel label1;
	private JPanel panel10;
	private JPanel panel11;
	private JLabel label17;
	protected JLabel label3;
	private JPanel panel12;
	private JLabel label19;
	private JPanel panel8;
	private JLabel nameLabel;
	protected JTextField textField9;
	private JPanel panel5;
	private JLabel labelDepth;
	protected JSpinner spinner3;
	private JLabel label8;
	private JLabel label11;
	protected JSpinner furoRug;
	private JLabel label9;
	private JLabel label2;
	protected JCheckBox checkBox1;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
