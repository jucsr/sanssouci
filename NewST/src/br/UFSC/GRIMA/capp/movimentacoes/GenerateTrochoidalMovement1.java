package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

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
	public GenerateTrochoidalMovement1(ArrayList<LimitedElement> elements, double radius, double avanco)
	{
		this.elements = elements;
		this.radius = radius;
		this.avanco = avanco;
		this.generatePaths();
	}
	//O ADD DOS PATHS CIRCULARES (2 PI) ESTAO COMENTADOS PARA FINS DE TESTE
	private void generatePaths()
	{
//		for(LimitedElement elementTmp : this.elements)
		ArrayList<Path> pathArrayFinal = new ArrayList<Path>();
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
				CircularPath circularPath = null; //Ponto final do ultimo circulo do array
				ArrayList<Path> pathsInLineBase = generatePathsInLimitedLineBase((LimitedLine)elementTmp);
				for(int j = 0;j<pathsInLineBase.size();j++) //Gera paths sobre uma linha guia
				{
					Path pathTmp = pathsInLineBase.get(j);
//					pathArrayFinal.add(pathTmp);            //Add no array total de paths (que sera retornado pelo metodo)
					if(j == pathsInLineBase.size()-1)
					{
						if(pathTmp.isCircular())
						{
							circularPath = (CircularPath)pathTmp;  //a variavel, que se refere ao ultimo circulo do array, ganha valor
						}
					}
				}
				
				if(thereIsNext)
				{
					for(Path pathTmp: generatePathsInTransition(circularPath, elementTmp, elementTmpNext)) //Gera paths de transisao entre os elementos guia
					{
						paths.add(pathTmp);
					}
				}
//				this.generatePathsInLimitedLineBase((LimitedLine)elementTmp);
			} else if(elementTmp.isLimitedArc()) //Se o elemento i e um arco guia
			{
				CircularPath circularPath = null; //Ponto final do ultimo circulo do array
				ArrayList<Path> pathsInArcBase = generatePathsInLimitedArcBase((LimitedArc)elementTmp);
				for(int j = 0;j<pathsInArcBase.size();j++) //Gera paths sobre um arco guia
				{
					Path pathTmp = pathsInArcBase.get(j);
//					pathArrayFinal.add(pathTmp);            //Add no array total de paths (que sera retornado pelo metodo)
					if(j == pathsInArcBase.size()-1)
					{
						if(pathTmp.isCircular())
						{
							circularPath = (CircularPath)pathTmp;  //a variavel, que se refere ao ultimo circulo do array, ganha valor
						}
					}
				}
				
				if(thereIsNext)
				{
					for(Path pathTmp: generatePathsInTransition(circularPath, elementTmp, elementTmpNext)) //Gera paths de transisao entre os elementos guia
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
	private ArrayList<Path>generatePathsInTransition(CircularPath circularPath, LimitedElement element1, LimitedElement element2)
	{
		ArrayList<Path> saida = new ArrayList<Path>();
		if(element1.isLimitedLine())
		{
			LimitedLine l1 = (LimitedLine)element1;
			if(element2.isLimitedLine())
			{
				LimitedLine l2 = (LimitedLine)element2;
				System.out.println("L1: P1F " + l1.getFinalPoint());
				System.out.println("L2: P2I " + l2.getInitialPoint());
				Point3d unitVector = GeometricOperations.unitVector(l1.getInitialPoint(), l1.getFinalPoint()); //vetor direcao da linha1
				double distance = circularPath.getCenter().distance(l1.getFinalPoint()); //tamanho do linear path
				Point3d linePathFinalPoint = new Point3d(circularPath.getInitialPoint().x + GeometricOperations.multiply(distance, unitVector).x, circularPath.getInitialPoint().y + GeometricOperations.multiply(distance, unitVector).y,l1.getInitialPoint().z);
				LinearPath linePath = new LinearPath(circularPath.getInitialPoint(), linePathFinalPoint); //path entre circulos
//				System.out.println("LinearPath: PI " + linePath.getInitialPoint() + " PF " + linePath.getFinalPoint());
				
				//CUIDADO COM O "INSIDE"
				Point3d arcPathFinalPoint = GeometricOperations.absoluteParallel(l2, circularPath.getRadius(), false).getInitialPoint();
				double alpha = GeometricOperations.calcDeltaAngle(linePathFinalPoint,arcPathFinalPoint , l2.getInitialPoint(), circularPath.getAngulo());
				System.out.println("alpha2: " + alpha);
				System.out.println("deltaAngulo: " + circularPath.getAngulo());
				CircularPath arcPath = new CircularPath(l1.getFinalPoint(), linePathFinalPoint, arcPathFinalPoint, alpha);
				System.out.println("Centro: "+arcPath.getCenter());
				System.out.println("PI: " + arcPath.getInitialPoint());
				System.out.println("PF: " + arcPath.getFinalPoint());
				if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
				{
					saida.add(arcPath);
				}
				if(!(GeometricOperations.isTheSamePoint(linePath.getInitialPoint(), linePath.getFinalPoint())))
				{
					saida.add(linePath);
				}
			}
			else if(element2.isLimitedArc())
			{
				LimitedArc a2 = (LimitedArc)element2;
				System.out.println("L1: P1F " + l1.getFinalPoint());
				System.out.println("L2: P2I " + a2.getInitialPoint());
				Point3d unitVector = GeometricOperations.unitVector(l1.getInitialPoint(), l1.getFinalPoint()); //vetor direcao da linha1
				double distance = circularPath.getCenter().distance(l1.getFinalPoint()); //tamanho do linear path
				Point3d linePathFinalPoint = new Point3d(circularPath.getInitialPoint().x + GeometricOperations.multiply(distance, unitVector).x, circularPath.getInitialPoint().y + GeometricOperations.multiply(distance, unitVector).y,l1.getInitialPoint().z);
				LinearPath linePath = new LinearPath(circularPath.getInitialPoint(), linePathFinalPoint); //path entre circulos
//				System.out.println("LinearPath: PI " + linePath.getInitialPoint() + " PF " + linePath.getFinalPoint());
				
				//CUIDADO COM O "INSIDE"
				Point3d arcPathFinalPoint = GeometricOperations.parallelArc(a2, circularPath.getRadius(), false,false).getInitialPoint();
				System.out.println(arcPathFinalPoint);
				double alpha = GeometricOperations.calcDeltaAngle(linePathFinalPoint,arcPathFinalPoint , a2.getInitialPoint(), circularPath.getAngulo());
				System.out.println("alpha2: " + alpha);
				System.out.println("deltaAngulo: " + circularPath.getAngulo());
				CircularPath arcPath = new CircularPath(l1.getFinalPoint(), linePathFinalPoint, arcPathFinalPoint, alpha);
				System.out.println("Centro: "+arcPath.getCenter());
				System.out.println("PI: " + arcPath.getInitialPoint());
				System.out.println("PF: " + arcPath.getFinalPoint());
				if(!(GeometricOperations.isTheSamePoint(arcPath.getInitialPoint(), arcPath.getFinalPoint())))
				{
					System.out.println("lol");
					saida.add(arcPath);
				}
				if(!(GeometricOperations.isTheSamePoint(linePath.getInitialPoint(), linePath.getFinalPoint())))
				{
					saida.add(linePath);
				}
			}
			
		}
		return saida;
	}
	private ArrayList<Path> generatePathsInLimitedLineBase(LimitedLine line)
	{
//		ArrayList<Path> saida = new ArrayList<Path>();
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

//			paths.add(circuloTmp);
			
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
		CircularPath circuloTmp = new CircularPath(centroTmp, pontoInicialTmp, pontoInicialTmp, 2 * Math.PI);
		paths.add(circuloTmp);
		
		return paths;
	}
	private ArrayList<Path> generatePathsInLimitedArcBase(LimitedArc arc)
	{
//		System.out.println("arc = " + arc.getCenter());
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
			if(arc.getDeltaAngle() > 0)
			{
				xBaseProxima = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado + avanco / arc.getRadius());
				yBaseProxima = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado + avanco / arc.getRadius());
				sense = CircularPath.CCW;
			} else
			{
				xBaseProxima = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado - avanco / arc.getRadius());
				yBaseProxima = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado - avanco / arc.getRadius());
				sense = CircularPath.CW;
				radiusTmp = -radiusTmp;
			}
			Point3d vetorUnitarioTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseTmp);
			Point3d pontoInicialTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioTmp).y, arc.getCenter().z + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioTmp).z);
			CircularPath circularTmp = new CircularPath(centroBaseTmp, pontoInicialTmp, pontoInicialTmp, -2 * Math.PI);
//			paths.add(circularTmp);
			
			Point3d centroBaseProximaTmp = new Point3d(xBaseProxima, yBaseProxima, arc.getCenter().z);
			Point3d vetorUnitarioProximaTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseProximaTmp);
			Point3d pontoFinalTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioProximaTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioProximaTmp).y, arc.getCenter().z + GeometricOperations.multiply(arc.getRadius() + radiusTmp, vetorUnitarioProximaTmp).z);
			
//			CircularPath arcTmp = new CircularPath(pontoInicialTmp, pontoFinalTmp, arc.getCenter(), sense);
			double deltaAnguloTmp = avanco / (arc.getRadius()); 
			double normaAcumulada = Math.abs((i + 1) *avanco/* * deltaTmp * arc.getRadius()*/);
			double normaAcumulada1 = Math.abs(i * avanco/*deltaTmp * arc.getRadius()*/);
			if(arc.getDeltaAngle() < 0)
			{
				 if(normaAcumulada > norma)
				 {
					 deltaAnguloTmp = -(norma - normaAcumulada1) / (arc.getRadius());
//					 System.err.println("****\ndeltaAnguloTmp = " + (deltaAnguloTmp * 180 / Math.PI));
//					 System.err.println("****\ndeltaAnguloTmp = " + (deltaAnguloTmp));
//					 System.err.println("norma = " + (norma));
//					 System.err.println("norma acumulada = " + (normaAcumulada));
//					 System.err.println("norma acumulada1 = " + (normaAcumulada1));
				 } else
				 {
					 deltaAnguloTmp = - deltaAnguloTmp;
				 }
			}	
			else
			{
				if(normaAcumulada > norma)
				 {
					 deltaAnguloTmp = (norma - normaAcumulada1) / (arc.getRadius());
				 }
			}
//			System.err.println("normaAcumulada = " + normaAcumulada);
//			if(arc.getDeltaAngle() < 0)
//			{
//				deltaAnguloTmp = -deltaAnguloTmp;
//			}
//			System.out.println("delta Acumulado == " + deltaAcumulado);
			CircularPath arcTmp = new CircularPath(arc.getCenter(), pontoInicialTmp, pontoFinalTmp, deltaAnguloTmp, sense);
//			System.out.println("delta ANGULO " + i + " = "+ arcTmp.getAngulo());
//			System.out.println("ponto inicial " + i + " = " + arcTmp.getInitialPoint());
//			System.out.println("ponto final " + i + " = " + arcTmp.getFinalPoint());
//			arcTmp.setAngulo(angulo);
			paths.add(arcTmp);


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
