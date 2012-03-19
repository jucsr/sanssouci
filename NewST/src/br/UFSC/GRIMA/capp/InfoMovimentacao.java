package br.UFSC.GRIMA.capp;

public class InfoMovimentacao {
	//tipos de movimentacao 
	public static final int RADIAL_FURO = 0;
	public static final int ZIG_ZAG = 1;
	public static final int RADIAL_CAVIDADE = 2;
	public int tipo = 0;
	public InfoMovimentacao()
	{
		
	}
	public boolean setTipo(int tipo){
		if(tipo >=0 && tipo <= 2){
			this.tipo = tipo;
			return true;
		}
		else
			return false;
	}
	public int getTipo()
	{	return this.tipo;
	}
	public String getTipoString()
	{
		switch (this.tipo)
		{
			case InfoMovimentacao.RADIAL_FURO:
				return "Radial";
			case InfoMovimentacao.ZIG_ZAG:
				return "ZigZag";
			case InfoMovimentacao.RADIAL_CAVIDADE:
				return "Radial Cavidade";
			default:
				return "";
		}
	}
}
