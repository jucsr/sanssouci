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

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
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
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraGeneralClosedPocket {
	
	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private GeneralClosedPocket genClosed;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private ArrayList<Boss> itsBoss;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraGeneralClosedPocket(Projeto projeto, Face face, GeneralClosedPocket genClosed) {

		this.projeto = projeto;
		this.faceTmp = face;
		this.genClosed = genClosed;
		this.bloco = projeto.getBloco();
		this.itsBoss = genClosed.getItsBoss();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		
		this.mapearGeneralClosedPocket();

	}

	private void mapearGeneralClosedPocket() {
		
		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp;
		
		double retractPlane = 5;

		wssFeature = new Vector<Workingstep>();
/*
 *  Comentei o que segue ... verificar pq estava aew!!!
 *  Tenho a impressao que foi copiado e colado da classe mapeadora de cavidade retangular!!!
 *  
 */
//		if (genClosed.getTolerancia() <= Feature.LIMITE_RUGOSIDADE
//				&& genClosed.getRugosidade() <= Feature.LIMITE_TOLERANCIA) {
//			genClosed.setAcabamento(true);
//		} 

		if(genClosed.getFeaturePrecedente()!= null){
			
			if(		genClosed.getFeaturePrecedente().getClass().equals(GeneralProfileBoss.class) || 
					genClosed.getFeaturePrecedente().getClass().equals(RectangularBoss.class) ||
					genClosed.getFeaturePrecedente().getClass().equals(CircularBoss.class))
			{
				wsPrecedenteTmp = genClosed.getFeaturePrecedente().getFeaturePrecedente().getWorkingsteps().lastElement();
			}
			else{
				wsPrecedenteTmp = genClosed.getFeaturePrecedente().getWorkingsteps().lastElement();
			}			
			System.out.println("wsp = " + genClosed.getFeaturePrecedente().getWorkingsteps().size());
			System.out.println("feature Pre = " + genClosed.getFeaturePrecedente());
//			wsPrecedenteTmp = genClosed.getFeaturePrecedente().getWorkingsteps().lastElement();
		}else{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		System.out.println("AAAAAAAAAAAAAAA = " + genClosed.isAcabamento());
		if (genClosed.isAcabamento()) {

			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(Feature.LIMITE_DESBASTE);

			if (!genClosed.isPassante())
				operation1.setAllowanceBottom(Feature.LIMITE_DESBASTE);

			operation1.setStartPoint(new Point3d(0, 0, 0));

//			double comprimento = genClosed.getComprimento();
//			double largura = genClosed.getLargura();
//			double L = getMenorDiametro(genClosed, 150);// 150 = Numero de pontos que a malha vai ter

//			if (largura < comprimento)
//				L = largura;

			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					genClosed, Feature.LIMITE_DESBASTE, 0);//TROCAR 0 POR L

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(genClosed, faceTmp, faceMill, condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId(this.genClosed.getNome() + "_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
			
			
			//FRESA PRA ACABAMENTO ANTECIPADA
			// FERRAMENTA
			EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills, genClosed);
			
			double dEndMill = endMill.getDiametroFerramenta();
			
			boolean precisaSegDesb = false;
			
			double rFresa = faceMill.getDiametroFerramenta()/2;
			double rGenClosed = genClosed.getRadius();
			
			double sobra = rFresa*(Math.sqrt(2)-1) - rGenClosed*(Math.sqrt(2)-1) + Feature.LIMITE_DESBASTE;
			
			if(dEndMill*0.75 >= sobra)
				precisaSegDesb = false;
			else
				precisaSegDesb = true;

			// se o raio da facemill for maior q o da cavidade
			if (faceMill.getDiametroFerramenta() / 2 > genClosed.getRadius()
					&& precisaSegDesb) {

				double D = genClosed.getRadius() * 2;

				// FERRAMENTA
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, genClosed, Feature.LIMITE_DESBASTE, D);

				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
				if (!genClosed.isPassante())
					operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);

//				double x = comprimento / 2 - Feature.LIMITE_DESBASTE
//						- faceMill2.getDiametroFerramenta() / 2;
//				double y = largura / 2 - Feature.LIMITE_DESBASTE
//						- faceMill.getDiametroFerramenta() / 2;
//				double z = 0;

				operation2.setStartPoint(new Point3d(0, 0, 0));

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(genClosed, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId(this.genClosed.getNome() + "_RGH");
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

//			double x = genClosed.getComprimento() / 2
//					- endMill.getDiametroFerramenta() / 2;

			operation3.setStartPoint(new Point3d(0, 0, 0));

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, endMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(genClosed, faceTmp, endMill,
					condicoesDeUsinagem, operation3);
			wsTmp.setTipo(Workingstep.ACABAMENTO);
			wsTmp.setId("Pocket_FNS");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			genClosed.setWorkingsteps(wssFeature);

		} else {

			
			ArrayList<Point2d> coordenadas;
			ArrayList<Point2D> vertex;
			ArrayList<Point2D> vertexPoint;
			ArrayList<Point3d> pontosPeriferia;
			ArrayList<Point3d> pontosPossiveis;
			ArrayList<ArrayList<Point3d>> pontos = null;
			ArrayList<Double> menorDistancia;
			ArrayList<Shape> bossArray;
			GeneralPath general;
			int numeroDePontosDaMalha;
			double[][] malhaMenoresDistancias;
			double maiorDiametro, menorDiametro, distEntreFerramentas, variacaoDaFerramenta, allowanceSide, allowanceBottom , maiorMenorDistancia = 0;
			double maiorFerramenta, menorFerramenta, diametro1 = 0, diametro2 = 0, raio, xMaior, xMenor, yMaior, yMenor, largura, comprimento, z, ae;
			double[][][] malha;
			
			
			raio = genClosed.getRadius();
			numeroDePontosDaMalha = 150;
			malhaMenoresDistancias = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1];
			malha = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2];
			
			vertex = genClosed.getPoints();
			general = new GeneralPath();

			//CRIA O GENERAL PATH DO FORMATO
			vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), raio);
			general.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
			for(int r=0;r<vertex.size();r++){
				general.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
			}
			general.closePath();
			
			
			//CRIAR MALHA DE PONTOS PARA COMPARA��O
			
			xMaior = 0; xMenor = 10000; yMaior = 0; yMenor = 10000;
			
			vertexPoint = genClosed.getPoints();
			for(int i=0;i<vertexPoint.size();i++){
				if(xMenor>vertexPoint.get(i).getX())
					xMenor=vertexPoint.get(i).getX();
				if(xMaior<vertexPoint.get(i).getX())
					xMaior=vertexPoint.get(i).getX();
				if(yMenor>vertexPoint.get(i).getY())
					yMenor=vertexPoint.get(i).getY();
				if(yMaior<vertexPoint.get(i).getY())
					yMaior=vertexPoint.get(i).getY();			
			}
			largura = yMaior-yMenor;
			comprimento = xMaior-xMenor;

			for(int i=0;i<malha.length;i++){
				for(int k=0;k<malha[i].length;k++){
					malha[i][k][0] = xMenor+comprimento*(i+1)/numeroDePontosDaMalha;//x
					malha[i][k][1] = yMenor+largura*(k+1)/numeroDePontosDaMalha;//y
				}
			}
			
			z = -genClosed.getProfundidade(); allowanceSide = 0.5;
			
			bossArray = getBossArray(allowanceSide, z);
			
			pontosPossiveis = getPontosPossiveis(malha, general, z, bossArray);

			pontosPeriferia = getPontosPeriferia(allowanceSide, z, raio, vertex);
			
			coordenadas = getCoordenadas(malha, general, bossArray);

			malhaMenoresDistancias = getMalhaMenoresDistancias(numeroDePontosDaMalha, pontosPossiveis, pontosPeriferia, coordenadas);
			
			menorDistancia = getMenorDistancia(pontosPossiveis, pontosPeriferia);

			double diametroTmp=1000, raioMedia=0, raioMenor=10000;
			int contador=0, numeroDeDiametrosAdicionados=0;

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
						raioMedia+=malhaMenoresDistancias[i][k];
						if(raioMenor>malhaMenoresDistancias[i][k])
							raioMenor = malhaMenoresDistancias[i][k];
					}
				}
			}

			raioMedia = raioMedia/numeroDeDiametrosAdicionados;
			maiorDiametro = 2*raioMedia;
			menorDiametro = 2*raioMenor;
			
			
			for(int i=0;i<menorDistancia.size();i++){
				if(maiorMenorDistancia<menorDistancia.get(i)){
					maiorMenorDistancia=menorDistancia.get(i);
				}
			}
			
			
			FaceMill faceMillMaior = chooseFaceMill(bloco.getMaterial(), faceMills,
					genClosed, 0, maiorDiametro);
			
			FaceMill faceMillMenor = chooseFaceMill(bloco.getMaterial(),
					faceMills, genClosed, 0, menorDiametro);
			
			FaceMill faceMill1 = null;
			FaceMill faceMill2 = null;
			
			maiorFerramenta = faceMillMaior.getDiametroFerramenta();
			menorFerramenta = faceMillMenor.getDiametroFerramenta();
			distEntreFerramentas = maiorFerramenta - menorFerramenta;
			variacaoDaFerramenta = 5;
			System.err.println("Maior Ferramenta =" + maiorFerramenta + "   Menor Ferramenta =" + menorFerramenta);
			
			//SETANDOS OS VALORES DOS WS!!!!!
			
			allowanceSide = 0;
			allowanceBottom = 0;
			
			// 1 - PARA QUE SEJA CRIADO 4 WS PRECISA VER SE ALGUMA COISA � UNSINADA EM TODOS OS WS, PARA ISSO UTILIZAR A LOGICA DA MOVIMENTACAO (VAI FICAR MUITO DEMORADO?)
			//     AQUI J� TENHO OS VALORES DA MAIOR E DA MENOR FERRAMENTA
			//     COMPARAR AS DUAS FERRAMENTAS PARA VER SE A DIFEREN�A � MUITO GRANDE, SE FOR UTILIZAR O PASSO 1. CASO NAO UTILIZAR S� 1 WS COM A MENOR FERRAMENTA
			
			if(distEntreFerramentas < variacaoDaFerramenta)
			{//CRIAR WS COM A MENOR FERRAMENTE POIS A MAIOR E A MENOR SAO QUASE DO MESMO TAMANHO.
				wsTmp = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				wssFeature.add(wsTmp);
				wsPrecedenteTmp = wsTmp;				
			}
			else
			{
				if(3*variacaoDaFerramenta <= distEntreFerramentas)
				{//VER QUANTOS WS VOU PRECISAR ------- 4WS
					diametro1 = maiorFerramenta - ( (maiorFerramenta - menorFerramenta) / 3 );
					diametro2 = maiorFerramenta - 2*( (maiorFerramenta - menorFerramenta) / 3 );
					
					System.err.println(diametro1 + "   Diametro 1");
					System.err.println(diametro2 + "   Diametro 2");
					
					faceMill1 = chooseFaceMill(bloco.getMaterial(),
							faceMills, genClosed, 0, diametro1);
					diametro1 = faceMill1.getDiametroFerramenta();
					
					faceMill2 = chooseFaceMill(bloco.getMaterial(),
							faceMills, genClosed, 0, diametro2);	
					diametro2 = faceMill2.getDiametroFerramenta();
				}
				else if(2*variacaoDaFerramenta <= distEntreFerramentas)
				{//3 WS
					diametro1 = maiorFerramenta - ( (maiorFerramenta - menorFerramenta) / 2 );
					
					FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
							faceMills, genClosed, 0, diametro1);	
				}
				
				double variacao = (comprimento/numeroDePontosDaMalha+largura/numeroDePontosDaMalha)/2;
				double diferenca;		
				ArrayList<Shape> listaDeBossesCriados;
				//USAR SWITCH--CASE PRA VER QUANTOS WS VOU PODER CRIAR ? 
				//FAZER UMA FUNCAO QUE RETORNE UM INT QUE � O NUMERO DE WS QUE SERAO CRIADAS, E COLOCAR COMO PARAMETRO DO SWITCH--CASE

				ae = 0.75*maiorFerramenta;
				pontos = getPontos(variacao, maiorFerramenta, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);
				
				listaDeBossesCriados = createBosses(variacao, maiorFerramenta/2, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);
				
				for(int i = 0; i < listaDeBossesCriados.size(); i++)
				{//COLOCANDO OS BOOSES DAS PASSADAS DA FERRAMENTA MAIOR NO ARRAY DE BOSSES
					bossArray.add(listaDeBossesCriados.get(i));
				}

				//CRIAR WS
				wsTmp = getWsDesbaste(faceMillMaior, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				wssFeature.add(wsTmp);
				wsPrecedenteTmp = wsTmp;

				pontosPossiveis = getPontosPossiveis(malha, general, z, bossArray);
				//menorDistancia = getMenorDistancia(pontosPossiveis, pontosPeriferia);
				
				ae = 0.75*diametro1;
				pontos = new ArrayList<ArrayList<Point3d>>();
				pontos = getPontos(variacao, diametro1, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);
				

				if(pontos.get(0).size() > 1 && 3*variacaoDaFerramenta <= distEntreFerramentas)
				{//TEM PONTOS SUFICIENTES PARA FAZER ALGUMA MOVIMENTACAO ENTA CRIAR UM WS, SE NAO NAO CRIA WS
					
					listaDeBossesCriados = createBosses(variacao, diametro1/2, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);
					
					for(int i = 0; i < listaDeBossesCriados.size(); i++)
					{//COLOCANDO OS BOOSES DAS PASSADAS DA FERRAMENTA MAIOR NO ARRAY DE BOSSES
						bossArray.add(listaDeBossesCriados.get(i));
					}

					//CRIAR WS
									
					System.err.println(faceMill1 + "     Ferramenta" + "       " + pontos.get(0).size());
					
					wsTmp = getWsDesbaste(faceMill1, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
					wssFeature.add(wsTmp);
					wsPrecedenteTmp = wsTmp;

					pontosPossiveis = getPontosPossiveis(malha, general, z, bossArray);
					//menorDistancia = getMenorDistancia(pontosPossiveis, pontosPeriferia);

					ae = 0.75*diametro2;
					pontos = new ArrayList<ArrayList<Point3d>>();
					pontos = getPontos(variacao, diametro2, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);

					if(pontos.get(0).size() > 1 && diametro2 != menorFerramenta)
					{//TEM PONTOS SUFICIENTES PARA FAZER ALGUMA MOVIMENTACAO ENTAO CRIAR UM WS, SE NAO NAO CRIA WS

						listaDeBossesCriados = createBosses(variacao, diametro2/2, ae, maiorMenorDistancia, pontosPossiveis, menorDistancia);
						
						for(int i = 0; i < listaDeBossesCriados.size(); i++)
						{//COLOCANDO OS BOOSES DAS PASSADAS DA FERRAMENTA MAIOR NO ARRAY DE BOSSES
							bossArray.add(listaDeBossesCriados.get(i));
						}

						//CRIAR WS
						
						wsTmp = getWsDesbaste(faceMill2, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
						wssFeature.add(wsTmp);
						wsPrecedenteTmp = wsTmp;
						
					}

				}

				//CRIAR WS DA MENOR FERRAMENTA E DAS PARTES FOR�ADAS(CANTOS)
				
				wsTmp = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				wssFeature.add(wsTmp);
				wsPrecedenteTmp = wsTmp;

											
			}
			/*
			if(3*variacaoDaFerramenta <= distEntreFerramentas)
			{// 4 WS
				
				diametro1 = maiorFerramenta - ( (maiorFerramenta - menorFerramenta) / 3 );
				diametro2 = maiorFerramenta - 2*( (maiorFerramenta - menorFerramenta) / 3 );
				
				faceMill1 = chooseFaceMill(bloco.getMaterial(),
						faceMills, genClosed, 0, diametro1);
				
				faceMill2 = chooseFaceMill(bloco.getMaterial(),
						faceMills, genClosed, 0, diametro2);
				
				ws1 = getWsDesbaste(faceMillMaior, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws2 = getWsDesbaste(faceMill1, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws3 = getWsDesbaste(faceMill2, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws4 = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				
				wssFeature.add(ws1);				
				wsPrecedenteTmp = ws1;
				
				{//DESBASTA ALGUMA COISA ?
					wssFeature.add(ws2);
					wsPrecedenteTmp = ws2;
				}
				
				wssFeature.add(ws3);
				wsPrecedenteTmp = ws3;
				wssFeature.add(ws4);
				wsPrecedenteTmp = ws4;
			}
			else if(2*variacaoDaFerramenta <= distEntreFerramentas)
			{// 3 WS
				
				diametro1 = maiorFerramenta - ( (maiorFerramenta - menorFerramenta) / 2 );
				
				FaceMill faceMill = chooseFaceMill(bloco.getMaterial(),
						faceMills, genClosed, 0, diametro1);
				
				ws1 = getWsDesbaste(faceMillMaior, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws2 = getWsDesbaste(faceMill, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws3 = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				
				wssFeature.add(ws1);			
				wsPrecedenteTmp = ws1;
				wssFeature.add(ws2);			
				wsPrecedenteTmp = ws2;
				wssFeature.add(ws3);				
				wsPrecedenteTmp = ws3;			
			}
			else if(variacaoDaFerramenta <= distEntreFerramentas)
			{// 2 WS
				ws1 = getWsDesbaste(faceMillMaior, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				ws2 = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				
				wssFeature.add(ws1);			
				wsPrecedenteTmp = ws1;
				wssFeature.add(ws2);			
				wsPrecedenteTmp = ws2;
			}
			else
			{// 1 WS
				ws1 = getWsDesbaste(faceMillMenor, wsPrecedenteTmp, retractPlane, allowanceSide, allowanceBottom, genClosed, faceTmp, condicoesDeUsinagem, bloco);
				
				wssFeature.add(ws1);			
				wsPrecedenteTmp = ws1;				
			}
	*/
			/*		
			
			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(0);
			operation1.setAllowanceBottom(0);
			operation1.setStartPoint(new Point3d(0, 0, 0));

//			double comprimento = genClosed.getComprimento();
//			double largura = genClosed.getLargura();
			
			// FERRAMENTA

			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					genClosed, 0, maiorDiametro);
			
			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
							.getMaterial());
			// WORKINGSTEP
			wsTmp = new Workingstep(genClosed, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId("Pocket_RGH");
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);

			// se o raio da facemill for maior q o da cavidade
//			if (faceMill.getDiametroFerramenta() > getMenorDiametro()) {

			
				
				// FERRAMENTA
			
				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(),
					faceMills, genClosed, 0, menorDiametro);
			
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operation2.setAllowanceSide(0);
				operation2.setAllowanceBottom(0);

//				double x = comprimento / 2 - faceMill2.getDiametroFerramenta()
//						/ 2;
//				double y = largura / 2 - faceMill.getDiametroFerramenta() / 2;
//				double z = 0;
//
				operation2.setStartPoint(new Point3d(0, 0, 0));//MUDAR DEPOIS QUANDO TIVER O GENERALPATH

				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMill2, bloco
								.getMaterial());
				// WORKINGSTEP
				wsTmp = new Workingstep(genClosed, faceTmp, faceMill2,
						condicoesDeUsinagem, operation2);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId("Pocket_RGH");
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;

				wssFeature.add(wsTmp);

			 */
			

				genClosed.setWorkingsteps(wssFeature);
		}
	}

	private Workingstep getWsDesbaste(FaceMill faceMill, Workingstep wsPrecedenteTmp, double retractPlane , double allowanceSide
										, double allowanceBottom, Feature genClosed, Face faceTmp, CondicoesDeUsinagem condicoesDeUsinagem, Bloco bloco)
	{
		// WORKINGSTEPS DE DESBASTE

		Workingstep wsTmp;
		
		// BOTTOM AND SIDE ROUGH MILLING
		BottomAndSideRoughMilling operation = new BottomAndSideRoughMilling(
				"Bottom And Side Rough Milling", retractPlane);
		operation.setAllowanceSide(allowanceSide);
		operation.setAllowanceBottom(allowanceBottom);
		operation.setStartPoint(new Point3d(0, 0, 0));

		// CONDIÇÕES DE USINAGEM

		condicoesDeUsinagem = MapeadoraDeWorkingsteps
				.getCondicoesDeUsinagem(this.projeto, faceMill, bloco
						.getMaterial());
		// WORKINGSTEP
		wsTmp = new Workingstep(genClosed, faceTmp, faceMill,
				condicoesDeUsinagem, operation);
		wsTmp.setTipo(Workingstep.DESBASTE);
		wsTmp.setId(this.genClosed.getNome() + "_RGH");
		wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
					
		return wsTmp;
	}
	
	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, GeneralClosedPocket genClosed,
			double limite_desbaste, double L) {

		ArrayList<FaceMill> faceMillsCandidatas = new ArrayList<FaceMill>();

		FaceMill faceMill = null;

		String ISO = "";
		
		String motivo = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_FaceMill");

		double limite_desbaste_fundo = limite_desbaste;
		
		if (genClosed.isPassante())
			limite_desbaste_fundo = 0;
		
		for (int i = 0; i < faceMills.size(); i++) { // Seleciona todas as
			// face mills
			// candidatas

			faceMill = faceMills.get(i);
			
			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (genClosed
							.getProfundidade() - limite_desbaste_fundo)) {

				faceMillsCandidatas.add(faceMill);
			}
		}

		if (faceMillsCandidatas.size() == 0) {

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Cavidade Perfil Geral \n" +
					"\tNome: " + genClosed.getNome() +"\n" +
//					"\tComprimento: " + genClosed.getComprimento()+" mm"+"\n" +
//					"\tLargura: " + genClosed.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + genClosed.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + genClosed.getRadius()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (L - 2 * limite_desbaste)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (genClosed.getProfundidade() - limite_desbaste_fundo )+" mm"+"\n\n" +
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
			ArrayList<EndMill> endMills, GeneralClosedPocket genClosed) {

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
					&& endMill.getDiametroFerramenta() <= 2 * genClosed
							.getRadius()
					&& endMill.getProfundidadeMaxima() >= genClosed
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
					"\tNome: " + genClosed.getNome() +"\n" +
//					"\tComprimento: " + genClosed.getComprimento()+" mm"+"\n" +
//					"\tLargura: " + genClosed.getLargura()+" mm"+"\n" +
					"\tProfundidade: " + genClosed.getProfundidade()+" mm"+"\n" +
					"\tRaio: " + genClosed.getRadius()+" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + (genClosed.getRadius() * 2)+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (genClosed.getProfundidade())+" mm"+"\n\n" +
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

	/*
	public static double[][][] getMalha(GeneralClosedPocket genClosed, int numeroDePontosDaMalha){
		double malha[][][] = new double [numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2];
		double largura, comprimento;
		double xMenor=1000,xMaior=0,yMenor=1000,yMaior=0;
		ArrayList<Point2D> vertexPoint;
		
		vertexPoint = genClosed.getPoints();
		for(int i=0;i<vertexPoint.size();i++){
			if(xMenor>vertexPoint.get(i).getX())
				xMenor=vertexPoint.get(i).getX();
			if(xMaior<vertexPoint.get(i).getX())
				xMaior=vertexPoint.get(i).getX();
			if(yMenor>vertexPoint.get(i).getY())
				yMenor=vertexPoint.get(i).getY();
			if(yMaior<vertexPoint.get(i).getY())
				yMaior=vertexPoint.get(i).getY();			
		}
		largura = yMaior-yMenor;
		comprimento = xMaior-xMenor;
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = xMenor+comprimento*(i+1)/numeroDePontosDaMalha;//x
				malha[i][k][1] = yMenor+largura*(k+1)/numeroDePontosDaMalha;//y
			}
		}
		
		return malha;
	}
	
	private	static ArrayList<Shape> createBosses(int numeroDeCortes, double[][][] malha, GeneralPath general, double allowanceSide, double z,
			double raio, ArrayList<Point2D> vertex, double variacao, double raioFerramenta,
			double ae, double maiorMenorDistancia, int numeroDePontosDaMalha, GeneralClosedPocket genClosed)
			{

		double raioTmp, distancia, diferenca, diametro;
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, genClosed, numeroDePontosDaMalha);
		ArrayList<Double> menorDistancia = getMenorDistancia(malha, general, allowanceSide, z, raio, vertex, numeroDePontosDaMalha, genClosed);
		ArrayList<Shape> bossArray = new ArrayList<Shape>();

		diametro = 2*raioFerramenta;

		diferenca = 1 + (2*maiorMenorDistancia-diametro)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);

		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}		

		for(int i=0;i<numeroDeCortes;i++){
			raioTmp = raioFerramenta + i*ae/2;
			diferenca = raioTmp - maiorMenorDistancia;

			for(int k=0;k<pontosPossiveis.size();k++){

				distancia = menorDistancia.get(k);

				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
						}
					}
				}
			}
		}

		return bossArray;
			}
	
	public static ArrayList<Point3d> getPontosPossiveis(double z,GeneralClosedPocket genClosed, int numeroDePontosDaMalha){
	
		GeneralPath general = new GeneralPath();
		ArrayList<Point2D> vertex = genClosed.getPoints();
		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), genClosed.getRadius());
		
		general.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		for(int r=1;r<vertex.size();r++){
			general.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		general.closePath();
		
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
		double[][][] malha = getMalha(genClosed, numeroDePontosDaMalha);
		int b=0;
		ArrayList<Shape> bossArray = getBossArray(z, genClosed); 
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					if(bossArray.size()!=0){
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
					else{
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));						
					}
				}
			}
		}
		
		return pontosPossiveis;
	}
	
	private static ArrayList<Double> getMenorDistancia(double[][][] malha, GeneralPath general, double allowanceSide, double z
			, double raio, ArrayList<Point2D> vertex, int numeroDePontosDaMalha, GeneralClosedPocket genClosed) 
			{
		double distanciaTmp;
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, genClosed, numeroDePontosDaMalha);
		ArrayList<Point3d> pontosPeriferia = getPontosPeriferia(z, genClosed);
		ArrayList<Double> menorDistancia = new ArrayList<Double>();


		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			menorDistancia.add(distanciaTmp);
		}

		return menorDistancia;
			}
	
	private	ArrayList<Point3d> getPontos(int numeroDeCortes, double[][][] malha, GeneralPath general, double allowanceSide, double z,
			double raio, ArrayList<Point2D> vertex, double variacao, double raioFerramenta,
			double ae, double maiorMenorDistancia, int numeroDePontosDaMalha, GeneralClosedPocket genClosed)
			{
		double raioTmp, distancia, diferenca, diametro;
		ArrayList<Point3d> pontos = null;
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, genClosed, numeroDePontosDaMalha);
		ArrayList<Double> menorDistancia = getMenorDistancia(malha, general, allowanceSide, z, raio, vertex, numeroDePontosDaMalha, genClosed);

		diametro = 2*raioFerramenta;

		diferenca = 1 + (2*maiorMenorDistancia-diametro)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);

		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}		

		for(int i=0;i<numeroDeCortes;i++){
			raioTmp = raioFerramenta + i*ae/2;
			diferenca = raioTmp - maiorMenorDistancia;

			for(int k=0;k<pontosPossiveis.size();k++){

				distancia = menorDistancia.get(k);

				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						pontos.add(pontosPossiveis.get(k));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						pontos.add(pontosPossiveis.get(k));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							pontos.add(pontosPossiveis.get(k));
						}
					}
				}
			}
		}

		return pontos;
			}	
	
	public static ArrayList<Point2d> getCoordenadas(double z, GeneralClosedPocket genClosed, int numeroDePontosDaMalha){
		

		ArrayList<Point2d> coordenadas = new ArrayList<Point2d>();
		GeneralPath general = new GeneralPath();
		ArrayList<Point2D> vertex = genClosed.getPoints();
		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), genClosed.getRadius());
		general.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		for(int r=0;r<vertex.size();r++){
			general.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		general.closePath();
		
		double[][][] malha = getMalha(genClosed, numeroDePontosDaMalha);
		int b=0;
		ArrayList<Shape> bossArray = getBossArray(z, genClosed); 
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						coordenadas.add(new Point2d(i,k));
//						System.out.println(k);
					}
					b=0;
				}
			}
		}
		
		return coordenadas;
	}

	public static ArrayList<Shape> getBossArray(double z, GeneralClosedPocket genClosed){

		ArrayList<Boss> itsBoss;
		itsBoss = genClosed.getItsBoss();
		double raioAtual;
		ArrayList<Shape> bossArray;
		
		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		Boss bossTmp;
		
		
		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX(), bossTmp.getPosicaoY(), boss.getL1(), boss.getL2(), boss.getRadius()*2, boss.getRadius()*2));
			}
			else if(itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertex = boss.getVertexPoints();
				vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), genClosed.getRadius());
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
	*/
	
	private static ArrayList<Point3d> getPontosPeriferiaGeneral(ArrayList<Point2D> vertex, double z, double raio){

		GeneralPath path = new GeneralPath();
		path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		ArrayList<Shape> bossArray = new ArrayList<Shape>();
		ArrayList<Point3d> pontosPeriferia = new ArrayList<Point3d>();

		for(int r=0;r<vertex.size();r++){
			path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		path.closePath();
		bossArray.add(path);
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
		return pontosPeriferia;
	}
	
	/*
	public static ArrayList<Point3d> getPontosPeriferia(double z,GeneralClosedPocket genClosed){
		
		ArrayList<Point3d> pontosPeriferia;
		ArrayList<Boss> itsBoss;
		itsBoss = genClosed.getItsBoss();
		double raioAtual;
		Point2D borda[];
		
		pontosPeriferia = new ArrayList<Point3d>();
		Boss bossTmp;
		for(int i=0;i<itsBoss.size();i++){
			bossTmp=itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
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
				ArrayList<Point2D> vertexx = boss.getVertexPoints();
				vertexx = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertexx), boss.getRadius());					
				for(int q=0;q<getPontosPeriferiaGeneral(vertexx, z, boss.getRadius()).size();q++){
					pontosPeriferia.add(getPontosPeriferiaGeneral(vertexx, z, boss.getRadius()).get(q));
				}
			}
		}

		ArrayList<Point2D> vertex = genClosed.getPoints();
		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex), genClosed.getRadius());
		for(int i=0;i<getPontosPeriferiaGeneral(vertex, z, genClosed.getRadius()).size();i++){
			pontosPeriferia.add(getPontosPeriferiaGeneral(vertex, z, genClosed.getRadius()).get(i));
		}
		
		return pontosPeriferia;
	}
	
	
	
	public static double[][] getMalhaMenoresDistancias(double z, GeneralClosedPocket genClosed, int numeroDePontosDaMalha){
		
		ArrayList<Point3d> pontosPeriferia = getPontosPeriferia(z, genClosed);
		double malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha][numeroDePontosDaMalha];
		ArrayList<Point3d> pontosPossiveis = getPontosPossiveis(z, genClosed, numeroDePontosDaMalha);
		ArrayList<Point2d> coordenadas = getCoordenadas(z, genClosed, numeroDePontosDaMalha);
		double distanciaTmp;

//		for(int i=0;i<pontosPossiveis.size();i++){
//			distanciaTmp=100;
//			for(int k=0;k<pontosPeriferia.size();k++){
//				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
//					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
//					System.out.println("DistanciaTmp : " + distanciaTmp);
//				}
//			}
//			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
//		}
//		
		System.out.println("Pontos Possiveis : " + pontosPossiveis.size());
		
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
//				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					if(pontosPeriferia.get(k).distance(pontosPossiveis.get(i))<distanciaTmp){
//					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
					distanciaTmp=pontosPeriferia.get(k).distance(pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			//				System.out.println(menorDistancia.get(i));
		}
		
		
		return malhaMenoresDistancias;
	}
	*/
	public static double getMenorDiametro(int numeroDePontosDaMalha, double[][] malhaMenoresDistancias){
		double menorDiametro, raioMenor=10000;
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
		System.out.println("MENOR DIAMETRO:   "+menorDiametro);
		return menorDiametro;
	}
	
	public static double getMaiorDiametro(int numeroDePontosDaMalha, double[][] malhaMenoresDistancias){
		int contador,numeroDeDiametrosAdicionados=0;
		double raioMedia, maiorDiametro=0;

		raioMedia=0;
		numeroDeDiametrosAdicionados=0;
		
		
		for(int i=1;i<malhaMenoresDistancias.length-1;i++){
			for(int k=1;k<malhaMenoresDistancias.length-1;k++){
				contador = 0;
//				System.out.println("Malha Menores Distancias : "+ malhaMenoresDistancias[i][k]);
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

		System.out.println("MAIOR DIAMETRO:     "+maiorDiametro);
		return maiorDiametro;
	}
	
	private ArrayList<Shape> getBossArray(double allowanceSide, double z)
	{
		ArrayList<Shape> bossArray = new ArrayList<Shape>();
		Boss bossTmp;
		double raioAtual;
		
		for(int i=0;i<this.itsBoss.size();i++){
			bossTmp=this.itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2))+2*allowanceSide;
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX()+allowanceSide, bossTmp.getPosicaoY()+allowanceSide, boss.getL1()-2*allowanceSide, boss.getL2()-2*allowanceSide, boss.getRadius()*2, boss.getRadius()*2));
			}
			else if(this.itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertexx = boss.getVertexPoints();
				vertexx = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertexx), boss.getRadius());		
				GeneralPath path = new GeneralPath();
				path.moveTo(vertexx.get(0).getX(), vertexx.get(0).getY());
				for(int r=0;r<vertexx.size();r++){
					path.lineTo(vertexx.get(r).getX(), vertexx.get(r).getY());
				}
				path.closePath();
				bossArray.add(path);
			}
		}
		
		return bossArray;
	}
	
	public ArrayList<Point3d> getPontosPeriferia(double allowanceSide, double z, double raio, ArrayList<Point2D> vertex)
	{
		ArrayList<Point3d> pontosPeriferia = new ArrayList<Point3d>();
		ArrayList<Point3d> periferia = new ArrayList<Point3d>();
		Point2D borda[];
		Boss bossTmp;
		double raioAtual;
		
		//		listaElipses = new ArrayList<Ellipse2D>();	
		for(int i=0;i<this.itsBoss.size();i++){
			bossTmp=this.itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2))+2*allowanceSide;
				borda = Cavidade.determinarPontosEmCircunferencia(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2*raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX()+allowanceSide,boss.getPosicaoY()+allowanceSide,z), boss.getL1()-2*allowanceSide, boss.getL2()-2*allowanceSide, boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(this.itsBoss.get(i).getClass()==GeneralProfileBoss.class){
				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertexx = boss.getVertexPoints();
				vertexx = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertexx), boss.getRadius());		
				periferia = getPontosPeriferiaGeneral(vertexx, z, boss.getRadius());
				for(int q=0;q<periferia.size();q++){
					pontosPeriferia.add(periferia.get(q));
				}
			}
		}
		
		periferia = getPontosPeriferiaGeneral(vertex, z, raio);
		for(int i=0;i<periferia.size();i++){
			pontosPeriferia.add(periferia.get(i));
		}

		return pontosPeriferia;
	}

	public ArrayList<Point3d> getPontosPossiveis(double[][][] malha, GeneralPath general, double z, ArrayList<Shape> bossArray)
	{
		int b=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS N�O USINADOS
		ArrayList<Point3d> pontosPossiveis = new ArrayList<Point3d>();
				
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
					}
					b=0;
				}
			}
		}
		
		return pontosPossiveis;
	}
	
	public ArrayList<Point2d> getCoordenadas(double[][][] malha, GeneralPath general, ArrayList<Shape> bossArray)
	{
		int b=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS N�O USINADOS
		ArrayList<Point2d> coordenadas = new ArrayList<Point2d>();
		
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						coordenadas.add(new Point2d(i,k));
					}
					b=0;
				}
			}
		}
		
		return coordenadas;
	}
	
	private ArrayList<Double> getMenorDistancia(ArrayList<Point3d> pontosPossiveis, ArrayList<Point3d> pontosPeriferia) 
	{
		double distanciaTmp;
		ArrayList<Double> menorDistancia = new ArrayList<Double>();
		
		
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			menorDistancia.add(distanciaTmp);
		}

		return menorDistancia;
	}
	
	private double[][] getMalhaMenoresDistancias(int numeroDePontosDaMalha, ArrayList<Point3d> pontosPossiveis , ArrayList<Point3d> pontosPeriferia, ArrayList<Point2d> coordenadas) 
	{
		double distanciaTmp;
		double malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1];

		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
		}
		
		return malhaMenoresDistancias;
	}
	
	private	ArrayList<ArrayList<Point3d>> getPontos(double variacao, double raioFerramenta,
										 double ae, double maiorMenorDistancia, ArrayList<Point3d> pontosPossiveis , ArrayList<Double> menorDistancia)
	{
		double raioTmp, distancia, diferenca, diametro;
		ArrayList<ArrayList<Point3d>> pontos;
		ArrayList<Point3d> pontos2;
		int numeroDeCortes;
	
		diametro = 2*raioFerramenta;
		
		diferenca = 1 + (2*maiorMenorDistancia-diametro)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);
		
		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}		
		
		pontos = new ArrayList<ArrayList<Point3d>>();
		
		for(int i=0;i<numeroDeCortes;i++){
			pontos2 = new ArrayList<Point3d>();
			raioTmp = raioFerramenta + i*ae/2;
			diferenca = raioTmp - maiorMenorDistancia;
			
			for(int k=0;k<pontosPossiveis.size();k++){

				distancia = menorDistancia.get(k);
				
				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						pontos2.add(pontosPossiveis.get(k));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						pontos2.add(pontosPossiveis.get(k));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							pontos2.add(pontosPossiveis.get(k));
						}
					}
				}
			}
			pontos.add(pontos2);
		}
		
		return pontos;
	}
	
	private	ArrayList<Shape> createBosses(double variacao, double raioFerramenta, double ae, double maiorMenorDistancia, 
										  ArrayList<Point3d> pontosPossiveis, ArrayList<Double> menorDistancia)
	{
		double raioTmp, distancia, diferenca, diametro;
		ArrayList<Shape> bossArray = new ArrayList<Shape>();
		int numeroDeCortes;
		
		diametro = 2*raioFerramenta;
		
		diferenca = 1 + (2*maiorMenorDistancia-diametro)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);
		
		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}		
		
		for(int i=0;i<numeroDeCortes;i++){
			raioTmp = raioFerramenta + i*ae/2;
			diferenca = raioTmp - maiorMenorDistancia;

			for(int k=0;k<pontosPossiveis.size();k++){

				distancia = menorDistancia.get(k);

				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioFerramenta, pontosPossiveis.get(k).getY()-raioFerramenta, diametro, diametro));
						}
					}
				}
			}
		}

		return bossArray;
	}

	
	
}
