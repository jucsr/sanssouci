package br.UFSC.GRIMA.cam;


import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.movimentacoes.GenerateTrochoidalMovement1;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;

public class GenerateTrocoidalGCode 
{
	private Workingstep ws;
	public GenerateTrocoidalGCode(Workingstep ws)
	{
		this.ws = ws;
	}
	private String getGCode()
	{
//		GeneralClosedPocket pocket = ((GeneralClosedPocket)ws.getFeature());
		double offset = ((TrochoidalAndContourParallelStrategy)ws.getOperation().getMs()).getTrochoidalRadius();
		
//		ArrayList<ArrayList<LimitedElement>> elements = GeometricOperations.multipleParallelPath(pocket, offset,);
//		for(int i = 0;i < elements.size();i++)
//		{
//			GenerateTrochoidalMovement1 tm = new GenerateTrochoidalMovement1(elements, radius, avanco)
//		}
		String GCode = "N" + " G54" + "\n";
//		String ferramenta = ws.getFerramenta().getName();
//		((BottomAndSideRoughMilling)ws.getOperation()).get
		return GCode;
	}
}
