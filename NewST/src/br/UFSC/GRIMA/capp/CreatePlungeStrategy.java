package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.visual.PlungeFrame1;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.GeneralPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

/**
 * 
 * @author Honorio
 *
 */
public class CreatePlungeStrategy extends PlungeFrame1 implements ActionListener
{
	private ArrayList<Path> paths;
//	public ArrayList<Path> mergulho;
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
		this.setVisible(true);
		this.setTitle("Frame");
		this.width.setVisible(false);
		this.label2.setVisible(false);
		this.angle.setVisible(false);
		this.label3.setVisible(false);
		this.radius.setVisible(false);
		this.label4.setVisible(false);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();  
		if (source == button1)  //button1 = 'OK'
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
		this.width.setVisible(true);
		this.label2.setVisible(true);
		this.angle.setVisible(true);
		this.label3.setVisible(true);
		this.radius.setVisible(false);
		this.label4.setVisible(false);
	}

	private void funcaoBolaHelix() 
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/helix.png")));
		a = 'h';
		this.width.setVisible(false);
		this.label2.setVisible(false);
		this.angle.setVisible(true);
		this.label3.setVisible(true);
		this.radius.setVisible(true);
		this.label4.setVisible(true);
	}

	private void funcaoBolaRamp() 
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp.png")));
		a = 'r';
		this.width.setVisible(false);
		this.label2.setVisible(false);
		this.angle.setVisible(true);
		this.label3.setVisible(true);
		this.radius.setVisible(false);
		this.label4.setVisible(false);
	}
	
	private void funcaoBolaVert()
	{
		label1.setIcon(new ImageIcon(getClass().getResource("/images/vertical.png")));
		a = 'v';
		this.width.setVisible(false);
		this.label2.setVisible(false);
		this.angle.setVisible(false);
		this.label3.setVisible(false);
		this.radius.setVisible(false);
		this.label4.setVisible(false);
	}

	private void fechaJanela()
	{
		this.dispose();
	}

	private ArrayList<Path> calcularMergulho()
	{
		ArrayList<Path>	trajeto = new ArrayList<Path>();
		double width = ((Double)this.width.getValue());
		double angle = Math.toRadians((Double)this.angle.getValue());
		double high = (retractPlane - pInicial.z);
		double x0=pInicial.x;
		double x1=pFinal.x;
		double y0=pInicial.y;
		double y1=pFinal.y;
		double distP0 = Math.sqrt(Math.pow((x0 - x1),2) + Math.pow((y0 - y1),2) + Math.pow((pInicial.z - pFinal.z),2));  //distancia path
		double beta = Math.atan2((y1 - y0),(x1 - x0)); // beta = arco tangente* Y/X - * essa funçao especial trata o x=0
		double xk,yk,zk=retractPlane; 	//pontos de inicio da ferramenta 

		
/******** CASO VERTICAL *******/		
		if (a == 'v')
		{
			
		}

		
/******** CASO RAMPA *******/		
		else if (a == 'r') 
		{
			if (angle == 0)
			{
				System.out.println("Bad value for 'Angle'");
			}
			else
			{
				System.out.println("distP: " + distP0 );
				double course = high/Math.tan(angle);  // curso ferramenta (projetado em um plano de z)
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
				double temp = 0; // temporario que guarda a distancia entre os pontos ja passados
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
				double xTool; /* xk - linear */
				double yTool;/* yk - linear */
				Point3d p2Initial = paths.get(cont).getInitialPoint();
				Point3d p2Final = paths.get(cont).getFinalPoint();
				double beta2 = Math.atan2((p2Final.y - p2Initial.y),(p2Final.x - p2Initial.x)); // beta2 = arco tangente* Y/X relacao ao eixo x
				double distToolFinal = temp - course; //sera a distancia entre a ferramenta e o proximo ponto do PATH (nao importa se eh linear ou circular)
				double distPInicialFinal;
				double course2;
				if (paths.get(cont).getClass().equals(LinearPath.class))
				{
					
					distPInicialFinal = paths.get(cont).getInitialPoint().distance(paths.get(cont).getFinalPoint());//distancia entre os pontos inicial e final do caminho onde a ferramenta estara situada
					course2 = distPInicialFinal - distToolFinal; //distancia entre os pontos inicial e a ferramenta no caminho que a ferramenta começara
					if ((p2Final.x - p2Initial.x)>=0)
						xTool = p2Initial.x + Math.sqrt(Math.pow(course2, 2)/((Math.pow(Math.tan(beta2), 2))+1)); //x do pTool
					else
						xTool = p2Initial.x - Math.sqrt(Math.pow(course2, 2)/((Math.pow(Math.tan(beta2), 2))+1)); //x do pTool
					if (beta2 == Math.PI/2)
						yTool = p2Initial.y + course2;
					else if(beta2 == -Math.PI/2)
						yTool = p2Initial.y - course2;
					else
						yTool = ((xTool-p2Initial.x)*Math.tan(beta2))+p2Initial.y; //y do pTool
				}
				/* CASO EM QUE A FERRAMENTA PARA EM UM TRECHO CIRCULAR*/
				else
				{
					CircularPath pathCircular = (CircularPath)paths.get(cont); //precisa declarar o caminho como circular para poder usar o .getRadius e .getAngle
					Point3d centralPoint = pathCircular.getCenter(); 
					distPInicialFinal = pathCircular.getAngulo() * pathCircular.getRadius(); //dist = angulo * raio
					course2 = distPInicialFinal - distToolFinal; //distancia que a ferramenta percorre ate o ponto inicial
					double theta = course2/(pathCircular.getRadius()); //angulo = arco/raio - angulo entre ponto inicial e o ponto ferramenta, com o centro do caminho 
					double alfa = Math.atan2((pathCircular.getInitialPoint().y - centralPoint.y), (pathCircular.getInitialPoint().x - centralPoint.x));// angulo entre centro e inicial, em relação a abscissa
					xTool = pathCircular.getRadius() * Math.cos(theta+alfa); //raio * cos (alfa + theta)
					yTool = pathCircular.getRadius() * Math.sin(theta+alfa); //raio * sen (alfa + theta)
				}
				Point3d p2Tool = new Point3d(xTool,yTool,retractPlane); //inicio da ferramenta
				System.out.println("ponto ferramenta: " + p2Tool);
				System.out.println("beta: " + beta2);
				System.out.println("p2Final.x: "+p2Final.x +"\t\t p2Inicial.x: " + p2Initial.x);
				System.out.println("p2Final.y: "+ p2Final.y+"\t\t p2Inicial.y: " + p2Initial.y);
				System.out.println("ultimo Trecho: "+(cont+1)+"\nvoltas: "+voltas);
					
				Point3d pontoIni = paths.get(cont).getInitialPoint();
				Point3d pontoFin = paths.get(cont).getFinalPoint();
				Point3d pontoI = new Point3d(pontoIni.x, pontoIni.y, (retractPlane - Math.tan(angle)*course2)); //ponto final do primeiro path (descendo), inicial do ultimo caminho
	//PRIMEIRO TRECHO -- OBRIGATORIO
				if (paths.get(cont).getClass().equals(LinearPath.class)) //Se for linear
				{
					trajeto.add(new LinearPath(p2Tool,pontoI));
				}
				else if (paths.get(cont).getClass().equals(CircularPath.class)) //se for circular
				{
					CircularPath circularTemp = (CircularPath)paths.get(cont);
					Point3d pontoC = new Point3d(circularTemp.getCenter().x,circularTemp.getCenter().y,(retractPlane - pontoI.z)/2);// o z do centro eh a media entre o z do inicio e fim
					trajeto.add(new CircularPath(pontoC, p2Tool, pontoI, circularTemp.getAngulo()));//c, i, f, a - 
				}
				double alturaZ = pontoI.z;// armazena o ultimo valor de z, que vai ser usado a cada 'passo'. no momento esta recebendo o valor do ponto final do primeiro caminho
				if ((cont!=0) || (voltas!=0))
				{
					System.out.println("Entrou");
					if (cont!=0)
						cont--;
					else   //se cont = 0
					{
						cont=(paths.size() -1);
						voltas--;
					}
					Point3d pontoC;
					double distTemp;	
					System.out.println("cont: " + cont);
					System.out.println("voltas: " + voltas);
					while ((cont>=0) && (voltas>=0))
			 		{
						System.out.println("Entrou");
						pontoIni = new Point3d(paths.get(cont).getFinalPoint().x,paths.get(cont).getFinalPoint().y,alturaZ); //ponto inicial - recebe o FINAL do caminho PATHS, pois aqui ele esta voltando
						if (paths.get(cont).getClass().equals(LinearPath.class)) //LINEAR
						{
							distTemp = (paths.get(cont).getInitialPoint().distance(paths.get(cont).getFinalPoint()));
							alturaZ = alturaZ - (distTemp*Math.tan(angle));
							pontoFin = new Point3d (paths.get(cont).getInitialPoint().x, paths.get(cont).getInitialPoint().y, alturaZ);//ponto final - recebe o INICIAL do PATHS
							trajeto.add(new LinearPath(pontoIni,pontoFin));
						}
						else	//CIRCULAR
						{
							CircularPath circularTemp = (CircularPath)paths.get(cont);
							
							distTemp = circularTemp.getAngulo() * circularTemp.getRadius();
							alturaZ = alturaZ - (distTemp*Math.tan(angle));
							pontoFin = new Point3d (paths.get(cont).getInitialPoint().x, paths.get(cont).getInitialPoint().y, alturaZ);//ponto final - recebe o INICIAL do PATHS, com alturaZ
							pontoC = new Point3d(circularTemp.getCenter().x,circularTemp.getCenter().y,(pontoIni.z + pontoFin.z)/2);// o z do centro eh a media entre o z do inicio e fim
							trajeto.add(new CircularPath(pontoC, pontoIni, pontoFin, circularTemp.getAngulo()));//c, i, f, a
						}
						
						if (cont>0)
						{		
							cont --;
						}
						else if (cont == 0) //se completou uma volta
							{
								cont = paths.size()-1;
								voltas --;
							}
									
						}
				}
			}
		}


/******** CASO ZIGZAG *******/
		else if (a == 'z')
		{
			// angle and width
			Point3d zigzagFirstInitial = paths.get(0).getInitialPoint();
			Point3d zigzagFirstFinal
			= paths.get(0).getFinalPoint();
			double dist = zigzagFirstInitial.distance(zigzagFirstFinal);//distancia entre pontos do primeiro path
			double deltaX=zigzagFirstInitial.x - zigzagFirstFinal.x;
			if ( dist > width)
			{
				//Point3d zigzagLastPoint = new Point3d((zigzagFirstInitial.x+(deltaX/dist)),(),()); 
			}
		}
		
		else if (a == 'h')
		{
			
		}
		
		int cont=0;
		int i=0;
		Point3d pInicial,pFinal,pCentro;
		double angulo;
		System.out.println("tamanho array gerado = " + trajeto.size());
		for (i=0;i<trajeto.size();i++)
		{	cont++;
//DANDO ERRO NA LINHA ABAIXO		
			if (trajeto.get(i).getClass() == LinearPath.class)
			{
				pInicial = trajeto.get(i).getInitialPoint();
				System.out.println("Ponto inicial #" + cont +": "+ GeometricOperations.roundNumber(pInicial.x,2) + ", " + GeometricOperations.roundNumber(pInicial.y,2) + ", " + GeometricOperations.roundNumber(pInicial.z,2));
				pFinal = trajeto.get(i).getFinalPoint();
				System.out.println("Ponto final #" + cont +": "+ GeometricOperations.roundNumber(pFinal.x, 2) + ", " + GeometricOperations.roundNumber(pFinal.y, 2) + ", " + GeometricOperations.roundNumber(pFinal.z, 2));
			}
			else if(trajeto.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTemp = (CircularPath)trajeto.get(i);
				pInicial = circularTemp.getInitialPoint();
				System.out.println("\tPonto inicial #" + cont +": ("+ pInicial.x + ", " + pInicial.y + ", " + pInicial.z+")");
				pFinal = circularTemp.getFinalPoint();
				System.out.println("\tPonto final #" + cont +": ("+ pFinal.x + ", " + pFinal.y + ", " + pFinal.z+")");
				pCentro = circularTemp.getCenter();
				System.out.println("\tPonto central #" + cont +": ("+ pCentro.x + ", " + pCentro.y + ", " + pCentro.z+")");
				angulo = circularTemp.getAngulo();
				System.out.println("\tAngulo: "+Math.toDegrees(angulo)+"graus");
			}
		}
		
		return trajeto;
	}

}
