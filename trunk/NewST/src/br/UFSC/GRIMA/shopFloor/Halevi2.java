package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.util.CalculateMachiningTime;
import br.UFSC.GRIMA.shopFloor.Dyad;

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
	private ArrayList<ArrayList<Integer>> totalTimePathMatrix = new ArrayList<ArrayList<Integer>>();
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
		this.calculateTotalMatrixTime(this.getUniversalTimeMatrix());
		this.totalCostMatrix=this.calculateTotalMatrixCost(this.getUniversalCostMatrix());
		System.out.println("Constructor Done!");
	}
	
	private Dyad lowDyad(ArrayList<Double> list)
	{	
		Dyad dyadLow=new Dyad(0,0);
		
		double low=list.get(0);
		int lowIndex=0;
		
		for (int index=1; index<list.size(); index++)
		{
			if (low < list.get(index))
			{
				low =list.get(index);
				lowIndex=index;
			}
		}		
		
		dyadLow.setIndex(lowIndex);
		dyadLow.setValue(low);
		return dyadLow;		
	}

	private ArrayList<Dyad> totalMatrixTimeRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
	{
		ArrayList<Dyad> rowDyad = new ArrayList<Dyad>();
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
			rowDyad.add(this.lowDyad(sumList));
			
		}		
		return rowDyad;
	}

	private ArrayList<Dyad> totalMatrixCostRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
	{
		ArrayList<Dyad> rowDyad = new ArrayList<Dyad>();	
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
			rowDyad.add(this.lowDyad(sumList));
		}
		
		return rowDyad;
	}
	
	
	private void calculateTotalMatrixTime(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrixTime = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> totalMatrixPathTime = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> tempTotalPathMatrix=new ArrayList<ArrayList<Integer>>();

		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));
		
		for (int i=valuesTable.size()-1;i>0;i--)
		{
			ArrayList<Dyad> tempDyad = new ArrayList<Dyad>();
			ArrayList<Double> rowTime = new ArrayList<Double>();
			ArrayList<Integer> rowIndex = new ArrayList<Integer>();

			tempDyad = this.totalMatrixTimeRow(valuesTable.get(i-1),tempTotalMatrix.get(tempTotalMatrix.size()-1),i-1);
			for (Dyad dyadCurrent:tempDyad)
			{
				rowTime.add(dyadCurrent.getValue());
				rowIndex.add(dyadCurrent.getIndex());
			}
			tempTotalMatrix.add(rowTime);
			tempTotalPathMatrix.add(rowIndex);
		}
				
		
		System.out.println("RowsTempTotal: " + tempTotalMatrix.size());
		System.out.println("ColsTempTotal: " + tempTotalMatrix.get(0).size());
		
		for (int rowIndex=0;rowIndex<tempTotalMatrix.size();rowIndex++)
		{
			totalMatrixTime.add(new ArrayList<Double>());
			totalMatrixPathTime.add(new ArrayList<Integer>());
			
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
				totalMatrixTime.get(rowIndex).add(tempTotalMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				totalMatrixPathTime.get(rowIndex).add(tempTotalPathMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				System.out.println("Row " + rowIndex + " Col " + colIndex + " " + totalMatrixTime.get(rowIndex).get(colIndex));
			}
		}
		this.totalTimeMatrix=totalMatrixTime;
		this.totalTimePathMatrix=totalMatrixPathTime;
		
	}

	private ArrayList<ArrayList<Double>> calculateTotalMatrixCost(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrix = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> tempTotalPath=new ArrayList<ArrayList<Integer>>();
		
		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));
		
	
		for (int i=valuesTable.size()-1;i>0;i--)
		{
			ArrayList<Dyad> tempDyad = new ArrayList<Dyad>();
			ArrayList<Double> rowCost = new ArrayList<Double>();
			ArrayList<Integer> rowIndex = new ArrayList<Integer>();

			tempDyad = this.totalMatrixCostRow(valuesTable.get(i-1),tempTotalMatrix.get(tempTotalMatrix.size()-1),i-1);

			for (Dyad dyadCurrent:tempDyad)
			{
				rowCost.add(dyadCurrent.getValue());
				rowIndex.add(dyadCurrent.getIndex());
			}
			tempTotalMatrix.add(rowCost);
			tempTotalPath.add(rowIndex);
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
