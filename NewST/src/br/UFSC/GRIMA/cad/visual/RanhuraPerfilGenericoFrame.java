/*
 * Created by JFormDesigner on Tue Nov 30 20:09:30 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import br.UFSC.GRIMA.cad.DesenhadorBezier;

/**
 * @author Victor
 */
public class RanhuraPerfilGenericoFrame extends JDialog {
	public RanhuraPerfilGenericoFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public RanhuraPerfilGenericoFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel3 = new JPanel();
		labelImage = new JLabel();
		panel16 = new JPanel();
		button1 = new JButton();
		panel2 = new JPanel();
		panel5 = new JPanel();
		label2 = new JLabel();
		label10 = new JLabel();
		panel4 = new JPanel();
		label4 = new JLabel();
		panel17 = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		textFieldNome = new JTextField();
		tabbedPane1 = new JTabbedPane();
		panel10 = new JPanel();
		panel13 = new JPanel();
		panel6 = new JPanel();
		radioButtonX = new JRadioButton();
		radioButtonY = new JRadioButton();
		panel7 = new JPanel();
		label7 = new JLabel();
		spinnerDelocamento = new JSpinner();
		label9 = new JLabel();
		spinnerZ = new JSpinner();
		label8 = new JLabel();
		ranhuraRug = new JSpinner();
		panel12 = new JPanel();
		panel9 = new JPanel();
		label26 = new JLabel();
		label27 = new JLabel();
		label28 = new JLabel();
		panel15 = new JPanel();
		spinner13 = new JSpinner();
		panel14 = new JPanel();
		checkBox1 = new JCheckBox();
		scrollPane1 = new JScrollPane();
		panel8 = new JPanel();
		panel11 = new JPanel();
		label3 = new JLabel();
		label5 = new JLabel();
		spinner1 = new JSpinner();
		label6 = new JLabel();
		spinner2 = new JSpinner();
		label11 = new JLabel();
		label12 = new JLabel();
		spinner3 = new JSpinner();
		label13 = new JLabel();
		spinner4 = new JSpinner();
		label14 = new JLabel();
		label15 = new JLabel();
		spinner5 = new JSpinner();
		label16 = new JLabel();
		spinner6 = new JSpinner();
		label17 = new JLabel();
		label18 = new JLabel();
		spinner7 = new JSpinner();
		label19 = new JLabel();
		spinner8 = new JSpinner();
		label20 = new JLabel();
		label21 = new JLabel();
		spinner9 = new JSpinner();
		label22 = new JLabel();
		spinner10 = new JSpinner();
		label23 = new JLabel();
		label24 = new JLabel();
		spinner11 = new JSpinner();
		label25 = new JLabel();
		spinner12 = new JSpinner();
		label29 = new JLabel();
		label31 = new JLabel();
		spinner14 = new JSpinner();
		label32 = new JLabel();
		spinner15 = new JSpinner();
		label30 = new JLabel();
		label33 = new JLabel();
		spinner16 = new JSpinner();
		label34 = new JLabel();
		spinner17 = new JSpinner();
		label35 = new JLabel();
		spinner18 = new JSpinner();
		label36 = new JLabel();
		label37 = new JLabel();
		spinner19 = new JSpinner();
		label38 = new JLabel();
		spinner20 = new JSpinner();
		label39 = new JLabel();
		label40 = new JLabel();
		spinner21 = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Criar Ranhura com perfil Geral (Curva de Bezier)");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

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
					panel3.setBorder(new TitledBorder("Coloque os dados como no modelo"));
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//---- labelImage ----
					labelImage.setIcon(new ImageIcon(getClass().getResource("/images/ranhuraHorizontalBezierModelo.gif")));
					panel3.add(labelImage, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel16 ========
					{
						panel16.setLayout(new GridBagLayout());
						((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

						//---- button1 ----
						button1.setText("Visualizar Perfil");
						panel16.add(button1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					panel3.add(panel16, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 50, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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
					panel2.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel4 ========
					{
						panel4.setBorder(new TitledBorder("Perfil"));
						panel4.setLayout(new GridBagLayout());
						((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

						//---- label4 ----
						label4.setIcon(new ImageIcon(getClass().getResource("/images/PerfilBezier.gif")));
						panel4.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel17 ========
						{
							panel17.setLayout(new BorderLayout());
						}
						panel4.add(panel17, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					panel2.add(panel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

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
						textFieldNome.setText("ranhura com perfil \"Curva de Bezier\"");
						textFieldNome.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
						panel1.add(textFieldNome, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					panel2.add(panel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== tabbedPane1 ========
					{
						tabbedPane1.setToolTipText("Par\u00e2metros da ranhura");

						//======== panel10 ========
						{
							panel10.setLayout(new GridBagLayout());
							((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

							//======== panel13 ========
							{
								panel13.setLayout(new GridBagLayout());
								((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

								//======== panel6 ========
								{
									panel6.setBorder(new TitledBorder("Dire\u00e7\u00e3o"));
									panel6.setLayout(new GridBagLayout());
									((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 1.0E-4};
									((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

									//---- radioButtonX ----
									radioButtonX.setText("horizontal");
									panel6.add(radioButtonX, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- radioButtonY ----
									radioButtonY.setText("vertical");
									radioButtonY.setSelected(true);
									panel6.add(radioButtonY, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel13.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel10.add(panel13, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== panel7 ========
							{
								panel7.setBorder(new CompoundBorder(
									new TitledBorder(""),
									new EmptyBorder(5, 5, 5, 5)));
								panel7.setLayout(new GridBagLayout());
								((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 55, 0, 0};
								((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
								((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

								//---- label7 ----
								label7.setText("deslocamento(mm)");
								label7.setHorizontalAlignment(SwingConstants.RIGHT);
								panel7.add(label7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinnerDelocamento ----
								spinnerDelocamento.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
								panel7.add(spinnerDelocamento, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- label9 ----
								label9.setText(" -Z (mm)");
								label9.setHorizontalAlignment(SwingConstants.RIGHT);
								panel7.add(label9, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinnerZ ----
								spinnerZ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								panel7.add(spinnerZ, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- label8 ----
								label8.setText("Textura (um)");
								label8.setHorizontalAlignment(SwingConstants.RIGHT);
								panel7.add(label8, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- ranhuraRug ----
								ranhuraRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
								panel7.add(ranhuraRug, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));
							}
							panel10.add(panel7, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						tabbedPane1.addTab("Par\u00e2metros", panel10);


						//======== panel12 ========
						{
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 200, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

							//======== panel9 ========
							{
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

								//---- label26 ----
								label26.setText("N\u00famero");
								panel9.add(label26, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label27 ----
								label27.setText("de Pontos");
								panel9.add(label27, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label28 ----
								label28.setText("de controle");
								panel9.add(label28, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel15 ========
								{
									panel15.setLayout(new GridBagLayout());
									((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
									((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- spinner13 ----
									spinner13.setModel(new SpinnerNumberModel(6, 3, 10, 1));
									panel15.add(spinner13, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel9.add(panel15, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel14 ========
								{
									panel14.setLayout(new GridBagLayout());
									((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0};
									((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
									((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- checkBox1 ----
									checkBox1.setText("Em toda a pe\u00e7a");
									panel14.add(checkBox1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel9.add(panel14, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel12.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== scrollPane1 ========
							{
								scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

								//======== panel8 ========
								{
									panel8.setLayout(new GridBagLayout());
									((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0};
									((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
									((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

									//======== panel11 ========
									{
										panel11.setLayout(new GridBagLayout());
										((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 55, 0, 50, 0};
										((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
										((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

										//---- label3 ----
										label3.setText("P1 ->");
										panel11.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label5 ----
										label5.setText("x=");
										panel11.add(label5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner1 ----
										spinner1.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
										panel11.add(spinner1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label6 ----
										label6.setText("z=");
										panel11.add(label6, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner2 ----
										spinner2.setModel(new SpinnerNumberModel(0.0, null, 0.0, 1.0));
										panel11.add(spinner2, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label11 ----
										label11.setText("P2 ->");
										panel11.add(label11, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label12 ----
										label12.setText("x=");
										panel11.add(label12, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner3 ----
										spinner3.setModel(new SpinnerNumberModel(5.0, 0.0, null, 1.0));
										panel11.add(spinner3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label13 ----
										label13.setText("z=");
										panel11.add(label13, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner4 ----
										spinner4.setModel(new SpinnerNumberModel(-5.0, null, null, 1.0));
										panel11.add(spinner4, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label14 ----
										label14.setText("P3 ->");
										panel11.add(label14, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label15 ----
										label15.setText("x=");
										panel11.add(label15, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner5 ----
										spinner5.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
										panel11.add(spinner5, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label16 ----
										label16.setText("z=");
										panel11.add(label16, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner6 ----
										spinner6.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));
										panel11.add(spinner6, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label17 ----
										label17.setText("P4 ->");
										panel11.add(label17, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label18 ----
										label18.setText("x=");
										panel11.add(label18, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner7 ----
										spinner7.setModel(new SpinnerNumberModel(15.0, 0.0, null, 1.0));
										panel11.add(spinner7, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label19 ----
										label19.setText("z=");
										panel11.add(label19, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner8 ----
										spinner8.setModel(new SpinnerNumberModel(4.0, null, null, 1.0));
										panel11.add(spinner8, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label20 ----
										label20.setText("P5 ->");
										panel11.add(label20, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label21 ----
										label21.setText("x=");
										panel11.add(label21, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner9 ----
										spinner9.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
										panel11.add(spinner9, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label22 ----
										label22.setText("z=");
										panel11.add(label22, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner10 ----
										spinner10.setModel(new SpinnerNumberModel(-12.0, null, null, 1.0));
										panel11.add(spinner10, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label23 ----
										label23.setText("P6 ->");
										panel11.add(label23, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label24 ----
										label24.setText("x=");
										panel11.add(label24, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner11 ----
										spinner11.setModel(new SpinnerNumberModel(25.0, 0.0, null, 1.0));
										panel11.add(spinner11, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label25 ----
										label25.setText("z=");
										panel11.add(label25, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner12 ----
										spinner12.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));
										panel11.add(spinner12, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label29 ----
										label29.setText("P7 ->");
										panel11.add(label29, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label31 ----
										label31.setText("x=");
										panel11.add(label31, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner14 ----
										spinner14.setModel(new SpinnerNumberModel(30.0, 0.0, null, 1.0));
										panel11.add(spinner14, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label32 ----
										label32.setText("z=");
										panel11.add(label32, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner15 ----
										spinner15.setModel(new SpinnerNumberModel(-7.0, null, null, 1.0));
										panel11.add(spinner15, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label30 ----
										label30.setText("P8 ->");
										panel11.add(label30, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label33 ----
										label33.setText("x=");
										panel11.add(label33, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner16 ----
										spinner16.setModel(new SpinnerNumberModel(35.0, 0.0, null, 1.0));
										panel11.add(spinner16, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label34 ----
										label34.setText("z=");
										panel11.add(label34, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner17 ----
										spinner17.setModel(new SpinnerNumberModel(-5.0, null, null, 1.0));
										panel11.add(spinner17, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label35 ----
										label35.setText("P9 ->");
										panel11.add(label35, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner18 ----
										spinner18.setModel(new SpinnerNumberModel(40.0, 0.0, null, 1.0));
										panel11.add(spinner18, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label36 ----
										label36.setText("x=");
										panel11.add(label36, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label37 ----
										label37.setText("z=");
										panel11.add(label37, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- spinner19 ----
										spinner19.setModel(new SpinnerNumberModel(-2.0, null, null, 1.0));
										panel11.add(spinner19, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label38 ----
										label38.setText("P10 ->");
										panel11.add(label38, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- spinner20 ----
										spinner20.setModel(new SpinnerNumberModel(45.0, 0.0, null, 1.0));
										panel11.add(spinner20, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label39 ----
										label39.setText("x=");
										panel11.add(label39, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label40 ----
										label40.setText("z=");
										panel11.add(label40, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- spinner21 ----
										spinner21.setModel(new SpinnerNumberModel(0.0, null, 0.0, 1.0));
										panel11.add(spinner21, new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									panel8.add(panel11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));
								}
								scrollPane1.setViewportView(panel8);
							}
							panel12.add(scrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						tabbedPane1.addTab("Perfil", panel12);

					}
					panel2.add(tabbedPane1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
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
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(870, 740);
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioButtonX);
		buttonGroup1.add(radioButtonY);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel3;
	protected JLabel labelImage;
	private JPanel panel16;
	protected JButton button1;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel label2;
	protected JLabel label10;
	protected JPanel panel4;
	private JLabel label4;
	protected JPanel panel17;
	private JPanel panel1;
	private JLabel label1;
	protected JTextField textFieldNome;
	private JTabbedPane tabbedPane1;
	private JPanel panel10;
	private JPanel panel13;
	private JPanel panel6;
	protected JRadioButton radioButtonX;
	protected JRadioButton radioButtonY;
	private JPanel panel7;
	private JLabel label7;
	protected JSpinner spinnerDelocamento;
	private JLabel label9;
	protected JSpinner spinnerZ;
	private JLabel label8;
	protected JSpinner ranhuraRug;
	private JPanel panel12;
	private JPanel panel9;
	private JLabel label26;
	private JLabel label27;
	private JLabel label28;
	private JPanel panel15;
	protected JSpinner spinner13;
	private JPanel panel14;
	public JCheckBox checkBox1;
	private JScrollPane scrollPane1;
	private JPanel panel8;
	protected JPanel panel11;
	protected JLabel label3;
	protected JLabel label5;
	protected JSpinner spinner1;
	protected JLabel label6;
	protected JSpinner spinner2;
	protected JLabel label11;
	protected JLabel label12;
	protected JSpinner spinner3;
	protected JLabel label13;
	protected JSpinner spinner4;
	protected JLabel label14;
	protected JLabel label15;
	protected JSpinner spinner5;
	protected JLabel label16;
	protected JSpinner spinner6;
	protected JLabel label17;
	protected JLabel label18;
	protected JSpinner spinner7;
	protected JLabel label19;
	protected JSpinner spinner8;
	protected JLabel label20;
	protected JLabel label21;
	protected JSpinner spinner9;
	protected JLabel label22;
	protected JSpinner spinner10;
	protected JLabel label23;
	protected JLabel label24;
	protected JSpinner spinner11;
	protected JLabel label25;
	protected JSpinner spinner12;
	public JLabel label29;
	protected JLabel label31;
	protected JSpinner spinner14;
	protected JLabel label32;
	protected JSpinner spinner15;
	protected JLabel label30;
	protected JLabel label33;
	protected JSpinner spinner16;
	protected JLabel label34;
	protected JSpinner spinner17;
	protected JLabel label35;
	protected JSpinner spinner18;
	protected JLabel label36;
	protected JLabel label37;
	protected JSpinner spinner19;
	protected JLabel label38;
	protected JSpinner spinner20;
	protected JLabel label39;
	protected JLabel label40;
	protected JSpinner spinner21;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
