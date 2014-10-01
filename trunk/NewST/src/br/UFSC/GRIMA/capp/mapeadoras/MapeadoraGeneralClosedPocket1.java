package br.UFSC.GRIMA.capp.mapeadoras;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.CreateGeneralPocket;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.ToolManager;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.geometricOperations.GeometricOperations;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * 
 * @author feitosa
 *
 */
public class MapeadoraGeneralClosedPocket1 
{
	private Projeto projeto;
	private Bloco bloco;
	private Face faceTmp;
	private GeneralClosedPocket genClosed;
	private Vector<Workingstep> wssFeature;
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private ArrayList<Boss> itsBoss;

	private ArrayList<FaceMill> faceMills;
	private ArrayList<EndMill> endMills;

	public MapeadoraGeneralClosedPocket1(Projeto projeto, Face face, GeneralClosedPocket genClosed) 
	{

		this.projeto = projeto;
		this.faceTmp = face;
		this.genClosed = genClosed;
		this.bloco = projeto.getBloco();
		this.itsBoss = genClosed.getItsBoss();

		this.faceMills = ToolManager.getFaceMills();
		this.endMills = ToolManager.getEndMills();
		
//		this.mapearGeneralClosedPocket();

	}
	public MapeadoraGeneralClosedPocket1(GeneralClosedPocket genClosed) 
	{
		this.genClosed = genClosed;
		this.itsBoss = genClosed.getItsBoss();
		
//		this.mapearGeneralClosedPocket();

	}
	public double getMaiorMenorDistancia()
	{
		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
		ArrayList<ArrayList<Point2D>> matrix = new ArrayList<ArrayList<Point2D>>();
		double menorDistancia=0;
		
		//Posicao da forma
		Point2D minorPointX = genClosed.getPoints().get(0); //Menor X
		Point2D maxPointX = genClosed.getPoints().get(0);   //Maior Y
		Point2D minorPointY = genClosed.getPoints().get(0); //Menor X
		Point2D maxPointY = genClosed.getPoints().get(0);   //Maior Y
		for(Point2D pointTmp : genClosed.getPoints())
		{
			if(pointTmp.getX() < minorPointX.getX())
			{
				minorPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getX() > maxPointX.getX())
			{
				maxPointX = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getY() < minorPointY.getY())
			{
				minorPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
			if(pointTmp.getY() > maxPointY.getY())
			{
				maxPointY = new Point2D.Double(pointTmp.getX(),pointTmp.getY());
			}
		}
		System.out.println("Xminor: " + minorPointX);
		System.out.println("Xmax: " + maxPointX);
		System.out.println("Yminor: " + minorPointY);
		System.out.println("Ymax: " + maxPointY);

		int numeroDePontos = 100;
		double deltaX = minorPointX.distance(maxPointX)/numeroDePontos;
		double deltaY = minorPointY.distance(maxPointY)/numeroDePontos;
		
		//CRIA O GENERAL PATH DO FORMATO
//		GeneralPath gp = new GeneralPath();
//		vertex = CreateGeneralPocket.transformPolygonInRoundPolygon(CreateGeneralPocket.transformPolygonInCounterClockPolygon(genClosed.getVertexPoints()), genClosed.getRadius());
//		gp.moveTo(vertex.get(0).getX(), vertex.get(0).getY());
		GeneralPath gp = (GeneralPath)Face.getShape(genClosed);
			
		//CRIA UM Shape2D DA PROTUBERANCIA
		Shape boss = null;
		for(Boss bossTmp:itsBoss)
		{
			if(bossTmp.getClass() == CircularBoss.class)
			{
//				Ellipse2D circularBossShape = (Ellipse2D)Face.getShape(bossTmp);
				boss = (Ellipse2D)Face.getShape(bossTmp);
				System.out.println("CircularBoss: " + boss);
			}
			else if(bossTmp.getClass() == RectangularBoss.class)
			{
//				RoundRectangle2D rectangularBossShape = (RoundRectangle2D)Face.getShape(bossTmp);
				boss = (RoundRectangle2D)Face.getShape(bossTmp);
			}
			else if(bossTmp.getClass() == GeneralProfileBoss.class)
			{
//				GeneralPath gpBoss = (GeneralPath)Face.getShape(genClosed);
				boss = (GeneralPath)Face.getShape(genClosed);
			}
		}
		
		//Array de LimitedElement da forma da cavidade
		GeneralClosedPocketVertexAdd addPocket = new GeneralClosedPocketVertexAdd(genClosed.getVertexPoints(), genClosed.Z, genClosed.getRadius());
		for(int r=0;r<vertex.size();r++)
		{
			gp.lineTo(vertex.get(r).getX(), vertex.get(r).getY());				
		}
		gp.closePath();
			
		for(int i = 0; i < numeroDePontos; i++)
		{
			ArrayList<Point2D> arrayTmp = new ArrayList<Point2D>();
			for(int j = 0; j < numeroDePontos; j++)
			{
				Point2D pointTmp = new Point2D.Double(minorPointX.getX() + deltaX*i , minorPointX.getY() + deltaY*j);
				if(gp.contains(pointTmp)) //Se o ponto esta dentro da cavidade
				{
					if(boss != null)      //Se possui Protuberancia
					{
						if(boss.contains(pointTmp)) //Se o ponto esta dentro da protuberancia
						{
							break;
						}
					}
					double menorDistanciaTmp = GeometricOperations.minimumDistance(addPocket.getElements(), new Point3d(pointTmp.getX(),pointTmp.getY(),genClosed.Z));
					if(menorDistanciaTmp > menorDistancia)
					{
						System.out.println("Ponto: "+ pointTmp);
						menorDistancia = menorDistanciaTmp;
					}
				}
//					arrayTmp.add(pointTmp);
			}
//				matrix.add(arrayTmp);
		}
			
		return menorDistancia;
	}
}
