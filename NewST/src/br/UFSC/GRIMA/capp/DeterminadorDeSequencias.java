package br.UFSC.GRIMA.capp;

import java.util.Vector;

import br.UFSC.GRIMA.util.PermutationGenerator;

public class DeterminadorDeSequencias {
	Vector camadasDasFaces;
	Vector sequenciasCamadas;

	public DeterminadorDeSequencias(Vector camadasDasFaces) {
		// TODO Auto-generated constructor stub
		this.camadasDasFaces = camadasDasFaces;
		this.sequenciasCamadas = this.determinarSequencias(this.camadasDasFaces);
	}

	public Vector determinarSequencias(Vector entrada){
		Vector saida = new Vector();
		
		for(int i = 0; i < entrada.size(); i++)
		{
			Vector vTmp = (Vector)entrada.elementAt(i);
			Vector vFace = new Vector();
			for (int j = 0; j < vTmp.size(); j++)
			{
				Vector vTmp2 = (Vector)vTmp.elementAt(j);
				vFace.add(this.gerarSequencias(vTmp2.size()));
			}
			saida.add(vFace);
		}
		return saida;
	}
	
	public Vector gerarSequencias(int tamanho)
	{
		Vector saida = null;
		int n = tamanho;
		  int[] indices;
		  int [] elements = new int [n];
		  //String[] elements = {"a", "b", "c", "d", "e"};
		 // Vector elements = new Vector();
		  if (n > 0)
		  {
			  saida = new Vector();
			  for (int i = 0; i < n; i++)
			  {
				  elements[i] = i;
			  }
			  PermutationGenerator x = new PermutationGenerator (elements.length);
			  StringBuffer permutation;
			  while (x.hasMore ()) {
			    permutation = new StringBuffer ();
			    indices = x.getNext ();
			    saida.add(indices);
			    /*for (int pointIndex = 0; pointIndex < indices.length; pointIndex++) {
			      permutation.append (elements[indices[pointIndex]]);
			    }
			    System.out.println ("[" + permutation.toString () + "]");*/
			  }
		  }
		  else
		  {
			  System.out.println("N�o h� features");
		  }
		  
		  return saida;
	}
	
}
