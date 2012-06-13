package br.UFSC.GRIMA.capp.mapeadoras;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
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
		
		double L = determinarDiametroMinimo();
		EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills, regionTmp, L);
			
			// ------ Criando condicoes de usinagem ---------
		condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, endMill, this.bloco.getMaterial());
		
			// ------ Criando o Machining Workingstep ------
		wsTmp = new Workingstep(regionTmp, faceTmp, endMill, condicoesDeUsinagem, desbaste1);
		wsTmp.setId("WS Desbaste");
		wsTmp.setTipo(Workingstep.DESBASTE);
		wsTmp.setWorkingstepPrecedente(wsPrecedente);
		wsPrecedente = wsTmp;
		
		wssFeature.add(wsTmp);
		
		// --------------- CRIANDO WORKINGSTEP FREE FORM --------------------
		
			// -------- Criando Operação ---------
		FreeformOperation freeForm = new FreeformOperation("Free Form Operation", retractPlane);
		freeForm.setCoolant(true);
		
			// -------- Criando Ferramenta --------
		BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills, regionTmp, L);
			
			// --------- Criando Condicoes de Usinagem --------
		condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(projeto, ballEndMill, bloco.getMaterial());
		
			// -------- Criando o Machining Workingstep -------
		
		wsTmp = new Workingstep(regionTmp, faceTmp, ballEndMill, condicoesDeUsinagem, freeForm);
		wsTmp.setId("WS Free Form");
		wsTmp.setTipo(Workingstep.ACABAMENTO);
		wsTmp.setWorkingstepPrecedente(wsTmp);
	}

	private BallEndMill chooseBallEndMill(Material material, ArrayList<BallEndMill> ballEndMills, Region regionTmp, double L) {
		ArrayList<BallEndMill> ballEndMillsCandidatas = new ArrayList<BallEndMill>();
		BallEndMill ballEndMill = null;
		
		String ISO = "";

		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto,
				material, "Condicoes_De_Usinagem_BallEndMill");
		
		for (int i = 0; i < ballEndMills.size(); i++) { // Seleciona todas as
			// ball end mills
			// candidatas

			ballEndMill = ballEndMills.get(i);

			if (ballEndMill.getMaterial().equals(ISO)
					&& ballEndMill.getDiametroFerramenta() <= L
					&& ballEndMill.getProfundidadeMaxima() >= (regionTmp.getMaxDepth())
					&& ballEndMill.getEdgeRadius() <= L/2) {

				ballEndMillsCandidatas.add(ballEndMill);
			}
		}
		
		if (ballEndMillsCandidatas.size() == 0) 
		{

			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Ball End Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Ranhura Perfil Bezier \n" +
					"\tNome: " + regionTmp.getNome() +"\n" +
					"\tProfundidade: " + regionTmp.getMaxDepth() +" mm"+"\n" +
					"\tMaterial Bloco: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Ball End Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tDiametro da Ferramenta deve ser menor igual a: " + L+" mm" +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + regionTmp.getMaxDepth()+" mm"+"\n\n" +
					"\tAdicione Ball End Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma Ball End Mill selecionada");


		}

		return ballEndMill;
	}

	private EndMill chooseEndMill(Material material, ArrayList<EndMill> endMills, Region region, double L) 
	{
		ArrayList<EndMill>endMillsCandidatas = new ArrayList<EndMill>();
		EndMill endMill = null;
		String ISO = "";
		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto, material, "Condicoes_De_Usinagem_EndMill");
		for(int i = 0; i < endMills.size(); i++) // seleciona todas as endMills candidatas para usinar a regiao
		{
			endMill = endMills.get(i);
			if(endMill.getMaterial().equals(ISO) && endMill.getProfundidadeMaxima() >= region.getMaxDepth() && endMill.getDiametroFerramenta() <= L)
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
	private double determinarDiametroMinimo()
	{
		Point3d[][] pontosDaSuperficie = new BezierSurface(regionTmp.getControlVertex(), 21, 21).getMeshArray();
		double diametroMinimo = 0;
		
		for(int i = 0; i < pontosDaSuperficie.length; i++)
		{
			for(int j = 0; j < pontosDaSuperficie[0].length - 2; j++)
			{
				double l1Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 1].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 1].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				double l2Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 2].x - pontosDaSuperficie[i][j + 1].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 2].z - pontosDaSuperficie[i][j + 1].z), 2), 0.5);
				double l3Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 2].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 1].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				double anguloTmp = Math.acos((Math.pow(l2Tmp, 2) + Math.pow(l3Tmp, 2) - Math.pow(l1Tmp, 2)) / (2 * l2Tmp * l3Tmp));
				double diametroTmp = l1Tmp / Math.sin(anguloTmp);
				
				double deltaZ1, deltaZ2;
				
				double z0 = pontosDaSuperficie[i][j].z;
				double z1 = pontosDaSuperficie[i][j + 1].z;
				double z2 = pontosDaSuperficie[i][j + 2].z;
				
				deltaZ1 = z1 - z0;
				deltaZ2 = z2 - z1;
				
				if (deltaZ2 >= deltaZ1) 
				{
					if (diametroTmp < diametroMinimo) 
					{
						diametroMinimo = diametroTmp;
					}
				}
				/****************************************************/
				
				l1Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 1][j].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i + 1][j].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				l2Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 2][j].x - pontosDaSuperficie[i + 1][j].x), 2) +  Math.pow((pontosDaSuperficie[i + 2][j].z - pontosDaSuperficie[i + 1][j].z), 2), 0.5);
				l3Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 2][j].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i + 1][j].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				anguloTmp = Math.acos((Math.pow(l2Tmp, 2) + Math.pow(l3Tmp, 2) - Math.pow(l1Tmp, 2)) / (2 * l2Tmp * l3Tmp));
				diametroTmp = l1Tmp / Math.sin(anguloTmp);
				
				z0 = pontosDaSuperficie[i][j].z;
				z1 = pontosDaSuperficie[i + 1][j].z;
				z2 = pontosDaSuperficie[i + 2][j].z;
				
				deltaZ1 = z1 - z0;
				deltaZ2 = z2 - z1;
				
				if (deltaZ2 >= deltaZ1) 
				{
					if (diametroTmp < diametroMinimo) 
					{
						diametroMinimo = diametroTmp;
					}
				}
			}
		}
		return diametroMinimo;
	}
}
