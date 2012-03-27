package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.LinearPath;

public class MovimentacaoRanhuraPerfilQuadradoUTest {
	@Test
	public void getMovimentacaoRanhuraPerfilQuadradoUTest()
	{
		Bloco bloco = new Bloco(200, 150, 80);
		Face face = (Face) bloco.faces.get(Face.XY);
		double retractPlane = 21;
		RanhuraPerfilQuadradoU ranhuraQuadU = new RanhuraPerfilQuadradoU("RANHURAPERFILQUADRADOU",10,0,0,0,0,0,50,25,Ranhura.VERTICAL,5,90,21.132);
		ranhuraQuadU.setComprimento(150);
		
		
		// ---- MILLING
		BottomAndSideFinishMilling milling = new BottomAndSideFinishMilling("Fresamento", retractPlane);
		//BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Fresamento", retractPlane);
		milling.setCoolant(true);
		milling.setStartPoint(new Point3d(0, 0, 0));
		milling.setAllowanceSide(0.5);
		milling.setAllowanceBottom(0.5);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0, 2000, 2, 5);
		FaceMill faceMill = new FaceMill(30,50);
		
		//--- BSRM WS
		Workingstep ws = new Workingstep(ranhuraQuadU, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(milling);
		ws.setFerramenta(faceMill);
		
		MovimentacaoRanhuraPerfilQuadradoU mbe = new MovimentacaoRanhuraPerfilQuadradoU(ws);
		ArrayList<LinearPath> lado2 = mbe.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU();
		
		//ws.setPontosMovimentacao(mbe.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU(lado2));
		
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
