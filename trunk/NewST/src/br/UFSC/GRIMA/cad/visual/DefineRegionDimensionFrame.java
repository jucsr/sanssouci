/*
 * Created by JFormDesigner on Mon Aug 13 17:36:53 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author l
 */
public class DefineRegionDimensionFrame extends JDialog {
	public DefineRegionDimensionFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public DefineRegionDimensionFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane7 = new JScrollPane();
		panel4 = new JPanel();
		panel6 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		posicaoX = new JSpinner();
		posicaoY = new JSpinner();
		largura = new JSpinner();
		altura = new JSpinner();
		panel5 = new JPanel();
		panel7 = new JPanel();
		ok = new JButton();
		cancel = new JButton();

		//======== this ========
		setTitle("Define Region Dimension");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane7 ========
		{

			//======== panel4 ========
			{
				panel4.setLayout(new GridBagLayout());
				((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {192, 172, 0};
				((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {161, 37, 0};
				((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//======== panel6 ========
				{
					panel6.setBorder(new TitledBorder("Dimension in milimeters"));
					panel6.setLayout(new GridBagLayout());
					((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {62, 52, 0, 0};
					((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
					((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
					((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

					//---- label1 ----
					label1.setText("X Position");
					panel6.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label2 ----
					label2.setText("Y Position");
					panel6.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label3 ----
					label3.setText("Width");
					panel6.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label4 ----
					label4.setText("Height");
					panel6.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
					panel6.add(posicaoX, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					panel6.add(posicaoY, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					panel6.add(largura, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					panel6.add(altura, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				panel4.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 30, 30), 0, 0));

				//======== panel5 ========
				{
					panel5.setLayout(new FlowLayout());
				}
				panel4.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 30, 0), 0, 0));

				//======== panel7 ========
				{
					panel7.setLayout(new GridBagLayout());
					((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {12};

					//---- ok ----
					ok.setText("Ok");
					panel7.add(ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- cancel ----
					cancel.setText("Cancel");
					panel7.add(cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				panel4.add(panel7, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			scrollPane7.setViewportView(panel4);
		}
		contentPane.add(scrollPane7, BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane7;
	private JPanel panel4;
	private JPanel panel6;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	protected JSpinner posicaoX;
	protected JSpinner posicaoY;
	protected JSpinner largura;
	protected JSpinner altura;
	public JPanel panel5;
	private JPanel panel7;
	protected JButton ok;
	protected JButton cancel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
