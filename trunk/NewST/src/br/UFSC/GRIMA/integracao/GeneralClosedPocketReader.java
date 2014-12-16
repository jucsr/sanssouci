package br.UFSC.GRIMA.integracao;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.ABoss;
import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.AComposite_curve_segment;
import jsdai.SCombined_schema.EBoss;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ECircle;
import jsdai.SCombined_schema.ECircular_closed_profile;
import jsdai.SCombined_schema.EClosed_pocket;
import jsdai.SCombined_schema.EComposite_curve;
import jsdai.SCombined_schema.EComposite_curve_segment;
import jsdai.SCombined_schema.EGeneral_closed_profile;
import jsdai.SCombined_schema.EPolyline;
import jsdai.SCombined_schema.ERectangular_closed_profile;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

/**
 * 
 * @author Jc
 *
 */
public class GeneralClosedPocketReader 
{
	public static GeneralClosedPocket getGeneralClosedPocket(EClosed_pocket pocket) throws SdaiException
	{
		String id = pocket.getIts_id(null);
		
		double locX = pocket.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(1);
		double locY = pocket.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(2);		
		double locZ = pocket.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(3);
		
		
		double raioCavidade = pocket.getOrthogonal_radius(null).getTheoretical_size(null);

		double profundidadeCavidade = pocket.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3) * (-1);
	
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		ACartesian_point vertex = ((EPolyline)((EGeneral_closed_profile)pocket.getFeature_boundary(null)).getClosed_profile_shape(null)).getPoints(null);
		
		SdaiIterator iterator = vertex.createIterator();
		
		while(iterator.next())
		{
			ECartesian_point point = vertex.getCurrentMember(iterator);
			points.add(new Point2D.Double(point.getCoordinates(null).getByIndex(1) + locX, point.getCoordinates(null).getByIndex(2) + locY));
		}
		
		double x = 0, y = 0, z = 0;
		
		Face faceAtual = FaceReader.getFace(pocket);
		
		if(faceAtual.getTipo()==Face.XY)
		{
			x = locX;
			y = locY;
			z = faceAtual.getProfundidadeMaxima() - locZ;

		}else if(faceAtual.getTipo()==Face.YX)
		{
			x = locX;
			y = faceAtual.getLargura() - locY;
			z = locZ;

		}else if(faceAtual.getTipo()==Face.YZ)
		{
			x = faceAtual.getComprimento() - locX;
			y = locY;
			z = faceAtual.getProfundidadeMaxima() - locX;

		}else if(faceAtual.getTipo()==Face.ZY)
		{
			x = locZ;
			y = locY;
			z = locX;

		}else if(faceAtual.getTipo()==Face.XZ)
		{
			x = locX;
			y = locZ;
			z = locY;

		}else if(faceAtual.getTipo()==Face.ZX)
		{
			x = locX;
			y = faceAtual.getLargura() - locZ;
			z = faceAtual.getProfundidadeMaxima() - locY;
		}
		
		GeneralClosedPocket cavidade = new GeneralClosedPocket();
		cavidade.setNome(id);
		cavidade.setPosicao(x, y, z);
		cavidade.setRadius(raioCavidade);
		cavidade.setProfundidade(profundidadeCavidade);
		cavidade.setPoints(points);
		
		double zLinhaX = pocket.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(1);
		double zLinhaY = pocket.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(2);
		double zLinhaZ = pocket.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> axis = new ArrayList<Double>();
		axis.add(zLinhaX);
		axis.add(zLinhaY);
		axis.add(zLinhaZ);
		
		zLinhaX = pocket.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(1);
		zLinhaY = pocket.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(2);
		zLinhaZ = pocket.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> refDirection = new ArrayList<Double>();
		refDirection.add(zLinhaX);
		refDirection.add(zLinhaY);
		refDirection.add(zLinhaZ);
		
		double zOriginal = pocket.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(3);
				
				Point3d coordinates = new Point3d(x,y,zOriginal);
				
				Axis2Placement3D position = new Axis2Placement3D(coordinates,axis,refDirection);
				String name = pocket.getFeature_placement(null).getName(null);
				position.setName(name);
				cavidade.setPosition(position);
				

		ABoss bosses = pocket.getIts_boss(null);
		
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
		
		SdaiIterator iteratorBoss = bosses.createIterator();
		Axis2Placement3D positionBoss = new Axis2Placement3D(coordinates,axis,refDirection);
		while(iteratorBoss.next())
		{
			EBoss eBoss = bosses.getCurrentMember(iteratorBoss);
			id = eBoss.getIts_id(null);
			double altura = eBoss.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3);
			
			Point3d centre = new Point3d(eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(1),
					 eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(2),
					 eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(3));
			
			positionBoss.setName(eBoss.getFeature_placement(null).getName(null));
			
			if(eBoss.getIts_boundary(null).isKindOf(ECircular_closed_profile.class)){
				double diametro1 = ((ECircular_closed_profile) eBoss.getIts_boundary(null)).getDiameter(null).getTheoretical_size(null);
				double angulo = eBoss.getSlope(null);
				double diametro2 = 2*altura*Math.tan(angulo)+diametro1;
				
				CircularBoss circularBoss = new CircularBoss();
				circularBoss.setNome(id);
				circularBoss.setAltura(altura);
				circularBoss.setDiametro1(diametro1);
				circularBoss.setDiametro2(diametro2);
				circularBoss.setCenter(centre);
				circularBoss.setPosicao(centre.getX(), centre.getY(), z + profundidadeCavidade - altura);
				circularBoss.setFace(faceAtual);
				positionBoss.setCoordinates(centre);
				circularBoss.setPosition(positionBoss);
				
				itsBoss.add(circularBoss);
				
			} else if(eBoss.getIts_boundary(null).isKindOf(ERectangular_closed_profile.class))
			{
				double length = ((ERectangular_closed_profile)eBoss.getIts_boundary(null)).getProfile_length(null).getTheoretical_size(null);
				double width = ((ERectangular_closed_profile)eBoss.getIts_boundary(null)).getProfile_width(null).getTheoretical_size(null);
				
				RectangularBoss rectangularBoss = new RectangularBoss(length, width, altura, 0);
				rectangularBoss.setNome(id);
				rectangularBoss.setPosicao(centre.x - length / 2, centre.y - width / 2,  z + profundidadeCavidade - altura);
				positionBoss.setCoordinates(centre);
				rectangularBoss.setPosition(positionBoss);
				itsBoss.add(rectangularBoss);
				
			} else if(eBoss.getIts_boundary(null).isKindOf(EGeneral_closed_profile.class))
			{
				GeneralProfileBoss general = new GeneralProfileBoss();
				double aAtual=0, bAtual=0, aAnterior=0 , bAnterior=0 ,aPrimeiro=0, bPrimeiro=0, n , m;
				ArrayList<Point2D> vertexPoints = new ArrayList<Point2D>();
				AComposite_curve_segment listaDeSegmentos = ((EComposite_curve) ((EGeneral_closed_profile) eBoss.getIts_boundary(null)).getClosed_profile_shape(null)).getSegments(null);
				SdaiIterator iterator2 = listaDeSegmentos.createIterator();
				SdaiIterator cont = listaDeSegmentos.createIterator();
				int contador=0, numeroDePolylines=0;
				while(cont.next()){
					numeroDePolylines++;
				}
				boolean mesmaCoordenadaX=false, mesmaCoordenadaY=false;
				
				numeroDePolylines= numeroDePolylines/2;
				Point2D p1I = new Point2D.Double(),
						p2I = new Point2D.Double(),
						p1  = new Point2D.Double(),
						p2  = new Point2D.Double(),
						p1Comeco = new Point2D.Double(),
						p2Comeco = new Point2D.Double();
				ArrayList<Path> paths = new ArrayList<Path>();
				while(iterator2.next()){
					EComposite_curve_segment segmentoTmp = listaDeSegmentos.getCurrentMember(iterator2);

					if(segmentoTmp.getParent_curve(null).isKindOf(EPolyline.class)){
						
						
						if(contador!=0){
							p1I = p1;
							p2I = p2;
						}
//						
//						if(contador!=0){
//							aAnterior = aAtual;
//							bAnterior = bAtual;
//						}
						ACartesian_point pontosLinha = ((EPolyline) segmentoTmp.getParent_curve(null)).getPoints(null);
						ECartesian_point ponto1 = pontosLinha.getByIndex(1);
						ECartesian_point ponto2 = pontosLinha.getByIndex(2);
						p1 = new Point2D.Double(ponto1.getCoordinates(null).getByIndex(1),ponto1.getCoordinates(null).getByIndex(2));
						p2 = new Point2D.Double(ponto2.getCoordinates(null).getByIndex(1),ponto2.getCoordinates(null).getByIndex(2));
						LinearPath path = new LinearPath(new Point3d(p1.getX(), p1.getY(), 0), new Point3d(p2.getX(), p2.getY(), 0));
						paths.add(path);
						if(contador!=0){
//							System.out.println(p1 +" "+p2+" "+p1I+" "+p2I);
							vertexPoints.add(OperationsVector.getIntersectionPoint(p1, p2, p1I, p2I));
						}else{
							p1Comeco = p1;
							p2Comeco = p2;
						}
						
						if(numeroDePolylines==contador+1){
//							System.out.println(p1 +" "+p2+" "+p1Comeco+" "+p2Comeco);
							vertexPoints.add(OperationsVector.getIntersectionPoint(p1, p2, p1Comeco, p2Comeco));	
						}

						contador++;
						
					}
					else if(segmentoTmp.getParent_curve(null).isKindOf(ECircle.class)){
						double radius =  ( (ECircle) segmentoTmp.getParent_curve(null) ).getRadius(null);
						general.setRadius( radius);
						CircularPath path = new CircularPath(new Point3d(), new Point3d(), radius); /** <----------------- arrumar os pontos de inicio e fim do arco circular */
						paths.add(path);
					}
				}
				general.setVertexPoints(vertexPoints);
				general.setNome(id);
				general.setAltura(altura);
				general.setPosicao(centre.getX(), centre.getY(), z + profundidadeCavidade - altura);
				positionBoss.setCoordinates(centre);
				general.setPosition(positionBoss);
				general.setFace(faceAtual);
				general.setPaths(paths);
				itsBoss.add(general);
				}
			cavidade.setItsBoss(itsBoss);
		}
		return cavidade;
	}
}
