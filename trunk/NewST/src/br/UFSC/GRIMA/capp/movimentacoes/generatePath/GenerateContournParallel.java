package br.UFSC.GRIMA.capp.movimentacoes.generatePath;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateContournParallel 
{
	private GeneralClosedPocket pocket;
	private double planoZ;
	private double distance;
	private ArrayList<LimitedElement> elementsCavidade;
	ArrayList<LimitedElement> elementosProtuberancia;
	private ArrayList<LimitedElement> formaOriginal = new ArrayList<LimitedElement>();              //Array da forma original (cavidade+protuberancia)
	
	public GenerateContournParallel(GeneralClosedPocket pocket, double planoZ, double distance) 
	{
		this.pocket = pocket;
		this.planoZ = planoZ;
		this.distance = distance;
		this.gerarElementosDaCavidade();
		this.gerarElementosDaProtuberancia();
	}
	private void gerarElementosDaCavidade()
	{
		GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), planoZ, pocket.getRadius());
		elementsCavidade = addPocketVertex.getElements();
	}
	private void gerarElementosDaProtuberancia()
	{
		ArrayList<Boss> bossArray = pocket.getItsBoss();                                        //Protuberancia
		
		for(Boss bossTmp: bossArray)
		{
			elementosProtuberancia = new ArrayList<LimitedElement>();
			if(bossTmp.getClass() == CircularBoss.class)
			{
				CircularBoss tmp = (CircularBoss)bossTmp;
//				System.out.println("Profundidade Boss: " + tmp.Z);
//				System.out.println(tmp.getCenter().x + (tmp.getDiametro1()/2));
				LimitedArc arc = new LimitedArc(new Point3d(tmp.getCenter().x, tmp.getCenter().y, planoZ), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, planoZ), 2 * Math.PI);
//				System.out.println("Protuberancia Arco: " + arc.getInitialPoint());
				elementosProtuberancia.add(arc);
			}
			else if (bossTmp.getClass() == RectangularBoss.class)
			{
				RectangularBoss tmp = (RectangularBoss)bossTmp;
				//Tamanho em x
				double l = tmp.getL1();
				//Tamanho em y
				double c = tmp.getL2();
				Point3d position = new Point3d(tmp.X, tmp.Y, planoZ);
				LimitedLine l1 = new LimitedLine(GeometricOperations.pointPlusEscalar(position, "x", tmp.getRadius()),GeometricOperations.pointPlusEscalar(position,"x",(l-tmp.getRadius())));
				LimitedArc a1 = new LimitedArc(GeometricOperations.pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()),l1.getFinalPoint(),Math.PI/2);
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(),GeometricOperations.pointPlusEscalar(a1.getFinalPoint(), "y", (c-2*tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(GeometricOperations.pointPlusEscalar(l2.getFinalPoint(), "x", -tmp.getRadius()),l2.getFinalPoint(),Math.PI/2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),GeometricOperations.pointPlusEscalar(a2.getFinalPoint(), "x", -(l - 2*tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(GeometricOperations.pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()),l3.getFinalPoint(),Math.PI/2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(),GeometricOperations.pointPlusEscalar(a3.getFinalPoint(),"y",-(c - 2*tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(GeometricOperations.pointPlusEscalar(l4.getFinalPoint(), "x", tmp.getRadius()),l4.getFinalPoint(),Math.PI/2);
				elementosProtuberancia.add(l1);
				elementosProtuberancia.add(a1);
				elementosProtuberancia.add(l2);
				elementosProtuberancia.add(a2);
				elementosProtuberancia.add(l3);
				elementosProtuberancia.add(a3);
				elementosProtuberancia.add(l4);
				elementosProtuberancia.add(a4);
			}else if (bossTmp.getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss tmp = (GeneralProfileBoss)bossTmp;
				GeneralClosedPocketVertexAdd addBossVertex = new GeneralClosedPocketVertexAdd(tmp.getVertexPoints(), planoZ, tmp.getRadius());
				ArrayList<LimitedElement> elementosProtuberanciaGeral = addBossVertex.getElements();
				for(int i = 0;i < elementosProtuberanciaGeral.size();i++)
				{
					elementosProtuberancia.add(elementosProtuberanciaGeral.get(i));
				}		
			}
		}

		for(LimitedElement tmp:elementosProtuberancia)
		{
			boolean allowAdd = true;
			if(tmp.isLimitedArc())
			{
				if(((LimitedArc)tmp).getRadius() == 0)
				{
					allowAdd = false;
				}
			}
			if(allowAdd)
			{
				formaOriginal.add(tmp);
			}
		}
		for(LimitedElement tmp:elementsCavidade)
		{
			formaOriginal.add(tmp);
		}
		System.out.println("=====formaOriginal ======");
		GeometricOperations.showElements(formaOriginal);		
		System.out.println("=====EndformaOriginal ======");
	}

	public ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallelPath()
	{
		double percentagem = 0.75;
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = new ArrayList<ArrayList<ArrayList<LimitedElement>>>();
		
//		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath1(elements, distance);
		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath2(pocket, distance, planoZ);

		int aux = 1;
		double distanceAtualizada = 2*distance*percentagem;
//		System.out.println("Distancia: " + distance);
//		System.out.println("DistanciaAtual: " + distanceAtualizada);
//		System.out.println("Percentagem: " + percentagem);
//		parallelPath != null
		while (parallelPath != null)
		{
			multipleParallel.add(parallelPath);
			parallelPath = parallelPath2(pocket, distance + (aux*distanceAtualizada), planoZ);
			aux++;
			break;
		}		
	//		System.out.println("mutilplePath: " + multipleParallel.size());
	//		showElements(multipleParallel.get(0).get(0));
		return multipleParallel;
	}
	public ArrayList<ArrayList<LimitedElement>> parallelPath2 (GeneralClosedPocket pocket, double distance, double planoZ)
	{
//		System.out.println("Profundidade: " + pocket.getProfundidade());
		boolean inside = true;
		//Erro na criação da cavidade em um plano z != 0

		ArrayList<ArrayList<LimitedElement>> saida = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> parallelTemp1 = new ArrayList<LimitedElement>();      		//Paralela dos elementos da protuberancia
		ArrayList<LimitedElement> parallelTemp2 = new ArrayList<LimitedElement>();              //Paralela dos elementos da cavidade
		ArrayList<LimitedElement> totalParallel = new ArrayList<LimitedElement>();              //Array com todas as paralelas

		
		parallelTemp1 = parallelPath1(elementosProtuberancia, distance,!inside);
		parallelTemp2 = parallelPath1(elementsCavidade, distance, inside);
		
		for(LimitedElement tmp:parallelTemp1)
		{
			totalParallel.add(tmp);
		}
		for(LimitedElement tmp:parallelTemp2)
		{
			totalParallel.add(tmp);
		}
		
//		showElements(totalParallel);
		
//		saida = parallelPath1(laco, distance, !inside);
		GeometricOperations.showElements(totalParallel);
		saida = validarPath(totalParallel, formaOriginal, distance);
		
//		saida.add(totalParallel);
//		showElements(totalParallel);
		
		return saida;
	}
	public ArrayList<LimitedElement> parallelPath1 (ArrayList<LimitedElement> elements, double distance, boolean inside)
	{
//		boolean inside = true;
//		ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>();
//		ArrayList<LimitedElement> laco = new ArrayList<LimitedElement>();
//		for (int i = 0; i < elements.size(); i++)
//		{
//			ArrayList<LimitedElement> a0 = elements.get(i);
			ArrayList<LimitedElement> lacoTmp = new ArrayList<LimitedElement>();
//			System.out.println("Elementos do laco " + i + " da Forma Atual: " + a0.size());
			for(int j = 0;j < elements.size();j++)
			{
				if(elements.get(j).isLimitedLine())
				{
					LimitedLine lineTmp = (LimitedLine)elements.get(j);
					LimitedLine newLine = absoluteParallel(lineTmp, distance,inside);

					if(newLine != null)
					{
						lacoTmp.add(newLine);
					}
					//System.err.println("linha " + i);
				} 
				else if(elements.get(j).isLimitedArc())
				{
					LimitedArc arcTmp = (LimitedArc)elements.get(j);
					LimitedArc newArc;
					if (arcTmp.getRadius() == 0)
					{
//						System.out.println(lacoTmp);
						Point3d center = arcTmp.getInitialPoint();
						Point3d pI = lacoTmp.get(j - 1).getFinalPoint();
						newArc = new LimitedArc(center, pI, Math.PI / 2);
						
					}
					else
					{
						newArc = parallelArc(arcTmp, distance,inside);
					}
					if(newArc != null)
					{
						lacoTmp.add(newArc);
					}
//				System.out.println("Center: " + newArc.getCenter());
					//System.err.println("arco " + i);
				}
			}
//			laco.add(lacoTmp);
//		}
		
//		saida = validarPath(laco, elements, distance);
		
//		saida.add(lacoTmp);
		
		return lacoTmp;
	}
	public static ArrayList<ArrayList<LimitedElement>> validarPath(ArrayList<LimitedElement> elements, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
//		System.out.println("elements: " + elements.size());
//		for(int i = 0;i < elements.size();i++)
//		{
			ArrayList<LimitedElement> elementsIntermediario = validar1Path(elements);
//			GeometricOperations.showElements(elementsIntermediario);
			ArrayList<LimitedElement> elementsIntermediario2 = validar2Path(elementsIntermediario,formaOriginal,distance);
//			showElements(elementsIntermediario2);
//			elementsValidated.add(elementsIntermediario2);
//			showElements(elements);
//			System.out.println("elementsInter2: " + elementsIntermediario2.size());
			ArrayList<ArrayList<LimitedElement>> elementsIntermediario3 = validar3Path(elementsIntermediario2);
			if(elementsIntermediario3 != null)
			{
//				System.out.println("elementsInter3: " + elementsIntermediario3.size());
//				showElements(elementsIntermediario2);
				for (int j = 0; j < elementsIntermediario3.size(); j++)
				{
					elementsValidated.add(elementsIntermediario3.get(j));					
				}
			}
//		elementsValidated.add(elementsIntermediario2);
//		}
		if (elementsValidated.size() != 0)
		{
			return elementsValidated;
		}
		else
		{
			return null;
		}
	}
	
	public static ArrayList<LimitedElement> validar1Path(ArrayList<LimitedElement> elements)
	{
		/*
		 * 	Valida��o 1: Quebra dos Elementos na intersecao
		 */
		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
//		GeometricOperations.showElements(elements);
		
		for (int i=0; i < elements.size(); i++)
		{
			ArrayList<Point3d> intersection = null;
			boolean thereIsIntersection = false;
			boolean firstIntersection = true;
			LimitedElement ei = elements.get(i);
			for (int j = 0; j < elements.size(); j++)
			{
				ArrayList<Point3d> intersectionTemp = null;
				LimitedElement ej = elements.get(j);
//				intersection = null;
				if(!ei.equals(ej))
				{
					intersectionTemp = GeometricOperations.intersectionElements(ei, ej);
//					System.err.println("========" + ei.getInitialPoint() + "\t" + ei.getFinalPoint() + "\t EJ = " + ej.getInitialPoint() + "\t" + ej.getFinalPoint()) ;
					//essa condicao so funciona se o metodo que calcula as intersecoes retornar um array nulo quando nao ha intersecao (ao inves de um array vazio)
					if (intersectionTemp != null)
					{
						thereIsIntersection = true;
						if(firstIntersection)
						{
							intersection = new ArrayList<Point3d>();
							firstIntersection = false;
						}
//						System.out.println(intersection);
						for(int k = 0;k < intersectionTemp.size();k++)
						{
							intersection.add(intersectionTemp.get(k));
						}
					}
				}
			}
			if(intersection != null)
			{
//				System.out.println("Intersecoes: " + intersection);
				if (ei.isLimitedLine())
				{
					LimitedLine linei = (LimitedLine)ei;
					ArrayList<LimitedLine> lineTemp = GeometricOperations.quebraLinha(linei,intersection); 
					for(int k = 0;k < lineTemp.size();k++)
					{
						if(!GeometricOperations.isTheSamePoint(lineTemp.get(k).getInitialPoint(), lineTemp.get(k).getFinalPoint()))
						{
							elementsIntermediario.add(lineTemp.get(k));
						}
					}
				}
				else if(ei.isLimitedArc())
				{
					LimitedArc arci = (LimitedArc)ei;
//					System.out.println(arci.getInitialPoint());
//					System.out.println(arci.getCenter());
//					System.out.println(arci.getDeltaAngle());
//					System.out.println(intersection);
					ArrayList<LimitedArc> arcTemp = GeometricOperations.quebraArco(arci,intersection); 
//					System.out.println(arcTemp.size());
					for(int k = 0;k < arcTemp.size();k++)
					{
						if(!GeometricOperations.isTheSamePoint(arcTemp.get(k).getInitialPoint(), arcTemp.get(k).getFinalPoint()))
						{
							elementsIntermediario.add(arcTemp.get(k));
						}
					}
				}
			}
			if(thereIsIntersection == false)
			{
//				elementsIntermediario.add(ei);
				if(ei.isLimitedLine() && !GeometricOperations.isTheSamePoint(ei.getInitialPoint(), ei.getFinalPoint()))
				{
					elementsIntermediario.add(ei);
				}
				else if(ei.isLimitedArc() && GeometricOperations.roundNumber(((LimitedArc)ei).getDeltaAngle(),10) != 0)
				{
					elementsIntermediario.add(ei);
				}
			}
			
		}
//		showElements(elementsIntermediario);
		return elementsIntermediario;
	}
	public static ArrayList<LimitedElement> validar2Path(ArrayList<LimitedElement> elementsIntermediario, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		/*
		 * 	Valida��o 2: Elementos com a minima distancia (em relacao a forma original) menor que a distancia de offset, sao descartados 
		 */
		ArrayList<LimitedElement> elementsIntermediario2 = new ArrayList<LimitedElement>();
//		System.out.println("Elementos intermediarios: " + elementsIntermediario.size());
		for(int i = 0; i< elementsIntermediario.size();i++)
		{
			LimitedElement ei0 = elementsIntermediario.get(i);
			//System.out.println("Menor distancia elemento " + i + ": " +  minimumDistance(formaOriginal, ei0));
			if(GeometricOperations.roundNumber(GeometricOperations.minimumDistance(formaOriginal, ei0),7) >= distance)
			{
				elementsIntermediario2.add(ei0);
			}
		}
//		System.out.println("Elementos intermediarios2: " + elementsIntermediario2.size());
//		showElements(elementsIntermediario2);
		return elementsIntermediario2;
	}
	/**
	 * metodo de Validacao - separacao em lacos
	 * @param elementsIntermediario2
	 * @return lacos de elementos
	 */
	public static ArrayList<ArrayList<LimitedElement>> validar3Path(ArrayList<LimitedElement> elementsIntermediario2)
	{
		if(elementsIntermediario2.size() == 0)
		{
			return null;
		}
		else
		{
			ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
			elementsValidated.add(new ArrayList<LimitedElement>());
			int numeroDeLacos = 0;
			Point3d initialPoint = null;
			boolean alreadyPassed = false;
			LimitedElement ei0 = elementsIntermediario2.get(0);
			LimitedElement ei0new = elementsIntermediario2.get(0);
//			System.out.println("Size:" + elementsIntermediario2.size());
			Iterator iter = elementsIntermediario2.iterator();
			while(iter.hasNext())
			{
				boolean hasNoFinalPoint = true;
//			System.out.println("Size:" + elementsIntermediario2.size());
				ei0 = ei0new;
				Point3d ei0I = ei0.getInitialPoint();
//				System.out.println("ei0I: " + ei0I);
				Point3d ei0F = ei0.getFinalPoint();
				elementsValidated.get(numeroDeLacos).add(ei0);
				for(int j = 0; j < elementsIntermediario2.size(); j++)
				{
//				System.out.println("Aux2: " + aux2);
					LimitedElement ej = elementsIntermediario2.get(j);
					if(!(alreadyPassed))
					{
						initialPoint = ei0I;
						alreadyPassed = true;
//					System.out.println("InitialPoint: " + initialPoint);
					}
					Point3d ejI = ej.getInitialPoint();
//				System.out.println("ejI: " + ejI);
					if(GeometricOperations.isTheSamePoint(ei0F,ejI))
					{
						ei0new = elementsIntermediario2.get(j);
//						System.out.println("ei0I: " + ei0I);
						elementsIntermediario2.remove(ei0);
						hasNoFinalPoint = false;
						break;
					}
					else if(GeometricOperations.isTheSamePoint(ei0F,initialPoint))
					{
						alreadyPassed = false;
						elementsIntermediario2.remove(ei0);
						if(iter.hasNext())
						{
							ei0new = elementsIntermediario2.get(0);
							numeroDeLacos++;
							elementsValidated.add(new ArrayList<LimitedElement>());
						}
						break;
					}
					if(hasNoFinalPoint)
					{
						elementsIntermediario2.remove(ei0);
						if(iter.hasNext())
						{
							ei0new = elementsIntermediario2.get(0);
						}

					}
				}
//			System.out.println("Numero de lacos: " + numeroDeLacos);
			}
//			System.out.println("laco1: " + elementsValidated.get(0).size());
			if(elementsValidated.size() == 2)
			{
//				System.out.println("laco2: " + elementsValidated.get(1).size());
			}
			if(elementsValidated.size() == 3)
			{
//				System.out.println("laco2: " + elementsValidated.get(1).size());
//				System.out.println("laco3: " + elementsValidated.get(2).size());
			}
			
			return elementsValidated;
		}
	}
	public LimitedLine absoluteParallel(LimitedLine line, double distance, boolean inside)
	{
		Point3d initialPoint = new Point3d(line.getInitialPoint().x, line.getInitialPoint().y, 0);
		Point3d finalPoint = new Point3d(line.getFinalPoint().x, line.getFinalPoint().y, 0);
		double angleLine = GeometricOperations.angle(GeometricOperations.minus(initialPoint, finalPoint));
		double newDistanceAngle = angleLine+Math.PI/2;
		double x = Math.cos(newDistanceAngle);
		double y = Math.sin(newDistanceAngle);
		Point3d unitDistance = new Point3d(x, y, 0);
		Point3d distanceVector;
		if(inside)
		{
			distanceVector = GeometricOperations.multiply(distance, unitDistance);		
		}
		else
		{
			distanceVector = GeometricOperations.multiply(-distance, unitDistance);
		}

		Point3d newInitialPoint = GeometricOperations.plus(initialPoint, distanceVector);
		Point3d newFinalPoint = GeometricOperations.plus(finalPoint, distanceVector);
		/*
		 * fazendo uma gambiarra ...
		 */
		Point3d newInitialPoint1 = new Point3d(newInitialPoint.x, newInitialPoint.y, planoZ);
		Point3d newFinalPoint1 = new Point3d(newFinalPoint.x, newFinalPoint.y, planoZ);
//		System.out.println("Distance Vector " + distanceVector);
//		System.out.println("New Initial Point " + newInitialPoint);
//		System.out.println("New Final Point " + newFinalPoint);
		if ((GeometricOperations.roundNumber(newInitialPoint.x, 10) == GeometricOperations	.roundNumber(newFinalPoint.x, 10)) && (GeometricOperations.roundNumber(newInitialPoint.y, 10) == GeometricOperations.roundNumber(newFinalPoint.y, 10)))
		{
			return null;
		}
		else
		{
			LimitedLine lineParallel = new LimitedLine(newInitialPoint1, newFinalPoint1);
			return lineParallel;
		}
	}
	public LimitedArc parallelArc(LimitedArc arc, double distance, boolean inside)
	{
		Point3d initialPoint = new Point3d(arc.getInitialPoint().x,arc.getInitialPoint().y,planoZ);
		Point3d center = new Point3d(arc.getCenter().x,arc.getCenter().y,planoZ);
		boolean isBoss = !inside;
		Point3d newInitialPoint;
		LimitedArc newArc = null;
		if (arc.getDeltaAngle()<0)
		{
			if(!isBoss)
			{
				newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius()+distance),GeometricOperations.unitVector(center,initialPoint)));
				newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
				ArrayList<LimitedArc> arcs = new ArrayList<LimitedArc>();
				arcs.add(newArc);
			}
			else
			{
				if(arc.getRadius() > distance)
				{
					newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius()-distance),GeometricOperations.unitVector(center,initialPoint)));
					newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
				}
			}
//			showArcs(arcs);
			
		}
		else
		{
			if(!isBoss)
			{
				if(arc.getRadius() > distance)
				{
					newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius()-distance),GeometricOperations.unitVector(center,initialPoint)));
					newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
				}
			}
			else
			{
				newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius()+distance),GeometricOperations.unitVector(center,initialPoint)));
				newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
				ArrayList<LimitedArc> arcs = new ArrayList<LimitedArc>();
				arcs.add(newArc);
			}
		}
//		System.out.println("Arc from " + newArc.getInitialPoint() + " to " + newArc.getFinalPoint() + " center " + newArc.getCenter() + " delta " + newArc.getDeltaAngle()*180/Math.PI + " radius " + newArc.getRadius());
		return newArc;
	}
}
