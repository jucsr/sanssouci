package br.UFSC.GRIMA.entidades.features;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import jsdai.SCombined_schema.ERegion;

import br.UFSC.GRIMA.bReps.BezierSurface;

/**
 * 
 * @author Jc
 *
 */
public class Region extends Feature
{
	private String itsId = "";
	private BezierSurface surface;
	private Point3d [][] controlVertex;
	private int splitU = 11;
	private int splitV = 11;
	private double maxDepth;
	private transient ERegion eRegion;
	
	public Region(int tipo) 
	{
		super(tipo);
	}
	public String getItsId() 
	{
		return itsId;
	}
	public void setItsId(String itsId) 
	{
		this.itsId = itsId;
	}
	public BezierSurface getSurface() 
	{
		return surface;
	}
	public void setSurface(BezierSurface surface) 
	{
		this.surface = surface;
	}
	public Point3d[][] getControlVertex() 
	{
		return controlVertex;
	}
	public void setControlVertex(Point3d[][] controlVertex) 
	{
		this.controlVertex = controlVertex;
	}
	public int getSplitU() 
	{
		return splitU;
	}
	public void setSplitU(int splitU)
	{
		this.splitU = splitU;
	}
	public int getSplitV() 
	{
		return splitV;
	}
	public void setSplitV(int splitV) 
	{
		this.splitV = splitV;
	}
	public ERegion geteRegion() 
	{
		return eRegion;
	}
	public void seteRegion(ERegion eRegion)
	{
		this.eRegion = eRegion;
	}
	public double getMaxDepth() 
	{
		return maxDepth;
	}
	public void setMaxDepth(double maxDepth)
	{
		this.maxDepth = maxDepth;
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Bezier Surface -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Its Id: " + this.itsId));
		root.add(new DefaultMutableTreeNode("Its Position: " + this.getPosition().getCoordinates()));
		root.add(new DefaultMutableTreeNode("Its Control Vertex: " + this.controlVertex));
		return root;
	}
}
