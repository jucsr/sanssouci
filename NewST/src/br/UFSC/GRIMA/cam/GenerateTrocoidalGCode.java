package br.UFSC.GRIMA.cam;


import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrocoidalGCode 
{
	private Workingstep ws;
	private int n;
	private MovimentacaoGeneralClosedPocket mov;
	private ArrayList<Path> paths;
	private ArrayList<ArrayList<ArrayList<LimitedElement>>> baseLines;
	public GenerateTrocoidalGCode(Workingstep ws, int n)
	{
		this.ws = ws;
		this.n = n;
		this.mov = new MovimentacaoGeneralClosedPocket(this.ws);
	}
	/**
	 *  pro matheus
	 * @param path
	 * @return
	 */
	public static String transformPathToGCode(ArrayList<Path> path)
	{
		int n = 0;
		String GCode = "\nN" + (n + 1 * 10);
		GCode += "\nN" + (n + 2 * 10) + "\tG54";
		String sentidoRotacao = " M03";
		if(false)
		{
			sentidoRotacao = " M04";
		}
		GCode += "\nN" + (n + 3 * 10) + "\tS" + 1000 + "\tF" + 0.01;
		GCode += "\nN" + (n + 4 * 10) + "\tT1" ;
		GCode += "\nN" + (n + 5 * 10) + "M06" + sentidoRotacao;
//		int numeroDeLinha = 0;
		
		GCode += "\nN"+ (n + 6 * 10) + "G0 " + " X" + GeometricOperations.roundNumber(path.get(0).getInitialPoint().x, 5) + " Y" + GeometricOperations.roundNumber(path.get(0).getInitialPoint().y, 5) + " Z" + GeometricOperations.roundNumber(path.get(0).getInitialPoint().z, 5);
		for(int i = 0; i < path.size(); i++)
		{
//			numeroDeLinha = (i + 6) * 10;
			n = (i + 11) * 10;
			String aux = "";
			Path pathTmp = path.get(i);
			if(path.get(i).getClass() == LinearPath.class)
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				if(linearTmp.getTipoDeMovimento() == LinearPath.FAST_MOV)
				{
					aux = "G0 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y, 5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z, 5);
				} else if(linearTmp.getTipoDeMovimento() == LinearPath.SLOW_MOV)
				{
					aux = "G1 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y, 5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z, 5);
				}
			} else if(path.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
				double I = circularTmp.getCenter().x - circularTmp.getInitialPoint().x;
				double J = circularTmp.getCenter().y - circularTmp.getInitialPoint().y;
				
				if(circularTmp.getAngulo() < 0) // Sentido Horario
				{
					aux = "G2 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y, 5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z, 5) + " I" + GeometricOperations.roundNumber(I, 5) + " J" + GeometricOperations.roundNumber(J, 5);
				} else // sentido Antihorario
				{
					aux = "G3 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y,5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z, 5) + " I" + GeometricOperations.roundNumber(I, 5) + " J" + GeometricOperations.roundNumber(J, 5);
				}
			}
//			GCode += "\nN" + numeroDeLinha + aux;
			GCode += "\nN" + n + aux;
		}
		GCode += "\nN" + (n + 1 * 10) + "M30";
		return GCode;
	}
	
	public String getGCode()
	{
		System.out.println("Diameter: " + ws.getFerramenta().getDiametroFerramenta());
		if(ws.getOperation().getMachiningStrategy().getClass() == TrochoidalAndContourParallelStrategy.class)
		{
			this.paths = mov.getDesbasteTrocoidal();                 //Array de paths (caminhos da ferramenta)
		}
		else if(ws.getOperation().getMachiningStrategy().getClass() == ContourParallel.class)
		{
			this.paths = mov.getDesbasteContourParallel();
		}
		this.baseLines = mov.getMultipleLimitedElements();       //Array de linhas guia
//		System.err.println("PATHS = " + baseLines);
//		System.out.println("PATHS = " + paths.size());
//		System.out.println("elements = " + mov.getMultipleLimitedElements().size());
		String GCode = "\nN" + (n + 1 * 10) + "\t; Feature -->" + ws.getFeature().getNome() + "\t WS --> " + ws.getOperation().getOperationType();
		GCode += "\nN" + (n + 2 * 10) + "\tG54";
		String sentidoRotacao = " M03";
		if(ws.getFerramenta().getHandOfCut() == Ferramenta.RIGHT_HAND_OF_CUT)
		{
			sentidoRotacao = " M04";
		}
		GCode += "\nN" + (n + 3 * 10) + "\tS" + ws.getCondicoesUsinagem().getN() + " F" + ws.getCondicoesUsinagem().getF();
		GCode += "\nN" + (n + 4 * 10) + "\tT" + ws.getFerramenta().getName();
		GCode += "\nN" + (n + 5 * 10) + "\tM06" + sentidoRotacao;
//		int numeroDeLinha = 0;
		
		GCode += "\nN" + (n + 6 * 10) + "R2 = -" + ((GeneralClosedPocket)ws.getFeature()).getProfundidade();
		GCode += "\nN" + (n + 7 * 10) + "R1 = 0";
		GCode += "\nN" + (n + 8 * 10) + "LABEL 1:";
		GCode +=  "\nN" + (n + 9 * 10) + "R0 = R1 + " + ws.getCondicoesUsinagem().getAp();
		GCode +=  "\nN" + (n + 10 * 10) + "R1 = R1 - " + ws.getCondicoesUsinagem().getAp();
		for(int i = 0; i < paths.size(); i++)
		{
//			numeroDeLinha = (i + 6) * 10;
			n = (i + 11) * 10;
			String aux = "";
			Path pathTmp = paths.get(i);
			if(paths.get(i).getClass() == LinearPath.class)
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				if(linearTmp.getTipoDeMovimento() == LinearPath.FAST_MOV)
				{
					aux = "G0 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y, 5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z, 5);
				} else if(linearTmp.getTipoDeMovimento() == LinearPath.SLOW_MOV)
				{
					aux = "G1 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y, 5) + " Z = R1";
				}
			} else if(paths.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
				double I = circularTmp.getCenter().x - circularTmp.getInitialPoint().x;
				double J = circularTmp.getCenter().y - circularTmp.getInitialPoint().y;
				
				if(circularTmp.getAngulo() < 0) // Sentido Horario
				{
					aux = "G2 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y, 5) + " Z = R1" + " I" + GeometricOperations.roundNumber(I, 5) + " J" + GeometricOperations.roundNumber(J, 5);
				} else // sentido Antihorario
				{
					aux = "G3 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x, 5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y,5) + " Z = R1" + " I" + GeometricOperations.roundNumber(I, 5) + " J" + GeometricOperations.roundNumber(J, 5);
				}
			}
//			GCode += "\nN" + numeroDeLinha + aux;
			GCode += "\nN" + n + aux;
		}
		GCode += "\nN" + (n + 1 * 10) + "IF R1>R2 GOTOB LABEL1";
		return GCode;
	}
	/**
	 * 
	 * @return - o array de lacos do multipleParallel
	 */
	public ArrayList<ArrayList<ArrayList<LimitedElement>>> getMultipleLimitedElements()
	{
		return this.baseLines;
	}
	/**
	 *  linhas guia do caminho trocoidal
	 * @return
	 */
	public ArrayList<ArrayList<ArrayList<LimitedElement>>> getBaseLines() 
	{
		return baseLines;
	}
	public ArrayList<Path> getPaths()
	{
		return paths;
	}
}
