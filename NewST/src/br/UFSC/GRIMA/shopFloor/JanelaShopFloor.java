package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.shopFloor.visual.ShopFloorFrame;

/**
 * 
 * @author jc
 *
 */
public class JanelaShopFloor extends ShopFloorFrame implements ActionListener
{
	private ShopFloorPanel shopPanel;
	private ShopFloor shopFloor;
	public JanelaShopFloor(ShopFloor shopFloor)
	{
		this.shopFloor = shopFloor;
		this.addicionarOuvidores();
	}

	private void addicionarOuvidores() 
	{
		this.menuItemNovoProjeto.addActionListener(this);
		this.menuItemAbout.addActionListener(this);
		this.menuItemAbrir.addActionListener(this);
		this.menuItemAddNewMachine.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if(o.equals(menuItemAbout))
		{
			
		} else if(o.equals(menuItemAddNewMachine))
		{
			this.addNewMachine();
		}
	}

	private void addNewMachine() 
	{
		CreateMachine cm = new CreateMachine(this, shopFloor);
		cm.setVisible(true);
	}
}
