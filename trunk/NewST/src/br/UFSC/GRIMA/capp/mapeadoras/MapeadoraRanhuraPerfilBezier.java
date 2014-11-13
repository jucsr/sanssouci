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
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Bidirectional;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRanhuraPerfilBezier {

	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private RanhuraPerfilBezier ranhuraTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;
//	private Point3d [] pontosDeControle;
	private Point3d [] pontosDaCurva;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<BallEndMill> ballEndMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraRanhuraPerfilBezier(Projeto projeto) {

		this.projeto = projeto;
		this.bloco = projeto.getBloco();

	}

	public MapeadoraRanhuraPerfilBezier(Projeto projeto, Face face, RanhuraPerfilBezier ranhura) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.ranhuraTmp = ranhura;
		this.bloco = projeto.getBloco();

		this.faceMills = ToolManager.getFaceMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		this.endMills = ToolManager.getEndMills();
		
		this.verificarPerfilBezier();

		this.mapearRanhura();

	}

	private void verificarPerfilBezier() {
	
		boolean ok = false;
		
		double x = ranhuraTmp.getPosicaoX();
		double y = ranhuraTmp.getPosicaoY();
		
		boolean ranhuraAbrangeBloco = false;
		
		boolean ranhuraHorInferior = false;
		boolean ranhuraHorSuperior= false;
		
		boolean ranhuraVerEsquerda= false;
		boolean ranhuraVerDireita= false;
		
		
		if(ranhuraTmp.isEmTodaAPeca()){
			
			ranhuraAbrangeBloco = true;
			
		}else if(ranhuraTmp.getEixo()==Ranhura.HORIZONTAL){
			
			if(y==0.0)
				ranhuraHorInferior = true;
			else if((y+ranhuraTmp.getLargura()) == bloco.getLargura())
				ranhuraHorSuperior = true;
			
		}else if(ranhuraTmp.getEixo()==Ranhura.VERTICAL){
			
			if(x==0.0)
				ranhuraVerEsquerda = true;
			else if( (x+ranhuraTmp.getLargura()) == bloco.getComprimento())
				ranhuraVerDireita = true;
			
		}else{
			System.out.println("Eixo de Ranhura Desconhecido!!! (verificarPerfilBezier)");
		}
		
//		this.pontosDeControle = this.ranhuraTmp.getPontosDeControle();
		
//		this.pontosDaCurva = (new Bezier_1(pontosDeControle, 10)).getMeshArray();
		this.pontosDaCurva = this.ranhuraTmp.getPontosDaCurva();
		
		
		String pontos = "";
		
		for(int i =0;i<pontosDaCurva.length;i++){
			
			pontos = pontos + pontosDaCurva[i] + " ";
			
		}

//		System.out.println("PONTOS DA CURVAAAAAAA: " + pontos);
		
		int length = pontosDaCurva.length;
		
		Point3d primeroPonto = pontosDaCurva[0];
		Point3d segundoPonto = pontosDaCurva[1];

		Point3d penultimoPonto = pontosDaCurva[length-2];
		Point3d ultimoPonto = pontosDaCurva[length-1];
		
		String motivo = "";
		
		if(ranhuraAbrangeBloco){
			
			ok=true;
			
		}else if(ranhuraHorInferior||ranhuraVerDireita){
			
			double inclinacao = ultimoPonto.getY() - penultimoPonto.getY(); // Y = Z
			
			if(inclinacao>=0)
				ok = true;
			else
				ok = false;
			
			motivo = "Motivo: Inclinação inconsistente na extremidade direita do Perfil!";
			
		}else if(ranhuraHorSuperior||ranhuraVerEsquerda){
			
			double inclinacao = primeroPonto.getY() - segundoPonto.getY(); // Y = Z
			
			if(inclinacao>=0)
				ok = true;
			else
				ok = false;
			
			motivo = "Motivo: Inclinação inconsistente na extremidade esquerda do Perfil!";
			
		}else{
			
			double inclinacaoInicial = primeroPonto.getY() - segundoPonto.getY(); // Y = Z
			
			double inclinacaoFinal = ultimoPonto.getY() - penultimoPonto.getY(); // Y = Z

			if(inclinacaoInicial>=0 && inclinacaoFinal>=0)
				ok = true;
			else
				ok = false;
			
			if(inclinacaoInicial<0 && inclinacaoFinal<0)
				motivo = "Motivo: Inclinação inconsistente em ambas as extremidades do Perfil!";
			else if(inclinacaoInicial<0)
				motivo = "Motivo: Inclinação inconsistente na extremidade esquerda do Perfil!";
			else if(inclinacaoFinal<0)
				motivo = "Motivo: Inclinação inconsistente na extremidade direita do Perfil!";
		}
		
		if(!ok){
			
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Ranhura Perfil Bezier, modifique os Pontos de Controle!" +"\n"+
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Bezier \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidade()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"__________________________________________________________"+"\n"+
					"\t"+motivo
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
		}
		
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
					ranhuraTmp, ranhuraTmp.getLargura(), Feature.LIMITE_DESBASTE);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("GeneralProfileSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			//Segunda Facemill para desbastes mais profundos
			
			double L = determinarDiametroMinimo();
			double z = ranhuraTmp.getProfundidade();

			// FERRAMENTA
			FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, L, Feature.LIMITE_DESBASTE);
			
			if(!faceMill2.equals(faceMill)){

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
				operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);
				// Start Point vai ser setado na otimizacao
				operation2.setStartPoint(new Point3d(0,0,z));

				// PLUNGE STRATEGY
				PlungeToolAxis plungeStrategy2 = new PlungeToolAxis();
				operation2.setApproachStrategy(plungeStrategy2);

				// MACHINING STRATEGY
				Bidirectional machiningStrategy2 = new Bidirectional();
				machiningStrategy2.setAllowMultiplePasses(true);
				operation2.setMachiningStrategy(machiningStrategy2);				
				
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
						.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("GeneralProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}
			// WORKINGSTEPS DE ACABAMENTO

			int length = ranhuraTmp.getPontosDaCurva().length;
			
			if(ranhuraTmp.getPontosDaCurva()[0].y<0 || ranhuraTmp.getPontosDaCurva()[length-1].y<0){

				// BOTTOM AND SIDE FINNISH MILLING
				BottomAndSideFinishMilling operation3 = new BottomAndSideFinishMilling(
						"Bottom And Side Finish Milling", retractPlane);
				operation3.setAllowanceSide(0);
				operation3.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation3.setStartPoint(new Point3d(0,0,0));

				// PLUNGE STRATEGY
				PlungeToolAxis plungeStrategy3 = new PlungeToolAxis();
				operation3.setApproachStrategy(plungeStrategy3);

				// MACHINING STRATEGY
				Bidirectional machiningStrategy3 = new Bidirectional();
				machiningStrategy3.setAllowMultiplePasses(true);
				operation3.setMachiningStrategy(machiningStrategy3);
				
				// FERRAMENTA
				EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills,
						ranhuraTmp);

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, endMill, bloco
						.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, endMill,
						condicoesDeUsinagem, operation3);
				wsTmp.setTipo(Workingstep.ACABAMENTO);
				wsTmp.setId("GeneralProfileSlot_FNS");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);
				
			}
			
			// FREE FORM OPERATION
			
			FreeformOperation operation3 = new FreeformOperation("Free Form Operaion", retractPlane);
//			operation3.setAllowanceSide(0);
//			operation3.setAllowanceBottom(0);
			// Start Point vai ser setado na otimizacao
			operation3.setStartPoint(new Point3d(0,0,z));

			// PLUNGE STRATEGY
			PlungeToolAxis plungeStrategy3 = new PlungeToolAxis();
			operation3.setApproachStrategy(plungeStrategy3);

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills,
					ranhuraTmp, L);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
			.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
					.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("GeneralProfileSlot_FNS");
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
					ranhuraTmp, ranhuraTmp.getLargura(), 0);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("GeneralProfileSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			//Segunda Facemill para desbastes mais profundos
			
			double L = determinarDiametroMinimo();
			double z = ranhuraTmp.getProfundidade();

			// FERRAMENTA
			FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
					ranhuraTmp, L, 0);
			
			if(!faceMill2.equals(faceMill)){

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(0);
				operation2.setAllowanceBottom(0);
				// Start Point vai ser setado na otimizacao
				operation2.setStartPoint(new Point3d(0,0,z));


				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
						.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(ranhuraTmp, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("GeneralProfileSlot_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			}

			// BOTTOM AND SIDE ROUGH MILLING
//			BottomAndSideRoughMilling operation3 = new BottomAndSideRoughMilling(
//					"Bottom And Side Rough Milling", retractPlane);
//			operation3.setAllowanceSide(0);
//			operation3.setAllowanceBottom(0);
			FreeformOperation operation3 = new FreeformOperation("Free Form Operaion", retractPlane);
			// Start Point vai ser setado na otimizacao
			operation3.setStartPoint(new Point3d(0,0,z));

			// FERRAMENTA
			BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills,
					ranhuraTmp, L);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
			.getCondicoesDeUsinagem(this.projeto, ballEndMill, bloco
					.getMaterial());
			condicoesDeUsinagem.setAp(2);
			// WORKINGSTEP
			wsTmp = new Workingstep(ranhuraTmp, faceTmp, ballEndMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("GeneralProfileSlot_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			ranhuraTmp.setWorkingsteps(wssFeature);
		}
	}

	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, RanhuraPerfilBezier ranhuraTmp,
			double L, double limite_desbaste) {

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
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (ranhuraTmp
							.getProfundidadeMaxima() - limite_desbaste)) {

				faceMillsCandidatas.add(faceMill);

			}

		}

		if (faceMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Bezier \n" +
					"\tNome: " + ranhuraTmp.getNome() +"\n" +
					"\tComprimento: " + ranhuraTmp.getComprimento()+" mm"+"\n" +
					"\tLargura: " + ranhuraTmp.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + ranhuraTmp.getProfundidadeMaxima()+" mm"+"\n" +
					"\tEixo: " + ranhuraTmp.getStringEixo()+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (L - 2 * limite_desbaste)+" mm" +"\n" +
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
			ArrayList<EndMill> endMills, RanhuraPerfilBezier ranhuraTmp) {

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

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Bezier \n" +
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

	private BallEndMill chooseBallEndMill(Material material,
			ArrayList<BallEndMill> ballEndMills, RanhuraPerfilBezier ranhuraTmp,
			double L) {

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
					&& ballEndMill.getDiametroFerramenta() <= L
					&& ballEndMill.getProfundidadeMaxima() >= (ranhuraTmp.getProfundidadeMaxima())
					&& ballEndMill.getEdgeRadius() <= L/2) {

				ballEndMillsCandidatas.add(ballEndMill);
			}
		}

		if (ballEndMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Bezier \n" +
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
					"\tDiametro da Ferramenta deve ser menor igual a: " + L+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + ranhuraTmp.getProfundidadeMaxima()+" mm"+"\n\n" +
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
	private double determinarDiametroMinimo()
	{
		Point3d[] pontosDaCurva = ranhuraTmp.getPontosDaCurva();
		
		double diametroMinimo = 1000;
		
		for(int i = 0; i < pontosDaCurva.length - 2; i++)
		{
			
			double l1Tmp = Math.pow(Math.pow((pontosDaCurva[i + 1].x - pontosDaCurva[i].x), 2) +  Math.pow((pontosDaCurva[i + 1].y - pontosDaCurva[i].y), 2), 0.5);
			double l2Tmp = Math.pow(Math.pow((pontosDaCurva[i + 2].x - pontosDaCurva[i + 1].x), 2) +  Math.pow((pontosDaCurva[i + 2].y - pontosDaCurva[i + 1].y), 2), 0.5);
			double l3Tmp = Math.pow(Math.pow((pontosDaCurva[i + 2].x - pontosDaCurva[i].x), 2) +  Math.pow((pontosDaCurva[i + 2].y - pontosDaCurva[i].y), 2), 0.5);
			double anguloTmp = Math.acos((Math.pow(l2Tmp, 2) + Math.pow(l3Tmp, 2) - Math.pow(l1Tmp, 2)) / (2 * l2Tmp * l3Tmp));
			
//			double diametroTmp = Math.pow(l1Tmp, 2) / 2 / Math.pow(Math.pow(l1Tmp, 2) - Math.pow(l2Tmp, 2) / 4, 0.5);
			double diametroTmp = l1Tmp / Math.sin(anguloTmp);
			
			// verificar onde a curva é concava
			double deltaY1;
			double deltaY2;
			
			double y0 = pontosDaCurva[i].y;
			double y1 = pontosDaCurva[i+1].y;
			double y2 = pontosDaCurva[i+2].y;
			
			deltaY1 = y1-y0;
			deltaY2 = y2-y1;
			
			if(deltaY2>=deltaY1){

				if (diametroTmp < diametroMinimo)
				{
					diametroMinimo = diametroTmp;
				}
			}
			
			
			System.out.println(i + " diametroTmp = " + diametroTmp);
		}
//		System.out.println("diametro minimo = "  + diametroMinimo);
		return diametroMinimo;
	}
}
