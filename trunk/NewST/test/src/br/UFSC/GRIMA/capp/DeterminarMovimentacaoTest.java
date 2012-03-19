package br.UFSC.GRIMA.capp;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.Path;

public class DeterminarMovimentacaoTest
{
	@Test
	public void getTrajetoriasAcabamentoLadosCavidadeTest()
	{
		Bloco bloco = new Bloco(200, 150, 40);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 20, 0, 0,0,0, 10, 80, 100, 15);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.66);
		bottomAndSideRoughMilling.setAllowanceSide(0.58);
		bottomAndSideRoughMilling.setCoolant(true);
		
		BottomAndSideFinishMilling bottomAndSideFinishMilling = new BottomAndSideFinishMilling("acabamento", retractPlane);
		bottomAndSideFinishMilling.setCoolant(false);
		
		FaceMill faceMill = new FaceMill(15, 40);
		EndMill endMill = new EndMill(10, 40);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0, 2000, 2, 7);
		
		
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		
		Workingstep ws1 = new Workingstep(cavidade, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideFinishMilling);
		ws1.setFerramenta(endMill);
		
		DeterminarMovimentacao dm = new DeterminarMovimentacao();
		ArrayList<Path> trajetoriasCavidade = dm.getTrajetoriasAcabamentoLadosCavidade(ws1);
		
		
		for(int i = 0; i < trajetoriasCavidade.size(); i++)
		{
			
			System.out.println("" + i + " " + trajetoriasCavidade.get(i).getInitialPoint() + "\t" + trajetoriasCavidade.get(i).getFinalPoint() + "\t" + trajetoriasCavidade.get(i).toString());
			
		}
	}
	@Test
	public void getTrajetoriasCantosDaCavidadeTest()
	{
		Bloco bloco = new Bloco(200, 150, 40);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 8, 80, 100, 15);
		
		FaceMill faceMill = new FaceMill(20, 40);
		FaceMill faceMill2 = new FaceMill(3, 40);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		bottomAndSideRoughMilling.setCoolant(true);

		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("desbaste cantos", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(false);
		bottomAndSideRoughMilling1.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1.setAllowanceSide(0.5);
		Point3d startPoint = new Point3d(cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
		
		
		bottomAndSideRoughMilling1.setStartPoint(startPoint);
		
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0, 2000, 2, faceMill2.getDiametroFerramenta() * 0.75);
		
		
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(cavidade, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill2);
		ws1.setTipo(Workingstep.DESBASTE);

		DeterminarMovimentacao dm = new DeterminarMovimentacao();
		ArrayList<Path> trajetoriasCavidade = dm.getTrajetoriasCantosDaCavidade(ws1);
		
		for(int z = 0; z < trajetoriasCavidade.size(); z++){
			System.out.println("Size" + trajetoriasCavidade.size());
			System.out.println("Cantos cavidade" + trajetoriasCavidade.get(z));
			System.out.println("Cantos cavidade" + trajetoriasCavidade.get(z).getInitialPoint());
			System.out.println("Cantos cavidade" + trajetoriasCavidade.get(z).getFinalPoint());
		}
	}
}
