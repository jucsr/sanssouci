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
	private ArrayList<ArrayList<Double>> custoMatrix;
	private ArrayList<ArrayList<Double>> timesMatrix;
	private ArrayList<ArrayList<Integer>> pMatrix = new ArrayList<ArrayList<Integer>>();
	private Halevi halevi;
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
		this.fillTablesRouthing();
		this.fillTimesTable();
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
		halevi = new Halevi(projetoSF.getShopFloor(), workingsteps);
		custoMatrix = halevi.getUniversalCostMatrix();
		halevi.solveZMatrix();
		pMatrix = halevi.getpMatrix();
		
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
	private void fillTimesTable()
	{
		timesMatrix = halevi.getUniversalTimeMatrix();
//		halevi.solveZMatrix();
		
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
			this.table3.setModel(modelo);
			this.table4.setModel(modelo1);
			modelo.addRow(linha);
			modelo1.addRow(linha);
		}
		
		for(int i = 0; i < halevi.gettMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.gettMatrix().get(i).size(); j++)
			{
				this.table3.setValueAt(halevi.gettMatrix().get(i).get(j), i, j + 3);
			}
		}
		for(int i = 0; i < halevi.getpMatrix().size(); i++)
		{
			for(int j = 0; j < halevi.getpMatrix().get(i).size(); j++)
			{
				this.table4.setValueAt(halevi.getpMatrix().get(i).get(j), i, j + 3);
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
	
	private void fillTablesRouthing(){
		
		DefaultTableModel model = (DefaultTableModel) table5.getModel();
		double menorCusto = custoMatrix.get(0).get(0);
		int indiceMaquina = 0;
		int operation = 0, machine, trocou = 0, machineTest;
		double cost = 0, time, PenaltiesCust = 0, PenalitiesTime = 0, totalCost = 0, totalTime = 0;

		
		for(int i = 0; i < custoMatrix.get(0).size(); i++){
			
			if(custoMatrix.get(0).get(i) < menorCusto){
				
				menorCusto = custoMatrix.get(0).get(i);
				indiceMaquina = i;
				System.out.println("menor custo "+menorCusto);
			}
		}
		
		machine = indiceMaquina + 1;
		machineTest = machine;
		operation = 10;
		time = workingsteps.get(0).getTemposNasMaquinas().get(indiceMaquina);
		cost = custoMatrix.get(0).get(indiceMaquina);
		
		totalCost = cost;
		totalTime = time;
		
		Object [] linha2 = {operation, machine, cost, time};
		model.addRow(linha2);
	
		for(int i = 0; i <pMatrix.size() - 1; i++ ){
			
			for(int j = 0; j < pMatrix.get(0).size(); j++){
				
				if(j == indiceMaquina){
					
					machine = pMatrix.get(i).get(j);
					if(machineTest != machine){
						
						trocou++;
					}
						
					indiceMaquina = machine -1;
					
					time = workingsteps.get(i+1).getTemposNasMaquinas().get(indiceMaquina);
					cost = custoMatrix.get(i+1).get(indiceMaquina);
					
					totalCost = totalCost + cost;
					totalTime = totalTime + time;
					operation = operation + 10;
					
					
					
					Object [] linha = {operation, machine, cost, time};
					model.addRow(linha);
				}
			}
		}	
		PenaltiesCust = trocou * 0.2;
		PenalitiesTime = trocou * 0.03;
		
		Object[] linha3 = {"Total"," ", totalCost, totalTime};
		model.addRow(linha3);
		
		Object[] linha4 = {"Penalties "," ", PenaltiesCust, PenaltiesCust};
		model.addRow(linha4);
		
		totalCost = totalCost + PenaltiesCust;
		totalTime = totalTime + PenalitiesTime;
		
		Object [] linha5 = {"Total"," ", totalCost, totalTime};
		model.addRow(linha5);
	}
}










