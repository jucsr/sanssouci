package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
	private ProjetoSF projetoSF;
	
	public JanelaShopFloor(ShopFloor shopFloorNew, ProjetoSF projetoSFNew)
	{
		this.shopFloor = shopFloorNew;
		this.projetoSF = projetoSFNew;
		this.addicionarOuvidores();
		this.atualizarArvorePrecendences(); //new
	}

	private void addicionarOuvidores() 
	{
		this.menuItemNovoProjeto.addActionListener(this);
		this.menuItemAbout.addActionListener(this);
		this.menuItemAbrir.addActionListener(this);
		this.menuItemAddNewMachine.addActionListener(this);
		this.menuItemAddNewWS.addActionListener(this);
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
			this.addNewWS();
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
	
	public void atualizarArvorePrecendences()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		//Vector<Workingstep> wsAssociadas = new Vector<Workingstep>();
		//Vector<Workingstep> workingsteps = new Vector<Workingstep>();
		for (int i = 0; i < projetoSF.getWorkingsteps().size(); i++)
		{
			
			//projetoSF.getWorkingsteps().get(i).identificarPosCedente(workingsteps, wsAssociadas);
			
			//Workingstep wstmp = projetoSF.getWorkingsteps().get(i);
			//wstmp.identificarPosCedente(workingsteps, wsAssociadas);
			//System.out.println("wsAssociada:" + wsAssociadas.get(1).getId());
			
			//Teste
			/*
			if (wsAssociadas.size() != 0)
			{
				System.out.println("entrou no if wsAssociadas");
				for (int j = 0; j < wsAssociadas.size(); j++)
				{
					System.out.println("wsAssociada:" + wsAssociadas.get(j).getId());
				}
			}
			*/
			
			if (projetoSF.getWorkingsteps().get(i).getWorkingstepPrecedente() == null)
			{
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getId());
				root.add(newNode);
			}
			else
			{
				/*
				//Checa todos os elementos do root
				for (int k=0;k < root.getChildCount(); k++)
				{
					//Compara cada objeto do root com o precedente do workingstep a ser adicionado
					if(root.getChildAt(k).toString() == projetoSF.getWorkingsteps().get(i).getWorkingstepPrecedente().getId())
					{
						DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getWorkingstepPrecedente().getId());
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getId());
						
						//Insere o novo workingstep no seu precedente(alocando um novo precedente root no local
						// do antigo precedente node)
						newRoot.add(newNode);
						root.remove(k);
						root.insert(newRoot, k);
						k = root.getChildCount(); //sair do laço for
					}
				}
				*/			
			}
		}
		System.out.println("root child count: " + root.getChildCount());
		
		//Associação da JTree criada ao objeto visual tree3 e atualizacao da mesma
		this.tree3 = new JTree(root);
		scrollPaneTree2.setViewportView(tree3);
		scrollPaneTree2.revalidate();
	}
	/*
	public DefaultMutableTreeNode addTreeSubNode (DefaultMutableTreeNode root, Workingstep ws)
	{
		//Checa todos os elementos do root
		for (int k=0;k < root.getChildCount(); k++)
		{
			//Compara cada objeto do root com o precedente do workingstep a ser adicionado
			if(root.getChildAt(k).toString() == ws.getWorkingstepPrecedente().getId())
			{
				DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(ws.getWorkingstepPrecedente().getId());
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(ws.getId());
				
				if (ws.getWorkingstepPrecedente().getWorkingstepPrecedente() == null)
				{
					//Insere o novo workingstep no seu precedente(alocando um novo precedente root no local
					// do antigo precedente node)
					newRoot.add(newNode);
					root.remove(k);
					root.insert(newRoot, k);
				}
				else
				{
					newRoot.add(addTreeSubNode(newNode, ws.getWorkingstepPrecedente()));
					root.remove(k);
					root.insert(newRoot, k);
				}
				k = root.getChildCount(); //sair do laço for
			}
		}
		return n
	}
	*/
	
}
