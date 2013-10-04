package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.shopFloor.util.CalculateMachiningTime;
import br.UFSC.GRIMA.shopFloor.visual.TabelaTemposFrame;

public class TabelaTempos extends TabelaTemposFrame
{
	private ProjetoSF projetoSF;
	ArrayList<Workingstep> workingsteps;
	ArrayList<MachineTool> machines; 
	
	public TabelaTempos(ProjetoSF projetoSF)
	{
		this.projetoSF = projetoSF;
		workingsteps= new ArrayList<Workingstep>();
		for(int i = 0; i < this.projetoSF.getProjeto().getWorkingsteps().get(0).size(); i++)
		{
			workingsteps.add(this.projetoSF.getProjeto().getWorkingsteps().get(0).elementAt(i));
		}
		machines = projetoSF.getShopFloor().getMachines();
		this.init();
		this.calculateTempo();
		this.setVisible(true);
	}
	private void init()
	{
		
		Vector<String> cabecalho = new Vector<String>();
		cabecalho.add("M. Workingsteps");
		cabecalho.add("ID");
		cabecalho.add("Priorities");
		
		for(int i = 0; i < machines.size(); i++)
		{
			cabecalho.add(machines.get(i).getItsId());
		}
		DefaultTableModel modelo = new DefaultTableModel(cabecalho, 0);
		
		for(int i = 0; i < workingsteps.size(); i++)
		{
			Workingstep wsTmp = workingsteps.get(i);
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
			modelo.addRow(linha);
		}
//		modelo.setValueAt(23, 5, 5);
	}
	private void calculateTempo() 
	{
		double T = 0;
		Workingstep wstTemp;
		for(int i = 0; i < workingsteps.size(); i++){
			ArrayList<Double> temposNasMaquinas = new ArrayList<Double>();
			wstTemp = workingsteps.get(i);
			for(int j = 0; j < machines.size(); j++ ){
			
				CalculateMachiningTime calculateTime = new CalculateMachiningTime(wstTemp, machines.get(j), projetoSF.getProjeto().getBloco().getMaterial());
				if(calculateTime.calculateTimes()== 0){
				T = 1000;
				}else T = calculateTime.calculateTimes();
				
				temposNasMaquinas.add(T);
				System.out.println("P = "+ ((machines.get(j).getItsSpindle()).size()));
			this.table1.getModel().setValueAt(T, i, j + 3);

			}    
			workingsteps.get(i).setTemposNasMaquinas(temposNasMaquinas);
		}
		
	}
}
