package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;
import java.util.Vector;

import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_workingstep;
import jsdai.SCombined_schema.AManufacturing_feature;
import jsdai.SCombined_schema.EMachining_feature;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EManufacturing_feature;
import jsdai.SCombined_schema.ERegion;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class WorkingStepsReader {

	private AMachining_workingstep aMachining_workingstep;
	private EMachining_workingstep eMachining_workingstep;
	private int size;
	private int index;
	
	public WorkingStepsReader(AMachining_workingstep aMachining_workingstep){
		this.aMachining_workingstep = aMachining_workingstep;
	}
	
	public EMachining_workingstep get(int i) throws SdaiException {
		index = i+1;
		eMachining_workingstep = aMachining_workingstep.getByIndex(index);
		return eMachining_workingstep;
	}

	public int getSize() throws SdaiException {
		SdaiIterator iterator = aMachining_workingstep.createIterator();
		size = 0;
		while(iterator.next()){
			size++;
		}
		return size;
	}
	
	public static Vector<Workingstep> getWorkingsteps(AExecutable aMachining_workingstep) throws SdaiException
	{
		Vector<Workingstep> workingsteps = new Vector<Workingstep>();
		SdaiIterator iterator = aMachining_workingstep.createIterator();
		EManufacturing_feature eFeature = null;
		Feature feature = null;
		ArrayList<EManufacturing_feature> usedFeatures = new ArrayList<EManufacturing_feature>();		
		ArrayList<Feature> usedFeaturesO = new ArrayList<Feature>();
		
		while(iterator.next()){
			
			EMachining_workingstep eMachining_workingstep = (EMachining_workingstep)aMachining_workingstep.getCurrentMember(iterator);

			eFeature = (EManufacturing_feature)eMachining_workingstep.getIts_feature(null); 
			if(!alreadyUsed(usedFeatures, eFeature))
			{
				feature = FeatureReader.getFeature(eMachining_workingstep);
				usedFeatures.add(eFeature);
				usedFeaturesO.add(feature);
				
				Face face = null;
				if(eFeature.isKindOf(EMachining_feature.class))
				{
					face = FaceReader.getFace((EMachining_feature)eFeature);
				} else if(eFeature.isKindOf(ERegion.class))
				{
					face = FaceReader.getFace((ERegion)eFeature);
				}
				feature.setFace(face);
				Ferramenta ferramenta = FerramentaReader.getFerramenta(eMachining_workingstep);
				CondicoesDeUsinagem condicoes = CondicoesDeUsinagemReader.getCondicoes(eMachining_workingstep);
				MachiningOperation operation = MachiningOperationReader.getOperation(eMachining_workingstep);
				
				Workingstep wsTmp = new Workingstep(feature , face, ferramenta, condicoes, operation);
					
				feature.getWorkingsteps().add(wsTmp);
				
				String wsId = eMachining_workingstep.getIts_id(null);
				wsTmp.setId(wsId);
				String[] arrayId = wsId.split("_");
				
				int last = arrayId.length-1;
				String wsTipo = arrayId[last];
					
				if(wsTipo.equals("RGH")){
						System.err.println("wstipo = " + wsTipo);
					wsTmp.setTipo(Workingstep.DESBASTE);
						
				}else if(wsTipo.equals("FNS")){
					System.err.println("wstipo ELSE = " + wsTipo);

					wsTmp.setTipo(Workingstep.ACABAMENTO);
						
				}else{
					System.out.println("Tipo de Ws desconhecido: " + wsTipo);
				}
				workingsteps.add(wsTmp);
				
			} else
			{
				Feature featureTmp = FeatureReader.getFeature(eMachining_workingstep);
				
				for(int i = 0; i < usedFeaturesO.size(); i++)
				{
					if(featureTmp.getClass() == usedFeaturesO.get(i).getClass() &&
							featureTmp.X == usedFeaturesO.get(i).X &&
							featureTmp.Y == usedFeaturesO.get(i).Y &&
							featureTmp.Z == usedFeaturesO.get(i).Z)
					{
						feature = usedFeaturesO.get(i);
					}
				}
				Face face = feature.getFace();
				
				Ferramenta ferramenta = FerramentaReader.getFerramenta(eMachining_workingstep);
				CondicoesDeUsinagem condicoes = CondicoesDeUsinagemReader.getCondicoes(eMachining_workingstep);
				MachiningOperation operation = MachiningOperationReader.getOperation(eMachining_workingstep);
				
				Workingstep wsTmp = new Workingstep(feature , face, ferramenta, condicoes, operation);
					
				feature.getWorkingsteps().add(wsTmp);
				
				String wsId = eMachining_workingstep.getIts_id(null);
				wsTmp.setId(wsId);
				String[] arrayId = wsId.split("_");
				
				int last = arrayId.length-1;
				String wsTipo = arrayId[last];
					
				if(wsTipo.equals("RGH")){
					wsTmp.setTipo(Workingstep.DESBASTE);
						
				}else if(wsTipo.equals("FNS")){

					wsTmp.setTipo(Workingstep.ACABAMENTO);
						
				}else{
					System.out.println("Tipo de Ws desconhecido: " + wsTipo);
				}
				workingsteps.add(wsTmp);
			}
//			System.err.println("feature --> " + feature.getNome());
//			System.err.println("efeature --> " + eFeature);
//			if((EManufacturing_feature)eMachining_workingstep.getIts_feature(null)!= eFeature){
//
//				eFeature = (EManufacturing_feature)eMachining_workingstep.getIts_feature(null); 
//				feature = FeatureReader.getFeature(eMachining_workingstep);
//			}
			
//			Face face = null;
//			if(eFeature.isKindOf(EMachining_feature.class))
//			{
//				face = FaceReader.getFace((EMachining_feature)eFeature);
//			} else if(eFeature.isKindOf(ERegion.class))
//			{
//				face = FaceReader.getFace((ERegion)eFeature);
//			}
//			feature.setFace(face);
//			Ferramenta ferramenta = FerramentaReader.getFerramenta(eMachining_workingstep);
//			CondicoesDeUsinagem condicoes = CondicoesDeUsinagemReader.getCondicoes(eMachining_workingstep);
//			MachiningOperation operation = MachiningOperationReader.getOperation(eMachining_workingstep);
//			
//			Workingstep wsTmp = new Workingstep(feature , face, ferramenta, condicoes, operation);
//				
//			feature.getWorkingsteps().add(wsTmp);
//			
//			String wsId = eMachining_workingstep.getIts_id(null);
//			wsTmp.setId(wsId);
//			String[] arrayId = wsId.split("_");
//			
//			int last = arrayId.length-1;
//			String wsTipo = arrayId[last];
//				
//			if(wsTipo.equals("RGH")){
//					System.err.println("wstipo = " + wsTipo);
//				wsTmp.setTipo(Workingstep.DESBASTE);
//					
//			}else if(wsTipo.equals("FNS")){
//				System.err.println("wstipo ELSE = " + wsTipo);
//
//				wsTmp.setTipo(Workingstep.ACABAMENTO);
//					
//			}else{
//				System.out.println("Tipo de Ws desconhecido: " + wsTipo);
//			}
//			workingsteps.add(wsTmp);
				
				//Victor está setando
				
//				Ponto[] pontosIniciais = MapeadoraDeWorkingstep.determinadorDePontos(wsTmp);
//				
//				wsTmp.setPontos(pontosIniciais);
//				
//				Vector movimentacao = DeterminarMovimentacao
//				.getPontosMovimentacao(wsTmp); // alguns vetores (4 vetores)
//				
//				wsTmp.setPontosMovimentacao(movimentacao);
			
				
		}
		
		return workingsteps;
	}
	static private Boolean alreadyUsed(ArrayList<EManufacturing_feature> usedFeatures, EManufacturing_feature eFeature)
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
}
