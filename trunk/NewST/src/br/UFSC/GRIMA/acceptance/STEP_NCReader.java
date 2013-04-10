package br.UFSC.GRIMA.acceptance;

import java.util.Vector;

import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_operation;
import jsdai.SCombined_schema.AMachining_tool;
import jsdai.SCombined_schema.AMilling_machine_tool_body;
import jsdai.SCombined_schema.AMilling_tool_dimension;
import jsdai.SCombined_schema.ATool_body;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.integracao.BlocoReader;
import br.UFSC.GRIMA.integracao.CondicoesDeUsinagemReader;
import br.UFSC.GRIMA.integracao.ProjectReader;
import br.UFSC.GRIMA.integracao.WorkingStepsReader;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class STEP_NCReader  {
	
	AExecutable aMachiningWorkingstep;
	AMachining_operation aMachiningOperation;
	AMachining_tool aMachiningTool;
	AMilling_machine_tool_body aMilling_tool_body = new AMilling_machine_tool_body();
	ATool_body aTool_body;
	AMilling_tool_dimension aTool_dimension;
	String path;
	public ProjectReader projectReader;
	
	CondicoesDeUsinagemReader condicoesReader;
	
	public Projeto projeto;

	public STEP_NCReader(ASdaiModel model) {
		
		projectReader = new ProjectReader(model);

		projeto = projectReader.getProjeto();
	}

	public STEP_NCReader(String path, int tipo) {
		
		projectReader = new ProjectReader(path, tipo);
		
		projeto = projectReader.getProjeto();

	}
//	public STEP_NCReader()
//	{
//		projectReader = new ProjectReader();
//	
//		projeto = projectReader.getProjeto();
//	}
	public Rectangle3D getPieceProps() {
		try {
			EWorkpiece workpiece = projectReader.getWorkpiece();
			BlocoReader reader = new BlocoReader(workpiece);
			return new Rectangle3D((int) reader.getComprimento(), (int) reader
					.getLargura(), reader.getProfundidade());
		} catch (SdaiException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Vector<Vector<Workingstep>> getAllWorkingSteps() throws SdaiException
	{
		Vector<Vector<Workingstep>> allWorkingsteps = new Vector<Vector<Workingstep>>();
		AExecutable aExecutable = this.projectReader.getAllWorkplans();
		SdaiIterator iterator = aExecutable.createIterator();
		
		while(iterator.next())
		{
			Vector<Workingstep> wssFace = new Vector<Workingstep>();
		
			EWorkplan eWorkplanFace = (EWorkplan)aExecutable.getCurrentMember(iterator);
			
			AExecutable aMachining_workingstep = eWorkplanFace.getIts_elements(null);
			
			wssFace = WorkingStepsReader.getWorkingsteps(aMachining_workingstep);
			
			allWorkingsteps.add(wssFace);
		
		}
			
		this.projeto.setWorkingsteps(allWorkingsteps);
		this.projeto.setAllToolsFromWs(allWorkingsteps);
		
		return allWorkingsteps;
	}
	
	public Vector<Vector<Feature>> getAllFeatures(Vector<Vector<Workingstep>> allWorkingsteps) throws SdaiException
	{
		Vector<Vector<Feature>> allFeatures = new Vector<Vector<Feature>>();

		Feature featureTmp = null;

		for(int i = 0; i<allWorkingsteps.size(); i++){

			allFeatures.add(new Vector<Feature>());

			Vector<Workingstep> workingstepsTmp = allWorkingsteps.get(i);

			for(int j = 0; j< workingstepsTmp.size(); j++ )
				
				if(workingstepsTmp.get(j).getFeature()!= featureTmp){

					featureTmp = workingstepsTmp.get(j).getFeature();
					
					allFeatures.get(i).add(featureTmp);

				}	

		}

		setFeaturesBloco(allFeatures, this.projeto.getBloco());
		
		return allFeatures;
	}

	public void setFeaturesBloco(Vector<Vector<Feature>> allFeatures, Bloco bloco){
		
		Face faceTmp;
		
		for(int i = 0; i<allFeatures.size(); i++){
			
			int faceIndex = ((Feature)allFeatures.get(i).get(0)).getFace().getTipo();
			
			faceTmp	= (Face) bloco.getFaces().get(faceIndex);
			
			for(int j = 0; j < allFeatures.get(i).size(); j++){
				
				faceTmp.addFeature(allFeatures.get(i).get(j));
				
			}
		}
	}
	
	public Projeto getProjeto() {
		return projeto;
	}
}
