package br.UFSC.GRIMA.entidades.features;

public class RanhuraPerfilQuadradoU extends Ranhura
{
	private double angulo;
	private double largura2;
	private double raio;
	
	
	public RanhuraPerfilQuadradoU() {
		// TODO Auto-generated constructor stub
	}
	public RanhuraPerfilQuadradoU(String id, double x, double y, double z, double locX, double locY, double locZ,
			double larguraRanhura, double profundidadeRanhura, int eixoAtual,
			double raio, double angulo, double largura2) {
		super(id,x,y,z,locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual);
		setRaio(raio);
		setAngulo(angulo);
		setLargura2(largura2);
	}
	
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	public double getLargura2() {
		return largura2;
	}
	public void setLargura2(double largura2) {
		this.largura2 = largura2;
	}
	public double getRaio() {
		return raio;
	}
	public void setRaio(double raio) {
		this.raio = raio;
	}
}
