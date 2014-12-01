package br.UFSC.GRIMA.capp;

/**
 * 
 * @author Igor Beninc�
 *
 */

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.EditFaceMillFrame;
import br.UFSC.GRIMA.cam.GenerateTrocoidalGCode;
import br.UFSC.GRIMA.capp.movimentacoes.GenerateTrochoidalMovement1;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.DesenhadorDeLimitedElements;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class CreatePlungeStrategy1 extends EditFaceMillFrame implements ActionListener
{
	private ArrayList<Path> trajetoEntrada;
	private boolean ok = true;
	private double separacao = 2;
	public CreatePlungeStrategy1(ArrayList<Path> trajetoEntrada) // Cria��o do metodo, vulgo objeto
	{
		super(new Frame());
		this.okButton.addActionListener(this); // Dando ouvidos ao bot�o ok
		this.cancelButton.addActionListener(this);
		this.bolaVert.addActionListener(this);
		this.bolaRamp.addActionListener(this);
		this.bolaZigzag.addActionListener(this);
		this.setVisible(true);
		this.widthBox.setVisible(false);
		this.retractPlane.setVisible(true);
		this.angle.setVisible(false);
		this.angleZigZag.setVisible(false);
		this.trajetoEntrada = trajetoEntrada;
	}

	@Override
	public void actionPerformed(ActionEvent event) {  // O que fazer quando o bot�o for escutado
		Object source = event.getSource();
		if (source == okButton){
			this.calcularMergulho();
		}
		else if (source == cancelButton){
			this.dispose(); // Fecha a janela.
			}
		
		else if (source == bolaVert)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Axis.png")));
			//this.retractText.setVisible(true);
			this.retractPlane.setVisible(true);
			//this.angleText.setVisible(false);
			this.angle.setVisible(false);
			this.angleZigZag.setVisible(false);
			//this.widthText.setVisible(false);
			this.widthBox.setVisible(false);
		}
		
		else if (source == bolaRamp)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp Plunge.png")));
			//this.angleText.setVisible(true);
			this.angle.setVisible(true);
			this.angleZigZag.setVisible(false);
			//this.widthText.setVisible(false);
			this.widthBox.setVisible(false);
			//this.retractText.setVisible(true);
			this.retractPlane.setVisible(true);
			this.retractPlane.setBounds(265, 215, 45, this.retractPlane.getPreferredSize().height);
		}
		else if (source == bolaZigzag)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Zig Zag.png")));
			//this.angleText.setVisible(true);
			this.angleZigZag.setVisible(true);
			//this.retractText.setVisible(true);
			this.retractPlane.setVisible(true);
			//this.widthText.setVisible(true);
			this.widthBox.setVisible(true);
			this.angle.setVisible(false);
			this.retractPlane.setBounds(265, 215, 45, this.retractPlane.getPreferredSize().height);
		}

	
	}

	public ArrayList<Path> verticalPlunge() {
		ArrayList<Path> trajeto = new ArrayList<Path>();
		double retract = ((Double)(retractPlane.getValue())).doubleValue();
		LinearPath p0 = new LinearPath(new Point3d(trajetoEntrada.get(0).getInitialPoint().x, trajetoEntrada.get(0).getInitialPoint().y, retract), trajetoEntrada.get(0).getInitialPoint());
		trajeto.add(p0);
		return trajeto; 
		
	}
	// Metodo de Mergulho em Rampa.
	
	
	public ArrayList<Path> rampPlunge(){
		ArrayList<Path> trajeto = new ArrayList<Path>();
		ArrayList<Path> trajetoT = new ArrayList<Path>(); //Apenas para invers�o do array
		double h= 0 ;
		double zAtual = trajetoEntrada.get(0).getInitialPoint().z; // PONTO!!!
		double retractTotal = ((Double)(retractPlane.getValue())).doubleValue();
		double alfa =(((Double)(angle.getValue())).doubleValue()*Math.PI)/(180);
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
						h = Math.tan(alfa)*dist;
						zAtual = zAtual + h;
						LinearPath p0 = new LinearPath (new Point3d(trajetoEntrada.get(contadorPaths).getInitialPoint().x, trajetoEntrada.get(contadorPaths).getInitialPoint().y, zAtual), new Point3d(trajetoEntrada.get(contadorPaths).getFinalPoint().x, trajetoEntrada.get(contadorPaths).getFinalPoint().y,zAtual- h));
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
						double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + circularTmp.getAngulo(); // na verdade é o angulo final
						dist = circularTmp.getRadius()*circularTmp.getAngulo();
						h = Math.tan(alfa)*dist;
						zAtual = zAtual + h;
						for(int j = 0; j < n - 1; j++) 
						{
							double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * j);
							double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * j);
							double z = zAtual - h + j * dAngle * circularTmp.getRadius() * Math.tan(alfa);
							Point3d pInitialTmp = new Point3d(x, y, z);
							
							x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (j + 1));
							y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (j + 1));
							z = zAtual - h + (j + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
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
							vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unit�rio
							zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
							alfa = Math.asin(zAtual/dist);
							zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
							dist = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unit�rio.
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
							vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unit�rio
							zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
							alfa = Math.asin(zAtual/dist);
							zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
							dist = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unit�rio.
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
	/**
	 * metodo que calcula o mergulho em rampa
	 * @return -- array de paths da rampa
	 */
	public ArrayList<Path> rampPlunge1()
	{
		ArrayList<Path> trajetoInverso = new ArrayList<Path>(); // cuidado! esta em sentido contrario
		ArrayList<Path> trajeto = new ArrayList<Path>(); // array de saida
		double distanceTmp = 0;
		double alfa = ((Double)angle.getValue()).doubleValue() * Math.PI / 180; // angulo em radianos
		double zRetractPlane = ((Double)(retractPlane.getValue())).doubleValue(); // z do retract plane (a ser atingido)
		double zAtual = trajetoEntrada.get(trajetoEntrada.size() - 1).getInitialPoint().z; // pode ser do ponto final tambem
		/*
		 *  primeiro elemento dos paths de entrada
		 */
		if(trajetoEntrada.get(trajetoEntrada.size() - 1).isLine()) 
		{
			distanceTmp = trajetoEntrada.get(trajetoEntrada.size() - 1).getInitialPoint().distance(trajetoEntrada.get(trajetoEntrada.size() - 1).getFinalPoint());			
		} else if(trajetoEntrada.get(trajetoEntrada.size() - 1).isCircular())
		{
			CircularPath cir = (CircularPath)trajetoEntrada.get(trajetoEntrada.size() - 1);
			distanceTmp = cir.getAngulo() * cir.getRadius();
		} else // GeneralPath
		{
			//eu nao sei
		}
		double deltaZ = distanceTmp * Math.tan(alfa);
		zAtual = zAtual + deltaZ;
		/*
		 *  do segundo elemento dos paths de entrada em diante
		 */
		int contadorPaths = 0;
		while(zAtual <= zRetractPlane)
		{
			if(trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).isLine())
			{
				Point3d pontoInicialTmp = new Point3d(trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getInitialPoint().x, trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getInitialPoint().y, zAtual);
				Point3d pontoFinalTmp = new Point3d(trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getFinalPoint().x, trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getFinalPoint().y, zAtual - deltaZ);
				LinearPath linearTmp = new LinearPath(pontoInicialTmp, pontoFinalTmp);
				trajetoInverso.add(linearTmp);
				distanceTmp = trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getInitialPoint().distance(trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).getFinalPoint()); // verificar aqui
			} else if(trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths).isCircular())
			{
				CircularPath circularTmp = (CircularPath)trajetoEntrada.get(trajetoEntrada.size() - 1 - contadorPaths);
				int n = (int)(circularTmp.getAngulo() * circularTmp.getRadius() / separacao);
				double dAngle = circularTmp.getAngulo() / (n);
				double initialAngle = Math.atan2(circularTmp.getInitialPoint().y - circularTmp.getCenter().y, circularTmp.getInitialPoint().x - circularTmp.getCenter().x) + circularTmp.getAngulo(); // na verdade é o angulo final
				distanceTmp = circularTmp.getRadius() * circularTmp.getAngulo(); 
				deltaZ = distanceTmp * Math.tan(alfa);
				/*
				 * interpolacao do arco circular
				 */
				for(int i = 0; i < n - 1; i++)
				{
					double x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * i);
					double y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * i);
					double z = zAtual - deltaZ + i * dAngle * circularTmp.getRadius() * Math.tan(alfa);
					Point3d pInitialTmp = new Point3d(x, y, z);
					
					x = circularTmp.getCenter().x + circularTmp.getRadius() * Math.cos(initialAngle - dAngle * (i + 1));
					y = circularTmp.getCenter().y + circularTmp.getRadius() * Math.sin(initialAngle - dAngle * (i + 1));
					z = zAtual - deltaZ + (i + 1)* dAngle * circularTmp.getRadius() * Math.tan(alfa);
					Point3d pFinalTmp = new Point3d(x, y, z);
					
					LinearPath lTmp = new LinearPath(pFinalTmp, pInitialTmp);
					trajetoInverso.add(lTmp);
					distanceTmp = circularTmp.getAngulo() * circularTmp.getRadius();
				}
			} else // GeneralPath
			{
				//eu nao sei
			}
			contadorPaths++;
			
			deltaZ = distanceTmp * Math.tan(alfa);
			zAtual = zAtual + deltaZ;
			if(contadorPaths == trajetoEntrada.size())
			{
				contadorPaths = 0;
			}
		}
		zAtual = zAtual - deltaZ; 
		/*
		 * ultimo elemento do path de entrada
		 *  IMPLEMENTAR
		 */
		Path ultimoPath = trajetoEntrada.get(contadorPaths);
		if(ultimoPath.isLine())
		{
			Point3d vetorUnitario = new Point3d (ultimoPath.getInitialPoint().x - ultimoPath.getFinalPoint().x , ultimoPath.getInitialPoint().y - ultimoPath.getFinalPoint().y, ultimoPath.getInitialPoint().z - ultimoPath.getFinalPoint().z);
			double distance = ultimoPath.getInitialPoint().distance(ultimoPath.getFinalPoint());
			vetorUnitario = new Point3d (vetorUnitario.x / distance, vetorUnitario.y / distance, vetorUnitario.z / distance);
			double l = (zRetractPlane - zAtual) / Math.tan(alfa);
			//l = ultimoPath.getInitialPoint().distance(ultimoPath.getFinalPoint()) - l;
			Point3d vetor = new Point3d(ultimoPath.getFinalPoint().x + vetorUnitario.x * l, ultimoPath.getFinalPoint().y + vetorUnitario.y * l, zRetractPlane );
			trajetoInverso.add(new LinearPath(vetor, new Point3d(ultimoPath.getFinalPoint().x, ultimoPath.getFinalPoint().y, zAtual)));
			System.out.println("linear");
		} else if(ultimoPath.isCircular())
		{
			System.out.println("circular");
		} else // GENERAL PATH
		{
			
		}
		
		/*
		 * invertendo o array
		 */
		for(int i = 0; i < trajetoInverso.size(); i++)
		{
			trajeto.add(trajetoInverso.get(trajetoInverso.size() - 1 - i));
		}
		return trajeto;
	}
	//Metodo que calcula o mergulho em Zigzag
	
	public ArrayList<Path> zigZagPlunge()
	{
		ArrayList<Path> trajeto = new ArrayList<Path>();
		ArrayList<Path> trajetoT = new ArrayList<Path>();
		ArrayList<Integer> listaPaths = new ArrayList<Integer>();
		double alfa = ((Double)angleZigZag.getValue())*Math.PI/180;
		double retractTotal = (Double)retractPlane.getValue();
		double width = ((Double)(widthBox.getValue())).doubleValue();
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
		
		// Caso em que o Width � maior que a primeira distancia proposta!	
		
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
									if (indicePaths < 0)
										indicePaths = 0;
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
				vector = new Point3d((vector.x/distance), (vector.y/distance), (vector.z/distance)); // vetor unit�rio
				zAtual = trajeto.get(iTmp).getInitialPoint().z - trajeto.get(iTmp).getFinalPoint().z;
				alfa = Math.asin(zAtual/distance);
				zAtual = retractTotal - trajeto.get(iTmp).getFinalPoint().z; 
				distance = (zAtual/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unit�rio.
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
			
	
	public ArrayList<Path> calcularMergulho() {
		ArrayList<Path> trajeto = null; //Declara��o da Vari�vel trajeto e cria��o desta
		if(bolaVert.isSelected())
		{
			trajeto = verticalPlunge();
		}
		else if (bolaRamp.isSelected())
		{
			trajeto = rampPlunge();
		}
		else if (bolaZigzag.isSelected())
		{
			trajeto = zigZagPlunge();
		}
		for(int i = 0; i < trajeto.size(); i++)
		{
			System.out.println("pi-> " + trajeto.get(i).getInitialPoint() + "\tpf -> " + trajeto.get(i).getFinalPoint());
		}
		if (ok)
		{
			System.out.println(GenerateTrocoidalGCode.transformPathToGCode(trajeto));
			DesenhadorDeLimitedElements desenho = new DesenhadorDeLimitedElements(GenerateTrochoidalMovement1.transformPathsInLimitedElements(trajeto));
		}
		return trajeto;
	}
	
}
