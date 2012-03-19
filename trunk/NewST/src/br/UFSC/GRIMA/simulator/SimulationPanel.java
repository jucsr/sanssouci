package br.UFSC.GRIMA.simulator;

//File:  animation/bb/SimulationPanel.java
//Description: Panel to layout buttons and graphics area.
//Author: Fred Swartz
//Date:   February 2005

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.cad.visual.SimulationFrame;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Furo;

/////////////////////////////////////////////////////////////////// SimulationPanel
public class SimulationPanel extends SimulationFrame implements ActionListener {
	
	ToolPanel tPanel;
	private LateralToolPanel lateralTPanel;
	private VisualTool visualTPanel;
	private ProjetoDeSimulacao projetoDeSimulacao;
	Rectangle3D r;
	ArrayList<Point> arrayListPoints;
	JCheckBox needPaintBox;

	public static boolean NEEDPAINT;
	
	// ========================================================== constructor
	/** Creates a panel with the controls and bouncing ball display. */

	public SimulationPanel(ProjetoDeSimulacao projetoDeSimulacao) {
		
		
		JSlider slider = this.slider;
		JButton startButton = this.iniciar;
		JButton stopButton = this.pausar;
		JButton reiniciateButton = this.reiniciar;

		this.setResizable(false);
		slider.setValue(10);


		// ... Add Listeners
		startButton.addActionListener(new StartAction());
		stopButton.addActionListener(new StopAction());
		slider.addChangeListener(new SlideAction());
		reiniciateButton.addActionListener(this);

//		for(int i = 0; i<6; i++){
			
			needPaintBox = this.paintMillingCheckBox;
			
			this.projetoDeSimulacao = projetoDeSimulacao;
//			this.projetoDeSimulacao.setWorkingsteps(this.projetoDeSimulacao.getWorkingstepsAllFaces().get(1));
			
			tPanel = new ToolPanel(projetoDeSimulacao);
			
			lateralTPanel = tPanel.getLateralToolPanel();
			lateralTPanel.setBounds(2, 5, 500, 249);
			
			
			this.toolPanel.add(tPanel);
			tPanel.setCoordinatesPanel(this.coordinatesPanel);
			this.lateralToolPanel.add(lateralTPanel);

			tPanel.setVelocity(slider.getValue()/2 + 0.1);
			label1.setText("Velocidade de Simula\u00e7\u00e3o (%): "
					+ (slider.getValue()/2 + 0.1));

			System.out.println("A");
//		}
		

	}

	public void actionPerformed(ActionEvent e) {
		SimulationPanel panel  = new SimulationPanel(projetoDeSimulacao);
		panel.setLocation(this.getX(), this.getY());
		panel.setVisible(true);
		this.dispose();
		//thread = new RotateThread(visualTPanel, tPanel, lateralTPanel);
		//thread.start();
		
	}

	// //////////////////////////////////// inner listener class StartAction
	class SlideAction implements ChangeListener {
		public void actionPerformed(ActionEvent e) {

		}

		public void stateChanged(ChangeEvent arg0) {
			tPanel.setVelocity(slider.getValue()/2 + 0.1);
			label1.setText("Velocidade de Simula\u00e7\u00e3o (%): "
					+ (slider.getValue()/2 + 0.1));

		}
	}

	class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tPanel.setAnimation(true);
			if(needPaintBox.isSelected())
				NEEDPAINT = true;
			else
				NEEDPAINT = false;
			needPaintBox.setEnabled(false);
			
		}
	}

	class StopAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tPanel.setAnimation(false);
			needPaintBox.setEnabled(true);
		}
	}
}// endclass SimulationPanel
