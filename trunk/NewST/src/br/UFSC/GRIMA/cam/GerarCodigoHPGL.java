package br.UFSC.GRIMA.cam;

import java.util.Vector;

import br.UFSC.GRIMA.capp.OrdenadoraDeSequencias;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.util.Ponto;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.OrdemDeFabricacao;

public class GerarCodigoHPGL 
{
	private static String[] nomesFaces = {"Face XY", "Face YZ", "Face XZ", "Face YX", "Face ZY", "Face ZX"};
	
	public GerarCodigoHPGL()
	{
	}
	public static void determinarCodigoHPGL(Vector vectorDeWorkingSteps, Vector sequencias)
	{
		
		for (int i = 0; i < vectorDeWorkingSteps.size(); i++)
		{
			Vector workingstepsTmp = (Vector)vectorDeWorkingSteps.elementAt(i);
			int [][] sequenciaTmp = (int[][])sequencias.elementAt(i);
			if(sequenciaTmp != null)
			{
				System.out.println(gerarCodigoFace(workingstepsTmp, sequenciaTmp));
			}		
		}
	}
	public static String determinarCodigoCamadasHPGL(Vector vetorWorkingstepsCamadas, Vector sequencias, DadosDeProjeto dadosDeProjeto, OrdemDeFabricacao ordemDeFabricacao)
	{
		Vector saida = new Vector();
		
		for (int i = 0; i < vetorWorkingstepsCamadas.size(); i++)
		{
			System.out.println("Face " + i);
			Vector camadasFaceTmp = (Vector)vetorWorkingstepsCamadas.elementAt(i);
			Vector sequenciasCamadasFaceTmp = (Vector)sequencias.elementAt(i);
			String codigoFaceTmp = "";
			
			if(camadasFaceTmp.size() != 0)
			{
				for (int j = 0; j < camadasFaceTmp.size(); j++)
				{
					System.out.println("Camada " + j);
					Vector camadasTmp = (Vector)camadasFaceTmp.elementAt(j);
					int [][] sequenciaTmp = (int[][])sequenciasCamadasFaceTmp.elementAt(j);
					String codigoCamadaTmp = "\n";
					
					if(sequenciaTmp.length != 0 && camadasTmp.size() != 0)
					{
						codigoCamadaTmp = gerarCodigoFace(camadasTmp, sequenciaTmp);
						System.out.println(gerarCodigoFace(camadasTmp, sequenciaTmp));
						System.out.println("camadasTmp tamanho = " + camadasTmp.size() + "   sequenciaTmp tamanho = " + sequenciaTmp.length);
					}
					
					codigoFaceTmp += codigoCamadaTmp;
				}
			}
			
			saida.add(codigoFaceTmp);
		}
		
		return formatarCodigoHPGL(saida, dadosDeProjeto, ordemDeFabricacao);
	}
	/**
	 * 
	 * @param workingsteps --> WS de uma face
	 * @param sequencia --> array de sequencias para uma face
	 * @return --> codigo HPGL para uma face
	 */
	public static String gerarCodigoFace(Vector workingsteps, int[][] sequencia){
		String codigoFace = "";
		for (int i = 0; i < sequencia.length; i++)
		{
			int[] seqTmp = sequencia[i];
			Workingstep wsTmp = (Workingstep)workingsteps.elementAt(seqTmp[0]);
			codigoFace += gerarCodigo(wsTmp, seqTmp[1]);
			
			if(i == sequencia.length - 1){
				codigoFace += "\n H;\n";
			}
		}
		return codigoFace;
	}
	/**
	 * 
	 * @param ws --> WorkinStep 
	 * @param indicePonto --> indice do ponto de inicio de usinagem da WS
	 * @return --> codigo HPGL para uma WorkinStep
	 */
	public static String gerarCodigo(Workingstep ws, int indicePonto)
	{
		String codigo = "";
		Vector movimentacao = ws.getPontosMovimentacao();
		//float z = (float)(ws.getFaceMae().getProfundidadeMaxima());
		//float deltaZ = (float)((60.25 - z) * 40);
		
			Vector vetorPontos = (Vector)movimentacao.elementAt(indicePonto);
			for (int j = 0; j < vetorPontos.size(); j++)
			{
				Ponto ponto = (Ponto)vetorPontos.elementAt(j);
				/*float pontoX, pontoY, pontoZ;
				pontoX = (float)(ponto.getX() * 40);
				pontoY = (float)(ponto.getY() * 40);
				pontoZ = (float)(ponto.getZ() * 40);
				codigo +="\n Z " + pontoX + ", " + pontoY + ", " + ( -pontoZ - deltaZ) + ";";*/
				//codigo += "\nZ " + ponto.getX() * 40 + ", " + ponto.getY() * 40 + ", " + ((-ponto.getZ() * 40) - deltaZ) + ";";
				codigo += "\n " + ponto.getX() + ", " + ponto.getY() + ", " + (-ponto.getZ() + (float)ws.getFace().getProfundidadeMaxima()) + ";";
			}
		return codigo;
	}
	
	public static String formatarCodigoHPGL(Vector codigoFaces, DadosDeProjeto dadosDeProjeto, OrdemDeFabricacao ordemDeFabricacao){
		String saida = "";
		System.out.println("############################################");
		System.out.println("#### Codigo HPGL formatado");
		
		saida += ("User:" + dadosDeProjeto.getUserName() + "\n");
		saida += ("ProjetcName:" + dadosDeProjeto.getProjectName() + "\n");
		saida += ("Material:" + ordemDeFabricacao.getCondicoesDeUsinagem().material + "\n");
		saida += ("Quantity:" + ordemDeFabricacao.getQuantidade() + "\n");
		saida += "IN;";
		for(int i = 0; i < codigoFaces.size(); i++){
			saida += ("\n#" + nomesFaces[i] + "\n");
			
			String codigoFaceTmp = (String)codigoFaces.elementAt(i);
			saida += codigoFaceTmp;
		}
		
		System.out.println(saida);
		
		return saida;
	}
}
