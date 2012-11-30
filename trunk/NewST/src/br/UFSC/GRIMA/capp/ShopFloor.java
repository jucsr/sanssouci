package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.capp.visual.ShopFloorFrame;

/**
 * 
 * @author jc
 *
 */
public class ShopFloor extends ShopFloorFrame implements ActionListener
{
	private ShopFloorPanel shopPanel;
	public ShopFloor()
	{
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
		CreateMachine cm = new CreateMachine(this);
		cm.setVisible(true);
	}
}
