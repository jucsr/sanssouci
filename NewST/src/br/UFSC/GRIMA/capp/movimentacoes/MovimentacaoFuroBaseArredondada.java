package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseArredondada 
{
	private Workingstep ws;
	private FuroBaseArredondada furo;
	private ArrayList<Path> movimentacaoDesbaste = new ArrayList<Path>();
	
	public MovimentacaoFuroBaseArredondada(Workingstep ws)
	{
		this.ws = ws;
		this.furo = (FuroBaseArredondada)this.ws.getFeature();
	}
	public ArrayList<Path> desbaste()
	{
		
		/**
		 *  ===== Parte cilindrica == 
		 */
		
		double zAtual = -this.furo.Z, raioAtual = 0;
		double apUtilizado = 0, aeUtilizado = 0;
		double limiteProfundidade = this.furo.getProfundidade();
		boolean terminouZ = false, terminouXY = false;
		double limiteRadial0 = 0; // verificar de onde deverá comecar a usinagem radial
		double limiteRadial1 = this.furo.getRaio() - ((BottomAndSideRoughMilling)this.ws.getOperation()).getAllowanceSide();
		double diamFerr = this.ws.getFerramenta().getDiametroFerramenta();
		double h;
		
		Point3d pontoInicial = new Point3d(this.furo.X, this.furo.Y, this.ws.getOperation().getRetractPlane());
		Point3d p1 = new Point3d(this.furo.X, this.furo.Y, zAtual);
		LinearPath path1 = new LinearPath(pontoInicial, p1);
		path1.setTipoDeMovimento(LinearPath.FAST_MOV);
		this.movimentacaoDesbaste.add(path1);
		
		
		if(this.ws.getFerramenta().getDiametroFerramenta() <= this.furo.getDiametro() - 2 * this.furo.getR1())
		{
			limiteProfundidade = this.furo.getProfundidade() + this.furo.getR1();
		} else
		{
			limiteProfundidade = this.furo.getProfundidade() + Math.sqrt(this.furo.getR1() * this.furo.getR1() - (this.ws.getFerramenta().getDiametroFerramenta() / 2 - (this.furo.getRaio() - this.furo.getR1())) * (this.ws.getFerramenta().getDiametroFerramenta() / 2 - (this.furo.getRaio() - this.furo.getR1())));
		}
//		System.out.println("limiteProfundidade = " + limiteProfundidade);
		while(!terminouZ)
		{
			terminouXY = false;
			raioAtual = 0;
			if((this.furo.Z + limiteProfundidade) + zAtual > this.ws.getCondicoesUsinagem().getAp())
			{
				apUtilizado = this.ws.getCondicoesUsinagem().getAp();
			} else
			{
//				apUtilizado = this.furo.Z + this.furo.getProfundidade() + zAtual;
				apUtilizado = this.furo.Z + limiteProfundidade + zAtual;
				terminouZ = true;
			}
			zAtual = zAtual - apUtilizado;
			LinearPath vertical = new LinearPath(path1.getFinalPoint(), new Point3d(path1.getFinalPoint().x, path1.getFinalPoint().y, zAtual));
			if(limiteRadial0 < diamFerr / 2)
			{
				vertical.setTipoDeMovimento(LinearPath.SLOW_MOV);//verificar se eh rapido ou lento (caso naum inicie no centro do circulo, pois jah pode ter sido pre-furado)
			}
			else
			{
				vertical.setTipoDeMovimento(LinearPath.FAST_MOV);
			}
			if(zAtual >= -(this.furo.Z + this.furo.getProfundidade())) // ===== parte cilindrica ======
			{
				limiteRadial1 = this.furo.getRaio() - ((BottomAndSideRoughMilling)this.ws.getOperation()).getAllowanceSide();
			} else 					// ======= parte arredondada ======
			{
				h = this.furo.Z + this.furo.getProfundidade() + this.furo.getR1() + zAtual;
//				System.err.println("h = " + h);
				limiteRadial1 = this.furo.getRaio() - this.furo.getR1() + Math.sqrt(this.furo.getR1() * this.furo.getR1() - (this.furo.getR1() - h) * (this.furo.getR1() - h));
//				System.err.println("---> limiteRadial = " + limiteRadial1);
			}
			while(!terminouXY)
			{
				// iniciar desde o centro
				if(limiteRadial1 - raioAtual - diamFerr / 2 > this.ws.getCondicoesUsinagem().getAe())
				{
					aeUtilizado = this.ws.getCondicoesUsinagem().getAe();
				} else
				{
					aeUtilizado = limiteRadial1 - raioAtual - diamFerr / 2;
					terminouXY = true;
				}
				raioAtual = raioAtual + aeUtilizado;
//				System.out.println("raioAtual = " + (raioAtual + diamFerr / 2));
				
				LinearPath horiz = new LinearPath(vertical.getFinalPoint(), new Point3d(vertical.getFinalPoint().x + raioAtual, vertical.getFinalPoint().y, vertical.getFinalPoint().z));
				horiz.setTipoDeMovimento(LinearPath.SLOW_MOV);
				this.movimentacaoDesbaste.add(horiz);
				
				CircularPath anelTmp = new CircularPath(horiz.getFinalPoint(), horiz.getFinalPoint(), (new Point3d(furo.X, furo.Y, zAtual)));
				anelTmp.setAngulo(2 * Math.PI);
				anelTmp.setSense(CircularPath.CCW);
				this.movimentacaoDesbaste.add(anelTmp);
			}
//			System.out.println("zatual = " + zAtual);
			LinearPath voltaAoCentro = new LinearPath(this.movimentacaoDesbaste.get(this.movimentacaoDesbaste.size() - 1).getFinalPoint(), new Point3d(this.movimentacaoDesbaste.get(this.movimentacaoDesbaste.size() - 1).getFinalPoint().x, this.movimentacaoDesbaste.get(this.movimentacaoDesbaste.size() - 1).getFinalPoint().y ,zAtual));
			this.movimentacaoDesbaste.add(voltaAoCentro);
		}
		
		/**
		 *  ===== Parte Arredondada ==
		 */

		return this.movimentacaoDesbaste;
	}
	public ArrayList<Path> movimentacaoFuracao()
	{
		ArrayList<Path> mov = new ArrayList<Path>();
		MachiningOperation operation = this.ws.getOperation();
		
		if (operation.getClass().equals(Drilling.class))
		{
			double zAtual = 0;
			double x = furo.X, y = furo.Y;
			double profundidadeDeQuedra = 4;
			double alturaDeQuebra = 2;
			double profundidadeDeQuebraUsada = profundidadeDeQuedra;
			double limiteProfundidade;
			if(((Drilling)operation).getCuttingDepth() != 0)
				limiteProfundidade = ((Drilling)operation).getCuttingDepth();
			else
				limiteProfundidade = this.furo.getProfundidade() + this.furo.getR1();
			
			boolean terminouZ = false;
			Point3d pontoInicial = new Point3d(x, y, operation.getRetractPlane());
			Point3d descendo = new Point3d(x, y, -this.furo.Z + this.ws.getOperation().getStartPoint().z);
			LinearPath init = new LinearPath(pontoInicial, descendo);
			zAtual = init.getFinalPoint().z;
			init.setTipoDeMovimento(LinearPath.FAST_MOV);
			mov.add(init);
			
			// movimentacao pica-pau
			while(!terminouZ)
			{
				if(zAtual - profundidadeDeQuedra > -(this.furo.Z + limiteProfundidade))
				{
					profundidadeDeQuebraUsada = profundidadeDeQuedra;
				} else
				{
					profundidadeDeQuebraUsada = this.furo.Z + limiteProfundidade + zAtual;
					terminouZ = true;
				}
				zAtual = zAtual - profundidadeDeQuebraUsada;
				LinearPath verticalDescendo = new LinearPath(new Point3d(x, y, mov.get(mov.size() - 1).getFinalPoint().z), new Point3d(x, y, zAtual));
				verticalDescendo.setTipoDeMovimento(LinearPath.SLOW_MOV);
				mov.add(verticalDescendo);
				// subir um pouco
				zAtual = zAtual + alturaDeQuebra;
				LinearPath verticalSubindo = new LinearPath(new Point3d(x, y, mov.get(mov.size() - 1).getFinalPoint().z), new Point3d(x, y, zAtual));
				verticalSubindo.setTipoDeMovimento(LinearPath.FAST_MOV);
				mov.add(verticalSubindo);
//				System.out.println("ZATUAL = " + zAtual);
			}
			LinearPath verticalFinal = new LinearPath(new Point3d(x, y, mov.get(mov.size() - 1).getFinalPoint().z), new Point3d(x, y, operation.getRetractPlane()));
			verticalFinal.setTipoDeMovimento(LinearPath.FAST_MOV);
			mov.add(verticalFinal);
		}
		return mov;
	}
	public ArrayList<Path> movimentacaoCenterDrilling()
	{
		ArrayList<Path> mov = new ArrayList<Path>();
		
		
		if (this.ws.getOperation().getClass().equals(CenterDrilling.class))
		{
			CenterDrilling operation = (CenterDrilling)this.ws.getOperation();
			Point3d initialPoint = new Point3d(furo.X, furo.Y, - (operation.getRetractPlane()));
			Point3d point2 = new Point3d(furo.X, furo.Y, - (furo.Z + operation.getStartPoint().z));
			LinearPath approach = new LinearPath(initialPoint, point2);
			approach.setTipoDeMovimento(LinearPath.FAST_MOV);
			mov.add(approach);
			
			Point3d finalPoint = new Point3d(furo.X, furo.Y, -(furo.Z + operation.getCuttingDepth()));
			LinearPath penetration = new LinearPath(point2, finalPoint);
			mov.add(penetration);
			
			LinearPath exit = new LinearPath(penetration.getFinalPoint(), new Point3d(furo.X, furo.Y, operation.getRetractPlane()));
			mov.add(exit);
		} 
		return mov;
	}
	public ArrayList<Path> movimentacaoBoring()
	{
		ArrayList<Path> mov = new ArrayList<Path>();
		
		
		if (this.ws.getOperation().getClass().equals(Boring.class))
		{
			Boring operation = (Boring)this.ws.getOperation();
			Point3d initialPoint = new Point3d(furo.X, furo.Y, - (operation.getRetractPlane()));
			Point3d point2 = new Point3d(furo.X, furo.Y, - (furo.Z + operation.getStartPoint().z));
			LinearPath approach = new LinearPath(initialPoint, point2);
			approach.setTipoDeMovimento(LinearPath.FAST_MOV);
			mov.add(approach);
			
			Point3d finalPoint = new Point3d(furo.X, furo.Y, -(furo.Z + operation.getCuttingDepth()));
			LinearPath penetration = new LinearPath(point2, finalPoint);
			mov.add(penetration);
			
			LinearPath exit = new LinearPath(penetration.getFinalPoint(), new Point3d(furo.X, furo.Y, operation.getRetractPlane()));
			mov.add(exit);
		} 
		return mov;
	}
	public ArrayList<Path> operacaoComBullnoseEndMill()
	{
		ArrayList<Path> mov = new ArrayList<Path>();
		BullnoseEndMill ferramenta = (BullnoseEndMill)this.ws.getFerramenta();
		double zAtual = this.ws.getOperation().getRetractPlane();
		double zInicial = -(furo.Z + furo.getProfundidade() + ferramenta.getEdgeRadius());
		double apUtilizado = this.ws.getCondicoesUsinagem().getAp();
		double limiteProfundidade = furo.getProfundidade() + furo.getR1();
		Point3d pontoInicial = new Point3d(furo.X, furo.Y, zAtual);
		Point3d ponto1 = new Point3d(furo.X, furo.Y, zInicial);
		zAtual = zInicial;
		LinearPath desce = new LinearPath(pontoInicial, ponto1);
		desce.setTipoDeMovimento(LinearPath.FAST_MOV);
		mov.add(desce);
		Point3d ponto2 = new Point3d(furo.X + furo.getRaio() - ferramenta.getDiametroFerramenta() / 2, furo.Y, zAtual);
		LinearPath lateral = new LinearPath(ponto1, ponto2);
		lateral.setTipoDeMovimento(LinearPath.SLOW_MOV);
		mov.add(lateral);
		boolean terminouZ = false;
		double radiusTmp = 0, alfatmp;
		while(!terminouZ)
		{
			if(furo.Z + limiteProfundidade > -zAtual + ws.getCondicoesUsinagem().getAp())
			{
				apUtilizado = ws.getCondicoesUsinagem().getAp();
			} else
			{
				apUtilizado = furo.Z + limiteProfundidade + zAtual;
				terminouZ = true;
			}
			zAtual = zAtual - apUtilizado;
			alfatmp = Math.asin((-zAtual - furo.getProfundidade() - ferramenta.getEdgeRadius()) / (furo.getR1() - ferramenta.getEdgeRadius()));
//			radiusTmp = furo.getRaio() - Math.tan(Math.asin((furo.getR1() - limiteProfundidade + zAtual - ferramenta.getEdgeRadius()) / (furo.getR1() - ferramenta.getEdgeRadius()))) * apUtilizado;			
			radiusTmp = furo.getRaio() - furo.getR1() + furo.getR1() * Math.cos(alfatmp) - ferramenta.getEdgeRadius() * Math.cos(alfatmp) - (ferramenta.getDiametroFerramenta() / 2 - ferramenta.getEdgeRadius());
		
			Point3d desce0 = new Point3d(mov.get(mov.size() - 1).getFinalPoint().x, mov.get(mov.size() - 1).getFinalPoint().y,mov.get(mov.size() - 1).getFinalPoint().z);
			Point3d desce1 = new Point3d(furo.X + radiusTmp, mov.get(mov.size() - 1).getFinalPoint().y, zAtual);
			LinearPath vert = new LinearPath(desce0, desce1);
			vert.setTipoDeMovimento(LinearPath.SLOW_MOV);
			mov.add(vert);
			
//			System.out.println("ZAtual = " + zAtual);
			CircularPath anel = new CircularPath(mov.get(mov.size() - 1).getFinalPoint(), mov.get(mov.size() - 1).getFinalPoint(), new Point3d(furo.X, furo.Y, zAtual));
			anel.setSense(CircularPath.CCW);
			anel.setAngulo(2 * Math.PI);
			mov.add(anel);
//			System.out.println("radiusTmp = " + radiusTmp);
//			System.out.println("alfaTmp = " + (alfatmp * 180 / Math.PI));
			
			
		}
//		System.out.println("pontofinal = " + mov.get(mov.size() - 1).getFinalPoint());
		return mov;
	}
	public static Vector<Point3d> transformCircularPathInPoints3d(ArrayList<Path> mov)
	{
		Vector<Point3d> saida = new Vector<Point3d>();
		Point3d pontoInicial = mov.get(0).getInitialPoint();
		saida.add(pontoInicial);
		for(Path pathTmp: mov)
		{
			if(pathTmp.getClass().equals(LinearPath.class))
			{
				Point3d pontoTmp = new Point3d(pathTmp.getFinalPoint().x, pathTmp.getFinalPoint().y, pathTmp.getFinalPoint().z); 
				saida.add(pontoTmp);
			}else if(pathTmp.getClass().equals(CircularPath.class))
			{
				ArrayList<Point3d> interpolacao = interpolarCircularPath((CircularPath)pathTmp, 13);
				{
					for(int i = 0; i < interpolacao.size(); i++)
					{
						saida.add(interpolacao.get(i));
					}
				}
			}
		}
		return saida;
	}
	public static ArrayList<Point3d> interpolarCircularPath(CircularPath path, int numeroDePontosNaInterpolacao)
	{
		ArrayList<Point3d> saida = new ArrayList<Point3d>();
		double deltaAngulo = 0;
		if(path.getSense() == CircularPath.CCW)
			deltaAngulo = path.getAngulo() / (numeroDePontosNaInterpolacao - 1);
		else
			deltaAngulo = -path.getAngulo() / (numeroDePontosNaInterpolacao - 1);;
		double anguloTmp = path.getinicialAngle() + deltaAngulo;
		double x, y, z;
		saida.add(path.getInitialPoint());
		for(int i = 1; i < numeroDePontosNaInterpolacao - 1; i++)
		{
			x = path.getCenter().x + path.getRadius() * Math.cos(anguloTmp);
			y = path.getCenter().y + path.getRadius() * Math.sin(anguloTmp);
			z = path.getCenter().z;
			saida.add(new Point3d(x, y, z));
			anguloTmp = anguloTmp + deltaAngulo;
		}
		saida.add(path.getFinalPoint());
		return saida;
	}
//	private static Vector<Point3d> interpolarCircularPath(CircularPath path, int numeroDePontosNaInterpolacao)
//	{
//		Vector<Point3d> saida = new Vector<Point3d>();
//		double delytAngulo = path.getAngulo() / numeroDePontosNaInterpolacao;
//		double anguloTmp = path.getAngulo();
//		double x, y, z;
//		for(int i = 0; i < numeroDePontosNaInterpolacao; i++)
//		{
//			x = path.getCenter().x +  path.getCenter().x * Math.cos(anguloTmp);
//			anguloTmp = anguloTmp + delytAngulo;
//		}
//		return saida;
//	}
	
	public static ArrayList<ArrayList<Point3d>> getTrechos(Point3d[] pontosNaCurva, double nivelZ)
	{
		ArrayList<ArrayList<Point3d>> saida = new ArrayList<ArrayList<Point3d>>();
		ArrayList<Point3d> trechoTmp = null;
		int j = 0;
		int k = 0;
		for(int i = 0; i < pontosNaCurva.length; i++)
		{
//			System.out.println("J = " + j);
			Point3d pontoTmp = pontosNaCurva[i];
			if(pontoTmp.z <= nivelZ)
			{
				if(j == 0)
				{
					trechoTmp = new ArrayList<Point3d>();
//					System.err.println("CRIAR");
					trechoTmp.add(pontoTmp);
					saida.add(trechoTmp);

				} 
				else if(j > 0)
				{
					trechoTmp.add(pontoTmp);
				}
				j++;
			} else
			{
				j = 0;
			}
		}
		
//		for(int i = 0; i < pontosNaCurva.length; i++)
//		{
//			Point3d pontoTmp = pontosNaCurva[i];
//			if(pontoTmp.z <= nivelZ)
//			{
//				if(k == 0)
//				saida.get(k).add(pontoTmp);
//			}
//		}
		return saida;
	}
	public static ArrayList<LinearPath> getTrechos(ArrayList<ArrayList<Point3d>> trechos)
	{
		ArrayList<LinearPath> saida = new ArrayList<LinearPath>();
	
		for(int i = 0; i < trechos.size(); i++)
		{
			Point3d pontoInicialTmp = trechos.get(i).get(0);
			Point3d pontoFinalTmp = trechos.get(i).get(trechos.get(i).size() - 1);
			LinearPath linear = new LinearPath(pontoInicialTmp, pontoFinalTmp);
			saida.add(linear);
		}
		return saida;
	}
}
