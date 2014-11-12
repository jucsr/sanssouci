package br.UFSC.GRIMA.capp.plunge;

import javax.vecmath.Point3d;
/**
 * 
 * @author Jc
 *
 */
public abstract class ApproachRetractStrategy 
{
	private Point3d toolDirection = new Point3d(1, 0, 0); // Vetor diretor do mergulho inicializado para entrada vertical

	public ApproachRetractStrategy(Point3d toolDirection)
	{
		this.toolDirection = toolDirection;
	}

	public Point3d getToolDirection()
	{
		return toolDirection;
	}

	public void setToolDirection(Point3d toolDirection)
	{
		this.toolDirection = toolDirection;
	}
	
}
