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
	private ArrayList<Workingstep> opmatrix; // dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	private ArrayList<Integer> bestSequence; // apenas os indices do array workingstep sao apresentados na ordem de execucao
	private ArrayList<ArrayList<Double>> zMatrix = new ArrayList<ArrayList<Double>>() ; // n operacoes em m maquinas
	private ArrayList<ArrayList<Double>>cMatrix = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Integer>> pMatrix=new ArrayList<ArrayList<Integer>>(); ; 
	private ArrayList<ArrayList<Double>>pauxMatrix;
	private int LotSize = 1000; // em unidades
	private double machineSetupTime = 30; //em minutos
	private double opimpossible = 999.0;
	/**
	 *  este método deve criar a matriz universal de halevi e as matrizes auxiliares Z e P
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
		
		fillMatrix();
		for(int i = (zMatrix.size()-2); i>=0;i--){
			//System.out.println("vez" +i);
			int aux =zMatrix.get(i).size();
			for(int f = 0 ; f < aux ; f++){
				double indice =zMatrix.get(i).get(f); 
				//System.out.println("coluna" +f);
				Linha line = new Linha();
				for(int j = 0; j< aux;j++){
					//System.out.println("elemento" +j);
					
					if(f==j){
						line.add((indice+zMatrix.get((i+1)).get(j)));
						
					}else {
						line.add((indice+0.2+zMatrix.get((i+1)).get(j)));	
					}
					//System.out.println("line "+line);
				}
				zMatrix.get(i).set(f,line.min());
				if(line.min() >= opimpossible){
					pMatrix.get(i).set(f, -1);
					
				}else{
				pMatrix.get(i).set(f,line.indexmin());
				//System.out.println(""+pMatrix);
				
				}
			}//termino de coluna
			
			
		}//termino de linha
	}//termino da fun��o
	/**
	 * 	metodo para calculo da matriz P (de sequencias)
	 */
	private void fillMatrix()
	{
		for(int i = 0; i < cMatrix.size();i++){
			zMatrix.add(cMatrix.get(i));
		}
		
		
		for(int i = 0; i< cMatrix.size();i++) {
			int j = cMatrix.get(0).size();
			ArrayList<Integer> line = new ArrayList<Integer>();
			for(int w = 0; w < j ;w++){
				line.add(0);
			}
			pMatrix.add(line);
		}
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