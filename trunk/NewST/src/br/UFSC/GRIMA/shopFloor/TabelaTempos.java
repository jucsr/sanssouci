package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
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
		ArrayList<Workingstep> workingsteps = new ArrayList<Workingstep>();
		ArrayList<MachineTool> machines = projetoSF.getShopFloor().getMachines();
		for(int i = 0; i < this.projetoSF.getProjeto().getWorkingsteps().get(0).size(); i++)
		{
			workingsteps.add(this.projetoSF.getProjeto().getWorkingsteps().get(0).elementAt(i));
		}
		DefaultTableModel modelo = (DefaultTableModel)this.table1.getModel();
		for(int j = 0; j < machines.size(); j++)
		{
			modelo.addColumn(machines.get(j).getItsId());
		}
		for(int i = 0; i < workingsteps.size(); i++)
		{
			Workingstep wsTmp = workingsteps.get(i);
			String nome = wsTmp.getId();
			int id = 10 + i * 10;
			Object[] linha = {nome, id};
			this.table1.setModel(modelo);
			modelo.addRow(linha);
		}
//		this.table1.setValueAt(2.66, 1, 3);
	}
	private void calculateTempo() 
	{
		
	}
}
