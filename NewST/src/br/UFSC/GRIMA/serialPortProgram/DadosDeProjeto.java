package br.UFSC.GRIMA.serialPortProgram;

public class DadosDeProjeto {
	private int userID = -1;
	private String userName = "Teste";
	private String projectName = "Projeto teste";
	private int quantidade = 0;
	private String material = "";
	
	public DadosDeProjeto(){
		
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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
	
	public void printData(){
		System.out.println("User: " + this.getUserName());
		System.out.println("Project: " + this.getProjectName());
	}
}
