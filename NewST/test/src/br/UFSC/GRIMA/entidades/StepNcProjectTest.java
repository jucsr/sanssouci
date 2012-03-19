package br.UFSC.GRIMA.entidades;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import jsdai.lang.SdaiException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;
/**
 * 
 * @author Jc
 *
 */
public class StepNcProjectTest 
{
	private StepNcProject stepNcProject;

	private Bloco bloco = null;
	private Face face = null;
	private DadosDeProjeto dadosDeProjeto = null;
	private Projeto projeto = null;
	private Vector WS;

	public final static int FURO = 0;
	public final static int DEGRAU = 1;
	public final static int RANHURA = 2;
	public final static int CAVIDADE = 3;
	public final static int CAVIDADE_FUNDO_ARREDONDADO = 4;
	public final static int FURO_BASE_CONICA = 5;
	
	@Before
	public void initProject()
	{
		
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
	public void addPocket() 
	{
		// ---- feature definition ----
		Cavidade cavidade = new Cavidade();
		cavidade.setComprimento(80);
		cavidade.setLargura(50);
		cavidade.setProfundidade(30);
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
		cavidade.setTolerancia(0.06);
		
		// ---- tool definition ----
		FaceMill faceMill = new FaceMill(20.0, 120.00);
		faceMill.setCuttingEdgeLength(8.0);
		faceMill.setHandOfCut(Ferramenta.LEFT_HAND_OF_CUT);
		faceMill.setNumberOfTeeth(4);

		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100, 0.04, 2.0, 20.0 * 0.75, 0, 0, 12, false, faceMill);
		double planoDeSeguranca = 10;
		
		BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling("Pocket Roughing Operation", planoDeSeguranca) ;
		bottomAndSideRoughMilling.setAllowanceBottom(0.5);
		bottomAndSideRoughMilling.setAllowanceSide(0.5);
		
		Workingstep workingstepRoughing = new Workingstep(cavidade, face);
		workingstepRoughing.setId("WS Rough - " + workingstepRoughing.getFeature().getNome());
		workingstepRoughing.setFerramenta(faceMill);
		workingstepRoughing.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstepRoughing.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstepRoughing));
		workingstepRoughing.setTipo(Workingstep.DESBASTE);
		workingstepRoughing.setOperation(bottomAndSideRoughMilling);
		
		EndMill endMill = new EndMill(10, 50);
		endMill.setCuttingEdgeLength(7.5);
		endMill.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		endMill.setNumberOfTeeth(5);
		
		CondicoesDeUsinagem cuAcabamento = new CondicoesDeUsinagem(200, 0.035, 0, 2000/60, 0.5, 10 * 0.75);
		BottomAndSideFinishMilling bottomAndSideFinishMilling = new BottomAndSideFinishMilling("Pocket Finishing Operation", planoDeSeguranca);

		Workingstep workingstepFinishing = new Workingstep(cavidade, face);
		workingstepFinishing.setId("WS Finishing - " + workingstepFinishing.getFeature().getNome());
		workingstepFinishing.setFerramenta(endMill);
		workingstepFinishing.setCondicoesUsinagem(cuAcabamento);
		workingstepFinishing.setPontos(MapeadoraDeWorkingsteps	.determinadorDePontos(workingstepFinishing));
		workingstepFinishing.setTipo(Workingstep.ACABAMENTO);
		workingstepFinishing.setOperation(bottomAndSideFinishMilling);
		
		/*
		
		Cavidade cavidade1 = new Cavidade();
		cavidade1.setComprimento(100);
		cavidade1.setLargura(40);
		cavidade1.setProfundidade(10);
		cavidade1.setPosicao(10, 10, 0);
		cavidade1.setRaio(20);

		Fresa fresa1 = new Fresa(10.0, 70.0);
		fresa1.setCuttingEdgeLength(10.0);

		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, fresa1);

		Workingstep workingstep1 = new Workingstep(cavidade1, face);
		workingstep1.setFerramenta(fresa1);
		workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
		workingstep1.setPontos(MapeadoraDeWorkingstep
				.determinadorDePontos(workingstep1));
		 */
		
		this.WS = new Vector();
		Vector wsFace = new Vector();
		wsFace.add(workingstepRoughing);
		wsFace.add(workingstepFinishing);
		WS.add(wsFace);
		// WS.add(workingstep1);

		ArrayList<Feature> arrayList = new ArrayList<Feature>();
		Feature anterior = null;
		
		for (int index = 0; index < this.WS.size(); index++)
		{
			Vector workinstepsFaceTmp = (Vector)this.WS.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
				
				if (anterior == null){
				arrayList.add(wsTmp.getFeature());	
				
				
				if (wsTmp.getFeature().getTipo() == FURO ){
					Furo fTmp = (Furo)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO \n" + "Nome do feature: " + fTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == DEGRAU ){
					Degrau dTmp = (Degrau)wsTmp.getFeature();
					System.out.println("Tipo do feature: DEGRAU \n" + "Nome do feature: " + dTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == RANHURA ){
					Ranhura rTmp = (Ranhura)wsTmp.getFeature();
					System.out.println("Tipo do feature: RANHURA \n" +  "Nome do feature: " + rTmp.getNome());
					}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE ){
					Cavidade cTmp = (Cavidade)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE \n" + "Nome do feature: " + cTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE_FUNDO_ARREDONDADO ){
					CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE_FUNDO_ARREDONDADO \n" + "Nome do feature: " + cfaTmp.getNome());
				}
				
								
				if (wsTmp.getFeature().getTipo() == FURO_BASE_CONICA ){
					FuroBaseConica fbcTmp = (FuroBaseConica)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO_BASE_CONICA \n" + "Nome do feature: " + fbcTmp.getNome());
				}
				
				anterior = wsTmp.getFeature();
				
				}
				
				else{
				if (wsTmp.getFeature().equals(anterior)){System.out.println("Feature igual nao adicionado");}
				else {arrayList.add(wsTmp.getFeature());
				
				if (wsTmp.getFeature().getTipo() == FURO ){
					Furo fTmp = (Furo)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO \n" + "Nome do feature: " + fTmp.getNome());
				}	
				if (wsTmp.getFeature().getTipo() == DEGRAU ){
					Degrau dTmp = (Degrau)wsTmp.getFeature();
					System.out.println("Tipo do feature: DEGRAU \n" + "Nome do feature: " + dTmp.getNome());
				}
				if (wsTmp.getFeature().getTipo() == RANHURA ){
					Ranhura rTmp = (Ranhura)wsTmp.getFeature();
					System.out.println("Tipo do feature: RANHURA \n" +  "Nome do feature: " + rTmp.getNome());
					}
				if (wsTmp.getFeature().getTipo() == CAVIDADE ){
					Cavidade cTmp = (Cavidade)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE \n" + "Nome do feature: " + cTmp.getNome());
				}
				if (wsTmp.getFeature().getTipo() == CAVIDADE_FUNDO_ARREDONDADO ){
					CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE_FUNDO_ARREDONDADO \n" + "Nome do feature: " + cfaTmp.getNome());
				}				
				if (wsTmp.getFeature().getTipo() == FURO_BASE_CONICA ){
					FuroBaseConica fbcTmp = (FuroBaseConica)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO_BASE_CONICA \n" + "Nome do feature: " + fbcTmp.getNome());
				}
				}
				anterior = wsTmp.getFeature();
				}
			}
		}
	System.out.println("Features n:" +arrayList.size() );
	try {
			stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addFlatBottomHole() 
	{
		// ---- feature definition ----
		FuroBasePlana furo = new FuroBasePlana();
		furo.setNome("FURO 1");
		furo.setPosicao(20, 60, 0);
		furo.setProfundidade(10);
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
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);

		// ---- tool definition ----
		TwistDrill broca = new TwistDrill(18, 79, 20);
		broca.setCuttingEdgeLength(50.0);
		broca.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca.setOffsetLength(80);
		broca.setName("Broca 1");
		broca.setNumberOfTeeth(2);
		broca.setMaterialClasse(Ferramenta.P);
		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100, 0.045, 2, 0, 0, 0, 900 / 60, false, broca);
		
		double planoDeSeguranca = 10;
		Drilling drilling = new Drilling("Operacao de furacao", planoDeSeguranca);
		
		Workingstep workingstep = new Workingstep(furo, this.face);
		workingstep.setId("furacao desbaste");
		workingstep.setTipo(Workingstep.DESBASTE);
		workingstep.setFerramenta(broca);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
		workingstep.setOperation(drilling);
		Vector wsFace = new Vector();
		this.WS = new Vector();

		TwistDrill broca1 = new TwistDrill(18, 79, 20);
		broca1.setCuttingEdgeLength(50.0);
		broca1.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca1.setOffsetLength(80);
		broca1.setName("broca desbaste");
		broca1.setMaterialClasse(TwistDrill.M);
		broca.setNumberOfTeeth(2);
		
		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100, 0.045, 2, 0, 0, 0, 900 / 60, false, broca1);
	
		Drilling drilling2 = new Drilling("Operacao de furacao - alargamento ", planoDeSeguranca);
		
		Workingstep workingstep1 = new Workingstep(furo, face);
		workingstep1.setId("furacao acabamento");
		workingstep1.setTipo(Workingstep.ACABAMENTO);
		workingstep1.setFerramenta(broca1);
		workingstep1.setCondicoesUsinagem(condicoesDeUsinagem1);
		workingstep1.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep1));
		workingstep1.setOperation(drilling2);
		
		wsFace.add(workingstep);
		wsFace.add(workingstep1);
		
		this.WS.add(wsFace);
		
		
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addThroughHole()
	{
		// ---- feature definition ----
		FuroBasePlana furo = new FuroBasePlana();
		furo.setNome("FURO PASSANTE");
		furo.setPosicao(20, 60, 0);
		furo.setProfundidade(50);
		furo.setRaio(10);
		furo.setPassante(true);	
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		
		// ---- tool definition ----
		TwistDrill broca = new TwistDrill(18, 79, 20);
		broca.setCuttingEdgeLength(50.0);
		broca.setOffsetLength(79);
		broca.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		broca.setName("Broca de desbaste");
		broca.setOffsetLength(82);
		broca.setNumberOfTeeth(2);
		broca.setMaterialClasse(TwistDrill.M);
		
		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100, 0.045, 2, 0, 0, 0, 900 / 60, false, broca);
		double planoDeSeguranca = 10;
		Drilling drilling = new Drilling("Drilling", planoDeSeguranca) ;
		
		Workingstep workingstep = new Workingstep(furo, face);
		workingstep.setId("furacao desbaste");
		workingstep.setTipo(Workingstep.DESBASTE);
		workingstep.setFerramenta(broca);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstep.setOperation(drilling);
		workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
		
		BoringTool borer = new BoringTool(20, 75);
		borer.setCuttingEdgeLength(55);
		borer.setOffsetLength(75);
		borer.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		borer.setNumberOfTeeth(5);
		borer.setName("alargador");
		borer.setMaterialClasse(BoringTool.K);
		
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.05, 2, 1200/60, 0, 0);
		
		Boring boring = new Boring("Boring Operation", planoDeSeguranca);
		
		Workingstep ws1 = new Workingstep(furo, face);
		ws1.setId("WS Boring");
		ws1.setTipo(Workingstep.ACABAMENTO);
		ws1.setFerramenta(borer);
		ws1.setCondicoesUsinagem(cu);
		ws1.setOperation(boring);
		//pontos
		
		Vector wsFace = new Vector();
		wsFace.add(workingstep);
		wsFace.add(ws1);
		this.WS = new Vector();
		this.WS.add(wsFace);
		try {
			stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addConicalBottomHole()
	{
		// ---- feature definition ----
		FuroBaseConica furo = new FuroBaseConica();
		furo.setTipAngle(70 * Math.PI/180);
		furo.setProfundidade(10);
		furo.setPosicao(30, 25, 0);
		furo.setRaio(9.0);
		furo.setNome("Furo Base Conica");
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		
		// ---- tools definition ----
		CenterDrill centerDrill = new CenterDrill(5, 10);
		centerDrill.setToolTipHalfAngle(20 * Math.PI/180);
		centerDrill.setName("Broca de centro");
		centerDrill.setNumberOfTeeth(2);
		centerDrill.setCuttingEdgeLength(8);
		centerDrill.setHandOfCut(CenterDrill.RIGHT_HAND_OF_CUT);
		centerDrill.setOffsetLength(40);
		centerDrill.setMaterialClasse(CenterDrill.H);
		
		TwistDrill broca = new TwistDrill(16, 60, 20 * Math.PI/180);
		broca.setName("Broca de desbaste");
		broca.setNumberOfTeeth(2);
		broca.setCuttingEdgeLength(60);
		broca.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca.setOffsetLength(70);
		
		TwistDrill broca1 = new TwistDrill(18, 60, 20 * Math.PI/180);
		broca1.setName("Broca de acabamento");
		broca1.setNumberOfTeeth(2);
		broca1.setCuttingEdgeLength(55);
		broca1.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca1.setOffsetLength(70);
		broca1.setMaterialClasse(TwistDrill.S);
		
		// ---- operations definition ----
		double retractPlane = 10;
		CenterDrilling centerDrilling = new CenterDrilling("Operacao de furacao de centro", retractPlane);
		centerDrilling.setCoolant(false);
		centerDrilling.setCuttingDepth(2);
		
		Drilling drilling = new Drilling("Desbaste Furo", retractPlane);
		drilling.setCoolant(true);
		drilling.setCuttingDepth(9);
		
		Drilling drilling1 = new Drilling("Acabamento Furo", retractPlane);
		drilling1.setCoolant(true);
		drilling1.setStartPoint(new Point3d(0, 0, -8));
		drilling1.setCuttingDepth(2);
		
		// ---- cutting parameters ----
		CondicoesDeUsinagem cu0 = new CondicoesDeUsinagem(120, 0.01, 0, 1000, 0, 0);
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.01, 0, 1200, 0, 0);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.01, 0, 1800, 0, 0);
		
		// workingsteps definition ----
		Workingstep ws0 = new Workingstep(furo, this.face);
		ws0.setCondicoesUsinagem(cu0);
		ws0.setId("WS center drilling");
		ws0.setFerramenta(centerDrill);
		ws0.setOperation(centerDrilling);
		
		Workingstep ws = new Workingstep(furo, this.face);
		ws.setCondicoesUsinagem(cu);
		ws.setFerramenta(broca);
		ws.setId("Operacao Furacao Desbaste");
		ws.setOperation(drilling);
		
		Workingstep ws1 = new Workingstep(furo, this.face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(broca1);
		ws1.setId("Operacao Furacao Acabamento");
		ws1.setOperation(drilling1);
		
		Vector wsFace = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws);
		wsFace.add(ws1);
		
		this.WS = new Vector();
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addFlatWithRadiusBottomHole()
	{
		// ---- feature definition ----
		FuroBaseArredondada furo = new FuroBaseArredondada();
		furo.setRaio(5);
		furo.setProfundidade(12);
		furo.setR1(2);
		furo.setPosicao(15, 25, 50);
		furo.setNome("Furo Base arredondada");
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		// ---- tools definition -----
		CenterDrill centerDrill = new CenterDrill(5, 10);
		centerDrill.setToolTipHalfAngle(20 * Math.PI/180);
		centerDrill.setName("Broca de centro");
		centerDrill.setNumberOfTeeth(2);
		centerDrill.setCuttingEdgeLength(8);
		centerDrill.setHandOfCut(CenterDrill.RIGHT_HAND_OF_CUT);
		centerDrill.setOffsetLength(40);
		
		TwistDrill broca = new TwistDrill(9, 60, 20 * Math.PI/180);
		broca.setName("Broca de desbaste");
		broca.setNumberOfTeeth(2);
		broca.setCuttingEdgeLength(60);
		broca.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca.setOffsetLength(70);
		
		Reamer reamer = new Reamer(10, 50);
		reamer.setName("Alargador");
		reamer.setNumberOfTeeth(6);
		reamer.setHandOfCut(Reamer.RIGHT_HAND_OF_CUT);
		reamer.setCuttingEdgeLength(40);
		reamer.setOffsetLength(50);
		
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("Fresa com cantos arredondados");
		faceMill.setNumberOfTeeth(4);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		faceMill.setCuttingEdgeLength(10);
		faceMill.setOffsetLength(40);
		faceMill.setEdgeRadius(2);
		
		// ---- operations definition ----
		double retractPlane = 11;
		
		CenterDrilling centerDrilling = new CenterDrilling("OP. furacao de centro", retractPlane);
		centerDrilling.setCoolant(true);
		centerDrilling.setCuttingDepth(4);
		
		Drilling drilling = new Drilling("OP Furacao", retractPlane);
		drilling.setCoolant(true);
		drilling.setCuttingDepth(10);
		
		Reaming reaming = new Reaming("OP Alargamento", retractPlane);
		reaming.setCoolant(false);
		reaming.setCuttingDepth(10);
		
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("OP Fresamento para dar a forma arredondada na base", retractPlane);
		bsrm.setCoolant(true);
		bsrm.setStartPoint(new Point3d(0, 0, -10));
		
		// ---- cutting parameters ----
		CondicoesDeUsinagem cu0 = new CondicoesDeUsinagem(100, 0.01, 0, 1500, 0, 0);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1000, 0, 0);
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(120, 0.01, 0, 1800, 0, 0);
		CondicoesDeUsinagem cu3 = new CondicoesDeUsinagem(100, 0.01, 0, 1000, 0.2, 0.75 * faceMill.getDiametroFerramenta());
		
		// ---- workingsteps definition ----
		Workingstep ws0 = new Workingstep(furo, face);
		ws0.setId("Machining Workingstep 0");
		ws0.setFerramenta(centerDrill);
		ws0.setOperation(centerDrilling);
		ws0.setCondicoesUsinagem(cu0);
		
		Workingstep ws1 = new Workingstep(furo, face);
		ws1.setId("Machining Workingstep 1");
		ws1.setFerramenta(broca);
		ws1.setOperation(drilling);
		ws1.setCondicoesUsinagem(cu1);
		
		Workingstep ws2 = new Workingstep(furo, face);
		ws2.setId("Machining Workinstep 2");
		ws2.setFerramenta(reamer);
		ws2.setOperation(reaming);
		ws2.setCondicoesUsinagem(cu2);
		
		Workingstep ws3 = new Workingstep(furo, face);
		ws3.setId("Machining Workinstep 3");
		ws3.setFerramenta(faceMill);
		ws3.setOperation(bsrm);
		ws3.setCondicoesUsinagem(cu3);
		
		Vector wsFace = new Vector();
		
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		wsFace.add(ws3);
		this.WS = new Vector();
		this.WS.add(wsFace);
		
		try {
			stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addSphericalBottomHole()
	{
		// ---- feature definition ----
		FuroBaseEsferica furo = new FuroBaseEsferica();
		furo.setRaio(5);
		furo.setProfundidade(12);
		furo.setFloorRadius(furo.getRaio());
		furo.setPosicao(15, 25, 50);
		furo.setNome("Furo com Base espferica");
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		
		// ---- tools definition -----
		CenterDrill centerDrill = new CenterDrill(5, 20);
		centerDrill.setToolTipHalfAngle(20 * Math.PI/180);
		centerDrill.setName("Broca de centro");
		centerDrill.setNumberOfTeeth(2);
		centerDrill.setCuttingEdgeLength(8);
		centerDrill.setHandOfCut(CenterDrill.RIGHT_HAND_OF_CUT);
		centerDrill.setOffsetLength(40);
		
		TwistDrill broca = new TwistDrill(9, 60, 20 * Math.PI/180);
		broca.setName("Broca de desbaste");
		broca.setNumberOfTeeth(2);
		broca.setCuttingEdgeLength(60);
		broca.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca.setOffsetLength(70);
		
		Reamer reamer = new Reamer(10, 50);
		reamer.setName("Alargador");
		reamer.setNumberOfTeeth(6);
		reamer.setHandOfCut(Reamer.RIGHT_HAND_OF_CUT);
		reamer.setCuttingEdgeLength(40);
		reamer.setOffsetLength(50);
		
		BallEndMill ballEndMill = new BallEndMill(10, 40);
		ballEndMill.setName("Fresa BallEndMIll");
		ballEndMill.setNumberOfTeeth(4);
		ballEndMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		ballEndMill.setCuttingEdgeLength(10);
		ballEndMill.setOffsetLength(40);
		ballEndMill.setEdgeRadius(ballEndMill.getDiametroFerramenta()/2);
		
		// ---- operations definition ----
		double retractPlane = 12;
		
		CenterDrilling centerDrilling = new CenterDrilling("OP. furacao de centro", retractPlane);
		centerDrilling.setCoolant(true);
		centerDrilling.setCuttingDepth(4);
		
		Drilling drilling = new Drilling("OP Furacao", retractPlane);
		drilling.setCoolant(true);
		drilling.setCuttingDepth(10);
		
		Reaming reaming = new Reaming("OP Alargamento", retractPlane);
		reaming.setCoolant(false);
		reaming.setCuttingDepth(10);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("OP Forma a base esferica", retractPlane);
		bsfm.setCoolant(true);
		bsfm.setStartPoint(new Point3d(0, 0, -10));
		
		// ---- cutting parameters ----
		CondicoesDeUsinagem cu0 = new CondicoesDeUsinagem(100, 0.01, 0, 1500, 0, 0);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1000, 0, 0);
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(120, 0.01, 0, 1800, 0, 0);
		CondicoesDeUsinagem cu3 = new CondicoesDeUsinagem(100, 0.01, 0, 1000, 0.2, 0.75 * ballEndMill.getDiametroFerramenta());
		
		// ---- workingsteps definition ----
		Workingstep ws0 = new Workingstep(furo, face);
		ws0.setId("Machining Workingstep 0");
		ws0.setFerramenta(centerDrill);
		ws0.setOperation(centerDrilling);
		ws0.setCondicoesUsinagem(cu0);
		
		Workingstep ws1 = new Workingstep(furo, face);
		ws1.setId("Machining Workingstep 1");
		ws1.setFerramenta(broca);
		ws1.setOperation(drilling);
		ws1.setCondicoesUsinagem(cu1);
		
		Workingstep ws2 = new Workingstep(furo, face);
		ws2.setId("Machining Workinstep 2");
		ws2.setFerramenta(reamer);
		ws2.setOperation(reaming);
		ws2.setCondicoesUsinagem(cu2);
		
		Workingstep ws3 = new Workingstep(furo, face);
		ws3.setId("Machining Workinstep 3");
		ws3.setFerramenta(ballEndMill);
		ws3.setOperation(bsfm);
		ws3.setCondicoesUsinagem(cu3);
		
		Vector wsFace = new Vector();
		
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		wsFace.add(ws3);
		this.WS = new Vector();
		this.WS.add(wsFace);
		
		try {
			stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addConicalHole()
	{
		// ---- feature definition ----
		FuroConico furo = new FuroConico();
		furo.setProfundidade(20);
		furo.setPosicao(30, 25, 50);
		furo.setRaio(9.0);
		furo.setRaio1(6.0);
		furo.setNome("Furo Base Conica");
		furo.setTolerancia(0.05);
		Point3d coordinates = new Point3d(furo.X, furo.Y, furo.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("furo Placement");
		furo.setPosition(position);
		furo.setPassante(false);
		
		// ---- tools definition ----
		CenterDrill centerDrill = new CenterDrill(4, 15);
		centerDrill.setToolTipHalfAngle(20 * Math.PI/180);
		centerDrill.setName("Broca de centro");
		centerDrill.setNumberOfTeeth(2);
		centerDrill.setCuttingEdgeLength(8);
		centerDrill.setHandOfCut(CenterDrill.RIGHT_HAND_OF_CUT);
		centerDrill.setOffsetLength(40);
		
		TwistDrill broca = new TwistDrill(12, 60, 20 * Math.PI/180);
		broca.setName("Broca de desbaste");
		broca.setNumberOfTeeth(2);
		broca.setCuttingEdgeLength(60);
		broca.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca.setOffsetLength(70);
		
		Reamer reamer = new Reamer(12, 60);
		reamer.setName("Broca de acabamento");
		reamer.setNumberOfTeeth(2);
		reamer.setCuttingEdgeLength(55);
		reamer.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		reamer.setOffsetLength(70);
		reamer.setToolCircunferenceAngle(Math.atan(3.0 /20.0));
	
		// ---- operations definition ----
		double retractPlane = 10;
		CenterDrilling centerDrilling = new CenterDrilling("Operacao de furacao de centro", retractPlane);
		centerDrilling.setCoolant(false);
		centerDrilling.setCuttingDepth(2);
		
		Drilling drilling = new Drilling("Desbaste Furo", retractPlane);
		drilling.setCoolant(true);
		drilling.setCuttingDepth(9);
		
		Reaming reaming = new Reaming("Acabamento Furo", retractPlane);
		reaming.setCoolant(true);
		reaming.setStartPoint(new Point3d(0, 0, -8));
		reaming.setCuttingDepth(2);
		
		// ---- cutting parameters ----
		CondicoesDeUsinagem cu0 = new CondicoesDeUsinagem(120, 0.01, 0, 1000, 0, 0);
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.01, 0, 1200, 0, 0);
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(150, 0.01, 0, 1800, 0, 0);
		
		// workingsteps definition ----
		Workingstep ws0 = new Workingstep(furo, face);
		ws0.setCondicoesUsinagem(cu0);
		ws0.setId("WS center drilling");
		ws0.setFerramenta(centerDrill);
		ws0.setOperation(centerDrilling);
		
		Workingstep ws = new Workingstep(furo, face);
		ws.setCondicoesUsinagem(cu);
		ws.setFerramenta(broca);
		ws.setId("Operacao Furacao Desbaste");
		ws.setOperation(drilling);
		
		Workingstep ws1 = new Workingstep(furo, face);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(reamer);
		ws1.setId("Operacao Furacao Acabamento");
		ws1.setOperation(reaming);
		
		Vector wsFace = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws);
		wsFace.add(ws1);
		
		this.WS = new Vector();
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addSlot() 
	{
		//---- feature definition ----
		Ranhura ranhura = new Ranhura();
		ranhura.setNome("Ranhura normal");
		ranhura.setLargura(25);
		ranhura.setProfundidade(10);
		ranhura.setPosicao(30, 0, 0);
		ranhura.setEixo(Ranhura.VERTICAL);
		ranhura.setComprimento(100);
		ranhura.setTolerancia(0.055);
		Point3d coordinates = new Point3d(ranhura.X + ranhura.getLargura() / 2, ranhura.Y, this.face.getProfundidadeMaxima() - ranhura.Z); // adicionei a altura do bloco
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("ranhura Placement");
		ranhura.setPosition(position);
		
		//---- tools definition ----
		FaceMill faceMill = new FaceMill(10.0, 70.0);
		faceMill.setCuttingEdgeLength(10.0);
		faceMill.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMill.setNumberOfTeeth(5);
		faceMill.setOffsetLength(70);

		EndMill endMill = new EndMill(6.0, 50);
		endMill.setCuttingEdgeLength(6);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setNumberOfTeeth(6);
		endMill.setOffsetLength(55);
		double retractPlane = 14;
		
		//---- operations definition ----
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("OP desbaste", retractPlane);
		bsrm.setCoolant(true);
		Point3d startPoint = new Point3d(-ranhura.getLargura()/2 + faceMill.getDiametroFerramenta() / 2 - bsrm.getAllowanceSide(), 0, 0);
		bsrm.setStartPoint(startPoint);
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("OP acabamento", retractPlane);
		startPoint = new Point3d(-ranhura.getLargura()/2 + endMill.getDiametroFerramenta(), 0, 0);
		bsfm.setStartPoint(startPoint);
		bsfm.setCoolant(false);
		
		//---- cutting parameters ----
		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100, 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, faceMill);

		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(150, 0.03, 0, 1200, 2.0, 0.75 * endMill.getDiametroFerramenta());
		
		//----workinsteps definition ----
		Workingstep workingstep = new Workingstep(ranhura, face);
		workingstep.setId("WS ranhura - desbaste");
		workingstep.setFerramenta(faceMill);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem1);
		workingstep.setOperation(bsrm);
		workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));

		Workingstep workingstep2 = new Workingstep(ranhura, this.face);
		workingstep2.setId("WS ranhura - acabamento");
		workingstep2.setFerramenta(endMill);
		workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
		workingstep2.setOperation(bsfm);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();

		wsFace.add(workingstep);
		wsFace.add(workingstep2);
		this.WS.add(wsFace);
		try {
			stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addSQuareUProfileSlot()
	{
		// ---- creating a square u profile slot
		RanhuraPerfilQuadradoU slot = new RanhuraPerfilQuadradoU();
		slot.setPosicao(0, 60, 0);
		slot.setNome("Ranhura perfil U Quadrado");
//		slot.setLargura(21);
		slot.setLargura2(12);
		slot.setComprimento(120);
		slot.setRaio(4.0);
		slot.setAngulo(30);
		slot.setProfundidade(10);
		slot.setTolerancia(0.053245);
		slot.setEixo(Ranhura.HORIZONTAL);
		Point3d coordinates = new Point3d(0, slot.Y + slot.getProfundidade() * Math.tan(slot.getAngulo() * Math.PI  / 180) + slot.getLargura2() / 2, this.face.getProfundidadeMaxima() - slot.Z); // por causa da profundidade
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("Slot placement");
		slot.setPosition(position);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(8, 50);
		faceMill.setName("Fresa de desbaste");
		faceMill.setCuttingEdgeLength(12);
		faceMill.setOffsetLength(70);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		faceMill.setNumberOfTeeth(4);
		
		BallEndMill ballEndMill = new BallEndMill(8, 50);
		ballEndMill.setName("Fresa esferica");
		ballEndMill.setCuttingEdgeLength(6);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		ballEndMill.setOffsetLength(55);
		ballEndMill.setNumberOfTeeth(4);
		
		// ---- operations definition ----
		double retractPlane = 11;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Op Desbaste", retractPlane);
		bsrm.setCoolant(true);
		bsrm.setAllowanceSide(0.5);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Op Acabamento", retractPlane);
		bsfm.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(120, 0.01, 0, 1000, 2, faceMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(110, 0.01, 0, 1500, 1, 0.2 * ballEndMill.getDiametroFerramenta());
	
		// ---- WS definition ----
		Workingstep ws = new Workingstep(slot, this.face);
		ws.setFerramenta(faceMill);
		ws.setOperation(bsrm);
		ws.setCondicoesUsinagem(cu);
		ws.setId("WS desbaste");
		
		Workingstep ws1 = new Workingstep(slot, this.face);
		ws1.setFerramenta(ballEndMill);
		ws1.setOperation(bsfm);
		ws1.setCondicoesUsinagem(cu1);
		ws1.setId("WS acabamento");
		
		Vector wsFace = new Vector();
		wsFace.add(ws);
		wsFace.add(ws1);
		
		this.WS = new Vector();
		this.WS.add(wsFace); 
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addPartialCircularProfileSlot()
	{
		// ---- feature definition ----
		RanhuraPerfilCircularParcial ranhura = new RanhuraPerfilCircularParcial();
		ranhura.setPosicao(0, 45, 0);
		ranhura.setRaio(10);
		ranhura.setComprimento(120);
		ranhura.setDz(3);
		ranhura.setAngulo(2 * 180 / Math. PI * Math.acos(ranhura.getDz() / ranhura.getRaio()));
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setTolerancia(0.07);
		Point3d coordinates = new Point3d(0, 40, this.face.getProfundidadeMaxima() - ranhura.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		ranhura.setPosition(position);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("Fresa de topo para desbaste");
		faceMill.setCuttingEdgeLength(12);
		faceMill.setOffsetLength(50);
		faceMill.setNumberOfTeeth(4);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		
		EndMill endMill = new EndMill(6, 30);
		endMill.setName("Fresa de topo pre acabamento");
		endMill.setCuttingEdgeLength(10);
		endMill.setOffsetLength(40);
		endMill.setNumberOfTeeth(4);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BallEndMill ballEndMill = new BallEndMill(10, 35);
		ballEndMill.setName("Fresa esferica para dar formato a ranhura");
		ballEndMill.setCuttingEdgeLength(5);
		ballEndMill.setOffsetLength(40);
		ballEndMill.setNumberOfTeeth(6);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		
		// ---- operations definition ----
		double retractPlane = 10;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Operacao Desbaste", retractPlane);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Operacao semi-acabamento", retractPlane);
		bsfm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm1 = new BottomAndSideFinishMilling("Operacao de acabamento", retractPlane);
		bsfm1.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.02, 0, 1000, 2, faceMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1200, 2, endMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(130, 0.01, 0, 120, 1, 2);
		
		// ---- WS definition ----
		Workingstep ws0 = new Workingstep(ranhura, this.	face);
		ws0.setOperation(bsrm);
		ws0.setId("WS 0");
		ws0.setCondicoesUsinagem(cu);
		ws0.setFerramenta(faceMill);
		
		Workingstep ws1 = new Workingstep(ranhura, this.face);
		ws1.setOperation(bsfm);
		ws1.setId("WS 1");
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(endMill);
		
		Workingstep ws2 = new Workingstep(ranhura, this.face);
		ws2.setOperation(bsfm1);
		ws2.setId("WS 2");
		ws2.setCondicoesUsinagem(cu2);
		ws2.setFerramenta(ballEndMill);
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addRoundedUProfileSlot()
	{
		// ---- feature definition ----
		RanhuraPerfilRoundedU ranhura = new RanhuraPerfilRoundedU();
		ranhura.setPosicao(0, 30, 0);
		ranhura.setComprimento(120);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setProfundidade(13);
		ranhura.setLargura(11);
		ranhura.setTolerancia(0.067258);
		Point3d coordinates = new Point3d(0, ranhura.getPosicaoY() + ranhura.getLargura() / 2, this.face.getProfundidadeMaxima() - ranhura.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		ranhura.setPosition(position);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("Fresa de topo para desbaste");
		faceMill.setCuttingEdgeLength(12);
		faceMill.setOffsetLength(50);
		faceMill.setNumberOfTeeth(4);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		
		EndMill endMill = new EndMill(6, 30);
		endMill.setName("Fresa de topo pre acabamento");
		endMill.setCuttingEdgeLength(10);
		endMill.setOffsetLength(40);
		endMill.setNumberOfTeeth(4);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BallEndMill ballEndMill = new BallEndMill(10, 35);
		ballEndMill.setName("Fresa esferica para dar formato a ranhura");
		ballEndMill.setCuttingEdgeLength(5);
		ballEndMill.setOffsetLength(40);
		ballEndMill.setNumberOfTeeth(6);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		
		// ---- operations definition ----
		double retractPlane = 10;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Operacao Desbaste", retractPlane);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Operacao semi-acabamento", retractPlane);
		bsfm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm1 = new BottomAndSideFinishMilling("Operacao de acabamento", retractPlane);
		bsfm1.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.02, 0, 1000, 2, faceMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1200, 2, endMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(130, 0.01, 0, 120, 1, 2);
		
		// ---- WS definition ----
		Workingstep ws0 = new Workingstep(ranhura, this.	face);
		ws0.setOperation(bsrm);
		ws0.setId("WS 0");
		ws0.setCondicoesUsinagem(cu);
		ws0.setFerramenta(faceMill);
		
		Workingstep ws1 = new Workingstep(ranhura, this.face);
		ws1.setOperation(bsfm);
		ws1.setId("WS 1");
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(endMill);
		
		Workingstep ws2 = new Workingstep(ranhura, this.face);
		ws2.setOperation(bsfm1);
		ws2.setId("WS 2");
		ws2.setCondicoesUsinagem(cu2);
		ws2.setFerramenta(ballEndMill);
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addVeeProfileSlot()
	{
		// ---- feature definition ----
		RanhuraPerfilVee ranhura = new RanhuraPerfilVee();
		ranhura.setNome("Ranhura perfil Vee");
		ranhura.setPosicao(0, 41, 0);
		ranhura.setComprimento(120);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		ranhura.setProfundidade(13);
		ranhura.setLargura(28.485);
		ranhura.setAngulo(90);
		ranhura.setRaio(3);
		ranhura.setTolerancia(0.063);
		Point3d coordinates = new Point3d(0, ranhura.Y + ranhura.getLargura() / 2, this.face.getProfundidadeMaxima() - ranhura.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		ranhura.setPosition(position);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("Fresa de topo para desbaste");
		faceMill.setCuttingEdgeLength(12);
		faceMill.setOffsetLength(50);
		faceMill.setNumberOfTeeth(4);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		
		EndMill endMill = new EndMill(6, 30);
		endMill.setName("Fresa de topo pre acabamento");
		endMill.setCuttingEdgeLength(10);
		endMill.setOffsetLength(40);
		endMill.setNumberOfTeeth(4);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BallEndMill ballEndMill = new BallEndMill(6, 35);
		ballEndMill.setName("Fresa esferica para dar formato a ranhura");
		ballEndMill.setCuttingEdgeLength(5);
		ballEndMill.setOffsetLength(40);
		ballEndMill.setNumberOfTeeth(6);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		
		// ---- operations definition ----
		double retractPlane = 10;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Operacao Desbaste", retractPlane);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Operacao semi-acabamento", retractPlane);
		bsfm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm1 = new BottomAndSideFinishMilling("Operacao de acabamento", retractPlane);
		bsfm1.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.02, 0, 1000, 2, faceMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1200, 2, endMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(130, 0.01, 0, 120, 1, 2);
		
		// ---- WS definition ----
		Workingstep ws0 = new Workingstep(ranhura, this.	face);
		ws0.setOperation(bsrm);
		ws0.setId("WS 0");
		ws0.setCondicoesUsinagem(cu);
		ws0.setFerramenta(faceMill);
		
		Workingstep ws1 = new Workingstep(ranhura, this.face);
		ws1.setOperation(bsfm);
		ws1.setId("WS 1");
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(endMill);
		
		Workingstep ws2 = new Workingstep(ranhura, this.face);
		ws2.setOperation(bsfm1);
		ws2.setId("WS 2");
		ws2.setCondicoesUsinagem(cu2);
		ws2.setFerramenta(ballEndMill);
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addGeneralProfileSlot()
	{
		// ---- feature definition ----
		RanhuraPerfilBezier ranhura = new RanhuraPerfilBezier();
		ranhura.setNome("Ranhura com perfil curva de Bezier");
		ranhura.setPosicao(0, 45, 0);
		ranhura.setComprimento(120);
		ranhura.setEixo(Ranhura.HORIZONTAL);
		//ranhura.setProfundidade(13);
		ranhura.setLargura(28);
		ranhura.setTolerancia(0.075);
		Point3d coordinates = new Point3d(0, 45, 50);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		ranhura.setPosition(position);
		Point3d [] pontosDeControle = new Point3d[5];
		pontosDeControle[0] = new Point3d(0, 50 - 0, 0);
		pontosDeControle[1] = new Point3d(7, 50 - 10, 0);
		pontosDeControle[2] = new Point3d(14, 50 - 30, 0);
		pontosDeControle[3] = new Point3d(21, 50 - 5, 0);
		pontosDeControle[4] = new Point3d(28, 50 - 0, 0);
		ranhura.setPontosDeControle(pontosDeControle);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(10, 40);
		faceMill.setName("Fresa de topo para desbaste");
		faceMill.setCuttingEdgeLength(12);
		faceMill.setOffsetLength(50);
		faceMill.setNumberOfTeeth(4);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		
		EndMill endMill = new EndMill(6, 30);
		endMill.setName("Fresa de topo pre acabamento");
		endMill.setCuttingEdgeLength(10);
		endMill.setOffsetLength(40);
		endMill.setNumberOfTeeth(4);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		BallEndMill ballEndMill = new BallEndMill(10, 35);
		ballEndMill.setName("Fresa esferica para dar formato a ranhura");
		ballEndMill.setCuttingEdgeLength(5);
		ballEndMill.setOffsetLength(40);
		ballEndMill.setNumberOfTeeth(6);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		
		// ---- operations definition ----
		double retractPlane = 10;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Operacao Desbaste", retractPlane);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Operacao semi-acabamento", retractPlane);
		bsfm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm1 = new BottomAndSideFinishMilling("Operacao de acabamento", retractPlane);
		bsfm1.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem cu = new CondicoesDeUsinagem(100, 0.02, 0, 1000, 2, faceMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu1 = new CondicoesDeUsinagem(120, 0.01, 0, 1200, 2, endMill.getDiametroFerramenta() * 0.75);
		
		CondicoesDeUsinagem cu2 = new CondicoesDeUsinagem(130, 0.01, 0, 120, 1, 2);
		
		// ---- WS definition ----
		Workingstep ws0 = new Workingstep(ranhura, this.	face);
		ws0.setOperation(bsrm);
		ws0.setId("WS 0");
		ws0.setCondicoesUsinagem(cu);
		ws0.setFerramenta(faceMill);
		
		Workingstep ws1 = new Workingstep(ranhura, this.face);
		ws1.setOperation(bsfm);
		ws1.setId("WS 1");
		ws1.setCondicoesUsinagem(cu1);
		ws1.setFerramenta(endMill);
		
		Workingstep ws2 = new Workingstep(ranhura, this.face);
		ws2.setOperation(bsfm1);
		ws2.setId("WS 2");
		ws2.setCondicoesUsinagem(cu2);
		ws2.setFerramenta(ballEndMill);
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		wsFace.add(ws0);
		wsFace.add(ws1);
		wsFace.add(ws2);
		
		this.WS.add(wsFace);
		
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addStep() 
	{
		// ---- feature definition ----
		Degrau degrau = new Degrau();
		degrau.setNome("Degrau Teste");
		degrau.setLargura(18);
		degrau.setProfundidade(13.7);
		degrau.setPosicao(0, 0, 0);
		degrau.setEixo(Degrau.VERTICAL);
		degrau.setComprimento(this.face.getLargura());
		degrau.setTolerancia(0.0541);
		Point3d coordinates = new Point3d(degrau.getLargura(), degrau.getComprimento(), this.face.getProfundidadeMaxima() - degrau.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(-1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("degrau placement");
		degrau.setPosition(position);
		
		// ---- tools definition ----
		FaceMill faceMill = new FaceMill(10.0, 62.5);
		faceMill.setCuttingEdgeLength(9.0);
		faceMill.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMill.setNumberOfTeeth(4);
		faceMill.setName("Fresa de topo para desbaste");
		faceMill.setOffsetLength(67);
		
		EndMill endMill = new EndMill(10, 40);
		endMill.setName("Fresa de topo de acabamento");
		endMill.setCuttingEdgeLength(15);
		endMill.setNumberOfTeeth(6);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setOffsetLength(50);
		
		// ---- operations definition ----
		double retractPlane = 11.56;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Operacao Desbaste", retractPlane);
		bsrm.setAllowanceBottom(0.56);
		bsrm.setAllowanceSide(0.62);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Operacao Acamabento", retractPlane);
		bsfm.setCoolant(false);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem condicoesDeUsinagem1 = new CondicoesDeUsinagem(100,
				0.1, 5.0, faceMill.getDiametroFerramenta() * 0.75, 0, 0, 10, false, faceMill);

		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(120.0, 0.04, 20.0, 1500, 2.0, endMill.getDiametroFerramenta() * 0.6);
		
		Workingstep workingstep = new Workingstep(degrau, this.face);
		workingstep.setId("WS Desbaste do degrau");
		workingstep.setFerramenta(faceMill);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem1);
		workingstep.setOperation(bsrm);
		workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));

		Workingstep workingstep2 = new Workingstep(degrau, this.face);
		workingstep2.setId("WS Acabamento do degrau");
		workingstep2.setFerramenta(endMill);
		workingstep2.setOperation(bsfm);
		workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
		
		this.WS = new Vector();
		Vector wsFace = new Vector();

		wsFace.add(workingstep);
		wsFace.add(workingstep2);
		this.WS.add(wsFace);
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addOneHoleOneSlotOneStepOnePocket()
	{
		// --- creating a hole ---
		FuroBaseConica furo = new FuroBaseConica();
		furo.setPosicao(20, 10, 0);
		furo.setProfundidade(10);
		furo.setRaio(6.2);
		furo.setTipAngle(70);
		furo.setNome("Furo de Base Conica 1");
		Point3d coordinatesFuro = new Point3d(15, 20, 50);
		ArrayList<Double> axisFeature = new ArrayList<Double>();
		ArrayList<Double> refDirectionFeature = new ArrayList<Double>();
		axisFeature.add(0.0);
		axisFeature.add(0.0);
		axisFeature.add(1.0);
		refDirectionFeature.add(1.0);
		refDirectionFeature.add(0.0);
		refDirectionFeature.add(0.0);
		Axis2Placement3D positionFuro = new Axis2Placement3D(coordinatesFuro, axisFeature, refDirectionFeature);
		furo.setPosition(positionFuro);
		furo.setTolerancia(0.5);
		
		// --- setting its tool ---
		CenterDrill centerDrill = new CenterDrill(5, 10);
		centerDrill.setName("broca de centro");
		centerDrill.setCuttingEdgeLength(5);
		centerDrill.setOffsetLength(30);
		centerDrill.setHandOfCut(CenterDrill.RIGHT_HAND_OF_CUT);
		centerDrill.setNumberOfTeeth(2);
		centerDrill.setToolTipHalfAngle(20);
		
		TwistDrill broca = new TwistDrill(12.4, 79, 20);
		broca.setCuttingEdgeLength(50.0);
		broca.setName("Broca para desbaste");
		broca.setOffsetLength(80);
		broca.setHandOfCut(TwistDrill.RIGHT_HAND_OF_CUT);
		broca.setNumberOfTeeth(2);
		
		// ---- setting its operations ----
		double retractPlane = 14.4;
		CenterDrilling centerDrilling = new CenterDrilling("Op. furacao de centro", retractPlane);
		centerDrilling.setCoolant(false);
		centerDrilling.setCuttingDepth(5);
		
		Drilling drilling = new Drilling("Op. furacao", retractPlane);
		drilling.setCoolant(true);
		
		// ---- setting its cutting conditions ---
		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100, 0.045, 2, 0, 0, 0, 900 / 60, false, broca);
		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(120, 0.04, 10, 1200, 0, 0);
		
		// ---- creating workingsteps ----
		Workingstep workingstepFuro = new Workingstep(furo, this.face);
		workingstepFuro.setFerramenta(centerDrill);
		workingstepFuro.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstepFuro.setOperation(centerDrilling);
		workingstepFuro.setId("WS centrar");
//		workingstep.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstep));
		
		Workingstep workingstepFuro2 = new Workingstep(furo, this.face);
		workingstepFuro2.setFerramenta(broca);
		workingstepFuro2.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstepFuro2.setOperation(drilling);
		workingstepFuro2.setId("WS desbaste furo");
//		workingstepA.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepA));
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		
//		wsFace.add(workingstep);
//		wsFace.add(workingstepA);
		
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
		faceMill.setName("Facemill");
		faceMill.setNumberOfTeeth(5);
		faceMill.setOffsetLength(72);
		
		EndMill endMill = new EndMill(8, 40);
		endMill.setName("EndMill");
		endMill.setCuttingEdgeLength(8);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setNumberOfTeeth(4);
		endMill.setOffsetLength(50);

		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmRanhura = new BottomAndSideRoughMilling("Op. desbaste ranhura", retractPlane);
		bsrmRanhura.setCoolant(true);
		BottomAndSideFinishMilling bsfmRanhura = new BottomAndSideFinishMilling("Op. acabamento ranhura", retractPlane);
		
		// ---- cutting conditions ----
		CondicoesDeUsinagem condicoesDeUsinagemRanhura = new CondicoesDeUsinagem(100, 0.1, 5.0, 10.0 * 0.75, 0, 0, 10, false, faceMill);
		CondicoesDeUsinagem condicoesDeUsinagemRanhura2 = new CondicoesDeUsinagem(150, 0.03, 15, 1500, 2, 6);
		
		Workingstep workingstepRanhura = new Workingstep(ranhura, this.face);
		workingstepRanhura.setFerramenta(faceMill);
		workingstepRanhura.setCondicoesUsinagem(condicoesDeUsinagemRanhura);
		workingstepRanhura.setOperation(bsrmRanhura);
		workingstepRanhura.setId("WS ranhura");
//		workingstepRanhura.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepRanhura));
		
		Workingstep workingstepRanhura2 = new Workingstep(ranhura, this.face);
		workingstepRanhura2.setFerramenta(faceMill);
		workingstepRanhura2.setCondicoesUsinagem(condicoesDeUsinagemRanhura);
		workingstepRanhura2.setOperation(bsfmRanhura);
		workingstepRanhura2.setId("WS ranhura 2");
		
//		workingstepRanhura2.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepRanhura2));
		
//		wsFace.add(workingstepRanhura);
//		wsFace.add(workingstepRanhura2);
		
		// ---- creating a vertical step ----
		Degrau degrau = new Degrau();
		degrau.setLargura(33.0);
		degrau.setProfundidade(12.0);
		degrau.setPosicao(87.0, 0, 0);
		degrau.setEixo(Degrau.VERTICAL);
		degrau.setNome("Degrau vertical 1");
		Point3d coordinatesDegrau = new Point3d(87, 0, 50);
		Axis2Placement3D positionDegrau = new Axis2Placement3D(coordinatesDegrau, axisFeature, refDirectionFeature);
		degrau.setPosition(positionDegrau);
		degrau.setTolerancia(0.485);
		
		// ---- tools definition ----
		FaceMill fmDegrau = new FaceMill(15.0, 62.5);
		fmDegrau.setCuttingEdgeLength(9.0);
		fmDegrau.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		fmDegrau.setNumberOfTeeth(8);
		fmDegrau.setName("fresa de topo de 15mm");
		fmDegrau.setOffsetLength(70);
		
		EndMill emDegrau = new EndMill(8, 50);
		emDegrau.setName("fresa de topo acabamento 10 mm");
		emDegrau.setCuttingEdgeLength(6.0);
		emDegrau.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		emDegrau.setNumberOfTeeth(4);
		emDegrau.setOffsetLength(60);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem condicoesDeUsinagemDegrau = new CondicoesDeUsinagem(100, 0.1, 1.8, 12.0 * 0.75, 0, 0, 10, false, fmDegrau);
		CondicoesDeUsinagem condicoesDeUsinagemDegrau1 = new CondicoesDeUsinagem(150, 0.02, 15, 1200, 2, 7);
		
		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmDegrau = new BottomAndSideRoughMilling("Op desbaste degrau", retractPlane);
		bsrmDegrau.setCoolant(true);
		bsrmDegrau.setAllowanceBottom(0.5);
		bsrmDegrau.setAllowanceSide(0.4);
		
		BottomAndSideFinishMilling bsfmDegrau = new BottomAndSideFinishMilling("Op. acabamento degrau", retractPlane);
		bsfmDegrau.setCoolant(true);
		
		// ---- workingsteps definition ----
		Workingstep workingstepDegrau = new Workingstep(degrau, this.face);
		workingstepDegrau.setId("WS 1 Degrau");
		workingstepDegrau.setFerramenta(fmDegrau);
		workingstepDegrau.setCondicoesUsinagem(condicoesDeUsinagemDegrau);
		workingstepDegrau.setOperation(bsrmDegrau);
//		workingstepDegrau.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepDegrau));
		
		Workingstep workingstepDegrau2 = new Workingstep(degrau, this.face);
		workingstepDegrau2.setFerramenta(fmDegrau);
		workingstepDegrau2.setCondicoesUsinagem(condicoesDeUsinagemDegrau);
		workingstepDegrau2.setId("WS 2 Degrau");
		workingstepDegrau2.setOperation(bsfmDegrau);
//		workingstepDegrau2.setPontos(MapeadoraDeWorkingstep.determinadorDePontos(workingstepDegrau2));
		
//		wsFace.add(workingstepDegrau);
//		wsFace.add(workingstepDegrau2);
		
		// ---- creating a closed pocket ----
		Cavidade cavidade = new Cavidade();
		cavidade.setComprimento(30);
		cavidade.setLargura(40);
		cavidade.setProfundidade(15.0);
		cavidade.setPosicao(30, 40, 0);
		cavidade.setRaio(4.5);
		cavidade.setNome("Cavidade 1");
		Point3d coordinatesCavidade = new Point3d(30 + 15, 40 + 20, 50);
		Axis2Placement3D positionCavidade = new Axis2Placement3D(coordinatesCavidade, axisFeature, refDirectionFeature);
		cavidade.setPosition(positionCavidade);
		cavidade.setTolerancia(0.65);
		
		// ---- tools definition ----
		FaceMill faceMillCavidade = new FaceMill(9.0, 80.00);
		faceMillCavidade.setCuttingEdgeLength(8.0);
		faceMillCavidade.setHandOfCut(Ferramenta.RIGHT_HAND_OF_CUT);
		faceMillCavidade.setNumberOfTeeth(2);
		faceMillCavidade.setName("Fresa de topo 9 mm");
		faceMillCavidade.setOffsetLength(85);
		
		EndMill endMillCavidade = new EndMill(8, 50);
		endMillCavidade.setName("Fresa de topo acabamento 8mm");
		endMillCavidade.setCuttingEdgeLength(20);
		endMillCavidade.setOffsetLength(55);
		endMillCavidade.setNumberOfTeeth(4);
		endMillCavidade.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		
		// ---- operations definition ----
		BottomAndSideRoughMilling bsrmCavidade = new BottomAndSideRoughMilling("Op. desbaste - Cavidade", retractPlane);
		bsrmCavidade.setCoolant(true);
		bsrmCavidade.setAllowanceBottom(0.37);
		bsrmCavidade.setAllowanceSide(0.62);
		
		BottomAndSideFinishMilling bsfmCavidade = new BottomAndSideFinishMilling("Op. acabamento - Cavidade", retractPlane);
		bsfmCavidade.setCoolant(true);
		
		// ---- cutting conditions definition ----
		CondicoesDeUsinagem condicoesDeUsinagemCavidade = new CondicoesDeUsinagem(100, 0.04, 2.3, 12.0 * 0.75, 0, 0, 12, false, faceMillCavidade);
		CondicoesDeUsinagem condicoesDeUsinagemCavidade2 = new CondicoesDeUsinagem(160, 0.01, 12, 1500, 1, 4);
		
		Workingstep workingstepCavidade = new Workingstep(cavidade, this.face);
		workingstepCavidade.setFerramenta(faceMillCavidade);
		workingstepCavidade.setCondicoesUsinagem(condicoesDeUsinagemCavidade);
		workingstepCavidade.setOperation(bsrmCavidade);
		workingstepCavidade.setId("WS 1 - Cavidade");
		workingstepCavidade.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstepCavidade));
		
		Workingstep workingstepCavidade2 = new Workingstep(cavidade, this.face);
		workingstepCavidade2.setFerramenta(endMillCavidade);
		workingstepCavidade2.setCondicoesUsinagem(condicoesDeUsinagemCavidade2);
		workingstepCavidade2.setOperation(bsfmCavidade);
		workingstepCavidade2.setId("WS 2 - Cavidade");
		workingstepCavidade2.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstepCavidade2));
		
//		wsFace.add(workingstepCavidade);
//		wsFace.add(workingstepCavidade2);
		
		wsFace.add(workingstepCavidade);
		wsFace.add(workingstepFuro);
		wsFace.add(workingstepRanhura);
		wsFace.add(workingstepFuro2);
		wsFace.add(workingstepDegrau);
		wsFace.add(workingstepRanhura2);
		wsFace.add(workingstepCavidade2);
		this.WS.add(wsFace);
		
		ArrayList<Feature> arrayList = new ArrayList<Feature>();
		Feature anterior = null;
		
		for (int index = 0; index < this.WS.size(); index++)
		{
			Vector workinstepsFaceTmp = (Vector)this.WS.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
				
				if (anterior == null){
				arrayList.add(wsTmp.getFeature());	
				
				
				if (wsTmp.getFeature().getTipo() == FURO ){
					Furo fTmp = (Furo)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO \n" + "Nome do feature: " + fTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == DEGRAU ){
					Degrau dTmp = (Degrau)wsTmp.getFeature();
					System.out.println("Tipo do feature: DEGRAU \n" + "Nome do feature: " + dTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == RANHURA ){
					Ranhura rTmp = (Ranhura)wsTmp.getFeature();
					System.out.println("Tipo do feature: RANHURA \n" +  "Nome do feature: " + rTmp.getNome());
					}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE ){
					Cavidade cTmp = (Cavidade)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE \n" + "Nome do feature: " + cTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE_FUNDO_ARREDONDADO ){
					CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE_FUNDO_ARREDONDADO \n" + "Nome do feature: " + cfaTmp.getNome());
				}
				
								
				if (wsTmp.getFeature().getTipo() == FURO_BASE_CONICA ){
					FuroBaseConica fbcTmp = (FuroBaseConica)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO_BASE_CONICA \n" + "Nome do feature: " + fbcTmp.getNome());
				}
				
				anterior = wsTmp.getFeature();
				
				}
				
				else{
				if (wsTmp.getFeature().equals(anterior)){System.out.println("Feature igual nao adicionado");}
				else {arrayList.add(wsTmp.getFeature());
				
				if (wsTmp.getFeature().getTipo() == FURO ){
					Furo fTmp = (Furo)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO \n" + "Nome do feature: " + fTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == DEGRAU ){
					Degrau dTmp = (Degrau)wsTmp.getFeature();
					System.out.println("Tipo do feature: DEGRAU \n" + "Nome do feature: " + dTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == RANHURA ){
					Ranhura rTmp = (Ranhura)wsTmp.getFeature();
					System.out.println("Tipo do feature: RANHURA \n" +  "Nome do feature: " + rTmp.getNome());
					}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE ){
					Cavidade cTmp = (Cavidade)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE \n" + "Nome do feature: " + cTmp.getNome());
				}
				
				if (wsTmp.getFeature().getTipo() == CAVIDADE_FUNDO_ARREDONDADO ){
					CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)wsTmp.getFeature();
					System.out.println("Tipo do feature: CAVIDADE_FUNDO_ARREDONDADO \n" + "Nome do feature: " + cfaTmp.getNome());
				}
				
								
				if (wsTmp.getFeature().getTipo() == FURO_BASE_CONICA ){
					FuroBaseConica fbcTmp = (FuroBaseConica)wsTmp.getFeature();
					System.out.println("Tipo do feature: FURO_BASE_CONICA \n" + "Nome do feature: " + fbcTmp.getNome());
				}
				
				}
				
				anterior = wsTmp.getFeature();
				}
				
			}
			
		}
		
		System.out.println("Features n:" +arrayList.size() );
	
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void addBanheira()
	{
		// --- creating a banheira ---
		CavidadeFundoArredondado banheira = new CavidadeFundoArredondado(40, 30, 0, 5.0, 3.5, 40, 60, 10);
		banheira.setNome("Banheira");
		Point3d coordinates = new Point3d(banheira.X + banheira.getComprimento() / 2, banheira.Y + banheira.getLargura() / 2, this.face.getProfundidadeMaxima() - banheira.Z);
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		banheira.setPosition(position);
		banheira.setTolerancia(0.0525);
		
		// --- setting its tool ---
		FaceMill faceMill = new FaceMill(12.4, 60);
		faceMill.setCuttingEdgeLength(50.0);
		faceMill.setName("Fresa de topo - Desbaste");
		faceMill.setNumberOfTeeth(2);
		faceMill.setOffsetLength(70);
		faceMill.setHandOfCut(FaceMill.RIGHT_HAND_OF_CUT);
		
		EndMill endMill = new EndMill(8, 44);
		endMill.setName("Fresa de topo - Acamabento");
		endMill.setCuttingEdgeLength(50);
		endMill.setNumberOfTeeth(4);
		endMill.setHandOfCut(EndMill.RIGHT_HAND_OF_CUT);
		endMill.setOffsetLength(50);
		
		BallEndMill ballEndMill = new BallEndMill(10, 50);
		ballEndMill.setName("Fresa esferica - Acabamento");
		ballEndMill.setCuttingEdgeLength(55);
		ballEndMill.setNumberOfTeeth(4);
		ballEndMill.setHandOfCut(BallEndMill.RIGHT_HAND_OF_CUT);
		ballEndMill.setOffsetLength(52);
		
		// ---- operations definition ----
		double retractPlane = 10.65;
		BottomAndSideRoughMilling bsrm = new BottomAndSideRoughMilling("Op. desbaste", retractPlane);
		bsrm.setAllowanceBottom(0.45);
		bsrm.setAllowanceSide(0.51);
		bsrm.setCoolant(true);
		
		BottomAndSideFinishMilling bsfm = new BottomAndSideFinishMilling("Op. acabamento", retractPlane);
		bsfm.setCoolant(false);
		
		BottomAndSideFinishMilling bsfm1 = new BottomAndSideFinishMilling("Op. para dar forma a banheira", retractPlane);
		bsfm1.setCoolant(true);
		
		// --- setting its cutting conditions ---
		CondicoesDeUsinagem condicoesDeUsinagem = new CondicoesDeUsinagem(100, 0.045, 2, 0, 0, 0, 900 / 60, false, faceMill);
		
		CondicoesDeUsinagem condicoesDeUsinagem2 = new CondicoesDeUsinagem(170, 0.03, 10, 1200, 1, endMill.getDiametroFerramenta() * 0.5);
		
		CondicoesDeUsinagem condicoesDeUsinagem3 = new CondicoesDeUsinagem(150, 0.02, 10, 1500, 1, ballEndMill.getDiametroFerramenta() * 0.3);
		
		// ---- WS definition ----
		Workingstep workingstep = new Workingstep(banheira, this.face);
		workingstep.setId("WS 1  Banheira");
		workingstep.setFerramenta(faceMill);
		workingstep.setCondicoesUsinagem(condicoesDeUsinagem);
		workingstep.setOperation(bsrm);
		workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
		workingstep.setTipo(Workingstep.DESBASTE);
		
		Workingstep workingstep2 = new Workingstep(banheira, this.face);
		workingstep2.setId("WS 2 Banheira");
		workingstep2.setFerramenta(endMill);
		workingstep2.setCondicoesUsinagem(condicoesDeUsinagem2);
		workingstep2.setOperation(bsfm);
		
		Workingstep workingstep3 = new Workingstep(banheira, this.face);
		workingstep3.setId("WS 3 Banheira");
		workingstep3.setFerramenta(ballEndMill);
		workingstep3.setCondicoesUsinagem(condicoesDeUsinagem3);
		workingstep3.setOperation(bsfm1);
		
		Vector wsFace = new Vector();
		this.WS = new Vector();
		
		wsFace.add(workingstep);
		wsFace.add(workingstep2);
		wsFace.add(workingstep3);
		
		this.WS.add(wsFace);
		try {
			this.stepNcProject = new StepNcProject(this.WS, this.projeto);
			System.out.println(this.stepNcProject.createSTEP21File());
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		}
	}
	@After
	public void close() {
		try {
			this.stepNcProject.closeSession();
		} catch (SdaiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
