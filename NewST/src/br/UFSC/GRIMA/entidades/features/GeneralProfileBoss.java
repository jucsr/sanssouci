package br.UFSC.GRIMA.entidades.features;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import br.UFSC.GRIMA.util.Path;

/**
 * 
 * @author Jc
 *
 */
public class GeneralProfileBoss extends Boss
{
	private GeneralPath forma;
	private double radius = 0;
	private ArrayList<Point2D> vertexPoints;
	private ArrayList<Path> paths;
	
	public GeneralProfileBoss(double radius, ArrayList<Point2D> vertexPoints)
	{
		this.vertexPoints = vertexPoints;
		this.radius = radius;
	}
	
	public GeneralProfileBoss()
	{
		
	}
	
	public GeneralPath getForma() 
	{
		return forma;
	}

	public void setForma(GeneralPath forma)
	{
		this.forma = forma;
	}

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	
	public ArrayList<Point2D> getVertexPoints() 
	{
		return vertexPoints;
	}
	
	public void setVertexPoints(ArrayList<Point2D> vertexPoints) 
	{
		this.vertexPoints = vertexPoints;
	}
	
	public DefaultMutableTreeNode getNodo()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("General Profile Boss -" + this.getIndice());
		root.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		root.add(new DefaultMutableTreeNode("Altura: " + this.getAltura()));
		root.add(new DefaultMutableTreeNode("Rugosidade: " + this.getRugosidade()));
		root.add(new DefaultMutableTreeNode("Radius = " + this.getRadius()));
		root.add(new DefaultMutableTreeNode("position Z =  " + this.Z));
		
		if(this.vertexPoints.size() > 0)
		{
			DefaultMutableTreeNode vertexNode = new DefaultMutableTreeNode("Vertex Points");
			for(int i = 0; i < this.vertexPoints.size(); i++)
			{
				DefaultMutableTreeNode pointNode = new DefaultMutableTreeNode("P - " + i + " : " + vertexPoints.get(i));
				vertexNode.add(pointNode);
			}
			root.add(vertexNode);
		}
		return root;
	}

	public ArrayList<Path> getPaths() {
		return paths;
	}

	public void setPaths(ArrayList<Path> paths) {
		this.paths = paths;
	}
}
