package br.UFSC.GRIMA.util;

import java.util.StringTokenizer;
import java.util.Vector;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
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




public class TradutorTextoParaClasse {

	public TradutorTextoParaClasse() {
		// TODO Auto-generated constructor stub
	}
	
	public static Feature criarFeatureDeTexto(String texto){
		
		
		return null;
	}
	
	public static Furo criarFuroDeTexto(String texto){
		Furo saida = null;
		StringTokenizer token = new StringTokenizer(texto, "&");
		
		if(token.countTokens() == 2){
			String baseFeature = token.nextToken();
			Feature fTmp = criarFeatureDeTexto(baseFeature);
			
			saida = new Furo();
			saida.featureMae = fTmp.featureMae;
			saida.featuresAnteriores = fTmp.getFeaturesAnteriores();
			
			saida.X = fTmp.X;
			saida.Y = fTmp.Y;
			saida.Z = fTmp.Z;
			saida.setIndice(fTmp.getIndice());
			
			StringTokenizer token2 = new StringTokenizer(token.nextToken(), "\n");
			while(token2.hasMoreElements()){
				System.out.println("####################");
				System.out.println(token2.nextToken());
			}
		}
		else{
			System.out.println("Erro ao traduzir texto!");
		}
		
		return null;
	}
	
	public static Degrau criarDegrauDeTexto(String texto){
		StringTokenizer token = new StringTokenizer(texto, "&");
		return null;
	}
	
	public static Ranhura criarRanhuraDeTexto(String texto){
		StringTokenizer token = new StringTokenizer(texto, "&");
		return null;
	}
	
	public static Cavidade criarCavidadeDeTexto(String texto){
		StringTokenizer token = new StringTokenizer(texto, "&");
		return null;
	}
	
	public static Vector criarVetorDeFeatureDeTexto(String texto){
		return null;
	}
	
	public static Face criarFaceDeTexto(String texto){
		return null;
	}
	
	public static Vector criarVetorDeFacesDeTexto(String texto){
		return null;
	}
	
	public static Bloco criarBlocoDeTexto(String texto){
		return null;
	}
	
	public static Maquina criarMaquinaDeTexto(String texto){
		return null;
	}
	
	public static DadosDeProjeto criarDadosDeProjetoDeTexto(String texto){
		return null;
	}
	
	public static Ferramenta criarFerramentaDeTexto(String texto){
		return null;
	}
	
	public static CondicoesDeUsinagem criarCondicoesDeUsinagemDeTexto(String texto){
		return null;
	}
	
	public static OrdemDeFabricacao criarOrdemDeFabricacaoDeTexto(String texto){
		return null;
	}

	public static Vector criarVetorDeOrdensDeFabricacaoDeTexto(String texto){
		return null;
	}
	
	public static Projeto criarProjetoDeTexto(String texto){
		return null;
	}
	
	
}
