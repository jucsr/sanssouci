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
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraFuroBasePlana {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private FuroBasePlana furoTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<TwistDrill> twistDrills;
	private ArrayList<CenterDrill> centerDrills;
	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;
	private ArrayList<BoringTool> boringTools;
	private ArrayList<Reamer> reamers;

	public MapeadoraFuroBasePlana(Projeto projeto) {

		this.projeto = projeto;
		this.bloco = projeto.getBloco();

	}

	public MapeadoraFuroBasePlana(Projeto projeto, Face face, FuroBasePlana furo) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.furoTmp = furo;
		this.bloco = projeto.getBloco();

		this.twistDrills = ToolManager.getTwistDrills();
		this.centerDrills = ToolManager.getCenterDrills();
		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		this.boringTools = ToolManager.getBoringTools();
		this.reamers = ToolManager.getReamers();

		this.mapearFuroBasePlana();
		

	}

	private void mapearFuroBasePlana() {

		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp = null;
		
		double retractPlane = 5;

		wssFeature = new Vector<Workingstep>();

		if (furoTmp.getTolerancia() <= Feature.LIMITE_RUGOSIDADE
				&& furoTmp.getRugosidade() <= Feature.LIMITE_TOLERANCIA) {
			furoTmp.setAcabamento(true);
		}
		
//		System.out.println(furoTmp.getFeaturePrecedente().equals(CircularBoss.class));

		if(furoTmp.getFeaturePrecedente()!= null){
			if(		furoTmp.getFeaturePrecedente().getClass().equals(GeneralProfileBoss.class) || 
					furoTmp.getFeaturePrecedente().getClass().equals(RectangularBoss.class) ||
					furoTmp.getFeaturePrecedente().getClass().equals(CircularBoss.class))
			{
				wsPrecedenteTmp = furoTmp.getFeaturePrecedente().getFeaturePrecedente().getWorkingsteps().lastElement();
			}
			else{
				wsPrecedenteTmp = furoTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
			}
			
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
						.getProfundidade() - Feature.LIMITE_DESBASTE)) {
					if (furoTmp.isPassante()) {

						double tipAngle = centerDrill.getToolTipHalfAngle()
						* Math.PI / 180.0;
						double raio = centerDrill.getDiametroFerramenta() / 2;
						double ponta = Math.tan(tipAngle) * raio;
						
						operation1.setCuttingDepth(furoTmp.getProfundidade()
								+ ponta);

					} else {
						operation1.setCuttingDepth(furoTmp.getProfundidade()
								- Feature.LIMITE_DESBASTE);
					}
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
				wsTmp.setId("FlatBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;
				
				wssFeature.add(wsTmp);
			}

			// FERRAMENTA
			TwistDrill twistDrill = chooseTwistDrill(bloco.getMaterial(),
					twistDrills, furoTmp, Feature.LIMITE_DESBASTE);

			// DRILLING
			Drilling operation2 = new Drilling("Drilling", retractPlane);
			operation2.setStartPoint(new Point3d(0, 0, 0));
			if (furoTmp.isPassante()) {

				double tipAngle = twistDrill.getToolTipHalfAngle() * Math.PI
						/ 180.0;
				double raio = twistDrill.getDiametroFerramenta() / 2;
				double ponta = Math.tan(tipAngle) * raio;

				operation2.setCuttingDepth(furoTmp.getProfundidade() + ponta);

			} else {
				operation2.setCuttingDepth(furoTmp.getProfundidade()
						- Feature.LIMITE_DESBASTE);
			}

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, twistDrill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, twistDrill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("FlatBottomHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			boolean usouFaceMill = false;

			if (furoTmp.getDiametro() - 2 * Feature.LIMITE_DESBASTE > twistDrill
					.getDiametroFerramenta()) {// Verifica se é necessário
				// utilizar uma Facemill

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, furoTmp, Feature.LIMITE_DESBASTE);

				usouFaceMill = true;

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation3.setAllowanceSide(Feature.LIMITE_DESBASTE);

				if (furoTmp.isPassante()) {
					operation3.setAllowanceBottom(0);
				} else {
					operation3.setAllowanceBottom(Feature.LIMITE_DESBASTE);
				}
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
				wsTmp.setId("FlatBottomHole_RGH");
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
			double ponta = 0;
			if (hasEqualsDiameter(furoTmp.getDiametro(), this.reamers, bloco.getMaterial())) {// Verifica
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
					wsTmp.setId("FlatBottomHole_FNS");
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

				if (usouFaceMill) {
					if (furoTmp.isPassante()) {
						operation5.setCuttingDepth(furoTmp.getProfundidade());
					} else {
						operation5.setCuttingDepth(furoTmp.getProfundidade()
								- Feature.LIMITE_DESBASTE);
					}
				} else {
					if (furoTmp.isPassante()) {
						operation5.setCuttingDepth(furoTmp.getProfundidade());
					} else {
						double tipAngle = twistDrill.getToolTipHalfAngle()
								* Math.PI / 180.0;
						double raio = twistDrill.getDiametroFerramenta() / 2;
						ponta = Math.tan(tipAngle) * raio;
						operation5.setCuttingDepth(furoTmp.getProfundidade()
								- Feature.LIMITE_DESBASTE - ponta);
					}
				}

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
				wsTmp.setId("FlatBottomHole_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			if (!furoTmp.isPassante() && (!usouReamer)) {
				// BOTTOM AND SIDE FINISH MILLING

				BottomAndSideFinishMilling operation6 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation6.setAllowanceBottom(0);
				operation6.setAllowanceSide(0);
				operation6.setCoolant(true);

				double x = 0;
				double y = 0;
				double z = furoTmp.getProfundidade() - ponta; // EM RELAÇAO A
																// FEATURE
																// (NORMA)
				Point3d startPoint = new Point3d(x, y, z);

				operation6.setStartPoint(startPoint);

				// FERRAMENTA
				EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
						furoTmp);
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, endMill, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, endMill,
						condicoesDeUsinagem, operation6);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("FlatBottomHole_FNS");
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

				double tipAngle = centerDrill.getToolTipHalfAngle()
				* Math.PI / 180.0;
				double raio = centerDrill.getDiametroFerramenta() / 2;
				double ponta = Math.tan(tipAngle) * raio;

				if (centerDrill.getCuttingEdgeLength() > (furoTmp
						.getProfundidade() + ponta)) {
					
					if (furoTmp.isPassante()) {
						operation1.setCuttingDepth(furoTmp.getProfundidade()
								+ ponta);
					} else {
						operation1.setCuttingDepth(furoTmp.getProfundidade());
					}
					
				}else{
					operation1.setCuttingDepth(centerDrill.getCuttingEdgeLength());
				}
				
				
				
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, centerDrill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, centerDrill,
						condicoesDeUsinagem, operation1);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;
				
				wssFeature.add(wsTmp);
			}

			// FERRAMENTA
			TwistDrill twistDrill = chooseTwistDrill(bloco.getMaterial(),
					twistDrills, furoTmp, 0);

			// DRILLING
			Drilling operation2 = new Drilling("Drilling", retractPlane);
			operation2.setStartPoint(new Point3d(0, 0, 0));

			if (furoTmp.isPassante()) {

				double tipAngle = twistDrill.getToolTipHalfAngle() * Math.PI
						/ 180.0;
				double raio = twistDrill.getDiametroFerramenta() / 2;
				double ponta = Math.tan(tipAngle) * raio;

				operation2.setCuttingDepth(furoTmp.getProfundidade() + ponta);

			} else {
				operation2.setCuttingDepth(furoTmp.getProfundidade());
			}

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, twistDrill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, twistDrill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("FlatBottomHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			boolean usouFaceMill = false;

			if (furoTmp.getDiametro() > twistDrill.getDiametroFerramenta()) {// Verifica
				// se
				// é
				// necessário
				// utilizar
				// uma
				// Facemill

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, furoTmp, 0);

				usouFaceMill = true;

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
				
				System.out.println("STAAAAART POINT :" + startPoint);
				
				operation3.setStartPoint(startPoint);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
								.getMaterial());

				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, faceMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}

			if (!usouFaceMill && !furoTmp.isPassante()) {// Obriga utilizar
															// facemill soh para
															// o desbaste
				// do fundo

				// FERRAMENTA
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, furoTmp, 0);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);
				operation3.setCoolant(true);

				double tipAngle = twistDrill.getToolTipHalfAngle() * Math.PI
						/ 180.0;
				double raio = twistDrill.getDiametroFerramenta() / 2;
				double ponta = Math.tan(tipAngle) * raio;

				double x = 0;
				double y = 0;
				double z = furoTmp.getProfundidade() - ponta;

				Point3d startPoint = new Point3d(x, y, z);

				operation3.setStartPoint(startPoint);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
								.getMaterial());

				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, faceMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("FlatBottomHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}
			furoTmp.setWorkingsteps(wssFeature);
		}
	}

	private CenterDrill chooseCenterDrill(Material material,
			ArrayList<CenterDrill> centerDrills, FuroBasePlana furoTmp,
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
		
		if (centerDrillsCandidatas.size() == 0) {

//			JOptionPane
//					.showMessageDialog(
//							null,
//							"Não é possível usinar esta Feature com as atuais Center Drills disponíveis! \n" +
//							"    Feature: Furo Base Plana \n" +
//							"    Nome: " + furoTmp.getNome() +"\n" +
//							"    Diametro: " + furoTmp.getDiametro()+"\n" +
//							"    Material Bloco: " + material.getName(),
//							"Erro", JOptionPane.ERROR_MESSAGE);


				JOptionPane
				.showMessageDialog(
						null,
						"Não é possível usinar esta Feature com as atuais Center Drills disponíveis! \n" +
						"__________________________________________________________"+"\n"+
						"\tFeature: Furo Base Plana \n" +
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
			throw new NullPointerException("Nenhuma Center Drill selecionada");

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
			ArrayList<TwistDrill> twistDrills, FuroBasePlana furoTmp,
			double limite_desbaste) {

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
					&& twistDrill.getDiametroFerramenta() <= (furoTmp
							.getDiametro() - 2 * limite_desbaste)
					&& twistDrill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade() - limite_desbaste)) {

				twistDrillsCandidatas.add(twistDrill);

			}

		}

		if (twistDrillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Twist Drills disponíveis! \n" +
//					"    Feature: Furo Base Plana \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Twist Drills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Twist Drills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro() - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade() - limite_desbaste) +
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
			ArrayList<FaceMill> faceMills, FuroBasePlana furoTmp,
			double limite_desbaste) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas ao furo

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (furoTmp
							.getDiametro() - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade() - limite_desbaste)) {

				faceMillsCandidatas.add(faceMill);

			}

		}

		if (faceMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
//					"    Feature: Furo Base Plana \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getDiametro() - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade() - limite_desbaste) +
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
			FuroBasePlana furoTmp) {

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
					&& reamerTmp.getDiametroFerramenta() == furoTmp
							.getDiametro()
					&& reamerTmp.getProfundidadeMaxima() >= furoTmp
							.getProfundidade()) {

				reamer = reamerTmp;

				break;

			}

		}

		if (reamer == null) {
			
//			JOptionPane
//					.showMessageDialog(
//							null,
//							"Não é possível fazer o acabamento deste Furo Base Plana com os Reamers atuais,\n " +
//							"    Utilizaremos uma Boring Tool. \n" +
//							"    Feature: Furo Base Plana \n" +
//							"    Nome: " + furoTmp.getNome() +"\n" +
//							"    Diametro: " + furoTmp.getDiametro()+"\n" +
//							"    Material Bloco: " + material.getName(),
//							"Reamer Não Disponível",
//							JOptionPane.WARNING_MESSAGE);
			
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com os atuais Reamers disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
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
			ArrayList<BoringTool> boringTools, FuroBasePlana furoTmp) {

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
					&& boringTool.getDiametroFerramenta() >= (furoTmp
							.getDiametro())
					&& boringTool.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade())) {

				boringToolsCandidatas.add(boringTool);

			}

		}

		if (boringToolsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com os atuais Boring Tools disponíveis! \n" +
//					"    Feature: Furo Base Plana \n" +
//					"    Nome: " + furoTmp.getNome() +"\n" +
//					"    Diametro: " + furoTmp.getDiametro()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com os atuais Boring Tools disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Base Plana \n" +
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
			ArrayList<EndMill> endMills, FuroBasePlana furoTmp) {

		ArrayList<EndMill> endMillsCandidatas = new ArrayList<EndMill>();

		EndMill endMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_EndMill");

		for (int i = 0; i < endMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas ao furo

			endMill = endMills.get(i);

			if (endMill.getMaterial().equals(ISO)
					&& endMill.getDiametroFerramenta() <= (furoTmp
							.getDiametro())
					&& endMill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade())) {

				endMillsCandidatas.add(endMill);

			}

		}

		if (endMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com os atuais End Mills disponíveis! \n" +
//					"    Feature: Furo Base Plana \n" +
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
			
			throw new NullPointerException("Nenhuma End Mill selecionada");

		}

		endMill = endMillsCandidatas.get(0);

		for (int i = 1; i < endMillsCandidatas.size(); i++) {// Seleciona a
			// face
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
/**
 *  Este método serve para...
 * @param diametro
 * @param reamers
 * @param material
 * @return
 */
	private boolean hasEqualsDiameter(double diametro, ArrayList<Reamer> reamers, Material material) {

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

}
