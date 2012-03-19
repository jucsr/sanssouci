package br.UFSC.GRIMA.entidades.features;

public class RanhuraPerfilCircularParcial extends Ranhura
{
	private double raio;
	private double angulo;
	private double dz;
	
	public RanhuraPerfilCircularParcial() {
		// TODO Auto-generated constructor stub
	}
	public RanhuraPerfilCircularParcial(String id, double x, double y,
			double z, double locX, double locY, double locZ, double larguraRanhura, double profundidadeRanhura,
			int eixoAtual, double raio, double angulo, double dz) {
		
		super(id, x, y, z, locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual);
		setRaio(raio);
		setAngulo(angulo);
		setDz(dz);
	}

	public double getRaio() {
		return raio;
	}
	public void setRaio(double raio) {
		this.raio = raio;
	}
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	public double getDz() {
		return dz;
	}
	public void setDz(double dz) {
		this.dz = dz;
	}
}
