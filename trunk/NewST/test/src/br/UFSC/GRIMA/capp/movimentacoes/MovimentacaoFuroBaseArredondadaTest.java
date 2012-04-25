package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.Path;

public class MovimentacaoFuroBaseArredondadaTest
{
	@Test
	public void destasteTest()
	{
		Bloco bloco = new Bloco(200, 150, 80);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		FuroBaseArredondada feature = new FuroBaseArredondada("FURO", 70, 55, 0, 60, 40, 10, 0.05);
		
		
		// ---- MILLING
		BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		//BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		milling.setCoolant(true);
		milling.setStartPoint(new Point3d(0, 0, 0));
		milling.setAllowanceSide(0.5);
		milling.setAllowanceBottom(0.5);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		FaceMill faceMill = new FaceMill(10,50);
		
		Workingstep ws = new Workingstep(feature, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(milling);
		ws.setFerramenta(faceMill);
		
		MovimentacaoFuroBaseArredondada m = new MovimentacaoFuroBaseArredondada(ws);
		ArrayList<Path> path = m.desbaste();
		
		for(Path patTmp:path)
		{
//			System.out.println(patTmp);
		}
	}
}
