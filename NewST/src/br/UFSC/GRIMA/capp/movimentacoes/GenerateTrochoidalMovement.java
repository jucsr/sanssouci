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
	public ArrayList<Path> movimentacao;
	
	public GenerateTrochoidalMovement(ArrayList<LimitedElement> elements,double raio, double avanco)
	{
		this.elements = elements;
		this.raio = raio;
		this.avanco = avanco;
		
		for(int i = 0; i < elements.size(); i++){
			
			if(elements.get(i).isLimitedLine()){
				
				 LimitedLine temp = (LimitedLine)elements.get(i);
				 double distance = temp.getInitialPoint().distance(temp.getFinalPoint());
				 double Yinterseccao = Math.sqrt(Math.pow(this.raio, 2)- (Math.pow(this.avanco, 2))/4);
				 
				 Point3d initialPoint = new Point3d();
				 Point3d finalPoint = new Point3d(); 
				 Point3d center = new Point3d();
				 
//				 CircularPath arco = new CircularPath(center, initialPoint, finalPoint, angulo, sense);
				
			}else if(elements.get(i).isLimitedArc()){
				
			}
		}
		
	}
}
