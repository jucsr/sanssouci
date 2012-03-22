package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EClosed_pocket;
import br.UFSC.GRIMA.util.findPoints.LimitedArc;
import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class Cavidade extends Feature implements Serializable {

	private double locX;
	private double locY;
	private double locZ;
	private double raio;
//  private double verticeRaio;
	private double largura;
	private double comprimento;
	private double profundidade;
	private ArrayList<Boss> itsBoss;
	private ArrayList<LimitedElement> geometricalElements;
	
	private transient EClosed_pocket eClosed_pocket;
	
	private boolean passante = false;
	
	public Cavidade() {
		super(Feature.CAVIDADE);
//		this.createGeometricalElements();
	}

	public ArrayList<Boss> getItsBoss() {
		return itsBoss;
	}

	public void setItsBoss(ArrayList<Boss> itsBoss) {
		this.itsBoss = itsBoss;
	}

	public ArrayList<LimitedElement> getGeometricalElements() {
		return geometricalElements;
	}

	public void setGeometricalElements(ArrayList<LimitedElement> geometricalElements) {
		this.geometricalElements = geometricalElements;
	}

	public Cavidade(String nome, double x, double y, double z, double locX, double locY, double locZ, double verticeRaio,
			double largura, double comprimento, double profundidade) {
		super(Feature.CAVIDADE);
		setNome(nome);
		setPosicao(x, y, z);
		setPosicaoNorma(locX,locY,locZ);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();
	}
	
	public Cavidade(String nome, double x, double y, double z, double verticeRaio,
			double largura, double comprimento,	double profundidade) {
		super(Feature.CAVIDADE);
		setNome(nome);
		setPosicao(x, y, z);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();

	}
	
	public Cavidade(double x, double y, double z, double verticeRaio,
			double largura, double comprimento, double profundidade) {
		super(Feature.CAVIDADE);
		setPosicao(x, y, z);
		this.raio = verticeRaio;
		this.largura = largura;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.createGeometricalElements();

	}

	public DefaultMutableTreeNode getNodo() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Closed Pocket -"
				+ this.getIndice());
		root.add(new DefaultMutableTreeNode("Name: "
				+ this.getNome()));
		root.add(new DefaultMutableTreeNode("Length = "
				+ this.getComprimento()));
		root.add(new DefaultMutableTreeNode("Largura = " + this.getLargura()));
		root.add(new DefaultMutableTreeNode("Depth = "
				+ this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("Corner radius = " + this.getRaio()));
		root.add(new DefaultMutableTreeNode("Position X = "
				+ this.getPosicaoX()));
		root.add(new DefaultMutableTreeNode("Position Y = "
				+ this.getPosicaoY()));
		root.add(new DefaultMutableTreeNode("Position Z = "
				+ this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Tolerance = " + this.getTolerancia()));
		root.add(new DefaultMutableTreeNode("Roughness = " + this.getRugosidade()));

		this.getNodoWorkingSteps(root);
		
		return root;
	}

	public double getRaio() {
		return raio;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}
	
	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getLargura() {
		return largura;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public double getComprimento() {
		return this.comprimento;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getProfundidade() {
		return this.profundidade;
	}

	public double getVerticeAngulo() {
		return raio;
	}

	public void setVerticeAngulo(double verticeAngulo) {
		this.raio = verticeAngulo;
	}

	public void imprimirDados() {
		System.out.print("*********CAVIDADE " + this.getIndice() + "********");
		System.out.print("\n Profundidade = " + this.getProfundidade());
		System.out.print("\t Raio = " + this.getRaio());
		System.out.print("\t Comprimento = " + this.getComprimento());
		System.out.print("\t Largura = " + this.getLargura());
		System.out.print("\t Posição X = " + this.getPosicaoX());
		System.out.print("\t Posição Y = " + this.getPosicaoY());
		System.out.print("\t Posição Z = " + this.getPosicaoZ());
		System.out.print("\n CAVIDADE adicionada com sucesso\n");
	}

	public EClosed_pocket geteClosed_pocket() {
		return eClosed_pocket;
	}

	public void seteClosed_pocket(EClosed_pocket eClosed_pocket) {
		this.eClosed_pocket = eClosed_pocket;
	}

	public boolean isPassante() {
		return passante;
	}

	public void setPassante(boolean passante) {
		this.passante = passante;
	}

	public void setPosicaoNorma(double locX, double locY, double locZ){
		
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
	public void createGeometricalElements()
	{
		int n = 50;
		this.geometricalElements = new ArrayList<LimitedElement>();
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.raio, this.Y + this.raio, this.Z), new Point3d(this.X, this.Y + this.raio, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.raio, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.raio, this.Y + this.largura, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.comprimento - this.raio, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.comprimento, this.Y + this.largura - this.raio, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedArc(new Point3d(this.X + this.comprimento - this.raio, this.Y + this.raio, this.Z), new Point3d(this.X + this.comprimento - this.raio, this.Y, this.Z), 90, LimitedArc.CCW, n));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X, this.Y + this.raio, this.Z), new Point3d(this.X, this.Y + this.largura - this.raio, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.raio, this.Y + this.largura, this.Z), new Point3d(this.X + this.comprimento - this.raio, this.Y + this.largura, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.comprimento, this.Y + this.largura - this.raio, this.Z), new Point3d(this.X + this.comprimento, this.Y + this.raio, this.Z)));
		this.geometricalElements.add(new LimitedLine(new Point3d(this.X + this.comprimento - this.raio, this.Y, this.Z), new Point3d(this.X + this.raio, this.Y, this.Z)));
	}
}
