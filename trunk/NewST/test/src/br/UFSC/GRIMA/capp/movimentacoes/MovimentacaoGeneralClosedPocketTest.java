package br.UFSC.GRIMA.capp.movimentacoes;

	import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MovimentacaoGeneralClosedPocketTest {	
	private Workingstep ws;
	private Ferramenta ferramenta;
	private GeneralClosedPocket genClosed;
	private ArrayList<Boss> itsBoss;
	public Projeto projeto;
	Bloco bloco;
	CircularBoss boss;
	RectangularBoss boss1;
	RectangularBoss boss2;
	Boss boss3;
	Face faceXY;
	ArrayList<ArrayList<Point3d>> pontos;
	ArrayList<Point3d> pontosPeriferia;
	int numeroDePontosDaMalha=150;
	double malha[][][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1][2];
	ArrayList<Point3d> pontosPossiveis, pontosPossX, pontosPossY;
	ArrayList<Point2d> coordenadas;
	ArrayList<Point2d> coor;
	ArrayList<Point3d> pontosMenores;
	double maiorMenorDistancia;
	ArrayList<Double> menorDistancia, menorX, menorY;
	ArrayList<Point3d> maximos;
	double raioMedia=0, raioMenor=10000;
	double x[] = new double[8404];
	double y[] = new double[8404];
	private GeneralPath general;
	double distMax;
	ArrayList<Point3d> testeTempo = new ArrayList<Point3d>();
	private MovimentacaoGeneralClosedPocket mov;
	
	
	@Before
	public void createProject()
	{
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();

		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		props.add(properties);

		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);

		bloco = new Bloco(200.0, 150.0, 40.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);

		projeto = new Projeto(bloco, dados);
		
		//============
		
		this.genClosed = new GeneralClosedPocket();
		this.genClosed.setNome("Name");
		this.genClosed.setPosicao(79, 22, 0);
		this.genClosed.setProfundidade(10);
		this.genClosed.setRadius(10);
		ArrayList<Point2D> points = new ArrayList<Point2D>();

		//      First Test		
//		points.add(new Point2D.Double(10,40));
//		points.add(new Point2D.Double(10,80));
//		points.add(new Point2D.Double(120,80));
//		points.add(new Point2D.Double(120,10));
//		points.add(new Point2D.Double(50,10));
//		points.add(new Point2D.Double(50,40));

		points.add(new Point2D.Double(10,10));
		points.add(new Point2D.Double(50,80));
		points.add(new Point2D.Double(120,80));
		points.add(new Point2D.Double(160,10));
		points.add(new Point2D.Double(120,10));
		points.add(new Point2D.Double(85,40));
		points.add(new Point2D.Double(50,10));		
		
		
		
//		points.add(new Point2D.Double(79,22));
//		points.add(new Point2D.Double(47,60));
//		points.add(new Point2D.Double(81,104));
//		points.add(new Point2D.Double(131,107));
//		points.add(new Point2D.Double(150,68));
//		points.add(new Point2D.Double(129,32));
//		points.add(new Point2D.Double(161,8));
//		points.add(new Point2D.Double(194,55));
//		points.add(new Point2D.Double(167,134));
//		points.add(new Point2D.Double(60,132));
//		points.add(new Point2D.Double(19,61));
//		points.add(new Point2D.Double(40,12));
		
//		points.add(new Point2D.Double(5.0, 35.0));
//		points.add(new Point2D.Double(20.0, 35.0));
//		points.add(new Point2D.Double(20.0, 5.0));
//		points.add(new Point2D.Double(60.0, 5.0));
//		points.add(new Point2D.Double(60.0, 35.0));
//		points.add(new Point2D.Double(95.0, 35.0));
//		points.add(new Point2D.Double(95.0, 65.0));
//		points.add(new Point2D.Double(125.0, 65.0));
//		points.add(new Point2D.Double(125.0, 35.0));
//		points.add(new Point2D.Double(180.0, 35.0));
//		points.add(new Point2D.Double(180.0, 75.0));
//		points.add(new Point2D.Double(155.0, 75.0));
//		points.add(new Point2D.Double(155.0, 100.0));
//		points.add(new Point2D.Double(195.0, 100.0));
//		points.add(new Point2D.Double(195.0, 145.0));
//		points.add(new Point2D.Double(75.0, 145.0));
//		points.add(new Point2D.Double(75.0, 120.0));
//		points.add(new Point2D.Double(40.0, 120.0));
//		points.add(new Point2D.Double(40.0, 95.0));
//		points.add(new Point2D.Double(5.0, 95.0));
		
//		points.add(new Point2D.Double(40.0, 120.0));
//		points.add(new Point2D.Double(40.0, 20.0));
//		points.add(new Point2D.Double(160.0, 20.0));
//		points.add(new Point2D.Double(160.0, 120.0));
		
		this.genClosed.setPoints(points);
		//		this.genClosed.setComprimento(80);
		//		this.genClosed.setLargura(60);
		//		this.genClosed.createGeometricalElements();

		faceXY.addFeature(this.genClosed);

		this.boss = new CircularBoss();
		this.boss.setCentre(new Point3d(30,30,0));
		this.boss.setDiametro1(10);
		this.boss.setDiametro2(10);
		this.boss.setAltura(10);
		this.boss.setPosicao(20, 35, 0);
		this.boss.setNome("lucas");
		this.boss.createGeometricalElements();

//		this.boss1 = new RectangularBoss(20, 10, 10, 5);
//		this.boss1.setPosicao(40, 30, 0);
		
		this.boss1 = new RectangularBoss(30, 25, 10, 0);
		this.boss1.setPosicao(80, 35, 0);

		this.boss2 = new RectangularBoss(20, 20, 10, 7);
		this.boss2.setPosicao(50, 45, 0);

		ArrayList<Point2D> vertices = new ArrayList<Point2D>();
		vertices.add(new Point2D.Double(40,15));
		vertices.add(new Point2D.Double(45,30));
		vertices.add(new Point2D.Double(60,35));
		vertices.add(new Point2D.Double(65,25));

		this.boss3 = new GeneralProfileBoss(1,vertices);
		this.boss3.setAltura(10);


		this.itsBoss = genClosed.getItsBoss();

		
		//		this.ferramenta = this.ws.getFerramenta();
		//		this.cavidade = (Cavidade) this.ws.getFeature();

		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
//				this.itsBoss.add(this.boss);
				this.itsBoss.add(this.boss1);
//				this.itsBoss.add(this.boss2);
//				this.itsBoss.add(this.boss3);
		this.genClosed.setItsBoss(this.itsBoss);
//		this.faceXY.addFeature(this.boss);
//				Generate3Dview Janela3D = new Generate3Dview(this.projeto);
//				Janela3D.setVisible(true);
		
		this.ferramenta = new Ferramenta(10, 70);
		
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setAp(3);
		cond.setAe(7.5);
		cond.setN(2000);
		cond.setVc(100);
		
		BottomAndSideRoughMilling operation = new BottomAndSideRoughMilling("operation", 5);
		
		this.ws = new Workingstep(genClosed, faceXY, ferramenta, cond, operation);
		//System.err.println("done!!!");

	}
	
	@Test
	public void testMovimentacao()
	{
		GeneralClosedPocket pocket = (GeneralClosedPocket)this.ws.getFeature();
		MovimentacaoGeneralClosedPocket mov = new MovimentacaoGeneralClosedPocket(this.ws); 
		
		double ae = this.ws.getCondicoesUsinagem().getAe();
		double radius = this.ws.getFerramenta().getDiametroFerramenta()/2;
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(pocket.getVertexPoints(), pocket.getPosicaoZ(),radius);
		System.out.println("Vertices " +  addPocket.getVertex().size());
		int j=0;
		for (Point3d v:addPocket.getVertex())
		{
			System.out.println(j + "\t" + v);
			j++;
		}
		
		System.out.println("Elementos " + addPocket.getElements().size());
		int i = 0;
		for(LimitedElement e:addPocket.getElements())
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI + " radius " + arc.getRadius()); 
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
			}
			
			i++;
		}
		
		ArrayList<LimitedElement> elements = GeometricOperations.acabamentoPath(addPocket, ws.getFerramenta().getDiametroFerramenta()/2);
		ArrayList<LimitedElement> elementsFirstDesbaste = GeometricOperations.firstPathDesbaste(pocket, ws.getFerramenta().getDiametroFerramenta()/4);
		i = 0;
		System.out.println("Elements for acabamento: ");
		for (LimitedElement e:elements)
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI  + " radius " + arc.getRadius()); 
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
			}			
			i++;			
		}

		System.out.println("Elements for desbaste: ");
		for (LimitedElement e:elementsFirstDesbaste)
		{
			if(e.isLimitedArc())
			{
				LimitedArc arc = (LimitedArc)e;
				System.out.println(i + "\t Arc from " + arc.getInitialPoint() + " to " + arc.getFinalPoint() + " center " + arc.getCenter() + " angle " + arc.getDeltaAngle()*180/Math.PI  + " radius " + arc.getRadius()); 
			}
			else if (e.isLimitedLine())
			{
				LimitedLine line = (LimitedLine)e;
				System.out.println(i + "\t Line from " + line.getInitialPoint() + " to " + line.getFinalPoint());
			}			
			i++;			
		}

	
	}

	
	public void createBossTest(){


		this.genClosed=new GeneralClosedPocket();
		this.genClosed.setNome("Name");
		this.genClosed.setPosicao(79, 22, 0);
		this.genClosed.setProfundidade(10);
		this.genClosed.setRadius(10);
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
		points.add(new Point2D.Double(10,40));
		points.add(new Point2D.Double(10,80));
		points.add(new Point2D.Double(120,80));
		points.add(new Point2D.Double(120,10));
		points.add(new Point2D.Double(50,10));
		points.add(new Point2D.Double(50,40));
				
//		points.add(new Point2D.Double(79,22));
//		points.add(new Point2D.Double(47,60));
//		points.add(new Point2D.Double(81,104));
//		points.add(new Point2D.Double(131,107));
//		points.add(new Point2D.Double(150,68));
//		points.add(new Point2D.Double(129,32));
//		points.add(new Point2D.Double(161,8));
//		points.add(new Point2D.Double(194,55));
//		points.add(new Point2D.Double(167,134));
//		points.add(new Point2D.Double(60,132));
//		points.add(new Point2D.Double(19,61));
//		points.add(new Point2D.Double(40,12));
		
//		points.add(new Point2D.Double(5.0, 35.0));
//		points.add(new Point2D.Double(20.0, 35.0));
//		points.add(new Point2D.Double(20.0, 5.0));
//		points.add(new Point2D.Double(60.0, 5.0));
//		points.add(new Point2D.Double(60.0, 35.0));
//		points.add(new Point2D.Double(95.0, 35.0));
//		points.add(new Point2D.Double(95.0, 65.0));
//		points.add(new Point2D.Double(125.0, 65.0));
//		points.add(new Point2D.Double(125.0, 35.0));
//		points.add(new Point2D.Double(180.0, 35.0));
//		points.add(new Point2D.Double(180.0, 75.0));
//		points.add(new Point2D.Double(155.0, 75.0));
//		points.add(new Point2D.Double(155.0, 100.0));
//		points.add(new Point2D.Double(195.0, 100.0));
//		points.add(new Point2D.Double(195.0, 145.0));
//		points.add(new Point2D.Double(75.0, 145.0));
//		points.add(new Point2D.Double(75.0, 120.0));
//		points.add(new Point2D.Double(40.0, 120.0));
//		points.add(new Point2D.Double(40.0, 95.0));
//		points.add(new Point2D.Double(5.0, 95.0));
		
//		points.add(new Point2D.Double(40.0, 120.0));
//		points.add(new Point2D.Double(40.0, 20.0));
//		points.add(new Point2D.Double(160.0, 20.0));
//		points.add(new Point2D.Double(160.0, 120.0));
		
		this.genClosed.setPoints(points);
		//		this.genClosed.setComprimento(80);
		//		this.genClosed.setLargura(60);
		//		this.genClosed.createGeometricalElements();

		faceXY.addFeature(this.genClosed);

		this.boss=new CircularBoss();
		this.boss.setCentre(new Point3d(30,30,0));
		this.boss.setDiametro1(10);
		this.boss.setDiametro2(10);
		this.boss.setAltura(10);
		this.boss.setPosicao(20, 35, 0);
		this.boss.setNome("lucas");
		this.boss.createGeometricalElements();

//		this.boss1 = new RectangularBoss(20, 10, 10, 5);
//		this.boss1.setPosicao(40, 30, 0);
		
		this.boss1 = new RectangularBoss(30, 25, 10, 0);
		this.boss1.setPosicao(80, 35, 0);

		this.boss2 = new RectangularBoss(20, 20, 10, 7);
		this.boss2.setPosicao(50, 45, 0);

		ArrayList<Point2D> vertices = new ArrayList<Point2D>();
		vertices.add(new Point2D.Double(40,15));
		vertices.add(new Point2D.Double(45,30));
		vertices.add(new Point2D.Double(60,35));
		vertices.add(new Point2D.Double(65,25));

		this.boss3 = new GeneralProfileBoss(1,vertices);
		this.boss3.setAltura(10);


		this.itsBoss = genClosed.getItsBoss();

		this.ws = ws;
		//		this.ferramenta = this.ws.getFerramenta();
		//		this.cavidade = (Cavidade) this.ws.getFeature();

		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
//				this.itsBoss.add(this.boss);
				this.itsBoss.add(this.boss1);
//				this.itsBoss.add(this.boss2);
//				this.itsBoss.add(this.boss3);
		genClosed.setItsBoss(this.itsBoss);
//		this.faceXY.addFeature(this.boss);
//				Generate3Dview Janela3D = new Generate3Dview(this.projeto);
//				Janela3D.setVisible(true);



		//			double largura=this.genClosed.getLargura(),
		//					comprimento=this.genClosed.getComprimento(),
		double	raio=this.genClosed.getRadius(),
				raioAtual,
				z=-10,
				largura,
				comprimento,
				ae;//this.ferramenta.getDiametroFerramenta();
		final double diametroFerramenta;

//		GeneralPath general = new GeneralPath();
		general = new GeneralPath();
		ArrayList<Point2D> vertex1 = genClosed.getPoints();
		ArrayList<Point2D> vertex;
//		System.err.println("vertex1 : " + vertex1);

		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertex1), raio);
		
		general.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		for(int r=0;r<vertex.size();r++){
			general.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
			
		}
		general.closePath();

		//			RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(this.genClosed.getPosicaoX(), this.genClosed.getPosicaoY(), comprimento, largura, 2*raio, 2*raio);

		double malhaMenoresDistancias[][] = new double[numeroDePontosDaMalha-1][numeroDePontosDaMalha-1];

		ArrayList<Shape> bossArray;
		//		ArrayList<Ellipse2D> listaElipses;
		ArrayList<Point3d> pontos2;
		Point2D borda[];


		//CRIAR MALHA DE PONTOS PARA COMPARA��O
		double xMenor=1000,xMaior=0,yMenor=1000,yMaior=0;
		ArrayList<Point2D> vertexPoint;

		vertexPoint = genClosed.getPoints();
		for(int i=0;i<vertexPoint.size();i++){
			if(xMenor>vertexPoint.get(i).getX())
				xMenor=vertexPoint.get(i).getX();
			if(xMaior<vertexPoint.get(i).getX())
				xMaior=vertexPoint.get(i).getX();
			if(yMenor>vertexPoint.get(i).getY())
				yMenor=vertexPoint.get(i).getY();
			if(yMaior<vertexPoint.get(i).getY())
				yMaior=vertexPoint.get(i).getY();			
		}
		largura = yMaior-yMenor;
		comprimento = xMaior-xMenor;
		
		distMax = new Point2d(xMenor , yMenor).distance(new Point2d (xMaior , yMaior));


		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				malha[i][k][0] = xMenor+comprimento*(i+1)/numeroDePontosDaMalha;//x
				malha[i][k][1] = yMenor+largura*(k+1)/numeroDePontosDaMalha;//y
			}
		}

		//REPRODUZIR BOSS
		bossArray = new ArrayList<Shape>();
		pontosPeriferia = new ArrayList<Point3d>();
		Boss bossTmp;
		//		listaElipses = new ArrayList<Ellipse2D>();	
		for(int i=0;i<this.itsBoss.size();i++){
			bossTmp=this.itsBoss.get(i);
			if(bossTmp.getClass()==CircularBoss.class){
				CircularBoss boss = (CircularBoss) bossTmp;
				raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.genClosed.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
				bossArray.add(new Ellipse2D.Double(boss.getPosicaoX()-raioAtual, boss.getPosicaoY()-raioAtual, raioAtual*2, 2*raioAtual));
				borda = Cavidade.determinarPontosEmCircunferencia(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), 0, 2*Math.PI, raioAtual, (int) Math.round(Math.PI*2*raioAtual));
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==RectangularBoss.class){
				RectangularBoss boss = (RectangularBoss) bossTmp;
				bossArray.add(new RoundRectangle2D.Double(bossTmp.getPosicaoX(), bossTmp.getPosicaoY(), boss.getL1(), boss.getL2(), boss.getRadius()*2, boss.getRadius()*2));
				borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(boss.getPosicaoX(),boss.getPosicaoY(),z), boss.getL1(), boss.getL2(), boss.getRadius());
				for(int k=0;k<borda.length;k++){
					pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
				}
			}
			else if(bossTmp.getClass()==GeneralProfileBoss.class){


				GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				ArrayList<Point2D> vertexx = boss.getVertexPoints();
				vertexx = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(vertexx), boss.getRadius());					
			
				ArrayList<Point3d> periferia = getPontosPeriferiaGeneral(vertexx, z, boss.getRadius());
				for(int q=0;q<periferia.size();q++){
//					pontosPeriferia.add(getPontosPeriferiaGeneral(vertexx, z, boss.getRadius()).get(q)); // ==========CUIDADO, PODE FAZER DEMORAR MAIS DO NECESSARIO ==========
					pontosPeriferia.add(periferia.get(q)); 
				}
				GeneralPath path = new GeneralPath();
				path.moveTo(vertexx.get(0).getX(), vertexx.get(0).getY());
				for(int r=0;r<vertexx.size();r++){
					path.lineTo(vertexx.get(r).getX(), vertexx.get(r).getY());
				}
				path.closePath();
				bossArray.add(path);
				//					//GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
				//					//ArrayList<Point2D> vertex = boss.getVertexPoints();
				//					testeDaFuncao(vertex,z);
				//					GeneralPath path = new GeneralPath();
				//					path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
				//					for(int r=0;r<vertex.size();r++){
				//						path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
				//					}
				//					path.closePath();
				//					bossArray.add(path);
				//					double distancia, maiorX, maiorY;
				//					int q;
				//					for(int j=0;j<vertex.size();j++){
				//						if(j==vertex.size()-1)
				//							q=0;
				//						else
				//							q=j+1;
				//						
				//						if(vertex.get(j).getX()>vertex.get(q).getX())
				//							maiorX = vertex.get(j).getX();
				//						else
				//							maiorX = vertex.get(q).getX();
				//						
				//						if(vertex.get(j).getY()>vertex.get(q).getY())
				//							maiorY = vertex.get(j).getY();
				//						else
				//							maiorY = vertex.get(q).getY();
				//						
				//						
				//						if(vertex.get(j).getX()==vertex.get(q).getX()){
				//							distancia = vertex.get(j).getY();
				//							for(int h=0;h<1000;h++){
				//								pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
				//								if(maiorY == vertex.get(j).getY())
				//									distancia-=1;
				//								else
				//									distancia+=1;
				//								if(distancia==vertex.get(q).getY()){
				//									h=1000;
				//									pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
				//								}
				//							}
				//						}
				//						else if(vertex.get(j).getY()==vertex.get(q).getY()){
				//							distancia = vertex.get(j).getX();
				//							for(int h=0;h<1000;h++){
				//								pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
				//								if(maiorX == vertex.get(j).getX())
				//									distancia-=1;
				//								else
				//									distancia+=1;
				//								if(distancia==vertex.get(q).getX()){
				//									h=1000;
				//									pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
				//								}
				//							}
				//						}
				//						else{
				//							double a,b;
				//							a= (vertex.get(q).getY()-vertex.get(j).getY())/(vertex.get(q).getX()-vertex.get(j).getX());
				//							b= vertex.get(j).getY()-a*vertex.get(j).getX();
				//														
				//							if(Math.abs(vertex.get(j).getX()-vertex.get(q).getX())>Math.abs(vertex.get(j).getY()-vertex.get(q).getY())){
				//								distancia = vertex.get(j).getX();
				//								for(int h=0;h<1000;h++){
				//									pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
				//									if(maiorX == vertex.get(j).getX()){
				//										distancia-=1;
				//										if(distancia<=vertex.get(q).getX()){
				//											h=1000;
				//											pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
				//										}
				//									}
				//									else{
				//										distancia+=1;
				//										if(distancia>=vertex.get(q).getX()){
				//											h=1000;
				//											pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
				//										}
				//									}
				//								}	
				//							}
				//							else{
				//								distancia = vertex.get(j).getY();
				//								for(int h=0;h<1000;h++){
				//									pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
				//									if(maiorY == vertex.get(j).getY()){
				//										distancia-=1;
				//										if(distancia<=vertex.get(q).getY()){
				//											h=1000;
				//											pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
				//										}
				//									}
				//									else{
				//										distancia+=1;
				//										if(distancia>=vertex.get(q).getY()){
				//											h=1000;
				//											pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
				//										}
				//									}
				//								}	
				//							}
				//						}
				//					}		
			}
		}

		//			borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(this.genClosed.getPosicaoX(),this.genClosed.getPosicaoY(),z), this.genClosed.getComprimento(), this.genClosed.getLargura(), this.genClosed.getRaio());
		//			for(int k=0;k<borda.length;k++){
		//				pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
		//			}
		ArrayList<Point3d> periferia = getPontosPeriferiaGeneral(vertex, z, raio);
//		for(int i=0;i<getPontosPeriferiaGeneral(vertex, z, raio).size();i++){
			for(int i=0;i<periferia.size();i++){
//			pontosPeriferia.add(getPontosPeriferiaGeneral(vertex, z, raio).get(i)); // ================== CUIDADO, Isto pode fazer o programa mais devagar q o necessario
			pontosPeriferia.add(periferia.get(i));
		}


		int b=0,c=0;
		//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS N�O USINADOS
		pontosPossiveis = new ArrayList<Point3d>();
		coordenadas = new ArrayList<Point2d>();
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						coordenadas.add(new Point2d(i,k));
						//							System.out.println(k);
					}
					else{
						//	malhaMenoresDistancias[i][k] = 0;
					}
					b=0;
				}
				//	malhaMenoresDistancias[i][k] = 0;
			}
		}

		System.out.println("PontosPossiveis : " + pontosPossiveis.size());
		//			if(pontosPossiveis.size()<1){
		//				break;
		//			}
		double distanciaTmp, distanciaTmpX, distanciaTmpY;
		int numeroDeCortes,u=0;

		menorDistancia = new ArrayList<Double>();
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
//				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					if(pontosPeriferia.get(k).distance(pontosPossiveis.get(i))<distanciaTmp){
//					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
					distanciaTmp=pontosPeriferia.get(k).distance(pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);
			//				System.out.println(menorDistancia.get(i));
//			System.out.println(coordenadas.get(i).getX());
//			System.out.println(coordenadas.get(i).getY());
//			System.out.println(distanciaTmp);
		}

		maximos = new ArrayList<Point3d>();

		/*TESTE*/
		double diametroTmp=1000;
		int contador=0, numeroDeDiametrosAdicionados=0;

		for(int i=1;i<malhaMenoresDistancias.length-1;i++){
			for(int k=1;k<malhaMenoresDistancias.length-1;k++){
				contador = 0;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k+1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k-1])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k])
					contador++;
				if(malhaMenoresDistancias[i][k]<=2)
					contador=0;

				if(contador>=6){
					if(raioMenor>malhaMenoresDistancias[i][k])
						raioMenor = malhaMenoresDistancias[i][k];
					numeroDeDiametrosAdicionados++;
					raioMedia = raioMedia + malhaMenoresDistancias[i][k];
					maximos.add(new Point3d(malha[i][k][0],malha[i][k][1],malhaMenoresDistancias[i][k]));
					if(diametroTmp>malhaMenoresDistancias[i][k] && malhaMenoresDistancias[i][k]!=0){
						diametroTmp=malhaMenoresDistancias[i][k];
					}
				}
			}
		}
		
		raioMedia = raioMedia/numeroDeDiametrosAdicionados;
		
		for(int i=0;i<menorDistancia.size();i++){
			if(maiorMenorDistancia<menorDistancia.get(i)){
				maiorMenorDistancia=menorDistancia.get(i);
			}
		}
		System.out.println("Raio Media :  " + raioMedia);
		System.out.println("Raio Menor :  " + raioMenor);

		diametroFerramenta = 2*raioMedia;
		ae = 0.75*diametroFerramenta;
		
		double diferenca;
		diferenca = 1 + (2*maiorMenorDistancia-diametroFerramenta)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);
		
		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}
					
		System.out.println("Maior Menor Distancia : " + maiorMenorDistancia);
		System.out.println("Numero De cortes : " + numeroDeCortes);
		
		
		double temp1=comprimento/numeroDePontosDaMalha,
				temp2=largura/numeroDePontosDaMalha;
		double variacao = (temp1+temp2)/2;

		pontos = new ArrayList<ArrayList<Point3d>>();
		double raioTmp, distancia;
		
		
		for(int i=0;i<numeroDeCortes;i++)
		{
			pontos2 = new ArrayList<Point3d>();
			raioTmp = raioMedia + i*ae*raioMedia;
			diferenca = raioTmp - maiorMenorDistancia;
						
			for(int k=0;k<menorDistancia.size();k++)
			{		
				distancia = menorDistancia.get(k);
				
				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						testeTempo.add(pontosPossiveis.get(k));
						
						pontos2.add(pontosPossiveis.get(k));
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						pontos2.add(pontosPossiveis.get(k));
						
							testeTempo.add(pontosPossiveis.get(k));
						
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							pontos2.add(pontosPossiveis.get(k));
							
								testeTempo.add(pontosPossiveis.get(k));
							
						}
					}
				}
			}
			System.out.println("Tamanho dos pontos :  "+pontos2.size());
			pontos.add(pontos2);
		}
		
		
		
		pontosMenores = new ArrayList<Point3d>();
		ArrayList<Point3d> test = new ArrayList<Point3d>();

		pontosPossiveis = new ArrayList<Point3d>();
		coordenadas = new ArrayList<Point2d>();
		for(int i=0;i<malha.length;i++){
			for(int k=0;k<malha[i].length;k++){
				if(general.contains(malha[i][k][0], malha[i][k][1])){
					for(int g=0;g<bossArray.size();g++){
						if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
							b++;
						}
					}
					if(b==bossArray.size()){
						pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						coordenadas.add(new Point2d(i,k));
						//							System.out.println(k);
					}
					else{
						//	malhaMenoresDistancias[i][k] = 0;
					}
					b=0;
				}
				//	malhaMenoresDistancias[i][k] = 0;
			}
		}


		menorDistancia = new ArrayList<Double>();
		for(int i=0;i<pontosPossiveis.size();i++){
			distanciaTmp=100;
			for(int k=0;k<pontosPeriferia.size();k++){
//				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					if(pontosPeriferia.get(k).distance(pontosPossiveis.get(i))<distanciaTmp){
//					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
					distanciaTmp=pontosPeriferia.get(k).distance(pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);
			//				System.out.println(menorDistancia.get(i));
		}
		
		final double diametro = 2*raioMenor;
		ae = 0.75*diametro;
		
		diferenca = 1 + (2*maiorMenorDistancia-diametro)/ae; // 1 � por causa do diametro
		numeroDeCortes = (int) Math.round(diferenca);
		
		if(diferenca>numeroDeCortes){
			numeroDeCortes += 1;
		}		
		
		
		System.out.println("Numero De cortes 2 : " + numeroDeCortes);
		for(int i=0;i<numeroDeCortes;i++){
			pontos2 = new ArrayList<Point3d>();
			raioTmp = raioMenor + i*ae/2;
			diferenca = raioTmp - maiorMenorDistancia;
			
			System.out.println("RaioTmp : " + raioTmp);
			System.out.println("Diferen�a : " + (raioTmp - maiorMenorDistancia));
			
			for(int k=0;k<menorDistancia.size();k++){

				distancia = menorDistancia.get(k);
				
				if(i == 0)
				{
					if(distancia + variacao >= raioTmp && distancia <= raioTmp)
					{
						pontosMenores.add(pontosPossiveis.get(k));
					}
				}
				else
				{
					if(distancia + variacao/2 >= raioTmp && distancia - variacao/2 <= raioTmp)
					{
						pontosMenores.add(pontosPossiveis.get(k));
					}
					else
					{
						if(distancia + variacao/2 >= raioTmp - diferenca && distancia - variacao/2 <= raioTmp - diferenca)
						{
							pontosMenores.add(pontosPossiveis.get(k));
						}
					}
				}
			}
			System.out.println("Pontos menores:  "+pontosMenores.size());

		}


		
		//ACABAMENTO




		class painelTest extends JPanel{

			GeneralPath p = new GeneralPath();
			GeneralPath r = new GeneralPath();
			GeneralPath uniaoDosPontos = new GeneralPath();
			ArrayList<GeneralPath> arrayPath = new ArrayList<GeneralPath>();
			ArrayList<Ellipse2D> m = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> e = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> f = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> w = new ArrayList<Ellipse2D>();
			double largura=150, comprimento=200;
			Point3d[][] matriz = new Point3d[98][98];

			

			painelTest(){


				double distTmp, distHold , somaDasDistancias = 0;
				int ee = 0 , posicaoTestada = 0;
				ArrayList<Point3d> listaPontosProximos = new ArrayList<Point3d>();	
				double maiorX = 0, maiorY = 0 , menorX = distMax, menorY = distMax , area = 0;
				int largura2 , comprimento2;
				
				
				uniaoDosPontos.moveTo(2*testeTempo.get(0).getX(), 2*testeTempo.get(0).getY());
				for(int qq = 1 ; qq < testeTempo.size() ; qq++)
				{
					if(maiorX < testeTempo.get(qq).getX())
						maiorX = testeTempo.get(qq).getX();
					if(maiorY < testeTempo.get(qq).getY())
						maiorY = testeTempo.get(qq).getY();
					if(menorX > testeTempo.get(qq).getX())
						menorX = testeTempo.get(qq).getX();
					if(menorY > testeTempo.get(qq).getY())
						menorY = testeTempo.get(qq).getY();
					
					listaPontosProximos.add(testeTempo.get(posicaoTestada));
					distTmp = distMax;
					for(int ww = 0 ; ww < testeTempo.size() ; ww++)
					{
						if(!listaPontosProximos.contains(testeTempo.get(ww)))
						{
							distHold = testeTempo.get(posicaoTestada).distance(testeTempo.get(ww));
							if(distHold < distTmp)
							{
								distTmp = distHold;
								ee = ww;
							}	
						}	
					}
					posicaoTestada = ee;
					if(distTmp < 10)
					{
						uniaoDosPontos.lineTo(2*testeTempo.get(posicaoTestada).getX(), 2*testeTempo.get(posicaoTestada).getY());
						somaDasDistancias = somaDasDistancias + distTmp;
					}
					else
					{
						uniaoDosPontos.closePath();
						arrayPath.add(uniaoDosPontos);
						uniaoDosPontos = new GeneralPath();
						uniaoDosPontos.moveTo(2*testeTempo.get(posicaoTestada).getX(), 2*testeTempo.get(posicaoTestada).getY());
					}
				}
				uniaoDosPontos.closePath();
				arrayPath.add(uniaoDosPontos);
//				area = somaDasDistancias*raio + diametro;
//							
//				comprimento2 = (int) (maiorX - menorX);
//				largura2 = (int) (maiorY - menorY);
//				
//				for(int i = 0 ; i < largura2 ; i++)
//				{
//					for(int j = 0 ; j < comprimento2 ; j++)
//					{
//						Rectangle2D quadradinhos = new Rectangle2D.Double(menorX + j , menorY + i , 1 , 1);
//						for(int k = 0 ; k < arrayPath.size() ; k++)
//						{
//							if(arrayPath.get(k).contains(quadradinhos))
//							{
//								area += 
//							}
//						}
//					}
//				}
				
				r.moveTo(0,0);
				r.lineTo(2*comprimento, 0);
				r.lineTo(2*comprimento, 2*largura);
				r.lineTo(0, 2*largura);
				r.closePath();

				int escala = 3;
				int raioBolinha = 2;
				
				for(int i=0;i<pontos.size();i++){
					for(int k=0;k<pontos.get(i).size();k++){
						if(pontos.get(i).size()<1){
							break;
						}
//						f.add(new Ellipse2D.Double(2*pontos.get(i).get(k).getX(),2*pontos.get(i).get(k).getY(),5,5));
						f.add(new Ellipse2D.Double(escala*(pontos.get(i).get(k).getX()-raioBolinha-diametroFerramenta/2),escala*(pontos.get(i).get(k).getY()-raioBolinha-diametroFerramenta/2),escala*diametroFerramenta,escala*diametroFerramenta));
					}
				}
//								for(int i=0;i<malha.length;i++){
//									for(int k=0;k<malha[i].length;k++){
//										e.add(new Ellipse2D.Double(5*malha[i][k][0],5*malha[i][k][1],5,5));
//									}
//								}
//								for(int i=0;i<pontosPossiveis.size();i++){
//									if(pontosPossiveis.size()<1){
//										break;
//									}
//									e.add(new Ellipse2D.Double(5*pontosPossiveis.get(i).getX(),5*pontosPossiveis.get(i).getY(),5,5));					
//								}
								for(int i=0;i<maximos.size();i++){
									if(maximos.size()<1){
										break;
									}
									m.add(new Ellipse2D.Double(escala*(maximos.get(i).getX()-raioBolinha),escala*(maximos.get(i).getY()-raioBolinha),2*raioBolinha,2*raioBolinha));					
								}
				for(int i=0;i<pontosMenores.size();i++){
					if(pontosMenores.size()<1){
						break;
					}
					e.add(new Ellipse2D.Double(escala*(pontosMenores.get(i).getX()-raioBolinha-diametro/2),escala*(pontosMenores.get(i).getY()-raioBolinha-diametro/2),escala*diametro,escala*diametro));					
				}
				
				for(int i=0;i<pontosPeriferia.size();i++){
					if(pontosPeriferia.size()<1){
						break;
					}
					w.add(new Ellipse2D.Double(escala*(pontosPeriferia.get(i).getX()-raioBolinha),escala*(pontosPeriferia.get(i).getY()-raioBolinha),2*raioBolinha,2*raioBolinha));
				}

//				for(int i = 0; i < pontos.size(); i++)
//				{
//					for(int k = 0; k < pontos.get(i).size(); k++)
//					{
//						System.out.println(pontos.get(i).get(k).getZ());
//					}
//				}
//				for(int i = 0; i < pontosMenores.size(); i++)
//				{
//					System.out.println(pontosMenores.get(i).getZ());
//				}
				
				
				
			}


			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);


				Graphics2D g2d = (Graphics2D)g;
				g2d.translate(25, 475);
				g2d.scale(1, -1);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.black);
				//g2d.draw(new Ellipse2D.Double());
				g2d.setColor(new Color(251, 100, 100));
				g2d.draw(p);
//				g2d.setColor(new Color(100,100,251));
//				g2d.draw(r);
//				g2d.setColor(new Color(100,251,100));
//				for(int i  = 0; i < arrayPath.size() ; i++)
//				{
//					g2d.draw(arrayPath.get(i));		
//				}
				
				g2d.setColor(new Color(100, 251, 100));
				for(int i=0;i<e.size();i++){
					g2d.fill(e.get(i));
				}
				g2d.setColor(new Color(100, 100, 251));
				for(int i=0;i<f.size();i++){
					g2d.fill(f.get(i));
				}
				g2d.setColor(new Color(251, 100, 100));
				for(int i=0;i<w.size();i++){
					g2d.fill(w.get(i));
				}
				g2d.setColor(new Color(255, 165, 0));
				for(int i=0;i<m.size();i++){
					g2d.fill(m.get(i));
				}
//				g2d.setColor(new Color(0,0,0));
//				g2d.draw(general);
			}
		}
		JFrame frame = new JFrame("Poligono");
		painelTest painel = new painelTest();
		frame.setSize(510, 535);
		frame.getContentPane().add(painel);
		frame.setVisible(true);
		painel.repaint();
		for(;;);


	}

	private static ArrayList<Point3d> getPontosPeriferiaGeneral(ArrayList<Point2D> vertex, double z, double raio){

		//GeneralProfileBoss boss = (GeneralProfileBoss) bossTmp;
		//ArrayList<Point2D> vertex = boss.getVertexPoints();
		ArrayList<Point3d> pontosPeriferia = new ArrayList<Point3d>();

	
		double distancia, maiorX, maiorY, menorX, menorY, divisoes=1;
		int q;
		for(int j=0;j<vertex.size();j++){
			if(j==vertex.size()-1)
				q=0;
			else
				q=j+1;

			if(vertex.get(j).getX()>vertex.get(q).getX()){
				maiorX = vertex.get(j).getX();
				menorX = vertex.get(q).getX();
			}
			else{
				maiorX = vertex.get(q).getX();
				menorX = vertex.get(j).getX();
			}
			if(vertex.get(j).getY()>vertex.get(q).getY()){
				maiorY = vertex.get(j).getY();
				menorY = vertex.get(q).getY();
			}
			else{
				maiorY = vertex.get(q).getY();
				menorY = vertex.get(j).getY();
			}

			if(vertex.get(j).getX()==vertex.get(q).getX()){
				distancia = vertex.get(j).getY();
				for(int h=0;h<1000;h++){
					pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
					if(maiorY == vertex.get(j).getY()){
						distancia-=divisoes;
						if(distancia<=vertex.get(q).getY()){
							h=1000;
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
						}
					}
					else{
						distancia+=divisoes;
						if(distancia>=vertex.get(q).getY()){
							h=1000;
							pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
						}
					}
				}
			}
			else if(vertex.get(j).getY()==vertex.get(q).getY()){
				distancia = vertex.get(j).getX();
				for(int h=0;h<1000;h++){
					pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
					if(maiorX == vertex.get(j).getX()){
						distancia-=1;
						if(distancia<=vertex.get(q).getX()){
							h=1000;
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
						}
					}
					else{
						distancia+=1;
						if(distancia>=vertex.get(q).getX()){
							h=1000;
							pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
						}
					}
				}
			}
			else{
				double a,b;
				a= (vertex.get(q).getY()-vertex.get(j).getY())/(vertex.get(q).getX()-vertex.get(j).getX());
				b= vertex.get(j).getY()-a*vertex.get(j).getX();

				if(Math.abs(vertex.get(j).getX()-vertex.get(q).getX())>Math.abs(vertex.get(j).getY()-vertex.get(q).getY())){
					distancia = vertex.get(j).getX();
					for(int h=0;h<1000;h++){
						pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
						if(maiorX == vertex.get(j).getX()){
							distancia-=1;
							if(distancia<=vertex.get(q).getX()){
								h=1000;
								pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
							}
						}
						else{
							distancia+=1;
							if(distancia>=vertex.get(q).getX()){
								h=1000;
								pontosPeriferia.add(new Point3d(distancia,a*distancia+b,z));
							}
						}
					}	
				}
				else{
					distancia = vertex.get(j).getY();
					for(int h=0;h<1000;h++){
						pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
						if(maiorY == vertex.get(j).getY()){
							distancia-=1;
							if(distancia<=vertex.get(q).getY()){
								h=1000;
								pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
							}
						}
						else{
							distancia+=1;
							if(distancia>=vertex.get(q).getY()){
								h=1000;
								pontosPeriferia.add(new Point3d((distancia-b)/a,distancia,z));
							}
						}
					}	
				}		
			}
		}
		return pontosPeriferia;
	}



}
