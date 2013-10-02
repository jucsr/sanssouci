package br.UFSC.GRIMA.shopFloor.util;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.MillingTypeSpindle;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CalculateMachiningTimeTest 
{
	private static final Degrau Degrau = null;
	Projeto projeto;
	Bloco bloco;
	Cavidade cavidade;
	FuroBasePlana furo;
	Ranhura ranhura;
	Degrau degrau;
	Face faceXY;
	Workingstep workingstep;
	ArrayList<MachineTool> machines = new ArrayList<MachineTool>();
	Material material;
	MachineTool m1;

	@Before
	public void createProject(){
		

		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		material = new Material("Aço", Material.ACO_SEM_LIGA, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
		
		new MapeadoraDeWorkingsteps(projeto);
		
		//adicionando feature na face
		faceXY = (Face)(bloco.faces.elementAt(Face.XY));
		

		// ---- feature definition ----
	
		cavidade = new Cavidade();
		cavidade.setComprimento(80);
		cavidade.setLargura(50);
		cavidade.setProfundidade(20);
		cavidade.setPosicao(10, 50, 0);
		cavidade.setRaio(10);
		cavidade.setNome("Cavidade 1");
		Point3d coordinates = new Point3d(70 + 40, 50 + 25, 50 - 0);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		cavidade.setPosition(position);
		cavidade.setPassante(true);
		cavidade.setTolerancia(0.05);
		cavidade.setRugosidade(0.05);
		
		// criando uma ranhura
		
		ranhura = new Ranhura();
		ranhura.setComprimento(200);
		ranhura.setLargura(40);
		ranhura.setProfundidade(20);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		faceXY.features.addElement(ranhura);	
    	System.out.println("Face "+faceXY.getComprimento());

		// criando um furo
		
		furo = new FuroBasePlana();
		furo.setRaio(5);
		furo.setProfundidade(20);
		
		// criando degraus
		
		degrau = new Degrau();
		degrau.setComprimento(150);
		degrau.setProfundidade(15);
		degrau.setLargura(30);
		degrau.setEixo(Degrau.VERTICAL);
		
		
	/*
	 * 	Creating Tool
	 */
		FaceMill faceMill = new FaceMill();
		faceMill.setDiametroFerramenta(20);
		faceMill.setCuttingEdgeLength(20);
		faceMill.setProfundidadeMaxima(60);
		faceMill.setMaterialClasse(Ferramenta.H);
		faceMill.setNumberOfTeeth(4);
		
		TwistDrill twistDrill = new TwistDrill();
		twistDrill.setDiametroFerramenta(10);
		twistDrill.setProfundidadeMaxima(50);
		twistDrill.setMaterialClasse(TwistDrill.H);
		
		/*
		 *  Creating Operation
		 */
		MachiningOperation operation = new BottomAndSideRoughMilling("Fresamento", 5);
		
		MachiningOperation operation2 = new Drilling("Drilling", 5);
		
		/*
		 *  Creating Cutting Conditions
		 */
		CondicoesDeUsinagem cond = new CondicoesDeUsinagem();
		cond.setVc(95); // m/minuto
		cond.setF(0.245); // mm/rotacao
		
		CondicoesDeUsinagem cond2 = new CondicoesDeUsinagem();
		cond2.setAe(faceMill.getDiametroFerramenta()*0.75);
		cond2.setF(0.0235);
		cond2.setAp(2);
		cond2.setVc(55); 
		
		workingstep = new Workingstep();
		workingstep.setFeature(ranhura);
		workingstep.setFerramenta(faceMill);
		workingstep.setOperation(operation);
		workingstep.setCondicoesUsinagem(cond2);
		workingstep.setFace(faceXY);

		
		m1 = new MillingMachine();
		ArrayList<Spindle> spindles = new ArrayList<Spindle>();
		Spindle spindle = new MillingTypeSpindle();
		spindle.setSpindleMaxPower(10); 	// kW
		spindle.setItsSpeedRange(5000); // RPM
		spindle.setMaxTorque(10);		// N-m
		spindles.add(spindle);
		m1.setItsSpindle(spindles);
		this.machines.add(m1);
		
//		if(workingstep.getOperation().getClass().equals(CenterDrilling.class))
//		{
//			
//		} else if(workingstep.get)
	}
	@Test
	public void calcularTempo()
	{
		CalculateMachiningTime calculate = new CalculateMachiningTime(workingstep, m1, material);
	double tempo = calculate.calculateTimes();
	System.out.println("Tempo = " + tempo);
	}
}
