package br.UFSC.GRIMA.capp;

/**
 * 
 * @author Igor Benincá
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

public class CreatePlungeStrategy1 extends PlungeFrame2 implements ActionListener
{
	private ArrayList<Path> trajetoEntrada;
	private char plungeType;
	public CreatePlungeStrategy1(ArrayList<Path> trajetoEntrada) // Criação do metodo, vulgo objeto
	{
		this.okButton.addActionListener(this); // Dando ouvidos ao botão ok
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
	public void actionPerformed(ActionEvent event) {  // O que fazer quando o botão for escutado
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
		ArrayList<Path> trajetoT = new ArrayList<Path>(); //Apenas para inversão do array
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
				double alfaZero = Math.atan2(circularTemp.getInitialPoint().y - circularTemp.getCenter().y,circularTemp.getInitialPoint().x - circularTemp.getCenter().x );
				double dalfa = circularTemp.getAngulo()/100;
				int j = 1;
				dist = circularTemp.getInitialPoint().distance(circularTemp.getCenter())*(alfa);
				h = Math.tan(alfa)*dist;
				if (Math.abs(h) > Math.abs(ht))
					ht = ht + h;
				else
					ht = ht + h;
				
				while(trajeto.get(i-1).getInitialPoint().z < ht)
				{
					System.err.println("Ht acumulados "+trajeto.get(i-1).getFinalPoint().z);
					LinearPath p0 = new LinearPath (new Point3d(circularTemp.getCenter().x + (circularTemp.getRadius())*Math.cos(alfaZero + dalfa*j), circularTemp.getCenter().y + (circularTemp.getRadius())*Math.sin(alfaZero + dalfa*j), trajeto.get(i-1).getInitialPoint().z + nSeparacoes), trajeto.get(i-1).getInitialPoint() );
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
					System.out.println("Distância dos pontos->" + dist);
					vector = new Point3d(trajeto.get(trajeto.size()-1).getFinalPoint().x - trajeto.get(trajeto.size()-1).getInitialPoint().x, trajeto.get(trajeto.size()-1).getFinalPoint().y - trajeto.get(trajeto.size()-1).getInitialPoint().y, trajeto.get(trajeto.size()-1).getFinalPoint().z - trajeto.get(trajeto.size()-1).getInitialPoint().z);
					vector = new Point3d((vector.x/dist), (vector.y/dist), (vector.z/dist)); // vetor unitário
					ht = ht - retractTotal;
					System.out.println("Distância h da dife retract->" + ht);
					dist = dist - (ht/Math.sin(alfa)); // tanto que eu preciso multiplicar meu vetor unitário.
					System.out.println("Distância mult->" + dist);
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

	public ArrayList<Path> calcularMergulho() {
		ArrayList<Path> trajeto = null; //Declaração da Variável trajeto e criação desta
		if(bolaVert.isSelected())
		{
			trajeto = verticalPlunge();
		}
		else if (bolaRamp.isSelected())
		{
			trajeto = rampPlunge();
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
