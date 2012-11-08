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
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
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
	private ArrayList<FaceMill> faceMills;
	private ArrayList<BallEndMill> ballEndMills;
	private double zMaximo;
	
	public MapeadoraRegion(Projeto projeto, Face face, Region region)
	{
		this.projeto = projeto;
		this.regionTmp = region;
		this.faceTmp = face;
		this.bloco = projeto.getBloco();
		
		this.endMills = ToolManager.getEndMills();
		this.faceMills = ToolManager.getFaceMills();
		this.ballEndMills = ToolManager.getBallEndMills();
		this.mapearRegion();
	}

	private void mapearRegion() 
	{
		Workingstep wsTmp, wsPrecedente;
		double retractPlane = 5;
		Point3d malha[][] = new BezierSurface(regionTmp.getControlVertex(), 200, 200).getMeshArray();
		
		
		wssFeature = new Vector<Workingstep>();
		if(regionTmp.getFeaturePrecedente() != null)
		{
			wsPrecedente = regionTmp.getFeaturePrecedente().getWorkingsteps().lastElement();
		} else
		{
			wsPrecedente = null;
		}
		
		zMaximo = getZMaximo(malha);
		if(zMaximo > 0)
		{
			zMaximo = -zMaximo;
		}
		
		// ---------------- CRIANDO WORKINGSTEP DE DESBASTE (zigue-zague)------------------
		
					// -----Criando operação ----
		System.err.println("===============>>>>>>>>> zMaximo  " + zMaximo + "  Posicao : " + regionTmp.getPosicaoZ());
		
		if(zMaximo<regionTmp.getPosicaoZ()){
			BottomAndSideRoughMilling desbaste1 = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);
			desbaste1.setStartPoint(new Point3d(0, 0, 0));

			// ----- Criando ferramenta -----

			
			double L;//determinarDiametroMinimo();
			if(regionTmp.getLength()>regionTmp.getWidth())
			{
				L = regionTmp.getLength();
			} else
			{
				L = regionTmp.getWidth();
			}			
			
			FaceMill faceMill = chooseFaceMill(bloco.getMaterial(), faceMills, regionTmp, L);

			// ------ Criando condicoes de usinagem ---------
			condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, faceMill, this.bloco.getMaterial());

			// ------ Criando o Machining Workingstep ------
			wsTmp = new Workingstep(regionTmp, faceTmp, faceMill, condicoesDeUsinagem, desbaste1);
			wsTmp.setId("WS Desbaste");
			wsTmp.setTipo(Workingstep.DESBASTE);
			wsTmp.setWorkingstepPrecedente(wsPrecedente);
			wsPrecedente = wsTmp;

			wssFeature.add(wsTmp);
			System.err.println("================>>>>>>>> WS DESBASTE ZIG_ZAG");
		}	
		// ---------------- CRIANDO WORKINGSTEP DE DESBASTE ------------------
		
			// -----Criando operação ----
		BottomAndSideRoughMilling desbaste1 = new BottomAndSideRoughMilling("Bottom And Side Rough Milling", retractPlane);

		if(zMaximo>=regionTmp.getPosicaoZ()){
			desbaste1.setStartPoint(new Point3d(0, 0, 0));
		}
		else if(zMaximo<regionTmp.getPosicaoZ()){
			desbaste1.setStartPoint(new Point3d(0, 0, zMaximo));			
		}
		
			// ----- Criando ferramenta -----
		
		double L = determinarDiametroMinimo();
		EndMill endMill = chooseEndMill(bloco.getMaterial(), endMills, regionTmp, L);
		System.out.println("MATERIAL DESBASTE 1 = " + endMill.getMaterial());
			// ------ Criando condicoes de usinagem ---------
		condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(this.projeto, endMill, this.bloco.getMaterial());
		
			// ------ Criando o Machining Workingstep ------
		wsTmp = new Workingstep(regionTmp, faceTmp, endMill, condicoesDeUsinagem, desbaste1);
		wsTmp.setId("WS Desbaste");
		wsTmp.setTipo(Workingstep.DESBASTE);
		wsTmp.setWorkingstepPrecedente(wsPrecedente);
		wsPrecedente = wsTmp;
		
		wssFeature.add(wsTmp);
		
//		System.err.println("================>>>>>>>> COND US = " + condicoesDeUsinagem.getAp());
		System.err.println("================>>>>>>>> WS DESBASTE NORMAL");

		// --------------- CRIANDO WORKINGSTEP FREE FORM --------------------
		
			// -------- Criando Operação ---------
		FreeformOperation freeForm = new FreeformOperation("Free Form Operation", retractPlane);
		freeForm.setCoolant(true);
		freeForm.setId("Free form Operation");
			// -------- Criando Ferramenta --------
		BallEndMill ballEndMill = chooseBallEndMill(bloco.getMaterial(), ballEndMills, regionTmp, L);
		System.out.println("material freeform = " + ballEndMill.getMaterial());
			
			// --------- Criando Condicoes de Usinagem --------
		condicoesDeUsinagem = MapeadoraDeWorkingsteps.getCondicoesDeUsinagem(projeto, ballEndMill, bloco.getMaterial());
			// -------- Criando o Machining Workingstep -------
		
		wsTmp = new Workingstep(regionTmp, faceTmp, ballEndMill, condicoesDeUsinagem, freeForm);
		wsTmp.setId("WS Free Form");
		wsTmp.setTipo(Workingstep.ACABAMENTO);
		wsTmp.setWorkingstepPrecedente(wsTmp);
		
		wssFeature.add(wsTmp);
//		System.err.println("================>>>>>>>> WS FREE_FORM");
		regionTmp.setWorkingsteps(wssFeature);
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
		ballEndMill = ballEndMillsCandidatas.get(0);

		for (int i = 1; i < ballEndMillsCandidatas.size(); i++) {// Seleciona a
			// ball end
			// mill de
			// maior
			// diametro

			if (ballEndMillsCandidatas.get(i).getDiametroFerramenta() > ballEndMill
					.getDiametroFerramenta()) {
				ballEndMill = ballEndMillsCandidatas.get(i);
			}

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
	private FaceMill chooseFaceMill(Material material, ArrayList<FaceMill> faceMills, Region region, double L) 
	{
		ArrayList<FaceMill>faceMillsCandidatas = new ArrayList<FaceMill>();
		FaceMill faceMill = null;
		String ISO = "";
		ISO = MapeadoraDeWorkingsteps.selectMaterialFerramenta(this.projeto, material, "Condicoes_De_Usinagem_FaceMill");
		for(int i = 0; i < faceMills.size(); i++) // seleciona todas as faceMills candidatas para usinar a regiao
		{
			faceMill = faceMills.get(i);
			if(faceMill.getMaterial().equals(ISO) && faceMill.getProfundidadeMaxima() >= -zMaximo && faceMill.getDiametroFerramenta() <= L)
			{
				faceMillsCandidatas.add(faceMill);
			}
		}
		if(faceMillsCandidatas.size() == 0)
		{
			JOptionPane
			.showMessageDialog(
					null,
					"Não é possível usinar esta Feature com as atuais Face Mills disponíveis! \n" +
					"__________________________________________________________"+"\n"+
					"\tFeature: Region \n" +
					"\tName: " + region.getNome() + "\n" +
					"\tMaximum Depth: " + region.getMaxDepth() +" mm"+"\n" +
					"\tRaw Block Material: " + material.getName()+"\n" +
					"__________________________________________________________"+"\n"+
					"\tMotivo: Do grupo das Face Mills do projeto, nenhuma satisfaz os" +"\n"+
					"\tseguintes requisitos necessários para a usinagem desta feature:"+"\n\n" +
					"\tMaterial da Ferramenta deve ser do tipo: "+ ISO +"\n" +
					"\tProfundidade Máxima da Ferramenta deve ser maior igual a: " + (region.getMaxDepth())+" mm"+"\n\n" +
					"\tAdd Face Mills adequadas ao projeto."
					,
					"Erro", JOptionPane.ERROR_MESSAGE);
			
			throw new NullPointerException("Nenhuma End Mill selecionada");
		}
		faceMill = faceMillsCandidatas.get(0);
		for(int i = 1; i < faceMillsCandidatas.size(); i++)
		{
			// ---- seleciona o endMill de maior diametro ----
			if(faceMillsCandidatas.get(i).getDiametroFerramenta() > faceMill.getDiametroFerramenta())
			{
				faceMill = faceMillsCandidatas.get(i);
			}
		}
		return faceMill;
	}
	private double determinarDiametroMinimo()
	{
		Point3d[][] pontosDaSuperficie = new BezierSurface(regionTmp.getControlVertex(), 21, 21).getMeshArray();
		double diametroMinimo = 0;
		double l1Tmp,l2Tmp,l3Tmp,anguloTmp,diametroTmp,deltaZ1,deltaZ2,z0,z1,z2;
		
		l1Tmp = Math.pow(Math.pow((pontosDaSuperficie[0][1].x - pontosDaSuperficie[0][0].x), 2) +  Math.pow((pontosDaSuperficie[0][1].z - pontosDaSuperficie[0][0].z), 2), 0.5);
		diametroMinimo = l1Tmp;
		
		for(int i = 0; i < pontosDaSuperficie.length; i++)
		{
			for(int j = 0; j < pontosDaSuperficie[0].length - 2; j++)
			{
				l1Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 1].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 1].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				l2Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 2].x - pontosDaSuperficie[i][j + 1].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 2].z - pontosDaSuperficie[i][j + 1].z), 2), 0.5);
				l3Tmp = Math.pow(Math.pow((pontosDaSuperficie[i][j + 2].x - pontosDaSuperficie[i][j].x), 2) +  Math.pow((pontosDaSuperficie[i][j + 1].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				anguloTmp = Math.acos((Math.pow(l2Tmp, 2) + Math.pow(l3Tmp, 2) - Math.pow(l1Tmp, 2)) / (2 * l2Tmp * l3Tmp));
				diametroTmp = l1Tmp / Math.sin(anguloTmp);
				
				//CONVEXO ou CONCAVO
				
				z0 = pontosDaSuperficie[i][j].z;
				z1 = pontosDaSuperficie[i][j + 1].z;
				z2 = pontosDaSuperficie[i][j + 2].z;
		
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
			}
		}
		for(int j = 0; j < pontosDaSuperficie.length; j++)
		{
			for(int i = 0; i < pontosDaSuperficie[0].length - 2; i++)
			{
				l1Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 1][j].y - pontosDaSuperficie[i][j].y), 2) +  Math.pow((pontosDaSuperficie[i + 1][j].z - pontosDaSuperficie[i][j].z), 2), 0.5);
				l2Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 2][j].y - pontosDaSuperficie[i + 1][j].y), 2) +  Math.pow((pontosDaSuperficie[i + 2][j].z - pontosDaSuperficie[i + 1][j].z), 2), 0.5);
				l3Tmp = Math.pow(Math.pow((pontosDaSuperficie[i + 2][j].y - pontosDaSuperficie[i][j].y), 2) +  Math.pow((pontosDaSuperficie[i + 1][j].z - pontosDaSuperficie[i][j].z), 2), 0.5);
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
	
	public static double getZMaximo(Point3d[][] malha)
	{
		double zMaximo;
		zMaximo=malha[0][0].getZ();
		
		for(int i=0;i<malha.length;i++){//PERCORRE A MALHA TODA PARA ACHAR O MENOR Z
			for(int j=0;j<malha[i].length;j++){				
				if(zMaximo<malha[i][j].getZ()){
					zMaximo=-malha[i][j].getZ();
				}
			}
		}
		
		return zMaximo;
	}
}
