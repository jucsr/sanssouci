/*
 * Created by JFormDesigner on Tue Aug 30 13:55:55 BRT 2011
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import com.jgoodies.forms.factories.*;

/**
 * @author ssiaum
 */
public class EditBallEndMillFrame extends JDialog {
	public EditBallEndMillFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public EditBallEndMillFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel4 = new JPanel();
		label3 = new JLabel();
		formattedTextField1 = new JFormattedTextField();
		label4 = new JLabel();
		textField2 = new JTextField();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		panel11 = new JPanel();
		panel12 = new JPanel();
		panel12a = new JPanel();
		layeredPane2 = new JLayeredPane();
		spinner13 = new JSpinner();
		spinner14 = new JSpinner();
		spinner15 = new JSpinner();
		label28 = new JLabel();
		panel13 = new JPanel();
		panel14 = new JPanel();
		label23 = new JLabel();
		label24 = new JLabel();
		label17 = new JLabel();
		formattedTextField3 = new JFormattedTextField();
		label18 = new JLabel();
		checkBox3 = new JCheckBox();
		panel2 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner1 = new JSpinner();
		spinner2 = new JSpinner();
		spinner4 = new JSpinner();
		spinner5 = new JSpinner();
		spinner3 = new JSpinner();
		label6 = new JLabel();
		panel7 = new JPanel();
		panel8 = new JPanel();
		label7 = new JLabel();
		textField1 = new JTextField();
		label8 = new JLabel();
		comboBox1 = new JComboBox();
		label9 = new JLabel();
		panel9 = new JPanel();
		spinner8 = new JSpinner();
		label10 = new JLabel();
		comboBox3 = new JComboBox();
		label11 = new JLabel();
		checkBox2 = new JCheckBox();
		panel3 = new JPanel();
		layeredPane3 = new JLayeredPane();
		spinner7 = new JSpinner();
		label19 = new JLabel();
		spinner9 = new JSpinner();
		label20 = new JLabel();
		spinner10 = new JSpinner();
		label14 = new JLabel();
		label12 = new JLabel();
		spinner11 = new JSpinner();
		spinner12 = new JSpinner();
		label25 = new JLabel();
		panel10 = new JPanel();
		panel15 = new JPanel();
		panel16 = new JPanel();
		label1 = new JLabel();
		spinner6 = new JSpinner();
		label2 = new JLabel();
		spinner16 = new JSpinner();
		scrollPane3 = new JScrollPane();
		table1 = new JTable();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("edit Machining Workingstep");
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 100, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//---- label3 ----
					label3.setText("Id: ");
					panel4.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- formattedTextField1 ----
					formattedTextField1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
					formattedTextField1.setText("ID01 bottomAndSide");
					panel4.add(formattedTextField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label4 ----
					label4.setText("its Feature:");
					label4.setVisible(false);
					panel4.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- textField2 ----
					textField2.setText("Closed Pocket");
					textField2.setVisible(false);
					panel4.add(textField2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== tabbedPane1 ========
				{

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//======== panel11 ========
						{
							panel11.setLayout(new GridBagLayout());
							((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//======== panel12 ========
							{
								panel12.setBorder(new EtchedBorder());
								panel12.setLayout(new GridBagLayout());
								((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {345, 0, 0};
								((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 340, 0};
								((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

								//======== panel12a ========
								{
									panel12a.setBorder(new EtchedBorder());
									panel12a.setLayout(new GridBagLayout());
									((GridBagLayout)panel12a.getLayout()).columnWidths = new int[] {340, 0};
									((GridBagLayout)panel12a.getLayout()).rowHeights = new int[] {340, 0};
									((GridBagLayout)panel12a.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
									((GridBagLayout)panel12a.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//======== layeredPane2 ========
									{
										layeredPane2.setBorder(new EtchedBorder());

										//---- spinner13 ----
										spinner13.setModel(new SpinnerNumberModel(0.5, 0.0, 10.0, 0.1));
										layeredPane2.add(spinner13, JLayeredPane.DEFAULT_LAYER);
										spinner13.setBounds(260, 225, 44, spinner13.getPreferredSize().height);

										//---- spinner14 ----
										spinner14.setModel(new SpinnerNumberModel(0.5, 0.0, 10.0, 0.1));
										layeredPane2.add(spinner14, JLayeredPane.DEFAULT_LAYER);
										spinner14.setBounds(135, 280, 44, spinner14.getPreferredSize().height);

										//---- spinner15 ----
										spinner15.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
										layeredPane2.add(spinner15, JLayeredPane.DEFAULT_LAYER);
										spinner15.setBounds(290, 120, 45, spinner15.getPreferredSize().height);

										//---- label28 ----
										label28.setIcon(new ImageIcon(getClass().getResource("/images/OperationBall.png")));
										layeredPane2.add(label28, JLayeredPane.DEFAULT_LAYER);
										label28.setBounds(new Rectangle(new Point(0, 0), label28.getPreferredSize()));
									}
									panel12a.add(layeredPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel12.add(panel12a, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//======== panel13 ========
								{
									panel13.setLayout(new GridBagLayout());
									((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

									//======== panel14 ========
									{
										panel14.setBorder(new EtchedBorder());
										panel14.setLayout(new GridBagLayout());
										((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
										((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

										//---- label23 ----
										label23.setText("Type: ");
										panel14.add(label23, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- label24 ----
										label24.setText("Bottom and Side Rough Or Finish Milling");
										panel14.add(label24, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label17 ----
										label17.setText("Id:");
										panel14.add(label17, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));

										//---- formattedTextField3 ----
										formattedTextField3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
										formattedTextField3.setText("ID02");
										panel14.add(formattedTextField3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//---- label18 ----
										label18.setText("Coolant:");
										panel14.add(label18, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- checkBox3 ----
										checkBox3.setBorderPaintedFlat(true);
										checkBox3.setSelected(true);
										panel14.add(checkBox3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									panel13.add(panel14, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));
								}
								panel12.add(panel13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel11.add(panel12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel1.add(panel11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Operation", panel1);


					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {345, 0, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {340, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

						//======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//======== panel6 ========
							{
								panel6.setBorder(new EtchedBorder());
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {340, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {340, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//======== layeredPane1 ========
								{
									layeredPane1.setBackground(Color.white);

									//---- spinner1 ----
									spinner1.setModel(new SpinnerNumberModel(80.0, 0.0, null, 1.0));
									layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
									spinner1.setBounds(90, 225, 41, spinner1.getPreferredSize().height);

									//---- spinner2 ----
									spinner2.setModel(new SpinnerNumberModel(60.0, 0.0, null, 1.0));
									layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
									spinner2.setBounds(200, 240, 36, spinner2.getPreferredSize().height);

									//---- spinner4 ----
									spinner4.setModel(new SpinnerNumberModel(20.0, 1.0, null, 1.0));
									layeredPane1.add(spinner4, JLayeredPane.DEFAULT_LAYER);
									spinner4.setBounds(150, 320, 36, spinner4.getPreferredSize().height);

									//---- spinner5 ----
									spinner5.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
									layeredPane1.add(spinner5, JLayeredPane.DEFAULT_LAYER);
									spinner5.setBounds(150, 0, 40, 21);

									//---- spinner3 ----
									spinner3.setModel(new SpinnerNumberModel(70.0, 0.0, null, 1.0));
									layeredPane1.add(spinner3, JLayeredPane.DEFAULT_LAYER);
									spinner3.setBounds(45, 160, 41, spinner3.getPreferredSize().height);

									//---- label6 ----
									label6.setIcon(new ImageIcon(getClass().getResource("/images/ballEndMill.png")));
									layeredPane1.add(label6, JLayeredPane.DEFAULT_LAYER);
									label6.setBounds(new Rectangle(new Point(0, 0), label6.getPreferredSize()));
								}
								panel6.add(layeredPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//======== panel7 ========
							{
								panel7.setLayout(new GridBagLayout());
								((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

								//======== panel8 ========
								{
									panel8.setBorder(new CompoundBorder(
										new TitledBorder("tool parameters"),
										Borders.DLU2_BORDER));
									panel8.setLayout(new GridBagLayout());
									((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

									//---- label7 ----
									label7.setText("Name");
									panel8.add(label7, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- textField1 ----
									textField1.setText("ball end mill");
									textField1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
									panel8.add(textField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label8 ----
									label8.setText("Material");
									panel8.add(label8, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- comboBox1 ----
									comboBox1.setModel(new DefaultComboBoxModel(new String[] {
										"Carbide P-Class",
										"Carbide M-Class",
										"Carbide S-Class",
										"Carbide K-Class",
										"Carbide H-Class",
										"Carbide N-Class"
									}));
									panel8.add(comboBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label9 ----
									label9.setText("N. Teeth");
									panel8.add(label9, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//======== panel9 ========
									{
										panel9.setLayout(new GridBagLayout());
										((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- spinner8 ----
										spinner8.setModel(new SpinnerNumberModel(4, 1, 20, 1));
										panel9.add(spinner8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
									}
									panel8.add(panel9, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label10 ----
									label10.setText("Sense");
									panel8.add(label10, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- comboBox3 ----
									comboBox3.setModel(new DefaultComboBoxModel(new String[] {
										"RIGHT_TOOL",
										"LEFT_TOOL",
										"NEUTRUM"
									}));
									panel8.add(comboBox3, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label11 ----
									label11.setText("Internal Cooling");
									panel8.add(label11, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
									panel8.add(checkBox2, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel7.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel5.add(panel7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel2.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					tabbedPane1.addTab("Tool", panel2);


					//======== panel3 ========
					{
						panel3.setLayout(new GridBagLayout());
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 555, 0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

						//======== layeredPane3 ========
						{

							//---- spinner7 ----
							spinner7.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane3.add(spinner7, JLayeredPane.DEFAULT_LAYER);
							spinner7.setBounds(310, 115, 50, spinner7.getPreferredSize().height);

							//---- label19 ----
							label19.setText("m/min");
							layeredPane3.add(label19, JLayeredPane.DEFAULT_LAYER);
							label19.setBounds(365, 120, 50, label19.getPreferredSize().height);

							//---- spinner9 ----
							spinner9.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane3.add(spinner9, JLayeredPane.DEFAULT_LAYER);
							spinner9.setBounds(150, 95, 55, spinner9.getPreferredSize().height);

							//---- label20 ----
							label20.setText("mm/rot");
							layeredPane3.add(label20, JLayeredPane.DEFAULT_LAYER);
							label20.setBounds(220, 100, 45, label20.getPreferredSize().height);

							//---- spinner10 ----
							spinner10.setModel(new SpinnerNumberModel(0, 0, null, 1));
							layeredPane3.add(spinner10, JLayeredPane.DEFAULT_LAYER);
							spinner10.setBounds(315, 35, 60, spinner10.getPreferredSize().height);

							//---- label14 ----
							label14.setText("rpm");
							layeredPane3.add(label14, JLayeredPane.DEFAULT_LAYER);
							label14.setBounds(380, 40, 35, label14.getPreferredSize().height);

							//---- label12 ----
							label12.setText("Cut Speed");
							layeredPane3.add(label12, JLayeredPane.DEFAULT_LAYER);
							label12.setBounds(275, 100, 65, label12.getPreferredSize().height);

							//---- spinner11 ----
							spinner11.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane3.add(spinner11, JLayeredPane.DEFAULT_LAYER);
							spinner11.setBounds(425, 165, 46, spinner11.getPreferredSize().height);

							//---- spinner12 ----
							spinner12.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane3.add(spinner12, JLayeredPane.DEFAULT_LAYER);
							spinner12.setBounds(350, 225, 50, 20);

							//---- label25 ----
							label25.setIcon(new ImageIcon(getClass().getResource("/images/technologyBallEndMill.png")));
							layeredPane3.add(label25, JLayeredPane.DEFAULT_LAYER);
							label25.setBounds(new Rectangle(new Point(0, 0), label25.getPreferredSize()));
						}
						panel3.add(layeredPane3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					tabbedPane1.addTab("Technology", panel3);


					//======== panel10 ========
					{
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {96, 0, 0, 0};
						((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel15 ========
						{
							panel15.setLayout(new GridBagLayout());
							((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//======== panel16 ========
							{
								panel16.setLayout(new GridBagLayout());
								((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {85, 58, 0};
								((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

								//---- label1 ----
								label1.setText("Operation Time");
								panel16.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinner6 ----
								spinner6.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
								panel16.add(spinner6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label2 ----
								label2.setText("Operation Cost");
								panel16.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- spinner16 ----
								spinner16.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
								panel16.add(spinner16, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel15.add(panel16, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel10.add(panel15, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== scrollPane3 ========
						{

							//---- table1 ----
							table1.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Select", "Workingstep Id", "Operation type"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Boolean.class, Object.class, Object.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							scrollPane3.setViewportView(table1);
						}
						panel10.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					tabbedPane1.addTab("Cost, Time & Relations", panel10);

				}
				contentPanel.add(tabbedPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel4;
	private JLabel label3;
	protected JFormattedTextField formattedTextField1;
	private JLabel label4;
	protected JTextField textField2;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JPanel panel11;
	private JPanel panel12;
	private JPanel panel12a;
	private JLayeredPane layeredPane2;
	protected JSpinner spinner13;
	protected JSpinner spinner14;
	protected JSpinner spinner15;
	protected JLabel label28;
	private JPanel panel13;
	private JPanel panel14;
	private JLabel label23;
	protected JLabel label24;
	private JLabel label17;
	protected JFormattedTextField formattedTextField3;
	private JLabel label18;
	protected JCheckBox checkBox3;
	private JPanel panel2;
	private JPanel panel5;
	private JPanel panel6;
	private JLayeredPane layeredPane1;
	protected JSpinner spinner1;
	protected JSpinner spinner2;
	protected JSpinner spinner4;
	protected JSpinner spinner5;
	protected JSpinner spinner3;
	protected JLabel label6;
	private JPanel panel7;
	private JPanel panel8;
	private JLabel label7;
	protected JTextField textField1;
	private JLabel label8;
	protected JComboBox comboBox1;
	private JLabel label9;
	private JPanel panel9;
	protected JSpinner spinner8;
	private JLabel label10;
	protected JComboBox comboBox3;
	private JLabel label11;
	protected JCheckBox checkBox2;
	private JPanel panel3;
	private JLayeredPane layeredPane3;
	protected JSpinner spinner7;
	private JLabel label19;
	protected JSpinner spinner9;
	private JLabel label20;
	protected JSpinner spinner10;
	private JLabel label14;
	private JLabel label12;
	protected JSpinner spinner11;
	protected JSpinner spinner12;
	private JLabel label25;
	private JPanel panel10;
	private JPanel panel15;
	private JPanel panel16;
	private JLabel label1;
	protected JSpinner spinner6;
	private JLabel label2;
	protected JSpinner spinner16;
	private JScrollPane scrollPane3;
	protected JTable table1;
	private JPanel buttonBar;
	public JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}