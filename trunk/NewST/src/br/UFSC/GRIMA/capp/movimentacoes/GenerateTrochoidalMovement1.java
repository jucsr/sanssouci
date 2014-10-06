package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

/**
 * 
 * @author jc
 *
 */
public class GenerateTrochoidalMovement1 
{
	private ArrayList<LimitedElement> elements;
	private ArrayList<Path> paths = new ArrayList<Path>();
	private double radius;
	private double avanco;
	private Workingstep ws;
	public GenerateTrochoidalMovement1(ArrayList<LimitedElement> elements, Workingstep ws)
	{
		this.ws = ws;
		this.elements = elements;
		this.radius = ((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).getTrochoidalRadius();
		this.avanco = ((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).getTrochoidalFeedRate();
		this.generatePaths();
	}
	//O ADD DOS PATHS CIRCULARES (2 PI) ESTAO COMENTADOS PARA FINS DE TESTE
	private void generatePaths()
	{
//		for(LimitedElement elementTmp : this.elements)
//		ArrayList<Path> pathArrayFinal = new ArrayList<Path>();
		for(int i = 0;i < this.elements.size();i++)
		{
			boolean thereIsNext = false;
			LimitedElement elementTmp = this.elements.get(i);
			LimitedElement elementTmpNext = null;
			
			if(i < this.elements.size()-1)
			{
				elementTmpNext = this.elements.get(i+1);
				thereIsNext = true;
			}
			
			if(elementTmp.isLimitedLine()) //Se o elemento i e uma linha guia
			{
				Path lastPath = null; //Ponto final do ultimo circulo do array 
				double circularPathAngle = 0;
				ArrayList<Path> pathsInLineBase = generatePathsInLimitedLineBase((LimitedLine)elementTmp);
				for(int j = 0;j<pathsInLineBase.size();j++) //Gera paths sobre uma linha guia
				{
					Path pathTmp = pathsInLineBase.get(j);
//					pathArrayFinal.add(pathTmp);            //Add no array total de paths (que sera retornado pelo metodo)
					if(j == pathsInLineBase.size()-1)
					{
//						if(pathTmp.isLine())
//						{
							lastPath = pathTmp;  //a variavel, que se refere ao ultimo circulo do array, ganha valor
//						}
					}
					//CUIDADO COM OS INDICES
					if(j == pathsInLineBase.size()-2)
					{
						if(pathTmp.isCircular())
						{
							circularPathAngle = ((CircularPath)pathTmp).getAngulo();
						}
					}
				}
				
				if(thereIsNext)
				{
					for(Path pathTmp: generatePathsInTransitionLine(lastPath,circularPathAngle, (LimitedLine)elementTmp, elementTmpNext)) //Gera paths de transisao entre os elementos guia
					{
						paths.add(pathTmp);
					}
				}
//				this.generatePathsInLimitedLineBase((LimitedLine)elementTmp);
			} else if(elementTmp.isLimitedArc()) //Se o elemento i e um arco guia
			{
				Path lastPath = null; //Ponto final do ultimo circulo do array
				double circularPathAngle = 0;
				ArrayList<Path> pathsInArcBase = generatePathsInLimitedArcBase((LimitedArc)elementTmp);
				for(int j = 0;j<pathsInArcBase.size();j++) //Gera paths sobre um arco guia
				{
					Path pathTmp = pathsInArcBase.get(j);
//					pathArrayFinal.add(pathTmp);            //Add no array total de paths (que sera retornado pelo metodo)
					if(j == pathsInArcBase.size()-1)
					{
//						if(pathTmp.isCircular())
//						{
							lastPath = pathTmp;  //a variavel, que se refere ao ultimo circulo do array, ganha valor
//						}
					}
					if(j == pathsInArcBase.size()-2)
					{
						if(pathTmp.isCircular())
						{
							circularPathAngle = ((CircularPath)pathTmp).getAngulo();
						}
					}
				}
				
				if(thereIsNext)
				{
					for(Path pathTmp: generatePathsInTransitionArc(lastPath, circularPathAngle, (LimitedArc)elementTmp, elementTmpNext)) //Gera paths de transisao entre os elementos guia
					{
						paths.add(pathTmp);
					}
				}
//				for(Path pathTmp:generatePathsInLimitedArcBase((LimitedArc)elementTmp)) //Gera paths sobre um arco guia
//				{
//					pathArrayFinal.add(pathTmp);   //Add no array total de paths (que sera retornado pelo metodo)
//				}
//				if(thereIsNext)
//				{
//					for(Path pathTmp: generatePathsInTransition(elementTmp, elementTmpNext)) //Gera paths de transisao entre os elementos guia
//					{
//						pathArrayFinal.add(pathTmp);
//					}
//				}
				
//				ArrayList<Path> paths = generatePathsInLimitedArcBase((LimitedArc)elementTmp);
//				this.generatePathsInLimitedArcBase((LimitedArc)elementTmp);
			}
		}
	}
	private ArrayList<Path>generatePathsInTransitionLine(Path lastPath, double circularPathAngle, LimitedLine l1, LimitedElement element2)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		if(element2.isLimitedLine()) //se o elemento 2 for linha
		{
			LimitedLine l2 = (LimitedLine)element2;
			//CUIDADO COM O "INSIDE"
			double arcPathRadius = l1.getFinalPoint().distance(lastPath.getFinalPoint());
//			ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//			show.add(l1);
//			show.add(l2);
//			GeometricOperations.showElements(show);
//			System.out.println("TransitionPathRadiusLL: " + arcPathRadius);
			Point3d arcPathFinalPoint = GeometricOperations.absoluteParallel(l2, arcPathRadius, false).getInitialPoint();
			double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , l2.getInitialPoint(), circularPathAngle);
			CircularPath arcPath = new CircularPath(l1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha); //path circular (leva a ferramenta ao ponto inicial do primeiro circulo trocoidal da linha guia 2)
			if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
			{
				saida.add(arcPath);
			}
		}
		else if(element2.isLimitedArc())
		{
			LimitedArc a2 = (LimitedArc)element2;
			//CUIDADO COM O "INSIDE" e o "isBoss"
			double arcPathRadius = l1.getFinalPoint().distance(lastPath.getFinalPoint());
//			ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//			show.add(l1);
//			show.add(a2);
//			GeometricOperations.showElements(show);
//			System.out.println("TransitionPathRadiusLA: " + arcPathRadius);
			Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a2,arcPathRadius, false,false).getInitialPoint();
			double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a2.getInitialPoint(), circularPathAngle);
			CircularPath arcPath = new CircularPath(l1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
			if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
			{
				saida.add(arcPath);
			}
		}
		return saida;
	}
	private ArrayList<Path>generatePathsInTransitionArc(Path lastPath, double circularPathAngle, LimitedArc a1, LimitedElement element2)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		if(element2.isLimitedLine())
		{
			LimitedLine l2 = (LimitedLine)element2;
//			System.out.println(lastPath.getFinalPoint());
//			System.out.println(a1.getFinalPoint());
//			ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//			show.add(a1);
//			show.add(l2);
			//CUIDADO COM O "INSIDE" e o "isBoss"
//			System.out.println("A1: " + a1.getInitialPoint().z);
			double arcPathRadius = a1.getFinalPoint().distance(lastPath.getFinalPoint());
//			System.out.println(arcPathRadius);
//			GeometricOperations.showElements(show);
//			System.out.println("TransitionPathRadiusAL: " + arcPathRadius);
//			Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a1,arcPathRadius, false,false).getInitialPoint();
			Point3d arcPathFinalPoint = GeometricOperations.absoluteParallel(l2, arcPathRadius, false).getInitialPoint();
			double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a1.getInitialPoint(), circularPathAngle);
//			System.out.println("lastPath: " + lastPath.getFinalPoint());
//			System.out.println("arcPathFinalPoint: " + arcPathFinalPoint);
			CircularPath arcPath = new CircularPath(l2.getInitialPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
			if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
			{
				saida.add(arcPath);
			}
		}
		else if(element2.isLimitedArc())
		{
			LimitedArc a2 = (LimitedArc)element2;
//			System.out.println("lolo: " + a1.getFinalPoint());
			double arcPathRadius = a1.getFinalPoint().distance(lastPath.getFinalPoint());
//			ArrayList<LimitedElement> show = new ArrayList<LimitedElement>();
//			show.add(a1);
//			show.add(a2);
//			GeometricOperations.showElements(show);
//			System.out.println("TransitionPathRadiusAA: " + arcPathRadius);
			Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a2,arcPathRadius, false,false).getInitialPoint();
			double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a2.getInitialPoint(), circularPathAngle);
			CircularPath arcPath = new CircularPath(a1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
			if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
			{
				saida.add(arcPath);
			}
		}
		return saida;
	}
//	private ArrayList<Path>generatePathsInTransition(Path lastPath, double circularPathAngle, LimitedElement element1, LimitedElement element2)
//	{
//		ArrayList<Path> saida = new ArrayList<Path>();
//		if(element1.isLimitedLine()) //se o elemento 1 for linha
//		{
//			LimitedLine l1 = (LimitedLine)element1;
//			if(element2.isLimitedLine()) //se o elemento 2 for linha
//			{
//				LimitedLine l2 = (LimitedLine)element2;
////				System.out.println("L1: P1F " + l1.getFinalPoint());
////				System.out.println("L2: P2I " + l2.getInitialPoint());
////				Point3d unitVector = GeometricOperations.unitVector(l1.getInitialPoint(), l1.getFinalPoint()); //vetor direcao da linha1
////				double distance = circularPath.getCenter().distance(l1.getFinalPoint()); //tamanho do linear path
////				Point3d linePathFinalPoint = new Point3d(circularPath.getInitialPoint().x + GeometricOperations.multiply(distance, unitVector).x, circularPath.getInitialPoint().y + GeometricOperations.multiply(distance, unitVector).y,l1.getInitialPoint().z);
////				LinearPath linePath = new LinearPath(circularPath.getInitialPoint(), linePathFinalPoint); //path entre circulos
//				
//				//CUIDADO COM O "INSIDE"
//				double arcPathRadius = l1.getFinalPoint().distance(lastPath.getFinalPoint());
//				Point3d arcPathFinalPoint = GeometricOperations.absoluteParallel(l2, arcPathRadius, false).getInitialPoint();
//				double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , l2.getInitialPoint(), circularPathAngle);
//				System.out.println("alpha2: " + alpha);
////				System.out.println("deltaAngulo: " + circularPath.getAngulo());
//				CircularPath arcPath = new CircularPath(l1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha); //path circular (leva a ferramenta ao ponto inicial do primeiro circulo trocoidal da linha guia 2)
//				System.out.println("Centro: "+arcPath.getCenter());
//				System.out.println("PI: " + arcPath.getInitialPoint());
//				System.out.println("PF: " + arcPath.getFinalPoint());
//				if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
//				{
//					saida.add(arcPath);
//				}
////				if(!(GeometricOperations.isTheSamePoint(linePath.getInitialPoint(), linePath.getFinalPoint())))
////				{
////					saida.add(linePath);
////				}
//			}
//			else if(element2.isLimitedArc())
//			{
//				LimitedArc a2 = (LimitedArc)element2;
////				System.out.println("L1: P1F " + l1.getFinalPoint());
////				System.out.println("L2: P2I " + a2.getInitialPoint());
////				Point3d unitVector = GeometricOperations.unitVector(l1.getInitialPoint(), l1.getFinalPoint()); //vetor direcao da linha1
////				double distance = circularPath.getCenter().distance(l1.getFinalPoint()); //tamanho do linear path
////				Point3d linePathFinalPoint = new Point3d(circularPath.getInitialPoint().x + GeometricOperations.multiply(distance, unitVector).x, circularPath.getInitialPoint().y + GeometricOperations.multiply(distance, unitVector).y,l1.getInitialPoint().z);
////				LinearPath linePath = new LinearPath(circularPath.getInitialPoint(), linePathFinalPoint); //path entre circulos
//				
//				//CUIDADO COM O "INSIDE" e o "isBoss"
//				double arcPathRadius = l1.getFinalPoint().distance(lastPath.getFinalPoint());
//				Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a2,arcPathRadius, false,false).getInitialPoint();
//				System.out.println(arcPathFinalPoint);
//				double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a2.getInitialPoint(), circularPathAngle);
//				System.out.println("alpha2: " + alpha);
////				System.out.println("deltaAngulo: " + circularPath.getAngulo());
//				CircularPath arcPath = new CircularPath(l1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
//				System.out.println("Centro: "+arcPath.getCenter());
//				System.out.println("PI: " + arcPath.getInitialPoint());
//				System.out.println("PF: " + arcPath.getFinalPoint());
//				if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
//				{
//					saida.add(arcPath);
//				}
////				if(!(GeometricOperations.isTheSamePoint(linePath.getInitialPoint(), linePath.getFinalPoint())))
////				{
////					saida.add(linePath);
////				}
//			}
//			
//		}
//		else if(element1.isLimitedArc())
//		{
//			LimitedArc a1 = (LimitedArc)element1;
//			if(element2.isLimitedLine())
//			{
//				LimitedLine l2 = (LimitedLine)element2;
//				//CUIDADO COM O "INSIDE" e o "isBoss"
//				double arcPathRadius = a1.getFinalPoint().distance(lastPath.getFinalPoint());
//				Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a1,arcPathRadius, false,false).getInitialPoint();
//				System.out.println(arcPathFinalPoint);
//				double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a1.getInitialPoint(), circularPathAngle);
//				System.out.println("alpha2: " + alpha);
////				System.out.println("deltaAngulo: " + circularPath.getAngulo());
//				CircularPath arcPath = new CircularPath(l2.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
//				System.out.println("Centro: "+arcPath.getCenter());
//				System.out.println("PI: " + arcPath.getInitialPoint());
//				System.out.println("PF: " + arcPath.getFinalPoint());
//			
//			}
//			else if(element2.isLimitedArc())
//			{
//				LimitedArc a2 = (LimitedArc)element2;
//				double arcPathRadius = a1.getFinalPoint().distance(lastPath.getFinalPoint());
//				Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a2,arcPathRadius, false,false).getInitialPoint();
//				System.out.println(lastPath.getFinalPoint());
////				System.out.println(GeometricOperations.parallelArc(a2,arcPathRadius, false,false).getFinalPoint());
//				double alpha = GeometricOperations.calcDeltaAngle(lastPath.getFinalPoint(),arcPathFinalPoint , a2.getInitialPoint(), circularPathAngle);
//				CircularPath arcPath = new CircularPath(a1.getFinalPoint(), lastPath.getFinalPoint(), arcPathFinalPoint, alpha);
//				if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
//				{
////					saida.add(arcPath);
//				}
//			}
//		}
//		return saida;
//	}
	private ArrayList<Path> generatePathsInLimitedLineBase(LimitedLine line)
	{
//		ArrayList<Path> saida = new ArrayList<Path>();
		double trochoidalAngle = 2*Math.PI;
		if(((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).getTrochoidalSense())
		{
			trochoidalAngle = -trochoidalAngle;
		}
		double norma = line.getInitialPoint().distance(line.getFinalPoint());
		double distanciaAcumulada = 0;
		LimitedLine lineAuxTmp = GeometricOperations.absoluteParallel(line, radius, false); // linha paralela
//		System.out.println("line = " + line.getInitialPoint() + line.getFinalPoint() + "\t\t\tlineaux = " + lineAuxTmp);
		Point3d vetorUnitario = GeometricOperations.unitVector(lineAuxTmp.getInitialPoint(), lineAuxTmp.getFinalPoint()); // vetor unitario da linha paralela

		int fracionamento = (int)(norma / avanco);
		for (int i = 0; i < fracionamento; i++)
		{
//			Point3d pontoInicialTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, lineAuxTmp.getInitialPoint().z + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).z);
			Point3d pontoInicialTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z);
//			System.out.println("line = " + line.getInitialPoint());
//			System.out.println("pi = " + pontoInicialTmp);
			/*
			 * gerando uma movimentacao circular
			 */
//			Point3d centroTmp = new Point3d(line.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, line.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).z);
			Point3d centroTmp = new Point3d(line.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, line.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z);
			CircularPath circuloTmp = new CircularPath(centroTmp, pontoInicialTmp, pontoInicialTmp, 2 * Math.PI);
//			System.err.println("RADIUS = " + circuloTmp.getRadius());

			paths.add(circuloTmp);
			
			/*
			 * gerando uma movimentacao linear
			 */
			if(distanciaAcumulada + avanco < norma)
			{
				distanciaAcumulada = distanciaAcumulada + avanco;
			} else
			{
				distanciaAcumulada = norma;
			}
			
//			Point3d pontoFinalTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, lineAuxTmp.getInitialPoint().z + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).z);
			Point3d pontoFinalTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z);
			LinearPath linhaTmp = new LinearPath(pontoInicialTmp, pontoFinalTmp, LinearPath.SLOW_MOV);
			paths.add(linhaTmp);
		}
		/*
		 * ultimo circulo no final da linha ------> cuidado! pode nao ser necessario
		 */
//		Point3d pontoInicialTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, lineAuxTmp.getInitialPoint().z + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).z);
		Point3d pontoInicialTmp = new Point3d(lineAuxTmp.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, lineAuxTmp.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z);
//		Point3d centroTmp = new Point3d(line.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, line.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).z);
		Point3d centroTmp = new Point3d(line.getInitialPoint().x + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).x, line.getInitialPoint().y + GeometricOperations.multiply(distanciaAcumulada, vetorUnitario).y, line.getInitialPoint().z);
		CircularPath circuloTmp = new CircularPath(centroTmp, pontoInicialTmp, pontoInicialTmp, trochoidalAngle);
		LinearPath linePath = new LinearPath(circuloTmp.getInitialPoint(),lineAuxTmp.getFinalPoint());
		paths.add(circuloTmp);
		paths.add(linePath);
		
		return paths;
	}
	private ArrayList<Path> generatePathsInLimitedArcBase(LimitedArc arc)
	{
//		System.out.println("lol: " + arc.getFinalPoint());
//		System.out.println("lol1: " + arc.getInitialPoint());

		double trochoidalAngle = 2*Math.PI;
		if(((TrochoidalAndContourParallelStrategy)ws.getOperation().getMachiningStrategy()).getTrochoidalSense())
		{
			trochoidalAngle = -trochoidalAngle;
		}//		System.out.println("arc = " + arc.getCenter());
//		ArrayList<Path> saida = new ArrayList<Path>();
		double norma = Math.abs(arc.getDeltaAngle() * arc.getRadius());
		double initialAngle = Math.atan2(arc.getInitialPoint().y - arc.getCenter().y, arc.getInitialPoint().x - arc.getCenter().x); 
		double deltaAcumulado = 0;                      //variavel que dita o avanco angular dos circulos
		int fracionamento = (int)(norma / avanco) + 1;  //Numero de arcos (que interligam os circulos) que cabem no arco guia
//		double deltaTmp = avanco / arc.getRadius();
//		System.out.println("fracionamento = " + fracionamento);
//		System.out.println("norma = " + norma);
		for(int i = 0; i < fracionamento; i++)
		{
//			for(int i = 0; i * deltaAcumulado * arc.getRadius() < norma; i++)

			double xBase = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado);
			double yBase = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado);
			Point3d centroBaseTmp = new Point3d(xBase, yBase, arc.getCenter().z);
			
			
			double xBaseProxima;
			double yBaseProxima;
			double radiusTmp = radius;
			int sense;
			//-------------------------------------------------------------------------------------
			//valida o ultimo arco (quando nao for do mesmo tamanho dos outros)
			//CUIDADO COM O DELTAANGULOTMP
			double deltaAnguloTmp = avanco / (arc.getRadius()); 
			double normaAcumulada = Math.abs((i + 1) *avanco/* * deltaTmp * arc.getRadius()*/); //Norma contando o proximo arco
			double normaAcumulada1 = Math.abs(i * avanco/*deltaTmp * arc.getRadius()*/);        //Norma Atual
//			if(arc.getDeltaAngle() < 0)
//			{
//				if(normaAcumulada > norma)
//				{
//					//CUIDADO DELTATMP DO ULTIMO PATH
//					deltaAnguloTmp = -(norma - normaAcumulada1) / (arc.getRadius());
////					 System.err.println("****\ndeltaAnguloTmp = " + (deltaAnguloTmp * 180 / Math.PI));
////					 System.err.println("****\ndeltaAnguloTmp = " + (deltaAnguloTmp));
////					 System.err.println("norma = " + (norma));
////					 System.err.println("norma acumulada = " + (normaAcumulada));
////					 System.err.println("norma acumulada1 = " + (normaAcumulada1));
//				} else
//				{
//					deltaAnguloTmp = - deltaAnguloTmp;
//				}
//			}	
//			else
//			{
				if(normaAcumulada > norma) //Criacao do ultimo arco (pode nao ser do mesmo tamanho dos outros)
				{
					deltaAnguloTmp = (norma - normaAcumulada1) / (arc.getRadius());
//					System.out.println("DeltaAngle: " + deltaAnguloTmp);
				}
//			}
			//-------------------------------------------------------------------------------------
			if(arc.getDeltaAngle() > 0)
			{
				xBaseProxima = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado + deltaAnguloTmp);
				yBaseProxima = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado + deltaAnguloTmp);
				sense = CircularPath.CCW;
			} else
			{
				xBaseProxima = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado - deltaAnguloTmp);
				yBaseProxima = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado - deltaAnguloTmp);
				sense = CircularPath.CW;
				radiusTmp = -radiusTmp;
				deltaAnguloTmp = - deltaAnguloTmp;
			}
			Point3d vetorUnitarioTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseTmp);
			Point3d pontoInicialTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioTmp).y, arc.getCenter().z);
//			System.out.println("PI: " + pontoInicialTmp);
			CircularPath circularTmp = new CircularPath(centroBaseTmp, pontoInicialTmp, pontoInicialTmp, trochoidalAngle);
			paths.add(circularTmp);
			
			Point3d centroBaseProximaTmp = new Point3d(xBaseProxima, yBaseProxima, arc.getCenter().z);
			Point3d vetorUnitarioProximaTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseProximaTmp);
			/*
			 * Errado para o ultimo path
			 */
			Point3d pontoFinalTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioProximaTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioProximaTmp).y, arc.getCenter().z);
//			System.out.println("Pf: " + pontoFinalTmp);
//			System.out.println("PF: " + pontoFinalTmp);
//			CircularPath arcTmp = new CircularPath(pontoInicialTmp, pontoFinalTmp, arc.getCenter(), sense);
//			System.err.println("normaAcumulada = " + normaAcumulada);
//			if(arc.getDeltaAngle() < 0)
//			{
//				deltaAnguloTmp = -deltaAnguloTmp;
//			}
//			System.out.println("delta Acumulado == " + deltaAcumulado);
//			System.out.println("DeltaTmp: " + deltaAnguloTmp);
			CircularPath arcTmp = new CircularPath(arc.getCenter(), pontoInicialTmp, pontoFinalTmp, deltaAnguloTmp, sense);
//			System.out.println("arcPath: C" + arcTmp.getCenter() + "PI" + arcTmp.getInitialPoint() + "ANG" + arcTmp.getAngulo() + "R" + arcTmp.getRadius());
//			System.out.println("lol"+pontoInicialTmp);
//			System.out.println("delta ANGULO " + i + " = "+ arcTmp.getAngulo());
//			System.out.println("ponto inicial " + i + " = " + arcTmp.getInitialPoint());
//			System.out.println("ponto final " + i + " = " + arcTmp.getFinalPoint());
//			arcTmp.setAngulo(angulo);
//			if(i == fracionamento-1)
//			{
			paths.add(arcTmp);
//			}


//			System.out.println("RaioArcTmp =  " + pontoInicialTmp.distance(arc.getCenter()));
//			System.out.println("centerBaseTmp = " + centroBaseTmp);
			
//			System.out.println(i + " ===============");
//			System.out.println("raio arco" + arc.getRadius());
//			System.out.println("Raio circular = " + circularTmp.getCenter());
//			System.out.println("center = " + circularTmp.getCenter());
//			System.out.println("pontoinicial = " + circularTmp.getInitialPoint());
//			if(arc.getDeltaAngle() > 0)
//			{
				deltaAcumulado = deltaAcumulado + deltaAnguloTmp/*avanco / arc.getRadius()*/;
//			} else
//			{
//				deltaAcumulado = deltaAcumulado - avanco / arc.getRadius();
//			}
		}
		return paths;
	}
	/**
	 *  transforma paths em limitedElements
	 * @param paths --> paths
	 * @return
	 */
	public static ArrayList<LimitedElement> transformPathsInLimitedElements(ArrayList<Path> paths)
	{
		ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>();
		for(Path pathTmp : paths)
		{
			if(pathTmp.isLine())
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				LimitedLine lineTmp = new LimitedLine(linearTmp.getInitialPoint(), linearTmp.getFinalPoint());
				saida.add(lineTmp);
			}
			else if(pathTmp.isCircular())
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
//				LimitedArc arcTmp = new LimitedArc(circularTmp.getInitialPoint(), circularTmp.getFinalPoint(), circularTmp.getCenter());
				LimitedArc arcTmp;

				if(GeometricOperations.isTheSamePoint(circularTmp.getInitialPoint(), circularTmp.getFinalPoint()))
				{
					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), 2 * Math.PI, CircularPath.CCW);	
				}
				else
				{
					arcTmp = new LimitedArc(circularTmp.getCenter(), circularTmp.getInitialPoint(), circularTmp.getAngulo());	

//					if(circularTmp.getSense() == CircularPath.CCW)
//					{
//						arcTmp = new LimitedArc(circularTmp.getInitialPoint(), circularTmp.getFinalPoint(), circularTmp.getCenter());
//					}
//					else
//					{
//						arcTmp = new LimitedArc(circularTmp.getFinalPoint(), circularTmp.getInitialPoint(), circularTmp.getCenter());
//					}
				}
				saida.add(arcTmp);
			}
		}
		return saida;
	}
	public ArrayList<Path> getPaths() 
	{
		return paths;
	}
}
