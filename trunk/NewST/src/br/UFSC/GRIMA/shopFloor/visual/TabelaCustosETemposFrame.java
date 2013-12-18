/*
 * Created by JFormDesigner on Wed Sep 11 18:57:20 BRT 2013
 */

package br.UFSC.GRIMA.shopFloor.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import com.jgoodies.forms.layout.*;


public class TabelaCustosETemposFrame extends JDialog {
	public TabelaCustosETemposFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public TabelaCustosETemposFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel5 = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel6 = new JPanel();
		splitPane1 = new JSplitPane();
		panel1 = new JPanel();
		panel3 = new JPanel();
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		panel2 = new JPanel();
		panel4 = new JPanel();
		label2 = new JLabel();
		scrollPane2 = new JScrollPane();
		table2 = new JTable();
		panel7 = new JPanel();
		panel10 = new JPanel();
		splitPane2 = new JSplitPane();
		panel11 = new JPanel();
		panel12 = new JPanel();
		label3 = new JLabel();
		scrollPane3 = new JScrollPane();
		table3 = new JTable();
		panel13 = new JPanel();
		panel14 = new JPanel();
		label4 = new JLabel();
		scrollPane4 = new JScrollPane();
		table4 = new JTable();
		panel8 = new JPanel();
		panel9 = new JPanel();
		scrollPane5 = new JScrollPane();
		table5 = new JTable();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("Cost & Time Matrix");
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

				//======== panel5 ========
				{
					panel5.setLayout(new FormLayout(
						"default, $lcgap, default:grow",
						"default, $lgap, default:grow, $lgap, default"));

					//======== tabbedPane1 ========
					{

						//======== panel6 ========
						{
							panel6.setLayout(new GridBagLayout());
							((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

							//======== splitPane1 ========
							{
								splitPane1.setResizeWeight(0.5);
								splitPane1.setOneTouchExpandable(true);

								//======== panel1 ========
								{
									panel1.setLayout(new GridBagLayout());
									((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

									//======== panel3 ========
									{
										panel3.setLayout(new GridBagLayout());
										((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
										((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

										//---- label1 ----
										label1.setText("COST MATRIX [$]");
										panel3.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));
									}
									panel1.add(panel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//======== scrollPane1 ========
									{

										//---- table1 ----
										table1.setModel(new DefaultTableModel(
											new Object[][] {
												{null, null, null},
												{null, null, null},
											},
											new String[] {
												"OPERATION", "ID", "PRIORITY"
											}
										));
										scrollPane1.setViewportView(table1);
									}
									panel1.add(scrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));
								}
								splitPane1.setLeftComponent(panel1);

								//======== panel2 ========
								{
									panel2.setLayout(new GridBagLayout());
									((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

									//======== panel4 ========
									{
										panel4.setLayout(new GridBagLayout());
										((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
										((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

										//---- label2 ----
										label2.setText("PATH MATRIX");
										panel4.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 5), 0, 0));
									}
									panel2.add(panel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//======== scrollPane2 ========
									{

										//---- table2 ----
										table2.setModel(new DefaultTableModel(
											new Object[][] {
												{null, null, null},
												{null, null, null},
											},
											new String[] {
												"OPERATIONS", "ID", "PRIORITY"
											}
										));
										scrollPane2.setViewportView(table2);
									}
									panel2.add(scrollPane2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));
								}
								splitPane1.setRightComponent(panel2);
							}
							panel6.add(splitPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						tabbedPane1.addTab("Cost", panel6);


						//======== panel7 ========
						{
							panel7.setLayout(new GridBagLayout());
							((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

							//======== panel10 ========
							{
								panel10.setLayout(new GridBagLayout());
								((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
								((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

								//======== splitPane2 ========
								{
									splitPane2.setResizeWeight(0.5);
									splitPane2.setOneTouchExpandable(true);

									//======== panel11 ========
									{
										panel11.setLayout(new GridBagLayout());
										((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
										((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

										//======== panel12 ========
										{
											panel12.setLayout(new GridBagLayout());
											((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
											((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
											((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
											((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

											//---- label3 ----
											label3.setText("TIME MATRIX [minutes]");
											panel12.add(label3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 5, 5), 0, 0));
										}
										panel11.add(panel12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//======== scrollPane3 ========
										{

											//---- table3 ----
											table3.setModel(new DefaultTableModel(
												new Object[][] {
													{null, null, null},
													{null, null, null},
												},
												new String[] {
													"OPERATION", "ID", "PRIORITY"
												}
											));
											scrollPane3.setViewportView(table3);
										}
										panel11.add(scrollPane3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));
									}
									splitPane2.setLeftComponent(panel11);

									//======== panel13 ========
									{
										panel13.setLayout(new GridBagLayout());
										((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
										((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

										//======== panel14 ========
										{
											panel14.setLayout(new GridBagLayout());
											((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
											((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
											((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
											((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

											//---- label4 ----
											label4.setText("PATH MATRIX");
											panel14.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 5, 5), 0, 0));
										}
										panel13.add(panel14, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));

										//======== scrollPane4 ========
										{

											//---- table4 ----
											table4.setModel(new DefaultTableModel(
												new Object[][] {
													{null, null, null},
													{null, null, null},
												},
												new String[] {
													"OPERATIONS", "ID", "PRIORITY"
												}
											));
											scrollPane4.setViewportView(table4);
										}
										panel13.add(scrollPane4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 5, 0), 0, 0));
									}
									splitPane2.setRightComponent(panel13);
								}
								panel10.add(splitPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));
							}
							panel7.add(panel10, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						tabbedPane1.addTab("Time", panel7);


						//======== panel8 ========
						{
							panel8.setLayout(new GridBagLayout());
							((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

							//======== panel9 ========
							{
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

								//======== scrollPane5 ========
								{

									//---- table5 ----
									table5.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
											"Workingstep", "Machine", "Cost", "Time"
										}
									));
									scrollPane5.setViewportView(table5);
								}
								panel9.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));
							}
							panel8.add(panel9, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						tabbedPane1.addTab("Routing", panel8);

					}
					panel5.add(tabbedPane1, cc.xy(3, 3));
				}
				contentPanel.add(panel5, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
	private JPanel panel5;
	private JTabbedPane tabbedPane1;
	private JPanel panel6;
	private JSplitPane splitPane1;
	private JPanel panel1;
	private JPanel panel3;
	private JLabel label1;
	private JScrollPane scrollPane1;
	protected JTable table1;
	private JPanel panel2;
	private JPanel panel4;
	private JLabel label2;
	private JScrollPane scrollPane2;
	protected JTable table2;
	private JPanel panel7;
	private JPanel panel10;
	private JSplitPane splitPane2;
	private JPanel panel11;
	private JPanel panel12;
	private JLabel label3;
	private JScrollPane scrollPane3;
	protected JTable table3;
	private JPanel panel13;
	private JPanel panel14;
	private JLabel label4;
	private JScrollPane scrollPane4;
	protected JTable table4;
	private JPanel panel8;
	private JPanel panel9;
	private JScrollPane scrollPane5;
	public JTable table5;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
