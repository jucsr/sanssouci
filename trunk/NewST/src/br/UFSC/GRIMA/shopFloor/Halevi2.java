package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.util.CalculateMachiningTime;

public class Halevi2 
{
	private ShopFloor shopFloor; // dado de entrada necessário
	private ProjetoSF projetoSF;
	private ArrayList<Workingstep> workingsteps; // array de workingsteps -- dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	private Penalties penal;
	
	private ArrayList<ArrayList<Integer>> pathMatrix = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Double>> universalTimeMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> universalCostMatrix = new ArrayList<ArrayList<Double>>();

	public Halevi2(ProjetoSF projetoSF, ArrayList<Workingstep> workingsteps)
	{
		this.projetoSF = projetoSF;
		this.shopFloor = this.projetoSF.getShopFloor();
		this.workingsteps = workingsteps;
		this.machineTools = shopFloor.getMachines();
		this.calculateUniversalTimeMatrix();
		this.universalTimeMatrix = this.getUniversalTimeMatrix();
		this.calculateUniversalCostMatrix();
		this.universalCostMatrix = this.getUniversalCostMatrix();
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
		
		for (int j1 = 0;j1<row1.size();j1++)
		{
			pathMatrix.add(new ArrayList<Integer>());
			for (int j2 = 0;j2<row2.size();j2++)
			{
				if (j1==j2)
				{
					sumList.add(row1.get(j1).getCusto() + row2.get(j1).getCusto());
				}
				else
				{
					Penalties tempPenalty = new Penalties(this.projetoSF, machineTools.get(j1), machineTools.get(j2),row1.get(j1));
					sumList.add(row1.get(j1).getCusto() + row2.get(j1).getCusto()+tempPenalty.getTotalPenalty());
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
	
	
	private ArrayList<ArrayList<Double>> getTotalMatrix(ArrayList<ArrayList<Workingstep>> pricesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();

		tempTotalMatrix.add(this.totalMatrixRow(pricesTable.get(pricesTable.size()-2),pricesTable.get(pricesTable.size()-1)));
		for (int i=pricesTable.size()-3;i>1;i--)
		{
			
			tempTotalMatrix.add(this.totalMatrixRowDouble(pricesTable.get(i),tempTotalMatrix.get(tempTotalMatrix.size()-1)));
		}
		
		for (int rowIndex=this.workingsteps.size()-2;rowIndex>1;rowIndex--)
		{
			totalMatrix.add(new ArrayList<Double>());
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
				totalMatrix.get(rowIndex).add(tempTotalMatrix.get(rowIndex).get(colIndex));
			}
		}
		return totalMatrix;
	}
	
	public void  calculateUniversalTimeMatrix()
	{
		int iWorkStep=0;
		
		for (Workingstep workingStep : workingsteps)			
		{
			int iMachTool=0;			
			universalTimeMatrix.add(new ArrayList<Double>());			
			for (MachineTool machineTool : machineTools)
			{	
				CalculateMachiningTime calcTime = new CalculateMachiningTime(workingStep, machineTool, projetoSF.getProjeto().getBloco().getMaterial());
				universalTimeMatrix.get(iWorkStep).add(calcTime.getTime());
				iMachTool++;
			}
			iWorkStep++;
		}		
	}
	
	public ArrayList<ArrayList<Double>> getUniversalTimeMatrix()
	{
		return universalTimeMatrix;
	}

	public void  calculateUniversalCostMatrix()
	{
		int iWorkStep=0;
		
		for (Workingstep workingStep : workingsteps)			
		{
			int iMachTool=0;			
			universalCostMatrix.add(new ArrayList<Double>());			
			for (MachineTool machineTool : machineTools)
			{	
				universalCostMatrix.get(iWorkStep).add(this.getUniversalTimeMatrix().get(iWorkStep).get(iMachTool)*machineTools.get(iMachTool).getRelativeCost());
				iMachTool++;
			}
			iWorkStep++;
		}		
	}
	
	public ArrayList<ArrayList<Double>> getUniversalCostMatrix() 
	{

		return universalCostMatrix;
	}
	
}
