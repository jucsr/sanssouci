package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
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
import br.UFSC.GRIMA.capp.movimentacoes.GenerateTrochoidalMovement1;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.MachinningStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel.RotationDirection;
import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
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
import br.UFSC.GRIMA.samples.PocketTest;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.GenerateVoronoiArray;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.Transformer;
import br.UFSC.GRIMA.util.Triangulation;
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
	private double time;

	public MapeadoraGeneralClosedPocket1(Projeto projeto, Face face, GeneralClosedPocket genClosed) 
	{
//		wssFeature = new Vector();
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
//		final GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
		final GeneralPath gp = (GeneralPath)Face.getShape(addPocket.getElements());

			
		//CRIA UM Shape2D DA PROTUBERANCIA
		ArrayList<Shape> bossShape = new ArrayList<Shape>();
		for(ArrayList<LimitedElement> bossTmp:bossElements)
		{
//			GeometricOperations.showElements(bossTmp);
			bossShape.add(Face.getShape(bossTmp));
		}

		//Percorre uma matriz de pontos dentro da forma da cavidade, verificando qual e a maior distancia 
		//entre as menores distancias entre os pontos e os elementos
//		boolean contains = false;
		final ArrayList<Point2D> arrayPointTmp = new ArrayList<Point2D>();    //pontos validos (dentro da cavidade e fora da protuberancia)
		for(int i = 0; i < numeroDePontos; i++)
		{
			for(int j = 0; j < numeroDePontos; j++)
			{
				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointY.getY() + deltaY*j);
				
				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
				{
					boolean contains = false;
					if(thereIsBoss)      //Se possui Protuberancia
					{
						for(Shape bossTmp:bossShape)
//						for(int k = 0;k < bossShape.size();k++)
						{
							if(!bossTmp.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
//							if(!bossShape.get(k).contains(pointTmp)) //Se o ponto esta dentro da protuberancia
							{
//								arrayPointTmp.add(pointTmp);
//								contains = false;
							}
							else
							{
								contains = true;
							}
						}
						if(!contains)
						{
							arrayPointTmp.add(pointTmp);
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
		
//		//Desenhador
//		JFrame frame = new JFrame();
//		frame.setSize(new Dimension(300, 300));
//		class Panel extends JPanel
//		{
//			protected void paintComponent(Graphics g)
//			{
//				Graphics2D g2d = (Graphics2D)g;
//				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
//				
////				g2d.translate(0, 300);
////				g2d.scale(1, -1);
//				g2d.draw(gp);
//				for(Shape shape:bossShape)
//				{
//					g2d.draw(shape);
//				}
////				for(ArrayList<Point2D> arrayTmp:matrix)
////				{
//					for(Point2D pointTmp:arrayPointTmp)
//					{
//////						g2d.drawOval((int)pointTmp.getX(), (int)pointTmp.getY(), 1, 1);
//						g2d.draw(new Ellipse2D.Double(pointTmp.getX(), pointTmp.getY(), 1, 1));
//					}
////				}
//			}
//		}
//		frame.getContentPane().add(new Panel());
//		frame.setVisible(true);
//		drawShape(addPocket.getElements(), bossElements);
		
		return minimumMaxDistance;
	}
	
	public double getMenorMenorDistance(/*GeneralClosedPocket genClosed*/ArrayList<ArrayList<LimitedElement>> arrayBossElements)
	{
//		ArrayList<Boss> itsBoss = genClosed.getItsBoss(); //Array de protuberancias
		//Linearizar o array de boss (o metodo requer um array unidimensional)
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
			if(menorDistancia > genClosed.getRadius())
			{
				menorDistancia = genClosed.getRadius();
			}
//			System.out.println("lolol menor: " + menorDistancia);
//			System.out.println("Menor Menor Distancia c/protuberancia: " + menorDistancia);
		}
		else
		{
//			menorDistancia = genClosed.getRadius();
			GenerateVoronoiArray voronoi = new GenerateVoronoiArray(genClosed);
			menorDistancia = voronoi.getMinimumMax();
//			System.out.println("Menor Menor Distancia s/protuberancia: " + menorDistancia);
			//menorDistancia = GeometricOperations.minimumDistance(addPocket.getElements());
		}
		return menorDistancia;
	}
	
	private FaceMill chooseFaceMill(Material material,ArrayList<FaceMill> faceMills, GeneralClosedPocket genClosed,double limite_desbaste, double L) 
	{
//		System.out.println("L: " + L);
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
			
//			System.err.println(faceMill.getDiametroFerramenta());
//			System.err.println(L);
			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (genClosed
							.getProfundidade() - limite_desbaste_fundo))
			{
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
	private ArrayList<FaceMill> chooseFaceMills(Material material,ArrayList<FaceMill> faceMills, GeneralClosedPocket genClosed,double limite_desbaste, double L) 
	{
//		System.out.println("L: " + L);
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
			
//			System.err.println(faceMill.getDiametroFerramenta());
//			System.err.println(L);
			if (faceMill.getMaterial().equals(ISO)
					&& faceMill.getDiametroFerramenta() <= (L - 2 * limite_desbaste)
					&& faceMill.getProfundidadeMaxima() >= (genClosed
							.getProfundidade() - limite_desbaste_fundo))
			{
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
		//Maior diametro possivel
		faceMill = faceMillsCandidatas.get(faceMillsCandidatas.size()-1);
		ArrayList<FaceMill> saida = new ArrayList<FaceMill>();
		saida.add(faceMill);
		
		//Seleciona as faceMills que cabem no diametro passado, ate a que seja 75% do valor da primeira
		for (int i = 1; i < faceMillsCandidatas.size(); i++) 
		{
			FaceMill faceMillTmp = faceMillsCandidatas.get(i);
			System.err.println("Facemill: " + faceMillTmp.getDiametroFerramenta());
			if (faceMillTmp.getDiametroFerramenta() >= faceMill.getDiametroFerramenta()*0.75) 
			{
				saida.add(faceMillsCandidatas.get(i));
			}

		}

		return saida;
	}
	
	private void mapearGeneralClosedPocket() 
	{
		double fator = 0.75; //Para verificar a possibilidade de outra ferramenta menor
		//ArrayList<Workingstep> workingSteps = new ArrayList<Workingstep>();
		Workingstep wsPrecedenteTmp;
		wssFeature = new Vector<Workingstep>();
		double retractPlane = 5;
		
		//Calculo da area da cavidade
//		Triangulation triangulation = new Triangulation(genClosed.getPoints());
		Triangulation triangulation = new Triangulation(Transformer.limitedElementToPoints2D(addPocket.getElements()));
		double areaCavidade = triangulation.getArea();
		System.err.println("area da cavidade: " + areaCavidade);
		
		//Calculo da area das protuberancias
		ArrayList<ArrayList<LimitedElement>> bossElements = GenerateContournParallel.gerarElementosDaProtuberancia(genClosed, genClosed.Z); //protuberancias reais
		double areaBoss = 0;
		for(ArrayList<LimitedElement> array:bossElements)
		{
			triangulation = new Triangulation(Transformer.limitedElementToPoints2D(array));
			areaBoss += triangulation.getArea();
		}
		
		//Area total a ser desbastada
		double areaDeDesbaste = areaCavidade - areaBoss;
		System.err.println("area dos bosses: " + areaBoss);
		System.err.println("area a ser desbastada: " + areaDeDesbaste);
		
		//
		double maiorMenorDistanciaTmp = getMaiorMenorDistancia(bossElements); //maior menor distancia inicial
		double menorMenorDistanciaTmp = getMenorMenorDistance(bossElements);
		System.err.println("Maior Distancia: " + maiorMenorDistanciaTmp);
		System.err.println("Menor Distancia: " + menorMenorDistanciaTmp);
		
		//Se nao for a primeira cavidade
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
		}
		//Se for a primeira cavidade
		else
		{
			//Nao tem ws precedente
			wsPrecedenteTmp = null;
		}
		// WORKINGSTEPS DE DESBASTE
		if (!genClosed.isAcabamento()) 
		{
			bossElements = null; //a partir de agora esse array guarda as protuberancias virtuais
//			while(maiorMenorDistanciaTmp >= menorMenorDistanciaTmp)
//			{
//				System.err.println("Maior Distancia: " + maiorMenorDistanciaTmp);
//				System.err.println("Menor Distancia: " + menorMenorDistanciaTmp);
//				boolean flag = false;
//				// BOTTOM AND SIDE ROUGH MILLING
//				BottomAndSideRoughMilling operationTmp = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);
//				operationTmp.setAllowanceSide(Feature.LIMITE_DESBASTE);
//	
//				if (!genClosed.isPassante())
//				{
//					operationTmp.setAllowanceBottom(Feature.LIMITE_DESBASTE);
//				}
//				
//				// FERRAMENTA
//				
////				FaceMill faceMillTmp = chooseFaceMill(bloco.getMaterial(), faceMills,genClosed, 0, maiorMenorDistanciaTmp);
//				//array de facemills que possuem diamentro maior que 75% do maior diametro
//				ArrayList<FaceMill> faceMillsTmp = chooseFaceMills(bloco.getMaterial(), faceMills,genClosed, 0, maiorMenorDistanciaTmp);
//				//facemill de maior diametro
//				FaceMill faceMillTmp = faceMillsTmp.get(faceMillsTmp.size()-1);
//				
//				System.err.println("Ferramenta: " + faceMillTmp.getDiametroFerramenta());
//				//Criacao de um boss virtual (indicando a area que sera desbastada pela ferramenta de diametro escolhido acima)
//				double trochoidalRadius = faceMillTmp.getDiametroFerramenta()/2;
//				double overLap = 2;
//				//Uma copia dos bosses virtuais (o original sera modificado)
//				ArrayList<ArrayList<LimitedElement>> bossElementsAux = new ArrayList<ArrayList<LimitedElement>>();
//				if(bossElements != null)
//				{
//					for(ArrayList<LimitedElement> array:bossElements)
//					{
//						bossElementsAux.add(array);
//					}
//				}
//				else
//				{
//					bossElementsAux = null;
//				}
//				
//				// CONDICOES DE USINAGEM
//				condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, faceMillTmp, bloco.getMaterial());
//				//Estrategia de usinagem
//				TrochoidalAndContourParallelStrategy machiningStrategyTmp = new TrochoidalAndContourParallelStrategy();
//				machiningStrategyTmp.setAllowMultiplePasses(true);
////				machiningStrategyTmp.setOverLap(0.25*faceMillTmp.getDiametroFerramenta()); //Overlap
//				machiningStrategyTmp.setOverLap(2); //Overlap
//				machiningStrategyTmp.setTrochoidalRadius(trochoidalRadius); //REVER MAIS TARDE
//				machiningStrategyTmp.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
//				machiningStrategyTmp.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
//				operationTmp.setMachiningStrategy(machiningStrategyTmp);    
//				
//				//Estrategia de aproximacao
//				PlungeStrategy plungeStrategy = new PlungeToolAxis();
//				operationTmp.setApproachStrategy(plungeStrategy);
//				
//				// WORKINGSTEP
//				Workingstep wsTmp = new Workingstep(genClosed, faceTmp, faceMillTmp,condicoesDeUsinagem, operationTmp);
//				wsTmp.setTipo(Workingstep.DESBASTE);
//				wsTmp.setId(this.genClosed.getNome() + "_RGH");
//				wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
//				
//				//Boss virtual referente a ferramenta escolhida
//				bossElements = getAreaAlreadyDesbasted1(genClosed,bossElements, genClosed.Z, trochoidalRadius + faceMillTmp.getDiametroFerramenta()/2, overLap,wsTmp);
//				System.err.println("Time: " + time);
//				double maiorMenorDistanciaAtual = maiorMenorDistanciaTmp;
//				maiorMenorDistanciaTmp = getMaiorMenorDistancia(bossElements);
//				
//				//Obtencao da area desbastada pela ferramenta escolhida
//				double areaFerramentaAtual = 0;
//				for(ArrayList<LimitedElement> array:bossElements)
//				{
//					ArrayList<Point2D> points = Transformer.limitedElementToPoints2D(array);
//					triangulation = new Triangulation(points);
//					areaFerramentaAtual += triangulation.getArea();
//				}
//				System.err.println("Area desbastada: " + areaFerramentaAtual);
//				double areaRestanteFerramentaAtual = (areaDeDesbaste - areaFerramentaAtual);
//				System.err.println("Area restante: " + areaRestanteFerramentaAtual);
//				System.err.println("Maior Distancia: " + maiorMenorDistanciaTmp);
//				drawShape(addPocket.getElements(), bossElements);
//				
//				//Verifica a possibilidade de uma outra ferramenta ser mais apropriada
//				if(maiorMenorDistanciaAtual != 0)
//				{
//					//Pega uma ferramenta auxiliar com o diametro um percentual menor do que a ja escolhida
//					FaceMill faceMillAux = chooseFaceMill(bloco.getMaterial(), faceMills,genClosed, 0, maiorMenorDistanciaAtual*fator);
//					System.err.println("Ferramenta Aux: " + faceMillAux.getDiametroFerramenta());
//					double trochoidalRadiusAux = faceMillAux.getDiametroFerramenta()/2;
//					
//					// CONDICOES DE USINAGEM
//					CondicoesDeUsinagem condicoesDeUsinagemAux = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, faceMillAux, bloco.getMaterial());
//					BottomAndSideRoughMilling operationAux = (BottomAndSideRoughMilling)wsTmp.getOperation();
//					
//					//Estrategia de usinagem
//					TrochoidalAndContourParallelStrategy machiningStrategyAux = (TrochoidalAndContourParallelStrategy)wsTmp.getOperation().getMachiningStrategy();
//					machiningStrategyAux.setTrochoidalRadius(trochoidalRadiusAux); //REVER MAIS TARDE
//					operationAux.setMachiningStrategy(machiningStrategyAux);
//					
//					//Ws referente a ferramenta aux
//					Workingstep wsAux = new Workingstep(genClosed, faceTmp, faceMillAux,condicoesDeUsinagemAux, operationAux);
//					wsAux.setTipo(wsTmp.getTipo());
//					wsAux.setId(wsTmp.getId());
//					wsAux.setWorkingstepPrecedente(wsPrecedenteTmp);
//					
//					//Boss virtual referente a ferramenta auxiliar
//					bossElementsAux = getAreaAlreadyDesbasted1(genClosed,bossElementsAux, genClosed.Z, trochoidalRadiusAux + faceMillAux.getDiametroFerramenta()/2, overLap,wsAux);
//					System.err.println("TimeAux: " + time);
//					double maiorMenorDistanciaAux = getMaiorMenorDistancia(bossElementsAux);
//					
//					//Obtencao da area desbastada pela ferramenta escolhida
//					double areaFerramentaAux = 0;
//					for(ArrayList<LimitedElement> array:bossElementsAux)
//					{
//						ArrayList<Point2D> points = Transformer.limitedElementToPoints2D(array);
//						triangulation = new Triangulation(points);
//						areaFerramentaAux += triangulation.getArea();
//					}
//					System.err.println("Area desbastada aux: " + areaFerramentaAux);
//					double areaRestanteFerramentaAux = (areaDeDesbaste - areaFerramentaAux);
//					System.err.println("Area restante aux: " + areaRestanteFerramentaAux);
//					System.err.println("Maior Distancia aux: " + maiorMenorDistanciaAux);
//					//percentual de diferenca entre o desbaste referente a cada ferramenta
//					double diferancaRelativaDeDesbaste = (Math.abs(areaRestanteFerramentaAux - areaRestanteFerramentaAtual)/areaRestanteFerramentaAtual);
////					double diferancaRelativaDeDesbaste = (Math.abs(areaFerramentaAux - areaFerramentaAtual)/areaFerramentaAux)*100;
//					System.err.println("Diferanca relativa: " + diferancaRelativaDeDesbaste);
//					System.err.println("Fator: " + diferancaRelativaDeDesbaste/time);
//					drawShape(addPocket.getElements(), bossElementsAux);
//					//se o percentual de diferenca for maior que 30%, troca a ferramenta atual pela auxiliar
//					if(diferancaRelativaDeDesbaste >= 0.3)
//					{
//						//faceMillTmp = faceMillTmpAux;
//						wsPrecedenteTmp = wsAux;
//						maiorMenorDistanciaTmp = maiorMenorDistanciaAux;
//						wssFeature.add(wsAux);
//						flag = true;
//					}
//				}
//				if(!flag)
//				{
//					wsPrecedenteTmp = wsTmp;
//					wssFeature.add(wsTmp);
//				}
//			}
			
			while(maiorMenorDistanciaTmp >= menorMenorDistanciaTmp)
			{
				System.err.println("Maior Distancia: " + maiorMenorDistanciaTmp);
				System.err.println("Menor Distancia: " + menorMenorDistanciaTmp);
				boolean flag = false;
				
				// BOTTOM AND SIDE ROUGH MILLING
				BottomAndSideRoughMilling operationTmp = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);
				operationTmp.setAllowanceSide(Feature.LIMITE_DESBASTE);
				if (!genClosed.isPassante())
				{
					operationTmp.setAllowanceBottom(Feature.LIMITE_DESBASTE);
				}
					
				// FERRAMENTA
				//array de facemills que possuem diamentro maior que 75% do maior diametro
				ArrayList<FaceMill> faceMillsTmp = chooseFaceMills(bloco.getMaterial(), faceMills,genClosed, 0, maiorMenorDistanciaTmp);
				//facemill de maior diametro
				double maiorFerramenta = faceMillsTmp.get(faceMillsTmp.size()-1).getDiametroFerramenta();
				System.err.println("Ferramenta: " + maiorFerramenta);
				
				//laco para criar os possiveis ws
				ArrayList<Workingstep> wsPossiveis = new ArrayList<Workingstep>();
				ArrayList<Double> maioresMenoresDistancias = new ArrayList<Double>();
				ArrayList<Double> fatores = new ArrayList<Double>();
				for(FaceMill faceMillTmp:faceMillsTmp)
				{
					double trochoidalRadius = faceMillTmp.getDiametroFerramenta()/2;
					double overLap = 2;
					//Criacao de um boss virtual (indicando a area que sera desbastada pela ferramenta de diametro escolhido acima)
					//Uma copia dos bosses virtuais (o original sera modificado)
					ArrayList<ArrayList<LimitedElement>> bossElementsAux = new ArrayList<ArrayList<LimitedElement>>();
					if(bossElements != null)
					{
						for(ArrayList<LimitedElement> array:bossElements)
						{
							bossElementsAux.add(array);
						}
					}
					else
					{
						bossElementsAux = null;
					}
					
					// CONDICOES DE USINAGEM
					condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, faceMillTmp, bloco.getMaterial());
					//Estrategia de usinagem
					TrochoidalAndContourParallelStrategy machiningStrategyTmp = new TrochoidalAndContourParallelStrategy();
					machiningStrategyTmp.setAllowMultiplePasses(true);
//				    machiningStrategyTmp.setOverLap(0.25*faceMillTmp.getDiametroFerramenta()); //Overlap
					machiningStrategyTmp.setOverLap(2); //Overlap
					machiningStrategyTmp.setTrochoidalRadius(trochoidalRadius); //REVER MAIS TARDE
					machiningStrategyTmp.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
					machiningStrategyTmp.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
					operationTmp.setMachiningStrategy(machiningStrategyTmp);    
					
					//Estrategia de aproximacao
					PlungeStrategy plungeStrategy = new PlungeToolAxis();
					operationTmp.setApproachStrategy(plungeStrategy);
					
					// WORKINGSTEP
					Workingstep wsTmp = new Workingstep(genClosed, faceTmp, faceMillTmp,condicoesDeUsinagem, operationTmp);
					wsTmp.setTipo(Workingstep.DESBASTE);
					wsTmp.setId(this.genClosed.getNome() + "_RGH");
					wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
					
					//Boss virtual referente a ferramenta escolhida
					bossElementsAux = getAreaAlreadyDesbasted1(genClosed,bossElements, genClosed.Z, trochoidalRadius + faceMillTmp.getDiametroFerramenta()/2, overLap,wsTmp);
					double maiorMenorDistanciaAux = getMaiorMenorDistancia(bossElementsAux);
					
					//Obtencao da area desbastada pela ferramenta escolhida
					double areaFerramentaAux = 0;
					for(ArrayList<LimitedElement> array:bossElementsAux)
					{
						ArrayList<Point2D> points = Transformer.limitedElementToPoints2D(array);
						triangulation = new Triangulation(points);
						areaFerramentaAux += triangulation.getArea();
					}
					double fatorDeDesbaste = (areaFerramentaAux/areaDeDesbaste)/time;
					wsPossiveis.add(wsTmp);
					maioresMenoresDistancias.add(maiorMenorDistanciaAux);
					fatores.add(fatorDeDesbaste);
					
					drawShape(addPocket.getElements(), bossElementsAux);
				}
				
				System.err.println("Maior Distancia: " + maiorMenorDistanciaTmp);
				//wsPrecedenteTmp = wsTmp;
				//wssFeature.add(wsTmp);
			}			
			// WORKINGSTEP DE ACABAMENTO
			// BOTTOM AND SIDE ROUGH MILLING
			BottomAndSideRoughMilling operationTmp = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);
			operationTmp.setAllowanceSide(Feature.LIMITE_DESBASTE);
			System.out.println("Menor menor distancia: " + menorMenorDistanciaTmp);
			// FERRAMENTA
			double diametroFerramentaAcabamento = menorMenorDistanciaTmp;
			FaceMill faceMillTmp = chooseFaceMill(bloco.getMaterial(), faceMills,genClosed, 0, diametroFerramentaAcabamento);

			//Estrategia de usinagem
			ContourParallel machiningStrategyTmp = new ContourParallel();
			operationTmp.setMachiningStrategy(machiningStrategyTmp);
			machiningStrategyTmp.setAllowMultiplePasses(false);
			machiningStrategyTmp.setRotationDirection(RotationDirection.CCW);
//			machiningStrategyTmp.setCutmodeType(TrochoidalAndContourParallelStrategy.conventional);
			
			//Estrategia de aproximacao
			PlungeStrategy plungeStrategy = new PlungeToolAxis();
			operationTmp.setApproachStrategy(plungeStrategy);
			
			// CONDICOES DE USINAGEM
			condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, faceMillTmp, bloco.getMaterial());
			
			// WORKINGSTEP
			Workingstep wsTmp = new Workingstep(genClosed, faceTmp, faceMillTmp, condicoesDeUsinagem, operationTmp);
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setId(this.genClosed.getNome() + "_RGH");
			
			wsTmp.setWorkingstepPrecedente(wsPrecedenteTmp);
			wsPrecedenteTmp = wsTmp;
			
			wssFeature.add(wsTmp);
			//workingSteps.add(wsTmp);

			genClosed.setWorkingsteps(wssFeature);
		}
//-----------------------------------------------------------------------------------------------------
//END Teste
//-----------------------------------------------------------------------------------------------------
		
	}
	public static ArrayList<ArrayList<LimitedElement>> getAreaAlreadyDesbasted(GeneralClosedPocket pocket,ArrayList<ArrayList<LimitedElement>> bossElements, double planoZ, double distance, double overLap)
	{
		ArrayList<ArrayList<LimitedElement>> alreadyDesbastededArea = new ArrayList<ArrayList<LimitedElement>>(); //Array de array de elementos que ser�o convertidos em boss para a nova forma (acabamento) 
//		ArrayList<ArrayList<LimitedElement>> firstOffsetMultipleParallel = GenerateContournParallel.multipleParallelPath(pocket,planoZ,distance,overLap).get(0);
		GenerateContournParallel contourn = new GenerateContournParallel(pocket, planoZ, distance, overLap); //contrutor pra a primeira trajetotia do primeiro working step
		if(bossElements != null)
		{
			contourn = new GenerateContournParallel(pocket,bossElements, planoZ, distance, overLap);//construtor para as trajetorias
		}
//		System.out.println("lolol: " + contourn.multipleParallelPath());
//		if(contourn.multipleParallelPath().size() != 0)
//		{
		ArrayList<ArrayList<LimitedElement>> firstOffsetMultipleParallel = contourn.multipleParallelPath().get(0);
		//Estamos interessados do primeiro offset. Ele nos dira o que falta desbastar.
		for(int i = 0; i < firstOffsetMultipleParallel.size(); i++)
		{
//			ArrayList<LimitedElement> meshInverted = firstOffsetMultipleParallel.get(i);
//			ArrayList<LimitedElement> meshInverted = GeometricOperations.elementInverter(firstOffsetMultipleParallel.get(i));
//			ArrayList<LimitedElement> meshInverted = GeometricOperations.arrayInverter(GeometricOperations.elementInverter(firstOffsetMultipleParallel.get(i)));
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
//					if(validationParallelElementTmp.getFinalPoint() != firstValidationElementInitialPoint)
					if(!GeometricOperations.isTheSamePoint(validationParallelElementTmp.getFinalPoint(), firstValidationElementInitialPoint))

					{
//						Point3d transitionArcCenter = null;
						Point3d transitionArcCenter = firstOffsetElementFinalPoint;
//						if(validationParallelElementTmp.isLimitedArc())
//						{
//							if(((LimitedArc)validationParallelElementTmp).getDeltaAngle() < 0)
//							{
//								transitionArcCenter = ((LimitedArc)firstOffsetMultipleParallel.get(i).get(j)).getCenter();
//							}
//						}
						LimitedArc transitionArc = new LimitedArc(transitionArcCenter, validationParallelElementTmp.getFinalPoint(), /*2*Math.PI +*/ GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), firstValidationElementInitialPoint, firstOffsetElementFinalPoint, 2*Math.PI));
						alreadyDesbastededAreaTmp.add(transitionArc);
					}
//					GeometricOperations.showElements(alreadyDesbastededAreaTmp);
				}
				else
				{
					LimitedElement validationParallelElementTmpNext = validationParallelTmp.get(j+1);
//					if(validationParallelElementTmp.getFinalPoint() != validationParallelElementTmpNext.getInitialPoint())
					if(!GeometricOperations.isTheSamePoint(validationParallelElementTmp.getFinalPoint(), validationParallelElementTmpNext.getInitialPoint()))
					{
						Point3d transitionArcCenter = firstOffsetElementFinalPoint;
//						if(validationParallelElementTmp.isLimitedArc())
//						{
//							if(((LimitedArc)validationParallelElementTmp).getDeltaAngle() < 0)
//							{
//								transitionArcCenter = ((LimitedArc)firstOffsetMultipleParallel.get(i).get(j)).getCenter();
//							}
//						}						
						LimitedArc transitionArc = new LimitedArc(transitionArcCenter, validationParallelElementTmp.getFinalPoint(), /*2*Math.PI +*/ GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), validationParallelElementTmpNext.getInitialPoint(), firstOffsetElementFinalPoint, 2*Math.PI));
						alreadyDesbastededAreaTmp.add(transitionArc);
					}
				}
			}
//			alreadyDesbastededArea.add(alreadyDesbastededAreaTmp);
			//OS ELEMENTOS FORAM GERADOS NO SENTIDO ANTI-HORARIO
			//DEVEMOS INVERTER O SENTIDO PARA QUE O ARRAY SE PASSE POR UMA PROTUBERANCIA
			alreadyDesbastededArea.add(GeometricOperations.arrayInverter(GeometricOperations.elementInverter(alreadyDesbastededAreaTmp)));
//		}
		}
		return alreadyDesbastededArea;
	}
	/**
	 * Cria protuberancias virtuais (que indicam o que ja foi desbastado)
	 * @param pocket
	 * @param bossElements
	 * @param planoZ
	 * @param distance
	 * @param overLap
	 * @return
	 */
	public ArrayList<ArrayList<LimitedElement>> getAreaAlreadyDesbasted1(GeneralClosedPocket pocket,ArrayList<ArrayList<LimitedElement>> bossElements, double planoZ, double distance, double overLap,Workingstep ws)
	{
		double timeAux = 0;
		ArrayList<ArrayList<LimitedElement>> alreadyDesbastededArea = new ArrayList<ArrayList<LimitedElement>>(); //Array de array de elementos que ser�o convertidos em boss para a nova forma (acabamento) 
		GenerateContournParallel contourn = null; //contrutor pra a primeira trajetotia do primeiro working step
		if(bossElements != null)
		{
			contourn = new GenerateContournParallel(pocket,bossElements, planoZ, distance, overLap);//construtor para as trajetorias
		}
		else
		{
			contourn = new GenerateContournParallel(pocket, planoZ, distance, overLap);
		}
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = contourn.multipleParallelPath();
		if(multipleParallel.size() != 0)
		{
			ArrayList<ArrayList<LimitedElement>> firstOffsetMultipleParallel = multipleParallel.get(0);
//			System.out.println(firstOffsetMultipleParallel.size());
			//Estamos interessados do primeiro offset. Ele nos dira o que falta desbastar.
			for(int i = 0; i <firstOffsetMultipleParallel.size(); i++) //percorre os lacos do primeiro offset
			{
				ArrayList<LimitedElement> meshInverted = GeometricOperations.arrayInverter(GeometricOperations.elementInverter(firstOffsetMultipleParallel.get(i)));
				ArrayList<LimitedElement> alreadyDesbastededAreaTmp = fillArrayWithArcs(meshInverted, distance); //elementos ordenados dos novos bosses
//				ArrayList<LimitedElement> alreadyDesbastededAreaTmp = fillArrayWithArcs(firstOffsetMultipleParallel.get(i), distance); //elementos ordenados dos novos bosses
				alreadyDesbastededArea.add(alreadyDesbastededAreaTmp);
			}
			for(ArrayList<ArrayList<LimitedElement>> offset:multipleParallel)
			{
				for(ArrayList<LimitedElement> insideOffset:offset)
				{
					GenerateTrochoidalMovement1 trochoidalMovement = new GenerateTrochoidalMovement1(insideOffset, ws);
					double timeTmp = getTime(trochoidalMovement.getPaths(), ws.getCondicoesUsinagem().getVf());
					timeAux += timeTmp;
				}
			}
		}
		if(bossElements != null)
		{
			for(ArrayList<LimitedElement> arrayTmp:bossElements)
			{
				alreadyDesbastededArea.add(arrayTmp);
			}
		}
		time = timeAux;
		return alreadyDesbastededArea;
	}
	/**
	 * Faz a paralela dos elementos de um array e completa as lacunas com arcos
	 * @param arrayToParallelAndFill
	 * @param distance
	 * @return
	 */
	public ArrayList<LimitedElement> fillArrayWithArcs(ArrayList<LimitedElement> arrayToParallelAndFill,double distance)
	{
		ArrayList<LimitedElement> alreadyDesbastededAreaTmp = new ArrayList<LimitedElement>(); //elementos ordenados dos novos bosses
		ArrayList<LimitedElement> arrayToFill = GenerateContournParallel.parallelPath1(arrayToParallelAndFill, distance, false, true); //elementos, nao interligados, dos novos bosses
//		ArrayList<LimitedElement> arrayToFill = GeometricOperations.arrayInverter(GeometricOperations.elementInverter(GenerateContournParallel.parallelPath1(arrayToParallelAndFill, distance, false, true))); //elementos, nao interligados, dos novos bosses
		Point3d firstValidationElementInitialPoint = arrayToFill.get(0).getInitialPoint(); //ponto inicial do primeiro elemento do array
//		System.out.println("Offset Initial Point: " + firstValidationElementInitialPoint);
		for(int j = 0; j < arrayToFill.size(); j++) //percorre os elementos paralelos de cada laco
		{
//			Point3d firstOffsetElementFinalPoint = meshInverted.get(j).getFinalPoint(); //centro dos arcos de transicao
			LimitedElement validationParallelElementTmp = arrayToFill.get(j);
			Point3d transitionArcCenter = arrayToParallelAndFill.get(j).getFinalPoint(); //centro dos arcos de transicao
			alreadyDesbastededAreaTmp.add(validationParallelElementTmp);
			if(j == arrayToFill.size() - 1)
			{
				if(!GeometricOperations.isTheSamePoint(validationParallelElementTmp.getFinalPoint(), firstValidationElementInitialPoint))
				{
					LimitedArc transitionArc = new LimitedArc(transitionArcCenter, validationParallelElementTmp.getFinalPoint(), /*2*Math.PI +*/ GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), firstValidationElementInitialPoint, transitionArcCenter, -2*Math.PI));
					GeometricOperations.showElements(transitionArc, 0);
					alreadyDesbastededAreaTmp.add(transitionArc);
				}
			}
			else
			{
				LimitedElement validationParallelElementTmpNext = arrayToFill.get(j+1);
				if(!GeometricOperations.isTheSamePoint(validationParallelElementTmp.getFinalPoint(), validationParallelElementTmpNext.getInitialPoint()))
				{
					LimitedArc transitionArc = new LimitedArc(transitionArcCenter, validationParallelElementTmp.getFinalPoint(), /*2*Math.PI +*/ GeometricOperations.calcDeltaAngle(validationParallelElementTmp.getFinalPoint(), validationParallelElementTmpNext.getInitialPoint(), transitionArcCenter, -2*Math.PI));
					GeometricOperations.showElements(transitionArc, 0);
//					System.out.println("Final Point: " + transitionArc.getFinalPoint());
					alreadyDesbastededAreaTmp.add(transitionArc);
				}
			}
		}
		return alreadyDesbastededAreaTmp;
	}

	public static void drawShape(ArrayList<LimitedElement> pocketElements, ArrayList<ArrayList<LimitedElement>> bossElements)
	{
//		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		//CRIA O GENERAL PATH DO FORMATO
		final GeneralPath gp = (GeneralPath)Face.getShape(pocketElements);

		//CRIA Shape2D DAS PROTUBERANCIAS
		final ArrayList<Shape> bossShape = new ArrayList<Shape>();
		if(bossElements != null)
		{
			for(ArrayList<LimitedElement> bossTmp:bossElements)
			{
				bossShape.add(Face.getShape(bossTmp));
			}
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
				
				g2d.translate(0, 400);
				g2d.scale(1, -1);
				g2d.setColor(new Color(0, 0, 0));
//				g2d.fill(gp);
				g2d.draw(gp);
				for(Shape shape:bossShape)
				{
					g2d.setColor(new Color(15, 60, 212));
					g2d.fill(shape);
					g2d.draw(shape);
				}
			}
		}
		frame.getContentPane().add(new Panel());
		frame.setVisible(true);
	}
	public static void drawShapeAndPoints(ArrayList<LimitedElement> pocketElements, ArrayList<ArrayList<LimitedElement>> bossElements, final ArrayList<Point3d> points)
	{
//		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
		//CRIA O GENERAL PATH DO FORMATO
		final GeneralPath gp = (GeneralPath)Face.getShape(pocketElements);

		//CRIA Shape2D DAS PROTUBERANCIAS
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
				
				g2d.translate(0, 400);
				g2d.scale(1, -1);
//				g2d.setColor(new Color(0, 0, 0));
//				g2d.fill(gp);
				g2d.draw(gp);
				for(Shape shape:bossShape)
				{
//					g2d.setColor(new Color(15, 60, 212));
//					g2d.fill(shape);
					g2d.draw(shape);
				}
				for(Point3d pointTmp:points)
				{
////					g2d.drawOval((int)pointTmp.getX(), (int)pointTmp.getY(), 1, 1);
					g2d.draw(new Ellipse2D.Double(pointTmp.x, pointTmp.y, 1, 1));
				}
			}
		}
		frame.getContentPane().add(new Panel());
		frame.setVisible(true);
	}
	public static double getTime(ArrayList<Path> paths,double speed)
	{
		double totalDistance = 0;
		for(Path path:paths)
		{
			if(path.isLine())
			{
				totalDistance += path.getInitialPoint().distance(path.getFinalPoint());
			}
			else if(path.isCircular())
			{
				CircularPath circularPath = (CircularPath)path;
				totalDistance += circularPath.getAngulo()*circularPath.getRadius();
			}
		}
		return totalDistance/speed;
	}
}


