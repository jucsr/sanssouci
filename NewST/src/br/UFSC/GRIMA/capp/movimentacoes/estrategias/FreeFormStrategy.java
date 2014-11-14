package br.UFSC.GRIMA.capp.movimentacoes.estrategias;
/**
 * 
 * @author Jc
 *
 */
public class FreeFormStrategy extends MachinningStrategy
{
	public enum PathModeType {FORRWARD, ZIG_ZAG};
	public enum CutModeType {CLIMB, CONVENTIONAL};
	
	private double chordalTolerance = 0.01; // 4.2.7.1.3 Tolerances - Geometric error resulting from a linear approximation of a curve.
	private double scallopHeight = 0.01; // 4.2.7.1.3 Tolerances - Height of the grooves caused by the tool radius
	private double stepOver = 0.05; //The distance between two neighboring toolpaths. If given, the stepover calculated by use of its_milling_tolerances will be ignored.
	
	/**
	 * 4.2.7.1.3 Tolerances - Geometric error resulting from a linear approximation of a curve.
	 * @param chordalTolerance
	 */
	public double getChordalTolerance() 
	{
		return chordalTolerance;
	}
	public void setChordalTolerance(double chordalTolerance) 
	{
		this.chordalTolerance = chordalTolerance;
	}
	/**
	 * 4.2.7.1.3 Tolerances - Height of the grooves caused by the tool radius
	 * @return
	 */
	public double getScallopHeight() 
	{
		return scallopHeight;
	}
	public void setScallopHeight(double scallopHeight)
	{
		this.scallopHeight = scallopHeight;
	}
	/**
	 * The distance between two neighboring toolpaths. If given, the stepover calculated by use of its_milling_tolerances will be ignored.
	 * @param stepOver
	 */
	public double getStepOver() 
	{
		return stepOver;
	}
	public void setStepOver(double stepOver)
	{
		this.stepOver = stepOver;
	}
}
