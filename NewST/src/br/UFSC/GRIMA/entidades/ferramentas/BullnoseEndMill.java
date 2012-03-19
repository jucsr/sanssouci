package br.UFSC.GRIMA.entidades.ferramentas;

public class BullnoseEndMill extends Ferramenta
{
	
	public BullnoseEndMill(String nome, String material, double diametro,
			double edgeRadius, double edgeCenterVertical,
			double edgeCenterHorizontal, double cuttingEdge,
			double profundidade, double offSetLength, double dm,
			double rugosidade, double tolerancia, int handOfCut) {
		
		super(nome, material, diametro, cuttingEdge, profundidade, offSetLength, dm);
		setEdgeRadius(edgeRadius);
		setEdgeCenterVertical(edgeCenterVertical);
		setEdgeCenterHorizontal(edgeCenterHorizontal);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
	}
	
	public BullnoseEndMill(String nome, String material, String diametro, String edgeRadius, String edgeCenterVertical,String edgeCenterHorizontal,
			String cuttingEdge, String profundidade, String offSetLength,
			String dm, String rugosidade, String tolerancia, String handOfCut) {

		super(nome, material, Double.parseDouble(diametro), Double
				.parseDouble(cuttingEdge), Double.parseDouble(profundidade),
				Double.parseDouble(offSetLength), Double.parseDouble(dm));
		setEdgeRadius(Double.parseDouble(edgeRadius));
		setEdgeCenterVertical(Double.parseDouble(edgeCenterVertical));
		setEdgeCenterHorizontal(Double.parseDouble(edgeCenterHorizontal));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
	}
	
	
	public BullnoseEndMill(double diametro, double profundidadeMaxima) 
	{
		super(diametro, profundidadeMaxima);
	}
}
