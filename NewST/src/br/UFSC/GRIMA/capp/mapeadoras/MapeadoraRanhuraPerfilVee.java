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
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhuraPerfilVee {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private RanhuraPerfilVee ranhuraTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<BallEndMill> ballEndMills;

	public MapeadoraRanhuraPerfilVee(Projeto projeto, Face face, RanhuraPerfilVee ranhura) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.ranhuraTmp = ranhura;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.ballEndMills = ToolManager.getBallEndMills();

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
			wsTmp.setId("VeeSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// WORKINGSTEP DE DESBASTE DO FUNDO

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
			operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);
			// Start Point vai ser setado na otimizacao
			operation2.setStartPoint(new Point3d(0,0,ranhuraTmp.getProfundidade()*0.75));

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills,
					ranhuraTmp);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
							.getMaterial());
			condicoesDeUsinagem.setAp(2); //AP pro desbaste!
			
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("VeeSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			// WORKINGSTEP DE ACABAMENTO

			// BOTTOM AND SIDE FINISH MILLING
			BottomAndSideFinishMilling operation3 = new BottomAndSideFinishMilling(
					"Bottom And Side Finish Milling", retractPlane);
			operation3.setAllowanceSide(0);
			operation3.setAllowanceBottom(0);
			// Start Point vai ser setado na otimizacao
			operation3.setStartPoint(new Point3d(0,0,0));

			// FERRAMENTA É A MESMA QUE A ANTERIOR
			
			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
							.getMaterial());
			
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("VeeSlot_FNS");
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
			wsTmp.setId("VeeSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// WORKINGSTEP DE DESBASTE DO FUNDO

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation2.setAllowanceSide(0);
			operation2.setAllowanceBottom(0);
			// Start Point vai ser setado na otimizacao
			operation2.setStartPoint(new Point3d(0,0,0));

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills,
					ranhuraTmp);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
							.getMaterial());
			condicoesDeUsinagem.setAp(2); //AP pro desbaste!
			
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("VeeSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			ranhuraTmp.setWorkingsteps(wssFeature);
		}
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, RanhuraPerfilVee ranhuraTmp,
			double limite_desbaste) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");
		
		
		double L = ranhuraTmp.getLargura()/4;
		

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
//					"    Feature: Ranhura Perfil Vee \n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Raio: " + ranhuraTmp.getRaio()+"\n" +
//					"    Angulo: " + ranhuraTmp.getAngulo()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Vee \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + ranhuraTmp.getRaio()+"\n" +
					"\tAngulo: " + ranhuraTmp.getAngulo()+"\n" +
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
			// diametro mais próximo a L

			if (Math
					.abs((faceMillsCandidatas.get(i).getDiametroFerramenta() - L)) < Math
					.abs((faceMill.getDiametroFerramenta() - L))) {
				faceMill = faceMillsCandidatas.get(i);
			}

		}

		return faceMill;
	}

	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, RanhuraPerfilVee ranhuraTmp) {

		ArrayList<BallEndMill> ballEndMillsCandidatas = new ArrayList<BallEndMill>();

		BallEndMill ballEndMill = null;

		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_EndMill");

		for (int i = 0; i < ballEndMills.size(); i++) { // Seleciona todas as
			// ball end mills
			// candidatas

			ballEndMill = ballEndMills.get(i);

			if (ballEndMill.getMaterial().equals(ISO)
					&& ballEndMill.getDiametroFerramenta() <= (ranhuraTmp.getRaio()*2)
					&& ballEndMill.getProfundidadeMaxima() >= (ranhuraTmp.getProfundidade())) {

				ballEndMillsCandidatas.add(ballEndMill);

			}

		}

		if (ballEndMillsCandidatas.size() == 0) {

//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
//					"    Feature: Ranhura Perfil Vee \n" +
//					"    Nome: " + ranhuraTmp.getNome() +"\n" +
//					"    Comprimento: " + ranhuraTmp.getComprimento()+"\n" +
//					"    Largura: " + ranhuraTmp.getLargura()+"\n" +
//					"    Profundidade: " + ranhuraTmp.getProfundidade()+"\n" +
//					"    Raio: " + ranhuraTmp.getRaio()+"\n" +
//					"    Angulo: " + ranhuraTmp.getAngulo()+"\n" +
//					"    Eixo: " + ranhuraTmp.getStringEixo()+"\n" +
//					"    Material Bloco: " + material.getName(),
//					"Erro", JOptionPane.ERROR_MESSAGE);
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Ball End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (ranhuraTmp.getRaio()*2)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (ranhuraTmp.getProfundidade())+" mm"+"\n\n" +
					"\tAdicione Ball End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Ball End Mill selecionada");

		}

		ballEndMill = ballEndMillsCandidatas.get(0);

		for (int i = 1; i < ballEndMillsCandidatas.size(); i++) {// Seleciona a
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
