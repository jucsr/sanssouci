package br.UFSC.GRIMA.STEPmanager;

import jsdai.SCombined_schema.AProject;
import jsdai.SCombined_schema.EPerson_and_address;
import jsdai.SCombined_schema.EProject;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import jsdai.lang.SdaiModel;

public class DadosProjeto {

	public DadosProjeto() {
	}

	public static EProject getProjectE() throws SdaiException {

		// AProject projects = null;
		EProject projectE = null;
		SdaiModel model = LerArquivoStep.model;
		ASdaiModel models = LerArquivoStep.repository.getModels();
		SdaiIterator modelIterator = models.createIterator();
		while (modelIterator.next()) {

			model = models.getCurrentMember(modelIterator);
			if (model.getMode() == SdaiModel.NO_ACCESS) {
				model.startReadOnlyAccess();
			}

			AProject projects = (AProject) model.getInstances(EProject.class);
			SdaiIterator projectIterator = projects.createIterator();
			while (projectIterator.next()) {
				EProject project = projects.getCurrentMember(projectIterator);
				projectE = project;
			}

		}
		return projectE;
	}

	public static String getProjectName() throws SdaiException {

		String projectName = getProjectE().getIts_id(null);
		return projectName;

	}
	
	public static EPerson_and_address getProjectOwner() throws SdaiException{
		
		EPerson_and_address projectOwner = null;
		if (getProjectE().testIts_owner(null)){
		 EPerson_and_address pOwner = getProjectE().getIts_owner(null);
		 projectOwner = pOwner;
		}
		return projectOwner; 
	}
	
}
