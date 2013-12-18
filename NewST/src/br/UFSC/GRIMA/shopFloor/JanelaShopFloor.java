package br.UFSC.GRIMA.shopFloor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import jsdai.lang.SdaiException;
import jsdai.lang.SdaiSession;
import br.UFSC.GRIMA.acceptance.STEP_NCReader;
import br.UFSC.GRIMA.cad.ArvorePrecedencias;
import br.UFSC.GRIMA.cad.DesenhadorDeFaces;
import br.UFSC.GRIMA.cad.Generate3Dview;
import br.UFSC.GRIMA.cad.ProjectTools;
import br.UFSC.GRIMA.cad.visual.Progress3D;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.integracao.ProjectReader;
import br.UFSC.GRIMA.shopFloor.visual.ShopFloorFrame;
import br.UFSC.GRIMA.util.ToolReader;
import br.UFSC.GRIMA.util.projeto.Projeto;
//New
//New
//New
//New
//New

/**
 * 
 * @author jc
 *
 */
public class JanelaShopFloor extends ShopFloorFrame implements ActionListener, TreeSelectionListener
{
	public ShopFloorPanel shopPanel; // painel capable of drawing
	private ShopFloor shopFloor;
	private ProjetoSF projetoSF;
	private double zooming =0;
	
	JanelaShopFloor janelaShopFloor; /** ----> nunca ganha valor??? */

	private Projeto projeto = null; //New
	private Face faceVisualizada = null; //New
	private Face faceTrabalho = null; //New
	public DesenhadorDeFaces desenhador;
	public WSPrecedencesPanel desenhadorPrecedencias;
	
	public JanelaShopFloor(ShopFloor shopFloorNew, ProjetoSF projetoSFNew)
	{
		this.shopFloor = shopFloorNew;
		this.projetoSF = projetoSFNew;
		this.addicionarOuvidores();
		this.shopPanel = new ShopFloorPanel (this.projetoSF, this.shopFloor);
		this.panel1.setLayout(new BorderLayout());
		this.panel1.add(shopPanel);
		this.zooming = ((Double)spinnerZoom.getValue()).doubleValue();
		this.shopPanel.repaint();
		this.atualizarArvorePrecendences(); //New
//		this.atualizarArvoreMaquinas();
		this.atualizarArvoreMaquinas1();
		
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
		this.menuItemSalvar.addActionListener(this);
		this.importPiece.addActionListener(this); //New
		this.menuItem2.addActionListener(this);
		this.menuItem3.addActionListener(this);
		this.buttonAbrir.addActionListener(this);
		this.buttonSalvar.addActionListener(this);
		this.spinnerZoom.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				zooming = ((Double)spinnerZoom.getValue()).doubleValue();
				//add spinner value to zooming
				shopPanel.zooming(zooming);
				shopPanel.repaint();
			}
		});
		this.tree2.addTreeSelectionListener(this);
		this.buttonRemoverWS.addActionListener(this);
	
		
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
		} else if (o.equals(zoomMenos)){
			
			this.zooming = zooming - 10; // decrease 5 of zooming
			this.spinnerZoom.setValue(zooming);
			shopPanel.zooming(zooming);
			shopPanel.repaint();
		} else if(o.equals(mostrarGrade))
		{
			if(mostrarGrade.isSelected())
				shopPanel.grade = true;
			else
				shopPanel.grade = false;
			shopPanel.repaint();
		} else if(o.equals(importPiece))
		{
			importarPeca();
		} else if(o == this.menuItem2)
		{
			new TabelaTempos(projetoSF);
		} else if(o == this.menuItemSalvar || o == this.buttonSalvar)
		{
			this.salvar();
		} else if(o == this.menuItemAbrir || o == this.buttonAbrir)
		{
			this.abrir();
		} else if(o == this.menuItem3)
		{
			final Progress3D p3D = new Progress3D(this);
			p3D.setTitle("Calculating Times and Costs");
			p3D.setVisible(true);
			final JanelaShopFloor janelaShopFloor = this;
			SwingWorker worker = new SwingWorker() 
			{
				@Override
				protected Object doInBackground() throws Exception 
				{
					TabelaCustosETempos tabela = new TabelaCustosETempos(janelaShopFloor, projetoSF);
					return null;
				}
				@Override
				protected void done()
				{
					p3D.dispose();
					textArea1.setText(textArea1.getText() + "\n  Times and Costs matrices done!");
				}
			};
			worker.execute();
		}
		if(o.equals(buttonRemoverWS)){
			
			this.removeMachine();
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
			e1.printStackTrace();
		}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		// inicializa o desenhador
		this.desenhador = new DesenhadorDeFaces(this.projeto);
		this.scrollPaneDesenho2.setViewportView(this.desenhador);
		this.desenhador.revalidate();
		this.scrollPaneDesenho2.revalidate();
				
		// --- pegar os dados do projetista do shopfloor e nao do projeto da peca		
		this.setTitle("SHOP Floor - " + this.projeto.getDadosDeProjeto().getProjectName());
		this.faceVisualizada = (Face) this.projeto.getBloco().faces.elementAt(0);
		this.faceTrabalho = (Face) this.projeto.getBloco().faces.elementAt(0);
		this.desenhador.alterarProjeto(this.projeto);
		
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
		this.projetoSF.setProjeto(this.projeto);
		//this.atualizarArvore();
		//this.atualizarArvoreCAPP();
		
		//this.atualizarArvorePrecedencias(); //New
		
		
		
		new MapeadoraDeWorkingsteps(this.projeto); //New
		this.atualizarArvorePrecedenciasFeatures();
//		atualizarArvorePrecendences(this.projeto);
		this.atualizarArvorePrecendencesWorkingsteps();
		
		// inicializa desehador de precedencias
		
		this.desenhadorPrecedencias = new WSPrecedencesPanel(projetoSF);
		scrollPane2.setViewportView(desenhadorPrecedencias);
		desenhadorPrecedencias.revalidate();
		scrollPane2.revalidate();
		janelaShopFloor = new JanelaShopFloor(shopFloor, projetoSF);
		
		desenhadorPrecedencias.addMouseMotionListener(new MouseMotionListener() 
		 {
			
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				double x = e.getX();
				double y = e.getY();
				for (int i = 0; i < desenhadorPrecedencias.ClickWorkingsteps().size(); i++) 
				{
					if (desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x, y)) 
					{
						desenhadorPrecedencias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						break;
						
					} else if(!desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x, y))
					{
						desenhadorPrecedencias.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) 
			{
			
			}
		});
		
		desenhadorPrecedencias.addMouseListener(new MouseListener() {  
             public void mouseClicked(MouseEvent e) {  
                
            	 double x = e.getX();  
            	 double y= e.getY(); 
            	             	 
            	 
            	 for(int i = 0; i < desenhadorPrecedencias.ClickWorkingsteps().size(); i++){
            		 
			     	if(desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x,y)){
			     		
			     		System.err.println("clique");
			     	
			     	int id = (desenhadorPrecedencias.getIdClickBolinha().get(i));	
			     	if(id == 10){
			     		id = 0;
			     	}else{
			     		
			     		id = (id -10)/10;
			     	}
			     	System.err.println("--------------->> id = "+ id);
			     	
			     		if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == FaceMill.class || projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == BottomAndSideRoughMilling.class ){
			     				
			     			EditFaceMillWS editFacemillWS = new EditFaceMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editFacemillWS.setVisible(true);
			     			editFacemillWS.okButton.setVisible(false);
			     			editFacemillWS.spinner10.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getN());
			     			editFacemillWS.spinner9.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getVf());
			     			editFacemillWS.spinner11.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getAp());
			     			editFacemillWS.spinner12.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getAe());
			     			editFacemillWS.spinner7.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getVc());
			     			
			     		}else if (projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == CenterDrilling.class ){
			     			
			     			EditCenterDrillWS editCenterDrillWS = new EditCenterDrillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editCenterDrillWS.setVisible(true);
			     			editCenterDrillWS.okButton.setVisible(false);
			     		
			     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == Drilling.class){
			     			
			     			EditTwistDrillWS editTwistDrillWS = new EditTwistDrillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editTwistDrillWS.setVisible(true);
			     			editTwistDrillWS.okButton.setVisible(false);
			     			
			     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == BallEndMill.class){
			     			
			     			EditBallEndMillWS editBallEndMillWS = new EditBallEndMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editBallEndMillWS.setVisible(true);
			     			editBallEndMillWS.okButton.setVisible(false);
			     			
			     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == BoringTool.class){
			     			
			     			EditBoringToolWS editBoringToolWS = new EditBoringToolWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editBoringToolWS.setVisible(true);
			     			editBoringToolWS.okButton.setVisible(false);
			     			
			     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == EndMill.class || projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == BottomAndSideFinishMilling.class ){
			     				
			     			EditFaceMillWS editFacemillWS = new EditFaceMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editFacemillWS.setVisible(true);
			     			editFacemillWS.okButton.setVisible(false);
			     			
			     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == Reamer.class){
			     			
			     			EditReamerWS editReamerWS = new EditReamerWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
			     			editReamerWS.setVisible(true);
			     			editReamerWS.okButton.setVisible(false);
			     			
			     		}
			     	}
			      }
			  
             }  

             public void mousePressed(MouseEvent e) {  
             }  

             public void mouseReleased(MouseEvent e) {  
             }  

             public void mouseEntered(MouseEvent e) {  
             }  

             public void mouseExited(MouseEvent e) {  
             }  
         }); 
		
		
		this.gerar3D();
	}
	
	
	public void gerar3D() 
	{
		final Progress3D p3D = new Progress3D(this);

		p3D.setVisible(true);

		SwingWorker worker = new SwingWorker() 
		{
			@Override
			protected Object doInBackground() throws Exception 
			{
				Generate3Dview parent = new Generate3Dview(projeto, panel15);
				return null;
			}
			@Override
			protected void done()
			{
				p3D.dispose();
				textArea1.setText(textArea1.getText() + "\n Modelo 3D criado com sucesso!");
			}
		};
		worker.execute();
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
	
	public void atualizarArvoreMaquinas1()
	{
		ArvoreMaquinas arvore = new ArvoreMaquinas(projetoSF);
		this.tree2 = arvore.getArvoreMaquinas();
		scrollPaneTree.setViewportView(tree2);
		scrollPaneTree.revalidate();
	}
	public void atualizarArvoreMaquinas()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machines");
		this.tree2 = new JTree(root);
		
		for(int i = 0; i < shopFloor.getMachines().size(); i++)
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode("Its ID : " + shopFloor.getMachines().get(i).getItsId());
			System.out.println("******** ---- >>" + i + "\t" + shopFloor.getMachines().get(i).getItsId());
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
		CreateMachine cm = new CreateMachine(this, this.shopFloor);
		cm.setVisible(true);
	}
	/**
	 * 	Atualiza arvore de precedencias das features
	 */
	public void atualizarArvorePrecedenciasFeatures()
	{
		this.treePrecedencesFeatures = ArvorePrecedencias.getArvorePrecedencias(projeto);
		this.treePrecedencesFeatures.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		this.scrollPane1.setViewportView(treePrecedencesFeatures);
		this.scrollPane1.revalidate();
	}
	/**
	 * 	Atualiza arvore de precedencias dos machining workingsteps
	 */
	public void atualizarArvorePrecendencesWorkingsteps()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		ArrayList<Workingstep> workingstepsIniciais = new ArrayList<Workingstep>();
		Vector<Workingstep> vetorWSXY = projeto.getWorkingsteps().get(0);
		
		for (int i = 0; i < vetorWSXY.size(); i++) 
		{
			//Salva em array os workingsteps de primeiro n�vel
			/*
			 * Para a face XY
			 */
			vetorWSXY = projeto.getWorkingsteps().get(0);
			if (vetorWSXY.elementAt(i).getWorkingstepPrecedente() == null)
			{
				workingstepsIniciais.add(vetorWSXY.elementAt(i));
			}
		}
		ArrayList <Workingstep> aux = new ArrayList<Workingstep>();
		for(int i = 0; i < vetorWSXY.size(); i++)
		{
			aux.add(vetorWSXY.elementAt(i));
		}
		
		for (int i = 0; i < workingstepsIniciais.size(); i++)
		{
			root.add(addTreeSubNodeWorkingstep(root, workingstepsIniciais.get(i), aux));
		}
		
		//Associacao da JTree criada ao objeto visual tree3 e atualizacao da mesma
		this.tree3 = new JTree(root);
		scrollPaneTree2.setViewportView(tree3);
		scrollPaneTree2.revalidate();
	}

	
	public void atualizarArvorePrecendences()
	{
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Machine Workingsteps");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Feature Precedences");
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
		this.treePrecedencesFeatures = new JTree(root);
		scrollPane1.setViewportView(treePrecedencesFeatures);
		scrollPane1.revalidate();
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
	
	public DefaultMutableTreeNode addTreeSubNodeWorkingstep (DefaultMutableTreeNode root, Workingstep ws, ArrayList<Workingstep> wsArray)
	{
		int i;

		//Adiciona ao root somente workingsteps sem precedentes e sem poscedentes
//		System.err.println("ws.getwsprecedente = " + ws.getWorkingstepPrecedente() + " POSCEDENTES_DIRETOS = " + ws.getWorkingstepsPoscedentesDiretos(wsArray).size());
		if (ws.getWorkingstepPrecedente() == null && ws.getWorkingstepsPoscedentesDiretos(wsArray).size() == 0)
		{
//			System.out.println("entrou no if, nao tem precedente nem poscedente, adicionado ao root");
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(ws.getId());
			/**
			 * 
			 */
			DefaultMutableTreeNode nodoFeatureTmp = new DefaultMutableTreeNode("Its Feature:");
			DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
			DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");
			
			newNode.add(nodoFeatureTmp);
			newNode.add(nodoOperationTmp);
			newNode.add(nodoFerramentaTmp);
			newNode.add(nodoCondicoesTmp);
			
			MachiningOperation operationTmp = ws.getOperation();
			Ferramenta ferrTmp = ws.getFerramenta();
			CondicoesDeUsinagem condTmp = ws.getCondicoesUsinagem();
			
			nodoFeatureTmp.add(new DefaultMutableTreeNode("Name : " + ws.getFeature().getNome()));

			nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Retract Plane : " + operationTmp.getRetractPlane()));
			
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + ferrTmp.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Name : " + ferrTmp.getName()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + ferrTmp.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + ferrTmp.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + ferrTmp.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + ferrTmp.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + ferrTmp.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + ferrTmp.getMaterial()));
			if(ferrTmp.getClass() == CenterDrill.class || ferrTmp.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + ferrTmp.getToolTipHalfAngle() + " °"));
			} else if (ferrTmp.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
			} else if (ferrTmp.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
			}
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("vc : " + condTmp.getVc() + " m/min"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + (int)condTmp.getN() + " rpm"));
			if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
			{	
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
			}
			
			return newNode;
		}
		
		//Adiciona ao root somente workingsteps sem precedentes, tratando casos com poscedentes com recursividade
		if (ws.getWorkingstepPrecedente() == null)
		{
//			System.out.println("entrou no else");
			DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(ws.getId());
			//if (get(i).getWorkingstepsPoscedentes)
			//newRoot.add(addTreeSubNode(newRoot, ws, wsArray));
			
			DefaultMutableTreeNode nodoFeatureTmp = new DefaultMutableTreeNode("Its Feature:");
			DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
			DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");
			
			newRoot.add(nodoFeatureTmp);
			newRoot.add(nodoOperationTmp);
			newRoot.add(nodoFerramentaTmp);
			newRoot.add(nodoCondicoesTmp);
			
			MachiningOperation operationTmp = ws.getOperation();
			Ferramenta ferrTmp = ws.getFerramenta();
			CondicoesDeUsinagem condTmp = ws.getCondicoesUsinagem();
			
			nodoFeatureTmp.add(new DefaultMutableTreeNode("Name : " + ws.getFeature().getNome()));

			nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Retract Plane : " + operationTmp.getRetractPlane()));
			
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + ferrTmp.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Name : " + ferrTmp.getName()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + ferrTmp.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + ferrTmp.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + ferrTmp.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + ferrTmp.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + ferrTmp.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + ferrTmp.getMaterial()));
			if(ferrTmp.getClass() == CenterDrill.class || ferrTmp.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + ferrTmp.getToolTipHalfAngle() + " °"));
			} else if (ferrTmp.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
			} else if (ferrTmp.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
			}
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("vc : " + condTmp.getVc() + " m/min"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + (int)condTmp.getN() + " rpm"));
			if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
			{	
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
			}
			
			for (i=0;i < ws.getWorkingstepsPoscedentesDiretos(wsArray).size();i++)
			{
				
				/**
				 * 
				 */
				
				newRoot.add(addTreeSubNodeWorkingstep(newRoot, ws.getWorkingstepsPoscedentesDiretos(wsArray).get(i), wsArray));
			}
			//root.add(newRoot);
			
			//DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(projetoSF.getWorkingsteps().get(i).getId());
			//newRoot.add(newNode);
			return newRoot;
		}
		
		//Adiciona ao root recursivo workingsteps com precedentes e poscedentes
		if (ws.getWorkingstepsPoscedentesDiretos(wsArray) != null)
		{
//			System.out.println("COM PRE e POS");
			DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(ws.getId());
			
			/**
			 * 
			 */
			DefaultMutableTreeNode nodoFeatureTmp = new DefaultMutableTreeNode("Its Feature:");
			DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
			DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");
			
			newRoot.add(nodoFeatureTmp);
			newRoot.add(nodoOperationTmp);
			newRoot.add(nodoFerramentaTmp);
			newRoot.add(nodoCondicoesTmp);
			
			MachiningOperation operationTmp = ws.getOperation();
			Ferramenta ferrTmp = ws.getFerramenta();
			CondicoesDeUsinagem condTmp = ws.getCondicoesUsinagem();
			
			nodoFeatureTmp.add(new DefaultMutableTreeNode("Name : " + ws.getFeature().getNome()));

			nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Retract Plane : " + operationTmp.getRetractPlane()));
			
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + ferrTmp.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Name : " + ferrTmp.getName()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + ferrTmp.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + ferrTmp.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + ferrTmp.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + ferrTmp.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + ferrTmp.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + ferrTmp.getMaterial()));
			if(ferrTmp.getClass() == CenterDrill.class || ferrTmp.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + ferrTmp.getToolTipHalfAngle() + " °"));
			} else if (ferrTmp.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
			} else if (ferrTmp.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
			}
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("vc : " + condTmp.getVc() + " m/min"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + (int)condTmp.getN() + " rpm"));
			if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
			{	
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
			}
			for (i = 0; i < ws.getWorkingstepsPoscedentesDiretos(wsArray).size(); i++)
			{
				newRoot.add(addTreeSubNodeWorkingstep(newRoot, ws.getWorkingstepsPoscedentesDiretos(wsArray).get(i), wsArray));
			}
			return newRoot;
		}
		
		//Adiciona ao root recursivo nos sem poscedentes
		if (ws.getWorkingstepsPoscedentesDiretos(wsArray) == null)
		{
//			System.out.println("--------------------- SEM POSSEDENTES");
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(ws.getId());
			
			/**
			 * 
			 */
			DefaultMutableTreeNode nodoFeatureTmp = new DefaultMutableTreeNode("Its Feature:");
			DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
			DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");
			
			newNode.add(nodoFeatureTmp);
			newNode.add(nodoOperationTmp);
			newNode.add(nodoFerramentaTmp);
			newNode.add(nodoCondicoesTmp);
			
			MachiningOperation operationTmp = ws.getOperation();
			Ferramenta ferrTmp = ws.getFerramenta();
			CondicoesDeUsinagem condTmp = ws.getCondicoesUsinagem();
			
			nodoFeatureTmp.add(new DefaultMutableTreeNode("Name : " + ws.getFeature().getNome()));

			nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Retract Plane : " + operationTmp.getRetractPlane()));
			
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + ferrTmp.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Name : " + ferrTmp.getName()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + ferrTmp.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + ferrTmp.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + ferrTmp.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + ferrTmp.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + ferrTmp.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + ferrTmp.getMaterial()));
			if(ferrTmp.getClass() == CenterDrill.class || ferrTmp.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + ferrTmp.getToolTipHalfAngle() + " °"));
			} else if (ferrTmp.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
			} else if (ferrTmp.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
			}
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("vc : " + condTmp.getVc() + " m/min"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + (int)condTmp.getN() + " rpm"));
			if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
			{	
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
			}
			
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
	
	public void salvar() 
	{
		FileDialog fd = new FileDialog(this, "Salvar", FileDialog.SAVE);
		for(int i = 0; i < projetoSF.getShopFloor().getMachines().size(); i++)
		{
			System.out.println("NOMES DAS MAQUINAS = " + projetoSF.getShopFloor().getMachines().get(i).getItsId());
		}
		fd.setFile(this.projetoSF.getShopFloor().getName());
		fd.setVisible(true);
		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file + ".SFL";
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath, false));
			out.writeObject(this.projetoSF);
			out.flush();
			out.close();
			this.textArea1.setText(this.textArea1.getText() + "\n" + "'" + file.toUpperCase() + "'" + " foi salvo no Computador com sucesso!");

			// arquivo vai estar salvo
			// this.salvo = true;//global
		} catch (Exception e) {
			e.printStackTrace();
			this.textArea1.setText(this.textArea1.getText() + "\nErro ao salvar " + "'" + file.toUpperCase() + "'");
		}
	}
	
	public void abrir() 
	{
		FileDialog fd = new FileDialog(this, "Abrir", FileDialog.LOAD);

		fd.setVisible(true);

		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file;
		try {
			this.textArea1.setText(this.textArea1.getText() + "\nOpening " + filePath);

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));

			this.projetoSF = (ProjetoSF) (in.readObject());
			this.shopFloor = this.projetoSF.getShopFloor();
			this.projeto = this.projetoSF.getProjeto();
//			this.setTitle("Shop Floor - " + this.projeto.getDadosDeProjeto().getProjectName());
			this.setTitle("Shop Floor - " + this.projetoSF.getShopFloor().getName());
			in.close();
		
			this.shopPanel = new ShopFloorPanel (this.projetoSF, this.shopFloor);
			this.panel1.setLayout(new BorderLayout());
			this.panel1.add(shopPanel);
			this.zooming = ((Double)spinnerZoom.getValue()).doubleValue();
			this.panel1.repaint();
			this.shopPanel.repaint();
			
			// inicializa o desenhador
			this.desenhador = new DesenhadorDeFaces(this.projeto);
			this.scrollPaneDesenho2.setViewportView(this.desenhador);
			this.desenhador.revalidate();
			this.scrollPaneDesenho2.revalidate();
			
			this.faceVisualizada = (Face) this.projeto.getBloco().faces.elementAt(0);
			this.faceTrabalho = (Face) this.projeto.getBloco().faces.elementAt(0);
			this.desenhador.alterarProjeto(this.projeto);
			this.atualizarArvoreMaquinas();
			this.atualizarArvorePrecendencesWorkingsteps();
			
			this.desenhadorPrecedencias = new WSPrecedencesPanel(projetoSF);
			scrollPane2.setViewportView(desenhadorPrecedencias);
			desenhadorPrecedencias.revalidate();
			scrollPane2.revalidate();
			janelaShopFloor = new JanelaShopFloor(shopFloor, projetoSF);
			
			desenhadorPrecedencias.addMouseMotionListener(new MouseMotionListener() 
			 {
				
				@Override
				public void mouseMoved(MouseEvent e) 
				{
					double x = e.getX();
					double y = e.getY();
					for (int i = 0; i < desenhadorPrecedencias.ClickWorkingsteps().size(); i++) 
					{
						if (desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x, y)) 
						{
							desenhadorPrecedencias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							break;
							
						} else if(!desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x, y))
						{
							desenhadorPrecedencias.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
					}
				}
				
				@Override
				public void mouseDragged(MouseEvent e) 
				{
				
				}
			});
			
			desenhadorPrecedencias.addMouseListener(new MouseListener() {  
	             public void mouseClicked(MouseEvent e) {  
	                
	            	 double x = e.getX();  
	            	 double y= e.getY(); 
	            	 
	            	 for(int i = 0; i < desenhadorPrecedencias.ClickWorkingsteps().size(); i++){
	            		 
				     	if(desenhadorPrecedencias.ClickWorkingsteps().get(i).contains(x,y)){
				     		
				     		System.err.println("clique");
				     	
				     	int id = (desenhadorPrecedencias.getIdClickBolinha().get(i));	
				     	if(id == 10){
				     		id = 0;
				     	}else{
				     		
				     		id = (id -10)/10;
				     	}
				     	System.err.println("--------------->> id = "+ id);
				     	
				     		if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == FaceMill.class || projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == BottomAndSideRoughMilling.class ){
				     				
				     			EditFaceMillWS editFacemillWS = new EditFaceMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editFacemillWS.setVisible(true);
				     			editFacemillWS.okButton.setVisible(false);
				     			editFacemillWS.spinner10.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getN());
				     			editFacemillWS.spinner9.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getVf());
				     			editFacemillWS.spinner11.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getAp());
				     			editFacemillWS.spinner12.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getAe());
				     			editFacemillWS.spinner7.setValue(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getCondicoesUsinagem().getVc());
				     			
				     		}else if (projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == CenterDrilling.class ){
				     			
				     			EditCenterDrillWS editCenterDrillWS = new EditCenterDrillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editCenterDrillWS.setVisible(true);
				     			editCenterDrillWS.okButton.setVisible(false);
				     		
				     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == Drilling.class){
				     			
				     			EditTwistDrillWS editTwistDrillWS = new EditTwistDrillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editTwistDrillWS.setVisible(true);
				     			editTwistDrillWS.okButton.setVisible(false);
				     			
				     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == BallEndMill.class){
				     			
				     			EditBallEndMillWS editBallEndMillWS = new EditBallEndMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editBallEndMillWS.setVisible(true);
				     			editBallEndMillWS.okButton.setVisible(false);
				     			
				     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == BoringTool.class){
				     			
				     			EditBoringToolWS editBoringToolWS = new EditBoringToolWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editBoringToolWS.setVisible(true);
				     			editBoringToolWS.okButton.setVisible(false);
				     			
				     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == EndMill.class || projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getOperation().getClass() == BottomAndSideFinishMilling.class ){
				     				
				     			EditFaceMillWS editFacemillWS = new EditFaceMillWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editFacemillWS.setVisible(true);
				     			editFacemillWS.okButton.setVisible(false);
				     			
				     		}else if(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id).getFerramenta().getClass() == Reamer.class){
				     			
				     			EditReamerWS editReamerWS = new EditReamerWS(janelaShopFloor, projetoSF, projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(id));
				     			editReamerWS.setVisible(true);
				     			editReamerWS.okButton.setVisible(false);
				     			
				     		}
				     	}
				      }
				  
	             }  

	             public void mousePressed(MouseEvent e) {  
	             }  

	             public void mouseReleased(MouseEvent e) {  
	             }  

	             public void mouseEntered(MouseEvent e) {  
	             }  

	             public void mouseExited(MouseEvent e) {  
	             }  
	         }); 
			
			
			
			this.gerar3D();
//			this.atualizarArvore();
//			this.atualizarArvoreCAPP();
//			this.atualizarArvorePrecedencias();
			
//			Region region = (Region)((Face)projeto.getBloco().faces.elementAt(0)).features.elementAt(0);
//			
//			for(int i = 0; i < region.getWorkingsteps().size(); i++)
//			{
//				System.out.println(region.getWorkingsteps().get(i).getWorkingstepPrecedente());
//				System.out.println(i);
//			}
	
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"                O arquivo que você esta tentando abrir "
									+ "\n não corresponde ao tipo de dados suportados pelo sistema",
							"Erro ao tentar abrir o arquivo",
							JOptionPane.OK_CANCEL_OPTION);
			this.textArea1.setText(this.textArea1.getText() + "\nErro ao abrir " + filePath);

		}
	}
	private void removeMachine()
	{
		DefaultMutableTreeNode node = null;
		node = (DefaultMutableTreeNode) tree2.getLastSelectedPathComponent();
		int [] tsm = new int [1];
		tsm = tree2.getSelectionRows();
		if(node != null){
			
			int pos = node.getIndex(node);
			
			Object objMachine = node.getUserObject();
			String stringMachine = (String) objMachine;

			StringTokenizer token = new StringTokenizer(stringMachine, " ");
			String testeMachine = token.nextToken();
			if( testeMachine.equals("Machine")){
				String name = this.projetoSF.getShopFloor().getMachines().get(tsm[0]-1).getItsId();
				
				int i = JOptionPane.showConfirmDialog(
						this,
						"          You clicked in remove a machine, "
								+ "                      \n"
								+ "           are you shure you want remove it? ",
								"remove Machine",
								JOptionPane.OK_CANCEL_OPTION);
				if (i == 0) {
					
					this.projetoSF.getShopFloor().getMachines().remove(tsm[0]-1);
					this.atualizarArvoreMaquinas1();
					this.shopPanel.repaint();
					this.textArea1.setText(this.textArea1.getText() + "\nThe machine: '" + name.toUpperCase() + "' was remove with success!");
				}
			} else
			{
				JOptionPane.showMessageDialog(
						null,
						"Invalid selection",
						"this is not a machine",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	/*public int getPosicaoMachine(String input){
	
		int n = 0;
		StringTokenizer token = new StringTokenizer(input, " ");
		if(token.countTokens()== 2){
			
			String tipo = token.nextToken();
			String num = token.nextToken();
	
			n = Integer.parseInt(num); 
			
		}
	
		return n-1;
	}*/

	@Override
	public void valueChanged(TreeSelectionEvent arg0) 
	{
		
	}
}
