package br.UFSC.GRIMA.entidades;

import br.UFSC.GRIMA.util.projeto.Projeto;
import jsdai.lang.SdaiException;

public class ProjectManager {

	static STEPWorkingStepsProject project;

	public static boolean hasProject(){
		if(project==null)
			return false;
		
		return true;
	}
	
	public static STEPWorkingStepsProject getProject() throws SdaiException {

		if (project == null) {
			project = new STEPWorkingStepsProject();
		}

		return project;
	}

	
	public static STEPWorkingStepsProject resetProject() throws SdaiException {

		project = new STEPWorkingStepsProject();

		return project;
	}
	
	public static void clearProject() throws SdaiException {

		project = null;
	}

	public static void setMaterialType(STEPWorkingStepsProject project, int selectedIndex) throws SdaiException {
	
		Material material = new Material();
		
		switch (selectedIndex) {
		case 0:
			material.setName("SAE1015");
			material.setType("Steel");
			material.setPropertie("315Mpa");
			break;
		case 1:
			material.setName("SAE1020");
			material.setType("Steel");
			material.setPropertie("330Mpa");
			break;
		case 2:
			material.setName("SAE1030");
			material.setType("Steel");
			material.setPropertie("345Mpa");
			break;
		default:
			break;
		}
		project.setMaterial(material.getName(), material.getType(), material.getPropertie());
	}


}
