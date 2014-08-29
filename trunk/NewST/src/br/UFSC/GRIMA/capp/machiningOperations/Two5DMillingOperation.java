package br.UFSC.GRIMA.capp.machiningOperations;

import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Two5DMillingStrategy;

/**
 * 
 * @author Jc
 *
 */
public abstract class Two5DMillingOperation extends MillingTypeOperation
{
	public Two5DMillingOperation(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}
}
