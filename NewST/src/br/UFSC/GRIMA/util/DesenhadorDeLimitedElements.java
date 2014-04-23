package br.UFSC.GRIMA.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.visual.JanelaDesenhadorLimitedElements;

public class DesenhadorDeLimitedElements  extends JanelaDesenhadorLimitedElements implements ActionListener, ChangeListener
{
	public PainelDesenhadorDeElementos desenhador;
	private ArrayList<LimitedElement> elements;
	public DesenhadorDeLimitedElements(ArrayList<LimitedElement> elements)
	{
		this.desenhador = new PainelDesenhadorDeElementos(elements);
//		this.desenhador.setPreferredSize(preferredSize);
		this.adjustJFrame();
		this.elements = elements;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.spinnerZoom.addChangeListener(this);
		this.scrollPane1.setViewportView(desenhador);
		this.desenhador.revalidate();
		this.scrollPane1.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o == okButton)
		{
			this.dispose();
		} else if(o == cancelButton)
		{
			this.dispose();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		Object o = e.getSource();
		if(o == spinnerZoom)
		{
			this.desenhador.setZoom((Double)(this.spinnerZoom.getValue()) / 100);
			this.desenhador.repaint();
			this.desenhador.revalidate();
			this.scrollPane1.revalidate();
		}
	}
	public void adjustJFrame() 
	{
		Toolkit toolkit = this.getToolkit();

		this.setResizable(true);
		this.pack();
		Dimension windowDimension = toolkit.getScreenSize();
//		Dimension thisDimension = this.getPreferredSize();
		Dimension thisDimension = new Dimension((int)windowDimension.getWidth()/2, (int)windowDimension.getHeight()/2);
		this.setSize(thisDimension);
		
		this.setLocation((int) (windowDimension.getWidth() - thisDimension.getWidth()) / 2, (int) (windowDimension.getHeight() - thisDimension.getHeight()) / 2);
	}
}
