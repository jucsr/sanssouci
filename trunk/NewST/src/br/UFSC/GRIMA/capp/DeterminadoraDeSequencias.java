package br.UFSC.GRIMA.capp;

import java.util.Vector;

public class DeterminadoraDeSequencias {
	//vetor workingsteps
	//vetor de sequencias - faces(sequencias)
	
	
	public DeterminadoraDeSequencias(Vector workingsteps) {
		// TODO Auto-generated constructor stub
		
		this.determinarSequencias();
	}
	
	private void determinarSequencias(){
		//gera combina��o de workingsteps
		
		//por face - for para percorrer as faces
		int[][] combinacoes = this.determinarCombinacoes(2, 2);
	}
	
	private int[][] determinarCombinacoes(int numeroDeElementos, int tamanhoCombinacao){
		return null;
	}
}
