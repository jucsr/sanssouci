package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EMachining_feature;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;

public class FaceReader {

	public static Face getFace(EMachining_feature feature) throws SdaiException {

		BlocoReader blocoReader = new BlocoReader(feature
				.getIts_workpiece(null));

		Bloco bloco = blocoReader.getBloco();

		int tipoFace = blocoReader.getFaceFeature(feature);

		Face face = (Face) bloco.getFaces().get(tipoFace);		
		
		return face;
	}
	
	//getFace() antigo!
//	private static Face getFace(ERound_hole round_hole) throws SdaiException {
//
//		EBlock block = (EBlock) round_hole.getIts_workpiece(null)
//				.getIts_bounding_geometry(null);
//
//		double comprimentoFace = block.getX(null);
//		double larguraFace = block.getY(null);
//		int tipo = 0;
//
//		Face face = new Face(tipo, comprimentoFace, larguraFace);
//
//		EElementary_surface elementary_surface = round_hole.getDepth(null);
//		EAxis2_placement_3d position = elementary_surface.getPosition(null);
//		EDirection axis = position.getAxis(null);
//		A_double xyz = axis.getDirection_ratios(null);
//
//		if (xyz.getByIndex(3) == 1) { // (0,0,1)
//			face.setTipo(Face.XY);
//			System.out.println("Face XY");
//		} else if (xyz.getByIndex(3) == -1) { // (0,0,-1)
//			face.setTipo(Face.YX);
//			System.out.println("Face YX");
//		} else if (xyz.getByIndex(2) == 1) { // (0,1,0)
//			face.setTipo(Face.XZ);
//			System.out.println("Face XZ");
//		} else if (xyz.getByIndex(2) == -1) { // (0,-1,0)
//			face.setTipo(Face.ZX);
//			System.out.println("Face ZX");
//		} else if (xyz.getByIndex(1) == 1) { // (1,0,0)
//			face.setTipo(Face.YZ);
//			System.out.println("Face YZ");
//		} else if (xyz.getByIndex(1) == -1) { // (-1,0,0)
//			face.setTipo(Face.ZY);
//			System.out.println("Face ZY");
//		}
//
//		return face;
//	}

}
