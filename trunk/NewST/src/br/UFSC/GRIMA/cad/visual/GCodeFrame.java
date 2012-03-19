/*
 * Created by JFormDesigner on Sun May 15 16:28:43 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;

/**
 * @author Victor
 */
public class GCodeFrame extends JFrame {
	public GCodeFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem2 = new JMenuItem();
		menu2 = new JMenu();
		menuItem3 = new JMenuItem();
		menuItem4 = new JMenuItem();
		menuItem5 = new JMenuItem();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();

		//======== this ========
		setTitle("G-Code");
		setIconImage(new ImageIcon(getClass().getResource("/images/iconeGCode.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");

				//---- menuItem1 ----
				menuItem1.setText("Save");
				menuItem1.setIcon(new ImageIcon(getClass().getResource("/images/document-save16.png")));
				menu1.add(menuItem1);

				//---- menuItem2 ----
				menuItem2.setText("Quit");
				menuItem2.setIcon(new ImageIcon(getClass().getResource("/images/process-stop.png")));
				menu1.add(menuItem2);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Edit");

				//---- menuItem3 ----
				menuItem3.setText("Select all");
				menuItem3.setIcon(new ImageIcon(getClass().getResource("/images/edit-select-all.png")));
				menu2.add(menuItem3);

				//---- menuItem4 ----
				menuItem4.setText("Copy");
				menuItem4.setIcon(new ImageIcon(getClass().getResource("/images/edit-copy.png")));
				menu2.add(menuItem4);

				//---- menuItem5 ----
				menuItem5.setText("Find");
				menuItem5.setIcon(new ImageIcon(getClass().getResource("/images/edit-find-replace.png")));
				menu2.add(menuItem5);
			}
			menuBar1.add(menu2);
		}
		setJMenuBar(menuBar1);

		//======== scrollPane1 ========
		{

			//---- textPane1 ----
			textPane1.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
			textPane1.setSelectionColor(new Color(153, 153, 153));
			scrollPane1.setViewportView(textPane1);
		}
		contentPane.add(scrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));
		setSize(445, 480);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuItem1;
	protected JMenuItem menuItem2;
	private JMenu menu2;
	protected JMenuItem menuItem3;
	protected JMenuItem menuItem4;
	protected JMenuItem menuItem5;
	private JScrollPane scrollPane1;
	public JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
