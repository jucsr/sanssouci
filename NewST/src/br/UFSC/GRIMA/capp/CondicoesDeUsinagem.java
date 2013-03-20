package br.UFSC.GRIMA.capp;

import java.io.Serializable;

import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;


public class CondicoesDeUsinagem implements Serializable{
	//private double diametroFerramenta = 6;
	//determinar as condicoes de usinagem
	private double vc; // velocidade de corte
	private double vf; // avanco
	private double ap; // profundidade de corte axial
	private double ae; // profundidade de corte radial
	private double af; // nao sei o q eh
	private double f; // avanco por rotacao
	private double n; //rotacao do fuso

	public boolean acabamento = false;
	
	public enum Material {
		ALUMINIO, CIBATOOL, NAO_DEFINIDO, MADEIRA, DEFINIDO_PELO_USUARIO
	};
	
	public Material material = Material.NAO_DEFINIDO;
	
	public Ferramenta ferramenta;
	
	public CondicoesDeUsinagem(){
		
	}
	
	public CondicoesDeUsinagem(Material material, Ferramenta ferramenta2){
		this.material = material;
		this.ferramenta = ferramenta2;
		
		this.setCondicoesDeUsinagem(this.material);
	}
	
	public CondicoesDeUsinagem(double vc, double vf, double ap, double ae, double af, double f, int n, boolean acabamento, Ferramenta ferramenta){
		this.material = Material.DEFINIDO_PELO_USUARIO;
		this.ferramenta = ferramenta;
		
		this.setVc(vc);
		this.setVf(vf);
		this.setAp(ap);
		this.setAe(ae);
		this.setAf(af);
		this.setF(f);
		this.setN(n);
		this.setAcabamento(acabamento);
	}
	public CondicoesDeUsinagem(double vc, double f, double vf, double n, double ap, double ae)
	{
		this.vc = vc;
		this.f = f;
		this.vf = vf;
		this.n = n;
		this.ap = ap;
		this.ae = ae;
	}
	public void setCondicoesDeUsinagem(Material material){
		double vc = 0;
		double vf = 0;
		double ap = 0;
		double ae = 0;
		double af = 0;
		double f = 0;
		int n = 0;
		boolean acabamento = false;
		
		switch (material){
			case CIBATOOL:
				vc = 100;
				vf = 10;
				ap = 2;
				ae = this.ferramenta.getDiametroFerramenta() * 0.75;
				break;
			case ALUMINIO:
				vc = 100;
				vf = 10;
				ap = 2;
				ae = this.ferramenta.getDiametroFerramenta() * 0.75;
				break;
			default:
				break;
		}
		
		this.setVc(vc);
		this.setVf(vf);
		this.setAp(ap);
		this.setAe(ae);
		this.setAf(af);
		this.setF(f);
		this.setN(n);
		this.setAcabamento(acabamento);
	}
	
	
	
	public boolean isAcabamento() {
		return acabamento;
	}
	public void setAcabamento(boolean acabamento) {
		this.acabamento = acabamento;
	}
	public void setAf(double af) {
		this.af = af;
	}
	public double getAf() {
		return this.af;
	}
	public void setAp(double ap) {
		this.ap = ap;
	}
	public double getAp() {
		return this.ap;
	}
	public double getF() {
		return this.f;
	}
	public void setF(double f) {
		this.f = f;
	}
	public double getVc() {
		return vc;
	}
	public void setVc(double vc) {
		this.vc = vc;
	}
	public void setN(int n)
	{
		this.n = n;
	}
	public double getN()
	{
		return this.n;
	}
	public void setAe(double ae)
	{
		this.ae = ae;
	}
	public double getAe()
	{
		return this.ae;
	}
	public void setVf(double vf)
	{
		this.vf = vf;
	}
	public double getVf()
	{
		return this.vf;
	}
	
	public String toString(){
		String output = "";
		
		output += ("Condi��es de Usinagem (" + this.material + ")\n"); 
		output += ("\tVc = " + this.vc + "\n");
		output += ("\tVf = " + this.vf + "\n");
		output += ("\tAp = " + this.ap + "\n");
		output += ("\tAe = " + this.ae + "\n");
		output += ("\tAf = " + this.af + "\n");
		output += ("\tF = " + this.f + "\n");
		output += ("\tN = " + this.n + "\n");
		output += ("\tAcabamento = " + this.acabamento + "\n");
		output += ("\n\tFerramenta = " + "Ainda tem que colocar os tipos de ferramenta no programa");
		
		return output;
	}
}
