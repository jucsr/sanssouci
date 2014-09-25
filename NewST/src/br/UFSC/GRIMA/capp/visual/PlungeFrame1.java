/*
 * Created by JFormDesigner on Tue Aug 26 16:36:09 BRT 2014
 */

package br.UFSC.GRIMA.capp.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author jc
 */
public class PlungeFrame1 extends JFrame {
	public PlungeFrame1() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel3 = new JPanel();
		panel1 = new JPanel();
		bolaVert = new JRadioButton();
		bolaRamp = new JRadioButton();
		bolaHelix = new JRadioButton();
		bolaZigzag = new JRadioButton();
		label2 = new JLabel();
		width = new JSpinner();
		label3 = new JLabel();
		angle = new JSpinner();
		label4 = new JLabel();
		radius = new JSpinner();
		panel2 = new JPanel();
		label1 = new JLabel();
		panel4 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setTitle("Plunge Strategies");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== panel3 ========
		{
			panel3.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel1 ========
			{
				panel1.setLayout(new GridBagLayout());
				((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {18, 75, 0, 0};
				((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {100, 0, 0, 0, 0, 50, 10, 20, 10, 10, 20, 10, 0, 20, 50, 0};
				((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- bolaVert ----
				bolaVert.setText("Vertical");
				bolaVert.setSelected(true);
				panel1.add(bolaVert, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaRamp ----
				bolaRamp.setText("Ramp");
				panel1.add(bolaRamp, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaHelix ----
				bolaHelix.setText("Helix");
				panel1.add(bolaHelix, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaZigzag ----
				bolaZigzag.setText("Zig-Zag");
				panel1.add(bolaZigzag, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label2 ----
				label2.setText("Width");
				panel1.add(label2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- width ----
				width.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				panel1.add(width, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label3 ----
				label3.setText("Angle");
				panel1.add(label3, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- angle ----
				angle.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				panel1.add(angle, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label4 ----
				label4.setText("Radius");
				label4.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(label4, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- radius ----
				radius.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));
				panel1.add(radius, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel3.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//---- label1 ----
				label1.setIcon(new ImageIcon(getClass().getResource("/images/vertical.png")));
				panel2.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel3.add(panel2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel4 ========
		{
			panel4.setLayout(new GridBagLayout());
			((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 65, 60, 0};
			((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

			//---- button1 ----
			button1.setText("OK");
			panel4.add(button1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- button2 ----
			button2.setText("Cancel");
			panel4.add(button2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));
		}
		contentPane.add(panel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(bolaVert);
		buttonGroup1.add(bolaRamp);
		buttonGroup1.add(bolaHelix);
		buttonGroup1.add(bolaZigzag);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel3;
	private JPanel panel1;
	public JRadioButton bolaVert;
	public JRadioButton bolaRamp;
	public JRadioButton bolaHelix;
	public JRadioButton bolaZigzag;
	public JLabel label2;
	public JSpinner width;
	public JLabel label3;
	public JSpinner angle;
	public JLabel label4;
	public JSpinner radius;
	private JPanel panel2;
	public JLabel label1;
	private JPanel panel4;
	public JButton button1;
	public JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
