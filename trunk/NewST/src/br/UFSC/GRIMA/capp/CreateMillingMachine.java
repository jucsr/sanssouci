package br.UFSC.GRIMA.capp;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.shopFloor.JanelaShopFloor;
import br.UFSC.GRIMA.shopFloor.visual.CreateMillingMachineFrame;

public class CreateMillingMachine extends CreateMillingMachineFrame implements ActionListener
{
	public JanelaShopFloor shopFloor;

	public CreateMillingMachine(JanelaShopFloor shopFloor) 
	{
		super(shopFloor);
		this.shopFloor = shopFloor;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.button3.addActionListener(this);
		this.button4.addActionListener(this);
		this.button5.addActionListener(this);
		this.button6.addActionListener(this);
		this.button7.addActionListener(this);
		this.button8.addActionListener(this);
		this.button9.addActionListener(this);
		this.button10.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			
		} else if(o.equals(cancelButton))
		{
			
		} else if(o.equals(button1))
		{
			
		} else if(o.equals(button2))
		{
			
		} else if(o.equals(button3))
		{
			
		}  else if(o.equals(button4))
		{
			
		} else if(o.equals(button5))
		{
			
		} else if(o.equals(button6))
		{
			
		} else if(o.equals(button7))
		{
			
		} else if(o.equals(button8))
		{
			
		} else if(o.equals(button9))
		{
			
		} else if(o.equals(button10))
		{
			
		}
	}
}
