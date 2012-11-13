package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.CircularBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author Jc
 *
 */
public class CreateCircularBoss extends CircularBossFrame implements ActionListener
{
	private Projeto projeto;
	private Face face;
	private Feature feature;
	private CirclePanel circlePanel;
	private double zoom = 1;
	private double radius = 0, radius2 = 0;
	private double altura;
	private double rugosidade, tolerancia;
	private double profundidadeFeature;
	private JanelaPrincipal parent;
	private double x = 0, y = 0;
	public CreateCircularBoss(JanelaPrincipal parent, Projeto projeto, final Face face, final Feature feature) 
	{
		super(parent);
		this.projeto = projeto;
		this.face = face;
		this.parent = parent;
		this.feature = feature;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.checkBox2.addActionListener(this);
		this.circlePanel = new CirclePanel(this.projeto, this);
		this.circlePanel.revalidate();
		this.layeredPane1.setLayout(new BorderLayout());
		this.layeredPane1.add(circlePanel);
		this.circlePanel.setFacePrincipal(face.getTipo(), 0);
		this.radius = ((Double)this.radiusSpinner.getValue()).doubleValue();
		this.radius2 = ((Double)this.radius2Spinner.getValue()).doubleValue();
		
		if(feature.getClass() == Cavidade.class)
		{
			Cavidade feat = (Cavidade)feature;
			profundidadeFeature = feat.getProfundidade();
			this.spinnerPosZ.setValue(profundidadeFeature + this.feature.Z);
			this.spinnerPosZ.setEnabled(false);
			circlePanel.shape = new RoundRectangle2D.Double(20 + feat.X, 20 + feat.Y, feat.getComprimento(), feat.getLargura(), 2 * feat.getRaio(), 2 * feat.getRaio());
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
			
			circlePanel.shape = new GeneralPath();
			ArrayList<Point2D> points = CreateGeneralProfileBoss.transformPolygonInRoundPolygon(feat.getPoints(), feat.getRadius());
			((GeneralPath)circlePanel.shape).moveTo(points.get(0).getX() * zoom + 20, points.get(0).getY() * zoom + 20);

			for(int i = 1; i < points.size(); i++)
			{
				((GeneralPath)circlePanel.shape).lineTo(points.get(i).getX() * zoom + 20, points.get(i).getY() * zoom + 20);
			}
			((GeneralPath)circlePanel.shape).closePath();
		}
		super.spinnerZoom.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				circlePanel.setZoom(zoom);
				radius = ((Double)radiusSpinner.getValue()).doubleValue() * zoom;
				radius2 = ((Double)radius2Spinner.getValue()).doubleValue() * zoom;
				double x = (Double)xSpinner.getValue();
				double y = (Double)ySpinner.getValue();
//				System.err.println("X C = " + circlePanel.circleCenter.getX());
//				System.err.println("Y C = " + circlePanel.circleCenter.getY());
//				circlePanel.circle = new Ellipse2D.Double((circlePanel.circleCenter.getX() * zoom - radius * zoom + 20), (circlePanel.circleCenter.getY() * zoom - radius * zoom + 20), radius * 2 * zoom, radius * 2 * zoom);
//				circlePanel.circle2 = new Ellipse2D.Double((circlePanel.circleCenter.getX() * zoom - radius2 * zoom + 20), (circlePanel.circleCenter.getY() * zoom - radius2 * zoom + 20), radius2 * 2 * zoom, radius2 * 2 * zoom);
				
				circlePanel.circle = new Ellipse2D.Double(x * zoom + 20 - radius, y * zoom + 20 - radius, radius * 2, radius * 2);
				circlePanel.circle2 = new Ellipse2D.Double(x * zoom + 20 - radius2, y * zoom + 20 - radius2, radius2 * 2, radius2 * 2);
				
				xSpinner.setBounds((int)((x * zoom) / 2), (int)(face.getComprimento() * zoom - y * zoom + 10), 40, 20);
				circlePanel.xLine = new Line2D.Double(20, y * zoom + 20, x * zoom + 20, y * zoom + 20);
//				xSpinner.setVisible(true);
				
				ySpinner.setBounds((int)(x * zoom), (int)((face.getComprimento() * zoom - y * zoom / 2+ 10)), 40, 20);
				circlePanel.yLine = new Line2D.Double(x * zoom + 20, 20, x * zoom + 20, y * zoom + 20);
//				ySpinner.setVisible(true);

				if(feature.getClass() == Cavidade.class)
				{
					Cavidade feat = (Cavidade)feature;			
					circlePanel.shape = new RoundRectangle2D.Double(20 + feat.X * zoom, 20 + feat.Y * zoom, feat.getComprimento() * zoom, feat.getLargura() * zoom, 2 * feat.getRaio() * zoom, 2 * feat.getRaio() * zoom);
				} else if(feature.getClass() == Degrau.class)
				{
					Degrau feat = (Degrau)feature;
		
				} else if(feature.getClass() == GeneralClosedPocket.class)
				{
					GeneralClosedPocket feat = (GeneralClosedPocket)feature;
					circlePanel.shape = new GeneralPath();
					ArrayList<Point2D> points = CreateGeneralProfileBoss.transformPolygonInRoundPolygon(feat.getPoints(), feat.getRadius());
					((GeneralPath)circlePanel.shape).moveTo(points.get(0).getX() * zoom + 20, points.get(0).getY() * zoom + 20);

					for(int i = 1; i < points.size(); i++)
					{
						((GeneralPath)circlePanel.shape).lineTo(points.get(i).getX() * zoom + 20, points.get(i).getY() * zoom + 20);
					}
					((GeneralPath)circlePanel.shape).closePath();
				}
			}
		});
		super.spinnerSeparaGrade.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				circlePanel.separacaoGrade = (Double) spinnerSeparaGrade.getValue();
				circlePanel.repaint();
			}
		});
		super.radiusSpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				radius = (Double)radiusSpinner.getValue() * zoom;
//				radius = (Double)radiusSpinner.getValue();
				if(radiusSpinner.isEnabled());
				{
					if(circlePanel.clicked >= 2)
					{
//						System.out.println("center X = " + circlePanel.circle.getCenterX());
//						System.out.println("center Y = " + circlePanel.circle.getCenterY());
//						radius2Spinner.setModel(new SpinnerNumberModel((Double)radius2Spinner.getValue(), (Double)radiusSpinner.getValue(), null, 1.0));

						circlePanel.circle = new Ellipse2D.Double(circlePanel.circle.getCenterX() - radius, circlePanel.circle.getCenterY() - radius, radius * 2, radius * 2);
						circlePanel.repaint();
					}
				}
			}
		});
		super.radius2Spinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				radius2 = (Double)radius2Spinner.getValue() * zoom;

				circlePanel.circle2 = new Ellipse2D.Double(circlePanel.circle.getCenterX() - radius2, circlePanel.circle.getCenterY() - radius2, radius2 * 2, radius2 * 2);
				circlePanel.repaint();
			}
		});
		super.xSpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				radius = (Double)radiusSpinner.getValue() * zoom;
				radius2 = (Double)radius2Spinner.getValue() * zoom;
				double x = (Double)xSpinner.getValue();
				double y = (Double)ySpinner.getValue();
				
				if(circlePanel.clicked >= 2)
				{
					circlePanel.xLine = new Line2D.Double(20, y * zoom + 20, x * zoom + 20, y * zoom + 20);
					xSpinner.setBounds((int)((x * zoom) / 2), (int)(face.getComprimento() * zoom - y * zoom + 10), 40, 20);
					xSpinner.setVisible(true);
					
					circlePanel.yLine = new Line2D.Double(x * zoom + 20, 20, x * zoom + 20, y * zoom + 20);
					ySpinner.setBounds((int)(x * zoom), (int)((face.getComprimento() * zoom - y * zoom / 2+ 10)), 40, 20);
					ySpinner.setVisible(true);
					
					circlePanel.circle = new Ellipse2D.Double(x * zoom + 20 - radius, y * zoom + 20 - radius, radius * 2, radius * 2);
					circlePanel.circle2 = new Ellipse2D.Double(x * zoom + 20 - radius2, y * zoom + 20 - radius2, radius2 * 2, radius2 * 2);
				}
				circlePanel.repaint();
			}
		});
		super.ySpinner.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				radius = (Double)radiusSpinner.getValue() * zoom;
				radius2 = (Double)radius2Spinner.getValue() * zoom;
				double x = (Double)xSpinner.getValue();
				double y = (Double)ySpinner.getValue();
				
				if(circlePanel.clicked >= 2)
				{
					circlePanel.xLine = new Line2D.Double(20, y * zoom + 20, x * zoom + 20, y * zoom + 20);
					xSpinner.setBounds((int)((x * zoom) / 2), (int)(face.getComprimento() * zoom - y * zoom + 10), 40, 20);
					xSpinner.setVisible(true);
					
					circlePanel.yLine = new Line2D.Double(x * zoom + 20, 20, x * zoom + 20, y * zoom + 20);
					ySpinner.setBounds((int)(x * zoom), (int)((face.getComprimento() * zoom - y * zoom / 2+ 10)), 40, 20);
					ySpinner.setVisible(true);
					
					circlePanel.circle = new Ellipse2D.Double(x * zoom + 20 - radius, y * zoom+ 20 - radius, radius * 2, radius * 2);
					circlePanel.circle2 = new Ellipse2D.Double(x * zoom + 20 - radius2, y * zoom + 20 - radius2, radius2 * 2, radius2 * 2);
				}
				circlePanel.repaint();
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
		}else if(o.equals(cancelButton))
		{
			this.cancel();
		}else if(o.equals(checkBox2))
		{
			if(checkBox2.isSelected())
				circlePanel.grade = true;
			else
				circlePanel.grade = false;
			circlePanel.repaint();
		}
	}
	private void ok() 
	{
		radius2 = ((Double)this.radius2Spinner.getValue()).doubleValue();
		radius = ((Double)this.radiusSpinner.getValue()).doubleValue();
		altura = ((Double)this.spinnerDepth.getValue()).doubleValue();
//		double posX = circlePanel.circleCenter.getX();
//		double posY = circlePanel.circleCenter.getY();
		double posX = ((Double)xSpinner.getValue()).doubleValue();
		double posY = ((Double)ySpinner.getValue()).doubleValue();
		double posZ = profundidadeFeature + feature.Z - altura; 
		System.out.println("POSZ = " + posZ);
		boolean ok = false;
		boolean valido = false;
		CircularBoss boss = null;
		if(radius2 < radius)
		{
			ok = false;
			JOptionPane.showMessageDialog(parent, "O raio 2 deve ser maior que o raio 1", "Erro ", JOptionPane.OK_CANCEL_OPTION);
		} else
		{
			ok = true;
			if(profundidadeFeature >= altura)
			{
				ok = true;
			}
			else
			{
				ok = false;
				JOptionPane.showMessageDialog(parent, "a altura da protuberancia nao pode ultrapassar a profundidade da cavidade", "Erro ", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		
//		if(ok)
//		{
//			boss = new CircularBoss();
//			boss.setDiametro1(radius * 2);
//			boss.setDiametro2(radius2 * 2);
//			boss.setPosicao(posX, posY, posZ);
//			boss.setAltura(altura);
//			boss.setNome(this.textField1.getText());
//			
//			if(this.feature.getClass() == Cavidade.class)
//			{
//				Cavidade cavidade = (Cavidade)this.feature;
//				if(cavidade.validarBoss(boss))
//				{
////					posZ = cavidade.getProfundidade() - altura + cavidade.Z;
//					valido = true;
//					cavidade.addBoss(boss);
//				}
//				
//			} else if(this.feature.getClass() == Degrau.class)
//			{
//				Degrau degrau = (Degrau)this.feature;
//				// ---- IMPLEMENTAR
//			} else if(this.feature.getClass() == GeneralClosedPocket.class)
//			{
//				GeneralClosedPocket general = (GeneralClosedPocket)this.feature;
//				if(general.validarBoss(boss))
//				{
////					posZ = general.getProfundidade() - altura + general.Z;
//					valido = true;
//					general.addBoss(boss);
//				}
//			}
//		}
		boss = new CircularBoss();
		boss.setDiametro1(radius * 2);
		boss.setDiametro2(radius2 * 2);
		boss.setPosicao(posX, posY, posZ);
		boss.setAltura(altura);
		boss.setNome(this.textField1.getText());
		
		if(this.face.validarFeature(boss))
		{
			if(ok)
			{
				if(this.feature.getClass() == Cavidade.class)
				{
					Cavidade cavidade = (Cavidade)this.feature;
					if(cavidade.validarBoss(boss))
					{
//						posZ = cavidade.getProfundidade() - altura + cavidade.Z;
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
//						posZ = general.getProfundidade() - altura + general.Z;
						valido = true;
						general.addBoss(boss);
					}
				}
			}
			boss.setCentre(new Point3d(this.x, this.y, 0));
			if (valido) 
			{
				{
					Point3d coordinates = null;
					ArrayList<Double> axis = null, refDirection = null;
					if (this.face.getTipo() == Face.XY) 
					{
						coordinates = new Point3d(posX, posY,
								this.face.getProfundidadeMaxima() - posZ);
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
						coordinates = new Point3d(posX, posZ, posY);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(-1.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YX)
					{
						coordinates = new Point3d(posX, this.face.getLargura()
								- posY, posZ);
						axis = new ArrayList<Double>();
						axis.add(0.0);
						axis.add(0.0);
						axis.add(-1.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(1.0);
						refDirection.add(0.0);
						refDirection.add(0.0);

					} else if (this.face.getTipo() == Face.YZ)
					{
						coordinates = new Point3d(this.face.getProfundidadeMaxima()
								- posZ, posY, this.face.getComprimento() - posX);
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
						coordinates = new Point3d(posX,
								this.face.getProfundidadeMaxima() - posZ,
								this.face.getLargura() - posY);
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
						coordinates = new Point3d(posZ, posY, posX);
						axis = new ArrayList<Double>();
						axis.add(-1.0);
						axis.add(0.0);
						axis.add(0.0);

						refDirection = new ArrayList<Double>();
						refDirection.add(0.0);
						refDirection.add(0.0);
						refDirection.add(1.0);

					}
					Axis2Placement3D position = new Axis2Placement3D(coordinates,	axis, refDirection);
					position.setName(boss.getNome() + " placement");
					boss.setPosition(position);

					this.face.addFeature(boss);
					this.parent.desenhador.repaint();
					this.parent.atualizarArvore();
					this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" + "General Profile Boss: " + boss.getNome().toUpperCase() + " added with success!");

					dispose();
				}
		
			}
		}
	}
	private void cancel() 
	{
		this.dispose();
	}
}
