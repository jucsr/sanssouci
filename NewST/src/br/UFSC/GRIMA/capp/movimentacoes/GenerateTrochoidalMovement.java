package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class GenerateTrochoidalMovement 
{
	private ArrayList<LimitedElement> elements;
	private double raio, avanco;
	public ArrayList<Path> movimentacao = new ArrayList<Path>();
	
	public GenerateTrochoidalMovement(ArrayList<LimitedElement> elements,double raio, double avanco)
	{
		this.elements = elements;
		this.raio = raio;
		this.avanco = avanco;
		
		for(int i = 0; i < elements.size(); i++){
			
			if(elements.get(i).isLimitedLine()){
				
				 LimitedLine temp = (LimitedLine)elements.get(i);
				 
				 double xInicalPoint;
				 double yInicalPoint; 
				 double xFinalPoint; 
				 double yFinalPoint;
				 double xCenter; 
				 double yCenter; 
				
				 double angR = Math.atan((temp.getFinalPoint().y-temp.getInitialPoint().y)/(temp.getFinalPoint().x-temp.getInitialPoint().x));
				 double angT = Math.acos((this.avanco/2)/(this.raio));
				 double distance = temp.getInitialPoint().distance(temp.getFinalPoint());
				 double movInt = Math.floor(distance/this.avanco);
				
				 System.out.println(" theta "+ angR);
				 System.out.println(" PI/2 "+ Math.PI/2);	
				
				 if(Math.abs(angR) == Math.PI/2){
					
					 double sentido = (temp.getFinalPoint().y - temp.getInitialPoint().y)/Math.sqrt(Math.pow(temp.getFinalPoint().y - temp.getInitialPoint().y, 2));
					 System.err.println("Err1");
					
					  xInicalPoint = temp.getInitialPoint().x + Math.sqrt(Math.pow(this.raio, 2)- Math.pow(sentido*this.avanco/2, 2));
					  yInicalPoint = (temp.getInitialPoint().y + this.avanco/2);
					  xFinalPoint = xInicalPoint;
					  yFinalPoint = yInicalPoint;
					  xCenter = temp.getInitialPoint().x - this.raio/2 ;
					  yCenter = temp.getInitialPoint().y ;
					
					  
					 Point3d initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
					 Point3d center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
					 
					 CircularPath arco = new CircularPath(center, initialPoint, initialPoint, 2*Math.PI,CircularPath.CCW );
					 arco.setRadius(this.raio);
					 arco.setInitialAngle(2*Math.PI);
					 arco.setFinalAngle(2*Math.PI);
					 movimentacao.add(arco);  
					 
					 int j = 0;
					while(j < movInt){
						
					
						yFinalPoint = yFinalPoint + sentido*this.avanco;
						yCenter = yCenter + sentido*this.avanco;
						
						
						
						Point3d finalPoint = new Point3d(xFinalPoint, yFinalPoint, temp.getFinalPoint().z);
						initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
						center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
						arco = new CircularPath(center, initialPoint, finalPoint,(2*Math.PI - 2*angT),CircularPath.CCW );
						 arco.setRadius(this.raio);
						 arco.setInitialAngle(2*Math.PI - angT - angR);
						 arco.setFinalAngle(2*Math.PI + angT + angR);
						 movimentacao.add(arco);
					    
					    yInicalPoint = yFinalPoint;
					    yFinalPoint = yFinalPoint +sentido*this.avanco;
						 
						 j++;
						 
					}
					
					if(distance - movInt != 0 ){
						
						double avancoFinal = distance - movInt;
						
						yInicalPoint = yInicalPoint - avancoFinal;
						yFinalPoint = yInicalPoint;
						yCenter = yCenter + avancoFinal;
						
						Point3d finalPoint = new Point3d(xFinalPoint, yFinalPoint, temp.getFinalPoint().z);
						initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
						center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
						arco = new CircularPath(center, initialPoint, finalPoint,2*Math.PI,CircularPath.CCW );
						 arco.setRadius(this.raio);
						 arco.setInitialAngle(2*Math.PI);
						 arco.setFinalAngle(2*Math.PI);
						 movimentacao.add(arco);		
					}
					
				 }else{
					 
					 double theta = angR;
					 
					 xCenter = temp.getInitialPoint().x + this.raio;
					 yCenter = temp.getInitialPoint().y;
					 xInicalPoint = temp.getInitialPoint().x + (Math.cos(theta)*this.raio);
					 yInicalPoint = Math.sqrt(Math.pow(this.raio, 2) + Math.pow((xInicalPoint - xCenter), 2)) + yCenter;
					 xFinalPoint = xInicalPoint;
					 yFinalPoint = yInicalPoint;
					 System.out.println("X "+(angR));
					 
					 Point3d initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
					 Point3d center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
					 
					 CircularPath arco = new CircularPath(center, initialPoint, initialPoint, 2*Math.PI,CircularPath.CCW );
					 arco.setRadius(this.raio);
					 arco.setInitialAngle(2*Math.PI);
					 arco.setFinalAngle(2*Math.PI);
					 movimentacao.add(arco);  
					 
					 
					 int t = 0;
					 while(t < movInt){
						 
						 xCenter = xCenter +  (Math.cos(theta)*this.avanco);
						 yCenter = yCenter + (Math.sin(theta)*this.avanco);
						 xFinalPoint = xFinalPoint + (Math.cos(theta)*this.avanco);
						 yFinalPoint = Math.sqrt(Math.pow(this.raio, 2) + Math.pow((xInicalPoint - xCenter), 2)) + yCenter;
						 xFinalPoint = xInicalPoint;
						 yFinalPoint = yInicalPoint;
						 
						 
						 initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
						 center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
						 
						 arco = new CircularPath(center, initialPoint, initialPoint, (2*Math.PI - 2*angT),CircularPath.CCW );
						 arco.setRadius(this.raio);
						 arco.setInitialAngle(2*Math.PI - angT - angR);
						 arco.setFinalAngle(2*Math.PI + angT + angR);
						 movimentacao.add(arco); 
						 
						 yInicalPoint = yFinalPoint;
						    yFinalPoint = yFinalPoint +this.avanco;
							 
							 t++;
						  }
					 if(distance - movInt != 0 ){
						 
						 	
						 double avancoFinal = distance - movInt;
						 
						 xCenter = xCenter +  (Math.cos(theta)*avancoFinal);
						 yCenter = yCenter + (Math.sin(theta)*avancoFinal);
						 xInicalPoint = xInicalPoint - (Math.cos(theta)*avancoFinal);
						 yInicalPoint = Math.sqrt(Math.pow(this.raio, 2) + Math.pow((xInicalPoint - xCenter), 2)) + yCenter;
						 xFinalPoint = xInicalPoint;
						 yFinalPoint = yInicalPoint;
						 
						 Point3d finalPoint = new Point3d(xFinalPoint, yFinalPoint, temp.getFinalPoint().z);
						 initialPoint = new Point3d(xInicalPoint, yInicalPoint, temp.getInitialPoint().z);
						 center = new Point3d(xCenter, yCenter, temp.getInitialPoint().z);
						 arco = new CircularPath(center, initialPoint, finalPoint,2*Math.PI,CircularPath.CCW );
						 arco.setRadius(this.raio);
						 arco.setInitialAngle(2*Math.PI);
						 arco.setFinalAngle(2*Math.PI);
						 movimentacao.add(arco);	
						 
					     }				 
					 }
				
		     }else if(elements.get(i).isLimitedArc()){
				
			}
		}
		
	}
}
