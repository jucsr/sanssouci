package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EFlat_with_radius_hole_bottom;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;

public class FuroBaseArredondadaReader {
	
	public static FuroBaseArredondada getFuro(ERound_hole round_hole) throws SdaiException {

		if(round_hole.getBottom_condition(null).isKindOf(EFlat_with_radius_hole_bottom.class)){

//			double x = round_hole.getFeature_placement(null).getLocation(null)
//			.getCoordinates(null).getByIndex(1);
//			double y = round_hole.getFeature_placement(null).getLocation(null)
//			.getCoordinates(null).getByIndex(2);
//
//			double alturaBloco = ((EBlock)round_hole.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
//
//			double z = round_hole.getFeature_placement(null).getLocation(null)
//			.getCoordinates(null).getByIndex(3)*(-1) + alturaBloco;

			double diametroFuro = round_hole.getDiameter(null).getTheoretical_size(
					null);
			double profundidade = round_hole.getDepth(null).getPosition(null)
			.getLocation(null).getCoordinates(null).getByIndex(3)*(-1);

			double r1 = ((EFlat_with_radius_hole_bottom)round_hole.getBottom_condition(null)).getCorner_radius(null).getTheoretical_size(null);

			double tolerancia = ((EPlus_minus_value)round_hole.getDiameter(null).getImplicit_tolerance(null)).getUpper_limit(null);

			String id = round_hole.getIts_id(null);

			FuroBaseArredondada furo = new FuroBaseArredondada(id, 0, 0, 0, diametroFuro, profundidade, r1, tolerancia);

			FuroBasePlanaReader.setPosicaoFuroReader(furo, round_hole);

			return furo;
		}else{
			System.out.println(" ESTE NAO E UM FURO COM BASE ARREDONDADA! : " + round_hole.getBottom_condition(null).getClass());
		}
		return null;
	}
}
