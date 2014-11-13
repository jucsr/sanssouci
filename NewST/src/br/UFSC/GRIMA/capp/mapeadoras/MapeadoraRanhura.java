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
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Bidirectional;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhura {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private Ranhura ranhuraTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraRanhura(Projeto projeto) {

		this.projeto = projeto;
		this.bloco = projeto.getBloco();

	}

	public MapeadoraRanhura(Projeto projeto, Face face, Ranhura ranhura) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.ranhuraTmp = ranhura;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();

		this.mapearRanhura();

	}

	private void mapearRanhura() {
		
		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp = null;
		
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

			// PLUNGE STRATEGY
			PlungeToolAxis plungeStrategy = new PlungeToolAxis();
			operation1.setApproachStrategy(plungeStrategy);
			
			// MACHINING STRATEGY
			Bidirectional machiningStrategy = new Bidirectional();
			machiningStrategy.setAllowMultiplePasses(true);
			operation1.setMachiningStrategy(machiningStrategy);
			
			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, Feature.LIMITE_DESBASTE);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("Slot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// WORKINGSTEPS DE ACABAMENTO

			// BOTTOM AND SIDE FINNISH MILLING
			BottomAndSideFinishMilling operation2 = new BottomAndSideFinishMilling(
					"Bottom And Side Finish Milling", retractPlane);
			operation2.setAllowanceSide(0);
			operation2.setAllowanceBottom(0);
			// Start Point vai ser setado na otimizacao
			operation2.setStartPoint(new Point3d(0,0,0));
			
			// PLUNGE STRATEGY
			PlungeToolAxis plungeStrategy2 = new PlungeToolAxis();
			operation2.setApproachStrategy(plungeStrategy2);

			// MACHINING STRATEGY
			Bidirectional machiningStrategy2 = new Bidirectional();
			machiningStrategy2.setAllowMultiplePasses(true);
			operation2.setMachiningStrategy(machiningStrategy2);
			
			// FERRAMENTA
			EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
					ranhuraTmp);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, endMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, endMill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("Slot_FNS");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

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

			// PLUNGE STRATEGY
			PlungeToolAxis plungeStrategy = new PlungeToolAxis();
			operation1.setApproachStrategy(plungeStrategy);

			// MACHINING STRATEGY
			Bidirectional machiningStrategy = new Bidirectional();
			machiningStrategy.setAllowMultiplePasses(true);
			operation1.setMachiningStrategy(machiningStrategy);
			
			
			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, 0);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("Slot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			ranhuraTmp.setWorkingsteps(wssFeature);
		}
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, Ranhura ranhuraTmp,
			double limite_desbaste) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas a ranhura

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (ranhuraTmp
							.getLargura() - 2 * limite_desbaste)
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
//					"    Feature: Ranhura \n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);
						
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (ranhuraTmp.getLargura() - 2 * limite_desbaste)+" mm" +"\n" +
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
			ArrayList<EndMill> endMills, Ranhura ranhuraTmp) {

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
					&& endMill.getDiametroFerramenta() <= (ranhuraTmp
							.getLargura())
					&& endMill.getProfundidadeMaxima() >= (ranhuraTmp
							.getProfundidade())) {

				endMillsCandidatas.add(endMill);

			}

		}

		if (endMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
//					"    Feature: Ranhura \n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (ranhuraTmp.getLargura())+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (ranhuraTmp.getProfundidade())+" mm"+"\n\n" +
					"\tAdicione End Mills adequadas ao projeto."
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
