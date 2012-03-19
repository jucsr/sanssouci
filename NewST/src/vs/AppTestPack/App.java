package vs.AppTestPack;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JApplet;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.PontosDeApoio;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class App extends JApplet {

	public Bloco bloco = null;
	public DadosDeProjeto dadosDeProjeto = null;
	public Projeto projAux = null;
	
	public Vector WS;
	public Face face = null;
	
	
	public void init()
	{
		
		
		//Cria um bloco 50, 50, 50. So para testes.
		
		this.bloco = new Bloco(50, 50, 50);
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

		this.projAux = new Projeto(null, bloco, dadosDeProjeto);

		
		//Sao criados workingsteps tambem (Para obter os features) para teste:
		
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
				
				
				wsFace.add(workingstep1Furo1);
				wsFace.add(workingstep2Furo1);
				wsFace.add(workingstep3Furo1);
				wsFace.add(workingstep4Furo1);
				wsFace.add(workingstep5Furo1);
				
				Vector<Feature> vecFeat = new Vector<Feature>();
				
				//vecFeat.add(furo1);
				
				//Face faxeXY = (Face)projAux.getBloco().faces.get(0);
				
				((Face)projAux.getBloco().faces.get(0)).addFeature(furo1);
				
				this.WS.add(wsFace);
				
//		PontosDeApoio pDeApoioFrame = new PontosDeApoio(projAux);
//		
//		System.out.println("");
//		
//		pDeApoioFrame.setVisible(true);
		 
		    
	}
	
}
