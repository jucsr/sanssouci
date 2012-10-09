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
import br.UFSC.GRIMA.cad.CreateGeneralProfileBoss;
import br.UFSC.GRIMA.capp.Workingstep;
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
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
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
	}

	@Test
	public void createBossTest(){


		this.genClosed=new GeneralClosedPocket();
		this.genClosed.setNome("Lucas");
		this.genClosed.setPosicao(10, 10, 0);
		this.genClosed.setProfundidade(10);
		this.genClosed.setRadius(5);
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D.Double(20,20));
		points.add(new Point2D.Double(60,20));
		points.add(new Point2D.Double(70,50));
		points.add(new Point2D.Double(30,50));
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

		this.boss1 = new RectangularBoss(20, 10, 10, 5);
		this.boss1.setPosicao(40, 30, 0);

		this.boss2 = new RectangularBoss(20, 20, 10, 7);
		this.boss2.setPosicao(50, 45, 0);

		ArrayList<Point2D> vertices = new ArrayList<Point2D>();
		vertices.add(new Point2D.Double(20,15));
		vertices.add(new Point2D.Double(25,30));
		vertices.add(new Point2D.Double(40,35));
		vertices.add(new Point2D.Double(45,25));

		this.boss3 = new GeneralProfileBoss(1,vertices);
		this.boss3.setAltura(10);


		this.itsBoss = genClosed.getItsBoss();

		this.ws = ws;
		//		this.ferramenta = this.ws.getFerramenta();
		//		this.cavidade = (Cavidade) this.ws.getFeature();

		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		//		this.itsBoss.add(this.boss);
		//		this.itsBoss.add(this.boss1);
		//		this.itsBoss.add(this.boss2);
		//		this.itsBoss.add(this.boss3);
		genClosed.setItsBoss(this.itsBoss);
		this.faceXY.addFeature(this.boss);
		//		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		//		Janela3D.setVisible(true);



		//			double largura=this.genClosed.getLargura(),
		//					comprimento=this.genClosed.getComprimento(),
		double	raio=this.genClosed.getRadius(),
				raioAtual,
				z=-10,
				largura,
				comprimento;//this.ferramenta.getDiametroFerramenta();
		final double diametroFerramenta;

		GeneralPath general = new GeneralPath();
		ArrayList<Point2D> vertex1 = genClosed.getPoints();
		ArrayList<Point2D> vertex;
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

		System.out.println(largura+" "+comprimento);

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
				for(int q=0;q<getPontosPeriferiaGeneral(vertexx, z, boss.getRadius()).size();q++){
					pontosPeriferia.add(getPontosPeriferiaGeneral(vertexx, z, boss.getRadius()).get(q));
				}
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

		for(int i=0;i<getPontosPeriferiaGeneral(vertex, z, raio).size();i++){
			pontosPeriferia.add(getPontosPeriferiaGeneral(vertex, z, raio).get(i));
		}

		System.out.println("Bosses : " + bossArray.size());

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
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);
			//				System.out.println(menorDistancia.get(i));
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
						raioMedia+=malhaMenoresDistancias[i][k];
						maximos.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
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
		numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametroFerramenta));
		double temp1=comprimento/numeroDePontosDaMalha,
				temp2=largura/numeroDePontosDaMalha;
		double variacao = (temp1+temp2)/2;

		System.out.println("Numero de Cortes : " + numeroDeCortes);
		pontos = new ArrayList<ArrayList<Point3d>>();
		for(int i=0;i<numeroDeCortes+10;i++){
			pontos2 = new ArrayList<Point3d>();
			for(int k=0;k<menorDistancia.size();k++){
				if(i==0){
					if(menorDistancia.get(k)<=diametroFerramenta/2+variacao && menorDistancia.get(k)>=diametroFerramenta/2){
						pontos2.add(pontosPossiveis.get(k));
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
				}
				else{
					if(menorDistancia.get(k)<=(i+1)*(0.5*diametroFerramenta)+variacao/2 && menorDistancia.get(k)>=(i+1)*(0.5*diametroFerramenta)-variacao/2){
						pontos2.add(pontosPossiveis.get(k));
						bossArray.add(new Ellipse2D.Double(pontosPossiveis.get(k).getX()-raioMedia, pontosPossiveis.get(k).getY()-raioMedia, raioMedia*2, 2*raioMedia));
					}
				}
			}
			System.out.println("Tamanho dos pontos :  "+pontos2.size());
			pontos.add(pontos2);
		}

		System.out.println(pontosPossiveis.size());
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
				if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
					distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
				}
			}
			malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
			menorDistancia.add(distanciaTmp);
			//				System.out.println(menorDistancia.get(i));
		}
		System.out.println(pontosPossiveis.size());
		
		final double diametro = 2*raioMenor;
		
		numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametro));
		for(int i=0;i<numeroDeCortes+10;i++){
			for(int k=0;k<pontosPossiveis.size();k++){
				if(i==0){
					if(menorDistancia.get(k)<=diametro/2+variacao && menorDistancia.get(k)>=diametro/2){
						pontosMenores.add(pontosPossiveis.get(k));
					}
				}
				else{
					if(menorDistancia.get(k)<=(i+1)*(0.5*diametro)+variacao/2 && menorDistancia.get(k)>=(i+1)*(0.5*diametro)-variacao/2){
						pontosMenores.add(pontosPossiveis.get(k));
					}
				}
			}
		}

		System.out.println("Pontos menores:  "+pontosMenores.size());





		class painelTest extends JPanel{

			GeneralPath p = new GeneralPath();
			GeneralPath r = new GeneralPath();
			ArrayList<Ellipse2D> e = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> f = new ArrayList<Ellipse2D>();
			ArrayList<Ellipse2D> w = new ArrayList<Ellipse2D>();
			double largura=150, comprimento=200;
			Point3d[][] matriz = new Point3d[98][98];


			painelTest(){


				r.moveTo(0,0);
				r.lineTo(5*comprimento, 0);
				r.lineTo(5*comprimento, 5*largura);
				r.lineTo(0, 5*largura);
				r.lineTo(0, 0);

				for(int i=0;i<pontos.size();i++){
					for(int k=0;k<pontos.get(i).size();k++){
						if(pontos.get(i).size()<1){
							break;
						}
						f.add(new Ellipse2D.Double(5*pontos.get(i).get(k).getX()-2.5*diametroFerramenta,5*pontos.get(i).get(k).getY()-2.5*diametroFerramenta,5*diametroFerramenta,5*diametroFerramenta));
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
				//				for(int i=0;i<maximos.size();i++){
				//					if(maximos.size()<1){
				//						break;
				//					}
				//					e.add(new Ellipse2D.Double(5*maximos.get(i).getX(),5*maximos.get(i).getY(),5,5));					
				//				}
				for(int i=0;i<pontosMenores.size();i++){
					if(pontosMenores.size()<1){
						break;
					}
					e.add(new Ellipse2D.Double(5*pontosMenores.get(i).getX()-2.5*diametro,5*pontosMenores.get(i).getY()-2.5*diametro,diametro*5,diametro*5));					
				}
				for(int i=0;i<pontosPeriferia.size();i++){
					if(pontosPeriferia.size()<1){
						break;
					}
					w.add(new Ellipse2D.Double(5*pontosPeriferia.get(i).getX(),5*pontosPeriferia.get(i).getY(),5,5));
				}

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
				g2d.setColor(new Color(100,100,251));
				g2d.draw(r);

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
		GeneralPath path = new GeneralPath();
		path.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		ArrayList<Shape> bossArray = new ArrayList<Shape>();
		ArrayList<Point3d> pontosPeriferia = new ArrayList<Point3d>();

		for(int r=0;r<vertex.size();r++){
			path.lineTo(vertex.get(r).getX(), vertex.get(r).getY());
		}
		path.closePath();
		bossArray.add(path);
		double distancia, maiorX, maiorY;
		int q;
		for(int j=0;j<vertex.size();j++){
			if(j==vertex.size()-1)
				q=0;
			else
				q=j+1;

			if(vertex.get(j).getX()>vertex.get(q).getX())
				maiorX = vertex.get(j).getX();
			else
				maiorX = vertex.get(q).getX();

			if(vertex.get(j).getY()>vertex.get(q).getY())
				maiorY = vertex.get(j).getY();
			else
				maiorY = vertex.get(q).getY();


			if(vertex.get(j).getX()==vertex.get(q).getX()){
				distancia = vertex.get(j).getY();
				for(int h=0;h<1000;h++){
					pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
					if(maiorY == vertex.get(j).getY())
						distancia-=1;
					else
						distancia+=1;
					if(distancia==vertex.get(q).getY()){
						h=1000;
						pontosPeriferia.add(new Point3d(vertex.get(j).getX(),distancia,z));
					}
				}
			}
			else if(vertex.get(j).getY()==vertex.get(q).getY()){
				distancia = vertex.get(j).getX();
				for(int h=0;h<1000;h++){
					pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
					if(maiorX == vertex.get(j).getX())
						distancia-=1;
					else
						distancia+=1;
					if(distancia==vertex.get(q).getX()){
						h=1000;
						pontosPeriferia.add(new Point3d(distancia,vertex.get(j).getY(),z));
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
