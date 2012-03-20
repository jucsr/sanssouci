package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoDegrauTest {
	@Test
	public void getMovimentacaoDegrauTest()
	{
		Bloco bloco = new Bloco(200, 150, 80);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		Degrau degrau = new Degrau("DEGRAU",Degrau.HORIZONTAL,0,180,0,20,3);
		degrau.setComprimento(200);
		// ---- MILLING
		BottomAndSideFinishMilling milling = new BottomAndSideFinishMilling("Fresamento", retractPlane);
		milling.setCoolant(true);
		milling.setStartPoint(new Point3d(0, 0, 0));
		milling.setAllowanceSide(0);
		milling.setAllowanceBottom(0);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		EndMill endMill = new EndMill(10,50);
		
		//--- BSRM WS
		Workingstep ws = new Workingstep(degrau, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(milling);
		ws.setFerramenta(endMill);
		
		MovimentacaoDegrau mbe = new MovimentacaoDegrau(ws);
		ArrayList<LinearPath> lado2 = mbe.getMovimentacaoAcabamentoDegrau();
		for(int i = 0; i < lado2.size(); i++){
			System.out.println(lado2.get(i).getFinalPoint());
		}
		
//		ArrayList<Path> trajetorias = mbe.getVetorDeMovimentacao();
//		
//		for(int i = 0; i < trajetorias.size(); i++)
//		{
//			
//			System.out.println("" + i + " " + trajetorias.get(i).getClass() + trajetorias.get(i).getInitialPoint() + "\t" + trajetorias.get(i).getFinalPoint());
//			
//		}
	}
}
