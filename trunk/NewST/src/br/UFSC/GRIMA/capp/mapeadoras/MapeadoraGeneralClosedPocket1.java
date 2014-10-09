package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.MachinningStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author feitosa
 *
 */
public class MapeadoraGeneralClosedPocket1 
{
	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private GeneralClosedPocket genClosed;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private ArrayList<Boss> itsBoss;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraGeneralClosedPocket1(Projeto projeto, Face face, GeneralClosedPocket genClosed) 
	{

		this.projeto = projeto;
		this.faceTmp = face;
		this.genClosed = genClosed;
		this.bloco = projeto.getBloco();
		this.itsBoss = genClosed.getItsBoss();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		System.out.println("Tamanho: "+faceMills.size());
		
		this.mapearGeneralClosedPocket();

	}
	public MapeadoraGeneralClosedPocket1(GeneralClosedPocket genClosed) 
	{
		this.genClosed = genClosed;
		this.itsBoss = genClosed.getItsBoss();
		
//		this.mapearGeneralClosedPocket();

	}
	/**
	 * Realiza o metodo da minima distancia entre pontos dentro da cavidade e os elementos da borda da cavidade, e retorna o a maior distancia encontrada
	 * @param genClosed
	 * @return
	 */
	public double getMaiorMenorDistancia(GeneralClosedPocket genClosed)
	{
		boolean thereIsBoss = false;
		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
		ArrayList<ArrayList<Point2D>> matrix = new ArrayList<ArrayList<Point2D>>();
		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
		double minimumMaxDistance=0;
		
		//Verifica se ha protuberanica
		if(itsBoss != null)
		{
			if(itsBoss.size() != 0)
			{
				thereIsBoss = true;
			}
		}
		
		//Posicao da forma
		Point2D minorPointX = genClosed.getPoints().get(0); //Menor X
		Point2D maxPointX = genClosed.getPoints().get(0);   //Maior Y
		Point2D minorPointY = genClosed.getPoints().get(0); //Menor X
		Point2D maxPointY = genClosed.getPoints().get(0);   //Maior Y
		for(Point2D pointTmp : genClosed.getPoints())
		{
			if(pointTmp.getX() < minorPointX.getX())
			{
				minorPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getX() > maxPointX.getX())
			{
				maxPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getY() < minorPointY.getY())
			{
				minorPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getY() > maxPointY.getY())
			{
				maxPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
		}
//		System.out.println("Xminor: " + minorPointX);
//		System.out.println("Xmax: " + maxPointX);
//		System.out.println("Yminor: " + minorPointY);
//		System.out.println("Ymax: " + maxPointY);

		int numeroDePontos = 100;
		double deltaX = minorPointX.distance(maxPointX)/numeroDePontos;
		double deltaY = minorPointY.distance(maxPointY)/numeroDePontos;
		
		//CRIA O GENERAL PATH DO FORMATO
//		GeneralPath gp = new GeneralPath();
//		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(genClosed.getVertexPoints()), genClosed.getRadius());
//		gp.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
			
		//CRIA UM Shape2D DA PROTUBERANCIA
		Shape boss = null;
		for(Boss bossTmp:itsBoss)
		{
			if(bossTmp.getClass() == CircularBoss.class)
			{
//				Ellipse2D circularBossShape = (Ellipse2D)Face.getShape(bossTmp);
				boss = (Ellipse2D)Face.getShape(bossTmp);
//				System.out.println("CircularBoss: " + boss);
			}
			else if(bossTmp.getClass() == RectangularBoss.class)
			{
//				RoundRectangle2D rectangularBossShape = (RoundRectangle2D)Face.getShape(bossTmp);
				boss = (RoundRectangle2D)Face.getShape(bossTmp);
			}
			else if(bossTmp.getClass() == GeneralProfileBoss.class)
			{
//				GeneralPath gpBoss = (GeneralPath)Face.getShape(genClosed);
				boss = (GeneralPath)Face.getShape(genClosed);
			}
		}
		//Array de LimitedElement da forma da cavidade
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
		for(int r=0;r<vertex.size();r++)
		{
			gp.lineTo(vertex.get(r).getX(), vertex.get(r).getY());				
		}
		gp.closePath();
		
		//Percorre uma matriz de pontos dentro da forma da cavidade, verificando qual e a maior distancia 
		//entre as menores distancias entre os pontos e os elementos
		for(int i = 0; i < numeroDePontos; i++)
		{
			ArrayList<Point2D> arrayTmp = new ArrayList<Point2D>();
			for(int j = 0; j < numeroDePontos; j++)
			{
				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointX.getY() + deltaY*j);
				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
				{
					if(thereIsBoss)      //Se possui Protuberancia
					{
						if(boss.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
						{
//							System.out.println(pointTmp);
							break;
						}
						else //Se o ponto esta fora da protuberancia
						{
							ArrayList<LimitedElement> elementsPocketAndBoss = new ArrayList<LimitedElement>();
							for(LimitedElement tmp:addPocket.getElements())
							{
								elementsPocketAndBoss.add(tmp);
							}
							for(LimitedElement tmp:GeometricOperations.tranformeBossToLimitedElement(itsBoss, genClosed.Z))
							{
								elementsPocketAndBoss.add(tmp);
							}
							double minimumMaxDistancePointToPathTmp = GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
							
							if(minimumMaxDistancePointToPathTmp > minimumMaxDistance)
							{
//								System.out.println("Ponto: "+ pointTmp);
								minimumMaxDistance = minimumMaxDistancePointToPathTmp;
							}
						}
					}
					else //Se nao possui protuberancia
					{
						//Calcula a menor distancia entre o ponto atual e o array da forma da cavidade
						double menorDistanciaTmp = GeometricOperations.minimumDistance(addPocket.getElements(), new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
						if(menorDistanciaTmp > minimumMaxDistance)
						{
							minimumMaxDistance = menorDistanciaTmp;
						}
					}
				}
//					arrayTmp.add(pointTmp);
			}
//				matrix.add(arrayTmp);
		}
		
		System.out.println("Maior Menor Distancia: " + minimumMaxDistance);
		return minimumMaxDistance;
	}
	
//	public double getMaiorMenorDistancia(GeneralClosedPocket genClosed)
//	{
//		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
//		ArrayList<ArrayList<Point2D>> matrix = new ArrayList<ArrayList<Point2D>>();
//		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
//		//Forma da Cavidade
//		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
//		double maiorMenorDistancia=0;
//		
//		//Verifica se ha protuberanica
//		if(itsBoss != null)
//		{
//			maiorMenorDistancia = GeometricOperations.maxDistance(addPocket.getElements(), GeometricOperations.tranformeBossToLimitedElement(itsBoss, genClosed.Z));
//		}
//		else
//		{
//			//Posicao da forma
//			Point2D minorPointX = genClosed.getPoints().get(0); //Menor X
//			Point2D maxPointX = genClosed.getPoints().get(0);   //Maior Y
//			Point2D minorPointY = genClosed.getPoints().get(0); //Menor X
//			Point2D maxPointY = genClosed.getPoints().get(0);   //Maior Y
//			for(Point2D pointTmp : genClosed.getPoints())
//			{
//				if(pointTmp.getX() < minorPointX.getX())
//				{
//					minorPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//				}
//				if(pointTmp.getX() > maxPointX.getX())
//				{
//					maxPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//				}
//				if(pointTmp.getY() < minorPointY.getY())
//				{
//					minorPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//				}
//				if(pointTmp.getY() > maxPointY.getY())
//				{
//					maxPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//				}
//			}
//			System.out.println("Xminor: " + minorPointX);
//			System.out.println("Xmax: " + maxPointX);
//			System.out.println("Yminor: " + minorPointY);
//			System.out.println("Ymax: " + maxPointY);
//			
//			int numeroDePontos = 100;
//			double deltaX = minorPointX.distance(maxPointX)/numeroDePontos;
//			double deltaY = minorPointY.distance(maxPointY)/numeroDePontos;
//			
//			//CRIA O GENERAL PATH DO FORMATO
//			GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
//			
//			
//			for(int r=0;r<vertex.size();r++)
//			{
//				gp.lineTo(vertex.get(r).getX(), vertex.get(r).getY());				
//			}
//			gp.closePath();
//			
//			//Percorre uma matriz de pontos dentro da forma da cavidade, verificando qual e a maior distancia 
//			//entre as menores distancias entre os pontos e os elementos
//			for(int i = 0; i < numeroDePontos; i++)
//			{
//				for(int j = 0; j < numeroDePontos; j++)
//				{
//					Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointX.getY() + deltaY*j);
//					if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
//					{
//						//Calcula a menor distancia entre o ponto atual e o array da forma da cavidade
//						double menorDistanciaTmp = GeometricOperations.minimumDistance(addPocket.getElements(), new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
//						if(menorDistanciaTmp > maiorMenorDistancia)
//						{
//							maiorMenorDistancia = menorDistanciaTmp;
//						}
//					}
//				}
//			}
//			
//		}
//		return maiorMenorDistancia;
//	}
	
	public double getMenorMenorDistance(GeneralClosedPocket genClosed)
	{
		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());

		//minima distancia entre o array de elementos da forma e o array de elementos da protuberancia(se houver)
		double menorDistancia = 0;
		if(itsBoss.size() != 0) //PADRONIZAR SE A COMPARACAO COM O ITSBOSS SERA COM SIZE == 0, OU COM ITSBOSS == NULL
		{
			//CUIDADO COM O Z!!
			menorDistancia = GeometricOperations.minimumDistance(addPocket.getElements(), GeometricOperations.tranformeBossToLimitedElement(itsBoss, genClosed.Z));
		}
		else
		{
			menorDistancia = genClosed.getRadius();
		}
		System.out.println("Menor Menor Distancia: " + menorDistancia);
		return menorDistancia;
	}
	
	private FaceMill chooseFaceMill(Material material,
			ArrayList<FaceMill> faceMills, GeneralClosedPocket genClosed,
			double limite_desbaste, double L) 
	{
		System.out.println("L: " + L);
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
	
	private void mapearGeneralClosedPocket() 
	{
		Workingstep wsTmp;
		Workingstep wsPrecedenteTmp;
		wssFeature = new Vector<Workingstep>();
		double retractPlane = 5;
		if(genClosed.getFeaturePrecedente()!= null)
		{
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
		}
		else
		{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		if (!genClosed.isAcabamento()) 
		{
			// WORKINGSTEPS DE DESBASTE

			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation1.setAllowanceSide(Feature.LIMITE_DESBASTE);

			if (!genClosed.isPassante())
				operation1.setAllowanceBottom(Feature.LIMITE_DESBASTE);
			
			
			// FERRAMENTA
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
					genClosed, 0, getMaiorMenorDistancia(genClosed));
			//Estrategia
			TrochoidalAndContourParallelStrategy machiningStrategy = new TrochoidalAndContourParallelStrategy();
			operation1.setMachiningStrategy(machiningStrategy);
			machiningStrategy.setAllowMultiplePasses(true);
			machiningStrategy.setOverLap(0.25*faceMill.getDiametroFerramenta()); //Overlap
			machiningStrategy.setTrochoidalRadius(faceMill.getDiametroFerramenta()/2); //REVER MAIS TARDE
			machiningStrategy.setTrochoidalFeedRate(0.75*faceMill.getDiametroFerramenta()/2);
			machiningStrategy.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
			machiningStrategy.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
			
			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill,
							bloco.getMaterial());
			// WORKINGSTEP 1
			wsTmp = new Workingstep(genClosed, faceTmp, faceMill,
					condicoesDeUsinagem, operation1);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId(this.genClosed.getNome() + "_RGH");
			
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;
			
			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
					"Bottom And Side Rough Milling", retractPlane);
			operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);

			if (!genClosed.isPassante())
				operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);

			// FERRAMENTA
			FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
					genClosed, 0,
					getMenorMenorDistance(genClosed));
			// Estrategia
			ContourParallel machiningStrategy2 = new ContourParallel();
			operation2.setMachiningStrategy(machiningStrategy2);
			machiningStrategy2.setAllowMultiplePasses(false);
			machiningStrategy2.setCutmodeType(ContourParallel.conventional);

			// CONDIÇÕES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps
					.getCondicoesDeUsinagem(this.projeto, faceMill2,
							bloco.getMaterial());
			// WORKINGSTEP 2
			wsTmp = new Workingstep(genClosed, faceTmp, faceMill2,
					condicoesDeUsinagem, operation2);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId(this.genClosed.getNome() + "_RGH");

			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;

			wssFeature.add(wsTmp);
		}
		
	}
}
