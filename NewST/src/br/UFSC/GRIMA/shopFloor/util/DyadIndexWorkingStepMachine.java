package br.UFSC.GRIMA.shopFloor.util;

public class DyadIndexWorkingStepMachine 
{
	private int indexWorkingStep;
	private int indexMachine;
	
	public DyadIndexWorkingStepMachine()
	{
		this.indexMachine=0;
		this.indexWorkingStep=0;
	}

	public DyadIndexWorkingStepMachine(int indexWorkingStep, int indexMachine)
	{
		this.indexMachine=indexMachine;
		this.indexWorkingStep=indexWorkingStep;
	}	
	
	public int getIndexWorkingStep() {
		return indexWorkingStep;
	}
	public void setIndexWorkingStep(int indexWorkingStep) {
		this.indexWorkingStep = indexWorkingStep;
	}
	public int getIndexMachine() {
		return indexMachine;
	}
	public void setIndexMachine(int indexMachine) {
		this.indexMachine = indexMachine;
	}
	
}
