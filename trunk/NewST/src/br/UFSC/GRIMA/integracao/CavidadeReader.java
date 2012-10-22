package br.UFSC.GRIMA.integracao;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.ABoss;
import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.AComposite_curve_segment;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.EBoss;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ECircle;
import jsdai.SCombined_schema.ECircular_closed_profile;
import jsdai.SCombined_schema.EClosed_pocket;
import jsdai.SCombined_schema.EComposite_curve;
import jsdai.SCombined_schema.EComposite_curve_segment;
import jsdai.SCombined_schema.EGeneral_closed_profile;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.EPolyline;
import jsdai.SCombined_schema.ERectangular_closed_profile;
import jsdai.SCombined_schema.EThrough_pocket_bottom_condition;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.operationsVector.OperationsVector;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CavidadeReader {

	public static Cavidade getCavidade(EClosed_pocket pocket)
			throws SdaiException {

		String id = pocket.getIts_id(null);
		
		double locX = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(1);
		double locY = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(2);		
		double locZ = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(3);
		
		
		double raioCavidade = pocket.getOrthogonal_radius(null).getTheoretical_size(null);

		double largura = ((ERectangular_closed_profile) pocket
				.getFeature_boundary(null)).getProfile_width(null)
				.getTheoretical_size(null);

		double comprimento = ((ERectangular_closed_profile) pocket
				.getFeature_boundary(null)).getProfile_length(null)
				.getTheoretical_size(null);

		double profundidadeCavidade = pocket.getDepth(null).getPosition(null)
				.getLocation(null).getCoordinates(null).getByIndex(3)
				*(-1);
		
		double alturaBloco = ((EBlock)pocket.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		
		
		
//		double x = locX - comprimentoCavidade/2;
//		double y = locY - larguraCavidade/2;
//		double z = alturaBloco - locZ;
		
		double x = 0, y = 0, z = 0;
		
		Face faceAtual = FaceReader.getFace(pocket);
		
		if(faceAtual.getTipo()==Face.XY){

			x = locX - comprimento/2;

			y = locY - largura/2;
		
			z = faceAtual.getProfundidadeMaxima() - locZ;

		}else if(faceAtual.getTipo()==Face.YX){

			x = locX - comprimento/2;

			y = faceAtual.getLargura() - locY - largura/2;

			z = locZ;

		}else if(faceAtual.getTipo()==Face.YZ){

			x = faceAtual.getComprimento() - comprimento/2 - locZ;

			y = locY - largura/2;

			z = faceAtual.getProfundidadeMaxima() - locX;

		}else if(faceAtual.getTipo()==Face.ZY){

			x = locZ - comprimento/2;

			y = locY - largura/2;

			z = locX;

		}else if(faceAtual.getTipo()==Face.XZ){

			x = locX - comprimento/2;

			y = locZ - largura/2;

			z = locY;

		}else if(faceAtual.getTipo()==Face.ZX){

			x = locX - comprimento/2;

			y = faceAtual.getLargura()- locZ - largura/2;

			z = faceAtual.getProfundidadeMaxima() - locY;

		}
		
		Cavidade cavidade = new Cavidade(id, x, y, z, locX, locY, locZ, raioCavidade, largura, comprimento, profundidadeCavidade);

		ABoss bosses = pocket.getIts_boss(null);
		
		ArrayList<Boss> itsBoss = new ArrayList<Boss>();
	
		
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
		
		double zOriginal = pocket.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(3);
		
		Point3d coordinates = new Point3d(x,y,zOriginal);
		
		Axis2Placement3D position = new Axis2Placement3D(coordinates,axis,refDirection);
			
		SdaiIterator iterator = bosses.createIterator();
		
		Axis2Placement3D positionBoss = new Axis2Placement3D(coordinates,axis,refDirection);


		while(iterator.next()){
			EBoss eBoss = bosses.getCurrentMember(iterator);
			id = eBoss.getIts_id(null);
			double altura = eBoss.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3);
			
			Point3d centre = new Point3d(eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(1),
					 eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(2),
					 eBoss.getFeature_placement(null).getLocation(null).getCoordinates(null).getByIndex(3));
			
			
			if(faceAtual.getTipo()==Face.XY){

				x = centre.x;

				y = centre.y;
			
				z = faceAtual.getProfundidadeMaxima() - cavidade.Z - cavidade.getProfundidade() - centre.z;

			}else if(faceAtual.getTipo()==Face.YX){

				x = locX - comprimento/2;

				y = faceAtual.getLargura() - locY - largura/2;

				z = locZ;

			}else if(faceAtual.getTipo()==Face.YZ){

				x = faceAtual.getComprimento() - comprimento/2 - locZ;

				y = locY - largura/2;

				z = faceAtual.getProfundidadeMaxima() - locX;

			}else if(faceAtual.getTipo()==Face.ZY){

				x = locZ - comprimento/2;

				y = locY - largura/2;

				z = locX;

			}else if(faceAtual.getTipo()==Face.XZ){

				x = locX - comprimento/2;

				y = locZ - largura/2;

				z = locY;

			}else if(faceAtual.getTipo()==Face.ZX){

				x = locX - comprimento/2;

				y = faceAtual.getLargura()- locZ - largura/2;

				z = faceAtual.getProfundidadeMaxima() - locY;

			}
			
			
			
			if(eBoss.getIts_boundary(null).isKindOf(ECircular_closed_profile.class)){
				double diametro1 = ((ECircular_closed_profile) eBoss.getIts_boundary(null)).getDiameter(null).getTheoretical_size(null);
				double angulo = eBoss.getSlope(null);
				double diametro2 = 2*altura*Math.tan(angulo)+diametro1;
			
				CircularBoss circularBoss = new CircularBoss();
				circularBoss.setNome(id);
				circularBoss.setAltura(altura);
				circularBoss.setDiametro1(diametro1);
				circularBoss.setDiametro2(diametro2);
				circularBoss.setCentre(centre);
				circularBoss.setPosicao(centre.getX(), centre.getY(), z + profundidadeCavidade - altura);
				circularBoss.setFace(faceAtual);
				circularBoss.setPosition(positionBoss);
				itsBoss.add(circularBoss);
			} else if(eBoss.getIts_boundary(null).isKindOf(ERectangular_closed_profile.class))
			{
				double length = ((ERectangular_closed_profile)eBoss.getIts_boundary(null)).getProfile_length(null).getTheoretical_size(null);
				double width = ((ERectangular_closed_profile)eBoss.getIts_boundary(null)).getProfile_width(null).getTheoretical_size(null);
				
				
				RectangularBoss rectangularBoss = new RectangularBoss(length, width, altura, 0);
				rectangularBoss.setNome(id);
				rectangularBoss.setPosicao(centre.x - length / 2, centre.y - width / 2,  z + profundidadeCavidade - altura);
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
						
						if(contador!=0){
							System.out.println(p1 +" "+p2+" "+p1I+" "+p2I);
							vertexPoints.add(OperationsVector.getIntersectionPoint(p1, p2, p1I, p2I));
						}else{
							p1Comeco = p1;
							p2Comeco = p2;
						}
						
						if(numeroDePolylines==contador+1){
							System.out.println(p1 +" "+p2+" "+p1Comeco+" "+p2Comeco);
							vertexPoints.add(OperationsVector.getIntersectionPoint(p1, p2, p1Comeco, p2Comeco));	
						}

						contador++;
						
					}
					else if(segmentoTmp.getParent_curve(null).isKindOf(ECircle.class)){
						general.setRadius( ( (ECircle) segmentoTmp.getParent_curve(null) ).getRadius(null));
					}
				}
				general.setVertexPoints(vertexPoints);
				general.setNome(id);
				general.setAltura(altura);
				general.setPosicao(centre.getX(),centre.getY(),centre.getZ());
				general.setFace(faceAtual);
				general.setPosition(positionBoss);
				itsBoss.add(general);
				
			}

		}
		
		cavidade.setItsBoss(itsBoss);
		
		double tolerancia = ((EPlus_minus_value)pocket.getOrthogonal_radius(null).getImplicit_tolerance(null)).getUpper_limit(null);
		cavidade.setTolerancia(tolerancia);
		
		boolean b;
		
		if (pocket.getBottom_condition(null).isKindOf(EThrough_pocket_bottom_condition.class))
			b = true;
		else b = false;
		
		cavidade.setPassante(b);
		

		cavidade.setPosition(position);
		
		return cavidade;
	}

}
