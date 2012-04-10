/*
 * Created by JFormDesigner on Thu Apr 05 16:31:00 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;

/**
 * @author edu
 */
public class PontosDeApoioFrame2 extends JDialog {
	public PontosDeApoioFrame2(Frame owner) {
		super(owner);
		initComponents();
	}

	public PontosDeApoioFrame2(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		autoGenButton = new JButton();
		comboBox1 = new JComboBox();
		panel1 = new JPanel();

		//======== this ========
		setTitle("Define Climp Points");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 5, 117, 5, 173, 5, 263, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 5, 0, 5, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- autoGenButton ----
		autoGenButton.setText("Generate Points");
		contentPane.add(autoGenButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- comboBox1 ----
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {
			"Setup1",
			"Setup2",
			"\t"
		}));
		contentPane.add(comboBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
		}
		contentPane.add(panel1, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	protected JButton autoGenButton;
	private JComboBox comboBox1;
	private JPanel panel1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
