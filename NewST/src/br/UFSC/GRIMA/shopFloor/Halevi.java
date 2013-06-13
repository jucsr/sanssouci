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
	private ShopFloor shopFloor; // dado de entrada necess√°rio
	private ArrayList<Workingstep> opmatrix; // dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	private ArrayList<Integer> bestSequence; // apenas os indices do array workingstep sao apresentados na ordem de execucao
	private ArrayList<ArrayList<Double>> zMatrix = new ArrayList<ArrayList<Double>>() ; // n operacoes em m maquinas
	private ArrayList<ArrayList<Double>>cMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Integer>> pMatrix; 
	private int LotSize = 1000; // em unidades
	private double machineSetupTime = 30; //em minutos
	
	/**
	 *  este m√©todo deve criar a matriz universal de halevi e as matrizes auxiliares Z e P
	 *  
	 * @param shopFloor
	 * @param workingsteps
	 */
	public Halevi(ShopFloor shopFloor, ArrayList<Workingstep> workingsteps)
	{
		this.shopFloor = shopFloor;
		this.opmatrix = workingsteps;
		this.machineTools = shopFloor.getMachines();
	}
	public ArrayList<ArrayList<Double>> getUniversalCostMatrix()
	{	
		for(int i = 0; i < opmatrix.size(); i++){
			ArrayList<Double> aux = new ArrayList<Double>();
			for(int f = 0; f < machineTools.size();f++){
				aux.add((opmatrix.get(i).getTemposNasMaquinas().get(f) * machineTools.get(f).getRelativeCost()));
			}
			cMatrix.add(aux);
			
		}
		return cMatrix;
	}
	private ArrayList<ArrayList<Double>> getUniversalTimeMatrix()
	{
		
		return null;
	}
	/**
	 * 	metodo que calcula a matriz Z (de Tempos/Custos)
	 */
	public void solveZMatrix()
	{	
		
		
		for(int i = (cMatrix.size()-1); i>=0;i--){
			System.out.println("vez" +i);
			int aux =cMatrix.get(i).size();
			for(int f = 0 ; f < aux ; f++){
				Double indice =cMatrix.get(i).get(f); 
				System.out.println("coluna" +f);
				Linha line = new Linha();
				for(int j = 0; j< aux;j++){
					System.out.println("elemento" +j);
					
					if(f==j){
						line.add((indice+cMatrix.get(i).get(j)));
					}else {
						line.add((indice+0.2+cMatrix.get(i).get(j)));	
					}
					
				}
				cMatrix.get(i).set(f,line.min());
				
			}//termino de coluna
			zMatrix.add(cMatrix.get(i));
			
		}//termino de linha
	}//termino da função
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
		return opmatrix;
	}
	public void setWorkingsteps(ArrayList<Workingstep> workingsteps) {
		this.opmatrix = workingsteps;
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