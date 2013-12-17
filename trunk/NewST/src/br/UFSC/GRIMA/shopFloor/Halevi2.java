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
	
	private ArrayList<ArrayList<Integer>> pathTimeMatrix = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> pathCostMatrix = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Double>> universalTimeMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> universalCostMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> totalTimeMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> totalCostMatrix = new ArrayList<ArrayList<Double>>();

	
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
		this.totalTimeMatrix=this.calculateTotalMatrixTime(this.getUniversalTimeMatrix());
		this.totalCostMatrix=this.calculateTotalMatrixCost(this.getUniversalCostMatrix());
		System.out.println("Constructor Done!");
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


	private ArrayList<Double> totalMatrixTimeRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
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
					sumList.add(row1.get(j1) + row2.get(j1));
				}
				else
				{
					Penalties tempPenalty = new Penalties(this.projetoSF, machineTools.get(j1), machineTools.get(j2),this.workingsteps.get(indexWorkingStep));
					sumList.add(row1.get(j1) + row2.get(j1) + tempPenalty.getTotalPenalty());
				}
			}
			newRow.add(this.low(sumList));
		}		
		return newRow;
	}

	private ArrayList<Double> totalMatrixCostRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
	{
		ArrayList<Double> newRow = new ArrayList<Double>();
		ArrayList<Double> sumList = new ArrayList<Double>();
		
		for (int j1 = 0;j1<row1.size();j1++)
		{			
			for (int j2 = 0;j2<row2.size();j2++)
			{
				if (j1==j2)
				{
					sumList.add(row1.get(j1) + row2.get(j1));
				}
				else
				{
					Penalties tempPenalty = new Penalties(this.projetoSF, machineTools.get(j1), machineTools.get(j2),this.workingsteps.get(indexWorkingStep));
					sumList.add(row1.get(j1) + row2.get(j1) + tempPenalty.getTotalPenalty()*machineTools.get(j2).getRelativeCost());
				}
			}
			newRow.add(this.low(sumList));
		}
		
		return newRow;
	}
	
	
	private ArrayList<ArrayList<Double>> calculateTotalMatrixTime(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();

		//System.out.println("WorkingSteps: " + this.workingsteps.size());
		
		//System.out.println("RowsValuesTable: " + valuesTable.size());
		//System.out.println("ColsValuesTable: " + valuesTable.get(0).size());
		
		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));
		
		for (int i=valuesTable.size()-1;i>0;i--)
		{
			tempTotalMatrix.add(this.totalMatrixTimeRow(valuesTable.get(i-1),valuesTable.get(i),i-1));
			//System.out.println("i: " + i);
		}
				
		
		System.out.println("RowsTempTotal: " + tempTotalMatrix.size());
		System.out.println("ColsTempTotal: " + tempTotalMatrix.get(0).size());
		
		for (int rowIndex=0;rowIndex<tempTotalMatrix.size();rowIndex++)
		{
			totalMatrix.add(new ArrayList<Double>());
			
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
				totalMatrix.get(rowIndex).add(tempTotalMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				System.out.println("Row " + rowIndex + " Col " + colIndex + " " + totalMatrix.get(rowIndex).get(colIndex));
			}
		}
		return totalMatrix;
	}

	private ArrayList<ArrayList<Double>> calculateTotalMatrixCost(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();
		
		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));
	
		for (int i=valuesTable.size()-1;i>0;i--)
		{
			tempTotalMatrix.add(this.totalMatrixCostRow(valuesTable.get(i-1),valuesTable.get(i),i-1));

		}
		
		for (int rowIndex=0;rowIndex<tempTotalMatrix.size();rowIndex++)
		{
			totalMatrix.add(new ArrayList<Double>());
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
		//		System.out.println("RowIndex: " + rowIndex);
//				System.out.println("ColIndex: " + colIndex);
	//			System.out.println("tempValue: " + tempTotalMatrix.get(rowIndex).get(colIndex));				
		//		totalMatrix.get(rowIndex).add(tempTotalMatrix.get(this.workingsteps.size()-rowIndex-1).get(colIndex));
				totalMatrix.get(rowIndex).add(tempTotalMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				System.out.println("Row " + rowIndex + " Col " + colIndex + " " + totalMatrix.get(rowIndex).get(colIndex));
//				System.out.println("SizeCol: " + totalMatrix.get(rowIndex).size());				
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

		return this.universalCostMatrix;
	}

	public ArrayList<ArrayList<Double>> getTotalTimeMatrix()
	{
		return this.totalTimeMatrix;
	}
	
	public ArrayList<ArrayList<Double>> getTotalCostMatrix()
	{
		return this.totalCostMatrix;
	}	
}
