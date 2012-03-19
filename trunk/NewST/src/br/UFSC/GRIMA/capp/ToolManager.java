package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import javax.swing.JTable;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.cad.ProjectTools;
import br.UFSC.GRIMA.cad.SelectToolPanel;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.exceptions.ProjetoInvalidoException;
import br.UFSC.GRIMA.util.ToolReader;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ToolManager {

	private Projeto projeto;
	private ToolReader toolReader = new ToolReader();
	private SelectToolPanel toolPanel;
	private ProjectTools projectTools;

	private static ArrayList<TwistDrill> twistDrills;
	private static ArrayList<CenterDrill> centerDrills;
	private static ArrayList<FaceMill> faceMills;
	private static ArrayList<EndMill> endMills;
	private static ArrayList<BallEndMill> ballEndMills;
	private static ArrayList<BullnoseEndMill> bullnoseEndMills;
	private static ArrayList<BoringTool> boringTools;
	private static ArrayList<Reamer> reamers;
	
	private static boolean toolManagerDone = false;
	
	public ToolManager(Projeto projeto, ProjectTools projectTools)
	{
		this.projeto = projeto;
		this.projeto.setTwistDrills(createTwistDrills(projectTools.twistDrillTable));
		this.projeto.setCenterDrills(createCenterDrills(projectTools.centerDrillTable));
		this.projeto.setFaceMills(createFaceMills(projectTools.faceMillTable));
		this.projeto.setEndMills(createEndMills(projectTools.endMillTable));
		this.projeto.setBallEndMills(createBallEndMills(projectTools.ballEndMillTable));
		this.projeto.setBullnoseEndMills(createBullnoseEndMills(projectTools.bullnoseEndMillTable));
		this.projeto.setBoringTools(createBoringTools(projectTools.boringToolTable));
		this.projeto.setReamers(createReamers(projectTools.reamerTable));
//		System.out.println("twist = " + createTwistDrills(projectTools.twistDrillTable));
		
		this.projeto.setConn(toolReader.getConn());
		this.projeto.setStatement(toolReader.getStatement());

		toolManagerDone = true;
	}


	public ToolManager(Projeto projeto) {

		this.projeto = projeto;
		
			System.out.println("criando twist drills");
			this.twistDrills = createTwistDrills();
			System.out.println("criando center drills");
			this.centerDrills = createCenterDrills();
			System.out.println("criando face mills");
			this.faceMills = createFaceMills();
			System.out.println("criando end mills");
			this.endMills = createEndMills();
			System.out.println("criando ball end mills");
			this.ballEndMills = createBallEndMills();
			System.out.println("criando bullnose end mills");
			this.bullnoseEndMills = createBullnoseEndMills();
			System.out.println("criando boring tools");
			this.boringTools = createBoringTools();
			System.out.println("criando reamers");
			this.reamers = createReamers();
			
			this.projeto.setTwistDrills(this.twistDrills);
			this.projeto.setCenterDrills(this.centerDrills);
			this.projeto.setFaceMills(this.faceMills);
			this.projeto.setEndMills(this.endMills);
			this.projeto.setBallEndMills(this.ballEndMills);
			this.projeto.setBullnoseEndMills(this.bullnoseEndMills);
			this.projeto.setBoringTools(this.boringTools);
			this.projeto.setReamers(this.reamers);
			
			this.projeto.setConn(toolReader.getConn());
			this.projeto.setStatement(toolReader.getStatement());
			
			toolManagerDone = true;
		
	}

	public ToolManager(Projeto projeto, SelectToolPanel toolPanel ) {

		this.toolPanel = toolPanel;
		this.projeto = 	projeto;
		
		System.out.println("criando twist drills");
		twistDrills = createTwistDrills();
		System.out.println("criando center drills");
		centerDrills = createCenterDrills();
		System.out.println("criando face mills");
		faceMills = createFaceMills();
		System.out.println("criando end mills");
		endMills = createEndMills();
		System.out.println("criando ball end mills");
		ballEndMills = createBallEndMills();
		System.out.println("criando bullnose end mills");
		bullnoseEndMills = createBullnoseEndMills();
		System.out.println("criando boring tools");
		boringTools = createBoringTools();
		System.out.println("criando reamers");
		reamers = createReamers();
		
		
		this.projeto.setConn(toolReader.getConn());
		this.projeto.setStatement(toolReader.getStatement());
	
		toolManagerDone = true;
	}

	public ToolManager() {

		System.out.println("criando twist drills");
		twistDrills = createTwistDrills();
		System.out.println("criando center drills");
		centerDrills = createCenterDrills();
		System.out.println("criando face mills");
		faceMills = createFaceMills();
		System.out.println("criando end mills");
		endMills = createEndMills();
		System.out.println("criando ball end mills");
		ballEndMills = createBallEndMills();
		System.out.println("criando bullnose end mills");
		bullnoseEndMills = createBullnoseEndMills();
		System.out.println("criando boring tools");
		boringTools = createBoringTools();
		System.out.println("criando reamers");
		reamers = createReamers();
		
		toolManagerDone = true;
	}
	
	public void addFerramenta(Ferramenta ferramenta) {

		if (ferramenta.getClass() == TwistDrill.class) {
			twistDrills.add((TwistDrill) ferramenta);
		} else if (ferramenta.getClass() == CenterDrill.class) {
			centerDrills.add((CenterDrill) ferramenta);
		} else if (ferramenta.getClass() == FaceMill.class) {
			faceMills.add((FaceMill) ferramenta);
		} else if (ferramenta.getClass() == EndMill.class) {
			endMills.add((EndMill) ferramenta);
		} else if (ferramenta.getClass() == BallEndMill.class) {
			ballEndMills.add((BallEndMill) ferramenta);
		} else if (ferramenta.getClass() == BullnoseEndMill.class) {
			bullnoseEndMills.add((BullnoseEndMill) ferramenta);
		} else if (ferramenta.getClass() == BoringTool.class) {
			boringTools.add((BoringTool) ferramenta);
		} else if (ferramenta.getClass() == Reamer.class) {
			reamers.add((Reamer) ferramenta);
		} else {
			System.out.println("FERRAMENTA DESCONHECIDA!!! CLASSE: "
					+ ferramenta.getClass());
		}

	}

//	public ArrayList<TwistDrill> createTwistDrills() {
//		twistDrills = new ArrayList<TwistDrill>();
//		toolReader.setFerramentaTable("TwistDrill");
//
//		for (int id = 1; id <= toolReader.getSize(); id++) {
//
//			char[] materials = toolReader.getPropertie("Material_Ferramenta",
//					id).toCharArray();
//
//			for (int i = 0; i < materials.length; i++) {
//
//				String nome = toolReader.getPropertie("Nome", id);
//				String material = materials[i] + "";
//				String diametro = toolReader.getPropertie("Diametro", id);
//				String tipAngle = toolReader.getPropertie(
//						"Tool_Tip_Half_Angle", id);
//				String cuttingEdge = toolReader.getPropertie(
//						"Cutting_Edge_Length", id);
//				String profundidade = toolReader.getPropertie("Profundidade",
//						id);
//				String offSetLength = toolReader.getPropertie("Off_Set_Length",
//						id);
//				String dm = toolReader.getPropertie("Dm", id);
//				String rugosidade = toolReader.getPropertie("rugosidade", id);
//				String tolerancia = toolReader.getPropertie("tolerancia", id);
//				String handOfCut = toolReader.getPropertie("Hand_Of_Cut", id);
//
//				TwistDrill twistDrill = new TwistDrill(nome,
//						material, diametro, tipAngle, cuttingEdge,
//						profundidade, offSetLength, dm, rugosidade, tolerancia,
//						handOfCut);
//
//				twistDrills.add(twistDrill);
//			}
//		}
//
//		return twistDrills;
//	}

	private ArrayList<CenterDrill> createCenterDrills(JTable table) 
	{
		centerDrills = new ArrayList<CenterDrill>();
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			double tipAngle = (Double) table.getValueAt(linha, 7);
			String handOfCut = (String) table.getValueAt(linha, 8);
			String material = (String) table.getValueAt(linha, 9);
			String materialClass = (String) table.getValueAt(linha, 10);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

			CenterDrill centerDrill = new CenterDrill(nome, materialClass, diametro, tipAngle,
					cuttingEdge, profundidade, offSetLength, dm, rugosidade,
					tolerancia, hand);

			centerDrills.add(centerDrill);
		}

		return centerDrills;
	}
	private ArrayList<TwistDrill> createTwistDrills(JTable table) 
	{
		twistDrills = new ArrayList<TwistDrill>();		
		for (int linha = 0; linha < table.getRowCount(); linha++) {

				String nome = (String) table.getValueAt(linha, 1);
				double diametro = (Double) table.getValueAt(linha, 2);
				double dm = (Double)table.getValueAt(linha, 3);
				double cuttingEdge = (Double)table.getValueAt(linha, 4);
				double profundidade = (Double)table.getValueAt(linha, 5);
				double offSetLength = (Double)table.getValueAt(linha, 6);
				double tipAngle = (Double)table.getValueAt(linha, 7);
				String handOfCut = (String) table.getValueAt(linha, 8);
				String material = (String) table.getValueAt(linha, 9);
				String materialClass = (String) table.getValueAt(linha, 10);
				
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				
				int hand = 0;
				
				if(handOfCut.equals("Right"))
				hand =  Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
				TwistDrill twistDrill = new TwistDrill(nome ,
						materialClass, diametro, tipAngle, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand);

				twistDrills.add(twistDrill);
		}
		return twistDrills;
	}
	private ArrayList<FaceMill> createFaceMills(JTable table) 
	{
		faceMills = new ArrayList<FaceMill>();
		for (int linha = 0; linha < table.getRowCount() ; linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			String handOfCut = (String) table.getValueAt(linha, 7);
			String material = (String) table.getValueAt(linha, 8);
			String materialClass = (String) table.getValueAt(linha, 9);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				FaceMill faceMill = new FaceMill(nome ,
						materialClass, diametro, cuttingEdge, profundidade,
						offSetLength, dm, rugosidade, tolerancia, hand);
				faceMill.setNumberOfTeeth(4);

				faceMills.add(faceMill);
			}

		return faceMills;
	}
	private ArrayList<EndMill> createEndMills(JTable table) 
	{
		endMills = new ArrayList<EndMill>();
		
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			String handOfCut = (String) table.getValueAt(linha, 7);
			String material = (String) table.getValueAt(linha, 8);
			String materialClass = (String) table.getValueAt(linha, 9);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				EndMill endMill = new EndMill(nome, materialClass,
						diametro, cuttingEdge, profundidade, offSetLength, dm,
						rugosidade, tolerancia, hand);
				endMill.setNumberOfTeeth(4);
				
				endMills.add(endMill);
			}
		return endMills;
	}
	private ArrayList<BallEndMill> createBallEndMills(JTable table) 
	{
		ballEndMills = new ArrayList<BallEndMill>();
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			double edgeRadius = (Double) table.getValueAt(linha, 7);
			double edgeCenterVertical = (Double) table.getValueAt(linha, 8);
			String handOfCut = (String) table.getValueAt(linha, 9);
			String material = (String) table.getValueAt(linha, 10);
			String materialClass = (String) table.getValueAt(linha, 11);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				BallEndMill ballEndMill = new BallEndMill(
						nome, materialClass, diametro, edgeRadius,
						edgeCenterVertical, cuttingEdge, profundidade,
						offSetLength, dm, rugosidade, tolerancia, hand);
				ballEndMill.setNumberOfTeeth(4);
				
				ballEndMills.add(ballEndMill);
			}
		return ballEndMills;
	}
	private ArrayList<BullnoseEndMill> createBullnoseEndMills(JTable table) 
	{
		bullnoseEndMills = new ArrayList<BullnoseEndMill>();
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			double edgeRadius = (Double) table.getValueAt(linha, 7);
			double edgeCenterVertical = (Double) table.getValueAt(linha, 8);
			double edgeCenterHorizontal = (Double) table.getValueAt(linha, 9);
			String handOfCut = (String) table.getValueAt(linha, 10);
			String material = (String) table.getValueAt(linha, 11);
			String materialClass = (String) table.getValueAt(linha, 12);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
			BullnoseEndMill bullnoseEndMill = new BullnoseEndMill(nome, materialClass, diametro, edgeRadius,
						edgeCenterVertical, edgeCenterHorizontal, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand);
				bullnoseEndMill.setNumberOfTeeth(4);

				bullnoseEndMills.add(bullnoseEndMill);
			}

		return bullnoseEndMills;
	}
	private ArrayList<BoringTool> createBoringTools(JTable table) 
	{
		boringTools = new ArrayList<BoringTool>();
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			String diametro = (String) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			double edgeRadius = (Double) table.getValueAt(linha, 7);
			String handOfCut = (String) table.getValueAt(linha, 8);
			String material = (String) table.getValueAt(linha, 9);
			String materialClass = (String) table.getValueAt(linha, 10);
			String acoplamento = (String) table.getValueAt(linha, 11);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
				BoringTool boringTool = new BoringTool(nome,
						materialClass, diametro, edgeRadius, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand, acoplamento);
				boringTool.setNumberOfTeeth(1);
				
				String dMaximo = diametro.substring(2);
				double diametroMaximo = Double.parseDouble(dMaximo);
				boringTool.setDiametroFerramenta(diametroMaximo);
				
				boringTools.add(boringTool);
			}

		return boringTools;
	}
	private ArrayList<Reamer> createReamers(JTable table) 
	{
		reamers = new ArrayList<Reamer>();
		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 1);
			double diametro = (Double) table.getValueAt(linha, 2);
			double dm = (Double) table.getValueAt(linha, 3);
			double cuttingEdge = (Double) table.getValueAt(linha, 4);
			double profundidade = (Double) table.getValueAt(linha, 5);
			double offSetLength = (Double) table.getValueAt(linha, 6);
			int numberOfTeeth = (Integer) table.getValueAt(linha, 7);
			String handOfCut = (String) table.getValueAt(linha, 8);
			String material = (String) table.getValueAt(linha, 9);
			String materialClass = (String) table.getValueAt(linha, 10);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				Reamer reamer = new Reamer(nome, materialClass,
						diametro, cuttingEdge, profundidade, offSetLength, dm,
						rugosidade, tolerancia, hand, numberOfTeeth);

				reamers.add(reamer);
			}
		return reamers;
	}
	
	
	
	public ArrayList<CenterDrill> createCenterDrills() {

		centerDrills = new ArrayList<CenterDrill>();

		JTable table = toolPanel.centerDrillTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			double tipAngle = (Double) table.getValueAt(linha, 8);
			String handOfCut = (String) table.getValueAt(linha, 9);
			String material = (String) table.getValueAt(linha, 10);
			String materialClass = (String) table.getValueAt(linha, 11);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

			CenterDrill centerDrill = new CenterDrill(nome, materialClass, diametro, tipAngle,
					cuttingEdge, profundidade, offSetLength, dm, rugosidade,
					tolerancia, hand);

			centerDrills.add(centerDrill);
		}

		return centerDrills;

	}

	public ArrayList<TwistDrill> createTwistDrills() {
		
		twistDrills = new ArrayList<TwistDrill>();
		
		JTable table = toolPanel.twistDrillTable;
		
		for (int linha = 0; linha < table.getRowCount(); linha++) {

				String nome = (String) table.getValueAt(linha, 2);
				double diametro = (Double) table.getValueAt(linha, 3);
				double dm = (Double)table.getValueAt(linha, 4);
				double cuttingEdge = (Double)table.getValueAt(linha, 5);
				double profundidade = (Double)table.getValueAt(linha, 6);
				double offSetLength = (Double)table.getValueAt(linha, 7);
				double tipAngle = (Double)table.getValueAt(linha, 8);
				String handOfCut = (String) table.getValueAt(linha, 9);
				String material = (String) table.getValueAt(linha, 10);
				String materialClass = (String) table.getValueAt(linha, 11);
				
				double rugosidade = 0.0;
				double tolerancia = 0.0;
				
				int hand = 0;
				
				if(handOfCut.equals("Right"))
				hand =  Ferramenta.RIGHT_HAND_OF_CUT;
				else if(handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
				else if(handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
				else
					System.out.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
				TwistDrill twistDrill = new TwistDrill(nome,
						materialClass, diametro, tipAngle, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand);

				twistDrills.add(twistDrill);
		}

		return twistDrills;
	}
	
	public ArrayList<FaceMill> createFaceMills() {

		faceMills = new ArrayList<FaceMill>();
		
		JTable table = toolPanel.faceMillTable;

		for (int linha = 0; linha < table.getRowCount() ; linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			String handOfCut = (String) table.getValueAt(linha, 8);
			String material = (String) table.getValueAt(linha, 9);
			String materialClass = (String) table.getValueAt(linha, 10);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				FaceMill faceMill = new FaceMill(nome,
						materialClass, diametro, cuttingEdge, profundidade,
						offSetLength, dm, rugosidade, tolerancia, hand);
				faceMill.setNumberOfTeeth(4);

				faceMills.add(faceMill);
			}

		return faceMills;
	}

	public ArrayList<EndMill> createEndMills() {
		
		endMills = new ArrayList<EndMill>();
		
		JTable table = toolPanel.endMillTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			String handOfCut = (String) table.getValueAt(linha, 8);
			String material = (String) table.getValueAt(linha, 9);
			String materialClass = (String) table.getValueAt(linha, 10);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				EndMill endMill = new EndMill(nome, materialClass,
						diametro, cuttingEdge, profundidade, offSetLength, dm,
						rugosidade, tolerancia, hand);
				endMill.setNumberOfTeeth(4);
				
				endMills.add(endMill);
			}

		return endMills;
	}

	public ArrayList<BallEndMill> createBallEndMills() {
		
		ballEndMills = new ArrayList<BallEndMill>();
		
		JTable table = toolPanel.ballEndMillTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			double edgeRadius = (Double) table.getValueAt(linha, 8);
			double edgeCenterVertical = (Double) table.getValueAt(linha, 9);
			String handOfCut = (String) table.getValueAt(linha, 10);
			String material = (String) table.getValueAt(linha, 11);
			String materialClass = (String) table.getValueAt(linha, 12);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				BallEndMill ballEndMill = new BallEndMill(
						nome, materialClass, diametro, edgeRadius,
						edgeCenterVertical, cuttingEdge, profundidade,
						offSetLength, dm, rugosidade, tolerancia, hand);
				ballEndMill.setNumberOfTeeth(4);
				
				ballEndMills.add(ballEndMill);
			}
	
		return ballEndMills;

	}

	public ArrayList<BullnoseEndMill> createBullnoseEndMills() {
		
		bullnoseEndMills = new ArrayList<BullnoseEndMill>();
		
		JTable table = toolPanel.bullnoseEndMillTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			double edgeRadius = (Double) table.getValueAt(linha, 8);
			double edgeCenterVertical = (Double) table.getValueAt(linha, 9);
			double edgeCenterHorizontal = (Double) table.getValueAt(linha, 10);
			String handOfCut = (String) table.getValueAt(linha, 11);
			String material = (String) table.getValueAt(linha, 12);
			String materialClass = (String) table.getValueAt(linha, 13);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
			BullnoseEndMill bullnoseEndMill = new BullnoseEndMill(nome, materialClass, diametro, edgeRadius,
						edgeCenterVertical, edgeCenterHorizontal, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand);
				bullnoseEndMill.setNumberOfTeeth(4);

				bullnoseEndMills.add(bullnoseEndMill);
			}

		return bullnoseEndMills;

	}

	public ArrayList<BoringTool> createBoringTools() {
		
		boringTools = new ArrayList<BoringTool>();
		
		JTable table = toolPanel.boringToolTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			String diametro = (String) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			double edgeRadius = (Double) table.getValueAt(linha, 8);
			String handOfCut = (String) table.getValueAt(linha, 9);
			String material = (String) table.getValueAt(linha, 10);
			String materialClass = (String) table.getValueAt(linha, 11);
			String acoplamento = (String) table.getValueAt(linha, 12);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);
				
				BoringTool boringTool = new BoringTool(nome,
						materialClass, diametro, edgeRadius, cuttingEdge,
						profundidade, offSetLength, dm, rugosidade, tolerancia,
						hand, acoplamento);
				boringTool.setNumberOfTeeth(1);
				
				String dMaximo = diametro.substring(2);
				double diametroMaximo = Double.parseDouble(dMaximo);
				boringTool.setDiametroFerramenta(diametroMaximo);
				
				boringTools.add(boringTool);
			}

		return boringTools;
	}

	public ArrayList<Reamer> createReamers() {
		
		reamers = new ArrayList<Reamer>();
		
		JTable table = toolPanel.reamerTable;

		for (int linha = 0; linha < table.getRowCount(); linha++) {

			String nome = (String) table.getValueAt(linha, 2);
			double diametro = (Double) table.getValueAt(linha, 3);
			double dm = (Double) table.getValueAt(linha, 4);
			double cuttingEdge = (Double) table.getValueAt(linha, 5);
			double profundidade = (Double) table.getValueAt(linha, 6);
			double offSetLength = (Double) table.getValueAt(linha, 7);
			int numberOfTeeth = (Integer) table.getValueAt(linha, 8);
			String handOfCut = (String) table.getValueAt(linha, 9);
			String material = (String) table.getValueAt(linha, 10);
			String materialClass = (String) table.getValueAt(linha, 11);

			double rugosidade = 0.0;
			double tolerancia = 0.0;

			int hand = 0;

			if (handOfCut.equals("Right"))
				hand = Ferramenta.RIGHT_HAND_OF_CUT;
			else if (handOfCut.equals("Left"))
				hand = Ferramenta.LEFT_HAND_OF_CUT;
			else if (handOfCut.equals("Neutral"))
				hand = Ferramenta.NEUTRAL_HAND_OF_CUT;
			else
				System.out
						.println("Hand of Cut Desconhecido!!! = " + handOfCut);

				Reamer reamer = new Reamer(nome, materialClass,
						diametro, cuttingEdge, profundidade, offSetLength, dm,
						rugosidade, tolerancia, hand, numberOfTeeth);

				reamers.add(reamer);
			}
	
		return reamers;

	}
	
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Projeto getProjeto() {
		return this.projeto;
	}
	public static ArrayList<TwistDrill> getTwistDrills() {
		return twistDrills;
	}

	public static void setTwistDrills(ArrayList<TwistDrill> twistDrills2) {
		twistDrills = twistDrills2;
	}

	public static ArrayList<CenterDrill> getCenterDrills() {
		return centerDrills;
	}

	public static void setCenterDrills(ArrayList<CenterDrill> centerDrills2) {
		centerDrills = centerDrills2;
	}

	public static ArrayList<FaceMill> getFaceMills() {
		return faceMills;
	}

	public static void setFaceMills(ArrayList<FaceMill> faceMills2) {
		faceMills = faceMills2;
	}

	public static ArrayList<EndMill> getEndMills() {
		return endMills;
	}

	public static void setEndMills(ArrayList<EndMill> endMills2) {
		endMills = endMills2;
	}

	public static ArrayList<BallEndMill> getBallEndMills() {
		return ballEndMills;
	}

	public static void setBallEndMills(ArrayList<BallEndMill> ballEndMills2) {
		ballEndMills = ballEndMills2;
	}

	public static ArrayList<BullnoseEndMill> getBullnoseEndMills() {
		return bullnoseEndMills;
	}

	public static void setBullnoseEndMills(ArrayList<BullnoseEndMill> bullnoseEndMills2) {
		bullnoseEndMills = bullnoseEndMills2;
	}

	public static ArrayList<Reamer> getReamers() {
		return reamers;
	}

	public static void setReamers(ArrayList<Reamer> reamers2) {
		reamers = reamers2;
	}

	public static ArrayList<BoringTool> getBoringTools() {
		return boringTools;
	}

	public static void setBoringTools(ArrayList<BoringTool> boringTools2) {
		boringTools = boringTools2;
	}

	public static boolean isToolManagerDone() {
		return toolManagerDone;
	}

	public static void setToolManagerDone(boolean toolManagerDone) {
		ToolManager.toolManagerDone = toolManagerDone;
	}
}
