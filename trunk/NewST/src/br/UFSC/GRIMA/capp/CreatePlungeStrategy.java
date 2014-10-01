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
	private double retractPlane; // retract plane eh recebido pelo 'caller'
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
		this.width.setModel(new SpinnerNumberModel(210.,0.1,null,1));
		this.angle.setModel(new SpinnerNumberModel(3.,0.1,179.9,1));

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
		double course; //distancia que devera ser percorrida pela ferramenta do ponto da ferramenta até ponto inicial
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
				course = high/Math.tan(angle);  // curso ferramenta (projetado em um plano de z)
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
					while ((cont>=0) && (voltas>=0)) //ADICIONANDO PATHS
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
			ArrayList<Path> widthPath = new ArrayList<Path>(); //path com os caminhos do width (desconsiderar eixo z). seu indice zero tem o caminho do primeiro path, e segue o ultimo, o caminho ate widthpoint
			// angle and width
//			Point3d zigzagFirstInitial = paths.get(0).getInitialPoint();
//			Point3d zigzagFirstFinal = paths.get(0).getFinalPoint();
//			double dist = zigzagFirstInitial.distance(zigzagFirstFinal);//distancia entre pontos do primeiro path
//			double deltaX= zigzagFirstFinal.x - zigzagFirstInitial.x; // decomposicao do vetor em x (positivo ou negativo)
//			double deltaY= zigzagFirstFinal.y - zigzagFirstInitial.y; // decomposicao do vetor em y (positivo ou negativo)
			course = high/Math.tan(angle);  // curso ferramenta (projetado em um plano de z); 
//			double temp = course;
			Point3d toolPoint,widthPoint; //ponto inicial da ferramenta , ponto onde o width foi limitado
			
//			long times = Math.round(Math.floor(course/width)); //numero de vezes que completa o width. (floor -> arredonda para baixo. retorna double) (round -> ARREDONDA pra inteiro)
			
		//funciona se for path linear
			double t = 0;
			double espaco=0;//quantidade que a ferramenta anda até o ultimo ponto onde troca de caminho
			int voltas=0;
			int cont = 0;
			System.out.println("curso total da ferramenta: " + course);
			while (width >= t) // vai encontrar até onde o width irá percorrer os caminhos
			{
				if(paths.get(cont).getClass().equals(LinearPath.class)) //se o caminho for reto
				{
					LinearPath pTmp = (LinearPath)paths.get(cont);							
					t = t + pTmp.getInitialPoint().distance(pTmp.getFinalPoint());
					if (width>=t)
						widthPath.add(new LinearPath(new Point3d(pTmp.getInitialPoint().x,pTmp.getInitialPoint().y,0),new Point3d(pTmp.getFinalPoint().x, pTmp.getFinalPoint().y, 0)));
				}
				else if(paths.get(cont).getClass().equals(CircularPath.class))//se caminho for circular
				{
					CircularPath cTmp = (CircularPath)paths.get(cont);
					t = t + cTmp.getAngulo()*cTmp.getRadius();
					if (width>=t)
						widthPath.add(new CircularPath(cTmp.getCenter(),cTmp.getInitialPoint(),cTmp.getFinalPoint(),cTmp.getAngulo()));
				}
				if (width >= t) //verificar se vai continuar no while. se sim, incrementa
				{	
					espaco=t; 
					if (cont == (paths.size()-1)) //se completou uma volta
					{
						cont = 0;
						voltas ++;
					}
					else
						cont ++;	
				}
			}//fim while
			double sobra = width - espaco; // espaco percorrido no ultimo caminho
			if (paths.get(cont).getClass().equals(LinearPath.class))
			{
				double dX= paths.get(cont).getFinalPoint().x - paths.get(cont).getInitialPoint().x; // decomposicao do vetor em x (positivo ou negativo)
				double dY= paths.get(cont).getFinalPoint().y - paths.get(cont).getInitialPoint().y; // decomposicao do vetor em y (positivo ou negativo)
				double ang = Math.atan2(dY, dX); // delta y/delta x=tan
				Point3d pontoI = new Point3d(paths.get(cont).getInitialPoint().x,paths.get(cont).getInitialPoint().y,0);
				widthPoint = new Point3d (GeometricOperations.roundNumber((pontoI.x + Math.cos(ang)*(sobra)),6), GeometricOperations.roundNumber((pontoI.y + Math.sin(ang)*(sobra)),6), 0 );
				System.out.println("\nwidthPoint: "+widthPoint); //CERTO
				widthPath.add(new LinearPath(pontoI,widthPoint));
			}
/**VERIFICAR! CIRCULAR 
		* FAZER PEGAR O PONTO WIDTH EM CASO CIRCULAR*/					
			else if (paths.get(cont).getClass().equals(CircularPath.class))
			{
				
				CircularPath circular = (CircularPath)paths.get(cont);
				Point3d pontoI = circular.getInitialPoint();
				Point3d pontoC = circular.getCenter(); 
				double theta = sobra/(circular.getRadius()); //angulo = arco/raio - angulo entre ponto inicial e o ponto ferramenta, com o centro do caminho 
				double alfa = Math.atan2((circular.getInitialPoint().y - pontoC.y), (circular.getInitialPoint().x - pontoC.x));// angulo entre centro e inicial, em relação a abscissa
				double xTool = pontoC.x + circular.getRadius() * Math.cos(theta+alfa); //raio * cos (alfa + theta)
				double yTool = pontoC.y + circular.getRadius() * Math.sin(theta+alfa); //raio * sen (alfa + theta)
				widthPoint = new Point3d(xTool, yTool, 0);
				widthPath.add(new CircularPath(pontoC, pontoI, widthPoint, theta));
			}
			else //General
			{	
				widthPoint = new Point3d(0,0,0);
			}
			
			//PRINTANDO WIDTH
			Point3d ptoInicial, ptoFinal, ptoCentro;
			double angulo;
			System.out.println("tamanho array widthPath: "+widthPath.size()+"\n");
			for (int i=0;i<widthPath.size();i++)
			{
				if (widthPath.get(i).getClass() == LinearPath.class)
				{
					ptoInicial = widthPath.get(i).getInitialPoint();
					System.out.println("Ponto inicial \t#" + (i+1) +": \t("+ GeometricOperations.roundNumber(ptoInicial.x,2) + ", " + GeometricOperations.roundNumber(ptoInicial.y,2) + ", " + GeometricOperations.roundNumber(ptoInicial.z,2)+")");
					ptoFinal = widthPath.get(i).getFinalPoint();
					System.out.println("Ponto final \t#" + (i+1) +": \t("+ GeometricOperations.roundNumber(ptoFinal.x, 2) + ", " + GeometricOperations.roundNumber(ptoFinal.y, 2) + ", " + GeometricOperations.roundNumber(ptoFinal.z, 2)+")");
				}
				else if(widthPath.get(i).getClass() == CircularPath.class)
				{
					CircularPath circularTemp = (CircularPath)widthPath.get(i);
					ptoInicial = circularTemp.getInitialPoint();
					System.out.println("\tPonto inicial \t#" + (i) +": \t("+ ptoInicial+")");
					ptoFinal = circularTemp.getFinalPoint();
					System.out.println("\tPonto final \t#" + (i) +": \t("+ ptoFinal.x + ", " + ptoFinal.y + ", " + ptoFinal.z+")");
					ptoCentro = circularTemp.getCenter();
					System.out.println("\tPonto central \t#" + (i) +": \t("+ ptoCentro.x + ", " + ptoCentro.y + ", " + ptoCentro.z+")");
					angulo = circularTemp.getAngulo();
					System.out.println("\tAngulo: \t"+Math.toDegrees(angulo)+" graus");
				}
			
			}
			
//CERTO				
			
//=====			
			
			
//ENCONTRANDO PONTO FERRAMENTA + CRIANDO CAMINHOS
			
//=======================
//				if (times%2==0) //se o numero de vezes que passou for par (resto2 == 0)
//				{

/***trajeto esta sendo escrito de tras pra frente. verificar
 * ARRUMAR O EIXO Z ! ! ! */
			
				int aux = 0;
				double sobra1=0;
				boolean flag = true; //true = avançando / false = voltando
				double alturaZ=paths.get(0).getInitialPoint().z;
				//diminui 'course' e faz os paths 
				while(course>=0) //SE AINDA HA CAMINHO A PERCORRER
				{
					System.out.println("\ncourse: " + course);
					System.out.println("sobra1: " + sobra1);
					if (widthPath.get(aux).getClass()==LinearPath.class) //SE EH LINEAR
					{
						double distTemp = widthPath.get(aux).getInitialPoint().distance(widthPath.get(aux).getFinalPoint());
						course = course - distTemp;//(paths.get(aux).getInitialPoint().distance(paths.get(aux).getFinalPoint()));
						alturaZ = alturaZ + (distTemp*Math.tan(angle));
						if (course>=0) // SE AINDA HA CAMINHO A PERCORRER, DEPOIS DA SUBTRACAO, DEVE ADICIONAR O TRAJETO
						{
							if(flag==true)//indo
								trajeto.add(new LinearPath (widthPath.get(aux).getInitialPoint(),widthPath.get(aux).getFinalPoint()));
							else		//voltando
								trajeto.add(new LinearPath (widthPath.get(aux).getFinalPoint(),widthPath.get(aux).getInitialPoint()));
						}
					}
					//CIRCULAR
					else if(widthPath.get(aux).getClass()==CircularPath.class)
					{
						CircularPath cTmp = (CircularPath)widthPath.get(aux);
						double distTemp = cTmp.getAngulo() * cTmp.getRadius();
						course = course - distTemp;
						alturaZ = alturaZ + (distTemp*Math.tan(angle));
						Point3d pontoF = new Point3d (cTmp.getInitialPoint().x, cTmp.getInitialPoint().y, alturaZ);//ponto final - recebe o INICIAL do PATHS, com alturaZ
						Point3d pontoC = new Point3d(cTmp.getCenter().x,cTmp.getCenter().y,(cTmp.getInitialPoint().z + pontoF.z)/2);// o z do centro eh a media entre o z do inicio e fim
						if (course>=0)
							trajeto.add(new CircularPath(pontoC, cTmp.getInitialPoint(), pontoF, cTmp.getAngulo()));//c, i, f, a
					}
					if (course>=0)//SE ADICIONOU O TRAJETO E VAI CONTINUAR NO WHILE
					{
						sobra1=course;//sobra1 serah a quantidade que a ferramenta anda apos o ultimo ponto onde troca de caminho
						double z=alturaZ;
						/**se esta no penultimo caminho do width*/
						if (aux == (widthPath.size()-2) && (flag==true)) //QUANDO PASSOU O PENULTIMO PATH, na ida, O ULTIMO EH ESSA EXCESSAO 
						{
							System.out.println("\ncourse: " + course);
							System.out.println("sobra1: " + sobra1);
							
							double distTemp = widthPath.get(aux).getFinalPoint().distance(widthPoint);
							course = course - distTemp;
							if (course>=0)
							{
								trajeto.add(new LinearPath (widthPath.get(aux).getFinalPoint(),widthPoint));
								sobra1=course;
							}
							
							System.out.println("\ncourse: " + course);
							System.out.println("sobra1: " + sobra1);
							
							flag = !flag;
							course = course - distTemp;
							if((course>=0))
							{
								trajeto.add(new LinearPath (widthPoint,widthPath.get(aux).getFinalPoint()));
								sobra1=course;
							}
							
							System.out.println("\ncourse: " + course);
							System.out.println("sobra1: " + sobra1);
							
						}
						else /**se nao esta INDO no penultimo caminho*/
						{
							if(aux==0)	/**se esta no primeiro caminho*/
							{
								if (flag==false)//voltando
								{	
									flag = !flag;
								}
								else//indo
									aux++;
							}
							else //nao eh primeiro nem INDO no penultimo
							{
								if (flag==true)//indo
									aux ++; //eh o contador pra saber quantos caminhos a ferramenta andou
								else //voltando
									aux--;
							}
/**OK*/			}
					}	
				}//fim WHILE
				double deltX= paths.get(aux).getFinalPoint().x - paths.get(aux).getInitialPoint().x; // decomposicao do vetor em x (positivo ou negativo)
				double deltY= paths.get(aux).getFinalPoint().y - paths.get(aux).getInitialPoint().y; // decomposicao do vetor em y (positivo ou negativo)
				double ang = Math.atan2(deltY, deltX); // delta y/delta x=tan
//				double ultimo = high/Math.tan(angle) - sobra1; //course ja foi reescrito, mas era high/tangente
				if (flag==true) //se estava indo
				{
					toolPoint = new Point3d (trajeto.get(aux).getInitialPoint().x + sobra1*Math.cos(ang), trajeto.get(aux).getInitialPoint().y + sobra1*Math.sin(ang), 0/*VAI SER 'retractPlane' QUANDO PRONTO*/);
					trajeto.add(new LinearPath(trajeto.get(aux).getInitialPoint(),toolPoint));
				}
				else //se estava voltando
				{
					toolPoint = new Point3d (trajeto.get(aux).getFinalPoint().x - sobra1*Math.cos(ang), trajeto.get(aux).getFinalPoint().y - sobra1*Math.sin(ang), 0/*VAI SER RETRACT PLANE QUANDO PRONTO*/);
					trajeto.add(new LinearPath(trajeto.get(aux).getFinalPoint(),toolPoint));
				}
				
				
/**ESTA GERANDO O TRAJETO CERTO, MAS COMO O Z AINDA NAO FOI TRATADO, AS DISTANCIAS DIVERGEM E PROPAGA UM ERRO*/
				//COMO O WIDTH NAO TEVE SEU EIXO Z TRATADO, HA UM PROBLEMA NA FORMULA DA DISTANCIA LOGO ACIMA, QUE DEVERIA SER EM X e Y APENAS
				
									
				
				
/**ESTÁ COM ERRO! VERIFICAR    _||_
 * 							   \  / 
 * 								\/	*/
				
				
//			if ((cont!=0) || (voltas!=0))
//			{
//				if (cont!=0)
//					cont--;
//				else   //se cont = 0
//				{
//					cont=(paths.size() -1);
//					voltas--;
//				}
//				Point3d pontoC;
//				double distTemp;	
//				System.out.println("cont: " + cont);
//				System.out.println("voltas: " + voltas);
//			
//				while ((cont>=0) && (voltas>=0)) //ADICIONANDO PATHS
//		 		{
//					Point3d pontoIni = new Point3d(paths.get(cont).getFinalPoint().x,paths.get(cont).getFinalPoint().y,alturaZ); //ponto inicial - recebe o FINAL do caminho PATHS, pois aqui ele esta voltando
//					if (paths.get(cont).getClass().equals(LinearPath.class)) //LINEAR
//					{
//						distTemp = (paths.get(cont).getInitialPoint().distance(paths.get(cont).getFinalPoint()));
//						alturaZ = alturaZ - (distTemp*Math.tan(angle));
//						Point3d pontoFin = new Point3d (paths.get(cont).getInitialPoint().x, paths.get(cont).getInitialPoint().y, alturaZ);//ponto final - recebe o INICIAL do PATHS
//						trajeto.add(new LinearPath(pontoIni,pontoFin));
//					}
//					else	//CIRCULAR
//					{
//						CircularPath circularTemp = (CircularPath)paths.get(cont);
//						
//						distTemp = circularTemp.getAngulo() * circularTemp.getRadius();
//						alturaZ = alturaZ - (distTemp*Math.tan(angle));
//						Point3d pontoFin = new Point3d (paths.get(cont).getInitialPoint().x, paths.get(cont).getInitialPoint().y, alturaZ);//ponto final - recebe o INICIAL do PATHS, com alturaZ
//						pontoC = new Point3d(circularTemp.getCenter().x,circularTemp.getCenter().y,(pontoIni.z + pontoFin.z)/2);// o z do centro eh a media entre o z do inicio e fim
//						trajeto.add(new CircularPath(pontoC, pontoIni, pontoFin, circularTemp.getAngulo()));//c, i, f, a
//					}
//					
//					if (cont>0)
//					{		
//						cont --;
//					}
//					else if (cont == 0) //se completou uma volta
//						{
//							cont = paths.size()-1;
//							voltas --;
//						}
//								
//					}
//			}
		
		}
		
		else if (a == 'h')
		{
			
		}
		
		int cont=-1;
		int i=0;
		Point3d pInicial,pFinal,pCentro;
		double angulo;
		System.out.println("\n\ntamanho array gerado = " + trajeto.size());
		for (i=0;i<trajeto.size();i++)
		{	cont++;

			if (trajeto.get(i).getClass() == LinearPath.class)
			{
				pInicial = trajeto.get(i).getInitialPoint();
				System.out.println("Ponto inicial \t#" + cont +": \t("+ GeometricOperations.roundNumber(pInicial.x,2) + ", " + GeometricOperations.roundNumber(pInicial.y,2) + ", " + GeometricOperations.roundNumber(pInicial.z,2)+")");
				pFinal = trajeto.get(i).getFinalPoint();
				System.out.println("Ponto final \t#" + cont +": \t("+ GeometricOperations.roundNumber(pFinal.x, 2) + ", " + GeometricOperations.roundNumber(pFinal.y, 2) + ", " + GeometricOperations.roundNumber(pFinal.z, 2)+")");
			}
			else if(trajeto.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTemp = (CircularPath)trajeto.get(i);
				pInicial = circularTemp.getInitialPoint();
				System.out.println("\tPonto inicial \t#" + cont +": \t("+ pInicial.x + ", " + pInicial.y + ", " + pInicial.z+")");
				pFinal = circularTemp.getFinalPoint();
				System.out.println("\tPonto final \t#" + cont +": \t("+ pFinal.x + ", " + pFinal.y + ", " + pFinal.z+")");
				pCentro = circularTemp.getCenter();
				System.out.println("\tPonto central \t#" + cont +": \t("+ pCentro.x + ", " + pCentro.y + ", " + pCentro.z+")");
				angulo = circularTemp.getAngulo();
				System.out.println("\tAngulo: \t"+Math.toDegrees(angulo)+" graus");
			}
		}
		
		return trajeto;
	}

}
