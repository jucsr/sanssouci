package br.UFSC.GRIMA.capp;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sun.font.CreatedFontTracker;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.capp.visual.CreateMachineFrame;
import br.UFSC.GRIMA.capp.visual.CreateMillingMachineFrame;

/**
 * 
 * @author jc
 *
 */
public class CreateMachine extends CreateMachineFrame implements ActionListener
{
	ShopFloor shoopFloor;
	public CreateMachine(ShopFloor shopFloor) 
	{
		super(shopFloor);
		this.shoopFloor = shopFloor;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if(o.equals(this.okButton))
		{
			this.ok();
		} else if(o.equals(this.cancelButton))
		{
			this.dispose();
		}
	}
	private void ok() 
	{
		if(this.radioButton1.isSelected())
		{
			CreateDrillingMachine dm = new CreateDrillingMachine(this.shoopFloor);
			dm.setVisible(true);
			dispose();
		} else if(this.radioButton2.isSelected())
		{
			CreateMillingMachine mm = new CreateMillingMachine(this.shoopFloor);
			mm.setVisible(true);
			dispose();
		} else if(this.radioButton3.isSelected())
		{
			
		} else if(this.radioButton4.isSelected())
		{
			
		}
	}
}
