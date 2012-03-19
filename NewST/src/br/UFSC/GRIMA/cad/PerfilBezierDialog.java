package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import br.UFSC.GRIMA.cad.visual.PerfilBezierFrame;

public class PerfilBezierDialog extends PerfilBezierFrame implements ActionListener{

	public PerfilBezierDialog(JDialog owner) 
	{
		super(owner);
		this.okButton.addActionListener(this);
		this.button1.setEnabled(false);
		this.button2.setEnabled(false);
		this.spinner1.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if(source == okButton)
		{
			dispose();
		}
	}
}
