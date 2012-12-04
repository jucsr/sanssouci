package br.UFSC.GRIMA.entidades.ferramentas;

import java.io.Serializable;

import br.UFSC.GRIMA.entidades.Material;

public class Ferramenta implements Serializable
{
	public static final int NEUTRAL_HAND_OF_CUT = 3;
	public static final int RIGHT_HAND_OF_CUT = 2;
	public static final int LEFT_HAND_OF_CUT = 1;
	public static final int P = 4, M = 5, K = 6, S = 7, H = 8, N = 9;
	private String name = "";
	private double dm;
	private double profundidadeMaxima;
	private double diametro;
	private double edgeRadius;
	private double edgeCenterVertical;
	private double edgeCenterHorizontal;
	private double toolTipHalfAngle;
	private double toolCircunferenceAngle;
	private double cuttingEdgeLength; 
	private double offSetLength; // distancia desde a ponta da ferramenta até onde comeca a montagem
	private int numberOfTeeth = 2;
	private int handOfCut;
	private String material;
	private Material materialFerramenta = null;
	private int materialClasse;
	private double rugosidade;
	private double tolerancia;
	
	public Ferramenta()
	{
	}
	public Ferramenta(String nome, String material, double diametro, double cuttingEdge, double profundidadeMaxima, double offSetLength, double dm)
	{
		this.name = nome;
		this.material = material;
		this.diametro = diametro;
		this.cuttingEdgeLength = cuttingEdge;
		this.profundidadeMaxima = profundidadeMaxima;
		this.offSetLength = offSetLength;
		this.dm = dm;
	}
	public Ferramenta(double diametro,double profundidadeMaxima, String material, double dm)
	{
		this.diametro = diametro;
		this.profundidadeMaxima = profundidadeMaxima;
		this.material = material;
		this.dm = dm;
	}
	public Ferramenta(double diametro,double profundidadeMaxima)
	{
		this.diametro = diametro;
		this.profundidadeMaxima = profundidadeMaxima;
	}
	public Ferramenta(double diametro, double profundidadeMaxima, int materialClasse)
	{
		
	}
	public boolean equals(Ferramenta ferramenta){
		this.getClass().equals(ferramenta.getClass());
		return(this.diametro==ferramenta.getDiametroFerramenta()&&this.profundidadeMaxima==ferramenta.getProfundidadeMaxima());
	}
	public void setDiametroFerramenta(double diametro)
	{
		this.diametro = diametro;
	}
	public double getDiametroFerramenta()
	{
		return this.diametro;
	}
	public void setProfundidadeMaxima(double profundidadeMaxima)
	{
		this.profundidadeMaxima = profundidadeMaxima;
	}
	public double getProfundidadeMaxima()
	{
		return this.profundidadeMaxima;
	}
	public void setCuttingEdgeLength(double cuttingEdgeLength)
	{
		this.cuttingEdgeLength = cuttingEdgeLength;
	}
	public double getCuttingEdgeLength()
	{
		return this.cuttingEdgeLength;
	}
	public void setHandOfCut(int handOfCut)
	{
		this.handOfCut = handOfCut;
	}
	public int getHandOfCut()
	{
		return this.handOfCut;
	}
	public String getStringHandOfCut() {
		String strHandOfCut = null;

		switch (this.handOfCut) {
		case Ferramenta.RIGHT_HAND_OF_CUT:
			strHandOfCut = "Right";
			break;
		case Ferramenta.LEFT_HAND_OF_CUT:
			strHandOfCut = "Left";
			break;
		case Ferramenta.NEUTRAL_HAND_OF_CUT:
			strHandOfCut = "Neutral";
			break;
		default:
			break;
		}
		return strHandOfCut;
	}
	public double getDm() {
		return dm;
	}
	public void setDm(double dm) {
		this.dm = dm;
	}
	public String getMaterial() {

		if(this.materialClasse != 0){
			
			switch(this.materialClasse){

			case(Ferramenta.P):
				this.setMaterial("P");
			break;
			case(Ferramenta.M):
				this.setMaterial("M");
			break;
			case(Ferramenta.S):
				this.setMaterial("S");
			break;
			case(Ferramenta.K):
				this.setMaterial("K");
			break;
			case(Ferramenta.H):
				this.setMaterial("H");
			break;
			case(Ferramenta.N):
				this.setMaterial("N");
			break;
			default:
				break;
			}
		}
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public int getMaterialClasse() 
	{
		return materialClasse;
	}
	public void setMaterialClasse(int materialClasse) 
	{
		this.materialClasse = materialClasse;
	}
	public String getName() {
		
		return this.name;
	}
	public void setName(String name) {
		
		this.name = name;
	}
	public double getOffsetLength() {
		return offSetLength;
	}
	public void setOffsetLength(double offsetLength) {
		this.offSetLength = offsetLength;
	}
	public double getToolTipHalfAngle() {
		return toolTipHalfAngle;
	}
	public void setToolTipHalfAngle(double toolTipHalfAngle) {
		this.toolTipHalfAngle = toolTipHalfAngle;
	}
	public double getToolCircunferenceAngle() {
		return toolCircunferenceAngle;
	}
	public void setToolCircunferenceAngle(double toolCircunferenceAngle) {
		this.toolCircunferenceAngle = toolCircunferenceAngle;
	}
	public double getEdgeRadius() {
		return edgeRadius;
	}
	public void setEdgeRadius(double edgeRadius) {
		this.edgeRadius = edgeRadius;
	}
	public double getEdgeCenterVertical() {
		return edgeCenterVertical;
	}
	public void setEdgeCenterVertical(double edgeCenterVertical) {
		this.edgeCenterVertical = edgeCenterVertical;
	}
	public double getEdgeCenterHorizontal() {
		return edgeCenterHorizontal;
	}
	public void setEdgeCenterHorizontal(double edgeCenterHorizontal) {
		this.edgeCenterHorizontal = edgeCenterHorizontal;
	}
	public int getNumberOfTeeth() {
		return numberOfTeeth;
	}
	public void setNumberOfTeeth(int numberOfTeeth) {
		this.numberOfTeeth = numberOfTeeth;
	}
	public double getRugosidade() {
		return rugosidade;
	}
	public void setRugosidade(double rugosidade) {
		this.rugosidade = rugosidade;
	}
	public double getTolerancia() {
		return tolerancia;
	}
	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}
	public Material getMaterialFerramenta() {
		return materialFerramenta;
	}
	public void setMaterialFerramenta(Material materialFerramenta) {
		this.materialFerramenta = materialFerramenta;
	}
	public String type(Ferramenta ferramenta)
	{
		String saida = "";
		if(ferramenta.getClass().equals(CenterDrill.class))
		{
			saida = "Center Drill";
		}
		else if(ferramenta.getClass().equals(TwistDrill.class))
		{
			saida = "Twist Drill";
		}
		else if(ferramenta.getClass().equals(BoringTool.class))
		{
			saida = "Boring Tool";
		}
		else if(ferramenta.getClass().equals(BallEndMill.class))
		{
			saida = "Ball End Mill";
		}
		else if(ferramenta.getClass().equals(FaceMill.class))
		{
			saida = "Face Mill";
		}
		else if(ferramenta.getClass().equals(BullnoseEndMill.class))
		{
			saida = "Bullnose End Mill";
		}
		else if(ferramenta.getClass().equals(Reamer.class))
		{
			saida = "Reamer";
		}
		else if(ferramenta.getClass().equals(EndMill.class))
		{
			saida = "End Mill";
		}
		
		return saida;
	}
	
}
