package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EDiameter_taper;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.SCombined_schema.EThrough_bottom_condition;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.FuroConico;

public class FuroConicoReader {
	
	public static FuroConico getFuro(ERound_hole round_hole) throws SdaiException {

		if(round_hole.getChange_in_diameter(null).isKindOf(EDiameter_taper.class)){

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

			double tolerancia = ((EPlus_minus_value)round_hole.getDiameter(null).getImplicit_tolerance(null)).getUpper_limit(null);
			
			double raio1 = ((EDiameter_taper)round_hole.getChange_in_diameter(null)).getFinal_diameter(null).getTheoretical_size(null)/2;

			String id = round_hole.getIts_id(null);

			FuroConico furo = new FuroConico(id, 0, 0, 0, diametroFuro, profundidade, tolerancia, raio1);

			FuroBasePlanaReader.setPosicaoFuroReader(furo, round_hole);
			
			boolean b;
			
			if (round_hole.getBottom_condition(null).isKindOf(EThrough_bottom_condition.class))
				b = true;
			else b = false;
			
			furo.setPassante(b);
			
			return furo;
			
		}else{
			System.out.println(" ESTE NAO E UM FURO CONICO! : " + round_hole.getChange_in_diameter(null).getClass());
		}
		return null;
	}
}
