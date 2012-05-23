package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.OperationBezierSurface;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;

public class MovimentacaoSuperficieBezierTest {
	ArrayList<Point3d> poligono = new ArrayList<Point3d>();
	int a=0;
	double[][][] control_points = {
			{ { -3, -3, 0 }, { -1, -3, 0 }, { 1, -3, 0 }, { 3, -3, 0 } },
			{ { -3, -1, 0 }, { -1, -1, 0 }, { 1, -1, 0 }, { 3, -1, 0 } },
			{ { -3, 1, 0 }, { -1, 1, 0 }, { 1, 1, 0 }, { 3, 1, 0 } },
			{ { -3, 3, 0 }, { -1, 3, 0 }, { 1, 3, 0 }, { 3, 3, 0 } } 
			};
	Point3d [][] controlVertex = new Point3d[4][4];
	
//	Point3d p00 = new Point3d(0, 0, 30);
//	Point3d p01 = new Point3d(30, 0, 0);
//	Point3d p02 = new Point3d(60, 0, 0);
//	Point3d p03 = new Point3d(90, 0, 30);
//	Point3d p10 = new Point3d(0, 30, 0);
//	Point3d p11 = new Point3d(30, 30, 0);
//	Point3d p12 = new Point3d(60, 30, 0);
//	Point3d p13 = new Point3d(90, 30, 0);
//	Point3d p20 = new Point3d(0, 60, 0);
//	Point3d p21 = new Point3d(30, 60, 0);
//	Point3d p22 = new Point3d(60, 60, 0);
//	Point3d p23 = new Point3d(90, 60, 0);
//	Point3d p30 = new Point3d(0, 90, 30);
//	Point3d p31 = new Point3d(30, 90, 0);
//	Point3d p32 = new Point3d(60, 90, 0);
//	Point3d p33 = new Point3d(90, 90, 30);
	
	Point3d p00 = new Point3d(0, 0, 40);
	Point3d p01 = new Point3d(30, 0, 40);
	Point3d p02 = new Point3d(60, 0, 40);
	Point3d p03 = new Point3d(90, 0, 40);
	Point3d p10 = new Point3d(0, 30, 40);
	Point3d p11 = new Point3d(30, 30, 0);
	Point3d p12 = new Point3d(60, 30, 0);
	Point3d p13 = new Point3d(90, 30, 00);
	Point3d p20 = new Point3d(0, 60, 40);
	Point3d p21 = new Point3d(30, 60, -10);
	Point3d p22 = new Point3d(60, 60, -10);
	Point3d p23 = new Point3d(90, 60, 00);
	Point3d p30 = new Point3d(0, 90, 40);
	Point3d p31 = new Point3d(30, 90, 40);
	Point3d p32 = new Point3d(60, 90, 40);
	Point3d p33 = new Point3d(90, 90, 40);
	
	@Test
	public void bezierTest()
	{
		controlVertex[0][0] = p00;
		controlVertex[0][1] = p01;
		controlVertex[0][2] = p02;
		controlVertex[0][3] = p03;
		controlVertex[1][0] = p10;
		controlVertex[1][1] = p11;
		controlVertex[1][2] = p12;
		controlVertex[1][3] = p13;
		controlVertex[2][0] = p20;
		controlVertex[2][1] = p21;
		controlVertex[2][2] = p22;
		controlVertex[2][3] = p23;
		controlVertex[3][0] = p30;
		controlVertex[3][1] = p31;
		controlVertex[3][2] = p32;
		controlVertex[3][3] = p33;
		
		BezierSurface b = new BezierSurface(controlVertex, 200,200);
//		System.out.println(b.getMeshArray()[0][0]);
		for(int i = 0; i < b.getMeshArray().length; i++)
		{
			for(int j = 0; j < b.getMeshArray()[i].length; j++)
			{
				//System.out.println("[" + i + "]" + "[" + j + "]" + b.getMeshArray()[i][j]);
			}
		}
//		for(int j = 0; j < b.getMeshArray()[0].length; j++)
//		{
//			System.out.println(b.getMeshArray()[0][j]);
//			
//		}
		
		/*
		 * PONTOS DA PERIFERIA EM UM N�VEL Z
		 */
		Point3d malha[][] = b.getMeshArray();
		ArrayList<Point3d> subArray = new ArrayList<Point3d>();
		double plano=20;

		
		for(int i=0;i<malha.length;i++){
			for(int j=0;j<malha[i].length;j++){
				if(malha[i][j].getZ()<=plano+0.5){
					if(malha[i][j].getZ()>=plano){
						subArray.add(malha[i][j]);
						continue;
					}
				}
			}
		}
		/*****************************************/
		/*
		 * JUNTAR OS PONTOS MAIS PROXIMOS DA PERIFERIA
		 */
		
		double distanciaTemp=OperationsVector.distanceVector(subArray.get(0),subArray.get(1)),
				menorDistancia;
		int u=0,k=0;

		poligono.add(subArray.get(0)); 
		subArray.remove(0);
		a=subArray.size();
		k=0;
		
			for(int j=0;j<a;j++){
				menorDistancia=100;
				for(int i=0;i<subArray.size();i++){
					if(poligono.get(j)!=subArray.get(i)){
						if(j!=0){
							if(poligono.get(j-1)!=subArray.get(i)){
								distanciaTemp=OperationsVector.distanceVector(poligono.get(j),subArray.get(i));
							}
						}
						else{
							distanciaTemp=OperationsVector.distanceVector(poligono.get(j),subArray.get(i));		
						}
					}
					
					if(distanciaTemp<menorDistancia){
						menorDistancia=distanciaTemp;
						k=i;
					}
				}
					poligono.add(subArray.get(k));
					subArray.remove(k);
			}
		
		
		JFrame frame1 = new JFrame("BEZIER SURFACE");
		frame1.setBounds(100, 100, 800, 500);
		JPanel painel1 = new JPanel();
		painel1.repaint();
		painel1.setLayout(new BorderLayout());
		frame1.getContentPane().add(painel1);
		J3D j3d = new J3D(painel1);
		OperationBezierSurface operation = new OperationBezierSurface("BEZIER_SURFACE", controlVertex, 11, 11);
		
		j3d.addSolid(operation);
		
		frame1.setVisible(true);
		
		
		
		class painelTest extends JPanel{
			GeneralPath p = new GeneralPath();
			
			painelTest(){
				System.out.println(poligono.get(0));
				p.moveTo(5*poligono.get(0).getX(),5*poligono.get(0).getY());
				for(int i=1;i<poligono.size()-1;i++){
					System.out.println(poligono.get(i));
					p.lineTo(5*poligono.get(i).getX(),5*poligono.get(i).getY());
				}
				p.closePath();
			}
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				Graphics2D g2d = (Graphics2D)g;
				g2d.translate(25, 475);
				g2d.scale(1, -1);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.black);
				g2d.draw(new Rectangle2D.Double(0, 0, 90 * 5, 90 * 5));
				g2d.setColor(new Color(100, 125, 251));
				g2d.draw(p);
				
			}
		}
		JFrame frame = new JFrame("Poligono");
		painelTest painel = new painelTest();
		frame.setSize(510, 535);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
		
		
	}
	
	
}
