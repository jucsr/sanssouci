package br.UFSC.GRIMA.cad.visual;

//import images.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import br.UFSC.GRIMA.cad.DesenhadorDeFaces;

/**
 * @author Julio Benavente
 */
public class JanelaFundoFrame extends JFrame {
	public JanelaFundoFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Julio C
		menuBar2 = new JMenuBar();
		menuArquivo = new JMenu();
		menuSTEP = new JMenu();
		menuItemNovoProjeto = new JMenuItem();
		menuItemGerar = new JMenuItem();
		menuItemAbrir = new JMenuItem();
		menuItemAbrirSTEP = new JMenuItem();
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
		menuItemCriarFuro = new JMenuItem();
		menuItemCriarDegrau = new JMenuItem();
		menuItemCriarRanhura = new JMenuItem();
		menuItemCriarCavidade = new JMenuItem();
		menuItemCriarCavidadeFun = new JMenuItem();
		menuItemCriarFuroBaseConica = new JMenuItem();
		menuItemCriarFuroBaseEsferica = new JMenuItem();
		menuItemCriarFuroBaseArredondada = new JMenuItem();
		menuItemCriarFuroConico = new JMenuItem();
		menuItemCriarRanhuraUPerfil = new JMenuItem();
		menuItemCriarRanhuraCircularPerfil = new JMenuItem();
		menuItemCriarRanhuraPerfilRoundedU = new JMenuItem();
		menuItemCriarRanhuraPerfilVee = new JMenuItem();
		menuItemCriarRanhuraPerfilBezier = new JMenuItem();
		menuGirar = new JMenuItem();
		gerarCAPP = new JMenuItem();
		menuVer = new JMenu();
		menu8 = new JMenu();
		vistaPlanoXY = new JRadioButtonMenuItem();
		vistaPlanoYZ = new JRadioButtonMenuItem();
		vistaPlanoXZ = new JRadioButtonMenuItem();
		vistaPlanoYX = new JRadioButtonMenuItem();
		vistaPlanoZY = new JRadioButtonMenuItem();
		vistaPlanoZX = new JRadioButtonMenuItem();
		//vistaPlanosPrincipais = new JRadioButtonMenuItem();
		vistaPlanosPrincipais = new JCheckBoxMenuItem();
		mostrarFeaturesSecundarias = new JCheckBoxMenuItem();
		mostrarPecaBruta = new JCheckBoxMenuItem();
		mostrarMesa = new JCheckBoxMenuItem();
		verEixos = new JCheckBoxMenuItem();
		menuItemVerPlanosReferencia = new JMenuItem();
		gerar3D = new JMenuItem();
		menuAjuda = new JMenu();
		menuItemAbout = new JMenuItem();
		menuItemHelp = new JMenuItem();
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel3 = new JPanel();
		panel6 = new JPanel();
		toolBar2 = new JToolBar();
		buttonCriarFuro = new JButton();
		buttonCriarDegrau = new JButton();
		buttonCriarRanhura = new JButton();
		buttonCriarCavidade = new JButton();
		separator29 = new JPopupMenu.Separator();
		separator30 = new JPopupMenu.Separator();
		zoomMenos = new JButton();
		//zoomComboBox = new JComboBox();
		zoomMais = new JButton();
		girarEixoY = new JButton();
		girar2 = new JButton();
		girarEixoX = new JButton();
		separator31 = new JPopupMenu.Separator();
		panel1 = new JPanel();
		panel4 = new JPanel();
		label1 = new JLabel();
		label3 = new JLabel();
		panel2 = new JPanel();
		panel5 = new JPanel();
		label4 = new JLabel();
		label5 = new JLabel();
		panel12 = new JPanel();
		scrollPaneDesenho = new JScrollPane();
		panel13 = new JPanel();
		panel9 = new JPanel();
		scrollPaneTree = new JScrollPane();
		tree2 = new JTree();
		toolBar4 = new JToolBar();
		buttonRemover = new JButton();
		buttonEditar = new JButton();
		textFieldZoom = new JTextField();

		//======== this ========
		setTitle("CAD por Features Versão Beta");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar2 ========
		{
			
			//======== menuArquivo ========
			{
				menuArquivo.setText("Arquivo");
				menuArquivo.setMnemonic('A');
				menuArquivo.setForeground(new Color(0, 0, 153));
				menuArquivo.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				//---- menuItemNovoProjeto ----
				//System.out.println("########  ");
				URL novoURL = getClass().getResource("/images/mnovo.JPG");
				
				//System.out.println("########123  " + novoURL);
				
				menuItemNovoProjeto.setText("Novo Projeto");
				menuItemNovoProjeto.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuItemNovoProjeto.setForeground(new Color(51, 0, 153));
				menuItemNovoProjeto.setIcon(new ImageIcon(novoURL));
				menuArquivo.add(menuItemNovoProjeto);
				menuArquivo.addSeparator();
				
				//---- menuItemAbrir ----
				
				URL abrirURL = getClass().getResource("/images/mabrir.JPG");
				
				menuItemAbrir.setText("Abrir projeto localmente");
				menuItemAbrir.setIcon(new ImageIcon(abrirURL));
				menuItemAbrir.setForeground(new Color(0, 0, 153));
				menuItemAbrir.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuArquivo.add(menuItemAbrir);
				
				//---- menuItemAbrirBD ----
				
				menuItemAbrirBD.setText("Abrir projeto salvo no Servidor");
				menuItemAbrirBD.setIcon(new ImageIcon(abrirURL));
				menuItemAbrirBD.setForeground(new Color(0, 0, 153));
				menuItemAbrirBD.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuItemAbrirBD.setEnabled(true);
				menuArquivo.add(menuItemAbrirBD);
				menuArquivo.addSeparator();
				
				//---- menuItemSalvar ----
				
				URL salvarURL = getClass().getResource("/images/msalvar.JPG");
				
				menuItemSalvar.setText("Salvar projeto localmente");
				menuItemSalvar.setIcon(new ImageIcon(salvarURL));
				menuItemSalvar.setForeground(new Color(0, 0, 153));
				menuItemSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuArquivo.add(menuItemSalvar);
				
				//---- menuItemSalvar ----
				
				menuItemSalvarBD.setText("Salvar projeto no Servidor");
				menuItemSalvarBD.setIcon(new ImageIcon(salvarURL));
				menuItemSalvarBD.setForeground(new Color(0, 0, 153));
				menuItemSalvarBD.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuArquivo.add(menuItemSalvarBD);
				menuArquivo.addSeparator();
				
				//---- menuItemImprimir ----
				
				URL imprimirURL = getClass().getResource("/images/imprimir.JPG");
				
				menuItemImprimir.setText("Imprimir");
				menuItemImprimir.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuItemImprimir.setForeground(new Color(0, 0, 153));
				menuItemImprimir.setIcon(new ImageIcon(imprimirURL));
				menuArquivo.add(menuItemImprimir);
				
				//---- menuItemOrdemFabricacao ----
				
				URL impr3DURL = getClass().getResource("/images/mimprimir1.JPG");
				
				menuItemOrdemFabricacao.setText("Enviar orden de Fabrica\u00e7\u00e3o");
				menuItemOrdemFabricacao.setIcon(new ImageIcon(impr3DURL));
				menuItemOrdemFabricacao.setForeground(new Color(0, 0, 153));
				menuItemOrdemFabricacao.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuArquivo.add(menuItemOrdemFabricacao);
				menuArquivo.addSeparator();
				
				//---- menuItemSair ----
				menuItemSair.setText("Sair");
				menuItemSair.setForeground(new Color(0, 0, 153));
				menuItemSair.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuArquivo.add(menuItemSair);
			}
			menuBar2.add(menuArquivo);
			
			//======== menuFerramentas ========
			{
				menuFerramentas.setText("Ferramentas");
				menuFerramentas.setMnemonic('M');
				menuFerramentas.setForeground(new Color(0, 0, 153));
				menuFerramentas.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuFerramentas.addSeparator();
				
				//======== menuCriarFeatureEm ========
				{
					menuCriarFeatureEm.setText("Criar Feature em");
					menuCriarFeatureEm.setForeground(new Color(0, 0, 153));
					menuCriarFeatureEm.setToolTipText("Fixa a pe\u00e7a " +
							"na face na qual se deseja criar uma feature");
					menuCriarFeatureEm.setFont(new Font("Tahoma", Font.BOLD, 11));
					
					//---- fixarFaceXY ----
					fixarFaceXY.setText("Plano XY");
					fixarFaceXY.setSelected(true);
					fixarFaceXY.setForeground(new Color(0, 0, 153));
					fixarFaceXY.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuCriarFeatureEm.add(fixarFaceXY);
					
					menuCriarFeatureEm.addSeparator();
				}
				menuFerramentas.add(menuCriarFeatureEm);
				menuFerramentas.addSeparator();
				
				//======== menuCriarFeatures ========
				{
					menuCriarFeatures.setText("Criar feature");
					menuCriarFeatures.setForeground(new Color(0, 0, 153));
					menuCriarFeatures.setFont(new Font("Tahoma", Font.BOLD, 11));
					
					//---- menuItemCriarFuro ----
					
					URL furoURL = getClass().getResource("/images/mfuro.JPG");
					
					menuItemCriarFuro.setText("Criar Furo");
					menuItemCriarFuro.setIcon(new ImageIcon(furoURL));
					menuItemCriarFuro.setForeground(new Color(0, 0, 153));
					menuItemCriarFuro.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarFuro.setToolTipText("Criar Furo");
					menuCriarFeatures.add(menuItemCriarFuro);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarDegrau ----
					
					URL degrauURL = getClass().getResource("/images/mdegrau.JPG");
					
					menuItemCriarDegrau.setText("Criar Degrau");
					menuItemCriarDegrau.setIcon(new ImageIcon(degrauURL));
					menuItemCriarDegrau.setForeground(new Color(0, 0, 153));
					menuItemCriarDegrau.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarDegrau.setToolTipText("Criar Degrau");
					menuCriarFeatures.add(menuItemCriarDegrau);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarRanhura ----
					
					URL ranhuraURL = getClass().getResource("/images/mranhura.JPG");
					
					menuItemCriarRanhura.setText("Criar Ranhura");
					menuItemCriarRanhura.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhura.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhura.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhura.setToolTipText("Criar Ranhura");
					menuCriarFeatures.add(menuItemCriarRanhura);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarCavidade ----
					
					URL cavidadeURL = getClass().getResource("/images/mcavidade.JPG");
					
					menuItemCriarCavidade.setText("Criar Cavidade");
					menuItemCriarCavidade.setIcon(new ImageIcon(cavidadeURL));
					menuItemCriarCavidade.setForeground(new Color(0, 0, 153));
					menuItemCriarCavidade.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarCavidade.setToolTipText("Criar Cavidade");
					menuCriarFeatures.add(menuItemCriarCavidade);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarCavidadeFundoArredondado ----
					
					URL cavidadeFURL = getClass().getResource("/images/mcavidade.JPG");
					
					menuItemCriarCavidadeFun.setText("Cavidade fundo arredondado");
					menuItemCriarCavidadeFun.setIcon(new ImageIcon(cavidadeFURL));
					menuItemCriarCavidadeFun.setForeground(new Color(0, 0, 153));
					menuItemCriarCavidadeFun.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarCavidadeFun.setToolTipText("Criar Cavidade com fundo arredondado");
					menuCriarFeatures.add(menuItemCriarCavidadeFun);
					menuCriarFeatures.addSeparator();

					//---- menuItemCriarFuroBaseConica ----
					
					
					menuItemCriarFuroBaseConica.setText("Furo com base cônica");
					menuItemCriarFuroBaseConica.setIcon(new ImageIcon(furoURL));
					menuItemCriarFuroBaseConica.setForeground(new Color(0, 0, 153));
					menuItemCriarFuroBaseConica.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarFuroBaseConica.setToolTipText("Criar Furo com base cônica");
					menuCriarFeatures.add(menuItemCriarFuroBaseConica);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarFuroBaseEsferica ----
					
					menuItemCriarFuroBaseEsferica.setText("Furo com base esférica");
					menuItemCriarFuroBaseEsferica.setIcon(new ImageIcon(furoURL));
					menuItemCriarFuroBaseEsferica.setForeground(new Color(0, 0, 153));
					menuItemCriarFuroBaseEsferica.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarFuroBaseEsferica.setToolTipText("Criar Furo com base esférica");
					menuCriarFeatures.add(menuItemCriarFuroBaseEsferica);

					menuCriarFeatures.addSeparator();

					//---- menuItemCriarFuroBaseArredondada ----
					
					menuItemCriarFuroBaseArredondada.setText("Furo com base arredondada");
					menuItemCriarFuroBaseArredondada.setIcon(new ImageIcon(furoURL));
					menuItemCriarFuroBaseArredondada.setForeground(new Color(0, 0, 153));
					menuItemCriarFuroBaseArredondada.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarFuroBaseArredondada.setToolTipText("Criar Furo com base arredondada");
					menuCriarFeatures.add(menuItemCriarFuroBaseArredondada);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarFuroConico ----
					
					menuItemCriarFuroConico.setText("Furo cônico");
					menuItemCriarFuroConico.setIcon(new ImageIcon(furoURL));
					menuItemCriarFuroConico.setForeground(new Color(0, 0, 153));
					menuItemCriarFuroConico.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarFuroConico.setToolTipText("Criar Furo cônico");
					menuCriarFeatures.add(menuItemCriarFuroConico);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarRanhuraUPerfil ----
					
					menuItemCriarRanhuraUPerfil.setText("Ranhura perfil U quadrado");
					menuItemCriarRanhuraUPerfil.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhuraUPerfil.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhuraUPerfil.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhuraUPerfil.setToolTipText("Criar Ranhura com perfil U quadrado");
					menuCriarFeatures.add(menuItemCriarRanhuraUPerfil);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarRanhuraCircularPerfil ----
					
					menuItemCriarRanhuraCircularPerfil.setText("Ranhura perfil circular");
					menuItemCriarRanhuraCircularPerfil.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhuraCircularPerfil.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhuraCircularPerfil.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhuraCircularPerfil.setToolTipText("Criar Ranhura com perfil circular");
					menuCriarFeatures.add(menuItemCriarRanhuraCircularPerfil);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarRanhuraPerfilRoundedU ----
					
					menuItemCriarRanhuraPerfilRoundedU.setText("Ranhura perfil U circular");
					menuItemCriarRanhuraPerfilRoundedU.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhuraPerfilRoundedU.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhuraPerfilRoundedU.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhuraPerfilRoundedU.setToolTipText("Criar Ranhura com perfil U circular");
					menuCriarFeatures.add(menuItemCriarRanhuraPerfilRoundedU);
					menuCriarFeatures.addSeparator();
					
					//---- menuItemCriarRanhuraPerfilVee ----
					
					menuItemCriarRanhuraPerfilVee.setText("Ranhura perfil Vee");
					menuItemCriarRanhuraPerfilVee.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhuraPerfilVee.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhuraPerfilVee.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhuraPerfilVee.setToolTipText("Criar Ranhura com perfil Vee");
					menuCriarFeatures.add(menuItemCriarRanhuraPerfilVee);
					menuCriarFeatures.addSeparator();

					// ---- menuItemCriarRanhuraPerfilBezier ----
					
					menuItemCriarRanhuraPerfilBezier.setText("Ranhura perfil Bezier");
					menuItemCriarRanhuraPerfilBezier.setIcon(new ImageIcon(ranhuraURL));
					menuItemCriarRanhuraPerfilBezier.setForeground(new Color(0, 0, 153));
					menuItemCriarRanhuraPerfilBezier.setFont(new Font("Tahoma", Font.BOLD, 11));
					menuItemCriarRanhuraPerfilBezier.setToolTipText("Criar Ranhura com perfil geral usando uma curva de Bezier");
					menuCriarFeatures.add(menuItemCriarRanhuraPerfilBezier);
			
				}
				menuFerramentas.add(menuCriarFeatures);
				menuFerramentas.addSeparator();
				
				//---- menuGirar ----
				menuGirar.setText("Girar pe\u00e7a");
				menuGirar.setForeground(new Color(0, 0, 153));
				menuGirar.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuGirar.setToolTipText("Gira a peça 90° graus");
				menuFerramentas.add(menuGirar);
				menuFerramentas.addSeparator();	
				
				//---- gerarCAPP ----
				gerarCAPP.setText("CAPP");
				gerarCAPP.setForeground(new Color(0, 0, 153));
				gerarCAPP.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuFerramentas.add(gerarCAPP);
				menuFerramentas.addSeparator();				
			}
			menuBar2.add(menuFerramentas);
			
			//======== menuVer ========
			{
				menuVer.setText("Ver");
				menuVer.setMnemonic('V');
				menuVer.setForeground(new Color(0, 0, 153));
				menuVer.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				//======== menu8 ========
				{
					menu8.setText("Vista em plano");
					menu8.setForeground(new Color(0, 0, 153));
					menu8.setFont(new Font("Tahoma", Font.BOLD, 11));
					
					//---- vistaPlanoXY ----
					vistaPlanoXY.setText("XY");
					vistaPlanoXY.setSelected(true);
					vistaPlanoXY.setForeground(new Color(0, 0, 153));
					vistaPlanoXY.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoXY);
					
					//---- vistaPlanoYZ ----
					vistaPlanoYZ.setText("YZ");
					vistaPlanoYZ.setForeground(new Color(0, 0, 153));
					vistaPlanoYZ.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoYZ);
					
					//---- vistaPlanoXZ ----
					vistaPlanoXZ.setText("XZ");
					vistaPlanoXZ.setForeground(new Color(0, 0, 153));
					vistaPlanoXZ.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoXZ);
					
					//---- vistaPlanoYX ----
					vistaPlanoYX.setText("YX");
					vistaPlanoYX.setForeground(new Color(0, 0, 153));
					vistaPlanoYX.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoYX);
					
					//---- vistaPlanoZY ----
					vistaPlanoZY.setText("ZY");
					vistaPlanoZY.setForeground(new Color(0, 0, 153));
					vistaPlanoZY.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoZY);
					
					//---- vistaPlanoZX ----
					vistaPlanoZX.setText("ZX");
					vistaPlanoZX.setForeground(new Color(0, 0, 153));
					vistaPlanoZX.setFont(new Font("Tahoma", Font.BOLD, 11));
					menu8.add(vistaPlanoZX);
					
					//---- vistaPlanosPrincipais ----
					vistaPlanosPrincipais.setText("planos");
					vistaPlanosPrincipais.setToolTipText("Mostra todos os planos na painel");
					vistaPlanosPrincipais.setFont(new Font("Tahoma", Font.BOLD, 11));
					vistaPlanosPrincipais.setForeground(new Color(51, 0, 153));
					vistaPlanosPrincipais.setEnabled(true);
					menu8.add(vistaPlanosPrincipais);
				}
				menuVer.add(menu8);
				menuVer.addSeparator();
				
				//---- mostrarFeaturesSecundarias ----
				
				mostrarFeaturesSecundarias.setText("Mostrar features secundarias");
				mostrarFeaturesSecundarias.setForeground(new Color(0, 0, 153));
				mostrarFeaturesSecundarias.setSelected(true);
				mostrarFeaturesSecundarias.setFont(new Font("Tahoma", Font.BOLD, 11));
				mostrarFeaturesSecundarias.setToolTipText("Mostra ou apaga as features que nao podem ser" +
						" visulalizadas na face atual\n por estarem em outras faces");
				menuVer.add(mostrarFeaturesSecundarias);
				
				//---- mostrarPecaBruta ----
				mostrarPecaBruta.setText("Mostrar pe\u00e7a bruta");
				mostrarPecaBruta.setForeground(new Color(0, 0, 153));
				mostrarPecaBruta.setSelected(false);
				mostrarPecaBruta.setFont(new Font("Tahoma", Font.BOLD, 11));
				mostrarPecaBruta.setToolTipText("Mostra a peça bruta");
				menuVer.add(mostrarPecaBruta);
				
				//---- mostrarMesa ----
				mostrarMesa.setText("Mostrar Mesa da m\u00e1quina");
				mostrarMesa.setSelected(true);
				mostrarMesa.setForeground(new Color(51, 0, 153));
				mostrarMesa.setFont(new Font("Tahoma", Font.BOLD, 11));
				mostrarMesa.setToolTipText("Mostra ou apaga a mesa da Máquina");
				menuVer.add(mostrarMesa);
				
				//---- verEixos ----
				verEixos.setText("Mostrar Eixos");
				verEixos.setSelected(true);
				verEixos.setForeground(new Color(51, 0, 153));
				verEixos.setFont(new Font("Tahoma", Font.BOLD, 11));
				verEixos.setToolTipText("Mostra ou apaga os eixos da Máquina");
				menuVer.add(verEixos);
				menuVer.addSeparator();
				
				//---- menuItemVerPlanosReferencia ----
				menuItemVerPlanosReferencia.setText("Ver planos de referencias");
				menuItemVerPlanosReferencia.setForeground(new Color(0, 0, 153));
				menuItemVerPlanosReferencia.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuVer.add(menuItemVerPlanosReferencia);
				menuVer.addSeparator();
				
				//---- gerar3D ----
				gerar3D.setText("Gerar Vista em 3D");
				gerar3D.setForeground(new Color(0, 0, 153));
				gerar3D.setFont(new Font("Tahoma", Font.BOLD, 11));
				gerar3D.setEnabled(true);
				menuVer.add(gerar3D);
			}
			menuBar2.add(menuVer);
			
			//======== menuSTEP21 ========
			{
				menuSTEP.setText("STEP 21");
				menuSTEP.setMnemonic('S');
				menuSTEP.setForeground(new Color(0, 0, 153));
				menuSTEP.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				URL novoURL = getClass().getResource("/images/mnovo.JPG");
				
				//---- menuItemGerar ----
				
				menuItemGerar.setText("Gerar Arquivo");
				menuItemGerar.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuItemGerar.setForeground(new Color(51, 0, 153));
				menuItemGerar.setIcon(new ImageIcon(novoURL));
				menuSTEP.add(menuItemGerar);
				
				//---- menuItemAbrirSTEP----
				
				URL abrirURL = getClass().getResource("/images/mabrir.JPG");
				
				menuItemAbrirSTEP.setText("Abrir Arquivo");
				menuItemAbrirSTEP.setIcon(new ImageIcon(abrirURL));
				menuItemAbrirSTEP.setForeground(new Color(0, 0, 153));
				menuItemAbrirSTEP.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuSTEP.add(menuItemAbrirSTEP);
				
			}
			menuBar2.add(menuSTEP);
			
			//======== menuAjuda ========
			{
				menuAjuda.setText("Ajuda");
				menuAjuda.setMnemonic('U');
				menuAjuda.setForeground(new Color(0, 0, 153));
				menuAjuda.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				//---- menuItemAbout ----
				menuItemAbout.setText("About");
				menuItemAbout.setForeground(new Color(0, 0, 153));
				menuItemAbout.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuAjuda.add(menuItemAbout);
				
//				---- menuItemAbout ----
				menuItemHelp.setText("Ajuda");
				menuItemHelp.setForeground(new Color(0, 0, 153));
				menuItemHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
				menuAjuda.add(menuItemHelp);
			}
			menuBar2.add(menuAjuda);
			
			///
			///
			///
		
		}
		setJMenuBar(menuBar2);

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			
			/*// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});
			*/
			dialogPane.setLayout(new BorderLayout());
			
			//======== contentPanel ========
			{
				contentPanel.setBorder(new EtchedBorder());
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {755, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 8, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
				
				//======== panel3 ========
				{
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
					
					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {10, 0, 0, 0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {10, 0, 0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						
					//======== toolBar2 ========
					{
						toolBar2.setName("Criar features");
						toolBar2.setFloatable(false);
							
						//---- buttonCriarFuro ----
							
						URL furoURL = getClass().getResource("/images/furo01.JPG");
							
						buttonCriarFuro.setIcon(new ImageIcon(furoURL));
						buttonCriarFuro.setToolTipText("Criar furo");
						toolBar2.add(buttonCriarFuro);
							
						//---- buttonCriarDegrau ----
							
						URL degrauURL = getClass().getResource("/images/degrau01.JPG");
							
						buttonCriarDegrau.setToolTipText("Criar Degrau");
						buttonCriarDegrau.setIcon(new ImageIcon(degrauURL));
						toolBar2.add(buttonCriarDegrau);
							
						//---- buttonCriarRanhura ----
							
						URL ranhuraURL = getClass().getResource("/images/ranhura01.JPG");
							
						buttonCriarRanhura.setToolTipText("Criar Ranhura");
						buttonCriarRanhura.setIcon(new ImageIcon(ranhuraURL));
						toolBar2.add(buttonCriarRanhura);
						
						//---- buttonCriarCavidade ----
							
						URL cavidadeURL = getClass().getResource("/images/cavidade01.JPG");
							
						buttonCriarCavidade.setToolTipText("Criar Cavidade Retangular");
						buttonCriarCavidade.setIcon(new ImageIcon(cavidadeURL));
						toolBar2.add(buttonCriarCavidade);
						toolBar2.add(separator29);
							
							
							
						//---- zoomMenos ----
							
						URL zoomURL = getClass().getResource("/images/menos_zoom.GIF");
							
						zoomMenos.setIcon(new ImageIcon(zoomURL));
						zoomMenos.setToolTipText("Diminuir Zoom(%)");
						toolBar2.add(zoomMenos);
							
						//---- textFieldZoom ----
							
						textFieldZoom.setColumns(4);
						textFieldZoom.setText("100");
						textFieldZoom.setHorizontalAlignment(SwingConstants.CENTER);
						textFieldZoom.setFont(new Font("Tahoma", Font.BOLD, 12));
						textFieldZoom.setForeground(new Color(0, 0, 153));
						toolBar2.add(textFieldZoom);
							
						/*//---- comboBox1 ----
							
						zoomComboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
						zoomComboBox.setForeground(new Color(0, 0, 153));
						//zoomComboBox.setEditable(true);
						//comboBox1.setMaximumwCount(2);
						zoomComboBox.setToolTipText("Zoom");
						zoomComboBox.setAlignmentX(51);
						toolBar2.add(zoomComboBox);*/
							
						//---- zoomMais ----
							
						URL zoomMURL = getClass().getResource("/images/mais_zoom.GIF");
							
							
						zoomMais.setIcon(new ImageIcon(zoomMURL));
						zoomMais.setToolTipText("Aumentar Zoom(%)");
						toolBar2.add(zoomMais);
							
						//---- girarEixoY ----
							
						URL girarEixoYURL = getClass().getResource("/images/girarY.JPG");
							
						girarEixoY.setToolTipText("rotar a peça respeito ao eixo Y");
						girarEixoY.setIcon(new ImageIcon(girarEixoYURL));
						toolBar2.add(girarEixoY);
							
						//---- girar2 ----
							
						URL girarURL = getClass().getResource("/images/girar.JPG");
							
						girar2.setFont(new Font("Tahoma", Font.BOLD, 11));
						girar2.setForeground(new Color(51, 0, 153));
						girar2.setToolTipText("Gira a pe\u00e7a 90° graus");
						girar2.setIcon(new ImageIcon(girarURL));
						toolBar2.add(girar2);
						toolBar2.add(separator30);
							
						//---- girarEixoX ----
							
						URL girarEixoXURL = getClass().getResource("/images/girarX.JPG");
							
						girarEixoX.setToolTipText("rotar a peça respeito ao eixo X");
						girarEixoX.setIcon(new ImageIcon(girarEixoXURL));
						toolBar2.add(girarEixoX);
							
						toolBar2.add(separator31);
					}
					panel6.add(toolBar2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
						
					//======== panel1 ========
					{
						panel1.setBorder(null);
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 15, 10, 0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						
						//======== panel4 ========
						{
							panel4.setBorder(new EtchedBorder());
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {10, 0, 0, 5, 0};
							((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
							((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							
							//---- label1 ----
							label1.setText("Vista em 2D Face:");
							label1.setFont(new Font("Tahoma", Font.BOLD, 11));
							label1.setForeground(new Color(0, 0, 153));
							panel4.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
							
							//---- label3 ----
							label3.setText("XY");
							label3.setFont(new Font("Tahoma", Font.BOLD, 16));
							label3.setForeground(Color.red);
							panel4.add(label3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel1.add(panel4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					
						//======== panel2 ========
						{
							panel2.setBorder(new EtchedBorder());
							panel2.setLayout(new GridBagLayout());
							((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {10, 0, 0, 5, 0};
							((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 0.0, 1.0E-4};
							((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
							
							//---- label4 ----
							label4.setText("Criar Features na Face :");
							label4.setFont(new Font("Tahoma", Font.BOLD, 11));
							label4.setForeground(new Color(0, 0, 153));
							panel2.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
							
							//---- label5 ----
							label5.setText("XY");
							label5.setFont(new Font("Tahoma", Font.BOLD, 16));
							label5.setForeground(Color.red);
							panel2.add(label5, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						}
						panel1.add(panel2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
					}	
					panel6.add(panel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
				}
				panel3.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
					
				//======== panel12 ========
				{
					panel12.setLayout(new GridBagLayout());
					((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {10, 105, 0, 0};
					((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 105, 0, 0};
					((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
							
					//======== scrollPaneDesenho ========
					{
						scrollPaneDesenho.setBackground(Color.WHITE);
								
							//======== panel13 ========
							/*{
								panel13.setBackground(Color.white);
								panel13.setLayout(new GridBagLayout());
								((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 605, 0, 0};
								((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 555, 0, 0};
								((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
								((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
							}*/
							//scrollPaneDesenho.setViewportView(panel13);
							scrollPaneDesenho.setViewportView(desenhador);
						}
						panel12.add(scrollPaneDesenho, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					panel3.add(panel12, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
					
					//======== panel9 ========
					{
						panel9.setLayout(new GridBagLayout());
						((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 300, 3, 0};
						((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 400, 0, 0, 0, 0};
						((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
						
						//======== scrollPaneTree ========
						{
							scrollPaneTree.setViewportView(tree2);
						}
						panel9.add(scrollPaneTree, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
							
						//======== toolBar4 ========
						{
							toolBar4.setFloatable(false);
								
//							---- buttonRemover ----
							buttonRemover.setText("Remover");
							buttonRemover.setFont(new Font("Tahoma", Font.BOLD, 11));
							buttonRemover.setForeground(new Color(0, 0, 153));
							toolBar4.add(buttonRemover);
							
							//---- buttonEditar ----
							buttonEditar.setText("Editar");
							buttonEditar.setForeground(new Color(51, 0, 153));
							buttonEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
							buttonEditar.setEnabled(false);
							toolBar4.add(buttonEditar);
						}
						panel9.add(toolBar4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					panel3.add(panel9, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
				}
				contentPanel.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
	
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
		buttonGroup2.add(vistaPlanosPrincipais);
	// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Julio Benavente
	public JMenuBar menuBar2;
	public JMenu menuArquivo;
	public JMenu menuSTEP;
	public JMenuItem menuItemNovoProjeto;
	public JMenuItem menuItemGerar;
	public JMenuItem menuItemAbrir;
	public JMenuItem menuItemAbrirSTEP;
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
	public JMenuItem menuItemCriarFuro;
	public JMenuItem menuItemCriarDegrau;
	public JMenuItem menuItemCriarRanhura;
	public JMenuItem menuItemCriarCavidade;
	public JMenuItem menuItemCriarCavidadeFun;
	public JMenuItem menuItemCriarFuroBaseConica;
	public JMenuItem menuItemCriarFuroBaseEsferica;
	public JMenuItem menuItemCriarFuroBaseArredondada;
	public JMenuItem menuItemCriarFuroConico;
	public JMenuItem menuItemCriarRanhuraUPerfil;
	public JMenuItem menuItemCriarRanhuraCircularPerfil;
	public JMenuItem menuItemCriarRanhuraPerfilRoundedU;
	public JMenuItem menuItemCriarRanhuraPerfilVee;
	public JMenuItem menuItemCriarRanhuraPerfilBezier;
	public JMenuItem menuGirar;
	public JMenuItem gerarCAPP;
	public JMenu menuVer;
	public JMenu menu8;
	public JRadioButtonMenuItem vistaPlanoXY;
	public JRadioButtonMenuItem vistaPlanoYZ;
	public JRadioButtonMenuItem vistaPlanoXZ;
	public JRadioButtonMenuItem vistaPlanoYX;
	public JRadioButtonMenuItem vistaPlanoZY;
	public JRadioButtonMenuItem vistaPlanoZX;
	//protected JRadioButtonMenuItem vistaPlanosPrincipais;
	public JCheckBoxMenuItem vistaPlanosPrincipais;
	public JCheckBoxMenuItem mostrarFeaturesSecundarias;
	public JCheckBoxMenuItem mostrarPecaBruta;
	public JCheckBoxMenuItem mostrarMesa;
	public JCheckBoxMenuItem verEixos;
	public JMenuItem menuItemVerPlanosReferencia;
	public JMenuItem gerar3D;
	public JMenu menuAjuda;
	public JMenuItem menuItemAbout;
	public JMenuItem menuItemHelp;
	public JPanel dialogPane;
	public JPanel contentPanel;
	public JPanel panel3;
	public JPanel panel6;
	public JToolBar toolBar2;
	public JButton buttonCriarFuro;
	public JButton buttonCriarDegrau;
	public JButton buttonCriarRanhura;
	public JButton buttonCriarCavidade;
	public JPopupMenu.Separator separator29;
	public JPopupMenu.Separator separator30;
	public JButton zoomMenos;
	public JComboBox zoomComboBox;
	public JButton zoomMais;
	public JButton girarEixoY;
	public JButton girar2;
	public JButton girarEixoX;
	public JPopupMenu.Separator separator31;
	public JPanel panel1;
	public JPanel panel4;
	public JLabel label1;
	public JLabel label3;
	public JPanel panel2;
	public JPanel panel5;
	public JLabel label4;
	public JLabel label5;
	public JPanel panel12;
	public JScrollPane scrollPaneDesenho;
	public JPanel panel13;
	public JPanel panel9;
	public JScrollPane scrollPaneTree;
	public JTree tree2;
	public JToolBar toolBar4;
	public JButton buttonRemover;
	public JButton buttonEditar;
	public JTextField textFieldZoom;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	//protected Desenhador desenhador;
	public DesenhadorDeFaces desenhador;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
