package br.UFSC.GRIMA.entidades.ferramentas;
/**
 * 
 * @author Jc
 *
 */
public class CenterDrill extends Ferramenta
{
	
	public CenterDrill(String nome, String material, double diametro,
			double tipAngle, double cuttingEdge, double profundidade,
			double offSetLength, double dm, double rugosidade,
			double tolerancia, int handOfCut) 
	{
		super(nome,material,diametro,cuttingEdge,profundidade,offSetLength,dm);
		setToolTipHalfAngle(tipAngle);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
	}
	
	public CenterDrill(String nome, String material, String diametro,
			String tipAngle, String cuttingEdge, String profundidade,
			String offSetLength, String dm, String rugosidade,
			String tolerancia, String handOfCut) {
		super(nome, material, Double.parseDouble(diametro), Double
				.parseDouble(cuttingEdge), Double.parseDouble(profundidade),
				Double.parseDouble(offSetLength), Double.parseDouble(dm));
		setToolTipHalfAngle(Double.parseDouble(tipAngle));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
	}
	
	
	public CenterDrill(double diametro, double profundidadeMaxima)
	{
		super(diametro, profundidadeMaxima);
	}
}
