package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Test;

import com.lksoft.util.logging.SystemOutHandler;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.OperationBezierSurface;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.operationsVector.OperationVectorTest;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;

public class MovimentacaoSuperficieBezierTest {
	ArrayList<Point3d> poligono = new ArrayList<Point3d>();
	int a=0;
	double plano=21;
	ArrayList<Point3d> subArrayPossivelX = new ArrayList<Point3d>();
	ArrayList<Point3d> subArrayPossivelY = new ArrayList<Point3d>();
	double[][][] control_points = {
			{ { -3, -3, 0 }, { -1, -3, 0 }, { 1, -3, 0 }, { 3, -3, 0 } },
			{ { -3, -1, 0 }, { -1, -1, 0 }, { 1, -1, 0 }, { 3, -1, 0 } },
			{ { -3, 1, 0 }, { -1, 1, 0 }, { 1, 1, 0 }, { 3, 1, 0 } },
			{ { -3, 3, 0 }, { -1, 3, 0 }, { 1, 3, 0 }, { 3, 3, 0 } } 
			};
	public Point3d [][] controlVertex = new Point3d[4][4];
	
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
	
//	Point3d p00 = new Point3d(0, 0, 40);
//	Point3d p01 = new Point3d(30, 0, 0);
//	Point3d p02 = new Point3d(60, 0, 0);
//	Point3d p03 = new Point3d(90, 0, 0);
//	Point3d p10 = new Point3d(0, 30, 0);
//	Point3d p11 = new Point3d(30, 30, 20);
//	Point3d p12 = new Point3d(60, 30, 40);
//	Point3d p13 = new Point3d(90, 30, 00);
//	Point3d p20 = new Point3d(0, 60, 0);
//	Point3d p21 = new Point3d(30, 60, 40);
//	Point3d p22 = new Point3d(60, 60, 20);
//	Point3d p23 = new Point3d(90, 60, 0);
//	Point3d p30 = new Point3d(0, 90, 0);
//	Point3d p31 = new Point3d(30, 90, 0);
//	Point3d p32 = new Point3d(60, 90, 0);
//	Point3d p33 = new Point3d(90, 90, 40);
	
	Point3d p00 = new Point3d(0, 0, 0);
	Point3d p01 = new Point3d(30, 0, 40);
	Point3d p02 = new Point3d(60, 0, 40);
	Point3d p03 = new Point3d(90, 0, 0);
	Point3d p10 = new Point3d(0, 30, 40);
	Point3d p11 = new Point3d(30, 30, 0);
	Point3d p12 = new Point3d(60, 30, 0);
	Point3d p13 = new Point3d(90, 30, 40);
	Point3d p20 = new Point3d(0, 60, 40);
	Point3d p21 = new Point3d(30, 60, 0);
	Point3d p22 = new Point3d(60, 60, 0);
	Point3d p23 = new Point3d(90, 60, 40);
	Point3d p30 = new Point3d(0, 90, 0);
	Point3d p31 = new Point3d(30, 90, 40);
	Point3d p32 = new Point3d(60, 90, 40);
	Point3d p33 = new Point3d(90, 90, 0);
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

		
		for(int j=0;j<malha.length;j++){
			for(int i=0;i<malha[j].length;i++){
				if(malha[i][j].getZ()<=plano){
					if(malha[i][j].getZ()>=plano-0.25){
						subArray.add(new Point3d(malha[i][j].x,malha[i][j].y,plano));
						continue;
					}
				}
				else if((i==0 || i==malha[j].length-1 || j==0 || j==malha.length-1) && malha[i][j].getZ()<plano){
					subArray.add(new Point3d(malha[i][j].x,malha[i][j].y,plano));
					continue;
				}
			}
		}
		for(int i=0;i<malha.length;i++){
			for(int j=0;j<malha[i].length;j++){
				if(malha[i][j].getZ()<plano){
					subArrayPossivelY.add(new Point3d(malha[i][j].x,malha[i][j].y,plano));
				}
				if(malha[j][i].getZ()<plano){
					subArrayPossivelX.add(new Point3d(malha[j][i].x,malha[j][i].y,plano));
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
				
		
		
		poligono.add(subArray.get(u)); 
		subArray.remove(u);
		a=subArray.size();
		k=0;

		for(int j=0;j<a;j++){
			menorDistancia=100;
			for(int i=0;i<subArray.size();i++){
				if(poligono.get(j)!=subArray.get(i)){
//					if(j!=0){
//						if(poligono.get(j-1)!=subArray.get(i)){
//							distanciaTemp=OperationsVector.distanceVector(poligono.get(j),subArray.get(i));
//						}
//					}
//					else{
					distanciaTemp=OperationsVector.distanceVector(poligono.get(j),subArray.get(i));		
//					}

					if(distanciaTemp<menorDistancia){
						menorDistancia=distanciaTemp;
						k=i;
					}
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
			GeneralPath r = new GeneralPath();
			ArrayList<Ellipse2D> e = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> w = new ArrayList<Ellipse2D>();
			double largura, comprimento;
			Point3d[][] matriz = new Point3d[98][98];
			
			painelTest(){
				r.moveTo(5*poligono.get(0).getX(),5*poligono.get(0).getY());
				for(int i=1;i<poligono.size()-1;i++){
					if(OperationsVector.distanceVector(poligono.get(i-1), poligono.get(i))>1){
						r.moveTo(5*poligono.get(i).getX(),5*poligono.get(i).getY());
					}
					else{
						r.lineTo(5*poligono.get(i).getX(),5*poligono.get(i).getY());
					}
				}
				//p.closePath();
				largura = (double) controlVertex[3][3].getY()-controlVertex[0][0].getY();
				comprimento = (double) controlVertex[3][3].getX()-controlVertex[0][0].getX();
				double deltaX,deltaY;
				deltaX=comprimento/100;
				deltaY=largura/100;
				for(int i=0;i<98;i++){
					for(int k=0;k<98;k++){
						matriz[i][k]=new Point3d(deltaX*(k+1),deltaY*(i+1),plano);
//						System.out.println(matriz[i][k].y);
					}
				}
				r.moveTo(0,0);
				r.lineTo(5*comprimento, 0);
				r.lineTo(5*comprimento, 5*largura);
				r.lineTo(0, 5*largura);
				r.lineTo(0, 0);

				LimitedLine l1 = new LimitedLine(new Point3d(0,0,plano), new Point3d(comprimento,0,plano));
				LimitedLine l2 = new LimitedLine(new Point3d(comprimento,0,plano), new Point3d(comprimento,largura,plano));
				LimitedLine l3 = new LimitedLine(new Point3d(comprimento,largura,plano), new Point3d(0,largura,plano));
				LimitedLine l4 = new LimitedLine(new Point3d(0,largura,plano), new Point3d(0,0,plano));
				double distanciaTmp;
				ArrayList<Double> menorDistanciaX = new ArrayList<Double>();
				ArrayList<Double> menorDistanciaY = new ArrayList<Double>();

				for(int i=0;i<subArrayPossivelY.size();i++){
					/*CALCULO DA MENOR DISTANCIA VARRENDO NO SENTIDO Y*/
					distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l1, subArrayPossivelY.get(i));
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l2, subArrayPossivelY.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l2, subArrayPossivelY.get(i));
					}
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l3, subArrayPossivelY.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l3, subArrayPossivelY.get(i));
					}
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l4, subArrayPossivelY.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l4, subArrayPossivelY.get(i));
					}
					for(int k=0;k<poligono.size();k++){
						if(OperationsVector.distanceVector(poligono.get(k), subArrayPossivelY.get(i))<distanciaTmp){
							distanciaTmp=OperationsVector.distanceVector(poligono.get(k), subArrayPossivelY.get(i));
						}
					}
					menorDistanciaY.add(distanciaTmp);

					/*CALCULO DA MENOR DISTANCIA VARRENDO NO SENTIDO X*/
					distanciaTmp=100;
					distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l1, subArrayPossivelX.get(i));
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l2, subArrayPossivelX.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l2, subArrayPossivelX.get(i));
					}
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l3, subArrayPossivelX.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l3, subArrayPossivelX.get(i));
					}
					if(OperationsVector.calculateDistanceBetweenLineAndPoint(l4, subArrayPossivelX.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.calculateDistanceBetweenLineAndPoint(l4, subArrayPossivelX.get(i));
					}
					for(int k=0;k<poligono.size();k++){
						if(OperationsVector.distanceVector(poligono.get(k), subArrayPossivelX.get(i))<distanciaTmp){
							distanciaTmp=OperationsVector.distanceVector(poligono.get(k), subArrayPossivelX.get(i));
						}
					}
					menorDistanciaX.add(distanciaTmp);
//					System.out.println(subArrayPossivelX.get(i).x + "\t\t" + subArrayPossivelX.get(i).y);
//					System.out.println(subArrayPossivelX.get(i).x);
//					System.out.println(menorDistanciaX.get(i));
				}
				
//				System.out.println(menorDistanciaX.size());
//				System.out.println(menorDistanciaY.size());
				
				
				double diametroFerramenta = 10, maiorMenorDistancia=0;
				int numeroDeCortes;
				ArrayList<ArrayList<Point3d>> pontos = new ArrayList<ArrayList<Point3d>>();
				ArrayList<Point3d> pontos2 = null;

				for(int i=0;i<menorDistanciaX.size();i++){
					if(maiorMenorDistancia<menorDistanciaX.get(i)){
						maiorMenorDistancia=menorDistanciaX.get(i);
					}
				}
				
				numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametroFerramenta));
				
				for(int i=0;i<numeroDeCortes;i++){
					pontos2 = new ArrayList<Point3d>();
					for(int k=0;k<menorDistanciaX.size();k++){
						if(menorDistanciaX.get(k)<=(i+1)*(0.75*diametroFerramenta) && menorDistanciaX.get(k)>=(i+1)*(0.75*diametroFerramenta)-0.5){
							pontos2.add(subArrayPossivelX.get(k));
						}
					}
					pontos.add(pontos2);
				}
				
				
				
				
				ArrayList<ArrayList<Point3d>> pontosOrdenados = new ArrayList<ArrayList<Point3d>>();
				ArrayList<Point3d> temp = null;
				double menorDistancia=100;
				int t=0,m;
				double distanciaTemp;
				
				for(int i=0;i<pontos.size();i++){
					distanciaTemp=OperationsVector.distanceVector(pontos.get(i).get(0),pontos.get(i).get(1));
					temp = new ArrayList<Point3d>();
					temp.add(pontos.get(i).get(0));
					m=pontos.get(i).size();
					for(int k=0;k<m;k++){
						menorDistancia=100;
						for(int j=0;j<pontos.get(i).size();j++){
							if(temp.get(k)!=pontos.get(i).get(j)){
								distanciaTemp=OperationsVector.distanceVector(temp.get(k), pontos.get(i).get(j));
								
								if(distanciaTemp<menorDistancia){
									menorDistancia=distanciaTemp;							
									t=j;
								}
							}
						}
						temp.add(pontos.get(i).get(t));
						pontos.get(i).remove(t);
					}
					pontosOrdenados.add(temp);
				}
								
				
				//ERRADO
				ArrayList<Point3d> testeX = new ArrayList<Point3d>();
				ArrayList<Point3d> testeY = new ArrayList<Point3d>();
				ArrayList<Point3d> maximos = new ArrayList<Point3d>();

				for(int i=1;i<menorDistanciaX.size();i++){
					if(i!=menorDistanciaX.size()-1){
						if(menorDistanciaX.get(i-1)<menorDistanciaX.get(i) &&
							menorDistanciaX.get(i+1)<menorDistanciaX.get(i) &&
							(menorDistanciaX.get(i-1)+0.05<menorDistanciaX.get(i) ||
							menorDistanciaX.get(i+1)+0.05<menorDistanciaX.get(i))){
							maximos.add(subArrayPossivelX.get(i));
						}
						if(menorDistanciaY.get(i-1)<menorDistanciaY.get(i) &&
								menorDistanciaY.get(i+1)<menorDistanciaY.get(i) &&
							(menorDistanciaY.get(i-1)+0.05<menorDistanciaY.get(i) ||
							menorDistanciaY.get(i+1)+0.05<menorDistanciaY.get(i))){
								maximos.add(subArrayPossivelY.get(i));
							}
					}
				}
//				for(int i=0;i<testeY.size();i++){
//					for(int k=0;k<testeX.size();k++){
//						if(Math.abs(testeY.get(i).x-testeX.get(k).x)<0.5 &&
//							Math.abs(testeY.get(i).y-testeX.get(k).y)<0.5){
//							maximos.add(testeX.get(k));
//						}
//					}
//				}
				
				
//				for(int i=1;i<menorDistanciaY.size();i++){		
//					if(i!=menorDistanciaY.size()-1){
//						if(menorDistanciaY.get(i-1)<menorDistanciaY.get(i) &&
//								menorDistanciaY.get(i+1)<menorDistanciaY.get(i)){
//								maximos.add(subArrayPossivelY.get(i));
//								//System.out.println(menorDistancia.get(i));
//						}
//					}
//				}
				
				for(int k=0;k<maximos.size();k++){
					p.moveTo(5*maximos.get(k).getX(),5*maximos.get(k).getY());
					for(int i=1;i<maximos.size();i++){
//						if(OperationsVector.distanceVector(maximos.get(i-1), maximos.get(i))>1){
//							p.moveTo(5*maximos.get(i).getX(),5*maximos.get(i).getY());
//						}
//						else{
							//p.lineTo(5*maximos.get(i).getX(),5*maximos.get(i).getY());
							w.add(new Ellipse2D.Double(5*maximos.get(i).getX(),5*maximos.get(i).getY(),1,1));
//						}
					}
				}
				
				for(int k=0;k<pontosOrdenados.size();k++){
					p.moveTo(5*pontosOrdenados.get(k).get(0).getX(),5*pontosOrdenados.get(k).get(0).getY());
					for(int i=1;i<pontosOrdenados.get(k).size();i++){
//						if(OperationsVector.distanceVector(pontosOrdenados.get(k).get(i-1), pontosOrdenados.get(k).get(i))>1){
//							p.moveTo(5*pontosOrdenados.get(k).get(i).getX(),5*pontosOrdenados.get(k).get(i).getY());
//						}
//						else{
							//p.lineTo(5*pontosOrdenados.get(k).get(i).getX(),5*pontosOrdenados.get(k).get(i).getY());
							e.add(new Ellipse2D.Double(5*pontosOrdenados.get(k).get(i).getX(),5*pontosOrdenados.get(k).get(i).getY(),2,2));
//						}
					}
				}
							
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
				//g2d.draw(new Ellipse2D.Double());
				g2d.setColor(new Color(251, 100, 100));
				g2d.draw(p);
				g2d.setColor(new Color(100,100,251));
				g2d.draw(r);

				g2d.setColor(new Color(100, 251, 100));
				for(int i=0;i<e.size();i++){
					g2d.draw(e.get(i)); //path
				}
				g2d.setColor(new Color(251, 100, 100));
				for(int i=0;i<w.size();i++){
					g2d.draw(w.get(i));
				}
				
				
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
