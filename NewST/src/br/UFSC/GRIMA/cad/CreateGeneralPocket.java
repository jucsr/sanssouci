package br.UFSC.GRIMA.cad;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.cad.visual.CreateGeneralPocketFrame;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateGeneralPocket extends CreateGeneralPocketFrame implements ActionListener
{
	public LinePanel linePanel; 
	public DesenhadorDeFaces desenhador;
	
	public CreateGeneralPocket(Frame frame, Projeto projeto)
	{
		super(frame);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.desenhador = new DesenhadorDeFaces(projeto);
		this.desenhador.setFacePrincipal(Face.XY, 0);
		desenhador.setZoom(1);
		linePanel = new LinePanel(projeto);
		this.contentPanel.add(linePanel);
		linePanel.setFacePrincipal(Face.XY, 0);
		
//		desenhador.repaint();
		Toolkit toolKit = Toolkit.getDefaultToolkit();
    	Dimension d = toolKit.getScreenSize();
        setSize(d.width/2, d.height/2);
        setLocationRelativeTo(null);
	}
//	public static void main(String [] args)
//	{
//		CreateGeneralPocket create = new CreateGeneralPocket(null);
//		create.contentPanel.add(linePanel);
//		create.setVisible(true);
//	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
		} 
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		else if(o.equals(button1))
		{
			this.closeCurve();
		}
		else if(o.equals(button2))
		{
			this.help();
		}
	}
	private void closeCurve() 
	{
		if(linePanel.pointList.get(0) != linePanel.pointList.get(linePanel.pointList.size() - 1))
//			linePanel.pointList.add(new Point(linePanel.pointList.get(0)));
			
		linePanel.poligono.closePath();
		
		linePanel.repaint();
	}
	private void help() 
	{
		
	}
	private void ok() 
	{
		if(this.validateAngles())
		{
			dispose();
		}
	}
	private boolean validateAngles() 
	{
		boolean isValid = false;
		
		Point p0, p1, p2;
		for(int i = 0; i < linePanel.pointList.size(); i++)
		{
			try{
				p0 = linePanel.pointList.get(i);
				p1 = linePanel.pointList.get(i - 1);
				p2 = linePanel.pointList.get(i + 1);
			} catch(Exception e)
			{
				if(e.getMessage().equals("-1"))
				{
					p1 = linePanel.pointList.get(linePanel.pointList.size() - 1);
				} else
				{
					p2 = linePanel.pointList.get(0);
				}
			}
			
//			double distanceP1P2 
		}
		return isValid;
	}
}
