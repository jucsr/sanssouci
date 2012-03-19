package br.UFSC.GRIMA.entidades.features;

import javax.vecmath.Point3d;

/**
 * 
 * @author Jc
 *
 */
public class RanhuraPerfilBezier extends Ranhura
{
	private Point3d [] pontosDeControle;
	private boolean emTodaAPeca = false;
	private Point3d[] pontosDaCurva;
	private double profundidadeMaxima;
	
	public RanhuraPerfilBezier() 
	{
		
	}
	
	public RanhuraPerfilBezier(String id, double x, double y, double z,double locX,double locY,double locZ,
			double larguraRanhura, double profundidadeRanhura, int eixoAtual,
			Point3d[] pontosDeControle) {
		super(id,x,y,z,locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual);
		setPontosDeControle(pontosDeControle);
	}
	
	public Point3d[] getPontosDeControle() 
	{
		return pontosDeControle;
	}
	public void setPontosDeControle(Point3d[] pontosDeControle) 
	{
		this.pontosDeControle = pontosDeControle;
	}

	public boolean isEmTodaAPeca() {
		return emTodaAPeca;
	}

	public void setEmTodaAPeca(boolean emTodaAPeca) {
		this.emTodaAPeca = emTodaAPeca;
	}

	public Point3d[] getPontosDaCurva() {
		return pontosDaCurva;
	}

	public void setPontosDaCurva(Point3d[] pontosDaCurva) {
		this.pontosDaCurva = pontosDaCurva;
	}

	public double getProfundidadeMaxima() {
		return profundidadeMaxima;
	}

	public void setProfundidadeMaxima(double profundidadeMaxima) {
		this.profundidadeMaxima = profundidadeMaxima;
	}
}
