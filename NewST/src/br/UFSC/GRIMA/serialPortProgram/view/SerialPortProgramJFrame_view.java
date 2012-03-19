package br.UFSC.GRIMA.serialPortProgram.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class SerialPortProgramJFrame_view extends JFrame {
	public SerialPortProgramJFrame_view() {
		initComponents();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - jc cnc
		m_menuBar = new JMenuBar();
		m_principalMenu = new JMenu();
		m_abiriMenuItem = new JMenuItem();
		menuItem1 = new JMenuItem();
		m_sairMenuItem = new JMenuItem();
		m_ajudaMenu = new JMenu();
		m_ajudaMenuItem = new JMenuItem();
		panel1 = new JPanel();
		label4 = new JLabel();
		m_userName = new JLabel();
		label6 = new JLabel();
		m_material = new JLabel();
		label5 = new JLabel();
		m_projectName = new JLabel();
		label7 = new JLabel();
		m_quantity = new JLabel();
		m_princpalPanel = new JPanel();
		label1 = new JLabel();
		m_facesComFeaturesComboBox = new JComboBox();
		m_visualizarButton = new JButton();
		label2 = new JLabel();
		m_nextRadioButton = new JRadioButton();
		m_proximaFaceLabel = new JLabel();
		m_escolherFaceRadioButton = new JRadioButton();
		m_escolherFaceComboBox = new JComboBox();
		m_usinarButton = new JButton();

		//======== this ========
		setTitle("CAD por features - Comunica\u00e7\u00e3o com Porta Serial");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {25, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//======== m_menuBar ========
		{

			//======== m_principalMenu ========
			{
				m_principalMenu.setText("Principal");

				//---- m_abiriMenuItem ----
				m_abiriMenuItem.setText("Abrir HPG");
				m_principalMenu.add(m_abiriMenuItem);

				//---- menuItem1 ----
				menuItem1.setText("Abrir HPG salvo no servidor");
				m_principalMenu.add(menuItem1);
				m_principalMenu.addSeparator();

				//---- m_sairMenuItem ----
				m_sairMenuItem.setText("Sair");
				m_principalMenu.add(m_sairMenuItem);
			}
			m_menuBar.add(m_principalMenu);

			//======== m_ajudaMenu ========
			{
				m_ajudaMenu.setText("Ajuda");

				//---- m_ajudaMenuItem ----
				m_ajudaMenuItem.setText("Ajuda");
				m_ajudaMenu.add(m_ajudaMenuItem);
			}
			m_menuBar.add(m_ajudaMenu);
		}
		contentPane.add(m_menuBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder(null, "Informa\u00e7\u00f5es sobre o projeto", TitledBorder.LEADING, TitledBorder.TOP));

			// JFormDesigner evaluation mark
			panel1.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//---- label4 ----
			label4.setText("Usu\u00e1rio:");
			label4.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel1.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- m_userName ----
			m_userName.setForeground(Color.blue);
			panel1.add(m_userName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label6 ----
			label6.setText("Material:");
			label6.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel1.add(label6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- m_material ----
			m_material.setForeground(Color.blue);
			panel1.add(m_material, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label5 ----
			label5.setText("Projeto:");
			label5.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel1.add(label5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- m_projectName ----
			m_projectName.setForeground(Color.blue);
			panel1.add(m_projectName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label7 ----
			label7.setText("Quantidade:");
			label7.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel1.add(label7, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- m_quantity ----
			m_quantity.setForeground(Color.blue);
			panel1.add(m_quantity, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== m_princpalPanel ========
		{
			m_princpalPanel.setBorder(new TitledBorder(null, "Usinagem", TitledBorder.LEADING, TitledBorder.TOP));
			m_princpalPanel.setLayout(new GridBagLayout());
			((GridBagLayout)m_princpalPanel.getLayout()).columnWidths = new int[] {0, 0, 175, 0};
			((GridBagLayout)m_princpalPanel.getLayout()).rowHeights = new int[] {0, 20, 0, 0, 0, 0};
			((GridBagLayout)m_princpalPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)m_princpalPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- label1 ----
			label1.setText("Faces com features:");
			m_princpalPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			m_princpalPanel.add(m_facesComFeaturesComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- m_visualizarButton ----
			m_visualizarButton.setText("Visualizar C\u00f3digo HPGL");
			m_princpalPanel.add(m_visualizarButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label2 ----
			label2.setText("Usinar face:");
			m_princpalPanel.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- m_nextRadioButton ----
			m_nextRadioButton.setText("Pr\u00f3xima na sequencia");
			m_nextRadioButton.setSelected(true);
			m_princpalPanel.add(m_nextRadioButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- m_proximaFaceLabel ----
			m_proximaFaceLabel.setText("FACE XY");
			m_proximaFaceLabel.setForeground(Color.blue);
			m_proximaFaceLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			m_princpalPanel.add(m_proximaFaceLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- m_escolherFaceRadioButton ----
			m_escolherFaceRadioButton.setText("Escolher face");
			m_princpalPanel.add(m_escolherFaceRadioButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			m_princpalPanel.add(m_escolherFaceComboBox, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- m_usinarButton ----
			m_usinarButton.setText("Usinar");
			m_princpalPanel.add(m_usinarButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(m_princpalPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		//JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - jc cnc
	protected JMenuBar m_menuBar;
	protected JMenu m_principalMenu;
	protected JMenuItem m_abiriMenuItem;
	protected JMenuItem menuItem1;
	protected JMenuItem m_sairMenuItem;
	protected JMenu m_ajudaMenu;
	protected JMenuItem m_ajudaMenuItem;
	protected JPanel panel1;
	protected JLabel label4;
	protected JLabel m_userName;
	protected JLabel label6;
	protected JLabel m_material;
	protected JLabel label5;
	protected JLabel m_projectName;
	protected JLabel label7;
	protected JLabel m_quantity;
	protected JPanel m_princpalPanel;
	protected JLabel label1;
	protected JComboBox m_facesComFeaturesComboBox;
	protected JButton m_visualizarButton;
	protected JLabel label2;
	protected JRadioButton m_nextRadioButton;
	protected JLabel m_proximaFaceLabel;
	protected JRadioButton m_escolherFaceRadioButton;
	protected JComboBox m_escolherFaceComboBox;
	protected JButton m_usinarButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

}
