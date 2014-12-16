package br.UFSC.GRIMA.acceptance;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_operation;
import jsdai.SCombined_schema.AMachining_tool;
import jsdai.SCombined_schema.AMilling_machine_tool_body;
import jsdai.SCombined_schema.AMilling_tool_dimension;
import jsdai.SCombined_schema.ATool_body;
import jsdai.SCombined_schema.EManufacturing_feature;
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
			Vector<Feature> usedFeatures = new Vector<Feature>();
			for(int j = 0; j< workingstepsTmp.size(); j++ )
			{
				featureTmp = workingstepsTmp.get(j).getFeature();
				if(!alreadyUsed(usedFeatures, featureTmp))
				{
					allFeatures.get(i).add(featureTmp);
					usedFeatures.add(featureTmp);
				}	
			}
		}

		setFeaturesBloco(allFeatures, this.projeto.getBloco());

		return allFeatures;
	}
	
	public void setFeaturesPrecedences(Vector<Feature> features)
	{
		Vector<Feature> featuresTmp = new Vector<Feature>();
		featuresTmp = features;
		int i, j;
		
		//for(i=0;i<featuresTmp.size();i++)
		for(i=featuresTmp.size() - 1;i >= 0;i--) //Compara do �ltimo at� o primeiro
		{
			//features.get(i).getFace().getShape(features.get(i));
			
			//featuresTmp.removeElementAt(i);
			
			//for(j=1;j<featuresTmp.size();j++) //j=1 pois a feature i=0 � a mesma j=0
			//for(j=0;j<featuresTmp.size();j++)
			for(j=featuresTmp.size() - 1;j >= 0;j--) //Compara do �ltimo at� o primeiro
			{
				Shape shapeI = featuresTmp.get(i).getFace().getShape(featuresTmp.get(i));
				Shape shapeJ = featuresTmp.get(j).getFace().getShape(featuresTmp.get(j));
				Point2D [] bordaI = featuresTmp.get(i).getFace().getShapePontos(featuresTmp.get(i));
				Point2D [] bordaJ = featuresTmp.get(j).getFace().getShapePontos(featuresTmp.get(j));

				for(int k=0; k < bordaJ.length; k++) //Checa se a feature i � precedente de j
				{
					if(shapeI.contains(bordaJ[k]))
					{
						if(featuresTmp.get(j).getFeaturePrecedente() == null) //Checa se a feature j j� possui precedente
						{
							if(featuresTmp.get(i).Z < featuresTmp.get(j).Z) //Checa se o i esta acima do j
							{	
								featuresTmp.get(j).setFeaturePrecedente(featuresTmp.get(i)); //Define a feature i como precedente de j
								featuresTmp.get(j).featureMae = featuresTmp.get(i); //Define a feature i como m�e de j
								
								featuresTmp.get(i).itsSons.add(featuresTmp.get(j)); //Adiciona a feature j como filha da feature i
								
							}
						}
					}
					
				}
				for(int k=0; k < bordaI.length; k++) //Checa se a feature j � precedente de i
				{
					if(shapeJ.contains(bordaI[k]))
					{
						if(featuresTmp.get(i).getFeaturePrecedente() == null) //Checa se a feature i j� possui precedente
						{
							if(featuresTmp.get(j).Z < featuresTmp.get(i).Z) //Checa se o j esta acima do i
							{
								featuresTmp.get(i).setFeaturePrecedente(featuresTmp.get(j)); //Define a feature j como precedente de i
								featuresTmp.get(i).featureMae = featuresTmp.get(j); //Define a feature j como m�e de i
							
								featuresTmp.get(j).itsSons.add(featuresTmp.get(i)); //Adiciona a feature i como filha da feature j
							}
						}
					}
					
				}
				
			}
			//featuresTmp.removeElementAt(i); //Remove primeiro elemento da lista
			
		}
	}
	/*public void setFeaturesPrecedences(Vector<Feature> features)
	{
		Vector<Feature> featuresTmp = new Vector<Feature>();
		featuresTmp = features;
		
				Shape shapeI = featuresTmp.get(0).getFace().getShape(featuresTmp.get(0));
				Shape shapeJ = featuresTmp.get(1).getFace().getShape(featuresTmp.get(1));
				Point2D [] bordaI = featuresTmp.get(0).getFace().getShapePontos(featuresTmp.get(0));
				Point2D [] bordaJ = featuresTmp.get(1).getFace().getShapePontos(featuresTmp.get(1));
				
				for(int k=0; k < bordaJ.length; k++) //Checa se a feature i � precedente de j
				{
					if(shapeI.contains(bordaJ[k]))
					{
						if(featuresTmp.get(1).getFeaturePrecedente() == null) //Checa se a feature j j� possui precedente
						{
							if(featuresTmp.get(0).Z < featuresTmp.get(1).Z) //Checa se o i esta acima do j
							{	
								featuresTmp.get(1).setFeaturePrecedente(featuresTmp.get(0));
							}
						}
					}
					
				}
				for(int k=0; k < bordaI.length; k++) //Checa se a feature j � precedente de i
				{
					if(shapeJ.contains(bordaI[k]))
					{
						if(featuresTmp.get(0).getFeaturePrecedente() == null) //Checa se a feature i j� possui precedente
						{
							featuresTmp.get(0).setFeaturePrecedente(featuresTmp.get(1));
						}
					}
					
				}
			
	}*/

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
	static private Boolean alreadyUsed(Vector<Feature> usedFeatures, Feature eFeature)
	{
		boolean answer = false;
		if (usedFeatures.size() > 0)
		{
			for(int i = 0; i < usedFeatures.size(); i++)
			{
				if (eFeature.equals(usedFeatures.get(i)))
				{
					answer = true;
					break;
				}
			}
		}
		return answer;
	}
	public void setWorkingstepsPrecedentes(Vector<Feature> features)
	{
		for(Feature featureTmp:features)
		{
			Vector<Workingstep> vectorWs = featureTmp.getWorkingsteps();
			if(featureTmp.getFeaturePrecedente() == null)
			{
				vectorWs.get(0).setWorkingstepPrecedente(null);
			}
			else
			{
				vectorWs.get(0).setWorkingstepPrecedente(featureTmp.getFeaturePrecedente().getWorkingsteps().lastElement());
			}
			if(vectorWs.size() > 1)
			{
				for(int i = 1;i < vectorWs.size();i++)
				{
					vectorWs.get(i).setWorkingstepPrecedente(vectorWs.get(i-1));
				}
			}
		}
	}
	public Projeto getProjeto() 
	{
		//chamar o m�todo de cria��o de preced�ncias do stepNcReader
		Vector<Feature> featuresTmp = new Vector<Feature>();
		try
		{
			getAllFeatures(getAllWorkingSteps());
		}
		catch(SdaiException e)
		{
			e.printStackTrace();
		}
		//this.setFeaturesBloco(allFeatures, bloco);
		for(int k=0;k<this.projeto.getBloco().getFaces().size();k++)
		{
			featuresTmp = ((Face)this.projeto.getBloco().getFaces().get(k)).features;
			this.setFeaturesPrecedences(featuresTmp);
			this.setWorkingstepsPrecedentes(featuresTmp);
		}
		return projeto;
	}
}
