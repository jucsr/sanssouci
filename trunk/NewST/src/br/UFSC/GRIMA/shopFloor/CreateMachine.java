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
	private JanelaShopFloor janelaShoopFloor;
	private ShopFloor shopFloor;
	public CreateMachine(JanelaShopFloor janelaShopFloor, ShopFloor shopFloor) 
	{
		super(janelaShopFloor);
		this.shopFloor = shopFloor;
		this.janelaShoopFloor = janelaShopFloor;
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
			CreateDrillingMachine dm = new CreateDrillingMachine(janelaShoopFloor, shopFloor);
			dm.setVisible(true);
			dispose();
		} else if(this.radioButton2.isSelected())
		{
			CreateMillingMachine mm = new CreateMillingMachine(janelaShoopFloor, shopFloor);
			mm.setVisible(true);
			dispose();
		} else if(this.radioButton3.isSelected())
		{
			
		} else if(this.radioButton4.isSelected())
		{
			
		}
	}
}
