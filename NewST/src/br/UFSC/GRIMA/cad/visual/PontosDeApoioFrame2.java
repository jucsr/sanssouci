/*
 * Created by JFormDesigner on Thu Apr 05 16:31:00 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import br.UFSC.GRIMA.cad.PointsGenerator;
import br.UFSC.GRIMA.util.projeto.Projeto;

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

	private void pinRadioButtonActionPerformed(ActionEvent e) {
		this.diameterLabel.setVisible(true);
		this.diameterMMLabel.setVisible(true);
		this.diameterSpinner.setVisible(true);
	}

	private void viseRadioButtonActionPerformed(ActionEvent e) {
		this.diameterLabel.setVisible(false);
		this.diameterMMLabel.setVisible(false);
		this.diameterSpinner.setVisible(false);
	}

	private void autoGenButtonActionPerformed(ActionEvent e) {
		this.setupComboBox.setEnabled(true);
		this.pointsTable.setVisible(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		supportTypeLabel = new JLabel();
		panel2 = new JPanel();
		pinRadioButton = new JRadioButton();
		viseRadioButton = new JRadioButton();
		autoGenButton = new JButton();
		panel3 = new JPanel();
		diameterLabel = new JLabel();
		diameterSpinner = new JSpinner();
		diameterMMLabel = new JLabel();
		setupComboBox = new JComboBox();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		pointsTable = new JTable();
		drawingScrollPane = new JScrollPane();

		//======== this ========
		setTitle("Define Climp Points");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 5, 159, 5, 173, 5, 263, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 5, 30, 5, 227, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- supportTypeLabel ----
		supportTypeLabel.setText("Support Type:");
		supportTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(supportTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());

			//---- pinRadioButton ----
			pinRadioButton.setText("Pin");
			pinRadioButton.setSelected(true);
			pinRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pinRadioButtonActionPerformed(e);
				}
			});
			panel2.add(pinRadioButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- viseRadioButton ----
			viseRadioButton.setText("Vise");
			viseRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					viseRadioButtonActionPerformed(e);
				}
			});
			panel2.add(viseRadioButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- autoGenButton ----
		autoGenButton.setText("Generate Points");
		autoGenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoGenButtonActionPerformed(e);
			}
		});
		contentPane.add(autoGenButton, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel3 ========
		{
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {95, 31, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {32, 32, 0};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//---- diameterLabel ----
			diameterLabel.setText("Support Diameter:");
			panel3.add(diameterLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- diameterSpinner ----
			diameterSpinner.setModel(new SpinnerNumberModel(3.0, 3.0, 50.0, 1.0));
			panel3.add(diameterSpinner, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- diameterMMLabel ----
			diameterMMLabel.setText("mm");
			panel3.add(diameterMMLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- setupComboBox ----
			setupComboBox.setEnabled(false);
			panel3.add(setupComboBox, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel3, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
		}
		contentPane.add(panel1, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== scrollPane1 ========
		{

			//---- pointsTable ----
			pointsTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"", null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
				},
				new String[] {
					"X", "Y", "Z"
				}
			));
			pointsTable.setVisible(false);
			scrollPane1.setViewportView(pointsTable);
		}
		contentPane.add(scrollPane1, new GridBagConstraints(2, 5, 3, 1, 0.0, 0.0,
			GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));
		contentPane.add(drawingScrollPane, new GridBagConstraints(6, 3, 1, 3, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());

		//---- supportButtonGroup ----
		ButtonGroup supportButtonGroup = new ButtonGroup();
		supportButtonGroup.add(pinRadioButton);
		supportButtonGroup.add(viseRadioButton);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel supportTypeLabel;
	private JPanel panel2;
	private JRadioButton pinRadioButton;
	private JRadioButton viseRadioButton;
	protected JButton autoGenButton;
	private JPanel panel3;
	private JLabel diameterLabel;
	protected JSpinner diameterSpinner;
	private JLabel diameterMMLabel;
	protected JComboBox setupComboBox;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable pointsTable;
	protected JScrollPane drawingScrollPane;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}