package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;

public class Halevi2 
{
	private ShopFloor shopFloor; // dado de entrada necessário
	private ArrayList<Workingstep> workingsteps; // array de workingsteps -- dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	private Penalties penal;
	
	private ArrayList<ArrayList<Integer>> pathMatrix = new ArrayList<ArrayList<Integer>>();   
	private ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
	

	public Halevi2(ShopFloor shopFloor, ArrayList<Workingstep> workingsteps)
	{
		this.shopFloor = shopFloor;
		this.workingsteps = workingsteps;
		this.machineTools = shopFloor.getMachines();
	}
	
	private double low(ArrayList<Double> list)
	{		
		double low=list.get(0);
		
		for (int index=1; index<list.size(); index++)
		{
			if (low < list.get(index))
			{
				low =list.get(index);
			}
		}		
		return low;		
	}

	private double lowIndex(ArrayList<Double> list)
	{		
		double low=list.get(0);
		int lowPosition=0;
		
		for (int index=1; index<list.size(); index++)
		{
			if (low < list.get(index))
			{
				low = list.get(index);
				lowPosition=index;
			}
		}		
		return lowPosition;		
	}
	
	private ArrayList<Double> totalMatrixRow(ArrayList<Workingstep> row1, ArrayList<Workingstep> row2)
	{
		ArrayList<Double> newRow = new ArrayList<Double>();
		ArrayList<Double> sumList = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		for (int j1 = 0;j1<row1.size();j1++)
		{			
			for (int j2 = 0;j2<row2.size();j2++)
			{
				if (j1==j2)
				{
					sumList.add(row1.get(j1).getCusto() + row2.get(j1).getCusto());
				}
				else
				{
					sumList.add(row1.get(j1).getCusto() + row2.get(j1).getCusto() + 0.2);
				}
			}
			newRow.add(this.low(sumList));
		}
		
		return newRow;
	}

	private ArrayList<Double> totalMatrixRowDouble(ArrayList<Workingstep> row1, ArrayList<Double> row2)
	{
		ArrayList<Double> newRow = new ArrayList<Double>();
		ArrayList<Double> sumList = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		for (int j1 = 0;j1<row1.size();j1++)
		{			
			for (int j2 = 0;j2<row2.size();j2++)
			{
				if (j1==j2)
				{
					sumList.add(row1.get(j1).getCusto() + row2.get(j1));
				}
				else
				{
					sumList.add(row1.get(j1).getCusto() + row2.get(j1) + 0.2);
				}
			}
			newRow.add(this.low(sumList));
		}
		
		return newRow;
	}
	
	
	private void  getTotalMatrix(ArrayList<ArrayList<Workingstep>> pricesTable)
	{

		totalMatrix.add(this.totalMatrixRow(pricesTable.get(pricesTable.size()-2),pricesTable.get(pricesTable.size()-1)));
		for (int i=pricesTable.size()-3;i>1;i--)
		{
			
			totalMatrix.add(this.totalMatrixRowDouble(pricesTable.get(i),this.totalMatrix.get(this.totalMatrix.size()-1)));
		}
	}
	
	public ArrayList<ArrayList<Workingstep>>  getTimeMatrix()
	{
		ArrayList<ArrayList<Workingstep>> timeMatrix = new ArrayList<ArrayList<Workingstep>>(); // array de workingsteps -- dado de saida		
		ArrayList<ArrayList<Double>> doubleTimeMatrix = new ArrayList<ArrayList<Double>>(); 

		int iWorkStep=0;
		ArrayList<Integer> indexLowCosts = new ArrayList<Integer>();
		
		for (Workingstep workingStep : workingsteps)			
		{
			int iMachTool=0;
			int indexLowCost=0;
			
			ArrayList<Double> costByMachine = new ArrayList<Double>();			
			for (MachineTool machineTool : machineTools)
			{	

				costByMachine.add(machineTool.getRelativeCost()*workingStep.getTempo());				
			}
						
		}
		
		return timeMatrix;
	}
	
}
