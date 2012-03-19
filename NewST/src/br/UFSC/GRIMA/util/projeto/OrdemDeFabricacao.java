package br.UFSC.GRIMA.util.projeto;

import java.io.Serializable;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.entidades.Material;


public class OrdemDeFabricacao implements Serializable{
	private CondicoesDeUsinagem condicoesDeUsinagem = null;
	private Material material;
	private int quantidade = 0;
	
	public OrdemDeFabricacao(CondicoesDeUsinagem condicoesDeUsinagem, int quantidade){
		this.condicoesDeUsinagem = condicoesDeUsinagem;
		this.quantidade = quantidade;
	}
	
	public OrdemDeFabricacao(int quantidade, Material material){
		this.quantidade = quantidade;
		this.material = material;
	}

	public CondicoesDeUsinagem getCondicoesDeUsinagem() {
		return condicoesDeUsinagem;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Material getMaterial() {
		return material;
	}
}
