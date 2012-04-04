package br.UFSC.GRIMA.simulator;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;

public class ProjetoDeSimulacao {

	private Bloco bloco;
	
	private Rectangle3D block;
	
	public final static int PLANODETROCA = 250;

	public static int PLANODEMOVIMENTO = 50;
	
	private Vector<Feature> features;
	
	private Vector<Vector<Workingstep>> workingstepsAllFaces;
	
	private Vector<Workingstep> workingsteps;

	private ArrayList<Point3d> apoiosFaceInferior;
	
	public ProjetoDeSimulacao(Rectangle3D block, Vector<Feature> features, ArrayList<Point3d> apoiosFaceInferior) {
		this.block = block;
		this.features = features;
		this.apoiosFaceInferior = apoiosFaceInferior;
	}

	public ProjetoDeSimulacao(Rectangle3D block, ArrayList<Point3d> apoiosFaceInferior, Vector<Workingstep> workingsteps, int retractPlane) {
		this.block = block;
		this.apoiosFaceInferior = apoiosFaceInferior;
		this.workingsteps = workingsteps;
		ProjetoDeSimulacao.PLANODEMOVIMENTO = (int)(retractPlane + this.block.getZ() + 8);
	}
	
	public ProjetoDeSimulacao(Bloco bloco,
			ArrayList<Point3d> apoiosFaceInferior, Vector<Vector<Workingstep>> workingsteps) {

		this.bloco = bloco;
		this.block = this.setBlock();
//		this.features = ((Face)bloco.faces.elementAt(0)).features;
//		System.out.println("FEATURES : " + this.features);
		//NAO TA PEGANDO AS FEATURES DO BLOCO!
		this.apoiosFaceInferior = apoiosFaceInferior;
		this.workingstepsAllFaces = workingsteps;
		System.out.println("AAAAAABBBBBBBB: " + workingsteps);
		// Simulador funcionando corretamente apenas para uma face - nesse caso: XY
		this.workingsteps = workingsteps.get(0);
		ProjetoDeSimulacao.PLANODEMOVIMENTO = (int)(this.workingsteps.get(0).getOperation().getRetractPlane() + this.bloco.getProfundidade() + 8);
	}

	public Bloco getBloco() {
		return bloco;
	}

	public Rectangle3D setBlock(){
		
		this.block = new Rectangle3D(bloco.getX(),bloco.getY(),bloco.getZ());
		
		return this.block;
		
	}

	public void setBlock(double x, double y, double z){
		
		this.block = new Rectangle3D(x,y,z);
		
	}

	public void setBlock(Rectangle3D block) {
		this.block = block;
	}
	
	public Rectangle3D getBlock(){
		
		return this.block;
		
	}
	
	public ArrayList<Point3d> getApoiosFaceInferior() {
		return apoiosFaceInferior;
	}

	public void setApoiosFaceInferior(ArrayList<Point3d> apoiosFaceInferior) {
		this.apoiosFaceInferior = apoiosFaceInferior;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public Vector<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Vector<Feature> features) {
		this.features = features;
	}

	public Vector<Workingstep> getWorkingsteps() {
		return workingsteps;
	}

	public void setWorkingsteps(Vector<Workingstep> workingsteps) {
		this.workingsteps = workingsteps;
	}

	public Vector<Vector<Workingstep>> getWorkingstepsAllFaces() {
		return workingstepsAllFaces;
	}

	public void setWorkingstepsAllFaces(
			Vector<Vector<Workingstep>> workingstepsAllFaces) {
		this.workingstepsAllFaces = workingstepsAllFaces;
	}




}
