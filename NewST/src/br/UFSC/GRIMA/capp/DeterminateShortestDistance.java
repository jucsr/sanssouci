package br.UFSC.GRIMA.capp;

import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Feature;
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
			Cavidade cavidade = (Cavidade)this.feature;
			for(int i = 0; i < cavidade.getItsBoss().size(); i++)
			{
				if(cavidade.getItsBoss().get(i).getClass() == CircularBoss.class)
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
								double distanceTmp = 0;
								if(cavidade.getGeometricalElements().get(k).getClass() == LimitedCircle.class)
								{
									LimitedCircle limCircleCavidadeTmp = (LimitedCircle)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.distanceVector(limitedCircleBossTmp.getCenter(), limCircleCavidadeTmp.getCenter()) - limitedCircleBossTmp.getRadius() - limCircleCavidadeTmp.getRadius();
//									System.out.println("distanceTmpCirc = " + distanceTmp);
								
								} else if(cavidade.getGeometricalElements().get(k).getClass() == LimitedLine.class)
								{
									LimitedLine limitedLineCavidadeTmp = (LimitedLine)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBetweenCircleAndLine(limitedLineCavidadeTmp, limitedCircleBossTmp, 50);
									System.out.println("distanceTmp = " + distanceTmp);
								} else if(cavidade.getGeometricalElements().get(k).getClass() == LimitedArc.class)
								{
									LimitedArc limitedArcCavidadeTmp = (LimitedArc)cavidade.getGeometricalElements().get(k);
									distanceTmp = OperationsVector.calculateShortestDistanceBeetweenArcAndCircle(limitedCircleBossTmp, limitedArcCavidadeTmp);
									System.out.println("distanceTmpARC = " + distanceTmp);

								}
								if (k==0)
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
				} //else if (cavidade.getItsBoss().get(i).getClass() == RectangularBoss.class)
			}
//			cavidade.getGeometricalElements();
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
