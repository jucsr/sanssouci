package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.cad.visual.DefineRegionDimensionFrame;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class DefinirDimensaoRegion extends DefineRegionDimensionFrame implements ActionListener{

	private Projeto projeto;
	public RegionPanel desenhador;
	public Face face;
	
	private double x, y;
	private double width; //relativo ao eixo x
	private double height;//relativo ao eixo y
	
	public DefinirDimensaoRegion (Frame owner, Projeto projeto, Face face)
	{
		super(owner);
		this.projeto = projeto;
		this.desenhador = new RegionPanel(this.projeto);
		this.face = face;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		desenhador.setFacePrincipal(face.getTipo(), 0); //recebe qual face e vertice ativado
		this.panel5.add(desenhador);     				//adiciona o painel DesenhadorDeFaces ao frame
		x = (Double)posicaoX.getValue();
		y = (Double)posicaoY.getValue();
		width = (Double)largura.getValue();
		height = (Double)altura.getValue();
		
		//Toda vez que o valor do spinner PosicaoX for alterado o desenhador é atualizado
		posicaoX.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				x = (Double)posicaoX.getValue();
			
				desenhador.rectangle = new Rectangle2D.Double(x, y, width, height);
				desenhador.repaint();
			}
		});
		
		posicaoY.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				y = (Double)posicaoY.getValue();
				
				desenhador.rectangle = new Rectangle2D.Double(x, y, width, height);
			}
		});
		
		largura.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				width = (Double)largura.getValue();
				
				desenhador.rectangle = new Rectangle2D.Double(x, y, width, height);
			}
		});
		
		altura.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				height = (Double)altura.getValue();
				
				desenhador.rectangle = new Rectangle2D.Double(x, y, width, height);
			}
		});
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if (obj.equals(okButton))
			this.ok();
		
		else if (obj.equals(cancelButton))
			dispose();
		
	}
	
	//Verificacao de valores positivos
	private void ok()
	{
		boolean ok = true;
		double refLargura = 0.0;
		double refAltura = 0.0; 
		
		if(ok)
			if (x > 0.0 && y > 0.0 && width > 0.0 && height > 0.0)
				
				ok = true;
		
			else {
				ok = false;
				JOptionPane.showMessageDialog(
								null,
								"Todos os valores devem ser positivos",
								"Erro",
								JOptionPane.OK_CANCEL_OPTION);
			}
		
		//Validacao da largura; posicao x
		
		if(ok)
		{
		try
		{

			switch (face.verticeAtivado) {

			case 0:

				refLargura = x + width;
				if (width > 0 && refLargura < face.getLargura())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a largura ou a posicao X)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 1:

				refLargura = (face.getComprimento() - (y + height)) + height;
				if ( height > 0 && refLargura < face.getComprimento())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a altura ou a posicao Y)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 2:

				refLargura = (face.getLargura() - (x + width)) + width;
				if (width > 0 && refLargura < face.getLargura())
				{
					//System.out.println("largura: " + largura);
					ok = true;
					
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a largura ou a posicao X)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 3:

				refLargura = y + height;
				if (height >0 && refLargura < face.getComprimento())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a altura ou a posicao Y)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}

				break;
			default:
				break;
			}
		
		}
		
		catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Digite um Numero positivo para a largura da Regiao"
									+ "\n               Nao digite letras nem simbolos",
							"Erro na largura", JOptionPane.OK_CANCEL_OPTION);
			ok = false;
		}
		
		}
		
		//Validacao da altura; posicao Y
		
		if(ok)
		{
		try
		{

			switch (face.verticeAtivado) {

			case 0:

				refAltura = y + height;
				if (height > 0 && refAltura < face.getComprimento())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise o comprimento ou a posicao Y)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 1:

				refAltura = x + width;
				if ( width > 0 && refAltura < face.getLargura())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a largura ou a posicao X)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 2:

				refAltura = (face.getComprimento() - (y + height)) + height;
				if (height > 0 && refAltura < face.getLargura())
				{
					//System.out.println("largura: " + largura);
					ok = true;
					
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a altura ou a posicao Y)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
				break;
			case 3:

				refAltura = (face.getLargura() - (x + width)) + width;
				if (width > 0 && refAltura < face.getLargura())
				{
					ok = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"A br.UFSC.GRIMA.feature nao esta dentro dos limites da face"
							+ "\n               (revise a largura ou a posicao X)",
							"Erro ao criar a Regiao", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}

				break;
			default:
				break;
			}
		
		}
		
		catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Digite um Numero positivo para a largura da Regiao"
									+ "\n               Nao digite letras nem simbolos",
							"Erro na Altura", JOptionPane.OK_CANCEL_OPTION);
			ok = false;
		}
		
		}
		
		
		
	}
	

}
