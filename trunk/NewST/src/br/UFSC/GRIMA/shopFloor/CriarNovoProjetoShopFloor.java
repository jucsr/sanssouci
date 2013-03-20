package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.shopFloor.visual.NovoProjetoShopFloorFrame;

public class CriarNovoProjetoShopFloor extends NovoProjetoShopFloorFrame implements ActionListener, ChangeListener
{
	private JanelaShopFloor janelaShopFloor;
	private int userID;
	private String userName;
	private ShopFloor shopFloor;
	private double length;
	private double width;
	private ProjetoSF projetoSF;
	
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
		this.width = ((Double) this.spinnerX.getValue()).doubleValue();
		this.length = ((Double) this.spinnerY.getValue()).doubleValue();
		setVisible(true);
	}
	
	private void ok()
	{
		shopFloor = new ShopFloor(userName, userID, length, width);
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
			this.width = ((Double) this.spinnerX.getValue()).doubleValue();
			
		}
		else if(o.equals(spinnerY))
		{
			this.length = ((Double) this.spinnerY.getValue()).doubleValue();	
			
		}
	}
}
