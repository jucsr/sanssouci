/*
 * Created by JFormDesigner on Tue Mar 20 17:34:16 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Lucas
 */
public class CriarBossFrame extends JDialog {
	public CriarBossFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CriarBossFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		label1 = new JLabel();
		buttonRectangular = new JRadioButton();
		buttonCircular = new JRadioButton();
		panel2 = new JPanel();
		buttonCancel = new JButton();
		buttonOK = new JButton();

		//======== this ========
		setTitle("Boss Specification");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 5, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- label1 ----
			label1.setText("Choose the Boss type");
			panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonRectangular ----
			buttonRectangular.setText("Rectangular");
			panel1.add(buttonRectangular, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonCircular ----
			buttonCircular.setText("Circular");
			buttonCircular.setSelected(true);
			panel1.add(buttonCircular, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		}
		contentPane.add(panel1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- buttonCancel ----
			buttonCancel.setText("Cancel");
			panel2.add(buttonCancel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- buttonOK ----
			buttonOK.setText("Ok");
			panel2.add(buttonOK, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel2, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(buttonRectangular);
		buttonGroup1.add(buttonCircular);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JLabel label1;
	protected JRadioButton buttonRectangular;
	protected JRadioButton buttonCircular;
	private JPanel panel2;
	protected JButton buttonCancel;
	protected JButton buttonOK;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
