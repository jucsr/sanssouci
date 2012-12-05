package br.UFSC.GRIMA.shopFloor;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.shopFloor.visual.RotaryAxisFrame;

public class RotaryAxisShopFloor extends RotaryAxisFrame implements ActionListener{

	private CreateMillingMachine millingMachine;
	public RotaryAxisShopFloor(CreateMillingMachine millingMachine) 
	{
		super(millingMachine);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o.equals(okButton))
		{
			ok();
			this.dispose();
		}
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
	}
	
	private void ok()
	{
		if(radioButton1.isSelected())
		{
			XAxisRotation x = new XAxisRotation(millingMachine);
			setVisible(x);
		}
		else if(radioButton2.isSelected())
		{
			YAxisRotation y = new YAxisRotation(millingMachine);
			setVisible(y);
		}
	}

}
