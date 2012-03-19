package br.UFSC.GRIMA.util;

import java.util.Vector;

import br.UFSC.GRIMA.capp.Workingstep;


public class DeterminadorDeTempo 
{
	public static Vector pontosMovimentacao = new Vector();
	public static Vector movimentacao = new Vector();
	public DeterminadorDeTempo()
	{
	}
	public static int determinarTempo(Workingstep ws)
	{
		int tempoTotal = 0;
		Ponto ponto1, ponto2;
		double x1, y1, z1, x2, y2, z2;
		double percorrido = 0;
		movimentacao = ws.getPontosMovimentacao();
		if (ws.getPontosMovimentacao() == null)
		{
			System.out.println("pontosMovimentacao == NULO");
		}
		else
		{
			System.out.println("Movimentacao.tamanho = " + ws.getPontosMovimentacao().size());
			for(int i = 0; i < 1; i++)
			{
				pontosMovimentacao = (Vector)movimentacao.elementAt(i);
				for(int j = 0; j < pontosMovimentacao.size() - 1; j++)
				{
					ponto1 = (Ponto)pontosMovimentacao.elementAt(j);
					ponto2 = (Ponto)pontosMovimentacao.elementAt(j + 1);
					x1 = ponto1.getX();
					y1 = ponto1.getY();
					z1 = ponto1.getZ();
					x2 = ponto2.getX();
					y2 = ponto2.getY();
					z2 = ponto2.getZ();
					percorrido += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
					
				}
			}
			System.out.println("percorrido: " + percorrido);
			System.out.println("pontosMovimentacao == " + pontosMovimentacao.size());
			System.out.println("percorrido = " + percorrido);
			tempoTotal = (int)Math.round(percorrido / ws.getCondicoesUsinagem().getVf());
			System.out.println("TempoTotal = " + tempoTotal);
		}
		return tempoTotal;
	}
}