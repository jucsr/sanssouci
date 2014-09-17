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
	ArrayList<LimitedElement> elementosProtuberancia = new ArrayList<LimitedElement>();
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
			
			if(bossTmp.getClass() == CircularBoss.class)
			{
				CircularBoss tmp = (CircularBoss)bossTmp;

				LimitedArc arc = new LimitedArc(new Point3d(tmp.getCenter().x, tmp.getCenter().y, planoZ), new Point3d(tmp.getCenter().x + (tmp.getDiametro1()/2), tmp.getCenter().y, planoZ),  -2 * Math.PI); 
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
				LimitedLine l1 = new LimitedLine(GeometricOperations.pointPlusEscalar(position, "x", (l - tmp.getRadius())), GeometricOperations.pointPlusEscalar(position, "x", tmp.getRadius()));
				LimitedArc a1 = new LimitedArc(GeometricOperations.pointPlusEscalar(l1.getFinalPoint(), "y", tmp.getRadius()), l1.getFinalPoint(), -Math.PI / 2);
				LimitedLine l2 = new LimitedLine(a1.getFinalPoint(), GeometricOperations.pointPlusEscalar(a1.getFinalPoint(), "y", (c - 2 * tmp.getRadius())));
				LimitedArc a2 = new LimitedArc(GeometricOperations.pointPlusEscalar(l2.getFinalPoint(), "x", tmp.getRadius()), l2.getFinalPoint(), -Math.PI / 2);
				LimitedLine l3 = new LimitedLine(a2.getFinalPoint(),GeometricOperations.pointPlusEscalar(a2.getFinalPoint(), "x", (l - 2 * tmp.getRadius())));
				LimitedArc a3 = new LimitedArc(GeometricOperations.pointPlusEscalar(l3.getFinalPoint(), "y", -tmp.getRadius()), l3.getFinalPoint(), -Math.PI / 2);
				LimitedLine l4 = new LimitedLine(a3.getFinalPoint(), GeometricOperations.pointPlusEscalar(a3.getFinalPoint(), "y", -(c - 2 * tmp.getRadius())));
				LimitedArc a4 = new LimitedArc(GeometricOperations.pointPlusEscalar(l4.getFinalPoint(), "x", -tmp.getRadius()), l4.getFinalPoint(), -Math.PI / 2);
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
//		System.out.println("=====formaOriginal ======");
//		GeometricOperations.showElements(formaOriginal);		
//		System.out.println("=====EndformaOriginal ======");
	}

	public ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallelPath()
	{
		double percentagem = 0.75;
		ArrayList<ArrayList<ArrayList<LimitedElement>>> multipleParallel = new ArrayList<ArrayList<ArrayList<LimitedElement>>>();
		
		ArrayList<ArrayList<LimitedElement>> parallelPath = parallelPath2(pocket, distance, planoZ);

		int aux = 1;
		double distanceAtualizada = 2*distance*percentagem;

		while (parallelPath != null)
		{
			multipleParallel.add(parallelPath);
			parallelPath = parallelPath2(pocket, distance + (aux*distanceAtualizada), planoZ);
			aux++;
		}	
		
		for(int i = 0; i < multipleParallel.size(); i++)
		{
			for(int j = 0; j < multipleParallel.get(i).size(); j++)
			{
//				System.err.println("=====formaOriginal ======");
//				GeometricOperations.showElements(multipleParallel.get(i).get(j));		
//				System.err.println("=====EndformaOriginal ======");
			}
		}
		return multipleParallel;
	}
	public ArrayList<ArrayList<LimitedElement>> parallelPath2 (GeneralClosedPocket pocket, double distance, double planoZ)
	{
		boolean inside = true;
		boolean isBoss = true;
		ArrayList<ArrayList<LimitedElement>> saida = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> parallelTemp1 = new ArrayList<LimitedElement>();      		//Paralela dos elementos da protuberancia
		ArrayList<LimitedElement> parallelTemp2 = new ArrayList<LimitedElement>();              //Paralela dos elementos da cavidade
		ArrayList<LimitedElement> totalParallel = new ArrayList<LimitedElement>();              //Array com todas as paralelas

		
		parallelTemp1 = parallelPath1(elementosProtuberancia, distance, !inside,isBoss);
		GeometricOperations.showElements(parallelTemp1);
		parallelTemp2 = parallelPath1(elementsCavidade, distance, inside,!isBoss);
		
		for(LimitedElement tmp:parallelTemp1)
		{
			totalParallel.add(tmp);
		}
		for(LimitedElement tmp:parallelTemp2)
		{
			totalParallel.add(tmp);
		}
		
		saida = validarPath(totalParallel, formaOriginal, distance);
		
		return saida;
	}
	public ArrayList<LimitedElement> parallelPath1 (ArrayList<LimitedElement> elements, double distance, boolean inside,boolean isBoss)
	{
		ArrayList<LimitedElement> lacoTmp = new ArrayList<LimitedElement>();
		for(int j = 0; j < elements.size();j++)
		{
			if(elements.get(j).isLimitedLine())
			{
				LimitedLine lineTmp = (LimitedLine)elements.get(j);
				LimitedLine newLine = absoluteParallel(lineTmp, distance, inside);

				if(newLine != null)
				{
					lacoTmp.add(newLine);
				}
			} 
			else if(elements.get(j).isLimitedArc())
			{
				LimitedArc arcTmp = (LimitedArc)elements.get(j);
				LimitedArc newArc;
				if (arcTmp.getRadius() == 0)
				{
					Point3d center = arcTmp.getInitialPoint();
					Point3d pI = lacoTmp.get(j - 1).getFinalPoint();
					newArc = new LimitedArc(center, pI, Math.PI / 2);
				}
				else
				{
					newArc = parallelArc(arcTmp, distance, inside,isBoss);
				}
				if(newArc != null)
				{
					lacoTmp.add(newArc);
				}
			}
		}
		return lacoTmp;
	}
	public static ArrayList<ArrayList<LimitedElement>> validarPath(ArrayList<LimitedElement> elements, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		ArrayList<ArrayList<LimitedElement>> elementsValidated = new ArrayList<ArrayList<LimitedElement>>();
		ArrayList<LimitedElement> elementsIntermediario = validar1Path(elements);
		ArrayList<LimitedElement> elementsIntermediario2 = validar2Path(elementsIntermediario,formaOriginal,distance);
		ArrayList<ArrayList<LimitedElement>> elementsIntermediario3 = validar3Path(elementsIntermediario2);
		if(elementsIntermediario3 != null)
		{
			for (int j = 0; j < elementsIntermediario3.size(); j++)
			{
				elementsValidated.add(elementsIntermediario3.get(j));					
			}
		}
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
		/**
		 * 	Validacao 1: Quebra dos Elementos na intersecao
		 */
		ArrayList<LimitedElement> elementsIntermediario = new ArrayList<LimitedElement>();
		
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
				if(!ei.equals(ej))
				{
					intersectionTemp = GeometricOperations.intersectionElements(ei, ej);
					if (intersectionTemp != null)
					{
						thereIsIntersection = true;
						if(firstIntersection)
						{
							intersection = new ArrayList<Point3d>();
							firstIntersection = false;
						}
						for(int k = 0;k < intersectionTemp.size();k++)
						{
							intersection.add(intersectionTemp.get(k));
						}
					}
				}
			}
			if(intersection != null)
			{
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
					ArrayList<LimitedArc> arcTemp = GeometricOperations.quebraArco(arci,intersection); 
					for(int k = 0;k < arcTemp.size();k++)
					{
						if(!GeometricOperations.isTheSamePoint(arcTemp.get(k).getInitialPoint(), arcTemp.get(k).getFinalPoint()))
						{
							elementsIntermediario.add(arcTemp.get(k));
						}
					}
				}
			}
//			if(thereIsIntersection == false)
			else
			{
				if(ei.isLimitedLine() && !GeometricOperations.isTheSamePoint(ei.getInitialPoint(), ei.getFinalPoint()))
				{
					elementsIntermediario.add(ei);
				}
				else if(ei.isLimitedArc() && GeometricOperations.roundNumber(((LimitedArc)ei).getDeltaAngle(),10) != 0)
				{
					System.out.println("Arco: " + ei.getInitialPoint());
					elementsIntermediario.add(ei);
				}
			}
		}
		return elementsIntermediario;
	}
	public static ArrayList<LimitedElement> validar2Path(ArrayList<LimitedElement> elementsIntermediario, ArrayList<LimitedElement> formaOriginal, double distance)
	{
		/**
		 * 	Validacao 2: Elementos com a minima distancia (em relacao a forma original) menor que a distancia de offset, sao descartados 
		 */
		ArrayList<LimitedElement> elementsIntermediario2 = new ArrayList<LimitedElement>();
		for(int i = 0; i< elementsIntermediario.size();i++)
		{
			LimitedElement ei0 = elementsIntermediario.get(i);
			if(GeometricOperations.roundNumber(GeometricOperations.minimumDistance(formaOriginal, ei0),5) >= distance)
			{
				elementsIntermediario2.add(ei0);
			}
		}
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
			Iterator iter = elementsIntermediario2.iterator();
			while(iter.hasNext())
			{
				boolean hasNoFinalPoint = true;
				ei0 = ei0new;
				Point3d ei0I = ei0.getInitialPoint();
				Point3d ei0F = ei0.getFinalPoint();
				elementsValidated.get(numeroDeLacos).add(ei0);
				for(int j = 0; j < elementsIntermediario2.size(); j++)
				{
					LimitedElement ej = elementsIntermediario2.get(j);
					if(!(alreadyPassed))
					{
						initialPoint = ei0I;
						alreadyPassed = true;
					}
					Point3d ejI = ej.getInitialPoint();
					if(GeometricOperations.isTheSamePoint(ei0F,ejI))
					{
						ei0new = elementsIntermediario2.get(j);
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
			}
			return elementsValidated;
		}
	}
	public LimitedLine absoluteParallel(LimitedLine line, double distance, boolean inside)
	{
		Point3d initialPoint = new Point3d(line.getInitialPoint().x, line.getInitialPoint().y, 0);
		Point3d finalPoint = new Point3d(line.getFinalPoint().x, line.getFinalPoint().y, 0);
		double angleLine = GeometricOperations.angle(GeometricOperations.minus(initialPoint, finalPoint));
		double newDistanceAngle = angleLine + Math.PI / 2;
		double x = GeometricOperations.roundNumber(Math.cos(newDistanceAngle),10);
		double y = GeometricOperations.roundNumber(Math.sin(newDistanceAngle),10);
		//Cuidado com o Z
		Point3d unitDistance = new Point3d(x, y, line.getInitialPoint().z);
		Point3d distanceVector;
		if(!inside) // ========= Nao entendi ====== 
		{
			distanceVector = GeometricOperations.multiply(distance, unitDistance);		
			System.out.println("Distance: " + distance);
			System.out.println("unitDistance: " + unitDistance);
		}
		else
		{
			distanceVector = GeometricOperations.multiply(-distance, unitDistance);
		}
		System.out.println("distanceVector: " + distanceVector);
		//Analisar pq o plus nao esta somando...
		Point3d newInitialPoint = GeometricOperations.plus(initialPoint, distanceVector);
		Point3d newFinalPoint = GeometricOperations.plus(finalPoint, distanceVector);
		System.out.println("PI: " + newInitialPoint);
		System.out.println("PF: " + newFinalPoint);
		System.out.println("nPI: " + newInitialPoint);
		System.out.println("nPF: " + newFinalPoint);

		/**
		 *  ======= fazendo uma gambiarra ...
		 */
		Point3d newInitialPoint1 = new Point3d(newInitialPoint.x, newInitialPoint.y, planoZ);
		Point3d newFinalPoint1 = new Point3d(newFinalPoint.x, newFinalPoint.y, planoZ);
		if ((GeometricOperations.roundNumber(newInitialPoint.x, 10) == GeometricOperations.roundNumber(newFinalPoint.x, 10)) && (GeometricOperations.roundNumber(newInitialPoint.y, 10) == GeometricOperations.roundNumber(newFinalPoint.y, 10)))
		{
			return null;
		}
		else
		{
			LimitedLine lineParallel = new LimitedLine(newInitialPoint1, newFinalPoint1);
			return lineParallel;
		}
	}
//	public LimitedArc parallelArc(LimitedArc arc, double distance, boolean inside)
//	{
	public static LimitedArc parallelArc(LimitedArc arc, double distance, boolean inside, boolean isBoss)
	{
			double newRadius;
			if (arc.getDeltaAngle()<0) //Sentido horario
			{
				if(isBoss) //Se for boss
				{
					newRadius = arc.getRadius()+distance; //o novo raio sera o antigo mais a distancia da paralela
				}
				else //Se nao for boss
				{
					if(inside) //Se for para dentro
					{
						newRadius = arc.getRadius()+distance;
					}
					else //Se for para fora
					{
						newRadius = arc.getRadius()-distance;
					}
				}
			}
			else //Sentido anti-horario
			{
				if(isBoss) //Se for boss
				{
					newRadius = arc.getRadius()+distance; //o novo raio sera o antigo mais a distancia da paralela
				}
				else //Se nao for boss
				{
					if(inside) //Se for para dentro
					{
						newRadius = arc.getRadius()-distance;
					}
					else //Se for para fora
					{
						newRadius = arc.getRadius()+distance;
					}
				}
			}
			
//			System.out.println("RADIUS: " + newRadius);
			return (new LimitedArc(arc.getCenter(), GeometricOperations.plus(arc.getCenter(),GeometricOperations.multiply(newRadius,GeometricOperations.unitVector(arc.getCenter(),arc.getInitialPoint()))), arc.getDeltaAngle()));
//		Point3d initialPoint = new Point3d(arc.getInitialPoint().x, arc.getInitialPoint().y, planoZ);
//		Point3d center = new Point3d(arc.getCenter().x, arc.getCenter().y, planoZ);
//		boolean isBoss = !inside;
//		Point3d newInitialPoint;
//		LimitedArc newArc = null;
//		if (arc.getDeltaAngle() < 0)
//		{
//			if(!isBoss)
//			{
//				newInitialPoint = GeometricOperations.plus(center, GeometricOperations.multiply((arc.getRadius() + distance), GeometricOperations.unitVector(center, initialPoint)));
//				newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
//			}
//			else
//			{
//				newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius()+distance),GeometricOperations.unitVector(center, initialPoint)));
//				newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
//			}
//		}
//		else
//		{
////			if(!isBoss)
////			{
//				if(arc.getRadius() > distance)
//				{
//					newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius() - distance),GeometricOperations.unitVector(center, initialPoint)));
//					newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
//				}
////			}
////			else
////			{
////				newInitialPoint = GeometricOperations.plus(center,GeometricOperations.multiply((arc.getRadius() + distance),GeometricOperations.unitVector(center, initialPoint)));
////				newArc = new LimitedArc(center, newInitialPoint, arc.getDeltaAngle());
////			}
//		}
//		return newArc;
	}
}
