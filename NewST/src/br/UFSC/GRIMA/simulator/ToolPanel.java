package br.UFSC.GRIMA.simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class ToolPanel extends JPanel {
//	// ============================================== fields

	private static final long serialVersionUID = 1L;
	private Graphics2D g2d;
	boolean pass = true;
	protected static double velocity = 100;
	private LateralToolPanel lateralToolPanel;
	private JPanel coordinatesPanel;
	private JLabel labelX;
	private JLabel labelY;
	private JLabel labelZ;


	private int m_interval = 10; // Milliseconds between updates.
	private Timer m_timer; // Timer fires to animate one step.
	int blockX;
	int blockY;
	int blockZ;
	int lineIndex = 0;
	protected boolean changeTool = false;
	int i = 0;
	int f = 0;
	int actualDiameter;

	// /////
	private ProjetoDeSimulacao projetoDeSimulacao;
	private VisualTool visualTool;
	private int workingstepsIndex;


	public ToolPanel(ProjetoDeSimulacao projetoDeSimulacao) {
		this.projetoDeSimulacao = projetoDeSimulacao;
		blockX = (int) projetoDeSimulacao.getBlock().getX();
		blockY = (int) projetoDeSimulacao.getBlock().getY();
		blockZ = (int) projetoDeSimulacao.getBlock().getZ();

		workingstepsIndex = 0;

		visualTool = new VisualTool(projetoDeSimulacao, this);

		this.lateralToolPanel = new LateralToolPanel(projetoDeSimulacao, visualTool);

		m_timer = new Timer(m_interval, new TimerAction());
	}

	public LateralToolPanel getLateralToolPanel() {
		return lateralToolPanel;
	}

	public int getWorkingstepsIndex() {
		return workingstepsIndex;
	}
	
	public void setWorkingstepsIndex(int workingstepsIndex) {
		this.workingstepsIndex = workingstepsIndex;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setAnimation(boolean turnOnOff) {
		if (turnOnOff) {
			m_timer.start(); // start animation by starting the timer.
		} else {
			m_timer.stop(); // stop timer
		}
	}

	// ======================================================= paintComponent
	public void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;

//		g2d.scale(2, 2);
//		
//		g2d.translate(0, -200);
		
		setGraphicsOptions();

		super.paintComponent(g); // Paint background, border

		setAxis();

		drawBlock();
		
		drawClampingPoints();
		
		drawBlockBorder();

		visualTool.paintUpperFurosTrail(g);
		visualTool.paintUpperRanhurasTrail(g);
		visualTool.paintUpperCavidadeTrail(g);
		drawGrade();

		drawWrintings();

		drawAxis();
		visualTool.upperPaint(g);
		visualTool.paintUpperTrail(g);
		
		updateCoordinates(visualTool.getX(),visualTool.getY(),visualTool.getZ()-blockZ-8);
		
	}

	private void updateCoordinates(double x, double y, double z) {
		
		String xP = "", yP = "", zP = "";
		
		xP = ((""+x).concat("000000")).substring(0,7) + " mm";
		yP = ((""+y).concat("000000")).substring(0,7) + " mm";
		zP = ((""+z).concat("000000")).substring(0,7) + " mm";

		labelX.setText(xP);
		labelY.setText(yP);
		labelZ.setText(zP);
	}

	private void drawClampingPoints() {
		ArrayList<Point3d> arrayList  = this.projetoDeSimulacao.getApoiosFaceInferior();
		Point3d point3d;
		
		float dash1[] = { 5.0f, 0.5f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));

		g2d.setColor(Color.BLACK);
		
		Iterator<Point3d> iterator = arrayList.iterator();
		
		while(iterator.hasNext()){
			point3d = iterator.next();
			
			//Default: raio = 2
			g2d.drawOval((int)point3d.getY(),(int) point3d.getX(), 4, 4);
			
		}
		
		
	}

	private void drawBlockBorder() {
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, blockY, blockX);

	}

	private void drawAxis() {
		float dash1[] = { 5.0f, 0f };
		g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));

		g2d.setColor(Color.BLACK);
		g2d.drawLine((int) Math.round(visualTool.getY()), 0, (int) Math
				.round(visualTool.getY()), 500);
		g2d.drawLine(0, (int) Math.round(visualTool.getX()), 500, (int) Math
				.round(visualTool.getX()));

		g2d.setStroke(new BasicStroke());
	}

	public void setGraphicsOptions() {
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private void drawGrade() {

		float dash1[] = { 5.0f, 2.5f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));

		g2d.setColor(Color.GRAY);
		for (int i = 0; i < 10; i++) {
			g2d.drawLine(i * 50, 0, i * 50, 500);
		}
		for (int i = 0; i < 10; i++) {
			g2d.drawLine(0, i * 50, 500, i * 50);
		}
		g2d.setColor(Color.BLACK);

		g2d.setStroke(new BasicStroke());
	}

	private void setAxis() {
		g2d.translate(0, 347);
		g2d.rotate(-Math.PI / 2);
	}

	private void drawBlock() {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, blockY, blockX);
	}

	public int getActualDiameter() {
		return actualDiameter;
	}

	public void setActualDiameter(int actualDiameter) {
		this.actualDiameter = actualDiameter;
	}

	private void drawWrintings() {
		g2d.rotate(Math.PI / 2);
		// Eixo Y//
		
		double a1 = 0 - blockZ - 8;
		
		
		g2d.drawString("_0", 4, -1);
		g2d.drawString("_50", 4, -50);
		g2d.drawString("_100", 4, -100);
		g2d.drawString("_150", 4, -150);
		g2d.drawString("_200", 4, -200);
		g2d.drawString("_250", 4, -250);
		g2d.drawString("_300", 4, -300);
		g2d.drawString("_350", 4, -350);
		g2d.drawString("_400", 4, -400);
		g2d.drawString("_450", 4, -450);
		g2d.drawString("_500", 4, -500);

		// Eixo x//
		g2d.drawString("|0", 1, -4);
		g2d.drawString("|50", 50, -4);
		g2d.drawString("|100", 100, -4);
		g2d.drawString("|150", 150, -4);
		g2d.drawString("|200", 200, -4);
		g2d.drawString("|250", 250, -4);
		g2d.drawString("|300", 300, -4);
		g2d.drawString("|350", 350, -4);
		g2d.drawString("|400", 400, -4);
		g2d.drawString("|450", 450, -4);
		g2d.drawString("|500", 500, -4);

		g2d.rotate(-Math.PI / 2);

	}

//	boolean refreshTool() {
//
//		if (lineIndex != 0) {
//
//			if (furos.size() > lineIndex + i) {
//
//				Furo furo1 = furos.get(lineIndex - 1 + i);
//				Furo furo2 = furos.get(lineIndex + i);
//
//				if (furo2 != null) {
//					if (furo1.getWorkingsteps().get(0).getFerramenta().equals(furo2.getWorkingsteps().get(0).getFerramenta())) {
//					} else {
//						// toolPath.addPoint(new Point(0, 0), lineIndex + 1);
//						changeTool = true;
//						pass = false;
//
//					}
//				}
//			}
//		}
//		return true;
//	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	// ////////////////////////////////// inner listener class ActionListener
	class TimerAction implements ActionListener {
		int i = 0;

		// ================================================== actionPerformed
		public void actionPerformed(ActionEvent e) {
			
			repaint();
			
			if (visualTool.isReadyForNextWkstp()) {
				if (visualTool.haveNewTool()) {
					visualTool.changeTool();
				}
				visualTool.goToNextWorkingstepPoint();
			}
			if (visualTool.isAtWorkingstepPoint()) {
				visualTool.doThisWorkingstep();
			}

			if (visualTool.isJobDone()) {
				visualTool.goToMovementPoint();
			}
			if (visualTool.isAtMovementPoint()) {
				if (!visualTool.isMoving()) {
					if (hasToChangeTool()) {
						visualTool.goToChangePoint();
						visualTool.haveNewTool(true);
					} else {
						visualTool.goToNextWorkingstepPoint();
						visualTool.haveNewTool(false);
					}
				}
			}
			lateralToolPanel.actionPerformed();
			visualTool.move();
		}

		private boolean hasToChangeTool() {
			
			Ferramenta atual = projetoDeSimulacao.getWorkingsteps().get(workingstepsIndex).getFerramenta();
			
			if (projetoDeSimulacao.getWorkingsteps().size() == workingstepsIndex + 1) {
				return true;
			}
			
			Ferramenta proxima = projetoDeSimulacao.getWorkingsteps().get(workingstepsIndex + 1).getFerramenta();

			return !atual.equals(proxima);
//			return false;
		}

	}

	public void setVelocity(double velocity) {
		visualTool.setVelocity(velocity);

	}

	public JPanel getCoordinatesPanel() {
		return coordinatesPanel;
	}

	public void setCoordinatesPanel(JPanel panel) {
		this.coordinatesPanel = panel;
		this.labelX = (JLabel)panel.getComponent(1);
		this.labelY = (JLabel)panel.getComponent(3);
		this.labelZ = (JLabel)panel.getComponent(5);
	}
}