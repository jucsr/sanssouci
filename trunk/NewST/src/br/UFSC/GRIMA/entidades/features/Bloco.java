package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import br.UFSC.GRIMA.entidades.Material;

public class Bloco implements Serializable{
	private double comprimento = 0.0;   //X
	private double largura = 0.0;       //Y
	private double profundidade = 0.0;  //Z
	public Vector faces = null;
	public Material material;
	private double toleranciaGlobal = 50;
	
	public Bloco(double comprimento, double largura, double profundidade){
		this.comprimento = comprimento;
		this.largura = largura;
		this.profundidade = profundidade;
		
		this.faces = criarFaces();
	}
	public Bloco(double comprimento, double largura, double profundidade, Material material)
	{
		this.comprimento = comprimento;
		this.largura = largura;
		this.profundidade = profundidade;
		this.material = material;
		
		this.faces = criarFaces();
	}
	public Bloco(double comprimento, double largura, double profundidade, double toleranciaGlobal, Material material)
	{
		this.comprimento = comprimento;
		this.largura = largura;
		this.profundidade = profundidade;
		this.material = material;
		this.faces = criarFaces();
		this.toleranciaGlobal = toleranciaGlobal;
	}
	public DefaultMutableTreeNode getNodo1()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Block General Data");
		root.add(new DefaultMutableTreeNode("Length = " + this.comprimento));
		root.add(new DefaultMutableTreeNode("Width = " + this.largura));
		root.add(new DefaultMutableTreeNode("Depth = " + this.profundidade));
		root.add(new DefaultMutableTreeNode("Global Tolerance = " + this.toleranciaGlobal * 0.001 + " mm"));
		root.add(new DefaultMutableTreeNode("Material : " + this.material.getName()));
		root.add(new DefaultMutableTreeNode("Hardness : " + this.material.getProperties().get(0).getParameterValue() + " " + this.material.getProperties().get(0).getParameterUnit()));
		return root;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Features ");
		//DefaultMutableTreeNode root1 = new DefaultMutableTreeNode("BLOCO");
		//root1.add();
				
		for (int i = 0; i < this.faces.size(); i++)
		{
			DefaultMutableTreeNode tmp;
			Face face = (Face)(this.faces.elementAt(i));
			tmp = face.getNodo();
			root.add(tmp);
		}
		return  root;
		
	}
	
	private Vector criarFaces()
	{
		Vector saida = new Vector();
		
		for(int i = 0; i < 6; i++){
			Face faceTmp = new Face(i);
			
			if(faceTmp.getTipo() == Face.XY || faceTmp.getTipo() == Face.YX){
				faceTmp.setComprimento(this.comprimento);
				faceTmp.setLargura(this.largura);
				faceTmp.setProfundidadeMaxima(this.profundidade);
			}
			else if(faceTmp.getTipo() == Face.ZX || faceTmp.getTipo() == Face.XZ){
				faceTmp.setComprimento(this.comprimento);
				faceTmp.setLargura(this.profundidade);
				faceTmp.setProfundidadeMaxima(this.largura);
			}
			else if(faceTmp.getTipo() == Face.ZY || faceTmp.getTipo() == Face.YZ){
				faceTmp.setComprimento(this.profundidade);
				faceTmp.setLargura(this.largura);
				faceTmp.setProfundidadeMaxima(this.comprimento);
			}
			else{
				//System.out.println("Bloco.criarFace: Erro (face " + (pointIndex/*+1*/) + ").");
			}
			
			//System.out.println((pointIndex + 1) + " FACE " + faceTmp.getTipoString() + ": Comprimento = " + faceTmp.getComprimento() + "\t Largura = " + faceTmp.getLargura());
			
			saida.add(faceTmp);
		}
		
		return saida;
	}

	
	public void setComprimento(double comprimento)
	{
		this.comprimento = comprimento;
	}
	public double getComprimento()
	{
		return this.comprimento;
	}
	public double getX(){
		return this.comprimento;
	}
	public void setProfundidade(double profundidade)
	{	
		this.profundidade = profundidade;
	}
	public double getProfundidade()
	{
		return this.profundidade;
	}
	public double getZ()
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
	public double getY()
	{
		return this.largura;
	}

	public Material getMaterial() 
	{
		return material;
	}

	public void setMaterial(Material material) 
	{
		this.material = material;
	}
	public double getToleranciaGlobal() {
		return toleranciaGlobal;
	}
	public Vector getFaces() {
		return faces;
	}
	public void setFaces(Vector faces) {
		this.faces = faces;
	}
}
