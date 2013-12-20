package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author jc
 * 
 * 	Teste dos metodos de criacao das matrizes
 *
 */
public class HaleviTest 
{
	public ArrayList<Workingstep> workingsteps = new ArrayList<Workingstep>();
	private ShopFloor shopFloor; 
	public ArrayList<MachineTool> machines = new ArrayList<MachineTool>();
	public Halevi halevi;
	public HaleviTest()
	{
		//initComponents();
	}
	
	@Before
	public void initComponents()
	{		
		/*
		 * creating machining workingsteps
		 */
		Workingstep ws10 = new Workingstep();
		ArrayList<Double> temposNasMaquinasOp10 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp20 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp30 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp40 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp50 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp60 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp70 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp80 = new ArrayList<Double>();
		ArrayList<Double> temposNasMaquinasOp90 = new ArrayList<Double>();
		
		temposNasMaquinasOp10.add(0.57);
		temposNasMaquinasOp10.add(0.62);
		temposNasMaquinasOp10.add(1.28);
		temposNasMaquinasOp10.add(99.0);
		temposNasMaquinasOp10.add(1.62);
		temposNasMaquinasOp10.add(1.18);
		
		temposNasMaquinasOp20.add(0.27);
		temposNasMaquinasOp20.add(0.32);
		temposNasMaquinasOp20.add(0.88);
		temposNasMaquinasOp20.add(99.0);
		temposNasMaquinasOp20.add(1.22);
		temposNasMaquinasOp20.add(0.59);
		
		temposNasMaquinasOp30.add(0.41);
		temposNasMaquinasOp30.add(0.46);
		temposNasMaquinasOp30.add(0.97);
		temposNasMaquinasOp30.add(99.0);
		temposNasMaquinasOp30.add(99.0);
		temposNasMaquinasOp30.add(0.56);
		
		temposNasMaquinasOp40.add(1.99);
		temposNasMaquinasOp40.add(2.04);
		temposNasMaquinasOp40.add(2.55);
		temposNasMaquinasOp40.add(99.0);
		temposNasMaquinasOp40.add(99.0);
		temposNasMaquinasOp40.add(2.14);
		
		temposNasMaquinasOp50.add(0.34);
		temposNasMaquinasOp50.add(0.39);
		temposNasMaquinasOp50.add(0.99);
		temposNasMaquinasOp50.add(99.0);
		temposNasMaquinasOp50.add(1.32);
		temposNasMaquinasOp50.add(0.74);
		
		temposNasMaquinasOp60.add(4.26);
		temposNasMaquinasOp60.add(4.31);
		temposNasMaquinasOp60.add(4.82);
		temposNasMaquinasOp60.add(99.0);
		temposNasMaquinasOp60.add(99.0);
		temposNasMaquinasOp60.add(4.41);

		temposNasMaquinasOp70.add(0.13);
		temposNasMaquinasOp70.add(0.18);
		temposNasMaquinasOp70.add(0.69);
		temposNasMaquinasOp70.add(0.69);
		temposNasMaquinasOp70.add(1.03);
		temposNasMaquinasOp70.add(0.28);
		
		temposNasMaquinasOp80.add(0.32);
		temposNasMaquinasOp80.add(0.37);
		temposNasMaquinasOp80.add(0.88);
		temposNasMaquinasOp80.add(0.88);
		temposNasMaquinasOp80.add(1.22);
		temposNasMaquinasOp80.add(0.47);
		
		temposNasMaquinasOp90.add(0.30);
		temposNasMaquinasOp90.add(0.35);
		temposNasMaquinasOp90.add(0.86);
		temposNasMaquinasOp90.add(0.86);
		temposNasMaquinasOp90.add(99.0);
		temposNasMaquinasOp90.add(0.45);
		
		
		
		Workingstep ws20 = new Workingstep();
		Workingstep ws30 = new Workingstep();
		Workingstep ws40 = new Workingstep();
		Workingstep ws50 = new Workingstep();
		Workingstep ws60 = new Workingstep();
		Workingstep ws70 = new Workingstep();
		Workingstep ws80 = new Workingstep();
		Workingstep ws90 = new Workingstep();
		ws10.setTemposNasMaquinas(temposNasMaquinasOp10);
		ws10.setWorkingstepPrecedente(null);
		ws10.setId("Bottom And Side Rough Milling");
		ws20.setTemposNasMaquinas(temposNasMaquinasOp20);
		ws20.setWorkingstepPrecedente(ws10);
		ws20.setId("Bottom And Side Rough Milling");
		ws30.setTemposNasMaquinas(temposNasMaquinasOp30);
		ws30.setWorkingstepPrecedente(ws20);
		ws30.setId("Bottom And Side Rough Milling");
		ws40.setTemposNasMaquinas(temposNasMaquinasOp40);
		ws40.setWorkingstepPrecedente(ws30);
		ws40.setId("Bottom And Side Finish Milling");
		ws50.setTemposNasMaquinas(temposNasMaquinasOp50);
		ws50.setWorkingstepPrecedente(ws10);
		ws50.setId("Bottom And Side Rough Milling");
		ws60.setTemposNasMaquinas(temposNasMaquinasOp60);
		ws60.setWorkingstepPrecedente(ws50);
		ws60.setId("Bottom And Side Finish Milling");
		ws70.setTemposNasMaquinas(temposNasMaquinasOp70);
		ws70.setWorkingstepPrecedente(ws20);
		ws70.setId("Center Drilling");
		ws80.setTemposNasMaquinas(temposNasMaquinasOp80);
		ws80.setWorkingstepPrecedente(ws70);
		ws80.setId("Drilling");
		ws90.setTemposNasMaquinas(temposNasMaquinasOp90);
		ws90.setWorkingstepPrecedente(ws80);
		ws90.setId("Boring");
		
		this.workingsteps.add(ws10);
		this.workingsteps.add(ws20);
		this.workingsteps.add(ws30);
		this.workingsteps.add(ws40);
		this.workingsteps.add(ws50);
		this.workingsteps.add(ws60);
		this.workingsteps.add(ws70);
		this.workingsteps.add(ws80);
		this.workingsteps.add(ws90);
		
		System.out.println("ok");
		Bloco bloco = new Bloco(200, 150, 50);
		Projeto p = new Projeto();
		
		for(int i = 0; i < workingsteps.size(); i++)
		{
			
//			p.setWorkingsteps(workingsteps);
		}
		/*
		 * creating machines
		 */
		MachineTool m1 = new MachineTool();
		MachineTool m2 = new MachineTool();
		MachineTool m3 = new MachineTool();
		MachineTool m4 = new MachineTool();
		MachineTool m5 = new MachineTool();
		MachineTool m6 = new MachineTool();
		
		m1.setRelativeCost(4);
		m1.setSetUpTime(30);
		m1.setItsId("MAQ 1");
		m2.setRelativeCost(3);
		m2.setSetUpTime(30);
		m2.setItsId("MAQ 2");
		m3.setRelativeCost(1.4);
		m3.setSetUpTime(30);
		m3.setItsId("MAQ 3");
		m4.setRelativeCost(1);
		m4.setSetUpTime(30);
		m4.setItsId("MAQ 4");
		m5.setRelativeCost(1);
		m5.setSetUpTime(30);
		m5.setItsId("MAQ 5");
		m6.setRelativeCost(2);
		m6.setSetUpTime(30);
		m6.setItsId("MAQ 6");

		this.machines.add(m1);
		this.machines.add(m2);
		this.machines.add(m3);
		this.machines.add(m4);
		this.machines.add(m5);
		this.machines.add(m6);
		
		/*
		 * creating shopFloor
		 */
		this.shopFloor = new ShopFloor("SHOP FLOOR", 0, 20, 15);
		this.shopFloor.setMachines(this.machines);
	}
	
	@Test
	public void solveZMatrixTest()
	{
		this.halevi = new Halevi(this.shopFloor, this.workingsteps);
		System.out.println(""+this.workingsteps.size());
		System.out.println(""+this.shopFloor.getMachines().size());
		this.halevi.getUniversalCostMatrix();
		//System.out.println(this.halevi.getUniversalCostMatrix());
		this.halevi.solveZMatrix();
		this.halevi.MakeRankedList();
		System.out.println(this.halevi.getZnormalMatrix());
		System.out.println(this.halevi.getzMatrix());
		System.out.println(this.halevi.getpMatrix());
		
		System.out.println(this.halevi.getRankedList().get(0).getTime());
		System.out.println(this.halevi.getRankedList().get(0).getList());
		System.out.println(this.halevi.getRankedList().get(0).getWorkingstep());
	}
}
