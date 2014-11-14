package br.UFSC.GRIMA.capp.movimentacoes.estrategias;

import javax.vecmath.Point3d;

public class UVStrategy extends FreeFormStrategy
{
	private Point3d forwardDirection = new Point3d(1, 0, 0); // direcao de avanco
	private Point3d sidewardDirection = new Point3d(0, 1, 0); //direcao de avanco lateral
	public Point3d getForwardDirection()
	{
		return forwardDirection;
	}
	public void setForwardDirection(Point3d forwardDirection) 
	{
		this.forwardDirection = forwardDirection;
	}
	public Point3d getSidewardDirection() 
	{
		return sidewardDirection;
	}
	public void setSidewardDirection(Point3d sidewardDirection)
	{
		this.sidewardDirection = sidewardDirection;
	}
}
