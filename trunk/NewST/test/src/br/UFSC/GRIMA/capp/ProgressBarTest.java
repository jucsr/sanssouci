package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.junit.Test;

import br.UFSC.GRIMA.cad.RunnableProgressBar;

public class ProgressBarTest {
	
	
	@Test
	public void showProgressBar(){
	
		Thread t1 = new Thread(new RunnableProgressBar(),"thread1");
		t1.start();
		
		Thread t2 = new Thread(new RunnableProgressBar(),"thread2");
		t2.start();
		
		Timer timer = new Timer (2000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("timerrrrrrrrrrrrrrrr");
				
			}	
			
		});
		
		timer.start();
		
		System.out.println(" TESTE ");
		
		while(true);
		
	}

}
