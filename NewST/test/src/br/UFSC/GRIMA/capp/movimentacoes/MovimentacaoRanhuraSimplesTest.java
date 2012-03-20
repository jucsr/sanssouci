package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraSimplesTest {
	@Test
	public void getTrajetoriasAcabamentoLadosCavidadeTest()
	{
		Bloco bloco = new Bloco(200, 150, 80);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		//Ranhura ranhura = new Ranhura("ranhura", 0, 0, 0, 74, 3, Ranhura.HORIZONTAL, Vector);
		Ranhura ranhura = new Ranhura("ranhura", 60, 0, 0, 0, 0, 0, 20, 5, 100, Ranhura.VERTICAL);
		// ---- MILLING
		
		//BottomAndSideFinishMilling milling = new BottomAndSideFinishMilling("Fresamento", retractPlane);
		BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		milling.setCoolant(true);
		milling.setStartPoint(new Point3d(0, 0, 0));
		milling.setAllowanceSide(0.5);
		milling.setAllowanceBottom(0.5);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		//EndMill endMill = new EndMill(10,50);
		FaceMill faceMill = new FaceMill(10,50);
		
		//--- BSRM WS
		Workingstep ws = new Workingstep(ranhura, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(milling);
		//ws.setFerramenta(endMill);
		ws.setFerramenta(faceMill);
		
		MovimentacaoRanhuraSimples mbe = new MovimentacaoRanhuraSimples(ws);
		ArrayList<LinearPath> lado2 = mbe.getMovimentacaoDesbasteRanhura();
	//	ArrayList<LinearPath> lado2 = mbe.getMovimentacaoAcabamentoRanhura();
		for(int i = 0; i<lado2.size();i++){
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
