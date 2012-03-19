package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.util.Ponto;

public class Camada
{
	Vector workingstepsCamada;// vetor de workinsteps de uma camada
	int tempoTrabalho; // tempo total de usnagem das features numa camada
	int [][] sequencias; // todas as sequencias possiveis de usinagem da camada
	int [][] tempoVazio; // tempo de movimentacao em vazio entre workinsteps
	Ponto pontoInicial; // ponto inicial de usinagem da camada
	Ponto [] pontofinal; // ponto final da camada
	
	public Camada(Vector workinstepsCamada)
	{
		this.workingstepsCamada = workinstepsCamada;
	}
	public int calcularTempoVazio(Vector workingstepCamada)
	{
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < workingstepCamada.size(); i++)
		{
			Workingstep wsTmp = (Workingstep)workingstepCamada.elementAt(i);
			if (x == 0 && y == 0 && z ==0)
			{
				x = wsTmp.getPontoFinal().getX();
				y = wsTmp.getPontoFinal().getY();
				z = wsTmp.getPontoFinal().getZ();
			}
		}
		return -1;
	}
}
