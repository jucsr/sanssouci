package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.visual.PlungeFrame1;
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
		pFinal = paths.get(0).getFinalPoint();
		pInicial = paths.get(0).getInitialPoint();
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
		double distP = Math.sqrt((x0 - x1) + (y0 - y1) + (pInicial.z - pFinal.z));
		double beta = Math.atan((y1 - y0)/(x1 - x0)); // beta = arco tangente Y/X
		 
		if (a == 'v')
		{
		
		}
		else if (a == 'r')
		{
			System.out.println("distP: " + distP );
			System.out.println("x0/x1: " +x0 +"/"+ x1);
			System.out.println("y0/y1: "+ y0 +"/"+ y1);
			double course = high/Math.tan(angle);
			System.out.println("course: " + course );
			if (course <= distP)
			{
				double xK = Math.sqrt(Math.pow(course, 2)/(Math.pow(Math.tan(beta), 2))+1) + x0; //x do pTool
				double yK = ((y1-y0)*(xK-x0)/(x1-x0))+y0; //y do pTool
				pTool = new Point3d(xK,yK,retractPlane);
				System.out.println("ponto: " + pTool);
			}
		}
		else if (a == 'z')
		{
			
		}
		else if (a == 'h')
		{
			
		}
	}
}
