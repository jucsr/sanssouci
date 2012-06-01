/*
 * Created by JFormDesigner on Mon Dec 27 19:42:14 BRST 2010
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import br.UFSC.GRIMA.cad.DesenhadorDeFaces;

/**
 * @author Victor
 */
public class JanelaPrincipalFrame extends JFrame {
	public JanelaPrincipalFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menuArquivo = new JMenu();
		menuItemNovoProjeto = new JMenuItem();
		menuItemAbrir = new JMenuItem();
		menuItemAbrirBD = new JMenuItem();
		menuItemSalvar = new JMenuItem();
		menuItemSalvarBD = new JMenuItem();
		menuItemImprimir = new JMenuItem();
		menuItemOrdemFabricacao = new JMenuItem();
		menuItemSair = new JMenuItem();
		menuFerramentas = new JMenu();
		menuCriarFeatureEm = new JMenu();
		fixarFaceXY = new JRadioButtonMenuItem();
		fixarFaceYZ = new JRadioButtonMenuItem();
		fixarFaceXZ = new JRadioButtonMenuItem();
		fixarFaceYX = new JRadioButtonMenuItem();
		fixarFaceZY = new JRadioButtonMenuItem();
		fixarFaceZX = new JRadioButtonMenuItem();
		menuCriarFeatures = new JMenu();
		menu10 = new JMenu();
		menuItemCriarFuro = new JMenuItem();
		menuItemCriarFuroBaseConica = new JMenuItem();
		menuItemCriarFuroBaseEsferica = new JMenuItem();
		menuItemCriarFuroBaseArredondada = new JMenuItem();
		menuItemCriarFuroConico = new JMenuItem();
		menu11 = new JMenu();
		menuItemCriarRanhura = new JMenuItem();
		menuItemCriarRanhuraUPerfil = new JMenuItem();
		menuItemCriarRanhuraCircularPerfil = new JMenuItem();
		menuItemCriarRanhuraPerfilRoundedU = new JMenuItem();
		menuItemCriarRanhuraPerfilVee = new JMenuItem();
		menuItemCriarRanhuraPerfilBezier = new JMenuItem();
		menu12 = new JMenu();
		menuItemCriarDegrau = new JMenuItem();
		menu13 = new JMenu();
		menuItemCriarCavidade = new JMenuItem();
		menuItemCriarCavidadeFun = new JMenuItem();
		criarRegion = new JMenuItem();
		menuItemRemover = new JMenuItem();
		menuItemEditar = new JMenuItem();
		menuGirar = new JMenuItem();
		menu3 = new JMenu();
		menu9 = new JMenu();
		vistaPlanoXY = new JRadioButtonMenuItem();
		vistaPlanoYZ = new JRadioButtonMenuItem();
		vistaPlanoXZ = new JRadioButtonMenuItem();
		vistaPlanoYX = new JRadioButtonMenuItem();
		vistaPlanoZY = new JRadioButtonMenuItem();
		vistaPlanoZX = new JRadioButtonMenuItem();
		vistaPlanosPrincipais = new JCheckBoxMenuItem();
		mostrarFeaturesSecundarias = new JCheckBoxMenuItem();
		mostrarPecaBruta = new JCheckBoxMenuItem();
		mostrarMesa = new JCheckBoxMenuItem();
		verEixos = new JCheckBoxMenuItem();
		menuItemVerPlanosReferencia = new JMenuItem();
		gerar3D = new JMenuItem();
		menu1 = new JMenu();
		menuItemGerFerr = new JMenuItem();
		gerarCAPP = new JMenuItem();
		definirPontosApoio = new JMenuItem();
		menuSTEP = new JMenu();
		menuItemGerar = new JMenuItem();
		menuItemAbrirSTEP = new JMenuItem();
		menuItemAbrirXML = new JMenuItem();
		menuAjuda = new JMenu();
		menuItemAbout = new JMenuItem();
		menuItemHelp = new JMenuItem();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel9 = new JPanel();
		toolBar1 = new JToolBar();
		buttonNovo = new JButton();
		buttonAbrir = new JButton();
		buttonSalvar = new JButton();
		toolBar2 = new JToolBar();
		zoomMenos = new JButton();
		spinnerZoom = new JSpinner();
		zoomMais = new JButton();
		toolBar3 = new JToolBar();
		girarEixoX = new JButton();
		girar2 = new JButton();
		girarEixoY = new JButton();
		panel5 = new JPanel();
		splitPane1 = new JSplitPane();
		splitPane2 = new JSplitPane();
		tabbedPane1 = new JTabbedPane();
		panel6 = new JPanel();
		scrollPaneTree = new JScrollPane();
		tree2 = new JTree();
		panel7 = new JPanel();
		buttonBoss = new JButton();
		panel8 = new JPanel();
		buttonRemover = new JButton();
		buttonEditar = new JButton();
		panel10 = new JPanel();
		scrollPaneWsTree = new JScrollPane();
		workingstepsTree = new JTree();
		panel11 = new JPanel();
		buttonRemoverWS = new JButton();
		buttonEditarWS = new JButton();
		buttonAlterarWorkplan = new JButton();
		panel12 = new JPanel();
		scrollPaneWsTree2 = new JScrollPane();
		workplanTree = new JTree();
		panel13 = new JPanel();
		buttonRemoverWS2 = new JButton();
		buttonEditarWS2 = new JButton();
		buttonAlterarWorkplan2 = new JButton();
		scrollPaneDesenho = new JScrollPane();
		panel1 = new JPanel();
		scrollPane3 = new JScrollPane();
		textArea1 = new JTextArea();
		panel2 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();

		//======== this ========
		setTitle("STEP Modeler");
		setIconImage(new ImageIcon(getClass().getResource("/images/iconePrincipal.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menuArquivo ========
			{
				menuArquivo.setText("Arquivo");
				menuArquivo.setFont(menuArquivo.getFont().deriveFont(menuArquivo.getFont().getStyle() | Font.BOLD));

				//---- menuItemNovoProjeto ----
				menuItemNovoProjeto.setText("Novo Projeto");
				menuItemNovoProjeto.setIcon(new ImageIcon(getClass().getResource("/images/document-new16.png")));
				menuItemNovoProjeto.setFont(menuItemNovoProjeto.getFont().deriveFont(menuItemNovoProjeto.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemNovoProjeto);
				menuArquivo.addSeparator();

				//---- menuItemAbrir ----
				menuItemAbrir.setText("Abrir projeto localmente");
				menuItemAbrir.setIcon(new ImageIcon(getClass().getResource("/images/icone abrir arquivo 21.png")));
				menuItemAbrir.setFont(menuItemAbrir.getFont().deriveFont(menuItemAbrir.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemAbrir);

				//---- menuItemAbrirBD ----
				menuItemAbrirBD.setText("Abrir projeto salvo no servidor");
				menuItemAbrirBD.setIcon(new ImageIcon(getClass().getResource("/images/icone abrir arquivo do servidor.png")));
				menuItemAbrirBD.setFont(menuItemAbrirBD.getFont().deriveFont(menuItemAbrirBD.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemAbrirBD);
				menuArquivo.addSeparator();

				//---- menuItemSalvar ----
				menuItemSalvar.setText("Salvar projeto localmente");
				menuItemSalvar.setIcon(new ImageIcon(getClass().getResource("/images/icone salvar.png")));
				menuItemSalvar.setFont(menuItemSalvar.getFont().deriveFont(menuItemSalvar.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemSalvar);

				//---- menuItemSalvarBD ----
				menuItemSalvarBD.setText("Salvar projeto no servidor");
				menuItemSalvarBD.setIcon(new ImageIcon(getClass().getResource("/images/icone salvar no Bd.png")));
				menuItemSalvarBD.setFont(menuItemSalvarBD.getFont().deriveFont(menuItemSalvarBD.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemSalvarBD);
				menuArquivo.addSeparator();

				//---- menuItemImprimir ----
				menuItemImprimir.setText("Imprimir");
				menuItemImprimir.setIcon(new ImageIcon(getClass().getResource("/images/document-print.png")));
				menuItemImprimir.setFont(menuItemImprimir.getFont().deriveFont(menuItemImprimir.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemImprimir);

				//---- menuItemOrdemFabricacao ----
				menuItemOrdemFabricacao.setText("Enviar uma ordem de fabrica\u00e7\u00e3o");
				menuItemOrdemFabricacao.setIcon(new ImageIcon(getClass().getResource("/images/iconOrdemFabricacao.png")));
				menuItemOrdemFabricacao.setFont(menuItemOrdemFabricacao.getFont().deriveFont(menuItemOrdemFabricacao.getFont().getStyle() & ~Font.BOLD));
				menuArquivo.add(menuItemOrdemFabricacao);
				menuArquivo.addSeparator();

				//---- menuItemSair ----
				menuItemSair.setText("Sair");
				menuItemSair.setFont(menuItemSair.getFont().deriveFont(menuItemSair.getFont().getStyle() & ~Font.BOLD));
				menuItemSair.setIcon(new ImageIcon(getClass().getResource("/images/process-stop.png")));
				menuArquivo.add(menuItemSair);
			}
			menuBar1.add(menuArquivo);

			//======== menuFerramentas ========
			{
				menuFerramentas.setText("Configurar");
				menuFerramentas.setFont(menuFerramentas.getFont().deriveFont(menuFerramentas.getFont().getStyle() | Font.BOLD));

				//======== menuCriarFeatureEm ========
				{
					menuCriarFeatureEm.setText("Plano de Trabalho");
					menuCriarFeatureEm.setFont(menuCriarFeatureEm.getFont().deriveFont(menuCriarFeatureEm.getFont().getStyle() & ~Font.BOLD));

					//---- fixarFaceXY ----
					fixarFaceXY.setText("XY");
					fixarFaceXY.setSelected(true);
					fixarFaceXY.setFont(fixarFaceXY.getFont().deriveFont(fixarFaceXY.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceXY);

					//---- fixarFaceYZ ----
					fixarFaceYZ.setText("YZ");
					fixarFaceYZ.setFont(fixarFaceYZ.getFont().deriveFont(fixarFaceYZ.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceYZ);

					//---- fixarFaceXZ ----
					fixarFaceXZ.setText("XZ");
					fixarFaceXZ.setFont(fixarFaceXZ.getFont().deriveFont(fixarFaceXZ.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceXZ);

					//---- fixarFaceYX ----
					fixarFaceYX.setText("YX");
					fixarFaceYX.setFont(fixarFaceYX.getFont().deriveFont(fixarFaceYX.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceYX);

					//---- fixarFaceZY ----
					fixarFaceZY.setText("ZY");
					fixarFaceZY.setFont(fixarFaceZY.getFont().deriveFont(fixarFaceZY.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceZY);

					//---- fixarFaceZX ----
					fixarFaceZX.setText("ZX");
					fixarFaceZX.setFont(fixarFaceZX.getFont().deriveFont(fixarFaceZX.getFont().getStyle() & ~Font.BOLD));
					menuCriarFeatureEm.add(fixarFaceZX);
				}
				menuFerramentas.add(menuCriarFeatureEm);
				menuFerramentas.addSeparator();

				//======== menuCriarFeatures ========
				{
					menuCriarFeatures.setText("Adicionar feature");
					menuCriarFeatures.setFont(menuCriarFeatures.getFont().deriveFont(menuCriarFeatures.getFont().getStyle() & ~Font.BOLD));

					//======== menu10 ========
					{
						menu10.setText("Criar Furo");
						menu10.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuro1.png")));
						menu10.setFont(menu10.getFont().deriveFont(menu10.getFont().getStyle() & ~Font.BOLD));

						//---- menuItemCriarFuro ----
						menuItemCriarFuro.setText("Furo com base plana");
						menuItemCriarFuro.setFont(menuItemCriarFuro.getFont().deriveFont(menuItemCriarFuro.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarFuro.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuroBasePlana.png")));
						menu10.add(menuItemCriarFuro);

						//---- menuItemCriarFuroBaseConica ----
						menuItemCriarFuroBaseConica.setText("Furo com base c\u00f4nica");
						menuItemCriarFuroBaseConica.setFont(menuItemCriarFuroBaseConica.getFont().deriveFont(menuItemCriarFuroBaseConica.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarFuroBaseConica.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuroBaseConica.png")));
						menu10.add(menuItemCriarFuroBaseConica);

						//---- menuItemCriarFuroBaseEsferica ----
						menuItemCriarFuroBaseEsferica.setText("Furo com base esf\u00e9rica");
						menuItemCriarFuroBaseEsferica.setFont(menuItemCriarFuroBaseEsferica.getFont().deriveFont(menuItemCriarFuroBaseEsferica.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarFuroBaseEsferica.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuroBaseEsferica.png")));
						menu10.add(menuItemCriarFuroBaseEsferica);

						//---- menuItemCriarFuroBaseArredondada ----
						menuItemCriarFuroBaseArredondada.setText("Furo com base arredondada");
						menuItemCriarFuroBaseArredondada.setFont(menuItemCriarFuroBaseArredondada.getFont().deriveFont(menuItemCriarFuroBaseArredondada.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarFuroBaseArredondada.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuroBaseArredondada.png")));
						menu10.add(menuItemCriarFuroBaseArredondada);

						//---- menuItemCriarFuroConico ----
						menuItemCriarFuroConico.setText("Furo c\u00f4nico");
						menuItemCriarFuroConico.setFont(menuItemCriarFuroConico.getFont().deriveFont(menuItemCriarFuroConico.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarFuroConico.setIcon(new ImageIcon(getClass().getResource("/images/iconeFuroConico.png")));
						menu10.add(menuItemCriarFuroConico);
					}
					menuCriarFeatures.add(menu10);

					//======== menu11 ========
					{
						menu11.setText("Criar Ranhura");
						menu11.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhura1.png")));
						menu11.setFont(menu11.getFont().deriveFont(menu11.getFont().getStyle() & ~Font.BOLD));

						//---- menuItemCriarRanhura ----
						menuItemCriarRanhura.setText("Perfil retangular");
						menuItemCriarRanhura.setFont(menuItemCriarRanhura.getFont().deriveFont(menuItemCriarRanhura.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhura.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraRetangular.png")));
						menu11.add(menuItemCriarRanhura);

						//---- menuItemCriarRanhuraUPerfil ----
						menuItemCriarRanhuraUPerfil.setText("Perfil U quadrado");
						menuItemCriarRanhuraUPerfil.setFont(menuItemCriarRanhuraUPerfil.getFont().deriveFont(menuItemCriarRanhuraUPerfil.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhuraUPerfil.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraUQuadrado.png")));
						menu11.add(menuItemCriarRanhuraUPerfil);

						//---- menuItemCriarRanhuraCircularPerfil ----
						menuItemCriarRanhuraCircularPerfil.setText("Perfil circular");
						menuItemCriarRanhuraCircularPerfil.setFont(menuItemCriarRanhuraCircularPerfil.getFont().deriveFont(menuItemCriarRanhuraCircularPerfil.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhuraCircularPerfil.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraPerfilCircular.png")));
						menu11.add(menuItemCriarRanhuraCircularPerfil);

						//---- menuItemCriarRanhuraPerfilRoundedU ----
						menuItemCriarRanhuraPerfilRoundedU.setText("Perfil U circular");
						menuItemCriarRanhuraPerfilRoundedU.setFont(menuItemCriarRanhuraPerfilRoundedU.getFont().deriveFont(menuItemCriarRanhuraPerfilRoundedU.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhuraPerfilRoundedU.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraPerfilUCircular.png")));
						menu11.add(menuItemCriarRanhuraPerfilRoundedU);

						//---- menuItemCriarRanhuraPerfilVee ----
						menuItemCriarRanhuraPerfilVee.setText("Perfil Vee");
						menuItemCriarRanhuraPerfilVee.setFont(menuItemCriarRanhuraPerfilVee.getFont().deriveFont(menuItemCriarRanhuraPerfilVee.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhuraPerfilVee.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraV.png")));
						menu11.add(menuItemCriarRanhuraPerfilVee);

						//---- menuItemCriarRanhuraPerfilBezier ----
						menuItemCriarRanhuraPerfilBezier.setText("Perfil geral (Bezier)");
						menuItemCriarRanhuraPerfilBezier.setFont(menuItemCriarRanhuraPerfilBezier.getFont().deriveFont(menuItemCriarRanhuraPerfilBezier.getFont().getStyle() & ~Font.BOLD));
						menuItemCriarRanhuraPerfilBezier.setIcon(new ImageIcon(getClass().getResource("/images/iconeRanhuraBezier.png")));
						menu11.add(menuItemCriarRanhuraPerfilBezier);
					}
					menuCriarFeatures.add(menu11);

					//======== menu12 ========
					{
						menu12.setText("Criar Degrau");
						menu12.setIcon(new ImageIcon(getClass().getResource("/images/iconeDegrau1.png")));
						menu12.setFont(menu12.getFont().deriveFont(menu12.getFont().getStyle() & ~Font.BOLD));

						//---- menuItemCriarDegrau ----
						menuItemCriarDegrau.setText("Perfil retangular");
						menuItemCriarDegrau.setFont(menuItemCriarDegrau.getFont().deriveFont(menuItemCriarDegrau.getFont().getStyle() & ~Font.BOLD));
						menu12.add(menuItemCriarDegrau);
					}
					menuCriarFeatures.add(menu12);

					//======== menu13 ========
					{
						menu13.setText("Criar Cavidade");
						menu13.setIcon(new ImageIcon(getClass().getResource("/images/iconeCavidade1.png")));
						menu13.setFont(menu13.getFont().deriveFont(menu13.getFont().getStyle() & ~Font.BOLD));

						//---- menuItemCriarCavidade ----
						menuItemCriarCavidade.setText("Retangular com fundo plano");
						menuItemCriarCavidade.setFont(menuItemCriarCavidade.getFont().deriveFont(menuItemCriarCavidade.getFont().getStyle() & ~Font.BOLD));
						menu13.add(menuItemCriarCavidade);

						//---- menuItemCriarCavidadeFun ----
						menuItemCriarCavidadeFun.setText("Retangular com fundo arredondado");
						menuItemCriarCavidadeFun.setFont(menuItemCriarCavidadeFun.getFont().deriveFont(menuItemCriarCavidadeFun.getFont().getStyle() & ~Font.BOLD));
						menu13.add(menuItemCriarCavidadeFun);
					}
					menuCriarFeatures.add(menu13);

					//---- criarRegion ----
					criarRegion.setText("Criar Region");
					menuCriarFeatures.add(criarRegion);
				}
				menuFerramentas.add(menuCriarFeatures);

				//---- menuItemRemover ----
				menuItemRemover.setText("Remover Feature");
				menuItemRemover.setFont(menuItemRemover.getFont().deriveFont(menuItemRemover.getFont().getStyle() & ~Font.BOLD));
				menuItemRemover.setEnabled(false);
				menuFerramentas.add(menuItemRemover);

				//---- menuItemEditar ----
				menuItemEditar.setText("Editar Feature");
				menuItemEditar.setEnabled(false);
				menuItemEditar.setFont(menuItemEditar.getFont().deriveFont(menuItemEditar.getFont().getStyle() & ~Font.BOLD));
				menuFerramentas.add(menuItemEditar);
				menuFerramentas.addSeparator();

				//---- menuGirar ----
				menuGirar.setText("Girar Pe\u00e7a");
				menuGirar.setFont(menuGirar.getFont().deriveFont(menuGirar.getFont().getStyle() & ~Font.BOLD));
				menuFerramentas.add(menuGirar);
			}
			menuBar1.add(menuFerramentas);

			//======== menu3 ========
			{
				menu3.setText("Visualizar");
				menu3.setFont(menu3.getFont().deriveFont(menu3.getFont().getStyle() | Font.BOLD));

				//======== menu9 ========
				{
					menu9.setText("Visualizar face");
					menu9.setToolTipText("Selecione a face que deseja ver");
					menu9.setFont(menu9.getFont().deriveFont(menu9.getFont().getStyle() & ~Font.BOLD));

					//---- vistaPlanoXY ----
					vistaPlanoXY.setText("XY");
					vistaPlanoXY.setSelected(true);
					vistaPlanoXY.setFont(vistaPlanoXY.getFont().deriveFont(vistaPlanoXY.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoXY);

					//---- vistaPlanoYZ ----
					vistaPlanoYZ.setText("YZ");
					vistaPlanoYZ.setFont(vistaPlanoYZ.getFont().deriveFont(vistaPlanoYZ.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoYZ);

					//---- vistaPlanoXZ ----
					vistaPlanoXZ.setText("XZ");
					vistaPlanoXZ.setFont(vistaPlanoXZ.getFont().deriveFont(vistaPlanoXZ.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoXZ);

					//---- vistaPlanoYX ----
					vistaPlanoYX.setText("YX");
					vistaPlanoYX.setFont(vistaPlanoYX.getFont().deriveFont(vistaPlanoYX.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoYX);

					//---- vistaPlanoZY ----
					vistaPlanoZY.setText("ZY");
					vistaPlanoZY.setFont(vistaPlanoZY.getFont().deriveFont(vistaPlanoZY.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoZY);

					//---- vistaPlanoZX ----
					vistaPlanoZX.setText("ZX");
					vistaPlanoZX.setFont(vistaPlanoZX.getFont().deriveFont(vistaPlanoZX.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanoZX);

					//---- vistaPlanosPrincipais ----
					vistaPlanosPrincipais.setText("Todos os planos");
					vistaPlanosPrincipais.setFont(vistaPlanosPrincipais.getFont().deriveFont(vistaPlanosPrincipais.getFont().getStyle() & ~Font.BOLD));
					menu9.add(vistaPlanosPrincipais);
				}
				menu3.add(menu9);
				menu3.addSeparator();

				//---- mostrarFeaturesSecundarias ----
				mostrarFeaturesSecundarias.setText("Mostrar features das outras faces");
				mostrarFeaturesSecundarias.setFont(mostrarFeaturesSecundarias.getFont().deriveFont(mostrarFeaturesSecundarias.getFont().getStyle() & ~Font.BOLD));
				mostrarFeaturesSecundarias.setSelected(true);
				menu3.add(mostrarFeaturesSecundarias);

				//---- mostrarPecaBruta ----
				mostrarPecaBruta.setText("Mostrar Pe\u00e7a Bruta");
				mostrarPecaBruta.setFont(mostrarPecaBruta.getFont().deriveFont(mostrarPecaBruta.getFont().getStyle() & ~Font.BOLD));
				menu3.add(mostrarPecaBruta);

				//---- mostrarMesa ----
				mostrarMesa.setText("Mostrar Grade");
				mostrarMesa.setFont(mostrarMesa.getFont().deriveFont(mostrarMesa.getFont().getStyle() & ~Font.BOLD));
				mostrarMesa.setSelected(true);
				mostrarMesa.setIcon(new ImageIcon(getClass().getResource("/images/iconeGrade.png")));
				menu3.add(mostrarMesa);

				//---- verEixos ----
				verEixos.setText("Mostrar Eixos");
				verEixos.setFont(verEixos.getFont().deriveFont(verEixos.getFont().getStyle() & ~Font.BOLD));
				verEixos.setSelected(true);
				verEixos.setIcon(new ImageIcon(getClass().getResource("/images/iconeEixos.png")));
				verEixos.setSelectedIcon(new ImageIcon(getClass().getResource("/images/emblem-default.png")));
				menu3.add(verEixos);
				menu3.addSeparator();

				//---- menuItemVerPlanosReferencia ----
				menuItemVerPlanosReferencia.setText("Ver Planos de refer\u00eancia");
				menuItemVerPlanosReferencia.setFont(menuItemVerPlanosReferencia.getFont().deriveFont(menuItemVerPlanosReferencia.getFont().getStyle() & ~Font.BOLD));
				menu3.add(menuItemVerPlanosReferencia);
				menu3.addSeparator();

				//---- gerar3D ----
				gerar3D.setText("Gerar Modelo em 3D");
				gerar3D.setFont(gerar3D.getFont().deriveFont(gerar3D.getFont().getStyle() & ~Font.BOLD));
				gerar3D.setIcon(new ImageIcon(getClass().getResource("/images/icone3D.png")));
				menu3.add(gerar3D);
			}
			menuBar1.add(menu3);

			//======== menu1 ========
			{
				menu1.setText("CAPP");
				menu1.setFont(menu1.getFont().deriveFont(menu1.getFont().getStyle() | Font.BOLD));

				//---- menuItemGerFerr ----
				menuItemGerFerr.setText("Gerenciar Ferramentas");
				menuItemGerFerr.setFont(menuItemGerFerr.getFont().deriveFont(menuItemGerFerr.getFont().getStyle() & ~Font.BOLD));
				menuItemGerFerr.setIcon(new ImageIcon(getClass().getResource("/images/preferences-system.png")));
				menu1.add(menuItemGerFerr);

				//---- gerarCAPP ----
				gerarCAPP.setText("Gerar Plano do Processo");
				gerarCAPP.setFont(gerarCAPP.getFont().deriveFont(gerarCAPP.getFont().getStyle() & ~Font.BOLD));
				gerarCAPP.setIcon(new ImageIcon(getClass().getResource("/images/iconeCAPP.png")));
				menu1.add(gerarCAPP);

				//---- definirPontosApoio ----
				definirPontosApoio.setText("Definir pontos de apoio");
				definirPontosApoio.setFont(definirPontosApoio.getFont().deriveFont(definirPontosApoio.getFont().getStyle() & ~Font.BOLD));
				definirPontosApoio.setIcon(new ImageIcon(getClass().getResource("/images/iconeApoios.png")));
				menu1.add(definirPontosApoio);
			}
			menuBar1.add(menu1);

			//======== menuSTEP ========
			{
				menuSTEP.setText("STEP");
				menuSTEP.setFont(menuSTEP.getFont().deriveFont(menuSTEP.getFont().getStyle() | Font.BOLD));

				//---- menuItemGerar ----
				menuItemGerar.setText("Gerar Arquivo f\u00edsico (.p21)");
				menuItemGerar.setFont(menuItemGerar.getFont().deriveFont(menuItemGerar.getFont().getStyle() & ~Font.BOLD));
				menuItemGerar.setIcon(new ImageIcon(getClass().getResource("/images/iconeParte21.png")));
				menuSTEP.add(menuItemGerar);

				//---- menuItemAbrirSTEP ----
				menuItemAbrirSTEP.setText("Abrir Arquivo f\u00edsico (.p21)");
				menuItemAbrirSTEP.setFont(menuItemAbrirSTEP.getFont().deriveFont(menuItemAbrirSTEP.getFont().getStyle() & ~Font.BOLD));
				menuItemAbrirSTEP.setIcon(new ImageIcon(getClass().getResource("/images/icone abrir arquivo 21.png")));
				menuSTEP.add(menuItemAbrirSTEP);

				//---- menuItemAbrirXML ----
				menuItemAbrirXML.setText("Abrir Arquivo XML");
				menuItemAbrirXML.setFont(menuItemAbrirXML.getFont().deriveFont(menuItemAbrirXML.getFont().getStyle() & ~Font.BOLD));
				menuItemAbrirXML.setIcon(new ImageIcon(getClass().getResource("/images/icone abrir arquivo xml.png")));
				menuSTEP.add(menuItemAbrirXML);
			}
			menuBar1.add(menuSTEP);

			//======== menuAjuda ========
			{
				menuAjuda.setText("Ajuda");
				menuAjuda.setFont(menuAjuda.getFont().deriveFont(menuAjuda.getFont().getStyle() | Font.BOLD));

				//---- menuItemAbout ----
				menuItemAbout.setText("About");
				menuItemAbout.setFont(menuItemAbout.getFont().deriveFont(menuItemAbout.getFont().getStyle() & ~Font.BOLD));
				menuItemAbout.setIcon(new ImageIcon(getClass().getResource("/images/iconeAbout.png")));
				menuAjuda.add(menuItemAbout);

				//---- menuItemHelp ----
				menuItemHelp.setText("Ajuda");
				menuItemHelp.setFont(menuItemHelp.getFont().deriveFont(menuItemHelp.getFont().getStyle() & ~Font.BOLD));
				menuItemHelp.setIcon(new ImageIcon(getClass().getResource("/images/help-browser.png")));
				menuAjuda.add(menuItemHelp);
			}
			menuBar1.add(menuAjuda);
		}
		setJMenuBar(menuBar1);

		//======== panel3 ========
		{
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {6, 0, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

			//======== panel4 ========
			{
				panel4.setLayout(new GridBagLayout());
				((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
				((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== panel9 ========
				{
					panel9.setLayout(new FlowLayout());

					//======== toolBar1 ========
					{

						//---- buttonNovo ----
						buttonNovo.setIcon(new ImageIcon(getClass().getResource("/images/document-new.png")));
						buttonNovo.setToolTipText("Criar novo projeto");
						toolBar1.add(buttonNovo);

						//---- buttonAbrir ----
						buttonAbrir.setIcon(new ImageIcon(getClass().getResource("/images/icone abrir 24.png")));
						buttonAbrir.setToolTipText("Abrir projeto salvo no computador");
						toolBar1.add(buttonAbrir);

						//---- buttonSalvar ----
						buttonSalvar.setIcon(new ImageIcon(getClass().getResource("/images/icone salvar24.png")));
						buttonSalvar.setToolTipText("Salvar projeto no computador");
						toolBar1.add(buttonSalvar);
					}
					panel9.add(toolBar1);

					//======== toolBar2 ========
					{

						//---- zoomMenos ----
						zoomMenos.setIcon(new ImageIcon(getClass().getResource("/images/zoom-out.png")));
						zoomMenos.setToolTipText("reduzir zoom");
						toolBar2.add(zoomMenos);

						//---- spinnerZoom ----
						spinnerZoom.setModel(new SpinnerNumberModel(100.0, 5.0, 500.0, 5.0));
						spinnerZoom.setFont(new Font("Courier New", Font.PLAIN, 20));
						toolBar2.add(spinnerZoom);

						//---- zoomMais ----
						zoomMais.setIcon(new ImageIcon(getClass().getResource("/images/zoom-in.png")));
						zoomMais.setToolTipText("aumentar zoom");
						toolBar2.add(zoomMais);
					}
					panel9.add(toolBar2);

					//======== toolBar3 ========
					{

						//---- girarEixoX ----
						girarEixoX.setIcon(new ImageIcon(getClass().getResource("/images/iconeGirarX.png")));
						girarEixoX.setToolTipText("girar pe\u00e7a");
						toolBar3.add(girarEixoX);

						//---- girar2 ----
						girar2.setIcon(new ImageIcon(getClass().getResource("/images/refresh.png")));
						girar2.setToolTipText("girar pe\u00e7a");
						toolBar3.add(girar2);

						//---- girarEixoY ----
						girarEixoY.setIcon(new ImageIcon(getClass().getResource("/images/iconeGirar.png")));
						girarEixoY.setToolTipText("girar pe\u00e7a");
						toolBar3.add(girarEixoY);
					}
					panel9.add(toolBar3);
				}
				panel4.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			panel3.add(panel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== panel5 ========
			{
				panel5.setLayout(new GridBagLayout());
				((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
				((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== splitPane1 ========
				{
					splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
					splitPane1.setMinimumSize(new Dimension(800, 600));
					splitPane1.setPreferredSize(new Dimension(800, 600));
					splitPane1.setResizeWeight(0.8);
					splitPane1.setOneTouchExpandable(true);

					//======== splitPane2 ========
					{
						splitPane2.setMinimumSize(new Dimension(0, 0));
						splitPane2.setOneTouchExpandable(true);
						splitPane2.setResizeWeight(0.1);

						//======== tabbedPane1 ========
						{
							tabbedPane1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
							tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

							//======== panel6 ========
							{
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

								//======== scrollPaneTree ========
								{
									scrollPaneTree.setViewportView(tree2);
								}
								panel6.add(scrollPaneTree, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel7 ========
								{
									panel7.setLayout(new GridBagLayout());
									((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
									((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- buttonBoss ----
									buttonBoss.setText("Create Boss");
									panel7.add(buttonBoss, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//======== panel8 ========
									{
										panel8.setLayout(new GridBagLayout());
										((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- buttonRemover ----
										buttonRemover.setText("Remover");
										buttonRemover.setToolTipText("Remove a feature selecionada na \u00e1rvore");
										buttonRemover.setFont(buttonRemover.getFont().deriveFont(buttonRemover.getFont().getStyle() | Font.BOLD));
										panel8.add(buttonRemover, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- buttonEditar ----
										buttonEditar.setText("Editar");
										buttonEditar.setEnabled(false);
										buttonEditar.setToolTipText("Edita a feature selecionada na \u00e1rvore");
										buttonEditar.setFont(buttonEditar.getFont().deriveFont(buttonEditar.getFont().getStyle() | Font.BOLD));
										panel8.add(buttonEditar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									panel7.add(panel8, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel6.add(panel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							tabbedPane1.addTab("Features", panel6);


							//======== panel10 ========
							{
								panel10.setLayout(new GridBagLayout());
								((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

								//======== scrollPaneWsTree ========
								{

									//---- workingstepsTree ----
									workingstepsTree.setModel(new DefaultTreeModel(
										new DefaultMutableTreeNode("Roughing & Finishing Workingsteps") {
											{
												DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Workingsteps");
													node1.add(new DefaultMutableTreeNode("WS-Desbaste"));
													node1.add(new DefaultMutableTreeNode("WS-Acabamento"));
												add(node1);
											}
										}));
									scrollPaneWsTree.setViewportView(workingstepsTree);
								}
								panel10.add(scrollPaneWsTree, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel11 ========
								{
									panel11.setToolTipText("Selecione um machining workingstep");
									panel11.setLayout(new GridBagLayout());
									((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- buttonRemoverWS ----
									buttonRemoverWS.setText("Remover");
									buttonRemoverWS.setFont(buttonRemoverWS.getFont().deriveFont(buttonRemoverWS.getFont().getStyle() | Font.BOLD));
									buttonRemoverWS.setToolTipText("Remove o machining workingstep selecionado");
									panel11.add(buttonRemoverWS, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- buttonEditarWS ----
									buttonEditarWS.setText("Editar");
									buttonEditarWS.setFont(buttonEditarWS.getFont().deriveFont(buttonEditarWS.getFont().getStyle() | Font.BOLD));
									buttonEditarWS.setToolTipText("Edita os par\u00e2metros do machining workingstep selecionado");
									panel11.add(buttonEditarWS, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- buttonAlterarWorkplan ----
									buttonAlterarWorkplan.setText("Alterar Sequ\u00eancia");
									buttonAlterarWorkplan.setFont(buttonAlterarWorkplan.getFont().deriveFont(buttonAlterarWorkplan.getFont().getStyle() | Font.BOLD));
									buttonAlterarWorkplan.setToolTipText("Altera a ordem de execu\u00e7\u00e3o do machining workingstep selecionado");
									panel11.add(buttonAlterarWorkplan, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel10.add(panel11, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							tabbedPane1.addTab("CAPP", panel10);


							//======== panel12 ========
							{
								panel12.setLayout(new GridBagLayout());
								((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

								//======== scrollPaneWsTree2 ========
								{

									//---- workplanTree ----
									workplanTree.setModel(new DefaultTreeModel(
										new DefaultMutableTreeNode("Main Workplan") {
											{
												DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Setup XY");
													node1.add(new DefaultMutableTreeNode("Workingsteps"));
												add(node1);
											}
										}));
									scrollPaneWsTree2.setViewportView(workplanTree);
								}
								panel12.add(scrollPaneWsTree2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel13 ========
								{
									panel13.setToolTipText("Selecione um machining workingstep");
									panel13.setLayout(new GridBagLayout());
									((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- buttonRemoverWS2 ----
									buttonRemoverWS2.setText("Remover");
									buttonRemoverWS2.setFont(buttonRemoverWS2.getFont().deriveFont(buttonRemoverWS2.getFont().getStyle() | Font.BOLD));
									buttonRemoverWS2.setToolTipText("Remove o machining workingstep selecionado");
									panel13.add(buttonRemoverWS2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- buttonEditarWS2 ----
									buttonEditarWS2.setText("Editar");
									buttonEditarWS2.setFont(buttonEditarWS2.getFont().deriveFont(buttonEditarWS2.getFont().getStyle() | Font.BOLD));
									buttonEditarWS2.setToolTipText("Edita os par\u00e2metros do machining workingstep selecionado");
									panel13.add(buttonEditarWS2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- buttonAlterarWorkplan2 ----
									buttonAlterarWorkplan2.setText("Alterar Sequ\u00eancia");
									buttonAlterarWorkplan2.setFont(buttonAlterarWorkplan2.getFont().deriveFont(buttonAlterarWorkplan2.getFont().getStyle() | Font.BOLD));
									buttonAlterarWorkplan2.setToolTipText("Altera a ordem de execu\u00e7\u00e3o do machining workingstep selecionado");
									panel13.add(buttonAlterarWorkplan2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel12.add(panel13, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							tabbedPane1.addTab("Workplan", panel12);

						}
						splitPane2.setLeftComponent(tabbedPane1);

						//======== scrollPaneDesenho ========
						{

							//======== panel1 ========
							{
								panel1.setMinimumSize(new Dimension(400, 400));
								panel1.setLayout(new GridBagLayout());
								((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 505, 0, 0};
								((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
							}
							scrollPaneDesenho.setViewportView(panel1);
						}
						splitPane2.setRightComponent(scrollPaneDesenho);
					}
					splitPane1.setTopComponent(splitPane2);

					//======== scrollPane3 ========
					{

						//---- textArea1 ----
						textArea1.setEditable(false);
						textArea1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
						textArea1.setSelectionColor(new Color(153, 153, 153));
						scrollPane3.setViewportView(textArea1);
					}
					splitPane1.setBottomComponent(scrollPane3);
				}
				panel5.add(splitPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			panel3.add(panel5, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- label1 ----
			label1.setText("Status:");
			label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD));
			panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label2 ----
			label2.setText("Mostrando a face:");
			label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() & ~Font.BOLD));
			panel2.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label3 ----
			label3.setText("XY");
			label3.setForeground(Color.red);
			label3.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel2.add(label3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label4 ----
			label4.setText("Criando features na face:");
			label4.setFont(label4.getFont().deriveFont(label4.getFont().getStyle() & ~Font.BOLD));
			panel2.add(label4, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label5 ----
			label5.setText("XY");
			label5.setForeground(Color.red);
			label5.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel2.add(label5, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));
		}

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(fixarFaceXY);
		buttonGroup1.add(fixarFaceYZ);
		buttonGroup1.add(fixarFaceXZ);
		buttonGroup1.add(fixarFaceYX);
		buttonGroup1.add(fixarFaceZY);
		buttonGroup1.add(fixarFaceZX);

		//---- buttonGroup2 ----
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(vistaPlanoXY);
		buttonGroup2.add(vistaPlanoYZ);
		buttonGroup2.add(vistaPlanoXZ);
		buttonGroup2.add(vistaPlanoYX);
		buttonGroup2.add(vistaPlanoZY);
		buttonGroup2.add(vistaPlanoZX);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	public JMenu menuArquivo;
	public JMenuItem menuItemNovoProjeto;
	public JMenuItem menuItemAbrir;
	public JMenuItem menuItemAbrirBD;
	public JMenuItem menuItemSalvar;
	public JMenuItem menuItemSalvarBD;
	public JMenuItem menuItemImprimir;
	public JMenuItem menuItemOrdemFabricacao;
	public JMenuItem menuItemSair;
	public JMenu menuFerramentas;
	public JMenu menuCriarFeatureEm;
	public JRadioButtonMenuItem fixarFaceXY;
	public JRadioButtonMenuItem fixarFaceYZ;
	public JRadioButtonMenuItem fixarFaceXZ;
	public JRadioButtonMenuItem fixarFaceYX;
	public JRadioButtonMenuItem fixarFaceZY;
	public JRadioButtonMenuItem fixarFaceZX;
	public JMenu menuCriarFeatures;
	public JMenu menu10;
	public JMenuItem menuItemCriarFuro;
	public JMenuItem menuItemCriarFuroBaseConica;
	public JMenuItem menuItemCriarFuroBaseEsferica;
	public JMenuItem menuItemCriarFuroBaseArredondada;
	public JMenuItem menuItemCriarFuroConico;
	public JMenu menu11;
	public JMenuItem menuItemCriarRanhura;
	public JMenuItem menuItemCriarRanhuraUPerfil;
	public JMenuItem menuItemCriarRanhuraCircularPerfil;
	public JMenuItem menuItemCriarRanhuraPerfilRoundedU;
	public JMenuItem menuItemCriarRanhuraPerfilVee;
	public JMenuItem menuItemCriarRanhuraPerfilBezier;
	public JMenu menu12;
	public JMenuItem menuItemCriarDegrau;
	public JMenu menu13;
	public JMenuItem menuItemCriarCavidade;
	public JMenuItem menuItemCriarCavidadeFun;
	public JMenuItem criarRegion;
	public JMenuItem menuItemRemover;
	public JMenuItem menuItemEditar;
	public JMenuItem menuGirar;
	public JMenu menu3;
	public JMenu menu9;
	public JRadioButtonMenuItem vistaPlanoXY;
	public JRadioButtonMenuItem vistaPlanoYZ;
	public JRadioButtonMenuItem vistaPlanoXZ;
	public JRadioButtonMenuItem vistaPlanoYX;
	public JRadioButtonMenuItem vistaPlanoZY;
	public JRadioButtonMenuItem vistaPlanoZX;
	public JCheckBoxMenuItem vistaPlanosPrincipais;
	public JCheckBoxMenuItem mostrarFeaturesSecundarias;
	public JCheckBoxMenuItem mostrarPecaBruta;
	public JCheckBoxMenuItem mostrarMesa;
	public JCheckBoxMenuItem verEixos;
	public JMenuItem menuItemVerPlanosReferencia;
	public JMenuItem gerar3D;
	private JMenu menu1;
	public JMenuItem menuItemGerFerr;
	public JMenuItem gerarCAPP;
	public JMenuItem definirPontosApoio;
	public JMenu menuSTEP;
	public JMenuItem menuItemGerar;
	public JMenuItem menuItemAbrirSTEP;
	public JMenuItem menuItemAbrirXML;
	public JMenu menuAjuda;
	public JMenuItem menuItemAbout;
	public JMenuItem menuItemHelp;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel9;
	private JToolBar toolBar1;
	public JButton buttonNovo;
	public JButton buttonAbrir;
	public JButton buttonSalvar;
	private JToolBar toolBar2;
	public JButton zoomMenos;
	public JSpinner spinnerZoom;
	public JButton zoomMais;
	private JToolBar toolBar3;
	public JButton girarEixoX;
	public JButton girar2;
	public JButton girarEixoY;
	private JPanel panel5;
	public JSplitPane splitPane1;
	public JSplitPane splitPane2;
	private JTabbedPane tabbedPane1;
	private JPanel panel6;
	public JScrollPane scrollPaneTree;
	public JTree tree2;
	private JPanel panel7;
	public JButton buttonBoss;
	private JPanel panel8;
	public JButton buttonRemover;
	public JButton buttonEditar;
	private JPanel panel10;
	public JScrollPane scrollPaneWsTree;
	public JTree workingstepsTree;
	public JPanel panel11;
	public JButton buttonRemoverWS;
	public JButton buttonEditarWS;
	public JButton buttonAlterarWorkplan;
	private JPanel panel12;
	public JScrollPane scrollPaneWsTree2;
	public JTree workplanTree;
	public JPanel panel13;
	public JButton buttonRemoverWS2;
	public JButton buttonEditarWS2;
	public JButton buttonAlterarWorkplan2;
	public JScrollPane scrollPaneDesenho;
	public JPanel panel1;
	private JScrollPane scrollPane3;
	public JTextArea textArea1;
	public JPanel panel2;
	private JLabel label1;
	private JLabel label2;
	public JLabel label3;
	private JLabel label4;
	public JLabel label5;
public DesenhadorDeFaces desenhador;	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
