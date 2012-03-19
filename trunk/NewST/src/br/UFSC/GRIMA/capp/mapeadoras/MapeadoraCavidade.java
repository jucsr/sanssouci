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
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraCavidade {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private Cavidade cavidadeTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraCavidade(Projeto projeto, Face face, Cavidade cavidade) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.cavidadeTmp = cavidade;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		
		this.mapearCavidade();

	}

	private void mapearCavidade() {
		
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

			if (!cavidadeTmp.isPassante())
				operation1.setAllowanceBottom(Feature.LIMITE_DESBASTE);

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
			wsTmp.setId("Pocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			
			//FRESA PRA ACABAMENTO ANTECIPADA
			// FERRAMENTA
			EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
					cavidadeTmp);
			
			double dEndMill = endMill.getDiametroFerramenta();
			
			boolean precisaSegDesb = false;
			
			double rFresa = faceMill.getDiametroFerramenta()/2;
			double rCavidade = cavidadeTmp.getRaio();
			
			double sobra = rFresa*(Math.sqrt(2)-1) - rCavidade*(Math.sqrt(2)-1) + Feature.LIMITE_DESBASTE;
			
			if(dEndMill*0.75 >= sobra)
				precisaSegDesb = false;
			else
				precisaSegDesb = true;

			// se o raio da facemill for maior q o da cavidade
			if (faceMill.getDiametroFerramenta() / 2 > cavidadeTmp.getRaio()
					&& precisaSegDesb) {

				double D = cavidadeTmp.getRaio() * 2;

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, cavidadeTmp, Feature.LIMITE_DESBASTE, D);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
				if (!cavidadeTmp.isPassante())
					operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);

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
				wsTmp.setId("Pocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			// WORKINGSTEP DE ACABAMENTO

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
			wsTmp.setId("Pocket_FNS");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

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
			wsTmp.setId("Pocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// se o raio da facemill for maior q o da cavidade
			if (faceMill.getDiametroFerramenta() / 2 > cavidadeTmp.getRaio()) {

				double D = cavidadeTmp.getRaio() * 2;

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
				wsTmp.setId("Pocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			cavidadeTmp.setWorkingsteps(wssFeature);
		}
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, Cavidade cavidadeTmp,
			double limite_desbaste, double L) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";
		
		String motivo = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		double limite_desbaste_fundo = limite_desbaste;
		
		if (cavidadeTmp.isPassante())
			limite_desbaste_fundo = 0;
		
		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas

			faceMill = faceMills.get(i);

			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (cavidadeTmp
							.getProfundidade() - limite_desbaste_fundo )) {

				faceMillsCandidatas.add(faceMill);

			}
		}
			
		if (faceMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Cavidade \n" +
					"\tNome: " + cavidadeTmp.getNome() +"\n" +
					"\tComprimento: " + cavidadeTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + cavidadeTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + cavidadeTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + cavidadeTmp.getRaio()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (L - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (cavidadeTmp.getProfundidade() - limite_desbaste_fundo )+" mm"+"\n\n" +
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
			ArrayList<EndMill> endMills, Cavidade cavidadeTmp) {

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
					&& endMill.getDiametroFerramenta() <= 2 * cavidadeTmp
							.getRaio()
					&& endMill.getProfundidadeMaxima() >= cavidadeTmp
							.getProfundidade()) {

				endMillsCandidatas.add(endMill);

			}

		}

		if (endMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Cavidade \n" +
					"\tNome: " + cavidadeTmp.getNome() +"\n" +
					"\tComprimento: " + cavidadeTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + cavidadeTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + cavidadeTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + cavidadeTmp.getRaio()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (cavidadeTmp.getRaio() * 2)+" mm" +"\n" +
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
