/*
 * Created by JFormDesigner on Fri Apr 23 17:31:43 BRT 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.*;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.Borders;

/**
 * @author Victor Oliveira
 */
public class ImageFrame extends JFrame {
	private static final long serialVersionUID = 8226190298891716452L;

	public ImageFrame() {
		initComponents();
	}

	public void setIcon(Icon icon) {
		this.label1.setIcon(icon);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Victor Oliveira
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		dialogPane = new JPanel();
		label1 = new JLabel();

		//======== this ========
		setTitle("Modelo da Ferramenta");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("Arquivo");

				//---- menuItem1 ----
				menuItem1.setText("Sair");
				menu1.add(menuItem1);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//---- label1 ----
			label1.setIcon(new ImageIcon(getClass().getResource("/images/R411.5-10034D.jpg")));
			dialogPane.add(label1, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Victor Oliveira
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem menuItem1;
	private JPanel dialogPane;
	private JLabel label1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
