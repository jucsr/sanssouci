/*
 * Created by JFormDesigner on Sat May 08 17:19:32 BRT 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Victor Oliveira
 */
public class SimulationFrame extends JFrame {
	public SimulationFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		toolPanel = new JPanel();
		coordinatesPanel = new JPanel();
		label5 = new JLabel();
		labelX = new JLabel();
		label6 = new JLabel();
		labelY = new JLabel();
		label7 = new JLabel();
		labelZ = new JLabel();
		paintMillingCheckBox = new JCheckBox();
		lateralToolPanel = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		slider = new JSlider();
		buttonBar = new JPanel();
		iniciar = new JButton();
		pausar = new JButton();
		reiniciar = new JButton();

		//======== this ========
		setTitle("STEP SIMULATOR");
		setResizable(false);
		setIconImage(new ImageIcon(getClass().getResource("/images/iconeSimulator.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 510, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 355, 0, 255, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//======== toolPanel ========
				{
					toolPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
					toolPanel.setLayout(new BorderLayout());
				}
				contentPanel.add(toolPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== coordinatesPanel ========
				{
					coordinatesPanel.setLayout(new GridBagLayout());
					((GridBagLayout)coordinatesPanel.getLayout()).columnWidths = new int[] {0, 0, 55, 15, 0, 55, 15, 0, 55, 0, 0};
					((GridBagLayout)coordinatesPanel.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)coordinatesPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)coordinatesPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- label5 ----
					label5.setText("X :");
					coordinatesPanel.add(label5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- labelX ----
					labelX.setText("0");
					labelX.setFont(new Font("Consolas", Font.BOLD, 14));
					labelX.setForeground(new Color(28, 117, 17));
					coordinatesPanel.add(labelX, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label6 ----
					label6.setText("Y :");
					coordinatesPanel.add(label6, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- labelY ----
					labelY.setText("0");
					labelY.setFont(new Font("Consolas", Font.BOLD, 14));
					labelY.setForeground(new Color(28, 117, 17));
					coordinatesPanel.add(labelY, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label7 ----
					label7.setText("Z :");
					coordinatesPanel.add(label7, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- labelZ ----
					labelZ.setText("0");
					labelZ.setFont(new Font("Consolas", Font.BOLD, 14));
					labelZ.setForeground(new Color(28, 117, 17));
					coordinatesPanel.add(labelZ, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- paintMillingCheckBox ----
					paintMillingCheckBox.setText("Paint Machining");
					coordinatesPanel.add(paintMillingCheckBox, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(coordinatesPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== lateralToolPanel ========
				{
					lateralToolPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
					lateralToolPanel.setLayout(new BorderLayout());
				}
				contentPanel.add(lateralToolPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 255, 0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

					//---- label1 ----
					label1.setText("Velocidade de Simula\u00e7\u00e3o (%) :");
					label1.setHorizontalAlignment(SwingConstants.CENTER);
					label1.setForeground(Color.blue);
					panel2.add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					panel2.add(slider, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(panel2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 85, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0};
				((GridBagLayout)buttonBar.getLayout()).rowWeights = new double[] {0.0, 1.0};

				//---- iniciar ----
				iniciar.setText("start");
				buttonBar.add(iniciar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- pausar ----
				pausar.setText("pause");
				buttonBar.add(pausar, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- reiniciar ----
				reiniciar.setText("reset");
				buttonBar.add(reiniciar, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
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
	public JPanel toolPanel;
	public JPanel coordinatesPanel;
	private JLabel label5;
	public JLabel labelX;
	private JLabel label6;
	public JLabel labelY;
	private JLabel label7;
	public JLabel labelZ;
	public JCheckBox paintMillingCheckBox;
	protected JPanel lateralToolPanel;
	private JPanel panel2;
	public JLabel label1;
	protected JSlider slider;
	private JPanel buttonBar;
	public JButton iniciar;
	public JButton pausar;
	public JButton reiniciar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
