package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import jsdai.SCombined_schema.ERound_hole;
import br.UFSC.GRIMA.capp.Workingstep;


public class Furo extends Feature implements Serializable{
	
	private double raio;
	private double profundidade;
	private transient ERound_hole eRound_hole;
	private boolean passante = false;
	
	public Furo(){
		super(Feature.FURO);
	}
		
	public Furo(String nome, double x, double y, double z, double diametro, double profundidade) {
		super(Feature.FURO);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.raio = diametro/2;
		this.setProfundidade(profundidade);
	}
	
	public Furo(String nome, double x, double y, double z, double diametro) {
		super(Feature.FURO);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.raio = diametro/2;
	}
	
	
	public Furo(String nome, double x, double y, double z, double diametro, double profundidade, Vector<Workingstep> workingsteps) {
		super(Feature.FURO);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.raio = diametro/2;
		this.setProfundidade(profundidade);
		this.setWorkingsteps(workingsteps);
	}

	public Furo(String nome, double x, double y, double z, double diametro, double profundidade, Vector<Workingstep> workingsteps, double tolerancia, double rugosidade) {
		super(Feature.FURO);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.raio = diametro/2;
		this.setProfundidade(profundidade);
		this.setWorkingsteps(workingsteps);
		this.setTolerancia(tolerancia);
		this.setRugosidade(rugosidade);
	}
	
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Furo -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Raio = " + this.getRaio()));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("Posicao X = " + this.getPosicaoX()));
		root.add(new DefaultMutableTreeNode("Posicao Y = " + this.getPosicaoY()));
		root.add(new DefaultMutableTreeNode("Posicao Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}

	public void imprimirDados()
	{			
		System.out.print("***FURO " + this.getIndice() + "*******");
		System.out.print("\t Raio = " + this.getRaio());
		System.out.print("\t Profundidade = " + this.getProfundidade());
		System.out.print("\t Tolerancia = " + this.getTolerancia());
		System.out.print("\t Rugosidade = " + this.getRugosidade());
		System.out.print("\t Posicao X = " + this.getPosicaoX());
		System.out.print("\t Posicao Y = " + this.getPosicaoY());
		System.out.print("\t Posicao Z = " + this.getPosicaoZ());
		System.out.print("\n ****FURO adicionado com sucesso\n");
	}
	public void setRaio(double raio)
	{	
		this.raio = raio;
	}
	public double getRaio()
	{	
		return this.raio;
	}
	public double getDiametro()
	{	
		return 2*this.raio;
	}
	public void setProfundidade(double profundidade)
	{	
		this.profundidade = profundidade;
	}
	public double getProfundidade()
	{	
		return this.profundidade;
	}

	public ERound_hole geteRound_hole() {
		return eRound_hole;
	}

	public void seteRound_hole(ERound_hole eRound_hole) {
		this.eRound_hole = eRound_hole;
	}

	public boolean isPassante() {
		return passante;
	}

	public void setPassante(boolean passante) {
		this.passante = passante;
	}
}
