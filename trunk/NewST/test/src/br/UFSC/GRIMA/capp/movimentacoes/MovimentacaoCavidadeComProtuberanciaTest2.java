package br.UFSC.GRIMA.capp.movimentacoes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
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
		
//		points.add(new Point2D.Double(2,40));
//      points.add(new Point2D.Double(2,80));
//      points.add(new Point2D.Double(120,80));
//      points.add(new Point2D.Double(120,10));
//      points.add(new Point2D.Double(50,10));
//      points.add(new Point2D.Double(50,40));
//         
//		points.add(new Point2D.Double(2, 80));
//		points.add(new Point2D.Double(150, 80));
//		points.add(new Point2D.Double(120, 10));
//		points.add(new Point2D.Double(80, 10));
//		points.add(new Point2D.Double(70, 40));
//		points.add(new Point2D.Double(50, 40));
//		points.add(new Point2D.Double(40, 10));
//		points.add(new Point2D.Double(2, 10));
		
		points.add(new Point2D.Double(2, 2));
		points.add(new Point2D.Double(100, 2));
		points.add(new Point2D.Double(100, 80));
//		points.add(new Point2D.Double(80, 10));
		
		points = GeometricOperations.scalePoints(points, 4);
		this.cavidadeGeral.setPoints(points);
		
		// --- Criando Machining workingstep ----
		
		// ---- criando Operacao ----
		MachiningOperation operation = new BottomAndSideRoughMilling("Desbaste", 5);
		operation.setCoolant(true);
		
		// ---- criando Ferramenta ----
		this.ferramenta = new FaceMill();

		this.ferramenta.setDiametroFerramenta(40);

		this.ferramenta.setMaterialClasse(Material.ACO_ALTA_LIGA);
		
		// ---- criando Condicoes de usinagem -----S
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setAp(2);
		cond.setAe(20);
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
				
				GeneralPath shape = GeometricOperations.linearPathToGeneralPath(GeometricOperations.elementsLinearPath(elementsAcabamento));
				
//				ArrayList<ArrayList<LimitedElement>> multipleParallel = GeometricOperations.multipleParallelPath(elementsAcabamento, ws.getCondicoesUsinagem().getAe());
				
				ArrayList<GeneralPath> shapes = new ArrayList<GeneralPath>();

				g2d.draw(shape);
				
//				for(ArrayList<LimitedElement> elements:multipleParallel)
//				{
//					shapes.add(GeometricOperations.linearPathToGeneralPath(GeometricOperations.elementsLinearPath(elements)));										
//				}
				
				for (GeneralPath s:shapes)
				{
					g2d.setColor(new Color((int)(Math.random()*254), (int)(Math.random()*254), (int)(Math.random()*254)));
					g2d.draw(s);
				}
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
