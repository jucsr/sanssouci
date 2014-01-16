package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.xml.LateBindingReader;

import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseEsferica 
{
	private Workingstep ws;
	private ArrayList<Path> trajetorias = new ArrayList<Path>();
	public MovimentacaoFuroBaseEsferica(Workingstep ws)
	{
		this.ws = ws;
	}
	public ArrayList<Path> getVetorDeMovimentacao()
	{
		FuroBaseEsferica featureTmp = (FuroBaseEsferica)this.ws.getFeature();
		double apUtilizada, aeUtilizada, zAtual;
		Point3d initialPoint = new Point3d(featureTmp.X, featureTmp.Y, this.ws.getOperation().getRetractPlane());
		Point3d finalPoint = new Point3d(featureTmp.X, featureTmp.Y, - featureTmp.Z); // descendo z = 0 ou negativo
		zAtual = -featureTmp.Z;
		boolean terminouZ = false, terminouXY = false;
		boolean saindo = true, entrando = false;
		double limiteInterior = ws.getOperation().getStartPoint().x;
		double limiteExterior = featureTmp.getRaio()  - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide();
		
		// movimento de aproximacao
		LinearPath aprox = new LinearPath(initialPoint, finalPoint);
		aprox.setTipoDeMovimento(LinearPath.FAST_MOV);
		this.trajetorias.add(aprox);
		
		//---- passadas no nivel Z
		while(!terminouZ)
		{	 
			//determinar o ap
			if(/*-featureTmp.Z - featureTmp.getProfundidade() <= zAtual && */Math.abs(-featureTmp.Z - featureTmp.getProfundidade() - zAtual) > this.ws.getCondicoesUsinagem().getAp())
			{
				apUtilizada = this.ws.getCondicoesUsinagem().getAp();
			} else
			{
				apUtilizada = featureTmp.Z + featureTmp.getProfundidade() + zAtual ;
				terminouZ = true;
			}
			//--- descer em Z apUtilizada ---- se nao se setta o tipo de linearPath fica por default é LOW_MOV
			LinearPath descendo = new LinearPath(new Point3d(aprox.getFinalPoint().x, aprox.getFinalPoint().y, zAtual), new Point3d(aprox.getFinalPoint().x, aprox.getFinalPoint().y, zAtual - apUtilizada));

			this.trajetorias.add(descendo);
//			System.out.println("ApUtilizada = " + apUtilizada);
			zAtual = zAtual - apUtilizada;
			System.out.println("------------------------------\nzatual = " + zAtual);
			
			//--- avancos laterais
			
			//===== movimentar até o ponto de inicio da usinagem lentamente
			
			double xyAtual = 0; //--- valor relativo ao centro do furo
			LinearPath inicioLateral = new LinearPath(new Point3d(descendo.getFinalPoint()), new Point3d(descendo.getFinalPoint().x + limiteInterior, descendo.getFinalPoint().y, descendo.getFinalPoint().z));
			this.trajetorias.add(inicioLateral); // verificar se precisa (depois)
		
			xyAtual =  ws.getOperation().getStartPoint().x;
			//--- calcular o raio para a interpolacao circular
			Path circular = this.determinarPathCircular(new Point3d(featureTmp.X, featureTmp.Y, zAtual), xyAtual);//Math.sqrt(limiteInterior * limiteInterior - (-zAtual - featureTmp.Z) * (-zAtual - featureTmp.Z)));
			this.trajetorias.add(circular);
			
//			System.out.println("xyAtual = "+  xyAtual);
//			System.out.println("MMM = "+  (-zAtual - featureTmp.Z));
			System.out.println("conta = " + (limiteExterior - ws.getFerramenta().getDiametroFerramenta()/2 - xyAtual));
			terminouXY = false;
			while(!terminouXY)
			{
				//--- determinar ae
				if(limiteExterior - ws.getFerramenta().getDiametroFerramenta()/2 - xyAtual > ws.getCondicoesUsinagem().getAe())
				{
					aeUtilizada = ws.getCondicoesUsinagem().getAe();
					System.out.println("XY ");
				} else
				{
					aeUtilizada = limiteExterior - xyAtual - ws.getFerramenta().getDiametroFerramenta()/2;
					terminouXY = true;
				}
				System.out.println("aeUtilizada = " + (ws.getCondicoesUsinagem().getAe()));
				xyAtual = xyAtual + aeUtilizada;
				System.out.println("xyAtual = " + xyAtual);
				Path cpath = this.determinarPathCircular(new Point3d(featureTmp.X, featureTmp.Y, zAtual), xyAtual);
				this.trajetorias.add(cpath);
			}
		}
		return this.trajetorias;
	}
	private Path determinarPathCircular(Point3d center, double raio)
	{
		Point3d initialPoint = new Point3d(center.x + raio, center.y, center.z), finalPoint = new Point3d(initialPoint);
		CircularPath saida = new CircularPath(initialPoint, finalPoint, center);
		saida.setSense(CircularPath.CCW);
		return saida;
	}
}
