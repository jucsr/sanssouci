package br.UFSC.GRIMA.integracao;

import java.io.IOException;
import java.util.ArrayList;

import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.AProject;
import jsdai.SCombined_schema.ASurface_texture_parameter;
import jsdai.SCombined_schema.AWorkpiece;
import jsdai.SCombined_schema.EExecutable;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.ENumeric_parameter;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.EProperty_parameter;
import jsdai.SCombined_schema.ESurface_texture_parameter;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import jsdai.lang.SdaiModel;
import br.UFSC.GRIMA.STEPmanager.Util;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ProjectReader {
	ASdaiModel aSdaiModel;
	EProject eProject;
	EWorkplan eWorkplan;
	AWorkpiece aWorkpiece;
	EWorkpiece eWorkpiece;
	ASurface_texture_parameter aSurface_texture_parameter;
	Util util;
	public static final int FILE_21 = 0;
	public static final int FILE_XML = 1;
	
	public ProjectReader(ASdaiModel model) {
		aSdaiModel = model;
		try {
			getProject();
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}

	public ProjectReader(String path, int tipo) {
		
		if(tipo == 0)
		{
			aSdaiModel = openFile21(path);
		}else if (tipo == 1)
		{
			aSdaiModel = openFileXML(path);
		}
		
		try {
			getProject();
//			util.closeProject();
		} catch (SdaiException e1) {
			e1.printStackTrace();
		}
	}
	public ProjectReader()
	{
		
	}
	public ASdaiModel openFile21(String sourcePath) {
		util = new Util();
		try {
			return util.openFile21(sourcePath);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ASdaiModel openFileXML(String sourcePath)
	{
		util = new Util();
		try {
			try {
				return util.openFileXML(sourcePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SdaiException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Projeto getProjeto(){
		
		Projeto projeto = null;
		
		try {
			
			System.out.println("EPROJECT : " + eProject);
			System.out.println("EPROJECTTT : " + eProject.getIts_id(null));
			
			EWorkpiece eWorkpiece = eProject.getIts_workpieces(null).getByIndex(1);
			BlocoReader blocoReader = new BlocoReader(eWorkpiece);
			
			Bloco bloco = blocoReader.getBloco();
			
			
			
			Material material = new Material();
			material.setName(eWorkpiece.getIts_material(null).getStandard_identifier(null));
			material.setType(eWorkpiece.getIts_material(null).getMaterial_identifier(null));
			material.getCategoryMaterial();
			
			ArrayList<PropertyParameter> parameters = new ArrayList<PropertyParameter>();
			
			SdaiIterator iterator = eWorkpiece.getIts_material(null).getMaterial_property(null).createIterator();
			
			ENumeric_parameter eNumeric_parameter = null;
			EProperty_parameter eProperty_parameter = null;

			while(iterator.next()){

				PropertyParameter parameter = new PropertyParameter();
				eProperty_parameter = eWorkpiece.getIts_material(null).getMaterial_property(null).getCurrentMember(iterator);
				
				if(eProperty_parameter.isKindOf(ENumeric_parameter.class))
				{
					eNumeric_parameter = (ENumeric_parameter)eWorkpiece.getIts_material(null).getMaterial_property(null).getCurrentMember(iterator);
					
					parameter.setParameterValue(eNumeric_parameter.getIts_parameter_value(null));
					parameter.setParameterUnit( eNumeric_parameter.getIts_parameter_unit(null));
				}
				parameter.setParameterName(eProperty_parameter.getParameter_name(null));
				
				parameters.add(parameter);

			}
			
			material.setProperties(parameters);
			
			bloco.setMaterial(material);
			
			String nomeProjeto = eProject.getIts_id(null);
			String userName = "user default";
			int idUser = 0;
			
//			if(eProject.getIts_owner(null) != null)
			try
			{
				userName = eProject.getIts_owner(null).getIts_person(null).getFirst_name(null);
				
			} catch(SdaiException e)
			{
//				e.printStackTrace();
				System.out.println("Advertencia : its_owner not set" );
			}
			
//			if (eProject.getIts_owner(null).getIts_person(null).getId(null) != null)
			try
			{
				idUser = Integer.parseInt(eProject.getIts_owner(null).getIts_person(null).getId(null));	
			} catch(SdaiException e)
			{
//				e.printStackTrace();
				System.out.println("Advertencia : its_id not set" );

			}
			DadosDeProjeto dados = new DadosDeProjeto(idUser,userName, nomeProjeto ,material);
			
			projeto = new Projeto(bloco, dados);
			
		} catch (SdaiException e) {
			e.printStackTrace();
		}
		
		
		return projeto;
		
	}

	public EProject getProject() throws SdaiException {

		SdaiIterator modelIterator = aSdaiModel.createIterator();

		while (modelIterator.next()) {

			SdaiModel model = aSdaiModel.getCurrentMember(modelIterator);
//			if (model.getMode() == SdaiModel.NO_ACCESS) {
//				model.startReadOnlyAccess();
//			}
			AProject aProject = (AProject) model.getInstances(EProject.class);

			SdaiIterator projectIterator = aProject.createIterator();
			while (projectIterator.next()) {
				eProject = aProject.getCurrentMember(projectIterator);
			}
		}
		return eProject;
	}

	public EWorkplan getWorkplan() throws SdaiException {

		eWorkplan = (EWorkplan) eProject.getMain_workplan(null);

		return eWorkplan;
	}

	public EWorkpiece getWorkpiece() throws SdaiException {

		aWorkpiece = eProject.getIts_workpieces(null);
		SdaiIterator workpieceIterator = aWorkpiece.createIterator();
		while (workpieceIterator.next()) {
			eWorkpiece = aWorkpiece.getCurrentMember(workpieceIterator);
		}
		return eWorkpiece;

	}

	public ASurface_texture_parameter getSurfaceTextureParameter() throws SdaiException {

		SdaiIterator modelIterator = aSdaiModel.createIterator();
		

		while (modelIterator.next()) {

			SdaiModel model = aSdaiModel.getCurrentMember(modelIterator);
			if (model.getMode() == SdaiModel.NO_ACCESS) {
				model.startReadOnlyAccess();
			}
			aSurface_texture_parameter = (ASurface_texture_parameter) model
					.getInstances(ESurface_texture_parameter.class);
		}
		
		return aSurface_texture_parameter;

	}

	
	public String getNome() throws SdaiException {
		String projectName = eProject.getIts_id(null);
		return projectName;
	}
	public AMachining_workingstep getWorkingSteps() throws SdaiException {
		
		eWorkplan = this.getWorkplan();
		AExecutable aSetupWorkplan = eWorkplan.getIts_elements(null);
		//AQUI ESTAMOS PEGANDO SOMENTE O SETUP XY!!!
		AExecutable aWorkingstep = ((EWorkplan)aSetupWorkplan.getByIndex(1)).getIts_elements(null);
		AMachining_workingstep aMachiningWorkingstep = new AMachining_workingstep();
		SdaiIterator iterator = aWorkingstep.createIterator();
		int i = 1;
		while(iterator.next()){
			
			EExecutable eExecutable = aWorkingstep.getCurrentMember(iterator);
			
			if(eExecutable.isKindOf(EMachining_workingstep.class)){
				
				aMachiningWorkingstep.addByIndex(i, eExecutable);
				i++;
			}
			
		}
		return aMachiningWorkingstep;
	}
	public AExecutable getAllWorkplans() throws SdaiException
	{
		this.eWorkplan = this.getWorkplan();
		AExecutable aExecutable = this.eWorkplan.getIts_elements(null);
		return aExecutable;
	}
}
