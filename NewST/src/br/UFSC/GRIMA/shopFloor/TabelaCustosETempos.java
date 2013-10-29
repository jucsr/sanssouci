package br.UFSC.GRIMA.shopFloor;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.util.CalculateMachiningTime;
import br.UFSC.GRIMA.shopFloor.visual.TabelaCustosETemposFrame;

public class TabelaCustosETempos extends TabelaCustosETemposFrame
{
	private ProjetoSF projetoSF;
	private JanelaShopFloor janelaShopFloor;
	private ArrayList<Workingstep> workingsteps;
	private ArrayList<MachineTool> machines;
	
	public TabelaCustosETempos(Frame owner, ProjetoSF projetoSF) 
	{
		super(owner);
		this.projetoSF = projetoSF;
		workingsteps = new ArrayList<Workingstep>();
		this.machines = this.projetoSF.getShopFloor().getMachines();
//		ArrayList<MachineTool> machines = projetoSF.getShopFloor().getMachines();
		// enchendo o array de workingsteps
		for(int i = 0; i < this.projetoSF.getProjeto().getWorkingsteps().get(0).size(); i++)
		{
			workingsteps.add(this.projetoSF.getProjeto().getWorkingsteps().get(0).elementAt(i));
		}
		this.calculateTempo();
//		this.fillTables();
		this.fillTables1();
		this.setVisible(true);
		this.okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		this.cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
	}
	private void fillTables1()
	{
		Halevi halevi = new Halevi(projetoSF.getShopFloor(), workingsteps);
		halevi.getUniversalCostMatrix();
		halevi.solveZMatrix();
		
		Vector<String> cabecalho = new Vector<String>();
		cabecalho.add("M. Workingsteps");
		cabecalho.add("ID");
		cabecalho.add("Priorities");
		for(int i = 0; i < machines.size(); i++)
		{
			cabecalho.add(machines.get(i).getItsId());
		}
		DefaultTableModel modelo = new DefaultTableModel(cabecalho, 0);
		DefaultTableModel modelo1 = new DefaultTableModel(cabecalho, 0);
		for(int i = 0; i < workingsteps.size(); i++)
		{
			Workingstep wsTmp = workingsteps.get(i);
//			System.out.println("tempo nas maquinas = " + wsTmp.getTemposNasMaquinas().get(0));
			Workingstep wsPrecedente = wsTmp.getWorkingstepPrecedente();
			int idPrecedente = 0;
			if(wsPrecedente == null)
			{
				idPrecedente = 0;
			} else
			{
				for(int j = 0; j < workingsteps.size(); j++)
				{
					if(wsPrecedente == workingsteps.get(j))
					{
						idPrecedente = 10 + j * 10;
					}
				}
			}
			
			String nome = wsTmp.getId();
			int id = 10 + i * 10;
			Object[] linha = {nome, id, idPrecedente};
			this.table1.setModel(modelo);
			this.table2.setModel(modelo1);
			modelo.addRow(linha);
			modelo1.addRow(linha);
		}
		
		for(int i = 0; i < halevi.getzMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.getzMatrix().get(i).size(); j++)
			{
				this.table1.setValueAt(halevi.getzMatrix().get(i).get(j), i, j + 3);
			}
		}
		for(int i = 0; i < halevi.getpMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.getpMatrix().get(i).size(); j++)
			{
				this.table2.setValueAt(halevi.getpMatrix().get(i).get(j), i, j + 3);
			}
		}
	}
	private void calculateTempo() 
	{
		double tempo = 0;
		for(int i = 0; i < workingsteps.size(); i++)
		{
			ArrayList<Double> temposNasMaquinas = new ArrayList<Double>();
			for(int j = 0; j < machines.size(); j++ )
			{
				CalculateMachiningTime calculateTime = new CalculateMachiningTime(workingsteps.get(i), machines.get(j), projetoSF.getProjeto().getBloco().getMaterial());
				if(calculateTime.calculateTimes()== 0)
				{
					tempo = 1000;
				}else 
				{
					tempo = calculateTime.calculateTimes();
				}
				temposNasMaquinas.add(tempo);
			}    
			workingsteps.get(i).setTemposNasMaquinas(temposNasMaquinas);
		}
	}
	private void fillTables()
	{
		HaleviTest halevi = new HaleviTest();
		halevi.solveZMatrixTest();
		
		Vector<String> cabecalho = new Vector<String>();
		cabecalho.add("M. Workingsteps");
		cabecalho.add("ID");
		cabecalho.add("Priorities");
		for(int i = 0; i < halevi.machines.size(); i++)
		{
			cabecalho.add(halevi.machines.get(i).getItsId());
		}
		DefaultTableModel modelo = new DefaultTableModel(cabecalho, 0);
		DefaultTableModel modelo1 = new DefaultTableModel(cabecalho, 0);
		for(int i = 0; i < halevi.workingsteps.size(); i++)
		{
			Workingstep wsTmp = halevi.workingsteps.get(i);
			Workingstep wsPrecedente = wsTmp.getWorkingstepPrecedente();
			int idPrecedente = 0;
			if(wsPrecedente == null)
			{
				idPrecedente = 0;
			} else
			{
				for(int j = 0; j < workingsteps.size(); j++)
				{
					if(wsPrecedente == workingsteps.get(j))
					{
						idPrecedente = 10 + j * 10;
					}
				}
			}
			
			String nome = wsTmp.getId();
			int id = 10 + i * 10;
			Object[] linha = {nome, id, idPrecedente};
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
