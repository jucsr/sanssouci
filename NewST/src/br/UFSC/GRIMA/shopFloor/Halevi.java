package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
/**
 * .
 * @author jc
 *	
 */
public class Halevi 
{
	private ShopFloor shopFloor; // dado de entrada necessário
	private ArrayList<Workingstep> workingsteps; // dado de entrada necessario
	private ArrayList<Integer> bestSequence; // apenas os indices do array workingstep sao apresentados na ordem de execucao
	private ArrayList<ArrayList<Double>> zMatrix; // n operacoes em m maquinas
	private ArrayList<ArrayList<Integer>> pMatrix; 
	private int LotSize = 1000; // em unidades
	private double machineSetupTime = 30; //em minutos
	/**
	 *  este método deve criar a matriz universal de halevi e as matrizes auxiliares Z e P
	 *  
	 * @param shopFloor
	 * @param workingsteps
	 */
	public Halevi(ShopFloor shopFloor, ArrayList<Workingstep> workingsteps)
	{
		this.shopFloor = shopFloor;
		this.workingsteps = workingsteps;
	}
	private ArrayList<ArrayList<Double>> getUniversalCostMatrix()
	{
		
		return null;
	}
	private ArrayList<ArrayList<Double>> getUniversalTimeMatrix()
	{
		
		return null;
	}
	/**
	 * 	metodo que calcula a matriz Z (de Tempos/Custos)
	 */
	private void solveZMatrix()
	{
		ArrayList<MachineTool> machines = this.shopFloor.getMachines();
		for(int i = workingsteps.size() - 1; i < workingsteps.size() - 1; i--)
		{
			Workingstep wsTmp = workingsteps.get(i);
			Workingstep wsTmpAnterior = workingsteps.get(i + 1);
			int size = wsTmp.getTemposNasMaquinas().size();
			for(int j = 0; j < size; j++)
			{
				double tempoTmp = wsTmpAnterior.getTemposNasMaquinas().get(j);
			}
		}
	}
	/**
	 * 	metodo para calculo da matriz P (de sequencias)
	 */
	private void solvePMatrix()
	{
		
	}
	/**
	 * 	cria as penalidades
	 */
	private void createPenalties()
	{
		
	}
	public ShopFloor getShopFloor() {
		return shopFloor;
	}
	public void setShopFloor(ShopFloor shopFloor) {
		this.shopFloor = shopFloor;
	}
	public ArrayList<Workingstep> getWorkingsteps() {
		return workingsteps;
	}
	public void setWorkingsteps(ArrayList<Workingstep> workingsteps) {
		this.workingsteps = workingsteps;
	}
	public ArrayList<Integer> getBestSequence() {
		return bestSequence;
	}
	public void setBestSequence(ArrayList<Integer> bestSequence) {
		this.bestSequence = bestSequence;
	}
	public ArrayList<ArrayList<Double>> getzMatrix() {
		return zMatrix;
	}
	public void setzMatrix(ArrayList<ArrayList<Double>> zMatrix) {
		this.zMatrix = zMatrix;
	}
	public ArrayList<ArrayList<Integer>> getpMatrix() {
		return pMatrix;
	}
	public void setpMatrix(ArrayList<ArrayList<Integer>> pMatrix) {
		this.pMatrix = pMatrix;
	}
}