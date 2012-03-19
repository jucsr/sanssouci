package br.UFSC.GRIMA.capp;

import java.util.Vector;

public class OrdenadoraDeWorkingsteps {
	
	public static Vector<Vector<Workingstep>> ordenarWorkingSteps(Vector<Vector<Workingstep>> allWorkingsteps){
		
		Vector<Vector<Workingstep>> allWorkingstepsTMP = new Vector<Vector<Workingstep>>();
		
		for(int i = 0; i<allWorkingsteps.size();i++){
			
			Vector<Workingstep> workingstepsTMP = new Vector<Workingstep>();
			Vector<Workingstep> rghsTMP = new Vector<Workingstep>();
			Vector<Workingstep> fnssTMP = new Vector<Workingstep>();
			
			
			for(int j = 0; j<allWorkingsteps.get(i).size();j++){
				
				Workingstep wsTmp = allWorkingsteps.get(i).get(j);
				
				if(wsTmp.getTipo()==Workingstep.DESBASTE)
					rghsTMP.add(wsTmp);
				else if(wsTmp.getTipo()==Workingstep.ACABAMENTO)
					fnssTMP.add(wsTmp);
				else
					System.out.println("Tipo de Workingstep Desconhecido! (OrdenadoraDeWorkindsteps");
				
			}
			
			for(int j = 0; j<rghsTMP.size();j++)
				workingstepsTMP.add(rghsTMP.get(j));

			for(int j = 0; j<fnssTMP.size();j++)
				workingstepsTMP.add(fnssTMP.get(j));

			allWorkingstepsTMP.add(workingstepsTMP);
			
			
		}
		
		allWorkingsteps = allWorkingstepsTMP;
		
		return allWorkingsteps;
	}
	
}
