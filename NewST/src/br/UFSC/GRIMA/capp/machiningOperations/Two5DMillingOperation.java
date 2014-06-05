package br.UFSC.GRIMA.capp.machiningOperations;

import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Two5DMillingStrategy;

/**
 * 
 * @author Jc
 *
 */
public abstract class Two5DMillingOperation extends MillingTypeOperation
{
	private Two5DMillingStrategy machiningStrategy;
	public Two5DMillingOperation(String id, double retractPlane) 
	{
		super(id, retractPlane);
	}
	public Two5DMillingStrategy getMachiningStrategy() 
	{
		return machiningStrategy;
	}
	public void setMachiningStrategy(Two5DMillingStrategy machiningStrategy) 
	{
		this.machiningStrategy = machiningStrategy;
	}
}
