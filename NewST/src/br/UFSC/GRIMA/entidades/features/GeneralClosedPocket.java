package br.UFSC.GRIMA.entidades.features;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EClosed_pocket;
import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.cad.JanelaPrincipal;
/**
 * 
 * @author Jc
 *
 */
public class GeneralClosedPocket extends Feature
{
	private double radius = 0;
	private double profundidade = 0;
	
	public Face face = null;
	public Cavidade cavidade = null;
	private ArrayList<Point2D> vertexPoints;
	private GeneralPath forma; 	// � o desenho da borda do GeneralClosedPocket
	private boolean isPassante = false;
	private ArrayList<Boss> itsBoss = new ArrayList<Boss>();
	private transient EClosed_pocket eClosed_pocket;
	private int [] indices = {0, 0, 0, 0, 0, 0, 0};
	
	
	
	public GeneralClosedPocket()
	{
		super(Cavidade.CAVIDADE_PERFIL_GERAL);
	}
	public GeneralClosedPocket(double radius, ArrayList<Point2D> vertexPoints)
	{
		super(Cavidade.CAVIDADE_PERFIL_GERAL);
		this.vertexPoints = vertexPoints;
		this.radius = radius;
	}
	
	public double getProfundidade()
	{
		return profundidade;
	}
	public void setProfundidade(double profundidade)
	{
		this.profundidade = profundidade;
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	public ArrayList<Point2D> getPoints()
	{
		return vertexPoints;
	}
	public ArrayList<Point2D> getVertexPoints() 
	{
		return vertexPoints;
	}
	public void setPoints(ArrayList<Point2D> points) 
	{
		this.vertexPoints = points;
	}
	public void addBoss(Boss boss)
	{
		indices[boss.getTipo()]++;
		boss.setIndice(indices[boss.getTipo()]);
		this.itsBoss.add(boss);
		JanelaPrincipal.setDoneCAPP(false);
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("General Closed Profile Pocket -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("pos Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		root.add(new DefaultMutableTreeNode("Radius = " + this.getRadius()));
		
		if(this.itsBoss.size() > 0)
		{
			DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
			root.add(bossNode);
			for(int i = 0; i < this.itsBoss.size(); i++)
			{
				if(this.itsBoss.get(i).getClass() == CircularBoss.class)
				{
					CircularBoss circular = (CircularBoss)this.itsBoss.get(i);
					bossNode.add(circular.getNodo());
				} else if(this.itsBoss.get(i).getClass() == RectangularBoss.class)
				{
					RectangularBoss rectangular = (RectangularBoss)this.itsBoss.get(i);
					bossNode.add(rectangular.getNodo());
				} else if(this.itsBoss.get(i).getClass() == GeneralProfileBoss.class)
				{
					GeneralProfileBoss general = (GeneralProfileBoss)this.itsBoss.get(i);
					bossNode.add(general.getNodo());
				}
			}
		}
		
		if(this.vertexPoints.size() > 0)
		{
			DefaultMutableTreeNode vertexNode = new DefaultMutableTreeNode("Vertex Points");
			for(int i = 0; i < this.vertexPoints.size(); i++)
			{
				DefaultMutableTreeNode pointNode = new DefaultMutableTreeNode("P - " + i + " : " + vertexPoints.get(i));
				vertexNode.add(pointNode);
			}
			root.add(vertexNode);
		}
		this.getNodoWorkingSteps(root);
		return root;
	}
	
	public boolean validarBoss(Boss boss)
	{

		boolean isValid = false;
		Point2D [] bordaCircular = null;  //usado para discretizar a borda do boss
		
		forma = new GeneralPath();
		ArrayList<Point2D> vertices = CreateGeneralPocket.transformPolygonInRoundPolygon(vertexPoints, radius);
		forma.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
		for(int i = 1; i < vertices.size(); i++)
		{
			forma.lineTo(vertices.get(i).getX(), vertices.get(i).getY());
		}
		forma.closePath();
		
		if(boss.getClass() == CircularBoss.class)
		{

			CircularBoss cb = (CircularBoss)boss;
			
			double posX = cb.getPosicaoX();
			double posY = cb.getPosicaoY();
			double posZ = cb.getPosicaoZ();
			double raioMaiorBoss = cb.getDiametro2()/2;
			double n = 2*Math.PI*raioMaiorBoss;
			int numPontos = (int)n;
			
			/** Discretiza a borda do CircularBoss **/
			
			bordaCircular = cavidade.determinarPontosEmCircunferencia (new Point3d(posX,posY,posZ), 0.0, 2*Math.PI, raioMaiorBoss, numPontos);
			
			for (int i=0; i < bordaCircular.length; i++){

				if(!forma.contains(bordaCircular[i])) // verifica se o novo boss esta dentro da cavidade
				{
					isValid = false;
					JOptionPane.showMessageDialog(null, "The Boss intersects with the wall of the closed pocket", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
				break;
				} else
				{
					isValid = true; //tem q ser true pq se nao tiver outro boss o programa nao entra no "for" seguinte
					/** verificacao de intersecao entre o novo circularBoss e os outros Boss*/
					for (int j = 0; j < this.itsBoss.size(); j ++)
					{
						
						Boss bossTmp = this.itsBoss.get(j);
						
						if(bossTmp.getClass() == CircularBoss.class)
						{
							double rad = 0;
							CircularBoss cbTmp = (CircularBoss)bossTmp;
							if(cbTmp.getDiametro1() >= cbTmp.getDiametro2())
								rad = cbTmp.getDiametro1() / 2;							
							else
								rad = cbTmp.getDiametro2() / 2;
							
							Ellipse2D bossCTmp = new Ellipse2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);
							if (bossCTmp.contains(bordaCircular[i]))
							{
								JOptionPane.showMessageDialog(null, "The Boss intersects with other Circular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
								isValid = false;
								return isValid;
								
							} else
							{
								isValid = true;
							}
							break;
						}else if(bossTmp.getClass() == RectangularBoss.class)
						{
							RectangularBoss rectangularBoss = (RectangularBoss)bossTmp;
							RoundRectangle2D bossAuxTmp = new RoundRectangle2D.Double(rectangularBoss.X, rectangularBoss.Y, rectangularBoss.getL1(), rectangularBoss.getL2(),rectangularBoss.getRadius(), rectangularBoss.getRadius() );
							if(bossAuxTmp.contains(bordaCircular[i]))
							{
								JOptionPane.showMessageDialog(null, "The Boss intersects a Rectangular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
								isValid = false;
								return isValid;
								
							} else
							{
								isValid = true;
							}
							break;
						}
					}
				}

			}
			
		} else if(boss.getClass() == RectangularBoss.class){
		
	 		RectangularBoss recBoss = (RectangularBoss)boss;
	 		Point2D [] bordaRect = null;
	 		double posX = recBoss.getPosicaoX();
			double posY = recBoss.getPosicaoY();
			double posZ = recBoss.getPosicaoZ();
			double comprimento = recBoss.getL1();
			double largura = recBoss.getL2();
			double raio = recBoss.getRadius();

	 		bordaRect = cavidade.determinarPontosEmRoundRectangular(new Point3d(posX,posY,posZ), comprimento, largura, raio);
	
	 		for (int j=0; j < bordaRect.length; j++){
//	 			System.out.println("bordaRect[j]" + bordaRect[j] + j);
	 			if(!forma.contains(bordaRect[j])) // verifica se o novo boss esta dentro da cavidade
		 		{
		 			isValid = false;
		 			JOptionPane.showMessageDialog(null, "The Boss intersects with the wall of the closed pocket", "Error at creating the rectangular boss", JOptionPane.OK_CANCEL_OPTION);
		 			break;
		 		} else
		 		{
		 			isValid = true;
		 			/** verificacao de intersecao entre o novo rectangularBoss e os outros Boss*/
		 			for (int i = 0; i < this.itsBoss.size(); i ++)
		 			{
		 				Boss bossTmp = this.itsBoss.get(i);
		 				if(bossTmp.getClass() == CircularBoss.class)
		 				{
		 					double rad = 0;
		 					CircularBoss cbTmp = (CircularBoss)bossTmp;
		 					if(cbTmp.getDiametro1() >= cbTmp.getDiametro2())
		 						rad = cbTmp.getDiametro1() / 2;							
		 					else
		 						rad = cbTmp.getDiametro2() / 2;
				
		 					Ellipse2D bossCTmp = new Ellipse2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);

		 					if (bossCTmp.contains(bordaRect[j]))
		 					{
		 						JOptionPane.showMessageDialog(null, "The Boss intersects with other Circular Boss \n ", "Error at creating the rectangular boss", JOptionPane.OK_CANCEL_OPTION);
		 						isValid = false;
		 						return isValid;
		 					} else
		 					{
		 						isValid = true;
		 						
		 					}
		 					
		 				}else if(bossTmp.getClass() == RectangularBoss.class)
		 				{
		 					RectangularBoss rectangularBoss = (RectangularBoss)bossTmp;
 					
		 					RoundRectangle2D bossAuxTmp = new RoundRectangle2D.Double(rectangularBoss.getPosicaoX(), rectangularBoss.getPosicaoY(), rectangularBoss.getL1(), rectangularBoss.getL2(), rectangularBoss.getRadius(), rectangularBoss.getRadius());
		 					if(bossAuxTmp.contains(bordaRect[j]))
		 					{
		 						JOptionPane.showMessageDialog(null, "The Boss intersects a Rectangular Boss \n ", "Error at creating the rectangular boss", JOptionPane.OK_CANCEL_OPTION);
		 						isValid = false;
		 						return isValid;
		 					} else
		 					{
		 						isValid = true;
		 					}
		 					
		 					break;
		 				}else if(bossTmp.getClass() == GeneralProfileBoss.class)
		 				{
		 					Shape shape;
							GeneralProfileBoss gpb = (GeneralProfileBoss)bossTmp;
							shape = gpb.getForma();
							if(shape.contains(bordaRect[j]))
							{
								JOptionPane.showMessageDialog(null, "The Boss intersects a General Boss \n ", "Error at creating the rectangular boss", JOptionPane.OK_CANCEL_OPTION);
								isValid = false;
								return isValid;
							}else
							{
								isValid = true;
							}
							break;
		 				}
		 			}	
		 		}
	 		}
			 		
		}
		
		return isValid;
	
	}
	
	public GeneralPath getForma() 
	{
		return forma;
	}
	public void setForma(GeneralPath forma) 
	{
		this.forma = forma;
	}
	public ArrayList<Boss> getItsBoss() {
		return itsBoss;
	}
	public void setItsBoss(ArrayList<Boss> itsBoss) {
		this.itsBoss = itsBoss;
	}
	public boolean isPassante() {
		return isPassante;
	}
	public void setPassante(boolean isPassante) {
		this.isPassante = isPassante;
	}
	public EClosed_pocket geteClosed_pocket() {
		return eClosed_pocket;
	}
	public void seteClosed_pocket(EClosed_pocket eClosed_pocket) {
		this.eClosed_pocket = eClosed_pocket;
	}
	
	
}
