package br.UFSC.GRIMA.entidades.ferramentas;

public class EndMill extends Ferramenta
{
	public EndMill(String nome, String material, double diametro,
			double cuttingEdge, double profundidade, double offSetLength,
			double dm, double rugosidade, double tolerancia, int handOfCut) {

		super(nome, material, diametro, cuttingEdge, profundidade, offSetLength, dm);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
	}
	
	public EndMill(String nome, String material, String diametro,
			String cuttingEdge, String profundidade, String offSetLength,
			String dm, String rugosidade, String tolerancia, String handOfCut) {

		super(nome, material, Double.parseDouble(diametro), Double
				.parseDouble(cuttingEdge), Double.parseDouble(profundidade),
				Double.parseDouble(offSetLength), Double.parseDouble(dm));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
	}
	
	
	public EndMill(double diametro, double profundidadeMaxima)
	{
		super(diametro, profundidadeMaxima);
	}
}
