package br.UFSC.GRIMA.shopFloor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList; //New
import java.util.Vector; //New

import javax.swing.JTree; //New
import javax.swing.tree.DefaultMutableTreeNode; //New

import br.UFSC.GRIMA.capp.Workingstep; //New

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
		this.panel1.setLayout(new BorderLayout());
		this.panel1.add(shopPanel);
		this.zooming = ((Double)spinnerZoom.getValue()).doubleValue();
		this.shopPanel.repaint();
		this.atualizarArvorePrecendences(); //New
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
			this.zooming = zooming + 5; // add 5 to value of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		}else if (o.equals(zoomMenos)){
			
			this.zooming = zooming - 5; // decrease 5 of zooming
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
	
	
	/*public void atualizarArvorePrecendences()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		
		for (int i = 0; i < projetoSF.getWorkingsteps().size(); i++)
		{			
			if (projetoSF.getWorkingsteps().get(i).getWorkingstepPrecedente() == null)
			{
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getId());
				root.add(newNode);
			}
			else
			{
						
			}
		}		
		
		if (projetoSF.getWorkingsteps().size() > 0)
		{			
			for (int i=0;i<projetoSF.getWorkingsteps().get(0).getWorkingstepsPoscedentesDiretos
					(projetoSF.getWorkingsteps()).size();i++)
			{
				System.out.println("Poscedente "+i+": "+
				    projetoSF.getWorkingsteps().get(0).getWorkingstepsPoscedentesDiretos(projetoSF.getWorkingsteps()).get(i).getId());
			}
		}
		
		//Associa��o da JTree criada ao objeto visual tree3 e atualizacao da mesma
		this.tree3 = new JTree(root);
		scrollPaneTree2.setViewportView(tree3);
		scrollPaneTree2.revalidate();
	}
	*/
	
	public void atualizarArvorePrecendences()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		ArrayList<Workingstep> workingstepsIniciais = new ArrayList<Workingstep>();
		
		for (int i = 0; i < projetoSF.getWorkingsteps().size(); i++)
		{
			//Salva em array os workingsteps de primeiro n�vel
			if (projetoSF.getWorkingsteps().get(i).getWorkingstepPrecedente() == null)
			{
				workingstepsIniciais.add(projetoSF.getWorkingsteps().get(i));
			}
		
		}
		
		for (int i = 0; i < workingstepsIniciais.size(); i++)
		{
			
			root.add(addTreeSubNode(root, workingstepsIniciais.get(i), projetoSF.getWorkingsteps()));
		
		}
		
		//Associa��o da JTree criada ao objeto visual tree3 e atualizacao da mesma
		this.tree3 = new JTree(root);
		scrollPaneTree2.setViewportView(tree3);
		scrollPaneTree2.revalidate();
	}
	
	public DefaultMutableTreeNode addTreeSubNode (DefaultMutableTreeNode root, Workingstep ws, ArrayList<Workingstep> wsArray)
	{
		int i;
		
		//Adiciona ao root somente workingsteps sem precedentes e sem poscedentes
		if (ws.getWorkingstepPrecedente() == null && ws.getWorkingstepsPoscedentesDiretos(wsArray).size() == 0)
		{
			System.out.println("entrou no if, n�o tem precedente nem poscedente, adicionado ao root");
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(ws.getId());
			return newNode;
		}
		
		//Adiciona ao root somente workingsteps sem precedentes, tratando casos com poscedentes com recursividade
		if (ws.getWorkingstepPrecedente() == null)
		{
			//System.out.println("entrou no else");
			DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(ws.getId());
			//if (get(i).getWorkingstepsPoscedentes)
			//newRoot.add(addTreeSubNode(newRoot, ws, wsArray));
			
			for (i=0;i < ws.getWorkingstepsPoscedentesDiretos(wsArray).size();i++)
			{
				newRoot.add(addTreeSubNode(newRoot, ws.getWorkingstepsPoscedentesDiretos(wsArray).get(i), wsArray));
			}
			//root.add(newRoot);
			
			//DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getId());
			//newRoot.add(newNode);
			return newRoot;
		}
		
		//Adiciona ao root recursivo workingsteps com precedentes e poscedentes
		if (ws.getWorkingstepsPoscedentesDiretos(wsArray) != null)
		{
			DefaultMutableTreeNode newRoot2 = new DefaultMutableTreeNode(ws.getId());
			for (i=0;i < ws.getWorkingstepsPoscedentesDiretos(wsArray).size();i++)
			{
				newRoot2.add(addTreeSubNode(newRoot2, ws.getWorkingstepsPoscedentesDiretos(wsArray).get(i), wsArray));
			}
			return newRoot2;
		}
		
		//Adiciona ao root recursivo n�s sem poscedentes
		if (ws.getWorkingstepsPoscedentesDiretos(wsArray) == null)
		{
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(ws.getId());
			return newNode;
		}
		//projetoSF.getWorkingsteps().get(i).i
		return null;
	}
}
