package br.UFSC.GRIMA.cad;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ArvorePrecedencias 
{
	private static Projeto projeto;
	private static Bloco bloco;
	public ArvorePrecedencias(Projeto projeto)
	{
		this.projeto = projeto;
		this.bloco = this.projeto.getBloco();
	}
	
	private static DefaultMutableTreeNode getNode(Projeto projeto)
	{
		bloco = projeto.getBloco();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Precedences: ");
		for(int i = 0; i < bloco.getFaces().size(); i++)
		{
			DefaultMutableTreeNode nodeFaceTmp = new DefaultMutableTreeNode(((Face)bloco.getFaces().elementAt(i)).getStringFace(i));
			Face faceTmp = (Face)bloco.getFaces().elementAt(i);
			
			for(int j = 0; j < faceTmp.features.size(); j++)
			{
				Feature featureTmp = (Feature)faceTmp.features.elementAt(i);
				System.out.println("precedente --> " + featureTmp.getFeaturePrecedente());
				if(featureTmp.getFeaturePrecedente() == null)
				{
					
					DefaultMutableTreeNode nodoFeatureTmp = featureTmp.getNodo();
					nodeFaceTmp.add(nodoFeatureTmp);
					
					/*
					 * Pesquisar se tem filhas
					 */
				}
			}
			
			node.add(nodeFaceTmp);
		}
		return node;
	}
	public static JTree getArvorePrecedencias(Projeto projeto)
	{
		JTree arvore = new JTree(getNode(projeto));
		return arvore;
	}
}
