package br.UFSC.GRIMA.entidades;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.GCodeGenerator;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class GCodeGeneratorTest {

	private StepNcProject stepNcProject;
	private SdaiRepository repository;
	private SdaiModel model;
	private Bloco bloco = null;
	private DadosDeProjeto dadosDeProjeto = null;
	private Projeto projeto = null;
	private Vector WS;
	private Face face = null;
	
	public static final int NEUTRAL_HAND_OF_CUT = 0;
	public static final int RIGHT_HAND_OF_CUT = 1;
	public static final int LEFT_HAND_OF_CUT = 2;

	@Before
	public void initProject() {

		this.bloco = new Bloco(120, 100, 50);
		this.dadosDeProjeto = new DadosDeProjeto(0, "", "TEST PROJECT", 0);

		Material material = new Material();
		material.setName("SAE 1015");
		ArrayList<PropertyParameter> properties = new ArrayList<PropertyParameter>();
		PropertyParameter propertyParameter = new PropertyParameter();
		propertyParameter.setParameterName("Young Module");
		propertyParameter.setParameterUnit("MPa");
		propertyParameter.setParameterValue(420.00);
		properties.add(propertyParameter);
		material.setProperties(properties);

		this.dadosDeProjeto.setMaterial(material);
		this.bloco.setMaterial(material);

		this.projeto = new Projeto(null, bloco, dadosDeProjeto);

		this.face = (Face) bloco.faces.get(Face.XY);
	}

	@Test
	public void TestFeatures() {

		if (this.bloco == null) {
			bloco = new Bloco(100, 120, 50);
		}
		if (this.dadosDeProjeto == null) {
			dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
		}

		Material material = new Material();
		material.setName("SAE 1030");
		ArrayList<PropertyParameter> properties = new ArrayList<PropertyParameter>();
		PropertyParameter propertyParameter = new PropertyParameter();
		propertyParameter.setParameterName("Young Module");
		propertyParameter.setParameterUnit("MPa");
		propertyParameter.setParameterValue(550.00);
		properties.add(propertyParameter);
		material.setProperties(properties);

		dadosDeProjeto.setMaterial(material);
		bloco.setMaterial(material);

		if (this.projeto == null) {
			projeto = new Projeto(null, bloco, dadosDeProjeto);
		}
		Face face = (Face) bloco.faces.get(Face.XY);

		Vector wsFace = new Vector();
		this.WS = new Vector();

		Furo furo1 = new Furo();
		furo1.setPosicao(50, 60, 0);
		furo1.setProfundidade(50);
		furo1.setRaio(7);
		TwistDrill broca1 = new TwistDrill(14, 68, 20 * Math.PI / 180);
		broca1.setCuttingEdgeLength(40.0);
		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.045, 2, 0, 0, 0, 1200 / 60, false, broca1);

		Workingstep workingstep1 = new Workingstep(furo1, face);
		workingstep1.setFerramenta(broca1);
		workingstep1.getFerramenta().setName("Broca1");

		workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
		workingstep1.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(workingstep1));

		wsFace.add(workingstep1);
		this.WS.add(wsFace);

		ArrayList<Feature> arrayList = new ArrayList<Feature>();
		Feature anterior = null;

		for (int index = 0; index < this.WS.size(); index++) {
			Vector workinstepsFaceTmp = (Vector) this.WS.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++) {
				Workingstep wsTmp = (Workingstep) workinstepsFaceTmp
						.elementAt(i);

				if (anterior == null) {
					arrayList.add(wsTmp.getFeature());
					anterior = wsTmp.getFeature();
				}

				else {
					if (wsTmp.getFeature().equals(anterior)) {
						System.out.println("Feature igual nao adicionado");
					} else {
						arrayList.add(wsTmp.getFeature());
					}
					anterior = wsTmp.getFeature();
				}

			}

		}

		System.out.println("Features n:" + arrayList.size());

	}

	@Test
	public void addFlatBottomHole() {
		if (this.bloco == null) {
			bloco = new Bloco(100, 120, 100);
		}
		if (this.dadosDeProjeto == null) {
			dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
		}

		// ---- feature definition ----
		FuroBasePlana furo = new FuroBasePlana();
		furo.setNome("FURO 1");
		furo.setPosicao(20, 60, 0);
		furo.setProfundidade(50);
		furo.setRaio(10);
		furo.setPassante(false);
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis,
				refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);

		// ---- tool definition ----
		// Ferramenta para Workingstep 1
		CenterDrill brocaCenter = new CenterDrill(6, 30);
		brocaCenter.setCuttingEdgeLength(50.0);
		brocaCenter.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		brocaCenter.setOffsetLength(80);
		brocaCenter.setName("SF6");
		brocaCenter.setNumberOfTeeth(2);
		brocaCenter.setMaterialClasse(Ferramenta.P);

		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
				0.04, 0, 1500, 0, 0);
		double planoDeSeguranca = 10;

		// Workingstep 1
		CenterDrilling centerDrilling = new CenterDrilling("Center drilling",
				planoDeSeguranca);
		// Drilling drilling = new Drilling("Operacao de furacao",
		// planoDeSeguranca);
		centerDrilling.setCuttingDepth(5);
		Point3d startPointCenter = new Point3d(0, 0, 0);
		centerDrilling.setStartPoint(startPointCenter);

		Workingstep workingstep = new Workingstep(furo, this.face);
		workingstep.setId("furacao desbaste");
		workingstep.setTipo(Workingstep.DESBASTE);
		workingstep.setFerramenta(brocaCenter);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
		// workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
		workingstep.setOperation(centerDrilling);

		Vector wsFace = new Vector();
		this.WS = new Vector();

		// Ferramenta para Workingstep 2
		TwistDrill broca1 = new TwistDrill(10, 79, 20);
		broca1.setCuttingEdgeLength(50.0);
		broca1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca1.setOffsetLength(80);
		broca1.setName("SF10");
		broca1.setMaterialClasse(TwistDrill.M);
		broca1.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 0, 0);

		// Workingstep 2
		Drilling drilling2 = new Drilling("Operacao de furacao",
				planoDeSeguranca);
		Point3d startPointDrilling2 = new Point3d(0, 0, 5);
		drilling2.setStartPoint(startPointDrilling2);
		Workingstep workingstep1 = new Workingstep(furo, face);
		workingstep1.setId("furacao desbaste");
		workingstep1.setTipo(Workingstep.DESBASTE);
		workingstep1.setFerramenta(broca1);
		workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
		// workingstep1.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep1));
		workingstep1.setOperation(drilling2);

		// Ferramenta para Workingstep 3
		FaceMill faceMillRough = new FaceMill(10, 60);
		faceMillRough.setCuttingEdgeLength(50.0);
		faceMillRough.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillRough.setOffsetLength(80);
		faceMillRough.setName("SF10");
		faceMillRough.setMaterialClasse(Ferramenta.M);
		faceMillRough.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 2, 7.5);

		// Workingstep 3
		BottomAndSideRoughMilling roughMilling = new BottomAndSideRoughMilling(
				"Rough Milling", planoDeSeguranca);
		Point3d startPointRough = new Point3d(0, 0, 0);
		roughMilling.setStartPoint(startPointRough);
		roughMilling.setAllowanceBottom(Feature.LIMITE_DESBASTE);
		roughMilling.setAllowanceSide(Feature.LIMITE_DESBASTE);

		Workingstep workingstep2 = new Workingstep(furo, face);
		workingstep2.setId("Rough Milling - Furo");
		workingstep2.setTipo(Workingstep.DESBASTE);
		workingstep2.setFerramenta(faceMillRough);
		workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
		workingstep2.setOperation(roughMilling);

		// Ferramenta para Workingstep 4
		BoringTool boringTool = new BoringTool(20, 60);

		boringTool.setCuttingEdgeLength(10.0);
		boringTool.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		boringTool.setOffsetLength(70);
		boringTool.setName("SF20");
		boringTool.setMaterialClasse(Ferramenta.M);
		boringTool.setNumberOfTeeth(1);

		CondicoesDeUsinagem condicoesDeUsinagem3 = new CondicoesDeUsinagem(100,
				0.04, 0, 2500, 0, 0);

		// Workingstep 4
		Boring boringFinish = new Boring("Boring", planoDeSeguranca);
		Point3d startPointBoring = new Point3d(0, 0, 0);
		boringFinish.setStartPoint(startPointBoring);
		boringFinish.setCuttingDepth(furo.getProfundidade()
				- roughMilling.getAllowanceBottom());

		Workingstep workingstep3 = new Workingstep(furo, face);
		workingstep3.setId("Boring - Furo");
		workingstep3.setTipo(Workingstep.ACABAMENTO);
		workingstep3.setFerramenta(boringTool);
		workingstep3.setCondicoesUsinagem(condicoesDeUsinagem3);
		workingstep3.setOperation(boringFinish);

		// Ferramenta para Workingstep 5
		EndMill fresaAcabamento = new EndMill(10, 60);
		fresaAcabamento.setCuttingEdgeLength(40.0);
		fresaAcabamento.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		fresaAcabamento.setOffsetLength(60.0);
		fresaAcabamento.setName("SF10");
		fresaAcabamento.setMaterialClasse(Ferramenta.P);
		fresaAcabamento.setNumberOfTeeth(4);

		CondicoesDeUsinagem condicoesDeUsinagem4 = new CondicoesDeUsinagem(100,
				0.05, 0, 1500, 2, 15);

		// Workingstep 5
		BottomAndSideFinishMilling fresamentoFinish = new BottomAndSideFinishMilling(
				"Fresamento de Acabamento", planoDeSeguranca);
		Point3d startFresamento = new Point3d(
				0,
				0,
				(furo.getProfundidade() - roughMilling.getAllowanceBottom() - ((broca1
						.getDiametroFerramenta() / 2) * Math.tan(broca1
						.getToolTipHalfAngle()))));
		fresamentoFinish.setStartPoint(startFresamento);
		fresamentoFinish.setAllowanceBottom(0);
		fresamentoFinish.setAllowanceSide(0);

		Workingstep workingstep4 = new Workingstep(furo, face);
		workingstep4.setId("Fresamento - Furo");
		workingstep4.setTipo(Workingstep.ACABAMENTO);
		workingstep4.setFerramenta(fresaAcabamento);
		workingstep4.setCondicoesUsinagem(condicoesDeUsinagem4);
		workingstep4.setOperation(fresamentoFinish);

		// Adicionar os Workingsteps para a face da peca a ser Usinada
		wsFace.add(workingstep);
		wsFace.add(workingstep1);
		wsFace.add(workingstep2);
		wsFace.add(workingstep3);
		wsFace.add(workingstep4);

		this.WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);

	}

	// @Test
	// public void addThroughHole()
	// {
	// // ---- feature definition ----
	// FuroBasePlana furo = new FuroBasePlana();
	// furo.setNome("FURO PASSANTE");
	// furo.setPosicao(20, 60, 0);
	// furo.setProfundidade(50);
	// furo.setRaio(15);
	// furo.setPassante(true);
	// furo.setTolerancia(0.05);
	// Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
	// ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new
	// ArrayList<Double>();
	// axis.add(0.0);
	// axis.add(0.0);
	// axis.add(1.0);
	// refDirection.add(1.0);
	// refDirection.add(0.0);
	// refDirection.add(0.0);
	// Axis2Placement3D position = new Axis2Placement3D(coordinates, axis,
	// refDirection);
	// position.setName("furo Placement");
	// furo.setPosition(position);
	//
	// // ---- tool definition ----
	// TwistDrill broca = new TwistDrill(18, 79, 20);
	// broca.setCuttingEdgeLength(50.0);
	// broca.setOffsetLength(79);
	// broca.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
	// broca.setName("Broca de desbaste");
	// broca.setOffsetLength(82);
	// broca.setNumberOfTeeth(2);
	// broca.setMaterialClasse(TwistDrill.M);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
	// 0.045, 2, 0, 0, 0, 900 / 60, false, broca);
	// double planoDeSeguranca = 10;
	// Drilling drilling = new Drilling("Drilling", planoDeSeguranca) ;
	//
	// Workingstep workingstep = new Workingstep(furo, face);
	// workingstep.setId("furacao desbaste");
	// workingstep.setTipo(Workingstep.DESBASTE);
	// workingstep.setFerramenta(broca);
	// workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
	// workingstep.setOperation(drilling);
	// workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
	//
	// BoringTool borer = new BoringTool(20, 75);
	// borer.setCuttingEdgeLength(55);
	// borer.setOffsetLength(75);
	// borer.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
	// borer.setNumberOfTeeth(5);
	// borer.setName("alargador");
	// borer.setMaterialClasse(BoringTool.K);
	//
	// CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.05, 2, 1200/60,
	// 0, 0);
	//
	// Boring boring = new Boring("Boring Operation", planoDeSeguranca);
	//
	// Workingstep ws1 = new Workingstep(furo, face);
	// ws1.setId("WS Boring");
	// ws1.setTipo(Workingstep.ACABAMENTO);
	// ws1.setFerramenta(borer);
	// ws1.setCondicoesUsinagem(cu);
	// ws1.setOperation(boring);
	// //pontos
	//
	// Vector wsFace = new Vector();
	// wsFace.add(workingstep);
	// wsFace.add(ws1);
	// this.WS = new Vector();
	// this.WS.add(wsFace);
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	// }

	@Test
	public void variosFuros() {
		if (this.bloco == null) {
			bloco = new Bloco(100, 120, 100);
		}
		if (this.dadosDeProjeto == null) {
			dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
		}
		// Hacer varios furos

		// ---- feature definition (FURO1) ----
		FuroBasePlana furo1 = new FuroBasePlana();
		furo1.setNome("FURO 1");
		furo1.setPosicao(20, 60, 0);
		furo1.setProfundidade(15);
		furo1.setRaio(15);
		furo1.setPassante(false);
		furo1.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo1.X, furo1.Y, furo1.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis,
				refDirection);
		position.setName("furo Placement");
		furo1.setPosition(position);

		// ---- tool definition ----
		// Ferramenta para Workingstep 1 - Furo 1
		CenterDrill brocaCenter = new CenterDrill(5, 30);
		brocaCenter.setCuttingEdgeLength(50.0);
		brocaCenter.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		brocaCenter.setOffsetLength(80);
		brocaCenter.setName("SF5");
		brocaCenter.setNumberOfTeeth(2);
		brocaCenter.setMaterialClasse(Ferramenta.P);

		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
				0.04, 0, 1500, 0, 0);
		double planoDeSeguranca = 10;

		// Workingstep 1 - Furo 1
		CenterDrilling centerDrilling = new CenterDrilling("Center drilling",
				planoDeSeguranca);
		// Drilling drilling = new Drilling("Operacao de furacao",
		// planoDeSeguranca);
		centerDrilling.setCuttingDepth(5);
		Point3d startPointCenter = new Point3d(0, 0, 0);
		centerDrilling.setStartPoint(startPointCenter);

		Workingstep workingstep1Furo1 = new Workingstep(furo1, this.face);
		workingstep1Furo1.setId("furacao desbaste");
		workingstep1Furo1.setTipo(Workingstep.DESBASTE);
		workingstep1Furo1.setFerramenta(brocaCenter);
		workingstep1Furo1.setCondicoesUsinagem(condicoesDeUsinagem);
		// workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
		workingstep1Furo1.setOperation(centerDrilling);

		Vector wsFace = new Vector();
		this.WS = new Vector();

		// Ferramenta para Workingstep 2 - Furo 1
		TwistDrill broca1 = new TwistDrill(20, 79, 20);
		broca1.setCuttingEdgeLength(50.0);
		broca1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca1.setOffsetLength(80);
		broca1.setName("SF20");
		broca1.setMaterialClasse(TwistDrill.M);
		broca1.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 0, 0);

		// Workingstep 2 - Furo 1
		Drilling drilling2 = new Drilling("Operacao de furacao",
				planoDeSeguranca);
		Point3d startPointDrilling2 = new Point3d(0, 0, 5);
		drilling2.setStartPoint(startPointDrilling2);
		drilling2.setCuttingDepth(10 - Feature.LIMITE_DESBASTE);
		Workingstep workingstep2Furo1 = new Workingstep(furo1, face);
		workingstep2Furo1.setId("furacao desbaste");
		workingstep2Furo1.setTipo(Workingstep.DESBASTE);
		workingstep2Furo1.setFerramenta(broca1);
		workingstep2Furo1.setCondicoesUsinagem(condicoesDeUsinagem1);
		// workingstep1.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep1));
		workingstep2Furo1.setOperation(drilling2);

		// Ferramenta para Workingstep 3 - Furo 1
		FaceMill faceMillRough = new FaceMill(10, 60);
		faceMillRough.setCuttingEdgeLength(50.0);
		faceMillRough.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillRough.setOffsetLength(80);
		faceMillRough.setName("SF10");
		faceMillRough.setMaterialClasse(Ferramenta.M);
		faceMillRough.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 2, 7.5);

		// Workingstep 3 - Furo 1
		BottomAndSideRoughMilling roughMilling = new BottomAndSideRoughMilling(
				"Rough Milling", planoDeSeguranca);
		Point3d startPointRough = new Point3d(0, 0, 0);
		roughMilling.setStartPoint(startPointRough);
		roughMilling.setAllowanceBottom(Feature.LIMITE_DESBASTE);
		roughMilling.setAllowanceSide(Feature.LIMITE_DESBASTE);

		Workingstep workingstep3Furo1 = new Workingstep(furo1, face);
		workingstep3Furo1.setId("Rough Milling - Furo");
		workingstep3Furo1.setTipo(Workingstep.DESBASTE);
		workingstep3Furo1.setFerramenta(faceMillRough);
		workingstep3Furo1.setCondicoesUsinagem(condicoesDeUsinagem2);
		workingstep3Furo1.setOperation(roughMilling);

		// Ferramenta para Workingstep 4 - Furo 1
		BoringTool boringTool = new BoringTool(30, 60);

		boringTool.setCuttingEdgeLength(10.0);
		boringTool.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		boringTool.setOffsetLength(70);
		boringTool.setName("SF30");
		boringTool.setMaterialClasse(Ferramenta.M);
		boringTool.setNumberOfTeeth(1);

		CondicoesDeUsinagem condicoesDeUsinagem3 = new CondicoesDeUsinagem(100,
				0.04, 0, 2500, 0, 0);

		// Workingstep 4 - Furo 1
		Boring boringFinish = new Boring("Boring", planoDeSeguranca);
		Point3d startPointBoring = new Point3d(0, 0, 0);
		boringFinish.setStartPoint(startPointBoring);
		boringFinish.setCuttingDepth(furo1.getProfundidade()
				- roughMilling.getAllowanceBottom());

		Workingstep workingstep4Furo1 = new Workingstep(furo1, face);
		workingstep4Furo1.setId("Boring - Furo");
		workingstep4Furo1.setTipo(Workingstep.ACABAMENTO);
		workingstep4Furo1.setFerramenta(boringTool);
		workingstep4Furo1.setCondicoesUsinagem(condicoesDeUsinagem3);
		workingstep4Furo1.setOperation(boringFinish);

		// Ferramenta para Workingstep 5 - Furo 1
		EndMill fresaAcabamento = new EndMill(10, 60);
		fresaAcabamento.setCuttingEdgeLength(40.0);
		fresaAcabamento.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		fresaAcabamento.setOffsetLength(60.0);
		fresaAcabamento.setName("SF10");
		fresaAcabamento.setMaterialClasse(Ferramenta.P);
		fresaAcabamento.setNumberOfTeeth(4);

		CondicoesDeUsinagem condicoesDeUsinagem4 = new CondicoesDeUsinagem(100,
				0.05, 0, 1500, 2, 15);

		// Workingstep 5 - Furo 1
		BottomAndSideFinishMilling fresamentoFinish = new BottomAndSideFinishMilling(
				"Fresamento de Acabamento", planoDeSeguranca);
		Point3d startFresamento = new Point3d(
				0,
				0,
				(furo1.getProfundidade() - roughMilling.getAllowanceBottom() - ((broca1
						.getDiametroFerramenta() / 2) * Math.tan(broca1
						.getToolTipHalfAngle()))));
		fresamentoFinish.setStartPoint(startFresamento);
		fresamentoFinish.setAllowanceBottom(0);
		fresamentoFinish.setAllowanceSide(0);

		Workingstep workingstep5Furo1 = new Workingstep(furo1, face);
		workingstep5Furo1.setId("Fresamento - Furo");
		workingstep5Furo1.setTipo(Workingstep.ACABAMENTO);
		workingstep5Furo1.setFerramenta(fresaAcabamento);
		workingstep5Furo1.setCondicoesUsinagem(condicoesDeUsinagem4);
		workingstep5Furo1.setOperation(fresamentoFinish);

		// ---- feature definition (FURO2) ----
		FuroBaseConica furo2 = new FuroBaseConica();
		furo2.setNome("FURO BASE CONICA");
		furo2.setPosicao(20, 60, 15);
		furo2.setProfundidade(10);
		furo2.setRaio(8);
		furo2.setPassante(false);
		furo2.setTolerancia(0.05);
		Point3d coordinates2 = new Point3d(furo2.X, furo2.Y, furo2.Z);
		ArrayList<Double> axis2 = new ArrayList<Double>(), refDirection2 = new ArrayList<Double>();
		axis2.add(0.0);
		axis2.add(0.0);
		axis2.add(1.0);
		refDirection2.add(1.0);
		refDirection2.add(0.0);
		refDirection2.add(0.0);
		Axis2Placement3D position2 = new Axis2Placement3D(coordinates2, axis2,
				refDirection2);
		position2.setName("furo 2 Placement");
		furo2.setPosition(position2);
		
		// Ferramenta para Workingstep 1 - Furo 2
		CenterDrill brocaCenter1 = new CenterDrill(5, 30);
		brocaCenter1.setCuttingEdgeLength(50.0);
		brocaCenter1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		brocaCenter1.setOffsetLength(80);
		brocaCenter1.setName("SF5");
		brocaCenter1.setNumberOfTeeth(2);
		brocaCenter1.setMaterialClasse(Ferramenta.P);

		CondicoesDeUsinagem condicoesDeUsinagem1Furo2 = new CondicoesDeUsinagem(100,
				0.04, 0, 1500, 0, 0);

		// Workingstep 1 - Furo 2
		CenterDrilling centerDrilling2 = new CenterDrilling("Center drilling FURO2",
				planoDeSeguranca);
		// Drilling drilling = new Drilling("Operacao de furacao",
		// planoDeSeguranca);
		centerDrilling2.setCuttingDepth(5);
		Point3d startPointCenter2 = new Point3d(0, 0, furo2.getPosicaoZ());
		centerDrilling2.setStartPoint(startPointCenter2);

		Workingstep workingstep1Furo2 = new Workingstep(furo2, this.face);
		workingstep1Furo2.setId("furacao desbaste FURO2");
		workingstep1Furo2.setTipo(Workingstep.DESBASTE);
		workingstep1Furo2.setFerramenta(brocaCenter1);
		workingstep1Furo2.setCondicoesUsinagem(condicoesDeUsinagem1Furo2);
		// workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
		workingstep1Furo2.setOperation(centerDrilling2);

		// Ferramenta para Workingstep 2 - Furo 2
		TwistDrill broca2 = new TwistDrill(15, 79, 20);
		broca2.setCuttingEdgeLength(50.0);
		broca2.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca2.setOffsetLength(80);
		broca2.setName("SF15");
		broca2.setMaterialClasse(TwistDrill.M);
		broca2.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem2Furo2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 0, 0);

		// Workingstep 2 - Furo 2
		Drilling drillingFuro2 = new Drilling("Operacao de furacao FURO2",
				planoDeSeguranca);
		Point3d startPointDrillingFuro2 = new Point3d(0, 0, (furo2.getPosicaoZ() - 5));
		drillingFuro2.setStartPoint(startPointDrillingFuro2);
		drillingFuro2.setCuttingDepth(5 - Feature.LIMITE_DESBASTE);
		Workingstep workingstep2Furo2 = new Workingstep(furo2, face);
		workingstep2Furo2.setId("furacao desbaste FURO2");
		workingstep2Furo2.setTipo(Workingstep.DESBASTE);
		workingstep2Furo2.setFerramenta(broca2);
		workingstep2Furo2.setCondicoesUsinagem(condicoesDeUsinagem2Furo2);
		// workingstep1.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep1));
		workingstep2Furo2.setOperation(drillingFuro2);

//		// Ferramenta para Workingstep 3 - Furo 2
//		FaceMill faceMillRoughFuro2 = new FaceMill(10, 60);
//		faceMillRoughFuro2.setCuttingEdgeLength(50.0);
//		faceMillRoughFuro2.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
//		faceMillRoughFuro2.setOffsetLength(80);
//		faceMillRoughFuro2.setName("SF10");
//		faceMillRoughFuro2.setMaterialClasse(Ferramenta.M);
//		faceMillRoughFuro2.setNumberOfTeeth(2);
//
//		CondicoesDeUsinagem condicoesDeUsinagem3Furo2 = new CondicoesDeUsinagem(100,
//				0.04, 0, 2000, 2, 7.5);
//
//		// Workingstep 3 - Furo 1
//		BottomAndSideRoughMilling roughMillingFuro2 = new BottomAndSideRoughMilling(
//				"Rough Milling FURO2", planoDeSeguranca);
//		Point3d startPointRoughFuro2 = new Point3d(0, 0, furo2.getPosicaoZ());
//		roughMillingFuro2.setStartPoint(startPointRoughFuro2);
//		roughMillingFuro2.setAllowanceBottom(0.5);
//		roughMillingFuro2.setAllowanceSide(0.5);
//
//		Workingstep workingstep3Furo2 = new Workingstep(furo2, face);
//		workingstep3Furo2.setId("Rough Milling - Furo 2");
//		workingstep3Furo2.setTipo(Workingstep.DESBASTE);
//		workingstep3Furo2.setFerramenta(faceMillRoughFuro2);
//		workingstep3Furo2.setCondicoesUsinagem(condicoesDeUsinagem3Furo2);
//		workingstep3Furo2.setOperation(roughMillingFuro2);

		// Ferramenta para Workingstep 4 - Furo 2
		BoringTool boringTool2 = new BoringTool(16, 60);

		boringTool2.setCuttingEdgeLength(10.0);
		boringTool2.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		boringTool2.setOffsetLength(70);
		boringTool2.setName("SF16");
		boringTool2.setMaterialClasse(Ferramenta.M);
		boringTool2.setNumberOfTeeth(1);

		CondicoesDeUsinagem condicoesDeUsinagem4Furo2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2500, 0, 0);

		// Workingstep 4 - Furo 2
		Boring boringFinishFuro2 = new Boring("Boring FURO2", planoDeSeguranca);
		Point3d startPointBoringFuro2 = new Point3d(0, 0, furo2.getPosicaoZ());
		boringFinishFuro2.setStartPoint(startPointBoringFuro2);
		boringFinishFuro2.setCuttingDepth(furo2.getProfundidade()
				- 0.5);

		Workingstep workingstep4Furo2 = new Workingstep(furo2, face);
		workingstep4Furo2.setId("Boring - Furo2");
		workingstep4Furo2.setTipo(Workingstep.ACABAMENTO);
		workingstep4Furo2.setFerramenta(boringTool2);
		workingstep4Furo2.setCondicoesUsinagem(condicoesDeUsinagem4Furo2);
		workingstep4Furo2.setOperation(boringFinishFuro2);

		// Ferramenta para Workingstep 5 - Furo 2
		TwistDrill broca3 = new TwistDrill(16, 79, 20);
		broca3.setCuttingEdgeLength(50.0);
		broca3.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca3.setOffsetLength(80);
		broca3.setName("SF16");
		broca3.setMaterialClasse(TwistDrill.M);
		broca3.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem5Furo2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 0, 0);

		// Workingstep 5 - Furo 2
		Drilling drilling2Furo2 = new Drilling("Operacao de furacao 2 - FURO2",
				planoDeSeguranca);
		Point3d startPointDrilling2Furo2 = new Point3d(0, 0, (furo2.getPosicaoZ() + (furo2.getProfundidade()
				- Feature.LIMITE_DESBASTE) ));
		drilling2Furo2.setStartPoint(startPointDrilling2Furo2);
		drilling2Furo2.setCuttingDepth(Feature.LIMITE_DESBASTE);
		Workingstep workingstep5Furo2 = new Workingstep(furo2, face);
		workingstep5Furo2.setId("furacao desbaste 2 - FURO2");
		workingstep5Furo2.setTipo(Workingstep.DESBASTE);
		workingstep5Furo2.setFerramenta(broca3);
		workingstep5Furo2.setCondicoesUsinagem(condicoesDeUsinagem5Furo2);
		// workingstep1.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep1));
		workingstep5Furo2.setOperation(drilling2Furo2);

		
		// Adicionar os Workingsteps para a face da peca a ser Usinada
		wsFace.add(workingstep1Furo1);
		wsFace.add(workingstep2Furo1);
		wsFace.add(workingstep3Furo1);
		wsFace.add(workingstep4Furo1);
		wsFace.add(workingstep5Furo1);
		
		wsFace.add(workingstep1Furo2);
		wsFace.add(workingstep2Furo2);
//		wsFace.add(workingstep3Furo2);
		wsFace.add(workingstep4Furo2);
		wsFace.add(workingstep5Furo2);

		this.WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);

	}

	@Test
	public void ranhuraPQUAngulo45(){

		
		double retractPlane = 5;
		double allowance = 0;
		
		RanhuraPerfilQuadradoU ranhuraPQU = new RanhuraPerfilQuadradoU("ranhuraPQU",0.0,10.0,0.0,0.0,0.0,0.0,30.0,10.0,RanhuraPerfilQuadradoU.HORIZONTAL,5.0,45,10.0);
		
//		FaceMill faceMill1 = new FaceMill(16,68);
//		faceMill1.setName("SF16");
//		faceMill1.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		FaceMill faceMill1 = new FaceMill(3,39);
		faceMill1.setName("SF3");
		faceMill1.setHandOfCut(RIGHT_HAND_OF_CUT);
				
//		FaceMill faceMill2 = new FaceMill(12,61);
//		faceMill2.setName("SF12");
//		faceMill2.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		BullnoseEndMill bullNose1 = new BullnoseEndMill(8, 45);
		bullNose1.setName("SF8");
		//bullNose1.setName(""); buscar qual � a bull Nose de 20mm de diametro no sinuTrain;
		bullNose1.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("Desbaste 1 ranhuraPQU", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(allowance);
		bottomAndSideRoughMilling.setAllowanceSide(allowance);
		bottomAndSideRoughMilling.setCoolant(true);
		
		
//		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("Desbaste 2 ranhuraPQU", retractPlane);
//		bottomAndSideRoughMilling1.setCoolant(true);
//		bottomAndSideRoughMilling1.setAllowanceBottom(allowance);
//		bottomAndSideRoughMilling1.setAllowanceSide(allowance);
		
		BottomAndSideFinishMilling bottomAndSideFinishMilling = new BottomAndSideFinishMilling("Acabamento com BullNose Mill", retractPlane);
		bottomAndSideFinishMilling.setCoolant(true);
		bottomAndSideFinishMilling.setAllowanceBottom(allowance);
		bottomAndSideFinishMilling.setAllowanceSide(allowance);
		
//		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(145,0.0505,0.0505,2884.683343540603,2,12);
//		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(145,0.0315,0.0315,3846.2444580541373,2,9);
		
//		CondicoesDeUsinagem cuBullNose = new CondicoesDeUsinagem(660, 0.114, 0.114, 26260.565610162732, 0.4, 0.4);
		
		CondicoesDeUsinagem cuBullNose = new CondicoesDeUsinagem(660, 0.114, 0.114, 26260.565610162732, 4, 4);
		
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(145,0.0060,0.0060,15384.97783221655,2,2.25);
		
		Workingstep ws = new Workingstep(ranhuraPQU, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill1);
		ws.setTipo(Workingstep.DESBASTE);
		
//		Workingstep ws1 = new Workingstep(ranhuraPQU, face);
//		ws1.setCondicoesUsinagem(cu1);
//		ws1.setOperation(bottomAndSideRoughMilling1);
//		ws1.setFerramenta(faceMill2);
//		ws1.setTipo(Workingstep.DESBASTE);
		
		Workingstep wsBullNose = new Workingstep(ranhuraPQU, face);
		wsBullNose.setCondicoesUsinagem(cuBullNose);
		wsBullNose.setOperation(bottomAndSideFinishMilling);
		wsBullNose.setFerramenta(bullNose1);
		wsBullNose.setTipo(Workingstep.DESBASTE);

		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
//		wsFace.add(ws1);
		wsFace.add(wsBullNose);
		
		WS.add(wsFace);
		
		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		//System.out.println(GCodeString);
		
		  Writer output = null;
		  File file = new File("ranhuraPQU45.txt");
		  try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(GCodeString);
			output.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	
		
		
	}
	@Test
	
	public void ranhuraPQUTest (){
		
		double retractPlane = 5;
		double allowance = 0;
		
		RanhuraPerfilQuadradoU ranhuraPQU = new RanhuraPerfilQuadradoU("ranhuraPQU",0.0,10.0,0.0,0.0,0.0,0.0,25.0,10.0,RanhuraPerfilQuadradoU.HORIZONTAL,5.0,75.964,20.0);
		
		FaceMill faceMill1 = new FaceMill(16,68);
		faceMill1.setName("SF16");
		faceMill1.setHandOfCut(RIGHT_HAND_OF_CUT);
				
		FaceMill faceMill2 = new FaceMill(12,61);
		faceMill2.setName("SF12");
		faceMill2.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		BullnoseEndMill bullNose1 = new BullnoseEndMill(20, 79);
		//bullNose1.setName(""); buscar qual � a bull Nose de 20mm de diametro no sinuTrain;
		bullNose1.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("Desbaste 1 ranhuraPQU", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(allowance);
		bottomAndSideRoughMilling.setAllowanceSide(allowance);
		bottomAndSideRoughMilling.setCoolant(true);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("Desbaste 2 ranhuraPQU", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(true);
		bottomAndSideRoughMilling1.setAllowanceBottom(allowance);
		bottomAndSideRoughMilling1.setAllowanceSide(allowance);
		
//		BottomAndSideFinishMilling bottomAndSideFinishMilling = new BottomAndSideFinishMilling("Acabamento com BullNose Mill", retractPlane);
//		bottomAndSideRoughMilling1.setCoolant(true);
//		bottomAndSideRoughMilling1.setAllowanceBottom(allowance);
//		bottomAndSideRoughMilling1.setAllowanceSide(allowance);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(145,0.0505,0.0505,2884.683343540603,2,12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(145,0.0315,0.0315,3846.2444580541373,2,9);
//		CondicoesDeUsinagem cuBullNose = new CondicoesDeUsinagem(660, 0.18, 0.18, 10504.226244065092, 1, 1);
		
		Workingstep ws = new Workingstep(ranhuraPQU, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill1);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(ranhuraPQU, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill2);
		ws1.setTipo(Workingstep.DESBASTE);
		
//		Workingstep wsBullNose = new Workingstep(ranhuraPQU, face);
//		wsBullNose.setCondicoesUsinagem(cuBullNose);
//		wsBullNose.setOperation(bottomAndSideFinishMilling);
//		wsBullNose.setFerramenta(bullNose1);
//		wsBullNose.setTipo(Workingstep.DESBASTE);

		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
		wsFace.add(ws1);
//		wsFace.add(wsBullNose);
		
		WS.add(wsFace);
		
		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
	}
	
	@Test
	
	public void imitacaoCavApplet(){
		//Cavidade sem acabamento
		
		double retractPlane = 5;
		double allowance = 0;
		Cavidade cavidadeApp = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 7.5, 50, 70, 10);
		cavidadeApp.setTolerancia(0.05);
		FaceMill faceMill = new FaceMill(20, 79);
		faceMill.setName("SF20");
		faceMill.setHandOfCut(RIGHT_HAND_OF_CUT);

		FaceMill faceMill1 = new FaceMill(14, 61);
		faceMill1.setName("SF14");
		faceMill1.setHandOfCut(RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("Desbaste 1", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(allowance);
		bottomAndSideRoughMilling.setAllowanceSide(allowance);
		bottomAndSideRoughMilling.setCoolant(true);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("Desbaste cantos", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(true);
		bottomAndSideRoughMilling1.setAllowanceBottom(allowance);
		bottomAndSideRoughMilling1.setAllowanceSide(allowance);
		//Point3d startPoint = new Point3d(cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
		//bottomAndSideRoughMilling1.setStartPoint(startPoint);
		
		
		//CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(145,0.073,0.073,2307.7466748324823,2,15);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(145,0.041,0.041,3296.7809640464034,2,10.5);
		
			
		Workingstep ws = new Workingstep(cavidadeApp, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(cavidadeApp, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill1);
		ws1.setTipo(Workingstep.DESBASTE);
		
		Vector<Workingstep> vWorkingsteps = new Vector<Workingstep>();
		
		vWorkingsteps.add(ws);
		vWorkingsteps.add(ws1);
		
		cavidadeApp.setWorkingsteps(vWorkingsteps);
		ws.setFeature(cavidadeApp);
		ws1.setFeature(cavidadeApp);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
		wsFace.add(ws1);
		
		WS.add(wsFace);

		System.out.println("Chegou ate antes de criar o gerador");
		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
	}
	
	
	@Test
	public void acabamentoCavidade() {
		double retractPlane = 14.4;
		ArrayList<Double> axisFeature = new ArrayList<Double>();
		ArrayList<Double> refDirectionFeature = new ArrayList<Double>();

		// ---- creating a closed pocket ----
		Cavidade cavidade = new Cavidade();
		cavidade.setComprimento(30);
		cavidade.setLargura(40);
		cavidade.setProfundidade(15.0);
		cavidade.setPosicao(30, 40, 0);
		cavidade.setRaio(10);
		cavidade.setNome("Cavidade 1");
		Point3d coordinatesCavidade = new Point3d(30 + 15, 40 + 20, 50);
		Axis2Placement3D positionCavidade = new Axis2Placement3D(
				coordinatesCavidade, axisFeature, refDirectionFeature);
		cavidade.setPosition(positionCavidade);
		cavidade.setTolerancia(0.65);

		// ---- tools definition ----
		FaceMill faceMillCavidade = new FaceMill(20.0, 80.00);
		faceMillCavidade.setCuttingEdgeLength(8.0);
		faceMillCavidade.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillCavidade.setNumberOfTeeth(2);
		faceMillCavidade.setName("SF8");
		faceMillCavidade.setOffsetLength(85);

		EndMill endMillCavidade = new EndMill(4, 50);
		endMillCavidade.setName("SF8");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);

		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmCavidade = new BottomAndSideRoughMilling(
				"Op. desbaste - Cavidade", retractPlane);
		bsrmCavidade.setCoolant(true);
		bsrmCavidade.setAllowanceBottom(Feature.LIMITE_DESBASTE);
		bsrmCavidade.setAllowanceSide(Feature.LIMITE_DESBASTE);

		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,(cavidade.getProfundidade() - bsrmCavidade.getAllowanceBottom()));
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);

		// ---- cutting conditions definition ----
		CondicoesDeUsinagem condicoesDeUsinagemCavidade = new CondicoesDeUsinagem(
				100, 0.04, 2.3, 12.0 * 0.75, 0, 0, 1500, false,
				faceMillCavidade);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 1, 4);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
		// CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new
		// CondicoesDeUsinagem(160, 12, 1, 4,0,0.01,1500,true, endMillCavidade);

		Workingstep workingstepCavidade = new Workingstep(cavidade, this.face);
		workingstepCavidade.setFerramenta(faceMillCavidade);
		workingstepCavidade.setCondicoesUsinagem(condicoesDeUsinagemCavidade);
		workingstepCavidade.setOperation(bsrmCavidade);
		workingstepCavidade.setId("WS 1 - Cavidade");
		workingstepCavidade.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(workingstepCavidade));

		Workingstep workingstepCavidade2 = new Workingstep(cavidade, this.face);
		workingstepCavidade2.setFerramenta(endMillCavidade);
		workingstepCavidade2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		workingstepCavidade2.setOperation(bsfmCavidade);
		workingstepCavidade2.setId("WS 2 - Cavidade");
		workingstepCavidade2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(workingstepCavidade2));

		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(workingstepCavidade);
		wsFace.add(workingstepCavidade2);
		Vector<Workingstep> wsCavidade = new Vector<Workingstep>();
		wsCavidade.add(workingstepCavidade);
		wsCavidade.add(workingstepCavidade2);
		
		cavidade.setWorkingsteps(wsCavidade);
		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);

	}

	
	@Test
	
	public void addSlot(){
		if (this.bloco == null) {
			bloco = new Bloco(100, 120, 100);
		}
		if (this.dadosDeProjeto == null) {
			dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
		}
		
		Face face = (Face) bloco.faces.get(Face.XY);
		
		// ---- creating a horizontal slot ----
		Ranhura ranhura = new Ranhura();
		ranhura.setLargura(16.3);
		ranhura.setProfundidade(7.0);
		ranhura.setPosicao(0, 80, 0);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setNome("Ranhura 1");
		Point3d coordinatesRanhura = new Point3d(0, 80 + ranhura.getLargura()/2, this.face.getProfundidadeMaxima() - ranhura.Z);
		ArrayList<Double> axisFeature = new ArrayList<Double>();
		ArrayList<Double> refDirectionFeature = new ArrayList<Double>();
		Axis2Placement3D positionRanhura = new Axis2Placement3D(coordinatesRanhura, axisFeature, refDirectionFeature);
		ranhura.setPosition(positionRanhura);
		ranhura.setTolerancia(0.52);
		
		// ---- tools definitions ----
		FaceMill faceMill = new FaceMill(10.0, 70.0);
		faceMill.setCuttingEdgeLength(10.0);
		faceMill.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMill.setName("SF10");
		faceMill.setNumberOfTeeth(5);
		faceMill.setOffsetLength(72);
		
		EndMill endMill = new EndMill(8, 40);
		endMill.setName("EndMill");
		endMill.setCuttingEdgeLength(8);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setNumberOfTeeth(4);
		endMill.setOffsetLength(50);

		double retractPlane = 10;
		
		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmRanhura = new BottomAndSideRoughMilling("Op. desbaste ranhura", retractPlane  );
		bsrmRanhura.setCoolant(true);
		BottomAndSideFinishMilling bsfmRanhura = new BottomAndSideFinishMilling("Op. acabamento ranhura", retractPlane);
		
		// ---- cutting conditions ----
		CondicoesDeUsinagem condicoesDeUsinagemRanhura = new CondicoesDeUsinagem(100, 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, faceMill);
		CondicoesDeUsinagem condicoesDeUsinagemRanhura2 = new CondicoesDeUsinagem(150, 0.03, 15, 1500, 2, 6);
		
		Workingstep workingstepRanhura = new Workingstep(ranhura, this.face);
		workingstepRanhura.setFerramenta(faceMill);
		workingstepRanhura.setCondicoesUsinagem(condicoesDeUsinagemRanhura);
		//workingstepRanhura.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepRanhura));
		//Vector movimentacaoRanhura = DeterminarMovimentacao.getPontosMovimentacao(workingstepRanhura);
		//workingstepRanhura.setPontosMovimentacao(movimentacaoRanhura);
		workingstepRanhura.setOperation(bsrmRanhura);
		workingstepRanhura.setId("WS ranhura");
		
//		System.out.println("Passou primer WS");
//		
//		Workingstep workingstepRanhura2 = new Workingstep(ranhura, this.face);
//		workingstepRanhura2.setFerramenta(faceMill);
//		workingstepRanhura2.setCondicoesUsinagem(condicoesDeUsinagemRanhura);
//		workingstepRanhura.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepRanhura2));
//		Vector movimentacaoRanhura2 = DeterminarMovimentacao.getPontosMovimentacao(workingstepRanhura2);
//		workingstepRanhura.setPontosMovimentacao(movimentacaoRanhura2);
//		workingstepRanhura2.setOperation(bsfmRanhura);
//		workingstepRanhura2.setId("WS ranhura 2");
		
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(workingstepRanhura);
//		wsFace.add(workingstepRanhura2);
		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
	}

	@Test
	public void acabamentoCantosRFerramentaMenorQRCPocket(){
		

		
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 15, 80, 100, 15);
		
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("SF10");
		FaceMill faceMill2 = new FaceMill(3, 40);
		faceMill2.setName("SF3");
		
		EndMill endMillCavidade = new EndMill(10, 50);
		endMillCavidade.setName("SF10");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		bottomAndSideRoughMilling.setCoolant(true);

		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("desbaste cantos", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(true);
		bottomAndSideRoughMilling1.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1.setAllowanceSide(0.5);
		Point3d startPoint = new Point3d(cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
				
		bottomAndSideRoughMilling1.setStartPoint(startPoint);
		
		
		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,0);
		
			
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0.02, 2000, 2, faceMill2.getDiametroFerramenta()*0.75);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 2, endMillCavidade.getDiametroFerramenta() * 0.75);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
			
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
//		Workingstep ws1 = new Workingstep(cavidade, face);
//		ws1.setCondicoesUsinagem(cu1);
//		ws1.setOperation(bottomAndSideRoughMilling1);
//		ws1.setFerramenta(faceMill2);
//		ws1.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws2 = new Workingstep(cavidade, this.face);
		ws2.setFerramenta(endMillCavidade);
		ws2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		ws2.setOperation(bsfmCavidade);
		ws2.setId("WS 2 - Cavidade");
		ws2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(ws2));
		ws2.setTipo(Workingstep.ACABAMENTO);
	
		Vector<Workingstep> vWorkingsteps = new Vector<Workingstep>();
		
		vWorkingsteps.add(ws);
		//vWorkingsteps.add(ws1);
		vWorkingsteps.add(ws2);
		
		cavidade.setWorkingsteps(vWorkingsteps);
		ws.setFeature(cavidade);
		//ws1.setFeature(cavidade);
		ws2.setFeature(cavidade);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
		//wsFace.add(ws1);
		wsFace.add(ws2);

		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
		
	
	}
	@Test 
	public void TwoPocektsWCantos(){
		
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 9, 80, 100, 7.5);
			
		Cavidade cavidade2 = new Cavidade("cavidade2",35,25,7.5,0,0,0,9,40,50,5);
		
		FaceMill faceMill = new FaceMill(60, 40);
		faceMill.setName("PF60");
		FaceMill faceMillc2 = new FaceMill(25, 40);
		faceMillc2.setName("PF25");
		FaceMill faceMill2 = new FaceMill(3, 40);
		faceMill2.setName("SF3");
		
		EndMill endMillCavidade = new EndMill(10, 50);
		endMillCavidade.setName("SF10");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste c1", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		bottomAndSideRoughMilling.setCoolant(true);

		BottomAndSideRoughMilling bottomAndSideRoughMillingc2 = new BottomAndSideRoughMilling("desbaste c2", retractPlane);
		bottomAndSideRoughMillingc2.setAllowanceBottom(0.5);
		bottomAndSideRoughMillingc2.setAllowanceSide(0.5);
		bottomAndSideRoughMillingc2.setCoolant(true);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("desbaste cantos c1", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(false);
		bottomAndSideRoughMilling1.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1.setAllowanceSide(0.5);
		Point3d startPoint = new Point3d(cavidade.X + cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.Y + cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
		
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1c2 = new BottomAndSideRoughMilling("desbaste cantos c2", retractPlane);
		bottomAndSideRoughMilling1c2.setCoolant(false);
		bottomAndSideRoughMilling1c2.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1c2.setAllowanceSide(0.5);
		Point3d startPointc2 = new Point3d(cavidade2.X + cavidade2.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade2.Y +cavidade2.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), cavidade2.Z);
		
		bottomAndSideRoughMilling1.setStartPoint(startPoint);
		bottomAndSideRoughMilling1c2.setStartPoint(startPointc2);
		
		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,0);
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);
		
		BottomAndSideFinishMilling bsfmCavidade2 = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade2", retractPlane);
		Point3d startPointFinishCavidade2 = new Point3d(0,0,7.5);
		bsfmCavidade2.setStartPoint(startPointFinishCavidade2);
		bsfmCavidade2.setCoolant(true);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cuc2 = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0.02, 2000, 2, faceMill2.getDiametroFerramenta()*0.75);
		CondicoesDeUsinagem cu1c2 = new CondicoesDeUsinagem(150, 0.02, 0.02, 2000, 2, faceMill2.getDiametroFerramenta()*0.75);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 2, endMillCavidade.getDiametroFerramenta() * 0.75);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2c2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 2, endMillCavidade.getDiametroFerramenta() * 0.75);
		condicoesDeUsinagemCavidade2c2.setAcabamento(true);
			
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setId("ws");
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(cavidade, face);
		ws1.setId("ws1");
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill2);
		ws1.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws2 = new Workingstep(cavidade, this.face);
		ws2.setId("ws2");
		ws2.setFerramenta(endMillCavidade);
		ws2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		ws2.setOperation(bsfmCavidade);
		ws2.setId("WS 2 - Cavidade");
		ws2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(ws2));
		ws2.setTipo(Workingstep.ACABAMENTO);
		
		Workingstep wsc2 = new Workingstep(cavidade2, face);
		wsc2.setId("wsc2");
		wsc2.setCondicoesUsinagem(cuc2);
		wsc2.setOperation(bottomAndSideRoughMillingc2);
		wsc2.setFerramenta(faceMillc2);
		wsc2.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1c2 = new Workingstep(cavidade2, face);
		ws1c2.setId("ws1c2");
		ws1c2.setCondicoesUsinagem(cu1c2);
		ws1c2.setOperation(bottomAndSideRoughMilling1c2);
		ws1c2.setFerramenta(faceMill2);
		ws1c2.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws2c2 = new Workingstep(cavidade2, this.face);
		ws2c2.setId("ws2c2");
		ws2c2.setFerramenta(endMillCavidade);
		ws2c2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2c2);
		ws2c2.setOperation(bsfmCavidade2);
		ws2c2.setId("WS 2 - Cavidade c2");
		ws2c2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(ws2c2));
		ws2c2.setTipo(Workingstep.ACABAMENTO);
	
		Vector<Workingstep> vWorkingsteps1 = new Vector<Workingstep>();
		
		vWorkingsteps1.add(ws);
		vWorkingsteps1.add(ws1);
		vWorkingsteps1.add(ws2);
		
		Vector<Workingstep> vWorkingsteps2 = new Vector<Workingstep>();
		vWorkingsteps2.add(wsc2);
		vWorkingsteps2.add(ws1c2);
		vWorkingsteps2.add(ws2c2);
		
		cavidade.setWorkingsteps(vWorkingsteps1);
		ws.setFeature(cavidade);
		ws1.setFeature(cavidade);
		ws2.setFeature(cavidade);

		cavidade2.setWorkingsteps(vWorkingsteps2);
		wsc2.setFeature(cavidade2);
		ws1c2.setFeature(cavidade2);
		ws2c2.setFeature(cavidade2);
		
		this.WS = new Vector<Vector<Workingstep>>();
		Vector wsFace = new Vector<Workingstep>();
		wsFace.add(ws);
		wsFace.add(ws1);
		wsFace.add(ws2);
		wsFace.add(wsc2);
		wsFace.add(ws1c2);
		wsFace.add(ws2c2);

		WS.add(wsFace);

		
		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
		
		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
		
	
	}
	@Test 
	public void pocketCantoSmall(){
		

		
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 15, 80, 100, 15);
		
		FaceMill faceMill = new FaceMill(25, 40);
		faceMill.setName("PF25");
		FaceMill faceMill2 = new FaceMill(20, 40);
		faceMill2.setName("SF20");
		
		EndMill endMillCavidade = new EndMill(10, 50);
		endMillCavidade.setName("SF10");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		bottomAndSideRoughMilling.setCoolant(true);

		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("desbaste cantos", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(false);
		bottomAndSideRoughMilling1.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1.setAllowanceSide(0.5);
		Point3d startPoint = new Point3d(cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
				
		bottomAndSideRoughMilling1.setStartPoint(startPoint);
		
		
		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,0);
		
			
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0.02, 2000, 2, faceMill2.getDiametroFerramenta()*0.75);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 2, endMillCavidade.getDiametroFerramenta() * 0.75);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
			
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(cavidade, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill2);
		ws1.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws2 = new Workingstep(cavidade, this.face);
		ws2.setFerramenta(endMillCavidade);
		ws2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		ws2.setOperation(bsfmCavidade);
		ws2.setId("WS 2 - Cavidade");
		ws2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(ws2));
		ws2.setTipo(Workingstep.ACABAMENTO);
	
		Vector<Workingstep> vWorkingsteps = new Vector<Workingstep>();
		
		vWorkingsteps.add(ws);
		vWorkingsteps.add(ws1);
		vWorkingsteps.add(ws2);
		
		cavidade.setWorkingsteps(vWorkingsteps);
		ws.setFeature(cavidade);
		ws1.setFeature(cavidade);
		ws2.setFeature(cavidade);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
		wsFace.add(ws1);
		wsFace.add(ws2);

		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
		
	
	}
	
	@Test
	public void pocketComAcabamentoCantosRaioFerramenta60(){
		
		double retractPlane = 21;
		Cavidade cavidade = new Cavidade("cavidade", 10, 10, 0, 0,0,0, 9, 80, 100, 15);
		
		FaceMill faceMill = new FaceMill(60, 40);
		faceMill.setName("PF60");
		FaceMill faceMill2 = new FaceMill(3, 40);
		faceMill2.setName("SF3");
		
		EndMill endMillCavidade = new EndMill(10, 50);
		endMillCavidade.setName("SF10");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("desbaste", retractPlane);
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		bottomAndSideRoughMilling.setCoolant(true);

		
		BottomAndSideRoughMilling bottomAndSideRoughMilling1 = new BottomAndSideRoughMilling("desbaste cantos", retractPlane);
		bottomAndSideRoughMilling1.setCoolant(false);
		bottomAndSideRoughMilling1.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling1.setAllowanceSide(0.5);
		Point3d startPoint = new Point3d(cavidade.getComprimento() / 2 - bottomAndSideRoughMilling1.getAllowanceSide() - faceMill2.getDiametroFerramenta() / 2, cavidade.getLargura() / 2 - faceMill.getDiametroFerramenta() / 2 - bottomAndSideRoughMilling1.getAllowanceSide(), 0);
				
		bottomAndSideRoughMilling1.setStartPoint(startPoint);
		
		
		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,0);
		
			
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.04, 0.04, 2000, 2, 12);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.02, 0.02, 2000, 2, faceMill2.getDiametroFerramenta()*0.75);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 2, endMillCavidade.getDiametroFerramenta() * 0.75);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
			
		Workingstep ws = new Workingstep(cavidade, face);
		ws.setCondicoesUsinagem(cu);
		ws.setOperation(bottomAndSideRoughMilling);
		ws.setFerramenta(faceMill);
		ws.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws1 = new Workingstep(cavidade, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setOperation(bottomAndSideRoughMilling1);
		ws1.setFerramenta(faceMill2);
		ws1.setTipo(Workingstep.DESBASTE);
		
		Workingstep ws2 = new Workingstep(cavidade, this.face);
		ws2.setFerramenta(endMillCavidade);
		ws2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		ws2.setOperation(bsfmCavidade);
		ws2.setId("WS 2 - Cavidade");
		ws2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(ws2));
		ws2.setTipo(Workingstep.ACABAMENTO);
	
		Vector<Workingstep> vWorkingsteps = new Vector<Workingstep>();
		
		vWorkingsteps.add(ws);
		vWorkingsteps.add(ws1);
		vWorkingsteps.add(ws2);
		
		cavidade.setWorkingsteps(vWorkingsteps);
		ws.setFeature(cavidade);
		ws1.setFeature(cavidade);
		ws2.setFeature(cavidade);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(ws);
		wsFace.add(ws1);
		wsFace.add(ws2);

		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);
		
	}
	@Test
	
	public void onePocketOneHoleOneSlot(){
		if (this.bloco == null) {
			bloco = new Bloco(120, 100, 100);
		}
		if (this.dadosDeProjeto == null) {
			dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
		}
		
		double retractPlane = 14.4;
		ArrayList<Double> axisFeature = new ArrayList<Double>();
		ArrayList<Double> refDirectionFeature = new ArrayList<Double>();

		// ---- creating a closed pocket ----
		Cavidade cavidade = new Cavidade();
		cavidade.setComprimento(30);
		cavidade.setLargura(40);
		cavidade.setProfundidade(10);
		cavidade.setPosicao(30, 40, 0);
		cavidade.setRaio(10);
		cavidade.setNome("Cavidade 1");
		Point3d coordinatesCavidade = new Point3d(30 + 15, 40 + 20, 50);
		Axis2Placement3D positionCavidade = new Axis2Placement3D(
				coordinatesCavidade, axisFeature, refDirectionFeature);
		cavidade.setPosition(positionCavidade);
		cavidade.setTolerancia(0.65);

		// ---- tools definition ----
		FaceMill faceMillCavidade = new FaceMill(9.0, 80.00);
		faceMillCavidade.setCuttingEdgeLength(8.0);
		faceMillCavidade.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillCavidade.setNumberOfTeeth(2);
		faceMillCavidade.setName("SF9");
		faceMillCavidade.setOffsetLength(85);

		EndMill endMillCavidade = new EndMill(8, 50);
		endMillCavidade.setName("SF8");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);

		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmCavidade = new BottomAndSideRoughMilling(
				"Op. desbaste - Cavidade", retractPlane);
		bsrmCavidade.setCoolant(true);
		bsrmCavidade.setAllowanceBottom(0.37);
		bsrmCavidade.setAllowanceSide(0.62);

		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling(
				"Op. acabamento - Cavidade", retractPlane);
		Point3d startPointFinishCavidade = new Point3d(0,0,(cavidade.getProfundidade() - bsrmCavidade.getAllowanceBottom()));
		bsfmCavidade.setStartPoint(startPointFinishCavidade);
		bsfmCavidade.setCoolant(true);

		// ---- cutting conditions definition ----
		CondicoesDeUsinagem condicoesDeUsinagemCavidade = new CondicoesDeUsinagem(
				100, 0.04, 2.3, 12.0 * 0.75, 0, 0, 1500, false,
				faceMillCavidade);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(
				160, 0.01, 12, 1500, 1, 4);
		condicoesDeUsinagemCavidade2.setAcabamento(true);
		// CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new
		// CondicoesDeUsinagem(160, 12, 1, 4,0,0.01,1500,true, endMillCavidade);

		Workingstep workingstepCavidade = new Workingstep(cavidade, this.face);
		workingstepCavidade.setFerramenta(faceMillCavidade);
		workingstepCavidade.setCondicoesUsinagem(condicoesDeUsinagemCavidade);
		workingstepCavidade.setOperation(bsrmCavidade);
		workingstepCavidade.setId("WS 1 - Cavidade");
		workingstepCavidade.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(workingstepCavidade));

				
		Workingstep workingstepCavidade2 = new Workingstep(cavidade, this.face);
		workingstepCavidade2.setFerramenta(endMillCavidade);
		workingstepCavidade2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		workingstepCavidade2.setOperation(bsfmCavidade);
		workingstepCavidade2.setId("WS 2 - Cavidade");
		workingstepCavidade2.setPontos(MapeadoraDeWorkingsteps
				.determinadorDePontos(workingstepCavidade2));

		
		// ---- feature definition ----
		FuroBasePlana furo = new FuroBasePlana();
		furo.setNome("FURO 1");
		furo.setPosicao(41, 51 , 10);
		furo.setProfundidade(20);
		furo.setRaio(10);
		furo.setPassante(false);
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis,
				refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);

		// ---- tool definition ----
		// Ferramenta para Workingstep 1
		CenterDrill brocaCenter = new CenterDrill(6, 30);
		brocaCenter.setCuttingEdgeLength(50.0);
		brocaCenter.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		brocaCenter.setOffsetLength(80);
		brocaCenter.setName("SF6");
		brocaCenter.setNumberOfTeeth(2);
		brocaCenter.setMaterialClasse(Ferramenta.P);

		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
				0.04, 0, 1500, 0, 0);
		double planoDeSeguranca = 10;

		// Workingstep 1
		CenterDrilling centerDrilling = new CenterDrilling("Center drilling",
				planoDeSeguranca);
		// Drilling drilling = new Drilling("Operacao de furacao",
		// planoDeSeguranca);
		centerDrilling.setCuttingDepth(5);
		Point3d startPointCenter = new Point3d(0, 0, 10);
		centerDrilling.setStartPoint(startPointCenter);

		Workingstep workingstep = new Workingstep(furo, this.face);
		workingstep.setId("furacao desbaste");
		workingstep.setTipo(Workingstep.DESBASTE);
		workingstep.setFerramenta(brocaCenter);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
		// workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
		workingstep.setOperation(centerDrilling);

		// Ferramenta para Workingstep 2
		TwistDrill broca1 = new TwistDrill(10, 79, 20);
		broca1.setCuttingEdgeLength(50.0);
		broca1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca1.setOffsetLength(80);
		broca1.setName("SF10");
		broca1.setMaterialClasse(TwistDrill.M);
		broca1.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 0, 0);

		// Workingstep 2
		Drilling drilling2 = new Drilling("Operacao de furacao",
				planoDeSeguranca);
		Point3d startPointDrilling2 = new Point3d(0, 0, 15);
		drilling2.setStartPoint(startPointDrilling2);
		drilling2.setCuttingDepth(furo.getProfundidade() - Feature.LIMITE_DESBASTE - 5);
		Workingstep workingstep1 = new Workingstep(furo, face);
		workingstep1.setId("furacao desbaste");
		workingstep1.setTipo(Workingstep.DESBASTE);
		workingstep1.setFerramenta(broca1);
		workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
		// workingstep1.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep1));
		workingstep1.setOperation(drilling2);

		// Ferramenta para Workingstep 3
		FaceMill faceMillRough = new FaceMill(10, 60);
		faceMillRough.setCuttingEdgeLength(50.0);
		faceMillRough.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillRough.setOffsetLength(80);
		faceMillRough.setName("SF10");
		faceMillRough.setMaterialClasse(Ferramenta.M);
		faceMillRough.setNumberOfTeeth(2);

		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
				0.04, 0, 2000, 2, 7.5);

		// Workingstep 3
		BottomAndSideRoughMilling roughMilling = new BottomAndSideRoughMilling(
				"Rough Milling", planoDeSeguranca);
		Point3d startPointRough = new Point3d(0, 0, 10);
		roughMilling.setStartPoint(startPointRough);
		roughMilling.setAllowanceBottom(Feature.LIMITE_DESBASTE);
		roughMilling.setAllowanceSide(Feature.LIMITE_DESBASTE);

		Workingstep workingstep2 = new Workingstep(furo, face);
		workingstep2.setId("Rough Milling - Furo");
		workingstep2.setTipo(Workingstep.DESBASTE);
		workingstep2.setFerramenta(faceMillRough);
		workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
		workingstep2.setOperation(roughMilling);

		// Ferramenta para Workingstep 4
		BoringTool boringTool = new BoringTool(20, 60);

		boringTool.setCuttingEdgeLength(10.0);
		boringTool.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		boringTool.setOffsetLength(70);
		boringTool.setName("SF20");
		boringTool.setMaterialClasse(Ferramenta.M);
		boringTool.setNumberOfTeeth(1);

		CondicoesDeUsinagem condicoesDeUsinagem3 = new CondicoesDeUsinagem(100,
				0.04, 0, 2500, 0, 0);

		// Workingstep 4
		Boring boringFinish = new Boring("Boring", planoDeSeguranca);
		Point3d startPointBoring = new Point3d(0, 0, 10);
		boringFinish.setStartPoint(startPointBoring);
		boringFinish.setCuttingDepth(furo.getProfundidade() - Feature.LIMITE_DESBASTE - 5);

		Workingstep workingstep3 = new Workingstep(furo, face);
		workingstep3.setId("Boring - Furo");
		workingstep3.setTipo(Workingstep.ACABAMENTO);
		workingstep3.setFerramenta(boringTool);
		workingstep3.setCondicoesUsinagem(condicoesDeUsinagem3);
		workingstep3.setOperation(boringFinish);

		// Ferramenta para Workingstep 5
		EndMill fresaAcabamento = new EndMill(10, 60);
		fresaAcabamento.setCuttingEdgeLength(40.0);
		fresaAcabamento.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		fresaAcabamento.setOffsetLength(60.0);
		fresaAcabamento.setName("SF10");
		fresaAcabamento.setMaterialClasse(Ferramenta.P);
		fresaAcabamento.setNumberOfTeeth(4);

		CondicoesDeUsinagem condicoesDeUsinagem4 = new CondicoesDeUsinagem(100,
				0.05, 0, 1500, 2, 15);

		// Workingstep 5
		BottomAndSideFinishMilling fresamentoFinish = new BottomAndSideFinishMilling(
				"Fresamento de Acabamento", planoDeSeguranca);
		Point3d startFresamento = new Point3d(
				0,
				0,
				(furo.getProfundidade() - roughMilling.getAllowanceBottom() - ((broca1
						.getDiametroFerramenta() / 2) * Math.tan(broca1
						.getToolTipHalfAngle()))));
		
		fresamentoFinish.setStartPoint(startFresamento);
		fresamentoFinish.setAllowanceBottom(0);
		fresamentoFinish.setAllowanceSide(0);

		Workingstep workingstep4 = new Workingstep(furo, face);
		workingstep4.setId("Fresamento - Furo");
		workingstep4.setTipo(Workingstep.ACABAMENTO);
		workingstep4.setFerramenta(fresaAcabamento);
		workingstep4.setCondicoesUsinagem(condicoesDeUsinagem4);
		workingstep4.setOperation(fresamentoFinish);

		// ---- creating a horizontal slot ----
		Ranhura ranhura = new Ranhura();
		ranhura.setLargura(16.3);
		ranhura.setProfundidade(7.0);
		ranhura.setPosicao(0, 80, 0);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setNome("Ranhura 1");
		Point3d coordinatesRanhura = new Point3d(0, 80 + ranhura.getLargura()/2, this.face.getProfundidadeMaxima() - ranhura.Z);
	
		Axis2Placement3D positionRanhura = new Axis2Placement3D(coordinatesRanhura, axisFeature, refDirectionFeature);
		ranhura.setPosition(positionRanhura);
		ranhura.setTolerancia(0.52);
		
		// ---- tools definitions ----
		FaceMill faceMill = new FaceMill(10.0, 70.0);
		faceMill.setCuttingEdgeLength(10.0);
		faceMill.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMill.setName("SF10");
		faceMill.setNumberOfTeeth(5);
		faceMill.setOffsetLength(72);
		
		EndMill endMill = new EndMill(8, 40);
		endMill.setName("EndMill");
		endMill.setCuttingEdgeLength(8);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setNumberOfTeeth(4);
		endMill.setOffsetLength(50);

		
		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmRanhura = new BottomAndSideRoughMilling("Op. desbaste ranhura", retractPlane  );
		bsrmRanhura.setCoolant(true);
		BottomAndSideFinishMilling bsfmRanhura = new BottomAndSideFinishMilling("Op. acabamento ranhura", retractPlane);
		
		// ---- cutting conditions ----
		CondicoesDeUsinagem condicoesDeUsinagemRanhura = new CondicoesDeUsinagem(100, 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, faceMill);
		CondicoesDeUsinagem condicoesDeUsinagemRanhura2 = new CondicoesDeUsinagem(150, 0.03, 15, 1500, 2, 6);
		
		Workingstep workingstepRanhura = new Workingstep(ranhura, this.face);
		workingstepRanhura.setFerramenta(faceMill);
		workingstepRanhura.setCondicoesUsinagem(condicoesDeUsinagemRanhura);
		workingstepRanhura.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstepRanhura));
		Vector movimentacaoRanhura = DeterminarMovimentacao.getPontosMovimentacao(workingstepRanhura);
		workingstepRanhura.setPontosMovimentacao(movimentacaoRanhura);
		workingstepRanhura.setOperation(bsrmRanhura);
		workingstepRanhura.setId("WS ranhura");
		
		
		// Adicionar os Workingsteps para a face da peca a ser Usinada
		this.WS = new Vector();
		Vector wsFace = new Vector();
		
		wsFace.add(workingstepCavidade);
		wsFace.add(workingstepCavidade2);
		wsFace.add(workingstep);
		wsFace.add(workingstep1);
		wsFace.add(workingstep2);
		wsFace.add(workingstep3);
		wsFace.add(workingstep4);
		
		wsFace.add(workingstepRanhura);
		
		WS.add(wsFace);

		GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);

		String GCodeString = Generator.GenerateGCodeString();
		System.out.println(GCodeString);

		
		
	}

	// @Test
	// public void addHole() {
	// if (this.bloco == null) {
	// bloco = new Bloco(100, 120, 50);
	// }
	// if (this.dadosDeProjeto == null) {
	// dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
	// }
	//
	// Material material = new Material();
	// material.setName("SAE 1030");
	// ArrayList<PropertyParameter> properties = new
	// ArrayList<PropertyParameter>();
	// PropertyParameter propertyParameter = new PropertyParameter();
	// propertyParameter.setParameterName("Young Module");
	// propertyParameter.setParameterUnit("MPa");
	// propertyParameter.setParameterValue(550.00);
	// properties.add(propertyParameter);
	// material.setProperties(properties);
	//
	// dadosDeProjeto.setMaterial(material);
	// bloco.setMaterial(material);
	//
	// if (this.projeto == null) {
	// projeto = new Projeto(null, bloco, dadosDeProjeto);
	// }
	// Face face = (Face) bloco.faces.get(Face.XY);
	//
	// Vector wsFace = new Vector();
	// this.WS = new Vector();
	//
	// Furo furo1 = new Furo();
	// furo1.setPosicao(50, 60, 0);
	// furo1.setProfundidade(50);
	// furo1.setRaio(7);
	// TwistDrill broca1 = new TwistDrill(14, 68, 20 * Math.PI / 180);
	// broca1.setCuttingEdgeLength(40.0);
	// CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
	// 0.045, 2, 0, 0, 0, 1200 / 60, false, broca1);
	// broca1.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	//
	// Workingstep workingstep1 = new Workingstep(furo1, face);
	// workingstep1.setFerramenta(broca1);
	// workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
	// workingstep1.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep1));
	// workingstep1.getFerramenta().setName("Broca1");
	//
	// Workingstep workingstep2 = new Workingstep(furo1, face);
	// workingstep2.setFerramenta(broca1);
	// workingstep2.setCondicoesUsinagem(condicoesDeUsinagem1);
	// workingstep2.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep2));
	// workingstep2.getFerramenta().setName("Broca1");
	//
	// wsFace.add(workingstep1);
	// wsFace.add(workingstep2);
	// this.WS.add(wsFace);
	//
	//
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	//
	// /*
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList = Generator.GenerateGCode();
	//
	// System.out.println("\n");
	//
	// Iterator<String> iterator = arrayList.iterator();
	// while (iterator.hasNext())
	// {
	// String auxLine = iterator.next();
	// System.out.println(auxLine);
	// }
	// */
	//
	//
	// }

	// @Test
	// public void add2Holes() {
	// if (this.bloco == null) {
	// bloco = new Bloco(100, 120, 50);
	// }
	// if (this.dadosDeProjeto == null) {
	// dadosDeProjeto = new DadosDeProjeto(0, "", "HOLE TEST PROJECT", 0);
	// }
	//
	// Material material = new Material();
	// material.setName("SAE 1030");
	// ArrayList<PropertyParameter> properties = new
	// ArrayList<PropertyParameter>();
	// PropertyParameter propertyParameter = new PropertyParameter();
	// propertyParameter.setParameterName("Young Module");
	// propertyParameter.setParameterUnit("MPa");
	// propertyParameter.setParameterValue(550.00);
	// properties.add(propertyParameter);
	// material.setProperties(properties);
	//
	// dadosDeProjeto.setMaterial(material);
	// bloco.setMaterial(material);
	//
	// if (this.projeto == null) {
	// projeto = new Projeto(null, bloco, dadosDeProjeto);
	// }
	// Face face = (Face) bloco.faces.get(Face.XY);
	//
	// Vector wsFace = new Vector();
	// this.WS = new Vector();
	//
	// Furo furo1 = new Furo();
	// furo1.setPosicao(50, 60, 0);
	// furo1.setProfundidade(50);
	// furo1.setRaio(7);
	// TwistDrill broca1 = new TwistDrill(14, 68, 20 * Math.PI / 180);
	// broca1.setCuttingEdgeLength(40.0);
	// CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
	// 0.045, 2, 0, 0, 0, 1200 / 60, false, broca1);
	// broca1.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	//
	// Workingstep workingstep1 = new Workingstep(furo1, face);
	// workingstep1.setFerramenta(broca1);
	// workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
	// workingstep1.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep1));
	// workingstep1.getFerramenta().setName("Broca1");
	//
	// wsFace.add(workingstep1);
	//
	//
	// Furo furo2 = new Furo();
	// furo2.setPosicao(70, 30, 0);
	// furo2.setProfundidade(50);
	// furo2.setRaio(7);
	// TwistDrill broca2 = new TwistDrill(14, 68, 20 * Math.PI / 180);
	// broca2.setCuttingEdgeLength(40.0);
	// CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
	// 0.045, 2, 0, 0, 0, 1200 / 60, false, broca2);
	// broca2.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	//
	// Workingstep workingstep2 = new Workingstep(furo2, face);
	// workingstep2.setFerramenta(broca2);
	// workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
	// workingstep2.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep2));
	// workingstep2.getFerramenta().setName("Broca2");
	//
	// wsFace.add(workingstep2);
	// this.WS.add(wsFace);
	//
	//
	//
	//
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	//
	// /*
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList = Generator.GenerateGCode();
	//
	// System.out.println("\n");
	//
	// Iterator<String> iterator = arrayList.iterator();
	// while (iterator.hasNext()) {
	// String auxLine = iterator.next();
	// System.out.println(auxLine);
	//
	// }
	// */
	//
	// }

	// @Test
	// public void addPocket() {
	// if (bloco == null) {
	// bloco = new Bloco(120, 100, 50);
	// }
	// if (dadosDeProjeto == null) {
	// dadosDeProjeto = new DadosDeProjeto(0, "", "POCKET TEST PROJECT", 0);
	// }
	// Material material = new Material();
	// material.setName("SAE 1015");
	// ArrayList<PropertyParameter> properties = new
	// ArrayList<PropertyParameter>();
	// PropertyParameter propertyParameter = new PropertyParameter();
	// propertyParameter.setParameterName("Young Module");
	// propertyParameter.setParameterUnit("MPa");
	// propertyParameter.setParameterValue(420.00);
	// properties.add(propertyParameter);
	// material.setProperties(properties);
	//
	// dadosDeProjeto.setMaterial(material);
	// bloco.setMaterial(material);
	//
	// if (projeto == null) {
	// projeto = new Projeto(null, bloco, dadosDeProjeto);
	// }
	//
	// Face face = (Face) bloco.faces.get(Face.XY);
	//
	// Cavidade cavidade = new Cavidade();
	// cavidade.setComprimento(80);
	// cavidade.setLargura(50);
	// cavidade.setProfundidade(30);
	// cavidade.setPosicao(10, 15, 0);
	// cavidade.setRaio(10);
	// cavidade.setNome("Cavidade 1");
	//
	// FaceMill fresa = new FaceMill(20.0, 120.00);
	// fresa.setCuttingEdgeLength(8.0);
	// fresa.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	// fresa.setNumberOfTeeth(2);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
	// 0.04, 2.0, 20.0 * 0.75, 0, 0, 12, false, fresa);
	//
	// Workingstep workingstep = new Workingstep(cavidade, face);
	// workingstep.setFerramenta(fresa);
	// workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
	// workingstep.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep));
	// workingstep.getFerramenta().setName("SF20");
	//
	// Workingstep workingstepA = new Workingstep(cavidade, face);
	// workingstepA.setFerramenta(fresa);
	// workingstepA.setCondicoesUsinagem(condicoesDeUsinagem);
	// workingstepA.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstepA));
	// workingstepA.getFerramenta().setName("SF20");
	//
	// this.WS = new Vector();
	// Vector wsFace = new Vector();
	// wsFace.add(workingstep);
	// wsFace.add(workingstepA);
	// WS.add(wsFace);
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	//
	// /*
	//
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList = Generator.GenerateGCode();
	//
	// System.out.println("\n");
	//
	// Iterator<String> iterator = arrayList.iterator();
	// while (iterator.hasNext())
	// {
	// String auxLine = iterator.next();
	// System.out.println(auxLine);
	//
	// }*/
	//
	// }

	// @Test
	// public void add2Pockets() {
	// if (bloco == null) {
	// bloco = new Bloco(120, 100, 50);
	// }
	// if (dadosDeProjeto == null) {
	// dadosDeProjeto = new DadosDeProjeto(0, "", "POCKET TEST PROJECT", 0);
	// }
	// Material material = new Material();
	// material.setName("SAE 1015");
	// ArrayList<PropertyParameter> properties = new
	// ArrayList<PropertyParameter>();
	// PropertyParameter propertyParameter = new PropertyParameter();
	// propertyParameter.setParameterName("Young Module");
	// propertyParameter.setParameterUnit("MPa");
	// propertyParameter.setParameterValue(420.00);
	// properties.add(propertyParameter);
	// material.setProperties(properties);
	//
	// dadosDeProjeto.setMaterial(material);
	// bloco.setMaterial(material);
	//
	// if (projeto == null) {
	// projeto = new Projeto(null, bloco, dadosDeProjeto);
	// }
	//
	// Face face = (Face) bloco.faces.get(Face.XY);
	//
	// Cavidade cavidade = new Cavidade();
	// cavidade.setComprimento(80);
	// cavidade.setLargura(50);
	// cavidade.setProfundidade(30);
	// cavidade.setPosicao(10, 15, 0);
	// cavidade.setRaio(10);
	// cavidade.setNome("Cavidade 1");
	//
	// FaceMill fresa = new FaceMill(20.0, 120.00);
	// fresa.setCuttingEdgeLength(8.0);
	// fresa.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	// fresa.setNumberOfTeeth(2);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100,
	// 0.04, 2.0, 20.0 * 0.75, 0, 0, 12, false, fresa);
	//
	// Workingstep workingstep = new Workingstep(cavidade, face);
	// workingstep.setFerramenta(fresa);
	// workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
	// workingstep.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep));
	// workingstep.getFerramenta().setName("SF20");
	//
	// Workingstep workingstepA = new Workingstep(cavidade, face);
	// workingstepA.setFerramenta(fresa);
	// workingstepA.setCondicoesUsinagem(condicoesDeUsinagem);
	// workingstepA.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstepA));
	// workingstepA.getFerramenta().setName("SF20");
	//
	//
	// Cavidade cavidade2 = new Cavidade();
	// cavidade2.setComprimento(40);
	// cavidade2.setLargura(25);
	// cavidade2.setProfundidade(10);
	// cavidade2.setPosicao(20, 30, 30);
	// cavidade2.setRaio(10);
	// cavidade2.setNome("Cavidade 1");
	//
	// FaceMill fresa2 = new FaceMill(12.0, 120.00);
	// fresa2.setCuttingEdgeLength(8.0);
	// fresa2.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	// fresa2.setNumberOfTeeth(2);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
	// 0.04, 2.0, 20.0 * 0.75, 0, 0, 12, false, fresa2);
	//
	// Workingstep workingstep2 = new Workingstep(cavidade2, face);
	// workingstep2.setFerramenta(fresa2);
	// workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
	// workingstep2.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep2));
	// workingstep2.getFerramenta().setName("SF12");
	//
	// this.WS = new Vector();
	// Vector wsFace = new Vector();
	// wsFace.add(workingstep);
	// wsFace.add(workingstepA);
	// wsFace.add(workingstep2);
	//
	// WS.add(wsFace);
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	//
	// /*
	//
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList = Generator.GenerateGCode();
	//
	// System.out.println("\n");
	//
	// Iterator<String> iterator = arrayList.iterator();
	// while (iterator.hasNext())
	// {
	// String auxLine = iterator.next();
	// System.out.println(auxLine);
	//
	// }*/
	//
	// }

	// @Test
	// public void addSlot() {
	// if (this.bloco == null) {
	// bloco = new Bloco(100, 120, 50);
	// }
	// if (this.dadosDeProjeto == null) {
	// dadosDeProjeto = new DadosDeProjeto(0, "", "SLOT TEST PROJECT", 0);
	// }
	// Material material = new Material();
	// material.setName("SAE 1040");
	// ArrayList<PropertyParameter> properties = new
	// ArrayList<PropertyParameter>();
	// PropertyParameter propertyParameter = new PropertyParameter();
	// propertyParameter.setParameterName("Young Module");
	// propertyParameter.setParameterUnit("MPa");
	// propertyParameter.setParameterValue(620.00);
	// properties.add(propertyParameter);
	// material.setProperties(properties);
	//
	// dadosDeProjeto.setMaterial(material);
	// bloco.setMaterial(material);
	//
	// if (this.projeto == null) {
	// projeto = new Projeto(null, bloco, dadosDeProjeto);
	// }
	// Face face = (Face) bloco.faces.get(Face.XY);
	//
	// Ranhura ranhura = new Ranhura();
	// ranhura.setLargura(25);
	// ranhura.setProfundidade(10);
	// ranhura.setPosicao(10, 0, 0);
	// ranhura.setEixo(Ranhura.VERTICAL);
	//
	// FaceMill fresa = new FaceMill(10.0, 70.0);
	// fresa.setCuttingEdgeLength(10.0);
	// fresa.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
	// fresa.setName("SF25");
	// fresa.setNumberOfTeeth(5);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
	// 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, fresa);
	//
	// Workingstep workingstep = new Workingstep(ranhura, face);
	// workingstep.setFerramenta(fresa);
	// workingstep.setCondicoesUsinagem(condicoesDeUsinagem1);
	// workingstep.setPontos(MapeadoraDeWorkingstep
	// .determinadorDePontos(workingstep));
	// Vector movimentacao =
	// DeterminarMovimentacao.getPontosMovimentacao(workingstep);
	// workingstep.setPontosMovimentacao(movimentacao);
	//
	// Ranhura ranhura2 = new Ranhura();
	// ranhura2.setLargura(13);
	// ranhura2.setProfundidade(22);
	// ranhura2.setPosicao(13, 0, 10);
	// ranhura2.setEixo(Ranhura.VERTICAL);
	//
	// FaceMill facemill = new FaceMill(12.0, 70.0);
	// facemill.setCuttingEdgeLength(10.0);
	// facemill.setHandOfCut(Ferramenta.RIGTH_HAND_OF_CUT);
	// facemill.setName("12");
	// facemill.setNumberOfTeeth(5);
	//
	// CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(100,
	// 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, facemill);
	//
	// Workingstep workingstep2 = new Workingstep(ranhura2, face);
	// workingstep2.setFerramenta(facemill);
	// workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
	// workingstep2.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep2));
	// Vector movimentacao2 =
	// DeterminarMovimentacao.getPontosMovimentacao(workingstep2);
	// workingstep2.setPontosMovimentacao(movimentacao2);
	//
	// this.WS = new Vector();
	// Vector wsFace = new Vector();
	//
	// wsFace.add(workingstep);
	// wsFace.add(workingstep2);
	// this.WS.add(wsFace);
	//
	//
	//
	//
	//
	// Vector pontos = (Vector)workingstep.getPontosMovimentacao().get(0);
	//
	// System.out.println(pontos.size());
	//
	// System.out.println("Pontos de Movimentacao");
	// for (int i = 0; i < pontos.size(); i++){
	//
	// Ponto pontoAux = (Ponto)pontos.get(i);
	//
	// System.out.println("Ponto" + i);
	// System.out.println("x = ");
	// System.out.println(pontoAux.getX());
	// System.out.println("y = ");
	// System.out.println(pontoAux.getY());
	// System.out.println("z = ");
	// System.out.println(pontoAux.getZ());
	//
	// }
	//
	// GCodeGenerator Generator = new GCodeGenerator(this.WS, this.projeto);
	//
	// String GCodeString = Generator.GenerateGCodeString();
	// System.out.println(GCodeString);
	//
	// /*
	//
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList = Generator.GenerateGCode();
	//
	// System.out.println("\n");
	//
	// Iterator<String> iterator = arrayList.iterator();
	// while (iterator.hasNext())
	// {
	// String auxLine = iterator.next();
	// System.out.println(auxLine);
	//
	// }*/
	//
	//
	// }

}
