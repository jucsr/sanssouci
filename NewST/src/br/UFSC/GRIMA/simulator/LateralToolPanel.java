package br.UFSC.GRIMA.simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;

public class LateralToolPanel extends JPanel {

	private static final long serialVersionUID = -1759264028187348692L;
	private Graphics2D g2d;
	private Rectangle3D rectangle;
	private ToolPanel toolPanel;
	private int i = 1;
	private ArrayList<Point> trajectoryPoints;
	private double movementPlane;
	private double changeToolPlane;
	private final int zeroAxisPlane = 0;
	private int velocity;
	private int panelVelocity;
	private VisualTool visualTool;

	public void setPanelVelocity(int panelVelocity) {
		this.panelVelocity = panelVelocity;
	}
	
	public ToolPanel getToolPanel() {
		return toolPanel;
	}

	public LateralToolPanel(ProjetoDeSimulacao projeto, VisualTool visualTool) {
		
		repaint();
		rectangle = projeto.getBlock();

		this.visualTool = visualTool;

		movementPlane = rectangle.getZ() + 25 + 8;
		changeToolPlane = 200 + 8;
		trajectoryPoints = new ArrayList<Point>();
		trajectoryPoints.add(new Point((int) changeToolPlane, 0));
		velocity = 3;
		panelVelocity = 3;
		setPreferredSize(new Dimension(500, 250));
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;

		setGraphicsOptions();
		setAxis();

		drawBlock();
		drawFixers();
		drawBlockBorder();

		visualTool.paintLateralFurosTrail(g);
		visualTool.paintLateralRanhurasTrail(g);
		visualTool.paintLateralCavidadeTrail(g);
		drawGrade();
		drawWrintings();
		visualTool.paintLateralTrail(g);
		visualTool.lateralPaint(g);

	}

	private void drawFixers() {
		g2d.setColor(Color.GREEN);

		g2d.fillRect(0, 0, 8, 5);

		g2d.fillRect(0, (int) rectangle.getX() / 2 - 2, 8, 5);

		g2d.fillRect(0, (int) rectangle.getX() - 5, 8, 5);

		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, 8, 5);

		g2d.drawRect(0, (int) rectangle.getX() / 2 - 2, 8, 5);

		g2d.drawRect(0, (int) rectangle.getX() - 5, 8, 5);
	}

	private void drawBlockBorder() {
		g2d.setColor(Color.BLACK);
		g2d.drawRect(8, 0, (int) rectangle.getZ(), (int) rectangle.getX());

	}

	private void drawWrintings() {
		g2d.rotate(Math.PI / 2);
		// Eixo Y//
//		int a1 = 0 - (int)rectangle.getZ() - 8;
//		int a2 = 50 - (int)rectangle.getZ() - 8;
//		int a3 = 100 - (int)rectangle.getZ() - 8;
//		int a4 = 150 - (int)rectangle.getZ() - 8;
//		int a5 = 200 - (int)rectangle.getZ() - 8;
//		int a6 = 250 - (int)rectangle.getZ() - 8;
//		int a7 = 300 - (int)rectangle.getZ() - 8;
//		int a8 = 350 - (int)rectangle.getZ() - 8;
//		int a9 = 400 - (int)rectangle.getZ() - 8;
//		int a10 = 450 - (int)rectangle.getZ() - 8;
//		int a11 = 500 - (int)rectangle.getZ() - 8;
//		
//		
//		g2d.drawString("_"+a1, 4, -1);
//		g2d.drawString("_"+a2, 4, -50);
//		g2d.drawString("_"+a3, 4, -100);
//		g2d.drawString("_"+a4, 4, -150);
//		g2d.drawString("_"+a5, 4, -200);
//		g2d.drawString("_"+a6, 4, -250);
//		g2d.drawString("_"+a7, 4, -300);
//		g2d.drawString("_"+a8, 4, -350);
//		g2d.drawString("_"+a9, 4, -400);
//		g2d.drawString("_"+a10, 4, -450);
//		g2d.drawString("_"+a11, 4, -500);

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

	private void drawBlock() {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(8, 0, (int) rectangle.getZ(), (int) rectangle.getX());
	}

	public void setGraphicsOptions() {
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private void drawGrade() {
		
		this.g2d.translate(rectangle.getZ() + 8, 0);

		float dash1[] = { 5.0f, 2.5f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 	BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));

		g2d.setColor(Color.GRAY);
		for (int i = 0; i < 10; i++) { //grades horizontais
			g2d.drawLine(i * 50, 0, i * 50, 500);
			
			g2d.rotate(Math.PI / 2); // se liga no movimento!
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke());
			this.g2d.drawString("" + i * 50, 0, -i * 50); // numerinhos 
			g2d.rotate(-Math.PI / 2);
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 	BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			g2d.setColor(Color.GRAY);
			//-------------

			this.g2d.drawLine(-i * 50, 0, -i * 50, 500);
			
			g2d.rotate(Math.PI / 2); // se liga no movimento!
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke());
			this.g2d.drawString(" -" + (i * 50 + 50), 0, i * 50 + 50); // numerinhos 
			g2d.rotate(-Math.PI / 2);
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 	BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			g2d.setColor(Color.GRAY);
		}
		for (int i = 0; i < 10; i++) { // grades verticais
			g2d.drawLine(0, i * 50, 500, i * 50);
			this.g2d.drawLine(0, i*50, -500, i*50);
		}
		g2d.setColor(Color.BLACK);

		g2d.setStroke(new BasicStroke());
		this.g2d.translate(-rectangle.getZ() - 8, 0);
	}

	private void setAxis() {
		g2d.translate(0, 250);
		g2d.rotate(-Math.PI / 2);
	}

	public void actionPerformed() {
		repaint();
	}

	public void setToolPanel(ToolPanel toolPanel) {
		this.toolPanel = toolPanel;

	}

}
