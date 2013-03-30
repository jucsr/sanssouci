package br.UFSC.GRIMA.entidades.ferramentas;

public class Reamer extends Ferramenta
{
	public Reamer(){
		
	}
	
	public Reamer(String nome, String material, double diametro,
			double cuttingEdge, double profundidade, double offSetLength,
			double dm, double rugosidade, double tolerancia, int handOfCut,
			int numberOfTeeth) {
		super(nome, material, diametro, cuttingEdge, profundidade,
				offSetLength, dm);
		setRugosidade(rugosidade);
		setTolerancia(tolerancia);
		setHandOfCut(handOfCut);
		setNumberOfTeeth(numberOfTeeth);
	}

	public Reamer(String nome, String material, String diametro,
			String cuttingEdge, String profundidade, String offSetLength,
			String dm, String rugosidade, String tolerancia, String handOfCut,
			String numberOfTeeth) {
		super(nome, material, Double.parseDouble(diametro), Double
				.parseDouble(cuttingEdge), Double.parseDouble(profundidade),
				Double.parseDouble(offSetLength), Double.parseDouble(dm));
		setRugosidade(Double.parseDouble(rugosidade));
		setTolerancia(Double.parseDouble(tolerancia));
		setHandOfCut(Integer.parseInt(handOfCut));
		setNumberOfTeeth(Integer.parseInt(numberOfTeeth));
	}
	
	public Reamer(double diametro, double profundidadeMaxima)
	{
		super(diametro, profundidadeMaxima);
	}
}
