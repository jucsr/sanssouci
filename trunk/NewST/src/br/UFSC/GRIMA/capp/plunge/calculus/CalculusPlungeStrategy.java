package br.UFSC.GRIMA.capp.plunge.calculus;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideMilling;
import br.UFSC.GRIMA.capp.plunge.PlungeRamp;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.capp.plunge.PlungeZigzag;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class CalculusPlungeStrategy
{

	private Workingstep workingStep;
	private ArrayList<Path> trajeto;
	private ArrayList<Path> trajetoEntrada;
	private double separacao = 2;
	private PlungeStrategy plungeType;
	private boolean ok = true;

	
	public CalculusPlungeStrategy(ArrayList<Path> trajetoEntrada, Workingstep workingStep)
	{
		this.trajetoEntrada = trajetoEntrada;
		this.workingStep = workingStep;
		this.plungeType = (PlungeStrategy)((BottomAndSideMilling)workingStep.getOperation()).getApproachStrategy();
		this.trajeto = trajeto;
	}

	public ArrayList<Path> verticalPlunge()
	{
		ArrayList<Path> trajeto = new ArrayList<Path>();
		double retract = workingStep.getOperation().getRetractPlane();
		LinearPath p0 = new LinearPath(new Point3d(trajetoEntrada.get(0).getInitialPoint().x, trajetoEntrada.get(0).getInitialPoint().y, retract), trajetoEntrada.get(0).getInitialPoint());
		trajeto.add(p0);
		return trajeto; 
	}

	public ArrayList<Path> rampPlunge(){
		ArrayList<Path> trajeto = new ArrayList<Path>();
		ArrayList<Path> trajetoT = new ArrayList<Path>(); //Apenas para inversï¿½o do array
		PlungeRamp pl = (PlungeRamp)plungeType;
		double deltaZ= 0 ;
		double zAtual = trajetoEntrada.get(0).getInitialPoint().z; // PONTO!!!
		double retractTotal = workingStep.getOperation().getRetractPlane();
		double alfa = pl.getAngle() * Math.PI / 180;
		Point3d vector = null;
		int numeroPathFinal = 0;
		int contadorPaths = trajetoEntrada.size()-1;
		double dist;
		
		if(alfa <= 0 ){
			JOptionPane.showConfirmDialog(null,  "The angle has to be major than Zero", "Error", JOptionPane.ERROR_MESSAGE);
			ok = false;
			}
		else
			ok = true;
			if(ok)
			{
				while (zAtual <= retractTotal)
				{
					if(trajetoEntrada.get(contadorPaths).isLine())
					{
						dist = new Point3d(trajetoEntrada.get(contadorPaths).getFinalPoint().x,trajetoEntrada.get(contadorPaths).getFinalPoint().y, zAtual).distance(new Point3d(trajetoEntrada.get(contadorPaths).getInitialPoint().x, trajetoEntrada.get(contadorPaths).getInitialPoint().y, zAtual));
						deltaZ = Math.tan(alfa)*dist;
						zAtual = zAtual + deltaZ;
						LinearPath p0 = new LinearPath (new Point3d(trajetoEntrada.get(contadorPaths).getInitialPoint().x, trajetoEntrada.get(contadorPaths).getInitialPoint().y, zAtual), new Point3d(trajetoEntrada.get(contadorPaths).getFinalPoint().x, trajetoEntrada.get(contadorPaths).getFinalPoint().y,zAtual- deltaZ));
						trajeto.add(p0);
						contadorPaths--;
						numeroPathFinal++;
						if (contadorPaths<0)
							contadorPaths = trajetoEntrada.size()-1;
					}
					else if (trajetoEntrada.get(contadorPaths).isCircular())
					{
						CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths);
						int n = (int)(circularTmp.getAngulo() * circularTmp.getRadius() / separacao);
						double dAngle = circularTmp.getAngulo() / (n);
						double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + circularTmp.getAngulo(); // na verdade Ã© o angulo final
						dist = circularTmp.getRadius()*circularTmp.getAngulo();
						deltaZ = Math.tan(alfa)*dist;
						zAtual = zAtual + deltaZ;
						for(int j = 0; j < n - 1; j++) 
						{
							double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * j);
							double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * j);
							double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
							Point3d pInitialTmp = new Point3d(x, y, z);
							
							x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (j + 1));
							y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (j + 1));
							z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
							Point3d pFinalTmp = new Point3d(x, y, z);
							trajeto.add(new LinearPath(pFinalTmp, pInitialTmp));
							numeroPathFinal++;
						}
						contadorPaths--;
						if (contadorPaths< 0 )
							contadorPaths = trajetoEntrada.size()-1;
					}
				}
					contadorPaths++;
					if (contadorPaths>=trajetoEntrada.size())
						contadorPaths = 0;
					System.out.println("Indice de Entrada do ultimo Path Lido! ->"+contadorPaths);
					if(trajetoEntrada.get(contadorPaths).isLine())
					{
						if (zAtual >= retractTotal)
						{
							int iTmp = trajeto.size() -1;
							while ((trajeto.get(iTmp).getInitialPoint().z > retractTotal) && (trajeto.get(iTmp).getFinalPoint().z > retractTotal)){
								System.out.println("Indice do iTmp Linear "+ iTmp);
								iTmp--;
								}
							dist = trajeto.get(iTmp).getFinalPoint().distance(trajeto.get(iTmp).getInitialPoint());
							vector = new Point3d(trajeto.get(iTmp).getInitialPoint().x - trajeto.get(iTmp).getFinalPoint().x, trajeto.get(iTmp).getInitialPoint().y - trajeto.get(iTmp).getFinalPoint().y, trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z);
							vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unitï¿½rio
							zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
							alfa = Math.asin(zAtual/dist);
							zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
							dist = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unitï¿½rio.
							vector = new Point3d(trajeto.get(iTmp).getFinalPoint().x + vector.x*dist,  trajeto.get(iTmp).getFinalPoint().y+ vector.y*dist ,  trajeto.get(iTmp).getFinalPoint().z + (vector.z)*dist);
							LinearPath p0 = new LinearPath(vector, trajeto.get(iTmp).getFinalPoint());
							trajeto.set(iTmp,p0);
							for (contadorPaths = iTmp; contadorPaths>=0; contadorPaths--)
							{
								trajetoT.add(trajeto.get(contadorPaths));
							}
						}
					}
					else if (trajetoEntrada.get(contadorPaths).isCircular())
					{
						if (zAtual >= retractTotal)
						{
							int iTmp = trajeto.size() -1;
							System.out.println("Indice do Circular caso entre "+ iTmp);
							System.err.println("Ponto Z do circular Inicial " + trajeto.get(iTmp).getInitialPoint().z);
							System.err.println("Ponto Z do circular Final " + trajeto.get(iTmp).getFinalPoint().z);
							while (trajeto.get(iTmp).getInitialPoint().z > retractTotal && trajeto.get(iTmp).getFinalPoint().z > retractTotal)
							{
								System.err.println("Indice do iTmp Circular"+ iTmp);
								iTmp--;
							}
							dist = trajeto.get(iTmp).getFinalPoint().distance(trajeto.get(iTmp).getInitialPoint());
							vector = new Point3d(trajeto.get(iTmp).getInitialPoint().x - trajeto.get(iTmp).getFinalPoint().x, trajeto.get(iTmp).getInitialPoint().y - trajeto.get(iTmp).getFinalPoint().y, trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z);
							vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unitï¿½rio
							zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
							alfa = Math.asin(zAtual/dist);
							zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
							dist = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unitï¿½rio.
							vector = new Point3d(trajeto.get(iTmp).getFinalPoint().x + vector.x*dist,  trajeto.get(iTmp).getFinalPoint().y+ vector.y*dist ,  trajeto.get(iTmp).getFinalPoint().z + (vector.z)*dist);
							LinearPath p0 = new LinearPath(vector, trajeto.get(iTmp).getFinalPoint());
							trajeto.set(iTmp,p0);
							for (contadorPaths = iTmp; contadorPaths>=0; contadorPaths--)
							{
								trajetoT.add(trajeto.get(contadorPaths));
							}
						}
						
					}
				}
		
			
		 return trajetoT;
		}

	public ArrayList<Path> zigZagPlunge()
	{
		ArrayList<Path> trajeto = new ArrayList<Path>();
		ArrayList<Path> trajetoT = new ArrayList<Path>();
		ArrayList<Integer> listaPaths = new ArrayList<Integer>();
		PlungeZigzag pl = (PlungeZigzag)plungeType;
		double alfa = pl.getAngle()*Math.PI/180;
		double retractTotal = workingStep.getOperation().getRetractPlane();
		double width = pl.getWidth();
		double zAtual = trajetoEntrada.get(0).getInitialPoint().z; // poderia ser qual quer um deles.
		double deltaZ = 0;
		int contadorPaths = trajetoEntrada.size()-1;
		int  numeroPathsFinal = 0;
		int paridade = 0;
		double distance = 0;
		if(alfa <= 0 || width<5 ){
			JOptionPane.showConfirmDialog(null,  "The angle has to be major than Zero", "Error", JOptionPane.ERROR_MESSAGE);
			ok = false;
			}
		else
			ok = true;
		if(ok)
			{	
		if (trajetoEntrada.get(contadorPaths).isLine())
		{
			 distance = trajetoEntrada.get(contadorPaths).getInitialPoint().distance(trajetoEntrada.get(contadorPaths).getFinalPoint());
		}
		else if(trajetoEntrada.get(contadorPaths).isCircular())
		{
			CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths); 
			distance = circularTmp.getInitialPoint().distance(circularTmp.getCenter())*circularTmp.getAngulo();
			System.out.println("Distancia do Circular " + distance);
		}
		
		//Tratar todos os casos!
		
		
		if(trajetoEntrada.get(contadorPaths).isLine() && width <= distance )
			{
				while (zAtual < retractTotal)
				{
					deltaZ = width*Math.tan(alfa);
					distance = trajetoEntrada.get(contadorPaths).getInitialPoint().distance(trajetoEntrada.get(contadorPaths).getFinalPoint());
					Point3d vector = new Point3d (trajetoEntrada.get(contadorPaths).getInitialPoint().x - trajetoEntrada.get(contadorPaths).getFinalPoint().x,trajetoEntrada.get(contadorPaths).getInitialPoint().y - trajetoEntrada.get(contadorPaths).getFinalPoint().y, trajetoEntrada.get(contadorPaths).getInitialPoint().z - trajetoEntrada.get(contadorPaths).getFinalPoint().z);
					if (paridade%2 == 0)
					{
						vector = new Point3d (((vector.x /distance)*width)+ trajetoEntrada.get(contadorPaths).getFinalPoint().x , ((vector.y / distance)*width)+trajetoEntrada.get(contadorPaths).getFinalPoint().y , (vector.z / distance)*width); // vetor contendo as coordenadas x e y exatas.
						LinearPath p0 = new LinearPath( new Point3d(vector.x, vector.y, zAtual + deltaZ), new Point3d (trajetoEntrada.get(contadorPaths).getFinalPoint().x,  trajetoEntrada.get(contadorPaths).getFinalPoint().y, zAtual) );
						trajeto.add(p0);
						paridade++;
					}
					else
					{
						//vector = new Point3d (-(((vector.x /distance)*width)+ trajeto.get(0).getInitialPoint().x) , -(((vector.y / distance)*width)+trajeto.get(0).getInitialPoint().y) , (vector.z / distance)*width);
						LinearPath p0 = new LinearPath( new Point3d (trajetoEntrada.get(contadorPaths).getFinalPoint().x,trajetoEntrada.get(contadorPaths).getFinalPoint().y, zAtual + deltaZ), trajeto.get(numeroPathsFinal-1).getInitialPoint());
						trajeto.add(p0);
						paridade++;
					}
					zAtual = zAtual + deltaZ;
					numeroPathsFinal++;
				}
				
			}
		else if (trajetoEntrada.get(contadorPaths).isCircular() && width <= distance)
			{
				while (zAtual < retractTotal)
				{
					CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths);
					deltaZ = width*Math.tan(alfa);
					double teta = width/circularTmp.getRadius();
					int n = (int)(teta * circularTmp.getRadius() / separacao);
					double dAngle = teta / (n);
					double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + teta;
					for(int j = 0; j < n - 1; j++) 
					{
						double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * j);
						double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * j);
						//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
						double z = zAtual + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
						Point3d pInitialTmp = new Point3d(x, y, z);
						
						x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (j + 1));
						y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (j + 1));
						z = zAtual + (j+1) * dAngle * circularTmp.getRadius() * Math.tan(alfa);
						//z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
						Point3d pFinalTmp = new Point3d(x, y, z);
						
						LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
						trajeto.add(p0);
						
					}
					zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;
					initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x);
					
					
					for(int j = 0; j < n - 1; j++) 
					{
						double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * j);
						double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * j);
						double z = zAtual  + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
						//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
						Point3d pInitialTmp = new Point3d(x, y, z);
						
						x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * (j + 1));
						y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * (j + 1));
						z = zAtual + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
						//z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
						Point3d pFinalTmp = new Point3d(x, y, z);
						
						LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
						trajeto.add(p0);
					}
					zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;	
				}
			}
		
		// Caso em que o Width é maior que a primeira distancia proposta!	
		
		while (distance < width)
		{
			width = width- distance;
			listaPaths.add(contadorPaths);
			contadorPaths--;
			if (contadorPaths<0)
				contadorPaths = trajetoEntrada.size()-1;
			if (trajetoEntrada.get(contadorPaths).isLine())
			{
				distance = trajetoEntrada.get(contadorPaths).getInitialPoint().distance(trajetoEntrada.get(contadorPaths).getFinalPoint());
			}
			else if(trajetoEntrada.get(contadorPaths).isCircular())
			{
				CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths); 
				distance = circularTmp.getInitialPoint().distance(circularTmp.getCenter())*circularTmp.getAngulo();
			}
		}	
				//Tratando os paths e o Zig-Zag
				listaPaths.add(contadorPaths);
				int indicePaths = 0;
				boolean trocador = true;
				while (zAtual < retractTotal)
				{
						if(indicePaths != listaPaths.size()-1)
						{
							if(trajetoEntrada.get(listaPaths.get(indicePaths)).isLine())
							{
								deltaZ = trajetoEntrada.get(listaPaths.get(indicePaths)).getInitialPoint().distance(trajetoEntrada.get(listaPaths.get(indicePaths)).getFinalPoint())*Math.tan(alfa);
								if (trocador == true)
								{
									LinearPath p0 = new LinearPath(new Point3d(trajetoEntrada.get(listaPaths.get(indicePaths)).getInitialPoint().x , trajetoEntrada.get(listaPaths.get(indicePaths)).getInitialPoint().y, zAtual+deltaZ), new Point3d(trajetoEntrada.get(listaPaths.get(indicePaths)).getFinalPoint().x, trajetoEntrada.get(listaPaths.get(indicePaths)).getFinalPoint().y, zAtual));
									trajeto.add(p0);
									zAtual = deltaZ + zAtual;
									numeroPathsFinal++;
									indicePaths++;
									System.out.println("teste");
								}
							
								else if (trocador == false)
								{
									if(trajetoEntrada.get(listaPaths.get(indicePaths)).isLine())
									{
										LinearPath p0 = new LinearPath(new Point3d(trajetoEntrada.get(listaPaths.get(indicePaths)).getFinalPoint().x , trajetoEntrada.get(listaPaths.get(indicePaths)).getFinalPoint().y, zAtual+deltaZ), trajeto.get(numeroPathsFinal-1).getInitialPoint());
										trajeto.add(p0);
										zAtual = deltaZ + zAtual;
										numeroPathsFinal++;
										indicePaths--;
										if (indicePaths < 0)
										{
											indicePaths = 0;
											trocador = !trocador;
										}
									}
								}	
							}
							else if(trajetoEntrada.get(listaPaths.get(indicePaths)).isCircular())
							{
								if(trocador==true)
								{
									CircularPath circularTmp = (CircularPath)trajetoEntrada.get(listaPaths.get(indicePaths));
									deltaZ = circularTmp.getRadius()*circularTmp.getAngulo()*(Math.tan(alfa));
									double teta = circularTmp.getRadius()*circularTmp.getAngulo()/circularTmp.getRadius();
									int n = (int)(teta * circularTmp.getRadius() / separacao);
									double dAngle = teta / (n);
									double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + teta;
									for(int j = 0; j < n - 1; j++) 
									{
										double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * j);
										double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * j);
										//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										double z = zAtual + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pInitialTmp = new Point3d(x, y, z);
										
										x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (j + 1));
										y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (j + 1));
										z = zAtual + (j+1) * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pFinalTmp = new Point3d(x, y, z);
										
										LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
										trajeto.add(p0);
										numeroPathsFinal++;
									}
									indicePaths++;
									zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;
									System.out.println("estive aqui");
									
								}
								else if(trocador == false)
								{
									CircularPath circularTmp = (CircularPath)trajetoEntrada.get(listaPaths.get(indicePaths));
									deltaZ = circularTmp.getRadius()*circularTmp.getAngulo()*(Math.tan(alfa));
									double teta = circularTmp.getRadius()*circularTmp.getAngulo()/circularTmp.getRadius();
									int n = (int)(teta * circularTmp.getRadius() / separacao);
									double dAngle = teta / (n);
									double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x);
									
									for(int j = 0; j < n - 1; j++) 
									{
										double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * j);
										double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * j);
										double z = zAtual  + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pInitialTmp = new Point3d(x, y, z);
										
										x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * (j + 1));
										y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * (j + 1));
										z = zAtual + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
										//z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pFinalTmp = new Point3d(x, y, z);
										
										LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
										trajeto.add(p0);
										numeroPathsFinal++;
									}
									zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;
									indicePaths--;
									if (indicePaths < 0)
									{
										indicePaths = 0;
										trocador = !trocador;
									}
								}
							}
						}
						else if(indicePaths == listaPaths.size()-1)
						{
							if(trajetoEntrada.get(contadorPaths).isLine())
							{
								deltaZ = width*Math.tan(alfa);
								Point3d vector = new Point3d (trajetoEntrada.get(contadorPaths).getInitialPoint().x - trajetoEntrada.get(contadorPaths).getFinalPoint().x,trajetoEntrada.get(contadorPaths).getInitialPoint().y - trajetoEntrada.get(contadorPaths).getFinalPoint().y, trajetoEntrada.get(contadorPaths).getInitialPoint().z - trajetoEntrada.get(contadorPaths).getFinalPoint().z);
								if (paridade%2 == 0)
								{
									vector = new Point3d (((vector.x /distance)*width)+ trajetoEntrada.get(contadorPaths).getFinalPoint().x , ((vector.y / distance)*width)+trajetoEntrada.get(contadorPaths).getFinalPoint().y , (vector.z / distance)*width); // vetor contendo as coordenadas x e y exatas.
									LinearPath p0 = new LinearPath( new Point3d(vector.x, vector.y, zAtual + deltaZ), new Point3d (trajetoEntrada.get(contadorPaths).getFinalPoint().x,  trajetoEntrada.get(contadorPaths).getFinalPoint().y, zAtual) );
									trajeto.add(p0);
									numeroPathsFinal++;
									zAtual = deltaZ + zAtual;
								}
								else if (paridade%2 != 0)
								{
									System.out.println("teste");
									System.out.println("Delta Z "+ deltaZ);
									LinearPath p0 = new LinearPath( new Point3d (trajetoEntrada.get(contadorPaths).getFinalPoint().x,trajetoEntrada.get(contadorPaths).getFinalPoint().y, zAtual + deltaZ), trajeto.get(numeroPathsFinal-1).getInitialPoint());
									trajeto.add(p0);
									numeroPathsFinal++;
									zAtual = deltaZ + zAtual;
									System.out.println("Z Atual "+zAtual);
									indicePaths = listaPaths.size() - 2;
									System.err.println("Indice do Path Problema "+ listaPaths.get(indicePaths));
									trocador = !trocador;
								}
								paridade++;
							}
							else if (trajetoEntrada.get(contadorPaths).isCircular())
							{
								if(paridade%2==0)
								{
									CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths);
									deltaZ = width*Math.tan(alfa);
									double teta = width/circularTmp.getRadius();
									int n = (int)(teta * circularTmp.getRadius() / separacao);
									double dAngle = teta / (n);
									double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + teta;
									for(int j = 0; j < n - 1; j++) 
									{
										double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * j);
										double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * j);
										//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										double z = zAtual + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pInitialTmp = new Point3d(x, y, z);
										
										x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (j + 1));
										y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (j + 1));
										z = zAtual + (j+1) * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										//z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pFinalTmp = new Point3d(x, y, z);
										LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
										trajeto.add(p0);
										numeroPathsFinal++;
										
									}
									zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;
								}
								else if (paridade%2 !=2)
								{
									CircularPath circularTmp = (CircularPath)trajetoEntrada.get(contadorPaths);
									deltaZ = width*Math.tan(alfa);
									double teta = width/circularTmp.getRadius();
									int n = (int)(teta * circularTmp.getRadius() / separacao);
									double dAngle = teta / (n);
									double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x);
									for(int j = 0; j < n - 1; j++) 
									{
										double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * j);
										double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * j);
										double z = zAtual  + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										//double z = zAtual - deltaZ + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pInitialTmp = new Point3d(x, y, z);
										
										x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle + dAngle * (j + 1));
										y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle + dAngle * (j + 1));
										z = zAtual + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
										//z = zAtual - deltaZ + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
										Point3d pFinalTmp = new Point3d(x, y, z);
										
										LinearPath p0 = new LinearPath(pFinalTmp, pInitialTmp);
										trajeto.add(p0);
										numeroPathsFinal++;
									}
									zAtual = trajeto.get(trajeto.size()-1).getInitialPoint().z;	
									indicePaths = listaPaths.size() - 2;
									trocador = !trocador;
								}
								paridade++;
								
							}
						}
				}
		System.out.println("Ultimo Path-> "+ contadorPaths);
			if (zAtual >= retractTotal)
			{
				int iTmp = trajeto.size() -1;
				while ((trajeto.get(iTmp).getInitialPoint().z > retractTotal) && (trajeto.get(iTmp).getFinalPoint().z > retractTotal)){
					System.out.println("Indice do iTmp Linear "+ iTmp);
					iTmp--;
					}
				distance = trajeto.get(iTmp).getFinalPoint().distance(trajeto.get(iTmp).getInitialPoint());
				Point3d vector = new Point3d(trajeto.get(iTmp).getInitialPoint().x - trajeto.get(iTmp).getFinalPoint().x, trajeto.get(iTmp).getInitialPoint().y - trajeto.get(iTmp).getFinalPoint().y, trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z);
				vector = new Point3d((vector.x/distance), (vector.y/distance), (vector.z/distance)); // vetor unitï¿½rio
				zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
				alfa = Math.asin(zAtual/distance);
				zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
				distance = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unitï¿½rio.
				vector = new Point3d((trajeto.get(iTmp).getFinalPoint().x + vector.x*distance),  (trajeto.get(iTmp).getFinalPoint().y+ vector.y*distance) ,  trajeto.get(iTmp).getFinalPoint().z + (vector.z)*distance);
				LinearPath p0 = new LinearPath(vector, trajeto.get(iTmp).getFinalPoint());
				trajeto.set(iTmp,p0);
				for (contadorPaths = iTmp; contadorPaths>=0; contadorPaths--)
				{
					trajetoT.add(trajeto.get(contadorPaths));
				}
			}
			}
		return trajetoT;
		}
	
	public ArrayList<Path> getTrajeto()
	{
		if (plungeType.getClass() == PlungeToolAxis.class)
			trajeto = verticalPlunge();
		if (plungeType.getClass() == PlungeRamp.class)
			trajeto = rampPlunge();
		if (plungeType.getClass() == PlungeZigzag.class)
			trajeto = zigZagPlunge();
		
		return trajeto;
	}
	
	
	
	
	

}