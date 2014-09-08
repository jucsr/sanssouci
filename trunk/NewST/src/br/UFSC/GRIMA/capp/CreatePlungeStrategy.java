package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.visual.PlungeFrame1;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.GeneralPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

/**
 * 
 * @author Honorio
 *
 */
public class CreatePlungeStrategy extends PlungeFrame1 implements ActionListener
{
	private ArrayList<Path> paths;
	private double retractPlane;
	private char a;
	Point3d pInicial;
	Point3d pFinal;
	Point3d pTool; //Ponto de inicio da ferramenta
	
	public CreatePlungeStrategy(ArrayList<Path> paths, double retractPlane)
	{
		this.paths = paths;
		pInicial = paths.get(0).getInitialPoint();
		pFinal = paths.get(0).getFinalPoint();
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.bolaVert.addActionListener(this);
		this.bolaRamp.addActionListener(this);
		this.bolaHelix.addActionListener(this);
		this.bolaZigzag.addActionListener(this);
		this.retractPlane = retractPlane;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();  
		if (source == button1)
		{
			this.calcularMergulho();
		} 
		else if (source == button2)
		{
			this.fechaJanela();
		}
		else if (source == bolaRamp)
		{
			this.funcaoBolaRamp();
		}
		else if (source == bolaVert)
		{
			this.funcaoBolaVert();
		}
		else if (source == bolaHelix)
		{
			this.funcaoBolaHelix();
		}
		else if (source == bolaZigzag)
		{
			this.funcaoBolaZigzag();
		}
	}

	private void funcaoBolaZigzag() 
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/zigzag.png")));
		a = 'z';
	}

	private void funcaoBolaHelix() 
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/helix.png")));
		a = 'h';
	}

	private void funcaoBolaRamp() 
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp.png")));
		a = 'r';
	}
	
	private void funcaoBolaVert()
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/vertical.png")));
		a = 'v';
	}

	private void fechaJanela()
	{
		this.dispose();
	}

	private void calcularMergulho()
	{
		double width = ((Double)this.width.getValue());
		double angle = Math.toRadians((Double)this.angle.getValue());
		double high = (retractPlane - pInicial.z);
		double x0=pInicial.x;
		double x1=pFinal.x;
		double y0=pInicial.y;
		double y1=pFinal.y;
		double distP0 = Math.sqrt(Math.pow((x0 - x1),2) + Math.pow((y0 - y1),2) + Math.pow((pInicial.z - pFinal.z),2));  //distancia path
		double beta = Math.atan2((y1 - y0),(x1 - x0)); // beta = arco tangente* Y/X - * essa fun�ao especial trata o x=0
		double xk,yk,zk=retractPlane; 	//pontos de inicio da ferramenta 
		if (a == 'v')
		{
		
		}
// CASO RAMPA ~~		
		else if (a == 'r') 
		{
			System.out.println("distP: " + distP0 );
			double course = high/Math.tan(angle);  // curso ferramenta
			System.out.println("course: " + course );
				double perimetro=0;
				for (int i=0;i<paths.size();i++) //calcular perimetro
				{
					Path pathTmp = paths.get(i);
					if(pathTmp.getClass().equals(LinearPath.class))
						perimetro =+ pathTmp.getInitialPoint().distance(pathTmp.getFinalPoint());// simbolo =+ acumulap
					else if(pathTmp.getClass() == CircularPath.class)
					{
						CircularPath circularTmp = (CircularPath)pathTmp;
						perimetro =+ circularTmp.getAngulo() * circularTmp.getRadius();
					}
					else if(pathTmp.getClass() == GeneralPath.class)
					{
						
					}
				}	
					double temp = 0;
					int voltas=0, cont=0;				
					while (course >= temp)
					{
						if(paths.get(cont).getClass().equals(LinearPath.class)) //se o caminho for reto
						{
							LinearPath pTmp = (LinearPath)paths.get(cont);							
							temp = temp + pTmp.getInitialPoint().distance(pTmp.getFinalPoint());
						}
						else if(paths.get(cont).getClass().equals(CircularPath.class))//se caminho for circular
						{
							CircularPath cTmp = (CircularPath)paths.get(cont);
							temp = temp + cTmp.getAngulo()*cTmp.getRadius();
						}
						if (course >= temp) //verificar se vai continuar no while. se sim, incrementa
						{						
							if (cont == paths.size()-1) //se completou uma volta
							{
								cont = 0;
								voltas ++;
							}
							else
								cont ++;	
						}
					}
				/* CASO EM QUE A FERRAMENTA PARA EM UM TRECHO LINEAR*/
					double xk2; /* xk - linear */
					double yk2;/* yk - linear */
					Point3d p2Initial = paths.get(cont).getInitialPoint();
					Point3d p2Final = paths.get(cont).getFinalPoint();
					double beta2 = Math.atan2((p2Final.y - p2Initial.y),(p2Final.x - p2Initial.x)); // beta2 = arco tangente* Y/X relacao ao eixo x
					double distToolFinal = temp - course; //sera a distancia entre a ferramenta e o proximo ponto do PATH (nao importa se eh linear ou circular)
					double distPInicialFinal;
					double course2;
					if (paths.get(cont).getClass().equals(LinearPath.class))
					{
						
						distPInicialFinal = paths.get(cont).getInitialPoint().distance(paths.get(cont).getFinalPoint());//distancia entre os pontos inicial e final do caminho onde a ferramenta estara situada
						course2 = distPInicialFinal - distToolFinal; //distancia entre os pontos inicial e a ferramenta no caminho que a ferramenta come�ara
						if ((p2Final.x - p2Initial.x)>=0)
							xk2 = p2Initial.x + Math.sqrt(Math.pow(course2, 2)/((Math.pow(Math.tan(beta2), 2))+1)); //x do pTool
						else
							xk2 = p2Initial.x - Math.sqrt(Math.pow(course2, 2)/((Math.pow(Math.tan(beta2), 2))+1)); //x do pTool
						if (beta2 == Math.PI/2)
							yk2 = p2Initial.y + course2;
						else if(beta2 == -Math.PI/2)
							yk2 = p2Initial.y - course2;
						else
							yk2 = ((xk2-p2Initial.x)*Math.tan(beta2))+p2Initial.y; //y do pTool
					}
					/* CASO EM QUE A FERRAMENTA PARA EM UM TRECHO CIRCULAR*/
					else
					{
						CircularPath pathCircular = (CircularPath)paths.get(cont); //precisa declarar o caminho como circular para poder usar o .getRadius e .getAngle
						Point3d centralPoint = pathCircular.getCenter(); 
						distPInicialFinal = pathCircular.getAngulo() * pathCircular.getRadius(); //dist = angulo * raio
						course2 = distPInicialFinal - distToolFinal; //distancia que a ferramenta percorre ate o ponto inicial
						double crossedAngle = course2/(pathCircular.getRadius()); //angulo = arco/raio - angulo entre ponto inicial e o ponto ferramenta, com o centro do caminho 
/*TERMINAR A PARTE CIRCULAR - OLHAR DESENHO*/
						double angleInitialCenter= Math.atan2((centralPoint.y - p2Initial.y),(centralPoint.x - p2Initial.x)); //angulo, em rela��o ao eixo x, entre ponto inicial e centro
						
						xk2=1879423; //valor qqr
						yk2=4702193;	//valor teste
					}
					Point3d p2Tool = new Point3d(xk2,yk2,retractPlane);
					System.out.println("ponto ferramenta: " + p2Tool);
					System.out.println("beta: " + beta2);
					System.out.println("p2Final.x e p2Inicial.x " + p2Final.x + p2Initial.x);
					System.out.println("p2Final.y e p2Inicial.y " + p2Final.y + p2Initial.y);
					System.out.println("ultimo Trecho: "+(cont+1)+"\nvoltas: "+voltas);
		}
		else if (a == 'z')
		{
			
		}
		else if (a == 'h')
		{
			
		}
	}
}
