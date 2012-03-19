package br.UFSC.GRIMA.util.projeto;

import java.io.Serializable;

import br.UFSC.GRIMA.entidades.Material;


public class DadosDeProjeto implements Serializable {
	
	private int userID = -1;
	private String userName = "Teste";
	private String projectName = "Projeto teste", description = "", organization = "";
	private int selectedMaterial;
	private Material material;


	public DadosDeProjeto(int userID, String userName, String projectName,
			int selectedMaterial) {
		this.userID = userID;
		this.userName = userName;
		this.projectName = projectName;
		this.selectedMaterial = selectedMaterial;
	}
	
	public DadosDeProjeto(int userID, String projectName) {
		this.userID = userID;
		this.projectName = projectName;
	}
	
	public DadosDeProjeto(int userID, String userName, String projectName, Material material)
	{
		this.userID = userID;
		this.userName = userName;
		this.projectName = projectName;
		this.material = material;
	}
	public DadosDeProjeto(int userID, String userName, String projectName, Material material, String description, String organization)
	{
		this.userID = userID;
		this.userName = userName;
		this.projectName = projectName;
		this.material = material;
		this.description = description;
		this.organization = organization;
	}
	public int getSelectedMaterial() {
		return selectedMaterial;
	}

	public void setSelectedMaterial(int selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void printData() {
		System.out.println("User: " + this.getUserName());
		System.out.println("Project: " + this.getProjectName());
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return material;
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
