package br.UFSC.GRIMA.entidades.features;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import com.lksoft.util.logging.SystemOutHandler;

import jsdai.SCombined_schema.EClosed_pocket;
import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class Cavidade extends Feature implements Serializable {

	private double locX;
	private double locY;
	private double locZ;
	private double raio;
//  private double verticeRaio;
	private double largura;
	private double comprimento;
	private double profundidade;
	private ArrayList<Boss> itsBoss = new ArrayList<Boss>();
	private ArrayList<LimitedElement> geometricalElements;
	
	private transient EClosed_pocket eClosed_pocket;
	
	private boolean passante = false;
	private int [] indices = {0, 0, 0, 0, 0, 0, 0};
	
	public Cavidade() {
		super(Feature.CAVIDADE);
//		this.createGeometricalElements();
	}

	public ArrayList<Boss> getItsBoss() {
		return itsBoss;
	}

	public void setItsBoss(ArrayList<Boss> itsBoss) {
		this.itsBoss = itsBoss;
	}

	public ArrayList<LimitedElement> getGeometricalElements() {
		return geometricalElements;
	}

	public void setGeometricalElements(ArrayList<LimitedElement> geometricalElements) {
		this.geometricalElements = geometricalElements;
	}

	public Cavidade(String nome, double x, double y, double z, double locX, double locY, double locZ, double verticeRaio,
			double largura, double comprimento, double profundidade) {
		super(Feature.CAVIDADE);
		setNome(nome);
		setPosicao(x, y, z);
		setPosicaoNorma(locX,locY,locZ);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();
	}
	
	public Cavidade(String nome, double x, double y, double z, double verticeRaio,
			double largura, double comprimento,	double profundidade) {
		super(Feature.CAVIDADE);
		setNome(nome);
		setPosicao(x, y, z);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();

	}
	
	public Cavidade(double x, double y, double z, double verticeRaio,
			double largura, double comprimento, double profundidade) {
		super(Feature.CAVIDADE);
		setPosicao(x, y, z);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();

	}
	public void addBoss(Boss boss){
		indices[boss.getTipo()]++;
		boss.setIndice(indices[boss.getTipo()]);
		this.itsBoss.add(boss);
		JanelaPrincipal.setDoneCAPP(false);
	}
	public DefaultMutableTreeNode getNodo() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Closed Pocket -"
				+ this.getIndice());
		root.add(new DefaultMutableTreeNode("Name: "
				+ this.getNome()));
		root.add(new DefaultMutableTreeNode("Length = "
				+ this.getComprimento()));
		root.add(new DefaultMutableTreeNode("Largura = " + this.getLargura()));
		root.add(new DefaultMutableTreeNode("Depth = "
				+ this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("Corner radius = " + this.getRaio()));
		root.add(new DefaultMutableTreeNode("Position X = "
				+ this.getPosicaoX()));
		root.add(new DefaultMutableTreeNode("Position Y = "
				+ this.getPosicaoY()));
		root.add(new DefaultMutableTreeNode("Position Z = "
				+ this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Tolerance = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Roughness = " + this.getRugosidade()));
		
		DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
		root.add(bossNode);
		
		for(int i = 0; i < this.itsBoss.size(); i++)
		{
			if(this.itsBoss.get(i).getClass() == CircularBoss.class)
			{
				CircularBoss circular = (CircularBoss)this.itsBoss.get(i);
				bossNode.add(circular.getNode());
			} else if(this.itsBoss.get(i).getClass() == RectangularBoss.class)
			{
				RectangularBoss rectangular = (RectangularBoss)this.itsBoss.get(i);
				bossNode.add(rectangular.getNode());
			}
		}
		this.getNodoWorkingSteps(root);
		
		return root;
	}

	public double getRaio() {
		return raio;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}
	
	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getLargura() {
		return largura;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public double getComprimento() {
		return this.comprimento;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getProfundidade() {
		return this.profundidade;
	}

	public double getVerticeAngulo() {
		return raio;
	}

	public void setVerticeAngulo(double verticeAngulo) {
		this.raio = verticeAngulo;
	}

	public void imprimirDados() {
		System.out.print("*********CAVIDADE " + this.getIndice() + "********");
		System.out.print("\n Profundidade = " + this.getProfundidade());
		System.out.print("\t Raio = " + this.getRaio());
		System.out.print("\t Comprimento = " + this.getComprimento());
		System.out.print("\t Largura = " + this.getLargura());
		System.out.print("\t Posição X = " + this.getPosicaoX());
		System.out.print("\t Posição Y = " + this.getPosicaoY());
		System.out.print("\t Posição Z = " + this.getPosicaoZ());
		System.out.print("\n CAVIDADE adicionada com sucesso\n");
	}

	public EClosed_pocket geteClosed_pocket() {
		return eClosed_pocket;
	}

	public void seteClosed_pocket(EClosed_pocket eClosed_pocket) {
		this.eClosed_pocket = eClosed_pocket;
	}

	public boolean isPassante() {
		return passante;
	}

	public void setPassante(boolean passante) {
		this.passante = passante;
	}

	public void setPosicaoNorma(double locX, double locY, double locZ){
		
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
		
	}
	
	public double getLocX() {
		return locX;
	}

	public void setLocX(double locX) {
		this.locX = locX;
	}

	public double getLocY() {
		return locY;
	}

	public void setLocY(double locY) {
		this.locY = locY;
	}

	public double getLocZ() {
		return locZ;
	}

	public void setLocZ(double locZ) {
		this.locZ = locZ;
	}
	public void createGeometricalElements()
	{
		int n = 21;
		this.geometricalElements = new ArrayList<LimitedElement>();
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.raio, this.Y + this.raio, this.Z), new Point3d(this.X, this.Y + this.raio, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.raio, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.raio, this.Y + this.largura, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.comprimento - this.raio, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.comprimento, this.Y + this.largura - this.raio, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.comprimento - this.raio, this.Y + this.raio, this.Z), new Point3d(this.X + this.comprimento - this.raio, this.Y, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X, this.Y + this.raio, this.Z), new Point3d(this.X, this.Y + this.largura - this.raio, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.raio, this.Y + this.largura, this.Z), new Point3d(this.X + this.comprimento - this.raio, this.Y + this.largura, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.comprimento, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.comprimento, this.Y + this.raio, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.comprimento - this.raio, this.Y, this.Z), new Point3d(this.X + this.raio, this.Y, this.Z)));
	}
	/**
	 * This method should check the bosses are within closed pocked, and they do not collide between they self and the closed pocket
	 * @param boss
	 * @return is valid condition
	 */
	public boolean validarBoss(Boss boss)
	{
		boolean isValid = false;
		Point2D [] borda = null;  //usado para discretizar a borda do boss
		RoundRectangle2D cavidade = new RoundRectangle2D.Double(X, Y, comprimento, largura, 2 * raio, 2 * raio);
		
		if(boss.getClass() == CircularBoss.class)
		{
			double radius;
			CircularBoss cb = (CircularBoss)boss;
			
			double posX = cb.getPosicaoX();
			double posY = cb.getPosicaoY();
			double posZ = cb.getPosicaoZ();
			double raioMaiorBoss = cb.getDiametro2()/2;
			double n = 2*Math.PI*raioMaiorBoss;
			int numPontos = (int)n;
			
//			Point3d[] borda = null;
			
			if(cb.getDiametro1() >= cb.getDiametro2())
				radius = cb.getDiametro1() / 2;
			else
				radius = cb.getDiametro2() / 2;
			
			/** Discretiza a borda do CircularBoss **/
			
			borda = determinarPontosEmCircunferencia (new Point3d(posX,posY,posZ), 0.0, 2*Math.PI, raioMaiorBoss, numPontos);
			
			for (int i=0; i < borda.length; i++){System.out.println("borda" + borda[i]);
				if(!cavidade.contains(borda[i])) // verifica se o novo boss esta dentro da cavidade
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
							//Rectangle2D bossAux = new Rectangle2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);
							if (bossCTmp.contains(borda[i]))
							{
								JOptionPane.showMessageDialog(null, "The Boss intersects with other Circular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
								isValid = false;
								break;
							} else
							{
								isValid = true;
							}
							break;
						}else if(bossTmp.getClass() == RectangularBoss.class)
						{
							RectangularBoss rectangularBoss = (RectangularBoss)boss;
							RoundRectangle2D bossAuxTmp = new RoundRectangle2D.Double(rectangularBoss.X, rectangularBoss.Y, rectangularBoss.getL1(), rectangularBoss.getL2(),rectangularBoss.getRadius(), rectangularBoss.getRadius() );
							if(bossAuxTmp.contains(borda[i]))
							{
								JOptionPane.showMessageDialog(null, "The Boss intersects a Rectangular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
								isValid = false;
							} else
							{
								isValid = true;
							}
							break;
						}
					}
				}

			}
			
			//Rectangle2D novoCircBossRect2D = new Rectangle2D.Double(cb.X - radius, cb.Y - radius, radius * 2,radius * 2);
			 		} else if(boss.getClass() == RectangularBoss.class)
			{
//			/**
//			 *  implementar para rectangular boss!!!
//			 */
//			RectangularBoss rectangularBoss = (RectangularBoss)boss;
//			RoundRectangle2D novoRecBossRect2D = new RoundRectangle2D.Double(rectangularBoss.X, rectangularBoss.Y, rectangularBoss.getL1(),rectangularBoss.getL2(), rectangularBoss.getRadius(),rectangularBoss.getRadius());
//			Rectangle2D bossAux = new Rectangle2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);
//			
//			
//			if(!cavidade.contains(novoRecBossRect2D)) // verifica se o novo boss esta dentro da cavidade
//			{
//				isValid = false;
//				JOptionPane.showMessageDialog(null, "The Boss intersects with the wall of the closed pocket", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
//			} else
//			{
//				isValid = true;
//				/** verificacao de intersecao entre o novo rectangularBoss e os outros Boss*/
//				for (int i = 0; i < this.itsBoss.size(); i ++)
//				{
//					Boss bossTmp = this.itsBoss.get(i);
//					if(bossTmp.getClass() == CircularBoss.class)
//					{
//						double rad = 0;
//						CircularBoss cbTmp = (CircularBoss)bossTmp;
//						if(cbTmp.getDiametro1() >= cbTmp.getDiametro2())
//							rad = cbTmp.getDiametro1() / 2;							
//						else
//							rad = cbTmp.getDiametro2() / 2;
//						
////						RectangularBoss rbTmp = (RectangularBoss)bossTmp;
//						
//						Ellipse2D bossCTmp = new Ellipse2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);
//						Rectangle2D bossAux = new Rectangle2D.Double(cbTmp.X - rad, cbTmp.Y - rad, rad * 2, rad * 2);
//						if (bossCTmp.intersects(novoRecBossRect2D) || bossCTmp.contains(novoRecBossRect2D) || novoRecBossRect2D.contains(bossAux))
//						{
//							JOptionPane.showMessageDialog(null, "The Boss intersects with other Circular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
//							isValid = false;
//						} else
//						{
//							isValid = true;
//						}
//						break;
//					}else if(bossTmp.getClass() == RectangularBoss.class)
//					{
////						RectangularBoss rectangularBoss = (RectangularBoss)boss;
//						Rectangle2D bossAuxTmp = new Rectangle2D.Double(rectangularBoss.X, rectangularBoss.Y, rectangularBoss.getL1(), rectangularBoss.getL2());
//						if(bossAuxTmp.contains(novoRecBossRect2D) || bossAuxTmp.intersects(novoRecBossRect2D) || novoRecBossRect2D.contains(bossAuxTmp))
//						{
//							JOptionPane.showMessageDialog(null, "The Boss intersects a Rectangular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
//							isValid = false;
//						} else
//						{
//							isValid = true;
//						}
//						break;
//					}
//				}
//			}
			}
		
//		for(int i = 0; i < this.itsBoss.size(); i++)
//		{
//			Boss bossTmp = this.itsBoss.get(i);
//			if(boss.getClass() == CircularBoss.class)
//			{
//				double radius;
//				CircularBoss cbTmp = (CircularBoss)boss;
//				if(cbTmp.getDiametro1() >= cbTmp.getDiametro2())
//					radius = cbTmp.getDiametro1() / 2;
//				else
//					radius = cbTmp.getDiametro2() / 2;
//				
//				Rectangle2D rectangle = new Rectangle2D.Double(cbTmp.X - radius, cbTmp.Y - radius, radius * 2, radius * 2);
//				if(!cavidade.contains(rectangle))
//				{
//					JOptionPane.showConfirmDialog(null, "ERRO BOSS");
//				}
//			}
//		}
		return isValid;
	}
	
	public static Point2D[] determinarPontosEmReta(Point3d InitialPos, Point3d EndPos, int numeroDePontos)
	{
		Point2D[] saida = new Point2D [numeroDePontos + 1];
		double inclinacao = Math.tan((EndPos.y - InitialPos.y)/(EndPos.x - InitialPos.x));
		double angInclinacao = Math.atan(inclinacao);
		double comprimento = Math.sqrt(Math.pow((EndPos.y - InitialPos.y), 2) + Math.pow(EndPos.x - InitialPos.x, 2));
		double passo = comprimento/numeroDePontos;
		double x,y;
			
		if (((InitialPos.x <= EndPos.x) && (InitialPos.y <= EndPos.y)) || ((InitialPos.x >= EndPos.x) && (InitialPos.y <= EndPos.y))){ 
			
			for (int i = 0; i < numeroDePontos; i++)
			{
				x = InitialPos.x + passo*Math.cos(angInclinacao);
				y = InitialPos.y + passo*Math.sin(angInclinacao);
				
				saida[i] = new Point2D.Double(x, y);
			} 
			
		}else {
			
			for (int i = 0; i < numeroDePontos; i++)
			{
				x = InitialPos.x - passo*Math.cos(angInclinacao);
				y = InitialPos.y - passo*Math.sin(angInclinacao);
				
				
				saida[i] = new Point2D.Double(x, y);
			} 
		}
		
		return saida;
	}
	
	/**
	 * 
	 * @param center
	 * @param anguloInicial --> em radianos
	 * @param deltaAngulo --> eh ang de varredura total em radianos
	 * @param raio 
	 * @param numeroDePontos
	 * @return
	 */
	
	public static Point2D[] determinarPontosEmCircunferencia(Point3d center, double anguloInicial, double deltaAngulo, double raio, int numeroDePontos)  
	{
		Point2D[] saida = new Point2D [numeroDePontos + 1];
		double x, y, dAngulo = 0;

		dAngulo = deltaAngulo / numeroDePontos;
		for(int i = 0; i < numeroDePontos + 1; i++)
		{
			x = center.x + raio * Math.cos(anguloInicial + i * dAngulo);
			y = center.y + raio * Math.sin(anguloInicial + i * dAngulo);
			saida[i] = new Point2D.Double(x, y);
		}

		return saida;
	}
	public static Point2D[] determinarPontosEmRoundRectangular(Point3d position, double l1, double l2, double raio, int numeroDePontos) // o position � a coodenada da origem do roundRectangular
	{
		Point2D[] saida = new Point2D [numeroDePontos + 1];
		double dComprimento = 0, dLargura = 0;
		
		dComprimento = l2/numeroDePontos;
		dLargura = l1/numeroDePontos;
		
		
			
			
			
			saida[i] = new Point2D.Double(x,y);
		

		return saida;
	}

}
