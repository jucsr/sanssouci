package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public class ContourParallel extends Two5DMillingStrategy
{
	private boolean rotationDirectionCCW = true;
	private int cutmodeType = 1; // default --> CONVENTIONAL
	
	public static final int climb = 0;
	public static final int conventional = 1;
	
	public boolean isRotationDirectionCCW() 
	{
		return rotationDirectionCCW;
	}
	public void setRotationDirectionCCW(boolean rotationDirectionCCW) 
	{
		this.rotationDirectionCCW = rotationDirectionCCW;
	}
	public int getCutmodeType()
	{
		return cutmodeType;
	}
	/**
	 * 
	 * @param cutmodeType -->Specifies whether CONVENTIONAL or CLIMB cutting should be used. Default is
	 * 			conventional. The cutmode refers to the functional walls of the contour which are produced 
	 * 			by side milling, i. e. the outer contour of a pocket and possible bosses.
	 */
	public void setCutmodeType(int cutmodeType) 
	{
		this.cutmodeType = cutmodeType;
	}
}
