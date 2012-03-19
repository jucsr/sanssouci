package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.Ponto;



public class OrdenadoraDeSequencias {

	public OrdenadoraDeSequencias(){
	}
	
//	public static Vector<Workingstep> organizarWorkingSteps(Vector<Vector<Workingstep>> workingstepsEmCamadas){
//		
//		Vector<Workingstep> workingstepsFinal = new Vector<Workingstep>();
//		
//		for(int i = 0; i<workingstepsEmCamadas.size(); i++){
//			
//			Vector<Workingstep> wsBroca = new Vector<Workingstep>();
//			Vector<Workingstep> wsFresa = new Vector<Workingstep>();
//			
//			Vector<Workingstep> wsZtemp = workingstepsEmCamadas.get(i);
//
//			for(int j = 0; j < wsZtemp.size(); j++){
//				
//				Ferramenta ferrTmp = wsZtemp.get(j).getFerramenta();
//				
//				if(ferrTmp.getClass()==Broca.class){
//					wsBroca.add(wsZtemp.get(j));
//				}else if(ferrTmp.getClass()==Fresa.class){
//					wsFresa.add(wsZtemp.get(j));
//				}else{
//					System.out.println("CLASSE DE FERRAMENTA DESCONHECIDA");
//				}
//				
//			}
//			
//
//			Vector<Vector<Workingstep>> wsPorDiamTemp = new Vector<Vector<Workingstep>>(); 
//
//			wsPorDiamTemp = ordenarPorDiametro(wsBroca, Ferramenta.BROCA);
//
//			for(int k = 0; k<wsPorDiamTemp.size();k++){
//				Vector<Workingstep> wsPorDistTemp = ordenarPorDistancia(wsPorDiamTemp.get(k));
//
//				for(int p = 0; p<wsPorDistTemp.size();p++)
//					workingstepsFinal.add(wsPorDistTemp.get(p));
//
//			}
//
//			wsPorDiamTemp = ordenarPorDiametro(wsFresa, Ferramenta.FRESA);
//
//			for(int k = 0; k<wsPorDiamTemp.size();k++){
//				Vector<Workingstep> wsPorDistTemp = ordenarPorDistancia(wsPorDiamTemp.get(k));
//
//				for(int p = 0; p<wsPorDistTemp.size();p++)
//					workingstepsFinal.add(wsPorDistTemp.get(p));
//			}
//
//		}
//		
//		return workingstepsFinal;
//	}
	
	public static Vector determinarSequencias(Vector vetorDeWorkingsteps){
		Vector output = new Vector();
		
		for(int i = 0; i < vetorDeWorkingsteps.size(); i++)
		{
			int[][] sequenciasTmp = null;
			Vector workinstepsTmp = (Vector)vetorDeWorkingsteps.elementAt(i);
			if(workinstepsTmp.size() != 0)
			{
				sequenciasTmp = ordenarSequenciaFace(workinstepsTmp, new Ponto(0, 0, -3));	
				for (int j = 0; j < sequenciasTmp.length; j++)
				{
					Workingstep ws = (Workingstep)workinstepsTmp.elementAt(sequenciasTmp[j][0]);
					System.out.println(j + " -> " +ws.getFeature().getTipoString() + "\t\t------  ponto do array ->"+sequenciasTmp[j][1]);
				}
				System.out.println();
			}
			output.add(sequenciasTmp);
		}
		return output;
	}
	
	public static Vector determinarSequenciasCamadas(Vector vetorDeCamadasBloco)
	{
		Vector saida = new Vector();
		//Vector arraysCamadasFace = new Vector();
		Ponto pontoAtual = new Ponto(0, 0, -3);
		for (int i = 0; i < vetorDeCamadasBloco.size(); i++)//faces
		{
			System.out.println("\n\n ======== Face " + i + " ===========");
			Vector camadasFaceTmp = (Vector)vetorDeCamadasBloco.elementAt(i);
			Vector sequenciasCamadasFace = null;
			
			if(camadasFaceTmp.size() != 0)//tem camadas na face
			{
				sequenciasCamadasFace = new Vector();
				int[][] sequenciaTmp;
				for(int j = 0; j < camadasFaceTmp.size(); j++)
				{
					Vector camadaTmp = (Vector)camadasFaceTmp.elementAt(j);
										
					System.out.println("\n ========== Camada " + j + " =========");
					sequenciaTmp = ordenarSequenciaFace(camadaTmp, pontoAtual);
					/*for(int k = 0; k < sequenciasTmp.length; k++)
					{
						Workingstep ws = (Workingstep)camadaTmp.elementAt(sequenciasTmp[k][0]);
						System.out.println(k + " ----> " +ws.getFeature().getTipoString() + "\t\t---  ponto do array ->" + sequenciasTmp[k][1]);
					}*/
					Workingstep ultimo = (Workingstep)camadaTmp.elementAt(sequenciaTmp[sequenciaTmp.length - 1][0]);
					pontoAtual = ultimo.getPontos()[sequenciaTmp[sequenciaTmp.length - 1][1]];
					sequenciasCamadasFace.add(sequenciaTmp);

				}
			}
			saida.add(sequenciasCamadasFace);
			
			//atualiza o ponto inicial da face
			pontoAtual = new Ponto(0, 0, -3); 
		}
		
		imprimirDadosCamadas(saida);
		
		return saida;
	}
	
	/**
	 * @param workingsteps -> vetor com um grupo de ws a serem ordenadas
	 * @param origemAtual -> ponto da origem atual
	 * @return -> ws com menor distancia e o ponto mais proximo dos posiveis da ws � origem
	 */
	public static int[][] ordenarSequenciaFace(Vector workingsteps, Ponto origemAtual)
	{
		System.out.println("############################");
		
		int[][] sequencias = null;
		int[] proximoWS;
		Ponto pontoAtual = origemAtual;
		int passadas = 0;

		boolean terminou = false;//terminou de ordenar todas as WSs
		while(!terminou)
		{
			System.out.println("###############################################");
			double menorDistancia = -1;
			proximoWS = new int[2];
			passadas++;
			System.out.println("Passadas: " + passadas);
			
			for(int i = 0; i < workingsteps.size(); i++){
				System.out.println("\tWorkingstep: " + i);
				boolean usado = false;
				
				if(sequencias != null){
					for(int j = 0; j < sequencias.length; j++){
						//JOptionPane.showMessageDialog(null, sequencias[j][0] + " pointIndex =" + pointIndex);
						if(sequencias[j][0] == i){
							usado = true;
						}
					}
				}
				
				if(usado)
					System.out.println("\tWS j� est� na sequencia!");
				else
					System.out.println("\tWS n�o est� na sequencia!");
				if(!usado){//workingsteps de indice pointIndex � valido (ainda n�o esta na sequencia)
					Workingstep wsTmp = (Workingstep)workingsteps.elementAt(i);
					
					Feature fTmp = wsTmp.getFeature();
					Feature maeTmp = fTmp.featureMae;
					
					if(maeTmp == null)//16/01 -> isso naum serve pra nada...
					{
						
					}
					else
					{
						
					}
					
					Ponto [] pontos = wsTmp.getPontos();
					System.out.println("\tPontos-----------------");
					for (int j = 0; j < pontos.length; j++)
					{	
						System.out.println("ponto " + j + " do array de c/ws = " + pontos[j]);
						
						Ponto pTmp = pontos[j];
						double x, y, z, xTmp, yTmp, zTmp, distanciaTmp;
						x = pontoAtual.getX();
						y = pontoAtual.getY();
						z = pontoAtual.getZ();
						xTmp = pTmp.getX();
						yTmp = pTmp.getY();
						zTmp = pTmp.getZ();
						distanciaTmp = Math.sqrt((x - xTmp) * (x - xTmp) + (y - yTmp) * (y - yTmp) + (z - zTmp) * (z - zTmp));
						System.out.println("\t\t[" +j +"]" + " distanciaTmp = " + distanciaTmp);
						/*
						 * determirar a menor distancia relativa ao origem atual, as outras distancias serao descartadas
						 */
						
						if (menorDistancia == -1)
						{
							proximoWS[0] = i;
							proximoWS[1] = j;
							menorDistancia = distanciaTmp;
						}
						else
						{
							if(distanciaTmp < menorDistancia)
							{
								menorDistancia = distanciaTmp;
								proximoWS[0] = i;
								proximoWS[1] = j;
							}
						}					
					}
					System.out.println("\t\tproximoWS = [" + proximoWS[0] + ", " + proximoWS[1] + "]");	
				}
			}
			System.out.println("proximoWS = [" + proximoWS[0] + ", " + proximoWS[1] + "]");
			
			if(sequencias == null)
			{
				sequencias = new int[1][];
				sequencias[0] = proximoWS;
			}
			else
			{
				System.out.println("\t\t------- [" + proximoWS[0] + "," + proximoWS[1] + "], ");
				int[][] novaSequencia = new int[sequencias.length + 1][];
				
				for(int i = 0; i < sequencias.length; i++)
				{
					novaSequencia[i] = sequencias[i];
				}
				novaSequencia[novaSequencia.length - 1] = proximoWS;	
				sequencias = novaSequencia;
			}
			
			//s� para imprimir dados
			//System.out.println("");
			for(int k = 0; k < sequencias.length; k++){
				System.out.print("*[" + sequencias[k][0] + ", " + sequencias[k][1] + "], ");
			}
			//System.out.println("");
			
			/*
			 *    atualiza o ponto
			 */
			Workingstep wsTmp = (Workingstep)workingsteps.elementAt(proximoWS[0]);
//			pontoAtual = (Ponto)((Vector)wsTmp.getPontosMovimentacao().elementAt(proximoWS[1])).lastElement();	
			pontoAtual = (Ponto)(wsTmp.getPontosMovimentacao()).lastElement();	
			System.out.println("origem Atual = " + pontoAtual);		
			
			if(sequencias != null)
			{
				if(sequencias.length == workingsteps.size())
				{
					terminou = true;
				}
			}
			System.out.println("terminou = " + terminou);
		}
		return sequencias;
	}
	
	public static void imprimirDadosCamadas(Vector camadasBloco){
		System.out.println("\n");
		System.out.println("=======================================================");
		System.out.println("==  Dados do Determinador de Sequencias por Camadas  ==");
		System.out.println("=======================================================");
		
	///	JOptionPane.showMessageDialog(null, camadasBloco.size());
		for(int i = 0; i < camadasBloco.size(); i++){
			Vector sequenciasCamadasFace = (Vector)camadasBloco.elementAt(i);
			
			if(sequenciasCamadasFace != null){
				System.out.printf("..:: Face #%d (possui %d camadas) ::..\n", i, sequenciasCamadasFace.size());

				for(int j = 0; j < sequenciasCamadasFace.size(); j++){
					
				}
			}
		}
		
		
		System.out.println("=======================================================");
	}
}
