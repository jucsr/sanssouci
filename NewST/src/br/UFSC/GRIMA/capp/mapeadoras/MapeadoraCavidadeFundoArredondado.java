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
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraCavidadeFundoArredondado {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private CavidadeFundoArredondado cavidadeTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;
	private ArrayList<BallEndMill> ballEndMills;
	private ArrayList<BullnoseEndMill> bullnoseEndMills;

	public MapeadoraCavidadeFundoArredondado(Projeto projeto, Face face, CavidadeFundoArredondado cavidade) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.cavidadeTmp = cavidade;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		this.bullnoseEndMills = ToolManager.getBullnoseEndMills();
		
		this.mapearCavidadeFundoBaseArredondado();

	}

	private void mapearCavidadeFundoBaseArredondado() {
		
		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp;
		
		double retractPlane = 5;

		wssFeature = new Vector<Workingstep>();

		if (cavidadeTmp.getTolerancia() <= Feature.LIMITE_RUGOSIDADE
				&& cavidadeTmp.getRugosidade() <= Feature.LIMITE_TOLERANCIA) {
			cavidadeTmp.setAcabamento(true);
		}

		if(cavidadeTmp.getFeaturePrecedente()!= null){
			
			wsPrecedenteTmp = cavidadeTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
			
		}else{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		
		if (cavidadeTmp.isAcabamento()) {

			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(Feature.LIMITE_DESBASTE);
			operation1.setAllowanceBottom(0);

			operation1.setStartPoint(new Point3d(0, 0, 0));

			double comprimento = cavidadeTmp.getComprimento();
			double largura = cavidadeTmp.getLargura();
			double L = comprimento;

			if (largura < comprimento)
				L = largura;

			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					cavidadeTmp, Feature.LIMITE_DESBASTE, L);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("RoundedBottomPocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			
			// WORKINGSTEPs DE ACABAMENTO PARA PARTE DE CIMA (pode haver mais uma de desbaste)
			
			//ENDMILL PRA ACABAMENTO POSTERIOR
			// FERRAMENTA
			EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
					cavidadeTmp, L);
			
			double dEndMill = endMill.getDiametroFerramenta();
			
			boolean precisaSegDesb = false;
			
			double rFresa = faceMill.getDiametroFerramenta()/2;
			double rCavidade = cavidadeTmp.getVerticeRaio();
			
			double sobra = rFresa*(Math.sqrt(2)-1) - rCavidade*(Math.sqrt(2)-1) + Feature.LIMITE_DESBASTE;
			
			if(dEndMill*0.75 >= sobra)
				precisaSegDesb = false;
			else
				precisaSegDesb = true;

			// se o raio da facemill for maior q o da cavidade
			if (faceMill.getDiametroFerramenta() / 2 > cavidadeTmp.getVerticeRaio()
					&& precisaSegDesb) {

				double D = cavidadeTmp.getVerticeRaio() * 2;

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, cavidadeTmp, Feature.LIMITE_DESBASTE, D);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
				operation2.setAllowanceBottom(0);

				double x = comprimento / 2 - Feature.LIMITE_DESBASTE
						- faceMill2.getDiametroFerramenta() / 2;
				double y = largura / 2 - Feature.LIMITE_DESBASTE
						- faceMill.getDiametroFerramenta() / 2;
				double z = 0;

				operation2.setStartPoint(new Point3d(x, y, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("RoundedBottomPocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			
			// BOTTOM AND SIDE FINNISH MILLING
			BottomAndSideFinishMilling operation3 = new BottomAndSideFinishMilling(
					"Bottom And Side Finish Milling", retractPlane);
			operation3.setAllowanceSide(0);
			operation3.setAllowanceBottom(0);

			double x = cavidadeTmp.getComprimento() / 2
					- endMill.getDiametroFerramenta() / 2;

			operation3.setStartPoint(new Point3d(x, 0, 0));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, endMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(cavidadeTmp, faceTmp, endMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("RoundedBottomPocket_FNS");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			
			//USINAR PARTE DO FUNDO

			double bottomRadius = cavidadeTmp.getFundoRaio();
			double z = cavidadeTmp.getProfundidade()-bottomRadius;
			
			//WORKINGSTEP DE DESBASTE DO FUNDO
			
			// FERRAMENTA
			FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
					faceMills, cavidadeTmp, Feature.LIMITE_DESBASTE, L-2*bottomRadius);
			
			if(!faceMill2.equals(faceMill)){

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation4 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation4.setAllowanceSide(0);
			operation4.setAllowanceBottom(Feature.LIMITE_DESBASTE);
			operation4.setCoolant(true);
			operation4.setStartPoint(new Point3d(0, 0, z));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
							.getMaterial());

			// WORKINGSTEP
			wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill2,
					condicoesDeUsinagem, operation4);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("RoundedBottomPocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			}

			// WORKINGSTEPS DE ACABAMENTO DO FUNDO

			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco
					.getMaterial(), bullnoseEndMills, cavidadeTmp);

			if (bullnoseEndMill != null) {

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation5 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				operation5.setStartPoint(new Point3d(bottomRadius, 0, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill,
						bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, bullnoseEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("RoundedBottomPocket_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}else {// se nao utilizou Bull Nose, faremos
				// uma combinaçao de EndMill com BallEndMill

				// FERRAMENTA
				EndMill endMill2 = chooseEndMill(bloco.getMaterial(), endMills,
						cavidadeTmp, L-2*bottomRadius);

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation6 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation6.setAllowanceSide(0);
				operation6.setAllowanceBottom(0);
				operation6.setStartPoint(new Point3d(bottomRadius + endMill2.getDiametroFerramenta()/2, 0,z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, endMill2,
						bloco.getMaterial());
				
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, endMill2,
						condicoesDeUsinagem, operation6);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("RoundedBottomPocket_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);


				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, cavidadeTmp);

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation7 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation7.setAllowanceSide(0);
				operation7.setAllowanceBottom(0);

				operation7.setStartPoint(new Point3d(bottomRadius, 0, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, ballEndMill,
						bloco.getMaterial());
				
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation7);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("RoundedBottomPocket_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			cavidadeTmp.setWorkingsteps(wssFeature);

		} else {

			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(0);
			operation1.setAllowanceBottom(0);
			operation1.setStartPoint(new Point3d(0, 0, 0));

			double comprimento = cavidadeTmp.getComprimento();
			double largura = cavidadeTmp.getLargura();
			double L = comprimento;

			if (largura < comprimento)
				L = largura;

			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					cavidadeTmp, 0, L);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
			.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
					.getMaterial());

			// WORKINGSTEP
			wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("RoundedBottomPocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// se o raio da facemill for maior q o da cavidade
			if (faceMill.getDiametroFerramenta() / 2 > cavidadeTmp.getVerticeRaio()) {

				double D = cavidadeTmp.getVerticeRaio() * 2;

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, cavidadeTmp, 0, D);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(0);
				operation2.setAllowanceBottom(0);

				double x = comprimento / 2 - faceMill2.getDiametroFerramenta()
				/ 2;
				double y = largura / 2 - faceMill.getDiametroFerramenta() / 2;
				double z = 0;

				operation2.setStartPoint(new Point3d(x, y, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
						.getMaterial());

				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("RoundedBottomPocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}
			
			//DESBASTE DO FUNDO
			
			double bottomRadius = cavidadeTmp.getFundoRaio();
			double z = cavidadeTmp.getProfundidade()-bottomRadius;
			
			// FERRAMENTA
			FaceMill faceMill3 = chooseFaceMill(bloco.getMaterial(),
					faceMills, cavidadeTmp, 0, L-2*bottomRadius);

			if(!faceMill3.equals(faceMill)){

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation3.setAllowanceSide(0);
			operation3.setAllowanceBottom(0);
			operation3.setStartPoint(new Point3d(0, 0, z));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill3, bloco
							.getMaterial());

			// WORKINGSTEP
			wsTmp = new Workingstep(cavidadeTmp, faceTmp, faceMill3,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("RoundedBottomPocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			}

			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco
					.getMaterial(), bullnoseEndMills, cavidadeTmp);

			if (bullnoseEndMill != null) {

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation5 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				operation5.setStartPoint(new Point3d(bottomRadius, 0, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill,
						bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, bullnoseEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("RoundedBottomPocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}else {// se nao utilizou Bull Nose
				//utilizaremos BallEndMill

				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, cavidadeTmp);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation7 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation7.setAllowanceSide(0);
				operation7.setAllowanceBottom(0);
				operation7.setStartPoint(new Point3d(bottomRadius, 0, z));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, ballEndMill,
						bloco.getMaterial());
				
				// WORKINGSTEP
				wsTmp = new Workingstep(cavidadeTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation7);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("RoundedBottomPocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}
		}
		
		cavidadeTmp.setWorkingsteps(wssFeature);
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, CavidadeFundoArredondado cavidadeTmp,
			double limite_desbaste, double L) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (cavidadeTmp
							.getProfundidade() - limite_desbaste )) {

				faceMillsCandidatas.add(faceMill);

			}

		}

		if (faceMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
//					"    Feature: Cavidade com Fundo Arredondado \n" +
//					"    Nome: " + cavidadeTmp.getNome() +"\n" +
//					"    Comprimento: " + cavidadeTmp.getComprimento()+"\n" +
//					"    Largura: " + cavidadeTmp.getLargura()+"\n" +
//					"    Profundidade: " + cavidadeTmp.getProfundidade()+"\n" +
//					"    Raio dos Vértices: " + cavidadeTmp.getVerticeRaio()+"\n" +
//					"    Raio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Banheira \n" +
					"\tNome: " + cavidadeTmp.getNome() +"\n" +
					"\tComprimento: " + cavidadeTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + cavidadeTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + cavidadeTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio dos Vértices: " + cavidadeTmp.getVerticeRaio()+" mm"+"\n" +
					"\tRaio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (L - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (cavidadeTmp.getProfundidade() - limite_desbaste)+" mm"+"\n\n" +
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
			ArrayList<EndMill> endMills, CavidadeFundoArredondado cavidadeTmp, double L) {

		ArrayList<EndMill> endMillsCandidatas = new ArrayList<EndMill>();

		EndMill endMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_EndMill");

		for (int i = 0; i < endMills.size(); i++) { // Seleciona todas as
			// end mills
			// candidatas

			endMill = endMills.get(i);

			if (endMill.getMaterial().equals(ISO)
					&& endMill.getDiametroFerramenta() <= 2 * cavidadeTmp
					.getVerticeRaio()
					&& endMill.getDiametroFerramenta() <= L
					&& endMill.getProfundidadeMaxima() >= cavidadeTmp
					.getProfundidade()) {

				endMillsCandidatas.add(endMill);
			}
		}

		if (endMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
//					"    Feature: Cavidade com Fundo Arredondado \n" +
//					"    Nome: " + cavidadeTmp.getNome() +"\n" +
//					"    Comprimento: " + cavidadeTmp.getComprimento()+"\n" +
//					"    Largura: " + cavidadeTmp.getLargura()+"\n" +
//					"    Profundidade: " + cavidadeTmp.getProfundidade()+"\n" +
//					"    Raio dos Vértices: " + cavidadeTmp.getVerticeRaio()+"\n" +
//					"    Raio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Banheira \n" +
					"\tNome: " + cavidadeTmp.getNome() +"\n" +
					"\tComprimento: " + cavidadeTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + cavidadeTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + cavidadeTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio dos Vértices: " + cavidadeTmp.getVerticeRaio()+" mm"+"\n" +
					"\tRaio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (2 * cavidadeTmp.getVerticeRaio())+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (cavidadeTmp.getProfundidade())+" mm"+"\n\n" +
					"\tAdicione End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma End Mill selecionada");

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
	
	private BullnoseEndMill chooseBullnoseEndMill(Material material,
			ArrayList<BullnoseEndMill> bullnoseEndMills,
			CavidadeFundoArredondado cavidadeTmp) {

		BullnoseEndMill bullnoseEndMill = null;

		ArrayList<BullnoseEndMill> bullnoseCandidatas = new ArrayList<BullnoseEndMill>();

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BullnoseEndMill");

		for (int i = 0; i < bullnoseEndMills.size(); i++) { // Seleciona todas
			// as
			// bull nose
			// candidatas

			bullnoseEndMill = bullnoseEndMills.get(i);

			if (bullnoseEndMill.getMaterial().equals(ISO)
					&& bullnoseEndMill.getDiametroFerramenta() <= (cavidadeTmp.getVerticeRaio()*2)
					&& bullnoseEndMill.getProfundidadeMaxima() >= cavidadeTmp.getProfundidade()
					&& bullnoseEndMill.getEdgeRadius() <= cavidadeTmp.getFundoRaio()){

				bullnoseCandidatas.add(bullnoseEndMill);
			}
		}
		
		if (bullnoseCandidatas.size() == 0) {
			
			return null;
			
		}

		bullnoseEndMill = bullnoseCandidatas.get(0);

		for (int i = 1; i < bullnoseCandidatas.size(); i++) {// Seleciona a
			// bull
			// nose de
			// maior
			// Edge Radius

			if (bullnoseCandidatas.get(i).getEdgeRadius() > bullnoseEndMill
					.getEdgeRadius()) {
				bullnoseEndMill = bullnoseCandidatas.get(i);
			}

		}

		return bullnoseEndMill;
	}
	
	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, CavidadeFundoArredondado cavidadeTmp) {

		ArrayList<BallEndMill> ballEndMillsCandidatas = new ArrayList<BallEndMill>();

		BallEndMill ballEndMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BallEndMill");

		for (int i = 0; i < ballEndMills.size(); i++) { // Seleciona todas as
			// ball end mills
			// candidatas

			ballEndMill = ballEndMills.get(i);

			if (ballEndMill.getMaterial().equals(ISO)
					&& ballEndMill.getDiametroFerramenta() <= (cavidadeTmp.getVerticeRaio()*2)
					&& ballEndMill.getProfundidadeMaxima() >= (cavidadeTmp.getProfundidade())
					&& ballEndMill.getEdgeRadius() <= cavidadeTmp.getFundoRaio()) {

				ballEndMillsCandidatas.add(ballEndMill);
			}
		}

		if (ballEndMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
//					"    Feature: Cavidade com Fundo Arredondado \n" +
//					"    Nome: " + cavidadeTmp.getNome() +"\n" +
//					"    Comprimento: " + cavidadeTmp.getComprimento()+"\n" +
//					"    Largura: " + cavidadeTmp.getLargura()+"\n" +
//					"    Profundidade: " + cavidadeTmp.getProfundidade()+"\n" +
//					"    Raio dos Vértices: " + cavidadeTmp.getVerticeRaio()+"\n" +
//					"    Raio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Banheira \n" +
					"\tNome: " + cavidadeTmp.getNome() +"\n" +
					"\tComprimento: " + cavidadeTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + cavidadeTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + cavidadeTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio dos Vértices: " + cavidadeTmp.getVerticeRaio()+" mm"+"\n" +
					"\tRaio do Fundo: " + cavidadeTmp.getFundoRaio()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Ball End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (2 * cavidadeTmp.getFundoRaio())+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (cavidadeTmp.getProfundidade())+" mm"+"\n\n" +
					"\tAdicione Ball End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Ball End Mill selecionada");

		}

		ballEndMill = ballEndMillsCandidatas.get(0);

		for (int i = 1; i < ballEndMillsCandidatas.size(); i++) {// Seleciona a
			// ball end
			// mill de
			// maior Edge Radius

			if (ballEndMillsCandidatas.get(i).getEdgeRadius() > ballEndMill
					.getEdgeRadius()) {
				ballEndMill = ballEndMillsCandidatas.get(i);
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
