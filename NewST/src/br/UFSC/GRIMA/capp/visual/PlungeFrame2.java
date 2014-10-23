/*
 * Created by JFormDesigner on Mon Oct 20 18:02:57 BRST 2014
 */

package br.UFSC.GRIMA.capp.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.layout.*;

/**
 * @author jc
 */
public class PlungeFrame2 extends JFrame {
	public PlungeFrame2() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel3 = new JPanel();
		panel1 = new JPanel();
		bolaRamp = new JRadioButton();
		bolaHelix = new JRadioButton();
		bolaZigzag = new JRadioButton();
		widthText = new JLabel();
		widthBox = new JSpinner();
		angleText = new JLabel();
		angleBox = new JSpinner();
		radiusText = new JLabel();
		radiusBox = new JSpinner();
		bolaVert = new JRadioButton();
		retractText = new JLabel();
		retractBox = new JSpinner();
		panel2 = new JPanel();
		label1 = new JLabel();
		panel4 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

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
				((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {18, 0, 75, 0, 0};
				((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {100, 0, 0, 0, 0, 50, 10, 20, 10, 10, 20, 10, 0, 20, 10, 0, 0, 0};
				((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- bolaRamp ----
				bolaRamp.setText("Ramp");
				panel1.add(bolaRamp, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaHelix ----
				bolaHelix.setText("Helix");
				panel1.add(bolaHelix, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaZigzag ----
				bolaZigzag.setText("Zig-Zag");
				panel1.add(bolaZigzag, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- widthText ----
				widthText.setText("Width[mm]");
				panel1.add(widthText, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- widthBox ----
				widthBox.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
				panel1.add(widthBox, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- angleText ----
				angleText.setText("Angle[degrees]");
				panel1.add(angleText, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- angleBox ----
				angleBox.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
				panel1.add(angleBox, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- radiusText ----
				radiusText.setText("Radius[mm]");
				radiusText.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(radiusText, new GridBagConstraints(2, 12, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- radiusBox ----
				radiusBox.setModel(new SpinnerNumberModel(10.0, null, null, 1.0));
				panel1.add(radiusBox, new GridBagConstraints(2, 13, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- bolaVert ----
				bolaVert.setText("Vertical");
				bolaVert.setSelected(true);
				panel1.add(bolaVert, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- retractText ----
				retractText.setText("Retract Plane");
				retractText.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(retractText, new GridBagConstraints(2, 15, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- retractBox ----
				retractBox.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
				panel1.add(retractBox, new GridBagConstraints(2, 16, 1, 1, 0.0, 0.0,
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

			//---- okButton ----
			okButton.setText("OK");
			panel4.add(okButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			panel4.add(cancelButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
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
		buttonGroup1.add(bolaRamp);
		buttonGroup1.add(bolaHelix);
		buttonGroup1.add(bolaZigzag);
		buttonGroup1.add(bolaVert);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel3;
	private JPanel panel1;
	public JRadioButton bolaRamp;
	public JRadioButton bolaHelix;
	public JRadioButton bolaZigzag;
	public JLabel widthText;
	public JSpinner widthBox;
	public JLabel angleText;
	public JSpinner angleBox;
	public JLabel radiusText;
	public JSpinner radiusBox;
	public JRadioButton bolaVert;
	public JLabel retractText;
	public JSpinner retractBox;
	private JPanel panel2;
	public JLabel label1;
	private JPanel panel4;
	public JButton okButton;
	public JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
