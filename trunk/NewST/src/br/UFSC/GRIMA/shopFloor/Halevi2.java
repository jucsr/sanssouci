package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.util.CalculateMachiningTime;
import br.UFSC.GRIMA.shopFloor.util.Dyad;
import br.UFSC.GRIMA.shopFloor.util.DyadIndexWorkingStepMachine;

public class Halevi2 
{
	private ShopFloor shopFloor; // dado de entrada necessário
	private ProjetoSF projetoSF;
	private ArrayList<Workingstep> workingsteps; // array de workingsteps -- dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	
	private ArrayList<ArrayList<Integer>> pathTimeMatrix = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> pathCostMatrix = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<ArrayList<Double>> universalTimeMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> universalCostMatrix = new ArrayList<ArrayList<Double>>();
	
	private ArrayList<ArrayList<Double>> totalTimeMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Integer>> totalTimePathMatrix = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<ArrayList<ArrayList<Double>>> penaltiesTimeMatrix = new ArrayList<ArrayList<ArrayList<Double>>>();
	private ArrayList<ArrayList<ArrayList<Double>>> penaltiesCostMatrix = new ArrayList<ArrayList<ArrayList<Double>>>();
	
	private ArrayList<ArrayList<Double>> totalCostMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Integer>> totalCostPathMatrix = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<Integer> idealPathTime = new ArrayList<Integer>();
	private ArrayList<Integer> idealPathCost = new ArrayList<Integer>();
	
	private ArrayList<DyadIndexWorkingStepMachine> optimizedPathTime = new ArrayList<DyadIndexWorkingStepMachine>();
	private ArrayList<DyadIndexWorkingStepMachine> optimizedPathCost = new ArrayList<DyadIndexWorkingStepMachine>();
		
	
	
	public Halevi2(ProjetoSF projetoSF, ArrayList<Workingstep> workingsteps)
	{
		this.projetoSF = projetoSF;
		this.shopFloor = this.projetoSF.getShopFloor();
		this.workingsteps = workingsteps;
		this.machineTools = shopFloor.getMachines();
		this.initPenaltiesMatrix();
	
		this.calculateUniversalTimeMatrix();
		this.universalTimeMatrix = this.getUniversalTimeMatrix();
		this.calculateUniversalCostMatrix();
		//this.showUniversalTimeMatrix();
		this.universalCostMatrix = this.getUniversalCostMatrix();
		//System.out.println("Constructor First Step!");
		this.calculateTotalMatrixTime(this.getUniversalTimeMatrix());
		this.calculateTotalMatrixCost(this.getUniversalCostMatrix());
		
		this.optimizedPathTime = this.choosePathFromTotalDyad(this.universalTimeMatrix,this.totalTimeMatrix,this.totalTimePathMatrix);
		this.optimizedPathCost = this.choosePathFromTotalDyad(this.universalCostMatrix,this.totalCostMatrix,this.totalCostPathMatrix); 
		
		//System.out.println(this.choosePathFromUniversal(this.universalTimeMatrix));
		//System.out.println(this.choosePathFromUniversal(this.universalCostMatrix));
		System.out.println("Constructor Done!");
	}
	
	private void initPenaltiesMatrix()
	{
		
		for (int ws = 0;ws < this.workingsteps.size();ws++)
		{
			ArrayList<ArrayList<Double>> tmpSquare = new ArrayList<ArrayList<Double>>(); 
			for (int mi = 0; mi < this.machineTools.size();mi++)
			{
				ArrayList<Double> tmpRow = new ArrayList<Double>(); 
				for (int mj = 0; mj < this.machineTools.size();mj++)
				{
					
					tmpRow.add(0.0);
				}
				tmpSquare.add(tmpRow);
			}
			this.penaltiesTimeMatrix.add(tmpSquare);
			this.penaltiesCostMatrix.add(tmpSquare);
		}		
	}
	
	private Dyad lowDyad(ArrayList<Double> list)
	{	
		Dyad dyadLow=new Dyad(0,0);
		
		double low=list.get(0);
		
		int lowIndex=0;
		
		int index=0;
		for (Double currentValue:list)
		{
			if (low > currentValue)
			{
				low = currentValue;
				lowIndex=index;
			}
			index=index+1;
		}		
		dyadLow.setIndex(lowIndex+1);
		dyadLow.setValue(low);
//		System.out.println(list);
//		System.out.println("Low: " + dyadLow.getValue() + " LowIndex: " + dyadLow.getIndex());
		
		return dyadLow;		
	}

	private ArrayList<Dyad> totalMatrixTimeRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
	{
		ArrayList<Dyad> rowDyad = new ArrayList<Dyad>();
		
		ArrayList<ArrayList<Double>> squarePenalties = new ArrayList<ArrayList<Double>>(); 
	//	System.out.println("*********************************");
	//	System.out.println("Workingstep " + indexWorkingStep);
		for (int j1 = 0;j1<row1.size();j1++)
		{	
			ArrayList<Double> sumList = new ArrayList<Double>();
	//		System.out.println("------------------");
	//		System.out.println("Pivot Mach" + j1);
			ArrayList<Double> rowPenalties = new ArrayList<Double>();			
			for (int j2 = 0;j2<row2.size();j2++)
			{	
				double penalty = 0;
				 
				if (j1==j2)
				{
					sumList.add(row1.get(j1) + row2.get(j1));
					penalty = 0;
				}
				else
				{
					Penalties tempPenalty = new Penalties(this.projetoSF, machineTools.get(j1), machineTools.get(j2),this.workingsteps.get(indexWorkingStep));
					penalty = tempPenalty.getTotalPenalty();
	//				System.out.println("Mach" + j1 + "-> Mach" + j2  + " Penalty: " + tempPenalty.getTotalPenalty());
					sumList.add(row1.get(j1) + row2.get(j2) + penalty );
				}
				rowPenalties.add(penalty);
			}
			rowDyad.add(this.lowDyad(sumList));
			squarePenalties.add(rowPenalties);			
		}		
		this.penaltiesTimeMatrix.set(indexWorkingStep, squarePenalties);
		return rowDyad;
	}

	private ArrayList<Dyad> totalMatrixCostRow(ArrayList<Double> row1, ArrayList<Double> row2, int indexWorkingStep)
	{
		ArrayList<Dyad> rowDyad = new ArrayList<Dyad>();	
		ArrayList<ArrayList<Double>> squarePenalties = new ArrayList<ArrayList<Double>>(); 

		
		for (int j1 = 0;j1<row1.size();j1++)
		{
			
			ArrayList<Double> sumList = new ArrayList<Double>();	
			ArrayList<Double> rowPenalties = new ArrayList<Double>();
			for (int j2 = 0;j2<row2.size();j2++)
			{
				double penalty = 0;
				if (j1==j2)
				{
					sumList.add(row1.get(j1) + row2.get(j1));
					penalty=0;
				}
				else
				{					
					Penalties tempPenalty = new Penalties(this.projetoSF, machineTools.get(j1), machineTools.get(j2),this.workingsteps.get(indexWorkingStep));
					penalty = tempPenalty.getTotalPenalty()*machineTools.get(j2).getRelativeCost()/60;
					sumList.add(row1.get(j1) + row2.get(j2) + penalty);
				}
				rowPenalties.add(penalty);
			}
			rowDyad.add(this.lowDyad(sumList));
			squarePenalties.add(rowPenalties);
		}
		this.penaltiesCostMatrix.set(indexWorkingStep, squarePenalties);		
		return rowDyad;
	}
	
	
	private void calculateTotalMatrixTime(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrixTime = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> totalMatrixPathTime = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> tempTotalPathMatrix=new ArrayList<ArrayList<Integer>>();

		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));		
		tempTotalPathMatrix.add(new ArrayList<Integer>());
		
		for (ArrayList<Double> row:valuesTable)
		{
			//System.out.println(row);
		}
		
		for (int k = 0;k< valuesTable.get(0).size();k++)
		{
			tempTotalPathMatrix.get(0).add(0);	
		}
		
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
				
		for (int rowIndex=0;rowIndex<tempTotalMatrix.size();rowIndex++)
		{
			totalMatrixTime.add(new ArrayList<Double>());
			totalMatrixPathTime.add(new ArrayList<Integer>());
			
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
				totalMatrixTime.get(rowIndex).add(tempTotalMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				totalMatrixPathTime.get(rowIndex).add(tempTotalPathMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));				
			}
		}
		this.totalTimeMatrix=totalMatrixTime;
		this.totalTimePathMatrix=totalMatrixPathTime;
		
	}

	private void calculateTotalMatrixCost(ArrayList<ArrayList<Double>> valuesTable)
	{
		ArrayList<ArrayList<Double>> totalMatrixCost = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> totalMatrixPathCost = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Double>> tempTotalMatrix=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> tempTotalPathMatrix=new ArrayList<ArrayList<Integer>>();
		
		tempTotalMatrix.add(valuesTable.get(valuesTable.size()-1));
		tempTotalPathMatrix.add(new ArrayList<Integer>());
		
		for (int k = 0;k< valuesTable.get(0).size();k++)
		{
			tempTotalPathMatrix.get(0).add(0);	
		}
		
	
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
			tempTotalPathMatrix.add(rowIndex);
		}
		
		for (int rowIndex=0;rowIndex<tempTotalMatrix.size();rowIndex++)
		{
			totalMatrixCost.add(new ArrayList<Double>());
			totalMatrixPathCost.add(new ArrayList<Integer>());
			for (int colIndex=0;colIndex<this.machineTools.size();colIndex++)
			{
				totalMatrixCost.get(rowIndex).add(tempTotalMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));
				totalMatrixPathCost.get(rowIndex).add(tempTotalPathMatrix.get(tempTotalMatrix.size()-rowIndex-1).get(colIndex));				
			}
		}
		this.totalCostMatrix=totalMatrixCost;
		this.totalCostPathMatrix=totalMatrixPathCost;
	}
	
	public ArrayList<Integer> pathFromHere(Integer indexWorkingStep, Integer machineNumber, ArrayList<ArrayList<Integer>> matrixPath)
	{
		ArrayList<Integer> pathChoosed = new ArrayList<Integer>();
				
		pathChoosed.add(matrixPath.get(indexWorkingStep).get(machineNumber-1));
		for (int i=indexWorkingStep+1; i < matrixPath.size();i++)
		{
			pathChoosed.add(matrixPath.get(i).get(pathChoosed.get(i-indexWorkingStep-1)-1));
		}
		return pathChoosed;
	}
	

	public ArrayList<DyadIndexWorkingStepMachine> choosePathFromTotalDyad(ArrayList<ArrayList<Double>> universal, ArrayList<ArrayList<Double>> total, ArrayList<ArrayList<Integer>> path)
	{
		ArrayList<DyadIndexWorkingStepMachine> oldPath = new ArrayList<DyadIndexWorkingStepMachine>();
		ArrayList<DyadIndexWorkingStepMachine> newPath = new ArrayList<DyadIndexWorkingStepMachine>();
		
		ArrayList<DyadIndexWorkingStepMachine> newIdealPath = new ArrayList<DyadIndexWorkingStepMachine>();
		ArrayList<DyadIndexWorkingStepMachine> oldIdealPath = new ArrayList<DyadIndexWorkingStepMachine>();
				
		ArrayList<DyadIndexWorkingStepMachine> optPath = new ArrayList<DyadIndexWorkingStepMachine>();
		
		
		ArrayList<Integer> tempIdealPath = this.choosePathFromUniversal(universal);
		ArrayList<Integer> tempOldIdealPath = this.choosePathFromUniversal(universal);

		
		ArrayList<Integer> doneWorkingSteps = new ArrayList<Integer>();

		Dyad lowFirst = new Dyad();
		
		int iWStep=0;
		
		lowFirst = this.lowDyad(total.get(iWStep));
		
		ArrayList<Integer> tempOldPath=this.pathFromHere(iWStep, lowFirst.getIndex(), path);
		ArrayList<Integer> tempNewPath=this.pathFromHere(iWStep, lowFirst.getIndex(), path);
		
		oldPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(0).getIndiceArvore(),lowFirst.getIndex()));
		newPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(0).getIndiceArvore(),lowFirst.getIndex()));
		
		for(int i = 0; i<this.workingsteps.size();i++)
		{
			oldIdealPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(i).getIndiceArvore(),tempIdealPath.get(i)));
			newIdealPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(i).getIndiceArvore(),tempOldIdealPath.get(i)));
			
			if(i!=this.workingsteps.size()-1)
			{
				oldPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(i+1).getIndiceArvore(),tempOldPath.get(i)));
				newPath.add(new DyadIndexWorkingStepMachine(this.workingsteps.get(i+1).getIndiceArvore(),tempNewPath.get(i)));
			}
		}
		
		
		optPath.add(new DyadIndexWorkingStepMachine(oldPath.get(0).getIndexWorkingStep(),lowFirst.getIndex()));
		
		System.out.println("Row " + iWStep + " Total: " + total.get(iWStep));
		System.out.println("Choosed from row " + iWStep + ": " + lowFirst.getIndex());			
		System.out.println("Path from here (total): ");
		for (DyadIndexWorkingStepMachine d: newPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}
		System.out.println();
		System.out.println("IdealPath From Universal: ");
		for (DyadIndexWorkingStepMachine d: newIdealPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}
		System.out.println();
		doneWorkingSteps.add(optPath.get(0).getIndexWorkingStep());
		
		int iWS=0;
		
		System.out.println("List of workingsteps");
		
		for (Workingstep ws:this.workingsteps )
		{
			System.out.println(iWS + 1 + " IndArv " + ws.getIndiceArvore() + " Prec: " + ws.getWSPrecedenteID());
			iWS++;
		}
		
		System.out.println("****************************");
		
		for (int i = 1; i< this.workingsteps.size()-1; i++)
		{			
			if (newPath.get(i).getIndexMachine()==newPath.get(i-1).getIndexMachine())
			{
				System.out.println(i+1 + " Iguales");
				doneWorkingSteps.add(newPath.get(i).getIndexWorkingStep());
				for (DyadIndexWorkingStepMachine d: newPath)
				{
					System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
				}
				System.out.println();
				System.out.println("WorkingStep executed:" + doneWorkingSteps);
			}
			else
			{
				System.out.println(i+1 + " Diferentes");
				boolean existEqualMachine=false;
				for (int j=i;j<newIdealPath.size();j++)
				{
					boolean existPrecedence=false;
					
					System.out.println("Comp " + (j+1) + "th from ideal " + newIdealPath.get(j).getIndexMachine() + " with " + (i) + "th from Path " + newPath.get(i-1).getIndexMachine());
					if (newIdealPath.get(j).getIndexMachine()!=newPath.get(i-1).getIndexMachine())
					{
						System.out.println("Different machines");
						
					}
					else 
					{						
						System.out.println("Equal machines, comparing precedents ");
						Workingstep wsIdeal = new Workingstep();
						for(Workingstep tempWS:this.workingsteps)
						{
							if(tempWS.getIndiceArvore()==newIdealPath.get(j).getIndexWorkingStep())
							{
								wsIdeal = tempWS;
								break;
							}
						}
						//System.out.println("Comp " + this.workingsteps.get(newIdealPath.get(j).getIndexWorkingStep()).getWSPrecedenteID() + " with ");
						
						
						existEqualMachine=true;
						
						if (wsIdeal.getWSPrecedenteID()==0)
						{
							doneWorkingSteps.add(newIdealPath.get(j).getIndexWorkingStep());
							System.out.println("Precedence 0");
							System.out.println("WorkingStep executed:" + doneWorkingSteps);
							existPrecedence=true;
							System.out.println(doneWorkingSteps);							
						}
						
						else
						{
							System.out.println("Comp " + wsIdeal.getWSPrecedenteID() + " With ");
							for (int k = 0;k<doneWorkingSteps.size();k++)
							{
	
								if (wsIdeal.getWSPrecedenteID()==doneWorkingSteps.get(k))
								{
									doneWorkingSteps.add(newIdealPath.get(j).getIndexWorkingStep());
									System.out.println("Precedence not null");
									System.out.println("WorkingStep executed:" + doneWorkingSteps);
									System.out.println(doneWorkingSteps);						
									existPrecedence=true;
									break;
								}									
							}
						}
						if (existPrecedence)
						{
							ArrayList<DyadIndexWorkingStepMachine> tempPathIdeal = new ArrayList<DyadIndexWorkingStepMachine>();
							ArrayList<DyadIndexWorkingStepMachine> tempPathNew = new ArrayList<DyadIndexWorkingStepMachine>();
							
							DyadIndexWorkingStepMachine swapTerm = new DyadIndexWorkingStepMachine(newIdealPath.get(j).getIndexWorkingStep(), newIdealPath.get(j).getIndexMachine());
							
							System.out.println("Swap term  WS " + swapTerm.getIndexWorkingStep() + " Mach " + swapTerm.getIndexMachine() );
							System.out.println("");
							
							//Updating idealPath
							for (int idPath=0;idPath<newIdealPath.size();idPath++)
							{
								if(idPath==i)
								{
									tempPathIdeal.add(swapTerm);	
									System.out.println("Adding WS " + swapTerm.getIndexWorkingStep() + " Mach " + swapTerm.getIndexMachine() + " swap");
									tempPathIdeal.add(newIdealPath.get(idPath));
									System.out.println("Adding WS " + newIdealPath.get(idPath).getIndexWorkingStep() + " Mach " + newIdealPath.get(idPath).getIndexMachine());
								}
								else if (idPath!=j)
								{
									tempPathIdeal.add(newIdealPath.get(idPath));
									System.out.println("Adding WS " + newIdealPath.get(idPath).getIndexWorkingStep() + " Mach " + newIdealPath.get(idPath).getIndexMachine());
								}
							}
	
							for (int idPath=0;idPath<tempPathIdeal.size();idPath++)
							{
								newIdealPath.set(idPath, tempPathIdeal.get(idPath));
							}
							
							//Updating newPath
							for (int idPath=0;idPath<newPath.size();idPath++)
							{
								if (idPath==i)
								{
									tempPathNew.add(swapTerm);
									tempPathNew.add(newPath.get(idPath));
								}
								else if (idPath!=j)
								{
									tempPathNew.add(newPath.get(idPath));									
								}
							}
							
							for (int idPath=0;idPath<tempPathNew.size();idPath++)
							{
								newPath.set(idPath, tempPathNew.get(idPath));
							}
							
							System.out.println("Ideal Path Updated:");
							for (DyadIndexWorkingStepMachine d: newIdealPath)
							{
								System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
							}
							System.out.println();

							System.out.println("Path Updated:");
							for (DyadIndexWorkingStepMachine d: newPath)
							{
								System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
							}
							System.out.println();
							break;
						}
						
						if(!existPrecedence)
						{
							doneWorkingSteps.add(newPath.get(i).getIndexWorkingStep());
							System.out.println("Have Not Precedence");
							System.out.println("WorkingStep executed:" + doneWorkingSteps);
							break;
						}
					}
				}
				if (!existEqualMachine)
				{
					System.out.println("There was no equal machines");
				}
			}
		}
		
		for (int i=1;i<newPath.size();i++)
		{
			optPath.add(newPath.get(i));
		}
		System.out.println("WorkingStep executed:" + doneWorkingSteps);
		System.out.println();
		System.out.println("Old Ideal Path:");
		for (DyadIndexWorkingStepMachine d: oldIdealPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}
		System.out.println();
		System.out.println("Opt Ideal Path:");
		for (DyadIndexWorkingStepMachine d: newIdealPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}

		System.out.println();
		System.out.println("Old Total Path:");
		for (DyadIndexWorkingStepMachine d: oldPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}
		System.out.println();
		System.out.println("Opt Total Path:");
		for (DyadIndexWorkingStepMachine d: optPath)
		{
			System.out.println(d.getIndexWorkingStep() + " " + d.getIndexMachine());
		}
		System.out.println();
		
		System.out.println("**************************************");
		return optPath;
	}
	
	
	public ArrayList<Integer> choosePathFromUniversal(ArrayList<ArrayList<Double>> universal)
	{
		ArrayList<Integer> idealPath= new ArrayList<Integer>();
		
		for (ArrayList<Double> row:universal)
		{
			Dyad tempLow = new Dyad();
			
			tempLow = this.lowDyad(row);
			
			idealPath.add(tempLow.getIndex());
		}		
		return idealPath;
	}	
	
	public void orderByPrecedence(ArrayList<Integer> idealPath)
	{
		
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
	
	
	public void showUniversalTimeMatrix()
	{
		System.out.println("Begin UniversalTimeMatrix");
		for (ArrayList<Double> row:universalTimeMatrix)
		{
			System.out.println(row);
		}
		System.out.println("End UniversalTimeMatrix\n***************");
	}

	public void  calculateUniversalCostMatrix()
	{
		int iWorkStep=0;
		
		ArrayList<ArrayList<Double>> tempUniversalTimeMatrix= new ArrayList<ArrayList<Double>>();
		
		tempUniversalTimeMatrix = this.getUniversalTimeMatrix();
		
		for (Workingstep workingStep : workingsteps)			
		{
			int iMachTool=0;			
			universalCostMatrix.add(new ArrayList<Double>());			
			for (MachineTool machineTool : machineTools)
			{	
				universalCostMatrix.get(iWorkStep).add(tempUniversalTimeMatrix.get(iWorkStep).get(iMachTool)*machineTools.get(iMachTool).getRelativeCost()/60);
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

	public ArrayList<ArrayList<Integer>> getTotalTimePathMatrix()
	{
		return this.totalTimePathMatrix;
	}

	
	public ArrayList<ArrayList<Double>> getTotalCostMatrix()
	{
		return this.totalCostMatrix;
	}
	
	public ArrayList<ArrayList<Integer>> getTotalCostPathMatrix()
	{
		return this.totalCostPathMatrix;
	}
	
	public ArrayList<Integer> getIdealPathTime()
	{
		ArrayList<Integer> newPath = new ArrayList<Integer>(); 
		newPath=this.calculateIdealPathTotal(this.getTotalTimeMatrix(),this.getTotalTimePathMatrix());
		this.idealPathTime=newPath;
		return this.idealPathTime;
	}
	
	public ArrayList<Integer> getIdealPathCost()
	{
		ArrayList<Integer> newPath = new ArrayList<Integer>(); 
		newPath=this.calculateIdealPathTotal(this.getTotalCostMatrix(),this.getTotalCostPathMatrix());
		this.idealPathCost=newPath;
		return this.idealPathCost;
	}
	
	public ArrayList<DyadIndexWorkingStepMachine> getOptimizedPathTime()
	{
		return this.optimizedPathTime;
	}

	
	public ArrayList<DyadIndexWorkingStepMachine> getOptimizedPathCost()
	{
		return this.optimizedPathCost;
	}
	
	public ArrayList<Integer> calculateIdealPathTotal(ArrayList<ArrayList<Double>> total, ArrayList<ArrayList<Integer>> path)
	{
		Dyad lowFirst=new Dyad();
		int iWStep=0;	

		ArrayList<Integer> newPath = new ArrayList<Integer>();
		lowFirst = this.lowDyad(total.get(iWStep));
		
		newPath=this.pathFromHere(iWStep, lowFirst.getIndex(), path);
		return newPath;
	}

	public ArrayList<ArrayList<ArrayList<Double>>> getPenaltiesTimeMatrix() {
		return penaltiesTimeMatrix;
	}

	public ArrayList<ArrayList<ArrayList<Double>>> getPenaltiesCostMatrix() {
		return penaltiesCostMatrix;
	}

	public ArrayList<ArrayList<Integer>> getPathTimeMatrix() {
		return pathTimeMatrix;
	}

	public ArrayList<ArrayList<Integer>> getPathCostMatrix() {
		return pathCostMatrix;
	}	
	
}
