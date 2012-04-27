package br.UFSC.GRIMA.simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoFuroBaseArredondada;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilGenerico;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilParcialCircular;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilRoundedU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.Ponto;

public class VisualTool {

	private double x, y, z;
	private ToolPanel toolPanel;
	private int workingstepIndex;
	private double nextX, nextY, nextZ;
	private ProjetoDeSimulacao projetoDeSimulacao;
	private double velocity;
	private boolean isReadyForNextWkstp;
	private boolean haveNewTool;
	private boolean isAtWorkingstepPoint;
	private boolean isJobDone;
	private boolean isAtMovementPoint;
	private boolean isMoving;
	private boolean isFirstTime;
	private Rectangle3D block;
	private ArrayList<Point3d> trailPoints;
	private ArrayList<Workingstep> trailFuros;
	private final static int UPPERSHIFT = 8;
	private Iterator<Ponto> iterator;
	private int trajetoriaAtualIndex;
	int i,j;
	private boolean calculouMov;
	
	public VisualTool(ProjetoDeSimulacao projetoDeSimulacao, ToolPanel toolPanel) {

		setInicialConditions();
		this.block = projetoDeSimulacao.getBlock();
		velocity = 2;
		this.projetoDeSimulacao = projetoDeSimulacao;
		workingstepIndex = 0;
		x = y = 0;
		z = ProjetoDeSimulacao.PLANODETROCA; //z = 200
		trailFuros = new ArrayList<Workingstep>();
		trailPoints = new ArrayList<Point3d>();
		trailPoints.add(new Point3d((int) x, (int) y, (int) z));
		this.toolPanel = toolPanel;
		
		System.out.println("HA: " + projetoDeSimulacao);
		System.out.println("HAA: " + projetoDeSimulacao.getWorkingsteps());
		System.out.println("HAAA: " + projetoDeSimulacao.getWorkingsteps().get(workingstepIndex));
		System.out.println("HAAAA: " + projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFerramenta());
		System.out.println("HAAAAA: " + projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFerramenta().getDiametroFerramenta());
		
		setPosicao(0, 0, ProjetoDeSimulacao.PLANODETROCA);
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	private void setInicialConditions() {
		isReadyForNextWkstp = true;
		haveNewTool = false;
		isAtWorkingstepPoint = false;
		isJobDone = false;
		isAtMovementPoint = false;
		isMoving = false;
		isFirstTime = true;
	}

	public void goToNextWorkingstepPoint() {
		if (!isMoving) {
			isMoving = true;

			if (!isFirstTime) {
				workingstepIndex = toolPanel.getWorkingstepsIndex() + 1;
				if(workingstepIndex<this.projetoDeSimulacao.getWorkingsteps().size())
				ProjetoDeSimulacao.PLANODEMOVIMENTO = (int)(this.projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getOperation().getRetractPlane() + this.block.getZ() + 8);
			}

			isFirstTime = false;
			toolPanel.setWorkingstepsIndex(workingstepIndex);

			if (projetoDeSimulacao.getWorkingsteps().size() == workingstepIndex) {
				isReadyForNextWkstp = false;
				toolPanel.setAnimation(false);
				return;
			}

			Workingstep wsTmp = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex);

			Class featClass = wsTmp.getFeature().getClass(); 
			
			if(featClass.equals(Cavidade.class) && !(wsTmp.getOperation().getStartPoint().equals(new Point3d(0.0, 0.0, 0.0)))&& wsTmp.getTipo() == Workingstep.DESBASTE) // isto indica q este WS é o segundo de desbaste
			{
//				wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
				Vector movimentacao = new Vector();
				DeterminarMovimentacao detMov = new DeterminarMovimentacao();
				ArrayList<Path> path = detMov.getTrajetoriasCantosDaCavidade(wsTmp);
				
				 for(int j = 0; j < path.size(); j++)
					{
						double xAux = path.get(j).getFinalPoint().getX();
						double yAux = path.get(j).getFinalPoint().getY();
						double zAux = -path.get(j).getFinalPoint().getZ();
						
//						if (path.get(j).getClass().equals(LinearPath.class)){
							movimentacao.add(new Ponto(xAux, yAux, zAux));
//						}
//						if (path.get(j).getClass().equals(CircularPath.class)){
//						
//							Point3d inicialPoint = path.get(j).getInitialPoint();
//							Point3d finalPoint = path.get(j).getFinalPoint();
//							Point3d center = ((CircularPath)path.get(j)).getCenter();
//							double raio = ((CircularPath)path.get(j)).getRadius();
//							
//							System.out.println("RAIO : " + raio);
//							System.out.println("Y : " + (inicialPoint.getY() - center.getY()));
//							
//							double anguloInicial = Math.asin((inicialPoint.getY() - center.getY())*Math.PI/raio/180);
//							
//							double angulo2 = Math.asin((finalPoint.getY() - center.getY())*Math.PI/raio/180);
//							
////							double deltaAngulo = Math.PI-anguloInicial-Math.asin((finalPoint.getY() - center.getY())*Math.PI/raio/180);
//							
//							double deltaAngulo = angulo2 - anguloInicial;
//							
//							int numeroDePontos = 5; 
//								
//							Ponto[] movTmp = DeterminarMovimentacao.determinarPontosEmCircunferencia(Math.PI/2, Math.PI/2, raio, numeroDePontos, zAux);
//							
//							for(int i = 0; i<movTmp.length;i++){
//								
//								movTmp[i] = new Ponto(movTmp[i].getX() + center.getX(), movTmp[i].getY() + center.getY(), zAux);
//								
//								movimentacao.add(movTmp[i]);
//							}
//							
//							
//						}
					}
				 
				wsTmp.setPontosMovimentacao(movimentacao);
				calculouMov = true;
				
				Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//				System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
				iterator = wsTmp.getPontosMovimentacao().iterator();
				trajetoriaAtualIndex = 0;
				setNextX(point3d.getX());
				setNextY(point3d.getY());
				setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
			}else if(featClass.equals(Cavidade.class) && wsTmp.getTipo() == Workingstep.ACABAMENTO)
			{
				
//				wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
				Vector movimentacao = new Vector();
				DeterminarMovimentacao detMov = new DeterminarMovimentacao();
				ArrayList<Path> path = detMov.getTrajetoriasAcabamentoLadosCavidade(wsTmp);
				
				 for(int j = 0; j < path.size(); j++)
					{
						double xAux = path.get(j).getFinalPoint().getX();
						double yAux = path.get(j).getFinalPoint().getY();
						double zAux = -path.get(j).getFinalPoint().getZ();
						
						movimentacao.add(new Ponto(xAux, yAux, zAux));
					}
				 
				wsTmp.setPontosMovimentacao(movimentacao);
				calculouMov = true;
				
				Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//				System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
				iterator = wsTmp.getPontosMovimentacao().iterator();
				trajetoriaAtualIndex = 0;
				setNextX(point3d.getX());
				setNextY(point3d.getY());
				setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
			}else if (featClass.equals(FuroBasePlana.class)|| featClass.equals(FuroBaseConica.class) || featClass.equals(Ranhura.class) || featClass.equals(Degrau.class) || featClass.equals(Cavidade.class)) {
//				setNextX(projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFeature().getPosicaoX());
//				setNextY(projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFeature().getPosicaoY());
//				setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				System.out.println("WORKINGSTEP ATUAL : " + wsTmp.getClass());
				System.out.println("WORKINGSTEP ATUAL : " + wsTmp.getId());
				System.out.println("WORKINGSTEP ATUAL : " + wsTmp.getFeature().getPosition().getCoordinates());
				
				wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
				System.out.println("PONTO INICIAL : " + wsTmp.getPontos()[0]);
				Vector movimentacoes = DeterminarMovimentacao.getPontosMovimentacao(wsTmp);
				System.out.println("PONTOS MOVIMENTAÇAOOOOOOOOOOOOOOOOOOOO2: " + movimentacoes);

				Vector movimentacao = (Vector)movimentacoes.get(0);
				wsTmp.setPontosMovimentacao(movimentacao);
				calculouMov = true;
				
				Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
				System.out.println("PONTOS MOVIMENTAÇAOOOOOOOOOOOOOOOOOOOO2: " + wsTmp.getPontosMovimentacao() );
				iterator = wsTmp.getPontosMovimentacao().iterator();
				trajetoriaAtualIndex = 0;
				setNextX(point3d.getX());
				setNextY(point3d.getY());
				setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
			}else if(featClass.equals(RanhuraPerfilCircularParcial.class) && wsTmp.getFerramenta().getClass().equals(FaceMill.class)) {
				
				wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
				Vector movimentacao = new Vector();
				MovimentacaoRanhuraPerfilParcialCircular detMov = new MovimentacaoRanhuraPerfilParcialCircular(wsTmp, projetoDeSimulacao.getFace());
				ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilCircularParcial();
				
				 for(int j = 0; j < path.size(); j++)
					{
						double xAux = path.get(j).getFinalPoint().getX();
						double yAux = path.get(j).getFinalPoint().getY();
						double zAux = -path.get(j).getFinalPoint().getZ();
						
						movimentacao.add(new Ponto(xAux, yAux, zAux));
					}
				 
				wsTmp.setPontosMovimentacao(movimentacao);
				calculouMov = true;
				
				Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//				System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
				iterator = wsTmp.getPontosMovimentacao().iterator();
				trajetoriaAtualIndex = 0;
				setNextX(point3d.getX());
				setNextY(point3d.getY());
				setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
			
				
				}else if(featClass.equals(RanhuraPerfilCircularParcial.class) && wsTmp.getFerramenta().getClass().equals(BallEndMill.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilParcialCircular detMov = new MovimentacaoRanhuraPerfilParcialCircular(wsTmp, projetoDeSimulacao.getFace());
					ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilCircularParcial();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilQuadradoU.class) && wsTmp.getFerramenta().getClass().equals(FaceMill.class)) {
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilQuadradoU detMov = new MovimentacaoRanhuraPerfilQuadradoU(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilQuadradoU();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
					
					}else if(featClass.equals(RanhuraPerfilQuadradoU.class) && (wsTmp.getFerramenta().getClass().equals(BullnoseEndMill.class) || wsTmp.getFerramenta().getClass().equals(BallEndMill.class))){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilQuadradoU detMov = new MovimentacaoRanhuraPerfilQuadradoU(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilVee.class) && wsTmp.getFerramenta().getClass().equals(FaceMill.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilVee detMov = new MovimentacaoRanhuraPerfilVee(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilVee();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilVee.class) && wsTmp.getFerramenta().getClass().equals(BallEndMill.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilVee detMov = new MovimentacaoRanhuraPerfilVee(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilVee();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
					
				}else if(featClass.equals(RanhuraPerfilRoundedU.class) && wsTmp.getFerramenta().getClass().equals(FaceMill.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilRoundedU detMov = new MovimentacaoRanhuraPerfilRoundedU(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilRoundedU();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilRoundedU.class) && (wsTmp.getFerramenta().getClass().equals(BallEndMill.class) || wsTmp.getFerramenta().getClass().equals(EndMill.class))){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilRoundedU detMov = new MovimentacaoRanhuraPerfilRoundedU(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilRoundedU();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilBezier.class) && wsTmp.getFerramenta().getClass().equals(FaceMill.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilGenerico detMov = new MovimentacaoRanhuraPerfilGenerico(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilGenerico();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}else if(featClass.equals(RanhuraPerfilBezier.class) && wsTmp.getOperation().getClass().equals(FreeformOperation.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoRanhuraPerfilGenerico detMov = new MovimentacaoRanhuraPerfilGenerico(wsTmp);
					ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilGenerico();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
				}  else if (featClass.equals(FuroBaseArredondada.class) && (wsTmp.getOperation().getClass()).equals(CenterDrilling.class)){
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoFuroBaseArredondada detMov = new MovimentacaoFuroBaseArredondada(wsTmp);
					ArrayList<Path> path = detMov.movimentacaoCenterDrilling();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
					
				} else if(featClass.equals(FuroBaseArredondada.class) && (wsTmp.getOperation().getClass()).equals(Drilling.class)){

					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacao = new Vector();
					MovimentacaoFuroBaseArredondada detMov = new MovimentacaoFuroBaseArredondada(wsTmp);
					ArrayList<Path> path = detMov.movimentacaoFuracao();
					
					 for(int j = 0; j < path.size(); j++)
						{
							double xAux = path.get(j).getFinalPoint().getX();
							double yAux = path.get(j).getFinalPoint().getY();
							double zAux = -path.get(j).getFinalPoint().getZ();
							
							movimentacao.add(new Ponto(xAux, yAux, zAux));
						}
					 
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				
					
				}else if (featClass.equals(FuroBaseArredondada.class) && (wsTmp.getOperation().getClass()).equals(BottomAndSideRoughMilling.class))
				{
					Vector movimentacao = new Vector();
					MovimentacaoFuroBaseArredondada mov = new MovimentacaoFuroBaseArredondada(wsTmp);
					Vector <Point3d> path = MovimentacaoFuroBaseArredondada.transformCircularPathInPoints3d(mov.desbaste());
					for(Point3d pointTmp: path)
					{
						Ponto ponto = new Ponto(pointTmp.x, pointTmp.y, -pointTmp.z);
						movimentacao.add(ponto);
					}
					wsTmp.setPontosMovimentacao(movimentacao);
					calculouMov = true;
					
					Ponto point3d = (Ponto)wsTmp.getPontosMovimentacao().get(0);
//					System.out.println("PONTOS MOVIMENTAÇAO: " + wsTmp.getPontosMovimentacao() );
					iterator = wsTmp.getPontosMovimentacao().iterator();
					trajetoriaAtualIndex = 0;
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z = 50
				}
				else{
					System.out.println("Feature Class Desconhecida (goToNextWorkingstepPoint): " + projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFeature().getClass());
				}
		}
	}

	public void setNextX(double NextX) {
		nextX = NextX;
//		if (nextX - x >= 0)
//			directionX = 1;
//		else
//			directionX = -1;
	}

	public void setNextY(double NextY) {
		nextY = NextY;
//		if (nextY - y >= 0)
//			directionY = 1;
//		else
//			directionY = -1;
	}

	public void setNextZ(double NextZ) {
		nextZ = NextZ;
//		if (nextZ - z >= 0)
//			directionZ = 1;
//		else
//			directionZ = -1;
	}

	public void doThisWorkingstep() {
		
		Class featClass = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFeature().getClass();
		MachiningOperation operation = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getOperation();		
		
		if(featClass.equals(FuroBasePlana.class) || featClass.equals(FuroBaseConica.class)){

			Workingstep wsFuro = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex);
			
			Furo furo = (Furo)wsFuro.getFeature();
			
			int cuttingDepth = 0;
			
			if(operation.getClass()==CenterDrilling.class){
				cuttingDepth = (int) ((CenterDrilling)operation).getCuttingDepth();
			}else if(operation.getClass()==Drilling.class){
				cuttingDepth = (int) ((Drilling)operation).getCuttingDepth();
			}else if(operation.getClass()==Reaming.class){
				cuttingDepth = (int) ((Reaming)operation).getCuttingDepth();
			}

			if (!isMoving) {
//				setNextZ(VisualTool.UPPERSHIFT - 2);
				isMoving = true;
				if (iterator.hasNext()) {
					trajetoriaAtualIndex++;
					Ponto point3d = iterator.next();
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(block.getZ() - point3d.getZ() + UPPERSHIFT);
//					System.out.println("("+point3d.getX()+" , "+point3d.getY()+" , "+point3d.getZ()+")");
//
//					System.out.println("Cutting DEPTH : " + cuttingDepth);
//					System.out.println("Z : " + z);
//					System.out.println("EQUAÇAO : " + (block.getZ() + UPPERSHIFT - cuttingDepth));
//					
					if (z <= (block.getZ() + UPPERSHIFT - cuttingDepth)
							&& (wsFuro.getFerramenta().getClass() == TwistDrill.class || wsFuro
									.getFerramenta().getClass() == CenterDrill.class
									|| wsFuro.getFerramenta().getClass() == Reamer.class)) {
						
						trailPoints.add(new Point3d((int) x, (int) y, (int) z));
						trailFuros.add((Workingstep) projetoDeSimulacao.getWorkingsteps().get(workingstepIndex));

					}
//					}else if(furo.isPassante()){
//						
//						if(z == UPPERSHIFT - 2){
//							trailPoints.add(new Point3d((int) x, (int) y, (int) z));
//							trailFuros.add((Workingstep) projetoDeSimulacao.getWorkingsteps().get(workingstepIndex));
//						}
//					}
						
				}else {
					isAtWorkingstepPoint = false;
					isJobDone = true;
					isMoving = false;
				}
			}
			
		}else if (featClass.equals(RanhuraPerfilBezier.class) || featClass.equals(RanhuraPerfilCircularParcial.class) || featClass.equals(RanhuraPerfilRoundedU.class) || featClass.equals(Ranhura.class) || featClass.equals(RanhuraPerfilVee.class) || featClass.equals(Degrau.class) || featClass.equals(Cavidade.class) || featClass.equals(RanhuraPerfilQuadradoU.class) || featClass.equals(FuroBaseArredondada.class)) {
			
			if (!isMoving) {
				isMoving = true;
				if (iterator.hasNext()) {
					trajetoriaAtualIndex++;
					Ponto point3d = iterator.next();
					setNextX(point3d.getX());
					setNextY(point3d.getY());
					setNextZ(block.getZ() - point3d.getZ() + UPPERSHIFT);
//					System.out.println("("+point3d.getX()+" , "+point3d.getY()+" , "+point3d.getZ()+")");
				} else {
					isAtWorkingstepPoint = false;
					isJobDone = true;
					isMoving = false;
				}
			}
			
		}else {
			System.out.println("Feature Class Desconhecida (doThisFeature): " + projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFeature().getClass());
		}

	}

	public void move() {
		
		double resultante = calculateVelocityFactor();

		// System.out.println("("+x+", "+y+", "+z+")");

		if (Math.abs(nextX - x) <= Math.abs(resultante * (nextX - x))) {
			x = nextX;
		}

		if (Math.abs((nextY - y)) <= Math.abs(resultante * (nextY - y))) {
			y = nextY;
		}

		if (Math.abs(nextZ - z) <= Math.abs(resultante * (nextZ - z))) {
			z = nextZ;
		}

		if (x == nextX && y == nextY && z == nextZ) {
			trailPoints.add(new Point3d((int) x, (int) y, (int) z));
			isMoving = false;
			// Chegou ao ponto desejado
			if (isReadyForNextWkstp) {
				isReadyForNextWkstp = false;
				isAtWorkingstepPoint = true;

			} else if (isAtWorkingstepPoint) {

			} else if (isJobDone) {
				isJobDone = false;
				isAtMovementPoint = true;

			} else if (isAtMovementPoint) {
				isAtMovementPoint = false;
				isAtWorkingstepPoint = true;

			} else if (haveNewTool) {
				isReadyForNextWkstp = true;
				haveNewTool = false;
			}
		}

		x += resultante * (nextX - x);
		y += resultante * (nextY - y);
		z += resultante * (nextZ - z);
		// upperMove();
		// lateralMove();
	}

	private double calculateVelocityFactor() {
		
		double resultante = Math.sqrt((nextX - x) * (nextX - x) + (nextY - y)
				* (nextY - y) + (nextZ - z) * (nextZ - z));
		
		if (resultante != 0)
			resultante = velocity / resultante;
		else
			resultante = 0;
		
		return resultante;
	}

	public void lateralPaint(Graphics g) {
		
		if (workingstepIndex == projetoDeSimulacao.getWorkingsteps().size())
			return;
		
		double diameter = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getDiametroFerramenta();
		
		double depth = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getProfundidadeMaxima();
		
		g.setColor(Color.BLACK);
		drawTool(g, diameter, depth);
		
		if (projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getClass()
				.equals(TwistDrill.class))
			drawToolTriangle(g, diameter, depth);
		
		drawToolSupport(g, diameter, depth);

		paintLateralToolRanhuras(g,j);
		
		j++;
		
		if(j>20)
			j=0;
		
	}
	
	public void paintLateralToolRanhuras(Graphics g, int j) {
		
		if (workingstepIndex == projetoDeSimulacao.getWorkingsteps().size())
			return;
		
		double diameter = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getDiametroFerramenta();
		double depth = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFerramenta()
				.getProfundidadeMaxima();
		
		int l = (int) depth/8;
		if(j<=10){
			int x1[] = {(int) (z+depth), (int) (z+depth), (int) z, (int) z};
			int y1[] = {(int) (x-diameter/2), (int) (x-diameter/2+l), (int) (x), (int) (x-l)};
			int y2[] = {(int) (x), (int) (x+l), (int) (x+diameter/2), (int) (x+diameter/2-l)};
			drawLine(g, x1, y1);
			drawLine(g, x1, y2);
		}
		
		if(j>10 && j<=20){	
			int x1[] = {(int) (z+depth), (int) (z+depth), (int)(z+depth/3) , (int) (z+2*depth/3)};
			int y1[] = {(int) (x-diameter/2), (int)(x-diameter/2), (int) (x-diameter/2+(diameter/4)-l), (int) (x-diameter/2+diameter/4)};
			int x2[] = {(int) (z+depth), (int) (z+depth), (int) z, (int) z};
			int y2[] = {(int) (x-diameter/2+diameter/4), (int) (x-diameter/2+l+(diameter/4)), (int) (x-diameter/2+(diameter/2)+(diameter/4)), (int) (x-diameter/2+(diameter/2)+(diameter/4)-l)};
			int x4[] = {(int) (z+depth/3), (int)  (z+2*depth/3), (int) z, (int) z};
			int y4[] = {(int) (x-diameter/2+(diameter/2)+(diameter/4)), (int) (x-diameter/2+(diameter/2)+(diameter/4)+l), (int) (x-diameter/2+diameter), (int) (x-diameter/2+diameter)};
			int x3[] = {(int) (z+22*depth/36), (int) (z+87*depth/100), (int) z, (int) z};
			int y3[] = {(int) (x-diameter/2+(diameter/2)+(diameter/4)), (int) (x-diameter/2+(diameter/2)+(diameter/4)+l), (int) (x-diameter/2+diameter+15*(x-diameter/2)/18), (int) (x-diameter/2+diameter+6*(x-diameter/2)/18)};
			
			if (projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFerramenta().getClass()
					.equals(TwistDrill.class))
			{
				drawLine(g, x1, y1);
				drawLine(g, x2, y2);
				drawLine(g, x3, y3);
			}
			else{
				drawLine(g, x1, y1);
				drawLine(g, x2, y2);
				drawLine(g, x4, y4);				
			}
		}
	}
	
	private void drawLine(Graphics g, int x[], int y[]) {
		g.setColor(Color.RED);
		g.fillPolygon(x, y, 4);
	}
	
	private void drawTool(Graphics g, double diameter, double depth) {
		g.fillRect((int) z-1, (int) (x - diameter / 2), (int) depth,
				(int) diameter);
	}
	
	private void drawToolTriangle(Graphics g, double diameter, double depth) {
		int[] x = { (int) z, (int) z, (int) (z - diameter / 5.5) };
		int[] y = { (int) (this.x - diameter / 2),
				(int) (this.x + diameter / 2), (int) (this.x) };
		Polygon triangle = new Polygon(x, y, 3);
		g.fillPolygon(triangle);

	}
	
	public void drawToolSupport(Graphics g, double diameter, double depth) {
		int supportDiameter = 2;

		g.fillRect((int) (z + depth), (int) x - supportDiameter / 2, 300,
				supportDiameter);
	}
	
	public void upperPaint(Graphics g) {
		
		if (workingstepIndex == projetoDeSimulacao.getWorkingsteps().size())
			return;
		
		double diameter = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getDiametroFerramenta();
	
		g.setColor(Color.BLACK);
		
		g.fillOval((int) (y - diameter / 2), (int) (x - diameter / 2),(int) diameter, (int) diameter);
			
		paintUpperToolRanhuras(g,i);
		i++;
		if(i>20)
			i=0;
	}
	
	public void paintUpperToolRanhuras(Graphics g,int i) {
		
		if (workingstepIndex == projetoDeSimulacao.getWorkingsteps().size())
			return;
		
		double diameter = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex)
				.getFerramenta().getDiametroFerramenta();
		double depth = projetoDeSimulacao.getWorkingsteps().get(workingstepIndex).getFerramenta()
				.getProfundidadeMaxima();
		
		g.setColor(Color.RED);
		
		if(i<=10){
			
			g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
							(int) diameter, 0, 60);
			g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
					(int) diameter, 120, 60);
			g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
					(int) diameter, 240, 60);
		}
		if(i>10 && i<=20) {
		
		g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
						(int) diameter, 60, 60);
		g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
				(int) diameter, 180, 60);
		g.fillArc((int) ((y - diameter / 2) + (diameter/32-1)), (int) ((x - diameter / 2)+ (diameter/32-1)), (int) diameter,
				(int) diameter, 300, 60);
		}
	}

	public void goToChangePoint() {
		
		isAtMovementPoint = false;
		
		if (!isMoving) {
			isMoving = true;
			setNextX(0);
			setNextY(0);
			setNextZ(ProjetoDeSimulacao.PLANODETROCA); //z=200
		}
	}

	public void goToMovementPoint() {
		if (!isMoving) {
			isMoving = true;
			setNextZ(ProjetoDeSimulacao.PLANODEMOVIMENTO); //z=50
		}
	}

	private void setPosicao(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean isReadyForNextWkstp() {
		return isReadyForNextWkstp;
	}

	public boolean haveNewTool() {
		return haveNewTool;
	}


	public boolean isAtWorkingstepPoint() {
		return isAtWorkingstepPoint;
	}

	public boolean isJobDone() {
		return isJobDone;
	}

	public boolean isAtMovementPoint() {
		return isAtMovementPoint;
	}

	public void haveNewTool(boolean haveNewTool) {
		this.haveNewTool = haveNewTool;

	}

	public void isReadyForNextWkstp(boolean isReadyForNextWkstp) {
		this.isReadyForNextWkstp = isReadyForNextWkstp;
	}

	public boolean isMoving() {
		return isMoving;
	}

	private void upperDrawLastFeatureList(int toolIndex, Graphics g,
			Vector pontosDeTrajetoria, int ultimoIndex,int cap) {
		
		int index = 0;
		float dash1[] = { 3f, 0f };
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(new Color(205, 133, 63));
		
		while (index < ultimoIndex) {

			int width = (int) projetoDeSimulacao.getWorkingsteps().get(toolIndex)
			.getFerramenta().getDiametroFerramenta();
			
			if (index != 0 && index != 1) {
				Ponto p1 = (Ponto)pontosDeTrajetoria.get(index - 2);
				Ponto p2 = (Ponto)pontosDeTrajetoria.get(index - 1);


				g2d.setStroke(new BasicStroke(width, cap,
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				// DOit
				g2d.drawLine((int) p1.getY(), (int) p1.getX(), (int) p2.getY(),
						(int) p2.getX());
				g2d.drawLine((int) p2.getY(), (int) p2.getX(), (int) y, (int) x);
				//				

			} else if (index != 0) {

				g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				
				Ponto p1 = (Ponto)pontosDeTrajetoria.get(index - 1);
				
				g2d.drawLine((int) p1.getY(), (int) p1.getX(), (int) y, (int) x);
			}

			index++;
		}
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke());

	}

	private void upperDrawList(int toolIndex, Graphics g,
			Vector pontosDeTrajetoria, int ultimoIndex, int cap) {
		
		int index = 0;
		float dash1[] = { 3f, 0f };
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(new Color(205, 133, 63));
		
		while (index <= ultimoIndex) {
			if (index != 0 && index != 1) {
				Ponto p1 = (Ponto)pontosDeTrajetoria.get(index - 2);
				Ponto p2 = (Ponto)pontosDeTrajetoria.get(index - 1);

				int width = (int) projetoDeSimulacao.getWorkingsteps().get(toolIndex)
						.getFerramenta().getDiametroFerramenta();

				g2d.setStroke(new BasicStroke(width, cap,
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				// DOit
				g.drawLine((int) p1.getY(), (int) p1.getX(), (int) p2.getY(),
						(int) p2.getX());
				//				

			}
			index++;
		}
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke());
	}

	private void lateralDrawList(int toolIndex, Graphics g,
			Vector pontosDeTrajetoria, int ultimoIndex, int raio) {
		
		int index = 0;
		float dash1[] = { 3f, 0f };
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(205, 133, 63));
		while (index <= ultimoIndex) {

			if (index != 0 && index != 1) {
			
				double toolRaio = projetoDeSimulacao.getWorkingsteps().get(toolIndex)
						.getFerramenta().getDiametroFerramenta() / 2;

				Ponto p2 = (Ponto)pontosDeTrajetoria.get(index - 1);
				Ponto p1 = (Ponto)pontosDeTrajetoria.get(index - 2);

//				System.out.println("Posicao Z da feature"+projeto.getFeatures().get(toolIndex)
//						.getPosicaoZ());
//				
//				System.out.println("Posicao Z dos pontos"+p2.getZ());
				int width = Math.abs((int) (p1.getZ()));
				g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				// DOit
				if(p1.getZ() > 0 && p2.getZ() > 0){
					
					if ((p1.getX() - p2.getX()) > 0) {
						g.drawLine((int) (block.getZ() - p1.getZ() + UPPERSHIFT + width / 2),
								(int) (p1.getX()-raio), (int) (block.getZ()-p2.getZ()
										+ UPPERSHIFT + width / 2), (int) (p2
												.getX())+raio);
					} else {
						g.drawLine((int)(block.getZ() - p1.getZ() + UPPERSHIFT + width / 2),
								(int) (p1.getX()+raio), (int)( block.getZ()- p2.getZ()
										+ UPPERSHIFT + width / 2), (int) (p2
												.getX()-raio));
					}
				}

				//				ARRUMAR AQUI!!!!!

			} if (index != 0 && x > 0) {
				Ponto p2 = (Ponto)pontosDeTrajetoria.get(index - 1);
				int width = Math.abs((int) (p2.getZ()));
				
				g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				double toolRaio = projetoDeSimulacao.getWorkingsteps().get(toolIndex).
						getFerramenta().getDiametroFerramenta() / 2;

				if(p2.getZ() > 0){
					
					if (x - p2.getX() > 0) {
						g.drawLine((int) (block.getZ() -p2.getZ() + UPPERSHIFT + width / 2),
								(int) (p2.getX() - raio), (int) (block.getZ() -p2.getZ()
										+ UPPERSHIFT + width / 2),
										(int) (x + raio));
					} else {
						g.drawLine((int) (block.getZ() -p2.getZ() + UPPERSHIFT + width / 2),
								(int) (p2.getX() + raio), (int) (block.getZ() -p2.getZ()
										+ UPPERSHIFT + width / 2),
										(int) (x - raio));
					}
				}

			}
			index++;
		}
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke());
	}
	
	public void paintUpperCavidadeTrail(Graphics g) {
		
		int index = 0;
		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();

		while (index < workingsteps.size() && index <= workingstepIndex && calculouMov) {

			if (workingsteps.get(index).getFeature().getClass().equals(Cavidade.class)) {
				
				Cavidade actualFeature = (Cavidade) workingsteps.get(index).getFeature();
				
					if (index == workingstepIndex
							&& trajetoriaAtualIndex != workingsteps.get(workingstepIndex).getPontosMovimentacao().size()) {
						if(SimulationPanel.NEEDPAINT)
						upperDrawLastFeatureList(index, g, workingsteps.get(workingstepIndex).getPontosMovimentacao(), trajetoriaAtualIndex,BasicStroke.CAP_ROUND);
					
					} else {
						if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
						upperDrawList(index, g, workingsteps.get(index).getPontosMovimentacao(), workingsteps.get(index).getPontosMovimentacao().size() - 1,BasicStroke.CAP_ROUND);
						// DESENHAR CONTORNO
						g.setColor(Color.BLACK);
						g.drawRoundRect((int) actualFeature.getPosicaoY(),
								(int) actualFeature.getPosicaoX(),
								(int) actualFeature.getLargura(),
								(int) actualFeature.getComprimento(),
								(int) actualFeature.getRaio() * 2,
								(int) actualFeature.getRaio() * 2);
					}
			}else if (workingsteps.get(index).getFeature().getClass().equals(FuroBasePlana.class)
					&& (workingsteps.get(index).getOperation().getClass()==BottomAndSideRoughMilling.class
							|| workingsteps.get(index).getOperation().getClass()==BottomAndSideRoughMilling.class)) {
				
				FuroBasePlana actualFeature = (FuroBasePlana) workingsteps.get(index).getFeature();
				
					if (index == workingstepIndex
							&& trajetoriaAtualIndex != workingsteps.get(workingstepIndex).getPontosMovimentacao().size()) {
						if(SimulationPanel.NEEDPAINT)
						upperDrawLastFeatureList(index, g, workingsteps.get(workingstepIndex).getPontosMovimentacao(), trajetoriaAtualIndex,BasicStroke.CAP_ROUND);
					
					} else {
						if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
						upperDrawList(index, g, workingsteps.get(index).getPontosMovimentacao(), workingsteps.get(index).getPontosMovimentacao().size() - 1,BasicStroke.CAP_ROUND);
						// DESENHAR CONTORNO
						g.setColor(Color.BLACK);
						g.drawOval((int) (Math.round(actualFeature.getPosicaoY()) - actualFeature.getRaio())
								,(int) (Math.round(actualFeature.getPosicaoX()) - actualFeature.getRaio()),
								(int) actualFeature.getDiametro(), (int) actualFeature.getDiametro());
					}
			}
			index++;
		}

	}


	public void paintLateralCavidadeTrail(Graphics g) {
		
		int index = 0;
		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();
		
		while (index < workingsteps.size() && index <= workingstepIndex && calculouMov) {
			
			if (workingsteps.size() > index
					&& workingsteps.get(index).getFeature().getClass().equals(Cavidade.class)) {
				
				Vector pontos = workingsteps.get(index).getPontosMovimentacao();
				
				int raio = (int)workingsteps.get(index).getFerramenta().getDiametroFerramenta()/2;
				
				Cavidade cavidade = (Cavidade) workingsteps.get(index).getFeature();
				
//				System.out.println("Pontos Movimentacao Size: " + pontos.size());
				
				if (index == workingstepIndex
						&& trajetoriaAtualIndex != pontos.size()) {
					if(SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, trajetoriaAtualIndex - 1,raio);
					
				} else {
					if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, pontos.size() - 1,raio);

					//DESENHAR CONTORNO
					g.setColor(Color.BLACK);
					g.drawRect((int) (cavidade.getPosicaoZ()-(cavidade.getPosicaoZ()-block.getZ())
							- (int)cavidade.getPosicaoZ() + UPPERSHIFT - cavidade.getProfundidade()),
									(int) cavidade.getPosicaoX(),
									(int) cavidade.getProfundidade(),
									(int) cavidade.getComprimento());
				}
				
			} else if (workingsteps.size() > index
					&& workingsteps.get(index).getFeature().getClass().equals(
							FuroBasePlana.class)
					&& (workingsteps.get(index).getOperation().getClass() == BottomAndSideRoughMilling.class 
							|| workingsteps.get(index).getOperation().getClass() == BottomAndSideRoughMilling.class)) {
							
				Vector pontos = workingsteps.get(index).getPontosMovimentacao();
				
				int raio = (int)workingsteps.get(index).getFerramenta().getDiametroFerramenta()/2;
				
				FuroBasePlana furo = (FuroBasePlana) workingsteps.get(index).getFeature();
				
//				System.out.println("Pontos Movimentacao Size: " + pontos.size());
				
				if (index == workingstepIndex
						&& trajetoriaAtualIndex != pontos.size()) {
					if(SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, trajetoriaAtualIndex - 1,raio);
					
				} else {
					if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, pontos.size() - 1,raio);
					
					//DESENHAR CONTORNO
					
					double diametro = furo.getDiametro();
					double profundidade = furo.getProfundidade();
					
					int sobra = (int) (block.getZ() - profundidade);

					g.setColor(Color.BLACK);
					g.drawRect(VisualTool.UPPERSHIFT + sobra - (int)furo.getPosicaoZ()
							,(int) Math.round(furo.getPosicaoX()- 1 - (int) diametro / 2)
							, (int)profundidade
							, (int) diametro);
				}
			}
			index++;
		}
	}

	public void paintUpperRanhurasTrail(Graphics g) {
		int index = 0;

		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();
		
		while (index < workingsteps.size() && index <= workingstepIndex && calculouMov) {

			if (workingsteps.get(index).getFeature().getClass().equals(Ranhura.class)
					|| workingsteps.get(index).getFeature().getClass().equals(Ranhura.class)) {
				
					if (index == workingstepIndex
							&& trajetoriaAtualIndex != workingsteps.get(index).getPontosMovimentacao().size()) {
						if(SimulationPanel.NEEDPAINT)
						upperDrawLastFeatureList(index, g, workingsteps.get(index).getPontosMovimentacao(), trajetoriaAtualIndex,BasicStroke.CAP_BUTT);
						
					} else {
						if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
						upperDrawList(index, g, workingsteps.get(index).getPontosMovimentacao(), workingsteps.get(index).getPontosMovimentacao().size() - 1,BasicStroke.CAP_BUTT);
						
						// DESENHAR CONTORNO
							Ranhura actualFeature = (Ranhura)workingsteps.get(index).getFeature();
							
							if(actualFeature.getEixo()==Ranhura.HORIZONTAL){
								g.setColor(Color.BLACK);
								g.drawRect((int) actualFeature.getPosicaoY(),
										(int) actualFeature.getPosicaoX(),
										(int) actualFeature.getLargura(),
										(int) block.getX());

							}else{
								g.setColor(Color.BLACK);
								g.drawRect((int) actualFeature.getPosicaoY(),
										(int) actualFeature.getPosicaoX()
										,(int) block.getY(),(int) actualFeature.getLargura());
							}
						
					}
				
			}else if (workingsteps.get(index).getFeature().getClass().equals(Degrau.class)) {
				
				Degrau actualFeature = (Degrau) workingsteps.get(index).getFeature();
				
				if (index == workingstepIndex
						&& trajetoriaAtualIndex != workingsteps.get(index).getPontosMovimentacao().size()) {
					if(SimulationPanel.NEEDPAINT)
					upperDrawLastFeatureList(index, g, workingsteps.get(index).getPontosMovimentacao(), trajetoriaAtualIndex,BasicStroke.CAP_BUTT);
				
				} else {
					if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
					upperDrawList(index, g, workingsteps.get(index).getPontosMovimentacao(), workingsteps.get(index).getPontosMovimentacao().size() - 1,BasicStroke.CAP_BUTT);
					
					// DESENHAR CONTORNO
					if(actualFeature.getEixo()==Ranhura.HORIZONTAL){
						g.setColor(Color.BLACK);
						g.drawRect((int) actualFeature.getPosicaoY(),
								(int) actualFeature.getPosicaoX(),
								(int) actualFeature.getLargura(),
								(int) block.getX());
						
					}else{
						g.setColor(Color.BLACK);
						g.drawRect((int) actualFeature.getPosicaoY(),
								(int) actualFeature.getPosicaoX()
								,(int) block.getY(),(int)actualFeature.getLargura());
					}
					
				}
			
		}
			index++;
		}

	}

	public void paintLateralRanhurasTrail(Graphics g) {
		
		int index = 0;
		
		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();
		
		while (index <= workingstepIndex && calculouMov) {

			if (workingsteps.size() > index
					&& workingsteps.get(index).getFeature().getClass().equals(Ranhura.class)) {
				
				Vector pontos = workingsteps.get(index).getPontosMovimentacao();
				
				Ranhura ranhura = (Ranhura) workingsteps.get(index).getFeature();
				
				if (index == workingstepIndex
						&& trajetoriaAtualIndex != pontos.size()) {
					if(SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, trajetoriaAtualIndex - 1,0);
				
				} else {
					if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, pontos.size() - 1,0);

					if (ranhura.getEixo()==Ranhura.HORIZONTAL){
					
						g.setColor(Color.BLACK);
						g.drawRect((int) (ranhura.getPosicaoZ()-(ranhura.getPosicaoZ()-block.getZ())
								- (int)ranhura.getPosicaoZ() + UPPERSHIFT - ranhura.getProfundidade()),
								(int) ranhura.getPosicaoX(),
								(int) ranhura.getProfundidade(),
								(int) (block.getX()));
					}else{
						
						g.setColor(Color.BLACK);
						g.drawRect((int) (ranhura.getPosicaoZ()-(workingsteps.get(index).getFeature().getPosicaoZ()-block.getZ())
								- (int)ranhura.getPosicaoZ() + UPPERSHIFT - ranhura.getProfundidade()),
										(int) ranhura.getPosicaoX(),
										(int) ranhura.getProfundidade(),
										(int) ranhura.getLargura());
						
					}
					
				}
			}
			else if (workingsteps.size() > index
					&& workingsteps.get(index).getFeature().getClass().equals(Degrau.class)) {
				
				Vector pontos = workingsteps.get(index).getPontosMovimentacao();
				
				Degrau degrau = (Degrau) workingsteps.get(index).getFeature();
				
				if (index == workingstepIndex
						&& trajetoriaAtualIndex != pontos.size()) {
					if(SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, trajetoriaAtualIndex - 1,0);
					
				} else {
					if(workingstepIndex==projetoDeSimulacao.getWorkingsteps().size() || SimulationPanel.NEEDPAINT)
					lateralDrawList(index, g, pontos, pontos.size() - 1,0);

					if (degrau.getEixo()==Ranhura.HORIZONTAL){
						
						g.setColor(Color.BLACK);
						g.drawRect((int) (degrau.getPosicaoZ()-(degrau.getPosicaoZ()-block.getZ())- (int)degrau.getPosicaoZ()
								+ UPPERSHIFT - degrau.getProfundidade()),
								(int) degrau.getPosicaoX(),
								(int) degrau.getProfundidade(),
								(int) (block.getX()));
					}else{
						
						g.setColor(Color.BLACK);
						g.drawRect((int) (degrau.getPosicaoZ()-(degrau.getPosicaoZ()-block.getZ())- (int)degrau.getPosicaoZ()
								+ UPPERSHIFT - degrau.getProfundidade()),
										(int) degrau.getPosicaoX(),
										(int) degrau.getProfundidade(),
										(int) degrau.getLargura());
						
					}
					
				}
			}
			index++;
		}


	}

	public void paintUpperFurosTrail(Graphics g) {

		int index = 0;
		Graphics2D g2d = (Graphics2D) g;
		Furo furo;
		Workingstep wsFuro;

		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();

		while (index < workingsteps.size() && index <= workingstepIndex && calculouMov) {

			if (workingsteps.get(index).getFeature().getClass().equals(FuroBasePlana.class)
					|| workingsteps.get(index).getFeature().getClass().equals(FuroBaseConica.class)) {

				Iterator iterator = trailFuros.iterator();

				while(iterator.hasNext()){

					wsFuro = (Workingstep) iterator.next();
					furo = (Furo)wsFuro.getFeature();

					double diametro = wsFuro.getFerramenta().getDiametroFerramenta();

					if(SimulationPanel.NEEDPAINT || workingstepIndex==projetoDeSimulacao.getWorkingsteps().size()){
						g2d.setColor(new Color(205, 133, 63));
						g2d.fillOval((int) (Math.round(furo.getPosicaoY()) - diametro/ 2)
								,(int) (Math.round(furo.getPosicaoX()) - diametro / 2),
								(int) diametro, (int) diametro);
					}
					
					g2d.setColor(Color.BLACK);
					g2d	.drawOval((int) (Math.round(furo.getPosicaoY()) - diametro/ 2)
							,(int) (Math.round(furo.getPosicaoX()) - diametro / 2),
							(int) diametro, (int) diametro);

					g2d.setColor(new Color(205, 133, 63));
				}
			}
			index++;
		}
	}

	
	public void paintLateralFurosTrail(Graphics g) {

		int index = 0;
		Graphics2D g2d = (Graphics2D) g;
		Furo furo;
		Workingstep wsFuro;
		MachiningOperation operation;
		double profundidade = 0;
		
		Vector<Workingstep> workingsteps = projetoDeSimulacao.getWorkingsteps();

		while (index < workingsteps.size() && index <= workingstepIndex && calculouMov) {

			if (workingsteps.get(index).getFeature().getClass().equals(FuroBasePlana.class)
					|| workingsteps.get(index).getFeature().getClass().equals(FuroBaseConica.class)) {

//				System.out.println("AAAAAAAAAAAAAAMBUER");
				
				Iterator iterator = trailFuros.iterator();

				while(iterator.hasNext()){

					wsFuro = (Workingstep) iterator.next();
					furo = (Furo)wsFuro.getFeature();
					operation = wsFuro.getOperation();
					
					if(operation.getClass()==CenterDrilling.class){
						profundidade = ((CenterDrilling)operation).getCuttingDepth();
					}else if(operation.getClass()==Drilling.class){
						profundidade = ((Drilling)operation).getCuttingDepth();
					}else if(operation.getClass()==Reaming.class){
						profundidade = ((Reaming)operation).getCuttingDepth();	
					}else{
						System.out.println("Operation Desconhecida! (paintLateralFurosTrail) : " + operation.getClass());
					}
					
//					System.out.println("PROFUNDIDADE SIMULADOR : " + profundidade);
					double diametro = wsFuro.getFerramenta().getDiametroFerramenta();

					int sobra = (int) (block.getZ() - profundidade);

					if(SimulationPanel.NEEDPAINT || workingstepIndex==projetoDeSimulacao.getWorkingsteps().size()){
						g2d.setColor(new Color(205, 133, 63));
						g2d.fillRect(VisualTool.UPPERSHIFT + sobra - (int)furo.getPosicaoZ()
								,(int) Math.round(furo.getPosicaoX()- 1 - (int) diametro / 2)
								, (int)profundidade
								, (int) diametro);
					}
					
					g2d.setColor(Color.BLACK);
					g2d.drawRect(VisualTool.UPPERSHIFT + sobra - (int)furo.getPosicaoZ() 
							,(int) Math.round(furo.getPosicaoX()- 1 - (int) diametro / 2)
							, (int)profundidade
							, (int) diametro);
				}

			}
			index++;
		}

	}

	public void paintUpperTrail(Graphics g) {
		Point3d point1, point2;
		float dash1[] = { 3f, 4f };
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		for (int i = 0; i < trailPoints.size(); i++) {

			if (i < (trailPoints.size() - 1)) {
				point1 = trailPoints.get(i);
				point2 = trailPoints.get(i + 1);
				g2d.drawLine((int) point1.getY(), (int) point1.getX(),
						(int) point2.getY(), (int) point2.getX());
			} else {
				point1 = trailPoints.get(i);
				g2d.drawLine((int) point1.getY(), (int) point1.getX(), (int) y,
						(int) x);

			}

		}
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke());

	}

	public void paintLateralTrail(Graphics g) {
		Point3d point1, point2;
		float dash1[] = { 3f, 4f };
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		g2d.setColor(Color.BLUE);
		for (int i = 0; i < trailPoints.size(); i++) {

			if (i < (trailPoints.size() - 1)) {
				point1 = trailPoints.get(i);
				point2 = trailPoints.get(i + 1);
				g2d.drawLine((int) point1.getZ(), (int) point1.getX(),
						(int) point2.getZ(), (int) point2.getX());
			} else {
				point1 = trailPoints.get(i);
//				System.out.println("PINTANDO("+point1.getX()+" , "+point1.getY()+" , "+point1.getZ()+")");
				g2d.drawLine((int) point1.getZ(), (int) point1.getX(), (int) z,
						(int) x);

			}

		}

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke());

	}

	public void changeTool() {
		// TODO Auto-generated method stub
		
	}

}
