package br.UFSC.GRIMA.shopFloor;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.visual.TabelaCustosETemposFrame;

public class TabelaCustosETempos extends TabelaCustosETemposFrame
{
	private ProjetoSF projetoSF;
	private JanelaShopFloor janelaShopFloor;
	private ArrayList<Workingstep> workingsteps;
	
	public TabelaCustosETempos(Frame owner, ProjetoSF projetoSF) 
	{
		super(owner);
		this.projetoSF = projetoSF;
		workingsteps = new ArrayList<Workingstep>();
		
//		ArrayList<MachineTool> machines = projetoSF.getShopFloor().getMachines();
//		for(int i = 0; i < this.projetoSF.getProjeto().getWorkingsteps().get(0).size(); i++)
//		{
//			workingsteps.add(this.projetoSF.getProjeto().getWorkingsteps().get(0).elementAt(i));
//		}
		this.fillTables();
		this.setVisible(true);
	}
	
	private void fillTables()
	{
		HaleviTest halevi = new HaleviTest();
		halevi.solveZMatrixTest();
		
		Vector<String> cabecalho = new Vector<String>();
		cabecalho.add("M. Workingsteps");
		cabecalho.add("ID");
//		cabecalho.add("Priorities");
		for(int i = 0; i < halevi.machines.size(); i++)
		{
			cabecalho.add(halevi.machines.get(i).getItsId());
		}
		DefaultTableModel modelo = new DefaultTableModel(cabecalho, 0);
		DefaultTableModel modelo1 = new DefaultTableModel(cabecalho, 0);
		for(int i = 0; i < halevi.workingsteps.size(); i++)
		{
			Workingstep wsTmp = halevi.workingsteps.get(i);
			String nome = wsTmp.getId();
			int id = 10 + i * 10;
			Object[] linha = {nome, id};
			this.table1.setModel(modelo);
			this.table2.setModel(modelo1);
			modelo.addRow(linha);
			modelo1.addRow(linha);
		}
		
		for(int i = 0; i < halevi.halevi.getzMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.halevi.getzMatrix().get(i).size(); j++)
			{
				this.table1.setValueAt(halevi.halevi.getzMatrix().get(i).get(j), i, j + 2);
			}
		}
		for(int i = 0; i < halevi.halevi.getpMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.halevi.getpMatrix().get(i).size(); j++)
			{
				this.table2.setValueAt(halevi.halevi.getpMatrix().get(i).get(j), i, j + 2);
			}
		}
	}
}
