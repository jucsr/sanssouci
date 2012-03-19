/*
 * Created by JFormDesigner on Fri May 20 16:15:23 BRT 2011
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

/**
 * @author Victor
 */
public class AddNewTwistDrillFrame extends JDialog {
	public AddNewTwistDrillFrame(JFrame owner) {
		super(owner);
		initComponents();
	}

	public AddNewTwistDrillFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel2 = new JPanel();
		panel1 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner4 = new JSpinner();
		spinner3 = new JSpinner();
		spinner5 = new JSpinner();
		spinner2 = new JSpinner();
		spinner1 = new JSpinner();
		spinner6 = new JSpinner();
		label1 = new JLabel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		label2 = new JLabel();
		textField1 = new JTextField();
		label3 = new JLabel();
		comboBox1 = new JComboBox();
		label4 = new JLabel();
		panel6 = new JPanel();
		spinner8 = new JSpinner();
		label6 = new JLabel();
		comboBox3 = new JComboBox();
		label7 = new JLabel();
		checkBox1 = new JCheckBox();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Add new Twist Drill");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setBorder(new EtchedBorder());
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel1 ========
					{
						panel1.setBorder(new EtchedBorder());
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {340, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {340, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//======== layeredPane1 ========
						{
							layeredPane1.setBackground(Color.white);

							//---- spinner4 ----
							spinner4.setModel(new SpinnerNumberModel(20.0, 1.0, null, 1.0));
							layeredPane1.add(spinner4, JLayeredPane.DEFAULT_LAYER);
							spinner4.setBounds(0, 60, 45, spinner4.getPreferredSize().height);

							//---- spinner3 ----
							spinner3.setModel(new SpinnerNumberModel(40.0, 0.0, null, 1.0));
							layeredPane1.add(spinner3, JLayeredPane.DEFAULT_LAYER);
							spinner3.setBounds(75, 45, 40, spinner3.getPreferredSize().height);

							//---- spinner5 ----
							spinner5.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
							layeredPane1.add(spinner5, JLayeredPane.DEFAULT_LAYER);
							spinner5.setBounds(290, 55, 45, spinner5.getPreferredSize().height);

							//---- spinner2 ----
							spinner2.setModel(new SpinnerNumberModel(60.0, 0.0, null, 1.0));
							layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
							spinner2.setBounds(90, 130, 40, spinner2.getPreferredSize().height);

							//---- spinner1 ----
							spinner1.setModel(new SpinnerNumberModel(70.0, 0.0, null, 1.0));
							layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
							spinner1.setBounds(150, 160, 45, spinner1.getPreferredSize().height);

							//---- spinner6 ----
							spinner6.setModel(new SpinnerNumberModel(70.0, 0.0, 90.0, 1.0));
							layeredPane1.add(spinner6, JLayeredPane.DEFAULT_LAYER);
							spinner6.setBounds(new Rectangle(new Point(155, 230), spinner6.getPreferredSize()));

							//---- label1 ----
							label1.setIcon(new ImageIcon(getClass().getResource("/images/twistDrill.png")));
							layeredPane1.add(label1, JLayeredPane.DEFAULT_LAYER);
							label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));
						}
						panel1.add(layeredPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel2.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== panel3 ========
					{
						panel3.setLayout(new GridBagLayout());
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//======== panel4 ========
						{
							panel4.setBorder(new CompoundBorder(
								new TitledBorder("tool parameters"),
								Borders.DLU2_BORDER));
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
							((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//---- label2 ----
							label2.setText("Name");
							panel4.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField1 ----
							textField1.setText("twist drill");
							textField1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
							panel4.add(textField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label3 ----
							label3.setText("Material");
							panel4.add(label3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- comboBox1 ----
							comboBox1.setModel(new DefaultComboBoxModel(new String[] {
								"Carbide P-Class",
								"Carbide M-Class",
								"Carbide S-Class",
								"Carbide K-Class",
								"Carbide H-Class",
								"Carbide N-Class"
							}));
							panel4.add(comboBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label4 ----
							label4.setText("N. Teeth");
							panel4.add(label4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//======== panel6 ========
							{
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- spinner8 ----
								spinner8.setModel(new SpinnerNumberModel(2, 1, 20, 1));
								panel6.add(spinner8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));
							}
							panel4.add(panel6, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label6 ----
							label6.setText("Sense");
							panel4.add(label6, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- comboBox3 ----
							comboBox3.setModel(new DefaultComboBoxModel(new String[] {
								"RIGHT_TOOL",
								"LEFT_TOOL",
								"NEUTRUM"
							}));
							panel4.add(comboBox3, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label7 ----
							label7.setText("Internal Cooling");
							panel4.add(label7, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
							panel4.add(checkBox1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel3.add(panel4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					panel2.add(panel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("Add");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel2;
	private JPanel panel1;
	private JLayeredPane layeredPane1;
	protected JSpinner spinner4;
	protected JSpinner spinner3;
	protected JSpinner spinner5;
	protected JSpinner spinner2;
	protected JSpinner spinner1;
	protected JSpinner spinner6;
	protected JLabel label1;
	private JPanel panel3;
	private JPanel panel4;
	private JLabel label2;
	protected JTextField textField1;
	private JLabel label3;
	protected JComboBox comboBox1;
	private JLabel label4;
	private JPanel panel6;
	protected JSpinner spinner8;
	private JLabel label6;
	protected JComboBox comboBox3;
	private JLabel label7;
	protected JCheckBox checkBox1;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
