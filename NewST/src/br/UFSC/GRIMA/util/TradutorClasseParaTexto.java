package br.UFSC.GRIMA.util;

import java.util.Vector;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Maquina;
import br.UFSC.GRIMA.util.projeto.OrdemDeFabricacao;
import br.UFSC.GRIMA.util.projeto.Projeto;



public class TradutorClasseParaTexto {

	public TradutorClasseParaTexto() {
		// TODO Auto-generated constructor stub
	}
	
	public static String featureParaTexto(Feature feature){
		String saida = "";
		
		saida += "Tipo: " + feature.getTipo() + "\n";
		saida += "X: " + feature.X + "\n";
		saida += "Y: " + feature.Y + "\n";
		saida += "Z: " + feature.Z + "\n";
		saida += "Indice: " + feature.getIndice() + "\n";
		
		if(feature.featureMae != null){
			saida += featureParaTexto(feature.featureMae);
		}
		if(feature.featuresAnteriores != null){
			saida += vetorDeFeaturesParaTexto(feature.featuresAnteriores);
		}
		
		saida += "\n&\n";
		
		switch(feature.getTipo()){
		case Feature.FURO:
			saida += furoParaTexto((Furo)feature);
			break;
		case Feature.DEGRAU:
			saida += degrauParaTexto((Degrau)feature);
			break;
		case Feature.RANHURA:
			saida += ranhuraParaTexto((Ranhura)feature);
			break;
		case Feature.CAVIDADE:
			saida += cavidadeParaTexto((Cavidade)feature);
			break;
		}
			
		return saida;
	}
	
	public static String furoParaTexto(Furo furo){
		String saida = "\n";
		
		saida += "Raio: " + furo.getRaio() + "\n";
		saida += "Profundidade: " + furo.getProfundidade() + "\n";
		
		return saida;
	}

	public static String degrauParaTexto(Degrau degrau){
		String saida = "\n";
		
		saida += "Eixo: " + degrau.getEixo() + "\n";
		saida += "Largura: " + degrau.getLargura() + "\n";
		saida += "Profundidade: " + degrau.getProfundidade() + "\n";
		
		return saida;
	}
	
	public static String ranhuraParaTexto(Ranhura ranhura){
		String saida = "\n";
		
		saida += "Eixo: " + ranhura.getEixo() + "\n";
		saida += "Largura: " + ranhura.getLargura() + "\n";
		saida += "Profundidade: " + ranhura.getProfundidade() + "\n";
		
		return saida;
	}
	
	public static String cavidadeParaTexto(Cavidade cavidade){
		String saida = "\n";
		
		saida += "Raio: " + cavidade.getRaio() + "\n";
		saida += "Largura: " + cavidade.getLargura() + "\n";
		saida += "Comprimento: " + cavidade.getComprimento() + "\n";
		saida += "Profundidade: " + cavidade.getProfundidade() + "\n";		
		
		return saida;
	}
	
	public static String vetorDeFeaturesParaTexto(Vector features){
		String saida = "\n{";
		
		for(int i = 0; i < features.size(); i++){
			Feature featureTmp = (Feature)features.elementAt(i);
			
			saida += "[\n";
			saida += featureParaTexto(featureTmp);
			saida += "\n]\n";
		}
		
		saida += "}\n";
		
		return saida;
	}
	
	public static String faceParaTexto(Face face){
		String saida = "";
		
		saida += "Tipo: " + face.getTipo() + "\n";
		saida += "Comprimento: " + face.getComprimento() + "\n";
		saida += "Largura: " + face.getLargura() + "\n";
		saida += "Profundidade Maxima: " + face.getProfundidadeMaxima() + "\n";
		saida += "Vertice Ativado: " + face.verticeAtivado + "\n";

		String array = "[";
		for(int i = 0; i < face.indices.length; i++){
			array += face.indices[i];
			
			if(i < face.indices.length - 1){
				array += ", ";
			}
		}
		array += "]\n";
		saida += array;
		
		saida += vetorDeFeaturesParaTexto(face.features);
		
		return saida;
	}
	
	public static String vetorDeFacesParaTexto(Vector faces){
		String saida = "\n{";
		
		for(int i = 0; i < faces.size(); i++){
			Face faceTmp = (Face)faces.elementAt(i);
			
			saida += "[\n";
			saida += faceParaTexto(faceTmp);
			saida += "\n]\n";
		}
		
		saida += "}\n";
		
		return saida;
	}
	
	public static String blocoParaTexto(Bloco bloco){
		String saida = "";

		saida += "Comprimento: " + bloco.getComprimento() + "\n";
		saida += "Largura: " + bloco.getLargura() + "\n";
		saida += "Profundidade: " + bloco.getProfundidade() + "\n";
		saida += vetorDeFacesParaTexto(bloco.faces);		
		
		return saida;
	}
	
	public static String maquinaParaTexto(Maquina maquina){
		String saida = "";

		saida += "Comprimento: " + maquina.getComprimento() + "\n";
		saida += "Largura: " + maquina.getLargura() + "\n";
		saida += "Profundidade: " + maquina.getProfundidade() + "\n";
		
		return saida;		
	}
	
	public static String dadosDeProjetoParaTexto(DadosDeProjeto dadosDeProjeto){
		String saida = "";

		saida += "UserID: " + dadosDeProjeto.getUserID() + "\n";
		saida += "UserName: " + dadosDeProjeto.getUserName() + "\n";
		saida += "ProjectName: " + dadosDeProjeto.getProjectName() + "\n";
		
		return saida;		
	}
	
	public static String ferramentaParaTexto(Ferramenta ferramenta){
		String saida = "";

		saida += "Diametro: " + ferramenta.getDiametroFerramenta() + "\n";
		saida += "Profundidade Maxima: " + ferramenta.getProfundidadeMaxima() + "\n";
		
		return saida;	
	}
	
	public static String condicoesDeUsinagemParaTexto(CondicoesDeUsinagem condicoes){
		String saida = "";

		saida += "Vc: " + condicoes.getVc() + "\n";
		saida += "Vf: " + condicoes.getVf() + "\n";
		saida += "Ap: " + condicoes.getAp() + "\n";
		saida += "Ae: " + condicoes.getAe() + "\n";
		saida += "Af: " + condicoes.getAf() + "\n";
		saida += "F: " + condicoes.getF() + "\n";
		saida += "N: " + condicoes.getN() + "\n";
				
		saida += "Acabamento: " + condicoes.acabamento + "\n";
		saida += "Material: " + condicoes.material + "\n";
		
		saida += ferramentaParaTexto(condicoes.ferramenta);
		
		return saida;	
	}
	
	public static String ordemDeFabricacaoParaTexto(OrdemDeFabricacao ordem){
		String saida = "";

		saida += "Quantidade: " + ordem.getQuantidade() + "\n";
		saida += condicoesDeUsinagemParaTexto(ordem.getCondicoesDeUsinagem());
				
		return saida;	
	}
	
	public static String vetorDeOrdensDeFabricacaoParaTexto(Vector ordens){
		String saida = "\n{";
		
		if(ordens != null){
			for(int i = 0; i < ordens.size(); i++){
				OrdemDeFabricacao ordemTmp = (OrdemDeFabricacao)ordens.elementAt(i);

				saida += "[\n";
				saida += ordemDeFabricacaoParaTexto(ordemTmp);
				saida += "\n]\n";
			}
		}
		
		saida += "}\n";
		
		return saida;		
	}
	
	public static String projetoParaTexto(Projeto projeto){
		String saida = "";
		
		saida += blocoParaTexto(projeto.getBloco());
		saida += "#\n";
//		saida += maquinaParaTexto(projeto.getMaquina());
//		saida += "#\n";
		saida += dadosDeProjetoParaTexto(projeto.getDadosDeProjeto());
		saida += "#\n";
		saida += vetorDeOrdensDeFabricacaoParaTexto(projeto.getOrdensDeFabricacao());
		
		return saida;
	}

}
