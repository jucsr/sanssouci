package br.UFSC.GRIMA.shopFloor;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.File;
import java.util.ArrayList; //New
import java.util.Vector; //New

import javax.swing.JOptionPane;
import javax.swing.JTree; //New
import javax.swing.tree.DefaultMutableTreeNode; //New
import javax.swing.tree.TreeSelectionModel;

import jsdai.lang.SdaiException;
import jsdai.lang.SdaiSession;

import br.UFSC.GRIMA.acceptance.STEP_NCReader;
import br.UFSC.GRIMA.cad.ProjectTools;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep; //New
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.integracao.ProjectReader;

import br.UFSC.GRIMA.shopFloor.visual.ShopFloorFrame;
import br.UFSC.GRIMA.util.ToolReader;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author jc
 *
 */
public class JanelaShopFloor extends ShopFloorFrame implements ActionListener
{
	public ShopFloorPanel shopPanel; // painel capable of drawing
	private ShopFloor shopFloor;
	private ProjetoSF projetoSF;
	private double zooming =0;
	
	private Projeto projeto = null; //New
	private Face faceVisualizada = null; //New
	private Face faceTrabalho = null; //New
	
	public JanelaShopFloor(ShopFloor shopFloorNew, ProjetoSF projetoSFNew)
	{
		this.shopFloor = shopFloorNew;
		this.projetoSF = projetoSFNew;
		this.addicionarOuvidores();
		this.shopPanel = new ShopFloorPanel (projetoSF,shopFloor);
		this.panel1.setLayout(new BorderLayout());
		this.panel1.add(shopPanel);
		this.zooming = ((Double)spinnerZoom.getValue()).doubleValue();
		this.shopPanel.repaint();
		this.atualizarArvorePrecendences(); //New
		this.atualizarArvoreMaquinas();
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
		this.mostrarGrade.addActionListener(this);
		
		this.importPiece.addActionListener(this); //New
		
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
			this.zooming = zooming + 10; // add 5 to value of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		}else if (o.equals(zoomMenos)){
			
			this.zooming = zooming - 10; // decrease 5 of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		}else if(o.equals(mostrarGrade))
		{
			if(mostrarGrade.isSelected())
				shopPanel.grade = true;
			else
				shopPanel.grade = false;
			shopPanel.repaint();
		}else if(o.equals(importPiece))
		{
			importarPeca();
		}
	}
	
	public void importarPeca() 
	{
		FileDialog fd = new FileDialog(this, "Abrir", FileDialog.LOAD);

		fd.setVisible(true);

		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file;

		System.out.println("PATH : " + filePath);

		STEP_NCReader stepNcReader;
		
		try {

			System.out.println("SESSION : " + SdaiSession.getSession());
			
			if(SdaiSession.getSession() != null){
				
				SdaiSession.getSession().closeSession();
				
//				if(stepNc!= null)
//				stepNc.closeAndDeleteSession();
			}
			this.textArea1.setText(this.textArea1.getText() + "\nAbrindo Arquivo Físico .p21" + filePath);

			deleteFile(new File("C:\\repositories.tmp"));
			
			stepNcReader = new STEP_NCReader(filePath, ProjectReader.FILE_21);
			stepNcReader.getAllFeatures(stepNcReader.getAllWorkingSteps());
			
			
			//int idAtual = this.projeto.getDadosDeProjeto().getUserID();
			//String userNameAtual = this.projeto.getDadosDeProjeto().getUserName();
			
			int idAtual = this.shopFloor.getUserID();
			String userNameAtual = this.shopFloor.getName();
			
			
			this.projeto = stepNcReader.getProjeto();
			
			this.projeto.getDadosDeProjeto().setUserID(idAtual);
			this.projeto.getDadosDeProjeto().setUserName(userNameAtual);
	
			//chamar o m�todo de cria��o de preced�ncias do stepNcReader
			Vector<Feature> featuresTmp = new Vector<Feature>();
			
			for(int k=0;k<this.projeto.getBloco().getFaces().size();k++)
			{
				featuresTmp = ((Face)this.projeto.getBloco().getFaces().get(k)).features;
				stepNcReader.setFeaturesPrecedences(featuresTmp);
			}
			/*
			for(int j=0; j<featuresXY.size();j++)
			{
				System.out.println("precedente da feature "+j+ ": " + featuresXY.get(j).getFeaturePrecedente());
			}*/
			//System.out.println("precedente da feature 1 : " + featuresXY.get(0).getFeaturePrecedente());
			//System.out.println("precedente da feature 2 : " + featuresXY.get(1).getFeaturePrecedente());
	
		} catch (SdaiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		this.setTitle("STEP Modeler - " + this.projeto.getDadosDeProjeto().getProjectName());
		this.faceVisualizada = (Face) this.projeto.getBloco().faces
		.elementAt(0);
		this.faceTrabalho = (Face) this.projeto.getBloco().faces
		.elementAt(0);
		//this.desenhador.alterarProjeto(this.projeto);
		
		this.projeto.setConn(new ToolReader().getConn());
		this.projeto.setStatement(new ToolReader().getStatement());

		//setDoneCAPP(false);

		ProjectTools.setProjectToolsDone(true);
		ToolManager.setCenterDrills(this.projeto.getCenterDrills());
		ToolManager.setTwistDrills(this.projeto.getTwistDrills());
		ToolManager.setFaceMills(this.projeto.getFaceMills());
		ToolManager.setEndMills(this.projeto.getEndMills());
		ToolManager.setBallEndMills(this.projeto.getBallEndMills());
		ToolManager.setBullnoseEndMills(this.projeto.getBullnoseEndMills());
		ToolManager.setReamers(this.projeto.getReamers());
		ToolManager.setBoringTools(this.projeto.getBoringTools());

		//setDoneCAPP(true);
		
		//this.atualizarArvore();
		//this.atualizarArvoreCAPP();
		
		//this.atualizarArvorePrecedencias(); //New
		
		/*MapeadoraDeWorkingsteps mapeadora = new MapeadoraDeWorkingsteps(
				this.getProjeto()); //New
		*/
		atualizarArvorePrecendences(this.projeto);

	}
	
	public boolean deleteFile(File file) {
		boolean ok = false;

		if (file.isFile()) {
			file.delete();
		} else {
			file.delete();

			if (file.listFiles() != null) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]);
				}
			}
		}

		return ok;
	}
	

	public void atualizarArvoreMaquinas()
	{
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machines");
		this.tree2 = new JTree(root);
		
		for(int i = 0; i < shopFloor.getMachines().size(); i++)
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode("Its ID : " + shopFloor.getMachines().get(i).getItsId());
			root.add(node);
		}
		
		this.tree2.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		scrollPaneTree.setViewportView(tree2);
		scrollPaneTree.revalidate();
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
	
	public  void atualizarArvorePrecendences(Projeto projeto)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		ArrayList<Workingstep> workingstepsIniciais = new ArrayList<Workingstep>();
		
		for (int i = 0; i < this.projeto.getWorkingsteps().size(); i++)
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
	/**
	 * 
	 * @param shopFloor
	 * @param newMachine --> machine to be validate
	 * @return --> is valid or not
	 */
	public boolean validateMachine(ShopFloor shopFloor, MachineTool newMachine)
	{
		boolean isValid = false;
		ArrayList<MachineTool> machines = shopFloor.getMachines();
		Rectangle2D floor = new Rectangle2D.Double(0, 0, shopFloor.getLength(), shopFloor.getWidth());
		Rectangle2D newRect = new Rectangle2D.Double(newMachine.getItsOrigin().x, newMachine.getItsOrigin().y, 3, 2);
		if(floor.contains(newRect))
		{
			if(machines.size() ==0)
			{
				if(floor.contains(newRect))
				{
					isValid = true;
				} else
				{
					isValid = false;
					JOptionPane.showMessageDialog(null, "The machine is out of the shop floor area", "Erro", JOptionPane.ERROR_MESSAGE);  
				}
			} else
			{
				for(int i = 0; i < machines.size(); i++)
				{
					MachineTool machineTmp = machines.get(i);
					Rectangle2D rectTmp = new Rectangle2D.Double(machineTmp.getItsOrigin().x, machineTmp.getItsOrigin().y, 3, 2);
					if(rectTmp.intersects(newRect) || rectTmp.contains(newRect))
					{
						isValid = false;
						JOptionPane.showMessageDialog(null, "There is a collition between machines", "Erro", JOptionPane.ERROR_MESSAGE);  

					} else
					{
						isValid = true;
					}
				}
			}
		} else
		{
			JOptionPane.showMessageDialog(null, "The machine is out of the shop floor area", "Erro", JOptionPane.ERROR_MESSAGE);  
			isValid = false;
		}
		return isValid;
	}
}
