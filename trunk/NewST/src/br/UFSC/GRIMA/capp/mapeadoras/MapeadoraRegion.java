package br.UFSC.GRIMA.capp.mapeadoras;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRegion 
{
	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private Region regionTmp;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private ArrayList<EndMill> endMills;
	private ArrayList<BallEndMill> ballEndMills;
	
	public MapeadoraRegion(Projeto projeto, Face face, Region region)
	{
		this.projeto = projeto;
		this.regionTmp = region;
		this.faceTmp = face;
		this.bloco = projeto.getBloco();
		
		this.endMills = ToolManager.getEndMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		
		this.mapearRegion();
	}

	private void mapearRegion() 
	{
		Workingstep wsTmp, wsPrecedente;
		double retractPlane = 5;
		
		wssFeature = new Vector<Workingstep>();
		if(regionTmp.getFeaturePrecedente() != null)
		{
			wsPrecedente = regionTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
		} else
		{
			wsPrecedente = null;
		}
		
		// ---------------- CRIANDO WORKINGSTEP DE DESBASTE ------------------
		
			// -----Criando operação ----
		BottomAndSideRoughMilling desbaste1 = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);
		desbaste1.setStartPoint(new Point3d(0, 0, 0));
		
			// ----- Criando ferramenta -----
		EndMill endMill = choolseEndMill(bloco.getMaterial(), endMills, regionTmp);
	}

	private EndMill choolseEndMill(Material material, ArrayList<EndMill> endMills, Region region) 
	{
		ArrayList<EndMill>endMillsCandidatas = new ArrayList<EndMill>();
		EndMill endMill = null;
		String ISO = "";
		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto, material, "Condicoes_De_Usinagem_EndMill");
		for(int i = 0; i < endMills.size(); i++) // seleciona todas as endMills candidatas para usinar a regiao
		{
			endMill = endMills.get(i);
			if(endMill.getMaterial().equals(ISO) && endMill.getProfundidadeMaxima() >= region.getMaxDepth())
			{
				endMillsCandidatas.add(endMill);
			}
		}
		if(endMillsCandidatas.size() == 0)
		{
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Region \n" +
					"\tName: " + region.getNome() + "\n" +
					"\tMaximum Depth: " + region.getMaxDepth() +" mm"+"\n" +
					"\tRaw Block Material: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (region.getMaxDepth())+" mm"+"\n\n" +
					"\tAdd End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma End Mill selecionada");
		}
		endMill = endMillsCandidatas.get(0);
		for(int i = 1; i < endMillsCandidatas.size(); i++)
		{
			// ---- seleciona o endMill de maior diametro ----
			if(endMillsCandidatas.get(i).getDiametroFerramenta() > endMill.getDiametroFerramenta())
			{
				endMill = endMillsCandidatas.get(i);
			}
		}
		return endMill;
	}
}
