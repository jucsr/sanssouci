package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.RegionAreaFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateRegion extends RegionAreaFrame implements ActionListener
{
	private Projeto projeto;
	private Face face;
	private Feature feature;
	private RectangleRegionPanel rectanglePanel;
	private double length, width, radius, heigth, X, Y, Z;
	private JanelaPrincipal parent;
	private double zoom = 1;
	
	CreateRegion(JanelaPrincipal parent, Projeto projeto, final Face face)
	{
		super(parent);
		this.projeto = projeto;
		this.face = face;
		this.parent = parent;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.checkBox2.addActionListener(this);
		this.rectanglePanel = new RectangleRegionPanel(this.projeto, this);
		this.rectanglePanel.revalidate();
		this.layeredPane1.setLayout(new BorderLayout());
		this.layeredPane1.add(this.rectanglePanel);
		this.rectanglePanel.setFacePrincipal(face.getTipo(), 0);
		this.length = ((Double)this.lengthSpinner.getValue()).doubleValue();
		this.width = ((Double)this.widthSpinner.getValue()).doubleValue();
		
		super.spinnerZoom.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				rectanglePanel.setZoom(zoom);
				length = ((Double)lengthSpinner.getValue()).doubleValue() * zoom;
				width = ((Double)widthSpinner.getValue()).doubleValue() * zoom;
				radius = (Double)radiusSpinner.getValue() * zoom;
				double x = ((Double)xSpinner.getValue()).doubleValue() * zoom;
				double y = ((Double)ySpinner.getValue()).doubleValue() * zoom;

				rectanglePanel.rectangle = new RoundRectangle2D.Double(x + 20, y + 20, length, width, radius * 2, radius * 2);
				
				xSpinner.setBounds((int)(x / 2), (int)(face.getComprimento() * zoom - y + 10), 40, 20);
				rectanglePanel.xLine = new Line2D.Double(20, y + 20, x + 20, y + 20);
//				xSpinner.setVisible(true);
				
				ySpinner.setBounds((int)(x), (int)((face.getComprimento() * zoom - y / 2+ 10)), 40, 20);
				rectanglePanel.yLine = new Line2D.Double(x + 20, 20, x + 20, y + 20);
//				ySpinner.setVisible(true);
				
				lengthSpinner.setBounds((int)(x + length / 2), (int)(face.getComprimento() * zoom - y - width / 4 + 10), 40, 20);
				rectanglePanel.lengthLine = new Line2D.Double(20 + x, y + width / 4 + 20, 20 + x + length, y + width / 4 + 20);
				
				widthSpinner.setBounds((int)(x + length / 4), (int)(face.getComprimento() * zoom - y - width / 2 + 10), 40, 20);
				rectanglePanel.widthLine = new Line2D.Double(20 + x + length / 4, 20 + y, 20 + x + length / 4, 20 + y + width);
			}
		});
		super.spinnerSeparaGrade.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				rectanglePanel.separacaoGrade = (Double)spinnerSeparaGrade.getValue();
				rectanglePanel.repaint();
			}
		});
		super.xSpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				rectanglePanel.setZoom(zoom);
				length = ((Double)lengthSpinner.getValue()).doubleValue() * zoom;
				width = ((Double)widthSpinner.getValue()).doubleValue() * zoom;
				radius = ((Double)radiusSpinner.getValue()).doubleValue() * zoom;
				double x = ((Double)xSpinner.getValue()).doubleValue() * zoom;
				double y = ((Double)ySpinner.getValue()).doubleValue() * zoom;
				
				if(rectanglePanel.clicked >= 2)
				{
					rectanglePanel.xLine = new Line2D.Double(20, y + 20, x + 20, y + 20);
					xSpinner.setBounds((int)(x / 2), (int)(face.getComprimento() * zoom - y + 10), 40, 20);
					xSpinner.setVisible(true);
					
					rectanglePanel.yLine = new Line2D.Double(x + 20, 20, x + 20, y + 20);
					ySpinner.setBounds((int)(x ), (int)((face.getComprimento() * zoom - y / 2+ 10)), 40, 20);
					ySpinner.setVisible(true);
					
					rectanglePanel.rectangle = new RoundRectangle2D.Double(x + 20, y + 20, length, width, radius * 2, radius * 2);
				
					lengthSpinner.setBounds((int)(x + length / 2), (int)(face.getComprimento() * zoom - y - width / 4 + 10), 40, 20);
					rectanglePanel.lengthLine = new Line2D.Double(20 + x, y + width / 4 + 20, 20 + x + length, y + width / 4 + 20);
					
					widthSpinner.setBounds((int)(x + length / 4), (int)(face.getComprimento() * zoom - y - width / 2 + 10), 40, 20);
					rectanglePanel.widthLine = new Line2D.Double(20 + x + length / 4, 20 + y, 20 + x + length / 4, 20 + y + width);
					
				}
				rectanglePanel.repaint();
			}
		});
		super.ySpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				rectanglePanel.setZoom(zoom);
				length = ((Double)lengthSpinner.getValue()).doubleValue() * zoom;
				width = ((Double)widthSpinner.getValue()).doubleValue() * zoom;
				radius = ((Double)radiusSpinner.getValue()).doubleValue() * zoom;
				double x = ((Double)xSpinner.getValue()).doubleValue() * zoom;
				double y = ((Double)ySpinner.getValue()).doubleValue() * zoom;
				
				if(rectanglePanel.clicked >= 2)
				{
					rectanglePanel.xLine = new Line2D.Double(20, y + 20, x + 20, y + 20);
					xSpinner.setBounds((int)(x / 2), (int)(face.getComprimento() * zoom - y + 10), 40, 20);
					xSpinner.setVisible(true);
					
					rectanglePanel.yLine = new Line2D.Double(x + 20, 20, x + 20, y + 20);
					ySpinner.setBounds((int)x, (int)((face.getComprimento() * zoom - y / 2+ 10)), 40, 20);
					ySpinner.setVisible(true);
					
					rectanglePanel.rectangle = new RoundRectangle2D.Double(x + 20, y + 20, length, width, radius * 2, radius * 2);
					
					lengthSpinner.setBounds((int)(x + length / 2), (int)(face.getComprimento() * zoom - y - width / 4 + 10), 40, 20);
					rectanglePanel.lengthLine = new Line2D.Double(20 + x, y + width / 4 + 20, 20 + x + length, y + width / 4 + 20);
					
					widthSpinner.setBounds((int)(x + length / 4), (int)(face.getComprimento() * zoom - y - width / 2 + 10), 40, 20);
					rectanglePanel.widthLine = new Line2D.Double(20 + x + length / 4, 20 + y, 20 + x + length / 4, 20 + y + width);
					
				}
				rectanglePanel.repaint();
			}
		});
		super.lengthSpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				rectanglePanel.setZoom(zoom);
				length = ((Double)lengthSpinner.getValue()).doubleValue() * zoom;
				width = ((Double)widthSpinner.getValue()).doubleValue() * zoom;
				radius = ((Double)radiusSpinner.getValue()).doubleValue() * zoom;
				double x = ((Double)xSpinner.getValue()).doubleValue() * zoom;
				double y = ((Double)ySpinner.getValue()).doubleValue() * zoom;
				
				if(rectanglePanel.clicked >= 2)
				{
					rectanglePanel.rectangle = new RoundRectangle2D.Double(x + 20, y + 20, length, width, radius * 2, radius * 2);
					
					lengthSpinner.setBounds((int)(x + length / 2), (int)(face.getComprimento() * zoom - y - width / 4 + 10), 40, 20);
					rectanglePanel.lengthLine = new Line2D.Double(20 + x, y + width / 4 + 20, 20 + x + length, y + width / 4 + 20);
					
					widthSpinner.setBounds((int)(x + length / 4), (int)(face.getComprimento() * zoom - y - width / 2 + 10), 40, 20);
					rectanglePanel.widthLine = new Line2D.Double(20 + x + length / 4, 20 + y, 20 + x + length / 4, 20 + y + width);
					
				}
				rectanglePanel.repaint();
			}
		});
		super.widthSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				rectanglePanel.setZoom(zoom);
				length = ((Double)lengthSpinner.getValue()).doubleValue() * zoom;
				width = ((Double)widthSpinner.getValue()).doubleValue() * zoom;
				radius = ((Double)radiusSpinner.getValue()).doubleValue() * zoom;
				double x = ((Double)xSpinner.getValue()).doubleValue() * zoom;
				double y = ((Double)ySpinner.getValue()).doubleValue() * zoom;
				
				if(rectanglePanel.clicked >= 2)
				{
					rectanglePanel.rectangle = new RoundRectangle2D.Double(x + 20, y + 20, length, width, radius * 2, radius * 2);
					
					lengthSpinner.setBounds((int)(x + length / 2), (int)(face.getComprimento() * zoom - y - width / 4 + 10), 40, 20);
					rectanglePanel.lengthLine = new Line2D.Double(20 + x, y + width / 4 + 20, 20 + x + length, y + width / 4 + 20);
					
					widthSpinner.setBounds((int)(x + length / 4), (int)(face.getComprimento() * zoom - y - width / 2 + 10), 40, 20);
					rectanglePanel.widthLine = new Line2D.Double(20 + x + length / 4, 20 + y, 20 + x + length / 4, 20 + y + width);
					
				}
				rectanglePanel.repaint();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if(o == this.okButton)
		{
			this.ok();
		} else if(o == this.cancelButton)
		{
			this.cancel();
		} else if(o == this.checkBox2)
		{
			if(checkBox2.isSelected())
				rectanglePanel.grade = true;
			else
				rectanglePanel.grade = false;
			rectanglePanel.repaint();
		}
	}
	private void cancel() 
	{
		this.dispose();
	}
	private void ok() 
	{
		boolean ok = false, valido = false;
		length = ((Double)lengthSpinner.getValue()).doubleValue();
		width = ((Double)widthSpinner.getValue()).doubleValue();
		radius = ((Double)radiusSpinner.getValue()).doubleValue();
		heigth = ((Double)spinnerDepth.getValue()).doubleValue();
		double tolerancia = ((Double)spinnerRugosidade.getValue()).doubleValue();
		X = ((Double)xSpinner.getValue()).doubleValue();
		Y = ((Double)ySpinner.getValue()).doubleValue();
		Z = ((Double)spinnerPosZ.getValue()).doubleValue();
//		double posZ = Z;
		Region region = new Region(Feature.REGION);
		region.setWidth(width);
		region.setLength(length);
		region.setPosicao(X, Y, Z);
		region.setTolerancia(tolerancia);
		region.setItsId(this.textField1.getText());
		Point3d [][] controlVertex = new Point3d[4][4];
		double controlX = 0; 
		for(int i = 0; i < 4; i++)
		{
			double controlY = 0;
			for(int j = 0; j < 4; j++)
			{
				controlVertex[i][j] = new Point3d(controlX, controlY, Z);
				region.setControlVertex(controlVertex);
				controlY += width/3;
			}
			controlX += length/3;
		}
		CriarRegionFrame cr = new CriarRegionFrame(this.parent, region, this.face);
		cr.setVisible(true);
		this.dispose();
	}
}
