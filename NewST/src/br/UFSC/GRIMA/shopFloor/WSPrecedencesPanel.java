package br.UFSC.GRIMA.shopFloor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
		int x = 0, y = 0, z = 0, precedence = 0, contWsTemp = 0, contY = 0, pintou = 0, dimArvore = 0;
		Workingstep wstTemp = null;
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		for(int t = 0; t < workingsteps.size(); t++){
			
			int dimensao = workingsteps.get(t).getWorkingstepsPoscedentesDiretos(workingsteps).size();
			if(dimArvore < dimensao){
				
				dimArvore = dimensao;
			}
		}
		
	
		for(int i = 0; i < workingsteps.size(); i++){
			
			if(workingsteps.get(i).getWorkingstepPrecedente() == null){
			
				
				x = 0;
				y = contY+dimArvore/2;
				z = 20 + workingsteps.get(i).getWorkingstepsPoscedentesDiretos(workingsteps).size()/2;
				g2d.setColor(Color.BLUE);
				
				g2d.fillOval(x*40 + 30,(contY+dimArvore/2)*60 + 20 , 40, 40);
				g2d.setColor(new Color(255, 255, 255));

				int id = 10 + i*10;
				String strID = "" + id;
				g2d.setColor(new Color(255, 255, 255));
				g2d.setFont(new Font("Consolas", Font.BOLD, 16));
				g2d.drawString(strID, x*40 + 40, (contY+dimArvore/2)*60 + 43);
				
				workingsteps.get(i).setNivelXWSPrecedente(x);
				workingsteps.get(i).setNivelYWSPrecedente(y);
				wstTemp = workingsteps.get(i);
				
			}
			
			y = wstTemp.getNivelYWSPrecedente();
			x = wstTemp.getNivelXWSPrecedente();
			x++;	
			y = y - wstTemp.getWorkingstepsPoscedentesDiretos(workingsteps).size()/2;
			for(int j = 0; j < workingsteps.size(); j++){
				
				if(workingsteps.get(j).getWorkingstepPrecedente() == wstTemp){
					
					g2d.setColor(Color.BLUE);
					g2d.fillOval(x*80 + 30, y*60 + 20, 40, 40);
			
					int id = 10 + j*10;
					String strID = "" + id;
					g2d.setColor(new Color(255, 255, 255));
					g2d.drawString(strID, x*80 + 40, y*60 + 43);
					
					workingsteps.get(j).setNivelXWSPrecedente(x);
					workingsteps.get(j).setNivelYWSPrecedente(y);
					contY++;
					pintou++;
					y++;
				
				}
			}
			wstTemp = workingsteps.get(contWsTemp++);
			if(contY < y) contY = y;
			y = 0;
			if(pintou != 0);
			pintou = 0;
		}
	}
}
