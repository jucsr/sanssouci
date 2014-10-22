package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
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
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
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
	private GeneralClosedPocketVertexAdd addPocket;
//	private GenerateContournParallel contourn;
	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraGeneralClosedPocket1(Projeto projeto, Face face, GeneralClosedPocket genClosed) 
	{
		this.projeto = projeto;
		this.faceTmp = face;
		this.genClosed = genClosed;
		this.bloco = projeto.getBloco();
		this.itsBoss = genClosed.getItsBoss();
		this.addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		System.out.println("Tamanho: "+faceMills.size());
		
		this.mapearGeneralClosedPocket();

	}
	public MapeadoraGeneralClosedPocket1(GeneralClosedPocket genClosed) 
	{
		this.genClosed = genClosed;
		this.itsBoss = genClosed.getItsBoss();
		this.addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());

		
//		this.mapearGeneralClosedPocket();

	}
	//Array de LimitedElement da forma da cavidade
//	GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
//	GenerateContournParallel contourn = new GenerateContournParallel(genClosed, genClosed.Z, distance, overLap)
	/**
	 * Realiza o metodo da minima distancia entre pontos dentro da cavidade e os elementos da borda da cavidade, e retorna o a maior distancia encontrada
	 * @param genClosed
	 * @return
	 */
	public double getMaiorMenorDistancia(/*GeneralClosedPocket genClosed*/ArrayList<ArrayList<LimitedElement>> bossElements)
	{
//		boolean thereIsBoss = false;
//		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
//		final ArrayList<ArrayList<Point2D>> matrix = new ArrayList<ArrayList<Point2D>>();
//		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
//		System.out.println("LOLOL: " + itsBoss.get(0).X + ", " + itsBoss.get(0).Y + ", " + itsBoss.get(0).Z);
//
//		double minimumMaxDistance=0;
//		
//		//Verifica se ha protuberanica
//		if(itsBoss != null)
//		{
//			if(itsBoss.size() != 0)
//			{
//				thereIsBoss = true;
//			}
//		}
//		
//		//Posicao da forma
//		Point2D minorPointX = genClosed.getPoints().get(0); //Menor X
//		Point2D maxPointX = genClosed.getPoints().get(0);   //Maior Y
//		Point2D minorPointY = genClosed.getPoints().get(0); //Menor X
//		Point2D maxPointY = genClosed.getPoints().get(0);   //Maior Y
//		for(Point2D pointTmp : genClosed.getPoints())
//		{
//			if(pointTmp.getX() < minorPointX.getX())
//			{
//				minorPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//			}
//			if(pointTmp.getX() > maxPointX.getX())
//			{
//				maxPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//			}
//			if(pointTmp.getY() < minorPointY.getY())
//			{
//				minorPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//			}
//			if(pointTmp.getY() > maxPointY.getY())
//			{
//				maxPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
//			}
//		}
//		System.out.println("Xminor: " + minorPointX);
//		System.out.println("Xmax: " + maxPointX);
//		System.out.println("Yminor: " + minorPointY);
//		System.out.println("Ymax: " + maxPointY);
//
//		int numeroDePontos = 100;
//		double deltaX = minorPointX.distance(maxPointX)/numeroDePontos;
//		double deltaY = minorPointY.distance(maxPointY)/numeroDePontos;
//		
//		//CRIA O GENERAL PATH DO FORMATO
////		GeneralPath gp = new GeneralPath();
////		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(genClosed.getVertexPoints()), genClosed.getRadius());
////		gp.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
//		final GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
//			
//		//CRIA UM Shape2D DA PROTUBERANCIA
//		Shape boss = null;
//		for(Boss bossTmp:itsBoss)
//		{
//			if(bossTmp.getClass() == CircularBoss.class)
//			{
////				Ellipse2D circularBossShape = (Ellipse2D)Face.getShape(bossTmp);
//				boss = (Ellipse2D)Face.getShape(bossTmp);
////				System.out.println("CircularBoss: " + boss);
//			}
//			else if(bossTmp.getClass() == RectangularBoss.class)
//			{
////				RoundRectangle2D rectangularBossShape = (RoundRectangle2D)Face.getShape(bossTmp);
//				boss = (RoundRectangle2D)Face.getShape(bossTmp);
//			}
//			else if(bossTmp.getClass() == GeneralProfileBoss.class)
//			{
////				GeneralPath gpBoss = (GeneralPath)Face.getShape(genClosed);
//				boss = (GeneralPath)Face.getShape(genClosed);
//			}
//		}
//		//Array de LimitedElement da forma da cavidade
//		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
//	
//		
////		for(int r=0;r<vertex.size();r++)
////		{
////			gp.lineTo(vertex.get(r).getX(), vertex.get(r).getY());				
////		}
////		gp.closePath();
//		//Percorre uma matriz de pontos dentro da forma da cavidade, verificando qual e a maior distancia 
//		//entre as menores distancias entre os pontos e os elementos
//		for(int i = 0; i < numeroDePontos; i++)
//		{
//			ArrayList<Point2D> arrayTmp = new ArrayList<Point2D>();
//			for(int j = 0; j < numeroDePontos; j++)
//			{
////				System.out.println("lololo");
//				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointY.getY() + deltaY*j);
////				if(i<50)
////				System.err.println(pointTmp);
//				
//				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
//				{
//					if(thereIsBoss)      //Se possui Protuberancia
//					{
//						if(!boss.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
//						{
//							ArrayList<LimitedElement> elementsPocketAndBoss = new ArrayList<LimitedElement>();
//							for(LimitedElement tmp:addPocket.getElements())
//							{
//								elementsPocketAndBoss.add(tmp);
//							}
//							for(LimitedElement tmp:GeometricOperations.tranformeBossToLimitedElement(itsBoss, genClosed.Z))
//							{
//								elementsPocketAndBoss.add(tmp);
//							}
//							double minimumMaxDistancePointToPathTmp = GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
//							
//							if(minimumMaxDistancePointToPathTmp > minimumMaxDistance)
//							{
////								System.out.println("Ponto: "+ pointTmp);
//								minimumMaxDistance = minimumMaxDistancePointToPathTmp;
//							}
////							System.out.println("Maior Menor Distancia c/protuberancia: " + minimumMaxDistance);
//						}
//					}
//					else //Se nao possui protuberancia
//					{
//						//Calcula a menor distancia entre o ponto atual e o array da forma da cavidade
//						double menorDistanciaTmp = GeometricOperations.minimumDistance(addPocket.getElements(), new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
//						if(menorDistanciaTmp > minimumMaxDistance)
//						{
//							minimumMaxDistance = menorDistanciaTmp;
//						}
////						System.out.println("Maior Menor Distancia s/protuberancia: " + minimumMaxDistance);
//					}
//				}
//				arrayTmp.add(pointTmp);
//			}
//			matrix.add(arrayTmp);
//		}
//		
//		//Desenhador
//		JFrame frame = new JFrame();
//		frame.setSize(new Dimension(300, 300));
//		class Panel extends JPanel
//		{
//			protected void paintComponent(Graphics g)
//			{
//				Graphics2D g2d = (Graphics2D)g;
////				g2d.translate(0, 300);
////				g2d.scale(1, -1);
//				g2d.draw(gp);
//				for(ArrayList<Point2D> arrayTmp:matrix)
//				{
//					for(Point2D pointTmp:arrayTmp)
//					{
////						g2d.drawOval((int)pointTmp.getX(), (int)pointTmp.getY(), 1, 1);
//						g2d.draw(new Ellipse2D.Double(pointTmp.getX(), pointTmp.getY(), 1, 1));
//					}
//				}
//			}
//		}
//		frame.getContentPane().add(new Panel());
//		frame.setVisible(true);
//		
//		return minimumMaxDistance;
		
		//-----------------------------------------------------------------------------------------------------
		//Teste
		//-----------------------------------------------------------------------------------------------------
		boolean thereIsBoss = false;
//		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
//		final ArrayList<ArrayList<Point2D>> matrix = new ArrayList<ArrayList<Point2D>>();

		double minimumMaxDistance=0;
		ArrayList<LimitedElement> elementsPocketAndBoss = new ArrayList<LimitedElement>();
		for(LimitedElement tmp:addPocket.getElements())
		{
			elementsPocketAndBoss.add(tmp);
		}
		//Verifica se ha protuberanica
		if(bossElements != null)
		{
			if(bossElements.size() != 0)
			{
				thereIsBoss = true;
				for(ArrayList<LimitedElement> arrayTmp:bossElements)
				{
					for(LimitedElement elementTmp:arrayTmp)
					{	
						elementsPocketAndBoss.add(elementTmp);
					}
				}
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
		final GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
			
		//CRIA UM Shape2D DA PROTUBERANCIA
		final ArrayList<Shape> bossShape = new ArrayList<Shape>();
		for(ArrayList<LimitedElement> bossTmp:bossElements)
		{
			bossShape.add(Face.getShape(bossTmp));
		}
		//Desenhador
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(300, 300));
		class Panel extends JPanel
		{
			protected void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
				
//				g2d.translate(0, 300);
//				g2d.scale(1, -1);
//				g2d.draw(gp);
				g2d.draw(bossShape.get(0));
//				for(ArrayList<Point2D> arrayTmp:matrix)
//				{
//					for(Point2D pointTmp:arrayTmp)
//					{
////						g2d.drawOval((int)pointTmp.getX(), (int)pointTmp.getY(), 1, 1);
//						g2d.draw(new Ellipse2D.Double(pointTmp.getX(), pointTmp.getY(), 1, 1));
//					}
//				}
			}
		}
		frame.getContentPane().add(new Panel());
		frame.setVisible(true);
		
		//Percorre uma matriz de pontos dentro da forma da cavidade, verificando qual e a maior distancia 
		//entre as menores distancias entre os pontos e os elementos
		ArrayList<Point2D> arrayPointTmp = new ArrayList<Point2D>();    //pontos validos (dentro da cavidade e fora da protuberancia)
		for(int i = 0; i < numeroDePontos; i++)
		{
			for(int j = 0; j < numeroDePontos; j++)
			{
				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointY.getY() + deltaY*j);
				
				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
				{
					if(thereIsBoss)      //Se possui Protuberancia
					{
						for(Shape bossTmp:bossShape)
						{
							if(!bossTmp.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
							{
//								System.out.println("lol");
//								ArrayList<LimitedElement> elementsPocketAndBoss = new ArrayList<LimitedElement>();
//								for(LimitedElement tmp:addPocket.getElements())
//								{
//									elementsPocketAndBoss.add(tmp);
//								}
//								for(ArrayList<LimitedElement> arrayTmp:bossElements)
//								{
//									for(LimitedElement elementTmp:arrayTmp)
//									{	
//										elementsPocketAndBoss.add(elementTmp);
//									}
//								}
//								double minimumMaxDistancePointToPathTmp = GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
//								
//								if(minimumMaxDistancePointToPathTmp > minimumMaxDistance)
//								{
//									minimumMaxDistance = minimumMaxDistancePointToPathTmp;
//								}
								arrayPointTmp.add(pointTmp);
							}
						}
					}
					else //Se nao possui protuberancia
					{
//						//Calcula a menor distancia entre o ponto atual e o array da forma da cavidade
//						double menorDistanciaTmp = GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
//						if(menorDistanciaTmp > minimumMaxDistance)
//						{
//							minimumMaxDistance = menorDistanciaTmp;
//						}
						arrayPointTmp.add(pointTmp);
					}
				}
			}
		}
		for(Point2D matrixPoint:arrayPointTmp)
		{
			double minimumMaxDistancePointToPathTmp = GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(matrixPoint.getX(),matrixPoint.getY(),genClosed.Z));
			if(minimumMaxDistancePointToPathTmp > minimumMaxDistance)
			{
				minimumMaxDistance = minimumMaxDistancePointToPathTmp;
			}
		}
		
		return minimumMaxDistance;
		//-----------------------------------------------------------------------------------------------------
		//END Teste
		//-----------------------------------------------------------------------------------------------------
	}
	
	
	public double getMenorMenorDistance(/*GeneralClosedPocket genClosed*/ArrayList<ArrayList<LimitedElement>> arrayBossElements)
	{
//		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
		ArrayList<LimitedElement> bossElements = new ArrayList<LimitedElement>();
		for(ArrayList<LimitedElement> arrayTmp : arrayBossElements)
		{
			for(LimitedElement elementTmp : arrayTmp)
			{
				bossElements.add(elementTmp);
			}
		}
//		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
		//minima distancia entre o array de elementos da forma e o array de elementos da protuberancia(se houver)
		double menorDistancia = 0;
		if(bossElements.size() != 0) //PADRONIZAR SE A COMPARACAO COM O ITSBOSS SERA COM SIZE == 0, OU COM ITSBOSS == NULL
		{
			//CUIDADO COM O Z!!
			menorDistancia = GeometricOperations.minimumDistance(addPocket.getElements(), bossElements);
//			System.out.println("Menor Menor Distancia c/protuberancia: " + menorDistancia);
		}
		else
		{
			menorDistancia = genClosed.getRadius();
//			System.out.println("Menor Menor Distancia s/protuberancia: " + menorDistancia);
		}
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
//		double fator = 3;
//		Workingstep wsTmp1;
//		Workingstep wsTmp2;
//		Workingstep wsPrecedenteTmp;
//		wssFeature = new Vector<Workingstep>();
//		double retractPlane = 5;
//		if(genClosed.getFeaturePrecedente()!= null)
//		{
//			if(		genClosed.getFeaturePrecedente().getClass().equals(GeneralProfileBoss.class) || 
//					genClosed.getFeaturePrecedente().getClass().equals(RectangularBoss.class) ||
//					genClosed.getFeaturePrecedente().getClass().equals(CircularBoss.class))
//			{
//				wsPrecedenteTmp = genClosed.getFeaturePrecedente().getFeaturePrecedente().getWorkingsteps().lastElement();
//			}
//			else{
//				wsPrecedenteTmp = genClosed.getFeaturePrecedente().getWorkingsteps().lastElement();
//			}			
////			System.out.println("wsp = " + genClosed.getFeaturePrecedente().getWorkingsteps().size());
////			System.out.println("feature Pre = " + genClosed.getFeaturePrecedente());
//		}
//		else
//		{
//			//Nao tem ws precedente
//			wsPrecedenteTmp = null;
//			
//		}
//		if (!genClosed.isAcabamento()) 
//		{
//			// WORKINGSTEPS DE DESBASTE
//
//			// BOTTOM AND SIDE ROUGH MILLING
//			BottomAndSideRoughMilling operation1 = new BottomAndSideRoughMilling(
//					"Bottom And Side Rough Milling", retractPlane);
//			operation1.setAllowanceSide(Feature.LIMITE_DESBASTE);
//
//			if (!genClosed.isPassante())
//				operation1.setAllowanceBottom(Feature.LIMITE_DESBASTE);
//			
//			
//			// FERRAMENTA
//			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills,
//					genClosed, 0, getMaiorMenorDistancia(genClosed)/fator);
//			System.out.println("Ferramenta 1 de Diametro: " + faceMill.getDiametroFerramenta());
//
//			//Estrategia
//			TrochoidalAndContourParallelStrategy machiningStrategy = new TrochoidalAndContourParallelStrategy();
//			operation1.setMachiningStrategy(machiningStrategy);
//			machiningStrategy.setAllowMultiplePasses(true);
//			machiningStrategy.setOverLap(0.25*faceMill.getDiametroFerramenta()); //Overlap
//			machiningStrategy.setTrochoidalRadius(faceMill.getDiametroFerramenta()); //REVER MAIS TARDE
//			machiningStrategy.setTrochoidalFeedRate(0.75*faceMill.getDiametroFerramenta()/2);
//			machiningStrategy.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
//			machiningStrategy.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
//			
//			// CONDIÇÕES DE USINAGEM
//			condicoesDeUsinagem = MapeadoraDeWorkingsteps
//					.getCondicoesDeUsinagem(this.projeto, faceMill,
//							bloco.getMaterial());
//			// WORKINGSTEP 1
//			wsTmp1 = new Workingstep(genClosed, faceTmp, faceMill,
//					condicoesDeUsinagem, operation1);
//			wsTmp1.setTipo(Workingstep.DESBASTE);
//			wsTmp1.setId(this.genClosed.getNome() + "_RGH");
//			
//			wsTmp1.setWorkingstepPrecedente(wsPrecedenteTmp);
//			wsPrecedenteTmp = wsTmp1;
//			
//			wssFeature.add(wsTmp1);
//
//			
//			// BOTTOM AND SIDE ROUGH MILLING
//			BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
//					"Bottom And Side Rough Milling", retractPlane);
//			operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
//
//			if (!genClosed.isPassante())
//				operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);
//
//			// FERRAMENTA
//			FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
//					genClosed, 0,
//					getMenorMenorDistance(genClosed));
//			
//			System.out.println("Ferramenta 2 de Diametro: " + faceMill2.getDiametroFerramenta());
//			// Estrategia
//			ContourParallel machiningStrategy2 = new ContourParallel();
//			operation2.setMachiningStrategy(machiningStrategy2);
//			machiningStrategy2.setAllowMultiplePasses(false);
//			machiningStrategy2.setCutmodeType(ContourParallel.conventional);
//
//			// CONDIÇÕES DE USINAGEM
//			condicoesDeUsinagem = MapeadoraDeWorkingsteps
//					.getCondicoesDeUsinagem(this.projeto, faceMill2,
//							bloco.getMaterial());
//			// WORKINGSTEP 2
//			wsTmp2 = new Workingstep(genClosed, faceTmp, faceMill2,
//					condicoesDeUsinagem, operation2);
//			wsTmp2.setTipo(Workingstep.DESBASTE);
//			wsTmp2.setId(this.genClosed.getNome() + "_RGH");
//
//			wsTmp2.setWorkingstepPrecedente(wsPrecedenteTmp);
//			wsPrecedenteTmp = wsTmp2;
//
//			wssFeature.add(wsTmp2);
//
//			genClosed.setWorkingsteps(wssFeature);
//		}
		
//-----------------------------------------------------------------------------------------------------
//Teste
//-----------------------------------------------------------------------------------------------------
		
		double fator = 3;
		ArrayList<Workingstep> workingSteps = new ArrayList<Workingstep>();
		Workingstep wsPrecedenteTmp;
		wssFeature = new Vector<Workingstep>();
		double retractPlane = 5;
		ArrayList<ArrayList<LimitedElement>> bossElements = GenerateContournParallel.gerarElementosDaProtuberancia(genClosed, genClosed.Z);
		double maiorMenorDistanciaTmp = getMaiorMenorDistancia(bossElements)/fator;
		double menorMenorDistanciaTmp = getMenorMenorDistance(bossElements);
//		double toolDiameterTmp = maiorMenorDistanciaTmp;
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
//			System.out.println("wsp = " + genClosed.getFeaturePrecedente().getWorkingsteps().size());
//			System.out.println("feature Pre = " + genClosed.getFeaturePrecedente());
		}
		else
		{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
			
		}
		// WORKINGSTEPS DE DESBASTE
		if (!genClosed.isAcabamento()) 
		{
//			while(genClosed.getRadius() < maiorMenorDistanciaTmp)
			while(maiorMenorDistanciaTmp > menorMenorDistanciaTmp)
			{
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operationTmp = new BottomAndSideRoughMilling(
						"Bottom And Side Rough Milling", retractPlane);
				operationTmp.setAllowanceSide(Feature.LIMITE_DESBASTE);
	
				if (!genClosed.isPassante())
					operationTmp.setAllowanceBottom(Feature.LIMITE_DESBASTE);
				
				
				// FERRAMENTA
				FaceMill faceMillTmp = chooseFaceMill(bloco.getMaterial(), faceMills,
						genClosed, 0, maiorMenorDistanciaTmp);
//				System.out.println("Ferramenta 1 de Diametro: " + faceMillTmp.getDiametroFerramenta());
	
				//Estrategia
				TrochoidalAndContourParallelStrategy machiningStrategyTmp = new TrochoidalAndContourParallelStrategy();
				operationTmp.setMachiningStrategy(machiningStrategyTmp);
				machiningStrategyTmp.setAllowMultiplePasses(true);
				machiningStrategyTmp.setOverLap(0.25*faceMillTmp.getDiametroFerramenta()); //Overlap
				machiningStrategyTmp.setTrochoidalRadius(faceMillTmp.getDiametroFerramenta()); //REVER MAIS TARDE
				machiningStrategyTmp.setTrochoidalFeedRate(0.75*faceMillTmp.getDiametroFerramenta()/2);
				machiningStrategyTmp.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
				machiningStrategyTmp.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
				
				// CONDIÇÕES DE USINAGEM
				condicoesDeUsinagem = MapeadoraDeWorkingsteps
						.getCondicoesDeUsinagem(this.projeto, faceMillTmp,
								bloco.getMaterial());
				// WORKINGSTEP
				Workingstep wsTmp = new Workingstep(genClosed, faceTmp, faceMillTmp,
						condicoesDeUsinagem, operationTmp);
				wsTmp.setTipo(Workingstep.DESBASTE);
				wsTmp.setId(this.genClosed.getNome() + "_RGH");
				
				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
				wsPrecedenteTmp = wsTmp;
				
				wssFeature.add(wsTmp);
				workingSteps.add(wsTmp);
	
				bossElements = getAreaAlreadyDesbasted(genClosed, genClosed.Z, machiningStrategyTmp.getTrochoidalRadius() + faceMillTmp.getDiametroFerramenta()/2, machiningStrategyTmp.getOverLap());
				maiorMenorDistanciaTmp = getMaiorMenorDistancia(bossElements);
				menorMenorDistanciaTmp = getMenorMenorDistance(bossElements);
				// BOTTOM AND SIDE ROUGH MILLING
//				BottomAndSideRoughMilling operation2 = new BottomAndSideRoughMilling(
//						"Bottom And Side Rough Milling", retractPlane);
//				operation2.setAllowanceSide(Feature.LIMITE_DESBASTE);
//				
//				if (!genClosed.isPassante())
//					operation2.setAllowanceBottom(Feature.LIMITE_DESBASTE);
//				
//				// FERRAMENTA
//				FaceMill faceMill2 = chooseFaceMill(bloco.getMaterial(), faceMills,
//						genClosed, 0,
//						getMenorMenorDistance(genClosed));
//				
//				System.out.println("Ferramenta 2 de Diametro: " + faceMill2.getDiametroFerramenta());
//				// Estrategia
//				ContourParallel machiningStrategy2 = new ContourParallel();
//				operation2.setMachiningStrategy(machiningStrategy2);
//				machiningStrategy2.setAllowMultiplePasses(false);
//				machiningStrategy2.setCutmodeType(ContourParallel.conventional);
//				
//				// CONDIÇÕES DE USINAGEM
//				condicoesDeUsinagem = MapeadoraDeWorkingsteps
//						.getCondicoesDeUsinagem(this.projeto, faceMill2,
//								bloco.getMaterial());
//				// WORKINGSTEP 2
//				wsTmp2 = new Workingstep(genClosed, faceTmp, faceMill2,
//						condicoesDeUsinagem, operation2);
//				wsTmp2.setTipo(Workingstep.DESBASTE);
//				wsTmp2.setId(this.genClosed.getNome() + "_RGH");
//				
//				wsTmp2.setWorkingstepPrecedente(wsPrecedenteTmp);
//				wsPrecedenteTmp = wsTmp2;
//				
//				wssFeature.add(wsTmp2);
//				
			}
			genClosed.setWorkingsteps(wssFeature);
		}
//-----------------------------------------------------------------------------------------------------
//END Teste
//-----------------------------------------------------------------------------------------------------
		
	}
	public static ArrayList<ArrayList<LimitedElement>> getAreaAlreadyDesbasted(GeneralClosedPocket pocket, double planoZ, double distance, double overLap)
	{
		ArrayList<ArrayList<LimitedElement>> alreadyDesbastededArea = new ArrayList<ArrayList<LimitedElement>>(); //Array de array de elementos que ser�o convertidos em boss para a nova forma (acabamento) 
//		ArrayList<ArrayList<LimitedElement>> firstOffsetMultipleParallel = GenerateContournParallel.multipleParallelPath(pocket,planoZ,distance,overLap).get(0);
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, planoZ, distance, overLap);
		ArrayList<ArrayList<LimitedElement>> firstOffsetMultipleParallel = contourn.multipleParallelPath().get(0);
		//Estamos interessados do primeiro offset. Ele nos dira o que falta desbastar.
		for(int i = 0; i < firstOffsetMultipleParallel.size(); i++)
		{
			ArrayList<LimitedElement> validationParallelTmp = GenerateContournParallel.parallelPath1(firstOffsetMultipleParallel.get(i), distance, false, false); //elementos, nao interligados, dos novos bosses
			Point3d firstValidationElementInitialPoint = validationParallelTmp.get(0).getInitialPoint(); //ponto inicial do primeiro elemento do array
			ArrayList<LimitedElement> alreadyDesbastededAreaTmp = new ArrayList<LimitedElement>(); //elementos ordenados dos novos bosses
			for(int j = 0; j < validationParallelTmp.size(); j++)
			{
				Point3d firstOffsetElementFinalPoint = firstOffsetMultipleParallel.get(i).get(j).getFinalPoint(); //centro dos arcos de transicao
				LimitedElement validationParallelElementTmp = validationParallelTmp.get(j);
				alreadyDesbastededAreaTmp.add(validationParallelElementTmp);
				if(j == validationParallelTmp.size() - 1)
				{
					if(validationParallelElementTmp.getFinalPoint() != firstValidationElementInitialPoint)
					{
						LimitedArc transitionArc = new LimitedArc(firstOffsetElementFinalPoint, validationParallelElementTmp.getFinalPoint(), GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), firstValidationElementInitialPoint, firstOffsetElementFinalPoint, -2*Math.PI));
						alreadyDesbastededAreaTmp.add(transitionArc);
					}
				}
				else
				{
					LimitedElement validationParallelElementTmpNext = validationParallelTmp.get(j+1);
					if(validationParallelElementTmp.getFinalPoint() != validationParallelElementTmpNext.getInitialPoint())
					{
						LimitedArc transitionArc = new LimitedArc(firstOffsetElementFinalPoint, validationParallelElementTmp.getFinalPoint(), GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), validationParallelElementTmpNext.getInitialPoint(), firstOffsetElementFinalPoint, -2*Math.PI));
						alreadyDesbastededAreaTmp.add(transitionArc);
					}
				}
			}
			alreadyDesbastededArea.add(alreadyDesbastededAreaTmp);
		}
		return alreadyDesbastededArea;
	}
}
