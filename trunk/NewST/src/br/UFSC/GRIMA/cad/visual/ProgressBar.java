/*
 * Created by JFormDesigner on Mon Mar 14 16:03:16 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;



public class ProgressBar extends JDialog {
	public ProgressBar(Frame owner) {
		super(owner);
		initComponents();
	}

	public ProgressBar(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		progressBar1 = new JProgressBar();

		//======== this ========
		setTitle("Acessando Banco de Ferramentas");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {25, 0, 20, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 25, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};

		//---- progressBar1 ----
		progressBar1.setForeground(new Color(0, 51, 255));
		progressBar1.setIndeterminate(true);
		contentPane.add(progressBar1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		setSize(330, 95);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	public JProgressBar progressBar1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
