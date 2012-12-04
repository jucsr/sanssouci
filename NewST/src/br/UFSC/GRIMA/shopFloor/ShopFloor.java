package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;

/**
 * 
 * @author Pedro
 *
 */
public class ShopFloor {
	private ArrayList<MachineTool> machines;
	private double length;
	private double width;
	private String name;
	private String description;
	private String organization;
	private int userID;
	private ManufacturingOrder manufacturiongOrder;

	public ShopFloor(String name, int userID, double length, double width)
	{
		this.name = name;
		this.length = length;
		this.width = width;
		this.userID = userID;
	}
	
	public ArrayList<MachineTool> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<MachineTool> machines) {
		this.machines = machines;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
}
