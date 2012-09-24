package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.RectangularBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author Jc
 *
 */
public class CreateRectangularBoss extends RectangularBossFrame implements ActionListener
{
	private Projeto projeto;
	private Face face;
	private Feature feature;
	private RectanglePanel rectanglePanel;
	private double profundidadeFeature;
	private double zoom = 1;
	private double length, width, radius, heigth, X, Y, Z;
	private JanelaPrincipal parent;

	public CreateRectangularBoss(JanelaPrincipal parent, Projeto projeto, final Face face, final Feature feature) 
	{
		super(parent);
		this.projeto = projeto;
		this.face = face;
		this.parent = parent;
		this.feature = feature;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.checkBox2.addActionListener(this);
		this.rectanglePanel = new RectanglePanel(this.projeto, this);
		this.rectanglePanel.revalidate();
		this.layeredPane1.setLayout(new BorderLayout());
		this.layeredPane1.add(rectanglePanel);
		this.rectanglePanel.setFacePrincipal(face.getTipo(), 0);
		this.length = ((Double)this.lengthSpinner.getValue()).doubleValue();
		this.width = ((Double)this.widthSpinner.getValue()).doubleValue();
		
		if(feature.getClass() == Cavidade.class)
		{
			Cavidade feat = (Cavidade)feature;
			profundidadeFeature = feat.getProfundidade();
			this.spinnerPosZ.setValue(profundidadeFeature + this.feature.Z);
			this.spinnerPosZ.setEnabled(false);
			rectanglePanel.shape = new RoundRectangle2D.Double(20 + feat.X, 20 + feat.Y, feat.getComprimento(), feat.getLargura(), 2 * feat.getRaio(), 2 * feat.getRaio());
		} else if(feature.getClass() == Degrau.class)
		{
			Degrau feat = (Degrau)feature;
			profundidadeFeature = feat.getProfundidade();
			this.spinnerPosZ.setValue(profundidadeFeature + this.feature.Z);
			this.spinnerPosZ.setEnabled(false);
		} else if(feature.getClass() == GeneralClosedPocket.class)
		{
			GeneralClosedPocket feat = (GeneralClosedPocket)feature;
			profundidadeFeature = feat.getProfundidade();
			this.spinnerPosZ.setValue(profundidadeFeature + this.feature.Z);
			this.spinnerPosZ.setEnabled(false);
			
			rectanglePanel.shape = new GeneralPath();
			ArrayList<Point2D> points = CreateGeneralProfileBoss.transformPolygonInRoundPolygon(feat.getPoints(), feat.getRadius());
			((GeneralPath)rectanglePanel.shape).moveTo(points.get(0).getX() * zoom + 20, points.get(0).getY() * zoom + 20);

			for(int i = 1; i < points.size(); i++)
			{
				((GeneralPath)rectanglePanel.shape).lineTo(points.get(i).getX() * zoom + 20, points.get(i).getY() * zoom + 20);
			}
			((GeneralPath)rectanglePanel.shape).closePath();
		}
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
				
				if(feature.getClass() == Cavidade.class)
				{
					Cavidade feat = (Cavidade)feature;			
					rectanglePanel.shape = new RoundRectangle2D.Double(20 + feat.X * zoom, 20 + feat.Y * zoom, feat.getComprimento() * zoom, feat.getLargura() * zoom, 2 * feat.getRaio() * zoom, 2 * feat.getRaio() * zoom);
				} else if(feature.getClass() == Degrau.class)
				{
					Degrau feat = (Degrau)feature;
		
				} else if(feature.getClass() == GeneralClosedPocket.class)
				{
					GeneralClosedPocket feat = (GeneralClosedPocket)feature;
					rectanglePanel.shape = new GeneralPath();
					ArrayList<Point2D> points = CreateGeneralProfileBoss.transformPolygonInRoundPolygon(feat.getPoints(), feat.getRadius());
					((GeneralPath)rectanglePanel.shape).moveTo(points.get(0).getX() * zoom + 20, points.get(0).getY() * zoom + 20);

					for(int i = 1; i < points.size(); i++)
					{
						((GeneralPath)rectanglePanel.shape).lineTo(points.get(i).getX() * zoom + 20, points.get(i).getY() * zoom + 20);
					}
					((GeneralPath)rectanglePanel.shape).closePath();
				}
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
			super.radiusSpinner.addChangeListener(new ChangeListener() 
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
		double posZ = profundidadeFeature - heigth + feature.Z;
		double comprimento = width;
		
		if (length < width)
			comprimento = length;
		if(radius <=  comprimento / 2)
		{
			ok = true;
			
		}else 
		{
			JOptionPane.showMessageDialog(null, "radius should be less or equal than " + comprimento / 2);
			ok = false;
		}
		
		if(ok)
		{
			RectangularBoss boss = new RectangularBoss(length, width, heigth, radius);
			boss.setPosicao(X, Y, posZ);
			boss.setNome(this.textField1.getText());
			boss.setTolerancia(tolerancia*0.001);
			boss.setRugosidade(tolerancia*0.001);
			
			if(this.feature.getClass() == Cavidade.class)
			{
				Cavidade cavidade = (Cavidade)this.feature;
				if(cavidade.validarBoss(boss))
				{
					Z = cavidade.getProfundidade() - heigth + cavidade.Z;
					valido = true;
					cavidade.addBoss(boss);
				}
				
			} else if(this.feature.getClass() == Degrau.class)
			{
				Degrau degrau = (Degrau)this.feature;
				// ---- IMPLEMENTAR
			} else if(this.feature.getClass() == GeneralClosedPocket.class)
			{
				GeneralClosedPocket general = (GeneralClosedPocket)this.feature;
				if(general.validarBoss(boss))
				{
					Z = general.getProfundidade() - heigth + general.Z;
					valido = true;
					general.addBoss(boss);
				}
			}
			
						
//			if (this.cavidade.validarBoss(novoRectangularBoss)) 
//			{
			if(valido)
				{
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY) 
					{
						coordinates = new Point3d(boss.X, boss.Y, this.face.getProfundidadeMaxima() - boss.Z);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);
					} else if (this.face.getTipo() == Face.XZ)
					{
						coordinates = new Point3d(boss.X, boss.Z, boss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(-1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YX) {
						coordinates = new Point3d(boss.X, this.face.getLargura() - boss.Y, boss.Z);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(-1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YZ) {
						coordinates = new Point3d(
								this.face.getProfundidadeMaxima() - boss.Z, boss.Y, this.face.getComprimento() - boss.X);
						axis = new ArrayList<Double>();
						axis.add(1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(-1.0);

					} else if (this.face.getTipo() == Face.ZX) 
					{
						coordinates = new Point3d(boss.X, this.face.getProfundidadeMaxima() - boss.Z, this.face.getLargura() - boss.Y);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.ZY) 
					{
						coordinates = new Point3d(boss.Z, boss.Y, boss.X);
						axis = new ArrayList<Double>();
						axis.add(-1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(1.0);

					}
					Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
					position.setName(boss.getNome() + " placement");
					boss.setPosition(position);
					// this.face.addFeature(novoCircularBoss);

					/** fazer isto em todas as janelas */
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					/****************** atualiza a ARVORE ***************/
					// face.imprimeDados(novoFuro);
					// this.setModal(false);
					this.parent.setEnabled(true);

					this.parent.textArea1.setText(this.parent.textArea1
							.getText()
							+ "\n"
							+ "Boss: "
							+ boss.getNome().toUpperCase()
							+ " adicionado com sucesso!");
					this.dispose();
				}

//			} else {
//				JOptionPane.showMessageDialog(null, "Error trying to create the Boss", "invalid Boss", JOptionPane.OK_CANCEL_OPTION);
////				String erro = "";
////				StyleContext sc = StyleContext.getDefaultStyleContext();
////				AttributeSet aSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
//
//				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n Error trying to create the Boss!");
//			}
			this.dispose();
		}
	}
}
