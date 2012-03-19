package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.util.Ponto;


public class DeterminarSequenciasUsinagem 
{
	Vector workingsteps;
	public DeterminarSequenciasUsinagem()
	{
		
	}
	/**
	 * 
	 * @param workingsteps -> ws que deve ser ordenado de acordo com o menor tempo de usinagem
	 * @return -> Vetor de WS j� ordenado de acordo com o menor tempo de usinagem
	 */
	public Vector gerarSeguencia1(Vector workingsteps, Ponto origemAtual)
	{
		Vector saida = new Vector();
		Vector gruposDistancias = this.gruposDistancias(workingsteps, origemAtual);
		Vector menoresDistancias = new Vector();
		
		for(int i = 0; i < gruposDistancias.size(); i++)
		{
			Vector grupo = (Vector)gruposDistancias.elementAt(i);
			for(int j = 0; j < grupo.size(); j ++)
			{
				double distanciaMenor = 0;
				double distanciaTmp = (Double)grupo.elementAt(j);
				if (j == 0)
				{
					distanciaMenor = distanciaTmp;
				}
				else
				{
					if(distanciaTmp < distanciaMenor)
					{
						distanciaMenor = distanciaTmp;
					}
				}
				menoresDistancias.add(distanciaMenor);
			}
		}
		double menor = 0;
		int indice = 0;
		for(int i = 0; i < menoresDistancias.size(); i++)
		{
			double menorTmp = (Double)menoresDistancias.elementAt(i);
			if(i == 0)
			{
				indice = 0;
				menor = menorTmp;
			}
			else
			{
				if(menorTmp < menor)
				{
					indice = i;
					menor = menorTmp;
				}
			}
		}
		
		for(int i = 0; i < gruposDistancias.size(); i++)
		{
			Vector grupo = (Vector)gruposDistancias.elementAt(i);
			for (int j = 0; j < grupo.size(); j++)
			{
				double distanciaTmp = (Double)grupo.elementAt(j);
				if(distanciaTmp == menor)
				{
					saida.add(workingsteps.elementAt(indice));
					this.workingsteps.removeElementAt(indice);
				}
			}
		}
		return saida;
	}
	 /**
	  * 
	  * @param workingsteps -> ws as quais devem ser calculadas as distancias
	  * @param origemAtual -> distancias calculadas relativas este ponto
	  * @return -> Vetor de grupos de distancias
	  */
	private Vector gruposDistancias(Vector workingsteps, Ponto origemAtual)
	{
		// Vetor q contem os vetores de distancias de cada ws
		Vector gruposDistancias = new Vector();
		for (int i = 0; i < workingsteps.size(); i++)
		{
			Vector distanciasWS = new Vector(); 
			Workingstep wsTmp = (Workingstep)workingsteps.elementAt(i);
			for(int j = 0; j < wsTmp.getPontos().length; j++)
			{
				// vetor de distancias entre o origemAtual e os pontos getpontos da WS
				Ponto pTmp = wsTmp.getPontos()[j];
				double distancia, x, xTmp, y, yTmp, z, zTmp;
				x = origemAtual.getX();
				y = origemAtual.getY();
				z = origemAtual.getZ();
				xTmp = pTmp.getX();
				yTmp = pTmp.getY();
				zTmp = pTmp.getZ();
				distancia = Math.sqrt((x - xTmp) * (x - xTmp) + (y - yTmp) * (y - yTmp) + (z - zTmp) * (z - zTmp));
				distanciasWS.add(distancia);
			}
			gruposDistancias.add(distanciasWS);
		}
		return gruposDistancias;
	}
}
