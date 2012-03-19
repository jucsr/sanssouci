package br.UFSC.GRIMA.capp;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.cad.visual.AddNewToolFrame;

public class AddNewTool extends AddNewToolFrame implements ActionListener
{
	public AddNewTool(Frame owner)
	{
		super(owner);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object object = e.getSource();
		if(object == okButton)
		{
			this.ok();
		} else if (object == cancelButton)
		{
			this.dispose();
		}
	}
	public void ok()
	{
		
	}
}
