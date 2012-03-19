package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.entidades.features.Feature;


public class AgrupadorDeWorkingSteps {
	public Vector workingsteps; // vetor de ws de uma face *******???????
	public Vector grupos = new Vector();
	
	public AgrupadorDeWorkingSteps(Vector vetorWorkingstep)
	{
		this.workingsteps = vetorWorkingstep;
		this.grupos = this.agrupar();
		this.imprimirDados();
	}
	/**
	 * 
	 * @param workingStepsDoBloco -> vetor de vetores das workinSteps de cada uma das faces
	 * @return -> Vetor de vetores agrupados por camadas
	 */
	/*public Vector agruparCamadas(Vector workingStepsDoBloco)
	{
		Vector saida = new Vector();
		for(int pointIndex = 0; pointIndex < workingStepsDoBloco.size(); pointIndex++)
		{
			Vector workingStepsFaceTmp = (Vector)workingStepsDoBloco.elementAt(pointIndex);
			Vector camadas = this.agruparPorCamadas(workingStepsFaceTmp);
			saida.add(camadas);
		}
		return saida;
	}*/
	public Vector agrupar()
	{
		Vector saida = new Vector();
		for (int i= 0; i < this.workingsteps.size(); i++)
		{
			Vector vTmp = (Vector)this.workingsteps.elementAt(i);//vetor de ws de uma face
			//System.out.println("Face------>>>" + new Face(pointIndex).getTipoString());
			//saida.add(this.agruparFace(vTmp));
			saida.add(this.agruparPorCamadas(vTmp));
		}
		return saida;
	}
	/**
	 * 
	 * @param vetorFace Vetor de Workingsteps de uma face
	 * @return
	 */
	public Vector agruparFace(Vector vetorFace){
		Vector saida = new Vector();
		Vector tmp = new Vector();
		
		for(int i = 0; i < vetorFace.size(); i++){
			Workingstep wsTmp = (Workingstep)vetorFace.elementAt(i);
			Feature fTmp = (Feature)wsTmp.getFeature();
			
			//System.out.println(fTmp.getDados()); -------->

		}
		
		//System.out.println("********** Workingsteps (Tamanho do vetor WS): " + vetorFace.size() + " ***********");
		boolean terminou = false;
		while(!terminou){
			/*for(vetorFace){
			 *procurar ws cuja br.UFSC.GRIMA.feature nao possui mae e nem anteriores -> garante q a br.UFSC.GRIMA.feature tem z == 0
			 * para cada ws encontrado deve-se criar um grupo de workingsteps
			 * if(nao tem mae e naum tem anteriores){
			 * 	for(vetorFace){
			 *		if(wstemp2.feature.getmae == wstmp.feature){
			 *			adiciona o workingstep no grupo de workingsteps como filha
			 * }
			 * }
			 * }
			 * }
			 */
			for (int i = 0; i < vetorFace.size(); i++)
			{
				Workingstep wTmp = (Workingstep)vetorFace.elementAt(i);
				Feature fTmp = wTmp.getFeature();
				
				if (fTmp.featureMae == null && fTmp.featuresAnteriores == null)//isto garante que esta em Z = 0
				{
					GrupoDeWorkingSteps gws = new GrupoDeWorkingSteps();
					gws.maes.add(wTmp);
					for (int j = 0; j < vetorFace.size(); j ++)
					{
						Workingstep wTmp2 = (Workingstep)vetorFace.elementAt(j);
						Feature fTmp2 = wTmp2.getFeature();
						//System.out.println("%%%%%%%%%%" + fTmp2.featureMae);
						if(fTmp2.featureMae != null){
							if(fTmp2.featureMae.equals(fTmp))
							{
								gws.filhas.add(wTmp2);
								//System.out.println("gws.filhas: " + gws.filhas);
							}
						}
					}
					saida.add(gws);
					//System.out.println(gws.getDados());
				}
			}
			terminou = true;
		}		
		return saida;
	}
	public Vector agruparPorCamadas(Vector workingStep)
	{
		Vector saida = new Vector();
		double zAtual = -1;
		boolean terminou = false;
		while (!terminou)
		{
			double zTmp = -1;
			for (int i = 0; i < workingStep.size(); i++)
			{
				Workingstep wsTmp = (Workingstep)workingStep.elementAt(i);
				Feature fTmp = wsTmp.getFeature();
				
				if (zTmp == -1 && fTmp.getPosicaoZ() > zAtual)
				{
					zTmp = fTmp.getPosicaoZ();
				}
				else if(fTmp.getPosicaoZ() < zTmp && fTmp.getPosicaoZ() > zAtual)
				{
					zTmp = fTmp.getPosicaoZ();
				}
			}
			//System.out.println("Camada Z = " + zTmp);
			if(zTmp == -1){
				terminou = true;
			}
			else{
				zAtual = zTmp;
				
				Vector novo = new Vector();
				
				for(int j = 0; j < workingStep.size(); j ++){
					Workingstep wsTmp2 = (Workingstep)workingStep.elementAt(j);
					Feature fTmp2 = wsTmp2.getFeature();
					if (fTmp2.getPosicaoZ() == zAtual)
					{
						novo.add(wsTmp2);
					}
				}
				saida.add(novo);
				// System.out.println("Sequencias possiveis:");
				//this.gerarSequencias(novo.size());
				//System.out.println("Tamanho do grupo: " + novo.size());
			}
			
		}
		
		/*for(int pointIndex = 0; pointIndex < saida.size(); pointIndex++){
			Vector camadaTmp = (Vector)saida.elementAt(pointIndex);
			
			System.out.println("Camada -> z= " + ((Workingstep)camadaTmp.elementAt(0)).getFeature().getPosicaoZ());
			
			for(int j = 0; j < camadaTmp.size(); j++){
				Workingstep wsTmp = (Workingstep)camadaTmp.elementAt(j);
				System.out.println(wsTmp.getDados());
			}
		}*/
		//this.gerarSequencias(saida.size());
		//System.out.println("saida.size: " + saida.size());
		return saida;
	}
	
	public void imprimirDados(){
		//imprimir os dados do vetor de grupos
		System.out.println("\n");
		System.out.println("=======================================================");
		System.out.println("==        Dados do Agrupador de Workingsteps         ==");
		System.out.println("=======================================================");		
		
		
		for(int i = 0; i < this.grupos.size(); i++){
			Vector camadasDaFaceI = (Vector)this.grupos.elementAt(i);
			
			System.out.printf("..:: Face #%d (possui %d camadas) ::..\n", i, camadasDaFaceI.size());
			
			for(int j = 0; j < camadasDaFaceI.size(); j++){
				Vector camadaJ = (Vector)camadasDaFaceI.elementAt(j);
				System.out.printf("\tCamada #%d (possui %d workingsteps)\n", j, camadaJ.size());
				
				for(int k = 0; k < camadaJ.size(); k++){
					Workingstep wsTmp = (Workingstep)camadaJ.elementAt(k);
					
					System.out.printf("\t\tWorkingstep #%d (%s)\n", k, wsTmp.getFeature().toString());
				}
				System.out.println("");
			}
		}
		System.out.println("=======================================================");
	}

}
