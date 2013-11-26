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
	Graphics2D g2d;
	public ArrayList<Workingstep> workingsteps = new ArrayList<Workingstep>();
	public ArrayList<Ellipse2D> clickWst;
	public ArrayList<Integer> IdBolinha ;
	
	public WSPrecedencesPanel(ProjetoSF projetoSF){
	
		for(int i = 0; i < projetoSF.getProjeto().getWorkingsteps().elementAt(0).size(); i++)
		{
			workingsteps.add(projetoSF.getProjeto().getWorkingsteps().elementAt(0).get(i));
		}

	}
	void ClickBolinhas(ArrayList<Ellipse2D> clickBolinhas){
		
		clickWst = clickBolinhas;
	}
	ArrayList<Ellipse2D> ClickWorkingsteps(){
		
		return clickWst;
	}
	void setIdClickBolinha (ArrayList<Integer> click) {
		
		IdBolinha = click;
	}
	ArrayList<Integer> getIdClickBolinha (){
		
		return IdBolinha;
	}
	
	
	public void paintComponent(Graphics g)
	{ 
	
	//	int xDimension, yDimension;
		Dimension dimension = new Dimension(1000, 1000);
		setPreferredSize(dimension);
		this.revalidate();

		int x = 0, y = 0, nivelArvore = 0, contY = 0, q = 0;
		
		Workingstep wstTemp = null;
		Ellipse2D bolinha = null;
		ArrayList<ArrayList<Workingstep>> novoWorkingsteps = new ArrayList<ArrayList<Workingstep>>();
		ArrayList<Ellipse2D> bolinhas = new ArrayList<Ellipse2D>();
		ArrayList<Integer> IdBolinhaTemp = new ArrayList<Integer>();
		
		
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		// Enchendo Array de Array
		
		for(int i = 0; i < workingsteps.size(); i++){

			wstTemp = workingsteps.get(i);
			ArrayList<Workingstep> nivelTmp = new ArrayList<Workingstep>();
			if(workingsteps.get(i).getWorkingstepPrecedente() == null){
		
				nivelTmp.add(workingsteps.get(i));
				novoWorkingsteps.add(nivelTmp);
				workingsteps.get(i).setIndiceArvore(nivelArvore);
				workingsteps.get(i).setWSPrecedenteID(i*10 + 10);
				nivelArvore++;
				
			}
			
			for(int j = 0; j < workingsteps.size(); j++){
				
				if(workingsteps.get(j).getWorkingstepPrecedente() == wstTemp){
					
					novoWorkingsteps.get(wstTemp.getIndiceArvore()).add(workingsteps.get(j));
					workingsteps.get(j).setWSPrecedenteID(j*10+10);
					workingsteps.get(j).setIndiceArvore(wstTemp.getIndiceArvore());
				
				}
			}
			
		}
		
		// Imprimindo Ws Precendetes
		
		for(int w = 0; w < nivelArvore; w++){
			
		
		for(int i = 0; i < novoWorkingsteps.get(w).size(); i++){

			wstTemp = novoWorkingsteps.get(w).get(i);
			
			if(novoWorkingsteps.get(w).get(i).getWorkingstepPrecedente() == null){
		
				x = 0;
				y = contY;
				g2d.setColor(Color.BLUE);
		    	bolinha = new Ellipse2D.Double(x*80 + 30,(contY)*60 + 20 , 40, 40);
				bolinhas.add(bolinha);
				q++;
				g2d.fill(bolinha);

				int id = novoWorkingsteps.get(w).get(i).getWSPrecedenteID();
				//System.out.println("-------------------->> id teste = "+id);
				IdBolinhaTemp.add(id);
							
				String strID = "" + id;
				g2d.setColor(new Color(255, 255, 255));
				g2d.setFont(new Font("Consolas", Font.BOLD, 16));
				g2d.drawString(strID, x*80 + 40, (contY)*60 + 43);
				
				novoWorkingsteps.get(w).get(i).setNivelXWSPrecedente(x);
				novoWorkingsteps.get(w).get(i).setNivelYWSPrecedente(y);
				
			}
			
			y = wstTemp.getNivelYWSPrecedente();
			x = wstTemp.getNivelXWSPrecedente();
			x++;	
		
			for(int j = 0; j < novoWorkingsteps.get(w).size(); j++){
				
				if(novoWorkingsteps.get(w).get(j).getWorkingstepPrecedente() == wstTemp){
					
					
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
			
					int id = novoWorkingsteps.get(w).get(j).getWSPrecedenteID();
					
					IdBolinhaTemp.add(id);
					String strID = "" + id;
					g2d.setColor(new Color(255, 255, 255));
					g2d.drawString(strID, x*80 + 40, y*60 + 43);
					
					novoWorkingsteps.get(w).get(j).setNivelXWSPrecedente(x);
					novoWorkingsteps.get(w).get(j).setNivelYWSPrecedente(y);
			
					y++;
				}
			}
			if(contY < y) contY = y;
			y = 0;
			}
			
		}
		ClickBolinhas(bolinhas);
		setIdClickBolinha(IdBolinhaTemp);
		
	}	
	
}
