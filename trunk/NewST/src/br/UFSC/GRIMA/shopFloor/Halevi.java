package br.UFSC.GRIMA.shopFloor;


import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
/**
 * .
 * @author ms and jc
 *	
 */
public class Halevi 
{
	private ArrayList<ArrayList<Double>> cMatrix = new ArrayList<ArrayList<Double>>();
	private ShopFloor shopFloor; // dado de entrada necess√°rio
	private ArrayList<Workingstep> opmatrix; // dado de entrada necessario
	private ArrayList<MachineTool> machineTools; // vetor de maquinas
	private ArrayList<Integer> bestSequence; // apenas os indices do array workingstep sao apresentados na ordem de execucao
	
	private ArrayList<ArrayList<Double>> zMatrix = new ArrayList<ArrayList<Double>>() ; // n operacoes em m maquinas, zmatrix otimizado
	private ArrayList<ArrayList<Double>> znormalMatrix = new ArrayList<ArrayList<Double>>() ; // n operacoes em m maquinas, zmatrix antes da otimizacao
	public ArrayList<ArrayList<Double>> getcMatrix() {
		return cMatrix;
	}
	public void setcMatrix(ArrayList<ArrayList<Double>> cMatrix) {
		this.cMatrix = cMatrix;
	}
	public ArrayList<ArrayList<Double>> getZnormalMatrix() {
		return znormalMatrix;
	}
	public void setZnormalMatrix(ArrayList<ArrayList<Double>> znormalMatrix) {
		this.znormalMatrix = znormalMatrix;
	}
	public ArrayList<vectorWorkingPath> getRankedList() {
		return RankedList;
	}
	public void setRankedList(ArrayList<vectorWorkingPath> rankedList) {
		RankedList = rankedList;
	}
	private ArrayList<ArrayList<Integer>> pMatrix=new ArrayList<ArrayList<Integer>>(); ; 
	private ArrayList<ArrayList<Integer>> pnormalMatrix=new ArrayList<ArrayList<Integer>>(); ; 
	
	private ArrayList<vectorWorkingPath> RankedList = new  ArrayList<vectorWorkingPath>() ;
	
	private int LotSize = 1000; // em unidades
	private double machineSetupTime = 30; //em minutos
	private double opimpossible = 100.0;
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
	{	cMatrix.clear();
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
						line.add((indice+0.2+zMatrix.get((i+1)).get(j)));// Penalidade padrao 0.2!!	
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
		
	}//termino da função
	/**
	 * 	metodo para calculo da matriz P (de sequencias)
	 */
	private void fillMatrix()
	{
		for(int w =0; w< cMatrix.size();w++){
		 ArrayList<Double> t = new ArrayList<Double>();
		 t.addAll(cMatrix.get(w));
		 zMatrix.add(t);
		} // O que estava causando todos os problemas: zmatrix e cmatrix eram as mesma matrix.
		 
		 
		 
		 
		
		
		
		for(int i = 0; i< cMatrix.size();i++) {
			
			
			ArrayList<Integer> line = new ArrayList<Integer>();
			for(int w = 0; w < cMatrix.get(0).size() ;w++){
				line.add(0);
			}
			pMatrix.add(line);
			
		}
	}
	
	public void MakeRankedList(){
		int aux = 0;
		//System.out.println(""+cMatrix);
		for(int k = 0; k < cMatrix.get(0).size();k++){
			ArrayList<Integer> lista = new ArrayList<Integer>();
			aux = k;
			
			if(pMatrix.get(0).get(aux) > 0){ // nao coloca operacoes impossiveis
				vectorWorkingPath vwp = new vectorWorkingPath(); 
			vwp.setTime(zMatrix.get(0).get(aux));
			lista.add(aux+1);
		
			for(int j = 0; j < (pMatrix.size()-1);j++){ // listar os metodos da primeira otimização.
				
				
				
				lista.add(pMatrix.get(j).get(aux));
				//System.out.println(""+lista);
				aux = (pMatrix.get(j).get(aux)-1);
				//System.out.println("aux"+aux);
				
			}//System.out.println("ok");
			
			vwp.setList(lista);
			vwp.setWorkingstep(opmatrix);
			RankedList.add(vwp);// a lista tem a ordem de operacoes o rank e a ordem de operacoes
			}
		}
		
		aux =0;
			// listou todos da primeira otimização
		//
		for(int w =0; w< zMatrix.size();w++){
			 ArrayList<Double> t = new ArrayList<Double>();
			 ArrayList<Integer>x = new ArrayList<Integer>();
			 t.addAll(zMatrix.get(w));
			 x.addAll(pMatrix.get(w));
			 znormalMatrix.add(t);
			 pnormalMatrix.add(x);
			}	// copia o zmatrix e o pmatrix nao otimizado.
		
			
			aux = Matrixmin(zMatrix.get(0));
			aux= pMatrix.get(0).get(aux);
		
			if(aux != 0){
			if(CMatrixReform((1),(aux-1))){
			for(int k = 0; k < cMatrix.get(0).size();k++){	
				aux = k;
				
			/*	System.out.println("reformando...");
				System.out.println(""+pMatrix);
				System.out.println(""+zMatrix);*/
				if(pMatrix.get(0).get(aux) > 0){ // nao coloca operacoes impossiveis
					vectorWorkingPath vwp = new vectorWorkingPath();
					ArrayList<Integer> lista = new ArrayList<Integer>();
					vwp.setTime(zMatrix.get(0).get(aux));
					
					lista.add(aux+1);
					for(int j = 0; j < (pMatrix.size()-1);j++){ // listar os metodos da segunda otimização.
						
						lista.add(pMatrix.get(j).get(aux));
						aux = (pMatrix.get(j).get(aux)-1);
					
					}
					vwp.setList(lista);
					vwp.setWorkingstep(opmatrix);
					RankedList.add(vwp);
				//System.out.println("entrei");	
				}
				
			}
			}
			
			}// final da segunda otimização
		//Ranking
	
		
		for (int j = 0; j < RankedList.size() ;j++){
			for(int i = 1; i < RankedList.size();i++){
				if(RankedList.get(i).getTime() < RankedList.get(i-1).getTime()){
					vectorWorkingPath vwp = new vectorWorkingPath();
					vwp = RankedList.get(i-1);
					RankedList.set(i-1,RankedList.get(i));
					RankedList.set(i,vwp);
								
				}else if(RankedList.get(i).getList().equals(RankedList.get(i-1).getList())){
					RankedList.get(i-1).setTime(99999.9); // joga o repetido para o fim.
				}
				
			}
		}
		
		
	}
	public ArrayList<Workingstep> getOpmatrix() {
		return opmatrix;
	}
	public void setOpmatrix(ArrayList<Workingstep> opmatrix) {
		this.opmatrix = opmatrix;
	}
	public ArrayList<MachineTool> getMachineTools() {
		return machineTools;
	}
	public void setMachineTools(ArrayList<MachineTool> machineTools) {
		this.machineTools = machineTools;
	}
	public ArrayList<ArrayList<Integer>> getPnormalMatrix() {
		return pnormalMatrix;
	}
	public void setPnormalMatrix(ArrayList<ArrayList<Integer>> pnormalMatrix) {
		this.pnormalMatrix = pnormalMatrix;
	}
	public int getLotSize() {
		return LotSize;
	}
	public void setLotSize(int lotSize) {
		LotSize = lotSize;
	}
	public double getMachineSetupTime() {
		return machineSetupTime;
	}
	public void setMachineSetupTime(double machineSetupTime) {
		this.machineSetupTime = machineSetupTime;
	}
	public double getOpimpossible() {
		return opimpossible;
	}
	public void setOpimpossible(double opimpossible) {
		this.opimpossible = opimpossible;
	}
	private boolean CMatrixReform(int k, int aux){
		
		
		int pre;
		
		for(int i = (k+1); i < cMatrix.size();i++){
			Workingstep wstemp = new Workingstep();
			if( aux == Matrixmin(cMatrix.get(i)) ) {
				pre = 0;
				
				wstemp = opmatrix.get(i).getWorkingstepPrecedente();
				for( int j = 0; j < opmatrix.size(); j++){
					if(wstemp == opmatrix.get(j) ){
						pre = j;
					}
				}
				//ok ate aqui.
				if( pre < k){
					// duplo bubblesort
				ArrayList <Double> t1 = new ArrayList<Double>();
				ArrayList <Double> t2 = new ArrayList<Double>();
				Workingstep wstemp2 = new Workingstep();
				t1 = cMatrix.get(k+1);
				cMatrix.set(k+1,cMatrix.get(i));
				wstemp = opmatrix.get(k+1);
				opmatrix.set(k+1,opmatrix.get(i));
			//	System.out.println("s"+cMatrix);
				for(int j =k+2;j <= i ; ++j){
				//	System.out.println(""+t1);
						t2 = cMatrix.get(j);
						cMatrix.set(j, t1);
						t1 = t2;
						t2 = cMatrix.get(j+1);
						wstemp2 = opmatrix.get(j);
						opmatrix.set(j,wstemp);
						wstemp = wstemp2;
						wstemp2 = opmatrix.get(j+1);
						
					}
			//	System.out.println("X"+zMatrix);
				zMatrix.clear();
			//	System.out.println("x"+cMatrix);
				
				pMatrix.clear();
				
				solveZMatrix();
		//		System.out.println("t"+cMatrix);
		//		System.out.println("T"+zMatrix);
				return true;// alterou as matrizes
				}
			}
		}
		return false;
	}
	private int Matrixmin( ArrayList<Double> l){
		int rtn = 0;
		for(int i= 1 ; i < l.size(); i++){
			if(l.get(rtn) > l.get(i) ){
				rtn = i;
				}
		}
		return rtn;
	}
	private ArrayList<Workingstep> FindNULLPrecedence(){
		ArrayList<Workingstep> rtrn = new ArrayList<Workingstep>();
		for(int i =0; i < opmatrix.size(); i++){
			if(this.opmatrix.get(i).getWorkingstepPrecedente() == null)
				rtrn.add(this.opmatrix.get(i));
		}
		return  rtrn;
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