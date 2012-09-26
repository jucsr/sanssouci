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

import jsdai.SCombined_schema.ABoss;
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
	private ABoss aBoss;
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

	public Point3d getPosition3D(){
		
		Point3d point = new Point3d(locX, locY, locZ);
		
		return point;
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
			} else if(this.itsBoss.get(i).getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss general = (GeneralProfileBoss)this.itsBoss.get(i);
				bossNode.add(general.getNodo());
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

			CircularBoss cb = (CircularBoss)boss;
			
			double posX = cb.getPosicaoX();
			double posY = cb.getPosicaoY();
			double posZ = cb.getPosicaoZ();
			double raioMaiorBoss = cb.getDiametro2()/2;
			double n = 2*Math.PI*raioMaiorBoss;
			int numPontos = (int)n;
						
			/** Discretiza a borda do CircularBoss **/
			
			borda = determinarPontosEmCircunferencia (new Point3d(posX,posY,posZ), 0.0, 2*Math.PI, raioMaiorBoss, numPontos);
			
			for (int i=0; i < borda.length; i++){
				System.out.println("borda" + borda[i]);
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
							if (bossCTmp.contains(borda[i]))
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
							if(bossAuxTmp.contains(borda[i]))
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
			 			
					/**
					 *  implementar para rectangular boss!!!
					 */
			 			
			 		RectangularBoss recBoss = (RectangularBoss)boss;
			 		Point2D [] bordaRect = null;
			 		double posX = recBoss.getPosicaoX();
					double posY = recBoss.getPosicaoY();
					double posZ = recBoss.getPosicaoZ();
					double comprimento = recBoss.getL1();
					double largura = recBoss.getL2();
					double raio = recBoss.getRadius();
	
			 		bordaRect = determinarPontosEmRoundRectangular(new Point3d(posX,posY,posZ), comprimento, largura, raio);
			
			 		for (int j=0; j < bordaRect.length; j++){
			 			System.out.println("bordaRect[j]" + bordaRect[j] + j);
			 			if(!cavidade.contains(bordaRect[j])) // verifica se o novo boss esta dentro da cavidade
				 		{
				 			isValid = false;
				 			JOptionPane.showMessageDialog(null, "The Boss intersects with the wall of the closed pocket", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
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
				 						JOptionPane.showMessageDialog(null, "The Boss intersects with other Circular Boss \n ", "Error at creating the circular boss", JOptionPane.OK_CANCEL_OPTION);
				 						isValid = false;
				 						return isValid;
				 					} else
				 					{
				 						isValid = true;
				 						
				 					}
				 					
				 				}else if(bossTmp.getClass() == RectangularBoss.class)
				 				{
				 					RectangularBoss rectangularBoss = (RectangularBoss)boss;
		 					
				 					RoundRectangle2D bossAuxTmp = new RoundRectangle2D.Double(rectangularBoss.getPosicaoX(), rectangularBoss.getPosicaoY(), rectangularBoss.getL1(), rectangularBoss.getL2(), rectangularBoss.getRadius(), rectangularBoss.getRadius());
				 					if(bossAuxTmp.contains(bordaRect[j]))
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
			 		
			}
		
		return isValid;
	}
	
	public static Point2D[] determinarPontosEmReta(Point3d InitialPos, Point3d EndPos)
	{
		
		double angInclinacao = Math.atan2(EndPos.y - InitialPos.y, EndPos.x - InitialPos.x);
		double comprimento = Math.sqrt(Math.pow((EndPos.y - InitialPos.y), 2) + Math.pow(EndPos.x - InitialPos.x, 2));
		int numPontos = (int)comprimento; //numPontos = comprimento/1mm
		double passo = (int) (comprimento/numPontos);
		double x = InitialPos.x ,y = InitialPos.y;
		
		Point2D[] saida = new Point2D [numPontos];
			
			for (int i = 0; i < numPontos; i++)
			{
				x =  x + passo*Math.cos(angInclinacao);
				y =  y + passo*Math.sin(angInclinacao);
				
				saida[i] = new Point2D.Double(x, y);
				System.out.println("saida reta= " + saida[i] + i);
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
		Point2D[] saida = new Point2D [numeroDePontos];
		double x, y, dAngulo = 0;

		dAngulo = deltaAngulo / numeroDePontos;
		anguloInicial = anguloInicial + 0.01;
		
		for(int i = 0; i < numeroDePontos; i++)
		{
			x = center.x + raio * Math.cos(anguloInicial + i * dAngulo);
			y = center.y + raio * Math.sin(anguloInicial + i * dAngulo);
			
			saida[i] = new Point2D.Double(x, y);
			
			//System.out.println("posicao x, y : " + x  +y);
		}
		System.out.println("angulo final : " + ( numeroDePontos* dAngulo)*180/Math.PI );
		System.out.println("tamanho saida : " + saida.length);
		System.out.println("numeroPontos : " + numeroDePontos);

		return saida;
	}
	public static Point2D[] determinarPontosEmCircunferenciaV2(Point3d center, double anguloInicial, double deltaAngulo, double raio)  
	{
		int numeroDePontos = (int)(deltaAngulo); //deltaAngulo em rad
		Point2D[] saida = new Point2D [numeroDePontos];
		double x, y, dAngulo = 0;

		dAngulo = deltaAngulo / numeroDePontos;
		
		for(int i = 0; i < numeroDePontos; i++)
		{
			x = center.x + raio * Math.cos(anguloInicial + i * dAngulo);
			y = center.y + raio * Math.sin(anguloInicial + i * dAngulo);
			
			saida[i] = new Point2D.Double(x, y);
		}
		System.out.println("angulo final : " + ( numeroDePontos* dAngulo)*180/Math.PI );

		return saida;
	}
	public static Point2D[] determinarPontosEmRoundRectangular(Point3d position, double comprimento, double largura, double raio) // o position � a coodenada da origem do roundRectangular. Neste m�todo � calculado o n�mero de pontos considerando que se quer 1mm entre eles
	{
		
		double n = (2*(Math.PI)*raio)/4;
		int numeroDePontosBorda = (int)n;
		int numPontosComprimento = (int)(2*comprimento - 4*raio); //numero de pontos das 2 retas
		int numPontosLargura = (int)(2*largura - 4*raio);
		
//		System.out.println("numPontosComprimento" + numPontosComprimento);
//		System.out.println("numPontosLargura" + numPontosLargura);
//		System.out.println("numPontosTodaBorda" + 4*numeroDePontosBorda);
		
		int numeroDePontosLinhaHor = numPontosComprimento;
		int numeroDePontosLinhaVer = numPontosLargura;
		int numTotalPontos = 4*numeroDePontosBorda + numPontosComprimento + numPontosLargura;
		
		Point2D[] saida = new Point2D [numTotalPontos];
		
		//System.out.println("tamanho array Saida= " + numTotalPontos );
		
		Point2D[] borda1 = new Point2D [numeroDePontosBorda];
		Point2D[] borda2 = new Point2D [numeroDePontosBorda];
		Point2D[] borda3 = new Point2D [numeroDePontosBorda];
		Point2D[] borda4 = new Point2D [numeroDePontosBorda];
		
		Point2D[] linhaHor1 = new Point2D [numeroDePontosLinhaHor];  	//linha horizontal superior
		Point2D[] linhaHor2 = new Point2D [numeroDePontosLinhaHor];		//linha horizontal inferior
		Point2D[] linhaVer1 = new Point2D [numeroDePontosLinhaVer];		//vertical esquerda
		Point2D[] linhaVer2 = new Point2D [numeroDePontosLinhaVer];		//vertical direita
		
		Point3d iniPos1 = new Point3d(position.x + comprimento - raio, position.y + largura, 0.0);
		Point3d endPos1 = new Point3d(position.x + raio, position.y + largura, 0.0);
		Point3d iniPos2 = new Point3d(position.x + 0.0, position.y + largura - raio, 0.0);
		Point3d endPos2 = new Point3d(position.x + 0.0, position.y + raio, 0.0);
		Point3d iniPos3 = new Point3d(position.x + raio, position.y + 0.0, 0.0);
		Point3d endPos3 = new Point3d(position.x + comprimento - raio, position.y + 0.0, 0.0);
		Point3d iniPos4 = new Point3d(position.x + comprimento, position.y + raio, 0.0);
		Point3d endPos4 = new Point3d(position.x + comprimento, position.y + largura - raio, 0.0);
		
//		System.out.println("iniPos1" + iniPos1);
//		System.out.println("endPos1" + endPos1);
//		System.out.println("iniPos2" + iniPos2);
//		System.out.println("endPos2" + endPos2);
//		System.out.println("iniPos3" + iniPos3);
//		System.out.println("endPos3" + endPos3);
//		System.out.println("iniPos4" + iniPos4);
//		System.out.println("endPos4" + endPos4);
//		
		Point3d center1 = new Point3d(position.x + comprimento - raio, position.y + largura - raio, 0.0);
		Point3d center2 = new Point3d(position.x + raio, position.y + largura - raio, 0.0);
		Point3d center3 = new Point3d(position.x + raio, position.y + raio, 0.0);
		Point3d center4 = new Point3d(position.x + comprimento - raio, position.y + raio, 0.0);
		
//		System.out.println("center1" + center1);
//		System.out.println("center2" + center2);
//		System.out.println("center3" + center3);
//		System.out.println("center4" + center4);
//		
		borda1 = determinarPontosEmCircunferencia(center1, 0.0, Math.PI/2, raio, numeroDePontosBorda);
		borda2 = determinarPontosEmCircunferencia(center2, Math.PI/2 , Math.PI/2, raio, numeroDePontosBorda);
		borda3 = determinarPontosEmCircunferencia(center3, Math.PI, Math.PI/2, raio, numeroDePontosBorda);
		borda4 = determinarPontosEmCircunferencia(center4, 3*(Math.PI)/2, Math.PI/2, raio, numeroDePontosBorda);
		
//		System.out.println("pontos borda 1 " + borda1.length );
//		System.out.println("pontos borda 2 " + borda2.length );
//		System.out.println("pontos borda 3 " + borda3.length );
//		System.out.println("pontos borda 4 " + borda4.length );

		
			for (int j=0; j < borda1.length; j++){
			
				saida[j] = new Point2D.Double(borda1[j].getX(), borda1[j].getY());
				saida[borda1.length + j] = borda2[j];
				saida[2*borda1.length + j] = borda3[j];
				saida[3*borda1.length + j] = borda4[j];
			}
		
			linhaHor1 = determinarPontosEmReta(iniPos1, endPos1);
			linhaHor2 = determinarPontosEmReta(iniPos3, endPos3);
			
			for(int j=0; j < linhaHor1.length; j++){
			
				saida[4*borda1.length + j] = linhaHor1[j];
				saida[4*borda1.length + linhaHor1.length + j] = linhaHor2[j];
			}
		
			linhaVer1 = determinarPontosEmReta(iniPos2, endPos2);	
			linhaVer2 = determinarPontosEmReta(iniPos4, endPos4);
			
//			System.out.println("pontos linhaHor1 " + linhaHor1.length );
//			System.out.println("pontos linhaHor2 " + linhaHor2.length );
//			System.out.println("pontos linhaVer1 " + linhaVer1.length );
//			System.out.println("pontos linhaVer2 " + linhaVer2.length );
			
			for(int j=0; j < linhaVer1.length; j++){
				
				saida[4*borda1.length + 2*linhaHor1.length + j] = linhaVer1[j];
				saida[4*borda1.length + 2*linhaHor1.length + linhaVer1.length + j] = linhaVer2[j];
				
			}
			
		return saida;
	}

	public ABoss getaBoss() 
	{
		return aBoss;
	}

	public void setaBoss(ABoss aBoss) 
	{
		this.aBoss = aBoss;
	}

}
