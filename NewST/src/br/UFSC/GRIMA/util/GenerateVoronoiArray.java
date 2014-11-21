package br.UFSC.GRIMA.util;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.movimentacoes.generatePath.GenerateContournParallel;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateVoronoiArray 
{
	private GeneralClosedPocket pocket;
	private ArrayList<LimitedElement> elementosCavidade;
	private ArrayList<ArrayList<LimitedElement>> elementosProtuberancia;
	private ArrayList<Point3d> voronoiPoints;
	
	public GenerateVoronoiArray(GeneralClosedPocket pocket)
	{
		this.pocket = pocket;
		this.elementosCavidade = GenerateContournParallel.gerarElementosDaCavidade(pocket, pocket.Z);
		this.elementosProtuberancia = GenerateContournParallel.gerarElementosDaProtuberancia(pocket, pocket.Z);
	}
	public ArrayList<Point3d> getVornoiPoints()
	{
		boolean thereIsBoss = false;
		ArrayList<ArrayList<Point3d>> malha = new ArrayList<ArrayList<Point3d>>();
		//Posicao da forma
		Point2D minorPointX = pocket.getPoints().get(0); // Menor X
		Point2D maxPointX = pocket.getPoints().get(0); // Maior Y
		Point2D minorPointY = pocket.getPoints().get(0); // Menor X
		Point2D maxPointY = pocket.getPoints().get(0); // Maior Y
		for (Point2D pointTmp : pocket.getPoints()) 
		{
			if (pointTmp.getX() < minorPointX.getX()) 
			{
				minorPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if (pointTmp.getX() > maxPointX.getX()) {
				maxPointX = new Point2D.Double(pointTmp.getX(), pointTmp.getY());
			}
			if (pointTmp.getY() < minorPointY.getY()) {
				minorPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if (pointTmp.getY() > maxPointY.getY()) {
				maxPointY = new Point2D.Double(pointTmp.getX(), pointTmp.getY());
			}
		}
		int numeroDePontos = 400;
		double deltaX = minorPointX.distance(maxPointX)/numeroDePontos;
		double deltaY = minorPointY.distance(maxPointY)/numeroDePontos;
		System.out.println("DeltaX: " + deltaX);
		System.out.println("DeltaY: " + deltaY);
		
		//CRIA O GENERAL PATH DO FORMATO
		final GeneralPath gp = (GeneralPath)Face.getShape(elementosCavidade);

		//CRIA UM Shape2D DA PROTUBERANCIA
		final ArrayList<Shape> bossShape = new ArrayList<Shape>();
		for(ArrayList<LimitedElement> bossTmp:elementosProtuberancia)
		{
			bossShape.add(Face.getShape(bossTmp));
		}
		
		//Linearizacao dos elementos da cavidade e da protuberancia
		ArrayList<LimitedElement> elementsPocketAndBoss = new ArrayList<LimitedElement>();
		for(LimitedElement tmp:elementosCavidade)
		{
			elementsPocketAndBoss.add(tmp);
		}
		//Verifica se ha protuberanica
		if(elementosProtuberancia != null)
		{
			if(elementosProtuberancia.size() != 0)
			{
				thereIsBoss = true;
				for(ArrayList<LimitedElement> arrayTmp:elementosProtuberancia)
				{
					for(LimitedElement elementTmp:arrayTmp)
					{	
						elementsPocketAndBoss.add(elementTmp);
					}
				}
			}
		}
		for(int i = 0; i < numeroDePontos; i++)
		{
			ArrayList<Point3d> arrayTmp = new ArrayList<Point3d>();
			for(int j = 0; j < numeroDePontos; j++)
			{
				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointY.getY() + deltaY*j);
				
				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
				{
					boolean contains = false;
					if(thereIsBoss)      //Se possui Protuberancia
					{
						for(Shape bossTmp:bossShape)
						{
							if(bossTmp.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
							{
								contains = true;
							}
						}
						if(!contains)
						{
							arrayTmp.add(new Point3d(pointTmp.getX(),pointTmp.getY(),GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),pocket.Z))));
						}
					}
					else //Se nao possui protuberancia
					{
//						//Calcula a menor distancia entre o ponto atual e o array da forma da cavidade
						arrayTmp.add(new Point3d(pointTmp.getX(),pointTmp.getY(),GeometricOperations.minimumDistance(elementsPocketAndBoss, new Point3d(pointTmp.getX(),pointTmp.getY(),pocket.Z))));
					}
				}
			}
			malha.add(arrayTmp);
		}
		voronoiPoints = getMaximum(malha);
		return voronoiPoints;
	}
	
	private ArrayList<Point3d> getMaximum(ArrayList<ArrayList<Point3d>> malha)
	{
		ArrayList<Point3d> max = new ArrayList<Point3d>();
//		ArrayList<Point3d> max = new ArrayList<Point3d>();
		System.out.println("X: " + malha.size());
		for(int i = 1;i < malha.size()-1;i++)
		{
			System.out.println("Y: " + malha.get(i).size());
			for(int j = 1;j < malha.get(i).size()-1;j++)
			{
				try
				{
					//Maximo na direcao x
					double zAnterior = malha.get(i).get(j-1).z;
					double zAtual = malha.get(i).get(j).z;
					double zPosterior = malha.get(i).get(j+1).z;
					
					if((zAtual >= zAnterior) &&(zAtual >= zPosterior))
					{
						//Maximo na direcao y
						zAnterior = malha.get(i-1).get(j).z;
						zPosterior = malha.get(i+1).get(j).z;
						if((zAtual >= zAnterior) &&(zAtual >= zPosterior))
						{
							max.add(malha.get(i).get(j));
						}
					}
				}
				catch(Exception e)
				{
//					break;
				}
			}
		}
		return max;
	}
	
	public ArrayList<LimitedElement> getPocketElements()
	{
		return elementosCavidade;
	}
	public ArrayList<ArrayList<LimitedElement>> getBossElements()
	{
		return elementosProtuberancia;
	}
	
	
	
}
