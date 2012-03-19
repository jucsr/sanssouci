/*
 * Created by JFormDesigner on Thu Apr 07 22:10:05 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;

/**
 * @author Victor
 */
public class Progress3D extends JDialog {
	public Progress3D(Frame owner) {
		super(owner);
		initComponents();
	}

	public Progress3D(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		progressBar1 = new JProgressBar();

		//======== this ========
		setTitle("Generating 3D model");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {15, 205, 10, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {15, 25, 10, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

		//---- progressBar1 ----
		progressBar1.setIndeterminate(true);
		contentPane.add(progressBar1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JProgressBar progressBar1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
