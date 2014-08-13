package br.UFSC.GRIMA.cam;


import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;

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
		String GCode = "N" + n + " G54" + "\n";
		GCode += "N20" + " S" + ws.getCondicoesUsinagem().getN() + " F" + ws.getCondicoesUsinagem().getF();
		int numeroDeLinha = 0;
		for(int i = 0; i < paths.size(); i++)
		{
			numeroDeLinha = (i + 2) * 10;
			String aux = "";
			Path pathTmp = paths.get(i);
			if(paths.get(i).getClass() == LinearPath.class)
			{
				LinearPath linearTmp = (LinearPath)pathTmp;
				if(linearTmp.getTipoDeMovimento() == LinearPath.FAST_MOV)
				{
					aux = "G0 " + " X" + pathTmp.getFinalPoint().x + " Y" + pathTmp.getFinalPoint().y + " Z" + pathTmp.getFinalPoint().z;
				} else if(linearTmp.getTipoDeMovimento() == LinearPath.SLOW_MOV)
				{
					aux = "G1 " + " X" + pathTmp.getFinalPoint().x + " Y" + pathTmp.getFinalPoint().y + " Z" + pathTmp.getFinalPoint().z;
				}
			} else if(paths.get(i).getClass() == CircularPath.class)
			{
				CircularPath circularTmp = (CircularPath)pathTmp;
				double I = circularTmp.getInitialPoint().x - circularTmp.getCenter().x;
				double J = circularTmp.getInitialPoint().y - circularTmp.getCenter().y;
				
				if(circularTmp.getAngulo() < 0) // Sentido Horario
				{
					aux = "G2 " + " X" + circularTmp.getFinalPoint().x + " Y" + circularTmp.getFinalPoint().y + " Z" + circularTmp.getFinalPoint().z + " I" + I + " J" + J;
				} else // sentido Antihorario
				{
					aux = "G3 " + " X" + circularTmp.getFinalPoint().x + " Y" + circularTmp.getFinalPoint().y + " Z" + circularTmp.getFinalPoint().z + " I" + I + " J" + J;
				}
			}
			GCode += "\nN" + numeroDeLinha + aux;
		}
		return GCode;
	}
}
