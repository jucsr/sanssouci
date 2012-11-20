package br.UFSC.GRIMA.entidades.features;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import jsdai.SCombined_schema.AMachining_operation;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public abstract class Feature implements Serializable {
	
	public final static int FURO = 0;
	public final static int DEGRAU = 1;
	public final static int RANHURA = 2;
	public final static int CAVIDADE = 3;
	public final static int CAVIDADE_FUNDO_ARREDONDADO = 4;
	public final static int FURO_BASE_CONICA = 5;
	public final static int BOSS = 6;
	public final static int REGION = 7;
	public final static int CAVIDADE_PERFIL_GERAL = 8;

	public final static double LIMITE_TOLERANCIA =  0.01;
	public final static double LIMITE_RUGOSIDADE = 0.01;
	public static double LIMITE_DESBASTE = 0.5;
	public int tipo = 0;
	public double X = 0.0, Y = 0, Z = 0.0;// ponto de referencia para criar
	// qualquer
	// br.UFSC.GRIMA.feature(FURO,
	// DEGRAU, RANHURA,CAVIDADE)
	public Feature featureMae = null;
	private Feature featurePrecedente;
	public Vector featuresAnteriores = null;
	private Face face = null;
	private int indice = 0;
	public ArrayList<Feature> itsSons = new ArrayList<Feature>();
//	private ArrayList<Point3d> pontosDeTrajetoria = null;
//	private Vector trajetoriasDeMovimentacao;
	private Vector<Workingstep> workingsteps = new Vector<Workingstep>();

	private double tolerancia;
	private double rugosidade;
	private boolean acabamento = false;
	private String nome = "";
	
	private Axis2Placement3D position;
	private transient AMachining_operation operations;
	
	public Feature(int tipo) {
		this.setTipo(tipo);
	}

//	public ArrayList<Point3d> getPontosDeTrajetoria(double x, double y, double z) {
//		if (pontosDeTrajetoria != null)
//			return pontosDeTrajetoria;
//
//		double lowestDistance = 0;
//		int choosemIndex = 0;
//		for (int i = 0; i < trajetoriasDeMovimentacao.size(); i++) {
//			Vector trajetoriaTmp = (Vector) trajetoriasDeMovimentacao.get(i);
//			Ponto p = (Ponto) trajetoriaTmp.get(0);
//			double tmpDistance = Math.sqrt( (x - p.getX()) * (x - p.getX())
//					+ (y - p.getY()) * (y - p.getY()) + (z - p.getZ())
//					* (z - p.getZ()));
//
//			if (lowestDistance == 0) {
//				lowestDistance = tmpDistance;
//				choosemIndex = i;
//			} else if (lowestDistance > tmpDistance) {
//				lowestDistance = tmpDistance;
//				choosemIndex = i;
//			}
//		}
//		Vector answer = (Vector) trajetoriasDeMovimentacao.get(choosemIndex);
//
//		pontosDeTrajetoria = Transformer.transformVectorToArray(answer);
//		return pontosDeTrajetoria;
//	}
//
//	public void setPontosDeTrajetoria(ArrayList<Point3d> pontosDeTrajetoria) {
//		this.pontosDeTrajetoria = pontosDeTrajetoria;
//	}
	public abstract DefaultMutableTreeNode getNodo();
	
	public DefaultMutableTreeNode getNodoWorkingSteps(DefaultMutableTreeNode root) {

		
		
		DefaultMutableTreeNode nodoWsTmpMae = new DefaultMutableTreeNode("Workingsteps: ");
		root.add(nodoWsTmpMae);
		
		DefaultMutableTreeNode nodoDesbasteTmp = new DefaultMutableTreeNode("WS-Roughing:");
		DefaultMutableTreeNode nodoAcabamentoTmp = new DefaultMutableTreeNode("WS-Finishing:");

		nodoWsTmpMae.add(nodoDesbasteTmp);
		nodoWsTmpMae.add(nodoAcabamentoTmp);

		for(int j = 0; j < this.workingsteps.size(); j++){

			DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
			DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");

			Workingstep wsTmp = this.workingsteps.get(j);
			
//			DefaultMutableTreeNode nodoWsTmp = new DefaultMutableTreeNode("WS - "+ j + " : " + wsTmp.getId());
			DefaultMutableTreeNode nodoWsTmp = new DefaultMutableTreeNode("WS : " + wsTmp.getId());
			
			if(wsTmp.getTipo()==Workingstep.DESBASTE){

				nodoDesbasteTmp.add(nodoWsTmp);


			}else if(wsTmp.getTipo()==Workingstep.ACABAMENTO){

				nodoAcabamentoTmp.add(nodoWsTmp);

			}else{

				System.out.println("Tipo de workingstep nao reconhecido (tem que ser desbaste ou acabamento)! Tipo: " + wsTmp.getTipo());
			}

			nodoWsTmp.add(nodoOperationTmp);
			nodoWsTmp.add(nodoFerramentaTmp);
			nodoWsTmp.add(nodoCondicoesTmp);

			MachiningOperation operationTmp = wsTmp.getOperation();
			Ferramenta ferrTmp = wsTmp.getFerramenta();
			CondicoesDeUsinagem condTmp = wsTmp.getCondicoesUsinagem();

			nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
			nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));

			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + ferrTmp.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Name : " + ferrTmp.getName()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + ferrTmp.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + ferrTmp.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + ferrTmp.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + ferrTmp.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + ferrTmp.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + ferrTmp.getMaterial()));
			if(ferrTmp.getClass() == CenterDrill.class || ferrTmp.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + ferrTmp.getToolTipHalfAngle() + " °"));
			} else if (ferrTmp.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
			} else if (ferrTmp.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge center vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge center horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
			}
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("Vc : " + condTmp.getVc() + " m/min"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
			nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + condTmp.getN() + " rpm"));
			if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
			{	
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
			}

		}

		return root;
	}
	
	
	public boolean setTipo(int tipo) {
		if (tipo >= 0 && tipo <= 8) {
			this.tipo = tipo;
			return true;
		} else
			return false;
	}

	public void setPosicao(double X, double Y, double Z) {// reemplazei os "pos"
		// por X,Y,Z
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}

	public double getPosicaoX() {
		return this.X;
	}

	public double getPosicaoY() {
		return this.Y;
	}

	public double getPosicaoZ() {

		return this.Z;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getIndice() {
		return this.indice;
	}

	public Vector getFeaturesAnteriores() {
		return featuresAnteriores;
	}

	public void setFeaturesAnteriores(Vector featuresAnteriores) {
		this.featuresAnteriores = featuresAnteriores;
	}

//	public void setTrajetoriasDeMovimentacao(Vector trajetorias) {
//		this.trajetoriasDeMovimentacao = trajetorias;
//	}
//
//	public Vector getTrajetoriasDeMovimentacao() {
//		return this.trajetoriasDeMovimentacao;
//	}

	public double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public double getRugosidade() {
		return rugosidade;
	}

	public void setRugosidade(double rugosidade) {
		this.rugosidade = rugosidade;
	}

	public Vector<Workingstep> getWorkingsteps() {
		return workingsteps;
	}
	
	public void setWorkingsteps(Vector<Workingstep> workingsteps) {
		this.workingsteps = workingsteps;
	}
	
	public boolean isAcabamento() {
		return acabamento;
	}
	
	public void setAcabamento(boolean acabamento) {
		this.acabamento = acabamento;
	}
	
	public String toString() {
		String saida = this.getTipoString() + " (" + this.getIndice() + ")";
		
		return saida;
	}
	
	public String getTipoString() {
		switch (this.tipo) {
		case Feature.FURO:
			return "Furo";
		case Feature.DEGRAU:
			return "Degrau";
		case Feature.RANHURA:
			return "Ranhura";
		case Feature.CAVIDADE:
			return "Cavidade";
		case Feature.CAVIDADE_FUNDO_ARREDONDADO:
			return "Cavidade fundo arredondado";
		case Feature.FURO_BASE_CONICA:
			return "Furo com base conica";
		case Feature.BOSS:
			return "Boss";
		case Feature.REGION:
			return "Region";
		default:
			return "";
		}
	}

	public String getDados1() {
		String saida = "Feature ---------\n";
		saida += this.toString();

		if (this.featureMae != null) {
			saida += "\tMae: " + this.featureMae.toString();
		}
		if (this.featuresAnteriores != null) {
			for (int i = 0; i < this.featuresAnteriores.size(); i++) {
				saida += "\tAnterior"
						+ i
						+ ": "
						+ ((Feature) this.featuresAnteriores.elementAt(i))
								.toString();
			}
		}
		saida += "\n";

		return saida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Axis2Placement3D getPosition() {
		return position;
	}

	public void setPosition(Axis2Placement3D position) {
		this.position = position;
	}

	public AMachining_operation getOperations() {
		return operations;
	}

	public void setOperations(AMachining_operation operations) {
		this.operations = operations;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public Feature getFeaturePrecedente() {
		return featurePrecedente;
	}

	public void setFeaturePrecedente(Feature featurePrecedente) {
		this.featurePrecedente = featurePrecedente;
	}


}
