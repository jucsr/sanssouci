package br.UFSC.GRIMA.capp;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import br.UFSC.GRIMA.entidades.machiningResources.DrillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;

public class GraphicsElements 
{
	private MachineTool machine;
	private double zoom = 1;
	public GraphicsElements(MachineTool machine)
	{
		this.machine = machine;
	}
	public Shape createShape()
	{
		Shape machineShape = null;
		if(machine.getClass() == DrillingMachine.class)
		{
			machineShape = this.createDrillingMachineShape();
		} else if(machine.getClass() == MillingMachine.class)
		{
			machineShape = this.createMillingMachineShape();
		}
		return machineShape;
	}
	private Shape createDrillingMachineShape() 
	{
		Shape saida = null;
		saida = new Rectangle2D.Double(0, 0, 40 * zoom, 25 * zoom);
		return saida;
	}
	private Shape createMillingMachineShape() 
	{
		Shape saida = null;
		saida = new Rectangle2D.Double(0, 0, 40 * zoom, 25 * zoom);
		return saida;
	}
}
