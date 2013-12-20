/*
 * Created by JFormDesigner on Tue Aug 30 13:56:41 BRT 2011
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
public class EditBoringToolFrame extends JDialog {
	public EditBoringToolFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public EditBoringToolFrame(Dialog owner) {
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
		label5 = new JLabel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		panel10 = new JPanel();
		panel12 = new JPanel();
		layeredPane2 = new JLayeredPane();
		spinner7 = new JSpinner();
		spinner10 = new JSpinner();
		spinner9 = new JSpinner();
		spinner11 = new JSpinner();
		label28 = new JLabel();
		panel12a = new JPanel();
		panel13 = new JPanel();
		label17 = new JLabel();
		label18 = new JLabel();
		label1 = new JLabel();
		formattedTextField2 = new JFormattedTextField();
		label2 = new JLabel();
		checkBox1 = new JCheckBox();
		panel2 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner1 = new JSpinner();
		spinner2 = new JSpinner();
		spinner4 = new JSpinner();
		spinner3 = new JSpinner();
		spinner5 = new JSpinner();
		spinner6 = new JSpinner();
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
		panel11 = new JPanel();
		layeredPane3 = new JLayeredPane();
		spinner13 = new JSpinner();
		label23 = new JLabel();
		spinner14 = new JSpinner();
		label24 = new JLabel();
		spinner15 = new JSpinner();
		label25 = new JLabel();
		label26 = new JLabel();
		label27 = new JLabel();
		panel14 = new JPanel();
		panel15 = new JPanel();
		panel16 = new JPanel();
		label12 = new JLabel();
		spinner12 = new JSpinner();
		label13 = new JLabel();
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

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
					formattedTextField1.setText("ID01 Boring");
					panel4.add(formattedTextField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label4 ----
					label4.setText("its Feature:");
					label4.setVisible(false);
					panel4.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label5 ----
					label5.setText("feature");
					label5.setVisible(false);
					panel4.add(label5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
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
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//======== panel10 ========
						{
							panel10.setBorder(new EtchedBorder());
							panel10.setLayout(new GridBagLayout());
							((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {345, 0, 0};
							((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {340, 0};
							((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//======== panel12 ========
							{
								panel12.setBorder(new EtchedBorder());
								panel12.setLayout(new GridBagLayout());
								((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {340, 0};
								((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {340, 0};
								((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//======== layeredPane2 ========
								{
									layeredPane2.setBorder(new EtchedBorder());

									//---- spinner7 ----
									spinner7.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									layeredPane2.add(spinner7, JLayeredPane.DEFAULT_LAYER);
									spinner7.setBounds(265, 195, 44, spinner7.getPreferredSize().height);

									//---- spinner10 ----
									spinner10.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									layeredPane2.add(spinner10, JLayeredPane.DEFAULT_LAYER);
									spinner10.setBounds(161, 296, 44, spinner10.getPreferredSize().height);

									//---- spinner9 ----
									spinner9.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
									layeredPane2.add(spinner9, JLayeredPane.DEFAULT_LAYER);
									spinner9.setBounds(30, 170, 40, spinner9.getPreferredSize().height);

									//---- spinner11 ----
									spinner11.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
									layeredPane2.add(spinner11, JLayeredPane.DEFAULT_LAYER);
									spinner11.setBounds(300, 250, 40, spinner11.getPreferredSize().height);

									//---- label28 ----
									label28.setIcon(new ImageIcon(getClass().getResource("/images/OperationBoring.png")));
									layeredPane2.add(label28, JLayeredPane.DEFAULT_LAYER);
									label28.setBounds(new Rectangle(new Point(0, 0), label28.getPreferredSize()));
								}
								panel12.add(layeredPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel10.add(panel12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//======== panel12a ========
							{
								panel12a.setLayout(new GridBagLayout());
								((GridBagLayout)panel12a.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel12a.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel12a.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel12a.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

								//======== panel13 ========
								{
									panel13.setBorder(new EtchedBorder());
									panel13.setLayout(new GridBagLayout());
									((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

									//---- label17 ----
									label17.setText("Type: ");
									panel13.add(label17, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- label18 ----
									label18.setText("Boring");
									panel13.add(label18, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label1 ----
									label1.setText("Id:");
									panel13.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- formattedTextField2 ----
									formattedTextField2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
									panel13.add(formattedTextField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label2 ----
									label2.setText("Coolant:");
									panel13.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- checkBox1 ----
									checkBox1.setBorderPaintedFlat(true);
									checkBox1.setSelected(true);
									panel13.add(checkBox1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel12a.add(panel13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel10.add(panel12a, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
									spinner1.setModel(new SpinnerNumberModel(70.0, 0.0, null, 1.0));
									layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
									spinner1.setBounds(125, 175, 41, spinner1.getPreferredSize().height);

									//---- spinner2 ----
									spinner2.setModel(new SpinnerNumberModel(60.0, 0.0, null, 1.0));
									layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
									spinner2.setBounds(165, 45, 41, spinner2.getPreferredSize().height);

									//---- spinner4 ----
									spinner4.setModel(new SpinnerNumberModel(20.0, 1.0, null, 1.0));
									layeredPane1.add(spinner4, JLayeredPane.DEFAULT_LAYER);
									spinner4.setBounds(295, 115, 41, spinner4.getPreferredSize().height);

									//---- spinner3 ----
									spinner3.setModel(new SpinnerNumberModel(40.0, 0.0, null, 1.0));
									layeredPane1.add(spinner3, JLayeredPane.DEFAULT_LAYER);
									spinner3.setBounds(-1, 114, 41, 20);

									//---- spinner5 ----
									spinner5.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
									layeredPane1.add(spinner5, JLayeredPane.DEFAULT_LAYER);
									spinner5.setBounds(100, 285, 41, 25);

									//---- spinner6 ----
									spinner6.setModel(new SpinnerNumberModel(3.0, 1.0, null, 1.0));
									layeredPane1.add(spinner6, JLayeredPane.DEFAULT_LAYER);
									spinner6.setBounds(250, 115, 40, spinner6.getPreferredSize().height);

									//---- label6 ----
									label6.setIcon(new ImageIcon(getClass().getResource("/images/boringTool.png")));
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
									textField1.setText("boring tool");
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
										spinner8.setModel(new SpinnerNumberModel(1, 1, 20, 1));
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
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//======== panel11 ========
						{
							panel11.setBorder(new EtchedBorder());
							panel11.setLayout(new GridBagLayout());
							((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 555, 0, 0};
							((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {340, 0};
							((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//======== layeredPane3 ========
							{

								//---- spinner13 ----
								spinner13.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								layeredPane3.add(spinner13, JLayeredPane.DEFAULT_LAYER);
								spinner13.setBounds(125, 190, 50, spinner13.getPreferredSize().height);

								//---- label23 ----
								label23.setText("m/min");
								layeredPane3.add(label23, JLayeredPane.DEFAULT_LAYER);
								label23.setBounds(180, 195, 50, label23.getPreferredSize().height);

								//---- spinner14 ----
								spinner14.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								layeredPane3.add(spinner14, JLayeredPane.DEFAULT_LAYER);
								spinner14.setBounds(275, 100, 50, spinner14.getPreferredSize().height);

								//---- label24 ----
								label24.setText("mm/rot");
								layeredPane3.add(label24, JLayeredPane.DEFAULT_LAYER);
								label24.setBounds(new Rectangle(new Point(330, 105), label24.getPreferredSize()));

								//---- spinner15 ----
								spinner15.setModel(new SpinnerNumberModel(0, 0, null, 1));
								layeredPane3.add(spinner15, JLayeredPane.DEFAULT_LAYER);
								spinner15.setBounds(275, 60, 60, spinner15.getPreferredSize().height);

								//---- label25 ----
								label25.setText("rpm");
								layeredPane3.add(label25, JLayeredPane.DEFAULT_LAYER);
								label25.setBounds(340, 65, 35, label25.getPreferredSize().height);

								//---- label26 ----
								label26.setText("Cut Speed");
								layeredPane3.add(label26, JLayeredPane.DEFAULT_LAYER);
								label26.setBounds(135, 155, 70, label26.getPreferredSize().height);

								//---- label27 ----
								label27.setIcon(new ImageIcon(getClass().getResource("/images/technologyBoringTool.png")));
								layeredPane3.add(label27, JLayeredPane.DEFAULT_LAYER);
								label27.setBounds(new Rectangle(new Point(0, 0), label27.getPreferredSize()));
							}
							panel11.add(layeredPane3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel3.add(panel11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Technology", panel3);


					//======== panel14 ========
					{
						panel14.setLayout(new GridBagLayout());
						((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {96, 0, 0, 0};
						((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

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

								//---- label12 ----
								label12.setText("Operation Time");
								panel16.add(label12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinner12 ----
								spinner12.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
								panel16.add(spinner12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label13 ----
								label13.setText("Operation Cost");
								panel16.add(label13, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
						panel14.add(panel15, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
						panel14.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					tabbedPane1.addTab("Cost, Time & Relations", panel14);

				}
				contentPanel.add(tabbedPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
	protected JLabel label5;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JPanel panel10;
	private JPanel panel12;
	private JLayeredPane layeredPane2;
	public JSpinner spinner7;
	public JSpinner spinner10;
	public JSpinner spinner9;
	public JSpinner spinner11;
	private JLabel label28;
	private JPanel panel12a;
	private JPanel panel13;
	private JLabel label17;
	protected JLabel label18;
	private JLabel label1;
	public JFormattedTextField formattedTextField2;
	private JLabel label2;
	public JCheckBox checkBox1;
	private JPanel panel2;
	private JPanel panel5;
	private JPanel panel6;
	private JLayeredPane layeredPane1;
	public JSpinner spinner1;
	public JSpinner spinner2;
	public JSpinner spinner4;
	public JSpinner spinner3;
	public JSpinner spinner5;
	public JSpinner spinner6;
	protected JLabel label6;
	private JPanel panel7;
	private JPanel panel8;
	private JLabel label7;
	public JTextField textField1;
	private JLabel label8;
	public JComboBox comboBox1;
	private JLabel label9;
	private JPanel panel9;
	public JSpinner spinner8;
	private JLabel label10;
	public JComboBox comboBox3;
	private JLabel label11;
	public JCheckBox checkBox2;
	private JPanel panel3;
	private JPanel panel11;
	private JLayeredPane layeredPane3;
	protected JSpinner spinner13;
	private JLabel label23;
	protected JSpinner spinner14;
	private JLabel label24;
	protected JSpinner spinner15;
	private JLabel label25;
	private JLabel label26;
	private JLabel label27;
	private JPanel panel14;
	private JPanel panel15;
	private JPanel panel16;
	private JLabel label12;
	protected JSpinner spinner12;
	private JLabel label13;
	protected JSpinner spinner16;
	private JScrollPane scrollPane3;
	protected JTable table1;
	private JPanel buttonBar;
	public JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
