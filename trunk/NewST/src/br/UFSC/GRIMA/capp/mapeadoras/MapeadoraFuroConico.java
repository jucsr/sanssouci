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
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraFuroConico {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private FuroConico furoTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<TwistDrill> twistDrills;
	private ArrayList<CenterDrill> centerDrills;
	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;
	private ArrayList<BallEndMill> ballEndMills;

	public MapeadoraFuroConico(Projeto projeto, Face face, FuroConico furo) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.furoTmp = furo;
		this.bloco = projeto.getBloco();

		this.twistDrills = ToolManager.getTwistDrills();
		this.centerDrills = ToolManager.getCenterDrills();
		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		this.ballEndMills = ToolManager.getBallEndMills();

		this.mapearFuroConico();

	}

	private void mapearFuroConico() {

		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp = null;
		
		double retractPlane = 5;

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
				wsTmp.setId("ConicalHole_RGH");
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
			wsTmp.setId("ConicalHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
					faceMills, furoTmp, Feature.LIMITE_DESBASTE);

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
			wsTmp.setId("ConicalHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

				
			// WORKINGSTEPS DE ACABAMENTO

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(),
					ballEndMills, furoTmp, false);

			// BOTTOM AND SIDE FINISH MILLING
			BottomAndSideFinishMilling operation4 = new BottomAndSideFinishMilling(
					"Bottom And Side Finish Milling", retractPlane);
			operation4.setAllowanceSide(0);
			operation4.setAllowanceBottom(0);

			operation4.setStartPoint(new Point3d(0, 0, 0));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation4);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("ConicalHole_FNS");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			if (!furoTmp.isPassante()) {
				
				// FERRAMENTA
				BallEndMill ballEndMill2 = chooseBallEndMill(bloco.getMaterial(),
						ballEndMills, furoTmp, true);

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation5 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				operation5.setStartPoint(new Point3d(0, 0, ballEndMill.getDiametroFerramenta()/2));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill2,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("ConicalHole_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
				
				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation6 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation6.setAllowanceBottom(0);
				operation6.setAllowanceSide(0);
				operation6.setCoolant(true);

				double x = 0;
				double y = 0;
				double z = furoTmp.getProfundidade() - Feature.LIMITE_DESBASTE;
				
				operation6.setStartPoint(new Point3d(x, y, z));

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
				wsTmp.setId("ConicalHole_FNS");
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
						bloco.getMaterial(), centerDrills, furoTmp,
						0);

				// CENTERDRILLING
				CenterDrilling operation1 = new CenterDrilling(
						"Center Drilling", retractPlane);

				operation1.setStartPoint(new Point3d(0, 0, 0));

				if (centerDrill.getCuttingEdgeLength() > (furoTmp
						.getProfundidade())) {
					if (furoTmp.isPassante()) {

						double tipAngle = centerDrill.getToolTipHalfAngle()
						* Math.PI / 180.0;
						double raio = centerDrill.getDiametroFerramenta() / 2;
						double ponta = Math.tan(tipAngle) * raio;
						
						operation1.setCuttingDepth(furoTmp.getProfundidade()
								+ ponta);

					} else {
						operation1.setCuttingDepth(furoTmp.getProfundidade());
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
				wsTmp.setId("ConicalHole_RGH");
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
			wsTmp.setId("ConicalHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
					faceMills, furoTmp, 0);

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation3.setAllowanceSide(0);
			operation3.setAllowanceBottom(0);
			operation3.setCoolant(true);

			Point3d startPoint;

			if (((furoTmp.getRaio()) - twistDrill
					.getDiametroFerramenta() / 2) >= faceMill
					.getDiametroFerramenta() / 2) {
				double x = 0;
				double y = twistDrill.getDiametroFerramenta() / 2;
				double z = 0;
				startPoint = new Point3d(x, y, z);
			} else {
				double x = 0;
				double y = (furoTmp.getRaio())
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
			wsTmp.setId("ConicalHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// WORKINGSTEPS DE DESBASTES FINAIS

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(),
					ballEndMills, furoTmp, false);

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation4 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation4.setAllowanceSide(0);
			operation4.setAllowanceBottom(0);

			operation4.setStartPoint(new Point3d(0, 0, 0));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
							.getMaterial());
			condicoesDeUsinagem.setAp(2);
			
			// WORKINGSTEP
			wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation4);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("ConicalHole_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			if (!furoTmp.isPassante()) {
				
				// FERRAMENTA
				BallEndMill ballEndMill2 = chooseBallEndMill(bloco.getMaterial(),
						ballEndMills, furoTmp, true);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation5 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				operation5.setStartPoint(new Point3d(0, 0, ballEndMill.getDiametroFerramenta()/2));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill2, bloco
								.getMaterial());
				condicoesDeUsinagem.setAp(2);
				
				// WORKINGSTEP
				wsTmp = new Workingstep(furoTmp, faceTmp, ballEndMill2,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("ConicalHole_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}

			furoTmp.setWorkingsteps(wssFeature);
		}
	}

	private CenterDrill chooseCenterDrill(Material material,
			ArrayList<CenterDrill> centerDrills, FuroConico furoTmp,
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

				JOptionPane
				.showMessageDialog(
						null,
						"Não é possível usinar esta Feature com as atuais Center Drills disponíveis! \n" +
						"__________________________________________________________"+"\n"+
						"\tFeature: Furo Conico \n" +
						"\tNome: " + furoTmp.getNome() +"\n" +
						"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
						"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
						"\tRaio1: " + furoTmp.getRaio1()+" mm"+"\n" +
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
			ArrayList<TwistDrill> twistDrills, FuroConico furoTmp,
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
							.getRaio1()*2 - 2 * limite_desbaste)
					&& twistDrill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade() - limite_desbaste)) {

				twistDrillsCandidatas.add(twistDrill);

			}

		}

		if (twistDrillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Twist Drills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Conico \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tRaio1: " + furoTmp.getRaio1()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Twist Drills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getRaio1()*2 - 2 * limite_desbaste)+" mm" +"\n" +
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
			ArrayList<FaceMill> faceMills, FuroConico furoTmp,
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
							.getRaio1()*2 - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade() - limite_desbaste)) {

				faceMillsCandidatas.add(faceMill);

			}

		}

		if (faceMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Conico \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tRaio1: " + furoTmp.getRaio1()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getRaio1()*2 - 2 * limite_desbaste)+" mm" +"\n" +
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

	private EndMill chooseEndMill(Material material,
			ArrayList<EndMill> endMills, FuroConico furoTmp) {

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
							.getRaio1()*2)
					&& endMill.getProfundidadeMaxima() >= (furoTmp
							.getProfundidade())) {

				endMillsCandidatas.add(endMill);

			}

		}

		if (endMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Conico \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tRaio1: " + furoTmp.getRaio1()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getRaio1()*2)+" mm" +"\n" +
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
	
	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, FuroConico furoTmp, boolean menor) {

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
					&& ballEndMill.getDiametroFerramenta() <= furoTmp.getRaio1()*2
					&& ballEndMill.getProfundidadeMaxima() >= furoTmp
							.getProfundidade()) {

				ballEndMillsCandidatas.add(ballEndMill);

			}

		}

		if (ballEndMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Furo Conico \n" +
					"\tNome: " + furoTmp.getNome() +"\n" +
					"\tProfundidade: " + furoTmp.getProfundidade()+" mm"+"\n" +
					"\tDiâmetro: " + furoTmp.getDiametro()+" mm"+"\n" +
					"\tRaio1: " + furoTmp.getRaio1()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo de Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (furoTmp.getRaio1()*2)+" mm" +"\n" +
					"\tProfundidade máxima da Ferramenta deve ser maior igual a: " + (furoTmp.getProfundidade()) +
					"\tAdicione Ball End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Ball End Mill selecionada");

		}

		ballEndMill = ballEndMillsCandidatas.get(0);

		if(menor){
			
			for (int i = 1; i < ballEndMillsCandidatas.size(); i++) {// Seleciona a
				// ball end
				// mill de
				// menor
				// diametro

				if (ballEndMillsCandidatas.get(i).getDiametroFerramenta() < ballEndMill
						.getDiametroFerramenta()) {
					ballEndMill = ballEndMillsCandidatas.get(i);
				}

			}
			
		}else{
			
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
		}

		return ballEndMill;
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
