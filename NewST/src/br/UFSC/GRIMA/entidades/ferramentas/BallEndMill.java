package br.UFSC.GRIMA.entidades.ferramentas;

public class BallEndMill extends Ferramenta
{
	public BallEndMill() {
		
		
	}
	
	public BallEndMill(String nome, String material, double diametro, double edgeRadius, double edgeCenterVertical,
			double cuttingEdge, double profundidade, double offSetLength,
			double dm, double rugosidade, double tolerancia, int handOfCut) {
		
		super(nome, material, diametro, cuttingEdge, profundidade, offSetLength, dm);
		setEdgeRadius(edgeRadius);
		setEdgeCenterVertical(edgeCenterVertical);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
	}
	
	public BallEndMill(String nome, String material, String diametro, String edgeRadius, String edgeCenterVertical,
			String cuttingEdge, String profundidade, String offSetLength,
			String dm, String rugosidade, String tolerancia, String handOfCut) {

		super(nome, material, Double.parseDouble(diametro), Double
				.parseDouble(cuttingEdge), Double.parseDouble(profundidade),
				Double.parseDouble(offSetLength), Double.parseDouble(dm));
		setEdgeRadius(Double.parseDouble(edgeRadius));
		setEdgeCenterVertical(Double.parseDouble(edgeCenterVertical));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
	}
	
	
	
	public BallEndMill(double diametro, double profundidadeMaxima)
	{
		super(diametro, profundidadeMaxima);
	}
}
