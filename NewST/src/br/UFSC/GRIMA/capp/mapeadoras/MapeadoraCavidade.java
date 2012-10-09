package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
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
			double L = getMaiorDiametro(cavidadeTmp, 150);// 150 = Numero de pontos que a malha vai ter

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
//			if (faceMill.getDiametroFerramenta() > getMenorDiametro()) {

			
				double D = getMenorDiametro(cavidadeTmp, 150);// 150 = Numero de pontos que a malha vai ter

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, cavidadeTmp, 0, D);
				
				System.out.println("FERRAMENTA MENOR DIAMETRO:   " + faceMill2.getDiametroFerramenta());

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(0);
				operation2.setAllowanceBottom(0);

				double x = comprimento / 2 - faceMill2.getDiametroFerramenta()
						/ 2;
				double y = largura / 2 - faceMill.getDiametroFerramenta() / 2;
				double z = 0;

				operation2.setStartPoint(new Point3d(x, y, z));//MUDAR DEPOIS QUANDO TIVER O GENERALPATH

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

	private static double[][][] getMalha(Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		
		double malha[][][] = new double [numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2];
		double largura=cavidadeTmp.getLargura(), comprimento=cavidadeTmp.getComprimento();
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = cavidadeTmp.getPosicaoX()+comprimento*(i+1)/numeroDePontosDaMalha;//x
				malha[i][k][1] = cavidadeTmp.getPosicaoY()+largura*(k+1)/numeroDePontosDaMalha;//y
			}
		}
		
		return malha;
	}
	
	private static ArrayList<Point3d> getPontosPossiveis(double z,Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		
		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(cavidadeTmp.getPosicaoX(), cavidadeTmp.getPosicaoY(), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), 2*cavidadeTmp.getRaio(), 2*cavidadeTmp.getRaio());
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
		double[][][] malha = getMalha(cavidadeTmp, numeroDePontosDaMalha);
		int b=0;
		ArrayList<Shape> bossArray = getBossArray(z, cavidadeTmp); 
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
//						System.out.println(k);
					}
					b=0;
				}
			}
		}
		
		return pontosPossiveis;
	}
	
	private static ArrayList<Point2d> getCoordenadas(double z, Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		

		ArrayList<Point2d> coordenadas = new ArrayList<Point2d>();
		RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(cavidadeTmp.getPosicaoX(), cavidadeTmp.getPosicaoY(), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), 2*cavidadeTmp.getRaio(), 2*cavidadeTmp.getRaio());
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
		double[][][] malha = getMalha(cavidadeTmp, numeroDePontosDaMalha);
		int b=0;
		ArrayList<Shape> bossArray = getBossArray(z, cavidadeTmp); 
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						coordenadas.add(new Point2d(i,k));
//						System.out.println(k);
					}
					b=0;
				}
			}
		}
		
		return coordenadas;
	}

	private static ArrayList<Shape> getBossArray(double z, Cavidade cavidadeTmp){

		ArrayList<Boss> itsBoss;
		itsBoss = cavidadeTmp.getItsBoss();
		double raioAtual;
		ArrayList<Shape> bossArray;
		
		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		Boss bossTmp;
		
		
		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-cavidadeTmp.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX(), bossTmp.getPosicaoY(), boss.getL1(), boss.getL2(), boss.getRadius()*2, boss.getRadius()*2));
			}
			else if(itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				GeneralPath path = new GeneralPath();
				path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
				for(int r=0;r<vertex.size();r++){
					path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
				}
				path.closePath();
				bossArray.add(path);
			}
		}
		
		return bossArray;
	}
	
	private static ArrayList<Point3d> getPontosPeriferia(double z,Cavidade cavidadeTmp){
		
		ArrayList<Point3d> pontosPeriferia;ArrayList<Boss> itsBoss;
		itsBoss = cavidadeTmp.getItsBoss();
		double raioAtual;
		Point2D borda[];
		
		pontosPeriferia = new ArrayList<Point3d>();
		Boss bossTmp;
		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-cavidadeTmp.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				borda = Cavidade.determinarPontosEmCircunferencia(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2*raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), boss.getL1(), boss.getL2(), boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				double distancia, maiorX, maiorY;
				int q;
				for(int j=0;j<vertex.size();j++){
					if(j==vertex.size()-1)
						q=0;
					else
						q=j+1;
					
					if(vertex.get(j).getX()>vertex.get(q).getX())
						maiorX = vertex.get(j).getX();
					else
						maiorX = vertex.get(q).getX();
					
					if(vertex.get(j).getY()>vertex.get(q).getY())
						maiorY = vertex.get(j).getY();
					else
						maiorY = vertex.get(q).getY();
					
					
					if(vertex.get(j).getX()==vertex.get(q).getX()){
						distancia = vertex.get(j).getY();
						for(int h=0;h<1000;h++){
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
							if(maiorY == vertex.get(j).getY())
								distancia-=1;
							else
								distancia+=1;
							if(distancia==vertex.get(q).getY()){
								h=1000;
								pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
							}
						}
					}
					else if(vertex.get(j).getY()==vertex.get(q).getY()){
						distancia = vertex.get(j).getX();
						for(int h=0;h<1000;h++){
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
							if(maiorX == vertex.get(j).getX())
								distancia-=1;
							else
								distancia+=1;
							if(distancia==vertex.get(q).getX()){
								h=1000;
								pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
							}
						}
					}
					else{
						double a,b;
						a= (vertex.get(q).getY()-vertex.get(j).getY())/(vertex.get(q).getX()-vertex.get(j).getX());
						b= vertex.get(j).getY()-a*vertex.get(j).getX();
													
						if(Math.abs(vertex.get(j).getX()-vertex.get(q).getX())>Math.abs(vertex.get(j).getY()-vertex.get(q).getY())){
							distancia = vertex.get(j).getX();
							for(int h=0;h<1000;h++){
								pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
								if(maiorX == vertex.get(j).getX()){
									distancia-=1;
									if(distancia<=vertex.get(q).getX()){
										h=1000;
										pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
									}
								}
								else{
									distancia+=1;
									if(distancia>=vertex.get(q).getX()){
										h=1000;
										pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
									}
								}
							}	
						}
						else{
							distancia = vertex.get(j).getY();
							for(int h=0;h<1000;h++){
								pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
								if(maiorY == vertex.get(j).getY()){
									distancia-=1;
									if(distancia<=vertex.get(q).getY()){
										h=1000;
										pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
									}
								}
								else{
									distancia+=1;
									if(distancia>=vertex.get(q).getY()){
										h=1000;
										pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
									}
								}
							}	
						}
					}
				}		
			}
		}

		borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(cavidadeTmp.getPosicaoX(),cavidadeTmp.getPosicaoY(),z), cavidadeTmp.getComprimento(), cavidadeTmp.getLargura(), cavidadeTmp.getRaio());
		for(int k=0;k<borda.length;k++){
			pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
		}
		
		return pontosPeriferia;
	}
	
	private static double[][] getMalhaMenoresDistancias(double z, Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		
		ArrayList<Point3d> pontosPeriferia = getPontosPeriferia(z, cavidadeTmp);
		double malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha];
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, cavidadeTmp, numeroDePontosDaMalha);
		ArrayList<Point2d> coordenadas = getCoordenadas(z, cavidadeTmp, numeroDePontosDaMalha);
		ArrayList<Double> menorDistancia;		
		double distanciaTmp;

		menorDistancia = new ArrayList<Double>();
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);		
		}
		return malhaMenoresDistancias;
	}
	
	private static double getMenorDiametro(Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		double menorDiametro, raioMenor=10000;
		double[][] malhaMenoresDistancias = getMalhaMenoresDistancias(-cavidadeTmp.getProfundidade(), cavidadeTmp, numeroDePontosDaMalha);
		int contador;
		
		for(int i=1;i<malhaMenoresDistancias.length-1;i++){
			for(int k=1;k<malhaMenoresDistancias.length-1;k++){
				contador = 0;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]<=2)
					contador=0;
				
				if(contador>=6){
					if(raioMenor>malhaMenoresDistancias[i][k])
						raioMenor = malhaMenoresDistancias[i][k];
				}
			}
		}
		
		menorDiametro = raioMenor*2;

		System.out.println("MENOR DIAMETRO:   " + menorDiametro);
		return menorDiametro;
	}
	
	private static double getMaiorDiametro(Cavidade cavidadeTmp, int numeroDePontosDaMalha){
		double[][] malhaMenoresDistancias;
		int contador,numeroDeDiametrosAdicionados=0;
		double raioMedia, maiorDiametro=0;

		malhaMenoresDistancias = getMalhaMenoresDistancias(-cavidadeTmp.getProfundidade(), cavidadeTmp, numeroDePontosDaMalha);
		raioMedia=0;
		numeroDeDiametrosAdicionados=0;
		for(int i=1;i<malhaMenoresDistancias.length-1;i++){
			for(int k=1;k<malhaMenoresDistancias.length-1;k++){
				contador = 0;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]<=2)
					contador=0;

				if(contador>=6){
					numeroDeDiametrosAdicionados++;
					raioMedia+=malhaMenoresDistancias[i][k];
				}
			}
		}
		raioMedia = raioMedia/numeroDeDiametrosAdicionados;
		maiorDiametro+=2*raioMedia;

		return maiorDiametro;
	}
	
}
