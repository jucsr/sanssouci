package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;

public class Halevi2 
{
	private ShopFloor shopFloor; // dado de entrada necessário
	private ArrayList<Workingstep> workingsteps; // array de workingsteps -- dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas

	public Halevi2(ShopFloor shopFloor, ArrayList<Workingstep> workingsteps)
	{
		this.shopFloor = shopFloor;
		this.workingsteps = workingsteps;
		this.machineTools = shopFloor.getMachines();
	}
	
}
