package br.UFSC.GRIMA.cad;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import br.UFSC.GRIMA.cad.visual.PlanosFrame;

public class PlanosRef extends PlanosFrame implements ActionListener
{	
	public PlanosRef(JFrame parent)
	{	
		super(parent);
		
		this.adjustSize();
		this.adjustPosition();

		okButton.addActionListener(this);
		setVisible(true);
	}

	public void adjustSize(){
		this.pack();
	}

	//centraliza a janela no desktop do usuario
	public void adjustPosition(){
		Toolkit toolkit = this.getToolkit();
		
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();
		
		System.out.println(screenSize.toString());
		System.out.println(size.toString());

		int posX = (int)(screenSize.getWidth() - size.getWidth())/2;
		int posY = (int)(screenSize.getHeight() - size.getHeight())/2;

		this.setLocation(posX, posY);
		System.out.println(this.getLocation().toString());
	}
	
	public void actionPerformed(ActionEvent evento)
	{	Object origem = evento.getSource();
		if ( origem == okButton)
		{	ok();
		}
	}

	private void ok()
	{	
		dispose();
	}
	

}