package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.AaCartesian_point;
import jsdai.SCombined_schema.EBezier_surface;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ERegion_surface_list;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Region;

/**
 * 
 * @author Jc
 *
 */
public class RegionReader 
{
	public static Region getRegion(ERegion_surface_list eRegion_surface_list) throws SdaiException
	{
		String id = eRegion_surface_list.getIts_id(null);
		EBezier_surface surface = (EBezier_surface)eRegion_surface_list.getSurface_list(null).getByIndex(1);

		AaCartesian_point aAVertex = surface.getControl_points_list(null);
		Point3d[][] controlVertex = null;
		ArrayList<ArrayList<Point3d>> controlAux = new ArrayList<ArrayList<Point3d>>();
		SdaiIterator iterator1 = aAVertex.createIterator();
		
		while(iterator1.next())
		{
			ACartesian_point aVertex = aAVertex.getCurrentMember(iterator1);
			SdaiIterator iterator2 = aVertex.createIterator();
			
			ArrayList<Point3d> controlTmp = new ArrayList<Point3d>();
			
			while(iterator2.next())
			{
				ECartesian_point ponto = aVertex.getCurrentMember(iterator2);
				double x = ponto.getCoordinates(null).getByIndex(1);
				double y = ponto.getCoordinates(null).getByIndex(2);
				double z = ponto.getCoordinates(null).getByIndex(3);
				controlTmp.add(new Point3d(x, y, z));
			}
			controlAux.add(controlTmp);
		}
		controlVertex = new Point3d[controlAux.size()][controlAux.get(0).size()]; // ---- ESTRANHO
		for(int k = 0; k < controlAux.size(); k++)
		{
			for(int l = 0; l < controlAux.get(k).size(); l++)
			{
				controlVertex[k][l] = controlAux.get(k).get(l);
			}
		}
		Region region = new Region(Feature.REGION);
		region.setItsId(id);
		region.setControlVertex(controlVertex);
		setPosicaoRegionReader(region, eRegion_surface_list);
		double length = controlVertex[controlVertex.length - 1][controlVertex.length - 1].x - controlVertex[0][0].x;
		double width = controlVertex[controlVertex.length - 1][controlVertex.length - 1].y - controlVertex[0][0].y;
		region.setLength(length);
		region.setWidth(width);
		return region;
	}
	private static void setPosicaoRegionReader(Region region, ERegion_surface_list eRegion_surface_list) throws SdaiException
	{
		double x = 0, y = 0, z = 0;
		
		Face faceAtual = FaceReader.getFace(eRegion_surface_list);
		
		if(faceAtual.getTipo()==Face.XY){
			
			x = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);

			y = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);

			double alturaBloco = ((EBlock)eRegion_surface_list.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);

			z = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3)*(-1) + alturaBloco;
			
//			System.out.println("Profundidade Face : " + faceAtual.getProfundidadeMaxima() );
//			System.out.println("Comprimento Face : " + faceAtual.getComprimento() );
//			System.out.println("Largura Face : " + faceAtual.getLargura() );
			
//			System.out.println("Index(1) : " + round_hole.getFeature_placement(null).getLocation(null)
//			.getCoordinates(null).getByIndex(1));
			
//			System.out.println(x+","+y+","+z);
			
		}else if(faceAtual.getTipo()==Face.YX){
		
			x = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = faceAtual.getLargura() - eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
			z = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
		}else if(faceAtual.getTipo()==Face.YZ){

			x = faceAtual.getComprimento() - eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);

			y = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);

			z = faceAtual.getProfundidadeMaxima() - eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
		}else if(faceAtual.getTipo()==Face.ZY){
		
			x = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			y = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
			z = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
		}else if(faceAtual.getTipo()==Face.XZ){
		
			x = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			z = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
		}else if(faceAtual.getTipo()==Face.ZX){
		
			x = eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = faceAtual.getLargura() - eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			z = faceAtual.getProfundidadeMaxima() - eRegion_surface_list.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
		}
		
		region.setPosicao(x, y, z);
	}
}
