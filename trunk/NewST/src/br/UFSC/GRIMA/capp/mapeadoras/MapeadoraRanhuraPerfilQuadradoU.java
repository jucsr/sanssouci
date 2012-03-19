package br.UFSC.GRIMA.capp.mapeadoras;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhuraPerfilQuadradoU {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private RanhuraPerfilQuadradoU ranhuraTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;
	private ArrayList<BallEndMill> ballEndMills;
	private ArrayList<BullnoseEndMill> bullnoseEndMills;

	public MapeadoraRanhuraPerfilQuadradoU(Projeto projeto) {

		this.projeto = projeto;
		this.bloco = projeto.getBloco();

	}

	public MapeadoraRanhuraPerfilQuadradoU(Projeto projeto, Face face, RanhuraPerfilQuadradoU ranhura) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.ranhuraTmp = ranhura;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		this.endMills = ToolManager.getEndMills();
		this.bullnoseEndMills = ToolManager.getBullnoseEndMills();
	
		this.mapearRanhuraPerfilQuadradoU();

	}

	private void mapearRanhuraPerfilQuadradoU() {
		
		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp;
		
		double retractPlane = 5;

		wssFeature = new Vector<Workingstep>();

		if (ranhuraTmp.getTolerancia() <= Feature.LIMITE_RUGOSIDADE
				&& ranhuraTmp.getRugosidade() <= Feature.LIMITE_TOLERANCIA) {
			ranhuraTmp.setAcabamento(true);
		}

		if(ranhuraTmp.getFeaturePrecedente()!= null){
			
			wsPrecedenteTmp = ranhuraTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
			
		}else{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		
		if (ranhuraTmp.isAcabamento()) {

			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(Feature.LIMITE_DESBASTE);
			operation1.setAllowanceBottom(Feature.LIMITE_DESBASTE);
			// Start Point vai ser setado na otimizacao
			operation1.setStartPoint(new Point3d(0,0,0));

			// FERRAMENTA
			double prof = ranhuraTmp.getProfundidade();
			double r = ranhuraTmp.getRaio();
			double alfa = (90-ranhuraTmp.getAngulo())*Math.PI/180;
			double x1 = 0;
			
			if(alfa!=Math.PI/2)
			x1 = (prof - r)/Math.tan(alfa);
			
			double L3 = ranhuraTmp.getLargura2() - 2*x1;
			
			System.out.println("L3 = " + L3);
			
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, Feature.LIMITE_DESBASTE, L3);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("SquareUProfileSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			//Calculo do bottom pra facemill
			double x2 = 0;
			
			if(alfa!=Math.PI/2)
				x2 = r*Math.tan(alfa/2);
			
			double L4 = ranhuraTmp.getLargura2() - 2*x2;
			
			System.out.println("L4 = " + L4);
			
			if(faceMill.getDiametroFerramenta() > L4 && L4 >= 2.0){ // verifica se é necessário uma nova FaceMill e Operation
				
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
				operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);
				// Start Point vai ser setado na otimizacao
				operation2.setStartPoint(new Point3d(0,0,0));

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
						ranhuraTmp, Feature.LIMITE_DESBASTE, L4);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("SquareUProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}
			
			
			// WORKINGSTEPS DE ACABAMENTO

			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco.getMaterial(), bullnoseEndMills,
					ranhuraTmp, L4);

			if(bullnoseEndMill!=null){
				
				// BOTTOM AND SIDE FINNISH MILLING
				BottomAndSideFinishMilling operation3 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation3.setStartPoint(new Point3d(0,0,0));
				
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill, bloco
						.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, bullnoseEndMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("SquareUProfileSlot_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}else{// se nao utilizou Bull Nose, faremos
				// uma combinaçao de EndMill com BallEndMill
				
				// FERRAMENTA
				EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
						ranhuraTmp, L4);

				if (endMill != null) {

					// BOTTOM AND SIDE FINISH MILLING
					BottomAndSideFinishMilling operation4 = new BottomAndSideFinishMilling(
							"Bottom And Side Finish Milling", retractPlane);
					operation4.setAllowanceSide(0);
					operation4.setAllowanceBottom(0);
					// Start Point vai ser setado na otimizacao
					operation4.setStartPoint(new Point3d(0,0,0));
					
					operation4.setStartPoint(new Point3d(0, 0, ranhuraTmp.getProfundidade() - Feature.LIMITE_DESBASTE));

					// CONDIÇÕES DE USINAGEM
					condicoesDeUsinagem = MapeadoraDeWorkingsteps
							.getCondicoesDeUsinagem(this.projeto, endMill,
									bloco.getMaterial());
					// WORKINGSTEP
					wsTmp = new Workingstep(ranhuraTmp, faceTmp, endMill,
							condicoesDeUsinagem, operation4);
					wsTmp.setTipo(Workingstep.ACABAMENTO);
					wsTmp.setId("SquareUProfileSlot_FNS");
					wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
					wsPrecedenteTmp = wsTmp;

					wssFeature.add(wsTmp);

				}

				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, ranhuraTmp);

				// BOTTOM AND SIDE FINISH MILLING
				BottomAndSideFinishMilling operation5 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation5.setStartPoint(new Point3d(0,0,0));


				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("SquareUProfileSlot_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}
			
			ranhuraTmp.setWorkingsteps(wssFeature);

		} else {
			
			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(0);
			operation1.setAllowanceBottom(0);
			// Start Point vai ser setado na otimizacao
			operation1.setStartPoint(new Point3d(0,0,0));

			// FERRAMENTA
			double prof = ranhuraTmp.getProfundidade();
			double r = ranhuraTmp.getRaio();
			double alfa = (90-ranhuraTmp.getAngulo())*Math.PI/180;
			double x1 = 0;
			
			if(alfa!=Math.PI/2)
			x1 = (prof - r)/Math.tan(alfa);
			
			double L3 = ranhuraTmp.getLargura2() - 2*x1;
			
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, 0, L3);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("SquareUProfileSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			//Calculo do bottom pra facemill
			double x2 = 0;
			
			if(alfa!=Math.PI/2)
				x2 = r*Math.tan(alfa/2);
			
			double L4 = ranhuraTmp.getLargura2() - 2*x2;
			
			if(faceMill.getDiametroFerramenta() > L4 && L4 >= 2.0){ // verifica se é necessário uma nova FaceMill e Operation
				
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(0);
				operation2.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation2.setStartPoint(new Point3d(0,0,0));

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
						ranhuraTmp, 0, L4);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("SquareUProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
			}
			
			
			// FERRAMENTA
			BullnoseEndMill bullnoseEndMill = chooseBullnoseEndMill(bloco.getMaterial(), bullnoseEndMills,
					ranhuraTmp, L4);

			if(bullnoseEndMill!=null){
				
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation3.setStartPoint(new Point3d(0,0,0));
				
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, bullnoseEndMill, bloco
						.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, bullnoseEndMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("SquareUProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}else{// se nao utilizou Bull Nose, utilizaramos BallEndMill
				
				// FERRAMENTA
				BallEndMill ballEndMill = chooseBallEndMill(
						bloco.getMaterial(), ballEndMills, ranhuraTmp);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation5 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation5.setAllowanceSide(0);
				operation5.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation5.setStartPoint(new Point3d(0,0,0));


				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, ballEndMill,
								bloco.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
						condicoesDeUsinagem, operation5);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("SquareUProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}
			
			ranhuraTmp.setWorkingsteps(wssFeature);
		}
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, RanhuraPerfilQuadradoU ranhuraTmp,
			double limite_desbaste, double Lx) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");
		
		if(Lx <= 3)
			Lx=3;
		

		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas a ranhura

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (Lx - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (ranhuraTmp
							.getProfundidade() - limite_desbaste)) {

				faceMillsCandidatas.add(faceMill);

			}

		}

		if (faceMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
//					"    Feature: Ranhura Perfil Quadrado U\n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Largura 2: " + ranhuraTmp.getLargura2()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Angulo: " + ranhuraTmp.getAngulo()+"\n" +
//					"    Raio: " + ranhuraTmp.getRaio()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Quadrado U \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tLargura 2: " + ranhuraTmp.getLargura2()+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getEixo()+" mm"+"\n" +
					"\tRaio: " + ranhuraTmp.getRaio()+ " mm\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (Lx - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (ranhuraTmp.getProfundidade() - limite_desbaste)+" mm"+"\n\n" +
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
			ArrayList<EndMill> endMills, RanhuraPerfilQuadradoU ranhuraTmp, double L4) {

		ArrayList<EndMill> endMillsCandidatas = new ArrayList<EndMill>();

		EndMill endMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_EndMill");

		for (int i = 0; i < endMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas a ranhura

			endMill = endMills.get(i);

			if (endMill.getMaterial().equals(ISO)
					&& endMill.getDiametroFerramenta() <= L4
					&& endMill.getProfundidadeMaxima() >= ranhuraTmp
							.getProfundidade()) {

				endMillsCandidatas.add(endMill);

			}

		}

		if (endMillsCandidatas.size() == 0) {

			return null;

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

	private BullnoseEndMill chooseBullnoseEndMill(Material material,
			ArrayList<BullnoseEndMill> bullnoseEndMills,
			RanhuraPerfilQuadradoU ranhuraTmp, double L4) {

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
					&& bullnoseEndMill.getProfundidadeMaxima() >= ranhuraTmp.getProfundidade()
					&& (bullnoseEndMill.getEdgeCenterHorizontal() * 2) <= L4
					&& bullnoseEndMill.getEdgeRadius() <= ranhuraTmp.getRaio()) {

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
			// diametro

			if (bullnoseCandidatas.get(i).getDiametroFerramenta() > bullnoseEndMill
					.getDiametroFerramenta()) {
				bullnoseEndMill = bullnoseCandidatas.get(i);
			}

		}

		return bullnoseEndMill;
	}
	
	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, RanhuraPerfilQuadradoU ranhuraTmp) {

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
					&& ballEndMill.getProfundidadeMaxima() >= ranhuraTmp.getProfundidade()
					&& ballEndMill.getEdgeRadius() <= ranhuraTmp.getRaio()) {

				ballEndMillsCandidatas.add(ballEndMill);

			}

		}

		if (ballEndMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
//					"    Feature: Ranhura Perfil Quadrado U\n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Largura 2: " + ranhuraTmp.getLargura2()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Angulo: " + ranhuraTmp.getAngulo()+"\n" +
//					"    Raio: " + ranhuraTmp.getRaio()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);
			
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Quadrado U \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tLargura 2: " + ranhuraTmp.getLargura2()+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"\tRaio: " + ranhuraTmp.getRaio()+ " mm\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Ball End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (ranhuraTmp.getRaio() * 2)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (ranhuraTmp.getProfundidade())+" mm"+"\n\n" +
					"\tAdicione Ball End Mills adequadas ao projeto."
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
