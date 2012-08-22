package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.cad.visual.DefineRegionDimensionFrame;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class DefinirDimensaoRegion extends DefineRegionDimensionFrame implements ActionListener{

	private Projeto projeto;
	public RegionPanel desenhador;
	public Face face;
	
	private double x, y;
	private double width; //relativo ao eixo x
	private double height;//relativo ao eixo y
	
	public Vector features = new Vector();
	
	
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
		
		if(ok)
		{
			Point2D [] bordaRegion = null;
			x = (Double)posicaoX.getValue();
			y = (Double)posicaoY.getValue();
			width = (Double)largura.getValue();
			height = (Double)altura.getValue();
			
			Rectangle2D.Double region = new Rectangle2D.Double(x, y, width, height);
			
			for (int i = 0; i < this.features.size(); i++)
			{

				DefaultMutableTreeNode tmp = null;
				Feature ftmp = (Feature)this.features.elementAt(i);   /******************??????????*************/
				switch (ftmp.getTipo())
				{
					case Feature.FURO:
						
						Ellipse2D.Double furo = new Ellipse2D.Double(((Furo)ftmp).getPosicaoX(), ((Furo)ftmp).getPosicaoY(), ((Furo)ftmp).getRaio(), ((Furo)ftmp).getRaio());
						
						if(furo.intersects(region) || furo.contains(region))
						{
							JOptionPane.showMessageDialog(null, "A regiao esta dentro do furo ou elas se intersectam \n ", "Erro ao criar a regiao", JOptionPane.OK_CANCEL_OPTION);
							ok = false;
						}
							
						else 
							ok = true;
						
						break;
						
					case Feature.DEGRAU:
						
						Rectangle2D.Double degrau = new Rectangle2D.Double(((Degrau)ftmp).getPosicaoX(), ((Degrau)ftmp).getPosicaoY(), ((Degrau)ftmp).getLargura(), ((Degrau)ftmp).getComprimento());
						
						if(degrau.intersects(region))
						{
							JOptionPane.showMessageDialog(null, "A regiao intersecta o degrau \n ", "Erro ao criar a regiao", JOptionPane.OK_CANCEL_OPTION);
							ok = false;
						} 
						
						else if(degrau.contains(region))
						
						{
							//VERIFICAR SE NAO PRECISA DE OUTRA CONDICAO A MAIS!!!!!!!
							//Mudar a posicao Z para a superficie do degrau
							ok = true;
						}
							
						break;
						
					case Feature.RANHURA:
						
						Rectangle2D.Double ranhura = new Rectangle2D.Double(((Ranhura)ftmp).getPosicaoX(), ((Ranhura)ftmp).getPosicaoY(), ((Ranhura)ftmp).getLargura(), ((Ranhura)ftmp).getComprimento());
						
						if(ranhura.intersects(region) || ranhura.contains(region))
						{
							JOptionPane.showMessageDialog(null, "A regiao intersecta ou está contida na ranhura ou  \n ", "Erro ao criar a regiao", JOptionPane.OK_CANCEL_OPTION);
							ok = false;
						}
						
						else
							ok = true;
						
						break;
						
					case Feature.CAVIDADE:
						
						RoundRectangle2D.Double cavidade = new RoundRectangle2D.Double(((Cavidade)ftmp).getPosicaoX(), ((Cavidade)ftmp).getPosicaoY(), 
								((Cavidade)ftmp).getLargura(), ((Cavidade)ftmp).getComprimento(), ((Cavidade)ftmp).getRaio(), ((Cavidade)ftmp).getRaio());
						
						
						
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						
					case Feature.BOSS:
						if(ftmp.getClass() == CircularBoss.class) {
							
						}
							
						else if(ftmp.getClass() == RectangularBoss.class) {
							
						}
						break;
					default:
						break;
				}
				
			}
			
			
		}
		
	}
	

}
