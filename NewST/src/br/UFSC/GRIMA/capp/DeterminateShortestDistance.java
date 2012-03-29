package br.UFSC.GRIMA.capp;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;


public class DeterminateShortestDistance 
{
	private Feature feature;
	public double shortestDistance;
	public DeterminateShortestDistance(Feature feature)
	{
		this.feature = feature;
		this.determinateShortestDistance();
	}
	private void determinateShortestDistance() 
	{
		this.shortestDistance = 0;
		if(feature.getClass() == Cavidade.class)
		{
			/*
			 * ======= distancia entre os boss e a cavidade
			 */
			Cavidade cavidade = (Cavidade)this.feature;
			double distanceTmp = 0;
			for(int i = 0; i < cavidade.getItsBoss().size(); i++)
			{
				Boss bossTmp = cavidade.getItsBoss().get(i);
				if(bossTmp.getClass() == CircularBoss.class)
				{
					CircularBoss circularBossTmp = (CircularBoss)cavidade.getItsBoss().get(i);
					for(int j = 0; j < circularBossTmp.getGeometricalElements().size(); j++)
					{
						// soh tem um elemento no array e ele eh do tipo LimitedCircle
						if(circularBossTmp.getGeometricalElements().get(j).getClass() == LimitedCircle.class)
						{
							LimitedCircle limitedCircleBossTmp = (LimitedCircle)circularBossTmp.getGeometricalElements().get(j);
							
							for(int k = 0; k < cavidade.getGeometricalElements().size(); k++)
							{
//								distanceTmp = 0;
								if(cavidade.getGeometricalElements().get(k).getClass() == LimitedCircle.class)
								{
									LimitedCircle limCircleCavidadeTmp = (LimitedCircle)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.distanceVector(limitedCircleBossTmp.getCenter(), limCircleCavidadeTmp.getCenter()) - limitedCircleBossTmp.getRadius() - limCircleCavidadeTmp.getRadius();
//									System.out.println("distanceTmpCirc = " + distanceTmp);
								
								} else if(cavidade.getGeometricalElements().get(k).getClass() == LimitedLine.class)
								{
									LimitedLine limitedLineCavidadeTmp = (LimitedLine)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBetweenCircleAndLine(limitedLineCavidadeTmp, limitedCircleBossTmp, 50);
//									System.out.println("distanceTmp = " + distanceTmp);
								} else if(cavidade.getGeometricalElements().get(k).getClass() == LimitedArc.class)
								{
									LimitedArc limitedArcCavidadeTmp = (LimitedArc)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBeetweenArcAndCircle(limitedCircleBossTmp, limitedArcCavidadeTmp);
//									System.out.println("distanceTmpARC = " + distanceTmp);

								}
								if (k==0 && j == 0 && i == 0)
								{
									this.shortestDistance = distanceTmp;
								}

								if (distanceTmp < this.shortestDistance)
								{
									this.shortestDistance = distanceTmp;
								}
							}
						} else if (circularBossTmp.getGeometricalElements().get(j).getClass() == LimitedLine.class)
						{
							// circularBoss naum tem elementos geometricos do tipo linha.
						}
					}
				} else if (bossTmp.getClass() == RectangularBoss.class)
				{
					RectangularBoss rectangularBossTmp = (RectangularBoss)bossTmp;
					for(int j = 0; j < rectangularBossTmp.getGeometricalElements().size(); j++)
					{
						if(rectangularBossTmp.getGeometricalElements().get(j).getClass() == LimitedLine.class)
						{
							LimitedLine limitedLineTmp = (LimitedLine)rectangularBossTmp.getGeometricalElements().get(j);
							for(int k = 0; k < cavidade.getGeometricalElements().size(); k++)
							{
								if(cavidade.getGeometricalElements().get(k).getClass() == LimitedLine.class)
								{
									LimitedLine limitedLineCavidadeTmp = (LimitedLine)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBetweenTwoLimitedCrossedLine(limitedLineTmp, limitedLineCavidadeTmp, 50);
//									System.out.println("distanceTmp = " + distanceTmp);
								} else if(cavidade.getGeometricalElements().get(k).getClass() == LimitedArc.class)
								{
									LimitedArc limitedArcCavidadeTmp = (LimitedArc)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBeetweenArcAndLine(limitedLineTmp, limitedArcCavidadeTmp);
//									System.out.println("distanceTmpARC = " + distanceTmp);

								}
							}
						} else if (rectangularBossTmp.getGeometricalElements().get(j).getClass() == LimitedArc.class)
						{
							
						}
					}
				}
			}
			
			/*
			 *  ============ distancia entre os circular boss ====================
			 */
			
			for(int i = 0; i < cavidade.getItsBoss().size() - 1; i++)
			{
				Boss bossTmp = cavidade.getItsBoss().get(i);
				if (bossTmp.getClass() == CircularBoss.class)
				{
					CircularBoss circularBossTmp = (CircularBoss)bossTmp;
//					System.out.println("NAME = " + circularBossTmp.getNome());
					
					for (int j = 0; j < cavidade.getItsBoss().size() - 1 - i; j++)
					{
						Boss bossTmp1 = cavidade.getItsBoss().get(i + j + 1);
//						System.out.println("name = " + bossTmp1.getNome());
						if(bossTmp1.getClass() == CircularBoss.class) //&& bossTmp != bossTmp1)
						{
							CircularBoss circularBossTmp1 = (CircularBoss)bossTmp1;
//							distanceTmp = circularBossTmp.X + circularBossTmp.getDiametro1() / 2 - circularBossTmp1.getDiametro1() / 2;
							distanceTmp = OperationsVector.distanceVector(circularBossTmp.getCentre(), circularBossTmp1.getCentre()) - circularBossTmp.getDiametro1() / 2 - circularBossTmp1.getDiametro1() / 2;
						}
//						System.err.println("DIST TMP = " + distanceTmp);
						if (distanceTmp < this.shortestDistance)
						{
							this.shortestDistance = distanceTmp;
						}
					}
					
				} else if (bossTmp.getClass() == RectangularBoss.class)
				{
					
				}
			}
		} 
		else if(this.feature.getClass() == Degrau.class)
		{
			
		}
	}
//	private double getShortestDistance()
//	{
//		
//	}
	public double getShortestDistance() {
		return shortestDistance;
	}
	public void setShortestDistance(double shortestDistance) {
		this.shortestDistance = shortestDistance;
	}
}
