package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MovimentacaoCavidadeComProtuberanciaTest2
{
	private Workingstep ws;
	private Ferramenta ferramenta;
	private GeneralClosedPocket cavidadeGeral;
	private ArrayList<Boss> itsBoss;
	public Projeto projeto;
	private Bloco bloco;
	private CircularBoss boss;
	private CircularBoss boss1;
	private CircularBoss boss2;
	private RectangularBoss boss4;
	private Boss boss3;
	private Face faceXY;

	
	@Before
	public void createProject()
	{
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();

		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		props.add(properties);

		Material material = new Material("ACO", Material.ACO_ALTO_CARBONO, props);

		bloco = new Bloco(200.0, 150.0, 40.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);

		projeto = new Projeto(bloco, dados);
		
		this.cavidadeGeral =new GeneralClosedPocket();
		this.cavidadeGeral.setNome("Name");
		this.cavidadeGeral.setPosicao(79, 22, 0);
		this.cavidadeGeral.setProfundidade(10);
		this.cavidadeGeral.setRadius(25);
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
		points.add(new Point2D.Double(2, 40));
		points.add(new Point2D.Double(2, 80));
		points.add(new Point2D.Double(120, 80));
		points.add(new Point2D.Double(120, 10));
		points.add(new Point2D.Double(50, 10));
		points.add(new Point2D.Double(50,40));
//         
//		points.add(new Point2D.Double(2, 80));
//		points.add(new Point2D.Double(150, 80));
//		points.add(new Point2D.Double(120, 10));
//		points.add(new Point2D.Double(80, 10));
//		points.add(new Point2D.Double(70, 40));
//		points.add(new Point2D.Double(50, 40));
//		points.add(new Point2D.Double(40, 10));
//		points.add(new Point2D.Double(2, 10));
		
//		points.add(new Point2D.Double(2, 80));
//		points.add(new Point2D.Double(150, 80));
//		points.add(new Point2D.Double(150, 10));
//		points.add(new Point2D.Double(130, 10));
//		points.add(new Point2D.Double(110, 50));
//		points.add(new Point2D.Double(90, 10));
//		points.add(new Point2D.Double(80, 10));
//		points.add(new Point2D.Double(70, 40));
//		points.add(new Point2D.Double(50, 40));
//		points.add(new Point2D.Double(40, 10));
//		points.add(new Point2D.Double(2, 10));
		
		
//		points.add(new Point2D.Double(2, 2));
//		points.add(new Point2D.Double(100, 2));
//		points.add(new Point2D.Double(100, 80));
//		
//		points.add(new Point2D.Double(20, 80));
		
		points = GeometricOperations.scalePoints(points, 4);
		this.cavidadeGeral.setPoints(points);
		
		// --- Criando Machining workingstep ----
		
		// ---- criando Operacao ----
		MachiningOperation operation = new BottomAndSideRoughMilling("Desbaste", 5);
		operation.setCoolant(true);
		
		// ---- criando Ferramenta ----
		this.ferramenta = new FaceMill();

		this.ferramenta.setDiametroFerramenta(250);

		this.ferramenta.setMaterialClasse(Material.ACO_ALTA_LIGA);
		
		// ---- criando Condicoes de usinagem -----S
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setAp(2);
		cond.setAe(10);
		cond.setF(.0123);
		cond.setN(1500);
		
		this.ws = new Workingstep();
		this.ws.setOperation(operation);
		this.ws.setFerramenta(ferramenta);
		this.ws.setFeature(cavidadeGeral);
		this.ws.setCondicoesUsinagem(cond);
		Vector workingsteps = new Vector();
		workingsteps.add(this.ws);
		cavidadeGeral.setWorkingsteps(workingsteps);
		this.faceXY.addFeature(this.cavidadeGeral);
		
	}
	@Test
	public void lintersectionLineArcTest()
	{
//		LimitedArc arc = new LimitedArc(new Point3d(101.86, 224.49, 0), new Point3d(119.74, 206.61, 0), new Point3d(101.86, 206.61, 0));
//		LimitedLine line = new LimitedLine(new Point3d(105.592, 216.064, 0), new Point3d(131.310, 209.291, 0));
//		LimitedArc arc = new LimitedArc(new Point3d(25, 40, 0), new Point3d(50, 15, 0), new Point3d(50, 40, 0));
//		LimitedArc arc = new LimitedArc(new Point3d(50, 15, 0), new Point3d(75, 40, 0), new Point3d(50, 40, 0));
//---------------------------------------------------------------------------------------------------------------------------------------
//      Funcionou		
//		LimitedArc arc = new LimitedArc(new Point3d(50, 65, 0), new Point3d(25, 40, 0), new Point3d(50, 40, 0));
//		LimitedLine line = new LimitedLine(new Point3d(30, 0, 0), new Point3d(30, 60, 0));
//---------------------------------------------------------------------------------------------------------------------------------------		
//		Nao ha intersecao:
//		LimitedArc arc = new LimitedArc(new Point3d(84.12, 69.08, 0), new Point3d(56.14, 41.31, 0), new Point3d(56.35, 69.08, 0));
//		LimitedLine line = new LimitedLine(new Point3d(43.18, 19.09, 0), new Point3d(103.85, 53.91, 0));

		LimitedArc arc = new LimitedArc(new Point3d(175.0, 285.0, 0.0), new Point3d(325.0, 135.0, 0.0), new Point3d(175.0, 135.0, 0.0));
		LimitedLine line = new LimitedLine(new Point3d(355.0, 165.0000000000001, 0.0), new Point3d(355.0, 195.00000000000034, 0.0));
		Point3d intersection = GeometricOperations.intersectionPoint(arc, line);
		System.err.println("INTERSECTION POINT = " + intersection);
	}
	@Test
	public void nearestPointArcArcTest()
	{
		LimitedArc arc1 = new LimitedArc(new Point3d(50, 102.85, 0), new Point3d(33.92, 47.53, 0), new Point3d(50, 69.47, 0));
		LimitedArc arc2 = new LimitedArc(new Point3d(74.9, 154.2, 0), new Point3d(98.69, 105.92, 0), new Point3d(98.69, 135.92, 0));
		Point3d nearest = GeometricOperations.nearestPoint(arc1, arc2);
		double minimum = GeometricOperations.minimumDistanceArcToArc(arc1, arc2);
		System.err.println("nearest = " + nearest);
		System.err.println("minimum = " + minimum);
	}
	
	@Test
	public void intersectionPointLineToLine() 
	{
		LimitedLine line1 = new LimitedLine(new Point3d(16.51, 47.84, 0), new Point3d(52.49, 17.15, 0));
//		LimitedLine line2 = new LimitedLine(new Point3d(37.08, 23.13, 0), new Point3d(52.28, 64.56, 0));
		LimitedLine line2 = new LimitedLine(new Point3d(42.29, 37.25, 0), new Point3d(52.28, 64.56, 0));
		
		//Retas que se intersectão
		LimitedLine line5 = new LimitedLine(new Point3d(10, 10, 0), new Point3d(10, 20, 0));
		LimitedLine line6 = new LimitedLine(new Point3d(15, 5, 0), new Point3d(15, 15, 0));
		
		Point3d intersection = GeometricOperations.intersectionPoint(line5, line6);
		System.err.println("intersection line line= " + intersection);
	}
	@Test 
	public void intersectionPointArcArcTest()
	{
//		LimitedArc arc1 = new LimitedArc(new Point3d(50, 102.85, 0), new Point3d(33.92, 47.53, 0), new Point3d(50, 69.47, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(74.9, 154.2, 0), new Point3d(98.69, 105.92, 0), new Point3d(98.69, 135.92, 0));
		
//		LimitedArc arc1 = new LimitedArc(new Point3d(50, 10, 0), new Point3d(0, 60, 0), new Point3d(50, 60, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(60, 30, 0), new Point3d(20, 70, 0), new Point3d(60, 70, 0));
		
//		LimitedArc arc1 = new LimitedArc(new Point3d(52.98, 43.70, 0), new Point3d(11.28, 46.61, 0), new Point3d(32.02, 43.70, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(43.39, 66.38, 0), new Point3d(35.53, 40.40, 0), new Point3d(43.39, 52.20, 0));
//		
//		Point3d intersection = GeometricOperations.intersectionPoint(arc1, arc2);
//		System.err.println("intersection = " + intersection);

//		LimitedArc arc3 = new LimitedArc(new Point3d(40, 20, 0), new Point3d(20, 20, 0), new Point3d(30, 20, 0));
//		LimitedArc arc4 = new LimitedArc(new Point3d(45, 45, 0), new Point3d(45, 15, 0), new Point3d(45, 30, 0));
//		
//		Point3d intersection2 = GeometricOperations.intersectionPoint(arc3, arc4);
//		System.err.println("intersection2 = " + intersection2);
//----------------------------------------------------------------------------------------------------------------------
//Arcos que se intersectam		
//		LimitedArc arc5 = new LimitedArc(new Point3d(30, 10, 0), new Point3d(35, 28.66, 0), new Point3d(30, 20, 0));
//		LimitedArc arc6 = new LimitedArc(new Point3d(45, 45, 0), new Point3d(45, 15, 0), new Point3d(45, 30, 0));
//		
//		Point3d intersection3 = GeometricOperations.intersectionPoint(arc5, arc6);
//		System.err.println("intersection3 = " + intersection3);
//		System.out.println("MATH ATAN2 = " + Math.atan2(0, 0));
		
//		LimitedArc arc7 = new LimitedArc(new Point3d(45, 60, 0), new Point3d(30, 45, 0), new Point3d(30, 60, 0));
//		LimitedArc arc8 = new LimitedArc(new Point3d(60, 70, 0), new Point3d(60, 70, 0), new Point3d(60, 80, 0));
//		
//		Point3d intersection4 = GeometricOperations.intersectionPoint(arc7, arc8);
//		System.err.println("intersection4 = " + intersection4);
		
//		LimitedArc arc9 = new LimitedArc(new Point3d(40, 20, 0), new Point3d(35, 28.66, 0), new Point3d(30, 20, 0));
//		LimitedArc arc10 = new LimitedArc(new Point3d(45, 45, 0), new Point3d(45, 15, 0), new Point3d(45, 30, 0));
//		
//		Point3d intersection5 = GeometricOperations.intersectionPoint(arc9, arc10);
//		System.err.println("intersection5 = " + intersection5);
		
//		System.out.println(GeometricOperations.truncarDecimais(10.0004, 10));
		LimitedArc arc11 = new LimitedArc(new Point3d(75, 130, 0), new Point3d(50, 105, 0), new Point3d(50,130 , 0));
		LimitedArc arc12 = new LimitedArc(new Point3d(50, 125, 0), new Point3d(74.45, 105.19, 0), new Point3d(50, 100, 0));
		
		Point3d intersection6 = GeometricOperations.intersectionPoint(arc11, arc12);
		System.err.println("intersection6 = " + intersection6);
		//System.out.println("MATH ATAN2 = " + Math.atan2(0, 0)); 
	}
	@Test
	public void nearestPointIntersectingArcArcTest()
	{
//		LimitedArc arc1 = new LimitedArc(new Point3d(24.16, 40.11, 0), new Point3d(34.34, 27.59, 0), new Point3d(28.36, 33.13, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(35.93, 32.65, 0), new Point3d(38.31, 11.54, 0), new Point3d(38.31, 22.22, 0));
//		LimitedArc arc1 = new LimitedArc(new Point3d(23.29, 59.04, 0), new Point3d(28.79, 24.13, 0), new Point3d(28.79, 42.12, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(73.40, 47.34, 0), new Point3d(61.38, 80.23, 0), new Point3d(66.04, 63.29, 0));
//		LimitedArc arc1 = new LimitedArc(new Point3d(52.98, 43.38, 0), new Point3d(11.28, 46.61, 0), new Point3d(32.02, 43.38, 0));
//		LimitedArc arc2 = new LimitedArc(new Point3d(43.39, 66.04, 0), new Point3d(35.53, 40.06, 0), new Point3d(43.39, 51.86, 0));
		LimitedArc arc1 = new LimitedArc(new Point3d(52.98, 43.38, 0), new Point3d(11.28, 46.61, 0), new Point3d(32.02, 43.38, 0));
		LimitedArc arc2 = new LimitedArc(new Point3d(43.39, 66.04, 0), new Point3d(35.53, 40.06, 0), new Point3d(43.39, 51.86, 0));
		
		Point3d nearest = GeometricOperations.nearestPoint(arc1, arc2);
		
//		System.out.println("NEGATIVO = " + Math.sqrt(-1));
		
		System.out.println("NEAREST = " + nearest);
		/**
		 * nao funciona quando os arcos se intersectam
		 */
	}
	@Test
	public void determinarMovimentacaoGenCavTest()
	{

		class painelTest extends JPanel{
			
			GeneralPath formaFeature = new GeneralPath();
			
			painelTest()
			{
				formaFeature.moveTo(cavidadeGeral.getVertexPoints().get(0).getX(), cavidadeGeral.getVertexPoints().get(0).getY());
				
				for(int i = 0; i < cavidadeGeral.getVertexPoints().size(); i++)
				{
					formaFeature.lineTo(cavidadeGeral.getVertexPoints().get(i).getX(), cavidadeGeral.getVertexPoints().get(i).getY());
				}
				
				formaFeature.closePath();

			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponents(g);

				Graphics2D g2d = (Graphics2D)g;
				g2d.translate(25, 325);
				g2d.scale(1, -1);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
				//--- forma original ---
				desenharFormaOriginal(g2d);
				//---- End ---
				
				g2d.setStroke(new BasicStroke());
				
				g2d.setColor(Color.black);
				
				
				GeneralClosedPocketVertexAdd addPocketVertex = new GeneralClosedPocketVertexAdd(cavidadeGeral.getVertexPoints(), cavidadeGeral.Z,cavidadeGeral.getRadius());
				
				ArrayList<LimitedElement> elementsAcabamento = GeometricOperations.acabamentoPath(addPocketVertex, ferramenta.getDiametroFerramenta() / 2);
				
				ArrayList<LimitedElement> elementsArrumados  = arrumarElementos(elementsAcabamento, ferramenta.getDiametroFerramenta() / 2);
				for(int i=0;i<elementsAcabamento.size();i++)
				{
					if(elementsAcabamento.get(i).isLimitedLine()){
						LimitedLine temp = (LimitedLine)elementsAcabamento.get(i);
						System.out.println("LimitedLine " + "l"+i+"= new " + "LimitedLine("+ "new Point3d(" + temp.getInitialPoint().x + "," + temp.getInitialPoint().y + ",0)" + "new Point3d(" + temp.getFinalPoint().x + "," + temp.getFinalPoint().y + ",0)");
					}
					else if(elementsAcabamento.get(i).isLimitedArc())
					{
						LimitedArc temp = (LimitedArc)elementsAcabamento.get(i);
						System.out.println("LimitedArc " + "arco"+i+"= new " + "LimitedArc("+ "new Point3d(" + temp.getInitialPoint().x + "," + temp.getInitialPoint().y + ",0)" + "new Point3d(" + temp.getFinalPoint().x + "," + temp.getFinalPoint().y + ",0)" + "new Point3d(" + temp.getCenter().x + "," + temp.getCenter().y + ",0)");					
						}
					
				}
				
//				System.err.println("ELEMENTOS" + elementsAcabamento.size());
//				
//				for(int i = 0; i < elementsAcabamento.size(); i++)
//				{
//					if (elementsAcabamento.get(i).getClass() == LimitedLine.class) 
//					{
//						LimitedLine lineTmp = (LimitedLine)elementsAcabamento.get(i);
//						System.err.println("Line ----> ponto inicial = " + lineTmp.getInitialPoint() + " \t ponto final = " + lineTmp.getFinalPoint());
//					} else if(elementsAcabamento.get(i).getClass() == LimitedArc.class)
//					{
//						LimitedArc arcTmp = (LimitedArc)elementsAcabamento.get(i);
//						System.err.println("Arc ----> ponto inicial = " + arcTmp.getInitialPoint() + " \t ponto final = " + arcTmp.getFinalPoint());
//					}
//				}
				
				GeneralPath shape = GeometricOperations.linearPathToGeneralPath(GeometricOperations.elementsLinearPath(elementsAcabamento));
//				
//				ArrayList<ArrayList<LimitedElement>> multipleParallel = GeometricOperations.multipleParallelPath(elementsAcabamento, ws.getCondicoesUsinagem().getAe());
//				
//				ArrayList<GeneralPath> shapes = new ArrayList<GeneralPath>();
//
				g2d.draw(shape);
				for(int i = 0; i<GeometricOperations.intersecoes.size();i++)
				{
					g2d.setColor(Color.red);
					g2d.fill(new Ellipse2D.Double(GeometricOperations.intersecoes.get(i).x - 2.5, GeometricOperations.intersecoes.get(i).y - 2.5, 5, 5));
				}
//				
//				for(ArrayList<LimitedElement> elements:multipleParallel)
//				{
//					shapes.add(GeometricOperations.linearPathToGeneralPath(GeometricOperations.elementsLinearPath(elements)));										
//				}
//				
//				for (GeneralPath s:shapes)
//				{
//					g2d.setColor(new Color((int)(Math.random()*254), (int)(Math.random()*254), (int)(Math.random()*254)));
//					g2d.draw(s);
//				}
			}
			
			private ArrayList<LimitedElement> arrumarElementos(ArrayList<LimitedElement> elementsAcabamento, double distance)
			{
				ArrayList<LimitedElement> saida = new ArrayList<LimitedElement>();
				for(int i = 0; i < elementsAcabamento.size(); i++)
				{
					if(elementsAcabamento.get(i).getClass() == LimitedArc.class)
					{
						LimitedArc tmp1 = (LimitedArc)elementsAcabamento.get(i);
						for(int j = 0; j < elementsAcabamento.size(); j++)
						{
							if(elementsAcabamento.get(j).getClass() == LimitedArc.class)
							{
								
							}else if(elementsAcabamento.get(j).getClass() == LimitedLine.class)
							{
								
							}
						}
					} else if(elementsAcabamento.get(i).getClass() == LimitedLine.class)
					{
						
					}
				}
				return saida;
			}

			private void desenharFormaOriginal(Graphics2D g2d)
			{
				g2d.setStroke(new BasicStroke(3));
				g2d.setColor(new Color(215, 0, 15));
				GeneralPath forma = new GeneralPath();
				ArrayList<Point2D> vertices = CreateGeneralPocket.transformPolygonInCounterClockPolygon(cavidadeGeral.getVertexPoints());
				ArrayList<Point2D> formaInterpolada = CreateGeneralPocket.transformPolygonInRoundPolygon(vertices, cavidadeGeral.getRadius());
				forma.moveTo(formaInterpolada.get(0).getX(), formaInterpolada.get(0).getY());
				
				for(int i = 0; i < formaInterpolada.size(); i++)
				{
					forma.lineTo(formaInterpolada.get(i).getX(), formaInterpolada.get(i).getY());
				}
				forma.closePath();
				g2d.draw(forma);
			}
		}
		JFrame frame = new JFrame("Path");
		painelTest painel = new painelTest();
		frame.setSize(700, 400);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);
	}
	
	
}
