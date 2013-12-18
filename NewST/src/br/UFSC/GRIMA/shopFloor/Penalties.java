package br.UFSC.GRIMA.shopFloor;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;

/**
 * 
 * @author Jc
 *
 */
public class Penalties 
{
	private MachineTool currentMachine;
	private MachineTool nextMachine;
	private Workingstep workingstep;
	private ProjetoSF projetoSF;
	private double totalPenalty = 0; // em tempo
	private int lotSize = 1000;
	private double distance;
	/**
	 * Construtor
	 * @param machine --> maquina
	 * @param workingstep --> workingstep
	 */
	public Penalties(ProjetoSF projetoSF, MachineTool currentMachine, MachineTool nextMachine,  Workingstep workingstep)
	{
		this.currentMachine = currentMachine;
		this.nextMachine = nextMachine;
		this.workingstep = workingstep;
		this.projetoSF = projetoSF;
		this.totalPenalty = this.nextMachine.getSetUpTime() / this.projetoSF.getLotSize();
		this.calculatePenalty();
	}
	/**
	 *  penalidade total em minutos
	 * @return
	 */
	public double getTotalPenalty() 
	{
		totalPenalty = 0.03;
		return totalPenalty;
	}
	public void setTotalPenalty(double totalPenalty) 
	{
		this.totalPenalty = totalPenalty;
	}
	/**
	 * Metodo que calcula as penalidades em funcao dos recursos disponiveis na máquina
	 * @return penalidade total
	 */
	private double calculatePenalty()
	{
		double penaltyTmp = totalPenalty;		
		try 
		{
			for(int i = 0; i < this.currentMachine.getToolHandlingDevice().get(0).getToolList().size(); i++)
			{
				Ferramenta ferramentaTmp = this.currentMachine.getToolHandlingDevice().get(0).getToolList().get(i);
				Ferramenta ferramentaWs = workingstep.getFerramenta();
				if(haveToolToMachinig(ferramentaWs, ferramentaTmp))
				{
					totalPenalty = totalPenalty - penaltyTmp / 3;
					break;
				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("There is not a tool list in machine");
		}
		try {		
			if(haveWorkpieceHandlingDevice())
			{
				totalPenalty = totalPenalty - penaltyTmp / 3;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("There is not a Handling Device in machine");			
		}
//		totalPenalty = totalPenalty + currentMachine.getItsOrigin().distance(nextMachine.getItsOrigin())/(120);		
		return this.totalPenalty;
	}
	public boolean haveWorkpieceHandlingDevice()
	{
		boolean haveWorkpieceHandlingDevice = false;
		if(currentMachine.getWorkpieceHandlingDevice().size() > 0)
		{
			haveWorkpieceHandlingDevice = true;
		}
		return haveWorkpieceHandlingDevice;
	}
	/**
	 * 
	 * @param ferramentaWorkingstep --> Ferramenta necessaria para executar o WS
	 * @param ferramentaMachine --> Ferramenta Pronta no Magazine da maquina
	 * @return verifica se a ferramenta da maquina pode usinar o WS
	 */
	public static boolean haveToolToMachinig(Ferramenta ferramentaWorkingstep, Ferramenta ferramentaMachine)
	{
		boolean isSameTool = false;
		if(ferramentaMachine.getClass() == TwistDrill.class && ferramentaWorkingstep.getClass() == TwistDrill.class)
		{
			TwistDrill tdMachine = (TwistDrill)ferramentaMachine;
			TwistDrill tdWorkingstep = (TwistDrill)ferramentaWorkingstep;
			if(tdMachine.getDiametroFerramenta() == tdWorkingstep.getDiametroFerramenta() &&
					tdMachine.getToolTipHalfAngle() == tdWorkingstep.getToolTipHalfAngle() &&
					tdMachine.getCuttingEdgeLength() >= tdWorkingstep.getCuttingEdgeLength() &&
					tdMachine.getNumberOfTeeth() == tdWorkingstep.getNumberOfTeeth() &&
					tdMachine.getHandOfCut() == tdWorkingstep.getHandOfCut() &&
					tdMachine.getMaterial() == tdWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == CenterDrill.class && ferramentaWorkingstep.getClass() == CenterDrill.class)
		{
			CenterDrill cdMachine = (CenterDrill)ferramentaMachine;
			CenterDrill cdWorkingstep = (CenterDrill)ferramentaWorkingstep;
			if(cdMachine.getClass() == cdWorkingstep.getClass() &&
					cdMachine.getDiametroFerramenta() == cdWorkingstep.getDiametroFerramenta() &&
					cdMachine.getCuttingEdgeLength() >= cdWorkingstep.getCuttingEdgeLength() &&
					cdMachine.getMaterial() == cdWorkingstep.getMaterial() &&
					cdMachine.getNumberOfTeeth() == cdWorkingstep.getNumberOfTeeth() &&
					cdMachine.getHandOfCut() == cdWorkingstep.getHandOfCut())
			{
				isSameTool = true;
			}
			
		} else if(ferramentaMachine.getClass() == Reamer.class && ferramentaWorkingstep.getClass() == Reamer.class)
		{
			Reamer rMachine = (Reamer)ferramentaMachine;
			Reamer rWorkingstep = (Reamer)ferramentaWorkingstep;
			if(rMachine.getDiametroFerramenta() == rWorkingstep.getDiametroFerramenta() &&
					rMachine.getToolTipHalfAngle() == rWorkingstep.getToolTipHalfAngle() &&
					rMachine.getCuttingEdgeLength() >= rWorkingstep.getCuttingEdgeLength() &&
					rMachine.getNumberOfTeeth() >= rWorkingstep.getNumberOfTeeth() &&
					rMachine.getHandOfCut() == rWorkingstep.getHandOfCut() &&
					rMachine.getMaterial() == rWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == BoringTool.class && ferramentaWorkingstep.getClass() == BoringTool.class)
		{
			BoringTool bMachine = (BoringTool)ferramentaMachine;
			BoringTool bWorkingstep = (BoringTool)ferramentaWorkingstep;
			if(bMachine.getDiametroFerramenta() == bWorkingstep.getDiametroFerramenta() &&
					bMachine.getToolTipHalfAngle() == bWorkingstep.getToolTipHalfAngle() &&
					bMachine.getCuttingEdgeLength() >= bWorkingstep.getCuttingEdgeLength() &&
					bMachine.getNumberOfTeeth() >= bWorkingstep.getNumberOfTeeth() &&
					bMachine.getHandOfCut() == bWorkingstep.getHandOfCut() &&
					bMachine.getMaterial() == bWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == FaceMill.class && ferramentaWorkingstep.getClass() == FaceMill.class)
		{
			FaceMill fMachine = (FaceMill)ferramentaMachine;
			FaceMill fWorkingstep = (FaceMill)ferramentaWorkingstep;
			if(fMachine.getDiametroFerramenta() == fWorkingstep.getDiametroFerramenta() &&
					fMachine.getToolTipHalfAngle() == fWorkingstep.getToolTipHalfAngle() &&
					fMachine.getCuttingEdgeLength() >= fWorkingstep.getCuttingEdgeLength() &&
					fMachine.getNumberOfTeeth() >= fWorkingstep.getNumberOfTeeth() &&
					fMachine.getHandOfCut() == fWorkingstep.getHandOfCut() &&
					fMachine.getMaterial() == fWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == EndMill.class && ferramentaWorkingstep.getClass() == EndMill.class)
		{
			EndMill emMachine = (EndMill)ferramentaMachine;
			EndMill emWorkingstep = (EndMill)ferramentaWorkingstep;
			if(emMachine.getDiametroFerramenta() == emWorkingstep.getDiametroFerramenta() &&
					emMachine.getToolTipHalfAngle() == emWorkingstep.getToolTipHalfAngle() &&
					emMachine.getCuttingEdgeLength() >= emWorkingstep.getCuttingEdgeLength() &&
					emMachine.getNumberOfTeeth() >= emWorkingstep.getNumberOfTeeth() &&
					emMachine.getHandOfCut() == emWorkingstep.getHandOfCut() &&
					emMachine.getMaterial() == emWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == BullnoseEndMill.class && ferramentaWorkingstep.getClass() == BullnoseEndMill.class)
		{
			BullnoseEndMill bnMachine = (BullnoseEndMill)ferramentaMachine;
			BullnoseEndMill bnWorkingstep = (BullnoseEndMill)ferramentaWorkingstep;
			if(bnMachine.getDiametroFerramenta() == bnWorkingstep.getDiametroFerramenta() &&
					bnMachine.getToolTipHalfAngle() == bnWorkingstep.getToolTipHalfAngle() &&
					bnMachine.getCuttingEdgeLength() >= bnWorkingstep.getCuttingEdgeLength() &&
					bnMachine.getNumberOfTeeth() >= bnWorkingstep.getNumberOfTeeth() &&
					bnMachine.getHandOfCut() == bnWorkingstep.getHandOfCut() &&
					bnMachine.getMaterial() == bnWorkingstep.getMaterial() &&
					bnMachine.getEdgeRadius() == bnWorkingstep.getEdgeRadius() &&
					bnMachine.getEdgeCenterHorizontal() == bnWorkingstep.getEdgeCenterHorizontal() &&
					bnMachine.getEdgeCenterVertical() == bnWorkingstep.getEdgeCenterVertical())
			{
				isSameTool = true;
			}
		} else if(ferramentaMachine.getClass() == BallEndMill.class && ferramentaWorkingstep.getClass() == BallEndMill.class)
		{
			BallEndMill bemMachine = (BallEndMill)ferramentaMachine;
			BallEndMill bemWorkingstep = (BallEndMill)ferramentaWorkingstep;
			if(bemMachine.getDiametroFerramenta() == bemWorkingstep.getDiametroFerramenta() &&
					bemMachine.getToolTipHalfAngle() == bemWorkingstep.getToolTipHalfAngle() &&
					bemMachine.getCuttingEdgeLength() >= bemWorkingstep.getCuttingEdgeLength() &&
					bemMachine.getNumberOfTeeth() >= bemWorkingstep.getNumberOfTeeth() &&
					bemMachine.getHandOfCut() == bemWorkingstep.getHandOfCut() &&
					bemMachine.getMaterial() == bemWorkingstep.getMaterial())
			{
				isSameTool = true;
			}
		}
		
		return isSameTool;
	}
}
