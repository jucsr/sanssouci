package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseEsfericaTest {
	@Test
	public void getTrajetoriasAcabamentoLadosCavidadeTest()
	{
		Bloco bloco = new Bloco(200, 150, 80);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		FuroBaseEsferica furo = new FuroBaseEsferica("cavidade", 40, 60, 0, 74, 25, 0.05);
		
		// ---- DRILLING
		Drilling drilling = new Drilling("Furação", retractPlane);
		drilling.setCoolant(true);
		drilling.setCuttingDepth(furo.getProfundidade());
		drilling.setStartPoint(new Point3d(0, 0, 0));
		
		// ---- TWIST DRILL
		TwistDrill td = new TwistDrill("Broca", "P", 20, 20, 50, 60, 80, 20, 0.05, 0.05, Ferramenta.RIGHT_HAND_OF_CUT);
		Point3d startPoint = new Point3d();
		if(furo.getRaio() - td.getDiametroFerramenta()/2>= td.getDiametroFerramenta()/2)
		{
			startPoint = new Point3d(td.getDiametroFerramenta()/2, 0, 0);
		} else
		{
			startPoint = new Point3d(furo.getRaio() - td.getDiametroFerramenta()/2, 0, 0);
		}
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setStartPoint(startPoint);
		bottomAndSideRoughMilling.setAllowanceSide(0.0);
		bottomAndSideRoughMilling.setAllowanceBottom(0.66);
		bottomAndSideRoughMilling.setCoolant(true);
		
		BottomAndSideRoughMilling bsrm2 = new BottomAndSideRoughMilling("desbaste 2", retractPlane);
		bsrm2.setCoolant(false);
		
		FaceMill faceMill = new FaceMill(10, 40);
		FaceMill faceMill2 = new FaceMill(10, 40);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0, 2000, 2, 7);
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(150, 0.02, 0.02, 1200, 0, 0);
		
		//--- DRILLING WS
		Workingstep wsDrilling = new Workingstep(furo, face);
		wsDrilling.setCondicoesUsinagem(cu2);
		wsDrilling.setOperation(drilling);
		wsDrilling.setFerramenta(td);
		
		
		//--- BSRM WS
		Workingstep ws = new Workingstep(furo, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		
		//--- BSRM2 WS
		Workingstep ws1 = new Workingstep(furo, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bsrm2);
		ws1.setFerramenta(faceMill2);
		
		MovimentacaoFuroBaseEsferica mbe = new MovimentacaoFuroBaseEsferica(ws);
		ArrayList<Path> trajetorias = mbe.getVetorDeMovimentacao();
		
		for(int i = 0; i < trajetorias.size(); i++)
		{
			
			System.out.println("" + i + " " + trajetorias.get(i).getClass() + trajetorias.get(i).getInitialPoint() + "\t" + trajetorias.get(i).getFinalPoint());
			
		}
	}
}
