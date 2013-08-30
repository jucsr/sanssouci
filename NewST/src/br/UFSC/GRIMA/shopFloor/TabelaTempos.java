package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.shopFloor.visual.TabelaTemposFrame;

public class TabelaTempos extends TabelaTemposFrame
{
	private ProjetoSF projetoSF;
	public TabelaTempos(ProjetoSF projetoSF)
	{
		this.projetoSF = projetoSF;
//		this.calculateTempo();
		this.init();
		this.setVisible(true);
	}
	private void init()
	{
		ArrayList<Workingstep> workingsteps = projetoSF.getWorkingsteps();
		for(int i = 0; i < workingsteps.size(); i++)
		{
			Workingstep wsTmp = workingsteps.get(i);
			String nome = wsTmp.getId();
			this.table1.setValueAt(nome, 0, i);
		}
	}
	private void calculateTempo() 
	{
		
	}
	
}
