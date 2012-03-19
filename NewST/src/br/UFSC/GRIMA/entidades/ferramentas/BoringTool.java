package br.UFSC.GRIMA.entidades.ferramentas;
/**
 * 
 * @author Jc
 *
 */
public class BoringTool extends Ferramenta
{
	
	private String diametro; // ATENCAO: eh uma FAIXA de diametros
	private String acoplamento;
	
	public BoringTool(String nome, String material, String diametro, double edgeRadius,
			double cuttingEdge, double profundidade, double offSetLength,
			double dm, double rugosidade, double tolerancia, int handOfCut, String acoplamento) {
		
		super(nome, material, 0.0, cuttingEdge, profundidade, offSetLength, dm);
		
		this.diametro = diametro;
		setEdgeRadius(edgeRadius);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
		this.acoplamento = acoplamento;
	}
	
	public BoringTool(String nome, String material, String diametro, String edgeRadius,
			String cuttingEdge, String profundidade, String offSetLength,
			String dm, String rugosidade, String tolerancia, String handOfCut, String acoplamento) {

		super(nome, material, 0.0, Double.parseDouble(cuttingEdge), Double
				.parseDouble(profundidade), Double.parseDouble(offSetLength),
				Double.parseDouble(dm));
		
		this.diametro = diametro;
		setEdgeRadius(Double.parseDouble(edgeRadius));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
		this.acoplamento = acoplamento;
	}
	
	
	
	public BoringTool(double diametro, double profundidadeMaxima)
	{
		super(diametro, profundidadeMaxima);
	}

	public String getDiametro() {
		return diametro;
	}

	public void setDiametro(String diametro) {
		this.diametro = diametro;
	}

	public String getAcoplamento() {
		return acoplamento;
	}

	public void setAcoplamento(String acoplamento) {
		this.acoplamento = acoplamento;
	}
}
