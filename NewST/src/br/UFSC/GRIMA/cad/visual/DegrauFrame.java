/*
 * Created by JFormDesigner on Tue Nov 09 18:07:59 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

/**
 * @author Victor
 */
public class DegrauFrame extends JDialog {
	public DegrauFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public DegrauFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane2 = new JScrollPane();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel10 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner1 = new JSpinner();
		spinner3 = new JSpinner();
		spinner2 = new JSpinner();
		labelImage = new JLabel();
		panel11 = new JPanel();
		panel12 = new JPanel();
		label3 = new JLabel();
		label7 = new JLabel();
		panel13 = new JPanel();
		label4 = new JLabel();
		panel14 = new JPanel();
		labelNome = new JLabel();
		textField2 = new JTextField();
		panel9 = new JPanel();
		comboBox1 = new JComboBox();
		radioButton1 = new JRadioButton();
		radioButton2 = new JRadioButton();
		panel5 = new JPanel();
		label11 = new JLabel();
		degrauRug = new JSpinner();
		label12 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Criar Degrau");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane2 ========
		{

			//======== dialogPane ========
			{
				dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
				dialogPane.setLayout(new BorderLayout());

				//======== contentPanel ========
				{
					contentPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
					contentPanel.setLayout(new GridBagLayout());
					((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
					((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//======== panel10 ========
					{
						panel10.setBorder(new TitledBorder("Dimens\u00f5es em mil\u00edmetros"));
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 517, 0, 0};
						((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 397, 0, 0};
						((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//======== layeredPane1 ========
						{

							//---- spinner1 ----
							spinner1.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
							layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
							spinner1.setBounds(145, 30, 40, spinner1.getPreferredSize().height);

							//---- spinner3 ----
							spinner3.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane1.add(spinner3, JLayeredPane.DEFAULT_LAYER);
							spinner3.setBounds(10, 275, 40, spinner3.getPreferredSize().height);

							//---- spinner2 ----
							spinner2.setModel(new SpinnerNumberModel(5.0, 0.0, null, 1.0));
							layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
							spinner2.setBounds(145, 295, 40, spinner2.getPreferredSize().height);

							//---- labelImage ----
							labelImage.setIcon(new ImageIcon(getClass().getResource("/images/DegrauHorizontalTopo.png")));
							layeredPane1.add(labelImage, JLayeredPane.DEFAULT_LAYER);
							labelImage.setBounds(new Rectangle(new Point(0, 0), labelImage.getPreferredSize()));
						}
						panel10.add(layeredPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					contentPanel.add(panel10, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel11 ========
					{
						panel11.setLayout(new GridBagLayout());
						((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel12 ========
						{
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- label3 ----
							label3.setText("Degrau na face");
							panel12.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- label7 ----
							label7.setText("XY");
							label7.setFont(new Font("Tahoma", Font.BOLD, 16));
							label7.setForeground(Color.red);
							panel12.add(label7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel11.add(panel12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel13 ========
						{
							panel13.setBorder(new EtchedBorder());
							panel13.setLayout(new GridBagLayout());
							((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label4 ----
							label4.setIcon(new ImageIcon(getClass().getResource("/images/Faces de referencia.png")));
							panel13.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel11.add(panel13, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel14 ========
						{
							panel14.setLayout(new GridBagLayout());
							((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- labelNome ----
							labelNome.setText("nome:");
							panel14.add(labelNome, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField2 ----
							textField2.setText("degrau");
							textField2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
							panel14.add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel11.add(panel14, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel9 ========
						{
							panel9.setBorder(new TitledBorder("Degrau em"));
							panel9.setLayout(new GridBagLayout());
							((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
							((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

							//---- comboBox1 ----
							comboBox1.setModel(new DefaultComboBoxModel(new String[] {
								"HORIZONTAL",
								"VERTICAL"
							}));
							panel9.add(comboBox1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- radioButton1 ----
							radioButton1.setText("No topo");
							radioButton1.setSelected(true);
							panel9.add(radioButton1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- radioButton2 ----
							radioButton2.setText("Na Base");
							panel9.add(radioButton2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));
						}
						panel11.add(panel9, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setBorder(new CompoundBorder(
								new TitledBorder("Par\u00e2metros adicionais"),
								Borders.DLU2_BORDER));
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 65, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label11 ----
							label11.setText("textura das superf\u00edcies");
							label11.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label11, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- degrauRug ----
							degrauRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel5.add(degrauRug, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label12 ----
							label12.setText("um");
							panel5.add(label12, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel11.add(panel5, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					contentPanel.add(panel11, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));
				}
				dialogPane.add(contentPanel, BorderLayout.CENTER);

				//======== buttonBar ========
				{
					buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
					buttonBar.setLayout(new GridBagLayout());
					((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
					((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

					//---- okButton ----
					okButton.setText("OK");
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
			scrollPane2.setViewportView(dialogPane);
		}
		contentPane.add(scrollPane2, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioButton1);
		buttonGroup1.add(radioButton2);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane2;
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel10;
	private JLayeredPane layeredPane1;
	protected JSpinner spinner1;
	protected JSpinner spinner3;
	protected JSpinner spinner2;
	protected JLabel labelImage;
	private JPanel panel11;
	private JPanel panel12;
	private JLabel label3;
	protected JLabel label7;
	private JPanel panel13;
	private JLabel label4;
	private JPanel panel14;
	private JLabel labelNome;
	protected JTextField textField2;
	private JPanel panel9;
	protected JComboBox comboBox1;
	protected JRadioButton radioButton1;
	protected JRadioButton radioButton2;
	private JPanel panel5;
	private JLabel label11;
	protected JSpinner degrauRug;
	private JLabel label12;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
