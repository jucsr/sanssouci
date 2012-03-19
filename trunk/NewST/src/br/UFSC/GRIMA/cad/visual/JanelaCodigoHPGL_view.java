/*
 * Created by JFormDesigner on Mon Feb 26 13:41:18 BRT 2007
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;

/**
 * @author Roman Lorenzo
 */
public class JanelaCodigoHPGL_view extends JFrame {
	public JanelaCodigoHPGL_view() {
		initComponents();
	}

	private void initComponents() {
		//JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Victor Oliveira
		menuBar = new JMenuBar();
		fileJMenu = new JMenu();
		saveJMenuItem = new JMenuItem();
		exitJMenuItem = new JMenuItem();
		dialogPane = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea = new JTextArea();

		//======== this ========
		setTitle("C\u00f3digo HPGL gerado");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar ========
		{

			//======== fileJMenu ========
			{
				fileJMenu.setText("Arquivo");

				//---- saveJMenuItem ----
				saveJMenuItem.setText("Salvar...");
				fileJMenu.add(saveJMenuItem);
				fileJMenu.addSeparator();

				//---- exitJMenuItem ----
				exitJMenuItem.setText("Sair");
				fileJMenu.add(exitJMenuItem);
			}
			menuBar.add(fileJMenu);
		}
		setJMenuBar(menuBar);

		//======== dialogPane ========
		{

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(textArea);
			}
			dialogPane.add(scrollPane1, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		//JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Victor Oliveira
	protected JMenuBar menuBar;
	protected JMenu fileJMenu;
	public JMenuItem saveJMenuItem;
	public JMenuItem exitJMenuItem;
	protected JPanel dialogPane;
	protected JScrollPane scrollPane1;
	protected JTextArea textArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
