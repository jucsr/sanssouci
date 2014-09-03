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
	private void generatePaths()
	{
		for(LimitedElement elementTmp : this.elements)
		{
			if(elementTmp.isLimitedLine())
			{
				this.generatePathsInLimitedLineBase((LimitedLine)elementTmp);
			} else if(elementTmp.isLimitedArc())
			{
				this.generatePathsInLimitedArcBase((LimitedArc)elementTmp);
			}
		}
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
			CircularPath circuloTmp = new CircularPath(centroTmp, pontoInicialTmp, pontoInicialTmp, 2 * Math.PI, CircularPath.CCW);
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
		CircularPath circuloTmp = new CircularPath(centroTmp, pontoInicialTmp, pontoInicialTmp, -2 * Math.PI, CircularPath.CW);
		paths.add(circuloTmp);
		
		return paths;
	}
	private ArrayList<Path> generatePathsInLimitedArcBase(LimitedArc arc)
	{
//		System.out.println("arc = " + arc.getCenter());
//		ArrayList<Path> saida = new ArrayList<Path>();
		double norma = Math.abs(arc.getDeltaAngle() * arc.getRadius());
		double initialAngle = Math.atan2(arc.getInitialPoint().y - arc.getCenter().y, arc.getInitialPoint().x - arc.getCenter().x); 
		double deltaAcumulado = 0;
		int fracionamento = (int)(norma / avanco) + 1;
		double deltaTmp = avanco / arc.getRadius();
//		System.out.println("fracionamento = " + fracionamento);
//		System.out.println("norma = " + norma);
		for(int i = 0; i < fracionamento; i++)
		{
//			for(int i = 0; i * deltaAcumulado * arc.getRadius() < norma; i++)

			double xBase = arc.getCenter().x + arc.getRadius() * Math.cos(initialAngle + deltaAcumulado);
			double yBase = arc.getCenter().y + arc.getRadius() * Math.sin(initialAngle + deltaAcumulado);
			Point3d centroBaseTmp = new Point3d(xBase, yBase, arc.getCenter().z);
			
			Point3d vetorUnitarioTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseTmp);
			Point3d pontoInicialTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioTmp).y, arc.getCenter().z + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioTmp).z);
			CircularPath circularTmp = new CircularPath(centroBaseTmp, pontoInicialTmp, pontoInicialTmp, -2 * Math.PI, CircularPath.CCW);
			paths.add(circularTmp);
			
			double xBaseProxima;
			double yBaseProxima;
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
			}
			
			Point3d centroBaseProximaTmp = new Point3d(xBaseProxima, yBaseProxima, arc.getCenter().z);
			Point3d vetorUnitarioProximaTmp = GeometricOperations.unitVector(arc.getCenter(), centroBaseProximaTmp);
			Point3d pontoFinalTmp = new Point3d(arc.getCenter().x + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioProximaTmp).x, arc.getCenter().y + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioProximaTmp).y, arc.getCenter().z + GeometricOperations.multiply(arc.getRadius() + radius, vetorUnitarioProximaTmp).z);
			
//			CircularPath arcTmp = new CircularPath(pontoInicialTmp, pontoFinalTmp, arc.getCenter(), sense);
			double deltaAnguloTmp = avanco / (arc.getRadius()); 
			double normaAcumulada = Math.abs((i + 1) * deltaTmp * arc.getRadius());
			double normaAcumulada1 = Math.abs(i * deltaTmp * arc.getRadius());
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
			if(arc.getDeltaAngle() > 0)
			{
				deltaAcumulado = deltaAcumulado + avanco / arc.getRadius();
			} else
			{
				deltaAcumulado = deltaAcumulado - avanco / arc.getRadius();
			}
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
