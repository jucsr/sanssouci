package br.UFSC.GRIMA.cad;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.projeto.Projeto;


public class DrawingPanel extends JPanel  {
	

	
	ArrayList<Point3d> ArrayApoios;
	

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Graphics2D g2d;
	double panelWidth;
	double panelHeight;
	

	double pieceWidth; 
	double pieceHeight; 
	int raioApoio;

	Projeto projAux;
	Face faceXY;
	
	ArrayList<Feature> arrayFeat = new ArrayList<Feature>();
	
	public DrawingPanel (double panelWidth, double panelHeight, double pieceWidth, double pieceHeight, 
			ArrayList<Point3d> ArrayA, int raioCp, Projeto projeto){
		
		System.out.println("Criando dPanel");
		
		ArrayApoios = ArrayA;
		
		projAux = projeto;
		
		System.out.println("Size: " + ArrayApoios.size());
		
		this.pieceWidth = pieceWidth * 100 ;
		this.pieceHeight = pieceHeight * 100;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight ;
		this.raioApoio = raioCp;
		
		faceXY =  (Face)projeto.getBloco().faces.get(0);
		
		Vector<Feature> vecFeatures = faceXY.features;
		
		for(int i = 0; i < vecFeatures.size() ; i++)
		{

			Feature featAux = vecFeatures.get(i);
					
			arrayFeat.add(featAux);
			
		}
				
		repaint();
		System.out.println("Depois do repaint");
	} 

	  	
	public void paintComponent(Graphics g) {
		
		
			super.paintComponent(g);    
			g2d = (Graphics2D) g;
						
			setGraphicOptions();
	    	setAxis();
	    	drawPiece();
	    	drawPoints();
	    	
	    	DesenhadorDeFaces desenFace = new DesenhadorDeFaces(projAux);
	    	
	    	//DesenhadorDeFeatures desenFeat = new DesenhadorDeFeatures();
	   
	    	//desenFeat.desenharFeature(Feature feature, faceXY, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d)
	    	
	    	Feature featAux = arrayFeat.get(0);
	    	
	    	Point org = new Point(0,0);
	    	
	    	//desenFeat.desenharFeature(featAux, faceXY, 0, org, true, g2d);
	    	
	   // 	drawDrill();
	    }  
			
	
	public void setGraphicOptions(){
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public void setAxis() {
		g2d.translate(0, (int)Math.round(panelHeight));
		g2d.rotate(-Math.PI/2);
		g2d.scale(0.05,0.05);
		
	}
	
	public void drawPiece()
	{
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0,0,(int)Math.round(pieceHeight),(int)Math.round(pieceWidth));
		
        
        
	}
	
	public void drawPoints(){
		
	g2d.setColor(Color.RED);
	
	for(int i = 0; i < ArrayApoios.size(); i++)
	{
		Point3d pAux = ArrayApoios.get(i);
		g2d.fillOval((int)Math.round(pAux.getY() - raioApoio) * 100, (int)Math.round(pAux.getX() - raioApoio) * 100, raioApoio * 200, raioApoio *200);
	}
	
	
	//g2d.fillOval((int)Math.round(ponto1.getY()) * 100, (int)Math.round(ponto1.getX()) * 100, 100, 100);
	//g2d.fillOval((int)Math.round(ponto2.getY()) * 100, (int)Math.round(ponto2.getX()) * 100, 100, 100);
	//g2d.fillOval((int)Math.round(ponto3.getY()) * 100, (int)Math.round(ponto3.getX()) * 100, 100, 100);
	
	
	}
	
//	public void drawDrill(){
//	
//		g2d.setColor(Color.DARK_GRAY);
//		g2d.setStroke(new BasicStroke(1F));
//		g2d.fillOval(((int)Math.round(brocaY1)- (int)Math.round(radius1)), ((int)Math.round(brocaX1) - (int)Math.round(radius1)),(int)Math.round(drill1Diameter) ,(int)Math.round(drill1Diameter) );
//		g2d.fillOval(((int)Math.round(brocaY2)- (int)Math.round(radius2)), ((int)Math.round(brocaX2) -(int)Math.round(radius2)),(int)Math.round(drill2Diameter) ,(int)Math.round(drill2Diameter) );
//		g2d.fillOval(((int)Math.round(brocaY3)- (int)Math.round(radius3)), ((int)Math.round(brocaX3) -(int)Math.round(radius3)),(int)Math.round(drill3Diameter) ,(int)Math.round(drill3Diameter) );
//		g2d.fillOval(((int)Math.round(brocaY4)- (int)Math.round(radius4)), ((int)Math.round(brocaX4) -(int)Math.round(radius4)),(int)Math.round(drill4Diameter) ,(int)Math.round(drill4Diameter) );
//		g2d.fillOval(((int)Math.round(brocaY5)- (int)Math.round(radius5)), ((int)Math.round(brocaX5) -(int)Math.round(radius5)),(int)Math.round(drill5Diameter) ,(int)Math.round(drill5Diameter) );
//		g2d.fillOval(((int)Math.round(brocaY6)- (int)Math.round(radius6)), ((int)Math.round(brocaX6) -(int)Math.round(radius6)),(int)Math.round(drill6Diameter) ,(int)Math.round(drill6Diameter) );
//	}
	
	
	

	

}
