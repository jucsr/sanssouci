package br.UFSC.GRIMA.capp.mapeadoras;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraFuroBaseArredondada {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private FuroBaseArredondada furoTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<TwistDrill> twistDrills;
	private ArrayList<CenterDrill> centerDrills;
	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;
	private ArrayList<BallEndMill> ballEndMills;
	private ArrayList<BullnoseEndMill> bullnoseEndMills;
	private ArrayList<BoringTool> boringTools;
	private ArrayList<Reamer> reamers;

	public MapeadoraFuroBaseArredondada(Projeto projeto, Face face,
			FuroBaseArredondada furo) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.furoTmp = furo;
		this.bloco = projeto.getBloco();

		this.centerDrills = ToolManager.getCenterDrills();
		this.twistDrills = ToolManager.getTwistDrills();
		this.faceMills = ToolManager.getFaceMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		this.boringTools = ToolManager.getBoringTools();
		this.reamers = ToolManager.getReamers();
		this.endMills = ToolManager.getEndMills();
		this.bullnoseEndMills = ToolManager.getBullnoseEndMills();

		this.mapearFuroBaseArredondada();

	}

	private void mapearFuroBaseArredondada() {

		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp = null;
		
		double retractPlane = 5;
		double pontaEsferica = furoTmp.getR1();

		wssFeature = new Vector<Workingstep>();

		if (furoTmp.getTolerancia() <= Feature.LIMITE_RUGOSIDADE
				&& furoTmp.getRugosidade() <= Feature.LIMITE_TOLERANCIA) {
			furoTmp.setAcabamento(true);
		}

		if(furoTmp.getFeaturePrecedente()!= null){
			
			wsPrecedenteTmp = furoTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
			
		}else{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		
		if (furoTmp.isAcabamento()) {

			// WORKINGSTEPS DE DESBASTE

			if (furoTmp.getDiametro() >= 1.0) { // Verifica se é necessário
				// usar um CenterDrill

				// FERRAMENTA
				CenterDrill centerDrill = chooseCenterDrill(
						bloco.getMaterial(), centerDrills, furoTmp,
						Feature.LIMITE_DESBASTE);

				// CENTERDRILLING
				CenterDrilling operation1 = new CenterDrilling(
						"Center Drilling", retractPlane);

				operation1.setStartPoint(new Point3d(0, 0, 0));

				if (centerDrill.getCuttingEdgeLength() > (furoTmp
						.getProfundidade()
						+ pontaEsferica - Feature.LIMITE_DESBASTE)) {

					operation1.setCuttingDepth(furoTmp.getProfundidade()
							+ pontaEsferica - Feature.LIMITE_DESBASTE);
				} else {

					operation1.setCuttingDepth(centerDrill
							.getCuttingEdgeLength());
				}

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, centerDrill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, centerDrill,
						condicoesDeUsinagem, operation1);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}

			// FERRAMENTA
			TwistDrill twistDrill = chooseTwistDrill(bloco.getMaterial(),
					twistDrills, furoTmp, Feature.LIMITE_DESBASTE,
					pontaEsferica);

			// DRILLING
			Drilling operation2 = new Drilling("Drilling", retractPlane);
			operation2.setStartPoint(new Point3d(0, 0, 0));
			operation2.setCuttingDepth(furoTmp.getProfundidade()
					+ pontaEsferica - Feature.LIMITE_DESBASTE);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, twistDrill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, twistDrill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("FlatWithRadiusBottomHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			boolean usouFaceMill = false;

			if (furoTmp.getDiametro() - 2 * Feature.LIMITE_DESBASTE > twistDrill
					.getDiametroFerramenta() && (furoTmp.getDiametro() - 2*furoTmp.getR1()) >= 2.0) {// Verifica se é necessário
				// utilizar uma Facemill

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, furoTmp, Feature.LIMITE_DESBASTE, 0.0);

				usouFaceMill = true;

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation3.setAllowanceSide(Feature.LIMITE_DESBASTE);
				operation3.setAllowanceBottom(0);

				operation3.setCoolant(true);

				Point3d startPoint;

				if (((furoTmp.getRaio() - Feature.LIMITE_DESBASTE) - twistDrill
						.getDiametroFerramenta() / 2) >= faceMill
						.getDiametroFerramenta() / 2) {
					double x = 0;
					double y = twistDrill.getDiametroFerramenta() / 2;
					double z = 0;
					startPoint = new Point3d(x, y, z);
				} else {
					double x = 0;
					double y = (furoTmp.getRaio() - Feature.LIMITE_DESBASTE)
							- faceMill.getDiametroFerramenta() / 2;
					double z = 0;
					startPoint = new Point3d(x, y, z);
				}
				operation3.setStartPoint(startPoint);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
								.getMaterial());

				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, faceMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}

			// WORKINGSTEPS DE ACABAMENTO

			double previousDiameter = twistDrill.getDiametroFerramenta();

			if (usouFaceMill) {
				previousDiameter = furoTmp.getDiametro() - 2
						* Feature.LIMITE_DESBASTE;
			}

			boolean usouReamer = false;
			if (hasEqualsDiameter(furoTmp.getDiametro(), this.reamers, bloco
					.getMaterial())) {// Verifica
				// se
				// existe
				// reamer
				// com
				// o
				// mesmo
				// diametro
				// do
				// furo

				// REAMING
				Reaming operation4 = new Reaming("Reaming", retractPlane);
				operation4.setStartPoint(new Point3d(0, 0, 0));
				operation4.setPreviousDiameter(previousDiameter);
				operation4.setCuttingDepth(furoTmp.getProfundidade());

				// FERRAMENTA
				Reamer reamer = chooseReamer(bloco.getMaterial(), this.reamers,
						furoTmp);

				if (reamer != null) {

					usouReamer = true;

					// CONDIÇÕES DE USINAGEM
					condicoesDeUsinagem = MapeadoraDeWorkingsteps
							.getCondicoesDeUsinagem(this.projeto, reamer, bloco
									.getMaterial());
					// WORKINGSTEP
					wsTmp = new Workingstep(furoTmp, faceTmp, reamer,
							condicoesDeUsinagem, operation4);
					wsTmp.setTipo(Workingstep.ACABAMENTO);
					wsTmp.setId("FlatWithRadiusBottomHole_FNS");
					wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
					wsPrecedenteTmp = wsTmp;

					wssFeature.add(wsTmp);

				}
			}// else
			if (usouReamer == false) { // se nao foi possivel com reamer
				// utilizaremos um boringTool

				// BORING
				Boring operation5 = new Boring("Boring", retractPlane);

				operation5.setStartPoint(new Point3d(0, 0, 0));

				operation5.setPreviousDiameter(previousDiameter);

				operation5.setCuttingDepth(furoTmp.getProfundidade());

				// FERRAMENTA
				BoringTool boringTool = chooseBoringTool(bloco.getMaterial(),
						this.boringTools, furoTmp);
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, boringTool, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, boringTool,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("FlatWithRadiusBottomHole_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco
					.getMaterial(), bullnoseEndMills, furoTmp, pontaEsferica);

			if (bullnoseEndMill != null) {
				
				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation3 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);

				operation3.setStartPoint(new Point3d(0, 0, furoTmp
						.getProfundidade()));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill,
						bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, bullnoseEndMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("FlatWithRadiusBottomHole_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}else {// se nao utilizou Bull Nose, faremos
				// uma combinaçao de EndMill com BallEndMill

				// FERRAMENTA
				EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
						furoTmp, pontaEsferica);

				if (endMill != null) {

					// BOTTOM AND SIDE FINISH MILLING
					BottomAndSideFinishMilling operation4 = new BottomAndSideFinishMilling(
							"Bottom And Side Finish Milling", retractPlane);
					operation4.setAllowanceSide(0);
					operation4.setAllowanceBottom(0);

					operation4.setStartPoint(new Point3d(0, 0, furoTmp
							.getProfundidade()));

					// CONDIÇÕES DE USINAGEM
					condicoesDeUsinagem = MapeadoraDeWorkingsteps
							.getCondicoesDeUsinagem(this.projeto, endMill,
									bloco.getMaterial());
					// WORKINGSTEP
					wsTmp = new Workingstep(furoTmp, faceTmp, endMill,
							condicoesDeUsinagem, operation4);
					wsTmp.setTipo(Workingstep.ACABAMENTO);
					wsTmp.setId("FlatWithRadiusBottomHole_FNS");
					wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
					wsPrecedenteTmp = wsTmp;

					wssFeature.add(wsTmp);

				}

				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, furoTmp,
						pontaEsferica);

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation5 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);

				operation5.setStartPoint(new Point3d(0, 0, furoTmp
						.getProfundidade()));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("FlatWithRadiusBottomHole_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			furoTmp.setWorkingsteps(wssFeature);

		} else {

			// WORKINGSTEPS DE DESBASTE

			if (furoTmp.getDiametro() >= 1.0) { // Verifica se é necessário
				// usar um CenterDrill

				// FERRAMENTA
				CenterDrill centerDrill = chooseCenterDrill(
						bloco.getMaterial(), this.centerDrills, furoTmp, 0);

				// CENTERDRILLING
				CenterDrilling operation1 = new CenterDrilling(
						"Center Drilling", retractPlane);

				operation1.setStartPoint(new Point3d(0, 0, 0));

				if (centerDrill.getCuttingEdgeLength() > (furoTmp
						.getProfundidade() + pontaEsferica)) {
					operation1.setCuttingDepth(furoTmp.getProfundidade()
							+ pontaEsferica);
				} else {
					operation1.setCuttingDepth(centerDrill
							.getCuttingEdgeLength());
				}

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, centerDrill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, centerDrill,
						condicoesDeUsinagem, operation1);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}

			// FERRAMENTA
			TwistDrill twistDrill = chooseTwistDrill(bloco.getMaterial(),
					twistDrills, furoTmp, 0, pontaEsferica);

			// DRILLING
			Drilling operation2 = new Drilling("Drilling", retractPlane);
			operation2.setStartPoint(new Point3d(0, 0, 0));

			operation2.setCuttingDepth(furoTmp.getProfundidade());

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, twistDrill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, twistDrill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("FlatWithRadiusBottomHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			if (furoTmp.getDiametro() > twistDrill.getDiametroFerramenta()) {// Verifica
				// se
				// é
				// necessário
				// utilizar
				// uma
				// Facemill

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, furoTmp, 0, 0);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);
				operation3.setCoolant(true);

				Point3d startPoint;

				if ((furoTmp.getRaio() - twistDrill.getDiametroFerramenta() / 2) >= faceMill
						.getDiametroFerramenta() / 2) {
					double x = 0;
					double y = twistDrill.getDiametroFerramenta() / 2;
					double z = 0;
					startPoint = new Point3d(x, y, z);
				} else {
					double x = 0;
					double y = furoTmp.getRaio()
							- faceMill.getDiametroFerramenta() / 2;
					double z = 0;
					startPoint = new Point3d(x, y, z);
				}
				operation3.setStartPoint(startPoint);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
								.getMaterial());

				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, faceMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}

			boolean usouBullnose = false;

			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco
					.getMaterial(), bullnoseEndMills, furoTmp, pontaEsferica);

			if (bullnoseEndMill != null) {
				usouBullnose = true;
			}

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation3.setAllowanceSide(0);
			operation3.setAllowanceBottom(0);

			operation3.setStartPoint(new Point3d(0, 0, furoTmp
					.getProfundidade()));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill,
							bloco.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, bullnoseEndMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("FlatWithRadiusBottomHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			if (usouBullnose == false) {// se nao utilizou Bull Nose, faremos
				// uma combinaçao de FaceMill com BallEndMill

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
						furoTmp, 0.0, pontaEsferica);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation4 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation4.setAllowanceSide(0);
				operation4.setAllowanceBottom(0);

				operation4.setStartPoint(new Point3d(0, 0, furoTmp
						.getProfundidade()));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, faceMill,
						bloco.getMaterial());
				
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, faceMill,
						condicoesDeUsinagem, operation4);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);


				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, furoTmp,
						pontaEsferica);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation5 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);

				operation5.setStartPoint(new Point3d(0, 0, furoTmp
						.getProfundidade()));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatWithRadiusBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			furoTmp.setWorkingsteps(wssFeature);
		}

	}

	private CenterDrill chooseCenterDrill(Material material,
			ArrayList<CenterDrill> centerDrills, FuroBaseArredondada furoTmp,
			double limite_desbaste) {

		ArrayList<CenterDrill> centerDrillsCandidatas = new ArrayList<CenterDrill>();

		CenterDrill centerDrill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_CenterDrill");

		for (int i = 0; i < centerDrills.size(); i++) { // Seleciona todas as
			// center drills
			// candidatas ao furo

			centerDrill = centerDrills.get(i);

			if (centerDrill.getMaterial().equals(ISO)
					&& centerDrill.getDiametroFerramenta() <= (furoTmp
							.getDiametro() - 2 * limite_desbaste)) {

				centerDrillsCandidatas.add(centerDrill);
			}
		}
		if (centerDrills.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Center Drills disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Center Drills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Arredondada \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + furoTmp.getRaio()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Center Drills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro() - 2 * limite_desbaste)+" mm" +"\n" +
					"\tAdicione Center Drills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhum CenterDrill selecionado");

		}
		centerDrill = centerDrillsCandidatas.get(0);

		for (int i = 1; i < centerDrillsCandidatas.size(); i++) {// Seleciona a
			// center
			// drill de
			// maior
			// diametro

			if (centerDrillsCandidatas.get(i).getDiametroFerramenta() > centerDrill
					.getDiametroFerramenta()) {
				centerDrill = centerDrillsCandidatas.get(i);
			}

		}

		return centerDrill;
	}

	private TwistDrill chooseTwistDrill(Material material,
			ArrayList<TwistDrill> twistDrills, FuroBaseArredondada furoTmp,
			double limite_desbaste, double pontaEsferica) {

		ArrayList<TwistDrill> twistDrillsCandidatas = new ArrayList<TwistDrill>();

		TwistDrill twistDrill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_TwistDrill");

		for (int i = 0; i < twistDrills.size(); i++) { // Seleciona todas as
			// twist drills
			// candidatas ao furo

			twistDrill = twistDrills.get(i);

			if (twistDrill.getMaterial().equals(ISO)
					&& twistDrill.getDiametroFerramenta() <= (furoTmp	.getDiametro() - 2 * limite_desbaste)
					&& twistDrill.getProfundidadeMaxima() >= (furoTmp	.getProfundidade()	+ pontaEsferica - limite_desbaste)) {

				twistDrillsCandidatas.add(twistDrill);
			}
		}

		if (twistDrillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Twist Drills disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Twist Drills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Arredondada \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Twist Drills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro() - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade da Ferramenta deve ser maior igual a: " + (furoTmp	.getProfundidade()	+ pontaEsferica - limite_desbaste) +
					"\tAdicione Twist Drills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Twist Drill selecionada");

		}

		twistDrill = twistDrillsCandidatas.get(0);

		for (int i = 1; i < twistDrillsCandidatas.size(); i++) {// Seleciona a
			// twist
			// drill de
			// maior
			// diametro

			if (twistDrillsCandidatas.get(i).getDiametroFerramenta() > twistDrill
					.getDiametroFerramenta()) {
				twistDrill = twistDrillsCandidatas.get(i);
			}

		}

		return twistDrill;
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, FuroBaseArredondada furoTmp,
			double limite_desbaste, double pontaEsferica) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		
		double diamMenor = furoTmp.getDiametro() - 2 * pontaEsferica; // --- está estranho
		
		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas ao furo

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (diamMenor - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (furoTmp.getProfundidade())) {
				
				faceMillsCandidatas.add(faceMill);
			}
		}

		if (faceMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);
			
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Arredondada \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (diamMenor - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade()) +
					"\tAdicione Face Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Face Mill selecionada");

		}

		faceMill = faceMillsCandidatas.get(0);

		for (int i = 1; i < faceMillsCandidatas.size(); i++) {// Seleciona a
			// face
			// mill de
			// maior
			// diametro

			if (faceMillsCandidatas.get(i).getDiametroFerramenta() > faceMill
					.getDiametroFerramenta()) {
				faceMill = faceMillsCandidatas.get(i);
			}

		}

		return faceMill;
	}

	private Reamer chooseReamer(Material material, ArrayList<Reamer> reamers,
			FuroBaseArredondada furoTmp) {

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_Reamer");

		Reamer reamer = null;
		Reamer reamerTmp = null;

		for (int i = 0; i < reamers.size(); i++) { // Seleciona reamer que
			// possui o mesmo diametro
			// do furo

			reamerTmp = reamers.get(i);

			if (reamerTmp.getMaterial().equals(ISO)
					&& reamerTmp.getDiametroFerramenta() == furoTmp.getDiametro()
					&& reamerTmp.getProfundidadeMaxima() >= furoTmp.getProfundidade()) {

				reamer = reamerTmp;

				break;
			}
		}
		if (reamer == null) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível fazer o acabamento deste Furo Base Arredondada com os Reamers atuais,\n" +
//					"utilizaremos uma Boring Tool. \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName()+"\n" +
//					"    Reamer Não Disponível",
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com os atuais Reamers disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Arredondada\n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Reamers do projeto, nenhum satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser igual a: " + (furoTmp.getDiametro())+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade()) +
					"\tAdicione Reamers adequados ao projeto."
					,
					"Erro", JOptionPane.WARNING_MESSAGE);
		}

		return reamer;
	}

	private BoringTool chooseBoringTool(Material material,
			ArrayList<BoringTool> boringTools, FuroBaseArredondada furoTmp) {

		ArrayList<BoringTool> boringToolsCandidatas = new ArrayList<BoringTool>();

		BoringTool boringTool = null;

		double diametroMaximo = 0;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BoringTool");

		for (int i = 0; i < boringTools.size(); i++) { // Seleciona todas as
			// boring tools
			// candidatas ao furo

			boringTool = boringTools.get(i);

			String dMaximo = boringTool.getDiametro().substring(2);
			diametroMaximo = Double.parseDouble(dMaximo);
			boringTool.setDiametroFerramenta(diametroMaximo);

			if (boringTool.getMaterial().equals(ISO)
					&& boringTool.getDiametroFerramenta() >= (furoTmp.getDiametro())
					&& boringTool.getProfundidadeMaxima() >= (furoTmp.getProfundidade())) {

				boringToolsCandidatas.add(boringTool);
			}
		}

		if (boringToolsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Boring Tools disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com os atuais Boring Tools disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Arredondada \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Boring Tools do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro máximo do Boring Tool deve ser maior igual a: " + (furoTmp.getDiametro())+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade()) +
					"\tAdicione Boring Tools adequados ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhum Boring Tool selecionado");

		}

		boringTool = boringToolsCandidatas.get(0);

		for (int i = 1; i < boringToolsCandidatas.size(); i++) {// Seleciona a
			// boring
			// tool de
			// menor
			// diametro maximo

			if (boringToolsCandidatas.get(i).getDiametroFerramenta() < boringTool
					.getDiametroFerramenta()) {
				boringTool = boringToolsCandidatas.get(i);
			}

		}

		return boringTool;
	}

	private EndMill chooseEndMill(Material material,
			ArrayList<EndMill> endMills, FuroBaseArredondada furoTmp,
			double pontaEsferica) {

		ArrayList<EndMill> endMillsCandidatas = new ArrayList<EndMill>();

		EndMill endMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_EndMill");

		double diamMenor = furoTmp.getDiametro() - 2 * pontaEsferica;

		for (int i = 0; i < endMills.size(); i++) { // Seleciona todas as
			// end mills
			// candidatas ao furo

			endMill = endMills.get(i);

			if (endMill.getMaterial().equals(ISO)
					&& endMill.getDiametroFerramenta() <= diamMenor && endMill.getProfundidadeMaxima() >= (furoTmp.getProfundidade() + pontaEsferica)) {
				
				endMillsCandidatas.add(endMill);
			}
		}

		if (endMillsCandidatas.size() == 0) {
			return null;
		}

		endMill = endMillsCandidatas.get(0);

		for (int i = 1; i < endMillsCandidatas.size(); i++) {// Seleciona a
			// end
			// mill de
			// maior
			// diametro

			if (endMillsCandidatas.get(i).getDiametroFerramenta() > endMill
					.getDiametroFerramenta()) {
				endMill = endMillsCandidatas.get(i);
			}

		}

		return endMill;
	}

	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, FuroBaseArredondada furoTmp,
			double pontaEsferica) {

		ArrayList<BallEndMill> ballEndMillsCandidatas = new ArrayList<BallEndMill>();

		BallEndMill ballEndMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BallEndMill");

		for (int i = 0; i < ballEndMills.size(); i++) { // Seleciona todas as
			// ball end mills
			// candidatas ao furo

			ballEndMill = ballEndMills.get(i);

			if (ballEndMill.getMaterial().equals(ISO)
					&& ballEndMill.getDiametroFerramenta() <= (furoTmp
							.getDiametro())
					&& ballEndMill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade() + pontaEsferica)
					&& ballEndMill.getEdgeRadius() <= pontaEsferica) {

				ballEndMillsCandidatas.add(ballEndMill);
			}
		}

		if (ballEndMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro())+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade()) +
					"\tAdicione Face Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("Nenhuma Ball End Mill selecionada");

		}

		ballEndMill = ballEndMillsCandidatas.get(0);

		for (int i = 1; i < ballEndMillsCandidatas.size(); i++) {// Seleciona a
			// ball end
			// mill de
			// maior
			// diametro

			if (ballEndMillsCandidatas.get(i).getDiametroFerramenta() > ballEndMill
					.getDiametroFerramenta()) {
				ballEndMill = ballEndMillsCandidatas.get(i);
			}

		}

		return ballEndMill;

	}

	private BullnoseEndMill chooseBullnoseEndMill(Material material,
			ArrayList<BullnoseEndMill> bullnoseEndMills,
			FuroBaseArredondada furoTmp, double pontaEsferica) {

		BullnoseEndMill bullnoseEndMill = null;

		ArrayList<BullnoseEndMill> bullnoseCandidatas = new ArrayList<BullnoseEndMill>();

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BullnoseEndMill");

		for (int i = 0; i < bullnoseEndMills.size(); i++) { // Seleciona todas
			// as
			// bull nose
			// candidatas ao furo

			bullnoseEndMill = bullnoseEndMills.get(i);

			if (bullnoseEndMill.getMaterial().equals(ISO)
					&& bullnoseEndMill.getDiametroFerramenta() <= (furoTmp.getDiametro())
					&& bullnoseEndMill.getProfundidadeMaxima() >= (furoTmp.getProfundidade() + pontaEsferica)
					&& bullnoseEndMill.getEdgeRadius() <= furoTmp.getR1()
					&& bullnoseEndMill.getEdgeCenterHorizontal() * 2 <= (furoTmp	.getDiametro() - furoTmp.getR1() * 2)) {

				bullnoseCandidatas.add(bullnoseEndMill);

			}

		}

		
		if (bullnoseCandidatas.size() == 0) {
			
//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Bull Noses disponíveis! \n" +
//					"    Feature: Furo Base Arredondada \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Bull Nose End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Bull Nose End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro())+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade() + pontaEsferica) +
					"\tRaio de arredondamento (EdgeRadius) " + furoTmp.getR1() + 
					"    " +
					"\tAdicione Bull Nose End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("Nenhuma Bullnose selecionada");

//			return null;
		}

		bullnoseEndMill = bullnoseCandidatas.get(0);

		for (int i = 1; i < bullnoseCandidatas.size(); i++) {// Seleciona a
			// bull
			// nose de
			// maior
			// diametro

			if (bullnoseCandidatas.get(i).getDiametroFerramenta() > bullnoseEndMill
					.getDiametroFerramenta()) {
				bullnoseEndMill = bullnoseCandidatas.get(i);
			}

		}

		return bullnoseEndMill;
	}

	private boolean hasEqualsDiameter(double diametro,
			ArrayList<Reamer> reamers, Material material) {

		boolean answer = false;

		String ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(
				this.projeto, material, "Condicoes_De_Usinagem_Reamer");

		for (int i = 0; i < reamers.size(); i++) {

			if (reamers.get(i).getDiametroFerramenta() == diametro
					&& reamers.get(i).getMaterial().equals(ISO))
				answer = true;

		}

		return answer;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public Face getFaceTmp() {
		return faceTmp;
	}

	public void setFaceTmp(Face faceTmp) {
		this.faceTmp = faceTmp;
	}

}
