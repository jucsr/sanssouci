package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import jsdai.SCombined_schema.ESlot;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class Ranhura extends Feature implements Serializable{
	
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	private transient ESlot eSlot;
	private int eixo = Ranhura.HORIZONTAL;
	private double profundidade = 0.0;
	private double largura = 0.0;
	private double comprimento;
	private double locX;
	private double locY;
	private double locZ;
	private double deslocamentoNorma;
	private boolean startAtEnd;
	private Axis2Placement3D sweptShapePosition;
	
	public Ranhura(){
		super(Feature.RANHURA);
	}
	public Ranhura(String nome, double x, double y, double z, double largura,
			double profundidade, int eixo, Vector<Workingstep> workingsteps) {
		super(Feature.RANHURA);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.setLargura(largura);
		this.setProfundidade(profundidade);
		this.setEixo(eixo);
		this.setWorkingsteps(workingsteps);
	}
	public Ranhura(String nome, double x, double y, double z, double largura,
			double profundidade, int eixo) {
		super(Feature.RANHURA);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.setLargura(largura);
		this.setProfundidade(profundidade);
		this.setEixo(eixo);
	}
	public Ranhura(String nome, double x, double y, double z, double locX, double locY, double locZ, double largura,
			double profundidade, int eixo) {
		super(Feature.RANHURA);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.setPosicaoNorma(locX,locY,locZ);
		this.setLargura(largura);
		this.setProfundidade(profundidade);
		this.setEixo(eixo);
	}
	public Ranhura(String nome, double x, double y, double z, double locX, double locY, double locZ, double largura,
			double profundidade, double comprimentoRanhura, int eixo) {
		super(Feature.RANHURA);
		this.setNome(nome);
		this.setPosicao(x, y, z);
		this.setPosicaoNorma(locX,locY,locZ);
		this.setLargura(largura);
		this.setProfundidade(profundidade);
		this.setComprimento(comprimentoRanhura);
		this.setEixo(eixo);
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ranhura -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Largura = " + this.getLargura()));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("posição Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Deslocamento : " + this.getDeslocamento()));
		root.add(new DefaultMutableTreeNode("Eixo : " + this.getStringEixo()));
		root.add(new DefaultMutableTreeNode("Tolerancia = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		
		this.getNodoWorkingSteps(root);
		
		return root;
	}
	public void setEixo(int i)
	{
		if(i == Ranhura.HORIZONTAL)
		{
			this.eixo = Ranhura.HORIZONTAL;
		}
		else if(i == Ranhura.VERTICAL)
		{
			this.eixo = Ranhura.VERTICAL;
		}
		else
		{
			this.eixo = Ranhura.HORIZONTAL;
		}
	}
	public int getEixo()
	{
		return this.eixo;
	}
	
	public String getStringEixo()
	{
		if (this.eixo == HORIZONTAL)
		{
			return "HORIZONTAL";
		}
		else
			return "VERTICAL";
	}
	
	public double getDeslocamento()
	{
		if (this.eixo == HORIZONTAL)
		{
			return this.Y;
		}
		else
		{
			return this.X;
		}
		
	}	
	public void setProfundidade(double profundidade)
	{	
		this.profundidade = profundidade;
	}
	public double getProfundidade()
	{	
		return this.profundidade;
	}
	
	public void setLargura(double largura)
	{	
		this.largura = largura;
	}
	public double getLargura()
	{	
		return this.largura;
	}
	public void imprimirDados()
	{
		System.out.print("***RANHURA " + this.getIndice() + "*******");
		System.out.print("\t Profundidade = " + this.getProfundidade());
		System.out.print("\t Largura = " + this.getLargura());
		System.out.print("\t Posi��o X = " + this.getPosicaoX());
		System.out.print("\t Posi��o Y = " + this.getPosicaoY());
		System.out.print("\t Posi��o Z = " + this.getPosicaoZ());
		System.out.print("\t eixo =" + this.getEixo());
		System.out.print("\n ****RANHURA adicionada com sucesso\n");
	}
	/**
	 * @todo Criar metodos get e set para as variaveis desta classe!
	 */
	public void startAtEnd(boolean b) {
		startAtEnd=b;
	}
	public boolean isStartAtEnd() {
		return startAtEnd;
	}
	public void setStartAtEnd(boolean startAtEnd) {
		this.startAtEnd = startAtEnd;
	}
	public ESlot getESlot() {
		return eSlot;
	}
	public void setESlot(ESlot eSlot) {
		this.eSlot = eSlot;
	}
	public double getComprimento() {
		return comprimento;
	}
	public void setComprimento(double comprimentoRanhura) {
		this.comprimento = comprimentoRanhura;
	}
	public void setPosicaoNorma(double locX, double locY, double locZ) {
		
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
	}
	public double getLocX() {
		return locX;
	}
	public void setLocX(double locX) {
		this.locX = locX;
	}
	public double getLocY() {
		return locY;
	}
	public void setLocY(double locY) {
		this.locY = locY;
	}
	public double getLocZ() {
		return locZ;
	}
	public void setLocZ(double locZ) {
		this.locZ = locZ;
	}
	public double getDeslocamentoNorma() {
		return deslocamentoNorma;
	}
	public void setDeslocamentoNorma(double deslocamentoNorma) {
		this.deslocamentoNorma = deslocamentoNorma;
	}
	public Axis2Placement3D getSweptShapePosition() {
		return sweptShapePosition;
	}
	public void setSweptShapePosition(Axis2Placement3D sweptShapePosition) {
		this.sweptShapePosition = sweptShapePosition;
	}
}
