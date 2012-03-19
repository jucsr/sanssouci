package br.UFSC.GRIMA.util;

import br.UFSC.GRIMA.entidades.features.Feature;

public class PassaParaString {

	public PassaParaString() {
		// TODO Auto-generated constructor stub
	}

	public static String featureToString(Feature feature, String tab){
		String saida = "";

		saida += (tab + feature.getTipoString() + " (" + feature.getIndice() + ")\n");

		if(feature.featureMae != null){
			saida += (tab + "  Feature Mae: " + feature.featureMae.toString());
		}
		if(feature.featuresAnteriores != null){
			saida += (tab + "  Features Anteriores:\n");
			for(int i = 0; i < feature.featuresAnteriores.size(); i++){
				saida += (tab + tab + "#" + i + ": " + ((Feature)feature.featuresAnteriores.elementAt(i)).toString());
			}
		}
		saida += "\n";

		return saida;
	}
}
