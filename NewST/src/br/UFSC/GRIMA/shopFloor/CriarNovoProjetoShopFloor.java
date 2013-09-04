package br.UFSC.GRIMA.shopFloor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.shopFloor.visual.NovoProjetoShopFloorFrame;

public class CriarNovoProjetoShopFloor extends NovoProjetoShopFloorFrame implements ActionListener, ChangeListener
{
	private JanelaShopFloor janelaShopFloor;
	private int userID,x=10,y=10;
	private String userName;
	private ShopFloor shopFloor;
	private double length;
	private double width;
	private ProjetoSF projetoSF;
	private Desenho pdesenho;
	private Rectangle2D floor;
	public double escala = 10;
	public ProjetoSF getProjetoSF() {
		return projetoSF;
	}

	public void setProjetoSF(ProjetoSF projetoSF) {
		this.projetoSF = projetoSF;
	}

	public CriarNovoProjetoShopFloor(int userID, String userName){
		this.userID = userID;
		this.userName = userName;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.spinnerX.addChangeListener(this);
		this.spinnerY.addChangeListener(this);
		this.width = ((Double) this.spinnerY.getValue()).doubleValue();
		this.length = ((Double) this.spinnerX.getValue()).doubleValue();
		this.setVisible(true);
		this.floor = new Rectangle2D.Double(x, y, length * escala, width * escala);
		this.pdesenho = new Desenho();
		this.panel8.setLayout(new BorderLayout());
		this.panel8.add(pdesenho);
		this.pdesenho.repaint();
		
	}
	
	private void ok()
	{
		
		shopFloor = new ShopFloor(this.textField4.getText(), userID, length, width);
		this.projetoSF = new ProjetoSF(this.shopFloor);
		this.janelaShopFloor = new JanelaShopFloor(shopFloor, projetoSF);
		this.janelaShopFloor.setVisible(true);
		
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			ok();
		}
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object o = e.getSource();
		if(o.equals(spinnerX))
		{
			this.length = ((Double) this.spinnerX.getValue()).doubleValue();
			
			floor = new Rectangle2D.Double(x, y, length * escala, width * escala);
			pdesenho.repaint();
			
		}
		else if(o.equals(spinnerY))
		{
			this.width = ((Double) this.spinnerY.getValue()).doubleValue();	
			
			floor = new Rectangle2D.Double(x, y, length * escala, width * escala);
			pdesenho.repaint();
		}
	}
	
	class Desenho extends JPanel
	{
		public Desenho()
		{
			
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setColor(Color.BLUE);
			
			g2d.draw(floor);
			
			g2d.dispose();
			
		}
	}
}

