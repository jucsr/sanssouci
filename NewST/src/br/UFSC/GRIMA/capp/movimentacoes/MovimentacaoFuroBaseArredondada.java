package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseArredondada 
{
	private Workingstep ws;
	private FuroBaseArredondada furo;
	ArrayList<Path> movimentacaoDesbaste = new ArrayList<Path>();
	
	public MovimentacaoFuroBaseArredondada(Workingstep ws)
	{
		this.ws = ws;
	}
	public ArrayList<Path> desbaste()
	{
		this.furo = (FuroBaseArredondada)this.ws.getFeature();
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
		System.out.println("limiteProfundidade = " + limiteProfundidade);
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
				System.err.println("h = " + h);
				limiteRadial1 = this.furo.getRaio() - this.furo.getR1() + Math.sqrt(this.furo.getR1() * this.furo.getR1() - (this.furo.getR1() - h) * (this.furo.getR1() - h));
				System.err.println("---> limiteRadial = " + limiteRadial1);
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
				System.out.println("raioAtual = " + (raioAtual + diamFerr / 2));
				
				LinearPath horiz = new LinearPath(vertical.getFinalPoint(), new Point3d(vertical.getFinalPoint().x + raioAtual, vertical.getFinalPoint().y, vertical.getFinalPoint().z));
				horiz.setTipoDeMovimento(LinearPath.SLOW_MOV);
				this.movimentacaoDesbaste.add(horiz);
				
				CircularPath anelTmp = new CircularPath(horiz.getFinalPoint(), horiz.getFinalPoint(), raioAtual, (new Point3d(furo.X, furo.Y, zAtual)));
				anelTmp.setAngulo(2 * Math.PI);
				anelTmp.setSense(CircularPath.CCW);
				this.movimentacaoDesbaste.add(anelTmp);
			}
			System.out.println("zatual = " + zAtual);
			LinearPath voltaAoCentro = new LinearPath(this.movimentacaoDesbaste.get(this.movimentacaoDesbaste.size() - 1).getFinalPoint(), new Point3d());
		}
		
		/**
		 *  ===== Parte Arredondada ==
		 */
		
		return this.movimentacaoDesbaste;
	}
	public ArrayList<Path> acabamento()
	{
		ArrayList<Path> movimentacaoAcabamento = new ArrayList<Path>();
		
		return acabamento();
	}
	public static ArrayList<Point3d> interpolateCircularPath(ArrayList<Path> mov)
	{
		ArrayList<Point3d> saida = new ArrayList<Point3d>();
		Point3d pontoInicial = mov.get(0).getInitialPoint();
		saida.add(pontoInicial);
		for(Path pathTmp: mov)
		{
			if(pathTmp.getClass().equals(LinearPath.class))
			{
//				Point3d pontoTmp = new Point3d(pathTmp.getFinalPoint().x, pathTmp.getFinalPoint().y, pathTmp.getFinalPoint().z);
				Point3d pontoTmp = pathTmp.getFinalPoint(); // CUIDADO!!! veja se vai criar um novo ponto
				saida.add(pontoTmp);
			}else if(pathTmp.getClass().equals(CircularPath.class))
			{
				
			}
		}
		return saida;
	}
}
