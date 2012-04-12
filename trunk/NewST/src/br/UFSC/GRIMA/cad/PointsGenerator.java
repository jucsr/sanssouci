package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class PointsGenerator {
	private Projeto projeto;
	private ArrayList <Face>setups;
	private int setupNumber;
	private double raio;
	private double xForbiddenInitial;
	public PointsGenerator(Projeto projeto, double raio){
		this.projeto = projeto;
		this.raio = raio;
		this.pointgen();
	}
	private void pointgen() {
		//Procura por FEATURES nas faces
		for (int i=0; i<projeto.getBloco().faces.size(); i++)						//Procura por FEATURES em cada face
		{
			Face faceTmp = (Face)projeto.getBloco().faces.elementAt(i);	
			if (faceTmp.features.size() > 0)										//Se a face possui FEATURES
			{
				for (int j=0; j<faceTmp.features.size(); j++){						//Procura por FEATURES PASSANTES
					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
					if (featureTmp.getClass() == Cavidade.class){					//caso CAVIDADE
						Cavidade cavidade = (Cavidade)featureTmp;
						if(cavidade.isPassante()){
							cavidade.X
							point3d.
							/**
							 * 
							 */
							
						}
					}
					if (featureTmp.getClass() == FuroBasePlana.class){				//caso FURO
						FuroBasePlana furo = (FuroBasePlana)featureTmp;
						if(furo.isPassante()){
							/**
							 * 
							 */
						}
					}
				}
				
//				faceTmp.setPontosDeApoio(pontosDeApoio);
				setupNumber++;
				
			}
		}
	}
}
