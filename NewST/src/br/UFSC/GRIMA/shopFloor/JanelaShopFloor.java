package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.shopFloor.visual.ShopFloorFrame;

/**
 * 
 * @author jc
 *
 */
public class JanelaShopFloor extends ShopFloorFrame implements ActionListener
{
	private ShopFloorPanel shopPanel; // painel capable of drawing
	private ShopFloor shopFloor;
	private ProjetoSF projetoSF;
	private double zooming =0;
	
	public JanelaShopFloor(ShopFloor shopFloorNew, ProjetoSF projetoSFNew)
	{
		this.shopFloor = shopFloorNew;
		this.projetoSF = projetoSFNew;
		this.addicionarOuvidores();
		shopPanel = new ShopFloorPanel (projetoSF,shopFloor);
		this.panel1.add(shopPanel);
		this.zooming = ((Double)spinnerZoom.getValue()).doubleValue();
	}

	private void addicionarOuvidores() 
	{	//adding Listener
		this.menuItemNovoProjeto.addActionListener(this);
		this.menuItemAbout.addActionListener(this);
		this.menuItemAbrir.addActionListener(this);
		this.menuItemAddNewMachine.addActionListener(this);
		this.menuItemAddNewWS.addActionListener(this);
		this.zoomMenos.addActionListener(this);
		this.zoomMais.addActionListener(this);
		this.spinnerZoom.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				zooming = ((Double)spinnerZoom.getValue()).doubleValue();
				//add spinner value to zooming
				shopPanel.zooming(zooming);
				shopPanel.repaint();
			}
			
		});
		
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
		} else if(o.equals(menuItemAddNewWS))
		{
			this.addNewWS(); // create a new working space
			
		} else if(o.equals(zoomMais))
		{
			this.zooming++; // add 5 to value of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		}else if (o.equals(zoomMenos)){
			
			this.zooming--; // decrease 5 of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		}
	}
	
		
	

	private void addNewWS()
	{
		CreateNewWorkingStep newWS = new CreateNewWorkingStep(this, shopFloor, projetoSF);
		newWS.setVisible(true);
	}

	private void addNewMachine() 
	{
		CreateMachine cm = new CreateMachine(this, shopFloor);
		cm.setVisible(true);
	}
	
}
