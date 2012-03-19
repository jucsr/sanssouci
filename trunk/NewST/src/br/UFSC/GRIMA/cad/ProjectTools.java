package br.UFSC.GRIMA.cad;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.cad.visual.ProgressBar;
import br.UFSC.GRIMA.cad.visual.ProjectToolsFrame;
import br.UFSC.GRIMA.capp.AddNewBallEndMill;
import br.UFSC.GRIMA.capp.AddNewBoringTool;
import br.UFSC.GRIMA.capp.AddNewBullnoseEndMill;
import br.UFSC.GRIMA.capp.AddNewCenterDrill;
import br.UFSC.GRIMA.capp.AddNewEndMill;
import br.UFSC.GRIMA.capp.AddNewFaceMill;
import br.UFSC.GRIMA.capp.AddNewReamer;
import br.UFSC.GRIMA.capp.AddNewTwistDrill;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ProjectTools extends ProjectToolsFrame implements ActionListener
{
	private ArrayList<CenterDrill> centerDrills = new ArrayList<CenterDrill>();
	private ArrayList<TwistDrill> twistDrills = new ArrayList<TwistDrill>();
	private ArrayList<FaceMill> faceMills = new ArrayList<FaceMill>();
	private ArrayList<EndMill> endMills = new ArrayList<EndMill>();
	private ArrayList<BallEndMill> ballEndMills = new ArrayList<BallEndMill>();
	private ArrayList<BullnoseEndMill> bullnoseEndMills = new ArrayList<BullnoseEndMill>();
	private ArrayList<BoringTool> boringTools = new ArrayList<BoringTool>();
	private ArrayList<Reamer> reamers = new ArrayList<Reamer>();
	private ArrayList<ArrayList> selectedTools = new ArrayList<ArrayList>();
	
	private transient ToolManager toolManager;
	private transient JanelaPrincipal janela;
	
	private transient static boolean projectToolsDone;
	
	public ProjectTools(ArrayList<ArrayList> selectedTools, JanelaPrincipal janela)
	{
		this.janela = janela;
		this.selectedTools = selectedTools;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.addNewCenterDrill.addActionListener(this);
		this.addNewTwistDrill.addActionListener(this);
		this.addNewFaceMill.addActionListener(this);
		this.addNewEndMill.addActionListener(this);
		this.addNewBallEndMill.addActionListener(this);
		this.addNewBullnoseEndMill.addActionListener(this);
		this.addNewBoringTool.addActionListener(this);
		this.addNewReamer.addActionListener(this);
		this.buttonSelectNone.addActionListener(this);
		this.buttonSelectNone2.addActionListener(this);
		this.buttonSelectNone3.addActionListener(this);
		this.buttonSelectNone4.addActionListener(this);
		this.buttonSelectNone5.addActionListener(this);
		this.buttonSelectNone6.addActionListener(this);
		this.buttonSelectNone7.addActionListener(this);
		this.buttonSelectNone8.addActionListener(this);
		this.loadCatalogTools.addActionListener(this);
		this.menuItem1.addActionListener(this);
		this.menuItem2.addActionListener(this);
		
		this.setVisible(true);
		
		this.centerDrills = selectedTools.get(0);
		this.twistDrills = selectedTools.get(1);
		this.faceMills = selectedTools.get(2);
		this.endMills = selectedTools.get(3);
		this.ballEndMills = selectedTools.get(4);
		this.bullnoseEndMills  = selectedTools.get(5);
		this.boringTools = selectedTools.get(6);
		this.reamers = selectedTools.get(7);
		
		carregarSelecionadasNoPanel();
		
	}
	
	public ProjectTools(JanelaPrincipal janela)
	{
		this.janela = janela;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.addNewCenterDrill.addActionListener(this);
		this.addNewTwistDrill.addActionListener(this);
		this.addNewFaceMill.addActionListener(this);
		this.addNewEndMill.addActionListener(this);
		this.addNewBallEndMill.addActionListener(this);
		this.addNewBullnoseEndMill.addActionListener(this);
		this.addNewBoringTool.addActionListener(this);
		this.addNewReamer.addActionListener(this);
		this.buttonSelectNone.addActionListener(this);
		this.buttonSelectNone2.addActionListener(this);
		this.buttonSelectNone3.addActionListener(this);
		this.buttonSelectNone4.addActionListener(this);
		this.buttonSelectNone5.addActionListener(this);
		this.buttonSelectNone6.addActionListener(this);
		this.buttonSelectNone7.addActionListener(this);
		this.buttonSelectNone8.addActionListener(this);
		this.loadCatalogTools.addActionListener(this);
		this.menuItem1.addActionListener(this);
		this.menuItem2.addActionListener(this);
		this.menuItem3.addActionListener(this);
		
		this.setVisible(true);
		
		this.addToolsFromProject();
		
		
	}
	
	public void carregarSelecionadasNoPanel() 
	{
		DefaultTableModel model1 = (DefaultTableModel)centerDrillTable.getModel();
		DefaultTableModel model2 = (DefaultTableModel)twistDrillTable.getModel();
		DefaultTableModel model3 = (DefaultTableModel)faceMillTable.getModel();
		DefaultTableModel model4 = (DefaultTableModel)endMillTable.getModel();
		DefaultTableModel model5 = (DefaultTableModel)ballEndMillTable.getModel();
		DefaultTableModel model6 = (DefaultTableModel)bullnoseEndMillTable.getModel();
		DefaultTableModel model7 = (DefaultTableModel)boringToolTable.getModel();
		DefaultTableModel model8 = (DefaultTableModel)reamerTable.getModel();
		
		model1.setRowCount(0);
		model2.setRowCount(0);
		model3.setRowCount(0);
		model4.setRowCount(0);
		model5.setRowCount(0);
		model6.setRowCount(0);
		model7.setRowCount(0);
		model8.setRowCount(0);
		
		for(int i = 0; i<centerDrills.size(); i++){

			CenterDrill ferrTmp = centerDrills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getToolTipHalfAngle(), ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model1.addRow(rowData);

		}

		for(int i = 0; i<twistDrills.size(); i++){
			TwistDrill ferrTmp = twistDrills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getToolTipHalfAngle(), ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model2.addRow(rowData);
		}
		for(int i = 0; i<faceMills.size(); i++){

			FaceMill ferrTmp = faceMills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model3.addRow(rowData);

		}

		for(int i = 0; i<endMills.size(); i++){

			EndMill ferrTmp = endMills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model4.addRow(rowData);

		}

		for(int i = 0; i<ballEndMills.size(); i++){

			BallEndMill ferrTmp = ballEndMills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getEdgeRadius(), ferrTmp.getEdgeCenterVertical(), ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model5.addRow(rowData);

		}

		for(int i = 0; i<bullnoseEndMills.size(); i++){

			BullnoseEndMill ferrTmp = bullnoseEndMills.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getEdgeRadius(), ferrTmp.getEdgeCenterVertical(), ferrTmp.getEdgeCenterHorizontal(),
					ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model6.addRow(rowData);

		}

		for(int i = 0; i<boringTools.size(); i++){

			BoringTool ferrTmp = boringTools.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametro(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getEdgeRadius(), ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial(), ferrTmp.getAcoplamento()};

			model7.addRow(rowData);

		}
		
		for(int i = 0; i<reamers.size(); i++){

			Reamer ferrTmp = reamers.get(i);

			Object[] rowData = {i+1 , ferrTmp.getName(), ferrTmp.getDiametroFerramenta(), ferrTmp.getDm(), 
					ferrTmp.getCuttingEdgeLength(), ferrTmp.getProfundidadeMaxima(), ferrTmp.getOffsetLength(),
					ferrTmp.getNumberOfTeeth(), ferrTmp.getStringHandOfCut(), "Carbide", ferrTmp.getMaterial()};

			model8.addRow(rowData);

		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if(obj == okButton)
		{
			this.ok();
		} else if(obj == cancelButton)
		{
			this.dispose();
		} else if(obj == addNewReamer)
		{
			AddNewReamer ancd = new AddNewReamer(this);
			ancd.setVisible(true);
		} else if(obj == addNewTwistDrill)
		{
			AddNewTwistDrill antd = new AddNewTwistDrill(this);
			antd.setVisible(true);
		} else if(obj == addNewCenterDrill)
		{
			AddNewCenterDrill anfm = new AddNewCenterDrill(this);
			anfm.setVisible(true);
		} else if(obj == addNewFaceMill)
		{
			AddNewFaceMill anem = new AddNewFaceMill(this);
			anem.setVisible(true);
		} else if(obj == addNewEndMill)
		{
			AddNewEndMill end = new AddNewEndMill(this);
			end.setVisible(true);
		} else if(obj == addNewBallEndMill)
		{
			AddNewBallEndMill ball = new AddNewBallEndMill(this);
			ball.setVisible(true);
			
		} else if(obj == addNewBullnoseEndMill)
		{
			AddNewBullnoseEndMill bull = new AddNewBullnoseEndMill(this);
			bull.setVisible(true);
			
		} else if(obj == addNewBoringTool)
		{
			AddNewBoringTool boring = new AddNewBoringTool(this);
			boring.setVisible(true);
			
		} else if(obj == buttonSelectNone)
		{
			
		} else if(obj == buttonSelectNone2)
		{
			
		} else if(obj == buttonSelectNone3)
		{
			
		} else if(obj == buttonSelectNone4)
		{
			
		} else if(obj == buttonSelectNone5)
		{
			
		} else if(obj == buttonSelectNone6)
		{
			
		} else if(obj == buttonSelectNone7)
		{
			
		} else if(obj == buttonSelectNone8)
		{
			
		}else if(obj == loadCatalogTools)
		{
			final ProjectTools parent = this;
			final ProgressBar pb = new ProgressBar(this);
			pb.setVisible(true);

			SwingWorker worker = new SwingWorker() 
			{
				@Override
				protected Object doInBackground() throws Exception 
				{
					new SelectToolPanel(janela, parent);
					return null;
				}
				@Override
				protected void done()
				{
					pb.dispose();
				}
			};
			worker.execute();
		} else if(obj == this.menuItem1)
		{
			this.abrir();
		} else if(obj == this.menuItem2)
		{
			try {
				this.salvar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if(obj == this.menuItem3)
		{
			this.dispose();
		}
	}
	private void abrir() {

		new AbrirPeloBD(janela.getBancoDeDados(), janela.getProjeto().getDadosDeProjeto().getUserID()); 
		
	}

	private void ok()
	{
		this.toolManager = new ToolManager(this.janela.getProjeto(), this);
		
		janela.textArea1.setText(janela.textArea1.getText() + "\nFerramentas Adicionadas ao Projeto com sucesso! \n O Plano do Processo já pode ser gerado!");
		
		setProjectToolsDone(true);
		
		this.dispose();
	}
	public static boolean isProjectToolsDone() 
	{
		return projectToolsDone;
	}
	public static void setProjectToolsDone(boolean projectToolsDone) 
	{
		ProjectTools.projectToolsDone = projectToolsDone;
	}
	public void addToolsFromProject() 
	{
			
		Projeto projeto = janela.getProjeto();
		
		this.centerDrills = projeto.getCenterDrills();
		this.twistDrills = projeto.getTwistDrills();
		this.faceMills = projeto.getFaceMills();
		this.endMills = projeto.getEndMills();
		this.ballEndMills = projeto.getBallEndMills();
		this.bullnoseEndMills  = projeto.getBullnoseEndMills();
		this.boringTools = projeto.getBoringTools();
		this.reamers = projeto.getReamers();
		
		carregarSelecionadasNoPanel();
		
	}
	
	public ArrayList<ArrayList> getSelectedTools() 
	{
		return selectedTools;
	}
	public void setSelectedTools(ArrayList<ArrayList> allUsedTools) 
	{
		this.selectedTools = allUsedTools;
		this.centerDrills = this.selectedTools.get(0);
		this.twistDrills = this.selectedTools.get(1);
		this.faceMills = this.selectedTools.get(2);
		this.endMills = this.selectedTools.get(3);
		this.ballEndMills = this.selectedTools.get(4);
		this.bullnoseEndMills  = this.selectedTools.get(5);
		this.boringTools = this.selectedTools.get(6);
		this.reamers = this.selectedTools.get(7);
	}
	private void salvar() throws IOException 
	{
		
		this.toolManager = new ToolManager(this.janela.getProjeto(), this);
		
		this.addToolsFromProject();
		
		setProjectToolsDone(true);
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		ObjectOutputStream out = new ObjectOutputStream(bout);
		
		ArrayList<ArrayList> tools = new ArrayList<ArrayList>();
		
		tools.add(0, this.centerDrills);
		tools.add(1, this.twistDrills);
		tools.add(2, this.faceMills);
		tools.add(3, this.endMills);
		tools.add(4, this.ballEndMills);
		tools.add(5, this.bullnoseEndMills);
		tools.add(6, this.boringTools);
		tools.add(7, this.reamers);
		
		
		out.writeObject(tools);
		out.flush();
		out.close();
		
		byte[] b = bout.toByteArray();
		String s = JanelaPrincipal.write(b);
		
		AcessaBD bancoDeDados = janela.getBancoDeDados();
		
		Projeto projeto = janela.getProjeto();
		
		if (bancoDeDados.salvoNoBD(projeto)) {
			bancoDeDados.updateFerramentasProjeto(projeto, s);
		} else
			bancoDeDados.insertFerramentasProjeto(projeto, s);
		
	}
	
	private void abrir1(String tString) throws IOException, ClassNotFoundException 
	{
		
		byte b[];
		b = read(tString);  // string tools
		
		ByteArrayInputStream bait = new ByteArrayInputStream(b);
		ObjectInputStream in = new ObjectInputStream(bait);
		
		ArrayList<ArrayList> tools  = (ArrayList<ArrayList>) in.readObject();
		in.close();
		
		this.setSelectedTools(tools);
		
		this.carregarSelecionadasNoPanel();
		
	}
	
	public byte[] read(String lyly) {
		byte[] temp = lyly.getBytes();
		byte[] barney = new byte[temp.length / 4];
		// System.out.println("" + temp.length+ temp[temp.length-1]+
		// temp[temp.length-2]+ temp[temp.length-3]+ temp[temp.length-4]+
		// temp[temp.length-5]+ temp[temp.length-6]);
		for (int i = 0; i < temp.length; i = i + 4) {
			if (temp[i] == 45) {
				barney[i / 4] = (byte) ((-1) * (convert(temp[i + 1]) * 100
						+ convert(temp[i + 2]) * 10 + convert(temp[i + 3])));
			} else
				barney[i / 4] = (byte) (convert(temp[i + 1]) * 100
						+ convert(temp[i + 2]) * 10 + convert(temp[i + 3]));

			// System.out.println("" + barney[pointIndex/4] +": "+ pointIndex/4
			// + ":" + convert(temp[pointIndex+1])*100 +
			// convert(temp[pointIndex+2])*10 +convert(temp[pointIndex+3]));
		}
		return barney;
	}
	
	static int convert(byte bb) {
		return (bb - 48);
	}
	
	class AbrirPeloBD extends JFrame implements ActionListener {
		JLabel L1;
		JButton B2, B3;
		JComboBox TA1;
		JPanel P1;
		private AcessaBD bancoDeDados;
		private int userID;

		AbrirPeloBD(AcessaBD bancoDeDados, int userID) {

			this.bancoDeDados = bancoDeDados;
			this.userID = userID;
			
			setTitle("Abrir ferramentas salvas no servidor");
			this.setIconImage(new ImageIcon(getClass().getResource("/images/document-open16.png")).getImage());
			getContentPane().setLayout(new FlowLayout());
			L1 = new JLabel("Projetos:");
			L1.setForeground(Color.black);
			B2 = new JButton("Abrir");
			B3 = new JButton("Cancelar");
			B2.addActionListener(this);
			B3.addActionListener(this);
			P1 = new JPanel();
			P1.setLayout(new FlowLayout());
			TA1 = new JComboBox(bancoDeDados.listaProjeto(this.userID)); // UserID;
			TA1.setEnabled(true);
			getContentPane().add(L1);
			getContentPane().add(TA1);
			getContentPane().add(B2);
			getContentPane().add(B3);
			getContentPane().add(P1);
			setLocation(200, 200);
			pack();
			
			this.setVisible(true);

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == B3) // cancelar
			{
				dispose();
			}

			if (e.getSource() == B2) // abrir
			{
				try {
					
					String linha = (String) TA1.getSelectedItem();
					
					String pNome = linha.split(" : ")[0];
					
					String ferramentas = bancoDeDados.getFerramentasProjetoPorNome(this.userID, pNome); //UserID;
					
					if(ferramentas!=null)
						abrir1(ferramentas); 
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		}
	}

	public ToolManager getToolManager() {
		return toolManager;
	}

	public void setToolManager(ToolManager toolManager) {
		this.toolManager = toolManager;
	}

	public JanelaPrincipal getJanela() {
		return janela;
	}

	public void setJanela(JanelaPrincipal janela) {
		this.janela = janela;
	}
}
