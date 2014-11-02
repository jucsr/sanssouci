package br.UFSC.GRIMA.capp;

/**
 * 
 * @author Igor Beninc�
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cam.GenerateTrocoidalGCode;
import br.UFSC.GRIMA.capp.visual.PlungeFrame2;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class CreatePlungeStrategy1 extends PlungeFrame2 implements ActionListener
{
	private ArrayList<Path> trajetoEntrada;
	private char plungeType;
	public CreatePlungeStrategy1(ArrayList<Path> trajetoEntrada) // Cria��o do metodo, vulgo objeto
	{
		this.okButton.addActionListener(this); // Dando ouvidos ao bot�o ok
		this.cancelButton.addActionListener(this);
		this.bolaVert.addActionListener(this);
		this.bolaRamp.addActionListener(this);
		this.bolaHelix.addActionListener(this);
		this.bolaZigzag.addActionListener(this);
		this.setVisible(true);
		this.widthText.setVisible(false);
		this.widthBox.setVisible(false);
		this.retractText.setVisible(true);
		this.retractBox.setVisible(true);
		this.angleText.setVisible(false);
		this.angleBox.setVisible(false);
		this.radiusText.setVisible(false);
		this.radiusBox.setVisible(false);
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
			label1.setIcon(new ImageIcon(getClass().getResource("/images/vertical.png")));
			this.retractText.setVisible(true);
			this.retractBox.setVisible(true);
			this.angleText.setVisible(false);
			this.angleBox.setVisible(false);
			this.radiusText.setVisible(false);
			this.radiusBox.setVisible(false);
			this.widthText.setVisible(false);
			this.widthBox.setVisible(false);
		}
		
		else if (source == bolaRamp)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp.png")));
			this.angleText.setVisible(true);
			this.angleBox.setVisible(true);
			this.widthText.setVisible(false);
			this.widthBox.setVisible(false);
			this.radiusText.setVisible(false);
			this.radiusBox.setVisible(false);
			this.retractText.setVisible(true);
			this.retractBox.setVisible(true);
		}

	
	}

	public ArrayList<Path> verticalPlunge() {
		ArrayList<Path> trajeto = new ArrayList<Path>();
		double retract = ((Double)(retractBox.getValue())).doubleValue();
		LinearPath p0 = new LinearPath(new Point3d(trajetoEntrada.get(0).getInitialPoint().x, trajetoEntrada.get(0).getInitialPoint().y, retract), trajetoEntrada.get(0).getInitialPoint());
		trajeto.add(p0);
		return trajeto; 
		
	}
	
	public ArrayList<Path> rampPlunge(){
		ArrayList<Path> trajeto = new ArrayList<Path>();
		ArrayList<Path> trajetoT = new ArrayList<Path>(); //Apenas para invers�o do array
		double h= 0 ;
		double ht = trajetoEntrada.get(0).getInitialPoint().z; // PONTO!!!
		double nSeparacoes = 0.001;
		double retractTotal = ((Double)(retractBox.getValue())).doubleValue();
		double alfa =(((Double)(angleBox.getValue())).doubleValue()*Math.PI)/(180);
		Point3d vector = null;
		int i = 0;
		int c = 0;
		double dist;
		
		while (ht <= retractTotal)
		{
			if(trajetoEntrada.get(c).isLine())
			{
				dist = new Point3d(trajetoEntrada.get(c).getFinalPoint().x,trajetoEntrada.get(c).getFinalPoint().y, ht).distance(new Point3d(trajetoEntrada.get(c).getInitialPoint().x, trajetoEntrada.get(c).getInitialPoint().y, ht));
				h = Math.tan(alfa)*dist;
				System.out.println("Acumulador do h "+h);
				if (Math.abs(h) > Math.abs(ht))
					ht = ht + h;
				else
					ht = ht + h;
				LinearPath p0 = new LinearPath (new Point3d(trajetoEntrada.get(c).getInitialPoint().x, trajetoEntrada.get(c).getInitialPoint().y, ht), new Point3d(trajetoEntrada.get(c).getFinalPoint().x, trajetoEntrada.get(c).getFinalPoint().y,ht- h));
				trajeto.add(p0);
				c--;
				i++;
				if (c<0)
					c = trajetoEntrada.size()-1;
			}
			else if (trajetoEntrada.get(c).isCircular())
			{
				CircularPath circularTemp = (CircularPath)trajetoEntrada.get(c);
				double alfaZero = Math.atan2(circularTemp.getInitialPoint().y - circularTemp.getCenter().y,circularTemp.getInitialPoint().x - circularTemp.getCenter().x ) + circularTemp.getAngulo(); // cuidado com angulos negativos
//				double alfaZero = Math.atan2(circularTemp.getInitialPoint().y - circularTemp.getCenter().y,circularTemp.getInitialPoint().x - circularTemp.getCenter().x ); // cuidado com angulos negativos
				double dalfa = -circularTemp.getAngulo()/(10 + 1);
				int j = 0;
				dist = circularTemp.getInitialPoint().distance(circularTemp.getCenter())*(alfa);
				h = Math.tan(alfa)*dist;
				if (Math.abs(h) > Math.abs(ht))
					ht = ht + h;
				else
					ht = ht + h;
				
				while(trajeto.get(i-1).getInitialPoint().z < ht && GeometricOperations.roundNumber(dalfa * j, 7) <= GeometricOperations.roundNumber(circularTemp.getAngulo(), 7)) // e se ultrapassar o delta angulo do arco?
				{
//					System.err.println("Ht acumulados "+trajeto.get(i-1).getFinalPoint().z);
//					System.out.println("angulo0 + dalfa = " + (alfaZero + dalfa * j));
					double x = circularTemp.getCenter().x + circularTemp.getRadius() * Math.cos(alfaZero + dalfa * j);
					double y = circularTemp.getCenter().y + circularTemp.getRadius() * Math.sin(alfaZero + dalfa * j);
//					LinearPath p0 = new LinearPath (new Point3d(circularTemp.getCenter().x + (circularTemp.getRadius())*Math.cos(alfaZero + dalfa*j), circularTemp.getCenter().y + (circularTemp.getRadius())*Math.sin(alfaZero + dalfa*j), trajeto.get(i-1).getInitialPoint().z + nSeparacoes), trajeto.get(i-1).getInitialPoint() );
//					LinearPath p0 = new LinearPath (new Point3d(x, y, trajeto.get(i-1).getInitialPoint().z + nSeparacoes), trajeto.get(i-1).getInitialPoint());
					LinearPath p0 = new LinearPath (new Point3d(x, y, trajeto.get(i-1).getInitialPoint().z + nSeparacoes), trajeto.get(i-1).getInitialPoint());
					trajeto.add(p0);
					j++;
					i++;
				}
				c--;
				if (c< 0 )
					c = trajetoEntrada.size()-1;
			}
		}
			if(trajetoEntrada.get(trajetoEntrada.size() -1 ).isLine())
			{
				if (ht >= retractTotal)
				{
					dist = trajeto.get(trajeto.size()-1).getFinalPoint().distance(trajeto.get(trajeto.size()-1).getInitialPoint());
					System.out.println("Dist�ncia dos pontos->" + dist);
					vector = new Point3d(trajeto.get(trajeto.size()-1).getFinalPoint().x - trajeto.get(trajeto.size()-1).getInitialPoint().x, trajeto.get(trajeto.size()-1).getFinalPoint().y - trajeto.get(trajeto.size()-1).getInitialPoint().y, trajeto.get(trajeto.size()-1).getFinalPoint().z - trajeto.get(trajeto.size()-1).getInitialPoint().z);
					vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unit�rio
					ht = ht - retractTotal;
					System.out.println("Dist�ncia h da dife retract->" + ht);
					dist = dist - (ht/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unit�rio.
					System.out.println("Dist�ncia mult->" + dist);
					vector = new Point3d(trajeto.get(i-1).getFinalPoint().x + Math.abs(vector.x)*dist,  trajeto.get(i-1).getFinalPoint().y+ Math.abs(vector.y)*dist ,  trajeto.get(i-1).getFinalPoint().z + Math.abs(vector.z)*dist);
				}
			}
			else if (trajetoEntrada.get(trajetoEntrada.size()-1).isCircular())
			{
				while(trajeto.get(i-1).getInitialPoint().z > retractTotal)
				{
					i--;
				}
				vector = trajeto.get(i-1).getInitialPoint();
			}
			
			
		LinearPath p0 = new LinearPath(vector, trajeto.get(i-1).getFinalPoint());
		trajeto.set(i-1,p0);
		for (c = i-1; c>=0; c--)
		{
			trajetoT.add(trajeto.get(c));
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
		ArrayList<Path> trajeto = new ArrayList<Path>();
		double distanceTmp = 0;
		double alfa = ((Double)angleBox.getValue()).doubleValue() * Math.PI / 180; // angulo em radianos
		double zRetractPlane = ((Double)(retractBox.getValue())).doubleValue(); // z do retract plane (a ser atingido)
		double zAtual = trajetoEntrada.get(trajetoEntrada.size() - 1).getInitialPoint().z; // pode ser do ponto final tambem
	
		// primeiro elemento dos paths de entrada
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
		 *  segundo elemento dos paths de entrada em diante
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
				double separacao = 2;
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
		
		//ultimo elemento do path de entrada
		/*
		 *  IMPLEMENTAR
		 */
		Path ultimoPath = trajetoEntrada.get(contadorPaths);
		if(ultimoPath.isLine())
		{
			Point3d vetorUnitario = GeometricOperations.unitVector(ultimoPath.getFinalPoint(), ultimoPath.getInitialPoint());
		} else if(ultimoPath.isCircular())
		{
			
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
	public ArrayList<Path> calcularMergulho() {
		ArrayList<Path> trajeto = null; //Declara��o da Vari�vel trajeto e cria��o desta
		if(bolaVert.isSelected())
		{
			trajeto = verticalPlunge();
		}
		else if (bolaRamp.isSelected())
		{
			trajeto = rampPlunge1();
		}
		for(int i = 0; i < trajeto.size(); i++)
		{
			System.out.println("pi-> " + trajeto.get(i).getInitialPoint() + "\tpf -> " + trajeto.get(i).getFinalPoint());
		}
//		for(int i = 0; i < trajetoEntrada.size(); i++)
//		{
//			trajeto.add(trajetoEntrada.get(i));
//		}
		System.out.println(GenerateTrocoidalGCode.transformPathToGCode(trajeto));
		return trajeto;
	}
	
}
