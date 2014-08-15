package br.UFSC.GRIMA.cam;


import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrocoidalGCode 
{
	private Workingstep ws;
	private int n;
	public GenerateTrocoidalGCode(Workingstep ws, int n)
	{
		this.ws = ws;
		this.n = n;
	}
	public String getGCode()
	{
		MovimentacaoGeneralClosedPocket mov = new MovimentacaoGeneralClosedPocket(this.ws);
//		ArrayList<Path> paths = mov.getDesbasteTrocoidal();
		ArrayList<Path> paths = mov.getDesbasteContourParallel();
		String GCode = "N" + (n + 1 * 10) + "\tG54";
		String sentidoRotacao = " M03";
		if(ws.getFerramenta().getHandOfCut() == Ferramenta.RIGHT_HAND_OF_CUT)
		{
			sentidoRotacao = " M04";
		}
		GCode += "\nN" + (n + 2 * 10) + "\t S" + ws.getCondicoesUsinagem().getN() + " F" + ws.getCondicoesUsinagem().getF() + sentidoRotacao;
		GCode += "\nN" + (n + 3 * 10) + "\tT = " + ws.getFerramenta().getName();
		GCode += "\nN" + (n + 4 * 10) + "\tM06";
		int numeroDeLinha = 0;
		for(int i = 0; i < paths.size(); i++)
		{
			numeroDeLinha = (i + 6) * 10;
			String aux = "";
			Path pathTmp = paths.get(i);
			if(paths.get(i).getClass() == LinearPath.class)
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				if(linearTmp.getTipoDeMovimento() == LinearPath.FAST_MOV)
				{
					aux = "G0 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x,5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y,5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z,5);
				} else if(linearTmp.getTipoDeMovimento() == LinearPath.SLOW_MOV)
				{
					aux = "G1 " + " X" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().x,5) + " Y" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().y,5) + " Z" + GeometricOperations.roundNumber(pathTmp.getFinalPoint().z,5);
				}
			} else if(paths.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
				double I = circularTmp.getCenter().x - circularTmp.getInitialPoint().x;
				double J = circularTmp.getCenter().y - circularTmp.getInitialPoint().y;
				
				if(circularTmp.getAngulo() < 0) // Sentido Horario
				{
					aux = "G2 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x,5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y,5) + " Z" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().z,5) + " I" + GeometricOperations.roundNumber(I,5) + " J" + GeometricOperations.roundNumber(J,5);
				} else // sentido Antihorario
				{
					aux = "G3 " + " X" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().x,5) + " Y" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().y,5) + " Z" + GeometricOperations.roundNumber(circularTmp.getFinalPoint().z,5) + " I" + GeometricOperations.roundNumber(I,5) + " J" + GeometricOperations.roundNumber(J,5);
				}
			}
			GCode += "\nN" + numeroDeLinha + aux;
		}
		return GCode;
	}
}
