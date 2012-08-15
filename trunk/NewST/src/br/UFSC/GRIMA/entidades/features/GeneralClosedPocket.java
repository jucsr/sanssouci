package br.UFSC.GRIMA.entidades.features;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.UFSC.GRIMA.cad.JanelaPrincipal;

import jsdai.SCombined_schema.EClosed_pocket;
/**
 * 
 * @author Jc
 *
 */
public class GeneralClosedPocket extends Feature
{
	private double radius = 0;
	private double profundidade = 0;
	
	private ArrayList<Point2D> vertexPoints;
	private GeneralPath forma;
	private boolean isPassante = false;
	private ArrayList<Boss> itsBoss = new ArrayList<Boss>();
	private EClosed_pocket eClosed_pocket;
	private int [] indices = {0, 0, 0, 0, 0, 0, 0};
	
	public GeneralClosedPocket()
	{
		super(Cavidade.CAVIDADE_PERFIL_GERAL);
	}
	public GeneralClosedPocket(double radius, ArrayList<Point2D> vertexPoints)
	{
		super(Cavidade.CAVIDADE_PERFIL_GERAL);
		this.vertexPoints = vertexPoints;
		this.radius = radius;
	}
	
	public double getProfundidade()
	{
		return profundidade;
	}
	public void setProfundidade(double profundidade)
	{
		this.profundidade = profundidade;
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	public ArrayList<Point2D> getPoints()
	{
		return vertexPoints;
	}
	public void setPoints(ArrayList<Point2D> points) 
	{
		this.vertexPoints = points;
	}
	public void addBoss(Boss boss)
	{
		indices[boss.getTipo()]++;
		boss.setIndice(indices[boss.getTipo()]);
		this.itsBoss.add(boss);
		JanelaPrincipal.setDoneCAPP(false);
	}
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("General Closed Profile Pocket -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Profundidade = " + this.getProfundidade()));
		root.add(new DefaultMutableTreeNode("pos Z = " + this.getPosicaoZ()));
		root.add(new DefaultMutableTreeNode("Rugosidade = " + this.getRugosidade()));
		root.add(new DefaultMutableTreeNode("Radius = " + this.getRadius()));
		
		DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
		root.add(bossNode);
		
		for(int i = 0; i < this.itsBoss.size(); i++)
		{
			if(this.itsBoss.get(i).getClass() == CircularBoss.class)
			{
				CircularBoss circular = (CircularBoss)this.itsBoss.get(i);
				bossNode.add(circular.getNode());
			} else if(this.itsBoss.get(i).getClass() == RectangularBoss.class)
			{
				RectangularBoss rectangular = (RectangularBoss)this.itsBoss.get(i);
				bossNode.add(rectangular.getNode());
			} else if(this.itsBoss.get(i).getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss general = (GeneralProfileBoss)this.itsBoss.get(i);
				bossNode.add(general.getNodo());
			}
		}
		
		this.getNodoWorkingSteps(root);
		return root;
	}
	public GeneralPath getForma() 
	{
		return forma;
	}
	public void setForma(GeneralPath forma) 
	{
		this.forma = forma;
	}
	public ArrayList<Boss> getItsBoss() {
		return itsBoss;
	}
	public void setItsBoss(ArrayList<Boss> itsBoss) {
		this.itsBoss = itsBoss;
	}
	public boolean isPassante() {
		return isPassante;
	}
	public void setPassante(boolean isPassante) {
		this.isPassante = isPassante;
	}
	public EClosed_pocket geteClosed_pocket() {
		return eClosed_pocket;
	}
	public void seteClosed_pocket(EClosed_pocket eClosed_pocket) {
		this.eClosed_pocket = eClosed_pocket;
	}
	
}
