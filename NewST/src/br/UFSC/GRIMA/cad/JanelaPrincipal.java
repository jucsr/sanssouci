package br.UFSC.GRIMA.cad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
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
import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.cad.visual.JanelaPrincipalFrame;
import br.UFSC.GRIMA.cad.visual.Progress3D;
import br.UFSC.GRIMA.capp.AlterarPosicaoWs;
import br.UFSC.GRIMA.capp.CAPP;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.EditBallEndMillWS;
import br.UFSC.GRIMA.capp.EditBoringToolWS;
import br.UFSC.GRIMA.capp.EditBullnoseEndMillWS;
import br.UFSC.GRIMA.capp.EditCenterDrillWS;
import br.UFSC.GRIMA.capp.EditEndMillWS;
import br.UFSC.GRIMA.capp.EditFaceMillWS;
import br.UFSC.GRIMA.capp.EditReamerWS;
import br.UFSC.GRIMA.capp.EditTwistDrillWS;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.StepNcProject;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.exceptions.MissingDataException;
import br.UFSC.GRIMA.integracao.ProjectReader;
import br.UFSC.GRIMA.util.PrintUtilities;
import br.UFSC.GRIMA.util.ToolReader;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class JanelaPrincipal extends JanelaPrincipalFrame{
	public OrdemDeFabricacaoJDialog ordem;
	private CAPP capp;
	private AcessaBD bancoDeDados;
	private Projeto projeto = null;
	public ToolManager toolManager;
	private Face faceVisualizada = null;
	private Face faceTrabalho = null;
	private boolean modificado = false;
	public double zoom = 1;
	private StepNcProject stepNc;
	public boolean english = true;
	
	public CondicoesDeUsinagem condicoesAtuais = null;
	private boolean error = false;
	static private boolean doneCAPP = false;

	
	public JanelaPrincipal(Projeto projeto, AcessaBD bancoDeDados) 
	{
		this.bancoDeDados = bancoDeDados;
		this.projeto = projeto;
		this.setTitle("STEP Modeler - " + this.projeto.getDadosDeProjeto().getProjectName());
		if (english)
		{
			this.buttonRemover.setText("Remove");
			this.buttonRemoverWS.setText("Remove");
			this.buttonRemoverWS2.setText("Remove");
			this.buttonEditar.setText("Edit");
			this.buttonEditarWS.setText("Edit");
			this.buttonEditarWS2.setText("Edit");
			this.buttonAlterarWorkplan.setText("Change sequence");
			this.buttonAlterarWorkplan2.setText("Change sequence");
			this.menuArquivo.setText("File");
			this.menuFerramentas.setText("Settings");
			this.menu3.setText("View");
			this.menuAjuda.setText("Help");
		}
		this.init();
	}
	public void init() 
	{
		this.adicionarOuvidores();
		this.adjustJFrame();

		this.initComponents();

		this.setVisible(true);
	}
	
	/**
	 * matrizRotacoes
	 * [6(FaceDesenhada)][4(verticeAtivado)][3(tipoRotacao)][2(novaFace
	 * novoVertice)]
	 */

	// JOptionPane optionPane;
	// public MapeadoraDeWorkingstep mapeadora;
	static int convert(byte bb) {
		return (bb - 48);
	}

	// ///////

	class AbrirPeloBD extends JFrame implements ActionListener {
		JLabel L1;
		JButton B2, B3;
		JComboBox TA1;
		JPanel P1;
		JanelaPrincipal janela;
		
		private AcessaBD bancoDeDados;

		AbrirPeloBD(AcessaBD bancoDeDados, JanelaPrincipal appp) {
			
			this.janela = appp;

			this.bancoDeDados = bancoDeDados;
			setTitle("Abrir projeto pelo servidor");
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
			TA1 = new JComboBox(bancoDeDados.listaProjeto(janela.getProjeto().getDadosDeProjeto().getUserID())); //UserID;
			TA1.setEnabled(true);
			getContentPane().add(L1);
			getContentPane().add(TA1);
			getContentPane().add(B2);
			getContentPane().add(B3);
			getContentPane().add(P1);
			setLocation(200, 200);
			pack();

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == B3) // cancelar
			{
				dispose();
			}

			if (e.getSource() == B2) // abrir
			{
				String linha = (String) TA1.getSelectedItem();
				
				String pNome = linha.split(" : ")[0];
				try {
					
					
					abrir1(bancoDeDados.getProjetoPorNome(janela.getProjeto().getDadosDeProjeto().getUserID(), pNome)); //UserID;
					textArea1.setText(textArea1.getText() + "\nAbrindo " + "'" + linha.toUpperCase() + "'");

				} catch (IOException e1) {
					e1.printStackTrace();
					textArea1.setText(textArea1.getText() + "\nErro ao abrir projeto " + "'" + pNome.toUpperCase()+ "'" + " do banco de dados ");

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		}
	}


	
	public static String write(byte[] barney) throws IOException 
	{
		String lyly = "";
		String sinal = "";
		
		for (int i = 0; i < barney.length; i++)
		{
			if (barney[i] < 0)
			{
				sinal = "-";
				if (barney[i] == (byte) -128) {
					sinal = "";
				}
				barney[i] = (byte) (barney[i] * (-1));
			}else
				sinal = "+";
			
			if (barney[i] < 10 && barney[i] > -10) 
			{
				lyly = lyly + sinal + "00" + barney[i];
			} else if (barney[i] < 100 && barney[i] > -100) 
			{
				lyly = lyly + sinal + "0" + barney[i];
			} else
				lyly = lyly + sinal + barney[i];
		}
		return lyly;
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

	void abrir1(String pString) throws IOException, ClassNotFoundException {

		byte b2[];
		
		b2 = read(pString);  // string projeto
		
		ByteArrayInputStream bait2 = new ByteArrayInputStream(b2);
		ObjectInputStream in2 = new ObjectInputStream(bait2);
		Projeto projetoLido = (Projeto) in2.readObject();
		in2.close();
		
		this.projeto = projetoLido;
		this.setTitle("STEP Modeler - " + this.projeto.getDadosDeProjeto().getProjectName());
		this.faceVisualizada = (Face) this.projeto.getBloco().faces.elementAt(0);
		this.faceTrabalho = (Face) this.projeto.getBloco().faces.elementAt(0);
		this.desenhador.alterarProjeto(this.projeto);
		this.projeto.setConn(new ToolReader().getConn());
		this.projeto.setStatement(new ToolReader().getStatement());
		if(this.projeto.isToolsDone()){

			ProjectTools.setProjectToolsDone(true);
			ToolManager.setCenterDrills(this.projeto.getCenterDrills());
			ToolManager.setTwistDrills(this.projeto.getTwistDrills());
			ToolManager.setFaceMills(this.projeto.getFaceMills());
			ToolManager.setEndMills(this.projeto.getEndMills());
			ToolManager.setBallEndMills(this.projeto.getBallEndMills());
			ToolManager.setBullnoseEndMills(this.projeto.getBullnoseEndMills());
			ToolManager.setReamers(this.projeto.getReamers());
			ToolManager.setBoringTools(this.projeto.getBoringTools());

		}
		
		setDoneCAPP(this.projeto.isCappDone());
		atualizarArvore();
		atualizarArvoreCAPP();
	}

	public void initComponents() {
		// seta a possibilidade de salvar ou nï¿½o no banco de dados
		this.menuItemSalvarBD.setEnabled(true);

		// inicializa o desenhadorrra
		this.desenhador = new DesenhadorDeFaces(this.projeto);
		this.scrollPaneDesenho.setViewportView(this.desenhador);
		this.desenhador.revalidate();
		this.scrollPaneDesenho.revalidate();

		// inicializa o local de trabalho na peça bruta
		this.trocarFaceTrabalho(Face.XY);
		this.trocarFaceVisualizada(Face.XY, 0);

		// inicializa a arvore de informações do projeto
		this.atualizarArvore();
		this.atualizarArvorePrecedencias();
	}

	public void adjustJFrame() {
		// this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Toolkit toolkit = this.getToolkit();

		this.setResizable(false);
		this.pack();

		Dimension windowDimension = toolkit.getScreenSize();
		Dimension thisDimension = this.getPreferredSize();
		this
				.setLocation((int) (windowDimension.getWidth() - thisDimension
						.getWidth()) / 2,
						(int) (windowDimension.getHeight() - thisDimension
								.getHeight()) / 2);

	}

	class PerguntaSalvarNoBD extends JFrame implements ActionListener {
		JLabel L1;
		JButton B2, B3;
		JPanel P1;
		private AcessaBD bancoDeDados;
		private Projeto projeto;
		private JanelaPrincipal parent;
		
		public PerguntaSalvarNoBD(JanelaPrincipal parent, AcessaBD bancoDeDados, Projeto projeto) {
			this.parent = parent;
			this.projeto = projeto;
			this.bancoDeDados = bancoDeDados;
			this.setIconImage(new ImageIcon(getClass().getResource("/images/document-save16.png")).getImage());
			setTitle("Dejesa salvar projeto antes de sair?");
			setResizable(false);
			getContentPane().setLayout(new FlowLayout());
			L1 = new JLabel("Dejesa salvar o projeto no servidor?");
			L1.setForeground(Color.black);
			B2 = new JButton("Salvar");
			B3 = new JButton("Não salvar");
			B2.addActionListener(this);
			B3.addActionListener(this);
			P1 = new JPanel();
			P1.setLayout(new FlowLayout());

			getContentPane().add(L1);
			getContentPane().add(B2);
			getContentPane().add(B3);
			getContentPane().add(P1);
			setLocation(200, 200);
			pack();
			deleteFile(new File("C:\\repositories.tmp"));
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == B3) // nao salvar
			{
				dispose();
			}

			if (e.getSource() == B2) // salvar
				
			{
				try {
					parent.salvarNoBD();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				dispose();

			}
		}
	}

	public void adicionarOuvidores() {
		
		final JanelaPrincipal parent = this;
		
		JanelaPrincipal_button_actionAdapter ouvidorBotao = new JanelaPrincipal_button_actionAdapter(this);

		WindowListener x = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				JFrame Janela2 = new PerguntaSalvarNoBD(parent, bancoDeDados, projeto);
				WindowListener x1 = new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						dispose();
					}
				};
				Janela2.addWindowListener(x1);
				Janela2.setVisible(true);
			}
		};
		this.addWindowListener(x);
		this.setVisible(true);
		this.buttonRemover.addActionListener(ouvidorBotao);
		this.buttonEditar.addActionListener(ouvidorBotao);
		this.buttonBoss.addActionListener(ouvidorBotao);
		this.zoomMais.addActionListener(ouvidorBotao);
		this.zoomMenos.addActionListener(ouvidorBotao);
		this.girar2.addActionListener(ouvidorBotao);
		this.girarEixoX.addActionListener(ouvidorBotao);
		this.girarEixoY.addActionListener(ouvidorBotao);
		this.buttonNovo.addActionListener(ouvidorBotao);
		this.buttonSalvar.addActionListener(ouvidorBotao);
		this.buttonAbrir.addActionListener(ouvidorBotao);
		this.buttonRemoverWS.addActionListener(ouvidorBotao);
		this.buttonRemoverWS2.addActionListener(ouvidorBotao);
		this.buttonEditarWS.addActionListener(ouvidorBotao);
		this.buttonEditarWS2.addActionListener(ouvidorBotao);
		this.buttonAlterarWorkplan.addActionListener(ouvidorBotao);
		this.buttonAlterarWorkplan2.addActionListener(ouvidorBotao);
		JanelaPrincipal_menus_actionAdapter ouvidorMenus = new JanelaPrincipal_menus_actionAdapter(
				this, projeto, bancoDeDados);

		this.menuItemAbrirSTEP.addActionListener(ouvidorMenus);
		this.menuItemGerar.addActionListener(ouvidorMenus);
		this.menuItemAbout.addActionListener(ouvidorMenus);
		this.menuItemHelp.addActionListener(ouvidorMenus);
		this.menuItemCriarFuro.addActionListener(ouvidorMenus);
		this.menuItemCriarDegrau.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhura.addActionListener(ouvidorMenus);
		this.menuItemCriarCavidade.addActionListener(ouvidorMenus);
		this.menuItemCriarCavidadeFun.addActionListener(ouvidorMenus);
		this.menuItemCriarFuroBaseConica.addActionListener(ouvidorMenus);
		this.menuItemCriarFuroBaseEsferica.addActionListener(ouvidorMenus);
		this.menuItemCriarFuroBaseArredondada.addActionListener(ouvidorMenus);
		this.menuItemCriarFuroConico.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhuraUPerfil.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhuraCircularPerfil.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhuraPerfilRoundedU.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhuraPerfilVee.addActionListener(ouvidorMenus);
		this.menuItemCriarRanhuraPerfilBezier.addActionListener(ouvidorMenus);
		this.menuItemSair.addActionListener(ouvidorMenus);
		this.menuItemVerPlanosReferencia.addActionListener(ouvidorMenus);
		this.menuItemAbrir.addActionListener(ouvidorMenus);
		this.menuItemAbrirBD.addActionListener(ouvidorMenus);
		this.menuItemSalvar.addActionListener(ouvidorMenus);
		this.menuItemSalvarBD.addActionListener(ouvidorMenus);
		this.menuItemNovoProjeto.addActionListener(ouvidorMenus);
		this.menuItemOrdemFabricacao.addActionListener(ouvidorMenus);
		this.menuItemImprimir.addActionListener(ouvidorMenus);
		this.definirPontosApoio.addActionListener(ouvidorMenus);
		this.menuItemAbrirXML.addActionListener(ouvidorMenus);
		this.fixarFaceXY.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// XY
		this.fixarFaceYZ.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// YZ
		this.fixarFaceXZ.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// XZ
		this.fixarFaceYX.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// YX
		this.fixarFaceZY.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// ZY
		this.fixarFaceZX.addActionListener(ouvidorMenus);// fixar peça em
		// plano
		// ZX
		this.gerarCAPP.addActionListener(ouvidorMenus);
		this.vistaPlanoXY.addActionListener(ouvidorMenus); // Vista em Plano XY
		this.vistaPlanoYZ.addActionListener(ouvidorMenus); // Vista em Plano YZ
		this.vistaPlanoXZ.addActionListener(ouvidorMenus); // Vista em Plano XZ
		this.vistaPlanoYX.addActionListener(ouvidorMenus);// Vista em Plano YX
		this.vistaPlanoZY.addActionListener(ouvidorMenus);// Vista em Plano ZY
		this.vistaPlanoZX.addActionListener(ouvidorMenus);// Vista em Plano ZX
		this.vistaPlanosPrincipais.addActionListener(ouvidorMenus); // Desenha
		// todos os
		// planos
		this.mostrarPecaBruta.addActionListener(ouvidorMenus); // Mostra ou
		// deleta a peça
		// Bruta
		this.gerar3D.addActionListener(ouvidorMenus); // Gera vista em 3D
		this.verEixos.addActionListener(ouvidorMenus);
		this.mostrarMesa.addActionListener(ouvidorMenus);
		this.mostrarFeaturesSecundarias.addActionListener(ouvidorMenus);
		super.spinnerZoom.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				zoom = getZoom();
				desenhador.setZoom((double) zoom);
				scrollPaneDesenho.revalidate();
			}
		});
		this.menuGirar.addActionListener(ouvidorMenus);
		this.menuItemGerFerr.addActionListener(ouvidorMenus);
		this.criarRegion.addActionListener(ouvidorMenus);
		this.menuItemCriarCavidadeGeneral.addActionListener(ouvidorMenus);

		JanelaPrincipal_combobox_actionAdapter ouvidorCombobox = new JanelaPrincipal_combobox_actionAdapter(
				this);
		// this.zoomComboBox.addItemListener(ouvidorCombobox);

		JanelaPrincipal_arvore_actionAdapter ouvidorArvore = new JanelaPrincipal_arvore_actionAdapter(
				this);
		this.tree2.addTreeSelectionListener(ouvidorArvore);
	}

	public void setModificado(boolean x) {
		this.modificado = x;
	}

	/**
	 * comeï¿½o dos tratadores de cada evento
	 */
	public void girarX() {

	}

	public void girarY() {

	}

	public void girar(int botaoClicado) {
		int[] novaPosicao = Matrizes.rotacoes[this.faceVisualizada.getTipo()][this.faceVisualizada
				.getVerticeAtivada()][botaoClicado];
		this.trocarFaceVisualizada(novaPosicao[0], novaPosicao[1]);
		this.label3.setText(faceVisualizada.getTipoString());
		String text = textArea1.getText();
		textArea1.setText(text + "\n face visualizada:" + "\t " + this.faceVisualizada.getTipoString());
		this.desenhador.repaint();
		// this.vistas.setFaceCentral(novaPosicao[0], novaPosicao[1]);
	}

	public void mostrarFeaturesSecundarias() {
		if (this.desenhador.dFeaturesSecundarias)
			this.desenhador.dFeaturesSecundarias = false;
		else
			this.desenhador.dFeaturesSecundarias = true;
		this.desenhador.repaint();
	}

	public void editarFeature() {

	}

	public void removerFeature() {
		boolean ok = false;
		DefaultMutableTreeNode node1 = null, node2 = null;
		node1 = (DefaultMutableTreeNode) tree2.getLastSelectedPathComponent();
		if (node1 != null) {
			node2 = (DefaultMutableTreeNode) node1.getParent();
			if (node2 != null)
				ok = true;
		}
		if (ok) {
			Object objPai = node1.getUserObject();
			Object objFilho = node2.getUserObject();

			String stringPai = (String) objFilho;
			String stringFilho = (String) objPai;
			
			Face face = this.getFace(stringPai);
			if (face != null) {
				int pos = getPosicaoFeature(stringFilho, face);

				if (pos != -1) {
					Feature featureRemovida = (Feature) face.features
							.elementAt(pos);
					Rectangle2D removida2d = face
							.criarRetanguloShape(featureRemovida);
					Vector removidas = new Vector();
					for (int i = 0; i < face.features.size(); i++) {
						Feature featureTmp = (Feature) face.features
								.elementAt(i);
						Rectangle2D rect2dTmp = face
								.criarRetanguloShape(featureTmp);
						if (removida2d.contains(rect2dTmp)) {
							removidas.add(featureTmp);
						}
					}
					// System.out.println(" removidas = " + removidas +
					// " elementos: " + removidas.size());
					// if (removidas.size() != 0)
					if (removidas.size() > 1) {
						// avisa ao cara
						// remove

						int i = JOptionPane
								.showConfirmDialog(
										null,
										"A feature que você selecionou "
												+ "removerá também todas as features que estam debaixo dela"
												+ "\n                                                   quer removêla mesmo assim? ",
										"Aviso de remoção de varias features associadas",
										JOptionPane.OK_CANCEL_OPTION);

						if (i == 0) {
							for (int j = 0; j < removidas.size(); j++) {
								Feature fTmp = (Feature) removidas.elementAt(j);

								for (int r = 0; r < face.features.size(); r++) {
									Feature f = (Feature) face.features
											.elementAt(r);
									if (fTmp.equals(f)) {
										face.features.removeElementAt(r);
									}
								}
							}
							this.atualizarArvore();
							this.desenhador.repaint();
							JOptionPane.showMessageDialog(null,
									"As features foram removidas com sucesso",
									"remoção com sucesso",
									JOptionPane.INFORMATION_MESSAGE);
						}
						// System.out.println("face.features: " +
						// face.features);
					}
					if (removidas.size() == 1) {
						int i = JOptionPane
								.showConfirmDialog(
										this,
										"          Você Clicou em remover uma br.UFSC.GRIMA.feature, "
												+ "                      \n"
												+ "           Tem certeza que deseja removê-la? ",
										"Remover Feature",
										JOptionPane.OK_CANCEL_OPTION);
						if (i == 0) {
							// System.out.println("elementos: " +
							// face.features);
							face.features.removeElementAt(pos);
							this.desenhador.repaint();
							JOptionPane
									.showMessageDialog(
											null,
											"A Feature que você selecionou foi removida com sucesso",
											"Remoção concluida",
											JOptionPane.INFORMATION_MESSAGE);
							this.modificado = false;
							this.atualizarArvore();
							setDoneCAPP(false);
							ToolManager.setToolManagerDone(false);
							// System.out.println("elementos: " +
							// face.features);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"O item que você selecionou nao pode ser removido",
							"Erro ao remover item (AXOO1)",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"O item que você selecionou nao pode ser removido",
						"Erro ao remover item (SMDO2)",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"O item que você selecionou nao pode ser removido",
					"Erro ao remover item (LDKO3)", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Face getFace(String str) {
		int indice = -1;
		if (str.equals("Face XY")) {
			indice = 0;
		} else if (str.equals("Face YZ")) {
			indice = 1;
		} else if (str.equals("Face XZ")) {
			indice = 2;
		} else if (str.equals("Face YX")) {
			indice = 3;
		} else if (str.equals("Face ZY")) {
			indice = 4;
		} else if (str.equals("Face ZX")) {
			indice = 5;
		}

		if (indice == -1)
			return null;
		else
			return (Face) this.projeto.getBloco().faces.elementAt(indice);
	}


	/**
	 * Retorna a posiï¿½ï¿½o de uma br.UFSC.GRIMA.feature no vetor de features
	 * da face de entrada. Estado - finalizada.
	 * 
	 * @param input
	 * @param face
	 * @return
	 */
	public int getPosicaoFeature(String input, Face face) {
		int nTipo = -1;
		int n = -1;

		StringTokenizer token = new StringTokenizer(input, "-");
		if (token.countTokens() == 2) {
			String tipo = token.nextToken();
			String num = token.nextToken();
		
			if (tipo.equals("Furo "))
				nTipo = 0;
			else if (tipo.equals("Conic Bottom Round Hole "))
				nTipo = 0;
			else if (tipo.equals("Furo com base arredondada "))
				nTipo = 0;
			else if (tipo.equals("Furo com base esf�rica "))
				nTipo = 0;
			else if (tipo.equals("Furo c�nico "))
				nTipo = 0;
			else if (tipo.equals("Degrau "))
				nTipo = 1;
			else if (tipo.equals("Ranhura "))
				nTipo = 2;
			else if (tipo.equals("Closed Pocket "))
				nTipo = 3;
			else if (tipo.equals("Banheira "))
				nTipo = 4;
			else if (tipo.equals("Boss ")) // ----> dar uma olhada
				nTipo = 6;
			else if (tipo.equals("Region " ))
				nTipo = 7;
			else if(tipo.equals("General Closed Profile Pocket "))
				nTipo = 8;
			else
			{
				// dialog pane
				return -1;
			}
			try {
				n = Integer.parseInt(num);
			} catch (Exception e) {
				// dialog pane
				return -1;
			}
			for (int i = 0; i < face.features.size(); i++) {
				Feature f = (Feature) face.features.elementAt(i);
				if (f.getTipo() == nTipo && f.getIndice() == n) {
					return i;
				}
			}
		} else {
			return -1;
		}
		return -1;
	}

	public void atualizarArvore() {
		this.tree2 = this.projeto.getJTree();
		// this.tree2.addTreeSelectionListener(this);
		this.tree2.getSelectionModel().setSelectionMode(
				TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		scrollPaneTree.setViewportView(tree2);
		scrollPaneTree.revalidate();
		// System.out.println("" + tree2);
		// tree2.getSelectionPath();
	}
	public void atualizarArvorePrecedencias()
	{
		this.tree3 = ArvorePrecedencias.getArvorePrecedencias(projeto);
		this.tree3.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		this.scrollPaneTree2.setViewportView(tree3);
		this.scrollPaneTree2.revalidate();
	}
	public void atualizarArvoreCAPP() {

		
		DefaultMutableTreeNode nodoPrincipal = new DefaultMutableTreeNode
		("Roughing & Finishing Workingsteps");
		
		DefaultMutableTreeNode nodoPrincipalWorkplan = new DefaultMutableTreeNode
		("Main Workplan");
		
		
//		DefaultMutableTreeNode nodosSetups = this.projeto.getNodosSetups(nodoPrincipal);
//		
//		nodoPrincipal.add(nodosSetups);
		
		this.projeto.getNodosSetups(nodoPrincipal, false);
		this.projeto.getNodosSetups(nodoPrincipalWorkplan, true);

		this.workingstepsTree = new JTree(nodoPrincipal);
		this.workplanTree = new JTree(nodoPrincipalWorkplan);
		
		this.workingstepsTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		this.workplanTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		
		scrollPaneWsTree.setViewportView(workingstepsTree);
		scrollPaneWsTree2.setViewportView(workplanTree);
		
		scrollPaneWsTree.revalidate();
		scrollPaneWsTree2.revalidate();
	}
	
	/** aba das features maes e filhas **/
//	public void atualizarArvoreFeatureRelationship() {
//		this.tree3 = this.projeto.getJTree();
//		// this.tree2.addTreeSelectionListener(this);
//		this.tree3.getSelectionModel().setSelectionMode(
//				TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
//		scrollPaneTree2.setViewportView(tree3);
//		scrollPaneTree2.revalidate();
//		// System.out.println("" + tree2);
//		// tree2.getSelectionPath();
//	}

	
	public JTree getArvore() {
		return new JTree(new DefaultMutableTreeNode("Projeto"));
	}

	/**
	 * imprimir folha ****
	 */
	public void imprimirTela() {
		PrintUtilities.printComponent(this.desenhador);
	}

	public void novoProjeto() {
		/*
		 * if( optionPane.showConfirmDialog(this, " O projeto atual nï¿½o foi
		 * Salvo, \n" + " Deseja salvï¿½-lo? ", "Salvar Projeto Atual",
		 * JOptionPane.INFORMATION_MESSAGE) == 1) {
		 * 
		 * }
		 */

		// JOptionPane.showConfirmDialog(this, "
		// O projeto atual nï¿½o foi Salvo, \n" +
		// "                            Deseja salvï¿½-lo? ",
		// "Salvar Projeto Atual", JOptionPane.INFORMATION_MESSAGE);
		// this.dispose();
//		NovoProjeto novo = new NovoProjeto(this.projeto.getDadosDeProjeto()
//				.getUserID(), this.projeto.getDadosDeProjeto().getUserName());
//		novo.setVisible(true);
		CriarNovoProjeto criarNovoProjeto = new CriarNovoProjeto(this.projeto.getDadosDeProjeto().getUserID(), this.projeto.getDadosDeProjeto().getUserName());
		criarNovoProjeto.setVisible(true);
	}

	/**
	 * Altera a face que esta sendo visualizada.****************************
	 * 
	 * @param pos
	 */
	public void trocarFaceVisualizada(int faceNova, int verticeNovo) {
		this.faceVisualizada = (Face) this.projeto.getBloco().faces
				.elementAt(faceNova);
		this.faceVisualizada.setVertice(verticeNovo);
		// this.desenhador.setFaceDesenhada(this.faceVisualizada.getTipo());
		// /***********/
		this.desenhador.setFacePrincipal(this.faceVisualizada.getTipo(),
				verticeNovo);
	}

	public void verEixos() {
		if (this.desenhador.dEixos) {
			this.desenhador.dEixos = false;
		} else
			this.desenhador.dEixos = true;
		this.desenhador.repaint();
	}

	public void mostrarPlanosPrincipais() {
		// this.vistas.setVisible(true);
		// this.vistas.pack();
		this.desenhador.trocarModo();
		this.desenhador.repaint();
	}

	public void mostrarMesa() {
		if (this.desenhador.dMesa)
			this.desenhador.dMesa = false;
		else
			this.desenhador.dMesa = true;
		this.desenhador.repaint();
	}

	public void diminuirZoom() {
		double zoom = this.getZoom() - 0.05;
		if (zoom > 0.1)
			this.zoom = zoom;
		this.scrollPaneDesenho.revalidate();
//		this.textFieldZoom.setText("" + Math.round(zoom * 100));
		this.spinnerZoom.setModel(new SpinnerNumberModel(zoom * 100, 0, 500, 5));
		
		this.desenhador.setZoom(zoom);
	}

	public void aumentarZoom() {
		double zoom = this.getZoom() + 0.05;
		this.scrollPaneDesenho.revalidate();
//		this.textFieldZoom.setText(Math.round(zoom * 100) + "");
		this.spinnerZoom.setModel(new SpinnerNumberModel(zoom * 100, 0, 500, 5));
		
		desenhador.setZoom(zoom);
	}

	public void pecaBruta() {
		if (this.desenhador.blank)
			this.desenhador.blank = false;
		else
			this.desenhador.blank = true;
		this.desenhador.repaint();
	}

	public void gerar3D() {
		
		final Progress3D p3D = new Progress3D(this);

		p3D.setVisible(true);

		SwingWorker worker = new SwingWorker() 
		{
			@Override
			protected Object doInBackground() throws Exception 
			{
				Generate3Dview parent = new Generate3Dview(projeto);
				parent.setVisible(true);
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

	public void gerarCAPP() {
		/**
		 * Tem que perguntar o tipo de material para o usuario. Caso ele escolha
		 * um material proprio, deve-se pedir as condicoes de usinagem do
		 * material.
		 * 
		 * Estes dados devem ser enviados para a classe CAPP.
		 * 
		 * Quando o usuario resolver enviar uma ordem de fabricacao, deve-se
		 * verificar se ja existem dados referentes ao material e ï¿½s
		 * condicoes, neste caso pergunta-se ao usuario se ele deseja manter
		 * estas configuracoes. Caso ele deseje alterar as configuracoes ou caso
		 * estas ainda nao existam deve-se pergunta-las ao usuario. Por fim,
		 * deve-se perguntar ao usuario a quantidade de peças que ele deseja.
		 * 
		 * IMPORTANTE: O codigo HPGL formatado so deve ser gerado quando o
		 * usuaro solicitar uma ordem de fabricacao, por causa do parametro
		 * quantidade.
		 */
		{

			if (!ProjectTools.isProjectToolsDone()) {

				int i = JOptionPane
						.showConfirmDialog(
								null,
								"Voce deve definir um conjunto de ferramentas para gerar o Plano do Processo. \n"
										+ "Deseja definí-lo agora?",
								"Warning!", JOptionPane.OK_CANCEL_OPTION);

				if (i == JOptionPane.OK_OPTION) {

					this.gerenciarFerramentas();

				}

			} else {

//				this.textArea1.setText(this.textArea1.getText() + "\n"
//						+ "Plano de Processo gerado com sucesso!");

			}

			if (ProjectTools.isProjectToolsDone()) {
				

//				final ProgressBar pb = new ProgressBar(this);
//				System.out.println("DONE!");
//
//				pb.setVisible(true);
//				System.out.println("DONE1!");
//
//				final JanelaPrincipal parent = this;
//				System.out.println("DONE2!");

//				SwingWorker worker = new SwingWorker() {
					
//					@Override
//					protected Object doInBackground() throws Exception {
						
						MapeadoraDeWorkingsteps mapeadora = new MapeadoraDeWorkingsteps(
								this.getProjeto());
						System.out.println("DONE3!");

						this.atualizarArvoreCAPP();
						this.atualizarArvore();
						
						System.out.println("DONE4!");

						JanelaPrincipal.setDoneCAPP(true);
						this.textArea1.setText(this.textArea1.getText() + "\n"
								+ "Plano de Processo gerado com sucesso!");
//						return null;
//					}

//					@Override
//					protected void done() {
//						pb.dispose();
//						textArea1
//								.setText(textArea1.getText()
//										+ "\n As ferramentas foram carregadas com sucesso!");
//					}
//				};
//				worker.execute();

//				
//				ArrayList<ArrayList> allTools = this.getProjeto().getAllTools();
//
//				System.out.println("Ferramentas Selecionadas : " + this.getProjeto().getAllTools());
//
//				for (int i = 0; i < allTools.size(); i++) {
//
//					System.out.println("Size 1 = " + allTools.get(i).size());
//
//					for (int j = 0; j < allTools.get(i).size(); j++) {
//
//						System.out
//								.println("Size 2 = " + allTools.get(i).size());
//
//						Ferramenta fTmp = (Ferramenta) allTools.get(i).get(j);
//
//						System.out
//								.println("=========================================");
//						System.out.println("Nome = " + fTmp.getName());
//						System.out.println("Classe = " + fTmp.getClass());
//						System.out
//								.println("=========================================");
//					}
//
//				}

				
			}
		}
	}
	
	public void gerenciarOrdem(OrdemDeFabricacaoJDialog odfj) {
		if (odfj == null) {
			odfj = new OrdemDeFabricacaoJDialog(this);
			odfj.setVisible(true);
		} else
			odfj.dispose();
	}

	public void salvar() {
		FileDialog fd = new FileDialog(this, "Salvar", FileDialog.SAVE);
		this.projeto.setCappDone(isDoneCAPP());
		this.projeto.setToolsDone(ProjectTools.isProjectToolsDone());
		fd.setFile(this.projeto.getDadosDeProjeto().getProjectName());
		fd.setVisible(true);
		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file + ".GRM";
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, false));
			out.writeObject(this.projeto);
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

	public void salvarNoBD() throws IOException, ClassNotFoundException {
		
		ByteArrayOutputStream bout1 = new ByteArrayOutputStream();
		
		ObjectOutputStream out1 = new ObjectOutputStream(bout1);
		this.projeto.setToolsDone(ProjectTools.isProjectToolsDone());
		this.projeto.setCappDone(isDoneCAPP());
		out1.writeObject(this.projeto);
		out1.flush();
		out1.close();
		
		byte[] b1 = bout1.toByteArray();
		String s1 = write(b1);
		
		if (bancoDeDados.salvoNoBD(this.projeto)) {
			bancoDeDados.updateProjeto(this.projeto, s1);
		} else
			bancoDeDados.insertProjeto(this.projeto, s1);
		this.textArea1.setText(this.textArea1.getText() + "\n" + "'" + this.projeto.getDadosDeProjeto().getProjectName().toUpperCase() + "'" + " foi salvo no Banco de dados com sucesso!");

	}

	public void abrir() {
		FileDialog fd = new FileDialog(this, "Abrir", FileDialog.LOAD);

		fd.setVisible(true);

		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file;
		try {
			this.textArea1.setText(this.textArea1.getText() + "\nAbrindo " + filePath);

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));

			setDoneCAPP(false);
			ToolManager.setToolManagerDone(false);

			this.projeto = (Projeto) (in.readObject());
			this.setTitle("STEP Modeler - " + this.projeto.getDadosDeProjeto().getProjectName());
			in.close();
			this.faceVisualizada = (Face) this.projeto.getBloco().faces
					.elementAt(0);
			this.faceTrabalho = (Face) this.projeto.getBloco().faces
					.elementAt(0);
			this.desenhador.alterarProjeto(this.projeto);
			
			this.projeto.setConn(new ToolReader().getConn());
			this.projeto.setStatement(new ToolReader().getStatement());
			
			if(this.projeto.isToolsDone()){

				ProjectTools.setProjectToolsDone(true);
				ToolManager.setCenterDrills(this.projeto.getCenterDrills());
				ToolManager.setTwistDrills(this.projeto.getTwistDrills());
				ToolManager.setFaceMills(this.projeto.getFaceMills());
				ToolManager.setEndMills(this.projeto.getEndMills());
				ToolManager.setBallEndMills(this.projeto.getBallEndMills());
				ToolManager.setBullnoseEndMills(this.projeto.getBullnoseEndMills());
				ToolManager.setReamers(this.projeto.getReamers());
				ToolManager.setBoringTools(this.projeto.getBoringTools());

			}
			setDoneCAPP(this.projeto.isCappDone());
			this.atualizarArvore();
			this.atualizarArvoreCAPP();
			
			
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

	public void abrirPeloBD() {
		
		setDoneCAPP(false);
		ToolManager.setToolManagerDone(false);
		
		final JFrame Janela = new AbrirPeloBD(bancoDeDados, this);
		WindowListener x = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Janela.dispose();
			}
		};
		Janela.addWindowListener(x);
		Janela.setVisible(true);
	}

	public void openSTEPFile() 
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
			
			int idAtual = this.projeto.getDadosDeProjeto().getUserID();
			String userNameAtual = this.projeto.getDadosDeProjeto().getUserName();
			
			this.projeto = stepNcReader.getProjeto();
			
			this.projeto.getDadosDeProjeto().setUserID(idAtual);
			this.projeto.getDadosDeProjeto().setUserName(userNameAtual);
			
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
		this.desenhador.alterarProjeto(this.projeto);
		
		this.projeto.setConn(new ToolReader().getConn());
		this.projeto.setStatement(new ToolReader().getStatement());

		setDoneCAPP(false);

		ProjectTools.setProjectToolsDone(true);
		ToolManager.setCenterDrills(this.projeto.getCenterDrills());
		ToolManager.setTwistDrills(this.projeto.getTwistDrills());
		ToolManager.setFaceMills(this.projeto.getFaceMills());
		ToolManager.setEndMills(this.projeto.getEndMills());
		ToolManager.setBallEndMills(this.projeto.getBallEndMills());
		ToolManager.setBullnoseEndMills(this.projeto.getBullnoseEndMills());
		ToolManager.setReamers(this.projeto.getReamers());
		ToolManager.setBoringTools(this.projeto.getBoringTools());

		setDoneCAPP(true);
		
		this.atualizarArvore();
		this.atualizarArvoreCAPP();

	}
	
	public void abrirXML()
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
			
			this.textArea1.setText(this.textArea1.getText() + "\nAbrindo Arquivo XML " + filePath);
			deleteFile(new File("C:\\repositories.tmp"));
			
			stepNcReader = new STEP_NCReader(filePath, ProjectReader.FILE_XML);
			stepNcReader.getAllFeatures(stepNcReader.getAllWorkingSteps());
			
			int idAtual = this.projeto.getDadosDeProjeto().getUserID();
			String userNameAtual = this.projeto.getDadosDeProjeto().getUserName();
			
			this.projeto = stepNcReader.getProjeto();

			this.projeto.getDadosDeProjeto().setUserID(idAtual);
			this.projeto.getDadosDeProjeto().setUserName(userNameAtual);
			
		} catch (SdaiException e1) {
			e1.printStackTrace();
			this.textArea1.setText(this.textArea1.getText() + "\nerro ao abrir " + filePath);
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
		this.desenhador.alterarProjeto(this.projeto);
		
		this.projeto.setConn(new ToolReader().getConn());
		this.projeto.setStatement(new ToolReader().getStatement());

		setDoneCAPP(false);
		
		ProjectTools.setProjectToolsDone(true);
		ToolManager.setCenterDrills(this.projeto.getCenterDrills());
		ToolManager.setTwistDrills(this.projeto.getTwistDrills());
		ToolManager.setFaceMills(this.projeto.getFaceMills());
		ToolManager.setEndMills(this.projeto.getEndMills());
		ToolManager.setBallEndMills(this.projeto.getBallEndMills());
		ToolManager.setBullnoseEndMills(this.projeto.getBullnoseEndMills());
		ToolManager.setReamers(this.projeto.getReamers());
		ToolManager.setBoringTools(this.projeto.getBoringTools());

		setDoneCAPP(true);

		this.atualizarArvore();
		this.atualizarArvoreCAPP();

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
	
	
	
	
	/**
	 * ********************** CRIA FEATURES *********************************
	 * 
	 */

	public void criarFuro() {
		CriarFuroBasePlana cf = new CriarFuroBasePlana(this, faceTrabalho);
		cf.setVisible(true);
		// System.out.println("CRIAR FURO");
		// this.vistas.desenhadorDeFaces.repaint();
	}

	public void criarDegrau() {
		CriarDegrau cd = new CriarDegrau(this, faceTrabalho);
		cd.setVisible(true);
	}

	public void criarRanhura() {
		CriarRanhura cr = new CriarRanhura(this, faceTrabalho);
		cr.setVisible(true);
	}

	public void criarCavidade() {
		CriarCavidade cc = new CriarCavidade(this, faceTrabalho);
		cc.setVisible(true);
	}

	public void criarCavidadeFundoArredondado()
	{
		CriarCavidadeFundoArredondado c = new CriarCavidadeFundoArredondado(this, faceTrabalho);
		c.setVisible(true);
	}
	public void criarFuroBaseConica()
	{
		CriarFuroBaseConica fbc = new CriarFuroBaseConica(this, faceTrabalho);
		fbc.setVisible(true);
	}
	public void criarFuroBaseEsferica()
	{
		CriarFuroBaseEsferica fbe = new CriarFuroBaseEsferica(this, faceTrabalho);
		fbe.setVisible(true);
	}
	public void criarFuroBaseArredondada()
	{
		CriarFuroBaseArredondada fba = new CriarFuroBaseArredondada(this, faceTrabalho);
		fba.setVisible(true);
	}
	public void criarFuroConico()
	{
		CriarFuroConico cfc = new CriarFuroConico(this, faceTrabalho);
		cfc.setVisible(true);
	}
	public void criarRanhuraPerfilU()
	{
		CriarRanhuraPerfilUQuadrado cfc = new CriarRanhuraPerfilUQuadrado(this, faceTrabalho);
		cfc.setVisible(true);
	}
	public void criarRanhuraPerfilCircular()
	{
		CriarRanhuraPerfilCircular crpc = new CriarRanhuraPerfilCircular(this, faceTrabalho);
		crpc.setVisible(true);
	}
	public void criarRanhuraPerfilRoundedU()
	{
		CriarRanhuraPerfilRoundedU crpru = new CriarRanhuraPerfilRoundedU(this, faceTrabalho);
		crpru.setVisible(true);
	}
	public void criarRanhuraPerfilVee()
	{
		CriarRanhuraPerfilVee crpv = new CriarRanhuraPerfilVee(this, faceTrabalho);
		crpv.setVisible(true);
	}
	public void criarRanhuraPerfilBezier()
	{
		CriarRanhuraPerfilBezier crpb = new CriarRanhuraPerfilBezier(this, faceTrabalho);
		crpb.setVisible(true);
	}
	public void criarRegion()
	{
		CreateRegion cr = new CreateRegion(this, this.projeto, this.faceTrabalho);
		cr.setVisible(true);
	}
	
	public void criarCavidadePerfilGeral() 
	{
		CreateGeneralPocket cgp = new CreateGeneralPocket(this, this.projeto, faceTrabalho);
		cgp.setVisible(true);
	}
	public void mostrarReferencia() {
		PlanosRef PR = new PlanosRef(this);
		PR.setVisible(true);
	}

	/**
	 * #########################################################################
	 * Altera a face de trabalho!
	 * ###############################################
	 * ###########################
	 * 
	 * @param pos
	 */
	public void trocarFaceTrabalho(int pos) {
		this.faceTrabalho = (Face) this.projeto.getBloco().faces.elementAt(pos);
		String text = this.textArea1.getText();
		switch (pos) {
		case 0:

			this.vistaPlanoXY.setSelected(true);
			this.vistaPlanoXY.doClick();
			this.label5.setText("XY");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t XY");
			break;

		case 1:

			this.vistaPlanoYZ.setSelected(true);
			this.vistaPlanoYZ.doClick();
			this.label5.setText("YZ");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t YZ");
			break;

		case 2:

			this.vistaPlanoXZ.setSelected(true);
			this.vistaPlanoXZ.doClick();
			this.label5.setText("XZ");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t XZ");

			break;

		case 3:

			this.vistaPlanoYX.setSelected(true);
			this.vistaPlanoYX.doClick();
			this.label5.setText("YX");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t YX");

			break;

		case 4:

			this.vistaPlanoZY.setSelected(true);
			this.vistaPlanoZY.doClick();
			this.label5.setText("ZY");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t ZY");

			break;

		case 5:

			this.vistaPlanoZX.setSelected(true);
			this.vistaPlanoZX.doClick();
			this.label5.setText("ZX");
			this.textArea1.setText(text + "\n Criando Features na face: " + "\t ZX");

			break;

		default:
			break;
		}
	}

	/**
	 * *************************** zoom **********************************
	 * 
	 * @return
	 */
//	public void itemStateChanged(ItemEvent evt) {
//		int index = this.zoomComboBox.getSelectedIndex();
//		desenhador.setZoom((25 + index * 25) / 100);
//		desenhador.repaint();
//	}

	public double getZoom() {
		double zoom;
		try {
			zoom = ((Double)spinnerZoom.getValue()).doubleValue() / 100;
			if (zoom > 0) {
				return zoom;
			} else {
				JOptionPane.showMessageDialog(null,
						"Digite um número inteiro positivo ",
						"Erro em campo ZOOM", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite um Número válido",
					"Erro no campo ZOOM", JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}
	}

	/**
	 * *************************************************************************
	 * *
	 */
	public void about()
	{
		About about = new About(this);
		about.setVisible(true);
	}

	public void help() {
		try {
			Runtime
					.getRuntime()
					.exec(
							"cmd.exe /C start iexplore.exe http://grima.ufsc.br/stepnc_project/ajuda.php");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openSTEPWindow() throws HeadlessException, SdaiException,
			MissingDataException, InterruptedException {
		
		if (!isDoneCAPP()) {

			int i = JOptionPane
					.showConfirmDialog(
							null,
							"Voce deve criar primeiro um plano de processo, deseja criá-lo agora?",
							"Warning!", JOptionPane.OK_CANCEL_OPTION);
			if (i == JOptionPane.OK_OPTION) {

				this.gerarCAPP();

			}

		}else{
			
			try {

				stepNc = new StepNcProject(this.projeto.getWorkingsteps() , this.projeto);
				
				JanelaCodigoStep janelaArqFisico = new JanelaCodigoStep(stepNc);
//				janelaArqFisico.editTextArea(HeaderUtil.changeHeader(stepNc.createSTEP21File(), "GRIMA"));
				janelaArqFisico.setVisible(true);
				//FLAG
			} catch (SdaiException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public CAPP getCapp()
	{
		return capp;
	}
	public void setCapp(CAPP capp) 
	{
		this.capp = capp;
	}
	public static boolean isDoneCAPP() 
	{
		return doneCAPP;
	}
	public static void setDoneCAPP(boolean doneCAPP) 
	{
		JanelaPrincipal.doneCAPP = doneCAPP;
	}
	public Projeto getProjeto() 
	{
		return projeto;
	}
	public void setProjeto(Projeto projeto)
	{
		this.projeto = projeto;
	}
	public void gerenciarFerramentas() 
	{
		new ProjectTools(this);			
	}
	public void definirPontosApoio()
	{
		// chamar a sua janela
//		
//		PontosDeApoio pDeApoioFrame = new PontosDeApoio(this, this.projeto);
//		
//		pDeApoioFrame.setVisible(true);
		PontosDeApoio2 pDeApoioFrame = new PontosDeApoio2(this, this.projeto);
		pDeApoioFrame.setVisible(true);
	}

	public void removerWS() {

		boolean ok = false;
		
		DefaultMutableTreeNode nodeVo = null, nodePai = null, nodeFilho = null;
		
			nodeFilho = (DefaultMutableTreeNode) workingstepsTree.getLastSelectedPathComponent();
				
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if (nodePai != null)
				nodeVo = (DefaultMutableTreeNode) nodePai.getParent();
			if(nodePai != null)
				ok = true;
		}
		if (ok) {

			int tipoWs = nodeVo.getIndex(nodePai) + 1; // 1 = RGH , 2 = FNS
			
			int numWsRGH = nodeVo.getChildAt(0).getChildCount(); // contagem de RGHs
		
			
			Object objVo = nodeVo.getUserObject();
//			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringVo = (String) objVo;
//			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringVo.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

//			int indexWS = nodePai.getIndex(nodeFilho);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
//			if(tipoWs == Workingstep.ACABAMENTO)
//				indexWS =  indexWS + numWsRGH;

		if (indexFace != -1) {
				
				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				Workingstep wsTmp = workingstepsFace.get(indexWS);
				
				Vector<Workingstep> wsAssociadas = new Vector<Workingstep>();
				
				wsAssociadas = wsTmp.identificarPosCedente(workingstepsFace, wsAssociadas);
				
				if(wsTmp.getOperation().getClass()==CenterDrilling.class){
					
					int i = JOptionPane
					.showConfirmDialog(
							null,
							"A operação CenterDrilling é recomendável nesse caso.\n" +
							"Deseja eliminá-la mesmo assim?",
							"Remover CenterDrilling",
							JOptionPane.OK_CANCEL_OPTION);


					if(i==JOptionPane.OK_OPTION){
						
						Workingstep wsPrecedente = wsTmp.getWorkingstepPrecedente();
						Workingstep wsPoscedente = null;
						
						for(int j = 0; j<workingstepsFace.size(); j++){
							
							Workingstep wsTmpp = workingstepsFace.get(j);
							
							if(wsTmpp.getWorkingstepPrecedente()!= null){
							
								if(wsTmpp.getWorkingstepPrecedente().equals(wsTmp)){
								
									wsPoscedente = wsTmpp;
								
								}
							}
						}
						
						wsPoscedente.setWorkingstepPrecedente(wsPrecedente);
						
						Feature feature = wsTmp.getFeature();

						feature.getWorkingsteps().remove(wsTmp);

						workingstepsFace.remove(wsTmp);
						
						this.atualizarArvoreCAPP();
						this.atualizarArvore();
					}
					
				}else{
				
					String wsAss = "";
					
					for(int j = 0;j<wsAssociadas.size();j++){
						wsAss = wsAss+ "\n" + wsAssociadas.get(j).getId();
					}
					
					if(!wsAss.equals("")){
						
						int i = JOptionPane.
						showConfirmDialog(
								null,
								"A eliminação deste Machining Workingstep causará a remoção\n" +
								"dos seguintes Machining Workinsteps associados:\n" +
								wsAss + "\n"							
								+ "\nDeseja eliminá-los?",
								"Remover Machining Workingstep",
								JOptionPane.OK_CANCEL_OPTION);

						if(i==JOptionPane.OK_OPTION){
						
							for(int j=0;j<wsAssociadas.size();j++){
								
								Feature feature = wsAssociadas.get(j).getFeature();
								
								feature.getWorkingsteps().remove(wsAssociadas.get(j));
								
								workingstepsFace.remove(wsAssociadas.get(j));
							
								
							}
							
							Feature feature = wsTmp.getFeature();
							
							feature.getWorkingsteps().remove(wsTmp);
							
							workingstepsFace.remove(wsTmp);
							
							this.atualizarArvoreCAPP();
							this.atualizarArvore();
							
						}

					}else{
						
						int i = JOptionPane.
						showConfirmDialog(
								null,
								"Deseja eliminar este Machining Workingstep?",
								"Remover Machining Workingstep",
								JOptionPane.OK_CANCEL_OPTION);

						if(i==JOptionPane.OK_OPTION){
							wsTmp.removePosCedente(workingstepsFace);
							
							this.atualizarArvoreCAPP();
							this.atualizarArvore();
						}
					}
					
				}
				
//				this.atualizarArvoreCAPP();
//				this.atualizarArvore();

			}

		}
	}
	
	public void removerWS2() {

		boolean ok = false;
		
		DefaultMutableTreeNode nodePai = null, nodeFilho = null;
		
		nodeFilho = (DefaultMutableTreeNode) workplanTree.getLastSelectedPathComponent();
		
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if(nodePai != null)
				ok = true;
		}
		if (ok) {

			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringPai.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
			if (indexFace != -1) {
				
				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				Workingstep wsTmp = workingstepsFace.get(indexWS);
				
				Vector<Workingstep> wsAssociadas = new Vector<Workingstep>();
				
				wsAssociadas = wsTmp.identificarPosCedente(workingstepsFace, wsAssociadas);
				
				if(wsTmp.getOperation().getClass()==CenterDrilling.class){
					
					int i = JOptionPane
					.showConfirmDialog(
							null,
							"A operação CenterDrilling é recomendável nesse caso.\n" +
							"Deseja eliminá-la mesmo assim?",
							"Remover CenterDrilling",
							JOptionPane.OK_CANCEL_OPTION);


					if(i==JOptionPane.OK_OPTION){
						
						Workingstep wsPrecedente = wsTmp.getWorkingstepPrecedente();
						Workingstep wsPoscedente = null;
						
						for(int j = 0; j<workingstepsFace.size(); j++){
							
							Workingstep wsTmpp = workingstepsFace.get(j);
							
							if(wsTmpp.getWorkingstepPrecedente()!= null){
							
								if(wsTmpp.getWorkingstepPrecedente().equals(wsTmp)){
								
									wsPoscedente = wsTmpp;
								
								}
							}
						}
						
						wsPoscedente.setWorkingstepPrecedente(wsPrecedente);
						
						Feature feature = wsTmp.getFeature();

						feature.getWorkingsteps().remove(wsTmp);

						workingstepsFace.remove(wsTmp);
						
						this.atualizarArvoreCAPP();
						this.atualizarArvore();
					}
					
				}else{
				
					String wsAss = "";
					
					for(int j = 0;j<wsAssociadas.size();j++){
						wsAss = wsAss+ "\n" + wsAssociadas.get(j).getId();
					}
					
					if(!wsAss.equals("")){
						
						int i = JOptionPane.
						showConfirmDialog(
								null,
								"A eliminação deste Machining Workingstep causará a remoção\n" +
								"dos seguintes Machining Workinsteps associados:\n" +
								wsAss + "\n"							
								+ "\nDeseja eliminá-los?",
								"Remover Machining Workingstep",
								JOptionPane.OK_CANCEL_OPTION);

						if(i==JOptionPane.OK_OPTION){
						
							for(int j=0;j<wsAssociadas.size();j++){
								
								Feature feature = wsAssociadas.get(j).getFeature();
								
								feature.getWorkingsteps().remove(wsAssociadas.get(j));
								
								workingstepsFace.remove(wsAssociadas.get(j));
							
								
							}
							
							Feature feature = wsTmp.getFeature();
							
							feature.getWorkingsteps().remove(wsTmp);
							
							workingstepsFace.remove(wsTmp);
							
							this.atualizarArvoreCAPP();
							this.atualizarArvore();
							
						}

					}else{
						
						int i = JOptionPane.
						showConfirmDialog(
								null,
								"Deseja eliminar este Machining Workingstep?",
								"Remover Machining Workingstep",
								JOptionPane.OK_CANCEL_OPTION);

						if(i==JOptionPane.OK_OPTION){
							wsTmp.removePosCedente(workingstepsFace);
						
							this.atualizarArvoreCAPP();
							this.atualizarArvore();
						}
					}
					
				}

//				this.atualizarArvoreCAPP();
//				this.atualizarArvore();

			}

		}
	}
	
	public void editarWS() {

		boolean ok = false;

		DefaultMutableTreeNode nodeVo = null, nodePai = null, nodeFilho = null;

			nodeFilho = (DefaultMutableTreeNode) workingstepsTree.getLastSelectedPathComponent();
		
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if (nodePai != null)
				nodeVo = (DefaultMutableTreeNode) nodePai.getParent();
			if(nodePai != null)
				ok = true;
		}
		if (ok) {

			Object objVo = nodeVo.getUserObject();
//			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringVo = (String) objVo;
//			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringVo.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
			if (indexFace != -1) {

				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				Workingstep wsTmp = workingstepsFace.get(indexWS);
				
				Class ferrClass = wsTmp.getFerramenta().getClass();
				
				if(ferrClass == CenterDrill.class){
					
					new EditCenterDrillWS(this, wsTmp);
					
				}else if(ferrClass == TwistDrill.class) {
					
					new EditTwistDrillWS(this, wsTmp);
					
				}else if(ferrClass == FaceMill.class){
					
					new EditFaceMillWS(this, wsTmp);
					
				}else if(ferrClass == EndMill.class){
					
					new EditEndMillWS(this, wsTmp);
					
				}else if(ferrClass == BallEndMill.class){
					
					new EditBallEndMillWS(this, wsTmp);
					
				}else if(ferrClass == BullnoseEndMill.class){
					
					new EditBullnoseEndMillWS(this, wsTmp);
					
				}else if(ferrClass == Reamer.class){
					
					new EditReamerWS(this, wsTmp);
					
				}else if(ferrClass == BoringTool.class){
					
					new EditBoringToolWS(this, wsTmp);
					
				}

			}

		}
	}
	
	public void editarWS2() {

		boolean ok = false;

		DefaultMutableTreeNode nodePai = null, nodeFilho = null;

			nodeFilho = (DefaultMutableTreeNode) workplanTree.getLastSelectedPathComponent();
		
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if (nodePai != null)
				ok = true;
		}
		if (ok) {

			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringPai.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
			if (indexFace != -1) {

				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				Workingstep wsTmp = workingstepsFace.get(indexWS);
				
				Class ferrClass = wsTmp.getFerramenta().getClass();
				
				if(ferrClass == CenterDrill.class){
					
					new EditCenterDrillWS(this, wsTmp);
					
				}else if(ferrClass == TwistDrill.class) {
					
					new EditTwistDrillWS(this, wsTmp);
					
				}else if(ferrClass == FaceMill.class){
					
					new EditFaceMillWS(this, wsTmp);
					
				}else if(ferrClass == EndMill.class){
					
					new EditEndMillWS(this, wsTmp);
					
				}else if(ferrClass == BallEndMill.class){
					
					new EditBallEndMillWS(this, wsTmp);
					
				}else if(ferrClass == BullnoseEndMill.class){
					
					new EditBullnoseEndMillWS(this, wsTmp);
					
				}else if(ferrClass == Reamer.class){
					
					new EditReamerWS(this, wsTmp);
					
				}else if(ferrClass == BoringTool.class){
					
					new EditBoringToolWS(this, wsTmp);
					
				}

			}

		}
	}
	
	public void alterarSequencia() {

		
		boolean ok = false;

		DefaultMutableTreeNode nodeVo = null, nodePai = null, nodeFilho = null;

			nodeFilho = (DefaultMutableTreeNode) workingstepsTree.getLastSelectedPathComponent();
		
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if (nodePai != null)
				nodeVo = (DefaultMutableTreeNode) nodePai.getParent();
			if(nodePai != null)
				ok = true;
		}
		if (ok) {

			Object objVo = nodeVo.getUserObject();
//			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringVo = (String) objVo;
//			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringVo.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
			if (indexFace != -1) {

				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				new AlterarPosicaoWs (this, workingstepsFace, indexWS);
				
			}
		}
		
	}
	
	public void alterarSequencia2() {

		
		boolean ok = false;

		DefaultMutableTreeNode nodePai = null, nodeFilho = null;

		nodeFilho = (DefaultMutableTreeNode) workplanTree.getLastSelectedPathComponent();
		
		if (nodeFilho != null) {
			nodePai = (DefaultMutableTreeNode) nodeFilho.getParent();
			if (nodePai != null)
				ok = true;
		}
		if (ok) {

			Object objPai = nodePai.getUserObject();
			Object objFilho = nodeFilho.getUserObject();

			String stringPai = (String) objPai;
			String stringFilho = (String) objFilho;

			String strFace = stringPai.substring(8, 10);

			int indexFace = Face.getIndiceFace(strFace);

			String indexWs = stringFilho.substring(5, 8);
			
			indexWs = indexWs.trim();

			int indexWS = Integer.parseInt(indexWs);
			
			if (indexFace != -1) {

				Vector<Workingstep> workingstepsFace = this.projeto.getWorkingsteps().get(indexFace);
				
				new AlterarPosicaoWs (this, workingstepsFace, indexWS);
				
			}
		}
		
		
	}
	
	public void criarBoss(){
		// descobrir se clicou numa cavidade
		boolean ok = false;
		DefaultMutableTreeNode node1 = null, node2 = null;
		node1 = (DefaultMutableTreeNode) tree2.getLastSelectedPathComponent();
		if (node1 != null) {
			node2 = (DefaultMutableTreeNode) node1.getParent();
			if (node2 != null)
				ok = true;
		}
		if (ok) {
			Object objPai = node1.getUserObject();
			Object objFilho = node2.getUserObject();

			String stringPai = (String) objFilho;
			String stringFilho = (String) objPai;
			
			Face face = this.getFace(stringPai);
			if (face != null) {
				int pos = getPosicaoFeature(stringFilho, face);
				Feature feature = (Feature)face.features.elementAt(pos);
				if(feature.getClass() == Cavidade.class)
				{
//					System.out.println("CAVIDADEEEEEE");
					CriarBoss cb = new CriarBoss(this, this.projeto, face, feature); //criar no .cad
					cb.setVisible(true);
//				} else if (feature.getClass() == Degrau.class)
//				{
//					
				} else if (feature.getClass() == GeneralClosedPocket.class)
				{
					CriarBoss cb = new CriarBoss(this, this.projeto, face, feature);
					cb.setVisible(true);			
				}
						else
				{
					JOptionPane.showConfirmDialog(null, "Nao pode criar Boss nesta feature");
				}
			}
		}
		
		
	}
	public AcessaBD getBancoDeDados() {
		return bancoDeDados;
	}
	public void setBancoDeDados(AcessaBD bancoDeDados) {
		this.bancoDeDados = bancoDeDados;
	}
	
}

class JanelaPrincipal_button_actionAdapter implements ActionListener {
	JanelaPrincipal parent;

	public JanelaPrincipal_button_actionAdapter(JanelaPrincipal parent) {
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent event) {
		Object origem = event.getSource();
		if (origem == this.parent.zoomMais) {
			this.parent.aumentarZoom();
		} else if (origem == this.parent.zoomMenos) {
			this.parent.diminuirZoom();
		} else if (origem == this.parent.girarEixoX) {
			this.parent.girar(0);
		} else if (origem == this.parent.girarEixoY) {
			this.parent.girar(1);
		} else if (origem == this.parent.girar2) {
			this.parent.girar(2);
		} else if (origem == this.parent.buttonEditar) {
			this.parent.editarFeature();
		} else if (origem == this.parent.buttonRemover) {
			this.parent.removerFeature();
		} else if (origem == this.parent.buttonAbrir){
			this.parent.abrir();
		} else if (origem == this.parent.buttonNovo) {
			this.parent.novoProjeto();
		} else if (origem == this.parent.buttonSalvar) {
			this.parent.salvar();
		} else if (origem == this.parent.buttonRemoverWS) {
			this.parent.removerWS();
		}else if (origem == this.parent.buttonEditarWS) {
			this.parent.editarWS();
		}else if (origem == this.parent.buttonAlterarWorkplan) {
			this.parent.alterarSequencia();
		} else if (origem == this.parent.buttonRemoverWS2) {
			this.parent.removerWS2();
		}else if (origem == this.parent.buttonEditarWS2) {
			this.parent.editarWS2();
		}else if (origem == this.parent.buttonAlterarWorkplan2) {
			this.parent.alterarSequencia2();
		}else if(origem == this.parent.buttonBoss){
			this.parent.criarBoss();
		}
}
}

class JanelaPrincipal_menus_actionAdapter implements ActionListener {
	
	private JanelaPrincipal parent;
	private Projeto projeto;
	private AcessaBD bancoDeDados;
	
	public JanelaPrincipal_menus_actionAdapter(JanelaPrincipal parent,
			Projeto projeto, AcessaBD bancoDeDados) {
		this.parent = parent;
		this.projeto = projeto;
		this.bancoDeDados = bancoDeDados;
	}
	
	
	class PerguntaSalvarNoBD extends JFrame implements ActionListener {
		JLabel L1;
		JButton B2, B3, B4;
		JPanel P1;
		private AcessaBD bancoDeDados;
		private Projeto projeto;

		
		
		PerguntaSalvarNoBD(AcessaBD bancoDeDados, Projeto projeto) {
			this.projeto = projeto;
			this.bancoDeDados = bancoDeDados;
			this.setIconImage(new ImageIcon(getClass().getResource("/images/document-save16.png")).getImage());
			setTitle("Dejesa salvar projeto antes de sair?");
			setResizable(false);
			getContentPane().setLayout(new FlowLayout());
			L1 = new JLabel("Dejesa salvar o projeto no servidor?");
			L1.setForeground(Color.black);
			B2 = new JButton("Salvar");
			B3 = new JButton("Não salvar");
			B4 = new JButton("Cancelar");
			B2.addActionListener(this);
			B3.addActionListener(this);
			B4.addActionListener(this);
			P1 = new JPanel();
			P1.setLayout(new FlowLayout());

			getContentPane().add(L1);

			getContentPane().add(B2);
			getContentPane().add(B3);
			this.getContentPane().add(B4);
			getContentPane().add(P1);
			setLocation(200, 200);
			pack();
			parent.deleteFile(new File("C:\\repositories.tmp"));
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == B3) // nao salvar
			{
				parent.dispose();
				dispose();
			}

			if (e.getSource() == B2) // salvar
			{
				try {
					parent.salvarNoBD();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				parent.dispose();
				dispose();

			} else if(e.getSource() == B4)
			{
				this.dispose();
			}
		}

	}


	public static String write(byte[] barney) throws IOException {

		String lyly = "", sinal = "";
		for (int i = 0; i < barney.length; i++) {
			if (barney[i] < 0) {
				sinal = "-";
				if (barney[i] == (byte) -128) {
					sinal = "";
				}
				barney[i] = (byte) (barney[i] * (-1));
			}

			else
				sinal = "+";
			if (barney[i] < 10 && barney[i] > -10) {
				lyly = lyly + sinal + "00" + barney[i];
			} else if (barney[i] < 100 && barney[i] > -100) {
				lyly = lyly + sinal + "0" + barney[i];
			} else
				lyly = lyly + sinal + barney[i];
		}

		return lyly;
	}


	public void actionPerformed(ActionEvent event) {
		Object origem = event.getSource();
		if (origem == this.parent.menuItemAbout) {
			this.parent.about();
		} else if (origem == this.parent.menuItemHelp) {
			this.parent.help();
		} else if (origem == this.parent.menuItemAbrirSTEP) {
			this.parent.openSTEPFile();
		} else if (origem == this.parent.menuItemGerar) {
			try {
				this.parent.openSTEPWindow();
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (SdaiException e) {
				e.printStackTrace();
			} catch (MissingDataException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (origem == this.parent.menuItemCriarFuro) {
			this.parent.criarFuro();
		} else if (origem == this.parent.menuItemCriarDegrau) {
			this.parent.criarDegrau();
		} else if (origem == this.parent.menuItemCriarRanhura) {
			this.parent.criarRanhura();
		} else if (origem == this.parent.menuItemCriarCavidade) {
			this.parent.criarCavidade();
		} else if (origem == this.parent.menuItemCriarCavidadeFun)
		{
			this.parent.criarCavidadeFundoArredondado();
		} else if (origem == this.parent.menuItemCriarFuroBaseConica)
		{
			this.parent.criarFuroBaseConica();
		} else if (origem == this.parent.menuItemCriarFuroBaseEsferica)
		{
			this.parent.criarFuroBaseEsferica();
		} else if (origem == this.parent.menuItemCriarFuroBaseArredondada)
		{
			this.parent.criarFuroBaseArredondada();
		} else if (origem == this.parent.menuItemCriarFuroConico)
		{
			this.parent.criarFuroConico();
		} else if (origem == this.parent.menuItemCriarRanhuraUPerfil)
		{
			this.parent.criarRanhuraPerfilU();
		} else if (origem == this.parent.menuItemCriarRanhuraCircularPerfil)
		{
			this.parent.criarRanhuraPerfilCircular();
		}  else if (origem == this.parent.menuItemCriarRanhuraPerfilRoundedU)
		{
			this.parent.criarRanhuraPerfilRoundedU();
		} else if (origem == this.parent.menuItemCriarRanhuraPerfilVee)
		{
			this.parent.criarRanhuraPerfilVee();
		} else if (origem == this.parent.menuItemCriarRanhuraPerfilBezier)
		{
			this.parent.criarRanhuraPerfilBezier();
		}
		else if (origem == this.parent.menuItemSair) {
			// deseja salvar?
			JFrame Janela1 = new PerguntaSalvarNoBD(bancoDeDados, projeto);
			WindowListener x2 = new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
//					try {
//						ProjectManager.getProject().closeSession();
//					} catch (SdaiException e1) {
//						e1.printStackTrace();
//					} catch (InterruptedException e1) {
//					}
					parent.dispose();
				}
			};
			Janela1.addWindowListener(x2);
			Janela1.setVisible(true);

		} else if (origem == this.parent.menuItemVerPlanosReferencia) {
			this.parent.mostrarReferencia();
		} else if (origem == this.parent.menuItemAbrir) {
			this.parent.abrir();
		} else if (origem == this.parent.menuItemAbrirBD) {
			this.parent.abrirPeloBD();
		} else if (origem == this.parent.fixarFaceXY) {
			this.parent.trocarFaceTrabalho(Face.XY);
		} else if (origem == this.parent.fixarFaceYZ) {	
				this.parent.trocarFaceTrabalho(/* 1 */Face.YZ);
		} else if (origem == this.parent.fixarFaceXZ) {
				this.parent.trocarFaceTrabalho(/* 2 */Face.XZ);
		} else if (origem == this.parent.fixarFaceYX)
				this.parent.trocarFaceTrabalho(/* 3 */Face.YX);
		else if (origem == this.parent.fixarFaceZY)
			this.parent.trocarFaceTrabalho(/* 4 */Face.ZY);
		else if (origem == this.parent.fixarFaceZX)
			this.parent.trocarFaceTrabalho(/* 5 */Face.ZX);
		else if (origem == this.parent.menuItemSalvar) {
			this.parent.salvar();
		} else if (origem == this.parent.menuItemSalvarBD) {
			try {
				this.parent.salvarNoBD();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (origem == this.parent.menuItemOrdemFabricacao) {
			this.parent.gerenciarOrdem(null);
		} else if (origem == this.parent.gerarCAPP) {
			this.parent.gerarCAPP();
		} else if (origem == this.parent.gerar3D) {
			this.parent.gerar3D();
		} else if (origem == this.parent.vistaPlanoXY) {
			this.parent.trocarFaceVisualizada(0, 0);
			this.parent.label3.setText("XY");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t XY");
		} else if (origem == this.parent.vistaPlanoYZ) {
			this.parent.trocarFaceVisualizada(1, 0);
			this.parent.label3.setText("YZ");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t YZ");
		} else if (origem == this.parent.vistaPlanoXZ) {
			this.parent.trocarFaceVisualizada(2, 0);
			this.parent.label3.setText("XZ");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t XZ");
		} else if (origem == this.parent.vistaPlanoYX) {
			this.parent.trocarFaceVisualizada(3, 0);
			this.parent.label3.setText("YX");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t YX");
		} else if (origem == this.parent.vistaPlanoZY) {
			this.parent.trocarFaceVisualizada(4, 0);
			this.parent.label3.setText("ZY");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t ZY");
		} else if (origem == this.parent.vistaPlanoZX) {
			this.parent.trocarFaceVisualizada(5, 0);
			this.parent.label3.setText("ZX");
			String text = this.parent.textArea1.getText();
			this.parent.textArea1.setText(text + "\n face visualizada: " + "\t ZX");
		} else if (origem == this.parent.mostrarPecaBruta) {
			this.parent.pecaBruta();
		} else if (origem == this.parent.verEixos) {
			this.parent.verEixos();
		} else if (origem == this.parent.vistaPlanosPrincipais) {
			this.parent.mostrarPlanosPrincipais();
		} else if (origem == this.parent.mostrarMesa) {
			this.parent.mostrarMesa();
		} else if (origem == this.parent.menuGirar	|| origem == this.parent.girar2) {
			this.parent.girar(2);
		} else if (origem == this.parent.menuItemNovoProjeto) {
			this.parent.novoProjeto();
		} else if (origem == this.parent.menuItemImprimir) {
			this.parent.imprimirTela();
		} else if (origem == this.parent.mostrarFeaturesSecundarias) {
			this.parent.mostrarFeaturesSecundarias();
		} else if (origem == this.parent.menuItemGerFerr) {
			this.parent.gerenciarFerramentas();
		} else if(origem == this.parent.definirPontosApoio)
		{
			this.parent.definirPontosApoio();
		} else if(origem == this.parent.menuItemAbrirXML)
		{
			this.parent.abrirXML();
		} else if(origem == this.parent.criarRegion)
		{
			this.parent.criarRegion();
		} else if(origem == this.parent.menuItemCriarCavidadeGeneral)
		{
			this.parent.criarCavidadePerfilGeral();
		}
		
	}
}

class JanelaPrincipal_combobox_actionAdapter implements ItemListener {
	JanelaPrincipal parent;

	public JanelaPrincipal_combobox_actionAdapter(JanelaPrincipal parent) {
		this.parent = parent;
	}

	public void itemStateChanged(ItemEvent event) {
	}
}

class JanelaPrincipal_arvore_actionAdapter implements TreeSelectionListener {
	JanelaPrincipal parent;

	public JanelaPrincipal_arvore_actionAdapter(JanelaPrincipal parent) {
		this.parent = parent;
	}

	public void valueChanged(TreeSelectionEvent event) {
	}
}
