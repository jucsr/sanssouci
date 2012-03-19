package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class JanelaCodigoSTEP_view extends JFrame {
	public JanelaCodigoSTEP_view() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem2 = new JMenuItem();
		menuItem6 = new JMenuItem();
		menuItem3 = new JMenuItem();
		menu4 = new JMenu();
		menuItem7 = new JMenuItem();
		menuItem8 = new JMenuItem();
		menuItem9 = new JMenuItem();
		menu2 = new JMenu();
		menuItem4 = new JMenuItem();
		menu3 = new JMenu();
		menuItem5 = new JMenuItem();
		menuItem10 = new JMenuItem();
		menuItem11 = new JMenuItem();
		menuItem12 = new JMenuItem();
		dialogPane = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextPane();

		//======== this ========
		setTitle("STEP 21");
		setIconImage(new ImageIcon(getClass().getResource("/images/iconeParte21.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");

				//---- menuItem1 ----
				menuItem1.setText("Abrir");
				menuItem1.setIcon(new ImageIcon(getClass().getResource("/images/document-open16.png")));
				menu1.add(menuItem1);

				//---- menuItem2 ----
				menuItem2.setText("Salvar");
				menuItem2.setIcon(new ImageIcon(getClass().getResource("/images/document-save16.png")));
				menu1.add(menuItem2);

				//---- menuItem6 ----
				menuItem6.setText("Exportar como XML");
				menuItem6.setIcon(new ImageIcon(getClass().getResource("/images/iconeXML.png")));
				menu1.add(menuItem6);

				//---- menuItem3 ----
				menuItem3.setText("Sair");
				menuItem3.setIcon(new ImageIcon(getClass().getResource("/images/process-stop.png")));
				menu1.add(menuItem3);
			}
			menuBar1.add(menu1);

			//======== menu4 ========
			{
				menu4.setText("Edit");

				//---- menuItem7 ----
				menuItem7.setText("selecionar tudo");
				menuItem7.setIcon(new ImageIcon(getClass().getResource("/images/edit-select-all.png")));
				menu4.add(menuItem7);

				//---- menuItem8 ----
				menuItem8.setText("copiar");
				menuItem8.setIcon(new ImageIcon(getClass().getResource("/images/edit-copy.png")));
				menu4.add(menuItem8);

				//---- menuItem9 ----
				menuItem9.setText("buscar");
				menuItem9.setIcon(new ImageIcon(getClass().getResource("/images/edit-find.png")));
				menu4.add(menuItem9);
			}
			menuBar1.add(menu4);

			//======== menu2 ========
			{
				menu2.setText("Simulation");

				//---- menuItem4 ----
				menuItem4.setText("Iniciar Simula\u00e7\u00e3o");
				menu2.add(menuItem4);
			}
			menuBar1.add(menu2);

			//======== menu3 ========
			{
				menu3.setText("Post-processing");

				//---- menuItem5 ----
				menuItem5.setText("Siemens 840Di");
				menuItem5.setIcon(new ImageIcon(getClass().getResource("/images/iconeGCode.png")));
				menuItem5.setToolTipText("P\u00f3s processar para Siemens 840Di");
				menu3.add(menuItem5);

				//---- menuItem10 ----
				menuItem10.setText("Siemens 840D");
				menuItem10.setIcon(new ImageIcon(getClass().getResource("/images/iconeGCode.png")));
				menuItem10.setToolTipText("P\u00f3s processar para Siemens 840Di");
				menuItem10.setEnabled(false);
				menu3.add(menuItem10);

				//---- menuItem11 ----
				menuItem11.setText("Fanuc");
				menuItem11.setIcon(new ImageIcon(getClass().getResource("/images/iconeGCode.png")));
				menuItem11.setToolTipText("P\u00f3s processar para Siemens 840Di");
				menuItem11.setEnabled(false);
				menu3.add(menuItem11);

				//---- menuItem12 ----
				menuItem12.setText("Masak");
				menuItem12.setIcon(new ImageIcon(getClass().getResource("/images/iconeGCode.png")));
				menuItem12.setToolTipText("P\u00f3s processar para Siemens 840Di");
				menuItem12.setEnabled(false);
				menu3.add(menuItem12);
			}
			menuBar1.add(menu3);
		}
		setJMenuBar(menuBar1);

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EtchedBorder());
			dialogPane.setLayout(new BorderLayout());

			//======== scrollPane1 ========
			{

				//---- textArea1 ----
				textArea1.setEditable(false);
				textArea1.setText("STEP 21 file");
				textArea1.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
				textArea1.setSelectionColor(new Color(153, 153, 153));
				scrollPane1.setViewportView(textArea1);
			}
			dialogPane.add(scrollPane1, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(455, 500);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuItem1;
	protected JMenuItem menuItem2;
	protected JMenuItem menuItem6;
	protected JMenuItem menuItem3;
	private JMenu menu4;
	protected JMenuItem menuItem7;
	protected JMenuItem menuItem8;
	protected JMenuItem menuItem9;
	private JMenu menu2;
	protected JMenuItem menuItem4;
	private JMenu menu3;
	protected JMenuItem menuItem5;
	protected JMenuItem menuItem10;
	protected JMenuItem menuItem11;
	protected JMenuItem menuItem12;
	private JPanel dialogPane;
	private JScrollPane scrollPane1;
	protected JTextPane textArea1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
