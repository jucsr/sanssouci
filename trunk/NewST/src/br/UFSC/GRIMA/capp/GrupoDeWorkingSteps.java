package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.entidades.features.Feature;


public class GrupoDeWorkingSteps {
	public Vector maes = new Vector();
	public Vector filhas = new Vector();
	
	public GrupoDeWorkingSteps(){
		
	}
	
	public void imprimirDados(Workingstep ws)
	{
		Feature feature = ws.getFeature();
		System.out.println(feature.getTipoString() + " "+ feature.featureMae);
		System.out.println(feature.getTipoString() + " " + feature.featuresAnteriores);
	}
	
	public String getDados(){
		String saida = "GrupoDeWorkingsteps -------------------------------\n";
		
		if(this.maes.size() != 0){
			saida += "Vetor de M�es:\n";
			for(int i = 0; i < this.maes.size(); i++){
				Workingstep wsTmp = (Workingstep)this.maes.elementAt(i);
				saida += wsTmp.getDados("");
			}
		}
		
		if(this.filhas.size() != 0){
			saida += "Vetor de Filhas:\n";
			for(int i = 0; i < this.filhas.size(); i++){
				Workingstep wsTmp = (Workingstep)this.filhas.elementAt(i);
				saida += wsTmp.getDados("");
			}		
		}
		return saida;
	}
}
