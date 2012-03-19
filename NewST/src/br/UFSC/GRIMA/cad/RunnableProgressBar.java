package br.UFSC.GRIMA.cad;

import java.awt.Color;

import javax.swing.JFrame;

import br.UFSC.GRIMA.cad.visual.ProgressBar;

public class RunnableProgressBar implements Runnable{


	JFrame frame= new JFrame();
	ProgressBar pg;
	
	public RunnableProgressBar() {
		super();
		System.out.println("HÁ");
	}
	
	
	@Override
	public void run() {
		
		pg = new ProgressBar(frame);
		pg.setVisible(true);
		System.out.println("RUN!");
		
//		while(pg.isVisible()){
//			pg.repaint();
//			pg.progressBar1.revalidate();
//			System.out.println(" laço ");
//		}
		
	}


	public void dispose() {
		pg.dispose();
		
	}

}
