package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.shopFloor.visual.CreateMachineFrame;

/**
 * 
 * @author jc
 *
 */
public class CreateMachine extends CreateMachineFrame implements ActionListener
{
	JanelaShopFloor shoopFloor;
	public CreateMachine(JanelaShopFloor shopFloor) 
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
