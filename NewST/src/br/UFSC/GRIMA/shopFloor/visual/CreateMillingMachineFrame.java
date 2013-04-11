/*
 * Created by JFormDesigner on Tue Nov 27 15:43:32 BRST 2012
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import com.jgoodies.forms.layout.*;


public class CreateMillingMachineFrame extends JDialog {
	public CreateMillingMachineFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CreateMillingMachineFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel2 = new JPanel();
		panel3 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label7 = new JLabel();
		spinner4 = new JSpinner();
		label8 = new JLabel();
		comboBox1 = new JComboBox();
		label21 = new JLabel();
		panel18 = new JPanel();
		label22 = new JLabel();
		spinner13 = new JSpinner();
		label23 = new JLabel();
		spinner14 = new JSpinner();
		panel10 = new JPanel();
		panel13 = new JPanel();
		label12 = new JLabel();
		panel4 = new JPanel();
		panel6 = new JPanel();
		label2 = new JLabel();
		textField2 = new JTextField();
		panel19 = new JPanel();
		label15 = new JLabel();
		spinner8 = new JSpinner();
		label16 = new JLabel();
		spinner9 = new JSpinner();
		label17 = new JLabel();
		spinner10 = new JSpinner();
		label18 = new JLabel();
		spinner11 = new JSpinner();
		label20 = new JLabel();
		spinner12 = new JSpinner();
		panel20 = new JPanel();
		label19 = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		panel11 = new JPanel();
		button1 = new JButton();
		button17 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		panel7 = new JPanel();
		panel8 = new JPanel();
		scrollPane2 = new JScrollPane();
		table2 = new JTable();
		panel14 = new JPanel();
		button6 = new JButton();
		button7 = new JButton();
		button8 = new JButton();
		button9 = new JButton();
		button10 = new JButton();
		panel5 = new JPanel();
		panel9 = new JPanel();
		scrollPane3 = new JScrollPane();
		table3 = new JTable();
		panel15 = new JPanel();
		button11 = new JButton();
		button12 = new JButton();
		panel12 = new JPanel();
		scrollPane4 = new JScrollPane();
		table4 = new JTable();
		panel17 = new JPanel();
		button13 = new JButton();
		button14 = new JButton();
		button16 = new JButton();
		button15 = new JButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		spinner2 = new JSpinner();
		spinner3 = new JSpinner();
		spinner1 = new JSpinner();
		label4 = new JLabel();
		label3 = new JLabel();
		label5 = new JLabel();
		panel16 = new JPanel();
		label13 = new JLabel();
		textField3 = new JTextField();
		label14 = new JLabel();
		comboBox2 = new JComboBox();
		label6 = new JLabel();
		spinner5 = new JSpinner();
		label9 = new JLabel();
		spinner6 = new JSpinner();
		label10 = new JLabel();
		spinner7 = new JSpinner();
		label11 = new JLabel();
		checkBox1 = new JCheckBox();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("Create new Milling Machine");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");

				//---- menuItem1 ----
				menuItem1.setText("Save Machine");
				menu1.add(menuItem1);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 600, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== tabbedPane1 ========
					{

						//======== panel2 ========
						{
							panel2.setLayout(new GridBagLayout());
							((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//======== panel3 ========
							{
								panel3.setLayout(new GridBagLayout());
								((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

								//---- label1 ----
								label1.setText("Its Id");
								panel3.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- textField1 ----
								textField1.setText("Milling Machine");
								panel3.add(textField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label7 ----
								label7.setText("Accuracy (mm)");
								panel3.add(label7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- spinner4 ----
								spinner4.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								panel3.add(spinner4, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label8 ----
								label8.setText("Control");
								panel3.add(label8, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- comboBox1 ----
								comboBox1.setModel(new DefaultComboBoxModel(new String[] {
									"None",
									"Fanuc",
									"Mazak",
									"Siemens"
								}));
								panel3.add(comboBox1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label21 ----
								label21.setText("Its Origin");
								panel3.add(label21, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//======== panel18 ========
								{
									panel18.setLayout(new FormLayout(
										"5*(default, $lcgap), default",
										"default"));

									//---- label22 ----
									label22.setText("X");
									panel18.add(label22, cc.xy(1, 1));
									panel18.add(spinner13, cc.xywh(3, 1, 3, 1));

									//---- label23 ----
									label23.setText("Y");
									panel18.add(label23, cc.xy(7, 1));
									panel18.add(spinner14, cc.xywh(9, 1, 3, 1));
								}
								panel3.add(panel18, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel2.add(panel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel10 ========
							{
								panel10.setBorder(new EtchedBorder());
								panel10.setLayout(new GridBagLayout());
								((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

								//======== panel13 ========
								{
									panel13.setBorder(new EtchedBorder(EtchedBorder.RAISED));
									panel13.setLayout(new GridBagLayout());
									((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- label12 ----
									label12.setIcon(new ImageIcon(getClass().getResource("/images/millingMachine.png")));
									panel13.add(label12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel10.add(panel13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel2.add(panel10, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						tabbedPane1.addTab("General data", panel2);


						//======== panel4 ========
						{
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};

							//======== panel6 ========
							{
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

								//---- label2 ----
								label2.setText("its Id");
								panel6.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- textField2 ----
								textField2.setText("Default Magazine");
								panel6.add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel19 ========
								{
									panel19.setLayout(new GridBagLayout());
									((GridBagLayout)panel19.getLayout()).columnWidths = new int[] {0, 52, 0, 55, 0, 55, 0, 55, 0, 55, 38, 0};
									((GridBagLayout)panel19.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel19.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
									((GridBagLayout)panel19.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- label15 ----
									label15.setText("Capacity");
									panel19.add(label15, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- spinner8 ----
									spinner8.setModel(new SpinnerNumberModel(0, 0, null, 1));
									panel19.add(spinner8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label16 ----
									label16.setText("Max Weight");
									panel19.add(label16, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- spinner9 ----
									spinner9.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									panel19.add(spinner9, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label17 ----
									label17.setText("Max Tool Diameter");
									panel19.add(label17, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- spinner10 ----
									spinner10.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									panel19.add(spinner10, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label18 ----
									label18.setText("Max Tool Length");
									panel19.add(label18, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- spinner11 ----
									spinner11.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									panel19.add(spinner11, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label20 ----
									label20.setText("Change Time");
									panel19.add(label20, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- spinner12 ----
									spinner12.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
									panel19.add(spinner12, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel6.add(panel19, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel4.add(panel6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel20 ========
							{
								panel20.setLayout(new GridBagLayout());
								((GridBagLayout)panel20.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel20.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel20.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel20.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- label19 ----
								label19.setText("Avaliable Tools");
								panel20.add(label19, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));
							}
							panel4.add(panel20, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== scrollPane1 ========
							{

								//---- table1 ----
								table1.setModel(new DefaultTableModel(
									new Object[][] {
									},
									new String[] {
										"Select", "Id", "Name", "Diameter", "Type", "Current Tool"
									}
								) {
									Class<?>[] columnTypes = new Class<?>[] {
										Boolean.class, String.class, String.class, Double.class, String.class, Boolean.class
									};
									@Override
									public Class<?> getColumnClass(int columnIndex) {
										return columnTypes[columnIndex];
									}
								});
								{
									TableColumnModel cm = table1.getColumnModel();
									cm.getColumn(0).setPreferredWidth(30);
									cm.getColumn(1).setPreferredWidth(30);
								}
								scrollPane1.setViewportView(table1);
							}
							panel4.add(scrollPane1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel11 ========
							{
								panel11.setLayout(new GridBagLayout());
								((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

								//---- button1 ----
								button1.setText("View Details");
								panel11.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button17 ----
								button17.setText("Current Tool");
								panel11.add(button17, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button2 ----
								button2.setText("Add New Tool");
								panel11.add(button2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button3 ----
								button3.setText("Remove Tool");
								panel11.add(button3, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button4 ----
								button4.setText("Select all");
								panel11.add(button4, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button5 ----
								button5.setText("Deselect all");
								panel11.add(button5, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel4.add(panel11, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						tabbedPane1.addTab("Tool Magazine", panel4);


						//======== panel7 ========
						{
							panel7.setLayout(new GridBagLayout());
							((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

							//======== panel8 ========
							{
								panel8.setLayout(new GridBagLayout());
								((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//======== scrollPane2 ========
								{

									//---- table2 ----
									table2.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
											"Select", "Name", "Max load Capacity", "Type"
										}
									) {
										Class<?>[] columnTypes = new Class<?>[] {
											Boolean.class, String.class, Integer.class, String.class
										};
										@Override
										public Class<?> getColumnClass(int columnIndex) {
											return columnTypes[columnIndex];
										}
									});
									{
										TableColumnModel cm = table2.getColumnModel();
										cm.getColumn(0).setPreferredWidth(30);
										cm.getColumn(2).setPreferredWidth(30);
									}
									scrollPane2.setViewportView(table2);
								}
								panel8.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel7.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel14 ========
							{
								panel14.setLayout(new GridBagLayout());
								((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

								//---- button6 ----
								button6.setText("View Details");
								panel14.add(button6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button7 ----
								button7.setText("Add  Device");
								panel14.add(button7, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button8 ----
								button8.setText("Remove  Device");
								panel14.add(button8, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button9 ----
								button9.setText("Select all");
								panel14.add(button9, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button10 ----
								button10.setText("Deselect all");
								panel14.add(button10, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel7.add(panel14, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						tabbedPane1.addTab("Workpiece Handling Devices", panel7);


						//======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel9 ========
							{
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {340, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 355, 0, 0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 1.0, 0.0, 0.0, 1.0E-4};

								//======== scrollPane3 ========
								{

									//---- table3 ----
									table3.setAutoCreateRowSorter(true);
									table3.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
											"Select", "ID", "Name", "Origin"
										}
									) {
										Class<?>[] columnTypes = new Class<?>[] {
											Boolean.class, Integer.class, String.class, String.class
										};
										@Override
										public Class<?> getColumnClass(int columnIndex) {
											return columnTypes[columnIndex];
										}
									});
									scrollPane3.setViewportView(table3);
								}
								panel9.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel15 ========
								{
									panel15.setLayout(new GridBagLayout());
									((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

									//---- button11 ----
									button11.setText("Add Rotary Axis");
									panel15.add(button11, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- button12 ----
									button12.setText("Add Traveling Axis");
									panel15.add(button12, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));
								}
								panel9.add(panel15, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel5.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						tabbedPane1.addTab("Axis", panel5);


						//======== panel12 ========
						{
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

							//======== scrollPane4 ========
							{

								//---- table4 ----
								table4.setModel(new DefaultTableModel(
									new Object[][] {
									},
									new String[] {
										"Select", "Name", "Type", "Max Diameter", "Max Power", "Max Torque", "Speed Range", "Coolant"
									}
								) {
									Class<?>[] columnTypes = new Class<?>[] {
										Boolean.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Boolean.class
									};
									@Override
									public Class<?> getColumnClass(int columnIndex) {
										return columnTypes[columnIndex];
									}
								});
								scrollPane4.setViewportView(table4);
							}
							panel12.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel17 ========
							{
								panel17.setLayout(new GridBagLayout());
								((GridBagLayout)panel17.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel17.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel17.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel17.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

								//---- button13 ----
								button13.setText("Add Spindle");
								panel17.add(button13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button14 ----
								button14.setText("Remove Spindle");
								panel17.add(button14, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button16 ----
								button16.setText("Select All");
								panel17.add(button16, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- button15 ----
								button15.setText("Deselect All");
								panel17.add(button15, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));
							}
							panel12.add(panel17, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						tabbedPane1.addTab("Spindle", panel12);

					}
					panel1.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 85, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//---- spinner2 ----
		spinner2.setModel(new SpinnerNumberModel(200, 0, null, 1));

		//---- spinner3 ----
		spinner3.setModel(new SpinnerNumberModel(150, 0, null, 1));

		//---- spinner1 ----
		spinner1.setModel(new SpinnerNumberModel(200, 0, null, 1));

		//---- label4 ----
		label4.setText("(mm)");

		//---- label3 ----
		label3.setText("(mm)");

		//---- label5 ----
		label5.setText("(mm)");

		//======== panel16 ========
		{
			panel16.setLayout(new GridBagLayout());
			((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
			((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- label13 ----
			label13.setText("Name");
			panel16.add(label13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(textField3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label14 ----
			label14.setText("Spindle type");
			panel16.add(label14, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(comboBox2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label6 ----
			label6.setText("Rotation speed range");
			panel16.add(label6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(spinner5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label9 ----
			label9.setText("Max power");
			panel16.add(label9, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(spinner6, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label10 ----
			label10.setText("Max torque");
			panel16.add(label10, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(spinner7, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label11 ----
			label11.setText("Coolant information");
			panel16.add(label11, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel16.add(checkBox1, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuItem1;
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JTabbedPane tabbedPane1;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel label1;
	protected JTextField textField1;
	private JLabel label7;
	protected JSpinner spinner4;
	private JLabel label8;
	protected JComboBox comboBox1;
	private JLabel label21;
	private JPanel panel18;
	private JLabel label22;
	public JSpinner spinner13;
	private JLabel label23;
	public JSpinner spinner14;
	private JPanel panel10;
	private JPanel panel13;
	private JLabel label12;
	private JPanel panel4;
	private JPanel panel6;
	private JLabel label2;
	protected JTextField textField2;
	private JPanel panel19;
	private JLabel label15;
	protected JSpinner spinner8;
	private JLabel label16;
	protected JSpinner spinner9;
	private JLabel label17;
	protected JSpinner spinner10;
	private JLabel label18;
	protected JSpinner spinner11;
	private JLabel label20;
	protected JSpinner spinner12;
	private JPanel panel20;
	private JLabel label19;
	private JScrollPane scrollPane1;
	public JTable table1;
	private JPanel panel11;
	protected JButton button1;
	protected JButton button17;
	protected JButton button2;
	protected JButton button3;
	protected JButton button4;
	protected JButton button5;
	private JPanel panel7;
	private JPanel panel8;
	private JScrollPane scrollPane2;
	public JTable table2;
	private JPanel panel14;
	protected JButton button6;
	protected JButton button7;
	protected JButton button8;
	protected JButton button9;
	protected JButton button10;
	private JPanel panel5;
	private JPanel panel9;
	private JScrollPane scrollPane3;
	public JTable table3;
	private JPanel panel15;
	protected JButton button11;
	protected JButton button12;
	private JPanel panel12;
	private JScrollPane scrollPane4;
	public JTable table4;
	private JPanel panel17;
	protected JButton button13;
	protected JButton button14;
	protected JButton button16;
	protected JButton button15;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JSpinner spinner2;
	protected JSpinner spinner3;
	protected JSpinner spinner1;
	private JLabel label4;
	private JLabel label3;
	private JLabel label5;
	private JPanel panel16;
	private JLabel label13;
	private JTextField textField3;
	private JLabel label14;
	private JComboBox comboBox2;
	private JLabel label6;
	private JSpinner spinner5;
	private JLabel label9;
	private JSpinner spinner6;
	private JLabel label10;
	private JSpinner spinner7;
	private JLabel label11;
	private JCheckBox checkBox1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
