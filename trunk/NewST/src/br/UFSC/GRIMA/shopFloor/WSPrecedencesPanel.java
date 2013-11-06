package br.UFSC.GRIMA.shopFloor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.UFSC.GRIMA.capp.Workingstep;

public class WSPrecedencesPanel extends JPanel
{
	public ProjetoSF projetoSF;
	public ArrayList<Workingstep> workingsteps = new ArrayList<Workingstep>();
	Graphics2D g2d;
	
	public WSPrecedencesPanel(ProjetoSF projetoSF){
	
		for(int i = 0; i < projetoSF.getProjeto().getWorkingsteps().elementAt(0).size(); i++)
		{
			workingsteps.add(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(i));
		}
	}


	public void paintComponent(Graphics g)
	{ 
		int xDimension, yDimension;
		Dimension dimension = new Dimension(1000, 1000);
		setPreferredSize(dimension);
		this.revalidate();

		int x = 0, y = 0, contWsTemp = 0, contY = 0;
		Workingstep wstTemp = null;
		ArrayList<Ellipse2D> bolinhas = new ArrayList<Ellipse2D>();
		Ellipse2D bolinha = null, bolinhaTeste = null;
		
		
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		
		for(int i = 0; i < workingsteps.size(); i++){

			wstTemp = workingsteps.get(i);
			
			if(workingsteps.get(i).getWorkingstepPrecedente() == null){
		
				x = 0;
				y = contY;
				g2d.setColor(Color.BLUE);
		    	bolinha = new Ellipse2D.Double(x*80 + 30,(contY)*60 + 20 , 40, 40);
				bolinhas.add(bolinha);
				g2d.fill(bolinha);

				int id = 10 + i*10;

				System.err.println("------------->> ID 1 = "+id);				
				String strID = "" + id;
				g2d.setColor(new Color(255, 255, 255));
				g2d.setFont(new Font("Consolas", Font.BOLD, 16));
				g2d.drawString(strID, x*80 + 40, (contY)*60 + 43);
				
				workingsteps.get(i).setNivelXWSPrecedente(x);
				workingsteps.get(i).setNivelYWSPrecedente(y);
				
			}
			
			y = wstTemp.getNivelYWSPrecedente();
			x = wstTemp.getNivelXWSPrecedente();
			x++;	
	
			
			for(int j = 0; j < workingsteps.size(); j++){
				
				if(workingsteps.get(j).getWorkingstepPrecedente() == wstTemp){
					
					g2d.setColor(Color.BLUE);
					bolinha = new Ellipse2D.Double(x*80 + 30, y*60 + 20, 40, 40);
					
					for(int l = 0; l < bolinhas.size(); l++){
						
						if(bolinha.equals(bolinhas.get(l))){
							
							y++;
							bolinha = new Ellipse2D.Double(x*80 + 30, y*60 + 20, 40, 40);
							g2d.fill(bolinha);
							
						}
					}
					bolinhas.add(bolinha);
						
					g2d.fill(bolinha);
					g2d.setStroke(new BasicStroke(2f));
					g2d.drawLine(x*80 + 40, y*60 + 43, wstTemp.getNivelXWSPrecedente()*80 + 40, wstTemp.getNivelYWSPrecedente()*60 + 43);
			
					int id = 10 + j*10;
					System.err.println("------------->> ID 2 = "+id);		
					String strID = "" + id;
					g2d.setColor(new Color(255, 255, 255));
					g2d.drawString(strID, x*80 + 40, y*60 + 43);
					
					workingsteps.get(j).setNivelXWSPrecedente(x);
					workingsteps.get(j).setNivelYWSPrecedente(y);
					y++;
				
				}
			}
			if(contY < y) contY = y;
			y = 0;
		}
	}
}
