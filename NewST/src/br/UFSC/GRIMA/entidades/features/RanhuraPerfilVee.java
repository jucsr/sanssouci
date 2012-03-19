package br.UFSC.GRIMA.entidades.features;

/**
 * 
 * @author Jc
 *
 */
public class RanhuraPerfilVee extends Ranhura
{
	private double angulo;
	private double raio;

	public RanhuraPerfilVee() 
	{
	}
	public RanhuraPerfilVee(String id, double x, double y, double z, double locX, double locY, double locZ,
			double larguraRanhura, double profundidadeRanhura, int eixoAtual, double raio,
			double angulo) {
		super(id,x,y,z,locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual);
		setRaio(raio);
		setAngulo(angulo);
	}
	public double getAngulo() 
	{
		return angulo;
	}
	public void setAngulo(double angulo) 
	{
		this.angulo = angulo;
	}
	public double getRaio() {
		return raio;
	}
	public void setRaio(double raio) {
		this.raio = raio;
	}
}