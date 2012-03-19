/*
 * Created by JFormDesigner on Sun Jun 05 15:15:32 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author Victor
 */
public class ProjectToolsFrame extends JFrame {
	public ProjectToolsFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem2 = new JMenuItem();
		menuItem3 = new JMenuItem();
		menu2 = new JMenu();
		loadCatalogTools = new JMenuItem();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		twistDrillTable = new JTable();
		panel10 = new JPanel();
		addNewTwistDrill = new JButton();
		buttonSelectNone2 = new JButton();
		panel2 = new JPanel();
		scrollPane2 = new JScrollPane();
		centerDrillTable = new JTable();
		panel11 = new JPanel();
		addNewCenterDrill = new JButton();
		buttonSelectNone3 = new JButton();
		panel3 = new JPanel();
		scrollPane3 = new JScrollPane();
		faceMillTable = new JTable();
		panel12 = new JPanel();
		addNewFaceMill = new JButton();
		buttonSelectNone4 = new JButton();
		panel4 = new JPanel();
		scrollPane4 = new JScrollPane();
		endMillTable = new JTable();
		panel13 = new JPanel();
		addNewEndMill = new JButton();
		buttonSelectNone5 = new JButton();
		panel5 = new JPanel();
		scrollPane5 = new JScrollPane();
		ballEndMillTable = new JTable();
		panel14 = new JPanel();
		addNewBallEndMill = new JButton();
		buttonSelectNone6 = new JButton();
		panel6 = new JPanel();
		scrollPane6 = new JScrollPane();
		bullnoseEndMillTable = new JTable();
		panel15 = new JPanel();
		addNewBullnoseEndMill = new JButton();
		buttonSelectNone7 = new JButton();
		panel7 = new JPanel();
		scrollPane7 = new JScrollPane();
		boringToolTable = new JTable();
		panel16 = new JPanel();
		addNewBoringTool = new JButton();
		buttonSelectNone8 = new JButton();
		panel8 = new JPanel();
		scrollPane8 = new JScrollPane();
		reamerTable = new JTable();
		panel9 = new JPanel();
		addNewReamer = new JButton();
		buttonSelectNone = new JButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Project Tools - ");
		setIconImage(new ImageIcon(getClass().getResource("/images/preferences-system.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");
				menu1.setFont(menu1.getFont().deriveFont(menu1.getFont().getStyle() | Font.BOLD));

				//---- menuItem1 ----
				menuItem1.setText("Open");
				menuItem1.setToolTipText("Open set of tools saved in data base");
				menuItem1.setIcon(new ImageIcon(getClass().getResource("/images/document-open16.png")));
				menu1.add(menuItem1);

				//---- menuItem2 ----
				menuItem2.setText("Save");
				menuItem2.setToolTipText("Save this set of tools into data base");
				menuItem2.setIcon(new ImageIcon(getClass().getResource("/images/document-save16.png")));
				menu1.add(menuItem2);

				//---- menuItem3 ----
				menuItem3.setText("Quit");
				menuItem3.setIcon(new ImageIcon(getClass().getResource("/images/dialog-error.png")));
				menu1.add(menuItem3);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Tools");
				menu2.setFont(menu2.getFont().deriveFont(menu2.getFont().getStyle() | Font.BOLD));

				//---- loadCatalogTools ----
				loadCatalogTools.setText("Load Catalog Tools from Data Base");
				menu2.add(loadCatalogTools);
			}
			menuBar1.add(menu2);
		}
		setJMenuBar(menuBar1);

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== tabbedPane1 ========
				{

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane1 ========
						{

							//---- twistDrillTable ----
							twistDrillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Tool Tip Half Angle", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = twistDrillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(60);
								cm.getColumn(7).setPreferredWidth(105);
								cm.getColumn(8).setMinWidth(50);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(75);
								cm.getColumn(10).setMinWidth(40);
								cm.getColumn(10).setPreferredWidth(80);
							}
							twistDrillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane1.setViewportView(twistDrillTable);
						}
						panel1.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel10 ========
						{
							panel10.setLayout(new GridBagLayout());
							((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewTwistDrill ----
							addNewTwistDrill.setText("Add new Twist Drill");
							panel10.add(addNewTwistDrill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone2 ----
							buttonSelectNone2.setText("Remove Twist Drill");
							panel10.add(buttonSelectNone2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel1.add(panel10, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Twist Drills", panel1);


					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane2 ========
						{

							//---- centerDrillTable ----
							centerDrillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Tool Tip Half Angle", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = centerDrillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(60);
								cm.getColumn(7).setPreferredWidth(105);
								cm.getColumn(8).setMinWidth(50);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(75);
								cm.getColumn(10).setMinWidth(40);
								cm.getColumn(10).setPreferredWidth(80);
							}
							centerDrillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane2.setViewportView(centerDrillTable);
						}
						panel2.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel11 ========
						{
							panel11.setLayout(new GridBagLayout());
							((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewCenterDrill ----
							addNewCenterDrill.setText("Add new Center Drill");
							panel11.add(addNewCenterDrill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone3 ----
							buttonSelectNone3.setText("Remove Center Drill");
							panel11.add(buttonSelectNone3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel2.add(panel11, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Center Drills", panel2);


					//======== panel3 ========
					{
						panel3.setLayout(new GridBagLayout());
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane3 ========
						{

							//---- faceMillTable ----
							faceMillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = faceMillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(50);
								cm.getColumn(7).setPreferredWidth(75);
								cm.getColumn(8).setMinWidth(40);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(80);
							}
							faceMillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							faceMillTable.setRequestFocusEnabled(false);
							scrollPane3.setViewportView(faceMillTable);
						}
						panel3.add(scrollPane3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel12 ========
						{
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewFaceMill ----
							addNewFaceMill.setText("Add new Face Mill");
							panel12.add(addNewFaceMill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone4 ----
							buttonSelectNone4.setText("Remove Face Mill");
							panel12.add(buttonSelectNone4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel3.add(panel12, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Face Mills", panel3);


					//======== panel4 ========
					{
						panel4.setLayout(new GridBagLayout());
						((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane4 ========
						{

							//---- endMillTable ----
							endMillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = endMillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(50);
								cm.getColumn(7).setPreferredWidth(75);
								cm.getColumn(8).setMinWidth(40);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(80);
							}
							endMillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane4.setViewportView(endMillTable);
						}
						panel4.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel13 ========
						{
							panel13.setLayout(new GridBagLayout());
							((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewEndMill ----
							addNewEndMill.setText("Add new End Mill");
							panel13.add(addNewEndMill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone5 ----
							buttonSelectNone5.setText("Remove End Mill");
							panel13.add(buttonSelectNone5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel4.add(panel13, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("End Mills", panel4);


					//======== panel5 ========
					{
						panel5.setLayout(new GridBagLayout());
						((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane5 ========
						{

							//---- ballEndMillTable ----
							ballEndMillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Edge Radius", "Edge Center Vertical", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Object.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = ballEndMillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(60);
								cm.getColumn(7).setPreferredWidth(70);
								cm.getColumn(8).setMinWidth(60);
								cm.getColumn(8).setPreferredWidth(110);
								cm.getColumn(9).setMinWidth(50);
								cm.getColumn(9).setPreferredWidth(75);
								cm.getColumn(10).setMinWidth(40);
								cm.getColumn(10).setPreferredWidth(75);
								cm.getColumn(11).setMinWidth(40);
								cm.getColumn(11).setPreferredWidth(80);
							}
							ballEndMillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane5.setViewportView(ballEndMillTable);
						}
						panel5.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel14 ========
						{
							panel14.setLayout(new GridBagLayout());
							((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewBallEndMill ----
							addNewBallEndMill.setText("Add new Ball End Mill");
							panel14.add(addNewBallEndMill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone6 ----
							buttonSelectNone6.setText("Remove Ball End Mill");
							panel14.add(buttonSelectNone6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel5.add(panel14, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Ball End Mills", panel5);


					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane6 ========
						{

							//---- bullnoseEndMillTable ----
							bullnoseEndMillTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Edge Radius", "Edge Center Vertical", "Edge Center Horizontal", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Object.class, Object.class, Double.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = bullnoseEndMillTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(60);
								cm.getColumn(7).setPreferredWidth(70);
								cm.getColumn(8).setMinWidth(60);
								cm.getColumn(8).setPreferredWidth(110);
								cm.getColumn(9).setMinWidth(60);
								cm.getColumn(9).setPreferredWidth(125);
								cm.getColumn(10).setMinWidth(50);
								cm.getColumn(10).setPreferredWidth(75);
								cm.getColumn(11).setMinWidth(40);
								cm.getColumn(11).setPreferredWidth(75);
								cm.getColumn(12).setMinWidth(40);
								cm.getColumn(12).setPreferredWidth(80);
							}
							bullnoseEndMillTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane6.setViewportView(bullnoseEndMillTable);
						}
						panel6.add(scrollPane6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel15 ========
						{
							panel15.setLayout(new GridBagLayout());
							((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewBullnoseEndMill ----
							addNewBullnoseEndMill.setText("Add new Bullnose End Mill");
							panel15.add(addNewBullnoseEndMill, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone7 ----
							buttonSelectNone7.setText("Remove Bullnose End Mill");
							panel15.add(buttonSelectNone7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel6.add(panel15, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Bullnose End Mills", panel6);


					//======== panel7 ========
					{
						panel7.setLayout(new GridBagLayout());
						((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane7 ========
						{

							//---- boringToolTable ----
							boringToolTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Edge Radius", "Hand Of Cut", "Material", "Material Class", "Acoplamento"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = boringToolTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(65);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(60);
								cm.getColumn(7).setPreferredWidth(70);
								cm.getColumn(8).setMinWidth(50);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(75);
								cm.getColumn(10).setMinWidth(40);
								cm.getColumn(10).setPreferredWidth(80);
								cm.getColumn(11).setMinWidth(45);
								cm.getColumn(11).setPreferredWidth(70);
							}
							boringToolTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane7.setViewportView(boringToolTable);
						}
						panel7.add(scrollPane7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel16 ========
						{
							panel16.setLayout(new GridBagLayout());
							((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewBoringTool ----
							addNewBoringTool.setText("Add new Boring Tool");
							panel16.add(addNewBoringTool, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone8 ----
							buttonSelectNone8.setText("Remove Boring Tool");
							panel16.add(buttonSelectNone8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel7.add(panel16, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Boring Tools", panel7);


					//======== panel8 ========
					{
						panel8.setLayout(new GridBagLayout());
						((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

						//======== scrollPane8 ========
						{

							//---- reamerTable ----
							reamerTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Id", "Name", "Diameter", "Dm", "Cutting Edge Length", "Max Depth", "Off Set Length", "Number Of Teeth", "Hand Of Cut", "Material", "Material Class"
								}
							) {
								Class<?>[] columnTypes = new Class<?>[] {
									Integer.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Integer.class, String.class, String.class, String.class
								};
								@Override
								public Class<?> getColumnClass(int columnIndex) {
									return columnTypes[columnIndex];
								}
							});
							{
								TableColumnModel cm = reamerTable.getColumnModel();
								cm.getColumn(0).setMinWidth(25);
								cm.getColumn(0).setPreferredWidth(40);
								cm.getColumn(1).setMinWidth(50);
								cm.getColumn(1).setPreferredWidth(165);
								cm.getColumn(2).setMinWidth(45);
								cm.getColumn(2).setPreferredWidth(55);
								cm.getColumn(3).setMinWidth(25);
								cm.getColumn(3).setPreferredWidth(50);
								cm.getColumn(4).setMinWidth(85);
								cm.getColumn(4).setPreferredWidth(110);
								cm.getColumn(5).setMinWidth(55);
								cm.getColumn(5).setPreferredWidth(65);
								cm.getColumn(6).setMinWidth(60);
								cm.getColumn(6).setPreferredWidth(85);
								cm.getColumn(7).setMinWidth(75);
								cm.getColumn(7).setPreferredWidth(95);
								cm.getColumn(8).setMinWidth(50);
								cm.getColumn(8).setPreferredWidth(75);
								cm.getColumn(9).setMinWidth(40);
								cm.getColumn(9).setPreferredWidth(75);
								cm.getColumn(10).setMinWidth(40);
								cm.getColumn(10).setPreferredWidth(80);
							}
							reamerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							scrollPane8.setViewportView(reamerTable);
						}
						panel8.add(scrollPane8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel9 ========
						{
							panel9.setLayout(new GridBagLayout());
							((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- addNewReamer ----
							addNewReamer.setText("Add new Reamer");
							panel9.add(addNewReamer, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- buttonSelectNone ----
							buttonSelectNone.setText("Remove Reamer");
							panel9.add(buttonSelectNone, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel8.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					tabbedPane1.addTab("Reamers", panel8);

				}
				contentPanel.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
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
		setSize(985, 440);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuItem1;
	protected JMenuItem menuItem2;
	protected JMenuItem menuItem3;
	private JMenu menu2;
	protected JMenuItem loadCatalogTools;
	private JPanel dialogPane;
	private JPanel contentPanel;
	protected JTabbedPane tabbedPane1;
	protected JPanel panel1;
	protected JScrollPane scrollPane1;
	public JTable twistDrillTable;
	private JPanel panel10;
	protected JButton addNewTwistDrill;
	protected JButton buttonSelectNone2;
	private JPanel panel2;
	protected JScrollPane scrollPane2;
	public JTable centerDrillTable;
	private JPanel panel11;
	protected JButton addNewCenterDrill;
	protected JButton buttonSelectNone3;
	private JPanel panel3;
	protected JScrollPane scrollPane3;
	public JTable faceMillTable;
	private JPanel panel12;
	protected JButton addNewFaceMill;
	protected JButton buttonSelectNone4;
	private JPanel panel4;
	protected JScrollPane scrollPane4;
	public JTable endMillTable;
	private JPanel panel13;
	protected JButton addNewEndMill;
	protected JButton buttonSelectNone5;
	private JPanel panel5;
	protected JScrollPane scrollPane5;
	public JTable ballEndMillTable;
	private JPanel panel14;
	protected JButton addNewBallEndMill;
	protected JButton buttonSelectNone6;
	private JPanel panel6;
	protected JScrollPane scrollPane6;
	public JTable bullnoseEndMillTable;
	private JPanel panel15;
	protected JButton addNewBullnoseEndMill;
	protected JButton buttonSelectNone7;
	private JPanel panel7;
	protected JScrollPane scrollPane7;
	public JTable boringToolTable;
	private JPanel panel16;
	protected JButton addNewBoringTool;
	protected JButton buttonSelectNone8;
	private JPanel panel8;
	protected JScrollPane scrollPane8;
	public JTable reamerTable;
	private JPanel panel9;
	protected JButton addNewReamer;
	protected JButton buttonSelectNone;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
