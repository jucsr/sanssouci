package br.UFSC.GRIMA.cad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.UFSC.GRIMA.cad.visual.SelectToolPanelFrame;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.ToolReader;

public class SelectToolPanel extends SelectToolPanelFrame implements
ActionListener{

	private static final long serialVersionUID = 1L;

	private JanelaPrincipal janela;
	private ToolReader toolReader;
	private ToolManager toolManager;
	private ProjectTools projectTools;

	public SelectToolPanel(JanelaPrincipal janela, ProjectTools projectTools) {

		super();

		this.janela = janela;
		this.projectTools = projectTools;
		this.addAutomatic.addActionListener(this);
		this.addSelected.addActionListener(this);
		this.buttonSelectAll.addActionListener(this);
		this.buttonSelectAll2.addActionListener(this);
		this.buttonSelectAll3.addActionListener(this);
		this.buttonSelectAll4.addActionListener(this);
		this.buttonSelectAll5.addActionListener(this);
		this.buttonSelectAll6.addActionListener(this);
		this.buttonSelectAll7.addActionListener(this);
		this.buttonSelectAll8.addActionListener(this);
		this.buttonSelectNone.addActionListener(this);
		this.buttonSelectNone2.addActionListener(this);
		this.buttonSelectNone3.addActionListener(this);
		this.buttonSelectNone4.addActionListener(this);
		this.buttonSelectNone5.addActionListener(this);
		this.buttonSelectNone6.addActionListener(this);
		this.buttonSelectNone7.addActionListener(this);
		this.buttonSelectNone8.addActionListener(this);

		this.toolReader = new ToolReader();

		this.paintTwistDrillTable();
		String text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando twist drills");
		System.out.println("pintou 1");
		this.paintCenterDrillTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando center drills");
		System.out.println("pintou 2");
		this.paintFaceMillTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando face mills");
		System.out.println("pintou 3");
		this.paintEndMillTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando end mills");
		System.out.println("pintou 4");
		this.paintBallEndMillTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando ball end mills");
		System.out.println("pintou 5");
		this.paintBullnoseEndMillTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando bullnose end mills");
		System.out.println("pintou 6");
		this.paintBoringToolTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando boring tools");
		System.out.println("pintou 7");
		this.paintReamerTable();
		text = janela.textArea1.getText();
		janela.textArea1.setText(text + "\n carregando reamers");
		System.out.println("pintou 8");

		initTables();

		this.setVisible(true);
	}

	private void initTables() {

		for(int i = 0; i < this.reamerTable.getRowCount(); i++)
		{
			this.reamerTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.twistDrillTable.getRowCount(); i++)
		{
			this.twistDrillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.centerDrillTable.getRowCount(); i++)
		{
			this.centerDrillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.faceMillTable.getRowCount(); i++)
		{
			this.faceMillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.endMillTable.getRowCount(); i++)
		{
			this.endMillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.ballEndMillTable.getRowCount(); i++)
		{
			this.ballEndMillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.bullnoseEndMillTable.getRowCount(); i++)
		{
			this.bullnoseEndMillTable.setValueAt(false, i, 0);
		}
		for(int i = 0; i < this.boringToolTable.getRowCount(); i++)
		{
			this.boringToolTable.setValueAt(false, i, 0);
		}

	}

	private void paintTwistDrillTable() {

		toolReader.setFerramentaTable("TwistDrill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String tipAngle = toolReader.getPropertie(
						"Tool_Tip_Half_Angle", id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}

				this.twistDrillTable.setValueAt(linha+1, linha, 0+1);
				this.twistDrillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.twistDrillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.twistDrillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.twistDrillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.twistDrillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.twistDrillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.twistDrillTable.setValueAt(Double.parseDouble(tipAngle), linha, 7+1);
				this.twistDrillTable.setValueAt(handOfCut, linha, 8+1);
				this.twistDrillTable.setValueAt(material, linha, 9+1);
				this.twistDrillTable.setValueAt(materialClass, linha, 10+1);

				linha++;
			}
		}

	}

	private void paintCenterDrillTable()
	{
		toolReader.setFerramentaTable("CenterDrill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String tipAngle = toolReader.getPropertie(
						"Tool_Tip_Half_Angle", id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);

				switch (Integer.parseInt(handOfCut)) {
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}


				this.centerDrillTable.setValueAt(linha + 1, linha, 0+1);
				this.centerDrillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.centerDrillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.centerDrillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.centerDrillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.centerDrillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.centerDrillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.centerDrillTable.setValueAt(Double.parseDouble(tipAngle), linha, 7+1);
				this.centerDrillTable.setValueAt(handOfCut, linha, 8+1);
				this.centerDrillTable.setValueAt(material, linha, 9+1);
				this.centerDrillTable.setValueAt(materialClass, linha, 10+1);
				linha++;
			}
		}
	}

	private void paintFaceMillTable()
	{
		toolReader.setFerramentaTable("FaceMill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}


				this.faceMillTable.setValueAt(linha + 1, linha, 0+1);

				this.faceMillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.faceMillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.faceMillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.faceMillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.faceMillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.faceMillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.faceMillTable.setValueAt(handOfCut, linha, 7+1);
				this.faceMillTable.setValueAt(material, linha, 8+1);
				this.faceMillTable.setValueAt(materialClass, linha, 9+1);
				linha++;
			}
		}
	}
	private void paintEndMillTable()
	{
		toolReader.setFerramentaTable("EndMill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}

				this.endMillTable.setValueAt(linha + 1, linha, 0+1);

				this.endMillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.endMillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.endMillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.endMillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.endMillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.endMillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.endMillTable.setValueAt(handOfCut, linha, 7+1);
				this.endMillTable.setValueAt(material, linha, 8+1);
				this.endMillTable.setValueAt(materialClass, linha, 9+1);
				linha++;
			}
		}
	}
	private void paintBallEndMillTable()
	{
		toolReader.setFerramentaTable("BallEndMill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String edgeRadius = toolReader.getPropertie("Edge_Radius", id);
				String edgeCenterVertical = toolReader.getPropertie(
						"Edge_Center_Vertical", id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}


				this.ballEndMillTable.setValueAt(linha + 1, linha, 0+1);

				this.ballEndMillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.ballEndMillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(edgeRadius), linha, 7+1);
				this.ballEndMillTable.setValueAt(Double.parseDouble(edgeCenterVertical), linha, 8+1);
				this.ballEndMillTable.setValueAt(handOfCut, linha, 9+1);
				this.ballEndMillTable.setValueAt(material, linha, 10+1);
				this.ballEndMillTable.setValueAt(materialClass, linha, 11+1);
				linha++;
			}
		}
	}
	private void paintBullnoseEndMillTable()
	{
		toolReader.setFerramentaTable("BullnoseEndMill");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String edgeRadius = toolReader.getPropertie("Edge_Radius", id);
				String edgeCenterVertical = toolReader.getPropertie(
						"Edge_Center_Vertical", id);
				String edgeCenterHorizontal = toolReader.getPropertie(
						"Edge_Center_Horizontal", id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}

				this.bullnoseEndMillTable.setValueAt(linha + 1, linha, 0+1);

				this.bullnoseEndMillTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(edgeRadius), linha, 7+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(edgeCenterVertical), linha, 8+1);
				this.bullnoseEndMillTable.setValueAt(Double.parseDouble(edgeCenterHorizontal), linha, 9+1);
				this.bullnoseEndMillTable.setValueAt(handOfCut, linha, 10+1);
				this.bullnoseEndMillTable.setValueAt(material, linha, 11+1);
				this.bullnoseEndMillTable.setValueAt(materialClass, linha, 12+1);
				linha++;
			}
		}
	}

	private void paintBoringToolTable()
	{
		toolReader.setFerramentaTable("BoringTool");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				String edgeRadius = toolReader.getPropertie("Edge_Radius", id);
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}

				this.boringToolTable.setValueAt(linha + 1, linha, 0+1);

				this.boringToolTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.boringToolTable.setValueAt(diametro, linha, 2+1);
				this.boringToolTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.boringToolTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.boringToolTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.boringToolTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.boringToolTable.setValueAt(Double.parseDouble(edgeRadius), linha, 7+1);
				this.boringToolTable.setValueAt(handOfCut, linha, 8+1);
				this.boringToolTable.setValueAt(material, linha, 9+1);
				this.boringToolTable.setValueAt(materialClass, linha, 10+1);
				linha++;
			}
		}
	}
	private void paintReamerTable()
	{
		toolReader.setFerramentaTable("Reamer");

		int linha = 0;

		for (int id = 1; id <= toolReader.getSize(); id++) {

			char[] materials = toolReader.getPropertie("Material_Ferramenta",
					id).toCharArray();

			for (int i = 0; i < materials.length; i++) {

				String nome = toolReader.getPropertie("Nome", id);
				String diametro = toolReader.getPropertie("Diametro", id);
				String dm = toolReader.getPropertie("Dm", id);
				String cuttingEdge = toolReader.getPropertie(
						"Cutting_Edge_Length", id);
				String profundidade = toolReader.getPropertie("Profundidade",
						id);
				String offSetLength = toolReader.getPropertie("Off_Set_Length",
						id);
				int numberOfTeeth = 1;
				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
				String material = "Carbide";
				String materialClass = materials[i] + "";
				//				String rugosidade = toolReader.getPropertie("rugosidade", id);
				//				String tolerancia = toolReader.getPropertie("tolerancia", id);


				switch(Integer.parseInt(handOfCut)){
				case Ferramenta.RIGHT_HAND_OF_CUT:
					handOfCut = "Right";
					break;
				case Ferramenta.LEFT_HAND_OF_CUT:
					handOfCut = "Left";
					break;
				case Ferramenta.NEUTRAL_HAND_OF_CUT:
					handOfCut = "Neutral";
					break;
				}

				this.reamerTable.setValueAt(linha + 1, linha, 0+1);

				this.reamerTable.setValueAt(nome, linha, 1+1);  // (valor,linha,coluna)
				this.reamerTable.setValueAt(Double.parseDouble(diametro), linha, 2+1);
				this.reamerTable.setValueAt(Double.parseDouble(dm), linha, 3+1);
				this.reamerTable.setValueAt(Double.parseDouble(cuttingEdge), linha, 4+1);
				this.reamerTable.setValueAt(Double.parseDouble(profundidade), linha, 5+1);
				this.reamerTable.setValueAt(Double.parseDouble(offSetLength), linha, 6+1);
				this.reamerTable.setValueAt(numberOfTeeth, linha, 7+1);
				this.reamerTable.setValueAt(handOfCut, linha, 8+1);
				this.reamerTable.setValueAt(material, linha, 9+1);
				this.reamerTable.setValueAt(materialClass, linha, 10+1);
				linha++;
			}
		}
	}

	private void adicionarFerramentasSelecionadas() {

		ArrayList<ArrayList> selectedTools = new ArrayList<ArrayList>();

		selectedTools.add(new ArrayList<CenterDrill>());
		selectedTools.add(new ArrayList<TwistDrill>());
		selectedTools.add(new ArrayList<FaceMill>());
		selectedTools.add(new ArrayList<EndMill>());
		selectedTools.add(new ArrayList<BallEndMill>());
		selectedTools.add(new ArrayList<BullnoseEndMill>());
		selectedTools.add(new ArrayList<BoringTool>());
		selectedTools.add(new ArrayList<Reamer>());

		for(int i = 0; i < centerDrillTable.getRowCount(); i++){

			if((Boolean)(centerDrillTable.getValueAt(i, 0))){

				String nome = (String)centerDrillTable.getValueAt(i, 2);
				String materialClass = (String)centerDrillTable.getValueAt(i, 11);
				double diametro = (Double)centerDrillTable.getValueAt(i, 3);
				double tipAngle = (Double)centerDrillTable.getValueAt(i, 8);
				double cuttingEdge = (Double)centerDrillTable.getValueAt(i, 5);
				double profundidade = (Double)centerDrillTable.getValueAt(i, 6);
				double offSet = (Double)centerDrillTable.getValueAt(i, 7);
				double dm = (Double)centerDrillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)centerDrillTable.getValueAt(i, 9);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				CenterDrill ferrTmp = new CenterDrill(nome, materialClass, diametro,
						tipAngle, cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);

				selectedTools.get(0).add(ferrTmp);

			}

		}

		for(int i = 0; i < twistDrillTable.getRowCount(); i++){

			if((Boolean)(twistDrillTable.getValueAt(i, 0))){

				String nome = (String)twistDrillTable.getValueAt(i, 2);
				String materialClass = (String)twistDrillTable.getValueAt(i, 11);
				double diametro = (Double)twistDrillTable.getValueAt(i, 3);
				double tipAngle = (Double)twistDrillTable.getValueAt(i, 8);
				double cuttingEdge = (Double)twistDrillTable.getValueAt(i, 5);
				double profundidade = (Double)twistDrillTable.getValueAt(i, 6);
				double offSet = (Double)twistDrillTable.getValueAt(i, 7);
				double dm = (Double)twistDrillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)twistDrillTable.getValueAt(i, 9);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				TwistDrill ferrTmp = new TwistDrill(nome, materialClass, diametro,
						tipAngle, cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);

				selectedTools.get(1).add(ferrTmp);

			}

		}

		for(int i = 0; i < faceMillTable.getRowCount(); i++){

			if((Boolean)(faceMillTable.getValueAt(i, 0))){

				String nome = (String)faceMillTable.getValueAt(i, 2);
				String materialClass = (String)faceMillTable.getValueAt(i, 10);
				double diametro = (Double)faceMillTable.getValueAt(i, 3);
				double cuttingEdge = (Double)faceMillTable.getValueAt(i, 5);
				double profundidade = (Double)faceMillTable.getValueAt(i, 6);
				double offSet = (Double)faceMillTable.getValueAt(i, 7);
				double dm = (Double)faceMillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)faceMillTable.getValueAt(i, 8);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				FaceMill ferrTmp = new FaceMill(nome, materialClass, diametro,
						cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);

				selectedTools.get(2).add(ferrTmp);

			}

		}
		for(int i = 0; i < endMillTable.getRowCount(); i++){

			if((Boolean)(endMillTable.getValueAt(i, 0))){

				String nome = (String)endMillTable.getValueAt(i, 2);
				String materialClass = (String)endMillTable.getValueAt(i, 10);
				double diametro = (Double)endMillTable.getValueAt(i, 3);
				double cuttingEdge = (Double)endMillTable.getValueAt(i, 5);
				double profundidade = (Double)endMillTable.getValueAt(i, 6);
				double offSet = (Double)endMillTable.getValueAt(i, 7);
				double dm = (Double)endMillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)endMillTable.getValueAt(i, 8);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				EndMill ferrTmp = new EndMill(nome, materialClass, diametro,
						cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);

				selectedTools.get(3).add(ferrTmp);

			}

		}
		for(int i = 0; i < ballEndMillTable.getRowCount(); i++){

			if((Boolean)(ballEndMillTable.getValueAt(i, 0))){

				String nome = (String)ballEndMillTable.getValueAt(i, 2);
				String materialClass = (String)ballEndMillTable.getValueAt(i, 12);
				double diametro = (Double)ballEndMillTable.getValueAt(i, 3);
				double edgeRadius = (Double)ballEndMillTable.getValueAt(i, 8);
				double edgeCenterVertical = (Double)ballEndMillTable.getValueAt(i, 9);
				double cuttingEdge = (Double)ballEndMillTable.getValueAt(i, 5);
				double profundidade = (Double)ballEndMillTable.getValueAt(i, 6);
				double offSet = (Double)ballEndMillTable.getValueAt(i, 7);
				double dm = (Double)ballEndMillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)ballEndMillTable.getValueAt(i, 10);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				BallEndMill ferrTmp = new BallEndMill(nome, materialClass, diametro,
						edgeRadius, edgeCenterVertical, cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);

				selectedTools.get(4).add(ferrTmp);

			}

		}
		for(int i = 0; i < bullnoseEndMillTable.getRowCount(); i++){

			if((Boolean)(bullnoseEndMillTable.getValueAt(i, 0))){

				String nome = (String)bullnoseEndMillTable.getValueAt(i, 2);
				String materialClass = (String)bullnoseEndMillTable.getValueAt(i, 13);
				double diametro = (Double)bullnoseEndMillTable.getValueAt(i, 3);
				double edgeRadius = (Double)bullnoseEndMillTable.getValueAt(i, 8);
				double edgeCenterVertical = (Double)bullnoseEndMillTable.getValueAt(i, 9);
				double edgeCenterHorizontal = (Double)bullnoseEndMillTable.getValueAt(i, 10);
				double cuttingEdge = (Double)bullnoseEndMillTable.getValueAt(i, 5);
				double profundidade = (Double)bullnoseEndMillTable.getValueAt(i, 6);
				double offSet = (Double)bullnoseEndMillTable.getValueAt(i, 7);
				double dm = (Double)bullnoseEndMillTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)bullnoseEndMillTable.getValueAt(i, 11);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				BullnoseEndMill ferrTmp = new BullnoseEndMill(nome, materialClass, diametro,
						edgeRadius, edgeCenterVertical, edgeCenterHorizontal, cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand);


				selectedTools.get(5).add(ferrTmp);

			}

		}
		for(int i = 0; i < boringToolTable.getRowCount(); i++){

			if((Boolean)(boringToolTable.getValueAt(i, 0))){

				String nome = (String)boringToolTable.getValueAt(i, 2);
				String materialClass = (String)boringToolTable.getValueAt(i, 11);
				String diametro = (String)boringToolTable.getValueAt(i, 3);
				double edgeRadius = (Double)boringToolTable.getValueAt(i, 8);
				double cuttingEdge = (Double)boringToolTable.getValueAt(i, 5);
				double profundidade = (Double)boringToolTable.getValueAt(i, 6);
				double offSet = (Double)boringToolTable.getValueAt(i, 7);
				double dm = (Double)boringToolTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)boringToolTable.getValueAt(i, 9);
				String acoplamento = (String)boringToolTable.getValueAt(i, 12);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				BoringTool ferrTmp = new BoringTool(nome, materialClass, diametro,
						edgeRadius, cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand, acoplamento);

				selectedTools.get(6).add(ferrTmp);

			}

		}
		for(int i = 0; i < reamerTable.getRowCount(); i++){

			if((Boolean)(reamerTable.getValueAt(i, 0))){

				String nome = (String)reamerTable.getValueAt(i, 2);
				String materialClass = (String)reamerTable.getValueAt(i, 11);
				double diametro = (Double)reamerTable.getValueAt(i, 3);
				double cuttingEdge = (Double)reamerTable.getValueAt(i, 5);
				double profundidade = (Double)reamerTable.getValueAt(i, 6);
				double offSet = (Double)reamerTable.getValueAt(i, 7);
				double dm = (Double)reamerTable.getValueAt(i, 4);
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				String handOfCut = (String)reamerTable.getValueAt(i, 9);
				int numberOfTeeth = (Integer)reamerTable.getValueAt(i, 8);

				int hand = 0;

				if(handOfCut.equals("Right"))
					hand = Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
					hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
					hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand Of Cut Desconhecido = " + handOfCut);


				Reamer ferrTmp = new Reamer(nome, materialClass, diametro,
						cuttingEdge, profundidade, offSet, dm,
						rugosidade, tolerancia, hand, numberOfTeeth);

				selectedTools.get(7).add(ferrTmp);
			}
		}
		this.projectTools.setSelectedTools(selectedTools);
		this.projectTools.carregarSelecionadasNoPanel();
	}

	public void actionPerformed(ActionEvent event) {

		Object origin = event.getSource();

		if (origin.equals(this.addAutomatic)){

			//Verificar  meia duplicaçao estranha
			
			this.toolManager = new ToolManager(this.janela.getProjeto(), this);

			MapeadoraDeWorkingsteps mapeadora = new MapeadoraDeWorkingsteps(this.janela.getProjeto());

			this.projectTools.addToolsFromProject();
			
			this.dispose();

		} else if(origin == this.addSelected)
		{
			this.adicionarFerramentasSelecionadas();
			
		} else if(origin == this.buttonSelectAll)
		{
			for(int i = 0; i < this.reamerTable.getRowCount(); i++)
			{
				this.reamerTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll2)
		{
			for(int i = 0; i < this.twistDrillTable.getRowCount(); i++)
			{
				this.twistDrillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll3)
		{
			for(int i = 0; i < this.centerDrillTable.getRowCount(); i++)
			{
				this.centerDrillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll4)
		{
			for(int i = 0; i < this.faceMillTable.getRowCount(); i++)
			{
				this.faceMillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll5)
		{
			for(int i = 0; i < this.endMillTable.getRowCount(); i++)
			{
				this.endMillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll6)
		{
			for(int i = 0; i < this.ballEndMillTable.getRowCount(); i++)
			{
				this.ballEndMillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll7)
		{
			for(int i = 0; i < this.bullnoseEndMillTable.getRowCount(); i++)
			{
				this.bullnoseEndMillTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectAll8)
		{
			for(int i = 0; i < this.boringToolTable.getRowCount(); i++)
			{
				this.boringToolTable.setValueAt(true, i, 0);
			}
		} else if(origin == this.buttonSelectNone)
		{
			for(int i = 0; i < this.reamerTable.getRowCount(); i++)
			{
				this.reamerTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone2)
		{
			for(int i = 0; i < this.twistDrillTable.getRowCount(); i++)
			{
				this.twistDrillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone3)
		{
			for(int i = 0; i < this.centerDrillTable.getRowCount(); i++)
			{
				this.centerDrillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone4)
		{
			for(int i = 0; i < this.faceMillTable.getRowCount(); i++)
			{
				this.faceMillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone5)
		{
			for(int i = 0; i < this.endMillTable.getRowCount(); i++)
			{
				this.endMillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone6)
		{
			for(int i = 0; i < this.ballEndMillTable.getRowCount(); i++)
			{
				this.ballEndMillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone7)
		{
			for(int i = 0; i < this.bullnoseEndMillTable.getRowCount(); i++)
			{
				this.bullnoseEndMillTable.setValueAt(false, i, 0);
			}
		} else if(origin == this.buttonSelectNone8)
		{
			for(int i = 0; i < this.boringToolTable.getRowCount(); i++)
			{
				this.boringToolTable.setValueAt(false, i, 0);
			}
		}
	}

	//	public void actionPerformed(ActionEvent event) {
	//
	//		Object origin = event.getSource();
	//
	//		if (origin.equals(buttonValidarFerramentas)){
	//			
	//			boolean answer = this.manager.validateTools();
	//
	//			if (answer) {
	//				
	//				manager.setFerramentasAceitas();
	//				
	//				
	//			}else {
	//				System.out.println(manager.getMessage());
	//				JOptionPane.showMessageDialog(null, manager.getMessage());
	//			}
	//		}
	//		
	//		if (origin.equals(okButton)) {
	//
	//			boolean answer = this.manager.validateTools();
	//
	//			if (answer) {
	//				
	//				manager.setFerramentasAceitas();				
	//				
	//				CAPP capp = new CAPP(manager.getProject());
	//				
	//				manager.getJanelaPrincipal().setCapp(capp);
	//				
	//				//GERAR ARQUIVO FISICO
	//				StepNcProject stepNcProject;
	//				try {
	//					stepNcProject = new StepNcProject(capp.vetorDeWorkingSteps, manager.getProject());
	//					JanelaCodigoSTEP_view janelaArqFisico = new JanelaCodigoSTEP_view();
	//					janelaArqFisico.editTextArea(HeaderUtil.changeHeader(stepNcProject.createSTEP21File(), "GRIMA"));
	//					janelaArqFisico.setVisible(true);
	//				} catch (SdaiException e) {
	//					// TODO Auto-generated catch block
	//					e.printStackTrace();
	//				}
	//				
	//				
	//				this.dispose();
	//			} else {
	//				System.out.println(manager.getMessage());
	//				JOptionPane.showMessageDialog(null, manager.getMessage());
	//			}
	//
	//		}
	//		
	//	}
	//
	//	@Override
	//	public Component getTableCellRendererComponent(JTable table, Object value,
	//			boolean isSelected, boolean hasFocus, int row, int column) {
	//		
	////		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	////		tableFresas.setDefaultRenderer(String.class, renderer);
	//		
	//		System.out.println(tableFresas.getCellRenderer(3,1));
	//		System.out.println(tableFresas.getCellRenderer(3,2));
	//		
	//		return tableFresas.getCellRenderer(row,column).getTableCellRendererComponent(tableFresas, value, isSelected, hasFocus, row, column);  
	//
	//	}

}
