package br.UFSC.GRIMA.capp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.shopFloor.ProjetoSF;
import br.UFSC.GRIMA.util.PassaParaString;
import br.UFSC.GRIMA.util.Ponto;

public class Workingstep implements Serializable{
		
	public static final int DESBASTE = 1, ACABAMENTO = 2;
	private int tipo;
	private Feature feature;
	private Face face;
	private Ferramenta ferramenta;
	private InfoMovimentacao infoMovimentacao;
	private CondicoesDeUsinagem condicoesUsinagem;
	private MachiningOperation operation;
	private Ponto[] pontos;//todos os pontos de possiveis incios de usinagem
	private Ponto pontoInicial;//para o inicio da usinagem
	private Ponto pontoFinal;//ponto que a ferramenta deve ir quando terminar a usinagem
	private Vector movimentacao;
	private String id = "";
	//private DeterminadorDeTempo dT;
	private double tempo;
	private ArrayList<Double> temposNasMaquinas = new ArrayList<Double>(); // Tempo que leva efetuar a remocao do material na maquina i, i + 1, ...
	private ArrayList<Double> custosNasMaquinas = new ArrayList<Double>(); // custo da operacao na maquina i, i + 1, ...

	private double custo;
	private Workingstep workingstepPrecedente;
	
	public Workingstep(){

	}
	public Workingstep(Feature feature, Face faceMae){
		this.feature = feature;
		this.face = faceMae;
	}
	
	public Workingstep(Feature feature, Face faceMae, Ferramenta ferramenta){
		this.feature = feature;
		this.face = faceMae;
		this.ferramenta = ferramenta;
	}
	
	public Workingstep(Feature feature, Face faceMae, Ferramenta ferramenta, CondicoesDeUsinagem condicoesDeUsinagem, MachiningOperation operation){
		this.feature = feature;
		this.face = faceMae;
		this.ferramenta = ferramenta;
		this.condicoesUsinagem = condicoesDeUsinagem;
		this.operation = operation;
	}
	
	public CondicoesDeUsinagem getCondicoesUsinagem() {
		return condicoesUsinagem;
	}
	public void setCondicoesUsinagem(CondicoesDeUsinagem condicoesUsinagem) {
		this.condicoesUsinagem = condicoesUsinagem;
	}
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	public InfoMovimentacao getInfoMovimentacao() {
		return infoMovimentacao;
	}
	public void setInfoMovimentacao(InfoMovimentacao infoMovimentacao) {
		this.infoMovimentacao = infoMovimentacao;
	}
	public Face getFace() {
		return face;
	}
	public void setFace(Face faceMae) {
		this.face = faceMae;
	}
	public Ponto getPontoFinal() {
		return pontoFinal;
	}
	public void setPontoFinal(Ponto pontoFinal) {
		this.pontoFinal = pontoFinal;
	}
	public Ponto getPontoInicial() {
		return pontoInicial;
	}
	public void setPontoInicial(Ponto pontoInicial) {
		this.pontoInicial = pontoInicial;
	}
	public Ponto[] getPontos() {
		return pontos;
	}
	public void setPontos(Ponto[] pontos) {
		this.pontos = pontos;
	}
	public void setFerramenta(Ferramenta ferramenta2)
	{
		this.ferramenta = ferramenta2;
	}
	public Ferramenta getFerramenta()
	{
		return this.ferramenta;
	}
	public void setPontosMovimentacao(Vector movimentacao)
	{
		this.movimentacao = movimentacao;
	}
	public Vector getPontosMovimentacao()
	{
		return this.movimentacao;
	}
	public void setTempo(double tempo)
	{
		this.tempo = tempo;
	}
	public double getTempo()
	{
		return this.tempo;
	}
	public void setCusto(double custo)
	{
		this.custo = custo;
	}
	public double getCusto()
	{
		return this.custo;
	}
	public String getDados(String tab){
		String saida = "";
		saida += (tab + " Feature:\n");
		saida += PassaParaString.featureToString(this.feature, tab + tab);
		
		Ponto[] pontosTmp = this.getPontos();
		saida += (tab + " Pontos de Controle:\n");
		for(int i = 0; i < pontosTmp.length; i++){
			saida += (tab + tab + pontosTmp[i].getDados());
		}
		
		CondicoesDeUsinagem cuTmp = this.getCondicoesUsinagem();
		saida += (tab + " Condi��es de Usinagem:\n");
		saida += (tab + tab + "Ae = " + cuTmp.getAe() + "\n");
		saida += (tab + tab + "Af = " + cuTmp.getAf() + "\n");
		saida += (tab + tab + "Ap = " + cuTmp.getAp() + "\n");
		saida += (tab + tab + "F = " + cuTmp.getF() + "\n");
		saida += (tab + tab + "N = " + cuTmp.getN() + "\n");
		saida += (tab + tab + "Vc = " + cuTmp.getVc() + "\n");
		saida += (tab + tab + "Vf = " + cuTmp.getVf() + "\n");
		
		Ferramenta fTmp = this.getFerramenta();
		saida += (tab + " Ferramenta Utilizada:\n");
		saida += (tab + tab + "Diametro = " + fTmp.getDiametroFerramenta() + "\n");
		saida += (tab + tab + "Profundidade Maxima = " + fTmp.getProfundidadeMaxima() + "\n");
		
		InfoMovimentacao infoTmp = this.getInfoMovimentacao();
		saida += (tab + " Info Movimenta��o:\n");
		saida += (tab + tab + infoTmp.getTipoString() + "\n");
		
		saida += (tab + " Tempo de Usinagem = " + this.getTempo() + "\n");

		return saida;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MachiningOperation getOperation() {
		return operation;
	}

	public void setOperation(MachiningOperation operation) {
		this.operation = operation;
	}

	public Workingstep getWorkingstepPrecedente() {
		return workingstepPrecedente;
	}

	public void setWorkingstepPrecedente(Workingstep workingstepPrecedente) {
		this.workingstepPrecedente = workingstepPrecedente;
	}

	public void removePosCedente(Vector<Workingstep> workingstepsFace) {

		for(int i = 0; i<workingstepsFace.size(); i++){
			
			Workingstep wsTmp = workingstepsFace.get(i);
			
			if(wsTmp.getWorkingstepPrecedente()!= null){
			
				if(wsTmp.getWorkingstepPrecedente().equals(this)){
				
					wsTmp.removePosCedente(workingstepsFace);
				
				}
			}
			
		}
		
		Feature feature = this.getFeature();
		
		feature.getWorkingsteps().remove(this);
		
		workingstepsFace.remove(this);
		
	}
	
	public Vector<Workingstep> identificarPosCedente(Vector<Workingstep> workingstepsFace, Vector<Workingstep> wsAssociadas) {


		for(int i = 0; i<workingstepsFace.size(); i++){

			Workingstep wsTmp = workingstepsFace.get(i);

			if(wsTmp.getWorkingstepPrecedente()!= null){

				if(wsTmp.getWorkingstepPrecedente().equals(this)){

					wsAssociadas.add(wsTmp);

					wsTmp.identificarPosCedente(workingstepsFace, wsAssociadas);

				}
			}
		}

		return wsAssociadas;

	}
	
	public ArrayList<Workingstep> getWorkingstepsPoscedentesDiretos(ArrayList<Workingstep> wsArray)
	{
		ArrayList<Workingstep> wsPoscedentesArray = new ArrayList<Workingstep>();
		
		if(wsArray.size() > 0)
		{
			for (int i = 0; i < wsArray.size(); i++)
			{
				if (this == wsArray.get(i).getWorkingstepPrecedente())
				{
					wsPoscedentesArray.add(wsArray.get(i));
				}
			}
		}
		return wsPoscedentesArray;
	}
	public ArrayList<Double> getTemposNasMaquinas() {
		return temposNasMaquinas;
	}
	public void setTemposNasMaquinas(ArrayList<Double> temposNasMaquinas) {
		this.temposNasMaquinas = temposNasMaquinas;
	}
	public ArrayList<Double> getCustosNasMaquinas() {
		return custosNasMaquinas;
	}
	public void setCustosNasMaquinas(ArrayList<Double> custosNasMaquinas) {
		this.custosNasMaquinas = custosNasMaquinas;
	}
}
