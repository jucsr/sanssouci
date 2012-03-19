/*
 * Created by JFormDesigner on Thu Nov 18 16:57:27 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

/**
 * @author Victor
 */
public class RanhuraPerfilUFrame extends JDialog {
	public RanhuraPerfilUFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public RanhuraPerfilUFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane2 = new JScrollPane();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel3 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinnerDelocamento = new JSpinner();
		spinnerZ = new JSpinner();
		spinner5 = new JSpinner();
		labelImage = new JLabel();
		panel2 = new JPanel();
		panel5 = new JPanel();
		label2 = new JLabel();
		label10 = new JLabel();
		panel4 = new JPanel();
		layeredPane2 = new JLayeredPane();
		spinner3 = new JSpinner();
		spinner4 = new JSpinner();
		spinner2 = new JSpinner();
		spinner1 = new JSpinner();
		radioButton2 = new JRadioButton();
		radioButton1 = new JRadioButton();
		label4 = new JLabel();
		panel1 = new JPanel();
		label1 = new JLabel();
		textFieldNome = new JTextField();
		panel6 = new JPanel();
		radioButtonX = new JRadioButton();
		radioButtonY = new JRadioButton();
		panel7 = new JPanel();
		label9 = new JLabel();
		ranhuraTol = new JSpinner();
		label5 = new JLabel();
		label8 = new JLabel();
		ranhuraRug = new JSpinner();
		label3 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Criar Ranhura com perfil U");
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

					//======== panel3 ========
					{
						panel3.setBorder(new TitledBorder("Dimens\u00f5es em mil\u00edmetros"));
						panel3.setLayout(new GridBagLayout());
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 517, 0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 397, 0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//======== layeredPane1 ========
						{

							//---- spinnerDelocamento ----
							spinnerDelocamento.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
							layeredPane1.add(spinnerDelocamento, JLayeredPane.DEFAULT_LAYER);
							spinnerDelocamento.setBounds(135, 165, 40, spinnerDelocamento.getPreferredSize().height);

							//---- spinnerZ ----
							spinnerZ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane1.add(spinnerZ, JLayeredPane.DEFAULT_LAYER);
							spinnerZ.setBounds(10, 275, 40, spinnerZ.getPreferredSize().height);

							//---- spinner5 ----
							spinner5.setModel(new SpinnerNumberModel(7.0, 1.0, null, 1.0));
							layeredPane1.add(spinner5, JLayeredPane.DEFAULT_LAYER);
							spinner5.setBounds(110, 305, 40, 21);

							//---- labelImage ----
							labelImage.setIcon(new ImageIcon(getClass().getResource("/images/RanhuraPerfilUQuadradoHorizontal.png")));
							layeredPane1.add(labelImage, JLayeredPane.DEFAULT_LAYER);
							labelImage.setBounds(new Rectangle(new Point(0, 0), labelImage.getPreferredSize()));
						}
						panel3.add(layeredPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					contentPanel.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- label2 ----
							label2.setText("Ranhura na face");
							panel5.add(label2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- label10 ----
							label10.setText("XY");
							label10.setFont(new Font("Tahoma", Font.BOLD, 16));
							label10.setForeground(Color.red);
							panel5.add(label10, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel2.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel4 ========
						{
							panel4.setBorder(new TitledBorder("Perfil (\u00e1ngulo em graus sexagesimais)"));
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {250, 0};
							((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 170, 0, 0};
							((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
							((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//======== layeredPane2 ========
							{

								//---- spinner3 ----
								spinner3.setModel(new SpinnerNumberModel(10.0, 1.0, null, 1.0));
								layeredPane2.add(spinner3, JLayeredPane.DEFAULT_LAYER);
								spinner3.setBounds(110, 25, 40, 21);

								//---- spinner4 ----
								spinner4.setModel(new SpinnerNumberModel(2.0, 0.0, null, 1.0));
								layeredPane2.add(spinner4, JLayeredPane.DEFAULT_LAYER);
								spinner4.setBounds(110, 65, 40, spinner4.getPreferredSize().height);

								//---- spinner2 ----
								spinner2.setModel(new SpinnerNumberModel(10.0, 1.0, null, 1.0));
								spinner2.setEnabled(false);
								layeredPane2.add(spinner2, JLayeredPane.DEFAULT_LAYER);
								spinner2.setBounds(115, 125, 40, 21);

								//---- spinner1 ----
								spinner1.setModel(new SpinnerNumberModel(90.0, 0.0, 90.0, 1.0));
								layeredPane2.add(spinner1, JLayeredPane.DEFAULT_LAYER);
								spinner1.setBounds(170, 55, 40, 21);

								//---- radioButton2 ----
								radioButton2.setHorizontalAlignment(SwingConstants.RIGHT);
								radioButton2.setBackground(Color.white);
								layeredPane2.add(radioButton2, JLayeredPane.DEFAULT_LAYER);
								radioButton2.setBounds(95, 125, 20, radioButton2.getPreferredSize().height);

								//---- radioButton1 ----
								radioButton1.setHorizontalAlignment(SwingConstants.RIGHT);
								radioButton1.setSelected(true);
								radioButton1.setBackground(Color.white);
								layeredPane2.add(radioButton1, JLayeredPane.DEFAULT_LAYER);
								radioButton1.setBounds(210, 55, 20, radioButton1.getPreferredSize().height);

								//---- label4 ----
								label4.setIcon(new ImageIcon(getClass().getResource("/images/PerfilUQuadrado.png")));
								layeredPane2.add(label4, JLayeredPane.DEFAULT_LAYER);
								label4.setBounds(new Rectangle(new Point(0, 0), label4.getPreferredSize()));
							}
							panel4.add(layeredPane2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel2.add(panel4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel1 ========
						{
							panel1.setLayout(new GridBagLayout());
							((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label1 ----
							label1.setText("nome:");
							panel1.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textFieldNome ----
							textFieldNome.setText("ranhura com perfil U");
							textFieldNome.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
							panel1.add(textFieldNome, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel2.add(panel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel6 ========
						{
							panel6.setBorder(new TitledBorder("Dire\u00e7\u00e3o"));
							panel6.setLayout(new GridBagLayout());
							((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- radioButtonX ----
							radioButtonX.setText("horizontal");
							radioButtonX.setSelected(true);
							panel6.add(radioButtonX, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- radioButtonY ----
							radioButtonY.setText("vertical");
							panel6.add(radioButtonY, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel2.add(panel6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel7 ========
						{
							panel7.setBorder(new CompoundBorder(
								new TitledBorder("Par\u00e2metros adicionais"),
								Borders.DLU2_BORDER));
							panel7.setLayout(new GridBagLayout());
							((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 55, 0, 0};
							((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

							//---- label9 ----
							label9.setHorizontalAlignment(SwingConstants.RIGHT);
							label9.setText("toler\u00e2ncia dos r\u00e1ios");
							panel7.add(label9, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- ranhuraTol ----
							ranhuraTol.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel7.add(ranhuraTol, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label5 ----
							label5.setText("+/- um");
							panel7.add(label5, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label8 ----
							label8.setText("textura das superf\u00edcies ");
							label8.setHorizontalAlignment(SwingConstants.RIGHT);
							panel7.add(label8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- ranhuraRug ----
							ranhuraRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel7.add(ranhuraRug, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- label3 ----
							label3.setText("um");
							panel7.add(label3, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel2.add(panel7, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					contentPanel.add(panel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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

		//---- buttonGroup2 ----
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(radioButton2);
		buttonGroup2.add(radioButton1);

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioButtonX);
		buttonGroup1.add(radioButtonY);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane2;
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel3;
	private JLayeredPane layeredPane1;
	protected JSpinner spinnerDelocamento;
	protected JSpinner spinnerZ;
	protected JSpinner spinner5;
	protected JLabel labelImage;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel label2;
	protected JLabel label10;
	private JPanel panel4;
	private JLayeredPane layeredPane2;
	protected JSpinner spinner3;
	protected JSpinner spinner4;
	protected JSpinner spinner2;
	protected JSpinner spinner1;
	protected JRadioButton radioButton2;
	protected JRadioButton radioButton1;
	private JLabel label4;
	private JPanel panel1;
	private JLabel label1;
	protected JTextField textFieldNome;
	private JPanel panel6;
	protected JRadioButton radioButtonX;
	protected JRadioButton radioButtonY;
	private JPanel panel7;
	private JLabel label9;
	protected JSpinner ranhuraTol;
	private JLabel label5;
	private JLabel label8;
	protected JSpinner ranhuraRug;
	private JLabel label3;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
