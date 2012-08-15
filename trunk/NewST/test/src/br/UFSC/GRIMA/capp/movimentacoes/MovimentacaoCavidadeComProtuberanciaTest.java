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

import br.UFSC.GRIMA.cad.Generate3Dview;
import br.UFSC.GRIMA.capp.DeterminateShortestDistance;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MovimentacaoCavidadeComProtuberanciaTest {
	private Workingstep ws;
	private Ferramenta ferramenta;
	private Cavidade cavidade;
	private ArrayList<Boss> itsBoss;
	public Projeto projeto;
	Bloco bloco;
	CircularBoss boss;
	RectangularBoss boss1;
	RectangularBoss boss2;
	Face faceXY;
	ArrayList<ArrayList<Point3d>> pontos;
	ArrayList<Point3d> pontosPeriferia;
	double malha[][][] = new double[99][99][2];
	ArrayList<Point3d> pontosPossiveis, pontosPossX, pontosPossY;
	ArrayList<Point2d> coordenadas;
	ArrayList<Point2d> coor;
	double maiorMenorDistancia;
	ArrayList<Double> menorDistancia, menorX, menorY;
	ArrayList<Point3d> maximos;
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
		this.cavidade=new Cavidade();
		this.cavidade.setNome("Lucas");
		this.cavidade.setPosicao(10, 10, 0);
		this.cavidade.setProfundidade(10);
		this.cavidade.setRaio(10);
		this.cavidade.setComprimento(80);
		this.cavidade.setLargura(60);
		this.cavidade.createGeometricalElements();

		faceXY.addFeature(this.cavidade);

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
		
		this.itsBoss = cavidade.getItsBoss();
		
		this.ws = ws;
//		this.ferramenta = this.ws.getFerramenta();
//		this.cavidade = (Cavidade) this.ws.getFeature();
		
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		this.itsBoss.add(this.boss);
		this.itsBoss.add(this.boss1);
		this.itsBoss.add(this.boss2);
		cavidade.setItsBoss(this.itsBoss);
		this.faceXY.addFeature(this.boss);
		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		Janela3D.setVisible(true);

			double largura=this.cavidade.getLargura(),
					comprimento=this.cavidade.getComprimento(),
					raio=this.cavidade.getRaio(),
					raioAtual,
					z=-10,
					diametroFerramenta = 4;//this.ferramenta.getDiametroFerramenta();
			RoundRectangle2D retanguloCavidade = new RoundRectangle2D.Double(this.cavidade.getPosicaoX(), this.cavidade.getPosicaoY(), comprimento, largura, 2*raio, 2*raio);

			double malhaMenoresDistancias[][] = new double[99][99];
			
			ArrayList<Shape> bossArray;
			//		ArrayList<Ellipse2D> listaElipses;
			ArrayList<Point3d> pontos2;
			Point2D borda[];


			//CRIAR MALHA DE PONTOS PARA COMPARA��O
			for(int i=0;i<malha.length;i++){
				for(int k=0;k<malha[i].length;k++){
					malha[i][k][0] = this.cavidade.getPosicaoX()+comprimento*(i+1)/100;//x
					malha[i][k][1] = this.cavidade.getPosicaoY()+largura*(k+1)/100;//y
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
					raioAtual=((boss.getDiametro2()-boss.getDiametro1())*(-z-this.cavidade.getPosicaoZ())/(boss.getAltura()*2)+(boss.getDiametro1()/2));
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
				//			else if(this.itsBoss.get(i).getClass()==GeneralBoss.class){
				//				
				//			}

			}

			borda = Cavidade.determinarPontosEmRoundRectangular(new Point3d(this.cavidade.getPosicaoX(),this.cavidade.getPosicaoY(),z), this.cavidade.getComprimento(), this.cavidade.getLargura(), this.cavidade.getRaio());
			for(int k=0;k<borda.length;k++){
				pontosPeriferia.add(new Point3d(borda[k].getX(),borda[k].getY(),z));
			}

			int b=0,c=0;
			//CRIAR MALHA DE PONTOS DE USINAGEM (CONTAINS), E A MALHA DOS PONTOS N�O USINADOS
			pontosPossiveis = new ArrayList<Point3d>();
			pontosPossX = new ArrayList<Point3d>();
			pontosPossY = new ArrayList<Point3d>();
			coordenadas = new ArrayList<Point2d>();
			for(int i=0;i<malha.length;i++){
				for(int k=0;k<malha[i].length;k++){
					if(retanguloCavidade.contains(malha[i][k][0], malha[i][k][1])){
						for(int g=0;g<bossArray.size();g++){
							if(!bossArray.get(g).contains(malha[i][k][0], malha[i][k][1])){
								b++;
							}
							if(!bossArray.get(g).contains(malha[k][i][0], malha[k][i][1])){
								c++;
							}
						}
						if(b==bossArray.size()){
							pontosPossiveis.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
							pontosPossX.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
							coordenadas.add(new Point2d(i,k));
//							System.out.println(k);
						}
						else{
							malhaMenoresDistancias[i][k] = 0;
						}
						if(c==bossArray.size()){
							pontosPossY.add(new Point3d(malha[k][i][0],malha[k][i][1],z));							
						}
						else{
							malhaMenoresDistancias[i][k] = 0;
						}
						b=0;
						c=0;
					}
					malhaMenoresDistancias[i][k] = 0;
				}
			}

//			if(pontosPossiveis.size()<1){
//				break;
//			}
			double distanciaTmp, distanciaTmpX, distanciaTmpY;
			int numeroDeCortes,u=0;


			menorDistancia = new ArrayList<Double>();
			menorX = new ArrayList<Double>();
			menorY = new ArrayList<Double>();
			for(int i=0;i<pontosPossiveis.size();i++){
				distanciaTmp=100;
				distanciaTmpX=100;
				distanciaTmpY=100;
				for(int k=0;k<pontosPeriferia.size();k++){
					if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i))<distanciaTmp){
						distanciaTmp=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossiveis.get(i));
					}
					if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossX.get(i))<distanciaTmpX){
						distanciaTmpX=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossX.get(i));
					}
					if(OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossY.get(i))<distanciaTmpY){
						distanciaTmpY=OperationsVector.distanceVector(pontosPeriferia.get(k), pontosPossY.get(i));
					}
				}
//				distanciaTmp=distanciaTmp*10;
//				distanciaTmp=Math.round(distanciaTmp);
//				distanciaTmp=distanciaTmp/10;
//				System.out.println(distanciaTmp);
				malhaMenoresDistancias[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = distanciaTmp;
				menorDistancia.add(distanciaTmp);
				menorX.add(distanciaTmpX);
				menorY.add(distanciaTmpY);
//				System.out.println(menorDistancia.get(i));
			}

			
			double distanciaX,distanciaY;
			double derivadaX,derivadaY;
			distanciaX = malha[1][0][0]-malha[0][0][0];
			distanciaY = malha[0][1][1]-malha[0][0][1];
			
			for(int i=0;i<menorX.size();i++){
				derivadaY = menorX.get(i)/distanciaY;
			}
			
			maximos = new ArrayList<Point3d>();

//			for(int i=1;i<menorX.size();i++){
//				if(i!=menorX.size()-1){
//					if(menorX.get(i-1)<menorX.get(i) &&
//						menorX.get(i+1)<menorX.get(i) &&
//						menorY.get(i-1)<menorY.get(i) &&
//						menorY.get(i+1)<menorY.get(i)){
//						
//						maximos.add(pontosPossX.get(i));
//					}
////					if(menorY.get(i-1)<=menorY.get(i) &&
////							menorY.get(i+1)<=menorY.get(i) &&
////						(menorY.get(i-1)+0.1<=menorY.get(i) ||
////						menorY.get(i+1)+0.1<=menorY.get(i))){
////							maximos.add(pontosPossY.get(i));
////						}
//				}
//			}
			
			/*TESTE*/
			double diametroTmp=1000;
			int contador=0;
			
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
					if(malhaMenoresDistancias[i][k]==0)
						contador=0;
					
//					if(		malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k+1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i][k-1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k+1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k+1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k-1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k-1] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i+1][k] &&
//							malhaMenoresDistancias[i][k]>=malhaMenoresDistancias[i-1][k] &&
//							malhaMenoresDistancias[i][k]!=0){
//				
					if(contador>=6){
						maximos.add(new Point3d(malha[i][k][0],malha[i][k][1],z));
						if(diametroTmp>malhaMenoresDistancias[i][k] && malhaMenoresDistancias[i][k]!=0){
							diametroTmp=malhaMenoresDistancias[i][k];
						}
					}

					
				}
			}

			System.out.println(diametroTmp);
			
						
			for(int i=0;i<menorDistancia.size();i++){
				if(maiorMenorDistancia<menorDistancia.get(i)){
					maiorMenorDistancia=menorDistancia.get(i);
				}
			}
			
			
			numeroDeCortes = (int) (maiorMenorDistancia/(0.75*diametroFerramenta));

			pontos = new ArrayList<ArrayList<Point3d>>();
			for(int i=0;i<numeroDeCortes;i++){
				pontos2 = new ArrayList<Point3d>();
				for(int k=0;k<menorDistancia.size();k++){
					if(menorDistancia.get(k)<=(i+1)*(0.75*diametroFerramenta) && menorDistancia.get(k)>=(i+1)*(0.75*diametroFerramenta)-0.65){
						pontos2.add(pontosPossiveis.get(k));
					}
				}
				pontos.add(pontos2);
			}
//			JFrame frame1 = new JFrame("BEZIER SURFACE");
//			frame1.setBounds(100, 100, 800, 500);
//			JPanel painel1 = new JPanel();
//			painel1.repaint();
//			painel1.setLayout(new BorderLayout());
//			frame1.getContentPane().add(painel1);
//			J3D j3d = new J3D(painel1);
//			
//			
//			frame1.setVisible(true);
			//ELIMINAR OS PONTOS QUE N�O PODEM SER USINADOS DA MALHA

			//CRIAR O ARRAY DAS MENORES DISTANCIAS
			
			//ADICIONAR NA SAIDA.
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
						f.add(new Ellipse2D.Double(5*pontos.get(i).get(k).getX(),5*pontos.get(i).get(k).getY(),5,5));
					}
				}
//				for(int i=0;i<malha.length;i++){
//					for(int k=0;k<malha[i].length;k++){
//						e.add(new Ellipse2D.Double(5*malha[i][k][0],5*malha[i][k][1],5,5));
//					}
//				}
//				for(int i=0;i<pontosPossiveis.size();i++){
//					if(pontosPossiveis.size()<1){
//						break;
//					}
//					e.add(new Ellipse2D.Double(5*pontosPossiveis.get(i).getX(),5*pontosPossiveis.get(i).getY(),5,5));					
//				}
				for(int i=0;i<maximos.size();i++){
					if(maximos.size()<1){
						break;
					}
					e.add(new Ellipse2D.Double(5*maximos.get(i).getX(),5*maximos.get(i).getY(),5,5));					
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
					g2d.draw(e.get(i));
				}
				g2d.setColor(new Color(100, 100, 251));
//				for(int i=0;i<f.size();i++){
//					g2d.draw(f.get(i));
//				}
				g2d.setColor(new Color(251, 100, 100));
				for(int i=0;i<w.size();i++){
					g2d.draw(w.get(i));
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
}
