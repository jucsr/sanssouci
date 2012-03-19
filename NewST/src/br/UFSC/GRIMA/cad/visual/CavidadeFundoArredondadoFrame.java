/*
 * Created by JFormDesigner on Fri Nov 05 13:55:12 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

/**
 * @author Victor
 */
public class CavidadeFundoArredondadoFrame extends JDialog {
	public CavidadeFundoArredondadoFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public CavidadeFundoArredondadoFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane2 = new JScrollPane();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel9 = new JPanel();
		layeredPane1 = new JLayeredPane();
		spinner1 = new JSpinner();
		spinner4 = new JSpinner();
		spinner7 = new JSpinner();
		spinner8 = new JSpinner();
		spinner2 = new JSpinner();
		spinner9 = new JSpinner();
		spinner6 = new JSpinner();
		spinner3 = new JSpinner();
		label1 = new JLabel();
		panel10 = new JPanel();
		panel11 = new JPanel();
		label17 = new JLabel();
		label3 = new JLabel();
		panel12 = new JPanel();
		label19 = new JLabel();
		panel8 = new JPanel();
		label16 = new JLabel();
		textField9 = new JTextField();
		panel5 = new JPanel();
		label5 = new JLabel();
		spinner5 = new JSpinner();
		label8 = new JLabel();
		label4 = new JLabel();
		banheiraRug = new JSpinner();
		label11 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Cavidade com fundo arredondado (Banheira)");
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

					//======== panel9 ========
					{
						panel9.setBorder(new TitledBorder("Dimens\u00f5es em mil\u00edmetros"));
						panel9.setLayout(new GridBagLayout());
						((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 373, 0, 0};
						((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 398, 0, 0};
						((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

						//======== layeredPane1 ========
						{

							//---- spinner1 ----
							spinner1.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
							layeredPane1.add(spinner1, JLayeredPane.DEFAULT_LAYER);
							spinner1.setBounds(85, 165, 40, spinner1.getPreferredSize().height);

							//---- spinner4 ----
							spinner4.setModel(new SpinnerNumberModel(7.5, 0.0, null, 1.0));
							layeredPane1.add(spinner4, JLayeredPane.DEFAULT_LAYER);
							spinner4.setBounds(80, 25, 45, spinner4.getPreferredSize().height);

							//---- spinner7 ----
							spinner7.setModel(new SpinnerNumberModel(20.0, 0.0, null, 1.0));
							layeredPane1.add(spinner7, JLayeredPane.DEFAULT_LAYER);
							spinner7.setBounds(105, 300, 40, spinner7.getPreferredSize().height);

							//---- spinner8 ----
							spinner8.setModel(new SpinnerNumberModel(50.0, 0.0, null, 1.0));
							layeredPane1.add(spinner8, JLayeredPane.DEFAULT_LAYER);
							spinner8.setBounds(190, 20, 50, spinner8.getPreferredSize().height);

							//---- spinner2 ----
							spinner2.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
							layeredPane1.add(spinner2, JLayeredPane.DEFAULT_LAYER);
							spinner2.setBounds(210, 160, 40, spinner2.getPreferredSize().height);

							//---- spinner9 ----
							spinner9.setModel(new SpinnerNumberModel(40.0, 0.0, null, 1.0));
							layeredPane1.add(spinner9, JLayeredPane.DEFAULT_LAYER);
							spinner9.setBounds(100, 75, 40, spinner9.getPreferredSize().height);

							//---- spinner6 ----
							spinner6.setModel(new SpinnerNumberModel(2.0, 0.0, null, 1.0));
							layeredPane1.add(spinner6, JLayeredPane.DEFAULT_LAYER);
							spinner6.setBounds(85, 340, 40, spinner6.getPreferredSize().height);

							//---- spinner3 ----
							spinner3.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							layeredPane1.add(spinner3, JLayeredPane.DEFAULT_LAYER);
							spinner3.setBounds(5, 275, 40, spinner3.getPreferredSize().height);

							//---- label1 ----
							label1.setIcon(new ImageIcon(getClass().getResource("/images/CavidadeBaseArredondada.png")));
							layeredPane1.add(label1, JLayeredPane.DEFAULT_LAYER);
							label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));
						}
						panel9.add(layeredPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					contentPanel.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel10 ========
					{
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel11 ========
						{
							panel11.setLayout(new GridBagLayout());
							((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0};
							((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

							//---- label17 ----
							label17.setText("Banheira em");
							panel11.add(label17, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label3 ----
							label3.setText("XY");
							label3.setForeground(Color.red);
							label3.setFont(new Font("Tahoma", Font.BOLD, 16));
							panel11.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel12 ========
						{
							panel12.setBorder(new EtchedBorder());
							panel12.setLayout(new GridBagLayout());
							((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label19 ----
							label19.setIcon(new ImageIcon(getClass().getResource("/images/Faces de referencia.png")));
							panel12.add(label19, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel10.add(panel12, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel8 ========
						{
							panel8.setLayout(new GridBagLayout());
							((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//---- label16 ----
							label16.setText("nome:");
							panel8.add(label16, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- textField9 ----
							textField9.setText("Cavidade com fundo arredondado");
							textField9.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
							panel8.add(textField9, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel8, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setBorder(new CompoundBorder(
								new TitledBorder("Par\u00e2metros adicionais"),
								Borders.DLU2_BORDER));
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 55, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//---- label5 ----
							label5.setText("toler\u00e2ncia dos raios");
							label5.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- spinner5 ----
							spinner5.setModel(new SpinnerNumberModel(50.0, 0.0, null, 1.0));
							panel5.add(spinner5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label8 ----
							label8.setText("+/- (um)");
							label8.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label8, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- label4 ----
							label4.setText("textura das superf\u00edcies");
							label4.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- banheiraRug ----
							banheiraRug.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
							panel5.add(banheiraRug, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));

							//---- label11 ----
							label11.setText("(um)");
							label11.setHorizontalAlignment(SwingConstants.RIGHT);
							panel5.add(label11, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						panel10.add(panel5, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					contentPanel.add(panel10, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
		contentPane.add(scrollPane2, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane2;
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel9;
	private JLayeredPane layeredPane1;
	protected JSpinner spinner1;
	protected JSpinner spinner4;
	protected JSpinner spinner7;
	protected JSpinner spinner8;
	protected JSpinner spinner2;
	protected JSpinner spinner9;
	protected JSpinner spinner6;
	protected JSpinner spinner3;
	private JLabel label1;
	private JPanel panel10;
	private JPanel panel11;
	private JLabel label17;
	public JLabel label3;
	private JPanel panel12;
	private JLabel label19;
	private JPanel panel8;
	private JLabel label16;
	protected JTextField textField9;
	private JPanel panel5;
	private JLabel label5;
	protected JSpinner spinner5;
	private JLabel label8;
	private JLabel label4;
	protected JSpinner banheiraRug;
	private JLabel label11;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
