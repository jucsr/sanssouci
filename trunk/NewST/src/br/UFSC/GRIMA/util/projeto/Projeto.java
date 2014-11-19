package br.UFSC.GRIMA.util.projeto;

import java.awt.Container;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;



/**
 * Classe que possui todas as informacoes relativas ao projeto do
 * usuario.
 * 
 * Esta classe eh armazenada no banco de dados.
 * 
 * @author roman
 *
 */
public class Projeto implements Serializable{
	
//	private Maquina maquina;
	private Vector ordensDeFabricacao = null;
	private transient Connection conn;
	private transient Statement statement;
	private Bloco bloco;
	private DadosDeProjeto dadosDeProjeto;
	private Vector<Vector<Workingstep>> workingsteps = new Vector<Vector<Workingstep>>();
	private boolean cappDone;
	private boolean toolsDone;
	
	private ArrayList<CenterDrill> centerDrills = new ArrayList<CenterDrill>();
	private ArrayList<TwistDrill> twistDrills = new ArrayList<TwistDrill>();
	private ArrayList<FaceMill> faceMills = new ArrayList<FaceMill>();
	private ArrayList<EndMill> endMills = new ArrayList<EndMill>();
	private ArrayList<BallEndMill> ballEndMills = new ArrayList<BallEndMill>();
	private ArrayList<BullnoseEndMill> bullnoseEndMills = new ArrayList<BullnoseEndMill>();
	private ArrayList<BoringTool> boringTools = new ArrayList<BoringTool>();
	private ArrayList<Reamer> reamers = new ArrayList<Reamer>();
	
	private ArrayList<ArrayList> allTools;
	public ArrayList<ArrayList<ArrayList<Point3d>>> setupsArray;

	public Projeto(){
		
	}
	
	public Projeto(Maquina maquina, Bloco bloco, DadosDeProjeto dados){
		this.bloco = bloco;
//		this.maquina = maquina;
		this.dadosDeProjeto = dados;
	}
	public Projeto(Bloco bloco, DadosDeProjeto dados)
	{
		this.bloco = bloco;
		this.dadosDeProjeto = dados;
	}
	
	public Projeto(DadosDeProjeto dadosDeProjeto, Bloco bloco,
			ArrayList<ArrayList> tools) {

		this.dadosDeProjeto = dadosDeProjeto;
		this.bloco = bloco;
		this.setAllTools(tools);
		
	}

	public JTree getJTree()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode
			("Project: " + dadosDeProjeto.getProjectName());
//		root.add(this.maquina.getNodo());
		root.add(this.bloco.getNodo1());
		root.add(this.bloco.getNodo());
		
		return new JTree(root);
	}

	public void setAllToolss() {

		allTools = new ArrayList<ArrayList>();
		
		allTools.add(0,centerDrills);
		allTools.add(1,twistDrills);
		allTools.add(2,faceMills);
		allTools.add(3,endMills);
		allTools.add(4,ballEndMills);
		allTools.add(5,bullnoseEndMills);
		allTools.add(6,boringTools);
		allTools.add(7,reamers);

	}	

	public ArrayList<ArrayList> getAllTools() {
		return allTools;
	}
	
	public void setAllTools(ArrayList<ArrayList> tools) {

		this.setCenterDrills(tools.get(0));
		this.setTwistDrills(tools.get(1));
		this.setFaceMills(tools.get(2));
		this.setEndMills(tools.get(3));
		this.setBallEndMills(tools.get(4));
		this.setBullnoseEndMills(tools.get(5));
		this.setBoringTools(tools.get(6));
		this.setReamers(tools.get(7));

	}
	
	public void setAllToolsFromWs(Vector<Vector<Workingstep>> allWorkingsteps) {

		for(int i=0;i<allWorkingsteps.size();i++){
			
			for(int j=0;j<allWorkingsteps.get(i).size();j++){
				
				this.addFerramenta(allWorkingsteps.get(i).get(j).getFerramenta());
				
			}
			
		}
		
	}
	
	
	public DefaultMutableTreeNode getNodosSetups(DefaultMutableTreeNode root, boolean isWorkplan) {
		
		for(int i = 0; i < this.workingsteps.size();i++){//faces - setups

			DefaultMutableTreeNode nodoFaceAtual = new DefaultMutableTreeNode("Setup - " + Face.getStringFace(i) + " :");
			
			DefaultMutableTreeNode nodoDesbasteTmp = null;
			DefaultMutableTreeNode nodoAcabamentoTmp = null;
			
			if(!isWorkplan){
				nodoDesbasteTmp = new DefaultMutableTreeNode("WS-Roughing:");
				nodoAcabamentoTmp = new DefaultMutableTreeNode("WS-Finishing:");

				nodoFaceAtual.add(nodoDesbasteTmp);
				nodoFaceAtual.add(nodoAcabamentoTmp);
			}
			
			root.add(nodoFaceAtual);
			
			Vector<Workingstep> wss = this.workingsteps.get(i); 

			for(int j = 0; j < wss.size(); j++){//wss na face atual
				
				DefaultMutableTreeNode nodoFeatureTmp = new DefaultMutableTreeNode("Its Feature:");
				DefaultMutableTreeNode nodoOperationTmp = new DefaultMutableTreeNode("Its Operation:");
				DefaultMutableTreeNode nodoMachinningStrategy = new DefaultMutableTreeNode("Its Machinning Strategy:");
				DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Its Tool:");
				DefaultMutableTreeNode nodoCondicoesTmp = new DefaultMutableTreeNode("Its Technology:");
				
				Workingstep wsTmp = wss.get(j);
				
				DefaultMutableTreeNode nodoWsTmp = new DefaultMutableTreeNode("WS - "+ j + "  : " + wsTmp.getId());

				if(!isWorkplan){
					
					if(wsTmp.getTipo()==Workingstep.DESBASTE){

						nodoDesbasteTmp.add(nodoWsTmp);


					}else if(wsTmp.getTipo()==Workingstep.ACABAMENTO){

						nodoAcabamentoTmp.add(nodoWsTmp);

					}else{

						System.out.println("Tipo de workingstep nao reconhecido (tem que ser desbaste ou acabamento)! Tipo: " + wsTmp.getTipo());
					}
					
				}else{
					nodoFaceAtual.add(nodoWsTmp);
				}

				nodoWsTmp.add(nodoFeatureTmp);
				nodoWsTmp.add(nodoOperationTmp);
				nodoWsTmp.add(nodoFerramentaTmp);
				nodoWsTmp.add(nodoCondicoesTmp);
				
				MachiningOperation operationTmp = wsTmp.getOperation();
				Ferramenta ferrTmp = wsTmp.getFerramenta();
				CondicoesDeUsinagem condTmp = wsTmp.getCondicoesUsinagem();
				
				nodoFeatureTmp.add(new DefaultMutableTreeNode("Name : " + wsTmp.getFeature().getNome()));
				//Criando atributos da movimentacao trochoidal
				if(operationTmp.getMachiningStrategy().getClass() == TrochoidalAndContourParallelStrategy.class)
				{
					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Radius: " + ((TrochoidalAndContourParallelStrategy)operationTmp.getMachiningStrategy()).getTrochoidalRadius()));
					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Sense: " + ((TrochoidalAndContourParallelStrategy)operationTmp.getMachiningStrategy()).getTrochoidalSense()));
					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Feed Rate: " + ((TrochoidalAndContourParallelStrategy)operationTmp.getMachiningStrategy()).getTrochoidalFeedRate()));
				}
				else if(operationTmp.getMachiningStrategy().getClass() == ContourParallel.class)
				{
//					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Radius: " + ((ContourParallel)operationTmp.getMachiningStrategy()).));
//					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Sense: " + ((ContourParallel)operationTmp.getMachiningStrategy()).getTrochoidalSense()));
//					nodoMachinningStrategy.add(new DefaultMutableTreeNode("Trochoidal Feed Rate: " + ((ContourParallel)operationTmp.getMachiningStrategy()).getTrochoidalFeedRate()));
				
				}
				nodoOperationTmp.add(new DefaultMutableTreeNode("Type : " + operationTmp.getId()));
				nodoOperationTmp.add(new DefaultMutableTreeNode("Coolant : " + operationTmp.isCoolant()));
				nodoOperationTmp.add(new DefaultMutableTreeNode("Retract Plane : " + operationTmp.getRetractPlane()));
				nodoOperationTmp.add(nodoMachinningStrategy);
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
					nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + ferrTmp.getEdgeRadius() + " mm"));
					nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + ferrTmp.getEdgeCenterVertical() + " mm"));
					nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + ferrTmp.getEdgeCenterHorizontal() + " mm"));				
				}
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("vc : " + condTmp.getVc() + " m/min"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("f : " + condTmp.getF() + " mm/rot"));
				nodoCondicoesTmp.add(new DefaultMutableTreeNode("n : " + (int)condTmp.getN() + " rpm"));
				if(operationTmp.getClass() == BottomAndSideFinishMilling.class || operationTmp.getClass() == BottomAndSideRoughMilling.class || operationTmp.getClass() == FreeformOperation.class || operationTmp.getClass() == PlaneRoughMilling.class || operationTmp.getClass() == PlaneFinishMilling.class)
				{	
					nodoCondicoesTmp.add(new DefaultMutableTreeNode("ap : " + condTmp.getAp() + " mm"));
					nodoCondicoesTmp.add(new DefaultMutableTreeNode("ae : " + condTmp.getAe() + " mm"));
				}
				
			}
		}
		
		return root;
	}
	
	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}
	
	public Bloco getBloco(){
		return this.bloco;
	}
	
//	public Maquina getMaquina(){
//		return this.maquina;
//	}
	
	public Vector getOrdensDeFabricacao(){
		return this.ordensDeFabricacao;
	}
	
	public DadosDeProjeto getDadosDeProjeto(){
		return this.dadosDeProjeto;
	}
	
	public void addOrdemDeFabricacao(OrdemDeFabricacao ordem){
		if(this.ordensDeFabricacao == null)
			this.ordensDeFabricacao = new Vector();
		
		this.ordensDeFabricacao.add(ordem);
	}
	
	public void addFerramenta(Ferramenta ferramenta){
		
		boolean jaExiste = false;

		if(ferramenta.getClass()==CenterDrill.class){
			
			for(int i=0; i<this.centerDrills.size();i++){
				if(ferramenta.equals(this.centerDrills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addCenterDrill((CenterDrill)ferramenta);
			
		}else if(ferramenta.getClass()==TwistDrill.class){
			
			for(int i=0; i<this.twistDrills.size();i++){
				if(ferramenta.equals(this.twistDrills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addTwistDrill((TwistDrill)ferramenta);
			
		}else if(ferramenta.getClass()==FaceMill.class){
			
			for(int i=0; i<this.faceMills.size();i++){
				if(ferramenta.equals(this.faceMills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addFaceMill((FaceMill)ferramenta);
			
		}else if(ferramenta.getClass()==EndMill.class){
			
			for(int i=0; i<this.endMills.size();i++){
				if(ferramenta.equals(this.endMills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addEndMill((EndMill)ferramenta);
			
		}else if(ferramenta.getClass()==BallEndMill.class){
			
			for(int i=0; i<this.ballEndMills.size();i++){
				if(ferramenta.equals(this.ballEndMills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addBallEndMill((BallEndMill)ferramenta);
			
		}else if(ferramenta.getClass()==BullnoseEndMill.class){
			
			for(int i=0; i<this.bullnoseEndMills.size();i++){
				if(ferramenta.equals(this.bullnoseEndMills.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addBullnoseEndMill((BullnoseEndMill)ferramenta);
			
		}else if(ferramenta.getClass()==Reamer.class){
			
			for(int i=0; i<this.reamers.size();i++){
				if(ferramenta.equals(this.reamers.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addReamer((Reamer)ferramenta);
			
		}else if(ferramenta.getClass()==BoringTool.class){
			
			for(int i=0; i<this.boringTools.size();i++){
				if(ferramenta.equals(this.boringTools.get(i))){
					jaExiste = true;
				}
			}
			if(!jaExiste)
			this.addBoringTool((BoringTool)ferramenta);
			
		}else{
			System.out.println("CLASSE DE FERRAMENTA DESCONHECIDA! (Projeto/addFerramenta)");
		}
		
	}
	
	public ArrayList<CenterDrill> getCenterDrills() {
		return centerDrills;
	}

	public void setCenterDrills(ArrayList<CenterDrill> centerDrills) {
		this.centerDrills = centerDrills;
	}

	public void addCenterDrill(CenterDrill centerDrill){
		
		this.centerDrills.add(centerDrill);
		
	}
	
	public void setTwistDrills(ArrayList<TwistDrill> twistDrills)
	{
		this.twistDrills = twistDrills;
	}

	public ArrayList<TwistDrill> getTwistDrills() {
		return twistDrills;
	}
	
	public void addTwistDrill(TwistDrill twistDrill){
		
		this.twistDrills.add(twistDrill);
		
	}
	
	public ArrayList<FaceMill> getFaceMills() {
		return faceMills;
	}

	public void setFaceMills(ArrayList<FaceMill> faceMills) {
		this.faceMills = faceMills;
	}

	public void addFaceMill(FaceMill faceMill){
		
		this.faceMills.add(faceMill);
		
	}
	
	public ArrayList<EndMill> getEndMills() {
		return endMills;
	}

	public void setEndMills(ArrayList<EndMill> endMills) {
		this.endMills = endMills;
	}

	public void addEndMill(EndMill endMill){
		
		this.endMills.add(endMill);
		
	}

	public ArrayList<BallEndMill> getBallEndMills() {
		return ballEndMills;
	}

	public void setBallEndMills(ArrayList<BallEndMill> ballEndMills) {
		this.ballEndMills = ballEndMills;
	}

	public void addBallEndMill(BallEndMill ballEndMill){
		
		this.ballEndMills.add(ballEndMill);
		
	}
	
	public ArrayList<BullnoseEndMill> getBullnoseEndMills() {
		return bullnoseEndMills;
	}

	public void setBullnoseEndMills(ArrayList<BullnoseEndMill> bullnoseEndMills) {
		this.bullnoseEndMills = bullnoseEndMills;
	}

	public void addBullnoseEndMill(BullnoseEndMill bullnoseEndMill){
		
		this.bullnoseEndMills.add(bullnoseEndMill);
		
	}
	
	public ArrayList<BoringTool> getBoringTools() {
		return boringTools;
	}

	public void setBoringTools(ArrayList<BoringTool> boringTools) {
		this.boringTools = boringTools;
	}

	public void addBoringTool(BoringTool boringTool){
		
		this.boringTools.add(boringTool);
		
	}

	public ArrayList<Reamer> getReamers() {
		return reamers;
	}

	public void setReamers(ArrayList<Reamer> reamers) {
		this.reamers = reamers;
	}
	
	public void addReamer(Reamer reamer){
		
		this.reamers.add(reamer);
		
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection connection) {
		this.conn = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}


	public Vector<Vector<Workingstep>> getWorkingsteps() {
		return workingsteps;
	}

	public void setWorkingsteps(Vector<Vector<Workingstep>> workingsteps) {
		this.workingsteps = workingsteps;
	}

	public void setOrdensDeFabricacao(Vector ordensDeFabricacao) {
		this.ordensDeFabricacao = ordensDeFabricacao;
	}

	public void setDadosDeProjeto(DadosDeProjeto dadosDeProjeto) {
		this.dadosDeProjeto = dadosDeProjeto;
	}

	public boolean isCappDone() {
		return cappDone;
	}

	public void setCappDone(boolean cappDone) {
		this.cappDone = cappDone;
	}

	public boolean isToolsDone() {
		return toolsDone;
	}

	public void setToolsDone(boolean toolsDone) {
		this.toolsDone = toolsDone;
	}

	public ArrayList<ArrayList<ArrayList<Point3d>>> getSetupsArray() {
		return setupsArray;
	}

	public void setSetupsArray(ArrayList<ArrayList<ArrayList<Point3d>>> setupsArray) {
		this.setupsArray = setupsArray;
	}


}